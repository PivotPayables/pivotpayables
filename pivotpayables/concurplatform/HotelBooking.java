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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;





import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;


@XmlRootElement (name="Hotel")
@XmlAccessorType (XmlAccessType.FIELD)
public class HotelBooking {
	
	@XmlElement
	String Vendor;
	
	@XmlElement
	String Status;
	
	@XmlElement(name = "StartDateLocal")
	Date StartDate;// the date the trip begins.  This is the earliest start date of any booking in this trip

	@XmlElement(name = "EndDateLocal")
	Date EndDate;// the date the trip begins.  This is the latest end date of any booking in this trip

	@XmlElement
	String ConfirmationNumber;
	
	@XmlElement (name = "DateModifiedUtc")
	Date LastModified;// the date this trip was last modified
	
	@XmlElement
	String RateCode;
	
	@XmlElement
	String Name;
		
	@XmlElement (name = "HotelPropertyId")
	String PropertyID;
    
	@XmlElementWrapper(name = "Charges")
	@XmlElement(name = "Rate")
    ArrayList<FixedRate> Charges;
    
    public void display() {
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
		String strDate = null;

		out.println("HOTEL BOOKING");
		out.println("----------------------------------");
		out.println("Vendor: " + Vendor);
		if (Name != null){
			out.println("Hotel Name: " + Name);
		}
		if (ConfirmationNumber != null){
			out.println("ConfirmationNumber: " + ConfirmationNumber);
		}
		
		if (StartDate != null) {
			strDate = sdf.format(StartDate);	 
			out.println("Checkin Date: " + strDate);
		}
		if (EndDate != null) {
			strDate = sdf.format(EndDate);	 
			out.println("Checkout Date: " + strDate);
		}
		if (LastModified != null) {
			strDate = sdf.format(LastModified);	 
			out.println("Last Modified Date: " + strDate);		
		}
		if(Charges != null) {
			out.println("CHARGES");
			int count = Charges.size();
			FixedRate charge = null;
			if (count >0 ) {
			 for (int index=0 ; index<count; index++) {
				 charge = Charges.get(index);
				int itereation = index + 1;
				out.println("Charge: " + itereation);
				out.println("-----------------------");
				charge.display();
			 }
				//out.println("End Charges___________________________________________");
				out.println();
			}
		}
		


	}

}
