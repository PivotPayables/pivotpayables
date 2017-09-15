/**
 * 
 */
package com.pivotpayables.test;

import static java.lang.System.out;

import java.util.ArrayList;

import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.pivotpayables.expensesimulator.MongoDBFunctions;
import com.pivotpayables.prime.FieldContext;

/**
 * @author John Toman
 * 3/21/15
 * 
 * This is a tool to test the creation of a FieldContext.
 *
 */
public class TestFieldContext {
	private final static String host= "localhost";//the MongoDB server host
	private final static int port = 27017;//the MongoDB server port
	//Get FieldContext documents
	
	private static MongoDBFunctions myMongoCompanyFunctions = new MongoDBFunctions (host,port , "Company_Data");//establish a MongoDB Client to the FieldContexts collection 
	private static DBCollection conCollection = myMongoCompanyFunctions.setCollection("FieldContexts");//get the FieldContexts collection
	private static String CompanyID = "LBUD8458FZGE8123";

	public static void main(String[] args) {
		
		Boolean createcontext = false;
	    FieldContext fieldcontext= new FieldContext();//placeholder for a context object
		args = new String[4];// initialize the String array args
		
		if (createcontext) {// create default context mappings for Pivot Payables sandbox
			args[3] = CompanyID;
			
			args[0] = "ExpenseEntry";
			args[1] = "Custom3";
			args[2] = "Account";
			CreateFieldContext.main(args);// create the mapping the Expense Entry, Custom3 field
			
			args[1] = "Custom4";
			args[2] ="Activity";
			CreateFieldContext.main(args);// create the mapping the Expense Entry, Custom4 field
			
		}


	    ArrayList<DBObject> Docs = new ArrayList<DBObject>();//an ArrayList of FieldContext document elements
	    Docs = myMongoCompanyFunctions.getDocsByField(conCollection, "CompanyID", CompanyID);// get the field context mappings for this company
	    

	    int index = 0;//the number of elements in the ArrayList of FieldContext documents

	    
	    for (DBObject doc:Docs) {//iterate for each field context document, doc
	    	
	    	fieldcontext = fieldcontext.doctoFieldContext(doc);//convert the document into a FieldContext object
	    	out.println("FieldContext Number: " + Integer.toString(index+1));
			out.println("---------------------------------------");
			fieldcontext.display();//display it   
			out.println();
	    }//for DBObject doc:Docs loop

	}

}

