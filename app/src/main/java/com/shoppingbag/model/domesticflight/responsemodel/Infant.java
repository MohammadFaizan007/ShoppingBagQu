package com.shoppingbag.model.domesticflight.responsemodel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Infant implements Serializable {

    @SerializedName("TaxDetails")
    private List<TaxDetailsItem> taxDetails;

    @SerializedName("FareType")
    private String fareType;

    @SerializedName("TotalTaxAmount")
    private int totalTaxAmount;

    @SerializedName("FareBasis")
    private String fareBasis;

    @SerializedName("YQ")
    private int yQ;

    @SerializedName("Commission")
    private String commission;

    @SerializedName("BasicAmount")
    private int basicAmount;

    @SerializedName("GrossAmount")
    private int grossAmount;

    public List<TaxDetailsItem> getTaxDetails() {
        return taxDetails;
    }

    public void setTaxDetails(List<TaxDetailsItem> taxDetails) {
        this.taxDetails = taxDetails;
    }

    public String getFareType() {
        return fareType;
    }

    public void setFareType(String fareType) {
        this.fareType = fareType;
    }

    public int getTotalTaxAmount() {
        return totalTaxAmount;
    }

    public void setTotalTaxAmount(int totalTaxAmount) {
        this.totalTaxAmount = totalTaxAmount;
    }

    public String getFareBasis() {
        return fareBasis;
    }

    public void setFareBasis(String fareBasis) {
        this.fareBasis = fareBasis;
    }

    public int getYQ() {
        return yQ;
    }

    public void setYQ(int yQ) {
        this.yQ = yQ;
    }

    public String getCommission() {
        return commission;
    }

    public void setCommission(String commission) {
        this.commission = commission;
    }

    public int getBasicAmount() {
        return basicAmount;
    }

    public void setBasicAmount(int basicAmount) {
        this.basicAmount = basicAmount;
    }

    public int getGrossAmount() {
        return grossAmount;
    }

    public void setGrossAmount(int grossAmount) {
        this.grossAmount = grossAmount;
    }

    @Override
    public String toString() {
        return
                "Infant{" +
                        "taxDetails = '" + taxDetails + '\'' +
                        ",fareType = '" + fareType + '\'' +
                        ",totalTaxAmount = '" + totalTaxAmount + '\'' +
                        ",fareBasis = '" + fareBasis + '\'' +
                        ",yQ = '" + yQ + '\'' +
                        ",commission = '" + commission + '\'' +
                        ",basicAmount = '" + basicAmount + '\'' +
                        ",grossAmount = '" + grossAmount + '\'' +
                        "}";
    }
}