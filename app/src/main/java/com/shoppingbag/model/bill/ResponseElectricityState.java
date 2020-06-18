package com.shoppingbag.model.bill;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ResponseElectricityState {

    @SerializedName("Response")
    private String response;

    @SerializedName("GetAllProviderStateWiseList")
    private List<GetAllProviderStateWiseListItem> getAllProviderStateWiseList;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<GetAllProviderStateWiseListItem> getGetAllProviderStateWiseList() {
        return getAllProviderStateWiseList;
    }

    public void setGetAllProviderStateWiseList(List<GetAllProviderStateWiseListItem> getAllProviderStateWiseList) {
        this.getAllProviderStateWiseList = getAllProviderStateWiseList;
    }
}