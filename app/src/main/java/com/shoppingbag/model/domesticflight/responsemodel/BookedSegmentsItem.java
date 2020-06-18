package com.shoppingbag.model.domesticflight.responsemodel;

import com.google.gson.annotations.SerializedName;

public class BookedSegmentsItem{

	@SerializedName("Origin")
	private String origin;

	@SerializedName("Destination")
	private String destination;

	@SerializedName("BaggageAllowed")
	private String baggageAllowed;

	@SerializedName("TransType")
	private Object transType;

	@SerializedName("BasicAmount")
	private String basicAmount;

	@SerializedName("AirlineCode")
	private String airlineCode;

	@SerializedName("AirCraftType")
	private String airCraftType;

	@SerializedName("TicketNumber")
	private String ticketNumber;

	@SerializedName("TotalTaxAmount")
	private String totalTaxAmount;

	@SerializedName("BookingId")
	private Object bookingId;

	@SerializedName("MealCode")
	private String mealCode;

	@SerializedName("GrossAmount")
	private String grossAmount;

	@SerializedName("ClassCodeDesc")
	private String classCodeDesc;

	@SerializedName("Arrivaldatetime")
	private String arrivaldatetime;

	@SerializedName("CompanyId")
	private Object companyId;

	@SerializedName("SeatPreferId")
	private String seatPreferId;

	@SerializedName("FareBasis")
	private String fareBasis;

	@SerializedName("DestinationAirport")
	private String destinationAirport;

	@SerializedName("FrequentFlyerId")
	private String frequentFlyerId;

	@SerializedName("SpecialServiceCode")
	private String specialServiceCode;

	@SerializedName("OriginAirport")
	private String originAirport;

	@SerializedName("SupplierId")
	private Object supplierId;

	@SerializedName("ClassCode")
	private String classCode;

	@SerializedName("DepartureDateTime")
	private String departureDateTime;

	@SerializedName("StopOverAllowed")
	private String stopOverAllowed;

	@SerializedName("FlightId")
	private Object flightId;

	@SerializedName("FlightNumber")
	private String flightNumber;

	@SerializedName("FrequentFlyerNumber")
	private String frequentFlyerNumber;

	private boolean seatComnirm = false;

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

	public void setBaggageAllowed(String baggageAllowed){
		this.baggageAllowed = baggageAllowed;
	}

	public String getBaggageAllowed(){
		return baggageAllowed;
	}

	public void setTransType(Object transType){
		this.transType = transType;
	}

	public Object getTransType(){
		return transType;
	}

	public void setBasicAmount(String basicAmount){
		this.basicAmount = basicAmount;
	}

	public String getBasicAmount(){
		return basicAmount;
	}

	public void setAirlineCode(String airlineCode){
		this.airlineCode = airlineCode;
	}

	public String getAirlineCode(){
		return airlineCode;
	}

	public void setAirCraftType(String airCraftType){
		this.airCraftType = airCraftType;
	}

	public String getAirCraftType(){
		return airCraftType;
	}

	public void setTicketNumber(String ticketNumber){
		this.ticketNumber = ticketNumber;
	}

	public String getTicketNumber(){
		return ticketNumber;
	}

	public void setTotalTaxAmount(String totalTaxAmount){
		this.totalTaxAmount = totalTaxAmount;
	}

	public String getTotalTaxAmount(){
		return totalTaxAmount;
	}

	public void setBookingId(Object bookingId){
		this.bookingId = bookingId;
	}

	public Object getBookingId(){
		return bookingId;
	}

	public void setMealCode(String mealCode){
		this.mealCode = mealCode;
	}

	public String getMealCode(){
		return mealCode;
	}

	public void setGrossAmount(String grossAmount){
		this.grossAmount = grossAmount;
	}

	public String getGrossAmount(){
		return grossAmount;
	}

	public void setClassCodeDesc(String classCodeDesc){
		this.classCodeDesc = classCodeDesc;
	}

	public String getClassCodeDesc(){
		return classCodeDesc;
	}

	public void setArrivaldatetime(String arrivaldatetime){
		this.arrivaldatetime = arrivaldatetime;
	}

	public String getArrivaldatetime(){
		return arrivaldatetime;
	}

	public void setCompanyId(Object companyId){
		this.companyId = companyId;
	}

	public Object getCompanyId(){
		return companyId;
	}

	public void setSeatPreferId(String seatPreferId){
		this.seatPreferId = seatPreferId;
	}

	public String getSeatPreferId(){
		return seatPreferId;
	}

	public void setFareBasis(String fareBasis){
		this.fareBasis = fareBasis;
	}

	public String getFareBasis(){
		return fareBasis;
	}

	public void setDestinationAirport(String destinationAirport){
		this.destinationAirport = destinationAirport;
	}

	public String getDestinationAirport(){
		return destinationAirport;
	}

	public void setFrequentFlyerId(String frequentFlyerId){
		this.frequentFlyerId = frequentFlyerId;
	}

	public String getFrequentFlyerId(){
		return frequentFlyerId;
	}

	public void setSpecialServiceCode(String specialServiceCode){
		this.specialServiceCode = specialServiceCode;
	}

	public String getSpecialServiceCode(){
		return specialServiceCode;
	}

	public void setOriginAirport(String originAirport){
		this.originAirport = originAirport;
	}

	public String getOriginAirport(){
		return originAirport;
	}

	public void setSupplierId(Object supplierId){
		this.supplierId = supplierId;
	}

	public Object getSupplierId(){
		return supplierId;
	}

	public void setClassCode(String classCode){
		this.classCode = classCode;
	}

	public String getClassCode(){
		return classCode;
	}

	public void setDepartureDateTime(String departureDateTime){
		this.departureDateTime = departureDateTime;
	}

	public String getDepartureDateTime(){
		return departureDateTime;
	}

	public void setStopOverAllowed(String stopOverAllowed){
		this.stopOverAllowed = stopOverAllowed;
	}

	public String getStopOverAllowed(){
		return stopOverAllowed;
	}

	public void setFlightId(Object flightId){
		this.flightId = flightId;
	}

	public Object getFlightId(){
		return flightId;
	}

	public void setFlightNumber(String flightNumber){
		this.flightNumber = flightNumber;
	}

	public String getFlightNumber(){
		return flightNumber;
	}

	public void setFrequentFlyerNumber(String frequentFlyerNumber){
		this.frequentFlyerNumber = frequentFlyerNumber;
	}

	public String getFrequentFlyerNumber(){
		return frequentFlyerNumber;
	}

	public boolean isSeatComnirm() {
		return seatComnirm;
	}

	public void setSeatComnirm(boolean seatComnirm) {
		this.seatComnirm = seatComnirm;
	}
}