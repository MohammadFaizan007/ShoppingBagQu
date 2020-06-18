package com.shoppingbag.model.domesticflight.requestmodel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TaxInput {

    @SerializedName("GSTDetails")
    private GSTDetails gSTDetails;

    @SerializedName("TaxReqFlightSegments")
    private List<TaxReqFlightSegmentsItem> taxReqFlightSegments;

    public GSTDetails getGSTDetails() {
        return gSTDetails;
    }

    public void setGSTDetails(GSTDetails gSTDetails) {
        this.gSTDetails = gSTDetails;
    }

    public List<TaxReqFlightSegmentsItem> getTaxReqFlightSegments() {
        return taxReqFlightSegments;
    }

    public void setTaxReqFlightSegments(List<TaxReqFlightSegmentsItem> taxReqFlightSegments) {
        this.taxReqFlightSegments = taxReqFlightSegments;
    }

    @Override
    public String toString() {
        return
                "TaxInput{" +
                        "gSTDetails = '" + gSTDetails + '\'' +
                        ",taxReqFlightSegments = '" + taxReqFlightSegments + '\'' +
                        "}";
    }
}