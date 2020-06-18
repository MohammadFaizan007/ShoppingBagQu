package com.shoppingbag.model.response.bus_response;

import com.google.gson.annotations.SerializedName;

public class ResponseGetSearchBus {

    @SerializedName("FailureRemarks")
    private String failureRemarks;

    @SerializedName("ResponseStatus")
    private String responseStatus;

    @SerializedName("UserTrackId")
    private String userTrackId;

    @SerializedName("SearchOutput")
    private SearchOutput searchOutput;

    public void setFailureRemarks(String failureRemarks) {
        this.failureRemarks = failureRemarks;
    }

    public String getFailureRemarks() {
        return failureRemarks;
    }

    public void setResponseStatus(String responseStatus) {
        this.responseStatus = responseStatus;
    }

    public String getResponseStatus() {
        return responseStatus;
    }

    public void setUserTrackId(String userTrackId) {
        this.userTrackId = userTrackId;
    }

    public String getUserTrackId() {
        return userTrackId;
    }

    public void setSearchOutput(SearchOutput searchOutput) {
        this.searchOutput = searchOutput;
    }

    public SearchOutput getSearchOutput() {
        return searchOutput;
    }

    @Override
    public String toString() {
        return
                "ResponseGetSearchBus{" +
                        "failureRemarks = '" + failureRemarks + '\'' +
                        ",responseStatus = '" + responseStatus + '\'' +
                        ",userTrackId = '" + userTrackId + '\'' +
                        ",searchOutput = '" + searchOutput + '\'' +
                        "}";
    }
}