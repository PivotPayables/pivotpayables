
package com.pivotpayables.concurplatform;

/**
 * @author John Toman
 * 3/27/2015
 * This the base class for the List resource.
 *
 */
import static java.lang.System.out;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.*;

import com.mongodb.BasicDBObject;
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@JsonIgnoreProperties(ignoreUnknown = true)
public class List {
	@XmlElement
	@JsonProperty
	private String ID;
	@XmlElement
	@JsonProperty
	private String Name;
	@XmlElement
	@JsonProperty
    private Boolean IsVendorList; 
	@XmlElement
	@JsonProperty
    private Boolean DisplayCodeFirst; 
	@XmlElement
	@JsonProperty
    private String SearchCriteriaCode;
	@XmlElement
	@JsonProperty
    private String ConnectorID;
	@XmlElement
	@JsonProperty
    private Integer ExternalThreshold;
	@XmlElement
	@JsonProperty
    private String URI; 
	
	public List() {};// no args constructor
	
	public void display () {//method to display the List in the console

		out.println("ID: " + ID);
		out.println("Name: " + Name);
		if (IsVendorList != null) {
			out.println("Is Vendor List: " + IsVendorList.toString());
		}
		if (DisplayCodeFirst != null) {
			out.println("DisplayCodeFirst: " + DisplayCodeFirst.toString());
		}
		out.println("SearchCriteriaCode: " + SearchCriteriaCode);
		if (ConnectorID != null) {
			out.println("Connector ID: " + ConnectorID);
		}
		if (ExternalThreshold != null) {
			out.println("External Threshold: " + Integer.toString(ExternalThreshold));
		}
		out.println("URI: " + URI);
		
	}
	public BasicDBObject getDocument () {//this method returns the itemization as a BasicDBObject that can be added to a MongoDB
		BasicDBObject myList = new BasicDBObject("ID",this.ID);
		myList.append("Name",this.Name);
		myList.append("IsVendorList",this.IsVendorList);
		myList.append("DisplayCodeFirst", this.DisplayCodeFirst);
		myList.append("SearchCriteriaCode",this.SearchCriteriaCode);
		if (ConnectorID != null) {
			myList.append("ConnectorID",this.ConnectorID);
		}
		if (ExternalThreshold != null) {
			myList.append("ExternalThreshold",this.ExternalThreshold);
		}
		myList.append("URI",this.URI);
		
		return myList;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public Boolean getIsVendorList() {
		return IsVendorList;
	}

	public void setIsVendorList(Boolean isVendorList) {
		IsVendorList = isVendorList;
	}

	public Boolean getDisplayCodeFirst() {
		return DisplayCodeFirst;
	}

	public void setDisplayCodeFirst(Boolean displayCodeFirst) {
		DisplayCodeFirst = displayCodeFirst;
	}

	public String getSearchCriteriaCode() {
		return SearchCriteriaCode;
	}

	public void setSearchCriteriaCode(String searchCriteriaCode) {
		SearchCriteriaCode = searchCriteriaCode;
	}

	public String getConnectorID() {
		return ConnectorID;
	}

	public void setConnectorID(String connectorID) {
		ConnectorID = connectorID;
	}

	public Integer getExternalThreshold() {
		return ExternalThreshold;
	}

	public void setExternalThreshold(Integer externalThreshold) {
		ExternalThreshold = externalThreshold;
	}

	public String getURI() {
		return URI;
	}

	public void setURI(String uRI) {
		URI = uRI;
	}


}
