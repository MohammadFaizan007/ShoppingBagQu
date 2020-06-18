package com.shoppingbag.model.domesticflight.responsemodel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FlightTicketDetailsItem{

	@SerializedName("TravelType")
	private String travelType;

	@SerializedName("TotalSegments")
	private String totalSegments;

	@SerializedName("TerminalContactDetails")
	private TerminalContactDetails terminalContactDetails;

	@SerializedName("TotalAmount")
	private String totalAmount;

	@SerializedName("CustomerDetails")
	private CustomerDetails customerDetails;

	@SerializedName("HermesPNR")
	private String hermesPNR;

	@SerializedName("TransactionId")
	private String transactionId;

	@SerializedName("AirlineDetails")
	private List<AirlineDetailsItem> airlineDetails;

	@SerializedName("IATADetails")
	private IATADetails iATADetails;

	@SerializedName("InfantCount")
	private String infantCount;

	@SerializedName("AdultCount")
	private String adultCount;

	@SerializedName("ChildCount")
	private String childCount;

	@SerializedName("IssueDateTime")
	private String issueDateTime;

	@SerializedName("PaymentDetails")
	private PaymentDetails paymentDetails;

	@SerializedName("BookingRemarks")
	private Object bookingRemarks;

	@SerializedName("OtherCharges")
	private String otherCharges;

	@SerializedName("PassengerDetails")
	private List<PassengerDetailsItem> passengerDetails;

	@SerializedName("BookingType")
	private String bookingType;

	public void setTravelType(String travelType){
		this.travelType = travelType;
	}

	public String getTravelType(){
		return travelType;
	}

	public void setTotalSegments(String totalSegments){
		this.totalSegments = totalSegments;
	}

	public String getTotalSegments(){
		return totalSegments;
	}

	public void setTerminalContactDetails(TerminalContactDetails terminalContactDetails){
		this.terminalContactDetails = terminalContactDetails;
	}

	public TerminalContactDetails getTerminalContactDetails(){
		return terminalContactDetails;
	}

	public void setTotalAmount(String totalAmount){
		this.totalAmount = totalAmount;
	}

	public String getTotalAmount(){
		return totalAmount;
	}

	public void setCustomerDetails(CustomerDetails customerDetails){
		this.customerDetails = customerDetails;
	}

	public CustomerDetails getCustomerDetails(){
		return customerDetails;
	}

	public void setHermesPNR(String hermesPNR){
		this.hermesPNR = hermesPNR;
	}

	public String getHermesPNR(){
		return hermesPNR;
	}

	public void setTransactionId(String transactionId){
		this.transactionId = transactionId;
	}

	public String getTransactionId(){
		return transactionId;
	}

	public void setAirlineDetails(List<AirlineDetailsItem> airlineDetails){
		this.airlineDetails = airlineDetails;
	}

	public List<AirlineDetailsItem> getAirlineDetails(){
		return airlineDetails;
	}

	public void setIATADetails(IATADetails iATADetails){
		this.iATADetails = iATADetails;
	}

	public IATADetails getIATADetails(){
		return iATADetails;
	}

	public void setInfantCount(String infantCount){
		this.infantCount = infantCount;
	}

	public String getInfantCount(){
		return infantCount;
	}

	public void setAdultCount(String adultCount){
		this.adultCount = adultCount;
	}

	public String getAdultCount(){
		return adultCount;
	}

	public void setChildCount(String childCount){
		this.childCount = childCount;
	}

	public String getChildCount(){
		return childCount;
	}

	public void setIssueDateTime(String issueDateTime){
		this.issueDateTime = issueDateTime;
	}

	public String getIssueDateTime(){
		return issueDateTime;
	}

	public void setPaymentDetails(PaymentDetails paymentDetails){
		this.paymentDetails = paymentDetails;
	}

	public PaymentDetails getPaymentDetails(){
		return paymentDetails;
	}

	public void setBookingRemarks(Object bookingRemarks){
		this.bookingRemarks = bookingRemarks;
	}

	public Object getBookingRemarks(){
		return bookingRemarks;
	}

	public void setOtherCharges(String otherCharges){
		this.otherCharges = otherCharges;
	}

	public String getOtherCharges(){
		return otherCharges;
	}

	public void setPassengerDetails(List<PassengerDetailsItem> passengerDetails){
		this.passengerDetails = passengerDetails;
	}

	public List<PassengerDetailsItem> getPassengerDetails(){
		return passengerDetails;
	}

	public void setBookingType(String bookingType){
		this.bookingType = bookingType;
	}

	public String getBookingType(){
		return bookingType;
	}
}