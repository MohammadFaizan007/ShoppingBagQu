package com.shoppingbag.model.response.bus_response.responsepojo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FaresItem implements Serializable {

	@SerializedName("SeatTypeId")
	private String seatTypeId;

	@SerializedName("ServiceTax")
	private String serviceTax;

	@SerializedName("SeatType")
	private String seatType;

	@SerializedName("ConvenienceFee")
	private String convenienceFee;

	@SerializedName("Fare")
	private String fare;

	public void setSeatTypeId(String seatTypeId){
		this.seatTypeId = seatTypeId;
	}

	public String getSeatTypeId(){
		return seatTypeId;
	}

	public void setServiceTax(String serviceTax){
		this.serviceTax = serviceTax;
	}

	public String getServiceTax(){
		return serviceTax;
	}

	public void setSeatType(String seatType){
		this.seatType = seatType;
	}

	public String getSeatType(){
		return seatType;
	}

	public void setConvenienceFee(String convenienceFee){
		this.convenienceFee = convenienceFee;
	}

	public String getConvenienceFee(){
		return convenienceFee;
	}

	public void setFare(String fare){
		this.fare = fare;
	}

	public String getFare(){
		return fare;
	}

	@Override
 	public String toString(){
		return 
			"FaresItem{" + 
			"seatTypeId = '" + seatTypeId + '\'' + 
			",serviceTax = '" + serviceTax + '\'' + 
			",seatType = '" + seatType + '\'' + 
			",convenienceFee = '" + convenienceFee + '\'' + 
			",fare = '" + fare + '\'' + 
			"}";
		}
}