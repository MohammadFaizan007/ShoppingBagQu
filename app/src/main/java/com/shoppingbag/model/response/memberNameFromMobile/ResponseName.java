package com.shoppingbag.model.response.memberNameFromMobile;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseName {

    @SerializedName("result")
    private List<ResultItem> result;

    @SerializedName("response")
    private String response;
    @SerializedName("message")
    private String message;

    public String getMessage() {
        return message;
    }

    public List<ResultItem> getResult() {
        return result;
    }

    public String getResponse() {
        return response;
    }
}