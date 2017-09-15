/**
 * 
 */
package com.pivotpayables.test;

/**
 * @author John Toman
 * 8/19/15
 * 
 * This program generates a specified company in the MongoDB to use for demonstrations or testing.
 * 
 *
 */
import static java.lang.System.out;

import java.util.regex.*;

import java.util.Date;
import java.util.HashMap;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;


import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.pivotpayables.concurplatform.Location;
import com.pivotpayables.concurplatform.OAuthToken;
import com.pivotpayables.expensesimulator.Company;
import com.pivotpayables.expensesimulator.Employee;
import com.pivotpayables.expensesimulator.GUID;
import com.pivotpayables.expensesimulator.MongoDBFunctions;
import com.pivotpayables.nexus.BatchDefinition;
import com.pivotpayables.prime.FieldContext;

public class CreateDemoCompany {

	private static String LegalName;//compose the company's name
	private static String Domain;//compose the company's domain
	private static String Token;//OAuth access token
	private static String Industry;
	private static String city;

	
	private static String DBAName;
	private static Location HQ;// placeholder for the HQ location
	private static String PostingCurrency;
	private static String firstname = "Admin";// first name of the user (employee) who the OAuth Token is associated
	private static String lastname = "User";// last name of the user
	private static String login;// Pivot Payables user account login for this user
	private static String coid;// placeholder for
	private static String Instance = "https://www.concursolutions.com/";
	private static SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss a");

	private static String host= "localhost";//the MongoDB server host
	private static int port = 27017;//the MongoDB server port
	private static MongoDBFunctions myMongoCompanyFunctions = new MongoDBFunctions (host, port, "Company_Data");// create a MongoDB Client for the specified host and port, and get the Company_Data database 
	private static DBCollection comCollection = myMongoCompanyFunctions.setCollection("Companies");// get the Companies collection
	private static DBCollection empCollection = myMongoCompanyFunctions.setCollection("Employees");// get the Locations collection
	private static DBCollection locCollection = myMongoCompanyFunctions.setCollection("Locations");// get the Locations collection
	private static DBCollection tokenCollection = myMongoCompanyFunctions.setCollection("Tokens");// get the Tokens collection
	
	
	private static BasicDBObject myDoc;//placeholder for a Basic DBObject document
	private static Company company  = new Company();// placeholder for a Company object
	private static Employee employee = new Employee ();// placeholder for an Employee object
	private static OAuthToken token = new OAuthToken();
	private static Location location = new Location();// placeholder for a Location object
	private static String EmployeeID;
	private static String CompanyID;

	public static void main(String[] args) throws FileNotFoundException, ParseException {
		// Begin by creating default locations
		CreateLocations.main(args);// create default locations
		
		// Create Apex Consulting
		
		LegalName = "Apex Consulting";//compose the company's name
		DBAName = LegalName;
		Domain = "apexconsulting.com";//compose the company's domain
		Token = "0_NJTIPIpaJTl1XOyn9SmfFFrK0=";// OAuth access token
		Industry = "Consulting";
		city = "Bellevue";
		
		HQ = location.getCity(locCollection, city);
		HQ.display();
		if (HQ.getCountryCode().equals("CA")) {//then it's a Canadian company
			PostingCurrency = "CAD";
		} else if (HQ.getCountryCode().equals("GB")) {//then it's a United Kingdom company
			PostingCurrency = "GBP";
		} else if (HQ.getCountryCode().equals("AU")) {//then it's a Australian company
			PostingCurrency = "AUD";
		}else {//then its an American company
			PostingCurrency = "USD";
		}// if block
	
		
		company.setID(GUID.getGUID(4));
		company.setDBAName(DBAName);
		company.setLegalName(LegalName);
		company.setDomain(Domain);
		company.setIndustry(Industry);
		company.setHQ(HQ);
		company.setPostingCurrency(PostingCurrency);

		
		coid = company.getID();
		out.println("----------------------");
		company.display();
		myDoc = company.getDocument();//convert the Company object into a BasicDBObject document
		myMongoCompanyFunctions.addDoc(comCollection, myDoc);
		
		login="Support@" + Domain;
		
		employee.setID(GUID.getGUID(4));
		employee.setDisplayName(firstname + " "+ lastname);
		employee.setEmployerCompanyID(coid);
		employee.setEmployerCompanyName(LegalName);
		employee.setHome(HQ);
		employee.setLastName(lastname);
		employee.setFirstName(firstname);
		employee.setLoginID(login);

		out.println("----------------------");
		employee.display();
		myDoc = employee.getDocument();// convert this Employee object into a BasicDBObject
		myMongoCompanyFunctions.addDoc(empCollection, myDoc);// add this BasicDBObject to the MongoDB
		
		String strDate = "11/21/2017 04:58:55 AM";// assign the Expriration_Date to this "String date"
		Date ExpirationDate = sdf.parse(strDate);// parse the "String date" into a Date object using the SimpleDateFormat object sdf
		

		EmployeeID = employee.getID();// assign the ID for this Employee
		CompanyID = employee.getEmployerCompanyID();// assign the Company ID for this employee
		
		token.setID(GUID.getGUID(4));
		token.setInstanceURL(Instance);
		token.setToken(Token);
		token.setExpirationDate(ExpirationDate);
		token.setEmployeeID(EmployeeID);
		token.setCompanyID(CompanyID);

		out.println("----------------------");
		token.display();
		myDoc = token.getDocument();// convert the OAuthToken object into a BasicDBObject document
		myMongoCompanyFunctions.addDoc(tokenCollection, myDoc);// add the BasicDBObject to the Tokens collection
		
		
		
	

	}//end main method
}
