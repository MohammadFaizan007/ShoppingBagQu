package com.shoppingbag.model.response.m2p.login;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Result implements Serializable {

    @SerializedName("exception")
    private String exception;

    @SerializedName("result")
    private Result result;

    @SerializedName("pagination")
    private String pagination;

    @SerializedName("cardList")
    private String cardList;

    @SerializedName("cardStatusList")
    private String cardStatusList;

    @SerializedName("Dob")
    private String dob;

    @SerializedName("cardTypeList")
    private String cardTypeList;

    @SerializedName("kitList")
    private String kitList;

    @SerializedName("networkTypeList")
    private String networkTypeList;

    @SerializedName("expiryDateList")
    private String expiryDateList;

    @SerializedName("Name")
    private String name;

    public void setException(String exception) {
        this.exception = exception;
    }

    public String getException() {
        return exception;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Result getResult() {
        return result;
    }

    public void setPagination(String pagination) {
        this.pagination = pagination;
    }

    public String getPagination() {
        return pagination;
    }

    public void setCardList(String cardList) {
        this.cardList = cardList;
    }

    public String getCardList() {
        return cardList;
    }

    public void setCardStatusList(String cardStatusList) {
        this.cardStatusList = cardStatusList;
    }

    public String getCardStatusList() {
        return cardStatusList;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getDob() {
        return dob;
    }

    public void setCardTypeList(String cardTypeList) {
        this.cardTypeList = cardTypeList;
    }

    public String getCardTypeList() {
        return cardTypeList;
    }

    public void setKitList(String kitList) {
        this.kitList = kitList;
    }

    public String getKitList() {
        return kitList;
    }

    public void setNetworkTypeList(String networkTypeList) {
        this.networkTypeList = networkTypeList;
    }

    public String getNetworkTypeList() {
        return networkTypeList;
    }

    public void setExpiryDateList(String expiryDateList) {
        this.expiryDateList = expiryDateList;
    }

    public String getExpiryDateList() {
        return expiryDateList;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}