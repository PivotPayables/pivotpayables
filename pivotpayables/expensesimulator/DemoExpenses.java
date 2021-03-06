
package com.pivotpayables.expensesimulator;
/**
 * @author John Toman
 * 3/7/2015
 * This program creates simulated expenses for a specified, "demonstration company" created using the CreateDemoCompany function.
 * It stores these expenses for one of these options: 1) console, 2) MongoDB or 3) to Concur Expense (not operational yet)
 * 
 * Prior to using this program you must use the CreateDemoCompany program to create in the MongoDB a demonstration compapy.
 * A demonstration company is one configured expressly for Pivot Payables product demonstrations.
 * 
 * It creates for trips that span 60 days from today.
 * 
 *
 */

import static java.lang.System.out;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.pivotpayables.concurplatform.Itemization;
import com.pivotpayables.prime.AccountActivity;
import com.pivotpayables.prime.AccountActivityCharge;

/**
 * @author TranseoTech
 *
 */
public class DemoExpenses {
	protected static String companyid=null;


	public static void main(String[] args) {
		int option =1;//where to process the expenses: 1) console, 2) MongoDB, or 3) post to Concur Expense
		Random myRandom = new Random();
		

		if (args.length > 0) {
			companyid = args[0];
		} else {
			companyid ="PYCN5632YDXU0903TUIK";
		}
	

		
		int tripcount;// placeholder for the number of trips
		Calendar myCalendar = Calendar.getInstance();// placeholder for a Calendar object
		
		// Company Data
		ArrayList<DBObject> employeedocs = new ArrayList<DBObject>();//ArrayList of DBObject elements where each element represents an Employee document
		ArrayList<Employee> employees = new ArrayList<Employee>();//ArrayList of Employee elements where each element represents an Employee object in the sample
		ArrayList<DBObject> activitydocs = new ArrayList<DBObject>();//ArrayList of DBObject elements where each element represents an AccountActivity document
		ArrayList<AccountActivity> activities = new ArrayList<AccountActivity>();//this ArrayList holds an array of AccountActivity objects. 
		DBObject doc;// placeholder for a MongoDB document
		Company company=null;// placeholder for a Company object
		Employee employee;// placeholder for an Employee document

		
		// Expense Data
		Expense expense;// placeholder for an Expense object
	    ArrayList<Expense> expenses = new ArrayList<Expense>();//this is a ArrayList of Expense objects.  Each element represents one of the trip's expenses.
		Itemization item;// placeholder for an Itemization object
		Date paiddate;
		ArrayList<Itemization> items = new ArrayList<Itemization>();// an ArrayList of Itemization elements
		AccountActivity activity;//  placeholder for an AccountActivity object
		AccountActivityCharge charge;// placeholder for an AccountActivityCharge object
		ArrayList<AccountActivityCharge> charges = new ArrayList<AccountActivityCharge>();// Each elements represents one of the itemization's AccountActivityCharge. new ArrayList<AccountActivityCharge>();//this is a ArrayList of AccountActivityCharge objects.  


	    double splits[];//an array to hold the charge amounts
	    int expcount=0, itemcount=0;// the number of expenses, and items for for an expense; initialize to zero

		int employeecount = employees.size();//the number of employees in the sample
		int activitiescount;//the number of account activities

		
		// set the trips to span between 60 days before today and today
		Calendar BegDate = Calendar.getInstance();// Calendar object for the beginning date
		Calendar EndDate = Calendar.getInstance();// Calendar object for the end date; set to today

		BegDate.add(Calendar.DATE, -60);// set the Beginning 60 days before today

		
		Trip trip=null;// placeholder for the Trip object
		
		// connect to the MongoDB
		String host= "localhost";//the MongoDB server host
		int port = 27017;//the MongoDB server port

		
		// Connect to the Company_Data database, and then get the collections for Companies,Employees, and AccountActivities
		MongoDBFunctions myMongoCompanyFunctions = new MongoDBFunctions (host, port, "Company_Data");// create a MongoDB Client for the specified host and port, and get the Company_Data database 
		DBCollection comCollection = myMongoCompanyFunctions.setCollection("Companies");// get the Employees collection
		DBCollection empCollection = myMongoCompanyFunctions.setCollection("Employees");// get the Employees collection
		DBCollection actCollection = myMongoCompanyFunctions.setCollection("AccountActivities");// get the AccountActivities collection
		
		// Connect to  the Expense_Data database, and then get the Expenses and AccountActivityCharges collections
		MongoDBFunctions myMongoExpenseFunctions = new MongoDBFunctions (host, port, "Expense_Data");// create a MongoDB Client for the specified host and port, and get the Expense_Data database
		DBCollection chargeCollection = myMongoExpenseFunctions.setCollection("AccountActivityCharges");// get the Itemizations collections
        
       
        int index;// placeholder for an index
        
        // First, get the Company object for the specified Company ID
        
    	doc = myMongoCompanyFunctions.getDoc(comCollection, companyid);// get the Company document for the specified Company ID
    	company = myMongoCompanyFunctions.doctoCompany(doc);//convert the Company document into a Company object

    	
    	//Second, load the employees ArrayList with the employees for this Company
    	employeedocs = new ArrayList<DBObject>();// initiate the ArrayList of DBObject elements to hold Employee documents
    	employeedocs = myMongoCompanyFunctions.getDocsByField(empCollection, "EmployerCompanyID", company.ID);// get the Employee documents for this company's employees
    	int empcount = employeedocs.size();// the number of employees for this company
 		
		for (int e=0; e< empcount; e++) {// iterate for each employee for this company
        	doc = employeedocs.get(e);//get the Employee document for this iteration
        	employee = myMongoCompanyFunctions.doctoEmployee(doc);// convert the Employee document into an Employee object
        	employees.add(employee);// add this Employee object to the ArrayList of Employee objects in the sample
		}//e loop


	    
		//This section of the main method generates the expenses for all the employees.
        employeecount = employees.size();// the number of employees objects in the ArrayList of Employee objects in the sample
		for (int ei=0; ei < employeecount; ei++) {// Iterate through each employee in the Array
			employee = employees.get(ei);// the Employee object for this iteration
			ArrayList<Trip> trips = new ArrayList<Trip>();// an ArrayList of Trip elements for storing this employee's trips

			trips = Trips.create(BegDate,EndDate, employee, myMongoCompanyFunctions);// create the trips for this employee during the specified date range
			
			tripcount = trips.size();// the number of trips in the ArrayList of trips
		    expcount=0;//initialize the expense count
		    itemcount=0;//initialize the item count
		    
		    for (int t=0; t< tripcount; t++) {//iterate for each trip t
				trip = trips.get(t);//get the Trip object for this iteration
				 myCalendar.setTime(trip.BeginningDate);// Placeholder for a Calendar object; initialize to the trip's beginning date
				 myCalendar.add(Calendar.DATE, trip.Length);// calculate the end date for this trip
				 paiddate = myCalendar.getTime();// set the Paid Date to the trip's end date
				 
				
				
				//determine the account activities to be charged for this trip
				activitydocs = myMongoCompanyFunctions.getDocsByField(actCollection, "CompanyID", employee.EmployerCompanyID);//load the activitydocs ListArray with this company's account activities.
				activitiescount = activitydocs.size();// the number of account activities for this company
				
				int howmany = myRandom.nextInt(2) +1;// how many account activities to be charged for this trip. A random integer between 1 and 3. 
				// This means for any given trip there can be between 1 to 3 account activities charged.
				
				for (int ia=0; ia < howmany; ia++) {//iterate for each account activity to be charged for this trip
					index = myRandom.nextInt(activitiescount);// a random integer between 0 and the number of elements in the ArrayList of account activity documents
					doc = activitydocs.get(index);// select a random, account activity document from the activitydocs ArrayList.
					activity = myMongoCompanyFunctions.doctoAccountActivity(doc);//convert this document into an AccountActivity object.  
					activities.add(activity);// add the object to the ArrayList of AccountActivity objects
				}//ia loop
				

				//Now, create the expenses for this trip
			    expenses = new ArrayList<Expense>();// initialize ArrayList of Expense objects for this trip
			    items = new ArrayList<Itemization>();// initialize ArrayList of Itemization objects for this trip
			    charges = new ArrayList<AccountActivityCharge>();// initialize ArrayList of account activity charge objects for this trip
		
			    expenses = CreateExpenses.TripExpenses(trip, employee, company);// create the expenses for this trip for this employee and company.
				expcount = expenses.size();// expcount is the number of expenses for this trip.
		
				if (expcount > 0) {// then there is at least one expense for this trip
				    for (int e=0; e< expcount; e++) {// iterate for each expense
					    expense = expenses.get(e);// get the expense for this itereation
					    items = expense.getItems();// get the itemizations for this expense
						itemcount = items.size();// itemcount is how many itemizations there are for this expense
						
						for (int i=0 ; i<itemcount; i++) {// iterate for each itemization
							item = items.get(i);// get the itemization for this iteration
							splits = CustomFunctions.splitMoney(item.ApprovedAmount, 2, howmany);// split the item's Approved Amount evenly among how many account activities that will be charged.
							// splits is an array of double elements where each element represents the charge amount for an account activity to be charged for this itemization.
							
							for (int a=0; a<howmany; a++) {// iterate for each account activity to be charged for this trip
								double chargeamount = splits[a];//get the charge amount from the splits array
								activity = activities.get(a);// get the account activity from the activities ArrayList
								
								//create a new Account Activity Charge object for this account activity						
								charge = new AccountActivityCharge(activity.ID, activity.AccountID, activity.AccountName, activity.ActivityID, activity.ActivityName, 
										expense.getEmployeeDisplayName(), expense.getID() , expense.getExpenseTypeName(), expense.getTransactionDate(), expense.getOriginalAmount(), expense.getOriginalCurrency(), 
										expense.getPostedAmount(), expense.getPostedCurrency(), item.ID, item.ExpenseTypeName, item.Date, item.OriginalAmount, chargeamount, paiddate);

								charges.add(charge);//add the Account Activity Charge to the ArrayList.
							}//a loop
						}//i loop
				    }//e loop
		
				
				/* At this point there is a ArrayList with the trip's expenses and a ArrayList with the trip's account activity charges.
				 * Now, decide how to process these two ArrayLists.  There are these options.
				 * 1. Print to console
				 * 2. Write to MongoDB
				 * 3. Post to Concur Expense
				 */
		
				    
			    for (int e=0; e< expcount; e++) {//iterate for each expense in this trip
				    expense = expenses.get(e);//get the expense for this iteration
				    items = expense.getItems();//get the itemizations for this expense
					itemcount = items.size();//itemcount is how many itemizations there are for this expense
					
					switch (option) {//process it per the selected option
						case 1:
							out.println("Expense Number:" +(e+1));
							out.println("-------------------");
							expense.display();
							break;
						case 2:
							out.println("Expense Number:" +(e+1));
							out.println("-------------------");
							expense.display();
							myMongoExpenseFunctions.addExpense (expense);// add the Expense to the MongoDB
							break;
						case 3:
							//stub for the Concur Expense code
							break;
					}//switch block
			    }//e loop				
				
			    //Now, create the account activity charges for this trip
			    int chargecount = charges.size();// the number of AccountActivityCharge objects in the ArrayList of AccountActivityCharge objects
							
				for (int ch=0; ch < chargecount; ch++) {// iterate for each account activity charge for this trip
					charge = charges.get(ch);//get the account activity charge for this iteration
					
					switch (option) {//process it per the selected option
					case 1:
						out.println("Charge Number:" +(ch+1));
						out.println("-------------------");
						charge.display();
						break;
					case 2:
						BasicDBObject myDoc = charge.getDocument();// convert the AccountActivityCharge object into a document.
						myMongoExpenseFunctions.addDoc(chargeCollection, myDoc);;// add the AccountActivityCharge document to the AccountActivityCharges collection
						break;
					case 3:
						//stub for the Concur Expense code
						break;
					}//switch block	
				}//ch loop
			}//t loop
		}//i loop

	    
	}//main method
	}
}



