package com.shoppingbag.model.domesticflight.responsemodel;

import com.google.gson.annotations.SerializedName;

public class FareRuleOutput {

    @SerializedName("FareRules")
    private String fareRules;

    public String getFareRules() {
        return fareRules;
    }

    public void setFareRules(String fareRules) {
        this.fareRules = fareRules;
    }

    @Override
    public String toString() {
        return
                "FareRuleOutput{" +
                        "fareRules = '" + fareRules + '\'' +
                        "}";
    }
}