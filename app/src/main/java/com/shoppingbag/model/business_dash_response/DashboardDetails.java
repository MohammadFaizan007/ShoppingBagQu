package com.shoppingbag.model.business_dash_response;


import com.google.gson.annotations.SerializedName;

public class DashboardDetails{

	@SerializedName("AllOrders")
	private float allOrders;

	@SerializedName("MagixMoves")
	private float magixMoves;

	@SerializedName("CommissionWallet")
	private float commissionWallet;

	@SerializedName("TeamBusiness")
	private float teamBusiness;

	@SerializedName("TodayBusiness")
	private float todayBusiness;

	@SerializedName("RepurchaseIncomeTeam")
	private float repurchaseIncomeTeam;

	@SerializedName("AllBusiness")
	private float allBusiness;

	@SerializedName("Teamsize")
	private int teamsize;


	public int getNetIncome() {
		return NetIncome;
	}

	public void setNetIncome(int netIncome) {
		NetIncome = netIncome;
	}

	public int getDirectCount() {
		return DirectCount;
	}

	public void setDirectCount(int directCount) {
		DirectCount = directCount;
	}

	public int getServiceCharges() {
		return ServiceCharges;
	}

	public void setServiceCharges(int serviceCharges) {
		ServiceCharges = serviceCharges;
	}

	public int getTDS() {
		return TDS;
	}

	public void setTDS(int TDS) {
		this.TDS = TDS;
	}

	public int getGross() {
		return Gross;
	}

	public void setGross(int gross) {
		Gross = gross;
	}

	@SerializedName("NetIncome")
	private int NetIncome;
	@SerializedName("DirectCount")
	private int DirectCount;

	@SerializedName("ServiceCharges")
	private int ServiceCharges;
	@SerializedName("TDS")
	private int TDS;
	@SerializedName("Gross")
	private int Gross;



	@SerializedName("ReferralIncome")
	private float referralIncome;

	@SerializedName("DreamyWallet")
	private double dreamyWallet;

	@SerializedName("CashbackWallet")
	private float cashbackWallet;

	@SerializedName("LifeTimeIncome")
	private float lifeTimeIncome;

	@SerializedName("RepurchaseIncomeSelf")
	private float repurchaseIncomeSelf;

	public void setAllOrders(float allOrders){
		this.allOrders = allOrders;
	}

	public float getAllOrders(){
		return allOrders;
	}

	public void setMagixMoves(float magixMoves){
		this.magixMoves = magixMoves;
	}

	public float getMagixMoves(){
		return magixMoves;
	}

	public void setCommissionWallet(float commissionWallet){
		this.commissionWallet = commissionWallet;
	}

	public float getCommissionWallet(){
		return commissionWallet;
	}

	public void setTeamBusiness(float teamBusiness){
		this.teamBusiness = teamBusiness;
	}

	public float getTeamBusiness(){
		return teamBusiness;
	}

	public void setTodayBusiness(float todayBusiness){
		this.todayBusiness = todayBusiness;
	}

	public float getTodayBusiness(){
		return todayBusiness;
	}

	public void setRepurchaseIncomeTeam(float repurchaseIncomeTeam){
		this.repurchaseIncomeTeam = repurchaseIncomeTeam;
	}

	public float getRepurchaseIncomeTeam(){
		return repurchaseIncomeTeam;
	}

	public void setAllBusiness(float allBusiness){
		this.allBusiness = allBusiness;
	}

	public float getAllBusiness(){
		return allBusiness;
	}

	public void setTeamsize(int teamsize){
		this.teamsize = teamsize;
	}

	public int getTeamsize(){
		return teamsize;
	}

	public void setReferralIncome(float referralIncome){
		this.referralIncome = referralIncome;
	}

	public float getReferralIncome(){
		return referralIncome;
	}

	public void setDreamyWallet(double dreamyWallet){
		this.dreamyWallet = dreamyWallet;
	}

	public double getDreamyWallet(){
		return dreamyWallet;
	}

	public void setCashbackWallet(float cashbackWallet){
		this.cashbackWallet = cashbackWallet;
	}

	public float getCashbackWallet(){
		return cashbackWallet;
	}

	public void setLifeTimeIncome(float lifeTimeIncome){
		this.lifeTimeIncome = lifeTimeIncome;
	}

	public float getLifeTimeIncome(){
		return lifeTimeIncome;
	}

	public void setRepurchaseIncomeSelf(int repurchaseIncomeSelf){
		this.repurchaseIncomeSelf = repurchaseIncomeSelf;
	}

	public float getRepurchaseIncomeSelf(){
		return repurchaseIncomeSelf;
	}
}