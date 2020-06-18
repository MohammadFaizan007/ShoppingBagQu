package com.shoppingbag.model.mobile_recharge.requestmodel;

import com.google.gson.annotations.SerializedName;

public class RequestGetRechargeOperators {

    @SerializedName("Authentication")
    private Authentication authentication;

    public Authentication getAuthentication() {
        return authentication;
    }

    public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }

    @Override
    public String toString() {
        return
                "RequestGetRechargeOperators{" +
                        "authentication = '" + authentication + '\'' +
                        "}";
    }
}