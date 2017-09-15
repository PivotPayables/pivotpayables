package com.pivotpayables.expensesimulator;
/**
 * 
 */

/**
 * @author John Toman
 * 2/42015
 * This creates accounts and activities for these accounts for a specified company.
 * 
 * To create account names, it uses two text files, CompanyFirstNames.txt and IndustryBusinessNames.txt, that contain a
 * collection of seed names that are used to create a two-word, account name.
 * CompanyFirstNames are names that are suitable for the first word in the account name.
 * IndustryBusinessNames are names that are suitable for the second word of the account name.
 * 
 *
 */
import static java.lang.System.out;

import java.util.regex.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;

import com.mongodb.*;
import com.pivotpayables.prime.AccountActivity;

public class CreateAccountActivities {


		private static Pattern namepattern;
		private static String companyname;


		public static void main(String[] args) throws FileNotFoundException {
			/**
			 * Parameters:
			 * args[0] = the ID of the company to create account activities
			 * args[1] = where to store the account activities: 1 = console, 2 = MongoDB, or 3) Concur
			 * args[2] = number of accounts to create for a company
			 * args[3] = maximum number of activities for any account
			 */	
			String host ="localhost";//MongoDB server host
			int port = 27017;//MongoDB server port
			
			ArrayList<DBObject> docs = new ArrayList<DBObject>();//an ArrayList that will be used to store documents for every company in the Companies collection
			
			String coid = "0";//determines whether to create account activities for all companies, or for only one specified company. "0" means "all companies".  Otherwise, must
			//be a valid ID for a Company document in the Companies collections. default is "0", or create account activities for all companies
			int StoreWhere=1;//where to store the account activities: default is 1 = console
			int AccountCount=100;//number of accounts to create for a company; default is 100
			int MaxActivities=10;//maximum number of activities for any account; default is 10




			try {	
				if (args.length == 2) {//then there are parameters for the company to create account activities and where to store these.  Use defaults AccountCount and MaxActivities
					coid = args[0];	
					try {
						StoreWhere = Integer.parseInt(args[1]);
					} catch (NumberFormatException e) {
						StoreWhere = 1;
					}		
				} else if (args.length== 4) {//then there are parameters for the company to create account activities, where to store these, number of accounts, and max number of activities 
					coid = args[0];	
					try {
						StoreWhere = Integer.parseInt(args[1]);
					} catch (NumberFormatException e) {
						StoreWhere = 1;
					}	
					try {
						AccountCount = Integer.parseInt(args[2]);
					} catch (NumberFormatException e) {
						AccountCount = 100;
					}
					try {
					MaxActivities = Integer.parseInt(args[3]);
					} catch (NumberFormatException e) {
					MaxActivities = 10;
					}
					
				} else {//then use the defaults
					StoreWhere=2;
					AccountCount=100;
					MaxActivities=10;
				}//if block
			} catch (Exception e) {
	        		out.println(e);
			}//try block

			
			ArrayList<String> FirstName = new ArrayList<String>();//an ArrayList of String elements where each element represents a first name
			String first;//placeholder for the first name
			ArrayList<String> LastName = new ArrayList<String>();//an ArrayList of String elements where each element represents a last name
			String last;//placeholder for the last name

			Random myRandom = new Random();
			
			// open the seed files from disk
			Scanner FirstNames = new Scanner(new File("CompanyFirstNames.txt"));// open the file for seed names for account first name
			Scanner LastNames = new Scanner(new File("AccountLastNames.txt"));// open the file for seed names for account last name	
			
			namepattern = initnamepattern();// get the Pattern to use as the delimiter to parse the seed files
			FirstNames.useDelimiter(namepattern);// use this Pattern as the delimiter to parse the CompanyFirstNames text file into first names
			LastNames.useDelimiter(namepattern);// use this Pattern as the delimiter to parse the AccountLastNames text file into last names

			//load FirstName ArrayList with names from the CompanyFirstNames.txt file
			while (FirstNames.hasNext()==true) {// iterate through the first names file
				first = FirstNames.next();//get the first name for this iteration
				FirstName.add(first);// store it in the ArrayList of first name elements
			}//while block
			
			
			//load LastName ArrayList with names from the AccountLastNames.txt file
			while (LastNames.hasNext()==true) {// iterate through the last names file
				last = LastNames.next();// get the last name for this iteration
				LastName.add(last);// store it in the ArrayList of last name elements
			}

			
			// Connect to the MongoDB
	        MongoDBFunctions myMongoFunctions = new MongoDBFunctions (host, port, "Company_Data", "Companies");//create a MongoDB Client to the Companies collection
			DBCollection comCollection = myMongoFunctions.getCollection();//get the Companies collection
			

			docs = myMongoFunctions.getDocs(comCollection);// create an ArrayList with documents for every company in the Companies collection
			int companycount = docs.size();// the number of companies in the Companies collection
			
			
			//create account activities for either:  all companies, or one specified company
			if (coid == "0") {// then create account activities for all companies
				for (int j=0; j < companycount; j++) {// iterate for each Company document in the ArrayList
					Company myCompany = myMongoFunctions.doctoCompany(docs.get(j));//get the Company object for this iteration
					companyname = myCompany.DBAName;// get name of the Company for this iteration
			    	AccountCount = myRandom.nextInt(40) + 10;// the number of accounts to create for this company. A random integer between 10 and 49
				    MaxActivities = myRandom.nextInt(10) + 1;// the maximum number of activities to create for an account.  A random integer between 1 and 10
					CreateActivities(myCompany, AccountCount, MaxActivities, FirstName, LastName, docs, StoreWhere, myMongoFunctions);//create and store the account activity
				}//j loop

			} else {//create account activities only for the specified Company, coid
				DBObject doc = myMongoFunctions.getDoc(comCollection, coid);// get the Company document for this Company
				Company myCompany = myMongoFunctions.doctoCompany(doc);// convert this document into a Company object
				companyname = myCompany.DBAName;// get name of the specified Company
		    	AccountCount = 100;// the number of accounts to create for this company. 
			    MaxActivities = 10;// the maximum number of activities to create for an account.  A random integer between 5 and 10
				CreateActivities(myCompany, AccountCount, MaxActivities, FirstName, LastName, docs, StoreWhere, myMongoFunctions);//create and store the account activity

			}//if block

		}//main method
		private static Pattern initnamepattern () {//creates the Pattern, namepattern.  namepattern is the Patter to use as the delimiter to parse the seed name, text files
			if (namepattern == null) {
				String regex = "\\,\\s";//the regular expression for the Pattern
				namepattern = Pattern.compile(regex);//create the Pattern using this regular expression
			} 
			return namepattern;
		}
		private static String whatType (String Industry) {//determines the type of activity to use in composing the Activity Name
			
			String type;//the type of activity.  Presently, there are only three types: Matter, Project, and Engagement.  Eventually when there are more industries, we will add more types for these added industries.
			if (Industry.equals("Legal")) {//then use the type for the legal industry, which is "Matter".
				type = "Matter";
			} else if (Industry.equals("AEC") || (Industry.equals("Technology"))) {
				type = "Project";
			} else {//then use the type for the consulting industry, which is "Engagement".
				type = "Engagement";
			}
			return type;
		}
		private static String randomCompanyName (ArrayList<DBObject> docs) {//creates a random name that is used in composing the Activity Name
			Random myRandom = new Random();
			int doccount = docs.size();//the number of documents in the ArrayList of Company document elements
			String coname=companyname;// initialize the defendant name
			while ((companyname.equals(coname))){
				/*  The while loop selects a random company from the universe of all companies. 
				 *  It ensures the its name isn't the same as the name of the Company an account activity is associated.
				 */
				
				int index = myRandom.nextInt(doccount);// a random integer between 0 and the number of Company documents in the ArrayList
				DBObject myDoc = docs.get(index);//select a random company from the ArrayList
				coname = myDoc.get("DBAName").toString();// get the company's doing business as name
			}
			
			return coname;// return the company name.
		}
		private static String randomEngagementName (ArrayList<DBObject> docs) {//creates a random engagement name that is used in composing the Activity Name for a consulting company
			Random myRandom = new Random();
			String phrase = "";//the phrase to use in composing the engagement name
			
			int index = myRandom.nextInt(5) + 1;//a random integer between 1 and 5.  Used to determine what phrase to use in composing the engagement name.
			switch (index) {//each case represents a different version of the phrase to use when composing the engagement name
			case 1: 
				phrase = "Analyzing projects for ";
				break;
			case 2: 
				phrase = "Planning for ";
				break;
			case 3:
				phrase = "Determining the situation at ";
				break;
			case 4:
				phrase = "Researching concepts for ";
				break;
			case 5:
				phrase = "Collaborating with ";
				break;
			}
			return phrase;// Compose the engagement name by combining the phrase with the account name.
		}
		private static void CreateActivities (Company comp, int accounts, int activities, ArrayList<String> FirstName, ArrayList<String> LastName, ArrayList<DBObject> Docs, 
				int where, MongoDBFunctions functions) {
			
			/* creates an account activity and stores it in the specified location, where (StoreWhere)
			 * comp is the Company to create the accounts for
			 * account is the number of accounts to create
			 * activities is the maximum number of activities to create for any one account
			 * FirstName is an ArrayList with elements to use for the account's first name
			 * LastName is an ArrayList with elements to use for the account's last (second) name
			 * Docs is an ArrayList with elements that a Company documents for all the companies in the Companies collection
			 */


			String CompanyID = comp.ID;
			String ActivityType = whatType(comp.Industry);// get the type of activity for the company's industry
			String first, last;
			String AccountName, AccountID, ActivityName, ActivityID;
			Random myRandom = new Random();
			activities -= 5;// reduce the maximum number of activities to create by 5

		for (int j=0; j < accounts; j++) {// iterate for each account to be created
			AccountName = companyname;// initialize the account name so it initially matches the name of the company it belongs

			
			while ((companyname.equals(AccountName))){
				/*  The while loop selects a random company from the universe of all companies as the Account. 
				 *  It ensures the Account name isn't the same as the name of the Company this account belongs.
				 *  The loop continues until the account name is different than the company name.
				 */

				int FirstIndex=0, LastIndex=0;
				int fcount = FirstName.size();// the number of elements in the FirstName ArrayList
				FirstIndex = myRandom.nextInt(fcount);// select a random element from the FirstName array
				first = FirstName.get(FirstIndex);// get the first name
				
				int lcount = LastName.size();// the number of elements in the LastName ArrayList
				LastIndex = myRandom.nextInt(lcount); // select a random element from the LastName array
				last = LastName.get(LastIndex);// get the last name
				
				AccountName = first + " " + last;// compose the account name
			}// while companyname = account name loop
			
			AccountID = GUID.getGUID(5);// create its GUID
			
			//generate the activities for this account


			int activitiescount = myRandom.nextInt(activities) + 5;// the number of activities to create for this account.  An integer between 5 and the maximum number of activities

			if (ActivityType.equals("Project")) {
				activitiescount = 1;// create only one activity
			}
			for (int k = 0; k < activitiescount; k++) {//iterate for each activity to be created

				if (ActivityType.equals("Matter")) {//then compose an Activity Name for a law firm
					String defendantcompanyname = AccountName; // initialize the activity company name to match the account's name
					while (AccountName.equals(defendantcompanyname)) {
						/*  The while loop gets an defendant company name that doesn't match the name of the company the activity is being created. 
						 *  It ensures the defendant's company's name isn't the same as the name of the account the activity is associated.
						 *  The loop continues until the defendant's company name is different than the account name.
						 *  It exits the loop with distinct names for company name, account name, and defendant company name
						 */
						defendantcompanyname = randomCompanyName(Docs); // get a activity company name
					}
					
					ActivityName = "Case - " + AccountName + " v. " + defendantcompanyname;// compose the Activity Name: Case - <Account Name> v. <Defendant Name>
				} else if (ActivityType.equals("Project")) {
					// then compose an Activity Name for an AEC or a Technology Company

					if (comp.Industry.equals("AEC")) {// it's an architect, engineer, or construction company
						ActivityName = "Project - New headquarters for " + AccountName;// compose the Activity Name for an AEC company
					} else {
						ActivityName = "Project - Implement " + AccountName;// compose the Activity Name for an Technology company
					}
				
				} else {//then compose an Activity Name for a consulting company
					ActivityName = "Engagement - " + randomEngagementName(Docs) + AccountName;//compose the Activity Name
				}
				int blocks = myRandom.nextInt(3) + 1;//number blocks in the GUID: random integer between 1 and 4
				ActivityID = GUID.getGUID(blocks);//create its GUID
				
				AccountActivity myActivity = new AccountActivity(CompanyID, AccountID, AccountName, ActivityID, ActivityName);//create the Account Activity
					
					
				switch (where) {
				case 1: //print to console
					out.println("----------------------");
					myActivity.display();	
					break;
				case 2: //MongoDB
					out.println("----------------------");
					myActivity.display();	
					BasicDBObject myDoc = myActivity.getDocument();//convert the AccountActivity object into an AccountActivity document

					DBCollection myCollection = functions.setCollection("AccountActivities");
					functions.addDoc(myCollection, myDoc);//store the AccountActivity document in the AccountActivities collections
					break;
				case 3://MySQL
					//Stub for code to store the company to the Company table in MySQL
					break;
				}//end switch
			}//end k loop
		}//end j loop
		}//end CreateActivities method

	}