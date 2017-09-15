package com.pivotpayables.test;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.json.JSONException;












import java.io.IOException;
import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.pivotpayables.concurplatform.Itinerary;
import com.pivotpayables.concurplatform.ItineraryInfo;
import com.pivotpayables.concurplatform.Travel;
import com.sun.jersey.api.client.UniformInterfaceException;

import static java.lang.System.out;

public class TestGetTrips {
	private static Map<String, String> queryparameters = new HashMap<String, String>();// a HashMap to hold key-value pairs for a query parameter
	private static Travel t = new Travel();// ExpenseReports functions
	
	public static void main(String[] args) throws JsonParseException, JsonMappingException, ParseException, JSONException, IOException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String key = "0_mYkFgbh2K5LDa3Pz68kHzgD6k=";// MedPartners"0_2n15wT8FUUsTzLk2Y/dHakpbA=";//apex "0_wRCyqgowNZi7gA5kZl6Xoh/ME=";//Pritzer Group"0_rC7L9ZyQdnpNPQ7NNge2Wv28Q=";//Vantage"0_Vo4ekun+XzgPE4/rKg4SCahBI=";//Chopin Vodka"0_3DKd4eFeSXZtJFjDrqKyFIMEI= ";//Wetherly"0_La6PCrKLOAhjcFB4DgWYAnjy4=";//Mesirow"0_4VBWqUgYCwYlTTDnq2WTpX59I=";//Hargrove;////;"gEDKnWeGyPDdaTBBXTQX8YEWodE=";//Voicebrook//"0_6eHXFt6m8vtwG/rPWaSpci9XI=";//Pragmatic Works//"0_TTdJ95hoq3kSx6RwSAqayIGHI=";//OrbiMed//"VOKbC9xM2kftRcgSEwV2AAw+yLY=";//Coffman Engineers//"0_TH3kHDP+XJ3eJGh5qZignLpnc=";//HFZ//"0_v9841SkONKrlZPFDPcQMV1La8=";//Renoir Group//"0_ZKoC9r4R83iEXvHqit9oJlzvA=";//West Yost//"0_H8UDN5wGoLs/KEid6HkF7EB4I=";//nesoi solutions//"swcvpgXlclo7BzaISyA5uUYfhD4=";//LifeWay//"e15mCgSh7e0KrKZ4w363v1rSlek=";//K & L Beverage//"myL0YTKnKt9Bu57bxxNWgIB/LdA=";//Chopin Vodka//"iohyl8b1gbjyvMK3iH++bwkMybY=";// assurance resources;//"e15mCgSh7e0KrKZ4w363v1rSlek=";//K&L //
		String startdate = "2016%2F01%2F01";
		String enddate = "2016%2F12%2F31";
		Itinerary trip=null;
		String TripID = "";


		
		queryparameters = new HashMap<String, String>();// initialize the queryparameters HashMap
		
		queryparameters.put("startDate", startdate);
		queryparameters.put("endDate", enddate);
		queryparameters.put("bookingType", "Hotel");
		queryparameters.put("userid_type", "login");
		queryparameters.put("userid_value", "ALL");

		List<ItineraryInfo> trips= t.getTripsList(key, queryparameters);
		out.println("Checkpoint: trips count" + trips.size());
		if (trips != null){
			for (ItineraryInfo ittrip:trips){
				queryparameters = new HashMap<String, String>();// initialize query parameters
				queryparameters.put("bookingType", "Hotel");
				queryparameters.put("userid_type", "login");
				queryparameters.put("userid_value", "ALL");
				TripID = ittrip.getTripID();
				trip = t.getItinerary(key, TripID, queryparameters);
				trip.display();
			}
		}		
	}	
	
}
