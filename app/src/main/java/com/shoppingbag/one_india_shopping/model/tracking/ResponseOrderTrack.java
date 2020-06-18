package com.shoppingbag.one_india_shopping.model.tracking;

import com.google.gson.annotations.SerializedName;

public class ResponseOrderTrack {

    @SerializedName("tracking_data")
    private TrackingData trackingData;

    public void setTrackingData(TrackingData trackingData) {
        this.trackingData = trackingData;
    }

    public TrackingData getTrackingData() {
        return trackingData;
    }
}