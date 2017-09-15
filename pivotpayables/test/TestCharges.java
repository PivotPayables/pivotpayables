package com.pivotpayables.test;
/**
 * @author John Toman
 * 6-23-2015
 * 
 * This program tests the AccountActivityCharge class and the MongoDBFunctions class ensuring they can create and store an AccountActivityCharge object into, and then read from a MongoDB 
 * items collection.
 * 
 *
 */

import java.util.ArrayList;



import com.mongodb.DBCollection;
import com.mongodb.DBObject;

import com.pivotpayables.expensesimulator.MongoDBFunctions;
import com.pivotpayables.prime.AccountActivityCharge;

import static java.lang.System.out;

public class TestCharges {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		AccountActivityCharge charge = new AccountActivityCharge();// placeholder for an AccountActivityCharge object
		// connect to the MongoDB
		String host= "localhost";//the MongoDB server host
		int port = 27017;//the MongoDB server port

		// Connect to  the Expense_Data database, and then get the Expenses collection
		MongoDBFunctions myMongoExpenseFunctions = new MongoDBFunctions (host, port, "Expense_Data");// create a MongoDB Client for the specified host and port, and get the Expense_Data database
		DBCollection chargeCollection = myMongoExpenseFunctions.setCollection("AccountActivityCharges");// get the Itemizations collections

		
	    ArrayList<DBObject> Docs = new ArrayList<DBObject>();//an ArrayList of AccountActivityCharge document elements
	    Docs = myMongoExpenseFunctions.getDocs(chargeCollection);//get all the documents from the AccountActivityCharges collection
	    int count = 0; // the number of AccountActivityCharge documents in the ArrayList
	   
	    if (Docs.size() > 0) {// then there is an AccountActivityCharge
	    	for (DBObject doc:Docs) {// iterate for each AccountActivityCharge document
	    		count++;
	    		out.println("Charge " + count);
	    		charge = charge.doctoCharge(doc);// convert this document into an AccountActivityCharge  object
	    		out.println("-------------------------------------------------");
	    		out.println();
	    		
	    		if ((charge.getAccountID() == null)  || (charge.getActivityID() == null)) {
	    			out.println("ERROR: Account or Activity ID is null");
	    		}

	    		charge.display();
	    	}// for (DBObject doc:Docs)
	    }// if count > 0 block

	    out.println("AccountActivityCharge Count: " + count);

	}// main

}
