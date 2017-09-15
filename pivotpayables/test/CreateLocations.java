/**
 * 
 */
package com.pivotpayables.test;

/**
 * @author John Toman
 * 6/1/2105
 * This program creates location documents stored in the MongoDB collection Locations in the Company_Data database.
 * These location documents 
 *
 */
import static java.lang.System.out;

import java.util.ArrayList;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.pivotpayables.concurplatform.Location;
import com.pivotpayables.expensesimulator.GUID;
import com.pivotpayables.expensesimulator.MongoDBFunctions;

public class CreateLocations {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Location location;
		ArrayList<Location> locations = new ArrayList<Location>();//an ArrayList of Location elements where each element represents a possible destination for a trip
		
		String host ="localhost";//MongoDB server host
		int port = 27017;//MongoDB server port
        MongoDBFunctions myFunctions = new MongoDBFunctions(host, port, "Company_Data", "Locations");//create MongoDB Client for the Locations collection
        int StoreWhere = 2;//where to store the locations this program creates: 1 = console, 2 = MongoDB, 3 =mySQL

		location = new Location();
		location.setID(GUID.getGUID(2));
		location.setCity("San Francisco");
		location.setState("CA");
		location.setCountryCode("US");
		location.setCountryName("United States");
		location.setDisplayName(location.getCity() + ", " + location.getState() + " " + location.getCountryName());
		locations.add(location);
		
		location = new Location();
		location.setID(GUID.getGUID(2));
		location.setCity("Los Angeles");
		location.setState("CA");
		location.setCountryCode("US");
		location.setCountryName("United States");
		location.setDisplayName(location.getCity() + ", " + location.getState() + " " + location.getCountryName());
		locations.add(location);

		location = new Location();
		location.setID(GUID.getGUID(2));
		location.setCity("Anaheim");
		location.setState("CA");
		location.setCountryCode("US");
		location.setCountryName("United States");
		location.setDisplayName(location.getCity() + ", " + location.getState() + " " + location.getCountryName());
		locations.add(location);
		
		location = new Location();
		location.setID(GUID.getGUID(2));
		location.setCity("San Diego");
		location.setState("CA");
		location.setCountryCode("US");
		location.setCountryName("United States");
		location.setDisplayName(location.getCity() + ", " + location.getState() + " " + location.getCountryName());
		locations.add(location);
		
		location = new Location();
		location.setID(GUID.getGUID(2));
		location.setCity("Phoenix");
		location.setState("AZ");
		location.setCountryCode("US");
		location.setCountryName("United States");
		location.setDisplayName(location.getCity() + ", " + location.getState() + " " + location.getCountryName());
		locations.add(location);
		
		location = new Location();
		location.setID(GUID.getGUID(2));
		location.setCity("Las Vegas");
		location.setState("NV");
		location.setCountryCode("US");
		location.setCountryName("United States");
		location.setDisplayName(location.getCity() + ", " + location.getState() + " " + location.getCountryName());
		locations.add(location);
		
		location = new Location();
		location.setID(GUID.getGUID(2));
		location.setCity("Sacramento");
		location.setState("CA");
		location.setCountryCode("US");
		location.setCountryName("United States");
		location.setDisplayName(location.getCity() + ", " + location.getState() + " " + location.getCountryName());
		locations.add(location);
		
		location = new Location();
		location.setID(GUID.getGUID(2));
		location.setCity("Seattle");
		location.setState("WA");
		location.setCountryCode("US");
		location.setCountryName("United States");
		location.setDisplayName(location.getCity() + ", " + location.getState() + " " + location.getCountryName());
		locations.add(location);
		
		location = new Location();
		location.setID(GUID.getGUID(2));
		location.setCity("Bellevue");
		location.setState("WA");
		location.setCountryCode("US");
		location.setCountryName("United States");
		location.setDisplayName(location.getCity() + ", " + location.getState() + " " + location.getCountryName());
		locations.add(location);
		
		location = new Location();
		location.setID(GUID.getGUID(2));
		location.setCity("Salt Lake City");
		location.setState("UT");
		location.setCountryCode("US");
		location.setCountryName("United States");
		location.setDisplayName(location.getCity() + ", " + location.getState() + " " + location.getCountryName());
		locations.add(location);
		
		location = new Location();
		location.setID(GUID.getGUID(2));
		location.setCity("Boise");
		location.setState("ID");
		location.setCountryCode("US");
		location.setCountryName("United States");
		location.setDisplayName(location.getCity() + ", " + location.getState() + " " + location.getCountryName());
		locations.add(location);
		
