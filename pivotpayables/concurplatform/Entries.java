
package com.pivotpayables.concurplatform;

/**
 * @author John Toman
 * 5/23/2015
 * methods to GET, POST, PUT, and DELETE expense Entries
 *
 */
// java libraries
import static java.lang.System.out;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;



//JAX-WS, RS client side APIs version 2.x
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.codehaus.jackson.JsonParseException;
// jackson version 1.x
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
// java json
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.pivotpayables.expensesimulator.GUID;
import com.sun.jersey.api.client.Client;
// jeresy version 1.x
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;


public class Entries {
	static protected ArrayList<ExpenseEntry> entries = new ArrayList<ExpenseEntry>();
	protected static ExpenseEntry entry;// placeholder for an Entry object
	protected ExpenseReport report;// placeholder for an ExpenseReport object
	static protected ArrayList<Itemization> itemizations = new ArrayList<Itemization>();
	protected static Itemization itemization; // placeholder for an Itemization object
	protected static String jsonbody="";// placeholder for Content body in JSON
	protected static String xmlbody="";// placeholder for Content body in XML
	protected static ClientResponse response;// placeholder for a jersey-client, ClientRepsone object
	protected static String resourceURL = "/expense/entries";// the path to the Entries resource
	protected static String reportid;
	protected static String user;
	protected ConcurFunctions platform = new ConcurFunctions();// get an instance of the Concur Platform functions
	protected Client client = platform.getClient();// get a jersey Client to the Concur platform.
	protected ExpenseReports reports;
	protected final String pagelimit = "limit=25";// set the default query string parameter: page limit to 25 entries per page
	protected static String query;
	
