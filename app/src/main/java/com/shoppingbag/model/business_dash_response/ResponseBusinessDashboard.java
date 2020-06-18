package com.shoppingbag.model.business_dash_response;

import java.util.List;
import com.google.gson.annotations.SerializedName;


public class ResponseBusinessDashboard{

	@SerializedName("Order")
	private List<OrderItem> order;

	@SerializedName("response")
	private String response;

	@SerializedName("dashboardDetails")
	private DashboardDetails dashboardDetails;

	@SerializedName("message")
	private Object message;

	@SerializedName("totalCount")
	private int totalCount;

	@SerializedName("TransId")
	private int transId;

	public void setOrder(List<OrderItem> order){
		this.order = order;
	}

	public List<OrderItem> getOrder(){
		return order;
	}

	public void setResponse(String response){
		this.response = response;
	}

	public String getResponse(){
		return response;
	}

	public void setDashboardDetails(DashboardDetails dashboardDetails){
		this.dashboardDetails = dashboardDetails;
	}

	public DashboardDetails getDashboardDetails(){
		return dashboardDetails;
	}

	public void setMessage(Object message){
		this.message = message;
	}

	public Object getMessage(){
		return message;
	}

	public void setTotalCount(int totalCount){
		this.totalCount = totalCount;
	}

	public int getTotalCount(){
		return totalCount;
	}

	public void setTransId(int transId){
		this.transId = transId;
	}

	public int getTransId(){
		return transId;
	}
}