		location = new Location();
		location.setID(GUID.getGUID(2));
		location.setCity("Denver");
		location.setState("CO");
		location.setCountryCode("US");
		location.setCountryName("United States");
		location.setDisplayName(location.getCity() + ", " + location.getState() + " " + location.getCountryName());
		locations.add(location);
		
		location = new Location();
		location.setID(GUID.getGUID(2));
		location.setCity("Dallas");
		location.setState("TX");
		location.setCountryCode("US");
		location.setCountryName("United States");
		location.setDisplayName(location.getCity() + ", " + location.getState() + " " + location.getCountryName());
		locations.add(location);
		
		location = new Location();
		location.setID(GUID.getGUID(2));
		location.setCity("San Antonio");
		location.setState("TX");
		location.setCountryCode("US");
		location.setCountryName("United States");
		location.setDisplayName(location.getCity() + ", " + location.getState() + " " + location.getCountryName());
		locations.add(location);
		
		location = new Location();
		location.setID(GUID.getGUID(2));
		location.setCity("Austin");
		location.setState("TX");
		location.setCountryCode("US");
		location.setCountryName("United States");
		location.setDisplayName(location.getCity() + ", " + location.getState() + " " + location.getCountryName());
		locations.add(location);

		location = new Location();
		location.setID(GUID.getGUID(2));
		location.setCity("Houston");
		location.setState("TX");
		location.setCountryCode("US");
		location.setCountryName("United States");
		location.setDisplayName(location.getCity() + ", " + location.getState() + " " + location.getCountryName());
		locations.add(location);
		
		location = new Location();
		location.setID(GUID.getGUID(2));
		location.setCity("New Orleans");
		location.setState("LA");
		location.setCountryCode("US");
		location.setCountryName("United States");
		location.setDisplayName(location.getCity() + ", " + location.getState() + " " + location.getCountryName());
		locations.add(location);
		
		location = new Location();
		location.setID(GUID.getGUID(2));
		location.setCity("St. Louis");
		location.setState("MO");
		location.setCountryCode("US");
		location.setCountryName("United States");
		location.setDisplayName(location.getCity() + ", " + location.getState() + " " + location.getCountryName());
		locations.add(location);
		
		location = new Location();
		location.setID(GUID.getGUID(2));
		location.setCity("Kansas City");
		location.setState("MO");
		location.setCountryCode("US");
		location.setCountryName("United States");
		location.setDisplayName(location.getCity() + ", " + location.getState() + " " + location.getCountryName());
		locations.add(location);
		
		location = new Location();
		location.setID(GUID.getGUID(2));
		location.setCity("Minneapolis");
		location.setState("MN");
		location.setCountryCode("US");
		location.setCountryName("United States");
		location.setDisplayName(location.getCity() + ", " + location.getState() + " " + location.getCountryName());
		locations.add(location);
		
		location = new Location();location.setID(GUID.getGUID(2));
		location.setCity("Des Moines");
		location.setState("IA");
		location.setCountryCode("US");
		location.setCountryName("United States");
		location.setDisplayName(location.getCity() + ", " + location.getState() + " " + location.getCountryName());
		locations.add(location);
		
		location = new Location();
		location.setID(GUID.getGUID(2));
		location.setCity("Madison");
		location.setState("WI");
		location.setCountryCode("US");
		location.setCountryName("United States");
		location.setDisplayName(location.getCity() + ", " + location.getState() + " " + location.getCountryName());
		locations.add(location);
		
		location = new Location();
		location.setID(GUID.getGUID(2));
		location.setCity("Milwaukee");
		location.setState("WI");
		location.setCountryCode("US");
		location.setCountryName("United States");
		location.setDisplayName(location.getCity() + ", " + location.getState() + " " + location.getCountryName());
		locations.add(location);
		
		location = new Location();
		location.setID(GUID.getGUID(2));
		location.setCity("Chicago");
		location.setState("IL");
		location.setCountryCode("US");
		location.setCountryName("United States");
		location.setDisplayName(location.getCity() + ", " + location.getState() + " " + location.getCountryName());
		locations.add(location);
		
		location = new Location();
		location.setID(GUID.getGUID(2));
		location.setCity("Detroit");
		location.setState("MI");
		location.setCountryCode("US");
		location.setCountryName("United States");
		location.setDisplayName(location.getCity() + ", " + location.getState() + " " + location.getCountryName());
		locations.add(location);
		
