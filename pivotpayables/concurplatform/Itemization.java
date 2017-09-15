package com.pivotpayables.concurplatform;
/**
 * @author John Toman
 * 6/21/2016
 * This is the base class for the Itemization resource.
 * 
 * Added Allocations List
 * 
 * Moved doctoItemization
 *
 */
import static java.lang.System.out;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.pivotpayables.expensesimulator.GUID;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.*;
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Itemization {

	@XmlElementWrapper(name = "AllocationsList")
	@XmlElement(name = "Allocation")
	ArrayList<Allocation> allocations;
	
	@JsonProperty
	private String ID;

	@JsonProperty ("EntryID")
	private String Entry_ID;// the parent, Concur expense entry this itemization is a member
	@XmlElement
	@JsonProperty
	private String ExpenseTypeName;// the expense type name
	@XmlElement
	@JsonProperty
	private Boolean IsBillable;// whether this itemization can be billed to an account(client)
	@XmlElement(name="TransactionDate")
	@JsonProperty("TransactionDate")
	private Date Date;// when this itemization happened
	@XmlElement
	@JsonProperty("TransactionAmount")
	private double TransactionAmount;// the amount for this itemization as it appears on the receipt, and was paid to the merchant; in Original Currency
	@XmlElement
	@JsonProperty
	private double PostedAmount;// the itemization amount converted into Posted Currency
	@XmlElement
	@JsonProperty
	private double ApprovedAmount;// the portion of the posted amount approved for reimbursement/payment
	@XmlElement
	@JsonProperty
	private Date LastModified;
	@XmlElement
	@JsonProperty
	private CustomField	Custom1;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private CustomField	Custom2;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private CustomField	Custom3;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private CustomField	Custom4;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private CustomField	Custom5;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private CustomField	Custom6;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private CustomField	Custom7;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private CustomField	Custom8;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private CustomField	Custom9;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private CustomField	Custom10;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private CustomField	Custom11;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private CustomField	Custom12;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private CustomField	Custom13;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private CustomField	Custom14;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private CustomField	Custom15;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private CustomField	Custom16;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private CustomField	Custom17;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private CustomField	Custom18;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private CustomField	Custom19;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private CustomField	Custom20;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private CustomField	Custom21;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private CustomField	Custom22;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private CustomField	Custom23;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private CustomField	Custom24;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private CustomField	Custom25;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private CustomField	Custom26;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private CustomField	Custom27;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private CustomField	Custom28;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private CustomField	Custom29;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private CustomField	Custom30;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private CustomField	Custom31;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private CustomField	Custom32;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private CustomField	Custom33;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private CustomField	Custom34;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private CustomField	Custom35;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private CustomField	Custom36;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private CustomField	Custom37;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private CustomField	Custom38;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private CustomField	Custom39;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private CustomField	Custom40;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private CustomField	OrgUnit1;//	The details from the Org Unit fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private CustomField	OrgUnit2;//	The details from the Org Unit fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private CustomField	OrgUnit3;//	The details from the Org Unit fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private CustomField	OrgUnit4;//	The details from the Org Unit fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private CustomField	OrgUnit5;//	The details from the Org Unit fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private CustomField	OrgUnit6;//	The details from the Org Unit fields. These may not have data, depending on configuration.
	
	// These fields aren't in the response for GET Itemization V3.0
	// This means this method must calculate their values
	private String EntryID;// the ID for the parent, Expense document in the MongoDB
	private String TransactionCurrencyCode;// the currency paid to the merchant, and appears on the receipt
	private String PostedCurrency;// the currency of the company's AP system
	


	public Itemization () {}// no args constructor required by Jackson
	
	public Itemization (String entryid, String type, Date date, double oamt, double pamt, Boolean billable) {//constructor, basic
		Entry_ID = entryid;
		ExpenseTypeName = type;
		Date = date;
		TransactionAmount = oamt;
		PostedAmount = pamt;
		IsBillable = billable;
		ID = GUID.getGUID(5);
	}
	public Itemization (String entryid, String type, Date date, double oamt, double pamt, Boolean billable, double aamt, String id) {//constructor, used when converting an Itemization document
		this(entryid, type, date, oamt, pamt, billable);
		ApprovedAmount = aamt;
		ID = id;
	}
	public void display () {//method to display the itemization in the console
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
		Locale locale  = new Locale("en", "US");
		String pattern = "###.##";

		DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(locale);
		decimalFormat.applyPattern(pattern);

		
		out.println("ID: " + ID);
		out.println("Parent Expense document ID: " + EntryID);
		out.println("Parent Concur Entry ID: " + Entry_ID);
		out.println("Expense Type: " + ExpenseTypeName);
		String strDate = sdf.format(Date);	 
		if (Date != null) {
			strDate = sdf.format(Date);	 
			out.println("Date: " + strDate);
		}
		out.println("Amount: " + decimalFormat.format(TransactionAmount) + " " + TransactionCurrencyCode);
		out.println("Posted Amount: " + decimalFormat.format(PostedAmount) + " " + PostedCurrency);
		out.println("Approved Amount: " + decimalFormat.format(ApprovedAmount) + " " + PostedCurrency);
		if (IsBillable != null) {
			out.println("Has Billable Items: " + IsBillable.toString());
		}
		if (LastModified != null) {
			strDate = sdf.format(LastModified);	 
			out.println("Last Modified: " + strDate);
		}

		out.println("CUSTOM FIELDS");
		if (Custom1 != null) {
			out.println("Custom 1----------------------------");
			Custom1.display();;
			out.println();
		}
		if (Custom2 != null) {
			out.println("Custom 2----------------------------");
			 Custom2.display();
			out.println();
		}
		if (Custom3 != null) {
			out.println("Custom 3----------------------------");
			 Custom3.display();
		}
		if (Custom4 != null) {
			out.println("Custom 4----------------------------");
			 Custom4.display();
		}
		if (Custom5 != null) {
			out.println("Custom 5----------------------------");
			 Custom5.display();
		}
		if (Custom6 != null) {
			out.println("Custom 6----------------------------");
			 Custom6.display();
		}
		if (Custom7 != null) {
			out.println("Custom 7----------------------------");
			 Custom7.display();
		}
		if (Custom8 != null) {
			out.println("Custom 8----------------------------");
			 Custom8.display();
		}
		if (Custom9 != null) {
			out.println("Custom 9----------------------------");
			 Custom9.display();
		}
		if (Custom10 != null) {
			out.println("Custom 10----------------------------");
			 Custom10.display();
		}
		if (Custom11 != null) {
			out.println("Custom 11----------------------------");
			 Custom11.display();
		}
		if (Custom12 != null) {
			out.println("Custom 12----------------------------");
			Custom12.display();
		}
		if (Custom13 != null) {
			out.println("Custom 13----------------------------");
			Custom13.display();
		}
		if (Custom14 != null) {
			out.println("Custom 14----------------------------");
			Custom14.display();
		}
		if (Custom15 != null) {
			out.println("Custom 15----------------------------");
			Custom15.display();
		}
		if (Custom16 != null) {
			out.println("Custom 16----------------------------");
			Custom16.display();
		}
		if (Custom17 != null) {
			out.println("Custom 17----------------------------");
			Custom17.display();
		}
		if (Custom18 != null) {
			out.println("Custom 18----------------------------");
			Custom18.display();
		}
		if (Custom19 != null) {
			out.println("Custom 19----------------------------");
			Custom19.display();
		}
		if (Custom20 != null) {
			out.println("Custom 20----------------------------");
			Custom20.display();
		}
		if (Custom21 != null) {
			out.println("Custom 21----------------------------");
			Custom21.display();
		}
		if (Custom22 != null) {
			out.println("Custom 22----------------------------");
			Custom22.display();
		}
		if (Custom23 != null) {
			out.println("Custom 23----------------------------");
			Custom23.display();
		}
		if (Custom24 != null) {
			out.println("Custom 24----------------------------");
			Custom24.display();
		}
		if (Custom25 != null) {
			out.println("Custom 25----------------------------");
			Custom25.display();
		}
		if (Custom26 != null) {
			out.println("Custom 26----------------------------");
			Custom26.display();
		}
		if (Custom27 != null) {
			out.println("Custom 27----------------------------");
			Custom27.display();
		}
		if (Custom28 != null) {
			out.println("Custom 28----------------------------");
			Custom28.display();
		}
		if (Custom29 != null) {
			out.println("Custom 29----------------------------");
			Custom29.display();
		}
		if (Custom30 != null) {
			out.println("Custom 30----------------------------");
			Custom30.display();
		}
		if (Custom31 != null) {
			out.println("Custom 31----------------------------");
			Custom31.display();
		}
		if (Custom32 != null) {
			out.println("Custom 32----------------------------");
			Custom32.display();
		}
		if (Custom33 != null) {
			out.println("Custom 33----------------------------");
			Custom33.display();
		}
		if (Custom34 != null) {
			out.println("Custom 34----------------------------");
			Custom34.display();
		}
		if (Custom35 != null) {
			out.println("Custom 35----------------------------");
			Custom35.display();
		}
		if (Custom36 != null) {
			out.println("Custom 36----------------------------");
			Custom36.display();
		}
		if (Custom37 != null) {
			out.println("Custom 37----------------------------");
			Custom37.display();
		}
		if (Custom38 != null) {
			out.println("Custom 38----------------------------");
			Custom38.display();
		}
		if (Custom39 != null) {
			out.println("Custom 39----------------------------");
			Custom39.display();
		}
		if (Custom40 != null) {
			out.println("Custom 40----------------------------");
			Custom40.display();
		}
		out.println("___________________________________________");
		out.println();
		out.println("ORG UNIT FIELDS");
		if (OrgUnit1 != null) {
			out.println("Org Unit 1----------------------------");
			 OrgUnit1.display();
		}
		if (OrgUnit2 != null) {
			out.println("Org Unit 2----------------------------");
			 OrgUnit2.display();
		}
		if (OrgUnit3 != null) {
			out.println("Org Unit 3----------------------------");
			 OrgUnit3.display();
		}
		if (OrgUnit4 != null) {
			out.println("Org Unit 4----------------------------");
			 OrgUnit4.display();
		}
		if (OrgUnit5 != null) {
			out.println("Org Unit 5----------------------------");
			 OrgUnit5.display();
		}
		if (OrgUnit6 != null) {
			out.println("Org Unit 6----------------------------");
			 OrgUnit6.display();
		}
		out.println("___________________________________________");
		if(allocations != null) {
			out.println("ALLOCATIONS");
			int count = allocations.size();
			Allocation allocation = null;
			if (count >0 ) {
			 for (int index=0 ; index<count; index++) {
				allocation = allocations.get(index);
				int itereation = index + 1;
				out.println("Allocation: " + itereation);
				out.println("-----------------------");
				allocation.display();
			 }
				out.println("End Allocations");
				out.println();
			}
		}
	}
	public BasicDBObject getDocument () {//this method returns the itemization as a BasicDBObject that can be added to a MongoDB
		BasicDBObject myItemization = new BasicDBObject("ItemizationID",this.ID);
		myItemization.append("EntryID",this.EntryID);
		myItemization.append("Entry_ID",this.Entry_ID);
		myItemization.append("ExpenseTypeName", this.ExpenseTypeName);
		myItemization.append("IsBillable",this.IsBillable);
		myItemization.append("Date",this.Date);
		myItemization.append("TransactionAmount", this.TransactionAmount);
		myItemization.append("TransactionCurrencyCode",this.TransactionCurrencyCode);
		myItemization.append("PostedAmount", this.PostedAmount);
		myItemization.append("ApprovedAmount",this.ApprovedAmount);
		myItemization.append("PostedCurrency",this.PostedCurrency);
		myItemization.append("LastModified",this.LastModified);
		if (Custom1 != null) {
			myItemization.append("Custom1Type",this.Custom1.Type);
			myItemization.append("Custom1Value",this.Custom1.Value);
			myItemization.append("Custom1Code",this.Custom1.Code);
			myItemization.append("Custom1ID",this.Custom1.ListItemID);
		}
		if (Custom2 != null) {
			myItemization.append("Custom2Type",this.Custom2.Type);
			myItemization.append("Custom2Value",this.Custom2.Value);
			myItemization.append("Custom2Code",this.Custom2.Code);
			myItemization.append("Custom2ID",this.Custom2.ListItemID);
		}
		if (Custom3 != null) {
			myItemization.append("Custom3Type",this.Custom3.Type);
			myItemization.append("Custom3Value",this.Custom3.Value);
			myItemization.append("Custom3Code",this.Custom3.Code);
			myItemization.append("Custom3ID",this.Custom3.ListItemID);
		}
		if (Custom4 != null) {
			myItemization.append("Custom4Type",this.Custom4.Type);
			myItemization.append("Custom4Value",this.Custom4.Value);
			myItemization.append("Custom4Code",this.Custom4.Code);
			myItemization.append("Custom4ID",this.Custom4.ListItemID);
		}
		if (Custom5 != null) {
			myItemization.append("Custom5Type",this.Custom5.Type);
			myItemization.append("Custom5Value",this.Custom5.Value);
			myItemization.append("Custom5Code",this.Custom5.Code);
			myItemization.append("Custom5ID",this.Custom5.ListItemID);
		}
		if (Custom6 != null) {
			myItemization.append("Custom6Type",this.Custom6.Type);
			myItemization.append("Custom6Value",this.Custom6.Value);
			myItemization.append("Custom6Code",this.Custom6.Code);
			myItemization.append("Custom6ID",this.Custom6.ListItemID);
		}
		if (Custom7 != null) {
			myItemization.append("Custom7Type",this.Custom7.Type);
			myItemization.append("Custom7Value",this.Custom7.Value);
			myItemization.append("Custom7Code",this.Custom7.Code);
			myItemization.append("Custom7ID",this.Custom7.ListItemID);
		}
		if (Custom8 != null) {
			myItemization.append("Custom8Type",this.Custom8.Type);
			myItemization.append("Custom8Value",this.Custom8.Value);
			myItemization.append("Custom8Code",this.Custom8.Code);
			myItemization.append("Custom8ID",this.Custom8.ListItemID);
		}
		if (Custom9 != null) {
			myItemization.append("Custom9Type",this.Custom9.Type);
			myItemization.append("Custom9Value",this.Custom9.Value);
			myItemization.append("Custom9Code",this.Custom9.Code);
			myItemization.append("Custom9ID",this.Custom9.ListItemID);
		}
		if (Custom10 != null) {
			myItemization.append("Custom10Type",this.Custom10.Type);
			myItemization.append("Custom10Value",this.Custom10.Value);
			myItemization.append("Custom10Code",this.Custom10.Code);
			myItemization.append("Custom10ID",this.Custom10.ListItemID);
		}
		if (Custom11 != null) {
			myItemization.append("Custom11Type",this.Custom11.Type);
			myItemization.append("Custom11Value",this.Custom11.Value);
			myItemization.append("Custom11Code",this.Custom11.Code);
			myItemization.append("Custom11ID",this.Custom11.ListItemID);
		}
		if (Custom12 != null) {
			myItemization.append("Custom12Type",this.Custom12.Type);
			myItemization.append("Custom12Value",this.Custom12.Value);
			myItemization.append("Custom12Code",this.Custom12.Code);
			myItemization.append("Custom12ID",this.Custom12.ListItemID);
		}
		if (Custom13 != null) {
			myItemization.append("Custom13Type",this.Custom13.Type);
			myItemization.append("Custom13Value",this.Custom13.Value);
			myItemization.append("Custom13Code",this.Custom13.Code);
			myItemization.append("Custom13ID",this.Custom13.ListItemID);
		}
		if (Custom14 != null) {
			myItemization.append("Custom14Type",this.Custom14.Type);
			myItemization.append("Custom14Value",this.Custom14.Value);
			myItemization.append("Custom14Code",this.Custom14.Code);
			myItemization.append("Custom14ID",this.Custom14.ListItemID);
		}
		if (Custom15 != null) {
			myItemization.append("Custom15Type",this.Custom15.Type);
			myItemization.append("Custom15Value",this.Custom15.Value);
			myItemization.append("Custom15Code",this.Custom15.Code);
			myItemization.append("Custom15ID",this.Custom15.ListItemID);
		}
		if (Custom16 != null) {
			myItemization.append("Custom16Type",this.Custom16.Type);
			myItemization.append("Custom16Value",this.Custom16.Value);
			myItemization.append("Custom16Code",this.Custom16.Code);
			myItemization.append("Custom16ID",this.Custom16.ListItemID);
		}
		if (Custom17 != null) {
			myItemization.append("Custom17Type",this.Custom17.Type);
			myItemization.append("Custom17Value",this.Custom17.Value);
			myItemization.append("Custom17Code",this.Custom17.Code);
			myItemization.append("Custom17ID",this.Custom17.ListItemID);
		}
		if (Custom18 != null) {
			myItemization.append("Custom18Type",this.Custom18.Type);
			myItemization.append("Custom18Value",this.Custom18.Value);
			myItemization.append("Custom18Code",this.Custom18.Code);
			myItemization.append("Custom18ID",this.Custom18.ListItemID);
		}
		if (Custom19 != null) {
			myItemization.append("Custom19Type",this.Custom19.Type);
			myItemization.append("Custom19Value",this.Custom19.Value);
			myItemization.append("Custom19Code",this.Custom19.Code);
			myItemization.append("Custom19ID",this.Custom19.ListItemID);
		}
		if (Custom10 != null) {
			myItemization.append("Custom10Type",this.Custom10.Type);
			myItemization.append("Custom10Value",this.Custom10.Value);
			myItemization.append("Custom10Code",this.Custom10.Code);
			myItemization.append("Custom10ID",this.Custom10.ListItemID);
		}
		if (Custom20 != null) {
			myItemization.append("Custom20Type",this.Custom20.Type);
			myItemization.append("Custom20Value",this.Custom20.Value);
			myItemization.append("Custom20Code",this.Custom20.Code);
			myItemization.append("Custom20ID",this.Custom20.ListItemID);
		}
		if (Custom21 != null) {
			myItemization.append("Custom21Type",this.Custom21.Type);
			myItemization.append("Custom21Value",this.Custom21.Value);
			myItemization.append("Custom21Code",this.Custom21.Code);
			myItemization.append("Custom21ID",this.Custom21.ListItemID);
		}
		if (Custom22 != null) {
			myItemization.append("Custom22Type",this.Custom22.Type);
			myItemization.append("Custom22Value",this.Custom22.Value);
			myItemization.append("Custom22Code",this.Custom22.Code);
			myItemization.append("Custom22ID",this.Custom22.ListItemID);
		}
		if (Custom23 != null) {
			myItemization.append("Custom23Type",this.Custom23.Type);
			myItemization.append("Custom23Value",this.Custom23.Value);
			myItemization.append("Custom23Code",this.Custom23.Code);
			myItemization.append("Custom23ID",this.Custom23.ListItemID);
		}
		if (Custom24 != null) {
			myItemization.append("Custom24Type",this.Custom24.Type);
			myItemization.append("Custom24Value",this.Custom24.Value);
			myItemization.append("Custom24Code",this.Custom24.Code);
			myItemization.append("Custom24ID",this.Custom24.ListItemID);
		}
		if (Custom25 != null) {
			myItemization.append("Custom25Type",this.Custom25.Type);
			myItemization.append("Custom25Value",this.Custom25.Value);
			myItemization.append("Custom25Code",this.Custom25.Code);
			myItemization.append("Custom25ID",this.Custom25.ListItemID);
		}
		if (Custom26 != null) {
			myItemization.append("Custom26Type",this.Custom26.Type);
			myItemization.append("Custom26Value",this.Custom26.Value);
			myItemization.append("Custom26Code",this.Custom26.Code);
			myItemization.append("Custom26ID",this.Custom26.ListItemID);
		}
		if (Custom27 != null) {
			myItemization.append("Custom27Type",this.Custom27.Type);
			myItemization.append("Custom27Value",this.Custom27.Value);
			myItemization.append("Custom27Code",this.Custom27.Code);
			myItemization.append("Custom27ID",this.Custom27.ListItemID);
		}
		if (Custom28 != null) {
			myItemization.append("Custom28Type",this.Custom28.Type);
			myItemization.append("Custom28Value",this.Custom28.Value);
			myItemization.append("Custom28Code",this.Custom28.Code);
			myItemization.append("Custom28ID",this.Custom28.ListItemID);
		}
		if (Custom29 != null) {
			myItemization.append("Custom29Type",this.Custom29.Type);
			myItemization.append("Custom29Value",this.Custom29.Value);
			myItemization.append("Custom29Code",this.Custom29.Code);
			myItemization.append("Custom29ID",this.Custom29.ListItemID);
		}
		if (Custom30 != null) {
			myItemization.append("Custom30Type",this.Custom30.Type);
			myItemization.append("Custom30Value",this.Custom30.Value);
			myItemization.append("Custom30Code",this.Custom30.Code);
			myItemization.append("Custom30ID",this.Custom30.ListItemID);
		}
		if (Custom31 != null) {
			myItemization.append("Custom31Type",this.Custom31.Type);
			myItemization.append("Custom31Value",this.Custom31.Value);
			myItemization.append("Custom31Code",this.Custom31.Code);
			myItemization.append("Custom31ID",this.Custom31.ListItemID);
		}
		if (Custom32 != null) {
			myItemization.append("Custom32Type",this.Custom32.Type);
			myItemization.append("Custom32Value",this.Custom32.Value);
			myItemization.append("Custom32Code",this.Custom32.Code);
			myItemization.append("Custom32ID",this.Custom32.ListItemID);
		}
		if (Custom33 != null) {
			myItemization.append("Custom33Type",this.Custom33.Type);
			myItemization.append("Custom33Value",this.Custom33.Value);
			myItemization.append("Custom33Code",this.Custom33.Code);
			myItemization.append("Custom33ID",this.Custom33.ListItemID);
		}
		if (Custom34 != null) {
			myItemization.append("Custom34Type",this.Custom34.Type);
			myItemization.append("Custom34Value",this.Custom34.Value);
			myItemization.append("Custom34Code",this.Custom34.Code);
			myItemization.append("Custom34ID",this.Custom34.ListItemID);
		}
		if (Custom35 != null) {
			myItemization.append("Custom35Type",this.Custom35.Type);
			myItemization.append("Custom35Value",this.Custom35.Value);
			myItemization.append("Custom35Code",this.Custom35.Code);
			myItemization.append("Custom35ID",this.Custom35.ListItemID);
		}
		if (Custom36 != null) {
			myItemization.append("Custom36Type",this.Custom36.Type);
			myItemization.append("Custom36Value",this.Custom36.Value);
			myItemization.append("Custom36Code",this.Custom36.Code);
			myItemization.append("Custom36ID",this.Custom36.ListItemID);
		}
		if (Custom37 != null) {
			myItemization.append("Custom37Type",this.Custom37.Type);
			myItemization.append("Custom37Value",this.Custom37.Value);
			myItemization.append("Custom37Code",this.Custom37.Code);
			myItemization.append("Custom37ID",this.Custom37.ListItemID);
		}
		if (Custom38 != null) {
			myItemization.append("Custom38Type",this.Custom38.Type);
			myItemization.append("Custom38Value",this.Custom38.Value);
			myItemization.append("Custom38Code",this.Custom38.Code);
			myItemization.append("Custom38ID",this.Custom38.ListItemID);
		}
		if (Custom39 != null) {
			myItemization.append("Custom39Type",this.Custom39.Type);
			myItemization.append("Custom39Value",this.Custom39.Value);
			myItemization.append("Custom39Code",this.Custom39.Code);
			myItemization.append("Custom39ID",this.Custom39.ListItemID);
		}
		if (Custom40 != null) {
			myItemization.append("Custom40Type",this.Custom40.Type);
			myItemization.append("Custom40Value",this.Custom40.Value);
			myItemization.append("Custom40Code",this.Custom40.Code);
			myItemization.append("Custom40ID",this.Custom40.ListItemID);
		}
		if (OrgUnit1 != null) {
			myItemization.append("OrgUnit1Type",this.OrgUnit1.Type);
			myItemization.append("OrgUnit1Value",this.OrgUnit1.Value);
			myItemization.append("OrgUnit1Code",this.OrgUnit1.Code);
			myItemization.append("OrgUnit1ID",this.OrgUnit1.ListItemID);
		}
		if (OrgUnit2 != null) {
			myItemization.append("OrgUnit2Type",this.OrgUnit2.Type);
			myItemization.append("OrgUnit2Value",this.OrgUnit2.Value);
			myItemization.append("OrgUnit2Code",this.OrgUnit2.Code);
			myItemization.append("OrgUnit2ID",this.OrgUnit2.ListItemID);
		}
		if (OrgUnit3!= null) {
			myItemization.append("OrgUnit3Type",this.OrgUnit3.Type);
			myItemization.append("OrgUnit3Value",this.OrgUnit3.Value);
			myItemization.append("OrgUnit3Code",this.OrgUnit3.Code);
			myItemization.append("OrgUnit3ID",this.OrgUnit3.ListItemID);
		}
		if (OrgUnit4 != null) {
			myItemization.append("OrgUnit4Type",this.OrgUnit4.Type);
			myItemization.append("OrgUnit4Value",this.OrgUnit4.Value);
			myItemization.append("OrgUnit4Code",this.OrgUnit4.Code);
			myItemization.append("OrgUnit4ID",this.OrgUnit4.ListItemID);
		}
		if (OrgUnit5 != null) {
			myItemization.append("OrgUnit5Type",this.OrgUnit5.Type);
			myItemization.append("OrgUnit5Value",this.OrgUnit5.Value);
			myItemization.append("OrgUnit5Code",this.OrgUnit5.Code);
			myItemization.append("OrgUnit5ID",this.OrgUnit5.ListItemID);
		}
		if (OrgUnit6 != null) {
			myItemization.append("OrgUnit6Type",this.OrgUnit6.Type);
			myItemization.append("OrgUnit6Value",this.OrgUnit6.Value);
			myItemization.append("OrgUnit6Code",this.OrgUnit6.Code);
			myItemization.append("OrgUnit6ID",this.OrgUnit6.ListItemID);
		}
		int count = allocations.size();
		Allocation allocation = null;
		if (count >0 ) {
			BasicDBList allocs = new BasicDBList();
			
		 for (int index=0 ; index<count; index++) {
			allocation = allocations.get(index);
			BasicDBObject Doc = new BasicDBObject();
			Doc = allocation.getDocument();
			allocs.add(Doc);
		 }
		 myItemization.append("Allocations", allocs);

		}//if (count >0 )
		//out.println(myItemization.toString());
		return myItemization;
	}
	public Itemization doctoItemization (DBObject doc) {
		String ID = doc.get("ItemizationID").toString();

		String Entry_ID = doc.get("Entry_ID").toString();
		String ExpenseTypeName = doc.get("ExpenseTypeName").toString();
		Boolean IsBillable = (Boolean)(doc.get("IsBillable"));
		Date Date = (Date)doc.get("Date");
		String amt;//placeholder for an amount represented as a String
		amt = doc.get("TransactionAmount").toString();
		double OriginalAmount = Double.parseDouble(amt);
		amt = doc.get("PostedAmount").toString();
		double PostedAmount = Double.parseDouble(amt);
		amt= doc.get("ApprovedAmount").toString();
		double ApprovedAmount = Double.parseDouble(amt);
		
		Itemization myItemization = new Itemization(Entry_ID, ExpenseTypeName, Date, OriginalAmount, PostedAmount, IsBillable, ApprovedAmount, ID);
		if (doc.get("EntryID") != null){
			String EntryID = doc.get("EntryID").toString();
			myItemization.setEntryID(EntryID);
		}
		String OriginalCurrency = doc.get("TransactionCurrencyCode").toString();
		myItemization.setTransactionCurrencyCode(OriginalCurrency);
		String PostedCurrency = doc.get("PostedCurrency").toString();
		myItemization.setPostedCurrency(PostedCurrency);
		
		if (doc.get("Custom1Type")!=null) {
			CustomField Custom1 = new CustomField(doc.get("Custom1Type").toString(), doc.get("Custom1Value").toString());
			if ( (doc.get("Custom1Type").toString().equals("List")) || (doc.get("Custom1Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom1.setCode(doc.get("Custom1Code").toString());
				//Custom1.setListItemID(doc.get("Custom1ID").toString());
			}
			myItemization.setCustom1(Custom1);
		}
		if (doc.get("Custom2Type")!=null) {
			CustomField Custom2 = new CustomField(doc.get("Custom2Type").toString(), doc.get("Custom2Value").toString());
			if ( (doc.get("Custom2Type").toString().equals("List")) || (doc.get("Custom2Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom2.setCode(doc.get("Custom2Code").toString());
				//Custom2.setListItemID(doc.get("Custom2ID").toString());
			}
			myItemization.setCustom2(Custom2);
		}
		if (doc.get("Custom3Type")!=null) {
			CustomField Custom3 = new CustomField(doc.get("Custom3Type").toString(), doc.get("Custom3Value").toString());
			if ( (doc.get("Custom3Type").toString().equals("List")) || (doc.get("Custom3Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom3.setCode(doc.get("Custom3Code").toString());
				//Custom3.setListItemID(doc.get("Custom3ID").toString());
			}
			myItemization.setCustom3(Custom3);
		}
		if (doc.get("Custom4Type")!=null) {
			CustomField Custom4 = new CustomField(doc.get("Custom4Type").toString(), doc.get("Custom4Value").toString());
			if ( (doc.get("Custom4Type").toString().equals("List")) || (doc.get("Custom4Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom4.setCode(doc.get("Custom4Code").toString());
				//Custom4.setListItemID(doc.get("Custom4ID").toString());
			}
			myItemization.setCustom4(Custom4);
		}
		
		
		if (doc.get("Custom5Type")!=null) {
			CustomField Custom5 = new CustomField(doc.get("Custom5Type").toString(), doc.get("Custom5Value").toString());
			if ( (doc.get("Custom5Type").toString().equals("List")) || (doc.get("Custom5Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom5.setCode(doc.get("Custom5Code").toString());
				//Custom5.setListItemID(doc.get("Custom5ID").toString());
			}
			myItemization.setCustom5(Custom5);
		}

		if (doc.get("Custom6Type")!=null) {
			CustomField Custom6 = new CustomField(doc.get("Custom6Type").toString(), doc.get("Custom6Value").toString());
			if ( (doc.get("Custom6Type").toString().equals("List")) || (doc.get("Custom6Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom6.setCode(doc.get("Custom6Code").toString());
				//Custom6.setListItemID(doc.get("Custom6ID").toString());
			}
			myItemization.setCustom6(Custom6);
		}

		if (doc.get("Custom7Type")!=null) {
			CustomField Custom7 = new CustomField(doc.get("Custom7Type").toString(), doc.get("Custom7Value").toString());
			if ( (doc.get("Custom7Type").toString().equals("List")) || (doc.get("Custom7Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom7.setCode(doc.get("Custom7Code").toString());
				Custom7.setListItemID(doc.get("Custom7ID").toString());
			}
			myItemization.setCustom7(Custom7);
		}

		if (doc.get("Custom8Type")!=null) {
			CustomField Custom8 = new CustomField(doc.get("Custom8Type").toString(), doc.get("Custom8Value").toString());
			if ( (doc.get("Custom8Type").toString().equals("List")) || (doc.get("Custom8Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom8.setCode(doc.get("Custom8Code").toString());
				Custom8.setListItemID(doc.get("Custom8ID").toString());
			}
			myItemization.setCustom8(Custom8);
		}

		if (doc.get("Custom9Type")!=null) {
			CustomField Custom9 = new CustomField(doc.get("Custom9Type").toString(), doc.get("Custom9Value").toString());
			if ( (doc.get("Custom9Type").toString().equals("List")) || (doc.get("Custom9Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom9.setCode(doc.get("Custom9Code").toString());
				Custom9.setListItemID(doc.get("Custom9ID").toString());
			}
			myItemization.setCustom9(Custom9);
		}

		if (doc.get("Custom10Type")!=null) {
			CustomField Custom10 = new CustomField(doc.get("Custom10Type").toString(), doc.get("Custom10Value").toString());
			if ( (doc.get("Custom10Type").toString().equals("List")) || (doc.get("Custom10Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom10.setCode(doc.get("Custom10Code").toString());
				Custom10.setListItemID(doc.get("Custom10ID").toString());
			}
			myItemization.setCustom10(Custom10);
		}

		if (doc.get("Custom11Type")!=null) {
			CustomField Custom11 = new CustomField(doc.get("Custom11Type").toString(), doc.get("Custom11Value").toString());
			if ( (doc.get("Custom11Type").toString().equals("List")) || (doc.get("Custom11Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom11.setCode(doc.get("Custom11Code").toString());
				Custom11.setListItemID(doc.get("Custom11ID").toString());
				myItemization.setCustom11(Custom11);
			}
		}
			
		if (doc.get("Custom12Type")!=null) {
			CustomField Custom12 = new CustomField(doc.get("Custom12Type").toString(), doc.get("Custom12Value").toString());
			if ( (doc.get("Custom12Type").toString().equals("List")) || (doc.get("Custom12Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom12.setCode(doc.get("Custom12Code").toString());
				Custom12.setListItemID(doc.get("Custom12ID").toString());
			}
			myItemization.setCustom12(Custom12);
		}

		if (doc.get("Custom13Type")!=null) {
			CustomField Custom13 = new CustomField(doc.get("Custom13Type").toString(), doc.get("Custom13Value").toString());
			if ( (doc.get("Custom13Type").toString().equals("List")) || (doc.get("Custom13Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom13.setCode(doc.get("Custom13Code").toString());
				Custom13.setListItemID(doc.get("Custom13ID").toString());
			}
			myItemization.setCustom13(Custom13);
		}

		if (doc.get("Custom14Type")!=null) {
			CustomField Custom14 = new CustomField(doc.get("Custom14Type").toString(), doc.get("Custom14Value").toString());
			if ( (doc.get("Custom14Type").toString().equals("List")) || (doc.get("Custom14Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom14.setCode(doc.get("Custom14Code").toString());
				Custom14.setListItemID(doc.get("Custom14ID").toString());
			}
			myItemization.setCustom14(Custom14);
		}

		if (doc.get("Custom15Type")!=null) {
			CustomField Custom15 = new CustomField(doc.get("Custom15Type").toString(), doc.get("Custom15Value").toString());
			if ( (doc.get("Custom15Type").toString().equals("List")) || (doc.get("Custom15Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom15.setCode(doc.get("Custom15Code").toString());
				Custom15.setListItemID(doc.get("Custom15ID").toString());
			}
			myItemization.setCustom15(Custom15);
		}

		if (doc.get("Custom16Type")!=null) {
			CustomField Custom16 = new CustomField(doc.get("Custom16Type").toString(), doc.get("Custom16Value").toString());
			if ( (doc.get("Custom16Type").toString().equals("List")) || (doc.get("Custom16Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom16.setCode(doc.get("Custom16Code").toString());
				Custom16.setListItemID(doc.get("Custom16ID").toString());
			}
			myItemization.setCustom16(Custom16);
		}

		if (doc.get("Custom17Type")!=null) {
			CustomField Custom17 = new CustomField(doc.get("Custom17Type").toString(), doc.get("Custom17Value").toString());
			if ( (doc.get("Custom17Type").toString().equals("List")) || (doc.get("Custom17Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom17.setCode(doc.get("Custom17Code").toString());
				Custom17.setListItemID(doc.get("Custom17ID").toString());
			}
			myItemization.setCustom17(Custom17);
		}

		if (doc.get("Custom18Type")!=null) {
			CustomField Custom18 = new CustomField(doc.get("Custom18Type").toString(), doc.get("Custom18Value").toString());
			if ( (doc.get("Custom18Type").toString().equals("List")) || (doc.get("Custom18Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom18.setCode(doc.get("Custom18Code").toString());
				Custom18.setListItemID(doc.get("Custom18ID").toString());
			}
			myItemization.setCustom18(Custom18);
		}

		if (doc.get("Custom19Type")!=null) {
			CustomField Custom19 = new CustomField(doc.get("Custom19Type").toString(), doc.get("Custom19Value").toString());
			if ( (doc.get("Custom19Type").toString().equals("List")) || (doc.get("Custom19Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom19.setCode(doc.get("Custom19Code").toString());
				Custom19.setListItemID(doc.get("Custom19ID").toString());
			}
			myItemization.setCustom19(Custom19);
		}

		if (doc.get("Custom20Type")!=null) {
			CustomField Custom20 = new CustomField(doc.get("Custom20Type").toString(), doc.get("Custom20Value").toString());
			if ( (doc.get("Custom20Type").toString().equals("List")) || (doc.get("Custom20Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom20.setCode(doc.get("Custom20Code").toString());
				Custom20.setListItemID(doc.get("Custom20ID").toString());
			}
		}

		if (doc.get("Custom21Type")!=null) {
			CustomField Custom21 = new CustomField(doc.get("Custom21Type").toString(), doc.get("Custom21Value").toString());
			if ( (doc.get("Custom21Type").toString().equals("List")) || (doc.get("Custom21Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom21.setCode(doc.get("Custom21Code").toString());
				Custom21.setListItemID(doc.get("Custom21ID").toString());
			}
		}

		if (doc.get("Custom22Type")!=null) {
			CustomField Custom22 = new CustomField(doc.get("Custom22Type").toString(), doc.get("Custom22Value").toString());
			if ( (doc.get("Custom22Type").toString().equals("List")) || (doc.get("Custom22Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom22.setCode(doc.get("Custom22Code").toString());
				Custom22.setListItemID(doc.get("Custom22ID").toString());
			}
			myItemization.setCustom22(Custom22);
		}

		if (doc.get("Custom23Type")!=null) {
			CustomField Custom23 = new CustomField(doc.get("Custom23Type").toString(), doc.get("Custom23Value").toString());
			if ( (doc.get("Custom23Type").toString().equals("List")) || (doc.get("Custom23Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom23.setCode(doc.get("Custom23Code").toString());
				Custom23.setListItemID(doc.get("Custom23ID").toString());
			}
			myItemization.setCustom23(Custom23);
		}

		if (doc.get("Custom24Type")!=null) {
			CustomField Custom24 = new CustomField(doc.get("Custom24Type").toString(), doc.get("Custom24Value").toString());
			if ( (doc.get("Custom24Type").toString().equals("List")) || (doc.get("Custom24Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom24.setCode(doc.get("Custom24Code").toString());
				Custom24.setListItemID(doc.get("Custom24ID").toString());
			}
			myItemization.setCustom24(Custom24);
		}

		if (doc.get("Custom25Type")!=null) {
			CustomField Custom25 = new CustomField(doc.get("Custom25Type").toString(), doc.get("Custom25Value").toString());
			if ( (doc.get("Custom25Type").toString().equals("List")) || (doc.get("Custom25Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom25.setCode(doc.get("Custom25Code").toString());
				Custom25.setListItemID(doc.get("Custom25ID").toString());
			}
			myItemization.setCustom25(Custom25);
		}

		if (doc.get("Custom26Type")!=null) {
			CustomField Custom26 = new CustomField(doc.get("Custom26Type").toString(), doc.get("Custom26Value").toString());
			if ( (doc.get("Custom26Type").toString().equals("List")) || (doc.get("Custom26Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom26.setCode(doc.get("Custom26Code").toString());
				Custom26.setListItemID(doc.get("Custom26ID").toString());
			}
			myItemization.setCustom26(Custom26);
		}

		if (doc.get("Custom27Type")!=null) {
			CustomField Custom27 = new CustomField(doc.get("Custom27Type").toString(), doc.get("Custom27Value").toString());
			if ( (doc.get("Custom27Type").toString().equals("List")) || (doc.get("Custom27Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom27.setCode(doc.get("Custom27Code").toString());
				Custom27.setListItemID(doc.get("Custom27ID").toString());
			}
			myItemization.setCustom27(Custom27);
		}

		if (doc.get("Custom28Type")!=null) {
			CustomField Custom28 = new CustomField(doc.get("Custom28Type").toString(), doc.get("Custom28Value").toString());
			if ( (doc.get("Custom28Type").toString().equals("List")) || (doc.get("Custom28Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom28.setCode(doc.get("Custom28Code").toString());
				Custom28.setListItemID(doc.get("Custom28ID").toString());
			}
			myItemization.setCustom28(Custom28);
		}

		if (doc.get("Custom29Type")!=null) {
			CustomField Custom29 = new CustomField(doc.get("Custom29Type").toString(), doc.get("Custom29Value").toString());
			if ( (doc.get("Custom29Type").toString().equals("List")) || (doc.get("Custom29Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom29.setCode(doc.get("Custom29Code").toString());
				Custom29.setListItemID(doc.get("Custom29ID").toString());
			}
			myItemization.setCustom29(Custom29);
		}

		if (doc.get("Custom30Type")!=null) {
			CustomField Custom30 = new CustomField(doc.get("Custom30Type").toString(), doc.get("Custom30Value").toString());
			if ( (doc.get("Custom30Type").toString().equals("List")) || (doc.get("Custom30Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom30.setCode(doc.get("Custom30Code").toString());
				Custom30.setListItemID(doc.get("Custom30ID").toString());
			}
			myItemization.setCustom30(Custom30);
		}

		if (doc.get("Custom31Type")!=null) {
			CustomField Custom31 = new CustomField(doc.get("Custom31Type").toString(), doc.get("Custom31Value").toString());
			if ( (doc.get("Custom31Type").toString().equals("List")) || (doc.get("Custom31Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom31.setCode(doc.get("Custom31Code").toString());
				Custom31.setListItemID(doc.get("Custom31ID").toString());
			}
			myItemization.setCustom31(Custom31);
		}

		if (doc.get("Custom32Type")!=null) {
			CustomField Custom32 = new CustomField(doc.get("Custom32Type").toString(), doc.get("Custom32Value").toString());
			if ( (doc.get("Custom32Type").toString().equals("List")) || (doc.get("Custom32Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom32.setCode(doc.get("Custom32Code").toString());
				Custom32.setListItemID(doc.get("Custom32ID").toString());
			}
			myItemization.setCustom32(Custom32);
		}

		if (doc.get("Custom33Type")!=null) {
			CustomField Custom33 = new CustomField(doc.get("Custom33Type").toString(), doc.get("Custom33Value").toString());
			if ( (doc.get("Custom33Type").toString().equals("List")) || (doc.get("Custom33Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom33.setCode(doc.get("Custom33Code").toString());
				Custom33.setListItemID(doc.get("Custom33ID").toString());
			}
			myItemization.setCustom33(Custom33);
		}

		if (doc.get("Custom34Type")!=null) {
			CustomField Custom34 = new CustomField(doc.get("Custom34Type").toString(), doc.get("Custom34Value").toString());
			if ( (doc.get("Custom34Type").toString().equals("List")) || (doc.get("Custom34Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom34.setCode(doc.get("Custom34Code").toString());
				Custom34.setListItemID(doc.get("Custom34ID").toString());
			}
			myItemization.setCustom34(Custom34);
		}

		if (doc.get("Custom35Type")!=null) {
			CustomField Custom35 = new CustomField(doc.get("Custom35Type").toString(), doc.get("Custom35Value").toString());
			if ( (doc.get("Custom35Type").toString().equals("List")) || (doc.get("Custom35Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom35.setCode(doc.get("Custom35Code").toString());
				Custom35.setListItemID(doc.get("Custom35ID").toString());
			}
			myItemization.setCustom35(Custom35);
		}

		if (doc.get("Custom36Type")!=null) {
			CustomField Custom36 = new CustomField(doc.get("Custom36Type").toString(), doc.get("Custom36Value").toString());
			if ( (doc.get("Custom36Type").toString().equals("List")) || (doc.get("Custom36Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom36.setCode(doc.get("Custom36Code").toString());
				Custom36.setListItemID(doc.get("Custom36ID").toString());
			}
			myItemization.setCustom36(Custom36);
		}

		if (doc.get("Custom37Type")!=null) {
			CustomField Custom37 = new CustomField(doc.get("Custom37Type").toString(), doc.get("Custom37Value").toString());
			if ( (doc.get("Custom37Type").toString().equals("List")) || (doc.get("Custom37Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom37.setCode(doc.get("Custom37Code").toString());
				Custom37.setListItemID(doc.get("Custom37ID").toString());
			}
			myItemization.setCustom37(Custom37);
		}

		if (doc.get("Custom38Type")!=null) {
			CustomField Custom38 = new CustomField(doc.get("Custom38Type").toString(), doc.get("Custom38Value").toString());
			if ( (doc.get("Custom38Type").toString().equals("List")) || (doc.get("Custom38Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom38.setCode(doc.get("Custom38Code").toString());
				Custom38.setListItemID(doc.get("Custom38ID").toString());
			}
			myItemization.setCustom38(Custom38);
		}

		if (doc.get("Custom39Type")!=null) {
			CustomField Custom39 = new CustomField(doc.get("Custom39Type").toString(), doc.get("Custom39Value").toString());
			if ( (doc.get("Custom39Type").toString().equals("List")) || (doc.get("Custom39Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom39.setCode(doc.get("Custom39Code").toString());
				Custom39.setListItemID(doc.get("Custom39ID").toString());
			}
			myItemization.setCustom39(Custom39);
		}

		if (doc.get("Custom40Type")!=null) {
			CustomField Custom40 = new CustomField(doc.get("Custom40Type").toString(), doc.get("Custom40Value").toString());
			if ( (doc.get("Custom40Type").toString().equals("List")) || (doc.get("Custom40Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom40.setCode(doc.get("Custom40Code").toString());
				Custom40.setListItemID(doc.get("Custom40ID").toString());
			}
			myItemization.setCustom40(Custom40);
		}

		
		if (doc.get("OrgUnit1Type")!=null) {
			CustomField OrgUnit1 = new CustomField(doc.get("OrgUnit1Type").toString(), doc.get("OrgUnit1Value").toString());
			if ( (doc.get("OrgUnit1Type").toString().equals("List")) || (doc.get("OrgUnit1Type").toString().equals("ConnectedList")) ) {// it's a list type
				OrgUnit1.setCode(doc.get("OrgUnit1Code").toString());
				OrgUnit1.setListItemID(doc.get("OrgUnit1ID").toString());
			}
			myItemization.setOrgUnit1(OrgUnit1);
		}
		if (doc.get("OrgUnit2Type")!=null) {
			CustomField OrgUnit2 = new CustomField(doc.get("OrgUnit2Type").toString(), doc.get("OrgUnit2Value").toString());
			if ( (doc.get("OrgUnit2Type").toString().equals("List")) || (doc.get("OrgUnit2Type").toString().equals("ConnectedList")) ) {// it's a list type
				OrgUnit2.setCode(doc.get("OrgUnit2Code").toString());
				OrgUnit2.setListItemID(doc.get("OrgUnit2ID").toString());
			}
			myItemization.setOrgUnit2(OrgUnit2);
		}
		if (doc.get("OrgUnit3Type")!=null) {
			CustomField OrgUnit3 = new CustomField(doc.get("OrgUnit3Type").toString(), doc.get("OrgUnit3Value").toString());
			if ( (doc.get("OrgUnit3Type").toString().equals("List")) || (doc.get("OrgUnit3Type").toString().equals("ConnectedList")) ) {// it's a list type
				OrgUnit3.setCode(doc.get("OrgUnit3Code").toString());
				OrgUnit3.setListItemID(doc.get("OrgUnit3ID").toString());
			}
			myItemization.setOrgUnit3(OrgUnit3);
		}
		if (doc.get("OrgUnit4Type")!=null) {
			CustomField OrgUnit4 = new CustomField(doc.get("OrgUnit4Type").toString(), doc.get("OrgUnit4Value").toString());
			if ( (doc.get("OrgUnit4Type").toString().equals("List")) || (doc.get("OrgUnit4Type").toString().equals("ConnectedList")) ) {// it's a list type
				OrgUnit4.setCode(doc.get("OrgUnit4Code").toString());
				OrgUnit4.setListItemID(doc.get("OrgUnit4ID").toString());
			}
			myItemization.setOrgUnit4(OrgUnit4);
		}
		if (doc.get("OrgUnit5Type")!=null) {
			CustomField OrgUnit5 = new CustomField(doc.get("OrgUnit5Type").toString(), doc.get("OrgUnit5Value").toString());
			if ( (doc.get("OrgUnit5Type").toString().equals("List")) || (doc.get("OrgUnit5Type").toString().equals("ConnectedList")) ) {// it's a list type
				OrgUnit5.setCode(doc.get("OrgUnit5Code").toString());
				OrgUnit5.setListItemID(doc.get("OrgUnit5ID").toString());
			}
			myItemization.setOrgUnit5(OrgUnit5);
		}
		if (doc.get("OrgUnit6Type")!=null) {
			CustomField OrgUnit6 = new CustomField(doc.get("OrgUnit6Type").toString(), doc.get("OrgUnit6Value").toString());
			if ( (doc.get("OrgUnit6Type").toString().equals("List")) || (doc.get("OrgUnit6Type").toString().equals("ConnectedList")) ) {// it's a list type
				OrgUnit6.setCode(doc.get("OrgUnit6Code").toString());
				OrgUnit6.setListItemID(doc.get("OrgUnit6ID").toString());
			}
			myItemization.setOrgUnit6(OrgUnit6);
		}
		
		return myItemization;
	}
	
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getEntryID() {
		return EntryID;
	}
	public void setEntryID(String entryID) {
		EntryID = entryID;
	}
	
	public String getEntry_ID() {
		return Entry_ID;
	}

	public void setEntry_ID(String entry_ID) {
		Entry_ID = entry_ID;
	}

	public String getExpenseTypeName() {
		return ExpenseTypeName;
	}
	public void setExpenseTypeName(String expenseTypeName) {
		ExpenseTypeName = expenseTypeName;
	}
	public Boolean getIsBillable() {
		return IsBillable;
	}
	public void setIsBillable(Boolean isBillable) {
		IsBillable = isBillable;
	}
	public Date getDate() {
		return Date;
	}
	public void setDate(Date date) {
		Date = date;
	}
	public double getTransactionAmount() {
		return TransactionAmount;
	}
	public void setTransactionAmount(double originalAmount) {
		TransactionAmount = originalAmount;
	}
	public double getPostedAmount() {
		return PostedAmount;
	}
	public void setPostedAmount(double postedAmount) {
		PostedAmount = postedAmount;
	}
	public double getApprovedAmount() {
		return ApprovedAmount;
	}
	public void setApprovedAmount(double approvedAmount) {
		ApprovedAmount = approvedAmount;
	}
	public String getTransactionCurrencyCode() {
		return TransactionCurrencyCode;
	}
	public void setTransactionCurrencyCode(String originalCurrency) {
		TransactionCurrencyCode = originalCurrency;
	}
	public String getPostedCurrency() {
		return PostedCurrency;
	}
	public void setPostedCurrency(String postedCurrency) {
		PostedCurrency = postedCurrency;
	}

	public Date getLastModified() {
		return LastModified;
	}

	public void setLastModified(Date lastModified) {
		LastModified = lastModified;
	}

	public CustomField getCustom1() {
		return Custom1;
	}

	public void setCustom1(CustomField custom1) {
		Custom1 = custom1;
	}

	public CustomField getCustom2() {
		return Custom2;
	}

	public void setCustom2(CustomField custom2) {
		Custom2 = custom2;
	}

	public CustomField getCustom3() {
		return Custom3;
	}

	public void setCustom3(CustomField custom3) {
		Custom3 = custom3;
	}

	public CustomField getCustom4() {
		return Custom4;
	}

	public void setCustom4(CustomField custom4) {
		Custom4 = custom4;
	}

	public CustomField getCustom5() {
		return Custom5;
	}

	public void setCustom5(CustomField custom5) {
		Custom5 = custom5;
	}

	public CustomField getCustom6() {
		return Custom6;
	}

	public void setCustom6(CustomField custom6) {
		Custom6 = custom6;
	}

	public CustomField getCustom7() {
		return Custom7;
	}

	public void setCustom7(CustomField custom7) {
		Custom7 = custom7;
	}

	public CustomField getCustom8() {
		return Custom8;
	}

	public void setCustom8(CustomField custom8) {
		Custom8 = custom8;
	}

	public CustomField getCustom9() {
		return Custom9;
	}

	public void setCustom9(CustomField custom9) {
		Custom9 = custom9;
	}

	public CustomField getCustom10() {
		return Custom10;
	}

	public void setCustom10(CustomField custom10) {
		Custom10 = custom10;
	}

	public CustomField getCustom11() {
		return Custom11;
	}

	public void setCustom11(CustomField custom11) {
		Custom11 = custom11;
	}

	public CustomField getCustom12() {
		return Custom12;
	}

	public void setCustom12(CustomField custom12) {
		Custom12 = custom12;
	}

	public CustomField getCustom13() {
		return Custom13;
	}

	public void setCustom13(CustomField custom13) {
		Custom13 = custom13;
	}

	public CustomField getCustom14() {
		return Custom14;
	}

	public void setCustom14(CustomField custom14) {
		Custom14 = custom14;
	}

	public CustomField getCustom15() {
		return Custom15;
	}

	public void setCustom15(CustomField custom15) {
		Custom15 = custom15;
	}

	public CustomField getCustom16() {
		return Custom16;
	}

	public void setCustom16(CustomField custom16) {
		Custom16 = custom16;
	}

	public CustomField getCustom17() {
		return Custom17;
	}

	public void setCustom17(CustomField custom17) {
		Custom17 = custom17;
	}

	public CustomField getCustom18() {
		return Custom18;
	}

	public void setCustom18(CustomField custom18) {
		Custom18 = custom18;
	}

	public CustomField getCustom19() {
		return Custom19;
	}

	public void setCustom19(CustomField custom19) {
		Custom19 = custom19;
	}

	public CustomField getCustom20() {
		return Custom20;
	}

	public void setCustom20(CustomField custom20) {
		Custom20 = custom20;
	}

	public CustomField getCustom21() {
		return Custom21;
	}

	public void setCustom21(CustomField custom21) {
		Custom21 = custom21;
	}

	public CustomField getCustom22() {
		return Custom22;
	}

	public void setCustom22(CustomField custom22) {
		Custom22 = custom22;
	}

	public CustomField getCustom23() {
		return Custom23;
	}

	public void setCustom23(CustomField custom23) {
		Custom23 = custom23;
	}

	public CustomField getCustom24() {
		return Custom24;
	}

	public void setCustom24(CustomField custom24) {
		Custom24 = custom24;
	}

	public CustomField getCustom25() {
		return Custom25;
	}

	public void setCustom25(CustomField custom25) {
		Custom25 = custom25;
	}

	public CustomField getCustom26() {
		return Custom26;
	}

	public void setCustom26(CustomField custom26) {
		Custom26 = custom26;
	}

	public CustomField getCustom27() {
		return Custom27;
	}

	public void setCustom27(CustomField custom27) {
		Custom27 = custom27;
	}

	public CustomField getCustom28() {
		return Custom28;
	}

	public void setCustom28(CustomField custom28) {
		Custom28 = custom28;
	}

	public CustomField getCustom29() {
		return Custom29;
	}

	public void setCustom29(CustomField custom29) {
		Custom29 = custom29;
	}

	public CustomField getCustom30() {
		return Custom30;
	}

	public void setCustom30(CustomField custom30) {
		Custom30 = custom30;
	}

	public CustomField getCustom31() {
		return Custom31;
	}

	public void setCustom31(CustomField custom31) {
		Custom31 = custom31;
	}

	public CustomField getCustom32() {
		return Custom32;
	}

	public void setCustom32(CustomField custom32) {
		Custom32 = custom32;
	}

	public CustomField getCustom33() {
		return Custom33;
	}

	public void setCustom33(CustomField custom33) {
		Custom33 = custom33;
	}

	public CustomField getCustom34() {
		return Custom34;
	}

	public void setCustom34(CustomField custom34) {
		Custom34 = custom34;
	}

	public CustomField getCustom35() {
		return Custom35;
	}

	public void setCustom35(CustomField custom35) {
		Custom35 = custom35;
	}

	public CustomField getCustom36() {
		return Custom36;
	}

	public void setCustom36(CustomField custom36) {
		Custom36 = custom36;
	}

	public CustomField getCustom37() {
		return Custom37;
	}

	public void setCustom37(CustomField custom37) {
		Custom37 = custom37;
	}

	public CustomField getCustom38() {
		return Custom38;
	}

	public void setCustom38(CustomField custom38) {
		Custom38 = custom38;
	}

	public CustomField getCustom39() {
		return Custom39;
	}

	public void setCustom39(CustomField custom39) {
		Custom39 = custom39;
	}

	public CustomField getCustom40() {
		return Custom40;
	}

	public void setCustom40(CustomField custom40) {
		Custom40 = custom40;
	}

	public CustomField getOrgUnit1() {
		return OrgUnit1;
	}

	public void setOrgUnit1(CustomField orgUnit1) {
		OrgUnit1 = orgUnit1;
	}

	public CustomField getOrgUnit2() {
		return OrgUnit2;
	}

	public void setOrgUnit2(CustomField orgUnit2) {
		OrgUnit2 = orgUnit2;
	}

	public CustomField getOrgUnit3() {
		return OrgUnit3;
	}

	public void setOrgUnit3(CustomField orgUnit3) {
		OrgUnit3 = orgUnit3;
	}

	public CustomField getOrgUnit4() {
		return OrgUnit4;
	}

	public void setOrgUnit4(CustomField orgUnit4) {
		OrgUnit4 = orgUnit4;
	}

	public CustomField getOrgUnit5() {
		return OrgUnit5;
	}

	public void setOrgUnit5(CustomField orgUnit5) {
		OrgUnit5 = orgUnit5;
	}

	public CustomField getOrgUnit6() {
		return OrgUnit6;
	}

	public void setOrgUnit6(CustomField orgUnit6) {
		OrgUnit6 = orgUnit6;
	}

	public ArrayList<Allocation> getAllocations() {
		return allocations;
	}

	public void setAllocations(ArrayList<Allocation> allocations) {
		this.allocations = allocations;
	}
	
	
}
