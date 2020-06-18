package com.shoppingbag.model.bookedHistory.responseModel;


import com.google.gson.annotations.SerializedName;

public class GetBookingHistorydetailItem{

	@SerializedName("Origin")
	private String origin;

	@SerializedName("Destination")
	private String destination;

	@SerializedName("TerminalName")
	private String terminalName;

	@SerializedName("TotalAMount")
	private String totalAMount;

	@SerializedName("AirlineName")
	private String airlineName;

	@SerializedName("AirlineCode")
	private String airlineCode;

	@SerializedName("Pk_FlightResponseId")
	private String pkFlightResponseId;

	@SerializedName("Remarks")
	private String remarks;

	@SerializedName("BookSeatButton")
	private String bookSeatButton;

	@SerializedName("UserTrackId")
	private String userTrackId;

	@SerializedName("SeatNo")
	private String seatNo;

	@SerializedName("BookingType")
	private String bookingType;

	@SerializedName("PassengerId")
	private String passengerId;

	@SerializedName("SeatAmt")
	private String seatAmt;

	@SerializedName("DepartingDate")
	private String departingDate;

	@SerializedName("SeatCode")
	private String seatCode;

	@SerializedName("HermesPNR")
	private String hermesPNR;

	@SerializedName("historydetails")
	private String historydetails;

	@SerializedName("BoookingDate")
	private String boookingDate;

	@SerializedName("FlightId")
	private String flightId;

	@SerializedName("FK_MemId")
	private String fKMemId;

	@SerializedName("FromDate")
	private String fromDate;

	@SerializedName("ToDate")
	private String toDate;

	@SerializedName("ButtonDisplay")
	private String buttonDisplay;

	@SerializedName("AirlinePNR")
	private String airlinePNR;

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

	public void setTerminalName(String terminalName){
		this.terminalName = terminalName;
	}

	public String getTerminalName(){
		return terminalName;
	}

	public void setTotalAMount(String totalAMount){
		this.totalAMount = totalAMount;
	}

	public String getTotalAMount(){
		return totalAMount;
	}

	public void setAirlineName(String airlineName){
		this.airlineName = airlineName;
	}

	public String getAirlineName(){
		return airlineName;
	}

	public void setAirlineCode(String airlineCode){
		this.airlineCode = airlineCode;
	}

	public String getAirlineCode(){
		return airlineCode;
	}

	public void setPkFlightResponseId(String pkFlightResponseId){
		this.pkFlightResponseId = pkFlightResponseId;
	}

	public String getPkFlightResponseId(){
		return pkFlightResponseId;
	}

	public void setRemarks(String remarks){
		this.remarks = remarks;
	}

	public String getRemarks(){
		return remarks;
	}

	public void setBookSeatButton(String bookSeatButton){
		this.bookSeatButton = bookSeatButton;
	}

	public String getBookSeatButton(){
		return bookSeatButton;
	}

	public void setUserTrackId(String userTrackId){
		this.userTrackId = userTrackId;
	}

	public String getUserTrackId(){
		return userTrackId;
	}

	public void setSeatNo(String seatNo){
		this.seatNo = seatNo;
	}

	public String getSeatNo(){
		return seatNo;
	}

	public void setBookingType(String bookingType){
		this.bookingType = bookingType;
	}

	public String getBookingType(){
		return bookingType;
	}

	public void setPassengerId(String passengerId){
		this.passengerId = passengerId;
	}

	public String getPassengerId(){
		return passengerId;
	}

	public void setSeatAmt(String seatAmt){
		this.seatAmt = seatAmt;
	}

	public String getSeatAmt(){
		return seatAmt;
	}

	public void setDepartingDate(String departingDate){
		this.departingDate = departingDate;
	}

	public String getDepartingDate(){
		return departingDate;
	}

	public void setSeatCode(String seatCode){
		this.seatCode = seatCode;
	}

	public String getSeatCode(){
		return seatCode;
	}

	public void setHermesPNR(String hermesPNR){
		this.hermesPNR = hermesPNR;
	}

	public String getHermesPNR(){
		return hermesPNR;
	}

	public void setHistorydetails(String historydetails){
		this.historydetails = historydetails;
	}

	public String getHistorydetails(){
		return historydetails;
	}

	public void setBoookingDate(String boookingDate){
		this.boookingDate = boookingDate;
	}

	public String getBoookingDate(){
		return boookingDate;
	}

	public void setFlightId(String flightId){
		this.flightId = flightId;
	}

	public String getFlightId(){
		return flightId;
	}

	public void setFKMemId(String fKMemId){
		this.fKMemId = fKMemId;
	}

	public String getFKMemId(){
		return fKMemId;
	}

	public void setFromDate(String fromDate){
		this.fromDate = fromDate;
	}

	public String getFromDate(){
		return fromDate;
	}

	public void setToDate(String toDate){
		this.toDate = toDate;
	}

	public String getToDate(){
		return toDate;
	}

	public void setButtonDisplay(String buttonDisplay){
		this.buttonDisplay = buttonDisplay;
	}

	public String getButtonDisplay(){
		return buttonDisplay;
	}

	public void setAirlinePNR(String airlinePNR){
		this.airlinePNR = airlinePNR;
	}

	public String getAirlinePNR(){
		return airlinePNR;
	}

	@Override
 	public String toString(){
		return 
			"GetBookingHistorydetailItem{" + 
			"origin = '" + origin + '\'' + 
			",destination = '" + destination + '\'' + 
			",terminalName = '" + terminalName + '\'' + 
			",totalAMount = '" + totalAMount + '\'' + 
			",airlineName = '" + airlineName + '\'' + 
			",airlineCode = '" + airlineCode + '\'' + 
			",pk_FlightResponseId = '" + pkFlightResponseId + '\'' + 
			",remarks = '" + remarks + '\'' + 
			",bookSeatButton = '" + bookSeatButton + '\'' + 
			",userTrackId = '" + userTrackId + '\'' + 
			",seatNo = '" + seatNo + '\'' + 
			",bookingType = '" + bookingType + '\'' + 
			",passengerId = '" + passengerId + '\'' + 
			",seatAmt = '" + seatAmt + '\'' + 
			",departingDate = '" + departingDate + '\'' + 
			",seatCode = '" + seatCode + '\'' + 
			",hermesPNR = '" + hermesPNR + '\'' + 
			",historydetails = '" + historydetails + '\'' + 
			",boookingDate = '" + boookingDate + '\'' + 
			",flightId = '" + flightId + '\'' + 
			",fK_MemId = '" + fKMemId + '\'' + 
			",fromDate = '" + fromDate + '\'' + 
			",toDate = '" + toDate + '\'' + 
			",buttonDisplay = '" + buttonDisplay + '\'' + 
			",airlinePNR = '" + airlinePNR + '\'' + 
			"}";
		}
}