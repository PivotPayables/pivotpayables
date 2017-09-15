/**
 * 
 */
package com.pivotpayables.concurplatform;

import java.io.IOException;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * @author John Toman	
 * 8/8/2016
 * This getFieldContext method gets the field data for the expense allocation fields that are custom fields.
 * It returns a key-value pair of FieldID-Field Label.  
 *
 */
public class GetCustomFieldsContext {

	/**
	 * @param args[0] = key for WS Admin
	 */

	private static ExpenseReports r = new ExpenseReports();


	public static Map<String, String> getFieldContexts (String key) throws IOException {

		Map<String, String> customfields = new HashMap<String, String>();
		List<FormData> formdatalist = r.getFormDataList("ALLOCINFO", key);// get the list of Expense Allocation forms
		for (FormData form:formdatalist) {// iterate for each Allocation form
			List<FormField> fields = r.getFields(form.getFormID(), key);// get the fields for this iteration's form
			for (FormField field:fields) { // iterate for each field
				if ((field.getID().contains("Custom")) || (field.getID().contains("Org"))) {// then this is a custom field
					customfields.put(field.getID(), field.getLabel());
				}// if block
			}// for (FormField field:fields)
		}// for (FormData form:formdatalist)
		
		return customfields;

	}

}
