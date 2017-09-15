package com.pivotpayables.prime;
/**
 * @author John Toman
 * 9/20/2016
 * This class regenerates the AccountActivityCharge documents for a specified CompanyID.
 * Its purpose is to fix incorrect FieldConect values that corrupt the AccountActivityChargeDocument.  
 * 
 * Once the FieldContext values are corrected, you can use the regenAccountActivityChargeDocs method to regenerate
 * the AccountActivityCharge documents using the Expense documents stored in the MongoDB.
 * 
 * This utility provides a way to fix corrupted AccountActivityCharge documents without having to repull expenses from Concur.
 * This is very important because for large clients such as Cardno it can take several days to repull expenses.
 * 
 *
 */
//import static java.lang.System.out;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.ArrayList;

import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.pivotpayables.concurplatform.Expense;
import com.pivotpayables.concurplatform.FindConcurCompany;
import com.pivotpayables.expensesimulator.MongoDBFunctions;


public class FixCorruptedChargeDocs {
	
	static final String host = "localhost";
	static final int port = 27017;
	
	
	// Connect to  the Expense_Data database, and then get the Expenses collection
	private static MongoDBFunctions myMongoExpenseFunctions = new MongoDBFunctions (host, port, "Expense_Data");// create a MongoDB Client for the specified host and port, and get the Expense_Data database
	private static DBCollection expCollection = myMongoExpenseFunctions.setCollection("Expenses");// get the Expenses collection
	private static DBCollection chargeCollection = myMongoExpenseFunctions.setCollection("AccountActivityCharges");//get the AccountActivityCharges collection


	
	public static String regenAccountActivityChargeDocs (String key) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ParseException { 
		
		ArrayList<DBObject> expDocs = new ArrayList<DBObject>();// an ArrayList with MongoDB Expense document elements
		Expense expense = new Expense();// placeholder for an Expense object
		String status = "failed";// initialize status as failed; change only successful regenerating
		
		// regenerate AccountActivityCharge documents for the specified Company ID BasicDBObject 
		String companyID = FindConcurCompany.getCompanyID(key);// get the CompanyID for the company associated to the owner of the key
		
		

		// Next, get the Expense documents from the MongoDB for this CompanyID
	    expDocs = new ArrayList<DBObject>();// initialize expDocs
	    expDocs = myMongoExpenseFunctions.getDocsByField(expCollection, "CompanyID", companyID);// get the Expense documents for this company
		
	    for (DBObject mydoc:expDocs) {// iterate for each Expense document for this CompanyID

			expense = expense.doctoExpense(mydoc);// convert this Expense document into an Expense object
			status = myMongoExpenseFunctions.deleteDoc(chargeCollection, "EntryID", expense.getEntry_ID());// delete Charge documents associated with this ExpenseID
			//Next, regenerate the Charge documents	  
			if (!(status.equals("failed"))){// then create the Charge documents for this expense
				CreateChargeDocs.processExpenses(key);
		    	
		    } else {
		    	return status;
		    }
	    }// DBObject mydoc:expDocs
	   

		return status;
	}

}
