package com.shoppingbag.model.response.m2p.requestkit;

import com.google.gson.annotations.SerializedName;

public class ResponseKit {

    @SerializedName("result")
    private Result result;

    @SerializedName("response")
    private String response;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return
                "ResponseKit{" +
                        "result = '" + result + '\'' +
                        ",response = '" + response + '\'' +
                        "}";
    }
}