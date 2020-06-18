package com.shoppingbag.model.bill;

import com.google.gson.annotations.SerializedName;

public class ResponseElectricityVerification {

    @SerializedName("date")
    private String date;

    @SerializedName("RESULT")
    private String result;

    @SerializedName("AUTHCODE")
    private String authcode;

    @SerializedName("TRNXSTATUS")
    private String trnxstatus;

    @SerializedName("SESSION")
    private String session;

    @SerializedName("TRANSID")
    private String transid;

    @SerializedName("PRICE")
    private String price;

    @SerializedName("ERRMSG")
    private String errmsg;

    @SerializedName("ADDInfo")
    private String addInfo;

    @SerializedName("ERROR")
    private String error;

    @SerializedName("Message")
    private Object message;

    @SerializedName("Status")
    private Object status;

    public String getDate() {
        return date;
    }

    public String getResult() {
        return result;
    }

    public String getAuthcode() {
        return authcode;
    }

    public String getTrnxstatus() {
        return trnxstatus;
    }

    public String getSession() {
        return session;
    }

    public String getTransid() {
        return transid;
    }

    public String getPrice() {
        return price;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public String getAddInfo() {
        return addInfo;
    }

    public String getError() {
        return error;
    }

    public Object getMessage() {
        return message;
    }

    public Object getStatus() {
        return status;
    }
}