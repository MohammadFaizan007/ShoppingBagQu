package com.shoppingbag.model.utility;

import com.google.gson.annotations.SerializedName;

public class ListItem{

	@SerializedName("date")
	private String date;

	@SerializedName("amount")
	private String amount;

	@SerializedName("remark")
	private String remark;

	@SerializedName("tag")
	private String tag;

	@SerializedName("transactionId")
	private String transactionId;

	@SerializedName("status")
	private String status;



	@SerializedName("ApprovedDate")
	private String ApprovedDate;

	@SerializedName("TransactionNo")
	private String TransactionNo;


	public void setApprovedDate(String ApprovedDate){
		this.ApprovedDate = ApprovedDate;
	}

	public String getApprovedDate(){
		return ApprovedDate;
	}

	public void setTransactionNo(String ApprovedDate){
		this.TransactionNo = TransactionNo;
	}

	public String getTransactionNo(){
		return TransactionNo;
	}





	public void setDate(String date){
		this.date = date;
	}

	public String getDate(){
		return date;
	}

	public void setAmount(String amount){
		this.amount = amount;
	}

	public String getAmount(){
		return amount;
	}

	public void setRemark(String remark){
		this.remark = remark;
	}

	public String getRemark(){
		return remark;
	}

	public void setTag(String tag){
		this.tag = tag;
	}

	public String getTag(){
		return tag;
	}

	public void setTransactionId(String transactionId){
		this.transactionId = transactionId;
	}

	public String getTransactionId(){
		return transactionId;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}