package com.shoppingbag.model.mobile_recharge.requestmodel;

import com.google.gson.annotations.SerializedName;

public class RequestGetBillPaymentDone {

    @SerializedName("BillPaymentInput")
    private BillPaymentInput billPaymentInput;

    public BillPaymentInput getBillPaymentInput() {
        return billPaymentInput;
    }

    public void setBillPaymentInput(BillPaymentInput billPaymentInput) {
        this.billPaymentInput = billPaymentInput;
    }

    @Override
    public String toString() {
        return
                "RequestGetBillPaymentDone{" +
                        "billPaymentInput = '" + billPaymentInput + '\'' +
                        "}";
    }
}