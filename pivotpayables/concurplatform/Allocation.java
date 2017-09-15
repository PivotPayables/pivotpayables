package com.pivotpayables.concurplatform;
/**
 * @author John Toman
 * 6/21/2016
 * This is the base class for the payment request, Allocation resource
 * 
 * Added Journal Entry List and VAT Data List
 * 
 * Moved doctoAllocation method
 *
 */
import static java.lang.System.out;

import java.util.ArrayList;
import java.util.Locale;
import java.text.DecimalFormat;
import java.text.NumberFormat;


//import static java.lang.System.out;



import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.*;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Allocation {
	
	@XmlElementWrapper(name = "JournalEntriesList")
	@XmlElement(name = "JournalEntry")
	ArrayList<JournalEntry> journals;
	
	@XmlElementWrapper(name = "VATDataList")
	@XmlElement(name = "VATData")
	ArrayList<VATData> vatdetails;
	
	
	@XmlElement
	@JsonProperty
	private String AllocationAccountCode;// the Account Code for this allocation
	
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
	private String Percentage;// the percentage of the Line Item Amount for this allocation
	
	
	private String ID;
	
	public Allocation () {}// no args constructor required by Jackson
	
	
	public void display () {//method to display the line item in the console
		
		Locale locale  = new Locale("en", "US");
		String pattern = "###.##";

		DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(locale);
		decimalFormat.applyPattern(pattern);
		
		if (AllocationAccountCode != null) {
			out.println("Allocation Account Code: " + AllocationAccountCode);
		}
		Percentage = this.Percentage.replace('%','0');// replace the percent symbol with a zero
		out.println("Percentage: " + Percentage);
		

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
			 Custom11.display();
		}
		if (Custom12 != null) {
			 Custom12.display();
		}
		if (Custom13 != null) {
			 Custom13.display();
		}
		if (Custom14 != null) {
			 Custom14.display();
		}
		if (Custom15 != null) {
			 Custom15.display();
		}
		if (Custom16 != null) {
			 Custom16.display();
		}
		if (Custom17 != null) {
			 Custom17.display();
		}
		if (Custom18 != null) {
			 Custom18.display();
		}
		if (Custom19 != null) {
			 Custom19.display();
		}
		if (Custom20 != null) {
			 Custom20.display();
		}
		out.println("___________________________________________");
		if(journals != null) {
			out.println("JOURNAL ENTRIES");
			int count = journals.size();
			JournalEntry journal = null;
			if (count >0 ) {
			 for (int index=0 ; index<count; index++) {
				journal = journals.get(index);
				int itereation = index + 1;
				out.println("Journal Entry: " + itereation);
				out.println("-----------------------");
				journal.display();
			 }
				out.println("End Journal Entries");
				out.println();
			}
		}
		
		if(vatdetails != null) {
			out.println("VAT DETAILS");
			int count = vatdetails.size();
			VATData vatdetail = null;
			if (count >0 ) {
			 for (int index=0 ; index<count; index++) {
				vatdetail = vatdetails.get(index);
				int itereation = index + 1;
				out.println("VAT Detail: " + itereation);
				out.println("-----------------------");
				vatdetail.display();
			 }
				out.println("End VAT Details");
				out.println();
			}
		}
	}

	public BasicDBObject getDocument () {//this method returns the allocation as a BasicDBObject that can be added to a MongoDB
		BasicDBObject myAllocation = new BasicDBObject("AllocationID",this.ID);
		Percentage = this.Percentage.replace('%','0');// replace the percent symbol with a zero
		myAllocation.append("Percentage",Percentage);
		myAllocation.append("AllocationAccountCode",this.AllocationAccountCode);

		if (Custom1 != null) {
			myAllocation.append("Custom1Type",this.Custom1.Type);
			myAllocation.append("Custom1Value",this.Custom1.Value);
			myAllocation.append("Custom1Code",this.Custom1.Code);
			myAllocation.append("Custom1ID",this.Custom1.ListItemID);
		}
		if (Custom2 != null) {
			myAllocation.append("Custom2Type",this.Custom2.Type);
			myAllocation.append("Custom2Value",this.Custom2.Value);
			myAllocation.append("Custom2Code",this.Custom2.Code);
			myAllocation.append("Custom2ID",this.Custom2.ListItemID);
		}
		if (Custom3 != null) {
			myAllocation.append("Custom3Type",this.Custom3.Type);
			myAllocation.append("Custom3Value",this.Custom3.Value);
			myAllocation.append("Custom3Code",this.Custom3.Code);
			myAllocation.append("Custom3ID",this.Custom3.ListItemID);
		}
		if (Custom4 != null) {
			myAllocation.append("Custom4Type",this.Custom4.Type);
			myAllocation.append("Custom4Value",this.Custom4.Value);
			myAllocation.append("Custom4Code",this.Custom4.Code);
			myAllocation.append("Custom4ID",this.Custom4.ListItemID);
		}
		if (Custom5 != null) {
			myAllocation.append("Custom5Type",this.Custom5.Type);
			myAllocation.append("Custom5Value",this.Custom5.Value);
			myAllocation.append("Custom5Code",this.Custom5.Code);
			myAllocation.append("Custom5ID",this.Custom5.ListItemID);
		}
		if (Custom6 != null) {
			myAllocation.append("Custom6Type",this.Custom6.Type);
			myAllocation.append("Custom6Value",this.Custom6.Value);
			myAllocation.append("Custom6Code",this.Custom6.Code);
			myAllocation.append("Custom6ID",this.Custom6.ListItemID);
		}
		if (Custom7 != null) {
			myAllocation.append("Custom7Type",this.Custom7.Type);
			myAllocation.append("Custom7Value",this.Custom7.Value);
			myAllocation.append("Custom7Code",this.Custom7.Code);
			myAllocation.append("Custom7ID",this.Custom7.ListItemID);
		}
		if (Custom8 != null) {
			myAllocation.append("Custom8Type",this.Custom8.Type);
			myAllocation.append("Custom8Value",this.Custom8.Value);
			myAllocation.append("Custom8Code",this.Custom8.Code);
			myAllocation.append("Custom8ID",this.Custom8.ListItemID);
		}
		if (Custom9 != null) {
			myAllocation.append("Custom9Type",this.Custom9.Type);
			myAllocation.append("Custom9Value",this.Custom9.Value);
			myAllocation.append("Custom9Code",this.Custom9.Code);
			myAllocation.append("Custom9ID",this.Custom9.ListItemID);
		}
		if (Custom10 != null) {
			myAllocation.append("Custom10Type",this.Custom10.Type);
			myAllocation.append("Custom10Value",this.Custom10.Value);
			myAllocation.append("Custom10Code",this.Custom10.Code);
			myAllocation.append("Custom10ID",this.Custom10.ListItemID);
		}
		if (Custom11 != null) {
			myAllocation.append("Custom11Type",this.Custom11.Type);
			myAllocation.append("Custom11Value",this.Custom11.Value);
			myAllocation.append("Custom11Code",this.Custom11.Code);
			myAllocation.append("Custom11ID",this.Custom11.ListItemID);
		}
		if (Custom12 != null) {
			myAllocation.append("Custom12Type",this.Custom12.Type);
			myAllocation.append("Custom12Value",this.Custom12.Value);
			myAllocation.append("Custom12Code",this.Custom12.Code);
			myAllocation.append("Custom12ID",this.Custom12.ListItemID);
		}
		if (Custom13 != null) {
			myAllocation.append("Custom13Type",this.Custom13.Type);
			myAllocation.append("Custom13Value",this.Custom13.Value);
			myAllocation.append("Custom13Code",this.Custom13.Code);
			myAllocation.append("Custom13ID",this.Custom13.ListItemID);
		}
		if (Custom14 != null) {
			myAllocation.append("Custom14Type",this.Custom14.Type);
			myAllocation.append("Custom14Value",this.Custom14.Value);
			myAllocation.append("Custom14Code",this.Custom14.Code);
			myAllocation.append("Custom14ID",this.Custom14.ListItemID);
		}
		if (Custom15 != null) {
			myAllocation.append("Custom15Type",this.Custom15.Type);
			myAllocation.append("Custom15Value",this.Custom15.Value);
			myAllocation.append("Custom15Code",this.Custom15.Code);
			myAllocation.append("Custom15ID",this.Custom15.ListItemID);
		}
		if (Custom16 != null) {
			myAllocation.append("Custom16Type",this.Custom16.Type);
			myAllocation.append("Custom16Value",this.Custom16.Value);
			myAllocation.append("Custom16Code",this.Custom16.Code);
			myAllocation.append("Custom16ID",this.Custom16.ListItemID);
		}
		if (Custom17 != null) {
			myAllocation.append("Custom17Type",this.Custom17.Type);
			myAllocation.append("Custom17Value",this.Custom17.Value);
			myAllocation.append("Custom17Code",this.Custom17.Code);
			myAllocation.append("Custom17ID",this.Custom17.ListItemID);
		}
		if (Custom18 != null) {
			myAllocation.append("Custom18Type",this.Custom18.Type);
			myAllocation.append("Custom18Value",this.Custom18.Value);
			myAllocation.append("Custom18Code",this.Custom18.Code);
			myAllocation.append("Custom18ID",this.Custom18.ListItemID);
		}
		if (Custom19 != null) {
			myAllocation.append("Custom19Type",this.Custom19.Type);
			myAllocation.append("Custom19Value",this.Custom19.Value);
			myAllocation.append("Custom19Code",this.Custom19.Code);
			myAllocation.append("Custom19ID",this.Custom19.ListItemID);
		}
		if (Custom10 != null) {
			myAllocation.append("Custom10Type",this.Custom10.Type);
			myAllocation.append("Custom10Value",this.Custom10.Value);
			myAllocation.append("Custom10Code",this.Custom10.Code);
			myAllocation.append("Custom10ID",this.Custom10.ListItemID);
		}
		if (Custom20 != null) {
			myAllocation.append("Custom20Type",this.Custom20.Type);
			myAllocation.append("Custom20Value",this.Custom20.Value);
			myAllocation.append("Custom20Code",this.Custom20.Code);
			myAllocation.append("Custom20ID",this.Custom20.ListItemID);
		}
		
		int count = journals.size();// the number of Journal Entries for this Allocation
		JournalEntry journal = null;// placeholder for a Journal Entry object
		if (count >0 ) {// then there are Journal Entries to process
			BasicDBList entries = new BasicDBList();// a BasicDBList with Journal Entry documents as it elements
			
		 for (int index=0 ; index<count; index++) {// iterate for each element in the ArrayList of Journal entry objects
			journal = journals.get(index);// get the Journal Entry object for this iteration
			BasicDBObject Doc = new BasicDBObject();// placeholder for a BasicDBOjbect, MongoDB document
			Doc = journal.getDocument();// get the Journal Entry document for this Journal Entry object
			entries.add(Doc);// add this document to the BasicDBList of Journal Entry documents
		 }
		 myAllocation.append("JournalEntries", entries);// add the BasicDBList of Journal Entry documents to the Allocation document
		}//if (count >0 )
		
		if (vatdetails != null){
			count = vatdetails.size();// the number of VAT details for this Allocation
			if (count >0 ) {// then there are VAT Details to process
				BasicDBList vatdata = new BasicDBList();// a BasicDBList with VAT Data documents as it elements
				
			 for (VATData itvatdetail:vatdetails) {// iterate for each element in the ArrayList of VAT Data objects
				BasicDBObject Doc = new BasicDBObject();// get the VAT Data document for this VAT Data object
				Doc = itvatdetail.getDocument();// get the VAT Data document for this VAT Data object
				vatdata.add(Doc);// add this document to the BasicDBList of VAT Data documents
			 }
			 myAllocation.append("VATData", vatdata);// add the BasicDBList of VAT Data documents to the Allocation document
			}//if (count >0 )
		}
		

		return myAllocation;
	}
	public Allocation doctoAllocation (DBObject doc) {
		
		Allocation myAllocation = new Allocation();

		String ID = doc.get("AllocationID").toString();
		myAllocation.setID(ID);
		String Percentage = doc.get("Percentage").toString();
		Percentage.replace("%", "0");
		myAllocation.setPercentage(Percentage);
		String AllocationAccountCode;
		if (doc.get("AllocationAccountCode") != null){
			AllocationAccountCode = doc.get("AllocationAccountCode").toString();
			myAllocation.setAllocationAccountCode(AllocationAccountCode);
		}

		if (doc.get("Custom1Type")!=null) {
			CustomField Custom1 = new CustomField(doc.get("Custom1Type").toString(), doc.get("Custom1Value").toString());
			if ( (doc.get("Custom1Type").toString().equals("List")) || (doc.get("Custom1Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom1.setCode(doc.get("Custom1Code").toString());
				//Custom1.setListItemID(doc.get("Custom1ID").toString());
			}
			myAllocation.setCustom1(Custom1);
		}
		if (doc.get("Custom2Type")!=null) {
			CustomField Custom2 = new CustomField(doc.get("Custom2Type").toString(), doc.get("Custom2Value").toString());
			if ( (doc.get("Custom2Type").toString().equals("List")) || (doc.get("Custom2Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom2.setCode(doc.get("Custom2Code").toString());
				//Custom2.setListItemID(doc.get("Custom2ID").toString());
			}
			myAllocation.setCustom2(Custom2);
		}
		if (doc.get("Custom3Type")!=null) {
			CustomField Custom3 = new CustomField(doc.get("Custom3Type").toString(), doc.get("Custom3Value").toString());
			if ( (doc.get("Custom3Type").toString().equals("List")) || (doc.get("Custom3Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom3.setCode(doc.get("Custom3Code").toString());
				//Custom3.setListItemID(doc.get("Custom3ID").toString());
			}
			myAllocation.setCustom3(Custom3);
		}
		if (doc.get("Custom4Type")!=null) {
			CustomField Custom4 = new CustomField(doc.get("Custom4Type").toString(), doc.get("Custom4Value").toString());
			if ( (doc.get("Custom4Type").toString().equals("List")) || (doc.get("Custom4Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom4.setCode(doc.get("Custom4Code").toString());
				//Custom4.setListItemID(doc.get("Custom4ID").toString());
			}
			myAllocation.setCustom4(Custom4);
		}
		
		
		if (doc.get("Custom5Type")!=null) {
			CustomField Custom5 = new CustomField(doc.get("Custom5Type").toString(), doc.get("Custom5Value").toString());
			if ( (doc.get("Custom5Type").toString().equals("List")) || (doc.get("Custom5Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom5.setCode(doc.get("Custom5Code").toString());
				//Custom5.setListItemID(doc.get("Custom5ID").toString());
			}
			myAllocation.setCustom5(Custom5);
		}

		if (doc.get("Custom6Type")!=null) {
			CustomField Custom6 = new CustomField(doc.get("Custom6Type").toString(), doc.get("Custom6Value").toString());
			if ( (doc.get("Custom6Type").toString().equals("List")) || (doc.get("Custom6Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom6.setCode(doc.get("Custom6Code").toString());
				//Custom6.setListItemID(doc.get("Custom6ID").toString());
			}
			myAllocation.setCustom6(Custom6);
		}

		if (doc.get("Custom7Type")!=null) {
			CustomField Custom7 = new CustomField(doc.get("Custom7Type").toString(), doc.get("Custom7Value").toString());
			if ( (doc.get("Custom7Type").toString().equals("List")) || (doc.get("Custom7Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom7.setCode(doc.get("Custom7Code").toString());
				Custom7.setListItemID(doc.get("Custom7ID").toString());
			}
			myAllocation.setCustom7(Custom7);
		}

		if (doc.get("Custom8Type")!=null) {
			CustomField Custom8 = new CustomField(doc.get("Custom8Type").toString(), doc.get("Custom8Value").toString());
			if ( (doc.get("Custom8Type").toString().equals("List")) || (doc.get("Custom8Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom8.setCode(doc.get("Custom8Code").toString());
				Custom8.setListItemID(doc.get("Custom8ID").toString());
			}
			myAllocation.setCustom8(Custom8);
		}

		if (doc.get("Custom9Type")!=null) {
			CustomField Custom9 = new CustomField(doc.get("Custom9Type").toString(), doc.get("Custom9Value").toString());
			if ( (doc.get("Custom9Type").toString().equals("List")) || (doc.get("Custom9Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom9.setCode(doc.get("Custom9Code").toString());
				Custom9.setListItemID(doc.get("Custom9ID").toString());
			}
			myAllocation.setCustom9(Custom9);
		}

		if (doc.get("Custom10Type")!=null) {
			CustomField Custom10 = new CustomField(doc.get("Custom10Type").toString(), doc.get("Custom10Value").toString());
			if ( (doc.get("Custom10Type").toString().equals("List")) || (doc.get("Custom10Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom10.setCode(doc.get("Custom10Code").toString());
				Custom10.setListItemID(doc.get("Custom10ID").toString());
			}
			myAllocation.setCustom10(Custom10);
		}

		if (doc.get("Custom11Type")!=null) {
			CustomField Custom11 = new CustomField(doc.get("Custom11Type").toString(), doc.get("Custom11Value").toString());
			if ( (doc.get("Custom11Type").toString().equals("List")) || (doc.get("Custom11Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom11.setCode(doc.get("Custom11Code").toString());
				Custom11.setListItemID(doc.get("Custom11ID").toString());
				myAllocation.setCustom11(Custom11);
			}
		}
			
		if (doc.get("Custom12Type")!=null) {
			CustomField Custom12 = new CustomField(doc.get("Custom12Type").toString(), doc.get("Custom12Value").toString());
			if ( (doc.get("Custom12Type").toString().equals("List")) || (doc.get("Custom12Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom12.setCode(doc.get("Custom12Code").toString());
				Custom12.setListItemID(doc.get("Custom12ID").toString());
			}
			myAllocation.setCustom12(Custom12);
		}

		if (doc.get("Custom13Type")!=null) {
			CustomField Custom13 = new CustomField(doc.get("Custom13Type").toString(), doc.get("Custom13Value").toString());
			if ( (doc.get("Custom13Type").toString().equals("List")) || (doc.get("Custom13Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom13.setCode(doc.get("Custom13Code").toString());
				Custom13.setListItemID(doc.get("Custom13ID").toString());
			}
			myAllocation.setCustom13(Custom13);
		}

		if (doc.get("Custom14Type")!=null) {
			CustomField Custom14 = new CustomField(doc.get("Custom14Type").toString(), doc.get("Custom14Value").toString());
			if ( (doc.get("Custom14Type").toString().equals("List")) || (doc.get("Custom14Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom14.setCode(doc.get("Custom14Code").toString());
				Custom14.setListItemID(doc.get("Custom14ID").toString());
			}
			myAllocation.setCustom14(Custom14);
		}

		if (doc.get("Custom15Type")!=null) {
			CustomField Custom15 = new CustomField(doc.get("Custom15Type").toString(), doc.get("Custom15Value").toString());
			if ( (doc.get("Custom15Type").toString().equals("List")) || (doc.get("Custom15Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom15.setCode(doc.get("Custom15Code").toString());
				Custom15.setListItemID(doc.get("Custom15ID").toString());
			}
			myAllocation.setCustom15(Custom15);
		}

		if (doc.get("Custom16Type")!=null) {
			CustomField Custom16 = new CustomField(doc.get("Custom16Type").toString(), doc.get("Custom16Value").toString());
			if ( (doc.get("Custom16Type").toString().equals("List")) || (doc.get("Custom16Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom16.setCode(doc.get("Custom16Code").toString());
				Custom16.setListItemID(doc.get("Custom16ID").toString());
			}
			myAllocation.setCustom16(Custom16);
		}

		if (doc.get("Custom17Type")!=null) {
			CustomField Custom17 = new CustomField(doc.get("Custom17Type").toString(), doc.get("Custom17Value").toString());
			if ( (doc.get("Custom17Type").toString().equals("List")) || (doc.get("Custom17Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom17.setCode(doc.get("Custom17Code").toString());
				Custom17.setListItemID(doc.get("Custom17ID").toString());
			}
			myAllocation.setCustom17(Custom17);
		}

		if (doc.get("Custom18Type")!=null) {
			CustomField Custom18 = new CustomField(doc.get("Custom18Type").toString(), doc.get("Custom18Value").toString());
			if ( (doc.get("Custom18Type").toString().equals("List")) || (doc.get("Custom18Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom18.setCode(doc.get("Custom18Code").toString());
				Custom18.setListItemID(doc.get("Custom18ID").toString());
			}
			myAllocation.setCustom18(Custom18);
		}

		if (doc.get("Custom19Type")!=null) {
			CustomField Custom19 = new CustomField(doc.get("Custom19Type").toString(), doc.get("Custom19Value").toString());
			if ( (doc.get("Custom19Type").toString().equals("List")) || (doc.get("Custom19Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom19.setCode(doc.get("Custom19Code").toString());
				Custom19.setListItemID(doc.get("Custom19ID").toString());
			}
			myAllocation.setCustom19(Custom19);
		}

		if (doc.get("Custom20Type")!=null) {
			CustomField Custom20 = new CustomField(doc.get("Custom20Type").toString(), doc.get("Custom20Value").toString());
			if ( (doc.get("Custom20Type").toString().equals("List")) || (doc.get("Custom20Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom20.setCode(doc.get("Custom20Code").toString());
				Custom20.setListItemID(doc.get("Custom20ID").toString());
			}
		}
		
		return myAllocation;
	}
	public String getAllocationAccountCode() {
		return AllocationAccountCode;
	}


	public void setAllocationAccountCode(String allocationAccountCode) {
		AllocationAccountCode = allocationAccountCode;
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


	public String getPercentage() {
		return Percentage;
	}


	public void setPercentage(String percentage) {
		Percentage = percentage;
	}


	public ArrayList<JournalEntry> getJournals() {
		return journals;
	}


	public void setJournals(ArrayList<JournalEntry> journals) {
		this.journals = journals;
	}


	public String getID() {
		return ID;
	}


	public void setID(String iD) {
		ID = iD;
	}


	public ArrayList<VATData> getVatdetails() {
		return vatdetails;
	}


	public void setVatdetails(ArrayList<VATData> vatdetails) {
		this.vatdetails = vatdetails;
	}
	
	
	
}
