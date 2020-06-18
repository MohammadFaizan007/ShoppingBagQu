package com.shoppingbag.model.seat_book_response;


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

	@SerializedName("ContactNumber")
	private String contactNumber;

	@SerializedName("Title")
	private Object title;

	@SerializedName("City")
	private Object city;

	@SerializedName("Name")
	private String name;

	public void setCountryId(Object countryId){
		this.countryId = countryId;
	}

	public Object getCountryId(){
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

	public void setBookedDate(String bookedDate){
		this.bookedDate = bookedDate;
	}

	public String getBookedDate(){
		return bookedDate;
	}

	public void setIdProofNumber(String idProofNumber){
		this.idProofNumber = idProofNumber;
	}

	public String getIdProofNumber(){
		return idProofNumber;
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

	public void setCity(Object city){
		this.city = city;
	}

	public Object getCity(){
		return city;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}
}