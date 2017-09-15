/**
 * 
 */
package com.pivotpayables.concurplatform;

/**
 * @author John Toman
 * 3/27/2015
 * Base clase for the List Item resource
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
public class ListItem {
	@XmlElement
	@JsonProperty
	private String ID;//The unique identifier of the resource.
	@XmlElement
	@JsonProperty
	private String Name;//The name of item. Text maximum 64 characters
	@XmlElement
	@JsonProperty
	private String Level1Code;//The item code for the first level of the list. All lists have at least a Level1Code. Text maximum 32 characters
	@XmlElement
	@JsonProperty
	private String Level2Code;//The item code for the second level of the list. Empty when this level doesn't exist in the list. Text maximum 32 characters
	@XmlElement
	@JsonProperty
	private String Level3Code;//The item code for the third level of the list. Empty when this level doesn't exist in the list. Text maximum 32 characters
	@XmlElement
	@JsonProperty
	private String Level4Code;//The item code for the fourth level of the list. Empty when this level doesn't exist in the list. Text maximum 32 characters
	@XmlElement
	@JsonProperty
	private String Level5Code;//The item code for the fifth level of the list. Empty when this level doesn't exist in the list. Text maximum 32 characters
	@XmlElement
	@JsonProperty
	private String Level6Code;//The item code for the sixth level of the list. Empty when this level doesn't exist in the list. Text maximum 32 characters
	@XmlElement
	@JsonProperty
	private String Level7Code;//The item code for the seventh level of the list. Empty when this level doesn't exist in the list. Text maximum 32 characters
	@XmlElement
	@JsonProperty
	private String Level8Code;//The item code for the eighth level of the list. Empty when this level doesn't exist in the list. Text maximum 32 characters
	@XmlElement
	@JsonProperty
	private String Level9Code;//The item code for the ninth level of the list. Empty when this level doesn't exist in the list. Text maximum 32 characters
	@XmlElement
	@JsonProperty
	private String Level10Code;//The item code for the tenth level of the list. Empty when this level doesn't exist in the list. Text maximum 32 characters
	@XmlElement
	@JsonProperty
	private String ListID;//The unique identifier for the list this item is a member.
	@XmlElement
	@JsonProperty
	private String ParentID;//The unique identifier of this item's parent. Is empty when there is no parent.
	@XmlElement
	@JsonProperty
	private String URI;//The URI to the resource.
	
	public ListItem() {};// no args constructor
	
	public void display () {//method to display the List in the console

		out.println("ID: " + ID);
		out.println("List ID: " + ListID);
		out.println("Name: " + Name);
		String code = Level1Code;
		if (Level2Code != null) {
			code += ("|"+Level2Code);
		}
		if (Level3Code != null) {
			code += ("|"+Level3Code);
		}
		if (Level4Code != null) {
			code += ("|"+Level4Code);
		}
		if (Level5Code != null) {
			code += ("|"+Level5Code);
		}
		if (Level6Code != null) {
			code += ("|"+Level6Code);
		}
		if (Level7Code != null) {
			code += ("|"+Level7Code);
		}
		if (Level8Code != null) {
			code += ("|"+Level8Code);
		}
		if (Level9Code != null) {
			code += ("|"+Level9Code);
		}
		if (Level10Code != null) {
			code += ("|"+Level10Code);
		}
		out.println("Code: " + code);
		out.println("URI: " + URI);
		
	}
	public BasicDBObject getDocument () {//this method returns the itemization as a BasicDBObject that can be added to a MongoDB
		BasicDBObject myListItem = new BasicDBObject("ID",this.ID);
		myListItem.append("Name",this.Name);
		myListItem.append("Level1Code",this.Level1Code);
		if (Level2Code != null) {
			myListItem.append("Level2Code",this.Level2Code);
		}
		if (Level3Code != null) {
			myListItem.append("Level3Code",this.Level3Code);
		}
		if (Level4Code != null) {
			myListItem.append("Level4Code",this.Level4Code);
		}
		if (Level5Code != null) {
			myListItem.append("Level5Code",this.Level5Code);
		}
		if (Level6Code != null) {
			myListItem.append("Level6Code",this.Level6Code);
		}
		if (Level7Code != null) {
			myListItem.append("Level7Code",this.Level7Code);
		}
		if (Level8Code != null) {
			myListItem.append("Level8Code",this.Level8Code);
		}
		if (Level9Code != null) {
			myListItem.append("Level9Code",this.Level9Code);
		}
		if (Level10Code != null) {
			myListItem.append("Level10Code",this.Level10Code);
		}
		
		myListItem.append("URI",this.URI);
		
		return myListItem;
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

	public String getLevel1Code() {
		return Level1Code;
	}

	public void setLevel1Code(String level1Code) {
		Level1Code = level1Code;
	}

	public String getLevel2Code() {
		return Level2Code;
	}

	public void setLevel2Code(String level2Code) {
		Level2Code = level2Code;
	}

	public String getLevel3Code() {
		return Level3Code;
	}

	public void setLevel3Code(String level3Code) {
		Level3Code = level3Code;
	}

	public String getLevel4Code() {
		return Level4Code;
	}

	public void setLevel4Code(String level4Code) {
		Level4Code = level4Code;
	}

	public String getLevel5Code() {
		return Level5Code;
	}

	public void setLevel5Code(String level5Code) {
		Level5Code = level5Code;
	}

	public String getLevel6Code() {
		return Level6Code;
	}

	public void setLevel6Code(String level6Code) {
		Level6Code = level6Code;
	}

	public String getLevel7Code() {
		return Level7Code;
	}

	public void setLevel7Code(String level7Code) {
		Level7Code = level7Code;
	}

	public String getLevel8Code() {
		return Level8Code;
	}

	public void setLevel8Code(String level8Code) {
		Level8Code = level8Code;
	}

	public String getLevel9Code() {
		return Level9Code;
	}

	public void setLevel9Code(String level9Code) {
		Level9Code = level9Code;
	}

	public String getLevel10Code() {
		return Level10Code;
	}

	public void setLevel10Code(String level10Code) {
		Level10Code = level10Code;
	}

	public String getListID() {
		return ListID;
	}

	public void setListID(String listID) {
		ListID = listID;
	}

	public String getParentID() {
		return ParentID;
	}

	public void setParentID(String parentID) {
		ParentID = parentID;
	}

	public String getURI() {
		return URI;
	}

	public void setURI(String uRI) {
		URI = uRI;
	}
	

}
