package com.shoppingbag.model.response.wallet;

import com.google.gson.annotations.SerializedName;

public class ResultItem{

	@SerializedName("actionType")
	private String actionType;

	@SerializedName("amount")
	private double amount;

	@SerializedName("paymentdate")
	private String paymentdate;

	@SerializedName("transactionStatus")
	private String transactionStatus;

	@SerializedName("runningAmount")
	private double runningAmount;

	@SerializedName("drAmount")
	private double drAmount;

	@SerializedName("crAmount")
	private double crAmount;

	@SerializedName("remarks")
	private String remarks;

	@SerializedName("transactionId")
	private String transactionId;

	public void setActionType(String actionType){
		this.actionType = actionType;
	}

	public String getActionType(){
		return actionType;
	}

	public void setAmount(double amount){
		this.amount = amount;
	}

	public double getAmount(){
		return amount;
	}

	public void setPaymentdate(String paymentdate){
		this.paymentdate = paymentdate;
	}

	public String getPaymentdate(){
		return paymentdate;
	}

	public void setTransactionStatus(String transactionStatus){
		this.transactionStatus = transactionStatus;
	}

	public String getTransactionStatus(){
		return transactionStatus;
	}

	public void setRunningAmount(double runningAmount){
		this.runningAmount = runningAmount;
	}

	public double getRunningAmount(){
		return runningAmount;
	}

	public void setDrAmount(double drAmount){
		this.drAmount = drAmount;
	}

	public double getDrAmount(){
		return drAmount;
	}

	public void setCrAmount(double crAmount){
		this.crAmount = crAmount;
	}

	public double getCrAmount(){
		return crAmount;
	}

	public void setRemarks(String remarks){
		this.remarks = remarks;
	}

	public String getRemarks(){
		return remarks;
	}

	public void setTransactionId(String transactionId){
		this.transactionId = transactionId;
	}

	public String getTransactionId(){
		return transactionId;
	}
}