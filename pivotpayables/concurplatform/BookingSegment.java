package com.pivotpayables.concurplatform;

import java.util.ArrayList;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;


@XmlRootElement (name="Segment")
@XmlAccessorType (XmlAccessType.FIELD)
public class BookingSegment {
	
	@XmlElement (name ="Hotel")
	HotelBooking hotel;
	
    public void display() {
    	if (hotel != null){
    		hotel.display();
    	}
    }
}
