package com.shoppingbag.model.response.bus_response.requestpojo;

import com.google.gson.annotations.SerializedName;

public class RequestCancellation{

	@SerializedName("CancellationInput")
	private CancellationInput cancellationInput;

	public void setCancellationInput(CancellationInput cancellationInput){
		this.cancellationInput = cancellationInput;
	}

	public CancellationInput getCancellationInput(){
		return cancellationInput;
	}
}