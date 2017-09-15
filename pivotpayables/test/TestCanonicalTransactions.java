package com.pivotpayables.test;

import static java.lang.System.out;

import java.util.ArrayList;

import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.pivotpayables.expensesimulator.MongoDBFunctions;
import com.pivotpayables.nexus.AccountingTransaction;



public class TestCanonicalTransactions {
	public static void main(String[] args) {
		String host= "localhost";//the MongoDB server host
		int port = 27017;//the MongoDB server port

		
		// Connect to  the Expense_Data database, and then get the Expenses collection
		MongoDBFunctions myMongoExpenseFunctions = new MongoDBFunctions (host, port, "Expense_Data");// create a MongoDB Client for the specified host and port, and get the Expense_Data database
		DBCollection trnCollection = myMongoExpenseFunctions.setCollection("Transactions");// get the Transaction collection
	    
		ArrayList<DBObject> Docs = new ArrayList<DBObject>();//an ArrayList of AP Transaction document elements
	    Docs = myMongoExpenseFunctions.getDocs(trnCollection);//get all the documents from the Transactions collection
	    int count = 0;

	    AccountingTransaction transaction = new AccountingTransaction();// placeholder for an AP Transaction document

	    
	    	for (DBObject doc:Docs) {// iterate for each ReportPayee
	    		count++;
	    		out.println(" " + (count));
	    		//out.println(doc);
	    		transaction = transaction.doctoAccountingTransaction(doc);// convert document into an AP transaction
	    		//transaction.display();
	    		out.println("-------------------------------------------------");
	    		out.println();
	    		transaction.display();
	    	}// i loop
	  

	    out.println("Transction Count: " + count);

	}

}
