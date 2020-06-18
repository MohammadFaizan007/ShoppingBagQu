package com.shoppingbag.model.response.bus_response.responsepojo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SeatsItem implements Serializable {

	@SerializedName("SeatTypeId")
	private String seatTypeId;

	@SerializedName("ServiceTax")
	private String serviceTax;

	@SerializedName("BerthType")
	private String berthType;

	@SerializedName("SeatType")
	private String seatType;

	@SerializedName("SeatNo")
	private String seatNo;

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

	public void setBerthType(String berthType){
		this.berthType = berthType;
	}

	public String getBerthType(){
		return berthType;
	}

	public void setSeatType(String seatType){
		this.seatType = seatType;
	}

	public String getSeatType(){
		return seatType;
	}

	public void setSeatNo(String seatNo){
		this.seatNo = seatNo;
	}

	public String getSeatNo(){
		return seatNo;
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
			"SeatsItem{" + 
			"seatTypeId = '" + seatTypeId + '\'' + 
			",serviceTax = '" + serviceTax + '\'' + 
			",berthType = '" + berthType + '\'' + 
			",seatType = '" + seatType + '\'' + 
			",seatNo = '" + seatNo + '\'' + 
			",convenienceFee = '" + convenienceFee + '\'' + 
			",fare = '" + fare + '\'' + 
			"}";
		}
}