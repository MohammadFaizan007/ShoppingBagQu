package com.shoppingbag.model.domesticflight.responsemodel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AvailPaxFareDetailsItem implements Serializable {

    @SerializedName("ClassCodeDesc")
    private String classCodeDesc;

    @SerializedName("BaggageAllowed")
    private BaggageAllowed baggageAllowed;

    @SerializedName("ClassCode")
    private String classCode;

    @SerializedName("Adult")
    private Adult adult;

    @SerializedName("Infant")
    private Infant infant;

    @SerializedName("ChangePenalty")
    private String changePenalty;

    @SerializedName("CancellationCharges")
    private String cancellationCharges;

    @SerializedName("Child")
    private Child child;

    public String getClassCodeDesc() {
        return classCodeDesc;
    }

    public void setClassCodeDesc(String classCodeDesc) {
        this.classCodeDesc = classCodeDesc;
    }

    public BaggageAllowed getBaggageAllowed() {
        return baggageAllowed;
    }

    public void setBaggageAllowed(BaggageAllowed baggageAllowed) {
        this.baggageAllowed = baggageAllowed;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public Adult getAdult() {
        return adult;
    }

    public void setAdult(Adult adult) {
        this.adult = adult;
    }

    public Infant getInfant() {
        return infant;
    }

    public void setInfant(Infant infant) {
        this.infant = infant;
    }

    public String getChangePenalty() {
        return changePenalty;
    }

    public void setChangePenalty(String changePenalty) {
        this.changePenalty = changePenalty;
    }

    public String getCancellationCharges() {
        return cancellationCharges;
    }

    public void setCancellationCharges(String cancellationCharges) {
        this.cancellationCharges = cancellationCharges;
    }

    public Child getChild() {
        return child;
    }

    public void setChild(Child child) {
        this.child = child;
    }

    @Override
    public String toString() {
        return
                "AvailPaxFareDetailsItem{" +
                        "classCodeDesc = '" + classCodeDesc + '\'' +
                        ",baggageAllowed = '" + baggageAllowed + '\'' +
                        ",classCode = '" + classCode + '\'' +
                        ",adult = '" + adult + '\'' +
                        ",infant = '" + infant + '\'' +
                        ",changePenalty = '" + changePenalty + '\'' +
                        ",cancellationCharges = '" + cancellationCharges + '\'' +
                        ",child = '" + child + '\'' +
                        "}";
    }
}