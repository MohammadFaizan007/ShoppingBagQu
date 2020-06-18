package com.shoppingbag.model.bookedHistory.requestModel;

import com.google.gson.annotations.SerializedName;

public class RequestFullCancelation {

    @SerializedName("Authentication")
    private Authentication authentication;

    @SerializedName("CancellationInput")
    private CancellationInput cancellationInput;

    public Authentication getAuthentication() {
        return authentication;
    }

    public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }

    public CancellationInput getCancellationInput() {
        return cancellationInput;
    }

    public void setCancellationInput(CancellationInput cancellationInput) {
        this.cancellationInput = cancellationInput;
    }

    @Override
    public String toString() {
        return
                "RequestFullCancelation{" +
                        "authentication = '" + authentication + '\'' +
                        ",cancellationInput = '" + cancellationInput + '\'' +
                        "}";
    }
}