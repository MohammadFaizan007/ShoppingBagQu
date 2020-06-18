package com.shoppingbag.model.mobile_recharge.responsemodel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BillPaymentOperatorsOutput {

    @SerializedName("OperatorLists")
    private List<OperatorListsItem> operatorLists;

    public List<OperatorListsItem> getOperatorLists() {
        return operatorLists;
    }

    public void setOperatorLists(List<OperatorListsItem> operatorLists) {
        this.operatorLists = operatorLists;
    }

    @Override
    public String toString() {
        return
                "BillPaymentOperatorsOutput{" +
                        "operatorLists = '" + operatorLists + '\'' +
                        "}";
    }
}