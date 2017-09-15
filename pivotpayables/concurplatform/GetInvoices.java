package com.pivotpayables.concurplatform;
/**
 * @author John Toman
 * 8/11/16
 * 
 * This class pulls from Concur payment requests (a.k.a., "invoices") submitted within the previous 60 days AND
 * that were Extracted since the last time invoices were successfully pull from Concur.
 * 
 * Because there isn't a query parameter for "Extracted Date" like there is in the Expense Report API
 * we have to use the query parameter of "Submit Date" as a proxy for this.  The reasoning is payment requests
 * Concur Invoice extracted will have been recently submitted.   Certainly, they will have been submitted
 * well before 60 days before the Extract Date.  Once we pull these payment requests, we can examine their
 * Extract Date and determine is this date is after the last time we successfully pulled invoices.
 * 
 * Note that Concur calls an "invoice" a "payment request".  The resources in the Concur Invoice API
 * related to invoices are,
 * Payment Request Digest - a summary of the information in a payment request
 * Payment Request - complete detail of the information in a payment request
 * 
 * The idea is to use the GET PaymentRequestDigest as the way to search for payment requests that meet criteria.  Once
 * you find a payment request that you are interested, you use GET PaymentRequest to get all of its detail.
 *
 */

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.json.JSONException;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.pivotpayables.expensesimulator.Company;
import com.pivotpayables.expensesimulator.Employee;
import com.pivotpayables.expensesimulator.MongoDBFunctions;
import com.pivotpayables.prime.FieldContext;


//import static java.lang.System.out;

public class GetInvoices {
	static String companyID;// the CompanyID for the Company in the MongoDB or MySQL database for the company
	
	// MongoDB Host and Port
	static final String host = "localhost";
	static final int port = 27017;
	
	// Connect to the Company_Data database, and then get the collections for Companies and Employees
	private static MongoDBFunctions myMongoCompanyFunctions = new MongoDBFunctions (host, port, "Company_Data");// create a MongoDB Client for the specified host and port, and get the Company_Data database 
	private static DBCollection compCollection = myMongoCompanyFunctions.setCollection("Companies");// get the Companies collection
	private static DBCollection empCollection = myMongoCompanyFunctions.setCollection("Employees");// get the Employees collection
	private static DBCollection conCollection = myMongoCompanyFunctions.setCollection("FieldContexts");// get the Employees collection
	
	
	// Connect to  the Expense_Data database, and then get the Expenses collection
	private static MongoDBFunctions myMongoExpenseFunctions = new MongoDBFunctions (host, port, "Expense_Data");// create a MongoDB Client for the specified host and port, and get the Expense_Data database
	private static DBCollection expCollection = myMongoExpenseFunctions.setCollection("Expenses");// get the Expense collection for the specified host and port, and get the Expense_Data databaseprivate static DBCollection chgCollection = myMongoExpenseFunctions.setCollection("AccountActivityCharges");// get the Expense collection for the specified host and port, and get the Expense_Data database
	
	private static String status = "failed";
	private static Expense expense = new Expense(); // placeholder for an Expense object

	public static String getInvoices (String key, String lastsuccess) throws ParseException, JsonParseException, JsonMappingException, JSONException, IOException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
	
	ArrayList<DBObject> Docs = new ArrayList<DBObject>();// an ArrayList with MongoDB document elements
	BasicDBObject myDoc = null;// placeholder BasicDBObject
	String RequestID;
	ArrayList<PaymentRequest> requests = new ArrayList<PaymentRequest>();//
	Invoice invoice = new Invoice(); // placeholder for an ExpenseInvoice object
	
	String user;// placeholder for Invoice Owner Concur Login ID
	Employee employee;// placeholder for an Employee object
	ConcurFunctions concurfunctions = new ConcurFunctions();// instance of the Concur functions
	Invoices invoicefunctions = new Invoices();// instance of the Invoices functions
	FieldContext context = new FieldContext();// placeholder for a FieldContext object
	ArrayList<FieldContext> contexts = new ArrayList<FieldContext>();// placeholder for an ArrayList of FieldContext objects
	SimpleDateFormat invsdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");// date format the Invoice APIs use
	SimpleDateFormat expsdf = new SimpleDateFormat("yyyy-MM-dd");
	Date date = null;// placeholder for a Date object
	Date lastsuccessdate = null;// the lastsuccess as a Date
	String submitted;
	Company myCompany = new Company();// placeholder for a Company object 
	Map<String, String> queryparameters = new HashMap<String, String>();// a HashMap to hold key-value pairs for a query parameter
	ImageURL imageurl=null;
	Path currentRelativePath = Paths.get("");// find the current relative path
	String filepath = (currentRelativePath.toAbsolutePath().toString()) + "/ImageFiles/";// construct the absolute path to the ImageFiles directory
	String filename;// placeholder for image file name
	
