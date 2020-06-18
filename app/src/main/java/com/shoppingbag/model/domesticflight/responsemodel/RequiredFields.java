package com.shoppingbag.model.domesticflight.responsemodel;

import com.google.gson.annotations.SerializedName;

public class RequiredFields {

    @SerializedName("ChildReqFields")
    private ChildReqFields childReqFields;

    @SerializedName("AdultReqFields")
    private AdultReqFields adultReqFields;

    @SerializedName("InfantReqFields")
    private InfantReqFields infantReqFields;

    public ChildReqFields getChildReqFields() {
        return childReqFields;
    }

    public void setChildReqFields(ChildReqFields childReqFields) {
        this.childReqFields = childReqFields;
    }

    public AdultReqFields getAdultReqFields() {
        return adultReqFields;
    }

    public void setAdultReqFields(AdultReqFields adultReqFields) {
        this.adultReqFields = adultReqFields;
    }

    public InfantReqFields getInfantReqFields() {
        return infantReqFields;
    }

    public void setInfantReqFields(InfantReqFields infantReqFields) {
        this.infantReqFields = infantReqFields;
    }

    @Override
    public String toString() {
        return
                "RequiredFields{" +
                        "childReqFields = '" + childReqFields + '\'' +
                        ",adultReqFields = '" + adultReqFields + '\'' +
                        ",infantReqFields = '" + infantReqFields + '\'' +
                        "}";
    }
}