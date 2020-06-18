package com.shoppingbag.model.mobile_recharge.requestmodel;

import com.google.gson.annotations.SerializedName;

public class RequestGetTransactionStatus {

    @SerializedName("UserTrackId")
    private String userTrackId;

    public String getUserTrackId() {
        return userTrackId;
    }

    public void setUserTrackId(String userTrackId) {
        this.userTrackId = userTrackId;
    }

    @Override
    public String toString() {
        return
                "RequestGetTransactionStatus{" +
                        "userTrackId = '" + userTrackId + '\'' +
                        "}";
    }
}