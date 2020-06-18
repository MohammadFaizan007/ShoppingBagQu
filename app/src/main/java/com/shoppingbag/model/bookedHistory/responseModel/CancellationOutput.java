package com.shoppingbag.model.bookedHistory.responseModel;

import com.google.gson.annotations.SerializedName;

public class CancellationOutput {

    @SerializedName("CancellationDetails")
    private CancellationDetails cancellationDetails;

    public CancellationDetails getCancellationDetails() {
        return cancellationDetails;
    }

    public void setCancellationDetails(CancellationDetails cancellationDetails) {
        this.cancellationDetails = cancellationDetails;
    }

    @Override
    public String toString() {
        return
                "CancellationOutput{" +
                        "cancellationDetails = '" + cancellationDetails + '\'' +
                        "}";
    }
}