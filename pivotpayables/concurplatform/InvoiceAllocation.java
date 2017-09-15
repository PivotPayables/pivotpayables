package com.pivotpayables.concurplatform;
/**
* @author John Toman
* 2/18/2016
* This is the base class for the payment request, Allocation resource
*
*/
import static java.lang.System.out;

import java.util.Locale;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

import static java.lang.System.out;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.*;
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@JsonIgnoreProperties(ignoreUnknown = true)
public class InvoiceAllocation {
	@XmlElement
	@JsonProperty
	String AllocationAccountCode;// the Account Code for this allocation
	@XmlElement
	@JsonProperty
	private String	Custom1;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private String	Custom2;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private String	Custom3;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private String	Custom4;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private String	Custom5;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private String	Custom6;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private String	Custom7;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private String	Custom8;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private String	Custom9;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private String	Custom10;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private String	Custom11;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private String	Custom12;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private String	Custom13;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private String	Custom14;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private String	Custom15;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private String	Custom16;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private String	Custom17;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private String	Custom18;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private String	Custom19;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private String	Custom20;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private String Percentage;// the percentage of the Line Item Amount for this allocation
	

	
	public InvoiceAllocation () {}// no args constructor required by Jackson
	
	
	public void display () {//method to display the line item in the console
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
		Locale locale  = new Locale("en", "US");
		String pattern = "###.##";

		DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(locale);
		decimalFormat.applyPattern(pattern);
		
		if (AllocationAccountCode != null) {
			out.println("Allocation Account Code: " + AllocationAccountCode);
		}
		if (Percentage != null) {
			out.println("Percentage: " + Percentage);
		}



		out.println("CUSTOM FIELDS");
		if (Custom1 != null) {
			out.println("Custom 1----------------------------");
			out.println(Custom1);
		}
		if (Custom2 != null) {
			out.println("Custom 2----------------------------");
			out.println(Custom2);
		}
		if (Custom3 != null) {
			out.println("Custom 3----------------------------");
			out.println(Custom3);
		}
		if (Custom4 != null) {
			out.println("Custom 4----------------------------");
			out.println(Custom4);
		}
		if (Custom5 != null) {
			out.println("Custom 5----------------------------");
			out.println(Custom5);
		}
		if (Custom6 != null) {
			out.println("Custom 6----------------------------");
			out.println(Custom6);
		}
		if (Custom7 != null) {
			out.println("Custom 7----------------------------");
			out.println(Custom7);
		}
		if (Custom8 != null) {
			out.println("Custom 8----------------------------");
			out.println(Custom8);
		}
		if (Custom9 != null) {
			out.println("Custom 9----------------------------");
			out.println(Custom9);
		}
		if (Custom10 != null) {
			out.println("Custom 10----------------------------");
			out.println(Custom10);
		}
		if (Custom11 != null) {
			out.println("Custom 11----------------------------");
			out.println(Custom11);
		}
		if (Custom12 != null) {
			out.println("Custom 12----------------------------");
			out.println(Custom12);
		}
		if (Custom13 != null) {
			out.println("Custom 13----------------------------");
			out.println(Custom13);
		}
		if (Custom14 != null) {
			out.println("Custom 14----------------------------");
			out.println(Custom14);
		}
		if (Custom15 != null) {
			out.println("Custom 15----------------------------");
			out.println(Custom15);
		}
		if (Custom16 != null) {
			out.println("Custom 16----------------------------");
			out.println(Custom16);
		}
		if (Custom17 != null) {
			out.println("Custom 17----------------------------");
			out.println(Custom17);
		}
		if (Custom18 != null) {
			out.println("Custom 18----------------------------");
			out.println(Custom18);
		}
		if (Custom19 != null) {
			out.println("Custom 19----------------------------");
			out.println(Custom19);
		}
		if (Custom20 != null) {
			out.println("Custom 20----------------------------");
			out.println(Custom20);
		}
		
		out.println("___________________________________________");
		out.println();
		
	}


	public String getAllocationAccountCode() {
		return AllocationAccountCode;
	}


	public void setAllocationAccountCode(String allocationAccountCode) {
		AllocationAccountCode = allocationAccountCode;
	}


	public String getCustom1() {
		return Custom1;
	}


	public void setCustom1(String custom1) {
		Custom1 = custom1;
	}


	public String getCustom2() {
		return Custom2;
	}


	public void setCustom2(String custom2) {
		Custom2 = custom2;
	}


	public String getCustom3() {
		return Custom3;
	}


	public void setCustom3(String custom3) {
		Custom3 = custom3;
	}


	public String getCustom4() {
		return Custom4;
	}


	public void setCustom4(String custom4) {
		Custom4 = custom4;
	}


	public String getCustom5() {
		return Custom5;
	}


	public void setCustom5(String custom5) {
		Custom5 = custom5;
	}


	public String getCustom6() {
		return Custom6;
	}


	public void setCustom6(String custom6) {
		Custom6 = custom6;
	}


	public String getCustom7() {
		return Custom7;
	}


	public void setCustom7(String custom7) {
		Custom7 = custom7;
	}


	public String getCustom8() {
		return Custom8;
	}


	public void setCustom8(String custom8) {
		Custom8 = custom8;
	}


	public String getCustom9() {
		return Custom9;
	}


	public void setCustom9(String custom9) {
		Custom9 = custom9;
	}


	public String getCustom10() {
		return Custom10;
	}


	public void setCustom10(String custom10) {
		Custom10 = custom10;
	}


	public String getCustom11() {
		return Custom11;
	}


	public void setCustom11(String custom11) {
		Custom11 = custom11;
	}


	public String getCustom12() {
		return Custom12;
	}


	public void setCustom12(String custom12) {
		Custom12 = custom12;
	}


	public String getCustom13() {
		return Custom13;
	}


	public void setCustom13(String custom13) {
		Custom13 = custom13;
	}


	public String getCustom14() {
		return Custom14;
	}


	public void setCustom14(String custom14) {
		Custom14 = custom14;
	}


	public String getCustom15() {
		return Custom15;
	}


	public void setCustom15(String custom15) {
		Custom15 = custom15;
	}


	public String getCustom16() {
		return Custom16;
	}


	public void setCustom16(String custom16) {
		Custom16 = custom16;
	}


	public String getCustom17() {
		return Custom17;
	}


	public void setCustom17(String custom17) {
		Custom17 = custom17;
	}


	public String getCustom18() {
		return Custom18;
	}


	public void setCustom18(String custom18) {
		Custom18 = custom18;
	}


	public String getCustom19() {
		return Custom19;
	}


	public void setCustom19(String custom19) {
		Custom19 = custom19;
	}


	public String getCustom20() {
		return Custom20;
	}


	public void setCustom20(String custom20) {
		Custom20 = custom20;
	}


	public String getPercentage() {
		return Percentage;
	}


	public void setPercentage(String percentage) {
		Percentage = percentage;
	}
	

}
