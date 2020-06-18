package com.shoppingbag.model.jioPrepaid;

import com.google.gson.annotations.SerializedName;

public class ResponseJioBrowsePlanPrepaid {

    @SerializedName("date")
    private String date;

    @SerializedName("result")
    private String result;

    @SerializedName("session")
    private String session;

    @SerializedName("errmsg")
    private String errmsg;

    @SerializedName("error")
    private String error;

    @SerializedName("addinfo")
    private ResponseAddInfoJio addinfo;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public ResponseAddInfoJio getAddinfo() {
        return addinfo;
    }

    public void setAddinfo(ResponseAddInfoJio addinfo) {
        this.addinfo = addinfo;
    }

    @Override
    public String toString() {
        return
                "ResponseJioBrowsePlanPrepaid{" +
                        "date = '" + date + '\'' +
                        ",result = '" + result + '\'' +
                        ",session = '" + session + '\'' +
                        ",errmsg = '" + errmsg + '\'' +
                        ",error = '" + error + '\'' +
                        ",addinfo = '" + addinfo + '\'' +
                        "}";
    }
}