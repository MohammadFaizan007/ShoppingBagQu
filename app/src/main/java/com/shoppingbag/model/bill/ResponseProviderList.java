package com.shoppingbag.model.bill;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseProviderList {

    @SerializedName("Response")
    private String response;

    @SerializedName("AllProviderlist")
    private List<AllProviderlistItem> allProviderlist;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<AllProviderlistItem> getAllProviderlist() {
        return allProviderlist;
    }

    public void setAllProviderlist(List<AllProviderlistItem> allProviderlist) {
        this.allProviderlist = allProviderlist;
    }
}