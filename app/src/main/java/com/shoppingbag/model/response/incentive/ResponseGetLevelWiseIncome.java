package com.shoppingbag.model.response.incentive;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseGetLevelWiseIncome {

    @SerializedName("response")
    private String response;

    @SerializedName("grossIncome")
    private String grossIncome;

    @SerializedName("tds")
    private String tds;

    @SerializedName("paidIncome")
    private String paidIncome;

    @SerializedName("totalCommission")
    private String totalCommission;

    @SerializedName("HoldCom")
    private String HoldCom;

    @SerializedName("Cleared")
    private String Cleared;

    @SerializedName("released")
    private String released;

    @SerializedName("hold")
    private String hold;

    @SerializedName("balance")
    private String balance;

    @SerializedName("unclearBalance")
    private String unclearBalance;

    @SerializedName("selfIncome")
    private String selfIncome;

    @SerializedName("netIncome")
    private String netIncome;

    @SerializedName("remark")
    private String remark;

    @SerializedName("listLevelWiseIncome")
    private List<ListLevelWiseIncomeItem> listLevelWiseIncome;

    public String getSelfIncome() {
        return selfIncome;
    }

    public void setSelfIncome(String selfIncome) {
        this.selfIncome = selfIncome;
    }

    public String getPaidIncome() {
        return paidIncome;
    }

    public String getUnclearBalance() {
        return unclearBalance;
    }

    public String getResponse() {
        return response;
    }

    public List<ListLevelWiseIncomeItem> getListLevelWiseIncome() {
        return listLevelWiseIncome;
    }

    public String getGrossIncome() {
        return grossIncome;
    }

    public void setGrossIncome(String grossIncome) {
        this.grossIncome = grossIncome;
    }

    public String getTds() {
        return tds;
    }

    public void setTds(String tds) {
        this.tds = tds;
    }

    public String getNetIncome() {
        return netIncome;
    }

    public void setNetIncome(String netIncome) {
        this.netIncome = netIncome;
    }

    public String getTotalCommission() {
        return totalCommission;
    }

    public void setTotalCommission(String totalCommission) {
        this.totalCommission = totalCommission;
    }

    public String getReleased() {
        return released;
    }

    public void setReleased(String released) {
        this.released = released;
    }

    public String getHold() {
        return hold;
    }

    public void setHold(String hold) {
        this.hold = hold;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getHoldCom() {
        return HoldCom;
    }

    public void setHoldCom(String holdCom) {
        HoldCom = holdCom;
    }

    public String getCleared() {
        return Cleared;
    }

    public void setCleared(String cleared) {
        Cleared = cleared;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}