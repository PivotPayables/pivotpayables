/**
 * 
 */
package com.pivotpayables.prime;

/**
 * @author John
 * 
 * This class provides the currency exchange or "conversion" rate to convert a source currency into a targe currency
 *
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class CurrecyConverter {

	/**
	 * args[0] = string date for the day for the currency conversion
	 * args[1] = ISO 3-letter currency code for the source currency
	 * args[2] = ISO 3-letter currency code for the target currency
	 */
	public static void main(String[] args) {
		
		String strdate="latest";// default date
		String sourcecurrency="USD";// default source currency
		String targetcurrency="EUR";// default target currency
		if (args[0] != null) {// then there is a string date
			strdate = args[0];
		}
		if (args[1] != null) {// then there is a source currency
			sourcecurrency = args[1];
		}
		if (args[2] != null) {// then there is a target currency
			targetcurrency = args[2];
		}
		try {
			URL url = new URL("http://api.fixer.io/" + strdate + "?symbols=" +sourcecurrency + "," + targetcurrency);
			BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
			String strTemp = "";
			while (null != (strTemp = br.readLine())) {
				System.out.println(strTemp);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
