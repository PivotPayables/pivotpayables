package com.pivotpayables.test;
import static java.lang.System.out;

import java.util.ArrayList;

import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.pivotpayables.expensesimulator.MongoDBFunctions;
import com.pivotpayables.nexus.BatchDefinition;
import com.pivotpayables.nexus.FieldMapping;
import com.pivotpayables.prime.FieldContext;

import static java.lang.System.out;
public class TestBatchDefinitions {
	private final static String host= "localhost";//the MongoDB server host
	private final static int port = 27017;//the MongoDB server port
	//Get FieldContext documents
	
	private static MongoDBFunctions myMongoCompanyFunctions = new MongoDBFunctions (host,port , "Company_Data");//establish a MongoDB Client to the FieldContexts collection 
	private static DBCollection batCollection = myMongoCompanyFunctions.setCollection("BatchDefinitions");//get the FieldContexts collection
	

	public static void main(String[] args) {
		
		
	    BatchDefinition definition = new BatchDefinition();
		args = new String[4];// initialize the String array args
	


	    ArrayList<DBObject> Docs = new ArrayList<DBObject>();//an ArrayList of FieldContext document elements
	    Docs = myMongoCompanyFunctions.getDocs(batCollection);// get the batch definitions 
	    DBObject doc = null;//placeholder for a BatchDefinition document

	    int count = Docs.size();//the number of elements in the ArrayList of BatchDefinition documents

	    
	    for (int index =0; index<count; index++) {//iterate for each BatchDefinition
	    	doc = Docs.get(index);//get the BatchDefinition document for this iteration
	    	definition = definition.doctoBatchDefinition(doc);//convert the document into a BatchDefinition object
	    	out.println("BatchDefinition Number: " + Integer.toString(index+1));
			out.println("---------------------------------------");
			ArrayList<FieldMapping> mappings = definition.getDetailCustomFields();
			definition.display();//display it   
			out.println();
	    }//for i loop

	}

}
