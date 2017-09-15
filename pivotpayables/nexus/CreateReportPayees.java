package com.pivotpayables.nexus;
/**
 * @author John Toman
 * 6/1/2017
 * 
 * This class creates report payees for expenses where its NexusStatus is Not Processed.  Its method, processExpenses does the following:
 * 
 * 1. Determines the the Company associated to the provided OAuth Token, which acts as a unique identifier for a company.
 * 2. Collects from the MongoDB the batch definitions for this company.
 * 3. Searches for expenses where NexusStatus is "Not Processed".
 * 4. It iterates through each of these expenses.
 * 5. For each expense it searches the company's batch definitions to find one that matches this expense.  It uses the method, getDefinition to do this.
 * 6. If it finds a matching batch definition, it then determines whether there is an existing report payee for this expense's Report ID and this batch definition.
 * 7. If it does NOT find a matching report payee, it create ones and stores this in the MongoDB.
 * 8. It updates the expense's NexusStatus to Processed and sets the ReportPayeeID to the ID for the payee (either the existing one, or the new one just created).
 * 9. If it can't find a batch definition that matches the expense, it sets the expense's NexusStatus to "Missing Definition".  This indicates an error that
 * an administrator must correct by creating the necessary batch definition.
 * 
 * This class is intended to be part of a job that PivotNexus uses to create canonical transactions from expenses in the MongoDB.  Part of this process
 * is assigning a report payee to an expense so that when it is time to create canonical transactions, PivotNexus can quickly identify what
 * expenses are associated to a report payee (which is a payee on an expense report or payment request).  See the class, CreateCanonicalTransactions for details
 * about how it processes report payees to create canonical transactions. 
 *
 */

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.pivotpayables.concurplatform.CustomField;
import com.pivotpayables.concurplatform.Expense;
import com.pivotpayables.concurplatform.FindConcurCompany;
import com.pivotpayables.expensesimulator.GUID;
import com.pivotpayables.expensesimulator.MongoDBFunctions;

public class CreateReportPayees {
	// MongoDB Host and Port
	static final String host = "localhost";
	static final int port = 27017;
	
	// Connect to the Company_Data database, and then get the collections for Companies and Employees
	private static MongoDBFunctions myMongoCompanyFunctions = new MongoDBFunctions (host, port, "Company_Data");// create a MongoDB Client for the specified host and port, and get the Company_Data database 
	private static DBCollection batCollection = myMongoCompanyFunctions.setCollection("BatchDefinitions");// get the BatchDefintions collection
	
	// Connect to  the Expense_Data database, and then get the Expenses collection
	private static MongoDBFunctions myMongoExpenseFunctions = new MongoDBFunctions (host, port, "Expense_Data");// create a MongoDB Client for the specified host and port, and get the Expense_Data database
	private static DBCollection expCollection = myMongoExpenseFunctions.setCollection("Expenses");// get the Expense collection for the specified host and port, and get the Expense_Data databaseprivate static DBCollection chgCollection = myMongoExpenseFunctions.setCollection("AccountActivityCharges");// get the Expense collection for the specified host and port, and get the Expense_Data database
	private static DBCollection payCollection = myMongoExpenseFunctions.setCollection("ReportPayees");// get the ReportPayees collection for the specified host and port, and get the Expense_Data database
	
