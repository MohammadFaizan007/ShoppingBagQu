package com.shoppingbag.model.response.bus_response.responsepojo;

import com.google.gson.annotations.SerializedName;

public class ReprintOutput{

	@SerializedName("ReprintTicketDetails")
	private ReprintTicketDetails reprintTicketDetails;

	public void setReprintTicketDetails(ReprintTicketDetails reprintTicketDetails){
		this.reprintTicketDetails = reprintTicketDetails;
	}

	public ReprintTicketDetails getReprintTicketDetails(){
		return reprintTicketDetails;
	}

	@Override
 	public String toString(){
		return 
			"ReprintOutput{" + 
			"reprintTicketDetails = '" + reprintTicketDetails + '\'' + 
			"}";
		}
}