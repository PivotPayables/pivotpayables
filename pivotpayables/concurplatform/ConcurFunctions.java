/**
 * 
 */
package com.pivotpayables.concurplatform;

//java

import static java.lang.System.out;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.ws.rs.core.MediaType;
//JAXB XML Binding
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.pivotpayables.expensesimulator.Employee;
import com.pivotpayables.expensesimulator.GUID;

//jeresy version 1.x
import com.sun.jersey.api.client.*;
import com.sun.jersey.api.client.config.*;
import com.sun.jersey.api.json.*;

import org.codehaus.jackson.JsonParseException;
//jackson version 1.x
import org.codehaus.jackson.jaxrs.*;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.InputSource;


/**
 * @author John Toman
 * 2/13/2015
 * 
 * The class's methods provide access to the Concur Platform.
 *
 */


public class ConcurFunctions {
	protected final String host ="localhost";//MongoDB server host
	protected final int port = 27017;//MongoDB server port 
	protected final String baseURL = "https://www.concursolutions.com/api/v3.0";
	protected String resourceURL = null;// the path to the Concur resource
	protected String jsonbody="";// placeholder for Content body in JSON
	protected String xmlbody="";// placeholder for Content body in XML
	protected ClientResponse response;// placeholder for a jersey-client, ClientRepsone object
	@SuppressWarnings("rawtypes")
	private static Iterator it;
	
	public Client getClient () {
		ClientConfig config = new DefaultClientConfig();
	    config.getClasses().add(JacksonJaxbJsonProvider.class);
	    config.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
	    Client client = Client.create(config);
	    return client;
	}
	
	public static OAuthToken GetOAuthToken(String key, String name, String password) throws JAXBException  {
		// using Native OAuth, get from Concur an OAuth access token for the specified partner application key, and specified Concur Login ID and password
		// Native OAuth uses HTTP Basic authentication with the specified Concur Login ID, name, and Password, password

		final String NativeOAuthUrl = 
				"https://www.concursolutions.com/net2/oauth2/accesstoken.ashx";

		String response = makeRequestBasic(NativeOAuthUrl, key, name, password);
		if (response.substring(0, 4).equals("null")) {// then the response needs to fixed by removing the substring, null
			response = response.substring(4);//remove the first four characters, null
		}// if block
		
		 // create JAXB context and instantiate marshaller
	    JAXBContext context = JAXBContext.newInstance(OAuthToken.class);
	    Marshaller m = context.createMarshaller();
	    m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);


	    // get variables from our xml file, created before
	    Unmarshaller um = context.createUnmarshaller();
	    OAuthToken token = (OAuthToken) um.unmarshal(new InputSource(new StringReader(response)));
	    token.setID(GUID.getGUID(5));
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss a");
		Date expirationDate=null;
		
		try {
			expirationDate = sdf.parse(token.getstrExpirationDate());
			token.setExpirationDate(expirationDate);
	 
		} catch (ParseException e) {
			e.printStackTrace();
		}
	   