	public static void processExpenses(String key) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ParseException{
		BasicDBObject myDoc = null;// placeholder BasicDBObject
		ArrayList<DBObject> Docs;// an ArrayList with MongoDB document elements

		
		Expense expense = new Expense();
		ArrayList<Expense> expenses = new ArrayList<Expense>();
		ReportPayee payee = new ReportPayee();// placeholder for a ReportPayee object
		ArrayList<BatchDefinition> definitions = new ArrayList<BatchDefinition>();// ArrayList of batch definitions for this Company ID
		HashMap<String, String> criteria = new HashMap<String, String>();// initialize the criteria HashMap
		BatchDefinition definition= new BatchDefinition();// placeholder for a BatchDefinition object
		String status = "failed";// initialize status
		
		// get the Batch Definitions for this Company ID
		String companyID = FindConcurCompany.getCompanyID(key);// get the CompanyID for the company associated to the owner of the key
		Docs =	myMongoCompanyFunctions.getDocsByField(batCollection, "CompanyID", companyID); // get the Batch Definition documents for the specified company
		
		if (Docs.size() >0){// then there are Batch Definitions for this Company, which means they use PivotNexus
			// So, create an ArrayList of Batch Definitions for this company
			for (DBObject doc:Docs){// iterate for each Batch Definition document
				definition= new BatchDefinition();
				definition = definition.doctoBatchDefinition(doc);// convert the Batch Definition document into a Batch Definition object
				definitions.add(definition);// add this Batch Definition to the ArrayList of Batch Definition objects for this company
			}// (DBObject doc:Docs) loop

			// find Expense documents for this Company that haven't been processed by PivotNexus
			Docs = new ArrayList<DBObject>();
			criteria = new HashMap<String, String>();// initialize the criteria HashMap
			criteria.put("CompanyID", companyID);// add the criterion for this Company
			criteria.put("NexusStatus", "Not Processed");// add the criterion for NexusStatus of Not Processed
			Docs = myMongoExpenseFunctions.getDocsByCriteria(expCollection, criteria);// find Expense documents for this Company that are Not Processed
			for (DBObject doc:Docs){// iterate for each Expense document
				expense= expense.doctoExpense(doc);
				expenses.add(expense);// add this Expense to the ArrayList of Expense objects for this company
			}// (DBObject doc:Docs) loop
			
			for (Expense itexpense:expenses){// iterate for each Expense object
				payee = new ReportPayee();// initiate a ReportPayee object.
				// begin by determining whether there is a batch definition for this expense
				definition = getDefinition(definitions, itexpense);// determine whether there is a batch definition that matches this expense
				if (definition != null){// then there is a batch definition that matches this expense
					// determine whether there is already in the MongoDB a Report Payee for the combination of this expense's Report ID and the Batch Definition ID
					Docs = new ArrayList<DBObject>();// initialize the ArrayList of Report Payee documents
					criteria = new HashMap<String, String>();// initialize the criteria HashMap
					criteria.put("ReportID", itexpense.getReportID());// add the criterion for the expense's Report ID
					criteria.put("BatchDefinitionID", definition.getID());// add the criterion for the Batch Definition ID
					Docs = myMongoExpenseFunctions.getDocsByCriteria(payCollection, criteria);// find Report Payee documents with this Report ID and Batch Definition ID
					
					if (Docs.size() == 0){// then there isn't a Report Payee with this Report ID and Batch Definition ID
						//so, add this Report Payee to the Mongo DB
						payee.setID(GUID.getGUID(4));// assign a unique identifier
						payee.setCompanyID(companyID);// assign the CompanyID
						payee.setReportID(itexpense.getReportID());// assign the "ReportID" for this expense
						payee.setPaymentTypeCode(itexpense.getPaymentTypeCode());
						payee.setPaymentTypeName(itexpense.getPaymentTypeName());
						payee.setCurrency(itexpense.getPostedCurrency());// set the payee currency to the Posted Currency
						payee.setBatchDefinitionID(definition.getID());// Assign the ID of the batch definition
						payee.setPaymentMethod(definition.getPaymentMethod());// assign the Payment Method
						payee.setStatus("Not Processed");
						myDoc = payee.getDocument();// the Document for this Report Payee
								
						status = myMongoExpenseFunctions.addDoc(payCollection, myDoc);// add the Report Payee document to the MongoDB
   
					} else {// then there is a Report Payee for this Report ID and Batch Definition ID
						
						//so, use this Report Payee; don't create a new Report Payee
						DBObject doc = Docs.get(0);// get the Report Payee document that matches this Report ID and Batch Definition ID
						payee = payee.doctoReportPayee(doc);// convert the Report Payee document into a Report Payee object
						status = "success";
					} 
					if (status.equals("success")){// then process the Report Payee document for this expense
						// so, set its NexusStatus to Processed
						status = myMongoExpenseFunctions.updateDocByField(expCollection, "ID", itexpense.getID(), "NexusStatus", "Processed");// update the NexusStatus field to Processed
						status = myMongoExpenseFunctions.updateDocByField(expCollection, "ID", itexpense.getID(), "ReportPayeeID", payee.getID());// update the ReportPayeeID field to this Report Payee ID
						
					} 
				} else {// then there isn't a batch definition that matches this expense
					// so, set the NexusStatus to Missing Definition
					status = myMongoExpenseFunctions.updateDocByField(expCollection, "ID", expense.getID(), "NexusStatus", "Missing Definition");// update the NexusStatus field to Missing Definition
				}// if (definition != null)	
				
			}// for (Expense itexpense:expenses)
		}// if (Docs.size() >0)	
	}
	private static BatchDefinition getDefinition (ArrayList<BatchDefinition> definitions, Expense expense) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException { 
	/* 
	 * This method determines whether there is an existing Batch Definition that matches the specified Expense.
	 * If so, it returns this batch definition.  If not, it returns null, indicating there is no matching batch definition.
	 * 
	 */
	
	CustomField custom=null;// placeholder for the CustomField object
	Method method = null;// Method object used to determine the Concur custom field for a specified field context
	Object returnvalue = null;// Object to hold the Concur custom field for a specified field context
	String field=null;
	String group=null;
	String expensegroup=null;
	Iterator<Entry<String, String>> it = null;
	
	for (BatchDefinition itdefinition:definitions){// iterate through each Batch Definition for this Company ID
		//to find the definition that applies to this report payee
		if ( (itdefinition.getPaymentTypeCode().equals(expense.getPaymentTypeCode())) ) {// && (itdefinition.getCurrency().equals(expense.getPostedCurrency())) && (itdefinition.getCountry().equals(expense.getEmployeeCountry()))
			// then this definition may match this expense
			// So, determine if it's group matches
			if (itdefinition.getGroup() != null){// then the definition has a Group assigned; it's not Global
				// So, determine whether the Group field is a Custom Field or a Text Field
				it = itdefinition.getGroup().entrySet().iterator();// create an Iterator to iterate the entries in the Map for Group
				while (it.hasNext()) {// iterate for each entry in the Map, Group
			        @SuppressWarnings("rawtypes")
					Map.Entry pair = (Map.Entry)it.next();// get the Map entry (key, value) for this iteration
			        field = (String) pair.getKey();// get the key for the Map entry, which is what field to find the group value
			        group =  (String) pair.getValue();// get the value for the Map entry, which is the group value
			        method = expense.getClass().getMethod("get" + field);// construct the method to get the Expense, field for the Group
					returnvalue = method.invoke(expense);// returnvalue is the name of the Expense field that holds the Group value
					custom = (CustomField)returnvalue;// create a CustomField object for this Expense field
					if (custom != null){
					    expensegroup = custom.getValue();// assign the value for the Group for the Report Payee associated with this Expense								
					}// if (custom != null)
			        it.remove(); // avoids a ConcurrentModificationException, remove the entry from the iteration
			    }
				
				if (group.equals(expensegroup)){// then the batch definition's group matches this expense's expense group
					// So, return this definition

					return itdefinition;
				}//  if (group.equals(expensegroup))
				
			} else {// then this then batch definition's group is Global, which means this batch definition matches the report payee 
				// So, return this definition
				return itdefinition;
				
			}// if (itdefinition.getGroup()
			
		}// if ( (definition.getPaymentTypeCode().equals(payee.getPaymentTypeCode())) && (definition.getCurrency().equals(payee.getCurrency()))  && (definition.getCountry().equals(expense.getEmployeeCountry()))  && (definition.getPaymentMethod().equals(expense.getPayeePaymentMethod())) )
	}// for (BatchDefinition itdefinition:definitions)
	// no batch definition matches the Report Payee, so return a null batch definition indicating there is no match
	return null;// so, return null to indicate no match
	}
}
