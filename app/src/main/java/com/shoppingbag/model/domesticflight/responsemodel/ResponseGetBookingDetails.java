package com.shoppingbag.model.domesticflight.responsemodel;

import com.google.gson.annotations.SerializedName;

public class ResponseGetBookingDetails{

	@SerializedName("FailureRemarks")
	private Object failureRemarks;

	@SerializedName("BookOutput")
	private BookOutput bookOutput;

	@SerializedName("ResponseStatus")
	private String responseStatus;

	@SerializedName("UserTrackId")
	private String userTrackId;

	public void setFailureRemarks(Object failureRemarks){
		this.failureRemarks = failureRemarks;
	}

	public Object getFailureRemarks(){
		return failureRemarks;
	}

	public void setBookOutput(BookOutput bookOutput){
		this.bookOutput = bookOutput;
	}

	public BookOutput getBookOutput(){
		return bookOutput;
	}

	public void setResponseStatus(String responseStatus){
		this.responseStatus = responseStatus;
	}

	public String getResponseStatus(){
		return responseStatus;
	}

	public void setUserTrackId(String userTrackId){
		this.userTrackId = userTrackId;
	}

	public String getUserTrackId(){
		return userTrackId;
	}
}