package com.shoppingbag.model.response.bus_response.requestpojo;

import com.google.gson.annotations.SerializedName;

public class BookingCustomerDetails{

	@SerializedName("CountryId")
	private String countryId;

	@SerializedName("IdProofId")
	private String idProofId;

	@SerializedName("EmailId")
	private String emailId;

	@SerializedName("Address")
	private String address;

	@SerializedName("IdProofNumber")
	private String idProofNumber;

	@SerializedName("Title")
	private String title;

	@SerializedName("ContactNumber")
	private String contactNumber;

	@SerializedName("City")
	private String city;

	@SerializedName("Name")
	private String name;

	public void setCountryId(String countryId){
		this.countryId = countryId;
	}

	public String getCountryId(){
		return countryId;
	}

	public void setIdProofId(String idProofId){
		this.idProofId = idProofId;
	}

	public String getIdProofId(){
		return idProofId;
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

	public void setIdProofNumber(String idProofNumber){
		this.idProofNumber = idProofNumber;
	}

	public String getIdProofNumber(){
		return idProofNumber;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setContactNumber(String contactNumber){
		this.contactNumber = contactNumber;
	}

	public String getContactNumber(){
		return contactNumber;
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

	@Override
 	public String toString(){
		return 
			"BookingCustomerDetails{" + 
			"countryId = '" + countryId + '\'' + 
			",idProofId = '" + idProofId + '\'' + 
			",emailId = '" + emailId + '\'' + 
			",address = '" + address + '\'' + 
			",idProofNumber = '" + idProofNumber + '\'' + 
			",title = '" + title + '\'' + 
			",contactNumber = '" + contactNumber + '\'' + 
			",city = '" + city + '\'' + 
			",name = '" + name + '\'' + 
			"}";
		}
}