package com.pivotpayables.test;

/**
 * @author John Toman
 * 3/27/2015
 * This tests the List class
 *
 */
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.json.JSONException;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCollection;
import com.pivotpayables.concurplatform.ConcurFunctions;
import com.pivotpayables.concurplatform.Itemization;
import com.pivotpayables.concurplatform.ListItem;
import com.pivotpayables.expensesimulator.GUID;
import com.pivotpayables.expensesimulator.MongoDBFunctions;

import static java.lang.System.out;
public class TestListItem {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws JSONException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */


	
	public static void main(String[] args) throws JsonParseException, JsonMappingException, JSONException, IOException {
		String key = "0_TTdJ95hoq3kSx6RwSAqayIGHI=";//OrbiMed
		HashMap<String, String> queryparameters = new HashMap<String, String>();// initialize the queryparameters HashMap
		ArrayList<ListItem> items = new ArrayList<ListItem>();// initialize ArrayList of List object elements
		ListItem item = null;
		ConcurFunctions f= new ConcurFunctions();
	
		
		String code = "123";
		String listid ="gWgNcaQvnVDCsQfhnm2rwPxDYcZBOM7dQyw";
		item = f.getListItemByLongCode(key, listid, code);
		item.display();

	}

}
