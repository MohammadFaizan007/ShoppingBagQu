package com.shoppingbag.model.domesticflight.responsemodel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FareBreakUpDetails {

    @SerializedName("CurrencyCode")
    private String currencyCode;

    @SerializedName("TaxDetails")
    private List<TaxDetailsItem> taxDetails;

    @SerializedName("ServiceCharge")
    private int serviceCharge;

    @SerializedName("TotalTaxAmount")
    private int totalTaxAmount;

    @SerializedName("EquivalentFare")
    private int equivalentFare;

    @SerializedName("Commission")
    private String commission;

    @SerializedName("TransactionFee")
    private int transactionFee;

    @SerializedName("BasicAmount")
    private int basicAmount;

    @SerializedName("GrossAmount")
    private int grossAmount;

    @SerializedName("BasicCurrencyCode")
    private String basicCurrencyCode;

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public List<TaxDetailsItem> getTaxDetails() {
        return taxDetails;
    }

    public void setTaxDetails(List<TaxDetailsItem> taxDetails) {
        this.taxDetails = taxDetails;
    }

    public int getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(int serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public int getTotalTaxAmount() {
        return totalTaxAmount;
    }

    public void setTotalTaxAmount(int totalTaxAmount) {
        this.totalTaxAmount = totalTaxAmount;
    }

    public int getEquivalentFare() {
        return equivalentFare;
    }

    public void setEquivalentFare(int equivalentFare) {
        this.equivalentFare = equivalentFare;
    }

    public String getCommission() {
        return commission;
    }

    public void setCommission(String commission) {
        this.commission = commission;
    }

    public int getTransactionFee() {
        return transactionFee;
    }

    public void setTransactionFee(int transactionFee) {
        this.transactionFee = transactionFee;
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

    public String getBasicCurrencyCode() {
        return basicCurrencyCode;
    }

    public void setBasicCurrencyCode(String basicCurrencyCode) {
        this.basicCurrencyCode = basicCurrencyCode;
    }

    @Override
    public String toString() {
        return
                "FareBreakUpDetails{" +
                        "currencyCode = '" + currencyCode + '\'' +
                        ",taxDetails = '" + taxDetails + '\'' +
                        ",serviceCharge = '" + serviceCharge + '\'' +
                        ",totalTaxAmount = '" + totalTaxAmount + '\'' +
                        ",equivalentFare = '" + equivalentFare + '\'' +
                        ",commission = '" + commission + '\'' +
                        ",transactionFee = '" + transactionFee + '\'' +
                        ",basicAmount = '" + basicAmount + '\'' +
                        ",grossAmount = '" + grossAmount + '\'' +
                        ",basicCurrencyCode = '" + basicCurrencyCode + '\'' +
                        "}";
    }
}