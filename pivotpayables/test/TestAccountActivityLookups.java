package com.pivotpayables.test;
//import static java.lang.System.out;

import java.util.ArrayList;

import com.mongodb.DBCollection;
import com.mongodb.DBObject;

import com.pivotpayables.expensesimulator.MongoDBFunctions;
import com.pivotpayables.nexus.AccountingObjectLookup;


public class TestAccountActivityLookups {
	private final static String host= "localhost";//the MongoDB server host
	private final static int port = 27017;//the MongoDB server port
	//Get AccountActivityLookup documents
	
	private static MongoDBFunctions myMongoCompanyFunctions = new MongoDBFunctions (host,port , "Company_Data");//establish a MongoDB Client to the FieldContexts collection 
	private static DBCollection lulCollection = myMongoCompanyFunctions.setCollection("LookupLists");//get the LookupLists collection
	

	public static void main(String[] args) {
		
		
		AccountingObjectLookup lookup = new AccountingObjectLookup();// placeholder for AccountingObjectLookup object
	


	    ArrayList<DBObject> Docs = myMongoCompanyFunctions.getDocs(lulCollection);//an ArrayList of AccountingObjectLookup document elements
	    DBObject doc = null;//placeholder for a BatchDefinition document

	    int count = Docs.size();//the number of elements in the ArrayList of BatchDefinition documents

	    
	    for (int index =0; index<count; index++) {//iterate for each BatchDefinition
	    	doc = Docs.get(index);//get the BatchDefinition document for this iteration
	    	lookup = lookup.doctoAccountingObjectLookup(doc);
			lookup.display();//display it   
	
	    }//for i loop

	}

}
