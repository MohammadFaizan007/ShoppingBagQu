package com.shoppingbag.one_india_shopping.model.tracking;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TrackingData{

	@SerializedName("shipment_track")
	private List<ShipmentTrackItem> shipmentTrack;

	@SerializedName("shipment_track_activities")
	private List<ShipmentTrackActivitiesItem> shipmentTrackActivities;

	@SerializedName("track_status")
	private int trackStatus;

	@SerializedName("track_url")
	private String trackUrl;

	@SerializedName("shipment_status")
	private int shipmentStatus;

	public void setShipmentTrack(List<ShipmentTrackItem> shipmentTrack){
		this.shipmentTrack = shipmentTrack;
	}

	public List<ShipmentTrackItem> getShipmentTrack(){
		return shipmentTrack;
	}

	public void setShipmentTrackActivities(List<ShipmentTrackActivitiesItem> shipmentTrackActivities){
		this.shipmentTrackActivities = shipmentTrackActivities;
	}

	public List<ShipmentTrackActivitiesItem> getShipmentTrackActivities(){
		return shipmentTrackActivities;
	}

	public void setTrackStatus(int trackStatus){
		this.trackStatus = trackStatus;
	}

	public int getTrackStatus(){
		return trackStatus;
	}

	public void setTrackUrl(String trackUrl){
		this.trackUrl = trackUrl;
	}

	public String getTrackUrl(){
		return trackUrl;
	}

	public void setShipmentStatus(int shipmentStatus){
		this.shipmentStatus = shipmentStatus;
	}

	public int getShipmentStatus(){
		return shipmentStatus;
	}
}