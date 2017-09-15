/**
 * 
 */
package com.pivotpayables.concurplatform;

/**
 * @author John Toman
 * 5/1/2015
 * The base class for the Form Data List resource
 *
 */
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;

@XmlRootElement(name="FormDataList")
@XmlAccessorType (XmlAccessType.FIELD)
public class FormDataList {
	@XmlElement(name = "FormData")
    private List<FormData> forms = null;

	public List<FormData> getForms() {
		return forms;
	}

	public void setForms(List<FormData> forms) {
		this.forms = forms;
	}

}
