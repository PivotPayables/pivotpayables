/**
 * 
 */
package com.pivotpayables.test;

import java.util.ArrayList;

import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.pivotpayables.expensesimulator.MongoDBFunctions;
import com.pivotpayables.nexus.AccountingObjectLookup;
import com.pivotpayables.nexus.AccountingObjectTable;

/**
 * @author John
 *
 */

public class TestAccountingObjectTable {
	private final static String host= "localhost";//the MongoDB server host
	private final static int port = 27017;//the MongoDB server port
	//Get AccountActivityLookup documents
	
	private static MongoDBFunctions myMongoCompanyFunctions = new MongoDBFunctions (host,port , "Company_Data");//establish a MongoDB Client to the FieldContexts collection 
	private static DBCollection lutCollection = myMongoCompanyFunctions.setCollection("LookupTables");//get the LookupTables collection
	

	public static void main(String[] args) {
		
		
		AccountingObjectTable table = new AccountingObjectTable();


	    ArrayList<DBObject> Docs = myMongoCompanyFunctions.getDocs(lutCollection);//an ArrayList of AccountingObjectLookup document elements
	    DBObject doc = null;//placeholder for a BatchDefinition document

	    int count = Docs.size();//the number of elements in the ArrayList of BatchDefinition documents

	    
	    for (int index =0; index<count; index++) {//iterate for each BatchDefinition
	    	doc = Docs.get(index);//get the BatchDefinition document for this iteration
	    	table = table.doctoAccountingObjectTable(doc);
			table.display();
	
	    }//for i loop

	}


}
