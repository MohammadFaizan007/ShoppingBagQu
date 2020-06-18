package com.shoppingbag.model.mobile_recharge.responsemodel;

import com.google.gson.annotations.SerializedName;

public class ResponseGetRechargeOperators {

    @SerializedName("ResponseStatus")
    private int responseStatus;

    @SerializedName("RechargeOperatorsOutput")
    private RechargeOperatorsOutput rechargeOperatorsOutput;

    public int getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(int responseStatus) {
        this.responseStatus = responseStatus;
    }

    public RechargeOperatorsOutput getRechargeOperatorsOutput() {
        return rechargeOperatorsOutput;
    }

    public void setRechargeOperatorsOutput(RechargeOperatorsOutput rechargeOperatorsOutput) {
        this.rechargeOperatorsOutput = rechargeOperatorsOutput;
    }

    @Override
    public String toString() {
        return
                "ResponseGetRechargeOperators{" +
                        "responseStatus = '" + responseStatus + '\'' +
                        ",rechargeOperatorsOutput = '" + rechargeOperatorsOutput + '\'' +
                        "}";
    }
}