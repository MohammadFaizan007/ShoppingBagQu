package com.shoppingbag.model.response.bus_response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AvailableBusesItem{

	@SerializedName("TransportName")
	private String transportName;

	@SerializedName("DepartureTime")
	private String departureTime;

	@SerializedName("AvailableSeatCount")
	private String availableSeatCount;

	@SerializedName("BusType")
	private String busType;

	@SerializedName("BoardingPoints")
	private List<BoardingPointsItem> boardingPoints;

	@SerializedName("Commission")
	private String commission;

	@SerializedName("ScheduleId")
	private String scheduleId;

	@SerializedName("StationId")
	private String stationId;

	@SerializedName("Fares")
	private List<FaresItem> fares;

	@SerializedName("TripId")
	private String tripId;

	@SerializedName("TransportId")
	private String transportId;

	@SerializedName("BusName")
	private String busName;

	@SerializedName("DroppingPoints")
	private List<Object> droppingPoints;

	@SerializedName("ArrivalTime")
	private String arrivalTime;

	@SerializedName("Amenities")
	private String amenities;

	public void setTransportName(String transportName){
		this.transportName = transportName;
	}

	public String getTransportName(){
		return transportName;
	}

	public void setDepartureTime(String departureTime){
		this.departureTime = departureTime;
	}

	public String getDepartureTime(){
		return departureTime;
	}

	public void setAvailableSeatCount(String availableSeatCount){
		this.availableSeatCount = availableSeatCount;
	}

	public String getAvailableSeatCount(){
		return availableSeatCount;
	}

	public void setBusType(String busType){
		this.busType = busType;
	}

	public String getBusType(){
		return busType;
	}

	public void setBoardingPoints(List<BoardingPointsItem> boardingPoints){
		this.boardingPoints = boardingPoints;
	}

	public List<BoardingPointsItem> getBoardingPoints(){
		return boardingPoints;
	}

	public void setCommission(String commission){
		this.commission = commission;
	}

	public String getCommission(){
		return commission;
	}

	public void setScheduleId(String scheduleId){
		this.scheduleId = scheduleId;
	}

	public String getScheduleId(){
		return scheduleId;
	}

	public void setStationId(String stationId){
		this.stationId = stationId;
	}

	public String getStationId(){
		return stationId;
	}

	public void setFares(List<FaresItem> fares){
		this.fares = fares;
	}

	public List<FaresItem> getFares(){
		return fares;
	}

	public void setTripId(String tripId){
		this.tripId = tripId;
	}

	public String getTripId(){
		return tripId;
	}

	public void setTransportId(String transportId){
		this.transportId = transportId;
	}

	public String getTransportId(){
		return transportId;
	}

	public void setBusName(String busName){
		this.busName = busName;
	}

	public String getBusName(){
		return busName;
	}

	public void setDroppingPoints(List<Object> droppingPoints){
		this.droppingPoints = droppingPoints;
	}

	public List<Object> getDroppingPoints(){
		return droppingPoints;
	}

	public void setArrivalTime(String arrivalTime){
		this.arrivalTime = arrivalTime;
	}

	public String getArrivalTime(){
		return arrivalTime;
	}

	public void setAmenities(String amenities){
		this.amenities = amenities;
	}

	public String getAmenities(){
		return amenities;
	}

	@Override
 	public String toString(){
		return 
			"AvailableBusesItem{" + 
			"transportName = '" + transportName + '\'' + 
			",departureTime = '" + departureTime + '\'' + 
			",availableSeatCount = '" + availableSeatCount + '\'' + 
			",busType = '" + busType + '\'' + 
			",boardingPoints = '" + boardingPoints + '\'' + 
			",commission = '" + commission + '\'' + 
			",scheduleId = '" + scheduleId + '\'' + 
			",stationId = '" + stationId + '\'' + 
			",fares = '" + fares + '\'' + 
			",tripId = '" + tripId + '\'' + 
			",transportId = '" + transportId + '\'' + 
			",busName = '" + busName + '\'' + 
			",droppingPoints = '" + droppingPoints + '\'' + 
			",arrivalTime = '" + arrivalTime + '\'' + 
			",amenities = '" + amenities + '\'' + 
			"}";
		}
}