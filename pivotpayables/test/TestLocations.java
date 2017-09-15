/**
 * 
 */
package com.pivotpayables.test;

/**
 * @author John Toman
 * 2-4-2015
 * This program tests the program CreateLocations to ensure it successfully created the location documents in
 * the MongoDB.  It displays in the console all the documents in the Locations collection.
 *
 */
import java.util.ArrayList;

import com.mongodb.DBObject;
import com.mongodb.DBCollection;
import com.pivotpayables.concurplatform.Location;
import com.pivotpayables.expensesimulator.MongoDBFunctions;

import static java.lang.System.out;

public class TestLocations {


	public static void main(String[] args) {
		String host= "localhost";//the MongoDB server host
		int port = 27017;//the MongoDB server port

		
		//Get Location documents
		
		MongoDBFunctions myMongoFunctions = new MongoDBFunctions (host,port , "Company_Data", "Locations");//establish a MongoDB Client to the Locations collection 
		DBCollection myCollection = myMongoFunctions.getCollection();//get the Locations collection


	    Location loc= new Location();//placeholder for a Location object
	    ArrayList<DBObject> Docs = new ArrayList<DBObject>();//an ArrayList of Location document elements
	    Docs = myMongoFunctions.getDocs(myCollection);//get all the documents from the Locations collection
	    DBObject doc = null;//placeholder for a Location document

	    int locationcount = Docs.size();//the number of elements in the ArrayList of Location documents

	    
	    for (int index =0; index<locationcount; index++) {//iterate for each location
	    	doc = Docs.get(index);//get the Location document for this iteration
	    	loc = loc.doctoLocation(doc);//convert the document into a Location object
	    	out.println("Location Number: " + Integer.toString(index+1));
			out.println("---------------------------------------");
	    	loc.display();//display it   
			out.println();
	    }//for i loop


	}//main method

}
