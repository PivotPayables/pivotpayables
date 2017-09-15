package com.pivotpayables.test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.json.JSONException;

import com.pivotpayables.prime.CreateChargeDocs;


public class TestCreateCharges {
	public static void main(String[] args) throws JsonParseException, JsonMappingException, ParseException, JSONException, IOException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String key = "0_VWAHhSrZARamL5zcMyCDAf400=";//Global Excel;"0_TTdJ95hoq3kSx6RwSAqayIGHI=";//Orbimed;//Orbimed"0_2n15wT8FUUsTzLk2Y/dHakpbA=";//Apex//"0_TTdJ95hoq3kSx6RwSAqayIGHI=";//"0_TnyPMXn3DeChLXTDuNH5Xc6Yc=";//
		CreateChargeDocs.processExpenses(key);
		TestCharges.main(args);
		
	}
}
