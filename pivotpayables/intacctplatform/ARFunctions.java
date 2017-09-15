/**
 * 
 */
package com.pivotpayables.intacctplatform;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.UUID;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 * @author John
 *
 */
public class ARFunctions {
	public static List<Invoice> getInvoices (String controlstring, String sessionid, String query)  {

		String controlid = String.valueOf(UUID.randomUUID());
		String response="";
		InvoicesList invoiceslist=null;
		List<Invoice> invoices= null;

		
	    try {	    	
		    	String requestcontent = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><request>";
		    	requestcontent = requestcontent + controlstring;
		    	requestcontent = requestcontent + "<operation>";
		    	requestcontent = requestcontent + "<authentication><sessionid>" + sessionid + "</sessionid></authentication>";
		    	requestcontent = requestcontent + "<content><function controlid=\"" + controlid + "\"><readByQuery><object>ARINVOICE</object><fields>*</fields><query>"+ query+ "</query><pagesize>100</pagesize></readByQuery></function></content></operation></request>";
		    	
		 		System.out.println("Request:\n" + requestcontent);
		 		// open a URL connection object
		 		URL url = new URL( "https://api.intacct.com/ia/xml/xmlgw.phtml");
		 		URLConnection con = url.openConnection();
		 		
		 		// initialize the HTTP request
		 	    con.setDoInput(true);// will be receving the response
		 	    con.setDoOutput(true);// will be sending the request content
		 	    con.setConnectTimeout( 20000 ); // long timeout, but not infinite
		 	    con.setReadTimeout( 20000 );
		 	    con.setUseCaches (false);
		 	    con.setDefaultUseCaches (false);
		 	    con.setRequestProperty ( "Content-Type", "text/xml" );// set the content type
		 	   
		 	    //make the request
		 	    OutputStreamWriter writer = new OutputStreamWriter( con.getOutputStream() );// the output stream to send the request content
		 	    writer.write(requestcontent);// provide the request content body
		 	    writer.flush();
		 	    writer.close();
		 	    
		 	    // read the response
		 	    InputStreamReader reader = new InputStreamReader( con.getInputStream() );// read the response into an Input Stream Reader
		 	    StringBuilder buf = new StringBuilder();// placeholder for a String Builder object
		 	    char[] cbuf = new char[2048];// char array to hold characters from the String Buffer
		 	    int num;
		 	    while ( -1 != (num=reader.read(cbuf))){// loop while there are still characters in the Input Stream Reader
		 	        buf.append( cbuf, 0, num );// add a character to the string builder
		 	    }
		 	    
		 	    
		 	    response = buf.toString();// convert the string builder into a string, which is the response from the HTTP request

		 	    String[] pairs = response.split("<arinvoice>");
		 	    if (pairs.length > 0) {
		 	    	response = "<data>";
		 	    	for (int i=1; i< pairs.length ;i++){//
		 	    		response = response + "<arinvoice>" + pairs[i];
		 	    	}

		 	    	pairs = null;
		 	    	pairs = response.split("</result>");
		 	    	response = pairs[0];
			 	 	//System.out.println("Reponse: \n" + response);
			 	 	
		 	    	
		 		    try {
		 	        	JAXBContext jc = JAXBContext.newInstance(InvoicesList.class);
	
		 	            Unmarshaller unmarshaller = jc.createUnmarshaller();
		 	            StringReader sr = new StringReader(response);
		 	            invoiceslist = (InvoicesList) unmarshaller.unmarshal(sr);
		 	            invoices = invoiceslist.getInvoices();
		 	            
	
		 		        } catch (JAXBException e) {
		 		            e.printStackTrace();
		 		        }
		 		        
		 		        
		 	    }
	 	    
		 	}
		 	catch( Throwable t )
		 	{
		 	    t.printStackTrace( System.out );
		 	}


	    	return invoices;
		}
	public static String CreateInvoice (String controlstring, String sessionid, InvoiceLegacy invoice)  {

		String controlid = String.valueOf(UUID.randomUUID());
		String response="";

		String xmlInvoice = "";
		  try {
	        	JAXBContext jc = JAXBContext.newInstance(Invoice.class);
	            Marshaller marshaller = jc.createMarshaller();
	            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	            StringWriter writer = new StringWriter();
	            marshaller.marshal(invoice, writer);
	            xmlInvoice = writer.toString();

	            String[] pairs = xmlInvoice.split("<create_invoice>");
	            if (pairs.length > 1){
	            	xmlInvoice = "<create_invoice>"+pairs[1];
	            	xmlInvoice = xmlInvoice.replaceAll("\\s+","");
	            }
	            System.out.println("XML Invoice: \n" + xmlInvoice);
	            

		        } catch (JAXBException e) {
		            e.printStackTrace();
		        }

		
	    try {	    	
		    	String requestcontent = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><request>";
		    	requestcontent = requestcontent + controlstring;
		    	requestcontent = requestcontent + "<operation>";
		    	requestcontent = requestcontent + "<authentication><sessionid>" + sessionid + "</sessionid></authentication>";
		    	requestcontent = requestcontent + "<content><function controlid=\"" + controlid + "\">"+ xmlInvoice +"</function></content></operation></request>";
		    	
		    	System.out.println("Request:\n"+requestcontent);
		 		// open a URL connection object
		 		URL url = new URL( "https://api.intacct.com/ia/xml/xmlgw.phtml");
		 		URLConnection con = url.openConnection();
		 		
		 		// initialize the HTTP request
		 	    con.setDoInput(true);// will be receving the response
		 	    con.setDoOutput(true);// will be sending the request content
		 	    con.setConnectTimeout( 20000 ); // long timeout, but not infinite
		 	    con.setReadTimeout( 20000 );
		 	    con.setUseCaches (false);
		 	    con.setDefaultUseCaches (false);
		 	    con.setRequestProperty ( "Content-Type", "text/xml" );// set the content type
		 	   
		 	    //make the request
		 	    OutputStreamWriter writer = new OutputStreamWriter( con.getOutputStream() );// the output stream to send the request content
		 	    writer.write(requestcontent);// provide the request content body
		 	    writer.flush();
		 	    writer.close();
		 	    
		 	    // read the response
		 	    InputStreamReader reader = new InputStreamReader( con.getInputStream() );// read the response into an Input Stream Reader
		 	    StringBuilder buf = new StringBuilder();// placeholder for a String Builder object
		 	    char[] cbuf = new char[2048];// char array to hold characters from the String Buffer
		 	    int num;
		 	    while ( -1 != (num=reader.read(cbuf))){// loop while there are still characters in the Input Stream Reader
		 	        buf.append( cbuf, 0, num );// add a character to the string builder
		 	    }
		 	    
		 	    
		 	    response = buf.toString();// convert the string builder into a string, which is the response from the HTTP request
	 	    
		 	}
		 	catch( Throwable t )
		 	{
		 	    t.printStackTrace( System.out );
		 	}

	
	    	return response;
		}

}
