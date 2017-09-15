
package com.pivotpayables.expensesimulator;



/**
 * @author John Toman
 * 5/18/2015
 * 
 * This creates expenses in Concur for a specified trip
 *
 */
import static java.lang.System.out;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

import com.pivotpayables.concurplatform.*;
public class CreateDemoExpenses {

	/**
	 * @param args
	 */
	private static Calendar BeginningDate = Calendar.getInstance();// a Calendar object to track the beginning date
	private static Calendar EndingDate= Calendar.getInstance();// a Calendar object to track the ending date
	
	private static ArrayList<Trip> trips = new ArrayList<Trip>();// an ArrayList to store the trips
	private static Trip trip;// placeholder for the Trip object
	private static Employee employee = new Employee();
	private static Company company = new Company ();
	private static String user ="wsadmin@Connect-PivotPayables.com";
	private static String reportID = "323B2813CCD8405D80EC";
	private static String key = "kzfF70hLzdKJu1TxiQWthcJdXro=";
	private static int year;
	private static int month;
	private static int day;
	private static Location home = new Location ();
	private static ArrayList<Expense> expenses = new ArrayList<Expense>();
	private static Expense expense; // placeholder for an Expense object
	private static String code;
	private static String payment;
	private static String cash = "gWmU18qzMzI3JWU$sEuDnnxb9pWex$p";
	private static String cocard = "gWmU78aDgciTJyKKuR16iGFePqoWy";
	private static String personalmiles = "MILEG";
	private static String hotel = "LODNG";
	private static String roomrate = "LODNG";
	private static String airfare = "AIRFR";
	private static String travelmeal = "01028";
	private static Expenses e = new Expenses();
	
	
	


	public static void main(String[] args) throws IOException {
		//set the date range for the trips to span
		
		BeginningDate.set(2015, 3, 1);//April 1, 2015
		EndingDate.set(2015, 3, 30);//April 30, 2015
		Scanner myScanner = new Scanner(System.in);
		
		try {	
			if (args.length == 8) {//there are parameters for key, user, and password
				key = args[0];
				user = args[1];
				try {
					year = Integer.parseInt(args[2]);
				} catch (NumberFormatException e) {
					year = 2015;
				}
				try {
					month = Integer.parseInt(args[3]);
				} catch (NumberFormatException e) {
					month = 0;
				}
				try {
					day = Integer.parseInt(args[4]);
				} catch (NumberFormatException e) {
					day = 1;
				}
				BeginningDate.set(year, month, day);
				try {
					year = Integer.parseInt(args[5]);
				} catch (NumberFormatException e) {
					year = 2015;
				}
				try {
					month = Integer.parseInt(args[6]);
				} catch (NumberFormatException e) {
					month = 4;
				}
				try {
					day = Integer.parseInt(args[7]);
				} catch (NumberFormatException e) {
					day = 30;
				}
				EndingDate.set(year, month, day);
			} 
			/*else {//use the console to enter the key, user name, and password
				out.println("What is the key?");
				key = myScanner.next();
				out.println("What is the user login ID?");
				user = myScanner.next();
				out.println("What is the beginning date's year?");
				year = myScanner.nextInt();
				out.println("What is the beginning date's month?");
				month = myScanner.nextInt();
				out.println("What is the beginning date's day?");
				day = myScanner.nextInt();
				BeginningDate.set(year, month, day);
				out.println("What is the ending date's year?");
				year = myScanner.nextInt();
				out.println("What is the ending date's month?");
				month = myScanner.nextInt();
				out.println("What is the ending date's day?");
				day = myScanner.nextInt();
				EndingDate.set(year, month, day);
				


			}
							*/
			myScanner.close();
		} catch (Exception e) {
        		out.println(e);
		}
		employee = GetConcurUser.getEmployee(user, key);// create an Employee object for the specified Concur user
		//employee.display();
		home = employee.getHome();
		home.setCity("Bellevue");
		
		employee.setHome(home);
		company.setPostingCurrency("USD");
		trips = Trips.create(BeginningDate, EndingDate, employee);
		int tripcount = trips.size();// the number of trips in the ArrayList of Trip elements


	    for (int index=0; index< tripcount; index++) {// iterate for each trip
	    	out.println("Trip Number" + Integer.toString(index+1));
			out.println("---------------------------------------");
	    	trip = trips.get(index);//get the trip for this iteration
	    	//trip.display();
	    	expenses = CreateExpenses.TripExpenses(trip, employee, company);
	    	int expensecount = expenses.size();// the number of expenses for this trip
	    	
	    	
			for (int j=0; j<expensecount; j++) {// iterate for each expense
				expense = expenses.get(j);// get the expense for this iteration
				//out.println("----------------------");
				//expense.display();	
				postExpense();// POST the expense to Concur
			}// j loop
			out.println();
	    }//for i block	
		
	}
	private static void postExpense() {
		if (expense.getExpenseTypeName().equals("Personal Mileage")) {
			code = personalmiles;
		} else if (expense.getExpenseTypeName().equals("Airfare")) {
			code = airfare;
		} else if (expense.getExpenseTypeName().equals("Hotel")) {
			code = hotel;
		} else if (expense.getExpenseTypeName().equals("Room Rate")) {
			code = roomrate;
		} else if (expense.getExpenseTypeName().equals("Travel Meal")) {
			code = travelmeal;
		}
		
		if (expense.getPaymentTypeName().equals("Cash/Personal Credit Card")) {
			payment = cash;
		} else if (expense.getPaymentTypeName().equals("Company Card: AMEX")) {
			payment = cocard;
		}
		expense.setExpenseTypeCode(code);
		expense.setPaymentTypeID(payment);
		expense.setReportID(reportID);
		//expense.display();
		e.addExpense(expense, key);
	}



}
