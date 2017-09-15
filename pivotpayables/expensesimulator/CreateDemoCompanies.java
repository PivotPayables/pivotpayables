/**
 * 
 */
package com.pivotpayables.expensesimulator;

/**
 * @author John Toman
 * 8/19/15
 * 
 * This program generates a specified number of companies that can be used for the Expense simulation.
 * These are simulated companies that can have employees with expenses and account activities to associate these expenses.
 * 
 * To create company names, it uses two text files, CompanyFirstNames.txt and IndustryBusinessNames.txt, that contain a
 * collection of seed names that are used to create a two-word, company name.
 * CompanyFirstNames are names that are suitable for the first word in the company name.
 * IndustryBusinessNames are names that are suitable for the second word of the company name.
 *
 */
import static java.lang.System.out;

import java.util.regex.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.pivotpayables.concurplatform.Location;

public class CreateDemoCompanies {
	private static Pattern namepattern;
	
	public static void main(String[] args) throws FileNotFoundException {
		/**
		 * Parameters:
		 * args[0] = industry
		 * args[1] = where to store the companies: 1 = console, 2 = MongoDB, 3 = Concur Expense
		 * args[2] = MongoDB server host name
		 * args[3] = MongoDB server port
		 */	
		String industry = "Consulting";
        int StoreWhere = 2;//where to store the locations this program creates: 1 = console, 2 = MongoDB, 3 =mySQL
        

	    
		String host= "localhost";//the MongoDB server host
		int port = 27017;//the MongoDB server port
		MongoDBFunctions myMongoCompanyFunctions = new MongoDBFunctions (host, port, "Company_Data");// create a MongoDB Client for the specified host and port, and get the Company_Data database 


		Scanner myScanner = new Scanner(System.in);
		

		if (args.length == 2) {//there are parameters for Industry and StoreWhere.  Use the default for host and port.
			industry = args[0];	
			try {
				StoreWhere = Integer.parseInt(args[1]);
			} catch (NumberFormatException e) {
				StoreWhere = 1;
			}		
		} else if (args.length == 4){//then there are parameters for Industry, StoreWhere, host, and port
			industry = args[0];	
			try {
				StoreWhere = Integer.parseInt(args[1]);
			} catch (NumberFormatException e) {
				StoreWhere = 2;
			}	
			host = args[2];
			try {
				port = Integer.parseInt(args[3]);
			} catch (NumberFormatException e) {
				port = 27017;
			}	
			
		} else {//use the console to enter for Industry and StoreWhere.  Use the default for host and port.
			out.println("Industry (AEC, Consulting, Legal, or Technology)?");
			industry = myScanner.next();
			out.println("Where do you want to store the companies (1= console, 2 = MongoDB?");
			StoreWhere = myScanner.nextInt();
			myScanner.close();
		}

	
		// get the seed files from disk

		String FirstName [],first;//an Array of String elements for holding the seed names for Company first name.
		String LastName [],last;//an Array of String elements for holding the seed names for Company last name.
		String LegalName=null, DBAName=null, Domain=null, Industry=null, PostingCurrency=null;
		Location HQ;// placeholder for the HQ location
		


		Scanner FirstNames = new Scanner(new File("CompanyFirstNames.txt"));//open the file of seed names for company first names.
		Scanner LastNames = new Scanner(new File("IndustryBusinessNames.txt"));//open the file of seed names for company last names, which are industry business names.
		
		//load FirstName string array with names from the CompanyNames.txt file
		FirstName = new String[1000];//holds up to 1,000 seed names
		int FirstCount = 0;
		namepattern = initnamepattern();//the regular expression used to delimit text files.

		

		FirstNames.useDelimiter(namepattern);//parse the first names text file,CompanyFirstNames.txt, using the specified Pattern, namepattern.
		LastNames.useDelimiter(namepattern);//parse the last names text file, IndustryBusinessNames.txt, using the specified Pattern, namepattern.

		int index=0;//initialize the index  
		while (FirstNames.hasNext()==true) {//iterate through the first names file
			first = FirstNames.next();//get the first name for this iteration
			FirstName[index]=first;//store it in the Array of first name elements
			++index;//next iteration
		}//while block
		
		FirstCount=index-1;//the number of elements in the Array with a first name.
		
		//load LastName string array with names from the BusinessNames.txt file
		LastName = new String[100];
		int LastCount = 0;
		
		index=0;//initialize the index
		while (LastNames.hasNext()==true) {//iterate through the last names file
			last = LastNames.next();//get the last name for this iteration
			LastName[index] = last;//store it in the Array of last name elements
				++index;//next iteration
		}// while block
		LastCount=index-1;//the number of elements in the Array with a last name.


		int FirstIndex=0, LastIndex=0;
		Random RandomIndex = new Random();


		while (!(industry.equals(Industry))) { // loop until the Industry for the Company matches the specified industry
			FirstIndex = RandomIndex.nextInt(FirstCount);//select a random element from the FirstName array
			first = FirstName[FirstIndex];//get the name for this element, assign it to first
			LastIndex = RandomIndex.nextInt (LastCount); //select a random element from the LastName array
			last = LastName[LastIndex];//get the name for this element, assign it to last
			
			LegalName = first + " " + last;//compose the company's name
			DBAName = LegalName;
			Domain = first+last + ".com";//compose the company's domain
			Industry = whatIndustry(last);//based on its last name, determine the company's industry
		}// while loop

		HQ = myMongoCompanyFunctions.getRandomLocation();//select a random location as the company's HQ location

		
		if (HQ.getCountryCode().equals("CA")) {//then it's a Canadian company
			PostingCurrency = "CAD";
		} else {//then its an American company
			PostingCurrency = "USD";
		}// if block
		
		
		Company myCompany  = new Company(LegalName, DBAName, Domain, Industry, HQ, PostingCurrency);
		FirstNames.close();
		LastNames.close();
		
		switch (StoreWhere) {
		case 1: //print to console
			out.println("----------------------");
			myCompany.display();	
			break;
		case 2: //MongoDB
			out.println("----------------------");
			myCompany.display();
			BasicDBObject myDoc = myCompany.getDocument();//convert the Company object into a MongoDB document
			DBCollection myCollection = myMongoCompanyFunctions.setCollection("Companies");
			myMongoCompanyFunctions.addDoc(myCollection, myDoc);
			break;
		case 3://MySQL
			//Stub for code to store the company to the Company table in MySQL
			break;
		}// switch block
		
		//Next, create employees for the demo company
		args = new String[3];// initiate the Array args for CreateEmployees.main
		args[0] = "4";// the number of employees to create
		args[1] = Integer.toString(StoreWhere);
		args[2] = myCompany.getID();// create employees for this Company
		CreateEmployees.main(args);
		
		// Next, create account activities for the demo company
		args = new String[4];// initiate the Array args for CreateAccountActivities.main
		args[0] = myCompany.getID();// create account activities for this Company
		args[1] = Integer.toString(StoreWhere);
		args[2] = "100"; // number of accounts to create for the company
		args[3] = "10";//  maximum number of activities for any account
		CreateAccountActivities.main(args);
	

	}//end main method

