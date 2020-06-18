package com.shoppingbag.model.wallet;

import com.google.gson.annotations.SerializedName;

public class Result{

	@SerializedName("CashbackWalletAmount")
	private double cashbackWalletAmount;

	@SerializedName("DreamyWalletAmount")
	private double dreamyWalletAmount;

	@SerializedName("CommisionWalletAmount")
	private double commisionWalletAmount;

	public void setCashbackWalletAmount(double cashbackWalletAmount){
		this.cashbackWalletAmount = cashbackWalletAmount;
	}

	public double getCashbackWalletAmount(){
		return cashbackWalletAmount;
	}

	public void setDreamyWalletAmount(double dreamyWalletAmount){
		this.dreamyWalletAmount = dreamyWalletAmount;
	}

	public double getDreamyWalletAmount(){
		return dreamyWalletAmount;
	}

	public double getCommisionWalletAmount() {
		return commisionWalletAmount;
	}

	public void setCommisionWalletAmount(double commisionWalletAmount) {
		this.commisionWalletAmount = commisionWalletAmount;
	}
}