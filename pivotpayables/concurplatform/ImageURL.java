
package com.pivotpayables.concurplatform;

/**
 * @author John Toman
 * 3/22/2015
 * Base class for a ConcurUser
 *
 */
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import static java.lang.System.out;

@XmlRootElement(name = "Image")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImageURL {

	//@XmlElement
    private String Id;
	//@XmlElement
    private String Url;


	public ImageURL(){}// no args constructor
	public void display() {
		if (Id != null) {
			out.println("Image ID: " + Id);
		}
		if (Url != null) {
			out.println("Image URL: " + Url);
		}
	}
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getUrl() {
		return Url;
	}
	public void setUrl(String url) {
		Url = url;
	}
	

}
