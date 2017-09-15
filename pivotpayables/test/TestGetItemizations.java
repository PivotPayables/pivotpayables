/**
 * 
 */
package com.pivotpayables.test;

import static java.lang.System.out;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.json.JSONException;

import com.pivotpayables.concurplatform.Expenses;
import com.pivotpayables.concurplatform.Itemization;
import com.pivotpayables.expensesimulator.GUID;

/**
 * @author John
 *
 */
public class TestGetItemizations {

	private static ArrayList<Itemization> items = new ArrayList<Itemization>();
	private static Itemization itemization; // placeholder for an Itemization object
	private static Expenses e = new Expenses();// Expenses functions
	private static Map<String, String> queryparameters = new HashMap<String, String>();// a HashMap to hold key-value pairs for a query parameter
	private static String key = "JzsV4ry0wMjQth5DDGL0UI3j57k=";//"lQ1rkk7S0+GgAV4ApPHqbO2Iw4g=";
	private static String reportid = "8B64D2D4BD9D4AFF805D";
	private static String entryid = "gWsrgR2N8Q6TgJoVrAM%24p2TVo9EnBednWfTQ";//gWuGlX0IV6nUO8QyhDszSGqN8CWU6n1hylg";//"gWsrgR2N$pTqXlZrMXtvUOYHeyhea$sIQuVuA";
	private static String user = "ALL";//;//"Olivia.Barnes@apexconsultinggroup.com"
	
	public static void main(String[] args) throws JsonParseException, JsonMappingException, JSONException, IOException {
		queryparameters = new HashMap<String, String>();
		queryparameters.put("entryID", entryid);
		queryparameters.put("user", user);// search for expenses where the Report Owner is the specified user
		items = e.getItemizations(key, queryparameters);// get Itemizations for the specified EntryID
    	for (int k=0; k< items.size(); k++) { // Iterate for each itemization in the expense
			itemization = items.get(k);// get the itemization for this iteration
			itemization.setID(GUID.getGUID(4));// assign a Pivot Payables GUID to this itemization
			itemization.setOriginalCurrency("USD");// copy the Expense's original currency to the itemization
			itemization.setPostedCurrency("USD");// copy the Expense's posted currency to the itemization
			itemization.display();
		}// for k loop
	}

}
