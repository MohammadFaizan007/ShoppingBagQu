package com.shoppingbag.model.domesticflight.requestmodel;

import com.google.gson.annotations.SerializedName;

public class RequestGetTax {

    @SerializedName("TaxInput")
    private TaxInput taxInput;

    @SerializedName("Authentication")
    private Authentication authentication;

    @SerializedName("UserTrackId")
    private String userTrackId;

    public TaxInput getTaxInput() {
        return taxInput;
    }

    public void setTaxInput(TaxInput taxInput) {
        this.taxInput = taxInput;
    }

    public Authentication getAuthentication() {
        return authentication;
    }

    public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }

    public String getUserTrackId() {
        return userTrackId;
    }

    public void setUserTrackId(String userTrackId) {
        this.userTrackId = userTrackId;
    }

    @Override
    public String toString() {
        return
                "RequestGetTax{" +
                        "taxInput = '" + taxInput + '\'' +
                        ",authentication = '" + authentication + '\'' +
                        ",userTrackId = '" + userTrackId + '\'' +
                        "}";
    }
}