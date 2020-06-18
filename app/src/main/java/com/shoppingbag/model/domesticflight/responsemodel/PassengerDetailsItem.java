package com.shoppingbag.model.domesticflight.responsemodel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PassengerDetailsItem{

	@SerializedName("PersonOrgId")
	private String personOrgId;

	@SerializedName("CompanyId")
	private Object companyId;

	@SerializedName("FirstName")
	private String firstName;

	@SerializedName("Title")
	private String title;

	@SerializedName("TransType")
	private Object transType;

	@SerializedName("Gender")
	private Object gender;

	@SerializedName("PaxNumber")
	private Object paxNumber;

	@SerializedName("BookedSegments")
	private List<BookedSegmentsItem> bookedSegments;

	@SerializedName("PassengerType")
	private String passengerType;

	@SerializedName("TicketNumber")
	private String ticketNumber;

	@SerializedName("IdentityProofId")
	private String identityProofId;

	@SerializedName("BookingId")
	private Object bookingId;

	@SerializedName("PaxType")
	private Object paxType;

	@SerializedName("DateofBirth")
	private Object dateofBirth;

	@SerializedName("LastName")
	private String lastName;

	@SerializedName("TransmissionControlNo")
	private String transmissionControlNo;

	@SerializedName("IdentityProofNumber")
	private String identityProofNumber;

	@SerializedName("Age")
	private String age;

	public void setPersonOrgId(String personOrgId){
		this.personOrgId = personOrgId;
	}

	public String getPersonOrgId(){
		return personOrgId;
	}

	public void setCompanyId(Object companyId){
		this.companyId = companyId;
	}

	public Object getCompanyId(){
		return companyId;
	}

	public void setFirstName(String firstName){
		this.firstName = firstName;
	}

	public String getFirstName(){
		return firstName;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setTransType(Object transType){
		this.transType = transType;
	}

	public Object getTransType(){
		return transType;
	}

	public void setGender(Object gender){
		this.gender = gender;
	}

	public Object getGender(){
		return gender;
	}

	public void setPaxNumber(Object paxNumber){
		this.paxNumber = paxNumber;
	}

	public Object getPaxNumber(){
		return paxNumber;
	}

	public void setBookedSegments(List<BookedSegmentsItem> bookedSegments){
		this.bookedSegments = bookedSegments;
	}

	public List<BookedSegmentsItem> getBookedSegments(){
		return bookedSegments;
	}

	public void setPassengerType(String passengerType){
		this.passengerType = passengerType;
	}

	public String getPassengerType(){
		return passengerType;
	}

	public void setTicketNumber(String ticketNumber){
		this.ticketNumber = ticketNumber;
	}

	public String getTicketNumber(){
		return ticketNumber;
	}

	public void setIdentityProofId(String identityProofId){
		this.identityProofId = identityProofId;
	}

	public String getIdentityProofId(){
		return identityProofId;
	}

	public void setBookingId(Object bookingId){
		this.bookingId = bookingId;
	}

	public Object getBookingId(){
		return bookingId;
	}

	public void setPaxType(Object paxType){
		this.paxType = paxType;
	}

	public Object getPaxType(){
		return paxType;
	}

	public void setDateofBirth(Object dateofBirth){
		this.dateofBirth = dateofBirth;
	}

	public Object getDateofBirth(){
		return dateofBirth;
	}

	public void setLastName(String lastName){
		this.lastName = lastName;
	}

	public String getLastName(){
		return lastName;
	}

	public void setTransmissionControlNo(String transmissionControlNo){
		this.transmissionControlNo = transmissionControlNo;
	}

	public String getTransmissionControlNo(){
		return transmissionControlNo;
	}

	public void setIdentityProofNumber(String identityProofNumber){
		this.identityProofNumber = identityProofNumber;
	}

	public String getIdentityProofNumber(){
		return identityProofNumber;
	}

	public void setAge(String age){
		this.age = age;
	}

	public String getAge(){
		return age;
	}
}