		location = new Location();
		location.setID(GUID.getGUID(2));
		location.setCity("Cleavland");
		location.setState("OH");
		location.setCountryCode("US");
		location.setCountryName("United States");
		location.setDisplayName(location.getCity() + ", " + location.getState() + " " + location.getCountryName());
		locations.add(location);
		
		location = new Location();
		location.setID(GUID.getGUID(2));
		location.setCity("Cincinatti");
		location.setState("OH");
		location.setCountryCode("US");
		location.setCountryName("United States");
		location.setDisplayName(location.getCity() + ", " + location.getState() + " " + location.getCountryName());
		locations.add(location);
		
		location = new Location();
		location.setID(GUID.getGUID(2));
		location.setCity("Pittsburg");
		location.setState("PA");
		location.setCountryCode("US");
		location.setCountryName("United States");
		location.setDisplayName(location.getCity() + ", " + location.getState() + " " + location.getCountryName());
		locations.add(location);
		
		location = new Location();
		location.setID(GUID.getGUID(2));
		location.setCity("Philadelphia");
		location.setState("PA");
		location.setCountryCode("US");
		location.setCountryName("United States");
		location.setDisplayName(location.getCity() + ", " + location.getState() + " " + location.getCountryName());
		locations.add(location);
		
		location = new Location();
		location.setID(GUID.getGUID(2));
		location.setCity("Boston");
		location.setState("MA");
		location.setCountryCode("US");
		location.setCountryName("United States");
		location.setDisplayName(location.getCity() + ", " + location.getState() + " " + location.getCountryName());
		locations.add(location);
		
		location = new Location();
		location.setID(GUID.getGUID(2));
		location.setCity("Indinapolis");
		location.setState("IN");
		location.setCountryCode("US");
		location.setCountryName("United States");
		location.setDisplayName(location.getCity() + ", " + location.getState() + " " + location.getCountryName());
		locations.add(location);
		
		location = new Location();
		location.setID(GUID.getGUID(2));
		location.setCity("Portland");
		location.setState("ME");
		location.setCountryCode("US");
		location.setCountryName("United States");
		location.setDisplayName(location.getCity() + ", " + location.getState() + " " + location.getCountryName());
		locations.add(location);
		
		location = new Location();
		location.setID(GUID.getGUID(2));
		location.setCity("Buffalo");
		location.setState("NY");
		location.setCountryCode("US");
		location.setCountryName("United States");
		location.setDisplayName(location.getCity() + ", " + location.getState() + " " + location.getCountryName());
		locations.add(location);
		
		location = new Location();location.setID(GUID.getGUID(2));
		location.setCity("Syracuse");
		location.setState("NY");
		location.setCountryCode("US");
		location.setCountryName("United States");
		location.setDisplayName(location.getCity() + ", " + location.getState() + " " + location.getCountryName());
		locations.add(location);
		
		location = new Location();
		location.setID(GUID.getGUID(2));
		location.setCity("Albany");
		location.setState("NY");
		location.setCountryCode("US");
		location.setCountryName("United States");
		location.setDisplayName(location.getCity() + ", " + location.getState() + " " + location.getCountryName());
		locations.add(location);
		
		location = new Location();
		location.setID(GUID.getGUID(2));
		location.setCity("New York");
		location.setState("NY");
		location.setCountryCode("US");
		location.setCountryName("United States");
		location.setDisplayName(location.getCity() + ", " + location.getState() + " " + location.getCountryName());
		locations.add(location);
		
		location = new Location();
		location.setID(GUID.getGUID(2));
		location.setCity("Newark");
		location.setState("NJ");
		location.setCountryCode("US");
		location.setCountryName("United States");
		location.setDisplayName(location.getCity() + ", " + location.getState() + " " + location.getCountryName());
		locations.add(location);
		
		location = new Location();
		location.setID(GUID.getGUID(2));
		location.setCity("Brooklyn");
		location.setState("NY");
		location.setCountryCode("US");
		location.setCountryName("United States");
		location.setDisplayName(location.getCity() + ", " + location.getState() + " " + location.getCountryName());
		locations.add(location);
		
		location = new Location();
		location.setID(GUID.getGUID(2));
		location.setCity("Baltimore");
		location.setState("MD");
		location.setCountryCode("US");
		location.setCountryName("United States");
		location.setDisplayName(location.getCity() + ", " + location.getState() + " " + location.getCountryName());
		locations.add(location);
		
		location = new Location();
		location.setID(GUID.getGUID(2));
		location.setCity("Washington");
		location.setState("DC");
		location.setCountryCode("US");
		location.setCountryName("United States");
		location.setDisplayName(location.getCity() + ", " + location.getState() + " " + location.getCountryName());
		locations.add(location);
		
