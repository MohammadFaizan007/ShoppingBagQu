package com.shoppingbag.wallet.model;


import com.google.gson.annotations.SerializedName;


public class ResultItem{

	@SerializedName("amount")
	private int amount;

	@SerializedName("paymentdate")
	private String paymentdate;

	@SerializedName("paymentmode")
	private String paymentmode;

	@SerializedName("filepath")
	private String filepath;

	@SerializedName("remark")
	private String remark;

	@SerializedName("transactionId")
	private Object transactionId;

	@SerializedName("memberId")
	private int memberId;

	public void setAmount(int amount){
		this.amount = amount;
	}

	public int getAmount(){
		return amount;
	}

	public void setPaymentdate(String paymentdate){
		this.paymentdate = paymentdate;
	}

	public String getPaymentdate(){
		return paymentdate;
	}

	public void setPaymentmode(String paymentmode){
		this.paymentmode = paymentmode;
	}

	public String getPaymentmode(){
		return paymentmode;
	}

	public void setFilepath(String filepath){
		this.filepath = filepath;
	}

	public String getFilepath(){
		return filepath;
	}

	public void setRemark(String remark){
		this.remark = remark;
	}

	public String getRemark(){
		return remark;
	}

	public void setTransactionId(Object transactionId){
		this.transactionId = transactionId;
	}

	public Object getTransactionId(){
		return transactionId;
	}

	public void setMemberId(int memberId){
		this.memberId = memberId;
	}

	public int getMemberId(){
		return memberId;
	}

	@Override
 	public String toString(){
		return 
			"ResultItem{" + 
			"amount = '" + amount + '\'' + 
			",paymentdate = '" + paymentdate + '\'' + 
			",paymentmode = '" + paymentmode + '\'' + 
			",filepath = '" + filepath + '\'' + 
			",remark = '" + remark + '\'' + 
			",transactionId = '" + transactionId + '\'' + 
			",memberId = '" + memberId + '\'' + 
			"}";
		}
}