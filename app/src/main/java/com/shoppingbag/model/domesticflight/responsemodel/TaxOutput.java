package com.shoppingbag.model.domesticflight.responsemodel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TaxOutput {

    @SerializedName("BaggageAndMealsFlag")
    private int baggageAndMealsFlag;

    @SerializedName("TaxResFlightSegments")
    private List<TaxResFlightSegmentsItem> taxResFlightSegments;

    @SerializedName("Baggages")
    private List<BaggagesItem> baggages;

    @SerializedName("SpecialServices")
    private Object specialServices;

    @SerializedName("RequiredFields")
    private RequiredFields requiredFields;

    @SerializedName("Meals")
    private Object meals;

    public int getBaggageAndMealsFlag() {
        return baggageAndMealsFlag;
    }

    public void setBaggageAndMealsFlag(int baggageAndMealsFlag) {
        this.baggageAndMealsFlag = baggageAndMealsFlag;
    }

    public List<TaxResFlightSegmentsItem> getTaxResFlightSegments() {
        return taxResFlightSegments;
    }

    public void setTaxResFlightSegments(List<TaxResFlightSegmentsItem> taxResFlightSegments) {
        this.taxResFlightSegments = taxResFlightSegments;
    }

    public List<BaggagesItem> getBaggages() {
        return baggages;
    }

    public void setBaggages(List<BaggagesItem> baggages) {
        this.baggages = baggages;
    }

    public Object getSpecialServices() {
        return specialServices;
    }

    public void setSpecialServices(Object specialServices) {
        this.specialServices = specialServices;
    }

    public RequiredFields getRequiredFields() {
        return requiredFields;
    }

    public void setRequiredFields(RequiredFields requiredFields) {
        this.requiredFields = requiredFields;
    }

    public Object getMeals() {
        return meals;
    }

    public void setMeals(Object meals) {
        this.meals = meals;
    }

    @Override
    public String toString() {
        return
                "TaxOutput{" +
                        "baggageAndMealsFlag = '" + baggageAndMealsFlag + '\'' +
                        ",taxResFlightSegments = '" + taxResFlightSegments + '\'' +
                        ",baggages = '" + baggages + '\'' +
                        ",specialServices = '" + specialServices + '\'' +
                        ",requiredFields = '" + requiredFields + '\'' +
                        ",meals = '" + meals + '\'' +
                        "}";
    }
}