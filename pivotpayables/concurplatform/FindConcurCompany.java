
package com.pivotpayables.concurplatform;

/**
 * @author John Toman
 * 6-23-2016
 * This program finds in the Pivot Payables MongoDB or MySQL database the company that matches the specified Concur Concur OAuth Access Token (key).
 * Use this program to synchronize a Concur entity that the key owner is a user to its company in Pivot Payables.
 * the MongoDB.
 * 
 * Modified to use the doctoObject methods of the Employee and OAuthToken classes
 *
 */


import java.text.ParseException;
import java.util.ArrayList;

import com.mongodb.DBObject;
import com.mongodb.DBCollection;
import com.pivotpayables.expensesimulator.Employee;
import com.pivotpayables.expensesimulator.MongoDBFunctions;

public class FindConcurCompany {

    private static ArrayList<DBObject> docs = new ArrayList<DBObject>();//an ArrayList of Company document element 
    private static DBObject doc = null;//placeholder for a Company document
    
    private static String host= "localhost";//the MongoDB server host
    private static int port = 27017;//the MongoDB server port
    private static MongoDBFunctions myMongoCompanyFunctions = new MongoDBFunctions (host, port, "Company_Data");// create a MongoDB Client for the specified host and port, and get the Company_Data database 
    private  static DBCollection empCollection = myMongoCompanyFunctions.setCollection("Employees");// get the Companies collection
    private static DBCollection tokenCollection = myMongoCompanyFunctions.setCollection("Tokens");// get the Companies collection
    private static OAuthToken token= new OAuthToken();
    private static Employee employee= new Employee();
	
    public static void setCompanyID (String key) throws ParseException {
    	// This sets the Company ID for an exisitng key
		
		docs = myMongoCompanyFunctions.getDocsByField(tokenCollection, "Token", key);// find the Token document that matches the specified token
		if (docs.size() > 0) {// then it found a matching token document
			doc = docs.get(0);
			token = token.doctoToken(doc);
			String empid = token.getEmployeeID();
			doc = myMongoCompanyFunctions.getDoc(empCollection, empid);
			employee = employee.doctoEmployee(doc);
			String coid = employee.getEmployerCompanyID();
			myMongoCompanyFunctions.updateDocByField(tokenCollection, "ID", token.getID(), "CompanyID", coid);

		}
    }
    public static String getCompanyID (String key) throws ParseException {
    	docs = myMongoCompanyFunctions.getDocsByField(tokenCollection, "Token", key);// find the Token document that matches the specified token
		if (docs.size() > 0) {// then it found a matching token document
			doc = docs.get(0);
			token = token.doctoToken(doc);
			return token.getCompanyID();

		} else  {
			return "";

		}
    }
}
