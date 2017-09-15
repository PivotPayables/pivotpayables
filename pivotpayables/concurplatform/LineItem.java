package com.pivotpayables.concurplatform;
/**
 * @author John Toman
 * 1/23/2016
 * This is the base class for the payment request, Line Item resource
 *
 */
import static java.lang.System.out;






import java.util.ArrayList;
import java.util.Locale;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;








import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.*;
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LineItem {

	@XmlElement
	@JsonProperty
	double ApprovedLineItemAmount;// the portion of the posted amount approved for payment to the vendor
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
	private String Description;// the expense type name
	@XmlElement
	@JsonProperty
	private String ExpenseTypeCode;// the expense type code
	@XmlElement
	@JsonProperty 
	private String LineItemId;
	@XmlElement
	@JsonProperty
	private double Quantity;// the number of units for this line item as it appears on the invoice, and was paid to the merchant; in Original Currency
	@XmlElement
	@JsonProperty 
	private double TotalPrice;// the line item amount converted into Posted Currency@XmlElement
	@JsonProperty
	private String UnitofMeasure;// the unit of measure for this line item
	@XmlElement
	@JsonProperty
	private double UnitPrice;// the unit price for this line item as it appears on the invoice, and was paid to the merchant; in Original Currency
	@XmlElement
	@JsonProperty
	ArrayList<InvoiceAllocation> Allocations;
	

	
	public LineItem () {}// no args constructor required by Jackson
	
	
	public void display () {//method to display the line item in the console

		Locale locale  = new Locale("en", "US");
		String pattern = "###.##";

		DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(locale);
		decimalFormat.applyPattern(pattern);
		if (LineItemId != null) {
			out.println("Line Item ID: " + LineItemId);
		}
		out.println("Expense Type Code: " + ExpenseTypeCode);
		out.println("Description: " + Description);
		out.println("Unit of Measure: " + UnitofMeasure);
		out.println("Unit Price: " + decimalFormat.format(UnitPrice));
		out.println("Quantity: " + decimalFormat.format(Quantity));
		out.println("Total: " + decimalFormat.format(TotalPrice));
		out.println("Approved Amount: " + decimalFormat.format(ApprovedLineItemAmount));



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
		
		if(Allocations != null) {
			out.println("Allocations");
			int count = Allocations.size();
			InvoiceAllocation allocation = new InvoiceAllocation();
			if (count >0 ) {
			 for (int index=0 ; index<count; index++) {
				 allocation = Allocations.get(index);
				int itereation = index + 1;
				out.println("Allocation: " + itereation);
				out.println("-----------------------");
				allocation.display();
			 }
				out.println("End Allocations___________________________________________");
				
			}
		}
	}


	public double getApprovedLineItemAmount() {
		return ApprovedLineItemAmount;
	}


	public void setApprovedLineItemAmount(double approvedLineItemAmount) {
		ApprovedLineItemAmount = approvedLineItemAmount;
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


	public String getDescription() {
		return Description;
	}


	public void setDescription(String description) {
		Description = description;
	}


	public String getExpenseTypeCode() {
		return ExpenseTypeCode;
	}


	public void setExpenseTypeCode(String expenseTypeCode) {
		ExpenseTypeCode = expenseTypeCode;
	}

	public String getLineItemId() {
		return LineItemId;
	}


	public void setLineItemId(String lineItemId) {
		LineItemId = lineItemId;
	}


	public double getQuantity() {
		return Quantity;
	}


	public void setQuantity(double quantity) {
		Quantity = quantity;
	}


	public double getTotalPrice() {
		return TotalPrice;
	}


	public void setTotalPrice(double totalPrice) {
		TotalPrice = totalPrice;
	}


	public String getUnitofMeasure() {
		return UnitofMeasure;
	}


	public void setUnitofMeasure(String unitofMeasure) {
		UnitofMeasure = unitofMeasure;
	}


	public double getUnitPrice() {
		return UnitPrice;
	}


	public void setUnitPrice(double unitPrice) {
		UnitPrice = unitPrice;
	}


	public ArrayList<InvoiceAllocation> getAllocations() {
		return Allocations;
	}


	public void setAllocations(ArrayList<InvoiceAllocation> allocations) {
		Allocations = allocations;
	}
	

}
