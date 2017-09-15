
package com.pivotpayables.test;

/**
 * @author John Toman
 * 4/18/2015
 * This program uses Concur Native Flow to get an OAuth Access Token for a specified partner application key, user name, and password.
 * It displays the token in console.
 *
 */

import static java.lang.System.out;

import java.util.Scanner;

import javax.xml.bind.JAXBException;

import com.pivotpayables.concurplatform.ConcurFunctions;
import com.pivotpayables.concurplatform.OAuthToken;

public class CreateOAuthToken {

	/**
	 * Parameters:
	 * args[0] = partner application key
	 * args[1] = user name
	 * args[2] = password
	 * @throws JAXBException 
	 */	

	public static void main(String[] args) throws JAXBException {
		String key = null;
		String user = null;
		String password = null;
		Scanner myScanner = new Scanner(System.in);
		OAuthToken token;
		
		try {	
			if (args.length == 3) {//there are parameters for key, user, and password
				key = args[0];
				user = args[1];
				password = args[2];
			} else {//use the console to enter the key, user name, and password
				out.println("What is the key for the partner application?");
				key = myScanner.next();
				out.println("What is the user name?");
				user = myScanner.next();
				out.println("What is the password?");
				password = myScanner.next();
				myScanner.close();
			}
		} catch (Exception e) {
        		out.println(e);
		}
		token = ConcurFunctions.GetOAuthToken(key,user, password);
		out.println("Token: " + token.getToken());
	}

}
