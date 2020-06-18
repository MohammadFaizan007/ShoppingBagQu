package com.shoppingbag.model.response.bus_response.responsepojo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SeatLayout implements Serializable {

	@SerializedName("SeatColumns")
	private List<SeatColumnsItem> seatColumns;

	public void setSeatColumns(List<SeatColumnsItem> seatColumns){
		this.seatColumns = seatColumns;
	}

	public List<SeatColumnsItem> getSeatColumns(){
		return seatColumns;
	}

	@Override
 	public String toString(){
		return 
			"SeatLayout{" + 
			"seatColumns = '" + seatColumns + '\'' + 
			"}";
		}
}