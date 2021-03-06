/**
 * 
 */
package com.pivotpayables.test;

import static java.lang.System.out;

import java.util.ArrayList;
import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.gridfs.*;
import com.pivotpayables.concurplatform.Expense;
import com.pivotpayables.concurplatform.ExpenseReports;
import com.pivotpayables.concurplatform.Image;
import com.pivotpayables.concurplatform.ImageURL;
import com.pivotpayables.expensesimulator.MongoDBFunctions;



/**
 * @author John Toman
 * 8/14/15
 * A tool to test the the GetReportImage method of the ExpenseReports class and MongoDB putImage and getImage functions
 *
 */
public class TestGetReportImage {
	private static final String host= "localhost";//the MongoDB server host
	private final static int port = 27017;//the MongoDB server port
	
	private final static String key = "JzsV4ry0wMjQth5DDGL0UI3j57k=";//"RH5PUJV1cUCx6iEkwbQ8ZaQq5nU=";

	
	// Connect to  the Expense_Data database, and then get the Expenses collection
	private static MongoDBFunctions myMongoExpenseFunctions = new MongoDBFunctions (host, port, "Expense_Data");// create a MongoDB Client for the specified host and port, and get the Expense_Data database

	private static DBCollection expCollection = myMongoExpenseFunctions.setCollection("Expenses");// get the Expenses collection

	
	public static void main(String[] args) throws MalformedURLException, IOException {
		Expense expense;// placeholder for an Expense object
		ExpenseReports r = new ExpenseReports();
		String reportID = "";// the Report ID of the test expense report.
		String previousreportID = "";// 
		ImageURL imageurl=null;
		String url = null;
		Path currentRelativePath = Paths.get("");// find the current relative path
		String downloadpath = (currentRelativePath.toAbsolutePath().toString()) + "/ImageFiles/";// construct the absolute path to the ImageFiles directory
	
		ArrayList<DBObject> Docs = new ArrayList<DBObject>();// an ArrayList with MongoDB document elements


			Docs = new ArrayList<DBObject>();
			Docs = myMongoExpenseFunctions.getDocs(expCollection);// get the Expense documents from the Expenses collection
			int expensecount = Docs.size();// the number of Expense documents in the ArrayList.
			
			for (int k=0; k<expensecount; k++) {//iterate for each expense (int k=0; k<expensecount; k++)
				DBObject doc = Docs.get(k);// get the Expense document for this iteration
				expense = myMongoExpenseFunctions.doctoExpense(doc);// convert the Expense document into an Expense object
				reportID = expense.getReportID();// get the Report ID for this expense.
				
				if (reportID.isEmpty() || (reportID.equals(previousreportID))){// then skip this expense
					
				}
				else {
					previousreportID = reportID;// set the value for previous repordID to the current report ID; 
					imageurl = r.getReportImageURL(reportID, key);// get from the Concur platform the ImageURL for this report
					url = imageurl.getUrl();// get its URL
					String filename = reportID + ".PDF";// set the file name for the image to its Report ID
					Image.downloadImageFile(url, downloadpath + filename);// using this URL, download the image and store it in the file system
					myMongoExpenseFunctions.putImage(downloadpath, filename);
					//myMongoExpenseFunctions.getImage(downloadpath, filename);

				}// (reportID != null)

			}// k loop
	}
}
