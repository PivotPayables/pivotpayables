
package com.pivotpayables.expensesimulator;
/**
 * @author John Toman
 * 4/19/2015
 * 
 * This program creates a set of Account->Activity list items for specified Concur entity
 *
 */
import static java.lang.System.out;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Pattern;

import com.pivotpayables.concurplatform.Expenses;
import com.pivotpayables.concurplatform.ListItem;



public class CreateAccountActivityListItems {
	private static String companyname;
	private static String industry;
	private static String key;
	private static String list;
	private static int accounts;
	private static int activities=3;
	
	private static Pattern namepattern;
	private static ArrayList<String> FirstName = new ArrayList<String>();//an ArrayList of String elements where each element represents a first name
	private static String first;//placeholder for the first name
	private static ArrayList<String> LastName = new ArrayList<String>();//an ArrayList of String elements where each element represents a last name
	private static String last;//placeholder for the last name
	private static Random myRandom = new Random();
	private static ListItem item = new ListItem();
	private static Expenses e = new Expenses();

	
	/**
	 * Parameters:
	 * args[0] = the name of the Company to create the account activity list items
	 * args[1] = the company's industry: AEC, Consulting, Legal, or Technology
	 * args[2] = the OAuth token for a Concur user with the WS Admin role
	 * args[3] = the ID for the List to add the list items
	 * args[4] = number of accounts to create for a company
	 * args[5] = maximum number of activities for any account
	 * 
	 */	
	public static void main(String[] args) throws FileNotFoundException {

		Scanner myScanner = new Scanner(System.in);
		
		try {	
			if (args.length == 6) {//there are parameters for key, user, and password
				companyname= args[0];
				industry = args[1];
				key = args[2];
				list = args[3];
				try {
					accounts = Integer.parseInt(args[4]);
				} catch (NumberFormatException e) {
					accounts = 20;
				}
				try {
					activities = Integer.parseInt(args[5]);
				} catch (NumberFormatException e) {
					activities = 10;
				}
				
			} else {//use the console to enter the key, user name, and password
				out.println("What is name of the company?");
				companyname = myScanner.nextLine();

				out.println("What industry: AEC, Consulting, Legal, or Technology?");
				industry = myScanner.next();
				
				out.println("What is the token for the Concur User with the WS Admin role?");
				key = myScanner.next();

				
				out.println("What is the ID for the List to add the items?");
				list = myScanner.next();

				
				out.println("How many accounts?");
				accounts = myScanner.nextInt();


				
				myScanner.close();
			}
		} catch (Exception e) {
        		out.println(e);
		}
		
		
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
		FirstNames.close();
		
		
		//load LastName ArrayList with names from the AccountLastNames.txt file
		while (LastNames.hasNext()==true) {// iterate through the last names file
			last = LastNames.next();// get the last name for this iteration
			LastName.add(last);// store it in the ArrayList of last name elements
		}
		LastNames.close();
		CreateActivities();


	}
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
	private static String randomName (String coname) {
		// creates a random name used in composing either an Account Name or an Activity Name
		// when creating an Account Name, coname is the name of the company. It ensures the Account Name isn't the Company Name.
		// when creating an Activity Name, coname is the name of the Account. It ensures the Activity Name isn't the Account Name.
		String name=coname;
		// initialize the name so it matches the coname
		// This ensures the while companyname.equals(name) loop iterates at least once
		while ((coname.equals(name))){
			/*  The while loop selects a random name. 
			 *  It ensures the its name isn't the same as the name of the Company
			 */
			int FirstIndex=0, LastIndex=0;
			int fcount = FirstName.size();// the number of elements in the FirstName ArrayList
			FirstIndex = myRandom.nextInt(fcount);// select a random element from the FirstName array
			first = FirstName.get(FirstIndex);// get the first name
			
			int lcount = LastName.size();// the number of elements in the LastName ArrayList
			LastIndex = myRandom.nextInt(lcount); // select a random element from the LastName array
			last = LastName.get(LastIndex);// get the last name
			
			name = first + " " + last;// compose the name
		}
		
		return name;// return the company name.
	}
	private static String randomEngagementName () {//creates a random engagement name that is used in composing the Activity Name for a consulting company

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
	private static void CreateActivities () {// creates account activities, stores them as List Item
		
		String ActivityType = whatType(industry);// get the type of activity for the company's industry
		String AccountName, AccountCode, ActivityName, ActivityCode;

	for (int j=0; j < accounts; j++) {// iterate for each account to be created
		AccountName = randomName(companyname);// generate a random name for the Account
		int blocks = myRandom.nextInt(3) + 1;//number blocks in the GUID: random integer between 1 and 4
		AccountCode = GUID.getGUID(blocks);//create its GUID
		item = new ListItem();// initialize the item for the Account
		// add list item for the Account
		item.setListID(list);
		item.setName(AccountName);
		item.setLevel1Code(AccountCode);
		e.addListItem(item, key);

		//generate the activities for this account
		int activitiescount = myRandom.nextInt(activities)+1;// the number of activities to create for this account.  An integer between 1 and the maximum number of activities

		if (ActivityType.equals("Project")) {
			activitiescount = 1;// create only one activity
		}
		for (int k = 0; k < activitiescount; k++) {//iterate for each activity to be created

			if (ActivityType.equals("Matter")) {//then compose an Activity Name for a law firm
				String defendantname = randomName(AccountName); // create a defendant name
	
				ActivityName = "Case - " + AccountName + " v. " + defendantname;// compose the Activity Name: Case - <Account Name> v. <Defendant Name>
			} else if (ActivityType.equals("Project")) {
				// then compose an Activity Name for an AEC or a Technology Company

				if (industry.equals("AEC")) {// it's an architect, engineer, or construction company
					ActivityName = "Project - New headquarters for " + AccountName;// compose the Activity Name for an AEC company
				} else {
					ActivityName = "Project - Implement " + AccountName;// compose the Activity Name for an Technology company
				}
			
			} else {//then compose an Activity Name for a consulting company
				ActivityName = "Engagement - " + randomEngagementName() + AccountName;//compose the Activity Name
			}
			blocks = myRandom.nextInt(3) + 1;//number blocks in the GUID: random integer between 1 and 4
			ActivityCode = GUID.getGUID(blocks);//create its GUID
			
			// add list item for the Activity
			item = new ListItem();// initialize the item for the Account
			item.setListID(list);
			item.setName(ActivityName);
			item.setLevel1Code(AccountCode);
			item.setLevel2Code(ActivityCode);
			e.addListItem(item, key);
		}//end k loop
	}//end j loop
	}//end CreateActivities method
}
