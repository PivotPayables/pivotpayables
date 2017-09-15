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

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;


@XmlRootElement (name="Passenger")
@XmlAccessorType (XmlAccessType.FIELD)
public class Passenger {

	@XmlElement (name = "NameFirst")
    String FirstName;
	@XmlElement (name = "NameLast")
    String LastName;
	
	public void display() {

		if (FirstName != null){
			out.println("First Name: " + FirstName);
		}
		if (LastName != null){
			out.println("Last Name: " + LastName);
		}
		out.println();
	}
}
