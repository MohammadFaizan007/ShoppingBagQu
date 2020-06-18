package com.shoppingbag.model.response.bus_response.responsepojo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AvailableSeatsItem implements Serializable {

	@SerializedName("SeatNo")
	private String seatNo;

	public void setSeatNo(String seatNo){
		this.seatNo = seatNo;
	}

	public String getSeatNo(){
		return seatNo;
	}

	@Override
 	public String toString(){
		return 
			"AvailableSeatsItem{" + 
			"seatNo = '" + seatNo + '\'' + 
			"}";
		}
}