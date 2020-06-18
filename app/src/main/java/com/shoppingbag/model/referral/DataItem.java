package com.shoppingbag.model.referral;


import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("InvitedBy")
	private String invitedBy;

	@SerializedName("LoginId")
	private String loginId;

	@SerializedName("TotalReferal")
	private int totalReferal;

	@SerializedName("MemberName")
	private String memberName;

	@SerializedName("Mobile")
	private String mobile;

	public void setInvitedBy(String invitedBy){
		this.invitedBy = invitedBy;
	}

	public String getInvitedBy(){
		return invitedBy;
	}

	public void setLoginId(String loginId){
		this.loginId = loginId;
	}

	public String getLoginId(){
		return loginId;
	}

	public void setTotalReferal(int totalReferal){
		this.totalReferal = totalReferal;
	}

	public int getTotalReferal(){
		return totalReferal;
	}

	public void setMemberName(String memberName){
		this.memberName = memberName;
	}

	public String getMemberName(){
		return memberName;
	}

	public void setMobile(String mobile){
		this.mobile = mobile;
	}

	public String getMobile(){
		return mobile;
	}
}