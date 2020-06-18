package com.shoppingbag.model.directmembermodel;

import com.google.gson.annotations.SerializedName;

public class Result{

	@SerializedName("TotalRefferal")
	private String totalRefferal;

	@SerializedName("TotalTeam")
	private String totalTeam;

	@SerializedName("Business")
	private String business;

	public void setTotalRefferal(String totalRefferal){
		this.totalRefferal = totalRefferal;
	}

	public String getTotalRefferal(){
		return totalRefferal;
	}

	public void setTotalTeam(String totalTeam){
		this.totalTeam = totalTeam;
	}

	public String getTotalTeam(){
		return totalTeam;
	}

	public void setBusiness(String business){
		this.business = business;
	}

	public String getBusiness(){
		return business;
	}

	@Override
 	public String toString(){
		return 
			"Result{" + 
			"totalRefferal = '" + totalRefferal + '\'' + 
			",totalTeam = '" + totalTeam + '\'' + 
			",business = '" + business + '\'' + 
			"}";
		}
}