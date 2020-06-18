package com.shoppingbag.model.response.trasactions;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseTransactions {

    @SerializedName("Response")
    private String response;

    @SerializedName("AllTransactionList")
    private List<AllTransactionListItem> allTransactionList;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<AllTransactionListItem> getAllTransactionList() {
        return allTransactionList;
    }

    public void setAllTransactionList(List<AllTransactionListItem> allTransactionList) {
        this.allTransactionList = allTransactionList;
    }
}