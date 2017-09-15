/**
 * 
 */
package com.pivotpayables.test;

/**
 * @author John Toman
 *
 */
import static java.lang.System.out;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.json.JSONException;

import com.pivotpayables.concurplatform.ConcurFunctions;
import com.pivotpayables.concurplatform.Expenses;
import com.pivotpayables.concurplatform.ListItem;
public class TestGetListItems {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws JSONException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	public static void main(String[] args) throws JsonParseException, JsonMappingException, JSONException, IOException {
		String key = "0_nAQS07i9dCE4yxRr7bf+n61EA=";// Primitive Logic"YcvD9V0pD8Tm1sLqTURELsfad1s=";
		Map<String, String> queryparameters = new HashMap<String, String>();// a HashMap to hold key-value pairs for a query parameter

		ArrayList<ListItem> items = new ArrayList<ListItem>();// initialize the ArrayList
		ConcurFunctions concurfunctions = new ConcurFunctions();// initiate a ConcurFunctions object
		
		queryparameters = new HashMap<String, String>();// initialize the queryparameters HashMap
		String listid = "gWr13WFQvHQebqH4aoo$pCN4Gdey$sIMy0Ppw";// search for results for All report owners; not just reports from the user the key is associated
		queryparameters.put("listId", listid);// add the listid search term to the query parameters

		items = concurfunctions.getListItems(key, queryparameters);// pull the list items for this list from Concur Expense
		if (items.size() > 0) { // then there is at least one list item to process
		    for(ListItem item:items){// iterate for each report 
		    	item.display();		    		
		    }
		}

	}

}
