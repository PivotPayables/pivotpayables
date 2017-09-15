package com.pivotpayables.test;

import static java.lang.System.out;

import java.util.ArrayList;

import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.pivotpayables.expensesimulator.MongoDBFunctions;
import com.pivotpayables.nexus.APTransaction;


public class TestAPTransaction {

	public static void main(String[] args) {
		String host= "localhost";//the MongoDB server host
		int port = 27017;//the MongoDB server port

		
		// Connect to  the Expense_Data database, and then get the Expenses collection
		MongoDBFunctions myMongoExpenseFunctions = new MongoDBFunctions (host, port, "Expense_Data");// create a MongoDB Client for the specified host and port, and get the Expense_Data database
		DBCollection trnCollection = myMongoExpenseFunctions.setCollection("Transactions");// get the Transactions collection
		
	    ArrayList<DBObject> Docs = new ArrayList<DBObject>();//an ArrayList of Transaction document elements
	    Docs = myMongoExpenseFunctions.getDocs(trnCollection);//get all the documents from the Transactions collection
	    int count = 0;
	    APTransaction aptransaction = new APTransaction();

	    
	    	for (DBObject doc:Docs) {// iterate for each Transaction
	    		count++;
	    		out.println(" " + (count));
	    		aptransaction = aptransaction.doctoTransaction(doc);// convert this document into an Transaction  object
	    		out.println("-------------------------------------------------");
	    		out.println();

	    		payee.display();
	    	}// i loop
	  

	    out.println("Transaction Count: " + count);

	}

	}

}
