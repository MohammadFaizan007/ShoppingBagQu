package com.shoppingbag.model.response.bus_response.responsepojo;

import com.google.gson.annotations.SerializedName;

public class TransportDetails{

	@SerializedName("TransportName")
	private String transportName;

	@SerializedName("EmailId")
	private String emailId;

	@SerializedName("Address2")
	private String address2;

	@SerializedName("CityNamePinCode")
	private String cityNamePinCode;

	@SerializedName("Address3")
	private String address3;

	@SerializedName("ContactNumber")
	private String contactNumber;

	@SerializedName("Website")
	private String website;

	@SerializedName("Address1")
	private String address1;

	@SerializedName("Fax")
	private String fax;

	public void setTransportName(String transportName){
		this.transportName = transportName;
	}

	public String getTransportName(){
		return transportName;
	}

	public void setEmailId(String emailId){
		this.emailId = emailId;
	}

	public String getEmailId(){
		return emailId;
	}

	public void setAddress2(String address2){
		this.address2 = address2;
	}

	public String getAddress2(){
		return address2;
	}

	public void setCityNamePinCode(String cityNamePinCode){
		this.cityNamePinCode = cityNamePinCode;
	}

	public String getCityNamePinCode(){
		return cityNamePinCode;
	}

	public void setAddress3(String address3){
		this.address3 = address3;
	}

	public String getAddress3(){
		return address3;
	}

	public void setContactNumber(String contactNumber){
		this.contactNumber = contactNumber;
	}

	public String getContactNumber(){
		return contactNumber;
	}

	public void setWebsite(String website){
		this.website = website;
	}

	public String getWebsite(){
		return website;
	}

	public void setAddress1(String address1){
		this.address1 = address1;
	}

	public String getAddress1(){
		return address1;
	}

	public void setFax(String fax){
		this.fax = fax;
	}

	public String getFax(){
		return fax;
	}

	@Override
 	public String toString(){
		return 
			"TransportDetails{" + 
			"transportName = '" + transportName + '\'' + 
			",emailId = '" + emailId + '\'' + 
			",address2 = '" + address2 + '\'' + 
			",cityNamePinCode = '" + cityNamePinCode + '\'' + 
			",address3 = '" + address3 + '\'' + 
			",contactNumber = '" + contactNumber + '\'' + 
			",website = '" + website + '\'' + 
			",address1 = '" + address1 + '\'' + 
			",fax = '" + fax + '\'' + 
			"}";
		}
}