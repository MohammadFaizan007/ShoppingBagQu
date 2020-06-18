package com.shoppingbag.model.support;

import com.google.gson.annotations.SerializedName;

public class ResponseAddSupport{

	@SerializedName("response")
	private String response;

	@SerializedName("TicketNo")
	private String ticketNo;

	@SerializedName("message")
	private String message;

	@SerializedName("totalCount")
	private int totalCount;

	@SerializedName("TransId")
	private int transId;

	@SerializedName("Flag")
	private int flag;

	public void setResponse(String response){
		this.response = response;
	}

	public String getResponse(){
		return response;
	}

	public void setTicketNo(String ticketNo){
		this.ticketNo = ticketNo;
	}

	public String getTicketNo(){
		return ticketNo;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setTotalCount(int totalCount){
		this.totalCount = totalCount;
	}

	public int getTotalCount(){
		return totalCount;
	}

	public void setTransId(int transId){
		this.transId = transId;
	}

	public int getTransId(){
		return transId;
	}

	public void setFlag(int flag){
		this.flag = flag;
	}

	public int getFlag(){
		return flag;
	}
}