package com.pivotpayables.test;

//import static java.lang.System.out;

import java.lang.reflect.InvocationTargetException;

import com.pivotpayables.prime.FixCorruptedChargeDocs;

public class TestFixCorruptedCharges {
	
	public static void main(String[] args) {
		
	    String coid = "REKP3622WPAH1249";
	    String status = "failed";
	    try {
			status = FixCorruptedChargeDocs.regenAccountActivityChargeDocs(coid);
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    if (status == "success"){
	    	
	    }
	    TestCharges.main(args);


	    
	}//main

}
