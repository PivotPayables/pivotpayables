package com.pivotpayables.concurplatform;
/**
 * @author John Toman
 * 1/22/2016
 * Base class for payment request digest
 *
 */


import static java.lang.System.out;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.xml.bind.annotation.*;

import org.codehaus.jackson.annotate.*;
@XmlRootElement(name = "Reports")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentRequest {
	@XmlElement
	@JsonProperty
	private String ID;//	The unique identifier of the resource.
	@XmlElement
	@JsonProperty
	private String ApprovalStatusCode;// The approval status code for the request.

	@XmlElement
	@JsonProperty
	private String CurrencyCode;//	The 3-letter ISO 4217 currency code for the payment request currency. Examples: USD - US dollars; BRL - Brazilian real; CAD - Canadian dollar; CHF - Swiss franc; EUR - Euro; GBO - Pound sterling; HKD - Hong Kong dollar; INR - Indian rupee; MXN - Mexican peso; NOK - Norwegian krone; SEK - Swedish krona.
	@XmlElement
	@JsonProperty
	private String InvoiceNumber;//	The name of the payment request ledger. Maximum 20 characters.
	@XmlElement
	@JsonProperty
	private Boolean IsDeleted;//	If Y, then this payment request was soft deleted. Format: Y/N
	@XmlElement
	@JsonProperty
	private String OwnerLoginID;//	The Login ID of the user this request belongs to.
	@XmlElement
	@JsonProperty
	private String PurchaseOrderNumber;//		The name of the payment request owner.
	@XmlElement
	@JsonProperty
	private String PaymentStatusCode;//	The code for the payment status of the request.
	@XmlElement
	@JsonProperty
	private Double Total;// 		The total amount of the request.
	@XmlElement
	@JsonProperty
	private String URI;//			The URI to the resource.
	@XmlElement
	@JsonProperty
	private String VendorName;//	The URL to post a workflow action to the request using the Post Report Workflow Action function.
	
	public PaymentRequest () {// no arg constructor
		
	}



	public void display () {
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
		Locale locale  = new Locale("en", "US");
		String pattern = "###.##";

		DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(locale);
		decimalFormat.applyPattern(pattern);
		
		if (ID != null) {
			out.println("ID: " + ID);
		} 
		if (URI != null) {
			out.println("URI: " + URI);
		}

		if (OwnerLoginID != null) {
			out.println("Owner Login ID: " + OwnerLoginID);
		}
		if (VendorName != null) {
			out.println("Vendor Name: " + VendorName);
		}
		if (InvoiceNumber != null) {
			out.println("Invoice Number: " + InvoiceNumber);
		}
		if (PurchaseOrderNumber != null) {
			out.println("Purchase Order Number: " + PurchaseOrderNumber);
		}
		out.println("___________________________________________");
		out.println();
	
		
		out.println("WORKFLOW");

		if (ApprovalStatusCode != null) {
			out.println("Approval Status Code: " + ApprovalStatusCode);
		}
		if (PaymentStatusCode != null) {
			out.println("Payment Status Code: " + PaymentStatusCode);
		}
		out.println("___________________________________________");
		out.println();
		
		out.println("ACCOUNTING");
		if (Total != null) {
			out.println("Report Total Amount: " + decimalFormat.format(Total) + " " + CurrencyCode);
		}
		out.println("___________________________________________");
		out.println();	
		return;
	}



	public String getID() {
		return ID;
	}



	public void setID(String iD) {
		ID = iD;
	}



	public String getApprovalStatusCode() {
		return ApprovalStatusCode;
	}



	public void setApprovalStatusCode(String approvalStatusCode) {
		ApprovalStatusCode = approvalStatusCode;
	}



	public String getCurrencyCode() {
		return CurrencyCode;
	}



	public void setCurrencyCode(String currencyCode) {
		CurrencyCode = currencyCode;
	}



	public String getInvoiceNumber() {
		return InvoiceNumber;
	}



	public void setInvoiceNumber(String invoiceNumber) {
		InvoiceNumber = invoiceNumber;
	}



	public Boolean getIsDeleted() {
		return IsDeleted;
	}



	public void setIsDeleted(Boolean isDeleted) {
		IsDeleted = isDeleted;
	}



	public String getOwnerLoginID() {
		return OwnerLoginID;
	}



	public void setOwnerLoginID(String ownerLoginID) {
		OwnerLoginID = ownerLoginID;
	}



	public String getPurchaseOrderNumber() {
		return PurchaseOrderNumber;
	}



	public void setPurchaseOrderNumber(String purchaseOrderNumber) {
		PurchaseOrderNumber = purchaseOrderNumber;
	}



	public String getPaymentStatusCode() {
		return PaymentStatusCode;
	}



	public void setPaymentStatusCode(String paymentStatusCode) {
		PaymentStatusCode = paymentStatusCode;
	}



	public Double getTotal() {
		return Total;
	}



	public void setTotal(Double total) {
		Total = total;
	}



	public String getURI() {
		return URI;
	}



	public void setURI(String uRI) {
		URI = uRI;
	}



	public String getVendorName() {
		return VendorName;
	}



	public void setVendorName(String vendorName) {
		VendorName = vendorName;
	}



	
	

}
