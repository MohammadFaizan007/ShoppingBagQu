package com.shoppingbag.model.response;

import com.google.gson.annotations.SerializedName;

public class BankDetails{

	@SerializedName("CommissionAmount")
	private String commissionAmount;

	@SerializedName("bankName")
	private String bankName;

	@SerializedName("accountNumber")
	private String accountNumber;

	@SerializedName("ifscCode")
	private String ifscCode;

	@SerializedName("cardNumber")
	private String cardNumber;

	public void setCommissionAmount(String commissionAmount){
		this.commissionAmount = commissionAmount;
	}

	public String getCommissionAmount(){
		return commissionAmount;
	}

	public void setBankName(String bankName){
		this.bankName = bankName;
	}

	public String getBankName(){
		return bankName;
	}

	public void setAccountNumber(String accountNumber){
		this.accountNumber = accountNumber;
	}

	public String getAccountNumber(){
		return accountNumber;
	}

	public void setIfscCode(String ifscCode){
		this.ifscCode = ifscCode;
	}

	public String getIfscCode(){
		return ifscCode;
	}

	public void setCardNumber(String cardNumber){
		this.cardNumber = cardNumber;
	}

	public String getCardNumber(){
		return cardNumber;
	}
}