		location = new Location();
		location.setID(GUID.getGUID(2));
		location.setCity("Lexington");
		location.setState("KY");
		location.setCountryCode("US");
		location.setCountryName("United States");
		location.setDisplayName(location.getCity() + ", " + location.getState() + " " + location.getCountryName());
		locations.add(location);
		
		location = new Location();location.setID(GUID.getGUID(2));
		location.setCity("Trenton");
		location.setState("NJ");
		location.setCountryCode("US");
		location.setCountryName("United States");
		location.setDisplayName(location.getCity() + ", " + location.getState() + " " + location.getCountryName());
		locations.add(location);
		
		location = new Location();location.setID(GUID.getGUID(2));
		location.setCity("Fairfax");
		location.setState("VA");
		location.setCountryCode("US");
		location.setCountryName("United States");
		location.setDisplayName(location.getCity() + ", " + location.getState() + " " + location.getCountryName());
		locations.add(location);
		
		location = new Location();
		location.setID(GUID.getGUID(2));
		location.setCity("Richmond");
		location.setState("VA");
		location.setCountryCode("US");
		location.setCountryName("United States");
		location.setDisplayName(location.getCity() + ", " + location.getState() + " " + location.getCountryName());
		locations.add(location);
		
		location = new Location();
		location.setID(GUID.getGUID(2));
		location.setCity("Newport News");
		location.setState("VA");
		location.setCountryCode("US");
		location.setCountryName("United States");
		location.setDisplayName(location.getCity() + ", " + location.getState() + " " + location.getCountryName());
		locations.add(location);
		
		location = new Location();
		location.setID(GUID.getGUID(2));
		location.setCity("Charlotte");
		location.setState("NC");
		location.setCountryCode("US");
		location.setCountryName("United States");
		location.setDisplayName(location.getCity() + ", " + location.getState() + " " + location.getCountryName());
		locations.add(location);
		
		location = new Location();
		location.setID(GUID.getGUID(2));
		location.setCity("Charleston");
		location.setState("SC");
		location.setCountryCode("US");
		location.setCountryName("United States");
		location.setDisplayName(location.getCity() + ", " + location.getState() + " " + location.getCountryName());
		locations.add(location);
		
		location = new Location();
		location.setID(GUID.getGUID(2));
		location.setCity("Atlanta");
		location.setState("GA");
		location.setCountryCode("US");
		location.setCountryName("United States");
		location.setDisplayName(location.getCity() + ", " + location.getState() + " " + location.getCountryName());
		locations.add(location);
		
		location = new Location();
		location.setID(GUID.getGUID(2));
		location.setCity("Jacksonville");
		location.setState("FL");
		location.setCountryCode("US");
		location.setCountryName("United States");
		location.setDisplayName(location.getCity() + ", " + location.getState() + " " + location.getCountryName());
		locations.add(location);
		
		location = new Location();
		location.setID(GUID.getGUID(2));
		location.setCity("Miami");
		location.setState("FL");
		location.setCountryCode("US");
		location.setCountryName("United States");
		location.setDisplayName(location.getCity() + ", " + location.getState() + " " + location.getCountryName());
		locations.add(location);
		
		location = new Location();
		location.setID(GUID.getGUID(2));
		location.setCity("Tampa Bay");
		location.setState("FL");
		location.setCountryCode("US");
		location.setCountryName("United States");
		location.setDisplayName(location.getCity() + ", " + location.getState() + " " + location.getCountryName());
		locations.add(location);
		
		location = new Location();
		location.setID(GUID.getGUID(2));
		location.setCity("Burmingham");
		location.setState("AL");
		location.setCountryCode("US");
		location.setCountryName("United States");
		location.setDisplayName(location.getCity() + ", " + location.getState() + " " + location.getCountryName());
		locations.add(location);
		
		location = new Location();
		location.setID(GUID.getGUID(2));
		location.setCity("Vancouver");
		location.setState("BC");
		location.setCountryCode("CA");
		location.setCountryName("Canada");
		location.setDisplayName(location.getCity() + ", " + location.getState() + " " + location.getCountryName());
		locations.add(location);
		
		location = new Location();location.setID(GUID.getGUID(2));
		location.setCity("Victoria");
		location.setState("BC");
		location.setCountryCode("CA");
		location.setCountryName("Canada");
		location.setDisplayName(location.getCity() + ", " + location.getState() + " " + location.getCountryName());
		locations.add(location);
		
