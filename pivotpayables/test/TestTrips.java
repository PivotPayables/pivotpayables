package com.pivotpayables.test;
/**
 * @author John Toman
 * 2-5-2015
 * This program tests the program Trips.create method to ensure it can create the trips for a specified employee and date range.
 *
 */
import static java.lang.System.out;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import com.mongodb.DBObject;
import com.mongodb.DBCollection;
import com.pivotpayables.expensesimulator.Employee;
import com.pivotpayables.expensesimulator.MongoDBFunctions;
import com.pivotpayables.expensesimulator.Trip;
import com.pivotpayables.expensesimulator.Trips;


public class TestTrips {


	public static void main(String[] args) {

		Calendar BeginningDate = Calendar.getInstance();// a Calendar object to track the beginning date
		Calendar EndingDate= Calendar.getInstance();// a Calendar object to track the ending date
		
		ArrayList<Trip> trips = new ArrayList<Trip>();// an ArrayList to store the trips
		Trip trip;// placeholder for the Trip object
		
		//set the date range for the trips to span
		BeginningDate.set(2014, 0, 1);//January 1, 2014
		EndingDate.set(2014, 11, 31);//December 31, 2014
		
		String host= "localhost";//the MongoDB server host
		int port = 27017;//the MongoDB server port
		//Get Employee documents
		
		MongoDBFunctions myMongoFunctions = new MongoDBFunctions (host, port, "Company_Data", "Employees");
		DBCollection myCollection = myMongoFunctions.getCollection();//get the Employees collection
	    
	    ArrayList<DBObject> Docs = new ArrayList<DBObject>();//an ArrayList of Employee document elements
	    Docs = myMongoFunctions.getDocs(myCollection);//get all the documents from the Employees collection and store these in the ArrayList
	    int employeecount = Docs.size();//the number of elements in the ArrayList of Employee documents
	    Random myRandom = new Random();
	    int index = myRandom.nextInt(employeecount);// a random integer between 0 and the number of employees in the ArrayList
	    DBObject doc = Docs.get(index);// select a radom employee from the ArrayList of employees
	    Employee myEmployee = myMongoFunctions.doctoEmployee(doc);// convert this document into an Employee object


		trips = Trips.create(BeginningDate,EndingDate, myEmployee, myMongoFunctions);
		int tripcount = trips.size();// the number of trips in the ArrayList of Trip elements
		

	    for (index=0; index< tripcount; index++) {// iterate for each trip
	    	out.println("Trip Number" + Integer.toString(index+1));
			out.println("---------------------------------------");
	    	trip = trips.get(index);//get the trip for this iteration
	    	trip.display();    
			out.println();
	    }//for i block	   

	}//end main method

}
