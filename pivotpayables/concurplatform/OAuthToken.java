package com.pivotpayables.concurplatform;
/*
**
* @author John Toman
* 6/21/2016
* 
* This is the base class for a Concur OAuth Token
* 
* Moved doctoToken
* 
*/


import static java.lang.System.out;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "Access_Token")
public class OAuthToken {
	private String ID;// the GUID for the token
	@XmlElement(name = "Instance_Url")
	private String InstanceURL;// the instance that issued the token.  This is the Concur Data Center that houses its data.
	@XmlElement(name = "Token")
	private String Token;// OAuth access token
	@XmlElement(name = "Expiration_date")
	private String strExpirationDate;
	@XmlElement(name = "Refresh_Token")
	private String RefreshToken;// the refresh token
	
	// these are fields not in the Concur, GET Token response
	private String EmployeeID;// the GUID for the Employee object in the Pivot Payables MongoDB or MySQL database for the Concur User this token belongs
	private String CompanyID;// the GUID for the Company object in the Pivot Payables MongoDB or MySQL database for the Concur User this token belongs
	Date ExpirationDate;// when the token expires
	
	public OAuthToken () {}// no-arg constructor, required by Jackson


	public void display () {//method to display the token in the console
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss a");
		String strDate = null;
		out.println("ID: " + ID);
		out.println("InstanceURL: " + InstanceURL);
		out.println("Token: " + Token);
		strDate = sdf.format(ExpirationDate);
		out.println("Expiration Date: " + strDate);
		out.println("Refresh Token: " + RefreshToken);
		out.println("EmployeeID: " + EmployeeID);
		out.println("CompanyID: " + CompanyID);
		
	}

	public BasicDBObject getDocument () {//get the OAuthToken document for this OAuthToken object
		BasicDBObject myDocument = new BasicDBObject("ID", this.ID);//initialize a BasicDBObject to become the Company document
		myDocument.append("InstanceURL",this.InstanceURL);
		myDocument.append("Token",this.Token);
		myDocument.append("Refresh_Token", this.RefreshToken);
		myDocument.append("EmployeeID", this.EmployeeID);
		myDocument.append("CompanyID", this.CompanyID);
		myDocument.append("Expiration_date", this.ExpirationDate);

		return myDocument;
	}
	public OAuthToken doctoToken (DBObject doc) throws ParseException {
		String ID= "";
		String InstanceURL = "";
		String Token = doc.get("Token").toString();
		Date ExpirationDate = null;
		String RefreshToken = "";
		String EmployeeID = "";
		String CompanyID = "";
		
		if (doc.get("ID") != null) {
			ID= doc.get("ID").toString();
		}
		if (doc.get("InstanceURL") != null) {
			InstanceURL = doc.get("InstanceURL").toString();
		}
		if (doc.get("Expiration_date") != null) {
			 ExpirationDate = (Date)doc.get("Expiration_date");
		}
		if (doc.get("Refresh_Token") != null) {
			RefreshToken = doc.get("Refresh_Token").toString();
		}
		if (doc.get("EmployeeID") != null) {
			EmployeeID = doc.get("EmployeeID").toString();
		}
		if (doc.get("CompanyID") != null) {
			CompanyID = doc.get("CompanyID").toString();
		}
		

		OAuthToken myToken = new OAuthToken();
		myToken.setID(ID);
		myToken.setInstanceURL(InstanceURL);
		myToken.setToken(Token);
		myToken.setExpirationDate(ExpirationDate);
		myToken.setRefreshToken(RefreshToken);
		myToken.setEmployeeID(EmployeeID);
		myToken.setCompanyID(CompanyID);

		return myToken;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getInstanceURL() {
		return InstanceURL;
	}
	public void setInstanceURL(String instanceURL) {
		InstanceURL = instanceURL;
	}
	public String getToken() {
		return Token;
	}
	public void setToken(String token) {
		Token = token;
	}
	
	public String getstrExpirationDate() {
		return strExpirationDate;
	}
	public void setstrExpirationDatee(String stringDate) {
		this.strExpirationDate = stringDate;
	}
	public Date getExpirationDate() {
		return ExpirationDate;
	}
	public void setExpirationDate(Date date) {
		ExpirationDate = date;
	}
	public String getRefreshToken() {
		return RefreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		RefreshToken = refreshToken;
	}
	public String getEmployeeID() {
		return EmployeeID;
	}
	public void setEmployeeID(String employeeID) {
		EmployeeID = employeeID;
	}



	public String getCompanyID() {
		return CompanyID;
	}
	public void setCompanyID(String companyID) {
		CompanyID = companyID;
	}
	
	
}
