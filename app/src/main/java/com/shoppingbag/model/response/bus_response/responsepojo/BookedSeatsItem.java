package com.shoppingbag.model.response.bus_response.responsepojo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BookedSeatsItem implements Serializable {

	@SerializedName("SeatNo")
	private String seatNo;

	@SerializedName("Gender")
	private String gender;

	public void setSeatNo(String seatNo){
		this.seatNo = seatNo;
	}

	public String getSeatNo(){
		return seatNo;
	}

	public void setGender(String gender){
		this.gender = gender;
	}

	public String getGender(){
		return gender;
	}

	@Override
 	public String toString(){
		return 
			"BookedSeatsItem{" + 
			"seatNo = '" + seatNo + '\'' + 
			",gender = '" + gender + '\'' + 
			"}";
		}
}