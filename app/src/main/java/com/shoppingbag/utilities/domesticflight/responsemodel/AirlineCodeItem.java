package com.shoppingbag.utilities.domesticflight.responsemodel;

import com.google.gson.annotations.SerializedName;

public class AirlineCodeItem {

    @SerializedName("country")
    private String country;

    @SerializedName("code")
    private String code;

    @SerializedName("direct_flights")
    private String directFlights;

    @SerializedName("city")
    private String city;

    @SerializedName("tz")
    private String tz;

    @SerializedName("woeid")
    private String woeid;

    @SerializedName("lon")
    private String lon;

    @SerializedName("carriers")
    private String carriers;

    @SerializedName("type")
    private String type;

    @SerializedName("runway_length")
    private String runwayLength;

    @SerializedName("elev")
    private String elev;

    @SerializedName("name")
    private String name;

    @SerializedName("icao")
    private String icao;

    @SerializedName("state")
    private String state;

    @SerializedName("lat")
    private String lat;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDirectFlights() {
        return directFlights;
    }

    public void setDirectFlights(String directFlights) {
        this.directFlights = directFlights;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTz() {
        return tz;
    }

    public void setTz(String tz) {
        this.tz = tz;
    }

    public String getWoeid() {
        return woeid;
    }

    public void setWoeid(String woeid) {
        this.woeid = woeid;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getCarriers() {
        return carriers;
    }

    public void setCarriers(String carriers) {
        this.carriers = carriers;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRunwayLength() {
        return runwayLength;
    }

    public void setRunwayLength(String runwayLength) {
        this.runwayLength = runwayLength;
    }

    public String getElev() {
        return elev;
    }

    public void setElev(String elev) {
        this.elev = elev;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcao() {
        return icao;
    }

    public void setIcao(String icao) {
        this.icao = icao;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    @Override
    public String toString() {
        return
                "AirlineCodeItem{" +
                        "country = '" + country + '\'' +
                        ",code = '" + code + '\'' +
                        ",direct_flights = '" + directFlights + '\'' +
                        ",city = '" + city + '\'' +
                        ",tz = '" + tz + '\'' +
                        ",woeid = '" + woeid + '\'' +
                        ",lon = '" + lon + '\'' +
                        ",carriers = '" + carriers + '\'' +
                        ",type = '" + type + '\'' +
                        ",runway_length = '" + runwayLength + '\'' +
                        ",elev = '" + elev + '\'' +
                        ",name = '" + name + '\'' +
                        ",icao = '" + icao + '\'' +
                        ",state = '" + state + '\'' +
                        ",lat = '" + lat + '\'' +
                        "}";
    }
}