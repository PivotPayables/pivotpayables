package com.pivotpayables.nexus;
/**
 * @author John Toman
 * 6/1/2017
 * 
 * This class creates canonical transactions for report payees with a Status of Not Processed.  
 * 
 * This class is intended to be called by a job that creates canonical transactions from report payees that have yet to be processed.
 * 
 * It leverages the metadata found in the Batch Definition that is associated to a Report Payee.  It also uses the Expense objects associated with
 * this Report Payee.
 * 
 * 
 * Its method, createTransactions does the following:
 * 
 * 
 *   
 * 
 *
 */

//import static java.lang.System.out;

import java.lang.reflect.InvocationTargetException;

import java.text.ParseException;

import java.util.ArrayList;
import java.util.HashMap;




import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.pivotpayables.concurplatform.Expense;
import com.pivotpayables.concurplatform.FindConcurCompany;
import com.pivotpayables.expensesimulator.MongoDBFunctions;
import com.pivotpayables.nexus.BatchDefinition;
import com.pivotpayables.nexus.ReportPayee;



//import static java.lang.System.out;


public class CreateCanonicalTransactions {
	
	static final String host = "localhost";
	static final int port = 27017;
	
	// Connect to the Company_Data database, and the get the necessary collections
	private static MongoDBFunctions myMongoCompanyFunctions = new MongoDBFunctions (host, port, "Company_Data");// create a MongoDB Client for the specified host and port, and get the Company_Data database 
	private static DBCollection batCollection = myMongoCompanyFunctions.setCollection("BatchDefinitions");// get the BatchDefintions collection
	
	
	// Connect to the Expense_Data database, and the get the necessary collections
	private static MongoDBFunctions myMongoExpenseFunctions = new MongoDBFunctions (host, port, "Expense_Data");// create a MongoDB Client
	private static DBCollection expCollection = myMongoExpenseFunctions.setCollection("Expenses");// get the Expenses collection 
	private static DBCollection payCollection = myMongoExpenseFunctions.setCollection("ReportPayees");// get the ReportPayees collection
	private static DBCollection trnCollection = myMongoExpenseFunctions.setCollection("Transactions");// get the Transactions collection
	
	
	private static Expense expense=null; // placeholder for an Expense object, global field shared by all methods
	private static BatchDefinition definition = new BatchDefinition();// placeholder for a BatchDefinition object
	
		
	public static String createTransactions(String key) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ParseException{
		BasicDBObject myDoc = null;// placeholder BasicDBObject
		String status = null;
		HashMap<String, String> criteria = new HashMap<String, String>();// initialize the criteria HashMap
		ArrayList<Expense> expenses = new ArrayList<Expense>();

		
		AccountingTransaction transaction = new AccountingTransaction();// placeholder an AccountingTransaction object
		
		ReportPayee payee = new ReportPayee();// placeholder for a ReportPayee object

		ArrayList<DBObject> payDocs;// an ArrayList with ReportPayee document elements
		ArrayList<DBObject> expDocs;// an ArrayList with Expense document elements
		DBObject mydoc=null;// placeholder DBObject
		
		// get the report payees not processed for this Company ID
		String companyID = FindConcurCompany.getCompanyID(key);// get the CompanyID for the company associated to the owner of the key
		criteria = new HashMap<String, String>();// initialize the criteria HashMap
		criteria.put("CompanyID", companyID);// add the criterion for this Company
		criteria.put("Status", "Not Processed");// add the criterion for NexusStatus of Not Processed
		payDocs =	myMongoCompanyFunctions.getDocsByCriteria(payCollection, criteria); // get the Batch Definition documents for the specified company
				
		if (payDocs.size() >0){// then there is at least one Report Payee not processed
			// So, process these report payees
			for (DBObject paydoc:payDocs){// iterate for each Report Payee  document
				payee = payee.doctoReportPayee(paydoc);// convert the Report Payee  document into a Report Payee  object

				// begin by getting the batch definition for this report payee
				mydoc = myMongoCompanyFunctions.getDoc(batCollection, payee.getBatchDefinitionID());// get the Batch Definition document for this Report Payee
				definition = definition.doctoBatchDefinition(mydoc);// convert the Batch Definition document into a Batch Definition object
				expenses = new ArrayList<Expense>();// initialize the ArrayList of Expense objects for this Report Payee
				expDocs = new ArrayList<DBObject>();// ArrayList of Expense documents for this Report Payee ID
				expDocs =	myMongoExpenseFunctions.getDocsByField(expCollection, "ReportPayeeID", payee.getID()); // get the Expense documents with a ReportPayeID for the report payee
				if (expDocs.size()>0){// then there is at least one Expense  to process
					for (DBObject expdoc:expDocs){// iterate for each Expense document
						expense = new Expense();
						expense = expense.doctoExpense(expdoc);// convert the Expense document into an Expense object
						expenses.add(expense);// add this Expense to the ArrayList of Expenses for this Report Payee
					}// 
				}// if (expDocs.size()>0)

				
				/*  For now, the code can create only one transaction per report payee, AND only the AP Transaction type
				 *  So, begin by creating an AP Transaction.
				 *  
				 *  Later, there will be code that can create multiple transactions per report payee as well as
				 *  can create more than the AP Transaction type.
				 */
				
				//TODO Write the code that based on the batch definition determines the number of transactions, and what transaction types			
				
					
				if (definition.getTransactionType().equals("CARD")){// then create a Credit Card Charge transaction
					//TODO write code to create a Credit Card Charge transaction
					transaction = CreateCreditCardChargeTransaction.createTransaction(expenses, definition);
					myDoc = transaction.getDocument();// the Document for this Card Transaction
					status = myMongoExpenseFunctions.addDoc(trnCollection, myDoc);// add the AP Transaction document to the MongoDB
					
				} else if (definition.getTransactionType().equals("BILL")){// then create an AP transaction
					transaction = CreateAPTransaction.createTransaction(expenses, definition);
					myDoc = transaction.getDocument();// the Document for this AP Transaction
					status = myMongoExpenseFunctions.addDoc(trnCollection, myDoc);// add the AP Transaction document to the MongoDB

				}// if (definition.getTransactionType().equals("BILL")
			}// for (ReportPayee itpayee:sortedpayees)

		}// if (payDocs.size() >0)
		return status;
	}

}