	// Begin by finding the Company associated to the provided key
	String companyID = FindConcurCompany.getCompanyID(key);// get the CompanyID for the company associated to the owner of the key
	myCompany = myCompany.doctoCompany(myMongoCompanyFunctions.getDoc(compCollection, companyID));// convert the Company document into a Company object
	Docs =	myMongoCompanyFunctions.getDocsByField(conCollection, "CompanyID", companyID); // get the Field Contexts documents for the specified company
	if (Docs.size() >0){// then there are Field Contexts for this Company
		for (DBObject doc:Docs){// iterate for each Field Context document
			context = context.doctoFieldContext(doc);// convert the Field Context document into a Field Context object
			if ((context.getContext().equals("SimpleList")) || (context.getFormType().startsWith("Level")) || ((context.getFormType().startsWith("ListID")) && (context.getContext().equals("ConnectedList"))) ){// then this is field context related to processing list fields in Invoice
				contexts.add(context);// add this Field Context to the ArrayList of Field Context objects for this company
			}
		}// for (DBObject doc:Docs)
	}// if (Docs.size() >0)	
	// Now ready to pull payment requests

	queryparameters = new HashMap<String, String>();// initialize the query parameters HashMap
	
	//calculate the lastSubmitDate to be 60 days before last success
	lastsuccessdate = expsdf.parse(lastsuccess);// convert lastsuccess to a Date object
	Calendar day = Calendar.getInstance();// initialize a Calendar object
	day.setTime(lastsuccessdate);; // set the day to date of last success
	day.add(Calendar.DATE, -60);// set the date to 60 days before last success
	date = day.getTime();// convert calendar into a Date object
	submitted = expsdf.format(date);// convert date into a String date
	
	
	if (lastsuccess.equals("ALL")) {
		queryparameters.put("approvalStatus", "R_APPR");// search for invoices with an Approval Status of Approved
		requests = invoicefunctions.getPaymentRequests(key, queryparameters);// pull the invoices from Concur Expense
		
	} else {
		queryparameters.put("submitDateAfter", submitted);// search for Payment Request Digests (a.k.a., "invoices") with a Submit Date after this date
		requests = invoicefunctions.getPaymentRequests(key, queryparameters);// pull the Payment Request Digests from Concur Expense
	}// if (lastsuccess.equals("ALL"))
	if (requests.size() > 0) { // then there is at least one Payment Request Digest to process
	    for(PaymentRequest request:requests){// iterate for each Payment Request Digest 
	        RequestID = request.getID();// get the Payment Request ID for this invoice
	        
	     // Begin by doing a duplicate check for this Request ID
	        Docs = myMongoExpenseFunctions.getDocsByField(expCollection, "ReportID", RequestID);// search the Mongo DB for expenses with this Request ID
	        if (Docs.size() == 0){// then this payment request isn't in the MongoDB
	        	invoice = invoicefunctions.getInvoice(RequestID, key);// get the Invoice for this Payment Request ID
				if (invoice.getExtractDate() != null){// then this Invoice has an Extract Date and therefore can be checked for lastsuccess

					date = invsdf.parse(invoice.getExtractDate());// convert ExtractDate from a String date to a Date object
					if (date.after(lastsuccessdate)) {// then the Extract date is after the last success date.  So, process this invoice. 
						// Convert this invoice into an Expense object
						expense = InvoicetoExpense.getExpense(invoice, contexts, key);
				       
						// Determine who is the Invoice Owner, and then determine the Employee object for this person
						user = request.getOwnerLoginID();// get the Login ID for the invoice owner from the Payment Request Digest
				        Docs = myMongoCompanyFunctions.getDocsByField(empCollection, "LoginID", user);// search for an Employee document with the Invoice Owner's Concur Login ID
				        employee = new Employee();// initiate an Employee object
				        
				        if (Docs.size() > 0) {// found Employee document that matches this user's Concur Login ID
				        	employee = employee.doctoEmployee(Docs.get(0));// get the first Employee document that matches
				            employee.setEmployerCompanyID(myCompany.getID());
				            employee.setEmployerCompanyName(myCompany.getDBAName());
				            employee.setDisplayName(employee.getFirstName() + " " + employee.getLastName());
				        } else {
				            employee = concurfunctions.getEmployee(user, key);// get the Concur User using the Concur Functions
				            if (employee == null){// then couldn't find this user in Concur
				            	// so, create a default user using the login ID
				            	employee = new Employee();// initiate an Employee object
				            	employee.setLoginID(user);
				            	String[] parts = user.split("@");// split the login ID at the @ symbol
				            	employee.setDisplayName(parts[0]);
				            }
			            	employee.setEmployerCompanyID(myCompany.getID());
				            employee.setEmployerCompanyName(myCompany.getDBAName());
				            
				            BasicDBObject myDocument = employee.getDocument();// convert the Employee object into an Employee document
				            status = myMongoCompanyFunctions.addDoc(empCollection, myDocument);// add the employee to the MongoDB
							if (status.equals("failed")) {// then not able to store the Employee document; return with a failed status
								return status;
							}// if (status.equals("failed"))

				        }// if Doc.size > 0 block
						//TODO: write code the calculates the PayeeVendorID and PayeeAddressID
						/*
						 * The Payment Request API doesn't provide either the VendorID or Remittance ID for a payment request.
						 * There is actually no required element in the response that indicates who the vendor is.  The only
						 * way to determine this is to calculate the vendor by using the Vendor API, where you can search by
						 * vendor name, and address related criteria to resolve the vendor.  
						 * 
						 * The VendorName is an of the Payment Request Digest API.  It is also available in the VendorAddress parent element
						 * in the Payment Request API.
						 * 
						 * Address criteria such as postal code, city, state is available only in the VendorAddress parent element.
						 * 
						 * 
						 *
						 */
						//myExpense.setPayeeAddressID(payeeAddressID);
						//myExpense.setPayeeVendorID(payeeVendorID);
						expense.setMerchantName(request.getVendorName());
						expense.setCompanyID(companyID);
						expense.setReportOwnerID(request.getOwnerLoginID());
						expense.setEmployeeID(employee.getID());// the GUID for the employee who incurred the invoice by purchasing a good or service for business use
						expense.setEmployeeDisplayName(employee.getDisplayName());// the employee who incurred the invoice
						expense.setEmployeeFirstName(employee.getFirstName());
						expense.setEmployeeMiddleInitial(employee.getMiddleInitial());
						// initialize the status for PivotPrime and PivotNexus to Not Processed
						expense.setPrimeStatus("Not Processed");
						expense.setNexusStatus("Not Processed");
						 
						// Get the invoice image for this Payment Request
				        imageurl = invoicefunctions.getInvoiceImageURL(RequestID, key);// get from the Concur platform the ImageURL for this Invoice
				        if (imageurl !=null) { // then there is a Invoice image to process
							filename = RequestID + ".PDF";// set the file name for the image to its Invoice ID
							Image.downloadImageFile(imageurl.getUrl(), filepath + filename);// using this URL, download the Invoice image and store it in the file system
							myMongoExpenseFunctions.putImage(filepath, filename);
				        }//  if (imageurl !=null)
						// Save the Expense document in the MongoDB
						myDoc = expense.getDocument();//convert the Expense object into an Expense document

					    status = myMongoExpenseFunctions.addDoc(expCollection, myDoc);//add the Expense document to the Expenses collection
						if (status.equals("failed")) {// then return "failed" to indicate this pull wasn't successful
							rollbackExpense();
							return status;
						}// if (status.equals("failed")) on save expense
					}// if (date.after(lastsuccessdate))
				}// if (invoice.getExtractDate() != null)
	        }// if (Docs.size() == 0)
		    
		}// for(PaymentRequest request:requests)    
	}// if (requests.size() > 0)
	    
	return status;
	    
	}// getInvoices
	
	private static void rollbackExpense()  {
		// Performs a rollback 
		// remove from the MongoDB any documents stored while processing this Payment Request
		myMongoExpenseFunctions.deleteDoc(expCollection, "ReportID", expense.getReportID().toString());// delete Expense documents for this Payment Request (ReportID)		

	    
	}// rollbackExpense
		
}
	
	


