package com.pivotpayables.expensesimulator;

/**
 * @author John Toman
 * 2/5/2015
 * This creates a set of trips for a specified employee over a specified date range
 *
 */

import java.util.Random;

//import static java.lang.System.out;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import static java.lang.System.out;

import com.pivotpayables.concurplatform.Location;
import com.pivotpayables.test.CreateLocations;



public class Trips{

	
	public Trips() {//constructor: uses the date range for the specified employee

	}
	public static ArrayList<Trip> create(Calendar BegDate,Calendar EndDate, Employee emp){

		Employee myEmployee = emp;// This is the employee for this trip
		
		
		Calendar Start = Calendar.getInstance();//a Calendar object to track the Start date
		Calendar End = Calendar.getInstance();//a Calendar object to track the End date

		int year=0, month=0, date=0;
		
		//Set the Start Date to the specified beginning date
		year = BegDate.get(Calendar.YEAR);
		month= BegDate.get(Calendar.MONTH);
		date = BegDate.get(Calendar.DATE);
		Start.set(year, month, date);
		
		//Set the End Date to the specified beginning date
		year = EndDate.get(Calendar.YEAR);
		month= EndDate.get(Calendar.MONTH);
		date = EndDate.get(Calendar.DATE);
		End.set(year, month, date);
		

		Date start = Start.getTime();//placeholder for the start date
		Calendar myDate = Start;//placeholder Calendar object
		
		Random myRandom = new Random();
		
        // Get the date in milliseconds
        long milis1 = Start.getTimeInMillis();//Start Date in milliseconds
        long milis2 = End.getTimeInMillis();//End Date in milliseconds

        
        // Calculate difference in milliseconds
        long diff = milis2 - milis1;//the date range (Start Date - End Date) in milliseconds

        
        // Calculate difference in days
        long diffDays = diff / (24 * 60 * 60 * 1000);//convert these milliseconds into the number of days in the date range


        // Calculate the number of trips that span the date range
        int tripcount=0, remainder;
        remainder = (int)(diffDays % 7);//divide the number of days in the date range by 7 to calculate the number of week long trips that span the date range. 
        // remainder is the number of days that remain


        tripcount = ((int)diffDays/7);//the number of week long trips during the date range
        if (remainder != 0) {//then there needs to be one more trip added to cover the remaining days
        	tripcount = tripcount + 1;//add this trip to the trip count
        }//if block

		ArrayList<Trip> trips = new ArrayList<Trip>();//initialize an ArrayList of Trips with one element per trip
		Trip trip;//placeholder for the Trip object
		Location location;//placeholder for a Location object
	
		
        for(int i = 0; i < tripcount; i++) {// iterate for each trip
        	int triplength = myRandom.nextInt(5) +1;// the number of days in this trip: random integer between 1 and 5.  So, trips will be between 1 and 5 days in length.
			location = getRandomLocation();// select a random location as the trip's destination
        	trip = new Trip(start, triplength, myEmployee, location);//create a Trip object for this trip
        	trips.add(trip);//add it to the ArrayList of Trip elements
        	myDate.add(Calendar.DATE, 7);// Calculate the start date for the next trip to one week from this week's start date;
        	start = myDate.getTime();// set start date to this new date
        }//i loop

        return trips;	
        	
	}//end create method
	private static Location getRandomLocation() {
		Random myRandom = new Random();

	    Location location=null;//placeholder for the Location object
	    ArrayList<Location> locations = CreateLocations.getLocations();
	    int locationcount = locations.size();//the number of locations in the ArrayList of Location objects
	    int index = myRandom.nextInt(locationcount);//select a random integer between 0 and the number of elements in the ArrayList of Location objects
	    location = locations.get(index);//convert the document into a Location object
		return location;
		
	}

		
}


