package com.pivotpayables.concurplatform;
/**
 * @author John Toman
 * 6/20/2016
 * base class for a location
 * 
 * Moved all Location related methods in MongoDBFunctions to the Locations class
 * 
 * Made all properties fields by making them private
 *
 */
import static java.lang.System.out;

import java.util.ArrayList;
import java.util.Random;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.DBCollection;




public class Location {
	private String ID;
	private String DisplayName;// formatted as: City, State Country Name
	private String City;
	private String State;
	private String CountryCode;
	private String CountryName;
	
	public Location(){}// no args constructor

	public void display () {
		if (ID != null) {
			out.println("ID: " + ID);
		}
		if (DisplayName != null) {
			out.println("Display Name: " + DisplayName);
		}
		if (City != null) {
			out.println("City: " + City);
		}
		if (State != null) {
			out.println("State: " + State);
		}
		if (CountryCode != null) {
			out.println("Country Code: " + CountryCode);
		}
		if (CountryName != null) {
			out.println("Country Name: " + CountryName);
		}

	}
	public BasicDBObject getDocument () {//this method returns the location as a BasicDBObject that can be added to a MongoDB
		BasicDBObject myDoc = new BasicDBObject("ID",this.ID);
		myDoc.append("DisplayName",this.DisplayName);		
		myDoc.append("City", this.City);
		myDoc.append("State",this.State);
		myDoc.append("CountryCode",this.CountryCode);
		myDoc.append("CountryName", this.CountryName);
		return myDoc;
	}
	public Location doctoLocation (DBObject doc) {
		String ID = "";
		if (doc.get("ID") != null){
			ID = doc.get("ID").toString();
		}
		String DisplayName = "";
		if (doc.get("DisplayName") != null){
			DisplayName = doc.get("DisplayName").toString();
		}
		String City = "";
		if (doc.get("City") != null){
			City = doc.get("City").toString();
		}
		String State = "";
		if (doc.get("State") != null){
			State = doc.get("State").toString();
		}
		String CountryCode = "";
		if (doc.get("CountryCode") != null){
			CountryCode = doc.get("CountryCode").toString();
		}
		String CountryName = "";
		if (doc.get("CountryName") != null){
			CountryName = doc.get("CountryName").toString();
		}

		Location myLocation = new Location();
		myLocation.setID(ID);
		myLocation.setDisplayName(DisplayName);
		myLocation.setCity(City);
		myLocation.setState(State);
		myLocation.setCountryCode(CountryCode);
		myLocation.setCountryName(CountryName);


		return myLocation;
	}
	public Location getLocation(DBCollection myCollection, String id){//get the document with the specified LocationID, and then converts this document into a Location object
		
	    BasicDBObject query= new BasicDBObject("ID", id);
	    DBCursor cursor = myCollection.find(query);
		DBObject doc = cursor.next();
		Location location = doctoLocation(doc);

	    return location;
	 }
	public Location getCity(DBCollection myCollection, String city){//get the document with the specified City, and then converts this document into a Location object

	    BasicDBObject query= new BasicDBObject("City", city);
	    DBCursor cursor = myCollection.find(query);
		DBObject doc = cursor.next();
		Location location = doctoLocation(doc);

	    return location;
	  }
	public Location getRandomLocation(DBCollection myCollection) {// selects a random location from the Locations collection
		
		Random myRandom = new Random();
		
		//Get Location documents
	    ArrayList<DBObject> Docs = new ArrayList<DBObject>();//an ArrayList of Location document elements
	    DBCursor cursor = myCollection.find();

	    while(cursor.hasNext()) {
	      DBObject doc = cursor.next();
	      Docs.add(doc);
	    }
	    Location location=null;//placeholder for the Location object
	    DBObject doc = null;//placeholder for a Location document

	    int locationcount = Docs.size();//the number of documents in the ArrayList of Location documents
	    int index = myRandom.nextInt(locationcount);//select a random integer between 0 and the number of elements in the ArrayList of Location elements
	    doc = Docs.get(index);//get the Location document for this element
	    location = doctoLocation(doc);//convert the document into a Location object

		return location;
	}
	
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getDisplayName() {
		return DisplayName;
	}
	public void setDisplayName(String displayName) {
		DisplayName = displayName;
	}
	public String getCity() {
		return City;
	}
	public void setCity(String city) {
		City = city;
	}
	public String getState() {
		return State;
	}
	public void setState(String state) {
		State = state;
	}
	public String getCountryCode() {
		return CountryCode;
	}
	public void setCountryCode(String countryCode) {
		CountryCode = countryCode;
	}
	public String getCountryName() {
		return CountryName;
	}
	public void setCountryName(String countryName) {
		CountryName = countryName;
	}

}
