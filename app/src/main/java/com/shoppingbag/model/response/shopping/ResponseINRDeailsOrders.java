package com.shoppingbag.model.response.shopping;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseINRDeailsOrders {

    @SerializedName("lstINR")
    private List<LstINRItem> lstINR;

    @SerializedName("response")
    private String response;

    public List<LstINRItem> getLstINR() {
        return lstINR;
    }

    public void setLstINR(List<LstINRItem> lstINR) {
        this.lstINR = lstINR;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return
                "ResponseINRDeailsOrders{" +
                        "lstINR = '" + lstINR + '\'' +
                        ",response = '" + response + '\'' +
                        "}";
    }
}