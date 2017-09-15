package com.pivotpayables.nexus;

/**
 * @author John
 * This class creates the value for a canonical transaction header or detail object field.
 *
 */

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.pivotpayables.concurplatform.Allocation;
import com.pivotpayables.concurplatform.CustomField;
import com.pivotpayables.concurplatform.Expense;
import com.pivotpayables.concurplatform.Itemization;
import com.pivotpayables.concurplatform.JournalEntry;
import com.pivotpayables.concurplatform.JournalParentLookup;
import com.pivotpayables.expensesimulator.GUID;

public class CreateFieldValue {
	@SuppressWarnings("rawtypes")

	public static String createText(Map.Entry TextMap, Expense expense, String journalID, String batchdefinitionID) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		 /* 
		 * This method calculates a text value using the provided hashmap entry.  
		 * 
		 * The hashmap entry comes from the provided batch definition.  It provides the logic
		 * necessary to determine a text value for a specified field in the canonical transaction.
		 * 
		 * Presently, this method can handle four ways of calculating the text value to return:
		 * 
		 * Form Field Mapping is where the map entry is a key-value pair for Form (Object) Type and Field Name.  The map's key value is the
		 * object type (e.g., Expense, Item, Allocation, or Journal) and this object's field where the value lies.
		 * 
		 * Constant is where the map entry is a key-value pair where its value is the text value.
		 * 
		 * GUID is where the text value is a GUID that this method generates.
		 * 
		 * SerialNumber is where the text value is a serial number this method generates.
		 * 
		 */

		String text=null;// placeholder for the text value
		

		String mapkey;// placeholder for a map entry key
		String mapvalue;// placeholder for a map entry value
		Method method;// Method object used to determine the Concur custom field for a specified field context
		Object returnvalue;// Object to hold the Concur custom field for a specified field context
		
       mapkey = (String) TextMap.getKey();// get the key for the Map entry, which is how to find the Text value
       mapvalue =  (String) TextMap.getValue();// get the value for the Map entry, which is what field has the Text value

		if (mapkey.equals("ExpenseField")) {// then use the specified field from the Expense object to get the value for the text
			// mapvalue is the name of the field from the Expense object that holds the value for the Text
			method = expense.getClass().getMethod("get" + mapvalue);// construct the method to get the specified field name, mapvalue, for the Expense object
			returnvalue = method.invoke(expense);// the return value of the method.invoke is the value of specified field name
			if (returnvalue == null){
				return text;
			} else if (returnvalue.getClass().toString().equals("class com.pivotpaybles.concurplatform.CustomField")){
				CustomField cf = (CustomField) returnvalue;// convert the returnvalue into a CustomField object
				text = cf.getValue();
			} else if (returnvalue.getClass().toString().equals("class java.util.Date")){
				Date date = (Date)returnvalue;// get the value for this field and assign to the date
				SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
				String strDate = sdf.format(date);
				text = strDate;
			} else {
				text = (String)returnvalue;// get the value for this field and assign to the Text
			}
			return text;
		} else if (mapkey.equals("ItemField")){// then use the specified field from the Itemization object
			// mapvalue is the name of the field from the Itemization object that holds the value for the Customer
			Itemization item = JournalParentLookup.getItemization(expense, journalID);
			method = item.getClass().getMethod("get" + mapvalue);// construct the method to get the specified field for the Itemization
			returnvalue = method.invoke(item);// get the return value for this Itemization field
			if (returnvalue == null){
				return text;
			} else if (returnvalue.getClass().toString().equals("class com.pivotpaybles.concurplatform.CustomField")){
				CustomField cf = (CustomField) returnvalue;// convert the returnvalue into a CustomField object
				text = cf.getValue();
			} else if (returnvalue.getClass().toString().equals("class java.util.Date")){
				Date date = (Date)returnvalue;// get the value for this field and assign to the date
				SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
				String strDate = sdf.format(date);
				text = strDate;
			} else {
				text = (String)returnvalue;// get the value for this field and assign to the Text
			}
			return text;
			
		} else if (mapkey.equals("AllocationField")){// then use the specified field from the Allocation object
			// mapvalue is the name of the field from the Allocation object that holds the value for the Customer
			Allocation allocation = JournalParentLookup.getAllocation(expense, journalID);
			method = allocation.getClass().getMethod("get" + mapvalue);// construct the method to get the specified field for the Allocation
			returnvalue = method.invoke(allocation);// get the return value for this Allocation field
			if (returnvalue == null){
				return text;
			} else if (returnvalue.getClass().toString().equals("class com.pivotpaybles.concurplatform.CustomField")){
				CustomField cf = (CustomField) returnvalue;// convert the returnvalue into a CustomField object
				text = cf.getValue();
			} else if (returnvalue.getClass().toString().equals("class java.util.Date")){
				Date date = (Date)returnvalue;// get the value for this field and assign to the date
				SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
				String strDate = sdf.format(date);
				text = strDate;
			} else {
				text = (String)returnvalue;// get the value for this field and assign to the Text
			}	
			
			return text;
		} else if (mapkey.equals("JournalField")){// then use the specified field from the Journal Entry object
			// mapvalue is the name of the field from the Journal Entry object that holds the value for the Customer
			JournalEntry journal = JournalParentLookup.getJournal(expense, journalID);
			method = journal.getClass().getMethod("get" + mapvalue);// construct the method to get the specified field for the Journal Entry
			returnvalue = method.invoke(journal);// get the return value for this Journal Entry field
			if (returnvalue == null){
				return text;
			} else if (returnvalue.getClass().toString().equals("class com.pivotpaybles.concurplatform.CustomField")){
				CustomField cf = (CustomField) returnvalue;// convert the returnvalue into a CustomField object
				text = cf.getValue();
			} else if (returnvalue.getClass().toString().equals("class java.util.Date")){
				Date date = (Date)returnvalue;// get the value for this field and assign to the date
				SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
				String strDate = sdf.format(date);
				text = strDate;
			} else {
				text = (String)returnvalue;// get the value for this field and assign to the Text
			}
			return text;
		} else if (mapkey.equals("Constant")){// then this is a constant
			// mapvalue is the constant's value
			text = mapvalue;
			return text;
		} else if (mapkey.equals("GUID")){//then create a GUID
			text = GUID.getGUID(Integer.valueOf(mapvalue));// assign a GUID to the number of blocks specified in the map value
			return text;
		} else if (mapkey.equals("Serial")){// then create a Serial Number
			text = ManageSerialNumber.getNumber(batchdefinitionID);
			return text;
			
		}
		
