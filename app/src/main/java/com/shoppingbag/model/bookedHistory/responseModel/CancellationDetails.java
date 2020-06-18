package com.shoppingbag.model.bookedHistory.responseModel;

import com.google.gson.annotations.SerializedName;

public class CancellationDetails {

    @SerializedName("PartialCancelPNRDetails")
    private PartialCancelPNRDetails partialCancelPNRDetails;

    public PartialCancelPNRDetails getPartialCancelPNRDetails() {
        return partialCancelPNRDetails;
    }

    public void setPartialCancelPNRDetails(PartialCancelPNRDetails partialCancelPNRDetails) {
        this.partialCancelPNRDetails = partialCancelPNRDetails;
    }

    @Override
    public String toString() {
        return
                "CancellationDetails{" +
                        "partialCancelPNRDetails = '" + partialCancelPNRDetails + '\'' +
                        "}";
    }
}