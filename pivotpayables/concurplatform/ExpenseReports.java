package com.pivotpayables.concurplatform;
/**
 * @author John Toman
 * 
 * This class's methods provide access to Concur APIs related to expense reports.
 * 
 * 7/1/17
 * I added new method to Get Expense Group Configuration.
 * 
 * Also, I repaired various errors and problems throughout the class.
 */
// java
import java.util.ArrayList;

import java.util.List;
import java.util.Map;
import java.util.Iterator;

import static java.lang.System.out;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;

import java.io.UnsupportedEncodingException;
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
import org.apache.http.impl.client.HttpClientBuilder;
// jackson version 1.x
import org.codehaus.jackson.map.*;
import org.codehaus.jackson.JsonParseException;

// java json
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ExpenseReports {
	static protected ArrayList<ExpenseReport> reports = new ArrayList<ExpenseReport>();
	protected ExpenseReport report;// placeholder for an expense report
	protected String jsonbody="";// placeholder for Content body in JSON
	protected String xmlbody="";// placeholder for Content body in XML
	protected ClientResponse response;// placeholder for a jersey-client, ClientRepsone object
	protected String resourceURL = "/expense/reports";// the path to the reports resoure
	ConcurFunctions platform = new ConcurFunctions();// get an instance of the Concur Platform Functions
	Client client = platform.getClient();// get a jersey Client to the Concur platform.
	
	
	
	public ArrayList<ExpenseReport> getReports (String key, @SuppressWarnings("rawtypes") Map queryparameters) throws JSONException, JsonParseException, JsonMappingException, IOException {
		// get reports for specified query parameters using the specified OAuth access token
		
		// Process the query string parameters
		String query = "?limit=25";// set the default query string parameter: page limit to 25 reports per page

		
		if (queryparameters.size() > 0) {// then there are query string parameters to add to the default query
			@SuppressWarnings("rawtypes")
			Iterator it = queryparameters.entrySet().iterator();// create an Iterator to iterate the entries in the Map
			while (it.hasNext()) {// iterate for each entry in the Map
		        @SuppressWarnings("rawtypes")
				Map.Entry pair = (Map.Entry)it.next();// get the Map entry (key, value) for this iteration
				query= query + "&"+pair.getKey() + "="+ pair.getValue();// add its key and value to the query string
		        it.remove(); // avoids a ConcurrentModificationException// remove the entry from the iteration
		    }
		}


		WebResource webResource = client.resource(platform.baseURL + resourceURL + query);// construct the URI for the get reports call
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
				jsonbody = response.getEntity(String.class);// make the GET reports call
			} catch (UniformInterfaceException e) {
				response = e.getResponse();
			}

			if (jsonbody.substring(2, 7).equals("Items")) {// then the response needs to changed into a JSON Array by removing the substring, {"Items:
				jsonbody = jsonbody.substring(9);//remove the first 8 characters,  {"Items:
			} else if(jsonbody.substring(2, 8).equals("Error")) {
				
			} // if block
			String[] parts = jsonbody.split("],");// split jsonbody into two parts: 0) the JSON Array of expense reports, and 1) the Next Page URI
			jsonbody = parts[0] +"]";// assign the JSON Array to jsonbody
			nextpageURI = "{"+ parts[1];//

			JSONArray jsonarray = new JSONArray(jsonbody);// convert jsonbody into a JSON Array
	
			ObjectMapper mapper = new ObjectMapper();// create an instance of the Jackson mapper
		    for(int i=0; i<jsonarray.length(); i++){// iterate for each JSON object in the array
		        JSONObject obj = jsonarray.getJSONObject(i);// get the JSON object for this iteration
		        parts = obj.getString("WorkflowActionUrl").split("\\?");// split the WorkflowActionURL into a path URL and a query string
		        obj.put("WorkflowActionUrl", parts[0]);// set the WorkflowActionURL so it has only the path URL; removes the query string
		        report = mapper.readValue(obj.toString(),ExpenseReport.class);// have the mapper convert the JSON object into an ExpenseReport object
		        reports.add(report);// add the report to the ArrayList of ExpenseReport elements
		    }// for i loop 
			//out.println("Checkpoint: Next Page URI at split from jsonbody response: " + nextpageURI);
		    NextPageURI uri = mapper.readValue(nextpageURI, NextPageURI.class);
		    nextpageURI = uri.NextPage;
			//out.println("Checkpoint: Next Page URI converted from jsonbody: " + nextpageURI);
		    
			if (nextpageURI != null) {// then get the next page
				nextpageflag = true;
				webResource = client.resource(nextpageURI);// set the Web Resource to the Next Page URI	
			} else {
				nextpageflag = false;
			}
			//out.println("Checkpoint: Next Page URI at end of while loop: " + nextpageURI);
			//out.println("Next page flag: " + nextpageflag);
		} //while loop
		return reports;
	}
	public ExpenseReport getReport (String reportid, String user, String key) throws JSONException, JsonParseException, JsonMappingException, IOException {
		// get the specified expense report using specified OAuth access token
		String query = null;
		if (user.length() > 0 ) {
			query = "?user=" + user;
		}

		WebResource webResource = client.resource(platform.baseURL + resourceURL + reportid + query);// construct the URI for the get report by Report ID
		webResource.setProperty("Content-Type", MediaType.APPLICATION_JSON);// content is JSON
		String authorizationvalue = "OAuth " + key;// construct the Authorization header using the specified, OAuth access token

			try {
				response  = webResource.accept(MediaType.APPLICATION_JSON).
						header("Authorization", authorizationvalue).
						header("Content-Type","application/json; charset=UTF-8").
				        get(ClientResponse.class);// make the GET Report call
				jsonbody = response.getEntity(String.class);
				out.println("Checkpoint: jsonbody" + jsonbody);
			} catch (UniformInterfaceException e) {
				response = e.getResponse();
			}
			if (response.getStatus() == Response.Status.OK.getStatusCode()) {// then it found the report in Concur Expense
				ObjectMapper mapper = new ObjectMapper();// create an instance of the Jackson mapper
		        report = mapper.readValue(jsonbody,ExpenseReport.class);// have the mapper convert the JSON expense report into an ExpenseReport object
			} else {
				out.println("Response Status: " + response.getStatus());
				out.println("Response Body" + jsonbody);
			   report = null;// set ExpenseReport object to null
			}

	        return report;
	}
	public void updateExpenseReport (ExpenseReport report, String key) throws UnsupportedEncodingException {
		// update the specified expense report using specified OAuth access token
		
		String reportid = report.getID();// get the ReportID from the ExpenseReport object
		String ownerid = report.getOwnerLoginID();// get the Owner Login ID 
		String user = URLEncoder.encode(ownerid, "UTF-8");// URL Encode it
		
		WebResource webResource = client.resource(platform.baseURL + resourceURL + "/"+ reportid + "?user=" + user);// construct the URI for the put report by Report ID

		webResource.setProperty("Content-Type", MediaType.APPLICATION_JSON);// content is JSON
		String authorizationvalue = "OAuth " + key;// construct the Authorization header using the specified, OAuth access token
		
        JSONObject jsonObj = new JSONObject(report);// convert the ExpenseReport object into a JSON object
        jsonObj = updateSafeReport (jsonObj);// remove all JSON elements that can't be in the Request Content Body
        jsonbody=jsonObj.toString();// convert the JSON object into the Request Content Body
		try {
	        response = 	webResource.accept("application/json").
									header("Authorization", authorizationvalue).
									header("Content-Type","application/json; charset=UTF-8").
									put(ClientResponse.class, jsonbody);// make the PUT Report call
		} catch (UniformInterfaceException e) {
			response = e.getResponse();
		}
	
		if (response.getStatus() != 204) {// then there is an error
			out.println("Response Status: " + response.getStatus());
			out.println("Response Body" + jsonbody);
		}
		

}

	public void addExpenseReport (ExpenseReport report, String key) {
		// add the specified expense report using the specified OAuth access token
		String authorizationvalue = "OAuth " + key;

		/*
		ClientConfig config = new DefaultClientConfig();
	    config.getClasses().add(JacksonJaxbJsonProvider.class);
	    config.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
	    Client client = Client.create(config);
	    */
	    
		WebResource webResource = client.resource(platform.baseURL + resourceURL);
		webResource.setProperty("Content-Type", MediaType.APPLICATION_JSON);

		
        JSONObject jsonObj = new JSONObject(report);// convert the ExpenseReport object into a JSON object
        jsonObj = updateSafeReport (jsonObj);// remove all JSON elements that can't be in the Request Content Body
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
	public String approveExpenseReport(ExpenseReport report, String status, String key) throws JAXBException, IOException  {// get from Concur an OAuth access token for the specified Concur user (empid) using Native OAuth
		// this approves the specified expense report using the specified OAuth access token
		
		/* The Approve Expense Report API is a Concur Platform V1.1 API.  The V1.1 API works differently than the V3.0 API in a couple important ways.
		 * Among these are the POST ExpenseReport/Submit call is unconventional. This is because it is a POST method with neither query string parameters nor a Request content body.  
		 * Instead the data for the post is in the path URI. 
		 * 
		 * This makes it difficult to use various HTTP client libraries such as the jersey client, URLConnection, or HTTPURLConnection because these 
		 * require a POST method to have a Request content body or query parameters.  The Apache HTTP client library is one of the few that can successful use this API.
		 * 
		 * So, unlike other methods in the ExpenseReports class that use the jersey client, this method uses the Apache HTTP client library to make its HTTP call.
		 * 
		 */
		
		String currentstatus = report.getApprovalStatusCode();
		String result= null;
		if (currentstatus.equals(status)) {// then report is  at the expected workflow step, so it is okay to approve the report
			String authorizationvalue = "OAuth " + key;// construct the Authorization header
			String  stepinstance  = report.getWorkflowActionUrl();// get the WorkflowActionURL for this report
			String[] parts = stepinstance.split("report\\/");
			stepinstance = parts[1];
	
			String resourceurl = "https://www.concursolutions.com/api/expense/expensereport/v1.1/report/" ;
			String body = "<WorkflowAction xmlns=\"http://www.concursolutions.com/api/expense/expensereport/2011/03\">" + "\n" + "<Action>Approve</Action>" + "\n" + "</WorkflowAction>";
			
			WebResource webResource = client.resource(resourceurl + stepinstance);// construct the URI 
			
			try {
		        response = 	webResource.header("Authorization", authorizationvalue).
										post(ClientResponse.class, body);// make the POST call
			} catch (UniformInterfaceException e) {
				response = e.getResponse();
			}
		
			if (response.getStatus() != 204) {// then there is an error
				out.println("Response Status: " + response.getStatus());
				InputStream is = response.getEntityInputStream();

				@SuppressWarnings("resource")
				java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
			    is.close();
				result = s.hasNext() ? s.next() : "";
			    s.close();
			    out.println("Response Body" + result);
			}
		}
		  
			return result;	
		
	}	
	public String submitExpenseReport(String reportid, String key) throws JAXBException, IOException  {// get from Concur an OAuth access token for the specified Concur user (empid) using Native OAuth
		// this approves the specified expense report using the specified OAuth access token
		
		/* The Approve Expense Report API is a Concur Platform V1.1 API.  The V1.1 API works differently than the V3.0 API in a couple important ways.
		 * Among these are the POST ExpenseReport/Submit call is unconventional. This is because it is a POST method with neither query string parameters nor a Request content body.  
		 * Instead the data for the post is in the path URI. 
		 * 
		 * This makes it difficult to use various HTTP client libraries such as the jersey client, URLConnection, or HTTPURLConnection because these 
		 * require a POST method to have a Request content body or query parameters.  The Apache HTTP client library is one of the few that can successful use this API.
		 * 
		 * So, unlike other methods in the ExpenseReports class that use the jersey client, this method uses the Apache HTTP client library to make its HTTP call.
		 * 
		 */
		String authorizationvalue = "OAuth " + key;// construct the Authorization header

		
		String pathURL = "https://www.concursolutions.com/api/expense/expensereport/v1.1/report/" + reportid + "/submit";

		HttpClient client = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(pathURL);
 
		// add header
		post.setHeader("Authorization", authorizationvalue);
		HttpResponse response = client.execute(post);

	
		BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}

		return result.toString();	
	}	

	public List<ReportSummary> getReportsList (String key) throws IOException {
		/* This method uses the Concur Platform V1.1 API for getting a list of unsubmitted expense reports.   
		 * Its purpose is to learn the protected, Report ID for unsubmitted expense reports.  The protected,
		 * Report ID is necessary to use the POST SubmitReport API.
		 */
		

		WebResource webResource = client.resource("https://www.concursolutions.com/api/expense/expensereport/v1.1/reportslist/UNSUBMITTED");// construct the URI for the put report by Report ID
		String authorizationvalue = "OAuth " + key;// construct the Authorization header using the specified, OAuth access token
		ReportsList reportslist=null;
		List<ReportSummary> reportSummaries= null;
		
		try {
			response  = webResource.accept(MediaType.APPLICATION_XML).
									header("Authorization", authorizationvalue).
									header("Content-Type","application/xml; charset=UTF-8").
									get(ClientResponse.class);
			String xml = response.getEntity(String.class);

			xml = xml.replace("ReportsList xmlns=\"http://www.concursolutions.com/api/expense/expensereport/2011/03\" xmlns:i=\"http://www.w3.org/2001/XMLSchema-instance\"", "ReportsList");
	        try {
	        	JAXBContext jc = JAXBContext.newInstance(ReportsList.class);

	            Unmarshaller unmarshaller = jc.createUnmarshaller();
	            StringReader reader = new StringReader(xml);
	            reportslist = (ReportsList) unmarshaller.unmarshal(reader);
	            reportSummaries = reportslist.getSummaries();


		        } catch (JAXBException e) {
		            e.printStackTrace();
		        }
	    	} catch (UniformInterfaceException e) {
				response = e.getResponse();
			}
			
	        return reportSummaries;
	}
	public ConcurUser getUser (String loginid, String key) throws IOException {
		/* This method uses the Concur Platform V1.1 API for getting a list of Concur users.  
		 * 
		 */
		
		ConcurUser cu=null;
		WebResource webResource = client.resource("https://www.concursolutions.com/api/user/v1.0/User/?loginid="+loginid);// construct the URI for the Get User by login ID
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
	public List<FormType> getForms (String key) throws IOException {
		/* This method uses the Concur Platform V1.1 API for getting a list of expense forms.   
		 * Its purpose is to learn the FormCode of each form for the Expense Group associated to the key's owner.
		 * 
		 */

	WebResource webResource = client.resource("https://www.concursolutions.com/api/expense/expensereport/v1.1/report/Forms");// construct the URI for the get forms list
	String authorizationvalue = "OAuth " + key;// construct the Authorization header using the specified, OAuth access token
	FormTypesList formslist=null;
	List<FormType> forms= null;
	
	try {
		response  = webResource.accept(MediaType.APPLICATION_XML).
								header("Authorization", authorizationvalue).
								header("Content-Type","application/xml; charset=UTF-8").
								get(ClientResponse.class);
		String xml = response.getEntity(String.class);
		xml = xml.replace("FormTypesList xmlns=\"http://www.concursolutions.com/api/expense/expensereport/2011/03\" xmlns:i=\"http://www.w3.org/2001/XMLSchema-instance\"", "FormTypesList");

		try {
        	JAXBContext jc = JAXBContext.newInstance(FormTypesList.class);

            Unmarshaller unmarshaller = jc.createUnmarshaller();
            StringReader reader = new StringReader(xml);
            formslist = (FormTypesList) unmarshaller.unmarshal(reader);
            forms = formslist.getForms();


	        } catch (JAXBException e) {
	            e.printStackTrace();
	        }
    	} catch (UniformInterfaceException e) {
			response = e.getResponse();
		}
		
        return forms;
}
public List<FormData> getFormDataList (String type, String key) throws IOException {
	/* This method uses the Concur Platform V1.1 API for getting a list of forms for a specified type.   
	 * Its purpose is to learn the FormId of each form for this type of form
	 * 
	 */

WebResource webResource = client.resource("https://www.concursolutions.com/api/expense/expensereport/v1.1/report/Forms/"+type);// construct the URI for the get forms list
String authorizationvalue = "OAuth " + key;// construct the Authorization header using the specified, OAuth access token
FormDataList formdatalist=null;
List<FormData> forms= null;

try {
	response  = webResource.accept(MediaType.APPLICATION_XML).
							header("Authorization", authorizationvalue).
							header("Content-Type","application/xml; charset=UTF-8").
							get(ClientResponse.class);
	String xml = response.getEntity(String.class);
	xml = xml.replace("FormDataList xmlns=\"http://www.concursolutions.com/api/expense/expensereport/2011/03\" xmlns:i=\"http://www.w3.org/2001/XMLSchema-instance\"", "FormDataList");

	
	try {
    	JAXBContext jc = JAXBContext.newInstance(FormDataList.class);

        Unmarshaller unmarshaller = jc.createUnmarshaller();
        StringReader reader = new StringReader(xml);
        formdatalist = (FormDataList) unmarshaller.unmarshal(reader);
        forms = formdatalist.getForms();


        } catch (JAXBException e) {
            e.printStackTrace();
        }
	} catch (UniformInterfaceException e) {
		response = e.getResponse();
	}
	
    return forms;
}
public List<FormField> getFields (String form, String key) throws IOException {
	/* This method uses the Concur Platform V1.1 API for getting a list of form fields for a specified form.   
	 * Its purpose is to learn the form data of each field for this form
	 * 
	 */

WebResource webResource = client.resource("https://www.concursolutions.com/api/expense/expensereport/v1.1/report/Form/" + form +"/Fields");// construct the URI for the get forms list
String authorizationvalue = "OAuth " + key;// construct the Authorization header using the specified, OAuth access token
FormFieldsList fieldslist=null;
List<FormField> fields= null;

try {
	response  = webResource.accept(MediaType.APPLICATION_XML).
							header("Authorization", authorizationvalue).
							header("Content-Type","application/xml; charset=UTF-8").
							get(ClientResponse.class);
	String xml = response.getEntity(String.class);
	xml = xml.replace("FormFieldsList xmlns=\"http://www.concursolutions.com/api/expense/expensereport/2011/03\" xmlns:i=\"http://www.w3.org/2001/XMLSchema-instance\"", "FormFieldsList");
    
	try {
    	JAXBContext jc = JAXBContext.newInstance(FormFieldsList.class);

        Unmarshaller unmarshaller = jc.createUnmarshaller();
        StringReader reader = new StringReader(xml);
        fieldslist = (FormFieldsList) unmarshaller.unmarshal(reader);
        fields = fieldslist.getFields();


        } catch (JAXBException e) {
            e.printStackTrace();
        }
	} catch (UniformInterfaceException e) {
		response = e.getResponse();
	}
	
    return fields;
}
public ImageURL getReportImageURL (String reportid, String key) throws IOException {
	/* This method retrieve the report image for the specifed expense report.
	 * 
	 */

	ImageURL imageurl = null;


	
	WebResource webResource = client.resource("https://www.concursolutions.com/api/image/v1.0/report/"+ reportid);// construct the URI for the get report image by Expense ID
	String authorizationvalue = "OAuth " + key;// construct the Authorization header using the specified, OAuth access token

	try {
		response  = webResource.accept(MediaType.APPLICATION_XML).
								header("Authorization", authorizationvalue).
								header("Content-Type","application/xml; charset=UTF-8").
								get(ClientResponse.class);
		String xml = response.getEntity(String.class);

		if (xml.substring(2, 6).equals("Image")) {// then there is an image to process.  The response needs to changed into a JSON Array by removing the characters after Image
			xml = xml.replace("Image xmlns=\"http://www.concursolutions.com/api/image/2011/02\" xmlns:i=\"http://www.w3.org/2001/XMLSchema-instance\"", "Image");
			// remove the text following "Image" so the XML marshaler can process it.

	        try {
	        	JAXBContext jc = JAXBContext.newInstance(ImageURL.class);

	            Unmarshaller unmarshaller = jc.createUnmarshaller();
	            StringReader reader = new StringReader(xml);
	            imageurl = (ImageURL) unmarshaller.unmarshal(reader);

		        } catch (JAXBException e) {
		            e.printStackTrace();
		        }
			

		} // if block
	} catch (UniformInterfaceException e) {
		response = e.getResponse();
	}
        return imageurl;
}
public ArrayList<ExpenseGroupConfiguration> getConfigs (String key, @SuppressWarnings("rawtypes") Map queryparameters) throws JSONException, JsonParseException, JsonMappingException, IOException {
	// get Expense Group Configurations for specified query parameters using the specified OAuth access token
	resourceURL = "/expense/expensegroupconfigurations";// set the Resource for the URL to /expense/expensegroupconfigurations
	ExpenseGroupConfiguration config=null;// placeholder for an ExpenseGroupConfiguration object
	ArrayList<ExpenseGroupConfiguration> configs = new ArrayList<ExpenseGroupConfiguration>();// an ArrayList of ExpenseGroupConfiguration objects
	
	// Process the query string parameters
	String query = "?limit=25";// set the default query string parameter: page limit to 25 reports per page

	
	if (queryparameters.size() > 0) {// then there are query string parameters to add to the default query
		@SuppressWarnings("rawtypes")
		Iterator it = queryparameters.entrySet().iterator();// create an Iterator to iterate the entries in the Map
		while (it.hasNext()) {// iterate for each entry in the Map
	        @SuppressWarnings("rawtypes")
			Map.Entry pair = (Map.Entry)it.next();// get the Map entry (key, value) for this iteration
			query= query + "&"+pair.getKey() + "="+ pair.getValue();// add its key and value to the query string
	        it.remove(); // avoids a ConcurrentModificationException// remove the entry from the iteration
	    }
	}


	WebResource webResource = client.resource(platform.baseURL + resourceURL + query);// construct the URI for the get reports call
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
			jsonbody = response.getEntity(String.class);// make the GET reports call
		} catch (UniformInterfaceException e) {
			response = e.getResponse();
		}
		if (jsonbody.substring(2, 7).equals("Items")) {// then the response needs to changed into a JSON Array by removing the substring, {"Items:
			jsonbody = jsonbody.substring(9);//remove the first 8 characters,  {"Items: so that a JSON Array can be created that the Jackson mapper can process
		} else if(jsonbody.substring(2, 8).equals("Error")) {
			return configs;// return a null ArrayList because there was an error
		} // if block
		String[] parts = jsonbody.split(",\"NextPage");// split jsonbody into two parts: 0) the JSON Array of expense group configurations, and 1) the Next Page URI
		jsonbody = parts[0] +"]";// create an JSON Array and assign it to jsonbody
		nextpageURI = "{\"NextPage"+ parts[1];//create the next page URI


		JSONArray jsonarray = new JSONArray(jsonbody);// convert jsonbody into a JSON Array

		ObjectMapper mapper = new ObjectMapper();// create an instance of the Jackson mapper
	    for(int i=0; i<jsonarray.length(); i++){// iterate for each JSON object in the array
	        JSONObject obj = jsonarray.getJSONObject(i);// get the JSON object for this iteration
	        config = mapper.readValue(obj.toString(),ExpenseGroupConfiguration.class);// have the mapper convert the JSON object into an ExpenseGroupConfiguration object
	        configs.add(config);// add the config to the ArrayList of ExpenseReport elements
	    }// for i loop 
		
	    NextPageURI uri = mapper.readValue(nextpageURI, NextPageURI.class);
	    nextpageURI = uri.NextPage;
	    
		if (nextpageURI != null) {// then get the next page
			nextpageflag = true;
			webResource = client.resource(nextpageURI);// set the Web Resource to the Next Page URI	
		} else {
			nextpageflag = false;
		};
	} //while loop
	return configs;
}

	private JSONObject updateSafeReport (JSONObject jsonObj) {
		/* This method strips elements from a JSONObject of an ExpenseReport that are read-only fields in Concur Expense.
		 * This makes the JSONObject safe to use in update or add expense report methods
		 */
		
        jsonObj.remove("country");
        jsonObj.remove("currencyCode");
        jsonObj.remove("lastComment");
        jsonObj.remove("ID");
        jsonObj.remove("personalAmount");
        jsonObj.remove("lastComment");
        jsonObj.remove("ID");
        jsonObj.remove("amountDueCompanyCard");
        jsonObj.remove("ledgerName");
        jsonObj.remove("hasException");
        jsonObj.remove("workflowActionUrl");
        jsonObj.remove("approvalStatusName");
        jsonObj.remove("paymentStatusCode");
        jsonObj.remove("total");
        jsonObj.remove("ownerName");
        jsonObj.remove("policyID");
        jsonObj.remove("amountDueEmployee");
        jsonObj.remove("ownerLoginID");
        jsonObj.remove("everSentBack");
        jsonObj.remove("receiptsReceived");
        jsonObj.remove("createDate");
        jsonObj.remove("lastModifiedDate");
        jsonObj.remove("approvalStatusCode");
        jsonObj.remove("totalApprovedAmount");
        jsonObj.remove("totalClaimedAmount");
        jsonObj.remove("paymentStatusName");
        jsonObj.remove("userDefinedDate");
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
        jsonObj.remove("orgUnit1");
        jsonObj.remove("orgUnit2");
        jsonObj.remove("orgUnit3");
        jsonObj.remove("orgUnit4");
        jsonObj.remove("orgUnit5");
        jsonObj.remove("orgUnit6");
       
        return jsonObj;
	}
}
