package com.shoppingbag.model.domesticflight.responsemodel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class OngoingFlightsItem implements Serializable {

    @SerializedName("AvailSegments")
    private List<AvailSegmentsItem> availSegments;

    public List<AvailSegmentsItem> getAvailSegments() {
        return availSegments;
    }

    public void setAvailSegments(List<AvailSegmentsItem> availSegments) {
        this.availSegments = availSegments;
    }

    @Override
    public String toString() {
        return
                "OngoingFlightsItem{" +
                        "availSegments = '" + availSegments + '\'' +
                        "}";
    }
}