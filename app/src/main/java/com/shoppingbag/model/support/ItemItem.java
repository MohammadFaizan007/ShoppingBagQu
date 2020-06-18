package com.shoppingbag.model.support;

import com.google.gson.annotations.SerializedName;

public class ItemItem{

	@SerializedName("SupportName")
	private String supportName;

	@SerializedName("IsActive")
	private boolean isActive;

	@SerializedName("PK_SupportId")
	private int pKSupportId;

	public void setSupportName(String supportName){
		this.supportName = supportName;
	}

	public String getSupportName(){
		return supportName;
	}

	public void setIsActive(boolean isActive){
		this.isActive = isActive;
	}

	public boolean isIsActive(){
		return isActive;
	}

	public void setPKSupportId(int pKSupportId){
		this.pKSupportId = pKSupportId;
	}

	public int getPKSupportId(){
		return pKSupportId;
	}
}