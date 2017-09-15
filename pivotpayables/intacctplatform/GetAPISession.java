/**
 * 
 */
package com.pivotpayables.intacctplatform;

/**
 * @author John
 * 
 * The class's getSession method returns a session ID for a given control string and authorization string
 *
 */


import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.UUID;

public class GetAPISession {
	public static String getSession (String controlstring, String authstring)  {
		
		String sessionid=null;
		String controlid = String.valueOf(UUID.randomUUID());
		
        try {
        	//Build the request content
        	String requestcontent = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
     		requestcontent = requestcontent + "<request>";
     		requestcontent = requestcontent + controlstring;
     		requestcontent = requestcontent + "<operation>";
     		requestcontent = requestcontent + authstring;
     		requestcontent = requestcontent + "<content><function controlid=\"" + controlid + "\"><getAPISession /></function></content></operation></request>";

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
     	    con.setRequestProperty ( "Content-Type", "application/xml" );// set the content type
     	   
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
     	    
     	    
     	    String response = buf.toString();// convert the string builder into a string, which is the response from the HTTP request
     	    String[] parts = response.split("<sessionid>");// split the response into two parts, using the opening tag for the sessionid.
    	    
    	    if (parts.length>0){// then the response had a sessionid element
		 	    String sessionpart = parts[1];// the sessionpart is the text that follows the opening tag for the sessionid
		    	parts = null;// initialize the parts string array
		    	parts = sessionpart.split("</sessionid>");// split sessionpart into two parts, using the closing tag for the sessionid
		    	if (parts.length>0){// then it successfully split sessionpart into two parts
		    		sessionid = parts[0];// parts[0] is the text before the closing tag for sessionid, which is the value for the session ID
		    	}
    	    }
     	    
     	}
     	catch( Throwable t )
     	{
     	    t.printStackTrace( System.out );
     	}
        return sessionid;

		}

}
