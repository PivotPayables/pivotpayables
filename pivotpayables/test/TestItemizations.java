package com.pivotpayables.test;
/**
 * @author John Toman
 * 2-8-2015
 * This program tests the Itemization class and the MongoDBFunctions class ensuring they can create and store Itemization object into, and then read from a MongoDB 
 * items collection.
 * 
 * Can be used in a couple ways  By setting the variable createItemization to true, it will create and store a test Itemization, and the read it from the MongoDB.  This way is useful
 * when testing changes to the Itemization class, or when there are not Itemizations documents in the collection.
 * 
 * By setting this variable to false, it won't create a test Itemization.  It relies upon on other programs to have created Itemization documents.
 *
 */
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCollection;
import com.pivotpayables.concurplatform.CustomField;
import com.pivotpayables.concurplatform.Itemization;
import com.pivotpayables.expensesimulator.GUID;
import com.pivotpayables.expensesimulator.MongoDBFunctions;

import static java.lang.System.out;
public class TestItemizations {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		
		// connect to the MongoDB
		String host= "localhost";//the MongoDB server host
		int port = 27017;//the MongoDB server port
		MongoDBFunctions myMongoFunctions = new MongoDBFunctions (host, port, "Expense_Data", "Itemizations");// create a MongoDB Client to the Expense_Data database and Itemizations collection
		DBCollection myCollection = myMongoFunctions.getCollection();// get the Itemizations collection
		CustomField custom = null;
		
		Boolean createItemization = false;// Whether the test should first create and store an Itemization object. 
		
		if (createItemization) {// then create and store a test, item object in the MongoDB
			Calendar myCalendar = Calendar.getInstance();//a Calendar object 
			Date date = myCalendar.getTime();
			String id = GUID.getGUID(4);// create the GUID for the item object
			Itemization item = new Itemization("TEST", "Personal Mileage", date, 12.34, 12.34, true, 12.34, id);

			BasicDBObject myDoc = item.getDocument();//convert the Itemization object into a item document
			myMongoFunctions.addDoc(myCollection, myDoc);// store the Itemization document in the Itemizations collection
		}
		
	    ArrayList<DBObject> Docs = new ArrayList<DBObject>();//an ArrayList of Itemization document elements
	    Docs = myMongoFunctions.getDocs(myCollection);//get all the documents from the Itemizations collection
	    int count = Docs.size(); // the number of Itemization documents in the ArrayList
	    DBObject doc = null;//placeholder for a Itemization document



		Itemization myItem;
	    if (count > 0) {// then there is an Itemization
	    	for (int i=0; i<count; i++) {// iterate for each Itemization
	    		doc = Docs.get(i);// get the Itemization document for this iteration
	    		myItem = myMongoFunctions.doctoItemization(doc);// convert this document into an Itemization  object
	    		myItem.display();
	    	}
		    out.println("Itemization Count: " + count);
	    }
	}

}
