package com.shoppingbag.model.response.bus_response;

import com.google.gson.annotations.SerializedName;

public class GetDestinationResponse {

	@SerializedName("ResponseStatus")
	private String responseStatus;

	@SerializedName("FailureResponse")
	private Object failureResponse;

	@SerializedName("DestinationOutput")
	private DestinationOutput destinationOutput;

	public void setResponseStatus(String responseStatus){
		this.responseStatus = responseStatus;
	}

	public String getResponseStatus(){
		return responseStatus;
	}

	public void setFailureResponse(Object failureResponse){
		this.failureResponse = failureResponse;
	}

	public Object getFailureResponse(){
		return failureResponse;
	}

	public void setDestinationOutput(DestinationOutput destinationOutput){
		this.destinationOutput = destinationOutput;
	}

	public DestinationOutput getDestinationOutput(){
		return destinationOutput;
	}

	@Override
 	public String toString(){
		return 
			"GetDestinationResponse{" + 
			"responseStatus = '" + responseStatus + '\'' + 
			",failureResponse = '" + failureResponse + '\'' + 
			",destinationOutput = '" + destinationOutput + '\'' + 
			"}";
		}
}