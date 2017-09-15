package com.pivotpayables.concurplatform;


/**
 * @author John Toman
 * 6/21/2016
 * 
 * This class pulls paid expense reports with a Paid Date on or after a specified date, and
 * then stores the expenses in these expense reports as Expense documents in the MongoDB.
 * 
 * For customers using PivotPrime it creates and stores AccountActivityCharge or "Charge" documents.  
 * It uses their specified field contexts: Account, Activity, Phase, Task, Is Billable, and VAT Domestic
 * 
 * For customers using PivotNexus is creates and stores ReportPayee documents for these expense reports.
 * 
 * Now uses doctoObject methods using the class associated to the Object.
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
import com.pivotpayables.expensesimulator.GUID;
import com.pivotpayables.expensesimulator.MongoDBFunctions;


public class GetPaidReports  {

	
	static final String host = "localhost";
	static final int port = 27017;
	
	
	
	// Connect to the Company_Data database, and then get the collections for Companies and Employees
	private static MongoDBFunctions myMongoCompanyFunctions = new MongoDBFunctions (host, port, "Company_Data");// create a MongoDB Client for the specified host and port, and get the Company_Data database 
	private static DBCollection compCollection = myMongoCompanyFunctions.setCollection("Companies");// get the Companies collection
	private static DBCollection empCollection = myMongoCompanyFunctions.setCollection("Employees");// get the Employees collection
	
	// Connect to  the Expense_Data database, and then get the Expenses collection
	private static MongoDBFunctions myMongoExpenseFunctions = new MongoDBFunctions (host, port, "Expense_Data");// create a MongoDB Client
	private static DBCollection expCollection = myMongoExpenseFunctions.setCollection("Expenses");// get the Expense collection for the specified host and port, and get the Expense_Data database
	
	private static BasicDBObject myDoc = null;// placeholder BasicDBObject
	private static DBObject doc;
	private static ArrayList<DBObject> Docs = new ArrayList<DBObject>();// an ArrayList with MongoDB document elements

	private static Expense expense; // placeholder for an Expense object

	
	public static String getExpenses (String key, String lastsuccess) throws ParseException, JsonParseException, JsonMappingException, JSONException, IOException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String status = "failed";
		Company myCompany = new Company();// placeholder for a Company object 
		Map<String, String> queryparameters = new HashMap<String, String>();// a HashMap to hold key-value pairs for a query parameter
		Expenses expensefunctions = new Expenses();// Expenses functions
		ExpenseReports reportfunctions = new ExpenseReports();// ExpenseReports functions
		ImageURL imageurl=null;
		Path currentRelativePath = Paths.get("");// find the current relative path
		String filepath = (currentRelativePath.toAbsolutePath().toString()) + "/ImageFiles/";// construct the absolute path to the ImageFiles directory
		String filename;// placeholder for image file name
		ArrayList<ExpenseReport> reports = new ArrayList<ExpenseReport>();
		ArrayList <ExpenseEntry> xpenses;// ArrayList of ExpenseEntry object
		ReportDetails details;// placeholder for a ReportDetails object
		ArrayList<Itemization> items;// ArrayList of Itemization objects
		ArrayList<Allocation> allocations;// ArrayList of Allocation objects
		ArrayList<JournalEntry> journals;// ArrayList of JournalEntry objects
		ArrayList<VATData> vatdetails;// ArrayList of VATData objects
		String user;// placeholder for Report Owner Concur Login ID
		Employee employee= new Employee();// placeholder for an Employee object
		String companyID;// the CompanyID for the Company in the MongoDB or MySQL database for the company
		int j,k,l, m;// indexes for adding elements to the ArrayList for itemizations, allocations, journals, and vatdetails

		 


		companyID = FindConcurCompany.getCompanyID(key);// get the CompanyID for the company associated to the owner of the key
		doc =	myMongoCompanyFunctions.getDoc(compCollection, companyID); // get the Company document for the specified company
		Docs = new ArrayList<DBObject>();// an ArrayList with MongoDB document elements
		myCompany = myCompany.doctoCompany(doc);// convert the Company document into a Company object
		
		//calculate the extracted date to be 2 days before last success
		SimpleDateFormat expsdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;// placeholder for a Date object
		Date lastsuccessdate = null;// the lastsuccess as a Date
		String extracted;
		lastsuccessdate = expsdf.parse(lastsuccess);// convert lastsuccess to a Date object
		Calendar day = Calendar.getInstance();// initialize a Calendar object
		day.setTime(lastsuccessdate);; // set the day to date of last success
		day.add(Calendar.DATE, -2);// set the date to be 2 days before last success
		date = day.getTime();// convert calendar into a Date object
		extracted = expsdf.format(date);// convert date into a String date

		queryparameters = new HashMap<String, String>();// initialize the queryparameters HashMap
		user = "ALL";// search for results for All report owners; not just reports from the user the key is associated
		queryparameters.put("user", user);
		if (lastsuccess.equals("ALL")) {
			queryparameters.put("paymentStatusCode", "P_PAID");// search for reports with a Paid Date after this date
			reports = reportfunctions.getReports(key, queryparameters);// pull the reports from Concur Expense
			
		} else {
			queryparameters.put("paidDateAfter", extracted);// search for reports with a Paid Date after this date
			reports = reportfunctions.getReports(key, queryparameters);// pull the reports from Concur Expense
		}// if (lastsuccess.equals("ALL"))
		
		
	    for (ExpenseReport report:reports){// iterate for each report 
	    	String reportID = report.getID();// get the Report ID for this invoice
	        
		     // Begin by doing a duplicate check for this Report ID
		        Docs = myMongoExpenseFunctions.getDocsByField(expCollection, "ReportID", reportID);// search the Mongo DB for expenses with this Report ID
		        if (Docs.size() == 0){// then this expense report isn't in the MongoDB, so process it
		        	user = report.getOwnerLoginID();
			        Docs = myMongoCompanyFunctions.getDocsByField(empCollection, "LoginID", user);// search for an Employee document with the Report Owner's Concur Login ID

			        if (Docs.size() > 0) {// found Employee document that matches this user's Concur Login ID
			        	doc = Docs.get(0);// get the first Employee document that matches
			        	employee = employee.doctoEmployee(doc);
			            employee.setEmployerCompanyID(myCompany.getID());
			            employee.setEmployerCompanyName(myCompany.getDBAName());
			            employee.setDisplayName(employee.getFirstName() + " " + employee.getLastName());
			        } else {
			            employee = GetConcurUser.getEmployee(user, key);// get the Concur User from the Concur Platform
			            if (employee != null) {
			            	employee.setID(GUID.getGUID(4));
				            employee.setEmployerCompanyID(companyID);
				            employee.setEmployerCompanyName(myCompany.getDBAName());
				            employee.setDisplayName(employee.getFirstName() + " " + employee.getLastName());   
			        	} else {
			        		employee = new Employee();
			            	employee.setID(GUID.getGUID(4));
			        		employee.setEmployee_ID(user);
			        		employee.setEmployerCompanyID(companyID);
			        		employee.setEmployerCompanyName(myCompany.getDBAName());
			        		employee.setFirstName("");
			        		employee.setLastName("");

				            employee.setDisplayName(user);
			        	}// if (employee != null)
			            
			            BasicDBObject myDocument = employee.getDocument();// convert the Employee object into an Employee document
			            status = myMongoCompanyFunctions.addDoc(empCollection, myDocument);// add the employee to the MongoDB
						if (status.equals("failed")) {// then not able to store the Employee document; return with a failed status
							return status;
						}// if (status.equals("failed"))

			        }// if Doc.size > 0 block for Employee documents
			        
			    
			        imageurl = reportfunctions.getReportImageURL(reportID, key);// get from the Concur platform the ImageURL for this report
			        if (imageurl !=null) { // then there is a report image to process
						filename = reportID + ".PDF";// set the file name for the image to its Report ID
						Image.downloadImageFile(imageurl.getUrl(), filepath + filename);// using this URL, download the report image and store it in the file system
						myMongoExpenseFunctions.putImage(filepath, filename);
			        }// if (imageurl !=null)
			        details = expensefunctions.getReportDetails(reportID, key);// get the ReportDetails for this report
			        
			        if (details != null){// then there is a ReportDetails object for this report
						xpenses = details.getExpenses();// get the list of Expense Entry objects for this report
						
						for (ExpenseEntry entry:xpenses) { // Iterate for each Expense Entry object in the report
							
							expense = expensefunctions.getExpense(report, entry.getReportEntryID(), key);// get the Expense object for this Expense Entry
							expense.setID(GUID.getGUID(4));// assign a Pivot Payables GUID to this expense
							expense.setPaymentTypeCode(entry.getPaymentTypeCode());
							expense.setPostedCurrency(report.getCurrencyCode());
							items = entry.getItems();// get the list of Itemization objects for the Expense Entry
							j=0;// initialize the index for itemizations
							for (Itemization item:items){// iterate for each Itemization object for this Expense Entry
								allocations = item.getAllocations();// get the list of Allocation objects for this Itemization
								k=0;// initialize the index for allocations
								for (Allocation allocation:allocations){// Iterate for each Allocation object for this Itemization
									journals = allocation.getJournals();// get the list of Journal Entry objects for this Allocation
									l =0;// initialize the index for journals
									for (JournalEntry journal:journals){// iterate for each Journal Entry object
										journal.setID(GUID.getGUID(4));//assign a Pivot Payables GUID for this Journal Entry
										journal.setPostedCurrency(expense.getPostedCurrency());
										journals.set(l, journal);// add the journal at the index
										l++;// increment the index
									}// for (JournalEntry journal:journals)
									m =0;// initialize the index for the vatdetails
									vatdetails = allocation.getVatdetails();// get the list of VAT Data objects for this Allocation
									for (VATData vatdetail:vatdetails){// iterate for each VAT Data object
										vatdetail.setID(GUID.getGUID(4));//assign a Pivot Payables GUID for this VAT Data
										vatdetail.setOriginalCurrency(expense.getOriginalCurrency());
										vatdetail.setPostedCurrency(expense.getPostedCurrency());
										vatdetails.set(m, vatdetail);// add the vatdetails at the index
										m++;// increment the index
									}// for (VATData vatdetail:vatdetails)
									allocation.setID(GUID.getGUID(4));//assign a Pivot Payables GUID for this Allocation
									allocation.setJournals(journals);// add the Journal Entries to the Allocation
									allocation.setVatdetails(vatdetails);// add the VAT Data to the Allocation

									allocations.set(k, allocation);// add the allocation at the index
									k++;// increment the index
								}// for (Allocation allocation:allocations)
							
								item.setID(GUID.getGUID(4));// assign a Pivot Payables GUID to this itemization
								item.setEntryID(expense.getID());// copy the ID for the parent, Expense
								item.setEntry_ID(expense.getEntry_ID());
								item.setTransactionCurrencyCode(expense.getOriginalCurrency());// copy the Expense's original currency to the itemization
								item.setPostedCurrency(expense.getPostedCurrency());// copy the Expense's posted currency to the itemization
								item.setAllocations(allocations);
								items.set(j, item);// add the item at the index
								j++;// increment the index
							}// for (Itemization item:items)
							expense.setItems(items);// add the Itemizations to the Expense
							expense.setCompanyID(companyID);
							expense.setEmployeeID(employee.getID());// the GUID for the employee who incurred the expense by purchasing a good or service for business use
							expense.setEmployeeDisplayName(employee.getDisplayName());// the employee who incurred the expense
							expense.setEmployeeFirstName(employee.getFirstName());
							expense.setEmployeeMiddleInitial(employee.getMiddleInitial());
							expense.setEmployeeLastName(employee.getLastName());
							expense.setApprovalStatus(report.getApprovalStatusCode());// the approval status of the expense report that contains this expense
							expense.setPaymentStatus(report.getPaymentStatusCode());// the payment status of the expense report that contains this expense. Is "Paid" when Concur Expense extracts the expense report.
							expense.setPaidDate(report.getPaidDate());// when the expense report this expense entry is a member was paid
							
							expense.setAmountDueCompanyCard(report.getAmountDueCompanyCard());
							expense.setAmountDueEmployee(report.getAmountDueEmployee());
							expense.setReportName(report.getName());
							expense.setReportPurpose(report.getPurpose());
							expense.setReportTotal(report.getTotal());
							expense.setPolicyID(report.getPolicyID());
							expense.setLedgerName(report.getLedgerName());
							expense.setEmployeeCountry(report.getCountry());
							expense.setEmployeeState(report.getCountrySubdivision());
							expense.TaxNexus();// set the Tax Nexus for this expense
							expense.setPersonalExpense(report.getPersonalAmount());
							expense.setTotalApprovedAmount(report.getTotalApprovedAmount());
							// initialize the status for PivotPrime and PivotNexus to Not Processed
							expense.setPrimeStatus("Not Processed");
							expense.setNexusStatus("Not Processed");
							
							imageurl = expensefunctions.getExpenseImageURL(expense, key);// get the Image URL for this expense's receipt image
							if (imageurl != null){// then there is an expense entry image for this expense entry
								filename = expense.getEntry_ID();// set the name of the image file to its Entry ID
								Image.downloadImageFile(imageurl.getUrl(), filepath + filename);// using the file download URL, download the image file and store it in the specified path
								myMongoExpenseFunctions.putImage(filepath, filename);// store the file in the MongoDB
							}// if (imageurl != null)
							
						    myDoc = expense.getDocument();//convert the Expense object into an Expense document
						    
						    status = myMongoExpenseFunctions.addDoc(expCollection, myDoc);//add the Expense document to the Expenses collection
							if (status.equals("failed")) {// then return "failed" to indicate this pull wasn't successful
								rollbackExpenses();
								return status;
							}// if (status.equals("failed")) on save expense
					}// for (ExpenseEntry entry:xpenses)
					
			      }// if (details != null)
		        }//if (Docs.size() == 0)
	    }// for(ExpenseReport report:reports)
		    
		return status;
	    
	}// getExpenses
	
	
	private static void rollbackExpenses () throws JsonParseException, JsonMappingException, JSONException, IOException {
		// Performs a rollback 
		// remove from the MongoDB any documents stored while processing this Payment Request		
		myMongoExpenseFunctions.deleteDoc(expCollection, "ReportID", expense.getReportID().toString());// delete Expense documents for this Payment Request (ReportID)		
}// rollbackExpenses
}

