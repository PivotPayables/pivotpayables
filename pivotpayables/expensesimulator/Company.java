package com.pivotpayables.expensesimulator;

/**
 * @author John Toman
 * 6/21/2015
 * This is the base class for a Company.
 * 
 * Add doctoCompany method
 * 
 * Make properties into fields by making them private
 *
 */
import static java.lang.System.out;


import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.pivotpayables.concurplatform.Location;





public class Company {

	private String ID;
	private String LegalName;//the company's legal name
	private String DBAName;//the name it uses for Doing Business As
	private String Domain;//the domain used in its employee's Concur, Login ID
	private String Industry;//its industry
	private Location HQ;//Location of where the company's HQ is located
	private String PostingCurrency;//the currency the company's account system uses when posting transactions
	
	private static BasicDBObject Doc = new BasicDBObject();

	public Company () {}; // no args contructor
	
	public void display () {//method to display the company in the console

	       
		out.println("ID: " + ID);
		out.println("Legal Name: " + LegalName);
		out.println("DBA Name: " + DBAName);
		out.println("Domain: " + Domain);
		out.println("Industry: " + Industry);
		out.println("Posting Currency: " + PostingCurrency);
		if (HQ != null){
			out.println("HQ Location: ");
			HQ.display();
		}

	}

	public BasicDBObject getDocument () {//get the Company document for this Company object
		
		BasicDBObject myCompany = new BasicDBObject("LegalName",this.LegalName);//initialize a BasicDBObject to become the Company document
		myCompany.append("DBAName",this.DBAName);
		myCompany.append("Domain", this.Domain);
		myCompany.append("Industry", this.Industry);
		Doc = this.HQ.getDocument();
		myCompany.append("HQ", Doc);
		myCompany.append("PostingCurrency", this.PostingCurrency);
		myCompany.append("ID", this.ID);
		return myCompany;
	}			
	public Company doctoCompany (DBObject doc) {//converts the specified Company document into a Company object
		Company myCompany = new Company();
		String LegalName = doc.get("LegalName").toString();
		myCompany.setLegalName(LegalName);
		String DBAName = doc.get("DBAName").toString();
		myCompany.setDBAName(DBAName);
		String Domain = doc.get("Domain").toString();
		myCompany.setDomain(Domain);
		String ID = doc.get("ID").toString();
		myCompany.setID(ID);
		String Industry = doc.get("Industry").toString();
		myCompany.setIndustry(Industry);
		if (doc.get("Location")!=null) {
			Doc = (BasicDBObject) doc.get("Location");// get the Location document for this expense
			Location location = new Location();
			location = location.doctoLocation(Doc);// convert the Location document into a Location object
			myCompany.setHQ(location);
		}// if Location document
		String PostingCurrency = doc.get("PostingCurrency").toString();
		myCompany.setPostingCurrency(PostingCurrency);
		
		return myCompany;

	}
		
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getLegalName() {
		return LegalName;
	}
	public void setLegalName(String legalName) {
		LegalName = legalName;
	}
	public String getDBAName() {
		return DBAName;
	}
	public void setDBAName(String dBAName) {
		DBAName = dBAName;
	}
	public String getDomain() {
		return Domain;
	}
	public void setDomain(String domain) {
		Domain = domain;
	}
	public String getIndustry() {
		return Industry;
	}
	public void setIndustry(String industry) {
		Industry = industry;
	}
	
	public String getPostingCurrency() {
		return PostingCurrency;
	}
	public void setPostingCurrency(String postingCurrency) {
		PostingCurrency = postingCurrency;
	}

	public Location getHQ() {
		return HQ;
	}

	public void setHQ(Location hQ) {
		HQ = hQ;
	}
	

}
