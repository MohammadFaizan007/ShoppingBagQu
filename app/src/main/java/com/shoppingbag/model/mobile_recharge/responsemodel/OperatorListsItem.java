package com.shoppingbag.model.mobile_recharge.responsemodel;

import com.google.gson.annotations.SerializedName;

public class OperatorListsItem {

    @SerializedName("OperatorDescritpion")
    private String operatorDescritpion;

    @SerializedName("Commission")
    private double commission;

    @SerializedName("OperatorCode")
    private String operatorCode;

    @SerializedName("RechargeType")
    private String rechargeType;

    public String getOperatorDescritpion() {
        return operatorDescritpion;
    }

    public void setOperatorDescritpion(String operatorDescritpion) {
        this.operatorDescritpion = operatorDescritpion;
    }

    public double getCommission() {
        return commission;
    }

    public void setCommission(double commission) {
        this.commission = commission;
    }

    public String getOperatorCode() {
        return operatorCode;
    }

    public void setOperatorCode(String operatorCode) {
        this.operatorCode = operatorCode;
    }

    public String getRechargeType() {
        return rechargeType;
    }

    public void setRechargeType(String rechargeType) {
        this.rechargeType = rechargeType;
    }

    @Override
    public String toString() {
        return
                "OperatorListsItem{" +
                        "operatorDescritpion = '" + operatorDescritpion + '\'' +
                        ",commission = '" + commission + '\'' +
                        ",operatorCode = '" + operatorCode + '\'' +
                        ",rechargeType = '" + rechargeType + '\'' +
                        "}";
    }
}