package com.pivotpayables.concurplatform;
/**
* @author John Toman
* 8/6/2016
* This is the base class for the invoice, LocalizedData resource
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

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocalizedData {
	
	@XmlElement
	@JsonProperty
	private String Code;// the  Code for this data
	
	@XmlElement
	@JsonProperty
	private String LocalizedValue;// the  localized value for this data

	@XmlElement
	@JsonProperty
	private String LangCode;// the  language code for this data
	
	 public LocalizedData(){}// no args constructor

	public String getCode() {
		return Code;
	}
	
	public void display(){
		out.println("Code: "+ Code);
		out.println("Localized Value: "+ LocalizedValue);
		out.println("Language Code: "+ LangCode);
	}

	public void setCode(String code) {
		Code = code;
	}

	public String getLocalizedValue() {
		return LocalizedValue;
	}

	public void setLocalizedValue(String localizedValue) {
		LocalizedValue = localizedValue;
	}

	public String getLangCode() {
		return LangCode;
	}

	public void setLangCode(String langCode) {
		LangCode = langCode;
	}
	
	

}
