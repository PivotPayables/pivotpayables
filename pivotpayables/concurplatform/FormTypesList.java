/**
 * 
 */
package com.pivotpayables.concurplatform;

/**
 * @author John Toman
 * 5/1/2015
 * Base class for a FormsList resource
 *
 */
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;

@XmlRootElement(name="FormTypesList")
@XmlAccessorType (XmlAccessType.FIELD)
public class FormTypesList {
	@XmlElement(name = "FormType")
    private List<FormType> forms = null;

	public List<FormType> getForms() {
		return forms;
	}

	public void setForms(List<FormType> forms) {
		this.forms = forms;
	}

}
