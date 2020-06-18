package com.shoppingbag.model.mobile_recharge.responsemodel;

import com.google.gson.annotations.SerializedName;

public class ResponseBillPaymentOperators {

    @SerializedName("ResponseStatus")
    private int responseStatus;

    @SerializedName("BillPaymentOperatorsOutput")
    private BillPaymentOperatorsOutput billPaymentOperatorsOutput;

    public int getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(int responseStatus) {
        this.responseStatus = responseStatus;
    }

    public BillPaymentOperatorsOutput getBillPaymentOperatorsOutput() {
        return billPaymentOperatorsOutput;
    }

    public void setBillPaymentOperatorsOutput(BillPaymentOperatorsOutput billPaymentOperatorsOutput) {
        this.billPaymentOperatorsOutput = billPaymentOperatorsOutput;
    }

    @Override
    public String toString() {
        return
                "ResponseBillPaymentOperators{" +
                        "responseStatus = '" + responseStatus + '\'' +
                        ",billPaymentOperatorsOutput = '" + billPaymentOperatorsOutput + '\'' +
                        "}";
    }
}