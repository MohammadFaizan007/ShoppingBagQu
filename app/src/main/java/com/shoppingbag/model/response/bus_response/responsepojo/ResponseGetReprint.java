package com.shoppingbag.model.response.bus_response.responsepojo;

import com.google.gson.annotations.SerializedName;

public class ResponseGetReprint{

	@SerializedName("ResponseStatus")
	private String responseStatus;

	@SerializedName("UserTrackId")
	private String userTrackId;

	@SerializedName("ReprintOutput")
	private ReprintOutput reprintOutput;

	public void setResponseStatus(String responseStatus){
		this.responseStatus = responseStatus;
	}

	public String getResponseStatus(){
		return responseStatus;
	}

	public void setUserTrackId(String userTrackId){
		this.userTrackId = userTrackId;
	}

	public String getUserTrackId(){
		return userTrackId;
	}

	public void setReprintOutput(ReprintOutput reprintOutput){
		this.reprintOutput = reprintOutput;
	}

	public ReprintOutput getReprintOutput(){
		return reprintOutput;
	}

	@Override
 	public String toString(){
		return 
			"ResponseGetReprintMobile{" +
			"responseStatus = '" + responseStatus + '\'' + 
			",userTrackId = '" + userTrackId + '\'' + 
			",reprintOutput = '" + reprintOutput + '\'' + 
			"}";
		}
}