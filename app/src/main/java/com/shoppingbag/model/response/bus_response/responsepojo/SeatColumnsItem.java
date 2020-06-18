package com.shoppingbag.model.response.bus_response.responsepojo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SeatColumnsItem implements Serializable {

	@SerializedName("Seats")
	private List<SeatsItem> seats;

	public void setSeats(List<SeatsItem> seats){
		this.seats = seats;
	}

	public List<SeatsItem> getSeats(){
		return seats;
	}

	@Override
 	public String toString(){
		return 
			"SeatColumnsItem{" + 
			"seats = '" + seats + '\'' + 
			"}";
		}
}