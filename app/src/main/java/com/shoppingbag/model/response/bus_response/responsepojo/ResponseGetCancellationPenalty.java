package com.shoppingbag.model.response.bus_response.responsepojo;

import com.google.gson.annotations.SerializedName;

public class ResponseGetCancellationPenalty{

	@SerializedName("FailureRemarks")
	private Object failureRemarks;

	@SerializedName("CancellationPenaltyOutput")
	private CancellationPenaltyOutput cancellationPenaltyOutput;

	@SerializedName("ResponseStatus")
	private String responseStatus;

	public void setFailureRemarks(Object failureRemarks){
		this.failureRemarks = failureRemarks;
	}

	public Object getFailureRemarks(){
		return failureRemarks;
	}

	public void setCancellationPenaltyOutput(CancellationPenaltyOutput cancellationPenaltyOutput){
		this.cancellationPenaltyOutput = cancellationPenaltyOutput;
	}

	public CancellationPenaltyOutput getCancellationPenaltyOutput(){
		return cancellationPenaltyOutput;
	}

	public void setResponseStatus(String responseStatus){
		this.responseStatus = responseStatus;
	}

	public String getResponseStatus(){
		return responseStatus;
	}

	@Override
 	public String toString(){
		return 
			"ResponseGetCancellationPenalty{" + 
			"failureRemarks = '" + failureRemarks + '\'' + 
			",cancellationPenaltyOutput = '" + cancellationPenaltyOutput + '\'' + 
			",responseStatus = '" + responseStatus + '\'' + 
			"}";
		}
}