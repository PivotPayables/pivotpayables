package com.pivotpayables.concurplatform;
/**
 * @author John Toman
 * 2/18/2015
 * Base class for an expense report.
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

/**
 * @author John Toman
 * 3/9/15
 * Base class for an ExpenseReport
 *
 */
@XmlRootElement(name = "Reports")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExpenseReport {
	@XmlElement
	@JsonProperty
	private String ID;//	The unique identifier of the resource.
	@XmlElement
	@JsonProperty
	private Double AmountDueCompanyCard;//The total amount due to the company card for the report. Maximum 23 characters.
	@XmlElement
	@JsonProperty
	private Double AmountDueEmployee;// The total amount due to the employee for the report. Maximum 23 characters.
	@XmlElement
	@JsonProperty
	private String ApprovalStatusCode;// The approval status code for the report.
	@XmlElement
	@JsonProperty
	private String ApprovalStatusName;// The report's approval status, in the OAuth consumer's language.
	@XmlElement
	@JsonProperty
	private String ApproverLoginID;// The Login ID of the report owner's expense approver.
	@XmlElement
	@JsonProperty
	private String ApproverName;//	The name of the report owner's expense approver.
	@XmlElement
	@JsonProperty
	private String Country;//		The report country. Maximum 2 characters. Format: The ISO 3166-1 alpha-2 country code. Example: United States is US.
	@XmlElement
	@JsonProperty
	private String CountrySubdivision;//	The report country subdivision. Format: ISO 3166-2:2007 country subdivision.
	@XmlElement
	@JsonProperty
	private Date CreateDate;//		The date the report was created.
	@XmlElement
	@JsonProperty
	private String CurrencyCode;//	The 3-letter ISO 4217 currency code for the expense report currency. Examples: USD - US dollars; BRL - Brazilian real; CAD - Canadian dollar; CHF - Swiss franc; EUR - Euro; GBO - Pound sterling; HKD - Hong Kong dollar; INR - Indian rupee; MXN - Mexican peso; NOK - Norwegian krone; SEK - Swedish krona.
	@XmlElement
	@JsonProperty
	private CustomField	Custom1;//	The details from the Custom fields. These may not have data, depending on configuration.
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
	private CustomField	Custom2;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private CustomField	Custom20;//	The details from the Custom fields. These may not have data, depending on configuration.
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
	private Boolean EverSentBack;//	Whether the report has ever been sent back to the employee. Format: Y/N
	@XmlElement
	@JsonProperty
	private Boolean HasException;//	Whether the report has exceptions. Format: Y/N
	@XmlElement
	@JsonProperty
	private String LastComment;//	The text of the most recent comment on the report. Used only with GET
	@JsonProperty
	private String Comment;//	The text of a comment to add to the report. Used only with PUT/POST
	@XmlElement
	@JsonProperty
	private Date LastModifiedDate;// The date the report header was last modified.
	@XmlElement
	@JsonProperty
	private String LedgerName;//	The name of the expense report ledger. Maximum 20 characters.
	@XmlElement
	@JsonProperty
	private String Name;//			The name of the report.
	@XmlElement
	@JsonProperty
	private String Purpose;
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
	@XmlElement
	@JsonProperty
	private String OwnerLoginID;//	The Login ID of the user this report belongs to.
	@XmlElement
	@JsonProperty
	private String OwnerName;//		The name of the expense report owner.
	@XmlElement
	@JsonProperty
	private Date PaidDate;//		The date when all journal entries in the report was integrated with or extracted to the financial system.
	@XmlElement
	@JsonProperty
	private String PaymentStatusCode;//	The code for the payment status of the report.
	@XmlElement
	@JsonProperty
	private String PaymentStatusName;//	The report's payment status, in the OAuth consumer's language.
	@XmlElement
	@JsonProperty
	private Double PersonalAmount;// The total amount of expenses marked as personal. Maximum 23 characters.
	@XmlElement
	@JsonProperty
	private String PolicyID;//		The unique identifier of the policy that applies to this report. Maximum 64 characters.
	@XmlElement
	@JsonProperty
	private Date ProcessingPaymentDate;//	The date that the report completed all approvals and was ready to be extracted for payment.
	@XmlElement
	@JsonProperty
	private Boolean ReceiptsReceived;//	If Y, then this report has its receipt receipt confirmed by the Expense Processor. Format: Y/N
	@XmlElement
	@JsonProperty
	private Date SubmitDate;//		The date the report was submitted.
	@XmlElement
	@JsonProperty
	private Double Total;// 		The total amount of the report.
	@XmlElement
	@JsonProperty
	private Double TotalApprovedAmount;// The total amount of approved expenses in the report. Maximum 23 characters.
	@XmlElement
	@JsonProperty
	Double TotalClaimedAmount;// The total amount of all non-personal expenses in the report. Maximum 23 characters.
	@XmlElement
	@JsonProperty
	private String URI;//			The URI to the resource.
	@XmlElement
	@JsonProperty
	private Date UserDefinedDate;//	The date of the report assigned by the user.
	@XmlElement
	@JsonProperty
	private String WorkflowActionUrl;//	The URL to post a workflow action to the report using the Post Report Workflow Action function.
	
	public ExpenseReport () {// no arg constructor
		
	}
	public ExpenseReport (String name) {// constructor, to use when creating an expense report
		this.Name = name;



	}

	@XmlElement
	@JsonProperty
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public Double getAmountDueCompanyCard() {
		return AmountDueCompanyCard;
	}
	public void setAmountDueCompanyCard(Double amountDueCompanyCard) {
		AmountDueCompanyCard = amountDueCompanyCard;
	}
	public Double getAmountDueEmployee() {
		return AmountDueEmployee;
	}
	public void setAmountDueEmployee(Double amountDueEmployee) {
		AmountDueEmployee = amountDueEmployee;
	}
	public String getApprovalStatusCode() {
		return ApprovalStatusCode;
	}
	public void setApprovalStatusCode(String approvalStatusCode) {
		ApprovalStatusCode = approvalStatusCode;
	}
	public String getApprovalStatusName() {
		return ApprovalStatusName;
	}
	public void setApprovalStatusName(String approvalStatusName) {
		ApprovalStatusName = approvalStatusName;
	}
	public String getApproverLoginID() {
		return ApproverLoginID;
	}
	public void setApproverLoginID(String approverLoginID) {
		ApproverLoginID = approverLoginID;
	}
	public String getApproverName() {
		return ApproverName;
	}
	public void setApproverName(String approverName) {
		ApproverName = approverName;
	}
	public String getCountry() {
		return Country;
	}
	public void setCountry(String country) {
		Country = country;
	}
	public String getCountrySubdivision() {
		return CountrySubdivision;
	}
	public void setCountrySubdivision(String countrySubdivision) {
		CountrySubdivision = countrySubdivision;
	}
	public Date getCreateDate() {
		return CreateDate;
	}
	public void setCreateDate(Date createDate) {
		CreateDate = createDate;
	}
	public String getCurrencyCode() {
		return CurrencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		CurrencyCode = currencyCode;
	}
	public CustomField getCustom1() {
		return Custom1;
	}
	public void setCustom1(CustomField custom1) {
		Custom1 = custom1;
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
	public CustomField getCustom2() {
		return Custom2;
	}
	public void setCustom2(CustomField custom2) {
		Custom2 = custom2;
	}
	public CustomField getCustom20() {
		return Custom20;
	}
	public void setCustom20(CustomField custom20) {
		Custom20 = custom20;
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
	public Boolean getEverSentBack() {
		return EverSentBack;
	}
	public void setEverSentBack(Boolean everSentBack) {
		EverSentBack = everSentBack;
	}
	public Boolean getHasException() {
		return HasException;
	}
	public void setHasException(Boolean hasException) {
		HasException = hasException;
	}
	public String getLastComment() {
		return LastComment;
	}
	public void setLastComment(String lastComment) {
		LastComment = lastComment;
	}
	public String getComment() {
		return Comment;
	}
	public void setComment(String comment) {
		Comment = comment;
	}
	public Date getLastModifiedDate() {
		return LastModifiedDate;
	}
	public void setLastModifiedDate(Date lastModifiedDate) {
		LastModifiedDate = lastModifiedDate;
	}
	public String getLedgerName() {
		return LedgerName;
	}
	public void setLedgerName(String ledgerName) {
		LedgerName = ledgerName;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public CustomField getOrgUnit1() {
		return OrgUnit1;
	}
	public String getPurpose() {
		return Purpose;
	}
	public void setPurpose(String purpose) {
		Purpose = purpose;
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
	public String getOwnerLoginID() {
		return OwnerLoginID;
	}
	public void setOwnerLoginID(String ownerLoginID) {
		OwnerLoginID = ownerLoginID;
	}
	public String getOwnerName() {
		return OwnerName;
	}
	public void setOwnerName(String ownerName) {
		OwnerName = ownerName;
	}
	public Date getPaidDate() {
		return PaidDate;
	}
	public void setPaidDate(Date paidDate) {
		PaidDate = paidDate;
	}
	public String getPaymentStatusCode() {
		return PaymentStatusCode;
	}
	public void setPaymentStatusCode(String paymentStatusCode) {
		PaymentStatusCode = paymentStatusCode;
	}
	public String getPaymentStatusName() {
		return PaymentStatusName;
	}
	public void setPaymentStatusName(String paymentStatusName) {
		PaymentStatusName = paymentStatusName;
	}
	public Double getPersonalAmount() {
		return PersonalAmount;
	}
	public void setPersonalAmount(Double personalAmount) {
		PersonalAmount = personalAmount;
	}
	public String getPolicyID() {
		return PolicyID;
	}
	public void setPolicyID(String policyID) {
		PolicyID = policyID;
	}
	public Date getProcessingPaymentDate() {
		return ProcessingPaymentDate;
	}
	public void setProcessingPaymentDate(Date processingPaymentDate) {
		ProcessingPaymentDate = processingPaymentDate;
	}
	public Boolean getReceiptsReceived() {
		return ReceiptsReceived;
	}
	public void setReceiptsReceived(Boolean receiptsReceived) {
		ReceiptsReceived = receiptsReceived;
	}
	public Date getSubmitDate() {
		return SubmitDate;
	}
	public void setSubmitDate(Date submitDate) {
		SubmitDate = submitDate;
	}
	public Double getTotal() {
		return Total;
	}
	public void setTotal(Double total) {
		Total = total;
	}
	public Double getTotalApprovedAmount() {
		return TotalApprovedAmount;
	}
	public void setTotalApprovedAmount(Double totalApprovedAmount) {
		TotalApprovedAmount = totalApprovedAmount;
	}
	public Double getTotalClaimedAmount() {
		return TotalClaimedAmount;
	}
	public void setTotalClaimedAmount(Double totalClaimedAmount) {
		TotalClaimedAmount = totalClaimedAmount;
	}
	public String getURI() {
		return URI;
	}
	public void setURI(String uRI) {
		URI = uRI;
	}
	public Date getUserDefinedDate() {
		return UserDefinedDate;
	}
	public void setUserDefinedDate(Date userDefinedDate) {
		UserDefinedDate = userDefinedDate;
	}
	public String getWorkflowActionUrl() {
		return WorkflowActionUrl;
	}
	public void setWorkflowActionUrl(String workflowActionUrl) {
		WorkflowActionUrl = workflowActionUrl;
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
		
		if (OwnerName != null) {
			out.println("Owner Name: " + OwnerName);
		}
		if (Name != null) {
			out.println("Report Name: " + Name);
		} 
		out.println("___________________________________________");
		out.println();
		
		out.println("TIMELINE");
		String strDate;// placeholder for a String date
		if (UserDefinedDate != null) {
			strDate = sdf.format(UserDefinedDate);
			out.println("Report Date: " + strDate);
		}
		if (CreateDate != null) {
			strDate = sdf.format(CreateDate);
			out.println("Create Date: " + strDate);
		}
		if (SubmitDate != null) {
			strDate = sdf.format(SubmitDate);	 
			out.println("Submit Date: " + strDate);
		}
		if (ProcessingPaymentDate != null) {
			strDate = sdf.format(ProcessingPaymentDate);	 
			out.println("Processing Payment Date: " + strDate);
		}
		if (PaidDate != null) {
			strDate = sdf.format(PaidDate);
			out.println("Paid Date: " + strDate);
		}
		if (LastModifiedDate != null) {
			strDate = sdf.format(LastModifiedDate);
			out.println("Last Modified Date: " + strDate);
		}
		out.println("___________________________________________");
		out.println();
		
		out.println("WORKFLOW");
		if (ApprovalStatusName != null) {
			out.println("Approval Status Name: " + ApprovalStatusName);
		}
		if (ApprovalStatusCode != null) {
			out.println("Approval Status Code: " + ApprovalStatusCode);
		}
		if (PaymentStatusCode != null) {
			out.println("Payment Status Code: " + PaymentStatusCode);
		}
		if (PaymentStatusName != null) {
			out.println("Payment Status Name: " + PaymentStatusName);

		}
		if (LastComment != null) {
			out.println("Last Comment: " + LastComment);
		if (ApproverLoginID != null) {
			out.println("Approver Login ID: " + ApproverLoginID);
		}
		if (ApproverName != null) {
			out.println("Approver Name: " + ApproverName);
		}
		}
		if (WorkflowActionUrl != null) {
			out.println("Workflow Action Url: " + WorkflowActionUrl);
		}
		if (EverSentBack != null) {
			out.println("Ever Sent Back: " + EverSentBack);
		}
		out.println("___________________________________________");
		out.println();
		
			out.println("ACCOUNTING");
			if (TotalClaimedAmount != null) {
				out.println("Claimed Amount: " + decimalFormat.format(TotalClaimedAmount) + " " + CurrencyCode);
			}
			if (TotalApprovedAmount != null) {
				out.println("Approved Amount: " + decimalFormat.format(TotalApprovedAmount) + " " + CurrencyCode);
			}
			if (PersonalAmount != null) {
				out.println("Personal Amount: " + decimalFormat.format(PersonalAmount) + " " + CurrencyCode);
			}
			if (Total != null) {
				out.println("Report Total Amount: " + decimalFormat.format(Total) + " " + CurrencyCode);
			}
			out.println("___________________________________________");
			out.println();

			if (AmountDueCompanyCard != null) {
				out.println("Amount Due Company Card: " + decimalFormat.format(AmountDueCompanyCard) + " " + CurrencyCode);
			}
			if (AmountDueEmployee != null) {
				out.println("Amount Due Employee: " + decimalFormat.format(AmountDueEmployee) + " " + CurrencyCode);
			}
			out.println("___________________________________________");
			out.println();
			if (LedgerName != null) {
				out.println("Ledger Name: " + LedgerName);
			}
			out.println("___________________________________________");
			out.println();


			out.println("TAX LOCATION");
			if ( Country != null) {
				out.println(" Report Country: " + Country);
			}
			if (CountrySubdivision != null) {
				out.println("Report State/Region: " + CountrySubdivision);
			}
			out.println("___________________________________________");
			out.println();
			
			out.println("OTHER DATA");
			if (PolicyID != null) {
				out.println("Policy ID: " + PolicyID);
			}
			if (Name != null) {
				out.println("Report Name: " + Name);
			} 
			if (Purpose != null) {
				out.println("Purpose: " + Purpose);
			}

			if (HasException != null) {
				out.println("Has Exception: " + HasException);
			}
			out.println("___________________________________________");
			out.println();
			
			out.println("CUSTOM FIELDS");
			if (Custom1 != null) {
				Custom1.display();
			}
			if (Custom2 != null) {
				Custom2.display();
			}
			if (Custom2 != null) {
				Custom3.display();
			}
			if (Custom2 != null) {
				Custom4.display();
			}
			if (Custom5 != null) {
				Custom5.display();
			}
			if (Custom6 != null) {
				Custom6.display();
			}
			if (Custom7 != null) {
				Custom7.display();
			}
			if (Custom7 != null) {
				Custom8.display();
			}
			if (Custom9 != null) {
				Custom9.display();
			}
			if (Custom10 != null) {
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
				OrgUnit6.display();//	The details from the Org Unit fields. These may not have data, depending on configuration.
			}
			out.println("___________________________________________");
			out.println();
			
		return;
	}
	
}