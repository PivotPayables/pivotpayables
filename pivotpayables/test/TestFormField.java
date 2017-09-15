/**
 * 
 */
package com.pivotpayables.test;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.pivotpayables.concurplatform.ExpenseReports;
import com.pivotpayables.concurplatform.FormData;
import com.pivotpayables.concurplatform.FormField;
import com.pivotpayables.concurplatform.FormType;
import com.pivotpayables.concurplatform.GetCustomFieldsContext;

import static java.lang.System.out;

/**
 * @author John Toman
 * 8-2-15
 * 
 * This tests the class GetCustomFieldsContext that retrieves the list of custom expenese entry fields.
 *
 */
public class TestFormField {

	/**
	 * @param args
	 * @throws IOException 
	 */

	public static void main(String[] args) throws IOException {
		
		String key ="0_rC7L9ZyQdnpNPQ7NNge2Wv28Q=";//Vantage"0_YXJGQAIkcgDhQyJlXe6sKrV4Y=";//Brainreserve"0_daDheH9Q6QkGrlgfP27j2EhXg=";//Veracity"0_fPsZ5gMPAb2v0ELlEzuBSW9Qo=";//cardno"0_VWAHhSrZARamL5zcMyCDAf400=";// Global Excel//"JzsV4ry0wMjQth5DDGL0UI3j57k=";//apex consulting group////="oyafnBMjWuXX2lxlxJF6IqwIcws=";//"YcvD9V0pD8Tm1sLqTURELsfad1s=";
		
		ExpenseReports r = new ExpenseReports();
		String formtype = "ALLOCINFO";//"ENTRYINFO";//
		

		List<FormData> formdatalist = r.getFormDataList(formtype, key);// get the list of Expense Entry forms
		for (FormData form:formdatalist) {// iterate for each  form
			List<FormField> fields = r.getFields(form.getFormID(), key);// get the fields for this iteration's form
			for (FormField field:fields) { // iterate for each field
				
				if ((field.getID().contains("Custom")) || (field.getID().contains("Org")) || (field.getID().contains("IsBillable"))) {// then this is a custom field
					field.display();
				}// if block
				
			}// for (FormField field:fields)
		}// for (FormData form:formdatalist)
		
			

	}
	

}
