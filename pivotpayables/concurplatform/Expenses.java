
package com.pivotpayables.concurplatform;

/**
 * @author John Toman
 * 8/14/15
 * The Expenses class provides a series of methods for access Concur APIs related to expense entries or "expenses".
 *
 */
// java libraries
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.List;


import static java.lang.System.out;

import java.lang.Byte;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;


//JAX-WS, RS client side APIs version 2.x
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.pivotpayables.expensesimulator.GUID;
// jeresy version 1.x
import com.sun.jersey.api.client.*;

// jackson version 1.x
import org.codehaus.jackson.map.*;
import org.codehaus.jackson.JsonParseException;

// java json
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Expenses {
	static protected ArrayList<Expense> expenses = new ArrayList<Expense>();
	protected Expense expense;// placeholder for an Expense object
	protected ExpenseReport report;// placeholder for an ExpenseReport object
	static protected ArrayList<Itemization> itemizations = new ArrayList<Itemization>();
	protected Itemization itemization; // placeholder for an Itemization object
	protected String jsonbody="";// placeholder for Content body in JSON
	protected String xmlbody="";// placeholder for Content body in XML
	protected ClientResponse response;// placeholder for a jersey-client, ClientRepsone object
	protected String resourceURL = "/expense/expenses";// the path to the Expenses resource
	protected String reportid;
	protected String user;
	protected ConcurFunctions platform = new ConcurFunctions();// get an instance of the Concur Platform functions
	protected Client client = platform.getClient();// get a jersey Client to the Concur platform.
	protected ExpenseReports reports;
	protected final String pagelimit = "limit=25";// set the default query string parameter: page limit to 25 expenses per page
	protected String query;
	
	public ArrayList<Expense> getExpenses (String key, Map<String, String> queryparameters) throws JSONException, JsonParseException, JsonMappingException, IOException {
		// get expenses for specified query parameters using the specified OAuth access token
		
		// Process the query string parameters
		
		resourceURL = "/expense/entries";// the path to the Expenses resource
		ArrayList<Expense> expenses = new ArrayList<Expense>();// initialize ArrayList of Expense object elements

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
		WebResource webResource = client.resource(platform.baseURL + resourceURL + query);// construct the URI for the get expenses call
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
				jsonbody = response.getEntity(String.class);// make the GET expenses call
				} catch (UniformInterfaceException e) {
					response = e.getResponse();
				}// try block


			if (jsonbody.substring(2, 7).equals("Items")) {// then the response needs to changed into a JSON Array by removing the substring, {"Items:
				jsonbody = jsonbody.substring(9);// convert the JSON body into a JSON array by removing the first 8 characters,  {"Items:
			}// if block

			String[] parts = jsonbody.split("],");// split jsonbody into two parts: 0) the JSON Array of expenses, and 1) the Next Page URI
			jsonbody = parts[0] +"]";// assign the JSON Array to jsonbody
			nextpageURI = "{"+ parts[1];// assign the Next Page URI

			JSONArray jsonarray = new JSONArray(jsonbody);// convert jsonbody into a JSON Array
			ObjectMapper mapper = new ObjectMapper();// create an instance of the Jackson mapper
			for(int i=0; i<jsonarray.length(); i++){// iterate for each JSON object in the array
		        JSONObject obj = jsonarray.getJSONObject(i);// get the JSON object for this iteration
		        expense = mapper.readValue(obj.toString(),Expense.class);// have the mapper convert the JSON object into an Expense object
		        itemizations = new ArrayList<Itemization>();// initialize ArrayList of itemization objects for this expense
		        if (expense.getHasItemizations()) {// then get its itemizations from Concur Expense
		        	queryparameters = new HashMap<String, String>();
					queryparameters.put("entryID", expense.getEntry_ID());
					queryparameters.put("user", expense.getReportOwnerID());// search for expenses where the Report Owner is the specified user
					itemizations = getItemizations(key, queryparameters);// get Itemizations for the specified EntryID
					for (int k=0; k< itemizations.size(); k++) { // Iterate for each itemization in the expense
						itemization = itemizations.get(k);// get the itemization for this iteration
						itemization.setID(GUID.getGUID(4));// assign a Pivot Payables GUID to this itemization
						itemization.setTransactionCurrencyCode(expense.getOriginalCurrency());// copy the Expense's original currency to the itemization
						itemization.setPostedCurrency(expense.getPostedCurrency());// copy the Expense's posted currency to the itemization
					}// for k loop
		        } else {// then create an itemization for this regular expense
		        	itemization = new Itemization();
		        	itemization.setID(GUID.getGUID(4));
		        	itemization.setEntry_ID(expense.getEntry_ID());		
		    		itemization.setExpenseTypeName(expense.ExpenseTypeName);
		    		itemization.setIsBillable(expense.getHasBillableItems());
		    		itemization.setDate(expense.getTransactionDate());
		    		itemization.setTransactionAmount(expense.OriginalAmount);
		    		itemization.setTransactionCurrencyCode(expense.OriginalCurrency);
		    		itemization.setPostedAmount(expense.PostedAmount);
		    		itemization.setApprovedAmount(expense.ApprovedAmount);
		    		itemization.setPostedCurrency(expense.PostedCurrency);
		    		itemization.setLastModified(expense.LastModified);
		    		itemization.setCustom1(expense.getCustom1());
		    		itemization.setCustom2(expense.getCustom2());
		    		itemization.setCustom3(expense.getCustom3());
		    		itemization.setCustom4(expense.getCustom4());
		    		itemization.setCustom5(expense.getCustom5());
		    		itemization.setCustom6(expense.getCustom6());
		    		itemization.setCustom7(expense.getCustom7());
		    		itemization.setCustom8(expense.getCustom8());
		    		itemization.setCustom9(expense.getCustom9());
		    		itemization.setCustom10(expense.getCustom10());
		    		itemization.setCustom11(expense.getCustom11());
		    		itemization.setCustom12(expense.getCustom12());
		    		itemization.setCustom13(expense.getCustom13());
		    		itemization.setCustom14(expense.getCustom14());
		    		itemization.setCustom15(expense.getCustom15());
		    		itemization.setCustom16(expense.getCustom16());
		    		itemization.setCustom17(expense.getCustom17());
		    		itemization.setCustom18(expense.getCustom18());
		    		itemization.setCustom19(expense.getCustom19());
		    		itemization.setCustom20(expense.getCustom20());
		    		itemization.setCustom21(expense.getCustom21());
		    		itemization.setCustom22(expense.getCustom22());
		    		itemization.setCustom23(expense.getCustom23());
		    		itemization.setCustom24(expense.getCustom24());
		    		itemization.setCustom25(expense.getCustom25());
		    		itemization.setCustom26(expense.getCustom26());
		    		itemization.setCustom27(expense.getCustom27());
		    		itemization.setCustom28(expense.getCustom28());
		    		itemization.setCustom29(expense.getCustom29());
		    		itemization.setCustom30(expense.getCustom30());
		    		itemization.setCustom31(expense.getCustom31());
		    		itemization.setCustom32(expense.getCustom32());
		    		itemization.setCustom33(expense.getCustom33());
		    		itemization.setCustom34(expense.getCustom34());
		    		itemization.setCustom35(expense.getCustom35());
		    		itemization.setCustom36(expense.getCustom36());
		    		itemization.setCustom37(expense.getCustom37());
		    		itemization.setCustom38(expense.getCustom38());
		    		itemization.setCustom39(expense.getCustom39());
		    		itemization.setCustom40(expense.getCustom40());
		    		itemization.setOrgUnit1(expense.getOrgUnit1());
		    		itemization.setOrgUnit2(expense.getOrgUnit2());
		    		itemization.setOrgUnit3(expense.getOrgUnit3());
		    		itemization.setOrgUnit4(expense.getOrgUnit4());
		    		itemization.setOrgUnit5(expense.getOrgUnit5());
		    		itemization.setOrgUnit6(expense.getOrgUnit6());
		    		itemizations.add(itemization);

		        }// if has itemizations block

		        expense.setItems(itemizations);
		        expenses.add(expense);// add the expense to the ArrayList of Expense elements
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
		
	
		return expenses;
	}
	public Expense getExpense (ExpenseReport report, String expenseid, String key) throws JSONException, JsonParseException, JsonMappingException, IOException {
		// get the specified expense expense using specified OAuth access token
		String user = report.getOwnerLoginID();
		String query = null;
		if (user.length() > 0 ) {
			query = "?id=" + expenseid + "&user=" + user;
		}

		WebResource webResource = client.resource("https://www.concursolutions.com/api/v3.0/expense/entries"+ query);// construct the URI for the get expense by Expense ID
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
		        expense = mapper.readValue(jsonbody,Expense.class);// have the mapper convert the JSON expense expense into an Expense object
		        expense.setID(GUID.getGUID(4));// assign a Pivot Payables GUID to this expense
				expense.setEmployeeDisplayName(report.getOwnerName());// the employee who incurred the expense
				expense.setApprovalStatus(report.getApprovalStatusCode());// the approval status of the expense report that contains this expense
				expense.setPaymentStatus(report.getPaymentStatusCode());// the payment status of the expense report that contains this expense. Is "Paid" when Concur Expense extracts the expense report.
				expense.setPaidDate(report.getPaidDate());// when the expense report this expense entry is a member was paid
				expense.setPostedCurrency(report.getCurrencyCode());
				

			} else {
				out.println("Response Status: " + response.getStatus());
				out.println("Response Body" + jsonbody);
			   expense = null;// set Expense object to null
			}

	        return expense;
	}
	public ImageURL getExpenseImageURL (Expense expense, String key) throws IOException {
		//Byte[] image;
		ImageURL imageurl = null;
		


		
		WebResource webResource = client.resource("https://www.concursolutions.com/api/image/v1.0/expenseentry/"+ expense.getEntry_ID());// construct the URI for the get expense image by Expense ID
		String authorizationvalue = "OAuth " + key;// construct the Authorization header using the specified, OAuth access token

		try {
			response  = webResource.accept(MediaType.APPLICATION_XML).
									header("Authorization", authorizationvalue).
									header("Content-Type","application/xml; charset=UTF-8").
									get(ClientResponse.class);
			String xml = response.getEntity(String.class);

			if (xml.contains("Image")) {// then there is an image for this expense entry
				xml = xml.replace("Image xmlns=\"http://www.concursolutions.com/api/image/2011/02\" xmlns:i=\"http://www.w3.org/2001/XMLSchema-instance\"", "Image");
		        try {
		        	JAXBContext jc = JAXBContext.newInstance(ImageURL.class);
	
		            Unmarshaller unmarshaller = jc.createUnmarshaller();
		            StringReader reader = new StringReader(xml);
		            imageurl = (ImageURL) unmarshaller.unmarshal(reader);
	
			        } catch (JAXBException e) {
			            e.printStackTrace();
			        }
			}// if (xml.contains(<Image))
		    	} catch (UniformInterfaceException e) {
					response = e.getResponse();
				}

	        return imageurl;
	}
	
	public void updateExpense (Expense expense, String key) throws UnsupportedEncodingException {
		// update the specified expense expense using specified OAuth access token
		
		String expenseid = expense.getID();// get the ExpenseID from the Expense object
		String ownerid = expense.getReportOwnerID();// get the Owner Login ID 
		String user = URLEncoder.encode(ownerid, "UTF-8");// URL Encode it
		
		WebResource webResource = client.resource(platform.baseURL + resourceURL + "/"+ expenseid + "?user=" + user);// construct the URI for the put expense by Expense ID

		webResource.setProperty("Content-Type", MediaType.APPLICATION_JSON);// content is JSON
		String authorizationvalue = "OAuth " + key;// construct the Authorization header using the specified, OAuth access token
		
        JSONObject jsonObj = new JSONObject(expense);// convert the Expense object into a JSON object
        jsonObj = updateSafeExpense (jsonObj);// remove all JSON elements that can't be in the Request Content Body
        jsonbody=jsonObj.toString();// convert the JSON object into the Request Content Body
		try {
	        response = 	webResource.accept("application/json").
									header("Authorization", authorizationvalue).
									header("Content-Type","application/json; charset=UTF-8").
									put(ClientResponse.class, jsonbody);// make the PUT Expense call
		} catch (UniformInterfaceException e) {
			response = e.getResponse();
		}
	
		if (response.getStatus() != 204) {// then there is an error
			out.println("Response Status: " + response.getStatus());
			out.println("Response Body" + jsonbody);
		}
		

}

	public void addExpense (Expense expense, String key) {
		// add the specified expense expense using the specified OAuth access token
		String authorizationvalue = "OAuth " + key;

		/*
		ClientConfig config = new DefaultClientConfig();
	    config.getClasses().add(JacksonJaxbJsonProvider.class);
	    config.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
	    Client client = Client.create(config);
	    */
	    
		WebResource webResource = client.resource(platform.baseURL + resourceURL);
		webResource.setProperty("Content-Type", MediaType.APPLICATION_JSON);

		
        JSONObject jsonObj = new JSONObject(expense);// convert the Expense object into a JSON object
        jsonObj = updateSafeExpense (jsonObj);// remove all JSON elements that can't be in the Request Content Body
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
		    out.println(response.getEntity(String.class));
		} else {
			out.println("Response Status: " + response.getStatus());
			out.println("Response Body" + jsonbody);
		}

}
	public ArrayList<Itemization> getItemizations (String key, Map queryparameters) throws JSONException, JsonParseException, JsonMappingException, IOException {
		// get itemizations for specified query parameters using the specified OAuth access token
		
		// Process the query string parameters
		resourceURL = "/expense/itemizations";
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
	public  ArrayList<com.pivotpayables.concurplatform.List> getLists (String key) throws JSONException, JsonParseException, JsonMappingException, IOException{
		// get Lists for the Concur entity associated to the specified OAuth access token
			
		resourceURL = "/common/lists";// the path to the Lists resource
		ArrayList<com.pivotpayables.concurplatform.List> lists = new ArrayList<com.pivotpayables.concurplatform.List>();// initialize ArrayList of List object elements
		com.pivotpayables.concurplatform.List list = null;

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
			out.println(jsonbody);

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
		        list = mapper.readValue(obj.toString(),com.pivotpayables.concurplatform.List.class);// have the mapper convert the JSON object into an List object
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
	
	public ReportDetails getReportDetails (String reportID, String key) throws IOException {
	
		List<ExpenseEntry> expenses=null;
		ReportDetails details = new ReportDetails();
		
		WebResource webResource = client.resource("https://www.concursolutions.com/api/expense/expensereport/v2.0/report/"+ reportID);// construct the URI for the get Report Details by Report ID
		String authorizationvalue = "OAuth " + key;// construct the Authorization header using the specified, OAuth access token


		try {
			response  = webResource.accept(MediaType.APPLICATION_XML).
									header("Authorization", authorizationvalue).
									header("Content-Type","application/xml; charset=UTF-8").
									get(ClientResponse.class);
			String xml = response.getEntity(String.class);
			//out.println(xml);



			if (xml.contains("ReportDetails")) {// then there are details for this Report ID
				xml = xml.replace("ReportDetails xmlns=\"http://www.concursolutions.com/api/expense/expensereport/2012/07\" xmlns:i=\"http://www.w3.org/2001/XMLSchema-instance\"", "ReportDetails");
				xml = xml.replace("i:nil=\"true\"", "");// make empty any element with i:nill = "true" so JAXB can unmarshal the XML
				
				try {
		        	JAXBContext jc = JAXBContext.newInstance(ReportDetails.class);
	
		            Unmarshaller unmarshaller = jc.createUnmarshaller();
		            StringReader reader = new StringReader(xml);
		            details = (ReportDetails) unmarshaller.unmarshal(reader);

	
		        } catch (JAXBException e) {
		            e.printStackTrace();
		        }
				

			}// if (xml.contains("ReportDetails"))
			
			
		    	} catch (UniformInterfaceException e) {
					response = e.getResponse();
				}

	        return details;
	}
	private JSONObject updateSafeExpense (JSONObject jsonObj) {
	/* This method strips elements from a JSONObject of an Expense that are read-only fields in Concur Expense.
	 * This makes the JSONObject safe to use in update or add expense report methods
	 */
	
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
    jsonObj.remove("ApprovedAmount");
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

}
