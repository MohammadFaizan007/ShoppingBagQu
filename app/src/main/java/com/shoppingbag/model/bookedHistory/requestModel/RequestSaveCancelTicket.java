package com.shoppingbag.model.bookedHistory.requestModel;

import com.google.gson.annotations.SerializedName;

public class RequestSaveCancelTicket {

    @SerializedName("Type")
    private String type;

    @SerializedName("CancellationBy")
    private String cancellationBy;

    @SerializedName("Remarks")
    private String remarks;

    @SerializedName("AirlinePNR")
    private String airlinePNR;

    @SerializedName("HermesPNR")
    private String hermesPNR;

    @SerializedName("FK_MemId")
    private String fK_MemId;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCancellationBy() {
        return cancellationBy;
    }

    public void setCancellationBy(String cancellationBy) {
        this.cancellationBy = cancellationBy;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getAirlinePNR() {
        return airlinePNR;
    }

    public void setAirlinePNR(String airlinePNR) {
        this.airlinePNR = airlinePNR;
    }

    public String getHermesPNR() {
        return hermesPNR;
    }

    public void setHermesPNR(String hermesPNR) {
        this.hermesPNR = hermesPNR;
    }

    public String getfK_MemId() {
        return fK_MemId;
    }

    public void setfK_MemId(String fK_MemId) {
        this.fK_MemId = fK_MemId;
    }

    @Override
    public String toString() {
        return
                "RequestSaveCancelTicket{" +
                        "type = '" + type + '\'' +
                        ",cancellationBy = '" + cancellationBy + '\'' +
                        ",remarks = '" + remarks + '\'' +
                        ",airlinePNR = '" + airlinePNR + '\'' +
                        ",hermesPNR = '" + hermesPNR + '\'' +
                        ",FK_MemId = '" + fK_MemId + '\'' +
                        "}";
    }
}