package com.shoppingbag.model.response.bus_response.responsepojo;

import com.google.gson.annotations.SerializedName;

public class CancellationPenaltyOutput{

	@SerializedName("ToCancelPNRDetails")
	private ToCancelPNRDetails toCancelPNRDetails;

	public void setToCancelPNRDetails(ToCancelPNRDetails toCancelPNRDetails){
		this.toCancelPNRDetails = toCancelPNRDetails;
	}

	public ToCancelPNRDetails getToCancelPNRDetails(){
		return toCancelPNRDetails;
	}

	@Override
 	public String toString(){
		return 
			"CancellationPenaltyOutput{" + 
			"toCancelPNRDetails = '" + toCancelPNRDetails + '\'' + 
			"}";
		}
}