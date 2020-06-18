package com.shoppingbag.app;

import static com.shoppingbag.BuildConfig.BUNDLE;

public class AppConfig {
    public static final String PAYLOAD_BUNDLE = BUNDLE;
    public static final String PAYLOAD_DEBIT_TYPE_SHOPPING = "Shopping";
    public static final String TYPE_PREPAID_RECHARGE = "Prepaid Mobile Recharge";
    public static final String TYPE_POSTPAID_RECHARGE = "Postpaid Mobile Recharge";
    public static final String TYPE_DTH_RECHARGE = "DTH Recharge";
    public static final String SHARED_PREF = "ah_firebase";

    //DMT Type
    public static final String PAYLOAD_TYPE_FIVE_DMT_CUSTOMER_DETAILS = "5";                        //Type=5: To fetch customer details.
    public static final String PAYLOAD_TYPE_ZERO_DMT_CUSTOMER_REGISTRATION = "0";                   //Type=0: To register new customer
    public static final String PAYLOAD_TYPE_FOUR_DMT_BENEFICIARY_REGISTRATION = "4";                //Type=4: To add new beneficiary
    public static final String PAYLOAD_TYPE_TWO_DMT_BENEFICIARY_REGISTRATION_VALIDATE_OTP = "2";    //Type=2: To verify OTP for beneficiary verification.
    public static final String PAYLOAD_TYPE_NINE_DMT_BENEFICIARY_REGISTRATION_RESEND_OTP = "9";     //Type=9: To generate OTP for add and delete beneficiary
    public static final String PAYLOAD_TYPE_TEN_DMT_BENEFICIARY_ACCOUNT_VERIFICATION = "10";        //Type=10: To account validation
    public static final String PAYLOAD_TYPE_THREE_DMT_FUND_TRANSFER = "3";                          //Type=3: To transfer funds
    public static final String PAYLOAD_TYPE_SIX_DMT_BENEFICIARY_DELETE = "6";                       //Type=6: To delete beneficiary
    public static final String PAYLOAD_TYPE_TWENTY_THREE_DMT_BENEFICIARY_DELETE_VALIDATE_OTP = "23";    //Type=23: To verify OTP for delete beneficiary verification.
    public static final String PAYLOAD_TYPE_FIFTEEN_DMT_BANK_DETAILS = "15";                        //Type=15: To get Bank Details
    public static final String PAYLOAD_TYPE_TWENTY_TWO_DMT_REFUND_TRANSACTION = "22";               //Type=22: To get refund transaction. Please refer the details provided before Refund API logs.
    public static final String PAYLOAD_TYPE_TWENTY_FOUR_DMT_BENEFICIARY_UPDATE = "24";               //Type=24: To Update beneficiary
    public static final String PAYLOAD_TYPE_FOURTEEN_DMT_TRANSACTION_HISTORY = "14";                //Type=14: To get transaction history of funds transfer.


    public static final String PROC_ONE = "1";// ASK SERVER TO SEND OTP MESSAGE
    public static final String PROC_TWO = "2"; // ASK SERVER TO CONFIRM OTP MESSAGE
    public static final String PROC_THREE = "3"; // ASK SERVER TO MESSAGE OUR CONTENT
    public static final String PROC_FOUR = "4";  // ASK SERVER TO SEND MESSAGE WITHOUT FK_MEM_ID
    public static final String PAYLOAD_ACCOUNT_RECHARGE_TWO = "2";

    public static String authToken = "";
    public static String ANDROIDID = "";



}
