
package com.pivotpayables.test;

/**
 * @author John Toman
 * 2-12-2015
 * This program tests the OAuthToken class and the MongoDBFunctions class can create and store OAuthToken object into, and then read from the MongoDB 
 * Tokens collection.
 *
 */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.pivotpayables.concurplatform.OAuthToken;
import com.pivotpayables.expensesimulator.Employee;
import com.pivotpayables.expensesimulator.GUID;
import com.pivotpayables.expensesimulator.MongoDBFunctions;

import static java.lang.System.out;
public class TestOAuthToken {

	/**
	 * @param args
	 * @throws ParseException 
	 */
	public static void main(String[] args) throws ParseException {

		OAuthToken token = new OAuthToken();
		DBObject doc;
		String host= "localhost";//the MongoDB server host
		int port = 27017;//the MongoDB server port
		MongoDBFunctions myMongoCompanyFunctions = new MongoDBFunctions (host, port, "Company_Data");// create a MongoDB Client for the specified host and port, and get the Company_Data database 
		DBCollection tokenCollection = myMongoCompanyFunctions.setCollection("Tokens");// get the Tokens collection
		DBCollection empCollection = myMongoCompanyFunctions.setCollection("Employees");// get the Employees collection
		Employee employee = new Employee();
		
		/*
		String Instance = "https://www.concursolutions.com/";
		String Token = "JzsV4ry0wMjQth5DDGL0UI3j57k=";// OAuth access token
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss a");
		String strDate = "06/11/2016 04:58:55 AM";// assign the Expriration_Date to this "String date"
		Date ExpirationDate = sdf.parse(strDate);// parse the "String date" into a Date object using the SimpleDateFormat object sdf
		String RefreshToken="Test refresh";// the refresh token
		String EmployeeID = "OIRX4300NYJG9928GKWP";// the GUID for the Employee object for the Concur User this token belongs
		String CompanyID = "PRBQ3833MMRY9500ACAD";
		token.setID(GUID.getGUID(4));
		token.setInstanceURL(Instance);
		token.setToken(Token);
		token.setExpirationDate(ExpirationDate);
		token.setRefreshToken(RefreshToken);
		token.setEmployeeID(EmployeeID);
		token.setCompanyID(CompanyID);
*/
		
		//BasicDBObject myDoc = token.getDocument();// convert the OAuthToken object into a BasicDBObject document
		//myMongoCompanyFunctions.addDoc(tokenCollection, myDoc);// add the BasicDBObject to the Tokens collection
		//myMongoCompanyFunctions.deleteDoc(tokenCollection, "Token","JzsV4ry0wMjQth5DDGL0UI3j57k=");
			
		
		ArrayList<DBObject> docs = myMongoCompanyFunctions.getDocs(tokenCollection);
		for (int i=0; i<docs.size(); i++) {
			doc = docs.get(i);
			token = token.doctoToken(doc);
			//String empid = token.getEmployeeID();
			//doc = myMongoCompanyFunctions.getDoc(empCollection, empid);
			//employee = employee.doctoEmployee(doc);
			//out.println("Token for Employee " + employee.getDisplayName());
			//out.println("Token for Company " + employee.getEmployerCompanyName());
			//out.println("Token for Company ID " + employee.getEmployerCompanyID());
			token.display();
		}
		
	}

}
