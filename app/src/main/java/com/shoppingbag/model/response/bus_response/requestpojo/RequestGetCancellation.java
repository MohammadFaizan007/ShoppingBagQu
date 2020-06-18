package com.shoppingbag.model.response.bus_response.requestpojo;

import com.google.gson.annotations.SerializedName;

public class RequestGetCancellation{

	@SerializedName("FK_MemID")
	private String fKMemID;

	@SerializedName("CancellationInput")
	private CancellationInput cancellationInput;

	public void setFKMemID(String fKMemID){
		this.fKMemID = fKMemID;
	}

	public String getFKMemID(){
		return fKMemID;
	}

	public void setCancellationInput(CancellationInput cancellationInput){
		this.cancellationInput = cancellationInput;
	}

	public CancellationInput getCancellationInput(){
		return cancellationInput;
	}

	@Override
 	public String toString(){
		return 
			"RequestGetCancellation{" + 
			"fK_MemID = '" + fKMemID + '\'' + 
			",cancellationInput = '" + cancellationInput + '\'' + 
			"}";
		}
}