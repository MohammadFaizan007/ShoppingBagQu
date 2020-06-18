package com.shoppingbag.model.bill;

import com.google.gson.annotations.SerializedName;


public class GetAllProviderStateWiseListItem {


    @SerializedName("Number")
    private String number;

    @SerializedName("StateName")
    private String stateName;

    @SerializedName("Provider")
    private String provider;

    @SerializedName("ImageUrl")
    private String imageUrl;

    @SerializedName("PK_StateId")
    private String pKStateId;

    @SerializedName("FormatofNumber")
    private String formatofNumber;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPKStateId() {
        return pKStateId;
    }

    public void setPKStateId(String pKStateId) {
        this.pKStateId = pKStateId;
    }

    public String getFormatofNumber() {
        return formatofNumber;
    }

    public void setFormatofNumber(String formatofNumber) {
        this.formatofNumber = formatofNumber;
    }
}