package com.shoppingbag.model.response.wallet;

import com.google.gson.annotations.SerializedName;

public class ResponseNewWalletRequest {

    @SerializedName("response")
    private String response;

    @SerializedName("TransId")
    private String TransId;

    @SerializedName("message")
    private String message;

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

    public String getTransId() {
        return TransId;
    }

    public void setTransId(String transId) {
        TransId = transId;
    }
}