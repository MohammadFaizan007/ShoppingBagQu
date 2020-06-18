package com.shoppingbag.model.mobile_recharge.responsemodel;

import com.google.gson.annotations.SerializedName;

public class ResponseBillPaymentDone {

    @SerializedName("ResponseStatus")
    private int responseStatus;

    @SerializedName("UserTrackId")
    private String userTrackId;

    @SerializedName("BillPaymentOutput")
    private BillPaymentOutput billPaymentOutput;

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

    public BillPaymentOutput getBillPaymentOutput() {
        return billPaymentOutput;
    }

    public void setBillPaymentOutput(BillPaymentOutput billPaymentOutput) {
        this.billPaymentOutput = billPaymentOutput;
    }

    @Override
    public String toString() {
        return
                "ResponseBillPaymentDone{" +
                        "responseStatus = '" + responseStatus + '\'' +
                        ",userTrackId = '" + userTrackId + '\'' +
                        ",billPaymentOutput = '" + billPaymentOutput + '\'' +
                        "}";
    }
}