	private static Pattern initnamepattern () {//creates the regular expression to use as a delimiter
		if (namepattern == null) {//then the Pattern doesn't exit; create one
			String regex = "\\,\\s";//the regular expression for the delimiter: ,space
			namepattern = Pattern.compile(regex);//create a Pattern using this regular expression
		} 
		return namepattern;
	}
	 static String whatIndustry (String last) {//returns an industry based on the company's last name, which is an industry business name
		 //presently, Industry can be only Legal, AEC, Technology, or Consulting.  Later, this will be refactored to support a wider spectrum of industries.
		 
			Random RandomIndex = new Random();
			int Choice;
			String industry;
			if ( (last.equals("Law")) || (last.equals("Legal"))) {//then this is a law firm
				industry = "Legal";
			} else if ((last.equals("Partners")) || (last.equals("Associates")) || (last.equals("International")) || (last.equals("Group")) || 
					(last.equals("Alliance")) ) {//then this can be either a law, an AEC , or a consulting firm
				Choice = RandomIndex.nextInt(2);//a random integer between 0 and 2; determines whether the industry is Legal, AEC, or Consulting
				if (Choice == 1) {
					industry = "Legal";
				} else if (Choice == 2){
					industry = "AEC";
				} else {
					industry = "Consulting";
				}// if Choice block
			} else if ((last.equals("Company")) || (last.equals("Worldwide")))  {
				industry = "Technology";
			} else {
				industry = "Consulting";
			}
			return industry;

	 		}// if last.equals block
	}

