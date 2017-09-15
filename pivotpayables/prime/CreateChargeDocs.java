package com.pivotpayables.prime;
/**
 * @author John Toman
 * 8/15/2016
 * 
 * This class generates the AccountActivityCharge documents for Expense documents in the MongoDB
 * for companies that use PivotPrime. 
 * 
 * It uses the PivotPrimeStatus field of the Expense to determine whether PivotPrime has processed the expense.
 * 
 *
 */

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.lang.Math;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.pivotpayables.concurplatform.Allocation;
import com.pivotpayables.concurplatform.CustomField;
import com.pivotpayables.concurplatform.Expense;
import com.pivotpayables.concurplatform.FindConcurCompany;
import com.pivotpayables.concurplatform.Itemization;
import com.pivotpayables.concurplatform.ItemtoAllocation;
import com.pivotpayables.concurplatform.VATData;
import com.pivotpayables.expensesimulator.Company;
import com.pivotpayables.expensesimulator.GUID;
import com.pivotpayables.expensesimulator.MongoDBFunctions;

public class CreateChargeDocs {
	// MongoDB Host and Port
	static final String host = "localhost";
	static final int port = 27017;
	
	// Connect to the Company_Data database, and then get the collections for Companies and Employees
	private static MongoDBFunctions myMongoCompanyFunctions = new MongoDBFunctions (host, port, "Company_Data");// create a MongoDB Client for the specified host and port, and get the Company_Data database 
	private static DBCollection compCollection = myMongoCompanyFunctions.setCollection("Companies");// get the Companies collection
	private static DBCollection conCollection = myMongoCompanyFunctions.setCollection("FieldContexts");// get the FieldContexts collection
	
	// Connect to  the Expense_Data database, and then get the Expenses collection
	private static MongoDBFunctions myMongoExpenseFunctions = new MongoDBFunctions (host, port, "Expense_Data");// create a MongoDB Client for the specified host and port, and get the Expense_Data database
	private static DBCollection expCollection = myMongoExpenseFunctions.setCollection("Expenses");// get the Expense collection for the specified host and port, and get the Expense_Data databaseprivate static DBCollection chgCollection = myMongoExpenseFunctions.setCollection("AccountActivityCharges");// get the Expense collection for the specified host and port, and get the Expense_Data database
	private static DBCollection chgCollection = myMongoExpenseFunctions.setCollection("AccountActivityCharges");// get the AccountActivityCharges collection for the specified host and port, and get the Expense_Data database
		
	private static BasicDBObject myDoc = null;// placeholder BasicDBObject
	private static ArrayList<DBObject> Docs;// an ArrayList with MongoDB document elements
	
	private static String accountfield=null;
	private static String activityfield=null;
	private static String phasefield=null;
	private static String taskfield=null;
	private static String billablefield=null;
	private static CustomField accountcustom=null;// placeholder for the CustomField object for the account value
	private static CustomField activitycustom=null;// placeholder for the CustomField object for the activity value
	private static CustomField phasecustom=null;// placeholder for the CustomField object for the phase value
	private static CustomField taskcustom=null;// placeholder for the CustomField object for the task value
	private static CustomField billablecustom=null;// placeholder for the CustomField object for the Is Billable value
	private static FieldContext accountcontext=null;
	private static FieldContext activitycontext = null;
	private static FieldContext phasecontext = null;
	private static FieldContext taskcontext = null;
	private static FieldContext billablecontext = null;
	private static FieldContext vatdomestic = null;
	private static Boolean billable = false;
	
	private static AccountActivityCharge charge;// placeholder for an AccountActivityCharge object
	private static Itemization item;
	private static Allocation allocation;// placeholder for an Allocation object
	private static Expense expense = new Expense();// placeholder for an Expense object
	
	private static String formtype;
	private static String status = "failed";
	
