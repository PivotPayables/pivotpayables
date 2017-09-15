/**
 * 
 */
package com.pivotpayables.test;

import java.io.IOException;
import java.text.ParseException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.json.JSONException;



/**
 * @author John
 *
 */
public class TestCreateAccountingLookup {

	/**
	 * @param args
	 * @throws ParseException 
	 * @throws IOException 
	 * @throws JSONException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	public static void main(String[] args) throws ParseException, JsonParseException, JsonMappingException, JSONException, IOException {
		CreateAccountObjectLookup.create("0_NJTIPIpaJTl1XOyn9SmfFFrK0=", "");

	}

}
