package com.shoppingbag.model.domesticflight.requestmodel;

import com.google.gson.annotations.SerializedName;

public class RequestFlightAvalibility {

    @SerializedName("AvailabilityInput")
    private AvailabilityInput availabilityInput;

    @SerializedName("Authentication")
    private Authentication authentication;

    public AvailabilityInput getAvailabilityInput() {
        return availabilityInput;
    }

    public void setAvailabilityInput(AvailabilityInput availabilityInput) {
        this.availabilityInput = availabilityInput;
    }

    public Authentication getAuthentication() {
        return authentication;
    }

    public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }

    @Override
    public String toString() {
        return
                "RequestFlightAvalibility{" +
                        "availabilityInput = '" + availabilityInput + '\'' +
                        ",authentication = '" + authentication + '\'' +
                        "}";
    }
}