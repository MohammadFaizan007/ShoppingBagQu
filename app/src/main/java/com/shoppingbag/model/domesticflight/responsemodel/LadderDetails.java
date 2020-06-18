package com.shoppingbag.model.domesticflight.responsemodel;

import com.google.gson.annotations.SerializedName;

public class LadderDetails {

    @SerializedName("FareCalculation")
    private String fareCalculation;

    @SerializedName("EndorsementRestriction")
    private String endorsementRestriction;

    @SerializedName("IssueInExchangeFor")
    private String issueInExchangeFor;

    public String getFareCalculation() {
        return fareCalculation;
    }

    public void setFareCalculation(String fareCalculation) {
        this.fareCalculation = fareCalculation;
    }

    public String getEndorsementRestriction() {
        return endorsementRestriction;
    }

    public void setEndorsementRestriction(String endorsementRestriction) {
        this.endorsementRestriction = endorsementRestriction;
    }

    public String getIssueInExchangeFor() {
        return issueInExchangeFor;
    }

    public void setIssueInExchangeFor(String issueInExchangeFor) {
        this.issueInExchangeFor = issueInExchangeFor;
    }

    @Override
    public String toString() {
        return
                "LadderDetails{" +
                        "fareCalculation = '" + fareCalculation + '\'' +
                        ",endorsementRestriction = '" + endorsementRestriction + '\'' +
                        ",issueInExchangeFor = '" + issueInExchangeFor + '\'' +
                        "}";
    }
}