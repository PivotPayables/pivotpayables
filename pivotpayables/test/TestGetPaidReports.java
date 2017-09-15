
package com.pivotpayables.test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.json.JSONException;

import com.pivotpayables.concurplatform.GetPaidReports;

import static java.lang.System.out;

/**
 * @author John Toman
 * 
 * This class pulls expenses for the company with the specified OAuth Token.
 *
 */
public class TestGetPaidReports {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws JSONException 
	 * @throws ParseException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 */
	public static void main(String[] args) throws JsonParseException, JsonMappingException, ParseException, JSONException, IOException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String key = "0_NJTIPIpaJTl1XOyn9SmfFFrK0=";//Apex"0_/1IiWv12yZM8QvNnt4HwmfOqI=";//Hines"0_H8UDN5wGoLs/KEid6HkF7EB4I=";//nesoi solutions//"0_2n15wT8FUUsTzLk2Y/dHakpbA=";//Apex"0_fPsZ5gMPAb2v0ELlEzuBSW9Qo=";///Cardno//"VOKbC9xM2kftRcgSEwV2AAw+yLY=";//Coffman Engineers//"0_ZKoC9r4R83iEXvHqit9oJlzvA=";//West Yost//"0_v9841SkONKrlZPFDPcQMV1La8=";//Renoir Group//"JzsV4ry0wMjQth5DDGL0UI3j57k=";//"RH5PUJV1cUCx6iEkwbQ8ZaQq5nU=";
		String lastsuccess = "2016-09-30";
		String status;
		
		status = GetPaidReports.getExpenses(key, lastsuccess);
		out.println("GET Paid Reports Status " + status);
		TestExpenses.main(args);


	}

}
