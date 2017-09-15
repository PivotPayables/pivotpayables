/**
 * 
 */
package com.pivotpayables.test;

import static java.lang.System.out;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.pivotpayables.expensesimulator.MongoDBFunctions;
import com.pivotpayables.prime.AccountActivityCharge;
import com.pivotpayables.prime.GetBillingStatementZip;


/**
 * @author John
 *
 */
public class TestWorkbook {

	/**
	 * @param args
	 * @throws IOException 
	 */
	private static Path currentRelativePath = Paths.get("");// find the current relative path
	private static String sheetfilepath = (currentRelativePath.toAbsolutePath().toString()) + "/SheetFiles/";// construct the absolute path to the SheetFiles directory
	private static final String COMMA_DELIMITER = ",";
	private static final String NEW_LINE_SEPARATOR = "\n";
	private static final String FILE_HEADER = "Account Name, Charge Count";


	private static ArrayList<DBObject> docs = new ArrayList<DBObject>(); //an ArrayList of AccountActivityCharge document elements
    private static DBObject doc = null;//placeholder for a AccountActivityCharge document
    
    private static String host= "localhost";//the MongoDB server host
    private static int port = 27017;//the MongoDB server port
	
	// Connect to  the Expense_Data database, and then get the Expenses collection
    private static MongoDBFunctions myMongoExpenseFunctions = new MongoDBFunctions (host, port, "Expense_Data");// create a MongoDB Client for the specified host and port, and get the Expense_Data database
    private static DBCollection expCollection = myMongoExpenseFunctions.setCollection("Expenses");// get the Expenses collection
    private static DBCollection chargeCollection = myMongoExpenseFunctions.setCollection("AccountActivityCharges");// get the Itemizations collections
	
    private static ArrayList<AccountActivityCharge> charges = new ArrayList<AccountActivityCharge>();
    private static AccountActivityCharge charge;// placeholder for an AccountActivityCharge object
    private static String currentaccount=null;
    private static String currentactivity=null;
    
    private static ArrayList<String> accounts = new ArrayList<String>();
    private static ArrayList<String> activities = new ArrayList<String>();
    private static Boolean match = false;
    
    public static void main(String[] args) throws Exception {
	    docs = myMongoExpenseFunctions.getDocs(chargeCollection);//get all the documents from the AccountActivityCharges collection
	    int count = docs.size(); // the number of AccountActivityCharge documents in the ArrayList
	    DBObject doc = null;//placeholder for a AccountActivityCharge document
	    /*
		String filename = "Charges for Yost" + ".CSV";// set the filename	
		File file = new File(sheetfilepath + filename);
		FileWriter fileWriter = new FileWriter(file);
	
		
		fileWriter.append(FILE_HEADER.toString());//Write the CSV file header
		fileWriter.append(NEW_LINE_SEPARATOR);//Add a new line separator after the header
		*/
	    
    	for (int i=0; i<count; i++) {// iterate for each AccountActivityCharge
    		doc = docs.get(i);// get the AccountActivityCharge document for this iteration
    		//out.println("Charge " + (i+1));
    		charge = myMongoExpenseFunctions.doctoCharge(doc);// convert this document into an AccountActivityCharge  object
    		//out.println("-------------------------------------------------");
    		//out.println();

    		//charge.display();
    		match = false;// initialize the match flag to false
    		for (int j= 0; j<accounts.size(); j++) {// iterate through each account in the accounts ArrayList
    			if (charge.getAccountName().equals(accounts.get(j))) {// then this account already exists in accounts
    				match = true;// so, set the match flag to true to indicate this account is already in accounts and doesn't need to be added
    				j =accounts.size();// so terminate this j block by setting j to the accounts size
    			}// if block
    		}// for j loop
    		if (!match) {// then this account isn't in accounts, it is a new account
    			accounts.add(charge.getAccountName());// so add it to the ArrayList for accounts
    		}// if block
    		
    		
    		match = false;
    		for (int j= 0; j<activities.size(); j++) {// iterate through each activity in the activities ArrayList
    			if (charge.getActivityName().equals(activities.get(j))) {// then this activity already exists
    				match = true;// so, set the match flag to true to indicate this activity is already in activities and doesn't need to be added
    				j =activities.size();// so terminate this j block
    			}// if block
    		}// for j loop
    		if (!match) {// then new activity
    			activities.add(charge.getActivityName());// so add it to the ArrayList for activities
    		}// if block
    	}// for i loop
    	
    	/*
    	for (int i=0; i<accounts.size(); i++) {
    		out.println("Account " + (i+1) + " " + accounts.get(i));
    	}
    	for (int i=0; i<activities.size(); i++) {
    		out.println("Activity " + (i+1) + " " + activities.get(i));
    	}
    	
    	 */
    	
    	for (int i=0; i<accounts.size(); i++) {// iterate for each account name 
			docs = myMongoExpenseFunctions.getDocsByField(chargeCollection, "AccountName", accounts.get(i));// get the charge documents for this account name

    		
    		for (int j=0;j<activities.size() ; j++){// Iterate for each activity name 
    			currentactivity =  activities.get(j);// get the activity for this iteration
    			for (int k=0; k<docs.size(); k++) {// Iterate through each charge document for this account name
    	    		doc = docs.get(k);// get the AccountActivityCharge document for this iteration
    	    		charge = myMongoExpenseFunctions.doctoCharge(doc);// convert this document into an AccountActivityCharge  object
    	    		
    	    		if ( charge.getActivityName().equals(currentactivity)){// then this charge is for the current activity
            			charges.add(charge);// so add it to the charges ArrayList
            			//charge.display();

            		}// if block
    	    		
    			}// for k loop
    			
    			if (charges.size()>0){// then there is at least one charge for this billing statement
					
            		out.println(charge.getAccountName() + " - " + charge.getActivityName());
        		    out.println("Checkpoint: Size " +charges.size());
    				GetBillingStatementZip.createFile(charges);// so create a billing statement file

	    		    charges = new ArrayList<AccountActivityCharge>();// initialize the charges ArrayList so its ready for the next activity
    			}// if block
    		}// for j loop	
    		
    	}// i loop
    	
    	/*
		try {
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Error while flushing/closing fileWriter !!!");
            e.printStackTrace();
        }
        */

    }
}
