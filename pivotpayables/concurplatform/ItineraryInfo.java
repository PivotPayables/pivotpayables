package com.pivotpayables.concurplatform;

/**
 * @author John Toman
 * 
 * 4/5/2017
 * 
 * This is the base class for a Concur Travel v1.0 ItineraryInfo object.
 * 
 * An ItineraryInfo object provides the summary or header for a trip.
 *
 */

import static java.lang.System.out;

import java.util.Date;
import java.util.Locale;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;


import java.text.SimpleDateFormat;


@XmlRootElement (name="ItineraryInfo")
@XmlAccessorType (XmlAccessType.FIELD)
public class ItineraryInfo {
	
	
	@XmlElement
	String BookingSource;
	
	@XmlElement
	String Passengers;
	
	@XmlElement
	String TravelerFirst;
	
	@XmlElement
	String TravelerLast;
	
	@XmlElement
	String RecordLocator;
	
	@XmlElement(name = "TripId")
	String TripID;// the unique identifier for this trip in the Concur Itinerary Service
	
	@XmlElement
	String TripName;

	@XmlElement(name = "StartDateLocal")
	Date StartDate;// the date the trip begins.  This is the earliest start date of any booking in this trip

	@XmlElement(name = "EndDateLocal")
	Date EndDate;// the date the trip begins.  This is the latest end date of any booking in this trip

	@XmlElement(name = "id")
	String IntineraryInfoUrl;// URL to GET for this trip's detail
	
	@XmlElement(name = "UserLoginId")
	String OwnerLoginID;	

	@XmlElement (name = "DateModifiedUtc")
	Date LastModified;// the date this trip was last modified
	
	public String getOwnerLoginID() {
		return OwnerLoginID;
	}
	public void setOwnerLoginID(String ownerLoginID) {
		OwnerLoginID = ownerLoginID;
	}
	
	String EmployeeDisplayName;// the employee who incurred the expense
	String EmployeeFirstName;
	String EmployeeMiddleInitial;
	String EmployeeLastName;

	public void display() {
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
		String strDate = null;

		out.println("Trip Name: " + TripName);
		out.println("TripID " + TripID);
		if (StartDate != null) {
			strDate = sdf.format(StartDate);	 
			out.println("Start Date: " + strDate);
		}
		if (EndDate != null) {
			strDate = sdf.format(EndDate);	 
			out.println("End Date: " + strDate);
		}
		if (LastModified != null) {
			strDate = sdf.format(LastModified);	 
			out.println("Last Modified Date: " + strDate);
		}
		out.println("Trip Owner Login ID " + OwnerLoginID);
		
		if (Passengers != null){
			out.println("Traveler Name: " + Passengers);
		}

		if (TravelerFirst != null){
			out.println("First Name: " + TravelerFirst);
		}

		if (TravelerLast != null){
			out.println("Last Name: " + TravelerLast);
		}
		
		if (BookingSource != null){
			out.println("Booking Source: " + BookingSource);
		}

	}
	
	public String getBookingSource() {
		return BookingSource;
	}
	public void setBookingSource(String bookingSource) {
		BookingSource = bookingSource;
	}
	public String getPassengers() {
		return Passengers;
	}
	public void setPassengers(String passengers) {
		Passengers = passengers;
	}
	public String getTravelerFirst() {
		return TravelerFirst;
	}
	public void setTravelerFirst(String travelerFirst) {
		TravelerFirst = travelerFirst;
	}
	public String getTravelerLast() {
		return TravelerLast;
	}
	public void setTravelerLast(String travelerLast) {
		TravelerLast = travelerLast;
	}
	public String getRecordLocator() {
		return RecordLocator;
	}
	public void setRecordLocator(String recordLocator) {
		RecordLocator = recordLocator;
	}
	public String getTripID() {
		return TripID;
	}
	public void setTripID(String triptID) {
		TripID = triptID;
	}
	public String getTripName() {
		return TripName;
	}
	public void setTripName(String tripName) {
		TripName = tripName;
	}
	public Date getStartDate() {
		return StartDate;
	}
	public void setStartDate(Date startDate) {
		StartDate = startDate;
	}
	public Date getEndDate() {
		return EndDate;
	}
	public void setEndDate(Date endDate) {
		EndDate = endDate;
	}
	public String getIntineraryInfoUrl() {
		return IntineraryInfoUrl;
	}
	public void setIntineraryInfoUrl(String intineraryInfoUrl) {
		IntineraryInfoUrl = intineraryInfoUrl;
	}
	public Date getLastModified() {
		return LastModified;
	}
	public void setLastModified(Date lastModified) {
		LastModified = lastModified;
	}
	public String getEmployeeDisplayName() {
		return EmployeeDisplayName;
	}
	public void setEmployeeDisplayName(String employeeDisplayName) {
		EmployeeDisplayName = employeeDisplayName;
	}
	public String getEmployeeFirstName() {
		return EmployeeFirstName;
	}
	public void setEmployeeFirstName(String employeeFirstName) {
		EmployeeFirstName = employeeFirstName;
	}
	public String getEmployeeMiddleInitial() {
		return EmployeeMiddleInitial;
	}
	public void setEmployeeMiddleInitial(String employeeMiddleInitial) {
		EmployeeMiddleInitial = employeeMiddleInitial;
	}
	public String getEmployeeLastName() {
		return EmployeeLastName;
	}
	public void setEmployeeLastName(String employeeLastName) {
		EmployeeLastName = employeeLastName;
	}

	

}
