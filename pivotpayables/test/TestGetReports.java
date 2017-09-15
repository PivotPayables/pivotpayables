
package com.pivotpayables.test;

/**
 * @author John Toman
 * 8/19/2015
 * This tests the GetReports class
 *
 */



import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.json.JSONException;

import com.pivotpayables.concurplatform.ExpenseReports;
import com.pivotpayables.concurplatform.GetReports;

import java.io.IOException;

import static java.lang.System.out;



public class TestGetReports {

	/**
	 * @param args
	 */

	private static Map<String, String> queryparameters = new HashMap<String, String>();// a HashMap to hold key-value pairs for a query parameter
	private static ExpenseReports r = new ExpenseReports();// ExpenseReports functions
	
	public static void main(String[] args) throws JsonParseException, JsonMappingException, ParseException, JSONException, IOException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String key = "0_j7YkDGP8v/VNNe7wGyDxUGjYo=";//teletracking "0_2n15wT8FUUsTzLk2Y/dHakpbA=";//apex "0_wRCyqgowNZi7gA5kZl6Xoh/ME=";//Pritzer Group"0_rC7L9ZyQdnpNPQ7NNge2Wv28Q=";//Vantage"0_Vo4ekun+XzgPE4/rKg4SCahBI=";//Chopin Vodka"0_3DKd4eFeSXZtJFjDrqKyFIMEI= ";//Wetherly"0_La6PCrKLOAhjcFB4DgWYAnjy4=";//Mesirow"0_4VBWqUgYCwYlTTDnq2WTpX59I=";//Hargrove;////;"gEDKnWeGyPDdaTBBXTQX8YEWodE=";//Voicebrook//"0_6eHXFt6m8vtwG/rPWaSpci9XI=";//Pragmatic Works//"0_TTdJ95hoq3kSx6RwSAqayIGHI=";//OrbiMed//"VOKbC9xM2kftRcgSEwV2AAw+yLY=";//Coffman Engineers//"0_TH3kHDP+XJ3eJGh5qZignLpnc=";//HFZ//"0_v9841SkONKrlZPFDPcQMV1La8=";//Renoir Group//"0_ZKoC9r4R83iEXvHqit9oJlzvA=";//West Yost//"0_H8UDN5wGoLs/KEid6HkF7EB4I=";//nesoi solutions//"swcvpgXlclo7BzaISyA5uUYfhD4=";//LifeWay//"e15mCgSh7e0KrKZ4w363v1rSlek=";//K & L Beverage//"myL0YTKnKt9Bu57bxxNWgIB/LdA=";//Chopin Vodka//"iohyl8b1gbjyvMK3iH++bwkMybY=";// assurance resources;//"e15mCgSh7e0KrKZ4w363v1rSlek=";//K&L //
		String lastsuccess = "2017-09-01";
		String status;
		
		queryparameters = new HashMap<String, String>();// initialize the queryparameters HashMap
		String user = "ALL";// search for results for All report owners; not just reports from the user the key is associated
		queryparameters.put("user", user);
		queryparameters.put("paymentStatusCode", "P_PAID");// search for reports with a Paid Date after this date
		queryparameters.put("paidDateAfter", lastsuccess);// search for reports with a Paid Date after this date
		//queryparameters.put("paidDateBefore", "2016-12-01");// search for reports with a Paid Date before this date
		//queryparameters.put("submitDateAfter", lastsuccess);// search for reports with a Paid Date after this date
		
		r.getReports(key, queryparameters);
		
		status = GetReports.getExpenses("Teletracking", key, queryparameters);
		out.println("GET Reports Status " + status);
		
		
	}
}