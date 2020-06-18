package com.shoppingbag.model.request.utility.response;

import com.google.gson.annotations.SerializedName;

public class ResponseBalanceAmount {

//    {"BalanceAmount":1073.00,"MemberId":13191,"Status":"Success","ValidateToken":"0669F0AB-CAD2-4238-8CDD-0BA5CD126EF3"}

    @SerializedName("BalanceAmount")
    private float balanceAmount;

    @SerializedName("MemberId")
    private int memberId;

    @SerializedName("Status")
    private String status;

    @SerializedName("ValidateToken")
    private String ValidateToken;

    public float getBalanceAmount() {
        return balanceAmount;
    }

    public int getMemberId() {
        return memberId;
    }

    public String getStatus() {
        return status;
    }

    public String getValidateToken() {
        return ValidateToken;
    }
}