package com.shoppingbag.model.response.m2p.registration;

import com.google.gson.annotations.SerializedName;

import com.shoppingbag.model.response.m2p.requestkit.Result;

public class ResponseM2PRegistration {

    @SerializedName("result")
    private Result result;

    @SerializedName("response")
    private String response;

    public Result getResult() {
        return result;
    }

    public String getResponse() {
        return response;
    }
}