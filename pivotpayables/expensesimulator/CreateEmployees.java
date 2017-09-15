package com.pivotpayables.expensesimulator;
/**
 * 
 */

/**
 * @author John Toman
 * 2/4/15
 * This program generates a specified number of employees for a specified company.
 * 
 * To create employee names, it uses two text files, FirstNames.txt and LastNames.txt, that contain a
 * collection of seed names that are used to create an employee name.
 * 
 * It can create employees for a specified CompanyID or "coid".  Or, it can create employees for all Companies by using the default coid = 0;
 */
import static java.lang.System.out;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.pivotpayables.concurplatform.Location;




public class CreateEmployees {
	private static String firstname = "Amanda";
	private static String lastname = "Welk";
	private static String login ="amanda@apexconsultinggroup.com";
	private static String coid = "PRBQ3833MMRY9500ACAD";
	private static String coname = "Apex Consulting Group";
	

	/**
	 * @param args
	 * arg[0] is how  many employees to create.
	 * arg[1] is where to store the employees: 1) console, 2) MongoDB
	 * arg[2] is the Employer Company ID.
	 * 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		int EmployeeCount = 1;//set default
		int StoreWhere=2;//set default
		
		
	    ArrayList<DBObject> docs = new ArrayList<DBObject>();//an ArrayList of document elements    
	    DBObject doc = null;//placeholder for a Company document
	    
		String host= "localhost";//the MongoDB server host
		int port = 27017;//the MongoDB server port
		MongoDBFunctions myMongoCompanyFunctions = new MongoDBFunctions (host, port, "Company_Data");// create a MongoDB Client for the specified host and port, and get the Company_Data database 
		DBCollection compCollection = myMongoCompanyFunctions.setCollection("Companies");// get the Companies collection
		DBCollection locCollection = myMongoCompanyFunctions.setCollection("Locations");// get the Locations collection
		DBCollection empCollection = myMongoCompanyFunctions.setCollection("Employees");// get the Locations collection

		
		Boolean createTest = true;
		
		if (createTest) {// then only create the test employee
			docs = myMongoCompanyFunctions.getDocsByField(locCollection, "City", "Bellevue");// find the location for Bellevue, WA
			doc = docs.get(0);// get this location document from the ArrayList
			Location location = myMongoCompanyFunctions.doctoLocation(doc);// convert this Location document into a Location object
			Employee employee = new Employee (firstname, ' ',lastname, login, login, coid, coname,
					 location);// create the Employee object for the Pivot Payables Sandbox, WS Admin user
			employee.display();
			BasicDBObject myDoc = employee.getDocument();// convert this Employee object into a BasicDBObject
			myMongoCompanyFunctions.addDoc(empCollection, myDoc);// add this BasicDBObject to the MongoDB

		} else {// then generate the specified number of companies
	

			try {	
				if (args.length == 1) {
					//use defaults
	
				}if (args.length == 2) {//then there are parameters for EmployeeCount and StoreWhere.  Use defaults for host and port.  Create employees for all companies.
					try {
						EmployeeCount = Integer.parseInt(args[0]);
					} catch (NumberFormatException e) {
						EmployeeCount = 1;
					}	
					try {
						StoreWhere = Integer.parseInt(args[1]);
					} catch (NumberFormatException e) {
						StoreWhere = 1;
					}
	
				} else if (args.length == 3) {////then there are parameters for EmployeeCount and StoreWhere, as well as what company to create employees.  Use defaults for host and port.
					try {
						EmployeeCount = Integer.parseInt(args[0]);
					} catch (NumberFormatException e) {
						EmployeeCount = 1;
					}	
					try {
						StoreWhere = Integer.parseInt(args[1]);
					} catch (NumberFormatException e) {
						StoreWhere = 1;
					}
	
					coid = args[2];//create employees only for this CompanyID
	
							
		        	} else {//use defaults
		        		EmployeeCount = 1;//set default
		        		StoreWhere=2;//set default
		        		coid = "0";
		        	}
			} catch (Exception e) {
	    		out.println(e);
			}// try block
	
			String First [] = new String[1000];//an Array of String elements for holding the seed names for employee first name.
			String Last []= new String[1000];//an Array of String elements for holding the seed names for employee last name.

			int FirstCount = 0, LastCount = 0;


			
			// get the seed files from disk
			Scanner FirstNames = new Scanner(new File("FirstNames.txt"));//open the file of seed names for employee first names.
			Scanner LastNames = new Scanner(new File("LastNames.txt"));//open the file of seed names for employee last names.	
			
			//load FirstName string array with first names from the FirstNames.txt file
			int i=0;  
			while (FirstNames.hasNext()==true) {//iterate through the seed names in the file
				First[i]=FirstNames.next();//store the name in the array
				++i;
			}
			FirstCount=i-1;//the number of first names in the array

			
			//load LastName string array with first names from the LastNames.txt file
			i=0;
			while (LastNames.hasNext()==true) {//iterate through the seed names in the file
				Last[i]=LastNames.next();//store the name in the array
				++i;
				}
			LastCount=i-1;//the number of last names in the array
			
			
			//load an array of documents for all the companies in the Companies collection. 
			docs = myMongoCompanyFunctions.getDocs(compCollection);//create an ArrayList with documents for every company in the Companies collection
			int companycount = docs.size();//the number of companies in the Companies collection
			
			if (coid == "0") {//then create employees for all companies
				for (int index=0; index < companycount; index++) {//iterate for each company
					Company myCompany = myMongoCompanyFunctions.doctoCompany(docs.get(index));//get the Company object for this iteration
			    	EmployeeCount = CustomFunctions.skewedDistibution(100);//the number of employees to create for this company. A random integer between 1 and 100, with a skewed distribution
					CreateEmps(myCompany, EmployeeCount, First, Last, FirstCount, LastCount, docs, StoreWhere, myMongoCompanyFunctions, empCollection);//create the employees for this company
				}// for index loop

			} else {//create employees for the specified Company ID
				doc = myMongoCompanyFunctions.getDoc(compCollection, coid);//get the Company document for this CompanyID
				Company myCompany = myMongoCompanyFunctions.doctoCompany(doc);//convert this document into a Company object
				CreateEmps(myCompany, EmployeeCount, First, Last, FirstCount, LastCount, docs, StoreWhere, myMongoCompanyFunctions, empCollection);//create the employees for this company
			}// if coid== "0" block
		}// if createTest block
	}//end main method
	private static void CreateEmps (Company comp, int empcount, String[] FirstName, String[] LastName, int fcount, int lcount, ArrayList<DBObject> Docs, 
			int where, MongoDBFunctions functions, DBCollection collection) {
	
		String coid = comp.ID;
		String coname = comp.DBAName;
		String Domain = comp.Domain;
		String first, last;
		String login;
		String employeeid ="";
		char middle;
		Location location = comp.HQ;//set the employee's Home location to the company's HQ location
		int FirstIndex, LastIndex;

		Random RandomIndex = new Random();

		
		for (int j=0; j < empcount; j++) {
			
			FirstIndex = RandomIndex.nextInt(fcount);//select a random first name
			first = FirstName[FirstIndex];//assign it to first
			LastIndex = RandomIndex.nextInt (lcount);//select a random last name
			last = LastName[LastIndex];//assign it to last
			middle = (char) (RandomIndex.nextInt(26) + 'A');//select a random middle initial
			login = first +"."+ last + "@"+ Domain;//compose the Login ID
		
			// generate a random, 10 digit number for Employee_ID
			employeeid ="";
			for (int k=0; k< 10; k++) {
					employeeid = employeeid + (char) (RandomIndex.nextInt(10) + '0');
			}
			
			Employee myEmployee  = new Employee(first, middle, last, login, employeeid, coid, coname, location);//create the employee
			
			switch (where) {
			case 1: //print to console
				out.println("----------------------");
				myEmployee.display();	
				break;
			case 2: //MongoDB
				out.println("----------------------");
				myEmployee.display();
				BasicDBObject myDoc = myEmployee.getDocument();//convert the Employee object into a MongoDB document
				functions.addDoc(collection, myDoc);
				break;
			case 3://MySQL
				//Stub for code to store the company to the Employee table in MySQL
				break;
			}//end switch
		}//end for j loop
	}//end CreateEmps method

}

		
