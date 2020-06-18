package com.shoppingbag.model.response.bus_response.busTransactionStatus;

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

	public String getTransportName(){
		return transportName;
	}

	public String getEmailId(){
		return emailId;
	}

	public String getAddress2(){
		return address2;
	}

	public String getCityNamePinCode(){
		return cityNamePinCode;
	}

	public String getAddress3(){
		return address3;
	}

	public String getContactNumber(){
		return contactNumber;
	}

	public String getWebsite(){
		return website;
	}

	public String getAddress1(){
		return address1;
	}

	public String getFax(){
		return fax;
	}
}