package com.shoppingbag.model.bill;

import com.google.gson.annotations.SerializedName;

public class ResponseBroadBandVerification {

    @SerializedName("DATE")
    private String date;

    @SerializedName("TRNXSTATUS")
    private String trnxstatus;

    @SerializedName("REFERENCENO")
    private String referenceno;

    @SerializedName("SESSION")
    private String session;

    @SerializedName("TRANSID")
    private String transid;

    @SerializedName("ERRMSG")
    private String errmsg;

    @SerializedName("ERROR")
    private String error;

    @SerializedName("Message")
    private String message;

    @SerializedName("RESULT")
    private String result;

    @SerializedName("AUTHCODE")
    private String authcode;

    @SerializedName("BILLERCONFIRMATIONNUMBER")
    private String billerconfirmationnumber;

    @SerializedName("PRICE")
    private String price;

    @SerializedName("ADDINFO")
    private String addinfo;

    @SerializedName("Status")
    private String status;

    public String getDate() {
        return date;
    }

    public String getTrnxstatus() {
        return trnxstatus;
    }

    public String getReferenceno() {
        return referenceno;
    }

    public String getSession() {
        return session;
    }

    public String getTransid() {
        return transid;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getResult() {
        return result;
    }

    public String getAuthcode() {
        return authcode;
    }

    public String getBillerconfirmationnumber() {
        return billerconfirmationnumber;
    }

    public String getPrice() {
        return price;
    }

    public String getAddinfo() {
        return addinfo;
    }

    public String getStatus() {
        return status;
    }
}