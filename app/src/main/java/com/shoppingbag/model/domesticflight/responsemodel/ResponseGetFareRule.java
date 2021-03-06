package com.shoppingbag.model.domesticflight.responsemodel;

import com.google.gson.annotations.SerializedName;

public class ResponseGetFareRule {

    @SerializedName("FareRuleOutput")
    private FareRuleOutput fareRuleOutput;

    @SerializedName("ResponseStatus")
    private int responseStatus;

    @SerializedName("UserTrackId")
    private String userTrackId;

    public FareRuleOutput getFareRuleOutput() {
        return fareRuleOutput;
    }

    public void setFareRuleOutput(FareRuleOutput fareRuleOutput) {
        this.fareRuleOutput = fareRuleOutput;
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
                "ResponseGetFareRule{" +
                        "fareRuleOutput = '" + fareRuleOutput + '\'' +
                        ",responseStatus = '" + responseStatus + '\'' +
                        ",userTrackId = '" + userTrackId + '\'' +
                        "}";
    }
}