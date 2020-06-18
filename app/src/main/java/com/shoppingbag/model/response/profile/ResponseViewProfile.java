package com.shoppingbag.model.response.profile;

import com.google.gson.annotations.SerializedName;

public class ResponseViewProfile {

    @SerializedName("result")
    private Result result;

    @SerializedName("response")
    private String response;

    public void setResult(Result result) {
        this.result = result;
    }

    public Result getResult() {
        return result;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }
}