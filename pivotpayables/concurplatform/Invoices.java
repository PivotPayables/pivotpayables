package com.pivotpayables.concurplatform;
/**
 * @author John Toman
 * 2/22/16
 * This class's methods provide access to Concur APIs related to invoices.
 * 
 *
 */

import java.util.ArrayList;
import java.util.Map;
import java.util.Iterator;

import static java.lang.System.out;

import java.io.IOException;
import java.io.StringReader;






//JAX-WS, RS client side APIs version 2.x
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;





// jeresy version 1.x
import com.sun.jersey.api.client.*;

// jackson version 1.x
import org.codehaus.jackson.map.*;
import org.codehaus.jackson.JsonParseException;




// java json
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Invoices {
	
	protected Invoice invoice;// placeholder for an invoice
	static protected ArrayList<PaymentRequest> requests = new ArrayList<PaymentRequest>();// ArrayList of Payment Request Digest objects
	protected PaymentRequest request;// placeholder for a payment request digest
	protected String jsonbody="";// placeholder for Content body in JSON
	protected String xmlbody="";// placeholder for Content body in XML
	protected ClientResponse response;// placeholder for a jersey-client, ClientRepsone object
	protected String resourceURL = "/invoice/paymentrequestdigests";// the path to the payment requests resource
	protected StringBuilder sb;// placeholder for a StringBuilder object
	ConcurFunctions platform = new ConcurFunctions();// get an instance of the Concur Platform Functions
	Client client = platform.getClient();// get a jersey Client to the Concur platform.
	
	public ArrayList<PaymentRequest> getPaymentRequests (String key, @SuppressWarnings("rawtypes") Map queryparameters) throws JSONException, JsonParseException, JsonMappingException, IOException {
		// get payment request digests for specified query parameters using the specified OAuth access token
		
		// Process the query string parameters
		String query = "?limit=1000";// set the default query string parameter: page limit to 25 invoices per page

		
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


		WebResource webResource = client.resource(platform.baseURL + resourceURL + query);// construct the URI for the get invoices call
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
				jsonbody = response.getEntity(String.class);// make the GET invoices call
			} catch (UniformInterfaceException e) {
				response = e.getResponse();
			}
			if (jsonbody.substring(2, 22).equals("PaymentRequestDigest")) {// then there are invoices to process.  The response needs to changed into a JSON Array by removing the substring, {"Items:
				jsonbody = jsonbody.substring(24);//remove the first 25 characters,  {PaymentRequestDigest:

			} else  {// then there are no invoices to process, or there was some type of error
				return requests;// so return an empty ArrayList<Invoice>
			} // if block

			String[] parts = jsonbody.split("],");// split jsonbody into two parts: 0) the JSON Array of invoices, and 1) Total Count, Items, and 2) the Next Page URI
			jsonbody = parts[0] +"]";// assign the JSON Array to jsonbody

			nextpageURI = "{"+ parts[2];//


			JSONArray jsonarray = new JSONArray(jsonbody);// convert jsonbody into a JSON Array
	
			ObjectMapper mapper = new ObjectMapper();// create an instance of the Jackson mapper
		    for(int i=0; i<jsonarray.length(); i++){// iterate for each JSON object in the array
		        JSONObject obj = jsonarray.getJSONObject(i);// get the JSON object for this iteration
		        request = mapper.readValue(obj.toString(),PaymentRequest.class);// have the mapper convert the JSON object into a Payment Request digest object
		        requests.add(request);// add the invoice to the ArrayList of Invoice elements
		    }// for i loop 

		    NextPageURI uri = mapper.readValue(nextpageURI, NextPageURI.class);
		    nextpageURI = uri.NextPage;
		    
			if (nextpageURI != null) {// then get the next page
				nextpageflag = true;
				webResource = client.resource(nextpageURI);// set the Web Resource to the Next Page URI	
			} else {
				nextpageflag = false;
			}

			 
		} //while loop
		return requests;
	}
	
	public Invoice getInvoice (String invoiceid, String key) throws JSONException, JsonParseException, JsonMappingException, IOException {
		// get the specified payment request or "invoice" using specified OAuth access token
		
		
		resourceURL = "/invoice/paymentrequest";// the path to the payment requests resource


		WebResource webResource = client.resource(platform.baseURL + resourceURL + "/" + invoiceid);// construct the URI for the get invoice by Payment Request ID
		webResource.setProperty("Content-Type", MediaType.APPLICATION_JSON);// content is JSON
		String authorizationvalue = "OAuth " + key;// construct the Authorization header using the specified, OAuth access token

			try {
				response  = webResource.accept(MediaType.APPLICATION_JSON).
						header("Authorization", authorizationvalue).
						header("Content-Type","application/json; charset=UTF-8").
				        get(ClientResponse.class);// make the GET Invoice call
				jsonbody = response.getEntity(String.class);

			} catch (UniformInterfaceException e) {
				response = e.getResponse();
			}
			if (response.getStatus() == Response.Status.OK.getStatusCode()) {// then it found the invoice in Concur Expense

				
				
				ObjectMapper mapper = new ObjectMapper();// create an instance of the Jackson mapper
				
				// This following code fixes the JSON Array serialization that Concur uses.  The serialization Concur uses isn't not the same convention the
				// serialization Jackson mapper uses to deserialize.
				
				String portion;// placeholder for a string that holds a portion of the jsonbody to perform string operations
				String[] portions;
				String[] parts = null;
				String[] lines = null;
				String[] allocations = null;
				String jsontext;// placeholder for json text
				String line;
				String allocation = null;
				

				
				parts = jsonbody.split("\\[");// split the JSON response body using the opening bracket [.  
				/* The opening bracket indicates the beginning of a JSON array serialization. 
				 *  part[0] is the portion of the JSON body before the Line Items JSON array
				 */
				
				// begin building jsontext into json the Jackson mapper can deserialize
				jsontext = parts[0]; // the portion of the jsonbody before the Line Items JSON array
				
				
				// this removes the portion of the Concur JSON Array serialization that the Jackson mapper doesn't accept
				jsontext = jsontext.substring(0, (jsontext.length()-12));// remove from json text the last twelve characters: {"LineItem":
				jsontext = jsontext + "[";// add the opening bracket for the Line Items JSON array
				
				
				//Next,  determining how many line items this payment request has
				lines = jsonbody.split("\\{\"LineItemId");// use {"LineItemID to split the jsonbody by how many line items the payment request has
				
				
				// jsontext is now the corrected portion of the JSON body prior to the line items JSON Array
				jsonbody = jsontext;// so, assign jsontext to jsonbody
				jsontext = "";
				for (int i=0; i<lines.length; i++) {// iterate for each line item
					line = lines[i];
					
					if (i != 0){// then this isn't lines[0], which means it's the JSON for a  line item
						portions =	line.split("\\[");
						/*  portions splits the JSON for the line item into two parts
						 *  portions[0] is the part before the JSON Array for Allocations
						 *  portions[1] is the part after the JSON Array for Allocations
						 */
						portion = portions[0];// this is portion of the line item before the JSON Array for Allocations
						sb = new StringBuilder();
						sb.append(portion);
						sb.insert(0, "{\"LineItemId");// add the {"LineItemId string to the beginning of the line item
						portion = sb.toString();

						
						
						portion = portion.substring(0, (portion.length()-14));
						// remove from portion the last fourteen characters: {"Allocation":
						// this removes the portion of the Concur JSON Array serialization that the Jackson mapper doesn't accept
			
						portion = portion + "[";// add the opening bracket for the Allocations JSON array

						jsontext = jsontext + portion;// so, need to add this portion to the jsontext

						
						
						portion = portions[1];// portions[1] is the portion of the line item JSON that appears after the JSON Array for Allocations
						allocations = portion.split("Percentage");// use {"Percent to split the JSON Array for Allocations by how many allocations the line item has
						
						
						//jsontext=null;

						if (allocations.length> 0){
							allocation = allocations[0];
							allocation = allocation + "Percentage";
						}
						for (int j=1; j<allocations.length; j++){
							
							allocation = allocation + allocations[j];
						
						}// j loop
						jsontext = jsontext + allocation;
					}// if (i != 0)
					
				}// i loop
				jsonbody = jsonbody + jsontext;
				portions = jsonbody.split("\\]\\}");// ]}}]} split this jsonbody into two parts: 0) the portion before the end of the Line Items JSON array, and 1) the portion after
				jsonbody="";
				for (String part:portions){
					part = part + "]";// the result is replacing ]}} with ], so that there is correct number of closing braces
					jsonbody = jsonbody + part;//continue building the jsonbody by adding this now corrected part to jsonbody
				}
				jsonbody = jsonbody.substring(0, (jsonbody.length()-1));// remove the extra ] at the end of jsonbody
				
				// this is now the complete, corrected version of the jsonbody the Jackson mapper can deserialize

				
				invoice = mapper.readValue(jsonbody,Invoice.class);// have the mapper convert the JSON invoice into an Invoice object
				invoice.display(); 
			} else {
				out.println("Response Status: " + response.getStatus());
				out.println("Response Body" + jsonbody);
			   invoice = null;// set Invoice object to null
			}

	        return invoice;
	}
public ImageURL getInvoiceImageURL (String invoiceid, String key) throws IOException {
	/* This method retrieve the invoice image for the specifed invoice.
	 * 
	 */

	ImageURL imageurl = null;


	
	WebResource webResource = client.resource("https://www.concursolutions.com/api/image/v1.0/invoice/"+ invoiceid);// construct the URI for the get invoice image by Expense ID
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

	
}
