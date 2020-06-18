package com.shoppingbag.model.mobile_recharge.requestmodel;

import com.google.gson.annotations.SerializedName;

public class RequestGetReprintMobile {

    @SerializedName("Authentication")
    private Authentication authentication;

    @SerializedName("UserTrackId")
    private String userTrackId;

    public Authentication getAuthentication() {
        return authentication;
    }

    public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }

    public String getUserTrackId() {
        return userTrackId;
    }

    public void setUserTrackId(String userTrackId) {
        this.userTrackId = userTrackId;
    }

    @Override
    public String toString() {
        return
                "RequestGetReprint{" +
                        "authentication = '" + authentication + '\'' +
                        ",userTrackId = '" + userTrackId + '\'' +
                        "}";
    }
}