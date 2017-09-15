package com.pivotpayables.prime;
import static java.lang.System.out;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.pivotpayables.expensesimulator.GUID;



/**
 * @author John Toman
 * 6/21/2016
 * This is the base class for an Account Activity.
 * 
 * Moved all AccountActivity related methods from MongoDBFunctions
 * Converted properties to fields by making them private
 * 
 * Move to the PivotPrime package
 *
 */
public class AccountActivity {// an activity for a specified company's account (client)
	private String ID;
	private String CompanyID;//the company this account activity belongs
	private String AccountID;// the GUID for the Account (a.k.a. "client")
	private String AccountName;// the account's name
	private String ActivityID;// the GUID for an activity 
	private String ActivityName;// the activity's name

	public AccountActivity() {};// no args constructor
	
	public AccountActivity(String coid, String accountid, String name, String actid, String activity){//constructor, near complete
		ID = GUID.getGUID(5);
		CompanyID = coid;
		AccountID = accountid;
		AccountName = name;
		ActivityID = actid;
		ActivityName = activity;
	}
	public AccountActivity(String coid, String accountid, String name, String actid, String activity, String id){//constructor, use when converting a document into an AccountActivity
		this(coid, accountid,name, actid, activity);
		ID = id;

	}
	public void display () {//method to display the company in the console
		out.println("ID: " + ID);
		out.println("Company ID: " + CompanyID);
		out.println("Account ID: " + AccountID);
		out.println("Account Name: " + AccountName);
		out.println("Activity ID: " + ActivityID);
		out.println("Activity Name: " + ActivityName);
	}

	public BasicDBObject getDocument () {
		BasicDBObject myAccountActivity = new BasicDBObject("ID",this.ID);
		myAccountActivity.append("CompanyID",this.CompanyID);
		myAccountActivity.append("AccountID", this.AccountID);
		myAccountActivity.append("AccountName", this.AccountName);
		myAccountActivity.append("ActivityID", this.ActivityID);
		myAccountActivity.append("ActivityName", this.ActivityName);
		return myAccountActivity;
	}
	public AccountActivity doctoAccountActivity (DBObject doc) {
		String ID= doc.get("ID").toString();
		String CompanyID = doc.get("CompanyID").toString();
		String AccountID = doc.get("AccountID").toString();
		String AccountName = doc.get("AccountName").toString();
		String ActivityID = doc.get("ActivityID").toString();
		String ActivityName = doc.get("ActivityName").toString();
		
		AccountActivity myAccountActivity = new AccountActivity(CompanyID, AccountID, AccountName, ActivityID, ActivityName, ID);
		
		return myAccountActivity;
	}
	public int getAccountActivitiesCount(DBCollection collection, String coid){//returns documents for account activities at specified Employer Company ID
	    DBObject doc;
	    Long total = collection.count();
	    int count = total.intValue();
	    DBObject docs[]= new DBObject[count];
	    int Index=0;
	    BasicDBObject query= new BasicDBObject("ID", coid);
	    DBCursor cursor = collection.find(query);
	    while (cursor.hasNext()) {
	    	doc = cursor.next();
	    	docs[Index] = doc;
	    	Index = Index++;
	    }

	    return Index;
	  }
	public DBObject[] getAccountActivities(DBCollection collection, String coid){//returns documents for account activities at specified Employer Company ID
	    DBObject doc;
	    Long total = collection.count();
	    int count = total.intValue();
	    DBObject docs[]= new DBObject[count];
	    int Index=0;
	    BasicDBObject query= new BasicDBObject("CompanyID", coid);
	    DBCursor cursor = collection.find(query);
	    while (cursor.hasNext()) {
	    	doc = cursor.next();
	    	docs[Index] = doc;
	    	Index = Index++;
	    }

	    return docs;
	  }
	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getCompanyID() {
		return CompanyID;
	}

	public void setCompanyID(String companyID) {
		CompanyID = companyID;
	}

	public String getAccountID() {
		return AccountID;
	}

	public void setAccountID(String accountID) {
		AccountID = accountID;
	}

	public String getAccountName() {
		return AccountName;
	}

	public void setAccountName(String accountName) {
		AccountName = accountName;
	}

	public String getActivityID() {
		return ActivityID;
	}

	public void setActivityID(String activityID) {
		ActivityID = activityID;
	}

	public String getActivityName() {
		return ActivityName;
	}

	public void setActivityName(String activityName) {
		ActivityName = activityName;
	}
	

}