	    return token;
	}

	private static String makeRequestBasic(String stringUrl, String key, String name, String password) {
		String response = null;

		String authString = name + ":" + password;
		byte[] authEncBytes = Base64.getEncoder().encode(authString.getBytes());
		String authStringEnc = new String(authEncBytes);


		try {
		    URL url = new URL(stringUrl);
		    URLConnection conn = url.openConnection();
			conn.setRequestProperty("Authorization", "Basic " + authStringEnc);
			conn.setRequestProperty("X-ConsumerKey", key);
		    conn.setDoInput(true);
		    
		    BufferedReader in = 
			new BufferedReader(new InputStreamReader(conn.getInputStream()));
		    String chunk = null;
		    while ((chunk = in.readLine()) != null) response += chunk;
		    in.close();
		    return response;
		}
		catch(Exception e) { 
			throw new RuntimeException("Arrrg! " + e); 
		}
		
	}
	
	public  ArrayList<List> getLists (String key) throws JSONException, JsonParseException, JsonMappingException, IOException{
		// get Lists for the Concur entity associated to the specified OAuth access token
			
		resourceURL = "/common/lists";// the path to the Lists resource
		ArrayList<List> lists = new ArrayList<List>();// initialize ArrayList of List object elements
		List list = null;

		WebResource webResource = this.getClient().resource(this.baseURL + resourceURL);// construct the URI for the get lists call
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
				webResource = this.getClient().resource(nextpageURI);// set the Web Resource to the Next Page URI	
			} else {
				nextpageflag = false;// set the flag false so it will terminate the while loop
			}// if nextpageURI block
		} //while loop
			
		return lists;
	}
	
	@SuppressWarnings("rawtypes")
	public  ArrayList<ListItem> getListItems (String key, Map queryparameters) throws JSONException, JsonParseException, JsonMappingException, IOException{
		// get Lists for the Concur entity associated to the specified OAuth access token
			
		resourceURL = "/common/listitems";// the path to the Lists resource
		ArrayList<ListItem> items = new ArrayList<ListItem>();// initialize ArrayList of List object elements
		ListItem item = null;
		
		// Process the query string parameters
		String query = "?limit=25";// set the default query string parameter: page limit to 25 reports per page

		
		if (queryparameters.size() > 0) {// then there are query string parameters to add to the default query
			it = queryparameters.entrySet().iterator();// create an Iterator to iterate the entries in the Map
			while (it.hasNext()) {// iterate for each entry in the Map
		        Map.Entry pair = (Map.Entry)it.next();// get the Map entry (key, value) for this iteration
				query= query + "&"+pair.getKey() + "="+ pair.getValue();// add its key and value to the query string
		        it.remove(); // avoids a ConcurrentModificationException// remove the entry from the iteration
		    }
		}
		
		WebResource webResource = this.getClient().resource(this.baseURL + resourceURL + query);// construct the URI for the get list items call
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
			} 
			
			else {
				return items;
			}// if (jsonbody.substring(2, 7).equals("Items"))
			

			String[] parts = jsonbody.split("],");// split jsonbody into two parts: 0) the JSON Array of lists, and 1) the Next Page URI
			jsonbody = parts[0] +"]";// assign the JSON Array to jsonbody
			nextpageURI = "{"+ parts[1];// assign the Next Page URI

			JSONArray jsonarray = new JSONArray(jsonbody);// convert jsonbody into a JSON Array
			ObjectMapper mapper = new ObjectMapper();// create an instance of the Jackson mapper
			for(int i=0; i<jsonarray.length(); i++){// iterate for each JSON object in the array
		        JSONObject obj = jsonarray.getJSONObject(i);// get the JSON object for this iteration
		        item = mapper.readValue(obj.toString(),ListItem.class);// have the mapper convert the JSON object into an List object
		        items.add(item);// add the list to the ArrayList of List elements
		    }// for i loop 

		    NextPageURI uri = mapper.readValue(nextpageURI, NextPageURI.class);
		    nextpageURI = uri.NextPage;
		    
			if (nextpageURI != null) {// then get the next page
				nextpageflag = true;
				webResource = this.getClient().resource(nextpageURI);// set the Web Resource to the Next Page URI	
			} 
			else {
				nextpageflag = false;// set the flag false so it will terminate the while loop
			}// if nextpageURI block
		} //while loop
			
		return items;
	}
	public ListItem getListItemByLongCode (String key, String listid, String code) throws JsonParseException, JsonMappingException, JSONException, IOException {
		// this returns a ListItem object for the list item from the specified List and the specified long code
		
		Map<String, String> queryparameters = new HashMap<String, String>();// a HashMap to hold key-value pairs for a query parameter
		ArrayList<ListItem> items = new ArrayList<ListItem>();// initialize the ArrayList
		ListItem item = new ListItem();// placeholder for a list item
	
		queryparameters.put("listId", listid);// add the listid search term to the query parameters
	
		if (code.contains("-")) {// then it contains a hyphen character, which means this is a Long Code
			String[] parts = code.split("-");// split code into parts using the hyphen as the delimiter; one part per level in the Long Code
			
			for (int i=0; i<parts.length; i++) {// iterate through each level in the Long Code
				code = parts[i];
				code = URLEncoder.encode(code, "UTF-8");
				queryparameters.put("level" + Integer.toString(i+1) + "code", code);// add the search term to the query parameters for this iterations level
			}
		} else {// then it doesn't contain a hyphen, which means this is a Short Code.  There is only one level.
			code=URLEncoder.encode(code, "UTF-8");
			queryparameters.put("level1Code", code);// add the item code search term to the query parameters
		}
		items = this.getListItems(key, queryparameters);// pull the list item for this list from Concur
		if (items.size() > 0) { // then there is at least one list item to process
	    	item = items.get(0);// get the first, and what should be only, list item for the specified List Item Long Code
		}
		return item;
	}
	public String getListItemID (String key, String listid, String code, String level) throws JsonParseException, JsonMappingException, JSONException, IOException {
		// this returns the Item Name for the list item from the specified List and the Code for the specified Level
		
		Map<String, String> queryparameters = new HashMap<String, String>();// a HashMap to hold key-value pairs for a query parameter
		ArrayList<ListItem> items = new ArrayList<ListItem>();// initialize the ArrayList
		ListItem item = new ListItem();// placeholder for a list item
		String id = null;
		
		
		queryparameters.put("listId", listid);// add the listid search term to the query parameters
		queryparameters.put("level" + level + "Code", code);// add the item code search term to the query parameters
		
		items = this.getListItems(key, queryparameters);// pull the list items for this list from Concur Expense
		if (items.size() > 0) { // then there is at least one list item to process
	    	item = items.get(0);// get the first, and what should be only, list item for the specified List and Item Code
	    	id = item.getListID();
		}
		return id;
	}
	public ConcurUser getUser (String loginid, String key) throws IOException {
		/* This method uses the Concur Platform V1.1 API for getting a list of Concur users.  
		 * 
		 */
		
		ConcurUser cu=null;
		WebResource webResource = this.getClient().resource("https://www.concursolutions.com/api/user/v1.0/User/?loginid="+loginid);// construct the URI for the Get User by login ID
		String authorizationvalue = "OAuth " + key;// construct the Authorization header using the specified, OAuth access token
		
		try {
			response  = webResource.accept(MediaType.APPLICATION_XML).
									header("Authorization", authorizationvalue).
									header("Content-Type","application/xml; charset=UTF-8").
									get(ClientResponse.class);
			String xml = response.getEntity(String.class);
			if (xml.substring(1, 5).equals("User")) {// then there is s user to process.  The response needs to changed into a JSON Array by removing the characters after User
				xml = xml.replace("UserProfile xmlns=\"http://www.concursolutions.com/api/user/2011/02\" xmlns:i=\"http://www.w3.org/2001/XMLSchema-instance\"", "UserProfile");
		        try {
		        	JAXBContext jc = JAXBContext.newInstance(ConcurUser.class);
	
		            Unmarshaller unmarshaller = jc.createUnmarshaller();
		            StringReader reader = new StringReader(xml);
		            cu = (ConcurUser) unmarshaller.unmarshal(reader);
			        } catch (JAXBException e) {
			            e.printStackTrace();
			        }
			}// if block
	    	} catch (UniformInterfaceException e) {
				response = e.getResponse();
			}
			
	        return cu;
	}
	public Employee getEmployee (String user, String key) throws IOException {

		
		String loginid = URLEncoder.encode(user, "UTF-8");// URL Encode it
        ConcurUser cu = null;// placeholder for a ConcurUser object
        Employee employee = null;// placeholder for an Employee object
        cu = this.getUser(loginid, key);

        if (cu != null) {// then there is a Concur User to process
        	employee = new Employee ();
        	employee.setFirstName(cu.getFirstName());
        	employee.setLastName(cu.getLastName());
	        if (!(cu.getMi().isEmpty())) {
	        	employee.setMiddleInitial(cu.getMi());
	        }
	        employee.setDisplayName(employee.getFirstName() + " "+ employee.getLastName());
	        employee.setFullName(employee.getFirstName() + " "+ employee.getMiddleInitial() + " " + employee.getLastName());
	        employee.setLoginID(user);
	        employee.setID(GUID.getGUID(4));
	        employee.setEmployee_ID(cu.getEmpId());
	        Location home = new Location();
	        home.setCity("Unknown");
	        home.setCountryCode(cu.getCtryCode());
	        home.setState(cu.getCtrySubCode());
	        employee.setHome(home);
        }// if cu != null block
        
        return employee;

	}
	
	@SuppressWarnings("rawtypes")
	public  ArrayList<LocalizedData> getLocalizedData (String key, Map queryparameters) throws JSONException, JsonParseException, JsonMappingException, IOException{
		// get localized data for expense types, ledger, payMethod,  for the Concur entity associated to the specified OAuth access token
			
		resourceURL = "/invoice/localizeddata";// the path to the localized data resource
		ArrayList<LocalizedData> items = new ArrayList<LocalizedData>();// initialize ArrayList of LocalizedData object elements
		LocalizedData item = null;
		
		// Process the query string parameters
		String query = "?limit=100";// set the default query string parameter: page limit to 100 reports per page

		
		if (queryparameters.size() > 0) {// then there are query string parameters to add to the default query
			it = queryparameters.entrySet().iterator();// create an Iterator to iterate the entries in the Map
			while (it.hasNext()) {// iterate for each entry in the Map
		        Map.Entry pair = (Map.Entry)it.next();// get the Map entry (key, value) for this iteration
				query= query + "&"+pair.getKey() + "="+ pair.getValue();// add its key and value to the query string
		        it.remove(); // avoids a ConcurrentModificationException// remove the entry from the iteration
		    }
		}
		
		WebResource webResource = this.getClient().resource(this.baseURL + resourceURL + query);// construct the URI for the get localized data items call
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
			if (jsonbody.substring(2, 18).equals("LocalizationData")) {// then the response needs to be changed into a JSON Array by removing the substring, {"LocalizationData":
				jsonbody = jsonbody.substring(20);// convert the JSON body into a JSON array by removing the first 19 characters,  {"LocalizationData":
			} else {// then there is some error, so there are no localized data items to return
				return items;
			}// if (jsonbody.substring(2, 20).equals("LocalizationData"))
			

			String[] parts = jsonbody.split("],");// split jsonbody into two parts: 0) the JSON Array of localized data, and 1) the Next Page URI
			jsonbody = parts[0] +"]";// assign the JSON Array to jsonbody
			nextpageURI = "{"+ parts[1];// assign the Next Page URI

			JSONArray jsonarray = new JSONArray(jsonbody);// convert jsonbody into a JSON Array
			ObjectMapper mapper = new ObjectMapper();// create an instance of the Jackson mapper
			for(int i=0; i<jsonarray.length(); i++){// iterate for each JSON object in the array
		        JSONObject obj = jsonarray.getJSONObject(i);// get the JSON object for this iteration
		        item = mapper.readValue(obj.toString(),LocalizedData.class);// have the mapper convert the JSON object into a LocalizedData object
		        items.add(item);// add the localized data item to the ArrayList of LocalizedData elements
		    }// for i loop 

		    NextPageURI uri = mapper.readValue(nextpageURI, NextPageURI.class);
		    nextpageURI = uri.NextPage;
		    
			if (nextpageURI != null) {// then get the next page
				nextpageflag = true;
				webResource = this.getClient().resource(nextpageURI);// set the Web Resource to the Next Page URI	
			} 
			else {
				nextpageflag = false;// set the flag false so it will terminate the while loop
			}// if nextpageURI block
		} //while loop
			
		return items;
	}
	
 
}
