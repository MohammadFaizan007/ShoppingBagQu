package com.shoppingbag.model.domesticflight.responsemodel;

import com.google.gson.annotations.SerializedName;

public class ResponseGetTax {

    @SerializedName("TaxOutput")
    private TaxOutput taxOutput;

    @SerializedName("ResponseStatus")
    private int responseStatus;

    @SerializedName("UserTrackId")
    private String userTrackId;

    public TaxOutput getTaxOutput() {
        return taxOutput;
    }

    public void setTaxOutput(TaxOutput taxOutput) {
        this.taxOutput = taxOutput;
    }

    public int getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(int responseStatus) {
        this.responseStatus = responseStatus;
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
                "ResponseGetTax{" +
                        "taxOutput = '" + taxOutput + '\'' +
                        ",responseStatus = '" + responseStatus + '\'' +
                        ",userTrackId = '" + userTrackId + '\'' +
                        "}";
    }
}