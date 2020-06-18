package com.shoppingbag.model.domesticflight.responsemodel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ReturnFlightsItem implements Serializable {

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
                "ReturnFlightsItem{" +
                        "availSegments = '" + availSegments + '\'' +
                        "}";
    }
}