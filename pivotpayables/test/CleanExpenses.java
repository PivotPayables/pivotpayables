/**
 * 
 */
package com.pivotpayables.test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.ArrayList;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.json.JSONException;

import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.pivotpayables.concurplatform.Expense;
import com.pivotpayables.concurplatform.FindConcurCompany;
import com.pivotpayables.expensesimulator.MongoDBFunctions;
import com.pivotpayables.nexus.ResetExpenseforNexus;

/**
 * @author John
 * 
 * This class resets all expenses in the Expenses collection so that they can be processed by PivotNexus into canonical transactions.
 * 
 * It is helpful when troubleshooting PivotNexus code because it allows you to reuse expenses pulled from Concur.  It avoids needing
 * to repull expenses.
 *
 */
public class CleanExpenses {

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
	public static void main(String[] args) throws JsonParseException, JsonMappingException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ParseException, JSONException, IOException {
		Expense expense = new Expense();// placeholder for an Expense object
		
		String host= "localhost";//the MongoDB server host
		int port = 27017;//the MongoDB server port
		
		// Connect to  the Expense_Data database, and then get the Expenses collection
		MongoDBFunctions myMongoExpenseFunctions = new MongoDBFunctions (host, port, "Expense_Data");// create a MongoDB Client for the specified host and port, and get the Expense_Data database
		DBCollection expCollection = myMongoExpenseFunctions.setCollection("Expenses");// get the Expenses collection	
		String key = "";
		ArrayList<DBObject> Docs = new ArrayList<DBObject>();
		if (args.length > 0){// then get Expenses only for the specified Company ID
			key = args[0];
			String companyID = FindConcurCompany.getCompanyID(key);// get the CompanyID for the company associated to the owner of the key
			Docs = myMongoExpenseFunctions.getDocsByField(expCollection, "CompanyID", companyID);
		} else {// then get all Expenses from the Expenses collection
			Docs = myMongoExpenseFunctions.getDocs(expCollection);// get the Expense documents from the Expenses collection
		}
		
		for (DBObject doc:Docs){
			expense = expense.doctoExpense(doc);
			ResetExpenseforNexus.clean(expense.getID());
		}
		
	}

}
