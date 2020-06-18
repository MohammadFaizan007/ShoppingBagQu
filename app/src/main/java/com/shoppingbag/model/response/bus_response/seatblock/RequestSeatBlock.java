package com.shoppingbag.model.response.bus_response.seatblock;

import com.google.gson.annotations.SerializedName;

public class RequestSeatBlock{

	@SerializedName("CompanyId")
	private String companyId;

	@SerializedName("UserTrackId")
	private String userTrackId;

	@SerializedName("BookingId")
	private String bookingId;

	@SerializedName("TransType")
	private String transType;

	@SerializedName("BlockingInput")
	private BlockingInput blockingInput;

	public void setCompanyId(String companyId){
		this.companyId = companyId;
	}

	public String getCompanyId(){
		return companyId;
	}

	public void setUserTrackId(String userTrackId){
		this.userTrackId = userTrackId;
	}

	public String getUserTrackId(){
		return userTrackId;
	}

	public void setBookingId(String bookingId){
		this.bookingId = bookingId;
	}

	public String getBookingId(){
		return bookingId;
	}

	public void setTransType(String transType){
		this.transType = transType;
	}

	public String getTransType(){
		return transType;
	}

	public void setBlockingInput(BlockingInput blockingInput){
		this.blockingInput = blockingInput;
	}

	public BlockingInput getBlockingInput(){
		return blockingInput;
	}
}