package com.shoppingbag.model.response;

import com.google.gson.annotations.SerializedName;

public class PayoutDetailsListItem {

	@SerializedName("fK_MemID")
	private String fKMemID;

	@SerializedName("loginID")
	private String loginID;

	@SerializedName("processingCharges")
	private String processingCharges;

	@SerializedName("netAmount")
	private String netAmount;

	@SerializedName("directIncome")
	private String directIncome;

	@SerializedName("payoutNo")
	private String payoutNo;

	@SerializedName("grossAmount")
	private String grossAmount;

	@SerializedName("sbUpline")
	private String sbUpline;

	@SerializedName("closingDate")
	private String closingDate;

	@SerializedName("advance")
	private String advance;

	@SerializedName("adjustedAmount")
	private String adjustedAmount;

	@SerializedName("name")
	private String name;

	@SerializedName("tdsAmount")
	private String tdsAmount;

	@SerializedName("binaryIncome")
	private String binaryIncome;

	@SerializedName("cashBack")
	private String cashBack;

	public void setFKMemID(String fKMemID){
		this.fKMemID = fKMemID;
	}

	public String getFKMemID(){
		return fKMemID;
	}

	public void setLoginID(String loginID){
		this.loginID = loginID;
	}

	public String getLoginID(){
		return loginID;
	}

	public void setProcessingCharges(String processingCharges){
		this.processingCharges = processingCharges;
	}

	public String getProcessingCharges(){
		return processingCharges;
	}

	public void setNetAmount(String netAmount){
		this.netAmount = netAmount;
	}

	public String getNetAmount(){
		return netAmount;
	}

	public void setDirectIncome(String directIncome){
		this.directIncome = directIncome;
	}

	public String getDirectIncome(){
		return directIncome;
	}

	public void setPayoutNo(String payoutNo){
		this.payoutNo = payoutNo;
	}

	public String getPayoutNo(){
		return payoutNo;
	}

	public void setGrossAmount(String grossAmount){
		this.grossAmount = grossAmount;
	}

	public String getGrossAmount(){
		return grossAmount;
	}

	public void setSbUpline(String sbUpline){
		this.sbUpline = sbUpline;
	}

	public String getSbUpline(){
		return sbUpline;
	}

	public void setClosingDate(String closingDate){
		this.closingDate = closingDate;
	}

	public String getClosingDate(){
		return closingDate;
	}

	public void setAdvance(String advance){
		this.advance = advance;
	}

	public String getAdvance(){
		return advance;
	}

	public void setAdjustedAmount(String adjustedAmount){
		this.adjustedAmount = adjustedAmount;
	}

	public String getAdjustedAmount(){
		return adjustedAmount;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setTdsAmount(String tdsAmount){
		this.tdsAmount = tdsAmount;
	}

	public String getTdsAmount(){
		return tdsAmount;
	}

	public void setBinaryIncome(String binaryIncome){
		this.binaryIncome = binaryIncome;
	}

	public String getBinaryIncome(){
		return binaryIncome;
	}

	public void setCashBack(String cashBack){
		this.cashBack = cashBack;
	}

	public String getCashBack(){
		return cashBack;
	}

	@Override
 	public String toString(){
		return 
			"PayoutDetailsListItem{" + 
			"fK_MemID = '" + fKMemID + '\'' + 
			",loginID = '" + loginID + '\'' + 
			",processingCharges = '" + processingCharges + '\'' + 
			",netAmount = '" + netAmount + '\'' + 
			",directIncome = '" + directIncome + '\'' + 
			",payoutNo = '" + payoutNo + '\'' + 
			",grossAmount = '" + grossAmount + '\'' + 
			",sbUpline = '" + sbUpline + '\'' + 
			",closingDate = '" + closingDate + '\'' + 
			",advance = '" + advance + '\'' + 
			",adjustedAmount = '" + adjustedAmount + '\'' + 
			",name = '" + name + '\'' + 
			",tdsAmount = '" + tdsAmount + '\'' + 
			",binaryIncome = '" + binaryIncome + '\'' + 
			",cashBack = '" + cashBack + '\'' + 
			"}";
		}
}