package com.pivotpayables.test;

import static java.lang.System.out;

import java.io.IOException;
import java.util.ArrayList;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.json.JSONException;

import com.pivotpayables.concurplatform.Expenses;
import com.pivotpayables.concurplatform.List;
import com.pivotpayables.concurplatform.ListItem;

/**
 * @author John Toman
 * 4/19/2015
 * This tests the addListItem method in the Expenses class
 *
 */
public class TestGetLists {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws JSONException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	public static void main(String[] args) throws JsonParseException, JsonMappingException, JSONException, IOException {
		String key = "0_nAQS07i9dCE4yxRr7bf+n61EA=";// Primitive Logic"0_TH3kHDP+XJ3eJGh5qZignLpnc=";//HFZ "0_2n15wT8FUUsTzLk2Y/dHakpbA=";//Apex//"0_LNEfKHZigzawUCNa1pJ8zvNho=";//OrbiMed//
		ArrayList<com.pivotpayables.concurplatform.List> lists = new ArrayList<com.pivotpayables.concurplatform.List>();
		List list = null;
		Expenses e = new Expenses();
		lists = e.getLists(key);


		for (int i = 0; i < lists.size(); i++) {
			list = lists.get(i);
			list.display();
		}

	}

}
