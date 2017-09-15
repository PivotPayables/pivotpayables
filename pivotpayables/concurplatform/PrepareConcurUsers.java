/**
 * 
 */
package com.pivotpayables.concurplatform;

import com.pivotpayables.expensesimulator.MongoDBFunctions;

/**
 * @author TranseoTech
 *
 */







import java.text.ParseException;
import java.util.ArrayList;

import javax.xml.bind.JAXBException;

import static java.lang.System.out;







//MongoDB
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

//Expense Simulator
import com.pivotpayables.expensesimulator.Company;
import com.pivotpayables.expensesimulator.Employee;


public class PrepareConcurUsers {

	protected final static String host ="localhost";//MongoDB server host
	protected final static int port = 27017;//MongoDB server port 
	protected final static MongoDBFunctions myMongoCompanyFunctions= new MongoDBFunctions (host, port, "Company_Data");// create a MongoDB Client for the specified host and port, and get the Company_Data database 
	protected final static DBCollection compCollection = myMongoCompanyFunctions.setCollection("Companies");// get the Companies collection
	protected final static DBCollection empCollection = myMongoCompanyFunctions.setCollection("Employees");// get the Employees collection
	protected final static DBCollection locCollection = myMongoCompanyFunctions.setCollection("Locations");// get the Locations collection
	protected final static DBCollection tokenCollection = myMongoCompanyFunctions.setCollection("Tokens");// get the Tokens collection
	static ArrayList<DBObject> docs = new ArrayList<DBObject>();//an ArrayList of document elements
	static DBObject doc;// place holder for a DBObject document
	static BasicDBObject myDoc;// placeholder for a BasicDBObject document
	static Company company = null; // placeholder for a Company object
	static String coid= null;// placeheolder for CompanyID
	static Employee employee;// placeholder for an Employee object
	static OAuthToken token;// placeholder for a Token object
	
	public static void main(String[] args) throws JAXBException, ParseException {

		
		
		docs = myMongoCompanyFunctions.getDocsByField(compCollection, "Domain", "Connect-PivotPayables.com");
		if (docs.size() > 0) {
			doc = docs.get(0);//
			company = myMongoCompanyFunctions.doctoCompany(doc);// convert the Company document into an Company object
			coid = company.getID();// the GUID for the company
			out.println(company.getDBAName());
			/*
			docs = myMongoCompanyFunctions.getDocsByField(empCollection, "EmployerCompanyID", coid);// find the Employee documents for this company
			if (docs.size() > 0) {// then there are Employees for this company
				out.println("Checkpoint : Employee count" + docs.size());
				for (int i=0; i < docs.size(); i++) {
					doc = docs.get(i);// get the Employee document for this iteration
					employee = myMongoCompanyFunctions.doctoEmployee(doc);// convert the Employee document into an Employee object
					createToken(employee);
					getToken(employee);

				}
			} else {
				out.println("No Employees Founct");
			}
			*/
		} else {
			out.println("Comapny Not Found");
		}
		employee = createEmployee();
		createToken(employee);
		getToken(employee);

		/*
		docs = myMongoCompanyFunctions.getDocsByField(empCollection, "LoginID", "Cher.Patterson@Connect-PivotPayables.com");// find the Employee document for this user
		doc = docs.get(0);// get the Employee document
		employee = myMongoCompanyFunctions.doctoEmployee(doc);// convert the Employee document into an Employee object
		 */	
		
	}
	private static Employee createEmployee () {
		// create an Employee in the MongoDB for this Concur User
		employee = new Employee("US", 'A', "Processor", "processor@Connect-PivotPayables.com", "301", coid, "Pivot Payables Sandbox", company.getHQ());
		myDoc = employee.getDocument();//convert the Employee object into a MongoDB BasicDBObject
		myMongoCompanyFunctions.addDoc(empCollection, myDoc);// add the employee
		return employee;
	}
	private static void createToken (Employee employee) throws JAXBException {
		//create a token for this Employee and store in the MongoDB
		token = ConcurFunctions.GetOAuthToken("Kym5rO47mgC1RiXJcWW1JW", employee.getLoginID(), "welcome1", employee.getID());
		myDoc = token.getDocument();// convert the OAuthToken object into a BasicDBObject document
		myMongoCompanyFunctions.addDoc(tokenCollection, myDoc);// add the BasicDBObject to the Tokens collection
	}
	private static void getToken (Employee employee) throws ParseException{
		ArrayList<DBObject> Docs = myMongoCompanyFunctions.getDocsByField(tokenCollection, "EmployeeID", employee.getID());// find the Token documents for this Employee
		if (Docs.size() > 0) {// then there is at least one token
			out.println("Tokens for " + employee.DisplayName);
			for (int i=0; i < Docs.size(); i++) {
				doc = Docs.get(0);// get the Token document for this iteration
				token = myMongoCompanyFunctions.doctoToken(doc);// convert the Token document into a Token object
				token.display();
			}// for i loop
		}// if block

	}
}
