package com.pivotpayables.expensesimulator;
/**
 * @author John Toman
 * 2/5/2015
 * This is the base class for a Trip.
 *
 */

import java.util.Date;

import com.pivotpayables.concurplatform.Location;

import java.text.SimpleDateFormat;

import static java.lang.System.out;

public class Trip {
	Date BeginningDate;//when the trip begins
	int Length;//how many days it last
	Employee employee;//who took the trip
	Location destination;//what is the trip's destination


	
	public Trip(Date BegDate,int length, Employee emp, Location loc) {//constructor

		BeginningDate = BegDate;
		Length = length;
		employee = emp;
		destination = loc;//the trip's destination
		

	}
	public void display () {//method to display the trip in the console
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
		String strDate = sdf.format(BeginningDate);	      

		employee.display();
		out.println("Start Date: " + strDate);
		out.println("Length: " + Length);
		destination.display();

	}

}
