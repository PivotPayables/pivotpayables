/**
 * 
 */
package com.pivotpayables.concurplatform;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author TranseoTech
 *
 */
@XmlRootElement(name = "NextPage")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonIgnoreProperties(ignoreUnknown = true)
public class NextPageURI {
	@XmlElement
	@JsonProperty
	public String NextPage;

	public NextPageURI () {
		
	}
}
