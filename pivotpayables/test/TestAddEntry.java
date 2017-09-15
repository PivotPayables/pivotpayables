
package com.pivotpayables.test;



import static java.lang.System.out;

import java.text.SimpleDateFormat;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Base64;
import java.util.Calendar;

import javax.imageio.ImageIO;

import org.json.JSONException;

import com.pivotpayables.concurplatform.*;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;


/**
 * @author John Toman
 *
 */
public class TestAddEntry {
	private static Calendar BeginningDate = Calendar.getInstance();// a Calendar object to track the beginning date
	private static Calendar EndingDate= Calendar.getInstance();// a Calendar object to track the ending date
	private static String user ="wsadmin@Connect-PivotPayables.com";
	private static String reportID = "15B4B6ADAB554DCD8794";
	private static String key = "kzfF70hLzdKJu1TxiQWthcJdXro=";
	private static int year;
	private static int month;
	private static int day;
	private static Location home = new Location ();
	private static ExpenseEntry entry = new ExpenseEntry(); // placeholder for an Entry object
	private static Journey journey = new Journey();// placeholder for a Journey object
	private static String payment;
	private static String cash = "gWmU18qzMzI3JWU$sEuDnnxb9pWex$p";
	private static String cocard = "gWmU78aDgciTJyKKuR16iGFePqoWy";
	private static String personalmiles = "MILEG";
	private static String hotel = "LODNG";
	private static String roomrate = "LODNG";
	private static String airfare = "AIRFR";
	private static String travelmeal = "01028";
	private static Entries e = new Entries();
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");;
	private static String stringDate = "";
	private static String entryid;
	private static String filename = "MealReceiptSample.jpg";
	private static String imagefilepath = "/Users/TranseoTech/Dropbox/PivotPayables/";
	private static Image i;
	private static String type;
	
	public static void main(String[] args) throws IOException, JSONException {
		BeginningDate.set(2015, 3, 1);//April 1, 2015
		EndingDate.set(2015, 3, 30);//April 30, 2015


		stringDate = sdf.format(BeginningDate.getTime());
		entry.setExpenseTypeCode(personalmiles);
		entry.setPaymentTypeID(cash);
		entry.setReportID(reportID);
		entry.setLocationID("gWvYuzCATU4yKdghEvwUyDiUeGf2z2q6Rcg");
		entry.setTransactionDate(stringDate);
		entry.setTransactionAmount(54.32);
		journey.setBusinessDistance(72);
		journey.setUnitOfMeasure("M");
		journey.setStartLocation("Home");
		journey.setEndLocation("Airport");

		entry.setJourney(journey);
		entry.setTransactionCurrencyCode("USD");
		entry.setExchangeRate(1.00);
		entry.setDescription("Test 3");

		entryid = e.addMileageEntry(entry, key);
		out.println(entryid);
		entry = e.getEntry(entryid, user, key);
		entry.display();
		/*
		entry.setID("gWuGlX0IV6nR4RA3sDFh1ARBBoGCjZlihUA");
		entry.display();
		type="jpeg";
		//out.println("Checkpoint: request length " + request.length());
		File file = new File(imagefilepath,filename);
		String xml = e.postEntryImageURL(entry, key, file, type);
		out.println(xml);
		*/
		
		


	}

}
