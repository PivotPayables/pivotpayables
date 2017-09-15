package com.pivotpayables.expensesimulator;
/**
 * 
 */

/**
 * @author John Toman
 * 5/19/2015
 * This class creates a set of simulated expenses for a specified trip.
 *
 */



//import static java.lang.System.out;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import com.pivotpayables.concurplatform.Itemization;
import com.pivotpayables.concurplatform.Journey;
import com.pivotpayables.concurplatform.Location;

import java.util.ArrayList;

public class CreateExpenses {
	static String ExpenseTypes[] = new String[50];// an array of String elements for storing expense types
	static String PaymentTypes[] = new String[20];// an array of String elements for storing payment types
	static Calendar myCalendar = Calendar.getInstance();// a Calendar object for processing dates
	static int hundreds, tens, ones, dimes, cents;// Placeholder for money
	

	public static ArrayList<Expense> TripExpenses (Trip t, Employee emp, Company comp) {//creates simulated expenses for a specified trip, for the specified employee, and the specified company
		Trip trip=t;// create expenses for this trip
		Employee myEmployee = emp;// create expenses for this employee
		Company myCompany = comp;// the employee's employer
		Date date= trip.BeginningDate;// a placeholder for the Date object.  Initialize it to the trip's beginning date 


		int length = trip.Length;// how many days in the trip
		ArrayList<Expense> expenses = new ArrayList<Expense>();// an ArrayList of Expense elements for storing the trip's expenses
		Expense expense=null;

		// Initialize the Expense Types
		ExpenseTypes[0]="Airfare";
		ExpenseTypes[1]="Car Rental";
		ExpenseTypes[2]= "Hotel";
		ExpenseTypes[3]="Room Rate";
		ExpenseTypes[4]="Travel Meal";
		ExpenseTypes[5]="Internet/Telecommunication";
		ExpenseTypes[6]="Resort Fee";
		ExpenseTypes[7]="Room Taxes";
		ExpenseTypes[8]="Parking";
		ExpenseTypes[9]="Personal Mileage";
		ExpenseTypes[10]="Vehicle Fuel";
		ExpenseTypes[11]="Taxi, hired ride";
		ExpenseTypes[12]="Entertainment";
		ExpenseTypes[13]="Courier/Delivery Service";
		ExpenseTypes[14]="Photocopying/Reprographic Service";
		ExpenseTypes[15]="Beverage:Not Alcoholic";
		ExpenseTypes[16]="Beverage: Alcoholic";
		ExpenseTypes[17]="Baggage Fee";
		ExpenseTypes[18]="Gratuity/Tip";
		ExpenseTypes[19]="Public Transport: Bus, Rail";
		ExpenseTypes[20]="Common Carrier: Rail";
		ExpenseTypes[21]="Common Carrier: Bus";
		

		// Initialize the Payment Types
		PaymentTypes[0]="Cash/Personal Credit Card";
		PaymentTypes[1]="Company Card: AMEX";
		PaymentTypes[2]="Company Card: MasterCard";
		PaymentTypes[3]="Compnay Card: Visa";

		
		Location homelocation = myEmployee.Home;// set the home location to the employee's home location
		Location destination = trip.destination;// the trip's destination

		double originalamount;// placeholder for the Original Amount
		String postedcurrency= myCompany.PostingCurrency;// assign the Posted Currency to the company's posting currency
		String paymenttype= PaymentTypes[1];// placeholder for the payment type.  initialize it to AMEX
		int distance=0;// placeholder for personal mileage distance
		Journey journey = new Journey();// placeholder for the Journey object
		String typeregular;// placeholder for an expense type to be used for an Expense with a Regular expense code
		String merchant;// placeholder for the merchant
		Random myRandom = new Random();
		
	
		if (length > 1){ //multi-day trip
			//create a personal mileage expense for the journey from employee's home to airport

			paymenttype = PaymentTypes[0];//set payment type for personal mileage to Cash
			date =trip.BeginningDate;// set the date to the trip's beginning date
			
			// create the journey
			journey.setID(GUID.getGUID(4));// create the GUID for the Journey object
			distance = 10 + CustomFunctions.skewedDistibution(100);//random mileage between 11 and 111 miles, skewed left
			journey.setBusinessDistance(distance);
			journey.setUnitOfMeasure("M");//miles is unit of measure
			journey.setStartLocation("Home");
			journey.setEndLocation("Airport");
			journey.setVehicleID("NA");
			journey.setNumberOfPassengers(1);
			journey.setOdometerStart(0);;
			journey.setOdometerEnd(0);
			journey.setPersonalDistance(0);

			expense = createPersonalMileage (date, journey, myEmployee, ExpenseTypes[9], paymenttype, postedcurrency, homelocation);// create a personal mileage expense
			expenses.add(expense);// add this expense to the ArrayList of Expense elements
			
			//create a personal mileage expense for the journey from airport to employee's home 
			journey = new Journey();
			journey.setID(GUID.getGUID(4));// create the GUID for the Journey object
			journey.setBusinessDistance(distance);
			journey.setUnitOfMeasure("M");//miles is unit of measure
			journey.setStartLocation("Airport");
			journey.setEndLocation("Home");
			journey.setVehicleID("NA");
			journey.setNumberOfPassengers(1);
			journey.setOdometerStart(0);;
			journey.setOdometerEnd(0);
			journey.setPersonalDistance(0);
			
			myCalendar.setTime(date);// placeholder for the Calendar object.  Initialize it to the trip's beginning date
			myCalendar.add(length, Calendar.DATE);// calculate the end date for the trip by adding the trip's length to the beginning date
			date = myCalendar.getTime();// set the Expense date to the end date
			
			expense = createPersonalMileage (date, journey, myEmployee, ExpenseTypes[9], paymenttype,  postedcurrency, homelocation);// create a personal mileage expense
			expenses.add(expense);// add this expense to the ArrayList of Expense elements
			
			
			// create an airfare expense
			paymenttype = PaymentTypes[1];//set payment type for airfare to AMEX
			merchant = "Alaska Airlines";
			
			expense = createAirfare(trip, myEmployee, ExpenseTypes[0], paymenttype, postedcurrency, homelocation, merchant);// create an airfare expense
			expenses.add(expense);// add this expense to the ArrayList of Expense elements
			
			// create a hotel expense
			merchant = "Hilton Hotels";
			expense = createHotel (trip, myEmployee, paymenttype, postedcurrency, destination, merchant);// create a hotel expense
			expenses.add(expense);// add this expense to the ArrayList of Expense elements
			
			//create the travel means for this trip
			paymenttype = PaymentTypes[1];//set payment type for travel meal to AMEX
			typeregular =  ExpenseTypes[4];// set typeregular to the Travel Meal expense type
			merchant = "Not specified";
			
			for (int i=1; i<length; i++) {//iterate for each day in the trip
				if ((i==1)||(i==length)) {//then this is the first or last day of the trip, create only two meals for these days
					for (int j=0; j <2; j++){
						//create a travel meal expense
						tens= myRandom.nextInt(3) +1;//random integer between 1 and 4
						ones = myRandom.nextInt(10);
						dimes = myRandom.nextInt(10);
						cents = myRandom.nextInt(10);
						originalamount = (10*tens) + (ones) + (0.1*dimes) + (0.01*cents);//Original Amount in Original Currency
						expense = createRegular (date, myEmployee, typeregular, paymenttype, originalamount, postedcurrency, destination, merchant);// create a travel meal expense
						expenses.add(expense);
					}//j loop
				} else {//non travel day, create three meals for these days
					for (int j=0; j <3; j++){
						//create a travel meal expense
						tens= myRandom.nextInt(3) +1;//random integer between 1 and 4
						ones = myRandom.nextInt(10);
						dimes = myRandom.nextInt(10);
						cents = myRandom.nextInt(10);
						originalamount = (10*tens) + (ones) + (0.1*dimes) + (0.01*cents);//Original Amount in Original Currency
						
						expense = createRegular (date, myEmployee, typeregular, paymenttype, originalamount, postedcurrency, destination, merchant);// create a travel meal expense
						expenses.add(expense);
					}//j loop
				}//if block
				
				myCalendar.add(Calendar.DATE, 1);//increment the calendar date
				date = myCalendar.getTime();//set the date to new calendar date
			}//i loop
			
		} else {// day trip
			destination = homelocation;// a day trip can be only to the employee's Home location
			//create a personal mileage expense for the journey from employee's home to client's office

			paymenttype = PaymentTypes[0];//set payment type for personal mileage to Cash
			
			// create the journey
			journey.setID(GUID.getGUID(4));// create the GUID for the Journey object
			journey.setBusinessDistance(20 + CustomFunctions.skewedDistibution(100));//random mileage between 21 and 121 miles, skewed left
			journey.setUnitOfMeasure("M");//miles is unit of measure
			journey.setStartLocation("Home");
			journey.setEndLocation("Client's office");
			journey.setVehicleID("NA");
			journey.setNumberOfPassengers(1);
			journey.setOdometerStart(0);;
			journey.setOdometerEnd(0);
			journey.setPersonalDistance(0);
			
			date =trip.BeginningDate;


			expense = createPersonalMileage (date, journey, myEmployee, ExpenseTypes[9], paymenttype, postedcurrency, homelocation);// create a personal mileage expense
			expenses.add(expense);
			
			//create a travel meal expense
			paymenttype = PaymentTypes[1];//set payment type for travel meal to AMEX
			typeregular =  ExpenseTypes[4];//a Travel Meal
			merchant = "Not specified";
			tens= myRandom.nextInt(3) +1;//random integer between 1 and 4
			ones = myRandom.nextInt(10);
			dimes = myRandom.nextInt(10);
			cents = myRandom.nextInt(10);
			originalamount = (10*tens) + (ones) + (0.1*dimes) + (0.01*cents);//Original Amount in Original Currency
			
			expense = createRegular (date, myEmployee, typeregular, paymenttype, originalamount, postedcurrency, destination, merchant);// create a travel meal expense
			expenses.add(expense);

		}

		return expenses;// return the ArrayList of Expense elements with the trip's expenses
	}


private static Expense createAirfare (Trip t, Employee emp, String expensetype, String paymenttype, String postedcurrency, Location loc, String merchant){// create an airfare expense
	Trip trip = t;
	String type = ExpenseTypes[0];// set expense type to Airfare
	String payment = paymenttype;// set the specified payment type
	String code = "Regular";//The airfare expense type is a Regular expense code
	String ocur = getCurrency(loc);// set Original Currency
	String pcur = postedcurrency;// set Posted Currency 
	Location  location = loc;// where was it purchased
	String Merchant = merchant;// purchased from this merchant

	Date date = trip.BeginningDate;
	Random myRandom = new Random();

	
	hundreds = CustomFunctions.skewedDistibution(10);
	tens= myRandom.nextInt(10);
	ones = myRandom.nextInt(10);
	dimes = myRandom.nextInt(10);
	cents = myRandom.nextInt(10);
	double oamt = (100*hundreds) + (10*tens) + (ones) + (0.1*dimes) + (0.01*cents);//Original Amount in Original Currency
	oamt = CustomFunctions.convertMoney(oamt, 2);
	double pamt = convertCurrency(oamt, ocur, pcur);// calculate the Posted Amount
	String status ="Final Approval";//set Approval Status
	
	Expense expense = new Expense(emp, payment, code, type, date, ocur, oamt, pcur, pamt, status);// create an Expense object
	expense.setEntry_ID("None");
	expense.setReportID("None");
	expense.setEmployeeFirstName(emp.FirstName);
	expense.setEmployeeMiddleInitial(emp.MiddleInitial);
	expense.setEmployeeLastName(emp.LastName);
	expense.setDescription("Sample description or purpose of this expense");
	expense.setHasBillableItems(true);
	expense.setLocationDisplayName(location.getDisplayName());
	expense.setLocationCity(location.getCity());
	expense.setLocationState(location.getState());
	expense.setLocationCountry(location.getCountryCode());
	expense.setApprovedAmount(pamt);
	expense.setMerchantName(Merchant);
	expense.setPaymentStatus("Paid");
	expense.setPaidDate(date);
	
	
	// create the Itemization
	String entryid = expense.getID();
	Boolean billable=true;
	Itemization item = new Itemization(entryid, type, date, oamt, pamt, billable);
	item.setApprovedAmount(pamt);// set the Approved Amount to the Posted Amount
	ArrayList<Itemization> items= new ArrayList<Itemization>();// an ArrayList of Itemization elements to store the expense's itemization
	items.add(item);// add this to the ArrayList of Itemization elements
	expense.setItems(items);// add the items to the Expense object
	return expense;
}
private static Expense createHotel (Trip t, Employee emp, String paymenttype, String postedcurrency, Location loc, String merchant){// create a hotel expense
	Trip trip = t;
	int days = trip.Length;//number of days stayed
	String typehotel = ExpenseTypes[2];
	String typedailyroom = ExpenseTypes[3];
	String payment = paymenttype;
	String code = "Hotel";//hotel is a Hotel expense code
	String ocur = getCurrency(loc);// get the Original Currency for the location
	String pcur = postedcurrency;//set Posted Currency
	Location location = loc;
	String Merchant = merchant;

	Date date = trip.BeginningDate;//first day is the beginning of the trip
	myCalendar.setTime(date);//the calendar date for a day stayed

	int hundreds, tens, ones, dimes, cents;
	Random myRandom = new Random();

	//create a random, daily room rate between $100 and $299 a night
	hundreds = myRandom.nextInt(1)+1;//random integer, 1 or 2
	tens= myRandom.nextInt(10);
	ones = myRandom.nextInt(10);
	dimes = myRandom.nextInt(10);
	cents = myRandom.nextInt(10);
	double dailyoamt = (100*hundreds) + (10*tens) + (ones) + (0.1*dimes) + (0.01*cents);//Original Amount in Original Currency
	dailyoamt = CustomFunctions.convertMoney(dailyoamt, 2);// convert the daily room rate, Original Currency, into money 
	double dailypamt = convertCurrency(dailyoamt, ocur, pcur);// calculate the Posted Amount  for the daily room rate in Posted Currency
	dailypamt = CustomFunctions.convertMoney(dailypamt, 2);// convert the daily room rate, Posted Currency, into money 
	String status ="Final Approval";//set Approval Status
	
	double oamt = days * dailyoamt;//Original Amount for entire stay
	double pamt = days * dailypamt;//Posted Amount for entire stay


	
	Expense expense = new Expense(emp, payment, code, typehotel, date, ocur, oamt, pcur, pamt, status);
	expense.setEntry_ID("None");
	expense.setReportID("None");
	expense.setEmployeeFirstName(emp.FirstName);
	expense.setEmployeeMiddleInitial(emp.MiddleInitial);
	expense.setEmployeeLastName(emp.LastName);
	expense.setDescription("Sample description or purpose of this expense");
	expense.setHasBillableItems(true);
	expense.setLocationDisplayName(location.getDisplayName());
	expense.setLocationCity(location.getCity());
	expense.setLocationState(location.getState());
	expense.setLocationCountry(location.getCountryCode());
	expense.setApprovedAmount(pamt);
	expense.setMerchantName(Merchant);
	expense.setPaymentStatus("Paid");
	expense.setPaidDate(date);
	
	//create itemizations for each day stayed
	String entryid = expense.getID();
	Boolean billable=true;
	ArrayList<Itemization> items= new ArrayList<Itemization>();
	
	for (int i=0; i<days; i++) {//iterate for each day
		Itemization item = new Itemization(entryid, typedailyroom, date, dailyoamt, dailypamt, billable);
		item.setApprovedAmount(dailypamt);// set the Approved Amount to the Posted Amount
		items.add(item);
		myCalendar.add(Calendar.DATE, 1);//increment the calendar date
		date = myCalendar.getTime();//set the date to new calendar date
	}
	expense.setItems(items);
	return expense;
}
private static Expense createPersonalMileage (Date d, Journey j, Employee emp, String expensetype, String paymenttype, String postedcurrency, Location loc){
	Date date = d;
	Journey journey = j;
	String type = expensetype;
	String payment = paymenttype;
	String code = "Personal Car";//personal mileage is a Personal Car expense code
	String ocur = getCurrency(loc);//Original Currency
	String pcur = postedcurrency;//set Posted Currency 
	Location location = loc;

	double reimbursementrate = 0.55;//mileage reimbursement rate $0.55/mile

	double oamt = journey.getBusinessDistance() * reimbursementrate;//Original Amount in Original Currency
	oamt = CustomFunctions.convertMoney(oamt, 2);//convert the Original Amount into a double suitable to represent currency for USD/CAD
	double pamt = convertCurrency(oamt, ocur, pcur);// calculate the Posted Amount
	pamt = CustomFunctions.convertMoney(pamt, 2);//convert the Original Amount into a double suitable to represent currency for USD/CAD
	String status ="Final Approval";//set Approval Status

	Expense expense = new Expense(emp, payment, code, type, date, ocur, oamt, pcur, pamt, status);
	expense.setEntry_ID("None");
	expense.setReportID("None");
	expense.setEmployeeFirstName(emp.FirstName);
	expense.setEmployeeMiddleInitial(emp.MiddleInitial);
	expense.setEmployeeLastName(emp.LastName);
	expense.setDescription("Sample description or purpose of this expense");
	expense.setHasBillableItems(true);
	expense.setLocationDisplayName(location.getDisplayName());
	expense.setLocationCity(location.getCity());
	expense.setLocationState(location.getState());
	expense.setLocationCountry(location.getCountryCode());
	expense.setApprovedAmount(pamt);
	expense.setMerchantName("Not applicable for mileage");
	expense.setPaymentStatus("Paid");
	expense.setPaidDate(date);
	expense.setJourney(journey);

	String entryid = expense.getID();
	journey.setEntryID(entryid);
	Boolean billable=true;
	Itemization item = new Itemization(entryid, type, date, oamt, pamt, billable);
	item.setApprovedAmount(pamt);
	ArrayList<Itemization> items= new ArrayList<Itemization>();
	items.add(item);
	expense.setItems(items);
	return expense;
}
private static Expense createRegular (Date d, Employee emp, String expensetype, String paymenttype, double originalamount, 
		String postedcurrency, Location loc, String merchant){
	//create a Regular expense
	Date date = d;// placeholder for Expense date
	String type = expensetype;// placeholder for the expense type
	String payment = paymenttype;// placeholder for the payment type
	String code = "Regular";// set the expense code to Regular.  A Regular expense code is for "regular expenses"... ones that don't require special treatment, as is the case for mileage, hotel, travel allowance (TA), and cash advance
	String ocur = getCurrency(loc);// get the Original Currency for the location
	String pcur = postedcurrency;//set Posted Currency
	double postedamount = convertCurrency(originalamount, ocur, pcur);// calculate the Posted Amount
	Location location = loc;
	String Merchant = merchant;



	double oamt = originalamount;
	double pamt = postedamount;//set Posted Amount 
	String status ="Final Approval";//set Approval Status

	Expense expense = new Expense(emp, payment, code, type, date, ocur, oamt, pcur, pamt, status);
	expense.setEntry_ID("None");
	expense.setReportID("None");
	expense.setEmployeeFirstName(emp.FirstName);
	expense.setEmployeeMiddleInitial(emp.MiddleInitial);
	expense.setEmployeeLastName(emp.LastName);
	expense.setDescription("Sample description or purpose of this expense");
	expense.setHasBillableItems(true);
	expense.setLocationDisplayName(location.getDisplayName());
	expense.setLocationCity(location.getCity());
	expense.setLocationState(location.getState());
	expense.setLocationCountry(location.getCountryCode());
	expense.setApprovedAmount(pamt);
	expense.setMerchantName(Merchant);
	expense.setPaymentStatus("Paid");
	expense.setPaidDate(date);

	// create the Itemization
	String entryid = expense.getID();
	Boolean billable=true;
	Itemization item = new Itemization(entryid, type, date, oamt, pamt, billable);
	item.setApprovedAmount(pamt);// set the Approved Amount to the Posted Amount
	ArrayList<Itemization> items= new ArrayList<Itemization>();// an ArrayList of Itemization elements to store the expense's itemization
	items.add(item);// add this to the ArrayList of Itemization elements
	expense.setItems(items);// add the items to the Expense object
	return expense;
}
private static String getCurrency (Location loc) {//returns the currency for the specified location
	String countrycode = loc.getCountryCode();// get the location's Country Code
	String currency = "USD";// placeholder for the currency.  Set the default to USD.
	
	if (countrycode.equals("CA")) {// then the country is Canada
		currency = "CAD";
	}
	return currency;// return the currency
}
private static double convertCurrency (double amt, String ocur, String convcur) { // convert the specified amount in its Original Currency to its Converted Currency
	double convertedamount;
	
	if ((ocur.equals("CAD")) && (convcur.equals("USD"))) {// then convert Canadian dollar into US dollar
		convertedamount = amt * 0.80;// convert CAD to USD
	} else if ((ocur.equals("USD")) && (convcur.equals("CAD"))){// then convert US dollar into Canadian dollar
		convertedamount = amt * 1.24;// convert USD to CAD
	} else {// then don't convert the amount
		convertedamount= amt;
	}
	return convertedamount;// return the converted amount
}


}
