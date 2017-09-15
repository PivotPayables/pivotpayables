package com.pivotpayables.concurplatform;


/**
 * @author John Toman
 * 
 * 4/5/2017
 * 
 * This is the base class for a Concur Travel v1.0 List of Itineraries object called an "ItineraryInfoList".
 * 
 * This object provides a list of "trips" or "itineraries" (trip and itinerary are synonymous in Concur Travel).
 *
 */

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;

import static java.lang.System.out;

@XmlRootElement(name="ItineraryInfoList")
@XmlAccessorType (XmlAccessType.FIELD)
public class TripsList {
	@XmlElement(name = "ItineraryInfo")
    private List<ItineraryInfo> trips = null;
 

	public List<ItineraryInfo> getTrips() {
		return trips;
	}


	public void setTrips(List<ItineraryInfo> trips) {
		this.trips = trips;
	}


}