		location = new Location();
		location.setID(GUID.getGUID(2));
		location.setCity("Edmonton");
		location.setState("AB");
		location.setCountryCode("CA");
		location.setCountryName("Canada");
		location.setDisplayName(location.getCity() + ", " + location.getState() + " " + location.getCountryName());
		locations.add(location);

		location = new Location();
		location.setID(GUID.getGUID(2));
		location.setCity("Calgary");
		location.setState("AB");
		location.setCountryCode("CA");
		location.setCountryName("Canada");
		location.setDisplayName(location.getCity() + ", " + location.getState() + " " + location.getCountryName());
		locations.add(location);

		location = new Location();
		location.setID(GUID.getGUID(2));
		location.setCity("Winnipeg");
		location.setState("MB");
		location.setCountryCode("CA");
		location.setCountryName("Canada");
		location.setDisplayName(location.getCity() + ", " + location.getState() + " " + location.getCountryName());
		locations.add(location);

		location = new Location();
		location.setID(GUID.getGUID(2));
		location.setCity("Toronto");
		location.setState("ON");
		location.setCountryCode("CA");
		location.setCountryName("Canada");
		location.setDisplayName(location.getCity() + ", " + location.getState() + " " + location.getCountryName());
		locations.add(location);

		location = new Location();
		location.setID(GUID.getGUID(2));
		location.setCity("Ottawa");
		location.setState("ON");
		location.setCountryCode("CA");
		location.setCountryName("Canada");
		location.setDisplayName(location.getCity() + ", " + location.getState() + " " + location.getCountryName());
		locations.add(location);


		location = new Location();
		location.setID(GUID.getGUID(2));
		location.setCity("Hamilton");
		location.setState("ON");
		location.setCountryCode("CA");
		location.setCountryName("Canada");
		location.setDisplayName(location.getCity() + ", " + location.getState() + " " + location.getCountryName());
		locations.add(location);

		location = new Location();
		location.setID(GUID.getGUID(2));
		location.setCity("Kitchener");
		location.setState("ON");
		location.setCountryCode("CA");
		location.setCountryName("Canada");
		location.setDisplayName(location.getCity() + ", " + location.getState() + " " + location.getCountryName());
		locations.add(location);

		location = new Location();
		location.setID(GUID.getGUID(2));
		location.setCity("London");
		location.setState("ON");
		location.setCountryCode("CA");
		location.setCountryName("Canada");
		location.setDisplayName(location.getCity() + ", " + location.getState() + " " + location.getCountryName());
		locations.add(location);

		location = new Location();
		location.setID(GUID.getGUID(2));
		location.setCity("Montreal");
		location.setState("QB");
		location.setCountryCode("CA");
		location.setCountryName("Canada");
		location.setDisplayName(location.getCity() + ", " + location.getState() + " " + location.getCountryName());
		locations.add(location);

		location = new Location();
		location.setID(GUID.getGUID(2));
		location.setCity("Quebec City");
		location.setState("QB");
		location.setCountryCode("CA");
		location.setCountryName("Canada");
		location.setDisplayName(location.getCity() + ", " + location.getState() + " " + location.getCountryName());
		locations.add(location);
		
		location = new Location();
		location.setID(GUID.getGUID(2));
		location.setCity("Sydney");
		location.setState("NSW");
		location.setCountryCode("AU");
		location.setCountryName("Australia");
		location.setDisplayName(location.getCity() + ", " + location.getState() + " " + location.getCountryName());
		locations.add(location);
		
		location = new Location();
		location.setID(GUID.getGUID(2));
		location.setCity("London");
		location.setState("ENG");
		location.setCountryCode("GB");
		location.setCountryName("United Kingdom");
		location.setDisplayName(location.getCity() + ", " + location.getState() + " " + location.getCountryName());
		locations.add(location);
		
		int locationcount = locations.size();
		for (int index=0; index < locationcount; index++) {//iterate for each location in the ArrayList of location elements
			location = locations.get(index);//get the location for this iteration
			switch (StoreWhere) {//store the location according to the StoreWhere value
			case 1: //print to console
				out.println("----------------------");
				location.display();	
				break;
			case 2: //MongoDB
				BasicDBObject myDoc = location.getDocument();//convert the Company object into a MongoDB document
				DBCollection myCollection = myFunctions.getCollection();
				myFunctions.addDoc(myCollection, myDoc);
				break;
			case 3://MySQL
				//Stub for code to store the company to the Location table in MySQL
				break;
			}//switch block
		}//index loop

	}

}
