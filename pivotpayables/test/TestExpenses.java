package com.pivotpayables.test;
/**
 * 
 */

/**
 * @author John Toman
 * 3/22/2015
 * This program tests the class CreateExpenses to ensure it successfully creates Expense documents in
 * the MongoDB for the trips for a random employee selected from a random company.
 * 
 * It also tests the ExpenseSimulator program to ensure it successfully stored expenses in the MongoDB.
 * 
 */


import com.mongodb.DBObject;
import com.mongodb.DBCollection;
import com.pivotpayables.concurplatform.Expense;
import com.pivotpayables.expensesimulator.MongoDBFunctions;

import static java.lang.System.out;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;


public class TestExpenses {

	/**
	 * @param args
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 */
	public static void main(String[] args) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {


		Expense expense = new Expense();// placeholder for an Expense object
		ArrayList<DBObject> Docs = new ArrayList<DBObject>();// an ArrayList with MongoDB document elements
		String companyID = "LODL1343VLVT6109";//Apex"OCGT0572LAKE6722";//Hines//PEUC8501HBOD7997
		

		String host= "localhost";//the MongoDB server host
		int port = 27017;//the MongoDB server port

		
		// Connect to  the Expense_Data database, and then get the Expenses collection
		MongoDBFunctions myMongoExpenseFunctions = new MongoDBFunctions (host, port, "Expense_Data");// create a MongoDB Client for the specified host and port, and get the Expense_Data database
		DBCollection expCollection = myMongoExpenseFunctions.setCollection("Expenses");// get the Expenses collection
		
		int expensecount=0;
		Docs = new ArrayList<DBObject>();
		HashMap<String, String> criteria = new HashMap<String, String>();// initialize the criteria HashMap
		//criteria.put("CompanyID", companyID);// add the criterion for this Company
		//criteria.put("NexusStatus", "Not Processed");// add the criterion for PivotPrimeStatus of Not Processed
		//Docs = myMongoExpenseFunctions.getDocsByCriteria(expCollection, criteria);// find Expense documents for this Company that are Not Processed
		Docs = myMongoExpenseFunctions.getDocs(expCollection);// get the Expense documents from the Expenses collection
		System.out.println("Expense Documents: " + Docs.size());
		expensecount = 0;// the number of Expense documents in the ArrayList.

		for (DBObject doc:Docs) {//iterate for each expense
			expensecount++;
			out.println("EXPENSE NUMBER: " + expensecount);
			expense = expense.doctoExpense(doc);// convert the Expense document into an Expense object
			expense.display();
		}// for (DBObject doc:Docs)
		out.println("Total Expenses: " + expensecount);


	}//main method

}

