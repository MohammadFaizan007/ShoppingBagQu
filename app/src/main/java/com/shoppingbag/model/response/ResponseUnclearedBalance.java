package com.shoppingbag.model.response;

import com.google.gson.annotations.SerializedName;

public class ResponseUnclearedBalance{

	@SerializedName("kitNo")
	private String kitNo;

	@SerializedName("bagAmount")
	private String bagAmount;

	@SerializedName("cardType")
	private String cardType;

	@SerializedName("fK_ToId")
	private String fKToId;

	@SerializedName("panCardStatus")
	private int panCardStatus;

	@SerializedName("totalWalletAmount")
	private String totalWalletAmount;

	@SerializedName("totalCommission")
	private String totalCommission;

	@SerializedName("KycStatus")
	private String kycStatus;

	@SerializedName("unclearedamount")
	private String unclearedamount;

	@SerializedName("bankStatus")
	private int bankStatus;

	@SerializedName("selfincome")
	private String selfincome;

	@SerializedName("response")
	private String response;

	@SerializedName("adharStatus")
	private int adharStatus;

	@SerializedName("holdAmount")
	private String holdAmount;

	@SerializedName("holdReason")
	private String holdReason;

	@SerializedName("m2PStatus")
	private String m2PStatus;

	@SerializedName("notice")
	private String notice;

	public void setKitNo(String kitNo){
		this.kitNo = kitNo;
	}

	public String getKitNo(){
		return kitNo;
	}

	public void setBagAmount(String bagAmount){
		this.bagAmount = bagAmount;
	}

	public String getBagAmount(){
		return bagAmount;
	}

	public void setCardType(String cardType){
		this.cardType = cardType;
	}

	public String getCardType(){
		return cardType;
	}

	public void setFKToId(String fKToId){
		this.fKToId = fKToId;
	}

	public String getFKToId(){
		return fKToId;
	}

	public void setPanCardStatus(int panCardStatus){
		this.panCardStatus = panCardStatus;
	}

	public int getPanCardStatus(){
		return panCardStatus;
	}

	public void setTotalWalletAmount(String totalWalletAmount){
		this.totalWalletAmount = totalWalletAmount;
	}

	public String getTotalWalletAmount(){
		return totalWalletAmount;
	}

	public void setTotalCommission(String totalCommission){
		this.totalCommission = totalCommission;
	}

	public String getTotalCommission(){
		return totalCommission;
	}

	public void setKycStatus(String kycStatus){
		this.kycStatus = kycStatus;
	}

	public String getKycStatus(){
		return kycStatus;
	}

	public void setUnclearedamount(String unclearedamount){
		this.unclearedamount = unclearedamount;
	}

	public String getUnclearedamount(){
		return unclearedamount;
	}

	public void setBankStatus(int bankStatus){
		this.bankStatus = bankStatus;
	}

	public int getBankStatus(){
		return bankStatus;
	}

	public void setSelfincome(String selfincome){
		this.selfincome = selfincome;
	}

	public String getSelfincome(){
		return selfincome;
	}

	public void setResponse(String response){
		this.response = response;
	}

	public String getResponse(){
		return response;
	}

	public void setAdharStatus(int adharStatus){
		this.adharStatus = adharStatus;
	}

	public int getAdharStatus(){
		return adharStatus;
	}

	public void setHoldAmount(String holdAmount){
		this.holdAmount = holdAmount;
	}

	public String getHoldAmount(){
		return holdAmount;
	}

	public void setHoldReason(String holdReason){
		this.holdReason = holdReason;
	}

	public String getHoldReason(){
		return holdReason;
	}

	public void setM2PStatus(String m2PStatus){
		this.m2PStatus = m2PStatus;
	}

	public String getM2PStatus(){
		return m2PStatus;
	}

	public void setNotice(String notice){
		this.notice = notice;
	}

	public String getNotice(){
		return notice;
	}
}