	public ArrayList<ExpenseEntry> getEntries (String key, Map<String, String> queryparameters) throws JSONException, JsonParseException, JsonMappingException, IOException {
		// get entries for specified query parameters using the specified OAuth access token
		
		// Process the query string parameters
		
		resourceURL = "/expense/entries";// the path to the Entries resource
		ArrayList<ExpenseEntry> entries = new ArrayList<ExpenseEntry>();// initialize ArrayList of Entry object elements

		if (queryparameters.size() > 0) {// then there are query string parameters to add to the default query
			query= "?";
			Iterator<Entry<String, String>> it = queryparameters.entrySet().iterator();// create an Iterator to iterate the entries in the Map
			while (it.hasNext()) {// iterate for each entry in the Map
		        Map.Entry pair = (Map.Entry)it.next();// get the Map entry (key, value) for this iteration
		        query =  query + pair.getKey() + "="+ pair.getValue() + "&";// add its key and value to the query string
		        it.remove(); // avoids a ConcurrentModificationException, remove the entry from the iteration
		    }
			query= query + pagelimit;
		}
		WebResource webResource = client.resource(platform.baseURL + resourceURL + query);// construct the URI for the get entries call
		webResource.setProperty("Content-Type", MediaType.APPLICATION_JSON);// content is JSON
		String authorizationvalue = "OAuth " + key;// construct the Authorization header using the specified, OAuth access token
		Boolean nextpageflag = true;// initiate the next page flag; this flag determines whether there is another page of results to retrieve from the server
		String nextpageURI = null;// the URI to call to get the next page of results
		
		while (nextpageflag) {// iterate while the next page flag is true
			try {
				response  = webResource.accept(MediaType.APPLICATION_JSON).
						header("Authorization", authorizationvalue).
						header("Content-Type","application/json; charset=UTF-8").
				        get(ClientResponse.class);// trigger the GET call
				jsonbody = response.getEntity(String.class);// make the GET Entry call
				} catch (UniformInterfaceException e) {
					response = e.getResponse();
				}// try block


			if (jsonbody.substring(2, 7).equals("Items")) {// then the response needs to changed into a JSON Array by removing the substring, {"Items:
				jsonbody = jsonbody.substring(9);// convert the JSON body into a JSON array by removing the first 8 characters,  {"Items:
			}// if block

			String[] parts = jsonbody.split("],");// split jsonbody into two parts: 0) the JSON Array of entries, and 1) the Next Page URI
			jsonbody = parts[0] +"]";// assign the JSON Array to jsonbody
			nextpageURI = "{"+ parts[1];// assign the Next Page URI

			JSONArray jsonarray = new JSONArray(jsonbody);// convert jsonbody into a JSON Array
			ObjectMapper mapper = new ObjectMapper();// create an instance of the Jackson mapper
			for(int i=0; i<jsonarray.length(); i++){// iterate for each JSON object in the array
		        JSONObject obj = jsonarray.getJSONObject(i);// get the JSON object for this iteration
		        entry = mapper.readValue(obj.toString(),ExpenseEntry.class);// have the mapper convert the JSON object into an Entry object
		        itemizations = new ArrayList<Itemization>();// initialize ArrayList of itemization objects for this entry
		        if (entry.getHasItemizations()) {// then get its itemizations from Concur Expense
		        	queryparameters = new HashMap<String, String>();
					queryparameters.put("entryID", entry.getExpenseEntry_ID());
					itemizations = getItemizations(key, queryparameters);// get Itemizations for the specified EntryID

					for (int k=0; k< itemizations.size(); k++) { // Iterate for each itemization in the entry
						itemization = itemizations.get(k);// get the itemization for this iteration
						itemization.setID(GUID.getGUID(4));// assign a Pivot Payables GUID to this itemization
						itemization.setTransactionCurrencyCode(entry.getTransactionCurrencyCode());// copy the Expense's original currency to the itemization
						itemization.setPostedCurrency(entry.getPostedCurrency());// copy the Expense's posted currency to the itemization
					}// for k loop
		        } else {// then create an itemization for this regular entry
		        	itemization = new Itemization();
		        	itemization.setID(GUID.getGUID(4));	
		    		itemization.setExpenseTypeName(entry.ExpenseTypeName);
		    		itemization.setIsBillable(entry.getHasBillableItems());
		    		itemization.setDate(entry.getTransactionDate());
		    		itemization.setTransactionAmount(entry.TransactionAmount);
		    		itemization.setTransactionCurrencyCode(entry.TransactionCurrencyCode);
		    		itemization.setPostedAmount(entry.PostedAmount);
		    		itemization.setApprovedAmount(entry.ApprovedAmount);
		    		itemization.setPostedCurrency(entry.PostedCurrency);
		    		itemization.setLastModified(entry.LastModified);
		    		itemization.setCustom1(entry.getCustom1());
		    		itemization.setCustom2(entry.getCustom2());
		    		itemization.setCustom3(entry.getCustom3());
		    		itemization.setCustom4(entry.getCustom4());
		    		itemization.setCustom5(entry.getCustom5());
		    		itemization.setCustom6(entry.getCustom6());
		    		itemization.setCustom7(entry.getCustom7());
		    		itemization.setCustom8(entry.getCustom8());
		    		itemization.setCustom9(entry.getCustom9());
		    		itemization.setCustom10(entry.getCustom10());
		    		itemization.setCustom11(entry.getCustom11());
		    		itemization.setCustom12(entry.getCustom12());
		    		itemization.setCustom13(entry.getCustom13());
		    		itemization.setCustom14(entry.getCustom14());
		    		itemization.setCustom15(entry.getCustom15());
		    		itemization.setCustom16(entry.getCustom16());
		    		itemization.setCustom17(entry.getCustom17());
		    		itemization.setCustom18(entry.getCustom18());
		    		itemization.setCustom19(entry.getCustom19());
		    		itemization.setCustom20(entry.getCustom20());
		    		itemization.setCustom21(entry.getCustom21());
		    		itemization.setCustom22(entry.getCustom22());
		    		itemization.setCustom23(entry.getCustom23());
		    		itemization.setCustom24(entry.getCustom24());
		    		itemization.setCustom25(entry.getCustom25());
		    		itemization.setCustom26(entry.getCustom26());
		    		itemization.setCustom27(entry.getCustom27());
		    		itemization.setCustom28(entry.getCustom28());
		    		itemization.setCustom29(entry.getCustom29());
		    		itemization.setCustom30(entry.getCustom30());
		    		itemization.setCustom31(entry.getCustom31());
		    		itemization.setCustom32(entry.getCustom32());
		    		itemization.setCustom33(entry.getCustom33());
		    		itemization.setCustom34(entry.getCustom34());
		    		itemization.setCustom35(entry.getCustom35());
		    		itemization.setCustom36(entry.getCustom36());
		    		itemization.setCustom37(entry.getCustom37());
		    		itemization.setCustom38(entry.getCustom38());
		    		itemization.setCustom39(entry.getCustom39());
		    		itemization.setCustom40(entry.getCustom40());
		    		itemization.setOrgUnit1(entry.getOrgUnit1());
		    		itemization.setOrgUnit2(entry.getOrgUnit2());
		    		itemization.setOrgUnit3(entry.getOrgUnit3());
		    		itemization.setOrgUnit4(entry.getOrgUnit4());
		    		itemization.setOrgUnit5(entry.getOrgUnit5());
		    		itemization.setOrgUnit6(entry.getOrgUnit6());
		    		itemizations.add(itemization);

		        }// if has itemizations block
		  
		        entries.add(entry);// add the entry to the ArrayList of Expense elements
		    }// for i loop 

		    NextPageURI uri = mapper.readValue(nextpageURI, NextPageURI.class);
		    nextpageURI = uri.NextPage;
		    
			if (nextpageURI != null) {// then get the next page
				nextpageflag = true;
				webResource = client.resource(nextpageURI);// set the Web Resource to the Next Page URI	
			} else {
				nextpageflag = false;// set the flag false so it will terminate the while loop
			}// if nextpageURI block
		} //while loop
		
	
		return entries;
	}
	public ExpenseEntry getEntry (String expenseid, String user, String key) throws JSONException, JsonParseException, JsonMappingException, IOException {
		// get the specified entry using specified OAuth access token
		String query = null;
		if (user.length() > 0 ) {
			query = "?user=" + user;
		}

		WebResource webResource = client.resource(platform.baseURL + resourceURL + "/"+ expenseid + query);// construct the URI for the get expense by Expense ID
		webResource.setProperty("Content-Type", MediaType.APPLICATION_JSON);// content is JSON
		String authorizationvalue = "OAuth " + key;// construct the Authorization header using the specified, OAuth access token

			try {
				response  = webResource.accept(MediaType.APPLICATION_JSON).
						header("Authorization", authorizationvalue).
						header("Content-Type","application/json; charset=UTF-8").
				        get(ClientResponse.class);// make the GET Expense call
				jsonbody = response.getEntity(String.class);
			} catch (UniformInterfaceException e) {
				response = e.getResponse();
			}
			if (response.getStatus() == Response.Status.OK.getStatusCode()) {// then it found the expense in Concur Expense
				ObjectMapper mapper = new ObjectMapper();// create an instance of the Jackson mapper
		        entry = mapper.readValue(jsonbody,ExpenseEntry.class);// have the mapper convert the JSON entry entry into an Entry object

			} else {
				out.println("Response Status: " + response.getStatus());
				out.println("Response Body" + jsonbody);
			   entry = null;// set ExpenseEntry object to null
			}

	        return entry;
	}
	public ImageURL getEntryImageURL (ExpenseEntry entry, String key) throws IOException {
		ImageURL imageurl = null;


		
		WebResource webResource = client.resource("https://www.concursolutions.com/api/image/v1.0/expenseentry/"+ entry.getID());// construct the URI for the get entry image by Expense ID
		String authorizationvalue = "OAuth " + key;// construct the Authorization header using the specified, OAuth access token

		try {
			response  = webResource.accept(MediaType.APPLICATION_XML).
									header("Authorization", authorizationvalue).
									header("Content-Type","application/xml; charset=UTF-8").
									get(ClientResponse.class);
			String xml = response.getEntity(String.class);
			xml = xml.replace("Image xmlns=\"http://www.concursolutions.com/api/image/2011/02\" xmlns:i=\"http://www.w3.org/2001/XMLSchema-instance\"", "Image");
	        try {
	        	JAXBContext jc = JAXBContext.newInstance(ImageURL.class);

	            Unmarshaller unmarshaller = jc.createUnmarshaller();
	            StringReader reader = new StringReader(xml);
	            imageurl = (ImageURL) unmarshaller.unmarshal(reader);

		        } catch (JAXBException e) {
		            e.printStackTrace();
		        }
	    	} catch (UniformInterfaceException e) {
				response = e.getResponse();
			}
			
	        return imageurl;
	}
	public String postEntryImageURL (ExpenseEntry entry, String key, File file, String type) throws IOException {

		HttpClient httpclient = new DefaultHttpClient();
	    httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
	    HttpPost httppost = new HttpPost("https://www.concursolutions.com/api/image/v1.0/expenseentry/"+ entry.getID());


	    MultipartEntity mpEntity = new MultipartEntity();
	    ContentBody cbFile = new FileBody(file, "image/"+type);
	    mpEntity.addPart("userfile", cbFile);


	    httppost.setEntity(mpEntity);
	    out.println("executing request " + httppost.getRequestLine());
	    HttpResponse response = httpclient.execute(httppost);
	    HttpEntity resEntity = response.getEntity();

	    out.println(response.getStatusLine());
	    if (resEntity != null) {
	      out.println(EntityUtils.toString(resEntity));
	    }
	    if (resEntity != null) {
	      resEntity.consumeContent();
	    }

	    httpclient.getConnectionManager().shutdown();
	  
		return response.getStatusLine().toString();
		/*
		WebResource webResource = client.resource("https://www.concursolutions.com/api/image/v1.0/expenseentry/"+ entry.getID());// construct the URI for the post entry image by Expense ID
		String authorizationvalue = "OAuth " + key;// construct the Authorization header using the specified, OAuth access token
		String xml=null;
		

		try {
			response  = webResource.header("Authorization", authorizationvalue).
									header("Content-Type","image/" + type).
									header("Content-Length", Integer.toString(image.length())).
									post(ClientResponse.class, image);
			xml = response.getEntity(String.class);

			//xml = xml.replace("Image xmlns=\"http://www.concursolutions.com/api/image/2011/02\" xmlns:i=\"http://www.w3.org/2001/XMLSchema-instance\"", "Image");
			/*
	        try {
	        	JAXBContext jc = JAXBContext.newInstance(ImageURL.class);

	            Unmarshaller unmarshaller = jc.createUnmarshaller();
	            StringReader reader = new StringReader(xml);
	            imageurl = (ImageURL) unmarshaller.unmarshal(reader);

		        } catch (JAXBException e) {
		            e.printStackTrace();
		        }
		        
	    	} catch (UniformInterfaceException e) {
				response = e.getResponse();
			}
	    	*/
			
			

	}
	
