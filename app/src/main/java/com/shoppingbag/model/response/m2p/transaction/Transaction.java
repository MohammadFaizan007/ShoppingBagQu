package com.shoppingbag.model.response.m2p.transaction;

import com.google.gson.annotations.SerializedName;

public class Transaction {

    @SerializedName("billRefNo")
    private String billRefNo;

    @SerializedName("convertedAmount")
    private String convertedAmount;

    @SerializedName("businessId")
    private String businessId;

    @SerializedName("otherPartyId")
    private String otherPartyId;

    @SerializedName("description")
    private String description;

    @SerializedName("type")
    private String type;

    @SerializedName("mcc")
    private String mcc;

    @SerializedName("balance")
    private Double balance;

    @SerializedName("beneficiaryName")
    private String beneficiaryName;

    @SerializedName("limitCurrencyCode")
    private String limitCurrencyCode;

    @SerializedName("transactionCurrencyCode")
    private String transactionCurrencyCode;

    @SerializedName("externalTransactionId")
    private String externalTransactionId;

    @SerializedName("bankTid")
    private String bankTid;

    @SerializedName("acquirerId")
    private String acquirerId;

    @SerializedName("networkType")
    private String networkType;

    @SerializedName("yourWallet")
    private String yourWallet;

    @SerializedName("amount")
    private String amount;

    @SerializedName("kitNo")
    private String kitNo;

    @SerializedName("authCode")
    private String authCode;

    @SerializedName("transactionStatus")
    private String transactionStatus;

    @SerializedName("transactionType")
    private String transactionType;

    @SerializedName("txnOrigin")
    private String txnOrigin;

    @SerializedName("sorTxnId")
    private String sorTxnId;

    @SerializedName("txRef")
    private Double txRef;

    @SerializedName("beneficiaryType")
    private String beneficiaryType;

    @SerializedName("time")
    private long time;

    @SerializedName("beneficiaryWallet")
    private String beneficiaryWallet;

    @SerializedName("retrivalReferenceNo")
    private String retrivalReferenceNo;

    @SerializedName("beneficiaryId")
    private String beneficiaryId;

    @SerializedName("otherPartyName")
    private String otherPartyName;

    @SerializedName("status")
    private String status;

    public String getBillRefNo() {
        return billRefNo;
    }

    public void setBillRefNo(String billRefNo) {
        this.billRefNo = billRefNo;
    }

    public String getConvertedAmount() {
        return convertedAmount;
    }

    public void setConvertedAmount(String convertedAmount) {
        this.convertedAmount = convertedAmount;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getOtherPartyId() {
        return otherPartyId;
    }

    public void setOtherPartyId(String otherPartyId) {
        this.otherPartyId = otherPartyId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMcc() {
        return mcc;
    }

    public void setMcc(String mcc) {
        this.mcc = mcc;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getBeneficiaryName() {
        return beneficiaryName;
    }

    public void setBeneficiaryName(String beneficiaryName) {
        this.beneficiaryName = beneficiaryName;
    }

    public String getLimitCurrencyCode() {
        return limitCurrencyCode;
    }

    public void setLimitCurrencyCode(String limitCurrencyCode) {
        this.limitCurrencyCode = limitCurrencyCode;
    }

    public String getTransactionCurrencyCode() {
        return transactionCurrencyCode;
    }

    public void setTransactionCurrencyCode(String transactionCurrencyCode) {
        this.transactionCurrencyCode = transactionCurrencyCode;
    }

    public String getExternalTransactionId() {
        return externalTransactionId;
    }

    public void setExternalTransactionId(String externalTransactionId) {
        this.externalTransactionId = externalTransactionId;
    }

    public String getBankTid() {
        return bankTid;
    }

    public void setBankTid(String bankTid) {
        this.bankTid = bankTid;
    }

    public String getAcquirerId() {
        return acquirerId;
    }

    public void setAcquirerId(String acquirerId) {
        this.acquirerId = acquirerId;
    }

    public String getNetworkType() {
        return networkType;
    }

    public void setNetworkType(String networkType) {
        this.networkType = networkType;
    }

    public String getYourWallet() {
        return yourWallet;
    }

    public void setYourWallet(String yourWallet) {
        this.yourWallet = yourWallet;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getKitNo() {
        return kitNo;
    }

    public void setKitNo(String kitNo) {
        this.kitNo = kitNo;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getTxnOrigin() {
        return txnOrigin;
    }

    public void setTxnOrigin(String txnOrigin) {
        this.txnOrigin = txnOrigin;
    }

    public String getSorTxnId() {
        return sorTxnId;
    }

    public void setSorTxnId(String sorTxnId) {
        this.sorTxnId = sorTxnId;
    }

    public Double getTxRef() {
        return txRef;
    }

    public void setTxRef(Double txRef) {
        this.txRef = txRef;
    }

    public String getBeneficiaryType() {
        return beneficiaryType;
    }

    public void setBeneficiaryType(String beneficiaryType) {
        this.beneficiaryType = beneficiaryType;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getBeneficiaryWallet() {
        return beneficiaryWallet;
    }

    public void setBeneficiaryWallet(String beneficiaryWallet) {
        this.beneficiaryWallet = beneficiaryWallet;
    }

    public String getRetrivalReferenceNo() {
        return retrivalReferenceNo;
    }

    public void setRetrivalReferenceNo(String retrivalReferenceNo) {
        this.retrivalReferenceNo = retrivalReferenceNo;
    }

    public String getBeneficiaryId() {
        return beneficiaryId;
    }

    public void setBeneficiaryId(String beneficiaryId) {
        this.beneficiaryId = beneficiaryId;
    }

    public String getOtherPartyName() {
        return otherPartyName;
    }

    public void setOtherPartyName(String otherPartyName) {
        this.otherPartyName = otherPartyName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}