package com.shoppingbag.app;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Vivek on 17/9/18.
 */

public class PreferencesManager {
    //app login variables
    private static final String PREF_NAME = "com.shoppingbag";
    private static final String USERID = "user_id";
    private static final String LoginID = "login_id";
    private static final String Permission = "permission";

    private static final String REMITTER_ID = "remitter_id";

    private static final String PINCODE = "pincode";
    private static final String STATE = "state";
    private static final String CITY = "city";
    private static final String ADDRESS = "address";


    private static final String NAME = "name";
    private static final String STATUS = "status";
    private static final String DOB = "dob";
    private static final String LNAME = "lname";
    private static final String EMAIL = "email";
    private static final String MOBILE = "mobile";
    private static final String PROFILEPIC = "pic";
    private static final String BANK_ACCOUNT = "bankaccount";
    private static final String BANK_NAME = "bankname";
    private static final String BANK_IFSC = "ifsc";

    private static final String CARTCOUNT = "cart_count";
    private static final String TRANSACTION_PASS = "transaction_password";
    private static final String lastLogin = "lastLogin";
    private static final String InviteCode = "InviteCode";
    private static final String savedLOGINID = "savedLOGINID";
    private static final String savedPASSWORD = "savedPASSWORD";
    private static final String ANDROIDID = "android_id";
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    private static final String Token = "token";
    private static final String tracking_bool = "tracking_bool";
    private static final String TRACKING_TOKEN = "TRACKING_TOKEN";


    private static PreferencesManager sInstance;
    private final SharedPreferences mPref;

    private PreferencesManager(Context context) {
        mPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    //for fragment
    public static synchronized void initializeInstance(Context context) {
        if (sInstance == null) {
            sInstance = new PreferencesManager(context);
        }
    }

    //for getting instance
    public static synchronized PreferencesManager getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new PreferencesManager(context);
        }
        return sInstance;
    }


    public String getREMITTER_ID() {
        return mPref.getString(REMITTER_ID, "");
    }

    public void setREMITTER_ID(String value) {
        mPref.edit().putString(REMITTER_ID, value).apply();
    }


    //    saved login
    public String getSavedLOGINID() {
        return mPref.getString(savedLOGINID, "");
    }

    public void setSavedLOGINID(String value) {
        mPref.edit().putString(savedLOGINID, value).apply();
    }

    //    invide code
    public String getInviteCode() {
        return mPref.getString(InviteCode, "");
    }

    public void setInviteCode(String value) {
        mPref.edit().putString(InviteCode, value).apply();
    }

    //    saved password
    public String getSavedPASSWORD() {
        return mPref.getString(savedPASSWORD, "");
    }

    public void setSavedPASSWORD(String value) {
        mPref.edit().putString(savedPASSWORD, value).apply();
    }




    //  Last Login Date
    public String getLastLogin() {
        return mPref.getString(lastLogin, "");
    }

    public void setLastLogin(String value) {
        mPref.edit().putString(lastLogin, value).apply();
    }



    public boolean clear() {
        return mPref.edit().clear().commit();
    }

    public String getTransactionPass() {
        return mPref.getString(TRANSACTION_PASS, "");
    }

    public void setTransactionPass(String value) {
        mPref.edit().putString(TRANSACTION_PASS, value).apply();
    }


    public String getAddress() {
        return mPref.getString(ADDRESS, "");
    }

    public void setAddress(String value) {
        mPref.edit().putString(ADDRESS, value).apply();
    }


    public String getCity() {
        return mPref.getString(CITY, "");
    }

    public void setCity(String value) {
        mPref.edit().putString(CITY, value).apply();
    }

    public String getSate() {
        return mPref.getString(STATE, "");
    }

    public void setState(String value) {
        mPref.edit().putString(STATE, value).apply();
    }

    public String getPINCODE() {
        return mPref.getString(PINCODE, "");
    }

    public void setPINCODE(String value) {
        mPref.edit().putString(PINCODE, value).apply();
    }


    public String getLoginID() {
        return mPref.getString(LoginID, "");
    }

    public void setLoginID(String value) {
        mPref.edit().putString(LoginID, value).apply();
    }

    public String getUSERID() {
        return mPref.getString(USERID, "");
    }

    public void setPermission(boolean value) {
        mPref.edit().putBoolean(Permission, value).apply();
    }

    public boolean getPermission() {
        return mPref.getBoolean(Permission, false);
    }

    public void setUSERID(String value) {
        mPref.edit().putString(USERID, value).apply();
    }


    public String getNAME() {
        return mPref.getString(NAME, "");
    }

    public void setNAME(String value) {
        mPref.edit().putString(NAME, value).apply();
    }

    public void setStatus(String value) {
        mPref.edit().putString(STATUS, value).apply();
    }
    public String getStatus() {
        return mPref.getString(STATUS, "");
    }

    public String getDOB() {
        return mPref.getString(DOB, "");
    }

    public void setDOB(String value) {
        mPref.edit().putString(DOB, value).apply();
    }

    public String getANDROIDID() {
        return mPref.getString(ANDROIDID, "");
    }

    public void setANDROIDID(String value) {
        mPref.edit().putString(ANDROIDID, value).apply();
    }


    public String getLNAME() {
        return mPref.getString(LNAME, "");
    }

    public void setLNAME(String value) {
        mPref.edit().putString(LNAME, value).apply();
    }

    public String getEMAIL() {
        return mPref.getString(EMAIL, "");
    }

    public void setEMAIL(String value) {
        mPref.edit().putString(EMAIL, value).apply();
    }

    public String getMOBILE() {
        return mPref.getString(MOBILE, "");
    }

    public void setMOBILE(String value) {
        mPref.edit().putString(MOBILE, value).apply();
    }

    public String getPROFILEPIC() {
        return mPref.getString(PROFILEPIC, "");
    }

    public void setPROFILEPIC(String value) {
        mPref.edit().putString(PROFILEPIC, value).apply();
    }

    public String getBankAccount() {
        return mPref.getString(BANK_ACCOUNT, "");
    }

    public void setBankAccount(String value) {
        mPref.edit().putString(BANK_ACCOUNT, value).apply();
    }

    public String getBankIfsc() {
        return mPref.getString(BANK_IFSC, "");
    }

    public void setBankIfsc(String value) {
        mPref.edit().putString(BANK_IFSC, value).apply();
    }

    public String getBankName() {
        return mPref.getString(BANK_NAME, "");
    }

    public void setBankName(String value) {
        mPref.edit().putString(BANK_NAME, value).apply();
    }

    public int getCartCount() {
        return mPref.getInt(CARTCOUNT, 0);
    }

    public void setCartCount(int value) {
        mPref.edit().putInt(CARTCOUNT, value).apply();
    }

    public boolean getIsFirstTimeLaunch() {
        return mPref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public void setIsFirstTimeLaunch(boolean value) {
        mPref.edit().putBoolean(IS_FIRST_TIME_LAUNCH, value).apply();
    }

    public boolean gettracking_bool() {
        return mPref.getBoolean(tracking_bool, false);
    }

    public void settracking_bool(boolean value) {
        mPref.edit().putBoolean(tracking_bool, value).apply();
    }

    public void setTrackingToken(String value){
        mPref.edit().putString(TRACKING_TOKEN,value).apply();
    }
    public String getTrackingToken(){
        return mPref.getString(TRACKING_TOKEN,"");
    }







}