	public String updateEntry (ExpenseEntry entry, String key) throws UnsupportedEncodingException {
		// update the specified entry using specified OAuth access token
		
		String expenseid = entry.getID();// get the ExpenseID from the Expense object
		String ownerid = entry.getReportOwnerID();// get the Owner Login ID 
		String user = URLEncoder.encode(ownerid, "UTF-8");// URL Encode it
		
		WebResource webResource = client.resource(platform.baseURL + resourceURL + "/"+ expenseid + "?user=" + user);// construct the URI for the put ExpenseEntry by Expense ID

		webResource.setProperty("Content-Type", MediaType.APPLICATION_JSON);// content is JSON
		String authorizationvalue = "OAuth " + key;// construct the Authorization header using the specified, OAuth access token
		
        JSONObject jsonObj = new JSONObject(entry);// convert the ExpenseEntry object into a JSON object
        jsonObj = updateSafeEntry (jsonObj);// remove all JSON elements that can't be in the Request Content Body
        jsonbody=jsonObj.toString();// convert the JSON object into the Request Content Body
		try {
	        response = 	webResource.accept("application/json").
									header("Authorization", authorizationvalue).
									header("Content-Type","application/json; charset=UTF-8").
									put(ClientResponse.class, jsonbody);// make the PUT Entry call
		} catch (UniformInterfaceException e) {
			response = e.getResponse();
		}
	
		if (response.getStatus() != 204) {// then there is an error
			out.println("Response Status: " + response.getStatus());
			out.println("Response Body" + jsonbody);
		}
		return Integer.toString(response.getStatus());

}