		return text;// return a null description because there wasn't a if condition that applied to provided hashmap entry
	}
	public static Date createDate(HashMap<String, String> DateMap, Expense expense, String journalID) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ParseException{
		//This method returns a date field based on the provided HashMap
		Calendar calendar = Calendar.getInstance();
		Date date=null;
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");// the format to use with Date fields; default is MM-dd-yyyy

		String mapkey ="";
		String mapvalue ="";
		Method method;// Method object used to determine the Concur custom field for a specified field context
		Object returnvalue;// Object to hold the Concur custom field for a specified field context
		Iterator<Entry<String, String>> it;// placeholder for an Iterator object

		it = DateMap.entrySet().iterator();// create an Iterator to iterate the entries in the Map
		while (it.hasNext()) {// iterate for each entry in the Map
	        @SuppressWarnings("rawtypes")
			Map.Entry pair = (Map.Entry)it.next();// get the Map entry (key, value) for this iteration
	        mapkey = (String) pair.getKey();// get the key for the Map entry, which is how to find the Date value
	        mapvalue =  (String) pair.getValue();// get the value for the Map entry, which is what field the Date value
	        it.remove(); // avoids a ConcurrentModificationException, remove the entry from the iteration
	    }// while (it.hasNext())
		if (mapkey.equals("ExpenseField")) {// then use the specified field from the Expense object
			// mapvalue is the name of the field from the Expense object that holds the value for the date
			method = expense.getClass().getMethod("get" + mapvalue);// construct the method to get the specified field for the Expense
			returnvalue = method.invoke(expense);// get the return value for this Expense field
			date = (Date)returnvalue;// get the value for this field and assign to the date
			return date;
		} else if (mapkey.equals("ItemField")){// then use the specified field from the Itemization object
			// mapvalue is the name of the field from the Itemization object that holds the value for the Customer
			Itemization item = JournalParentLookup.getItemization(expense, journalID);
			method = item.getClass().getMethod("get" + mapvalue);// construct the method to get the specified field for the Itemization
			returnvalue = method.invoke(item);// get the return value for this Itemization field
			if (returnvalue == null){
				return date;
			} else if (returnvalue.getClass().toString().equals("class java.util.Date")){
				date = (Date)returnvalue;// get the value for this field and assign to the date
				return date;

			} 
		} else if (mapkey.equals("Constant")){// then this is a constant
			// mapvalue is the constant's value
			
			date = sdf.parse(mapvalue);// convert mapvalue into a Date data type
			return date;
		} else if (mapkey.equals("Today")){
			calendar.getTime();// set calendar to today
			date = calendar.getTime();// return today as a Date object
			return date;
		}// if (mapkey.equals("ExpenseField"))
		
		return date;// return a null date
	}
	public static Boolean createBoolean(HashMap<String, String> BooleanMap, Expense expense, String journalID) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		//This method returns a Boolean field based on the provided HashMap
		Boolean boolvalue=null;
		String mapkey ="";
		String mapvalue ="";
		Method method;// Method object used to determine the Concur custom field for a specified field context
		Object returnvalue;// Object to hold the Concur custom field for a specified field context
		Iterator<Entry<String, String>> it;// placeholder for an Iterator object

		it = BooleanMap.entrySet().iterator();// create an Iterator to iterate the entries in the Map
		while (it.hasNext()) {// iterate for each entry in the Map
	        @SuppressWarnings("rawtypes")
			Map.Entry pair = (Map.Entry)it.next();// get the Map entry (key, value) for this iteration
	        mapkey = (String) pair.getKey();// get the key for the Map entry, which is how to find the Boolean value
	        mapvalue =  (String) pair.getValue();// get the value for the Map entry, which is what field the Boolean value
	        it.remove(); // avoids a ConcurrentModificationException, remove the entry from the iteration
	    }
		if (mapkey.equals("ExpenseField")) {// then use the specified field from the Expense object
			// mapvalue is the name of the field from the Expense object that holds the value for the Boolean
			method = expense.getClass().getMethod("get" + mapvalue);// construct the method to get the specified field for the Expense
			returnvalue = method.invoke(expense);// get the return value for this Expense field
			if (returnvalue.getClass().toString().equals("class com.pivotpaybles.concurplatform.CustomField")){
				CustomField cf = (CustomField) returnvalue;// convert the returnvalue into a CustomField object
				boolvalue = Boolean.valueOf(cf.getValue());// get the value for this field and assign to the Boolean
			} else {
				boolvalue = Boolean.valueOf(returnvalue.toString());// get the value for this field and assign to the Boolean value
			}
			return boolvalue;
		} else if (mapkey.equals("ItemField")){// then use the specified field from the Itemization object
			// mapvalue is the name of the field from the Itemization object that holds the value for the Boolean
			Itemization item = JournalParentLookup.getItemization(expense, journalID);
			method = item.getClass().getMethod("get" + mapvalue);// construct the method to get the specified field for the Itemization
			returnvalue = method.invoke(item);// get the return value for this Itemization field
			if (returnvalue.getClass().toString().equals("class com.pivotpaybles.concurplatform.CustomField")){
				CustomField cf = (CustomField) returnvalue;// convert the returnvalue into a CustomField object
				boolvalue = Boolean.valueOf(cf.getValue());// get the value for this field and assign to the Boolean
			} else {
				boolvalue = Boolean.valueOf(returnvalue.toString());// get the value for this field and assign to the Boolean value
			}
			return boolvalue;
			
		} else if (mapkey.equals("AllocationField")){// then use the specified field from the Allocation object
			// mapvalue is the name of the field from the Itemization object that holds the value for the Boolean
			Allocation allocation = JournalParentLookup.getAllocation(expense, journalID);
			method = allocation.getClass().getMethod("get" + mapvalue);// construct the method to get the specified field for the Allocation
			returnvalue = method.invoke(allocation);// get the return value for this Allocation field
			if (returnvalue.getClass().toString().equals("class com.pivotpaybles.concurplatform.CustomField")){
				CustomField cf = (CustomField) returnvalue;// convert the returnvalue into a CustomField object
				boolvalue = Boolean.valueOf(cf.getValue());// get the value for this field and assign to the Boolean
			} else {
				boolvalue = Boolean.valueOf(returnvalue.toString());// get the value for this field and assign to the Boolean value
			}
			return boolvalue;
		} else if (mapkey.equals("Constant")){// then this
			// mapvalue is the constant's value
			boolvalue = Boolean.valueOf(mapvalue);// convert mapvalue into a Boolean data type
			return boolvalue;
		} 
		
		return boolvalue;// return a null boolvalue
	}
	@SuppressWarnings("rawtypes")
	public static String createCompoundValue (Iterator iterator, Expense expense, String journalID) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		/* 
		 *  This method creates the value for a canonical field that can be a combination of text values, or a "compound value".
		 *  
		 *  Some canonical fields can be the combination of multiple text values.  For example, the field can be the combination of two expense fields delimited by a hyphen.  
		 *  This would require three iterators: 
		 *  
		 *  ExpenseField/Custom1, 
		 *  Constant/-, and 
		 *  ExpenseField/Custom2 *  
		 *  
		 *  This method uses the provided iterator that contains map entries for determining how to create the text values to combine into value. 
		 *  Each map entry represents one of the text values to be combined into the value for the canonical field. The map entry is a key-value pair
		 *  that describes how to calculate the value for the text.
		 *    
		 *  Lastly, this method relies upon the createText method to do the calculation to determine the text value.
		 *  
		 *  
		 */
		
		Map.Entry pair;// placeholder for a map entry
		String fieldvalue="";;// placeholder for the value for a AP Transaction field

		
		while (iterator.hasNext()){// iterate through each map entry in the iterator
			pair = (Map.Entry)iterator.next();// get the Map entry (key, value) for this iteration
	        fieldvalue = fieldvalue + createText(pair, expense, journalID, "");// add the value for this iteration
	        iterator.remove(); // avoids a ConcurrentModificationException, remove the entry from the iteration
		}
		return fieldvalue;
	}
}
