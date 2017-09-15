package com.pivotpayables.test;

/*
 * This class tests creating canonical transactions for the company with the specified OAuth Token found in agrs[0]
 * 
 * It does the following
 * 
 * 1. Cleans any expenses in the Expenses collection so that they are available to process by PivotNexus
 * 2. It creates the report payees for expenses from the specified company
 * 3. It creates the canonical transactions
 * 
 * 
 */

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.json.JSONException;

import com.pivotpayables.nexus.CreateCanonicalTransactions;
import com.pivotpayables.nexus.CreateReportPayees;


public class TestCreateCanonicalTransactions {



	public static void main(String[] args) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ParseException, JsonParseException, JsonMappingException, JSONException, IOException {

		CleanExpenses.main(args);//
		CreateReportPayees.processExpenses("0_NJTIPIpaJTl1XOyn9SmfFFrK0=");
		CreateCanonicalTransactions.createTransactions("0_NJTIPIpaJTl1XOyn9SmfFFrK0=");
		TestCanonicalTransactions.main(null);

		

	}

}
