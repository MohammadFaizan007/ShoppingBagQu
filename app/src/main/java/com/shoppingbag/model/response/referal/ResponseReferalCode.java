package com.shoppingbag.model.response.referal;

import com.google.gson.annotations.SerializedName;

public class ResponseReferalCode {

    @SerializedName("response")
    private String response;

    @SerializedName("name")
    private String referalName;

    public String getResponse() {
        return response;
    }

    public String getReferalName() {
        return referalName;
    }
}