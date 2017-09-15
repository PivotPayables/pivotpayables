/**
 * 
 */
package com.pivotpayables.test;

import java.util.ArrayList;
import java.util.Date;

import javax.xml.bind.JAXBException;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.pivotpayables.concurplatform.ConcurFunctions;
import com.pivotpayables.concurplatform.OAuthToken;
import com.pivotpayables.expensesimulator.Employee;
import com.pivotpayables.expensesimulator.GUID;
import com.pivotpayables.expensesimulator.MongoDBFunctions;

import static java.lang.System.out;

/**
 * @author John Toman
 * 3/26/2015
 *
 *This tests the GETOAuthToken function.
 *It also is a way to get an OAuth token for a specified partner app key, and Concur Login ID and password
 */
public class TestGetOAuthToken {

	private static String key = "FwuD7IzMGvtF7WnUMHwojM";
	private static String login = "john.toman@teletracking.com";
	private static String password = "Welcome1";
	
	private static final String host= "localhost";//the MongoDB server host
	private static final int port = 27017;//the MongoDB server port
	private static MongoDBFunctions myMongoCompanyFunctions = new MongoDBFunctions (host, port, "Company_Data");// create a MongoDB Client for the specified host and port, and get the Company_Data database 
	private static DBCollection tokenCollection = myMongoCompanyFunctions.setCollection("Tokens");// get the Tokens collection
	private static DBCollection empCollection = myMongoCompanyFunctions.setCollection("Employees");// get the Employees collection
	
	public static void main(String[] args) throws JAXBException {
		
		OAuthToken token = new OAuthToken();
		DBObject doc;
		ArrayList<DBObject> Docs = new ArrayList<DBObject>();
		Employee employee;
		
/*
		Docs = myMongoCompanyFunctions.getDocsByField(empCollection, "LoginID", login);
		doc = Docs.get(0);
		employee = myMongoCompanyFunctions.doctoEmployee(doc);
		*/
		token = ConcurFunctions.GetOAuthToken(key, login, password);
		// token.setCompanyID(employee.getEmployerCompanyID());
		// token.setEmployeeID(employee.getID());
		token.display();
		//BasicDBObject myDoc = token.getDocument();// convert the OAuthToken object into a BasicDBObject document
		//myMongoCompanyFunctions.addDoc(tokenCollection, myDoc);// add the BasicDBObject to the Tokens collection
		//myMongoCompanyFunctions.deleteDoc(tokenCollection, "Token","h+rMchmMJRMO4sD1YVdSe2VLifY=");
	}

}
