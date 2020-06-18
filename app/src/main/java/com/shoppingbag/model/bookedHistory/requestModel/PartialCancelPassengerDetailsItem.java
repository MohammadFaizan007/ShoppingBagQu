package com.shoppingbag.model.bookedHistory.requestModel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PartialCancelPassengerDetailsItem {

    @SerializedName("PaxNumber")
    private int paxNumber;

    @SerializedName("PartialCancelTicketDetails")
    private List<PartialCancelTicketDetailsItem> partialCancelTicketDetails;

    public int getPaxNumber() {
        return paxNumber;
    }

    public void setPaxNumber(int paxNumber) {
        this.paxNumber = paxNumber;
    }

    public List<PartialCancelTicketDetailsItem> getPartialCancelTicketDetails() {
        return partialCancelTicketDetails;
    }

    public void setPartialCancelTicketDetails(List<PartialCancelTicketDetailsItem> partialCancelTicketDetails) {
        this.partialCancelTicketDetails = partialCancelTicketDetails;
    }

    @Override
    public String toString() {
        return
                "PartialCancelPassengerDetailsItem{" +
                        "paxNumber = '" + paxNumber + '\'' +
                        ",partialCancelTicketDetails = '" + partialCancelTicketDetails + '\'' +
                        "}";
    }
}