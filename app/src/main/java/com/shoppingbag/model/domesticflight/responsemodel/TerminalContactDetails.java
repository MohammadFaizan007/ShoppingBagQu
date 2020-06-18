package com.shoppingbag.model.domesticflight.responsemodel;

import com.google.gson.annotations.SerializedName;

public class TerminalContactDetails{

	@SerializedName("EmailId")
	private String emailId;

	@SerializedName("TerminalName")
	private String terminalName;

	@SerializedName("State")
	private String state;

	@SerializedName("Address2")
	private String address2;

	@SerializedName("ContactNumber")
	private String contactNumber;

	@SerializedName("Country")
	private String country;

	@SerializedName("Address1")
	private String address1;

	@SerializedName("City")
	private String city;

	public void setEmailId(String emailId){
		this.emailId = emailId;
	}

	public String getEmailId(){
		return emailId;
	}

	public void setTerminalName(String terminalName){
		this.terminalName = terminalName;
	}

	public String getTerminalName(){
		return terminalName;
	}

	public void setState(String state){
		this.state = state;
	}

	public String getState(){
		return state;
	}

	public void setAddress2(String address2){
		this.address2 = address2;
	}

	public String getAddress2(){
		return address2;
	}

	public void setContactNumber(String contactNumber){
		this.contactNumber = contactNumber;
	}

	public String getContactNumber(){
		return contactNumber;
	}

	public void setCountry(String country){
		this.country = country;
	}

	public String getCountry(){
		return country;
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
}