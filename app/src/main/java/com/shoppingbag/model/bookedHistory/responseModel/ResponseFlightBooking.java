package com.shoppingbag.model.bookedHistory.responseModel;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseFlightBooking{

	@SerializedName("Response")
	private String response;

	@SerializedName("GetBookingHistorydetail")
	private List<GetBookingHistorydetailItem> getBookingHistorydetail;

	public void setResponse(String response){
		this.response = response;
	}

	public String getResponse(){
		return response;
	}

	public void setGetBookingHistorydetail(List<GetBookingHistorydetailItem> getBookingHistorydetail){
		this.getBookingHistorydetail = getBookingHistorydetail;
	}

	public List<GetBookingHistorydetailItem> getGetBookingHistorydetail(){
		return getBookingHistorydetail;
	}

	@Override
 	public String toString(){
		return 
			"ResponseFlightBooking{" + 
			"response = '" + response + '\'' + 
			",getBookingHistorydetail = '" + getBookingHistorydetail + '\'' + 
			"}";
		}
}