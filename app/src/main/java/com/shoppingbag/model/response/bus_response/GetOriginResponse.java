package com.shoppingbag.model.response.bus_response;

import com.google.gson.annotations.SerializedName;

public class GetOriginResponse{

	@SerializedName("ResponseStatus")
	private String responseStatus;

	@SerializedName("OriginOutput")
	private OriginOutput originOutput;

	@SerializedName("FailureResponse")
	private Object failureResponse;

	public void setResponseStatus(String responseStatus){
		this.responseStatus = responseStatus;
	}

	public String getResponseStatus(){
		return responseStatus;
	}

	public void setOriginOutput(OriginOutput originOutput){
		this.originOutput = originOutput;
	}

	public OriginOutput getOriginOutput(){
		return originOutput;
	}

	public void setFailureResponse(Object failureResponse){
		this.failureResponse = failureResponse;
	}

	public Object getFailureResponse(){
		return failureResponse;
	}

	@Override
 	public String toString(){
		return 
			"GetOriginResponse{" + 
			"responseStatus = '" + responseStatus + '\'' + 
			",originOutput = '" + originOutput + '\'' + 
			",failureResponse = '" + failureResponse + '\'' + 
			"}";
		}
}