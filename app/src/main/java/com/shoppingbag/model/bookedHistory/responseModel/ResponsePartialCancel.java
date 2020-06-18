package com.shoppingbag.model.bookedHistory.responseModel;

import com.google.gson.annotations.SerializedName;

public class ResponsePartialCancel {

    @SerializedName("ResponseStatus")
    private int responseStatus;

    @SerializedName("CancellationOutput")
    private CancellationOutput cancellationOutput;

    public int getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(int responseStatus) {
        this.responseStatus = responseStatus;
    }

    public CancellationOutput getCancellationOutput() {
        return cancellationOutput;
    }

    public void setCancellationOutput(CancellationOutput cancellationOutput) {
        this.cancellationOutput = cancellationOutput;
    }

    @Override
    public String toString() {
        return
                "ResponsePartialCancel{" +
                        "responseStatus = '" + responseStatus + '\'' +
                        ",cancellationOutput = '" + cancellationOutput + '\'' +
                        "}";
    }
}