package com.shoppingbag.model.response.bus_response.requestpojo;

import com.google.gson.annotations.SerializedName;

public class RequestCancellationPolicy{

	@SerializedName("CancellationPolicyInput")
	private CancellationPolicyInput cancellationPolicyInput;

	@SerializedName("UserTrackId")
	private String userTrackId;

	public void setCancellationPolicyInput(CancellationPolicyInput cancellationPolicyInput){
		this.cancellationPolicyInput = cancellationPolicyInput;
	}

	public CancellationPolicyInput getCancellationPolicyInput(){
		return cancellationPolicyInput;
	}

	public void setUserTrackId(String userTrackId){
		this.userTrackId = userTrackId;
	}

	public String getUserTrackId(){
		return userTrackId;
	}
}