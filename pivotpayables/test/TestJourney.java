package com.pivotpayables.test;
/**
 * @author John Toman
 * 2-7-2015
 * This program tests the Journey class and the MongoDBFunctions class can create and store Journey object into, and then read from the MongoDB 
 * Journeys collection.
 *
 */

import java.util.ArrayList;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCollection;
import com.pivotpayables.concurplatform.Journey;
import com.pivotpayables.expensesimulator.GUID;
import com.pivotpayables.expensesimulator.MongoDBFunctions;

import static java.lang.System.out;


public class TestJourney {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Journey journey = new Journey();
		
		// connect to the MongoDB
		String host= "localhost";//the MongoDB server host
		int port = 27017;//the MongoDB server port
		MongoDBFunctions myMongoFunctions = new MongoDBFunctions (host, port, "Expense_Data", "Journeys");// create a MongoDB Client to the Expense_Data database and Journeys collection
		DBCollection myCollection = myMongoFunctions.getCollection();// get the Journeys collection
		
		Boolean createJourney = false;// Whether the test should first create and store a Journey object. 
		
		if (createJourney) {// then create and store a test, Journey object in the MongoDB
			journey.setID(GUID.getGUID(4));// create the GUID for the Journey object
			journey.setBusinessDistance(10);
			journey.setUnitOfMeasure("M");//miles is unit of measure
			journey.setStartLocation("Home");
			journey.setEndLocation("Airport");
			journey.setVehicleID("NA");
			journey.setEntryID("TEST");
			journey.setNumberOfPassengers(1);
			journey.setOdometerStart(0);
			journey.setOdometerEnd(0);
			journey.setPersonalDistance(0);

			
			BasicDBObject myDoc = journey.getDocument();//convert the Journey object into a Journey document
			myMongoFunctions.addDoc(myCollection, myDoc);// store the Journey document in the Journeys collection
		}
	

	    ArrayList<DBObject> Docs = new ArrayList<DBObject>();//an ArrayList of Journey document elements
	    //Docs = myMongoFunctions.getDocs(myCollection);//get all the documents from the Journeys collection
	    Docs = myMongoFunctions.getDocsByField(myCollection, "EntryID", "gWsrgR2N6SaDlltFTpeY$pEXlXj$pEDceqJ4g");//search for Journey documents for this Expense
	    int count = Docs.size(); // the number of Journey documents in the ArrayList
	    DBObject doc = null;//placeholder for a Journey document

	    out.println("Journey Count: " + count);

	    if (count > 0) {// then there are Journey documents in the ArrayList
	    	for (int index=0; index<count; index++) {// iterate for each Journey document
	    		doc = Docs.get(index);// get the Journey document for this iteration
	    		journey = myMongoFunctions.doctoJourney(doc);
	    		journey.display();
	    		out.println("---------------------------------------------------------");
	    		out.println();
	    	}
	    }
	}

}
