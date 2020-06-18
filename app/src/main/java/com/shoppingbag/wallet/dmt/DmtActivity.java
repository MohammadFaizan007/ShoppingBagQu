package com.shoppingbag.wallet.dmt;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.shoppingbag.constants.BaseActivity;
import com.shoppingbag.utils.LoggerUtil;
import com.shoppingbag.R;
import com.shoppingbag.wallet.dmt.addmoney.DmtRemitterFragment;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DmtActivity extends BaseActivity {


    public Fragment currentFragment;
    @BindView(R.id.side_menu)
    ImageView sideMenu;
    @BindView(R.id.title)
    TextView title;
//    private EditText editTextConfirmOtp;
//    private AlertDialog alertDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dmt_activity_lay);
        ButterKnife.bind(this);
        sideMenu.setOnClickListener(v -> onBackPressed());
        title.setText("Transfer Commission");

//        if (PreferencesManager.getInstance(context).getREMITTER_ID().equalsIgnoreCase("")) {
//            if (NetworkUtils.getConnectivityStatus(context) != 0) {
//                getCustomerVerification();
//            } else {
//                showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
//            }
//        } else {
            replaceFragmentWithHome(new DmtRemitterFragment(), "DMT");
//        }
    }

//    public void getCustomerVerification() {
//        try {
//            showLoading();
//            JsonObject object = new JsonObject();
//            object.addProperty("Type", PAYLOAD_TYPE_FIVE_DMT_CUSTOMER_DETAILS);
//            object.addProperty("NUMBER", Cons.decryptMsg(PreferencesManager.getInstance(context).getMOBILE(), cross_intent));
//            object.addProperty("AMOUNT_ALL", "1.00");
//            object.addProperty("Amount", "1.00");
//
//            LoggerUtil.logItem(object);
//
//            Call<JsonObject> objectCall = apiServicesCyper.getCustomerVerification(bodyParam(object), PreferencesManager.getInstance(context).getANDROIDID());
//            objectCall.enqueue(new Callback<JsonObject>() {
//                @Override
//                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
//                    try {
//                        hideLoading();
//                        LoggerUtil.logItem("getCustomerVerification");
//                        LoggerUtil.logItem(response.body());
//                        if (response.isSuccessful()) {
//                            JsonArray response_new = new Gson().fromJson(Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent), JsonArray.class);
//                            JsonObject jobj = response_new.get(0).getAsJsonObject();
//                            LoggerUtil.logItem(response_new.toString());
//                            if ((jobj.get("ERROR").getAsString()).equalsIgnoreCase("0") &&
//                                    (jobj.get("RESULT").getAsString()).equalsIgnoreCase("0")) {
//                                JSONObject addinfo = new JSONObject((Utils.replaceBackSlash(jobj.get("ADDINFO").getAsString())));
//                                JSONObject data = addinfo.getJSONObject("data").getJSONObject("remitter");
//                                LoggerUtil.logItem(data);
//                                if (data.getString("is_verified").equalsIgnoreCase("1")) {
//                                    PreferencesManager.getInstance(context).setREMITTER_ID(Cons.encryptMsg(data.getString("id"), cross_intent));
//                                    replaceFragmentWithHome(new DmtRemitterFragment(), "DMT");
//                                } else {
//                                    confirmOtp(data.getString("id"));
//                                }
//                            } else {
//                                getRemitterRegister();
//                            }
//                        } else if (response.code() == 403) {
//                            LoggerUtil.logItem("403");
////                            clearPrefrenceforTokenExpiry();
////                            getAndroidId();
////                            goToActivityWithFinish(LoginActivity.class, null);
//                        } else {
//                            goToActivityWithFinish(MaintenancePage.class, null);
//                        }
//                    } catch (Error | Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                @Override
//                public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
//                    hideLoading();
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//            hideLoading();
//        }
//    }

//    private void getRemitterRegister() {
//        try {
//            showLoading();
//            JsonObject object = new JsonObject();
//            object.addProperty("Type", PAYLOAD_TYPE_ZERO_DMT_CUSTOMER_REGISTRATION);
//            object.addProperty("NUMBER", Cons.decryptMsg(PreferencesManager.getInstance(context).getMOBILE(), cross_intent));
//            object.addProperty("fName", Cons.decryptMsg(PreferencesManager.getInstance(context).getNAME(), cross_intent));
//            object.addProperty("lName", Cons.decryptMsg(PreferencesManager.getInstance(context).getLNAME(), cross_intent));
//            object.addProperty("Pin", Cons.decryptMsg(PreferencesManager.getInstance(context).getPINCODE(), cross_intent));
//            object.addProperty("AMOUNT", "1.00");
//            object.addProperty("AMOUNT_ALL", "1.00");
//            LoggerUtil.logItem(object);
//
//            Call<JsonObject> objectCall = apiServicesCyper.getRemitterRegistration(bodyParam(object), PreferencesManager.getInstance(context).getANDROIDID());
//            objectCall.enqueue(new Callback<JsonObject>() {
//                @Override
//                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
//                    try {
//                        hideLoading();
//                        LoggerUtil.logItem("getRemitterRegister");
//                        LoggerUtil.logItem(response.body());
//                        if (response.isSuccessful()) {
//                            JsonArray response_new = new Gson().fromJson(Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent), JsonArray.class);
//                            LoggerUtil.logItem(response_new.toString());
//                            JsonObject jobj = response_new.get(0).getAsJsonObject();
//                            if ((jobj.get("ERROR").getAsString()).equalsIgnoreCase("0") && (jobj.get("RESULT").getAsString()).equalsIgnoreCase("0")) {
//                                JSONObject addinfo = new JSONObject((Utils.replaceBackSlash(jobj.get("ADDINFO").getAsString())));
//                                JSONObject data = addinfo.getJSONObject("data").getJSONObject("remitter");
//                                confirmOtp(data.getString("id"));
//                            }
//                        } else if (response.code() == 403) {
////                            clearPrefrenceforTokenExpiry();
////                            getAndroidId();
////                            goToActivityWithFinish(LoginActivity.class, null);
//                        } else {
//                            goToActivityWithFinish(MaintenancePage.class, null);
//                        }
//                    } catch (Error | Exception e) {
//                        hideLoading();
//                        e.printStackTrace();
//                    }
//                }
//
//                @Override
//                public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
//                    hideLoading();
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//            hideLoading();
//        }
//    }

