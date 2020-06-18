package com.shoppingbag.model.domesticflight.responsemodel;

import com.google.gson.annotations.SerializedName;

public class CustomerDetails{

	@SerializedName("AirlineDetails")
	private Object airlineDetails;

	@SerializedName("CountryId")
	private String countryId;

	@SerializedName("EmailId")
	private String emailId;

	@SerializedName("Address")
	private String address;

	@SerializedName("ContactNumber")
	private String contactNumber;

	@SerializedName("Title")
	private Object title;

	@SerializedName("City")
	private String city;

	@SerializedName("Name")
	private String name;

	@SerializedName("PinCode")
	private Object pinCode;

	public void setAirlineDetails(Object airlineDetails){
		this.airlineDetails = airlineDetails;
	}

	public Object getAirlineDetails(){
		return airlineDetails;
	}

	public void setCountryId(String countryId){
		this.countryId = countryId;
	}

	public String getCountryId(){
		return countryId;
	}

	public void setEmailId(String emailId){
		this.emailId = emailId;
	}

	public String getEmailId(){
		return emailId;
	}

	public void setAddress(String address){
		this.address = address;
	}

	public String getAddress(){
		return address;
	}

	public void setContactNumber(String contactNumber){
		this.contactNumber = contactNumber;
	}

	public String getContactNumber(){
		return contactNumber;
	}

	public void setTitle(Object title){
		this.title = title;
	}

	public Object getTitle(){
		return title;
	}

	public void setCity(String city){
		this.city = city;
	}

	public String getCity(){
		return city;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setPinCode(Object pinCode){
		this.pinCode = pinCode;
	}

	public Object getPinCode(){
		return pinCode;
	}
}