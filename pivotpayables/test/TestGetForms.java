/**
 * 
 */
package com.pivotpayables.test;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * @author John
 *
 */
public class TestGetForms {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
        	
     		// open a URL connection object
			String url = "https://www.concursolutions.com/api/expense/expensereport/v1.1/report/Forms/";
			String key = "0_nAQS07i9dCE4yxRr7bf+n61EA=";//Meetings & Incentives//"0_rC7L9ZyQdnpNPQ7NNge2Wv28Q=";//Vantage"0_YXJGQAIkcgDhQyJlXe6sKrV4Y=";//Brainreserve"0_daDheH9Q6QkGrlgfP27j2EhXg=";//Veracity"0_fPsZ5gMPAb2v0ELlEzuBSW9Qo=";//cardno"0_VWAHhSrZARamL5zcMyCDAf400=";// Global Excel//"JzsV4ry0wMjQth5DDGL0UI3j57k=";//apex consulting group////="oyafnBMjWuXX2lxlxJF6IqwIcws=";//"YcvD9V0pD8Tm1sLqTURELsfad1s=";
			String authorizationvalue = "OAuth " + key;// construct the Authorization header using the specified, OAuth access token

			URL obj = new URL(url);
     		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

     		
     		
     		// initialize the HTTP request
     		con.setRequestMethod("GET");
     		con.addRequestProperty("Authorization", authorizationvalue);

     		
     		int responseCode = con.getResponseCode();
    		System.out.println("\nSending 'GET' request to URL : " + url);
 		
    		System.out.println("Response Code : " + responseCode);

    		BufferedReader in = new BufferedReader(
    		        new InputStreamReader(con.getInputStream()));
    		String inputLine;
    		StringBuffer response = new StringBuffer();

    		while ((inputLine = in.readLine()) != null) {
    			response.append(inputLine);
    		}
    		in.close();

    		//print result
    		System.out.println(response.toString());

     	   
     	    
     	    
     	}
     	catch( Throwable t )
     	{
     	    t.printStackTrace( System.out );
     	}

	}

}
