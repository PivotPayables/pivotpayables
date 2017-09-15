package com.pivotpayables.test;


/**
 * @author John Toman
 * 4/19/2015
 * This tests the addListItem method in the Expenses class
 *
 */
import static java.lang.System.out;

import java.io.IOException;


import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.json.JSONException;

import com.pivotpayables.concurplatform.Expenses;
import com.pivotpayables.concurplatform.ListItem;

public class TestAddListItem {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws JSONException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	public static void main(String[] args) throws JsonParseException, JsonMappingException, JSONException, IOException {
		String key = "kzfF70hLzdKJu1TxiQWthcJdXro=";//"lyJkLrmhtXS1Ou4bEBMYnQLfllQ=";

		ListItem item = new ListItem();
		Expenses e = new Expenses();
		item.setListID("gWuOM18k5mRYLPY1zRYZWBYCVqvh$ssF4Qvg");
		item.setLevel1Code("acnt-02");
		item.setLevel2Code("actv-01");
		item.setName("Actvity Test 01");
		e.addListItem(item, key);



	}

}
