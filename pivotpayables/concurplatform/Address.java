package com.pivotpayables.concurplatform;
/**
 * @author John Toman
 * 2/5/2016
 * base class for an Address
 *
 */

import static java.lang.System.out;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Address {
	
	@JsonProperty
	String Name;// addressee's name
	@JsonProperty
	String Address1;// street address line 1
	@JsonProperty
	String Address2;// street address line 2
	@JsonProperty
	String Address3;// street address line 3
	@JsonProperty
	String City;
	@JsonProperty
	String State;
	@JsonProperty
	String CountryCode;
	@JsonProperty
	String PostalCode;
	public Address() {};// no args constructor required by Jackson

	public void display () {
		if (Name != null) {
			out.println("Name: " + Name);
		}
		if (Address1 != null) {
			out.println("Address 1: " + Address1);
		}
		if (Address2 != null) {
			out.println("Address 2: " + Address2);
		}
		if (Address3 != null) {
			out.println("Address 3: " + Address3);
		}
		if (City != null) {
			out.println("City: " + City);
		}
		if (State != null) {
			out.println("State: " + State);
		}
		if (CountryCode != null) {
			out.println("Country Code: " + CountryCode);
		}
		if (PostalCode != null) {
			out.println("Postal Code: " + PostalCode);
		}	
		
	}

	public String getAddress1() {
		return Address1;
	}

	public void setAddress1(String address1) {
		Address1 = address1;
	}

	public String getAddress2() {
		return Address2;
	}

	public void setAddress2(String address2) {
		Address2 = address2;
	}

	public String getAddress3() {
		return Address3;
	}

	public void setAddress3(String address3) {
		Address3 = address3;
	}

	public String getCity() {
		return City;
	}

	public void setCity(String city) {
		City = city;
	}

	public String getState() {
		return State;
	}

	public void setState(String state) {
		State = state;
	}

	public String getCountryCode() {
		return CountryCode;
	}

	public void setCountryCode(String countryCode) {
		CountryCode = countryCode;
	}

	public String getPostalCode() {
		return PostalCode;
	}

	public void setPostalCode(String postalCode) {
		PostalCode = postalCode;
	}
	

}
