package com.shoppingbag.model.response.bus_response.responsepojo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SeatMapOutput implements Serializable {

	@SerializedName("BusNumber")
	private String busNumber;

	@SerializedName("BoardingPoints")
	private List<BoardingPointsItem> boardingPoints;

	@SerializedName("DroppingPoints")
	private List<DroppingPointsItem> droppingPoints;

	@SerializedName("SeatLayoutDetails")
	private SeatLayoutDetails seatLayoutDetails;

	@SerializedName("Fares")
	private List<FaresItem> fares;

	public void setBusNumber(String busNumber){
		this.busNumber = busNumber;
	}

	public String getBusNumber(){
		return busNumber;
	}

	public void setBoardingPoints(List<BoardingPointsItem> boardingPoints){
		this.boardingPoints = boardingPoints;
	}

	public List<BoardingPointsItem> getBoardingPoints(){
		return boardingPoints;
	}

	public void setDroppingPoints(List<DroppingPointsItem> droppingPoints){
		this.droppingPoints = droppingPoints;
	}

	public List<DroppingPointsItem> getDroppingPoints(){
		return droppingPoints;
	}

	public void setSeatLayoutDetails(SeatLayoutDetails seatLayoutDetails){
		this.seatLayoutDetails = seatLayoutDetails;
	}

	public SeatLayoutDetails getSeatLayoutDetails(){
		return seatLayoutDetails;
	}

	public void setFares(List<FaresItem> fares){
		this.fares = fares;
	}

	public List<FaresItem> getFares(){
		return fares;
	}


}