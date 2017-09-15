/**
 * 
 */
package com.pivotpayables.nexus;

//import static java.lang.System.out;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;



import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.json.JSONException;

import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.pivotpayables.concurplatform.Expense;
import com.pivotpayables.expensesimulator.MongoDBFunctions;

/**
 * @author John
 * This class cleans the specified Expense object from PivotNexus data allowing this expense to be reprocessed by PivotNexus to create new report payees
 * and canocial transactions.

 * 
 * This class: 
 * - removes the ReportID and Canonical Transaction ID from the Expense
 * - sets NexusStatus to Not Processed.  
 * - deletes any report payees or canonical transactions associated to these expenses.
 */
public class ResetExpenseforNexus {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws JSONException 
	 * @throws ParseException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	public static void clean(String expenseID) throws JsonParseException, JsonMappingException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ParseException, JSONException, IOException {
		Expense expense = new Expense();// placeholder for an Expense object
	

		String host= "localhost";//the MongoDB server host
		int port = 27017;//the MongoDB server port

		
		// Connect to  the Expense_Data database, and then get the Expenses collection
		MongoDBFunctions myMongoExpenseFunctions = new MongoDBFunctions (host, port, "Expense_Data");// create a MongoDB Client for the specified host and port, and get the Expense_Data database
		DBCollection expCollection = myMongoExpenseFunctions.setCollection("Expenses");// get the Expenses collection
		DBCollection payCollection = myMongoExpenseFunctions.setCollection("ReportPayees");// get the ReportPayees collection
		DBCollection trnCollection = myMongoExpenseFunctions.setCollection("Transactions");// get the Transaction collection
		
	
		DBObject doc = myMongoExpenseFunctions.getDoc(expCollection, expenseID);// get the Expense document from the Expenses collection
		expense = expense.doctoExpense(doc);// convert the Expense document into an Expense object
		myMongoExpenseFunctions.deleteDoc(payCollection, "ID", expense.getReportPayeeID());// delete the Report Payee associated with this expense
		myMongoExpenseFunctions.deleteDoc(trnCollection, "ID", expense.getCanonicalTransactionID());// delete the Canonical Transaction associated with this expense
		myMongoExpenseFunctions.updateDocByField(expCollection, "ID", expense.getID(), "NexusStatus", "Not Processed");
		myMongoExpenseFunctions.updateDocByField(expCollection, "ID", expense.getID(), "ReportPayeeID", "");
		myMongoExpenseFunctions.updateDocByField(expCollection, "ID", expense.getID(), "CanonicalTransactionID", "");


	}

}
