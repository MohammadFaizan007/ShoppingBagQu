package com.shoppingbag.model.mobile_recharge.responsemodel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AccountStatementOutput {

    @SerializedName("Transactions")
    private List<TransactionsItem> transactions;

    @SerializedName("TravelAgentID")
    private String travelAgentID;

    @SerializedName("TotalRemainingAmount")
    private double totalRemainingAmount;

    @SerializedName("TravelAgentName")
    private String travelAgentName;

    public List<TransactionsItem> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionsItem> transactions) {
        this.transactions = transactions;
    }

    public String getTravelAgentID() {
        return travelAgentID;
    }

    public void setTravelAgentID(String travelAgentID) {
        this.travelAgentID = travelAgentID;
    }

    public double getTotalRemainingAmount() {
        return totalRemainingAmount;
    }

    public void setTotalRemainingAmount(double totalRemainingAmount) {
        this.totalRemainingAmount = totalRemainingAmount;
    }

    public String getTravelAgentName() {
        return travelAgentName;
    }

    public void setTravelAgentName(String travelAgentName) {
        this.travelAgentName = travelAgentName;
    }

    @Override
    public String toString() {
        return
                "AccountStatementOutput{" +
                        "transactions = '" + transactions + '\'' +
                        ",travelAgentID = '" + travelAgentID + '\'' +
                        ",totalRemainingAmount = '" + totalRemainingAmount + '\'' +
                        ",travelAgentName = '" + travelAgentName + '\'' +
                        "}";
    }
}