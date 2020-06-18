package com.shoppingbag.model.domesticflight.requestmodel;

import com.google.gson.annotations.SerializedName;

public class RequestgetFareRule {


    @SerializedName("UserTrackId")
    private String userTrackId;

    @SerializedName("FareRuleInput")
    private FareRuleInput fareRuleInput;

    public String getUserTrackId() {
        return userTrackId;
    }

    public void setUserTrackId(String userTrackId) {
        this.userTrackId = userTrackId;
    }

    public FareRuleInput getFareRuleInput() {
        return fareRuleInput;
    }

    public void setFareRuleInput(FareRuleInput fareRuleInput) {
        this.fareRuleInput = fareRuleInput;
    }

    @Override
    public String toString() {
        return
                "RequestgetFareRule{" +
                        ",userTrackId = '" + userTrackId + '\'' +
                        ",fareRuleInput = '" + fareRuleInput + '\'' +
                        "}";
    }
}