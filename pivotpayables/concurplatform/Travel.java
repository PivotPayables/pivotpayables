package com.pivotpayables.concurplatform;
/**
 * @author John Toman
 * 4/5/2017
 * This class's methods provide access to Concur APIs related to Concur Travel.
 * 
 *
 */

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Iterator;
import java.util.Scanner;

import static java.lang.System.out;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;









//JAX-WS, RS client side APIs version 2.x
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;








// jeresy version 1.x
import com.sun.jersey.api.client.*;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
// jackson version 1.x
import org.codehaus.jackson.map.*;
import org.codehaus.jackson.JsonParseException;



public class Travel {
	
	

	protected String xmlbody="";// placeholder for Content body in XML
	protected ClientResponse response;// placeholder for a jersey-client, ClientRepsone object
	protected String resourceURL = "/expense/reports";// the path to the reports resoure
	ConcurFunctions platform = new ConcurFunctions();// get an instance of the Concur Platform Functions
	Client client = platform.getClient();// get a jersey Client to the Concur platform.
	
	public List<ItineraryInfo> getTripsList (String key, @SuppressWarnings("rawtypes") Map queryparameters) throws IOException {
		/* 
		 * This method uses the Concur Platform V1.1 API for getting a list of itineraries or "trips" for a specified Company.
		 * 
		 *    The API uses the OAuth Token to determine the Company.
		 */
		
		// Process the query string parameters
		String query = "";
		if (queryparameters.size() > 0) {// then there are query string parameters to add to the URI
			query = "?";// set the default query string parameter
				
			@SuppressWarnings("rawtypes")
			Iterator it = queryparameters.entrySet().iterator();// create an Iterator to iterate the entries in the Map
			while (it.hasNext()) {// iterate for each entry in the Map
		        @SuppressWarnings("rawtypes")
				Map.Entry pair = (Map.Entry)it.next();// get the Map entry (key, value) for this iteration
				query= query + "&"+pair.getKey() + "="+ pair.getValue();// add its key and value to the query string
		        it.remove(); // avoids a ConcurrentModificationException// remove the entry from the iteration
		    }
		    
		}

		WebResource webResource = client.resource("https://www.concursolutions.com/api/travel/trip/v1.1/" + query);// construct the URI for  Get trips
		String authorizationvalue = "OAuth " + key;// construct the Authorization header using the specified, OAuth access token
		
		TripsList tripslist=null;
		List<ItineraryInfo> trips= null;
		
		try {
			response  = webResource.accept(MediaType.APPLICATION_XML).
									header("Authorization", authorizationvalue).
									header("Content-Type","application/xml; charset=UTF-8").
									get(ClientResponse.class);
			String xml = response.getEntity(String.class);
			
			//out.println("Checkpoint: Original XML = " + xml);
			xml = xml.replace("ItineraryInfoList xmlns=\"http://www.concursolutions.com/api/travel/trip/2010/06\" xmlns:i=\"http://www.w3.org/2001/XMLSchema-instance\"", "ItineraryInfoList");
			xml = xml.replace("i:nil=\"true\"", "");
			//out.println("Checkpoint: Modified XML = " + xml);
	        try {
	        	JAXBContext jc = JAXBContext.newInstance(TripsList.class);

	            Unmarshaller unmarshaller = jc.createUnmarshaller();
	            StringReader reader = new StringReader(xml);
	            tripslist = (TripsList) unmarshaller.unmarshal(reader);
	            trips = tripslist.getTrips();


		        } catch (JAXBException e) {
		            e.printStackTrace();
		        }
	    	} catch (UniformInterfaceException e) {
				response = e.getResponse();
			}
			
	        return trips;
	}
	
	public Itinerary getItinerary (String key, String TripID, @SuppressWarnings("rawtypes") Map queryparameters) throws IOException {
		/* 
		 *   This method uses the Concur Platform V1.1 API for getting the itinerary details for a specified Trip ID.
		 * 
		 */
		
		Itinerary trip = null;
		// Process the query string parameters
		String query = "";
		if (queryparameters.size() > 0) {// then there are query string parameters to add to the URI
			query = "?";// set the default query string parameter
				
			@SuppressWarnings("rawtypes")
			Iterator it = queryparameters.entrySet().iterator();// create an Iterator to iterate the entries in the Map
			while (it.hasNext()) {// iterate for each entry in the Map
		        @SuppressWarnings("rawtypes")
				Map.Entry pair = (Map.Entry)it.next();// get the Map entry (key, value) for this iteration
				query= query + "&"+pair.getKey() + "="+ pair.getValue();// add its key and value to the query string
		        it.remove(); // avoids a ConcurrentModificationException// remove the entry from the iteration
		    }
		    
		}

		WebResource webResource = client.resource("https://www.concursolutions.com/api/travel/trip/v1.1/" + TripID + query);// construct the URI for Get itinerary detail
		String authorizationvalue = "OAuth " + key;// construct the Authorization header using the specified, OAuth access token

		
		try {
			response  = webResource.accept(MediaType.APPLICATION_XML).
									header("Authorization", authorizationvalue).
									header("Content-Type","application/xml; charset=UTF-8").
									get(ClientResponse.class);
			String xml = response.getEntity(String.class);
			
			//out.println("Checkpoint: Original XML = " + xml);
			xml = xml.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", "");
			xml = xml.replace("Itinerary xmlns=\"http://www.concursolutions.com/api/travel/trip/2010/06\"", "Itinerary");
			xml = xml.replace("i:nil=\"true\"", "");
			/*
		   out.println("Checkpoint: Modified XML = " + xml);
		   out.println("------------------------------------------------------------------------------------------------------------------");
		   out.println();
		   */
	        try {
	        	JAXBContext jc = JAXBContext.newInstance(Itinerary.class);

	            Unmarshaller unmarshaller = jc.createUnmarshaller();
	            StringReader reader = new StringReader(xml);
	            trip = (Itinerary) unmarshaller.unmarshal(reader);


		        } catch (JAXBException e) {
		            e.printStackTrace();
		        }
	    	} catch (UniformInterfaceException e) {
				response = e.getResponse();
			}
			
	        return trip;
	}
	
	

	
}
