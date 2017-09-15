
package com.pivotpayables.expensesimulator;

/**
 * @author John Toman
 * 8/19/15
 * 
 * This class creates an Employee in the MongoDB for the specified company that can be used for demonstratins or testing.
 *
 */
import static java.lang.System.out;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.pivotpayables.concurplatform.Location;
public class CreateDemoEmployee {
	private static String firstname = "Admin";
	private static String lastname = "User";
	private static String login ="Support@westyost.com";
	private static String coid = "KGLL8737HDKO3434DMHM";
	private static String coname = "West Yost";
	

	/**
	 * @param args
	 * arg[0] is how  many employees to create.
	 * arg[1] is where to store the employees: 1) console, 2) MongoDB
	 * arg[2] is the Employer Company ID.
	 * 
	 */
	public static void main(String[] args) throws FileNotFoundException {


		
		
	    ArrayList<DBObject> docs = new ArrayList<DBObject>();//an ArrayList of document elements    
	    DBObject doc = null;//placeholder for a Company document
	    
		String host= "localhost";//the MongoDB server host
		int port = 27017;//the MongoDB server port
		MongoDBFunctions myMongoCompanyFunctions = new MongoDBFunctions (host, port, "Company_Data");// create a MongoDB Client for the specified host and port, and get the Company_Data database 
		DBCollection locCollection = myMongoCompanyFunctions.setCollection("Locations");// get the Locations collection
		DBCollection empCollection = myMongoCompanyFunctions.setCollection("Employees");// get the Locations collection
		
		docs = myMongoCompanyFunctions.getDocsByField(locCollection, "City", "Bellevue");// find the location for Bellevue, WA
		doc = docs.get(0);// get this location document from the ArrayList
		Location location = myMongoCompanyFunctions.doctoLocation(doc);// convert this Location document into a Location object
		Employee employee = new Employee (firstname, ' ',lastname, login, login, coid, coname,
				 location);// create the Employee object for the Pivot Payables Sandbox, WS Admin user
		employee.display();
		BasicDBObject myDoc = employee.getDocument();// convert this Employee object into a BasicDBObject
		myMongoCompanyFunctions.addDoc(empCollection, myDoc);// add this BasicDBObject to the MongoDB

		
	}//end main method

}
