package com.shoppingbag.model.response.otp_req;

import com.google.gson.annotations.SerializedName;

public class ResponseRequestOTP {

    @SerializedName("isexpired")
    private String isexpired;

    @SerializedName("response")
    private String response;

    @SerializedName("message")
    private String message;

    public String getIsexpired() {
        return isexpired;
    }

    public void setIsexpired(String isexpired) {
        this.isexpired = isexpired;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return
                "ResponseRequestOTP{" +
                        "isexpired = '" + isexpired + '\'' +
                        ",response = '" + response + '\'' +
                        ",message = '" + message + '\'' +
                        "}";
    }
}