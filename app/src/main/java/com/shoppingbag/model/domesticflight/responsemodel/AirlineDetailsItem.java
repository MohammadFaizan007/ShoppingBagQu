package com.shoppingbag.model.domesticflight.responsemodel;

import com.google.gson.annotations.SerializedName;

public class AirlineDetailsItem{

	@SerializedName("AirlineCode")
	private String airlineCode;

	@SerializedName("FaxNumber")
	private String faxNumber;

	@SerializedName("Address2")
	private String address2;

	@SerializedName("AirlineName")
	private String airlineName;

	@SerializedName("ContactNumber")
	private String contactNumber;

	@SerializedName("Address1")
	private String address1;

	@SerializedName("City")
	private String city;

	@SerializedName("EMailId")
	private String eMailId;

	@SerializedName("AirlinePNR")
	private String airlinePNR;

	public void setAirlineCode(String airlineCode){
		this.airlineCode = airlineCode;
	}

	public String getAirlineCode(){
		return airlineCode;
	}

	public void setFaxNumber(String faxNumber){
		this.faxNumber = faxNumber;
	}

	public String getFaxNumber(){
		return faxNumber;
	}

	public void setAddress2(String address2){
		this.address2 = address2;
	}

	public String getAddress2(){
		return address2;
	}

	public void setAirlineName(String airlineName){
		this.airlineName = airlineName;
	}

	public String getAirlineName(){
		return airlineName;
	}

	public void setContactNumber(String contactNumber){
		this.contactNumber = contactNumber;
	}

	public String getContactNumber(){
		return contactNumber;
	}

	public void setAddress1(String address1){
		this.address1 = address1;
	}

	public String getAddress1(){
		return address1;
	}

	public void setCity(String city){
		this.city = city;
	}

	public String getCity(){
		return city;
	}

	public void setEMailId(String eMailId){
		this.eMailId = eMailId;
	}

	public String getEMailId(){
		return eMailId;
	}

	public void setAirlinePNR(String airlinePNR){
		this.airlinePNR = airlinePNR;
	}

	public String getAirlinePNR(){
		return airlinePNR;
	}
}