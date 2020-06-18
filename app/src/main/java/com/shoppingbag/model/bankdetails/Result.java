package com.shoppingbag.model.bankdetails;


import com.google.gson.annotations.SerializedName;


public class Result{

	@SerializedName("address")
	private String address;

	@SerializedName("accountno")
	private String accountno;

	@SerializedName("bankname")
	private String bankname;

	@SerializedName("ifsccode")
	private String ifsccode;

	@SerializedName("accountmembername")
	private String accountmembername;

	@SerializedName("branchname")
	private String branchname;

	public void setAddress(String address){
		this.address = address;
	}

	public String getAddress(){
		return address;
	}

	public void setAccountno(String accountno){
		this.accountno = accountno;
	}

	public String getAccountno(){
		return accountno;
	}

	public void setBankname(String bankname){
		this.bankname = bankname;
	}

	public String getBankname(){
		return bankname;
	}

	public void setIfsccode(String ifsccode){
		this.ifsccode = ifsccode;
	}

	public String getIfsccode(){
		return ifsccode;
	}

	public void setAccountmembername(String accountmembername){
		this.accountmembername = accountmembername;
	}

	public String getAccountmembername(){
		return accountmembername;
	}

	public void setBranchname(String branchname){
		this.branchname = branchname;
	}

	public String getBranchname(){
		return branchname;
	}

	@Override
 	public String toString(){
		return 
			"Result{" + 
			"address = '" + address + '\'' + 
			",accountno = '" + accountno + '\'' + 
			",bankname = '" + bankname + '\'' + 
			",ifsccode = '" + ifsccode + '\'' + 
			",accountmembername = '" + accountmembername + '\'' + 
			",branchname = '" + branchname + '\'' + 
			"}";
		}
}