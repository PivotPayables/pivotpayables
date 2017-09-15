package com.pivotpayables.test;
import java.util.ArrayList;

import com.mongodb.DBObject;
import com.mongodb.DBCollection;
import com.pivotpayables.expensesimulator.Company;
import com.pivotpayables.expensesimulator.MongoDBFunctions;
import com.pivotpayables.expensesimulator.CreateCompanyBatchDefintion;

import static java.lang.System.out;

public class TestCreateCompanyBatchDefinition {
	public static void main(String[] args) {
		ArrayList<DBObject> docs = new ArrayList<DBObject>();//an ArrayList of Company document element 
	    DBObject doc = null;//placeholder for a Company document
	    
		String host= "localhost";//the MongoDB server host
		int port = 27017;//the MongoDB server port
		MongoDBFunctions myMongoCompanyFunctions = new MongoDBFunctions (host, port, "Company_Data");// create a MongoDB Client for the specified host and port, and get the Company_Data database 
		DBCollection compCollection = myMongoCompanyFunctions.setCollection("Companies");// get the Companies collection
		
		Company company = new Company();
		
		//Get Company documents
	    docs = myMongoCompanyFunctions.getDocs(compCollection);//get all the documents from the Companies collection
	    int companycount = docs.size();//the number of documents in the ArrayList of Company documents
	    
	    out.println(companycount);
	    for (int index =0; index<companycount; index++) {//iterate for each document
	    	doc = docs.get(index);//get the Company document for this iteration
	    	company = company.doctoCompany(doc);//convert the document into a Company object
	    	company.display();
	    	CreateCompanyBatchDefintion.CreateBatchDefintions(company);
	    	
	    }//for i loop

	}

}