	public static void processExpenses(String key) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ParseException{
		
		/* This method processes Expense documents in the MongoDB discovering documents that need PivotPrime Charge documents.
		 * 
		 */

		Company myCompany = new Company();// placeholder for a Company object 
		FieldContext context = new FieldContext();// placeholder for a FieldContext object
		ArrayList<FieldContext> expcontexts= new ArrayList<FieldContext>();// an ArrayList of FieldContext objects for processing Expense objects from Concur Expense
		ArrayList<FieldContext> invcontexts = new ArrayList<FieldContext>();// an ArrayList of FieldContext objects for processing Expense objects from Concur Invoice
		HashMap<String, String> criteria = new HashMap<String, String>();// initialize the criteria HashMap
		ArrayList<Expense> invoices = new ArrayList<Expense>();
		ArrayList<Expense> expenses = new ArrayList<Expense>();

		String companyID = FindConcurCompany.getCompanyID(key);// get the CompanyID for the company associated to the owner of the key
		myCompany = myCompany.doctoCompany(myMongoCompanyFunctions.getDoc(compCollection, companyID));// convert the Company document into a Company object
		
		// get the Field Context settings for this Company ID
		Docs =	myMongoCompanyFunctions.getDocsByField(conCollection, "CompanyID", companyID); // get the Field Contexts documents for the specified company
		
		if (Docs.size() >0){// then there are Field Contexts for this Company, which means they use PivotPrime
			
			/*  So, separate field contexts into Expense and Invoice fields contexts so that we know how to find the PivotPrime field contexts
			 *  by whether the Expense object came from Concur Expense or Concur Invoice.
			 *  
			 *  See, Concur Expense and Concur Invoice use different data entry forms.  This means that the field contexts for Concur Expense
			 *  can be different than the field contexts for Concur Invoice.
			 *  
			 *  The PaymentTypeCode field in the Expense object notes the source of the Expense object.
			 *  PaymentTypeCode = "INV" means this expense came from Concur Invoice
			 *  other payment type codes mean this expense came from Concur Expense
			 */
			
			
			for (DBObject doc:Docs){// iterate for each Field Context document to find the PivotPrime field contexts
				context = context.doctoFieldContext(doc);// convert the Field Context document into a Field Context object
				formtype = context.getFormType();
				if ((formtype.equals("PaymentRequest")) || (formtype.equals("InvoiceAllocation")) ){// then this is a PivotPrime field context related to Concur Invoice
					InvoiceFieldContexts.processFormType(context);// convert Invoice contexts for PaymentRequest or InvoiceAllocation to ones that CreateChargeDocs can use
					invcontexts.add(context);// add this Field Context to the ArrayList of invoice Field Context objects for this company
				} else if ((formtype.equals("ExpenseEntry")) || (formtype.equals("Allocations")) ){// then this is a PivotPrime field context related to Concur Expense
					expcontexts.add(context);// add this Field Context to the ArrayList of expense Field Context objects for this company
				}
			}
			
			// find Expense documents for this Company that haven't been processed by PivotPrime
			Docs = new ArrayList<DBObject>();
			criteria.put("CompanyID", companyID);// add the criterion for this Company
			criteria.put("PrimeStatus", "Not Processed");// add the criterion for PivotPrimeStatus of Not Processed
			Docs = myMongoExpenseFunctions.getDocsByCriteria(expCollection, criteria);// find Expense documents for this Company that are Not Processed
			
			// separate Expense documents into invoices and expenses
			for (DBObject doc:Docs){// iterate for each Expense document
				expense= expense.doctoExpense(doc);
				if (expense.getPaymentTypeCode().equals("INV")){// then this Expense object came from Concur Invoice
					invoices.add(expense);
				} else {// then this Expense object came from Concur Expense
					expenses.add(expense);
				}
			}
			
			if (invoices.size() > 0){// then there is at least on Expense from Concur Invoice to process// So, set the field contexts using the ones for Concur Invoice
				
				for (FieldContext itcontext:invcontexts) {// iterate for each Invoice, FieldContext object for this CompanyID
					switch (itcontext.getContext()){// switch on the PivotPrime field context
			    	case "Account":// then there is a field context for Account
					    accountcontext = itcontext;// convert the Field Context document into a FieldContext object
					    accountfield = accountcontext.getFieldID();// set the accountfield to the Field ID for the specified custom field in FieldContext object
					    break;
					case "Activity":// then there is a field context for Activity
					    activitycontext = itcontext;// convert the Field Context document into a FieldContext object
					    activityfield = activitycontext.getFieldID();// set the activityfield to the Field ID for the specified custom field in FieldContext object
					    break;
					case "Phase":// then there is a field context for Phase
					    phasecontext = itcontext;// convert the Field Context document into a FieldContext object
					    phasefield = phasecontext.getFieldID();// set the phasefield to the Field ID for the specified custom field in FieldContext object
					    break;
					case "Task":// then there is a field context for Task
					    taskcontext = itcontext;// convert the Field Context document into a FieldContext object
					    taskfield = taskcontext.getFieldID();// set the taskfield to the Field ID for the specified custom field in FieldContext object}// if loop
					    break;
					case "IsBillable":// then there is a field context for IsBillable
					    billablecontext = itcontext;// convert the Field Context document into a FieldContext object
					    billablefield = billablecontext.getFieldID();// set the taskfield to the Field ID for the specified custom field in FieldContext object}// if loop
					    break;
					case "VAT":// then there is a Field Context for Domestic VAT
						if (context.getFormType().equals("Domestic")){
							vatdomestic = itcontext;// convert the Field Context document into a FieldContext object
						}// if (context.getFormType().equals("Domestic"))
						break;
					}// switch (context.getContext()) block
				}// for (FieldContext itcontext:invcontexts)
				// Now, using these field contexts, create the Charge documents
				for (Expense itexpense:invoices){// iterate for each Expense object from Concur Invoice
					expense= itexpense;
					createDocs();// create the Charge documents for this Expense
				}
			}
			
			if (expenses.size() > 0) {// then there is at least one Expense object from Concur Expense
				// So, set the field contexts using the ones for Concur Expense
				for (FieldContext itcontext:expcontexts) {// iterate for each Expense, FieldContext object for this CompanyID
					switch (itcontext.getContext()){// switch on the PivotPrime field context
			    	case "Account":// then there is a field context for Account
					    accountcontext = context;// convert the Field Context document into a FieldContext object
					    accountfield = accountcontext.getFieldID();// set the accountfield to the Field ID for the specified custom field in FieldContext object
					    break;
					case "Activity":// then there is a field context for Activity
					    activitycontext = context;// convert the Field Context document into a FieldContext object
					    activityfield = activitycontext.getFieldID();// set the activityfield to the Field ID for the specified custom field in FieldContext object
					    break;
					case "Phase":// then there is a field context for Phase
					    phasecontext = context;// convert the Field Context document into a FieldContext object
					    phasefield = phasecontext.getFieldID();// set the phasefield to the Field ID for the specified custom field in FieldContext object
					    break;
					case "Task":// then there is a field context for Task
					    taskcontext = context;// convert the Field Context document into a FieldContext object
					    taskfield = taskcontext.getFieldID();// set the taskfield to the Field ID for the specified custom field in FieldContext object}// if loop
					    break;
					case "IsBillable":// then there is a field context for IsBillable
					    billablecontext = context;// convert the Field Context document into a FieldContext object
					    billablefield = billablecontext.getFieldID();// set the taskfield to the Field ID for the specified custom field in FieldContext object}// if loop
					    break;
					case "VAT":// then there is a Field Context for Domestic VAT
						if (context.getFormType().equals("Domestic")){
							vatdomestic = context;// convert the Field Context document into a FieldContext object
						}// if (context.getFormType().equals("Domestic"))
						break;
					}// switch (context.getContext()) block
				}// for (FieldContext itcontext:expcontexts)
				
				// Now, using these field contexts, create the Charge documents
				for (Expense itexpense:expenses){// iterate for each Expense object from Concur Expense
					expense=itexpense;
					createDocs();// create the Charge documents for this Expense
				}
			}// if (expenses.size() > 0)
	} // if (Docs.size() >0)

}
private static void createDocs () throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
	/* This method creates and stores the AccountActivityCharge documents for the current expense: the global field, expense.
	 * It uses the global fields for the various field contexts
	 */
	ArrayList<Itemization> items;// ArrayList to hold the Itemizations for the current expense
	ArrayList<Allocation> allocations;// ArrayList of Allocation objects

	
	Method method = null;// Method object used to determine the Concur custom field for a specified field context
	Object returnvalue = null;// Object to hold the Concur custom field for a specified field context

	
	formtype = accountcontext.getFormType();// determine the form type, Allocations or ExpenseEntry, to use to find field contexts
	items = expense.getItems();// get the Itemizations for this expense
	for (Itemization itm:items){// iterate for each Itemization
		item = itm;
		if (formtype.equals("Allocations")){// then use the Allocation form to create Charge documents
			allocations = item.getAllocations();// get the Allocations for this Itemization
			
			if (allocations == null){// then there are no allocations for this item
				/* So, create a "default allocation" using the itemization.
				 * A "default allocations" is one where 100% of the itemization is allocated to this one allocation.
				 * The class ItemtoAllocation converts the current itemization, the global field: item, into an allocation.
				 */
				allocation = ItemtoAllocation.createAllocation(item);// create an 
				allocations = new ArrayList<Allocation>();
				allocations.add(allocation);
			}
			for (Allocation itallocation:allocations){// iterate for each Allocation for the current Itemization
				allocation = itallocation;
				method = allocation.getClass().getMethod("get" + accountfield);// construct the method to get the custom field for the Account
				returnvalue = method.invoke(allocation);// returnvalue is the value for the Account field
				if (returnvalue != null) {// then there is an Account field
				    accountcustom = (CustomField)returnvalue;// so, convert returnvalue into a CustomField object for the Account field
				    if (accountcustom.getCode() != null){// then there is a value for the AccountID, which means this could be a billable Allocation
				    	//So, continue to determine whether the Allocation is billable
				    	if (billablefield != null){// then there is an Is Billable field context
				    		//So, determine the Is Billable value
				    		method = allocation.getClass().getMethod("get" + billablefield);// construct the method to get the custom field for Is Billable
							returnvalue = method.invoke(allocation);// returnvalue is the value for the IsBillable field
							billablecustom = (CustomField)returnvalue;// so, convert returnvalue into a CustomField object for the IsBillable field
							if (billablecustom!= null){// then there is a value for the Is Billable, Custom Field
								//So, determine whether IsBillable is true
								// TODO: we need to add more contexts for is billable = true.  Presently, we have only three ways to indicate is billable.
								if ((billablecustom.getValue().equals("Yes")) || (billablecustom.getValue().equals("Y"))  || (billablecustom.getValue().equals("Billable"))) {// then this is a billable Allocation
									billable = true;
								} else
									billable = false;
								}// if ((billablecustom.getValue().equals("Yes")) 
				    	} else {// then there isn't an Is Billable field context
				    		//So, this is a billable expense because there is an Account value, and the Is Billable field context isn't used
				    		billable = true;
				    		
				    	}// if (billablefield != null)	
				    		
				    }//if (accountcustom.getCode() != null)
				    if (billable){// then this Allocation is billable
				    	//So, create a Charge document for this Allocation
				    	charge = new AccountActivityCharge();// initiate an AccountActivityCharge object for this Allocation
					    charge.setAccountID(accountcustom.getCode());
					    charge.setAccountName(accountcustom.getValue());
					    
					    if (activityfield !=null) {// then there is an Activity field context
							method = allocation.getClass().getMethod("get" + activityfield);// construct the method to get the custom field for the Activity
							returnvalue = method.invoke(allocation);
							activitycustom = (CustomField)returnvalue;
							if (activitycustom != null){
							    charge.setActivityID(activitycustom.getCode());
							    charge.setActivityName(activitycustom.getValue());									
							}// if (activitycustom != null)

						}//if (activityfield !=null)
						
						if (phasefield !=null) {// then there is an Phase field context
							method = allocation.getClass().getMethod("get" + phasefield);// construct the method to get the custom field for the Phase
							returnvalue = method.invoke(allocation);
							phasecustom = (CustomField)returnvalue;
							if (phasecustom != null) {
							    charge.setPhaseID(phasecustom.getCode());
							    charge.setPhaseName(phasecustom.getValue());
							}// if (phasecustom != null) 

						}// if (phasefield !=null)
						if (taskfield !=null) {// then there is an Task field context
							method = allocation.getClass().getMethod("get" + taskfield);// construct the method to get the custom field for the Task
							returnvalue = method.invoke(allocation);
							taskcustom = (CustomField)returnvalue;
							if (taskcustom!= null){
							    charge.setTaskID(taskcustom.getCode());
							    charge.setTaskName(taskcustom.getValue());
							}// if (taskcustom!= null)
						}//if (taskfield !=null)
						
						status = addChargeDoc();// add this Charge document to the ArrayList of Charge documents

				    }// if (billable)
				}// if Account (returnvalue != null)
					
			}// for (Allocation itallocation:allocations)
				    	
		} else {// use the ExpenseEntry form to create Charge documents
			method = item.getClass().getMethod("get" + accountfield);// construct the method to get the custom field for the Account
			returnvalue = method.invoke(item);
			if (returnvalue != null) {// then these is a value for the Account field
				accountcustom = (CustomField)returnvalue;// get the CustomField object for the Account field
			    
				if (accountcustom.getCode() != null){// then there is a value for the Account field, which means this could be a billable Allocation
			    	//So, continue to determine whether the Allocation is billable
			    	if (billablefield != null){// then there is an Is Billable field context
			    		//So, determine the Is Billable value
			    		method = item.getClass().getMethod("get" + billablefield);// construct the method to get the custom field for Is Billable
						returnvalue = method.invoke(item);
						billablecustom = (CustomField)returnvalue;// get the CustomField for Is Billable
						if (billablecustom!= null){// then there is a value for the Is Billable, Custom Field
							//So, determine whether IsBillable is true
							if ((billablecustom.getValue().equals("Yes")) || (billablecustom.getValue().equals("Y"))  || (billablecustom.getValue().equals("Billable"))) {// then this is a billable Allocation
								billable = true;
							} else
								billable = false;
							}// if ((billablecustom.getValue().equals("Yes")) 
			    	} else {// then there isn't an Is Billable field context
			    		//So, this is a billable expense because there is an Account value, and the Is Billable field context isn't used
			    		billable = true;
			    		
			    	}// if (billablefield != null)
			    	if (billable){// then this Itemization is billable
				    	//So, create a Charge document for this Itemization
					    charge = new AccountActivityCharge();// initiate an AccountActivityCharge object
					    charge.setAccountID(accountcustom.getCode());
					    charge.setAccountName(accountcustom.getValue());
					    
					    if (activityfield !=null) {// then there is an Activity field context
							method = item.getClass().getMethod("get" + activityfield);// construct the method to get the custom field for the Activity
							returnvalue = method.invoke(item);
							activitycustom = (CustomField)returnvalue;
							if (activitycustom != null){
							    charge.setActivityID(activitycustom.getCode());
							    charge.setActivityName(activitycustom.getValue());									
							}// if (activitycustom != null)
	
						}// if (activityfield !=null)
						
						if (phasefield !=null) {// then there is an Phase field context
							method = item.getClass().getMethod("get" + phasefield);// construct the method to get the custom field for the Phase
							returnvalue = method.invoke(item);
							phasecustom = (CustomField)returnvalue;
							if (phasecustom != null) {
							    charge.setPhaseID(phasecustom.getCode());
							    charge.setPhaseName(phasecustom.getValue());
							}// if (phasecustom != null)
	
						}// if (phasefield !=null) 
						if (taskfield !=null) {// then there is an Task field context
							method = item.getClass().getMethod("get" + taskfield);// construct the method to get the custom field for the Task
							returnvalue = method.invoke(item);
							taskcustom = (CustomField)returnvalue;
							if (taskcustom!= null){
							    charge.setTaskID(taskcustom.getCode());
							    charge.setTaskName(taskcustom.getValue());
							}// if (taskcustom!= null)
						}// if (taskfield !=null)
						

						status = addChargeDoc();// add this Charge document to the ArrayList of Charge documents
						if (status.equals("failed")){// then break the loop processing Charge documents for this expense
							break;
						}
			    	}//if (billable)
				}// if (accountcustom.getCode() != null)
			}// if (returnvalue != null)
		}// if (formtype.equals("Allocations")
	}// for (Itemization itm:items)
	if (status.equals("failed")){// then roll back any charges for this Expense
		rollbackCharges();
	} else {// then it processed the Charge documents for this expense
		// so, set its PrimeStatus to Processed
		myMongoExpenseFunctions.updateDocByField(expCollection, "ID", expense.getID(), "PrimeStatus", "Processed");// update the PrimeStatus field to Processed
	}    

}
private static String addChargeDoc (){
	// this method completes the Charge object, converts it into a Charge document, and adds it to the MongoDB
	
	double amount;
	double percent = 0;
	double tax;// placeholder for calculating the tax amount
	double reclaim;// placeholder for calculating the reclaim amount
	double taxposted;// placeholder for calculating the tax posted amount
	double reclaimposted;// placeholder for calculating the reclaim posted amount
	ArrayList<VATData> vatdetails;// ArrayList of VATData object
	
	
	charge.setID(GUID.getGUID(4));
    charge.setReportID(expense.getReportID());
    charge.setEntryID(expense.getEntry_ID());
    charge.setEmployeeDisplayName(expense.getEmployeeDisplayName());
    charge.setExpenseID(expense.getID());
    charge.setExpenseTypeName(expense.getExpenseTypeName());
    charge.setExpenseDate(expense.getTransactionDate());
    charge.setExpenseAmount(expense.getOriginalAmount());
    charge.setOriginalCurrency(expense.getOriginalCurrency());
    charge.setExpenseBilledAmount(expense.getPostedAmount());// for now, assume Billing Currency is same as Posting Currency; thus BilledAmount = PostedAmount
    charge.setBillingCurrency(expense.getPostedCurrency());// assume Billing Currency is Posting Currency;
    charge.setPostedCurrency(expense.getPostedCurrency());// 
    charge.setItemizationID(item.getID());
    charge.setItemTypeName(item.getExpenseTypeName());
    charge.setItemDate(item.getDate());
    charge.setItemAmount(item.getTransactionAmount());
    charge.setItemPostedAmount(item.getPostedAmount());
   
    // calculate amount
    if (formtype.equals("Allocations")){// then use the Allocation to calculate the Charge Amount
    	if (allocation != null){
       	 percent = (Double.parseDouble(allocation.getPercentage()))/100;// convert Percentage (as text) for this Allocation into a double
       } else {
       	percent =1.0;
       }
    	amount = (percent) * (item.getPostedAmount());// assign the Charge Amount to the Allocation Amount, which is the Item Posted Amount times the Allocation percentage
    } else {// then use the Itemization to calculate the Charge Amount
    	amount = item.getPostedAmount();// assign the Charge Amount to the Item Posted Amount
    }
    
    
    if (vatdomestic != null){// then this Company ID uses VAT
    	// initialize the tax amounts
    	 tax = 0;
		 reclaim = 0;
		 taxposted = 0;
		 reclaimposted = 0;
		 
    	if (expense.getTaxNexus() != null) {// then there is a Tax Nexus for this Expense
    		if (expense.getTaxNexus().equals("Domestic") && (vatdomestic.getFieldID().equals("All") || (vatdomestic.getFieldID().equals("Reclaim")))) {// then reduce the Charge Amount by the Tax Amount
					vatdetails = allocation.getVatdetails();// get the VAT Details for this Allocation
					if (vatdetails != null){// then there is at least one VAT detail
						for (VATData vatdetail:vatdetails){// iterate for each VAT detail
				    		tax = tax + vatdetail.getTaxTransactionAmount();// add the Tax amount for this detail
				    		reclaim = reclaim + vatdetail.getTaxReclaimTransactionAmount();// add the Tax Reclaim amount for this detail
				    		taxposted = taxposted + vatdetail.getTaxPostedAmount();// add the Tax posted amount for this detail
				    		reclaimposted = reclaimposted + vatdetail.getTaxReclaimPostedAmount();// add the Tax Reclaim posted amount for this detail
				    	}// k loop
						// set the tax amounts for this Charge
						charge.setTaxAmount(tax);
						charge.setReclaimAmount(reclaim);
						charge.setTaxPostedAmount(taxposted);
						charge.setReclaimPostedAmount(reclaimposted);
						
						// calculate the Charge amount for this Charge
						if ((vatdomestic.getFieldID().equals("All"))) {// then reduce the Charge amount by the tax amount, which is ALL the taxes paid, regardless of whether they can be reclaimed.
							amount = amount - taxposted;// reduce the Charge amount by the Tax Amount, in posting currency
						} else {
							amount = amount - reclaimposted;// reduce the Charge amount by the Tax Reclaim Amount, in posted currency
						}
						
					}// if (vatdetails != null)
			}// (expense.getNexus().equals("Domestic") && vatdomestic.getFieldID().equals("Yes"))	   
    	}// if (expense.getNexus() != null)  
    }// if (vatdomestic != null)
    
    if (amount != 0){// then there is either a debit or credit amount to be billed
    	// so, create and store a Charge document
    	
    	if (Math.abs(amount) <= Math.abs(charge.getItemPostedAmount())){// then there is a valid Billed Amount
    		charge.setChargeAmount(amount);// set the Charge Amount
        	Date paiddate = expense.getPaidDate();// set the paid date to the expense's paid date
    	    charge.setPaidDate(paiddate);
    	    myDoc = charge.getDocument();
    	    status = myMongoExpenseFunctions.addDoc(chgCollection, myDoc);// add this Charge document to the MongoDB
        } else {// then the Billed Amount is greater than the Converted Amount ("ItemPostedAmount")
        	// so, trap this error
        	status = "failed";
        	System.out.println("Billed Amount exceeds the Converted Amount");
        }
    }
    	
    return status;
}
	
	private static void rollbackCharges()  {
		// Performs a rollback 
		// remove from the MongoDB any documents stored while processing this Payment Request
		myMongoExpenseFunctions.deleteDoc(chgCollection, "ExpenseID", expense.getID());// delete Charge documents for this Expense   
	}
}
