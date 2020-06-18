package com.shoppingbag.model.mobile_recharge.responsemodel;

import com.google.gson.annotations.SerializedName;

public class ResponseGetReprintMobile {

    @SerializedName("ResponseStatus")
    private int responseStatus;

    @SerializedName("UserTrackId")
    private String userTrackId;

    @SerializedName("ReprintOutput")
    private ReprintOutput reprintOutput;

    public int getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(int responseStatus) {
        this.responseStatus = responseStatus;
    }

    public String getUserTrackId() {
        return userTrackId;
    }

    public void setUserTrackId(String userTrackId) {
        this.userTrackId = userTrackId;
    }

    public ReprintOutput getReprintOutput() {
        return reprintOutput;
    }

    public void setReprintOutput(ReprintOutput reprintOutput) {
        this.reprintOutput = reprintOutput;
    }

    @Override
    public String toString() {
        return
                "ResponseGetReprintMobile{" +
                        "responseStatus = '" + responseStatus + '\'' +
                        ",userTrackId = '" + userTrackId + '\'' +
                        ",reprintOutput = '" + reprintOutput + '\'' +
                        "}";
    }
}