//    private void confirmOtp(String remitterId) {
//        //Creating a LayoutInflater object for the dialog box
//        LayoutInflater li = LayoutInflater.from(context);
//        //Creating a view to get the dialog box
//        View confirmDialog = li.inflate(R.layout.dialog_otp_verification, null);
//        //Initizliaing confirm button fo dialog box and edittext of dialog box
//        Button buttonConfirm = confirmDialog.findViewById(R.id.buttonConfirm);
//        Button btn_cancel = confirmDialog.findViewById(R.id.btn_cancel);
//        editTextConfirmOtp = confirmDialog.findViewById(R.id.editTextOtp);
//        //Creating an alertdialog builder
//        AlertDialog.Builder alert = new AlertDialog.Builder(context);
//        //Adding our dialog box to the view of alert dialog
//        alert.setView(confirmDialog);
//        //Creating an alert dialog
//        alertDialog = alert.create();
//        alertDialog.setCancelable(false);
//        alertDialog.setCanceledOnTouchOutside(false);
//        //Displaying the alert dialog
//        alertDialog.show();
//        btn_cancel.setOnClickListener(v -> {
//            hideKeyboard();
//            alertDialog.dismiss();
//        });
//        buttonConfirm.setOnClickListener(view -> {
//            if (TextUtils.isEmpty(editTextConfirmOtp.getText().toString().trim())) {
//                editTextConfirmOtp.setText("");
//                editTextConfirmOtp.setError("Invalid OTP");
//            } else if (editTextConfirmOtp.getText().toString().trim().length() < 4) {
//                editTextConfirmOtp.setError("Invalid OTP");
//            } else {
//                hideKeyboard();
//                getRemitterRegistrationValidationOTP(remitterId, editTextConfirmOtp.getText().toString().trim());
//
//            }
//        });
//    }

//    private void getRemitterRegistrationValidationOTP(String remId, String otp) {
//        try {
//
//            JsonObject object = new JsonObject();
//            object.addProperty("Type", PAYLOAD_TYPE_TWENTY_FOUR_DMT_BENEFICIARY_UPDATE);
//            object.addProperty("NUMBER", Cons.decryptMsg(PreferencesManager.getInstance(context).getMOBILE(), cross_intent));
//            object.addProperty("remId", remId);
//            object.addProperty("otc", otp);
//            object.addProperty("AMOUNT", "1.00");
//            object.addProperty("AMOUNT_ALL", "1.00");
//
//            LoggerUtil.logItem(object);
//
//            Call<JsonObject> objectCall = apiServicesCyper.getRemitterRegistrationValidationOTP(bodyParam(object), PreferencesManager.getInstance(context).getANDROIDID());
//            objectCall.enqueue(new Callback<JsonObject>() {
//                @Override
//                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
//                    try {
//                        LoggerUtil.logItem(response.body());
//                        if (response.isSuccessful()) {
//                            JsonArray response_new = new Gson().fromJson(Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent), JsonArray.class);
//                            JsonObject jobj = response_new.get(0).getAsJsonObject();
//                            if ((jobj.get("ERROR").getAsString()).equalsIgnoreCase("0") && (jobj.get("RESULT").getAsString()).equalsIgnoreCase("0")) {
//                                alertDialog.dismiss();
//                                PreferencesManager.getInstance(context).setREMITTER_ID(Cons.encryptMsg(remId, cross_intent));
//                                replaceFragmentWithHome(new DmtRemitterFragment(), "DMT");
//                            } else {
//                                editTextConfirmOtp.setText("");
//                                editTextConfirmOtp.setError("Invalid OTP");
//                            }
//                        } else if (response.code() == 403) {
//                            clearPrefrenceforTokenExpiry();
//                            getAndroidId();
//                            goToActivityWithFinish(LoginActivity.class, null);
//                        } else {
//                            goToActivityWithFinish(MaintenancePage.class, null);
//                        }
//                    } catch (Error | Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                @Override
//                public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
//                    alertDialog.dismiss();
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        LoggerUtil.logItem(fm.getBackStackEntryCount());
        if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStackImmediate();
        } else {
            super.onBackPressed();
        }
    }

    public void replaceFragmentWithHome(Fragment setFragment, String title) {
        currentFragment = setFragment;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame, setFragment, title);
        transaction.commit();
    }

    public void replaceFragmentAddBack(Fragment setFragment, String title) {
        currentFragment = setFragment;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction().addToBackStack(title);
        transaction.replace(R.id.frame, setFragment, title);
        transaction.commit();
    }

}
