
package com.pivotpayables.expensesimulator;

/**
 * @author John Toman
 * 8/19/15
 * 
 * This class converts a specified OAuth access token for specified employee into Token object and then stores token in the MongoDB associated to the employee.
 * The primary use case is taking an OAuth token for a PivotPrime customer into a test environment.
 *
 */
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.pivotpayables.concurplatform.OAuthToken;
import com.pivotpayables.expensesimulator.Employee;
import com.pivotpayables.expensesimulator.MongoDBFunctions;

public class CreateDemoOAuthToken {


	private static String login = "Support@westyost.com";// the ID for the employee to associate

	
	private static final String host= "localhost";//the MongoDB server host
	private static final int port = 27017;//the MongoDB server port
	private static MongoDBFunctions myMongoCompanyFunctions = new MongoDBFunctions (host, port, "Company_Data");// create a MongoDB Client for the specified host and port, and get the Company_Data database 
	private static DBCollection tokenCollection = myMongoCompanyFunctions.setCollection("Tokens");// get the Tokens collection
	private static DBCollection empCollection = myMongoCompanyFunctions.setCollection("Employees");// get the Employees collection
	
	public static void main(String[] args) throws ParseException {
		OAuthToken token = new OAuthToken();
		DBObject doc;
		ArrayList<DBObject> Docs = new ArrayList<DBObject>();
		Employee employee;
		
		String Instance = "https://www.concursolutions.com/";
		String Token = "0_H8UDN5wGoLs/KEid6HkF7EB4I=";// OAuth access token
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss a");
		String strDate = "3/8/2017 04:58:55 AM";// assign the Expriration_Date to this "String date"
		Date ExpirationDate = sdf.parse(strDate);// parse the "String date" into a Date object using the SimpleDateFormat object sdf
		String RefreshToken="Test refresh";// the refresh token


		

		Docs = myMongoCompanyFunctions.getDocsByField(empCollection, "LoginID", login);// find the Employee document with the matching LoginID
		doc = Docs.get(0);// get the first matching document (there should be only one)
		employee = myMongoCompanyFunctions.doctoEmployee(doc);// convert this document into an Employee document
		String EmployeeID = employee.getID();// assign the ID for this Employee
		String CompanyID = employee.getEmployerCompanyID();// assign the Company ID for this employee
		
		token.setID(GUID.getGUID(4));
		token.setInstanceURL(Instance);
		token.setToken(Token);
		token.setExpirationDate(ExpirationDate);
		token.setRefreshToken(RefreshToken);
		token.setEmployeeID(EmployeeID);
		token.setCompanyID(CompanyID);

	
		token.display();
		BasicDBObject myDoc = token.getDocument();// convert the OAuthToken object into a BasicDBObject document
		myMongoCompanyFunctions.addDoc(tokenCollection, myDoc);// add the BasicDBObject to the Tokens collection
		//myMongoCompanyFunctions.deleteDoc(tokenCollection, "Token","h+rMchmMJRMO4sD1YVdSe2VLifY=");

	}

}
