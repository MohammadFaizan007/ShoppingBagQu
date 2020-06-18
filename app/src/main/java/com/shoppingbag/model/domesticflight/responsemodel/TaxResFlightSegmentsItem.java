package com.shoppingbag.model.domesticflight.responsemodel;

import com.google.gson.annotations.SerializedName;

public class TaxResFlightSegmentsItem {

    @SerializedName("Baggages")
    private Object baggages;

    @SerializedName("FlightId")
    private String flightId;

    @SerializedName("AdultTax")
    private AdultTax adultTax;

    @SerializedName("ChildTax")
    private ChildTax childTax;

    @SerializedName("Meals")
    private Object meals;

    @SerializedName("InfantTax")
    private InfantTax infantTax;

    public Object getBaggages() {
        return baggages;
    }

    public void setBaggages(Object baggages) {
        this.baggages = baggages;
    }

    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    public AdultTax getAdultTax() {
        return adultTax;
    }

    public void setAdultTax(AdultTax adultTax) {
        this.adultTax = adultTax;
    }

    public ChildTax getChildTax() {
        return childTax;
    }

    public void setChildTax(ChildTax childTax) {
        this.childTax = childTax;
    }

    public Object getMeals() {
        return meals;
    }

    public void setMeals(Object meals) {
        this.meals = meals;
    }

    public InfantTax getInfantTax() {
        return infantTax;
    }

    public void setInfantTax(InfantTax infantTax) {
        this.infantTax = infantTax;
    }

    @Override
    public String toString() {
        return
                "TaxResFlightSegmentsItem{" +
                        "baggages = '" + baggages + '\'' +
                        ",flightId = '" + flightId + '\'' +
                        ",adultTax = '" + adultTax + '\'' +
                        ",childTax = '" + childTax + '\'' +
                        ",meals = '" + meals + '\'' +
                        ",infantTax = '" + infantTax + '\'' +
                        "}";
    }
}