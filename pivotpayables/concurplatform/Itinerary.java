package com.pivotpayables.concurplatform;
/**
 * @author John Toman
 * 
 * 4/5/2017
 * 
 * This is the base class for a Concur v1.0 Itinerary object.
 * 
 * An Itinerary object provides the details for the bookings within an itinerary.
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




@XmlRootElement (name="Itinerary")
@XmlAccessorType (XmlAccessType.FIELD)
public class Itinerary {
	
	@XmlElement (name ="id")
	String ItineraryDetailUrl;
	
	@XmlElement
	String ItinLocator;
	
	@XmlElement
	String ClientLocator;
   
	@XmlElement
	String ItinSourceName;

	@XmlElement
	String TripName;
    
	@XmlElement(name = "StartDateLocal")
	Date StartDate;// the date the trip begins.  This is the earliest start date of any booking in this trip

	@XmlElement(name = "EndDateLocal")
	Date EndDate;// the date the trip begins.  This is the latest end date of any booking in this trip

	@XmlElement (name = "DateModifiedUtc")
	Date LastModified;// the date this trip was last modified
	
	@XmlElement
	String BookedVia;
    
	@XmlElement
    String BookedByFirstName; 
	
	@XmlElement
    String BookedByLastName;
	
	@XmlElement
    Date DateBookedLocal;
	
	@XmlElementWrapper(name = "Bookings")
	@XmlElement(name = "Booking")
	ArrayList<Booking> Bookings;
	
	public void display() {
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
		String strDate = null;
		
		out.println("Trip Name: " + TripName);
		
		if (ItinLocator != null){
			out.println("ItinLocator " + ItinLocator);
		}
		
		if (StartDate != null) {
			strDate = sdf.format(StartDate);	 
			out.println("Trip Start Date: " + strDate);
		}
		if (EndDate != null) {
			strDate = sdf.format(EndDate);	 
			out.println("Trip End Date: " + strDate);
		}
		if (LastModified != null) {
			strDate = sdf.format(LastModified);	 
			out.println("Last Modified Date: " + strDate);
		}
		if(Bookings != null) {
			out.println("BOOKINGS");
			int count = Bookings.size();
			Booking booking = null;
			if (count >0 ) {
			 for (int index=0 ; index<count; index++) {
				booking = Bookings.get(index);
				int itereation = index + 1;
				booking.display();
			 }
				out.println("End Bookings");
				out.println("===========================================================================================");
				out.println();
			}
		}
		
	}

}
