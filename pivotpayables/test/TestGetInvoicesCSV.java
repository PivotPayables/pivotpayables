package com.pivotpayables.test;

import static java.lang.System.out;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.HashMap;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.json.JSONException;

import com.pivotpayables.concurplatform.GetInvoicesCSV;

public class TestGetInvoicesCSV {
	
	public static void main(String[] args) throws JsonParseException, JsonMappingException, ParseException, JSONException, IOException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String key = "0_nAQS07i9dCE4yxRr7bf+n61EA=";// Meetings & Incentives"0_AH8ASgmiaQmI0Dzjmu+Ap9I8M=";// Vashon Group"0_YXJGQAIkcgDhQyJlXe6sKrV4Y=";//Brainreserve "0_VWAHhSrZARamL5zcMyCDAf400=";//Global Excel//"0_2n15wT8FUUsTzLk2Y/dHakpbA=";//Apex//"0_TTdJ95hoq3kSx6RwSAqayIGHI=";//OrbiMed//"0_HvZQVN0uzvrSQc/zph8zQm318=";//apex consulting group////"0_fPsZ5gMPAb2v0ELlEzuBSW9Qo=";//Cardno//"e15mCgSh7e0KrKZ4w363v1rSlek=";//K & L Beverage//"myL0YTKnKt9Bu57bxxNWgIB/LdA=";//Chopin Vodka//"gEDKnWeGyPDdaTBBXTQX8YEWodE=";//Voicebrook//"iohyl8b1gbjyvMK3iH++bwkMybY=";// assurance resources;//"e15mCgSh7e0KrKZ4w363v1rSlek=";//K&L //
		String lastsuccess = "2017-01-01";
		String company = "Meetings & Incentives";
		
		GetInvoicesCSV.CreateCSVFile(key, lastsuccess, company);
		

		

		
		
	}

}
