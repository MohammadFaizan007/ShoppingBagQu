package com.shoppingbag.model.response.bus_response.busTransactionStatus;

import com.google.gson.annotations.SerializedName;

public class BookingCustomerDetails{

	@SerializedName("CountryId")
	private Object countryId;

	@SerializedName("IdProofId")
	private String idProofId;

	@SerializedName("EmailId")
	private String emailId;

	@SerializedName("Address")
	private String address;

	@SerializedName("BookedDate")
	private String bookedDate;

	@SerializedName("IdProofNumber")
	private String idProofNumber;

	@SerializedName("Title")
	private Object title;

	@SerializedName("ContactNumber")
	private String contactNumber;

	@SerializedName("City")
	private Object city;

	@SerializedName("Name")
	private String name;

	public Object getCountryId(){
		return countryId;
	}

	public String getIdProofId(){
		return idProofId;
	}

	public String getEmailId(){
		return emailId;
	}

	public String getAddress(){
		return address;
	}

	public String getBookedDate(){
		return bookedDate;
	}

	public String getIdProofNumber(){
		return idProofNumber;
	}

	public Object getTitle(){
		return title;
	}

	public String getContactNumber(){
		return contactNumber;
	}

	public Object getCity(){
		return city;
	}

	public String getName(){
		return name;
	}
}