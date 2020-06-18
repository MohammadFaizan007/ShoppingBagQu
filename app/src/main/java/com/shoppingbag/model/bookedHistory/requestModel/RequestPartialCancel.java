package com.shoppingbag.model.bookedHistory.requestModel;

import com.google.gson.annotations.SerializedName;

public class RequestPartialCancel {

    @SerializedName("Authentication")
    private Authentication authentication;

    @SerializedName("PartialCancellationInput")
    private PartialCancellationInput partialCancellationInput;

    public Authentication getAuthentication() {
        return authentication;
    }

    public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }

    public PartialCancellationInput getPartialCancellationInput() {
        return partialCancellationInput;
    }

    public void setPartialCancellationInput(PartialCancellationInput partialCancellationInput) {
        this.partialCancellationInput = partialCancellationInput;
    }

    @Override
    public String toString() {
        return
                "RequestPartialCancel{" +
                        "partialCancellationInput = '" + partialCancellationInput + '\'' +
                        "}";
    }
}