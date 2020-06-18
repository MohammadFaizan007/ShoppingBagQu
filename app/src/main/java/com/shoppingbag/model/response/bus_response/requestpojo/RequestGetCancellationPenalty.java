package com.shoppingbag.model.response.bus_response.requestpojo;

import com.google.gson.annotations.SerializedName;

public class RequestGetCancellationPenalty{

	@SerializedName("CancellationPenaltyInput")
	private CancellationPenaltyInput cancellationPenaltyInput;

	public void setCancellationPenaltyInput(CancellationPenaltyInput cancellationPenaltyInput){
		this.cancellationPenaltyInput = cancellationPenaltyInput;
	}

	public CancellationPenaltyInput getCancellationPenaltyInput(){
		return cancellationPenaltyInput;
	}

	@Override
 	public String toString(){
		return 
			"RequestGetCancellationPenalty{" + 
			"cancellationPenaltyInput = '" + cancellationPenaltyInput + '\'' + 
			"}";
		}
}