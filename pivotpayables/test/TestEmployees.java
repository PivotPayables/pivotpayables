package com.pivotpayables.test;
/**
 * @author John Toman
 * 2-4-2015
 * This program tests the program CreateEmployees to ensure it successfully created the employee documents in
 * the MongoDB.
 * 
 * Run this program after running the CreateEmployees program.
 *
 */



import java.io.FileNotFoundException;
import java.util.ArrayList;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCollection;
import com.pivotpayables.expensesimulator.Employee;
import com.pivotpayables.expensesimulator.MongoDBFunctions;

import static java.lang.System.out;



public class TestEmployees {

	public static void main(String[] args) throws FileNotFoundException {
		
		String coid = "";
		String host= "localhost";//the MongoDB server host
		int port = 27017;//the MongoDB server port
		//Get Employee documents
		
		MongoDBFunctions myMongoFunctions = new MongoDBFunctions (host, port, "Company_Data", "Employees");
		DBCollection myCollection = myMongoFunctions.getCollection();//get the Employees collection
	    
	    ArrayList<DBObject> Docs = new ArrayList<DBObject>();//an ArrayList of Employee document elements

	    char c;
	    
	    if (coid.isEmpty()) {// then get all employees
	    	Docs = myMongoFunctions.getDocs(myCollection); //get all the documents from the Employees collection and store these in the ArrayList
	    } else { // get employees only for the specified Company ID
	    	Docs = myMongoFunctions.getDocsByField(myCollection, "EmployerCompanyID", coid);
	    	//Docs = myMongoFunctions.getDocsByField(myCollection, "EmployeeDisplayName", "");
	    }
	    	
	    int employeecount = Docs.size();//the number of elements in the ArrayList of Employee documents

	
	    DBObject doc = null;// placeholder for an Employee document
	    Employee employee = new Employee();//placeholder for an Employee object

	    for (int index =0; index<employeecount; index++) {//iterate for each document in the ArrayList
	    	doc = Docs.get(index);//get the Employee document for this iteration
	    	employee = employee.doctoEmployee(doc);//convert this document into an Employee object
	    	//employee.display();
	    	if (employee.getLoginID().equals("gena.freeland@voicebrook.com")) {// corrupt Employee document
	    		out.println("Login ID is  " + employee.getLoginID());
	    		myMongoFunctions.deleteDocByDoc(myCollection, doc);
	    	} else {// then the Employee document has a Login ID
	    		if (employee.getDisplayName() == null) {
		    	    String first=null, last=null, name=null;
		    	    String[] parts = null;
		    	    StringBuilder sb = null;
		    		name = employee.getLoginID();
		    		if ((employee.getFirstName() != null) && (employee.getLastName() != null)) {// then there is both a FirstName and LastName, but not a Display Name
		    			name = employee.getFirstName() + " " + employee.getLastName();
			    		out.println("Display Name created from First and Last Name: " + name);
			    		employee.setDisplayName(name);

		    		} else {// then need to create first and last name from Login ID
			    		name = name.substring(0, name.lastIndexOf("@"));// get the user name from the login ID
			    		parts = name.split("\\.");// split the user name into first and last name using . as delimiter
			    		sb = new StringBuilder(parts[0]);
			    		
			    		c = sb.charAt(0);// get the first letter of first name
			    		if (Character.isLowerCase(c)) {
			    	        sb.setCharAt(0, Character.toUpperCase(c));
			    	    } 
			    		first = sb.toString();
			    		if (parts.length > 1) {// there is a last name
				    		sb = new StringBuilder(parts[1]);
				    		c = sb.charAt(0);// get the first letter of last name
				    		if (Character.isLowerCase(c)) {
				    	        sb.setCharAt(0, Character.toUpperCase(c));
				    	    } 
			    		last = sb.toString();
			    		}// if (parts.length > 1)
			    		out.println("First and Last Name created from Login ID: " + first + " " + last);
			    		employee.setDisplayName(first + " " + last);
			    		employee.setFirstName(first);
			    		employee.setLastName(last);
	
		    			}// if ((employee.getFirstName() != null) && (employee.getLastName() != null))
		    			myMongoFunctions.deleteDocByDoc(myCollection, doc);
		    			myMongoFunctions.addDoc(myCollection, employee.getDocument());
		    		}// if (employee.DisplayName == null)
	    	}
	    	
	    	out.println("Employee Number " + Integer.toString(index+1));
			out.println("---------------------------------------");
	    	employee.display();
			out.println();
	    }//index loop

	}//main
}