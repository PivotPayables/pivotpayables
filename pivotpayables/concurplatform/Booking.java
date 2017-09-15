package com.pivotpayables.concurplatform;
/**
 * @author John Toman
 * 
 * 4/7/2017
 * 
 * This is the base class for a Concur Travel v1.0 Booking object.
 * 
 * A Booking object provides the detail about a booking within an itinerary.
 *
 */


import static java.lang.System.out;

import java.text.SimpleDateFormat;
import java.util.ArrayList;



import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;


@XmlRootElement (name="Booking")
@XmlAccessorType (XmlAccessType.FIELD)
public class Booking {
	
	@XmlElement
    private String BookingOwner;
	
	@XmlElement
    private String Source;
	
	@XmlElement
    private String BookingSource;
	
	@XmlElement
    private Date DateBookedLocal;
	
	@XmlElement
    Date DateCreatedUtc;
	
	@XmlElement
    Date DateModifiedUtc;
	
	@XmlElement
    private String AgencyName;
	
	@XmlElement
    private String AgencyPCC;
	
	@XmlElement
    boolean IsGhostCard;
	
	@XmlElement
    private Date LastTicketDateUtc;
	
	@XmlElement
    private String RecordLocator;
	
	@XmlElementWrapper(name = "Passengers")
	@XmlElement(name = "Passenger")
	private ArrayList<Passenger> Passengers;
	
	@XmlElementWrapper(name = "Segments")
	@XmlElement(name = "Hotel")
	ArrayList<HotelBooking> hotels;
	
	public Booking () {
		// no args constructor
	}
	
	public void display() {
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
		String strDate = null;
		
		out.println("BOOKING");
		out.println("----------------------------------");
		/*
		if (RecordLocator != null){
			out.println("Record Locator: " + RecordLocator);
		}
		*/
		
		if (BookingOwner != null){
			out.println("Booking Owner: " + BookingOwner);
		}
		if (Source != null){
			out.println("Source: " + Source);
		}
		if (DateBookedLocal != null) {
			strDate = sdf.format(DateBookedLocal);	 
			out.println("Date Booked: " + strDate);
		}
		/*
		if (DateModifiedUtc != null) {
			strDate = sdf.format(DateModifiedUtc);	 
			out.println("Last Modified: " + strDate);
		}
		if (LastTicketDateUtc != null) {
			strDate = sdf.format(LastTicketDateUtc);	 
			out.println("Last Ticket Date: " + strDate);
		}
		if (AgencyName != null){
			out.println("Agency Name: " + AgencyName);
		}
		if (AgencyName != null){
			out.println("Agency Name: " + AgencyName);
		}
		if (AgencyPCC != null){
			out.println("Agency PCC: " + AgencyPCC);
		}
		
		if(Passengers != null) {
			out.println("PASSENGERS");
			int count = Passengers.size();
			Passenger passenger = null;
			if (count >0 ) {
			 for (int index=0 ; index<count; index++) {
				 passenger = Passengers.get(index);
				int itereation = index + 1;
				out.println("Passenger: " + itereation);
				out.println("-----------------------");
				passenger.display();
			 }
				out.println("End Passengers");
				out.println();
			}
		}
		*/
		
		if(hotels != null) {
			int count = hotels.size();
			HotelBooking hotel = null;
			if (count >0 ) {
			 for (int index=0 ; index<count; index++) {
				hotel = hotels.get(index);
				int itereation = index + 1;
				out.println("Hotel: " + itereation);
				out.println("-----------------------");
				hotel.display();
			 }
			}
		}
		
	}

	public String getBookingOwner() {
		return BookingOwner;
	}

	public void setBookingOwner(String bookingOwner) {
		BookingOwner = bookingOwner;
	}

	public String getSource() {
		return Source;
	}

	public void setSource(String source) {
		Source = source;
	}

	public String getBookingSource() {
		return BookingSource;
	}

	public void setBookingSource(String bookingSource) {
		BookingSource = bookingSource;
	}

	public Date getDateBookedLocal() {
		return DateBookedLocal;
	}

	public void setDateBookedLocal(Date dateBookedLocal) {
		DateBookedLocal = dateBookedLocal;
	}

	public Date getDateCreatedUtc() {
		return DateCreatedUtc;
	}

	public void setDateCreatedUtc(Date dateCreatedUtc) {
		DateCreatedUtc = dateCreatedUtc;
	}

	public Date getDateModifiedUtc() {
		return DateModifiedUtc;
	}

	public void setDateModifiedUtc(Date dateModifiedUtc) {
		DateModifiedUtc = dateModifiedUtc;
	}

	public String getAgencyName() {
		return AgencyName;
	}

	public void setAgencyName(String agencyName) {
		AgencyName = agencyName;
	}

	public String getAgencyPCC() {
		return AgencyPCC;
	}

	public void setAgencyPCC(String agencyPCC) {
		AgencyPCC = agencyPCC;
	}

	public boolean isIsGhostCard() {
		return IsGhostCard;
	}

	public void setIsGhostCard(boolean isGhostCard) {
		IsGhostCard = isGhostCard;
	}

	public Date getLastTicketDateUtc() {
		return LastTicketDateUtc;
	}

	public void setLastTicketDateUtc(Date lastTicketDateUtc) {
		LastTicketDateUtc = lastTicketDateUtc;
	}

	public String getRecordLocator() {
		return RecordLocator;
	}

	public void setRecordLocator(String recordLocator) {
		RecordLocator = recordLocator;
	}

	public ArrayList<Passenger> getPassengers() {
		return Passengers;
	}

	public void setPassengers(ArrayList<Passenger> passengers) {
		Passengers = passengers;
	}

	public ArrayList<HotelBooking> getHotels() {
		return hotels;
	}

	public void setHotels(ArrayList<HotelBooking> hotels) {
		this.hotels = hotels;
	}


	
}
