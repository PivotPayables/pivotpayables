package com.pivotpayables.test;
import static java.lang.System.out;

import java.util.ArrayList;



import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.pivotpayables.expensesimulator.MongoDBFunctions;
import com.pivotpayables.nexus.ReportPayee;


public class TestReportPayees {

	public static void main(String[] args) {
		String host= "localhost";//the MongoDB server host
		int port = 27017;//the MongoDB server port

		
		// Connect to  the Expense_Data database, and then get the Expenses collection
		MongoDBFunctions myMongoExpenseFunctions = new MongoDBFunctions (host, port, "Expense_Data");// create a MongoDB Client for the specified host and port, and get the Expense_Data database
		DBCollection payCollection = myMongoExpenseFunctions.setCollection("ReportPayees");// get the ReportPayees collection
		
	    ArrayList<DBObject> Docs = new ArrayList<DBObject>();//an ArrayList of ReportPayee document elements
	    Docs = myMongoExpenseFunctions.getDocs(payCollection);//get all the documents from the ReportPayees collection
	    int count = 0;
	    ReportPayee payee = new ReportPayee();

	    
	    	for (DBObject doc:Docs) {// iterate for each ReportPayee
	    		count++;
	    		out.println(" " + (count));
	    		payee = payee.doctoReportPayee(doc);// convert this document into an ReportPayee  object
	    		out.println("-------------------------------------------------");
	    		out.println();

	    		payee.display();
	    	}// i loop
	  

	    out.println("ReportPayee Count: " + count);

	}

}
