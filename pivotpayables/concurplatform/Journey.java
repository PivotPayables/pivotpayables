package com.pivotpayables.concurplatform;

/**
 * @author John Toman
 * 6/21/2016
 * base class for a Journey. A Journey object holds the details for the journey for a mileage expense
 * 
 * Moved doctoJourney
 *
 */


import static java.lang.System.out;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Journey {
	
	String ID;// GUID in MongoDB for this journey
	String EntryID;// the expense entry the journey is associated
	
	@JsonProperty
	String StartLocation;// where the journey began

	@JsonProperty
	String EndLocation;// where the journey ended

	@JsonProperty
	int BusinessDistance;// the portion of the journey that was for business
	
	//@JsonProperty
	int PersonalDistance;// the portion of the journey that was for personal

	@JsonProperty 
	String UnitOfMeasure;// M for miles, or K for kilometers

	@JsonProperty
	int OdometerStart;// the reading at the beginning of the journey (company mileage expenses only)

	@JsonProperty
	int OdometerEnd;// the reading at the end of the journey (company mileage expenses only)

	@JsonProperty
	String VehicleID;// GUID for the company car used (company mileage expenses only)

	@JsonProperty
	int NumberOfPassengers;// the number of passengers including the driver (company mileage expenses only)@XmlElement

	
	public Journey() {};// no args constructor required by Jackson

	public void display () {
		if (ID != null) {
			out.println("ID: " + ID);
		}
		if (StartLocation != null) {
			out.println("Start Location: " + StartLocation);
		}
		if (EndLocation != null) {
			out.println("EndLocation: " + EndLocation);
		}
		if (EntryID != null) {
			out.println("EntryID: " + EntryID);
		}
		if (VehicleID != null) {
			out.println("Vehicle ID: " + VehicleID);
		}
		if (NumberOfPassengers >0) {
			out.println("Passengers: " + NumberOfPassengers);
		}
		if (OdometerStart >0) {
			out.println("Odometer Begin: " + OdometerStart);
		}
		if (OdometerEnd >0) {
			out.println("Odometer End: " + OdometerEnd);
		}
	
		if (BusinessDistance >0) {
			if (UnitOfMeasure.equals("K")) {
				out.println("Business Distance: " + BusinessDistance + " Kilometers");
			} else if (UnitOfMeasure.equals("M")) {
				out.println("Business Distance: " + BusinessDistance + " Miles");
			}
		}
		if (PersonalDistance >0) {
			if (UnitOfMeasure.equals("K")) {
				out.println("PersonalDistance: " + PersonalDistance + " Kilometers");
			} else if (UnitOfMeasure.equals("M")) {
				out.println("PersonalDistance: " + PersonalDistance + " Miles");
			}
		}
	}
	public BasicDBObject getDocument () {//this method returns the journey as a BasicDBObject that can be added to a MongoDB
		BasicDBObject myDoc = new BasicDBObject("ID",this.ID);
		myDoc.append("StartLocation",this.StartLocation);		
		myDoc.append("EndLocation", this.EndLocation);
		myDoc.append("EntryID",this.EntryID);
		myDoc.append("VehicleID",this.VehicleID);
		myDoc.append("Passengers", this.NumberOfPassengers);
		myDoc.append("OdometerBegin", this.OdometerStart);
		myDoc.append("OdometerEnd",this.OdometerEnd);
		myDoc.append("BusinessDistance",this.BusinessDistance);
		myDoc.append("PersonalDistance",this.PersonalDistance);
		myDoc.append("UnitOfMeasure",this.UnitOfMeasure);
	
		return myDoc;
	}
	public Journey doctoJourney (DBObject doc) {
		String ID = null;
		String EntryID = null;
		String StartLocation = null;
		String EndLocation = null;
		String VehicleID = null;
		String UnitOfMeasure = null;
		int BusinessDistance=0;
		int PersonalDistance=0;
		int OdometerStart=0;
		int OdometerEnd=0;
		int NumberOfPassengers=0;
		
		if (doc.get("ID") != null){
			ID= doc.get("ID").toString();
		}
		if (doc.get("EntryID") != null){
			EntryID = doc.get("EntryID").toString();
		}
		if (doc.get("StartLocation") != null){
			StartLocation = doc.get("StartLocation").toString();
		}
		if (doc.get("EndLocation") != null){
			EndLocation = doc.get("EndLocation").toString();
		}
		if (doc.get("VehicleID") != null){
			VehicleID = doc.get("VehicleID").toString();
		}

		if (doc.get("UnitOfMeasure") != null){
			UnitOfMeasure = doc.get("UnitOfMeasure").toString();
		}
		if (doc.get("BusinessDistance") != null){
			BusinessDistance = Integer.parseInt(doc.get("BusinessDistance").toString());
		}
		if (doc.get("PersonalDistance") != null){
			PersonalDistance = Integer.parseInt(doc.get("PersonalDistance").toString());
		}
		if (doc.get("OdometerStart") != null){
			OdometerStart = Integer.parseInt(doc.get("OdometerStart").toString());
		}
		if (doc.get("OdometerEnd") != null){
			OdometerEnd = Integer.parseInt(doc.get("OdometerEnd").toString());
		}
		if (doc.get("NumberOfPassengers") != null){
			NumberOfPassengers = Integer.parseInt(doc.get("NumberOfPassengers").toString());
		}
		
		Journey myJourney = new Journey();
		myJourney.setID(ID);
		myJourney.setEntryID(EntryID);
		myJourney.setStartLocation(StartLocation);
		myJourney.setEndLocation(EndLocation);
		myJourney.setBusinessDistance(BusinessDistance);
		myJourney.setPersonalDistance(PersonalDistance);
		myJourney.setOdometerStart(OdometerStart);
		myJourney.setOdometerEnd(OdometerEnd);
		myJourney.setNumberOfPassengers(NumberOfPassengers);
		myJourney.setVehicleID(VehicleID);
		myJourney.setUnitOfMeasure(UnitOfMeasure);

		return myJourney;
	}
	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getEntryID() {
		return EntryID;
	}

	public void setEntryID(String entryID) {
		EntryID = entryID;
	}

	public String getStartLocation() {
		return StartLocation;
	}

	public void setStartLocation(String startLocation) {
		StartLocation = startLocation;
	}

	public String getEndLocation() {
		return EndLocation;
	}

	public void setEndLocation(String endLocation) {
		EndLocation = endLocation;
	}

	public int getBusinessDistance() {
		return BusinessDistance;
	}

	public void setBusinessDistance(int businessDistance) {
		BusinessDistance = businessDistance;
	}

	public int getPersonalDistance() {
		return PersonalDistance;
	}

	public void setPersonalDistance(int personalDistance) {
		PersonalDistance = personalDistance;
	}

	public String getUnitOfMeasure() {
		return UnitOfMeasure;
	}

	public void setUnitOfMeasure(String unitOfMeasure) {
		UnitOfMeasure = unitOfMeasure;
	}

	public int getOdometerStart() {
		return OdometerStart;
	}

	public void setOdometerStart(int odometerStart) {
		OdometerStart = odometerStart;
	}

	public int getOdometerEnd() {
		return OdometerEnd;
	}

	public void setOdometerEnd(int odometerEnd) {
		OdometerEnd = odometerEnd;
	}

	public String getVehicleID() {
		return VehicleID;
	}

	public void setVehicleID(String vehicleID) {
		VehicleID = vehicleID;
	}

	public int getNumberOfPassengers() {
		return NumberOfPassengers;
	}

	public void setNumberOfPassengers(int numberOfPassengers) {
		NumberOfPassengers = numberOfPassengers;
	}
	
}