package com.shoppingbag.model.response.bus_response.responsepojo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ResponseBusLayout implements Serializable {

	@SerializedName("ResponseStatus")
	private String responseStatus;

	@SerializedName("UserTrackId")
	private String userTrackId;

	@SerializedName("SeatMapOutput")
	private SeatMapOutput seatMapOutput;

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

	public void setSeatMapOutput(SeatMapOutput seatMapOutput){
		this.seatMapOutput = seatMapOutput;
	}

	public SeatMapOutput getSeatMapOutput(){
		return seatMapOutput;
	}


}