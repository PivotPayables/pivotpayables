
package com.pivotpayables.test;


/**
 * @author John Toman
 * 3/21/15
 * 
 * This class creates a form field to context mapping for a specified Company ID
 *
 */
import static java.lang.System.out;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.pivotpayables.expensesimulator.GUID;
import com.pivotpayables.expensesimulator.MongoDBFunctions;
import com.pivotpayables.prime.FieldContext;

public class CreateFieldContext {

	/**
	 * @param args
	 * args[0] is Form Type CompanyID
	 * args[1] is the Field ID
	 * args[2] is the Context
	 * args[3] is the CompanyID
	 * 
	 */
	private final static String host= "localhost";//the MongoDB server host
	private final static int port = 27017;//the MongoDB server port
	private static MongoDBFunctions myMongoCompanyFunctions = new MongoDBFunctions (host, port, "Company_Data");// create a MongoDB Client for the specified host and port, and get the Company_Data database 
	private static DBCollection conCollection = myMongoCompanyFunctions.setCollection("FieldContexts");// get the Companies collection

	
	public static void main(String[] args) {

		String CompanyID = "PRBQ3833MMRY9500ACAD";// the Company this context mapping belongs
		String FormType = "ExpenseEntry";// the Concur form type
		String FieldID = "Custom2";// the name of the field
		String Context = "Account";// its context
		FieldContext context = new FieldContext();

		try {
			if (args.length == 3) {
				//use default for CompanyID
				FormType = args[0];
				FieldID = args[1];
				Context = args[2];
			} else if (args.length == 4) {
				FormType = args[0];// the Concur form type
				FieldID = args[1];// the name of the field
				Context = args[2];// its context
				CompanyID = args[3];			
	        } 
		} catch (Exception e) {
    		out.println(e);
		}// try block
		
		context.setID(GUID.getGUID(2));
		context.setCompanyID(CompanyID);
		context.setFormType(FormType);
		context.setFieldID(FieldID);
		context.setContext(Context);
		BasicDBObject myDoc = context.getDocument();//convert the FieldContext object into a MongoDB document
		myMongoCompanyFunctions.addDoc(conCollection, myDoc);
		
	}

}
