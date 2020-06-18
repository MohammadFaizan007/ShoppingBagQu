package com.shoppingbag.model.support;

import com.google.gson.annotations.SerializedName;

public class ResultItem{

	@SerializedName("Path")
	private String path;

	@SerializedName("Status")
	private String status;

	@SerializedName("Message")
	private String message;

	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}

	@SerializedName("Type")
	private String Type;

	@SerializedName("TicketNumber")
	private String ticketNumber;

	@SerializedName("Subject")
	private String subject;

	public void setPath(String path){
		this.path = path;
	}

	public String getPath(){
		return path;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setTicketNumber(String ticketNumber){
		this.ticketNumber = ticketNumber;
	}

	public String getTicketNumber(){
		return ticketNumber;
	}

	public void setSubject(String subject){
		this.subject = subject;
	}

	public String getSubject(){
		return subject;
	}
}