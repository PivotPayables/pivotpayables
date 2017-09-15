package com.pivotpayables.test;
/**
 * @author John Toman
 * 2-5-2015
 * This program tests the program CreateAccountActivities program to ensure it successfully created the account activity documents in
 * the MongoDB.
 * 
 * Run this program after running the CreateAccountActivities program.
 *
 */
import java.util.ArrayList;

import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.pivotpayables.expensesimulator.MongoDBFunctions;
import com.pivotpayables.prime.AccountActivity;

import static java.lang.System.out;

public class TestAccountActivities {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		AccountActivity activity= null;//placeholder for an AccountActivity object


		
		MongoDBFunctions myMongoFunctions = new MongoDBFunctions ("localhost", 27017, "Company_Data", "AccountActivities");//create a MongoDB Client for the AccountActivities collection
		DBCollection myCollection = myMongoFunctions.getCollection();//get the AccountActivities collection
	   
	    ArrayList<DBObject> Docs = new ArrayList<DBObject>();//an ArrayList of AccountActivity document elements
	    Docs = myMongoFunctions.getDocs(myCollection);//get all the documents from the AccountActivities collection
	    int activitycount = Docs.size();//the number of elements in the ArrayList of AccountActivity documents
	    for (int index =0; index<activitycount; index++) {//iterate for each document in the ArrayList
			activity = myMongoFunctions.doctoAccountActivity(Docs.get(index));//get the AccountActivity object for this iteration
			out.println("Account Activity Number: " + (index+1));
			out.println("---------------------------------------");
			activity.display();//display it
			out.println();
	    }//i block
	 	
	}//main method

}
