package com.pivotpayables.test;
/**
 * @author John Toman
 * 
 *
 */
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;


import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.json.JSONException;

import com.pivotpayables.concurplatform.GetInvoices;


public class TestGetInvoices {
	
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
	String key = "0_VWAHhSrZARamL5zcMyCDAf400=";//Global Excel//"0_TTdJ95hoq3kSx6RwSAqayIGHI=";//Orbimed"0_2n15wT8FUUsTzLk2Y/dHakpbA=";//Apex//, "0_TTdJ95hoq3kSx6RwSAqayIGHI=";//"0_TnyPMXn3DeChLXTDuNH5Xc6Yc=";//
	String lastsuccess = "2017-01-01";

	
	GetInvoices.getInvoices(key, lastsuccess);
	//TestExpenses.main(args);
	//TestCharges.main(args);
	//TestReportPayees.main(args);
	


	}
}
