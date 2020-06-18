package com.shoppingbag.one_india_shopping.model.tracking;

import com.google.gson.annotations.SerializedName;

public class ShipmentTrackItem{

	@SerializedName("courier_company_id")
	private int courierCompanyId;

	@SerializedName("courier_agent_details")
	private Object courierAgentDetails;

	@SerializedName("delivered_to")
	private String deliveredTo;

	@SerializedName("consignee_name")
	private String consigneeName;

	@SerializedName("origin")
	private String origin;

	@SerializedName("destination")
	private String destination;

	@SerializedName("weight")
	private String weight;

	@SerializedName("shipment_id")
	private Object shipmentId;

	@SerializedName("packages")
	private int packages;

	@SerializedName("pickup_date")
	private String pickupDate;

	@SerializedName("current_status")
	private String currentStatus;

	@SerializedName("id")
	private int id;

	@SerializedName("awb_code")
	private String awbCode;

	@SerializedName("order_id")
	private int orderId;

	@SerializedName("delivered_date")
	private String deliveredDate;

	public void setCourierCompanyId(int courierCompanyId){
		this.courierCompanyId = courierCompanyId;
	}

	public int getCourierCompanyId(){
		return courierCompanyId;
	}

	public void setCourierAgentDetails(Object courierAgentDetails){
		this.courierAgentDetails = courierAgentDetails;
	}

	public Object getCourierAgentDetails(){
		return courierAgentDetails;
	}

	public void setDeliveredTo(String deliveredTo){
		this.deliveredTo = deliveredTo;
	}

	public String getDeliveredTo(){
		return deliveredTo;
	}

	public void setConsigneeName(String consigneeName){
		this.consigneeName = consigneeName;
	}

	public String getConsigneeName(){
		return consigneeName;
	}

	public void setOrigin(String origin){
		this.origin = origin;
	}

	public String getOrigin(){
		return origin;
	}

	public void setDestination(String destination){
		this.destination = destination;
	}

	public String getDestination(){
		return destination;
	}

	public void setWeight(String weight){
		this.weight = weight;
	}

	public String getWeight(){
		return weight;
	}

	public void setShipmentId(Object shipmentId){
		this.shipmentId = shipmentId;
	}

	public Object getShipmentId(){
		return shipmentId;
	}

	public void setPackages(int packages){
		this.packages = packages;
	}

	public int getPackages(){
		return packages;
	}

	public void setPickupDate(String pickupDate){
		this.pickupDate = pickupDate;
	}

	public String getPickupDate(){
		return pickupDate;
	}

	public void setCurrentStatus(String currentStatus){
		this.currentStatus = currentStatus;
	}

	public String getCurrentStatus(){
		return currentStatus;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setAwbCode(String awbCode){
		this.awbCode = awbCode;
	}

	public String getAwbCode(){
		return awbCode;
	}

	public void setOrderId(int orderId){
		this.orderId = orderId;
	}

	public int getOrderId(){
		return orderId;
	}

	public void setDeliveredDate(String deliveredDate){
		this.deliveredDate = deliveredDate;
	}

	public String getDeliveredDate(){
		return deliveredDate;
	}
}