	public String addEntry (ExpenseEntry entry, String key) {
		// add the specified entry using the specified OAuth access token
		String authorizationvalue = "OAuth " + key;//"OAuth kzfF70hLzdKJu1TxiQWthcJdXro=";

	    
		WebResource webResource = client.resource(platform.baseURL + resourceURL);
		webResource.setProperty("Content-Type", MediaType.APPLICATION_JSON);

		
        JSONObject jsonObj = new JSONObject(entry);// convert the ExpenseEntry object into a JSON object
        jsonbody=jsonObj.toString();// convert the JSON object into the Request Content Body
        jsonObj = updateSafeEntry (jsonObj);// remove all JSON elements that can't be in the Request Content Body
        jsonbody=jsonObj.toString();// convert the JSON object into the Request Content Body
       

		try {
	        response = 	webResource.accept("application/json").
									header("Authorization", authorizationvalue).
									header("Content-Type","application/json; charset=UTF-8").
									post(ClientResponse.class, jsonbody);// make the POST call
		} catch (UniformInterfaceException e) {
			response = e.getResponse();
		}
	
		if (response.getStatus() == Response.Status.OK.getStatusCode()) {
		    String entity = response.getEntity(String.class);
		    String[] parts = entity.split("\"");
		    return parts[3];

		} else {
			out.println("Response Status: " + response.getStatus());
			out.println("Request Body" + jsonbody);
		    out.println(response.getEntity(String.class));
		}
		return Integer.toString(response.getStatus());



}
	public String addMileageEntry (ExpenseEntry entry, String key) {
		// add the specified mileage expense entry using the specified OAuth access token
		String authorizationvalue = "OAuth " + key;//"OAuth kzfF70hLzdKJu1TxiQWthcJdXro=";

	    
		WebResource webResource = client.resource(platform.baseURL + resourceURL);
		webResource.setProperty("Content-Type", MediaType.APPLICATION_JSON);

		
        JSONObject jsonObj = new JSONObject(entry);// convert the ExpenseEntry object into a JSON object
        //jsonbody=jsonObj.toString();// convert the JSON object into the Request Content Body
        //out.println(jsonbody);
        jsonObj = updateSafeMileageEntry (jsonObj);// remove all JSON elements that can't be in the Request Content Body
        jsonbody=jsonObj.toString();// convert the JSON object into the Request Content Body
       // out.println(jsonbody);
        

		try {

	        response = 	webResource.accept("application/json").
									header("Authorization", authorizationvalue).
									header("Content-Type","application/json; charset=UTF-8").
									post(ClientResponse.class, jsonbody);// make the POST call
		} catch (UniformInterfaceException e) {
			response = e.getResponse();
		}
	
		if (response.getStatus() == Response.Status.OK.getStatusCode()) {
		    String entity = response.getEntity(String.class);
		    String[] parts = entity.split("\"");
		    return parts[3];

		} else {
			out.println("Response Status: " + response.getStatus());
			out.println("Request Body" + jsonbody);
		    out.println(response.getEntity(String.class));
		}
		return Integer.toString(response.getStatus());
		



}
	public ArrayList<Itemization> getItemizations (String key, Map queryparameters) throws JSONException, JsonParseException, JsonMappingException, IOException {
		// get itemizations for specified query parameters using the specified OAuth access token
		
		// Process the query string parameters
		resourceURL="";
		resourceURL = "/expense/itemizations";

		
		if (queryparameters.size() > 0) {// then there are query string parameters to add to the default query
			Iterator it = queryparameters.entrySet().iterator();// create an Iterator to iterate the entries in the Map
			while (it.hasNext()) {// iterate for each entry in the Map
		        Map.Entry pair = (Map.Entry)it.next();// get the Map entry (key, value) for this iteration
		        query= "?" + pair.getKey() + "="+ pair.getValue() + "&"+ pagelimit;// add its key and value to the query string
		        it.remove(); // avoids a ConcurrentModificationException, remove the entry from the iteration
		    }
		}
		WebResource webResource = client.resource(platform.baseURL + resourceURL + query);// construct the URI for the get itemizations call
		webResource.setProperty("Content-Type", MediaType.APPLICATION_JSON);// content is JSON
		String authorizationvalue = "OAuth " + key;// construct the Authorization header using the specified, OAuth access token
		Boolean nextpageflag = true;// initiate the next page flag; this flag determines whether there is another page of results to retrieve from the server
		String nextpageURI = null;// the URI to call to get the next page of results
		
		while (nextpageflag) {// iterate while the next page flag is true
			try {
				response  = webResource.accept(MediaType.APPLICATION_JSON).
						header("Authorization", authorizationvalue).
						header("Content-Type","application/json; charset=UTF-8").
				        get(ClientResponse.class);// trigger the GET call
				jsonbody = response.getEntity(String.class);// make the GET itemizations call
				} catch (UniformInterfaceException e) {
					response = e.getResponse();
				}// try block

			
			if (jsonbody.substring(2, 7).equals("Items")) {// then the response needs to changed into a JSON Array by removing the substring, {"Items:
				jsonbody = jsonbody.substring(9);// convert the JSON body into a JSON array by removing the first 8 characters,  {"Items:
			}// if block
			String[] parts = jsonbody.split("],");// split jsonbody into two parts: 0) the JSON Array of itemizations, and 1) the Next Page URI
			jsonbody = parts[0] +"]";// assign the JSON Array to jsonbody
			nextpageURI = "{"+ parts[1];// assign the Next Page URI

			JSONArray jsonarray = new JSONArray(jsonbody);// convert jsonbody into a JSON Array
			ObjectMapper mapper = new ObjectMapper();// create an instance of the Jackson mapper
			
			for(int i=0; i<jsonarray.length(); i++){// iterate for each JSON object in the array
		        JSONObject obj = jsonarray.getJSONObject(i);// get the JSON object for this iteration
		        itemization = mapper.readValue(obj.toString(),Itemization.class);// have the mapper convert the JSON object into an Itemization object
		        itemizations.add(itemization);// add the itemization to the ArrayList of Itemization elements
		    }// for i loop 

		    NextPageURI uri = mapper.readValue(nextpageURI, NextPageURI.class);
		    nextpageURI = uri.NextPage;
		    
			if (nextpageURI != null) {// then get the next page
				nextpageflag = true;
				webResource = client.resource(nextpageURI);// set the Web Resource to the Next Page URI	
			} else {
				nextpageflag = false;// set the flag false so it will terminate the while loop
			}// if nextpageURI block
		} //while loop
		
	
		return itemizations;
	}
	public  ArrayList<List> getLists (String key) throws JSONException, JsonParseException, JsonMappingException, IOException{
		// get Lists for the Concur entity associated to the specified OAuth access token
			
		resourceURL = "/common/lists";// the path to the Lists resource
		ArrayList<List> lists = new ArrayList<List>();// initialize ArrayList of List object elements
		List list = null;

		WebResource webResource = client.resource(platform.baseURL + resourceURL);// construct the URI for the get lists call
		webResource.setProperty("Content-Type", MediaType.APPLICATION_JSON);// content is JSON
		String authorizationvalue = "OAuth " + key;// construct the Authorization header using the specified, OAuth access token
		Boolean nextpageflag = true;// initiate the next page flag; this flag determines whether there is another page of results to retrieve from the server
		String nextpageURI = null;// the URI to call to get the next page of results
		
		while (nextpageflag) {// iterate while the next page flag is true
			try {
				response  = webResource.accept(MediaType.APPLICATION_JSON).
						header("Authorization", authorizationvalue).
						header("Content-Type","application/json; charset=UTF-8").
				        get(ClientResponse.class);// trigger the GET call
				jsonbody = response.getEntity(String.class);// make the GET lists call
				} catch (UniformInterfaceException e) {
					response = e.getResponse();
				}// try block


			if (jsonbody.substring(2, 7).equals("Items")) {// then the response needs to changed into a JSON Array by removing the substring, {"Items:
				jsonbody = jsonbody.substring(9);// convert the JSON body into a JSON array by removing the first 8 characters,  {"Items:
			}// if block

			String[] parts = jsonbody.split("],");// split jsonbody into two parts: 0) the JSON Array of lists, and 1) the Next Page URI
			jsonbody = parts[0] +"]";// assign the JSON Array to jsonbody
			nextpageURI = "{"+ parts[1];// assign the Next Page URI

			JSONArray jsonarray = new JSONArray(jsonbody);// convert jsonbody into a JSON Array
			ObjectMapper mapper = new ObjectMapper();// create an instance of the Jackson mapper
			for(int i=0; i<jsonarray.length(); i++){// iterate for each JSON object in the array
		        JSONObject obj = jsonarray.getJSONObject(i);// get the JSON object for this iteration
		        list = mapper.readValue(obj.toString(),List.class);// have the mapper convert the JSON object into an List object
		        lists.add(list);// add the list to the ArrayList of List elements
		    }// for i loop 

		    NextPageURI uri = mapper.readValue(nextpageURI, NextPageURI.class);
		    nextpageURI = uri.NextPage;
		    
			if (nextpageURI != null) {// then get the next page
				nextpageflag = true;
				webResource = client.resource(nextpageURI);// set the Web Resource to the Next Page URI	
			} else {
				nextpageflag = false;// set the flag false so it will terminate the while loop
			}// if nextpageURI block
		} //while loop
			
		
		return lists;
	}
	public ArrayList<ListItem> getListItems (String key, Map queryparameters) throws JSONException, JsonParseException, JsonMappingException, IOException {
		// get list items for specified query parameters using the specified OAuth access token
		
		// Process the query string parameters
		resourceURL="";
		resourceURL = "/common/listitems";
		ArrayList<ListItem> items = new ArrayList<ListItem>();// initialize ArrayList of List Item object elements
		ListItem item = new ListItem();// placeholder for a list item

		
		if (queryparameters.size() > 0) {// then there are query string parameters to add to the default query
			Iterator it = queryparameters.entrySet().iterator();// create an Iterator to iterate the entries in the Map
			while (it.hasNext()) {// iterate for each entry in the Map
		        Map.Entry pair = (Map.Entry)it.next();// get the Map entry (key, value) for this iteration
		        query= "?" + pair.getKey() + "="+ pair.getValue() + "&"+ pagelimit;// add its key and value to the query string
		        it.remove(); // avoids a ConcurrentModificationException, remove the entry from the iteration
		    }
		}
		WebResource webResource = client.resource(platform.baseURL + resourceURL + query);// construct the URI for the get itemizations call
		webResource.setProperty("Content-Type", MediaType.APPLICATION_JSON);// content is JSON
		String authorizationvalue = "OAuth " + key;// construct the Authorization header using the specified, OAuth access token
		Boolean nextpageflag = true;// initiate the next page flag; this flag determines whether there is another page of results to retrieve from the server
		String nextpageURI = null;// the URI to call to get the next page of results
		
		while (nextpageflag) {// iterate while the next page flag is true
			try {
				response  = webResource.accept(MediaType.APPLICATION_JSON).
						header("Authorization", authorizationvalue).
						header("Content-Type","application/json; charset=UTF-8").
				        get(ClientResponse.class);// trigger the GET call
				jsonbody = response.getEntity(String.class);// make the GET itemizations call
				} catch (UniformInterfaceException e) {
					response = e.getResponse();
				}// try block
			
			if (jsonbody.substring(2, 7).equals("Items")) {// then the response needs to changed into a JSON Array by removing the substring, {"Items:
				jsonbody = jsonbody.substring(9);// convert the JSON body into a JSON array by removing the first 8 characters,  {"Items:
			}// if block
			String[] parts = jsonbody.split("],");// split jsonbody into two parts: 0) the JSON Array of itemizations, and 1) the Next Page URI
			jsonbody = parts[0] +"]";// assign the JSON Array to jsonbody
			nextpageURI = "{"+ parts[1];// assign the Next Page URI

			JSONArray jsonarray = new JSONArray(jsonbody);// convert jsonbody into a JSON Array
			ObjectMapper mapper = new ObjectMapper();// create an instance of the Jackson mapper
			
			for(int i=0; i<jsonarray.length(); i++){// iterate for each JSON object in the array
		        JSONObject obj = jsonarray.getJSONObject(i);// get the JSON object for this iteration
		        item = mapper.readValue(obj.toString(),ListItem.class);// have the mapper convert the JSON object into a List Item object
		        items.add(item);// add the itemization to the ArrayList of Itemization elements
		    }// for i loop 

		    NextPageURI uri = mapper.readValue(nextpageURI, NextPageURI.class);
		    nextpageURI = uri.NextPage;
		    
			if (nextpageURI != null) {// then get the next page
				nextpageflag = true;
				webResource = client.resource(nextpageURI);// set the Web Resource to the Next Page URI	
			} else {
				nextpageflag = false;// set the flag false so it will terminate the while loop
			}// if nextpageURI block
		} //while loop
		
	
		return items;
	}
	public void addListItem (ListItem item, String key) {
		// add the specified list item using the specified OAuth access token
		String authorizationvalue = "OAuth " + key;
		resourceURL = "/common/listitems";

	    
		WebResource webResource = client.resource(platform.baseURL + resourceURL);
		webResource.setProperty("Content-Type", MediaType.APPLICATION_JSON);

		
        JSONObject jsonObj = new JSONObject(item);// convert the List Item object into a JSON object
        jsonObj = updateSafeListItem (jsonObj);// remove all JSON elements that can't be in the Request Content Body
        jsonbody=jsonObj.toString();// convert the JSON object into the Request Content Body


		try {
	        response = 	webResource.accept("application/json").
									header("Authorization", authorizationvalue).
									header("Content-Type","application/json; charset=UTF-8").
									post(ClientResponse.class, jsonbody);// make the POST call
		} catch (UniformInterfaceException e) {
			response = e.getResponse();
		}
	
		if (response.getStatus() == Response.Status.OK.getStatusCode()) {
		    //out.println(response.getEntity(String.class));
		} else {
			out.println("Response Status: " + response.getStatus());
			out.println("Response Body" + jsonbody);
		}
		

}
	public void deleteListItem (ListItem item, String key) throws UnsupportedEncodingException {
		// add the specified list item using the specified OAuth access token
		String authorizationvalue = "OAuth " + key;
		String itemid = URLEncoder.encode(item.getID(), "UTF-8");
		resourceURL = "/common/listitems/"+itemid;
		String query = "?listId=" +URLEncoder.encode(item.getListID(), "UTF-8");

	    
		WebResource webResource = client.resource(platform.baseURL + resourceURL+query);
		webResource.setProperty("Content-Type", MediaType.APPLICATION_JSON);



		try {
	        response = 	webResource.accept("application/json").
									header("Authorization", authorizationvalue).
									//header("Content-Type","application/json; charset=UTF-8").
									delete(ClientResponse.class, jsonbody);// make the DELETE call
		} catch (UniformInterfaceException e) {
			response = e.getResponse();
		}
	
		if (response.getStatus() == Response.Status.OK.getStatusCode()) {
		    out.println(response.getEntity(String.class));
		} else {
			out.println("Response Status: " + response.getStatus());
			out.println("Response Body" + jsonbody);
		}
		

}
	private JSONObject updateSafeEntry (JSONObject jsonObj) {
	/* This method strips elements from a JSONObject of an ExpenseEntry that are read-only fields in Concur Expense.
	 * This makes the JSONObject safe to use in update or add entry methods
	 */
	jsonObj.remove("postedAmount");
	jsonObj.remove("approvedAmount");
	jsonObj.remove("EmployeeMiddleInitial");
	jsonObj.remove("employeeMiddleInitial");
    jsonObj.remove("AllocationType");
    jsonObj.remove("ElectronicReceiptID");
    jsonObj.remove("TripID");
    jsonObj.remove("ExchangeRate");
    jsonObj.remove("ID");
    jsonObj.remove("IsBillable");
    jsonObj.remove("IsPaidByExpensePay");
    jsonObj.remove("ReportOwnerID");
    jsonObj.remove("createDate");
    jsonObj.remove("lastModifiedDate");
    jsonObj.remove("Custom1");
    jsonObj.remove("Custom2");
    jsonObj.remove("Custom3");
    jsonObj.remove("Custom4");
    jsonObj.remove("Custom5");
    jsonObj.remove("Custom6");
    jsonObj.remove("Custom7");
    jsonObj.remove("Custom8");
    jsonObj.remove("Custom9");
    jsonObj.remove("Custom10");
    jsonObj.remove("Custom11");
    jsonObj.remove("Custom12");
    jsonObj.remove("Custom13");
    jsonObj.remove("Custom14");
    jsonObj.remove("Custom15");
    jsonObj.remove("Custom16");
    jsonObj.remove("Custom17");
    jsonObj.remove("Custom18");
    jsonObj.remove("Custom19");
    jsonObj.remove("Custom20");
    jsonObj.remove("Custom21");
    jsonObj.remove("Custom22");
    jsonObj.remove("Custom23");
    jsonObj.remove("Custom24");
    jsonObj.remove("Custom25");
    jsonObj.remove("Custom26");
    jsonObj.remove("Custom27");
    jsonObj.remove("Custom28");
    jsonObj.remove("Custom29");
    jsonObj.remove("Custom30");
    jsonObj.remove("Custom31");
    jsonObj.remove("Custom32");
    jsonObj.remove("Custom33");
    jsonObj.remove("Custom34");
    jsonObj.remove("Custom35");
    jsonObj.remove("Custom36");
    jsonObj.remove("Custom37");
    jsonObj.remove("Custom38");
    jsonObj.remove("Custom39");
    jsonObj.remove("Custom40");
    jsonObj.remove("orgUnit1");
    jsonObj.remove("orgUnit2");
    jsonObj.remove("orgUnit3");
    jsonObj.remove("orgUnit4");
    jsonObj.remove("orgUnit5");
    jsonObj.remove("orgUnit6");
   
    return jsonObj;
}
	private JSONObject updateSafeMileageEntry (JSONObject jsonObj) {
		/* This method strips elements from a JSONObject of an ExpenseEntry that are read-only fields in Concur Expense.
		 * This makes the JSONObject safe to use in update or add entry methods
		 */
		jsonObj.remove("transactionAmount");
		jsonObj.remove("transactionCurrencyCode");
		jsonObj.remove("paymentTypeID");
		jsonObj.remove("exchangeRate");
		jsonObj.remove("locationID");
		jsonObj.remove("postedAmount");
		jsonObj.remove("approvedAmount");
		jsonObj.remove("EmployeeMiddleInitial");
		jsonObj.remove("employeeMiddleInitial");
	    jsonObj.remove("AllocationType");
	    jsonObj.remove("ElectronicReceiptID");
	    jsonObj.remove("TripID");
	    jsonObj.remove("ExchangeRate");
	    jsonObj.remove("ID");
	    jsonObj.remove("IsBillable");
	    jsonObj.remove("IsPaidByExpensePay");
	    jsonObj.remove("ReportOwnerID");
	    jsonObj.remove("createDate");
	    jsonObj.remove("lastModifiedDate");
	    jsonObj.remove("Custom1");
	    jsonObj.remove("Custom2");
	    jsonObj.remove("Custom3");
	    jsonObj.remove("Custom4");
	    jsonObj.remove("Custom5");
	    jsonObj.remove("Custom6");
	    jsonObj.remove("Custom7");
	    jsonObj.remove("Custom8");
	    jsonObj.remove("Custom9");
	    jsonObj.remove("Custom10");
	    jsonObj.remove("Custom11");
	    jsonObj.remove("Custom12");
	    jsonObj.remove("Custom13");
	    jsonObj.remove("Custom14");
	    jsonObj.remove("Custom15");
	    jsonObj.remove("Custom16");
	    jsonObj.remove("Custom17");
	    jsonObj.remove("Custom18");
	    jsonObj.remove("Custom19");
	    jsonObj.remove("Custom20");
	    jsonObj.remove("Custom21");
	    jsonObj.remove("Custom22");
	    jsonObj.remove("Custom23");
	    jsonObj.remove("Custom24");
	    jsonObj.remove("Custom25");
	    jsonObj.remove("Custom26");
	    jsonObj.remove("Custom27");
	    jsonObj.remove("Custom28");
	    jsonObj.remove("Custom29");
	    jsonObj.remove("Custom30");
	    jsonObj.remove("Custom31");
	    jsonObj.remove("Custom32");
	    jsonObj.remove("Custom33");
	    jsonObj.remove("Custom34");
	    jsonObj.remove("Custom35");
	    jsonObj.remove("Custom36");
	    jsonObj.remove("Custom37");
	    jsonObj.remove("Custom38");
	    jsonObj.remove("Custom39");
	    jsonObj.remove("Custom40");
	    jsonObj.remove("orgUnit1");
	    jsonObj.remove("orgUnit2");
	    jsonObj.remove("orgUnit3");
	    jsonObj.remove("orgUnit4");
	    jsonObj.remove("orgUnit5");
	    jsonObj.remove("orgUnit6");
	   
	    return jsonObj;
	}
	private JSONObject updateSafeListItem (JSONObject jsonObj) {
		/* This method strips elements from a JSONObject of a List Item that are read-only fields in Concur Expense.
		 * This makes the JSONObject safe to use in update or add list item methods
		 */
		
	    jsonObj.remove("document");
	    jsonObj.remove("ParentID");
	    jsonObj.remove("ID");
	    jsonObj.remove("URI");
	    return jsonObj;
	}
}
