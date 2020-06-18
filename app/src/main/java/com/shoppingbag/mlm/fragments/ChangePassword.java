package com.shoppingbag.mlm.fragments;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

import com.shoppingbag.app.AppConfig;
import com.shoppingbag.app.PreferencesManager;
import com.shoppingbag.constants.BaseFragment;
import com.shoppingbag.constants.Cons;
import com.shoppingbag.model.response.ResponseForgotPass;
import com.shoppingbag.model.response.otp_req.ResponseRequestOTP;
import com.shoppingbag.utils.LoggerUtil;
import com.shoppingbag.utils.NetworkUtils;
import com.shoppingbag.R;
import com.shoppingbag.common_activities.LoginActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.shoppingbag.app.AppConfig.PROC_ONE;
import static com.shoppingbag.app.AppConfig.PROC_TWO;

public class ChangePassword extends BaseFragment {

    @BindView(R.id.btn_reset)
    Button btnReset;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.et_old_pswd)
    TextInputEditText etOldPswd;
    @BindView(R.id.et_new_pswd)
    TextInputEditText etNewPswd;
    @BindView(R.id.et_confrm_pswd)
    TextInputEditText etConfrmPswd;
    @BindView(R.id.forgot_trans_pwd)
    TextView forgotTransPwd;
    @BindView(R.id.msg_txt)
    TextView msgTxt;
    String through = "";
    private Unbinder unbinder;
    private SharedPreferences pref;
    private EditText editTextConfirmOtp;
    private int DELAY_TIME = 2000;
    private AlertDialog alertDialog;
    private Dialog resetPasswordDialog;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_change_password, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreatedStuff(View view, @Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            through = getArguments().getString("through");
        }

        if (through.equalsIgnoreCase("loginpwd"))//
            forgotTransPwd.setVisibility(View.GONE);
        else if (through.equalsIgnoreCase("transpwd"))
            forgotTransPwd.setVisibility(View.VISIBLE);


        pref = getActivity().getSharedPreferences(AppConfig.SHARED_PREF, 0);

        btnSubmit.setOnClickListener(view12 -> {
            if (NetworkUtils.getConnectivityStatus(context) == 0)
                showMessage(getResources().getString(R.string.alert_internet));
            else if (Validation())
                getChangedPassword("", "");
        });

        btnReset.setOnClickListener(view1 -> reset());

        forgotTransPwd.setOnClickListener(v -> openForgotPasswordDialog());
    }

    private void getChangedPassword(String mobileNo_st, String confirm_pass) {
        try {
            JsonObject param = new JsonObject();
            if (mobileNo_st.equalsIgnoreCase("")) {
                param.addProperty("Mobile", Cons.decryptMsg(PreferencesManager.getInstance(context).getMOBILE(), cross_intent));
                param.addProperty("OldPassword", etOldPswd.getText().toString().trim());
                param.addProperty("NewPassword", etNewPswd.getText().toString().trim());
                if (through.equalsIgnoreCase("loginpwd"))
                    param.addProperty("Formname", "Change Password");
                else if (through.equalsIgnoreCase("transpwd"))
                    param.addProperty("Formname", "Transaction Password");
            } else {
                param.addProperty("Mobile", mobileNo_st);
                param.addProperty("OldPassword", "");
                param.addProperty("NewPassword", confirm_pass);
                param.addProperty("Formname", "Forgot Transaction Password");
                param.addProperty("UpdatedBy", "");
            }

            param.addProperty("UpdatedBy", Cons.decryptMsg(PreferencesManager.getInstance(context).getUSERID(), cross_intent));

            showLoading();
            Call<JsonObject> call = apiServicesLogin.ChangePassword(bodyParam(param), PreferencesManager.getInstance(context).getANDROIDID());
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                    hideLoading();
                    try {
                        if (response.isSuccessful()) {
                            String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent);
                            JsonObject convertedObject = new Gson().fromJson(paramResponse, JsonObject.class);
                            LoggerUtil.logItem(convertedObject);

                            if (convertedObject.get("response").getAsString().equalsIgnoreCase("Success")) {
                                if (through.equalsIgnoreCase("loginpwd")) {
                                    showMessage("Updated Successfully, Kindly, login again to continue.");
                                    reset();
                                    int WAIT_TIME = 2000;
                                    new Handler().postDelayed(() -> {
                                        clearPrefrenceforTokenExpiry();

                                        Intent intent1 = new Intent(context, LoginActivity.class);
                                        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        context.startActivity(intent1);
                                        context.finish();
                                    }, WAIT_TIME);
                                } else if (through.equalsIgnoreCase("transpwd")) {
                                    showMessage("Transaction password updated successfully.");
                                    reset();
                                    getActivity().onBackPressed();
                                }

                            } else {
                                msgTxt.setText(convertedObject.get("response").getAsString());
                            }
                        } else {
                            clearPrefrenceforTokenExpiry();
                            getAndroidId();
                            goToActivityWithFinish(LoginActivity.class, null);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                    hideLoading();
                }
            });
        } catch (Error | Exception e) {
            e.printStackTrace();
        }
    }

    private boolean Validation() {
        if (etOldPswd.getText().toString().trim().length() == 0) {
            etOldPswd.setError("Please enter old password");
            return false;
        } else if (etOldPswd.getText().toString().length() < 6) {
            etOldPswd.setError("Invalid old password");
            return false;
        } else if (etNewPswd.getText().toString().trim().length() == 0) {
            etNewPswd.setError("Please enter new password");
            return false;
        } else if (etNewPswd.getText().toString().trim().length() < 6) {
            etNewPswd.setError("Invalid new password");
            return false;
        } else if (etConfrmPswd.getText().toString().trim().length() == 0) {
            etConfrmPswd.setError("Please enter confirm password");
            return false;
        } else if (etConfrmPswd.getText().toString().trim().length() < 6) {
            etConfrmPswd.setError("Invalid confirm password");
            return false;
        } else if (!etConfrmPswd.getText().toString().trim().equalsIgnoreCase(etNewPswd.getText().toString().trim())) {
            etConfrmPswd.setError("Password not matched");
            return false;
        }
        return true;
    }

    private void reset() {
        hideKeyboard();
        etOldPswd.setText("");
        etNewPswd.setText("");
        etConfrmPswd.setText("");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void openForgotPasswordDialog() {
        //Creating a LayoutInflater object for the dialog box
        LayoutInflater li = LayoutInflater.from(context);
        //Creating a view to get the dialog box
        View confirmDialog = li.inflate(R.layout.forgot_pass_dialog, null);

        //Initizliaing confirm button fo dialog box and edittext of dialog box
        AppCompatButton button_getpassword = confirmDialog.findViewById(R.id.button_getpassword);
        EditText mobile_number = confirmDialog.findViewById(R.id.mobile_number);
        EditText user_id = confirmDialog.findViewById(R.id.user_id);

        //Creating an alertdialog builder
        AlertDialog.Builder alert = new AlertDialog.Builder(context);

        //Adding our dialog box to the view of alert dialog
        alert.setView(confirmDialog);

        //Creating an alert dialog
        final AlertDialog alertDialog = alert.create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(true);

        //Displaying the alert dialog
        alertDialog.show();
        button_getpassword.setOnClickListener(view -> {
            try {
                String mobileNo_st = mobile_number.getText().toString().trim();
                String user_id_st = user_id.getText().toString().trim();
               /* if (user_id_st.length() == 0) {
                    user_id_st = "";
                    showError(getResources().getString(R.string.loginid_err), user_id);
                } else */
                {
                    if (mobileNo_st.length() == 0 || mobileNo_st.length() != 10) {
                        mobileNo_st = "";
                        showError(getResources().getString(R.string.valid_mob_no_err), mobile_number);
                    } else {
                        if (NetworkUtils.getConnectivityStatus(context) != 0) {
                            alertDialog.dismiss();
                            getForgotPass(mobileNo_st);
                        } else {
                            showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void getForgotPass(String mobileNo_st) {
        try {
            showLoading();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("Mobile", mobileNo_st);
            Call<JsonObject> responseForgotPassCall = apiServicesLogin.getForgotPassword(bodyParam(jsonObject), PreferencesManager.getInstance(context).getANDROIDID());
            responseForgotPassCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                    hideLoading();
                    LoggerUtil.logItem(response.body());
                    LoggerUtil.logItem(response.code());
                    ResponseForgotPass responseForgot_pass;
                    try {
                        if (response.isSuccessful()) {
                            String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent);
                            LoggerUtil.logItem(paramResponse);
                            Gson gson = new GsonBuilder().create();
                            responseForgot_pass = gson.fromJson(paramResponse, ResponseForgotPass.class);
                            if (response.body() != null && responseForgot_pass.getResponse().equalsIgnoreCase("Success")) {
                                sendCredentials(mobileNo_st, responseForgot_pass.getfk_memId(), "resetpass");
                            } else {
                                showAlert(responseForgot_pass.getMessage(), R.color.red, R.drawable.error);
                            }
                        } else {
                            showMessage("Something went wrong");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                    hideLoading();
                }
            });
        } catch (Error | Exception e) {
            e.printStackTrace();
        }
    }

    private void sendCredentials(String mobileNo_st, String fk_memId, String from) {
        try {
            showLoading();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("fk_memId", Integer.parseInt(fk_memId));
            jsonObject.addProperty("androidId", PreferencesManager.getInstance(context).getANDROIDID());
            jsonObject.addProperty("ProcId", PROC_ONE);
            jsonObject.addProperty("purpose", "forgot transaction password");
            LoggerUtil.logItem(jsonObject);

            Call<JsonObject> responseOtpReq = apiServicesLogin.getRequestotp(bodyParam(jsonObject), PreferencesManager.getInstance(context).getANDROIDID());
            responseOtpReq.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                    hideLoading();
                    LoggerUtil.logItem(response.body());
                    LoggerUtil.logItem(response.code());
                    ResponseRequestOTP responseRequestOTP;
                    try {
                        if (response.isSuccessful()) {
                            String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent);
                            LoggerUtil.logItem(paramResponse);
                            Gson gson = new GsonBuilder().create();
                            responseRequestOTP = gson.fromJson(paramResponse, ResponseRequestOTP.class);
                            if (response.body() != null && responseRequestOTP.getResponse().equalsIgnoreCase("Success")) {
                                showAlert(responseRequestOTP.getMessage(), R.color.green, R.drawable.alerter_ic_notifications);
//                            startSMSListener();
                                new Handler().postDelayed(() -> confirmOtpDialog(mobileNo_st, fk_memId, from), DELAY_TIME);
                            } else {
                                showAlert(responseRequestOTP.getMessage(), R.color.red, R.drawable.error);
                            }
                        } else {
                            showMessage("Something went wrong");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                    hideLoading();
                }
            });
        } catch (Error | Exception e) {
            e.printStackTrace();
        }
    }

    private void confirmOtpDialog(String mobileNo_st, String fk_memId, String from) {
        try {
            //Creating a LayoutInflater object for the dialog box
            LayoutInflater li = LayoutInflater.from(context);
            View confirmDialog = li.inflate(R.layout.otp_lay, null);
            AppCompatButton buttonConfirm = confirmDialog.findViewById(R.id.buttonConfirm);
            AppCompatButton buttonCncel = confirmDialog.findViewById(R.id.buttonCncel);
            TextView tv_resend_otp = confirmDialog.findViewById(R.id.tv_resend_otp);
            editTextConfirmOtp = confirmDialog.findViewById(R.id.editTextOtp);


            //Creating an alertdialog builder
            AlertDialog.Builder alert = new AlertDialog.Builder(context);

            //Adding our dialog box to the view of alert dialog
            alert.setView(confirmDialog);

            //Creating an alert dialog
            alertDialog = alert.create();
            alertDialog.setCancelable(false);
            alertDialog.setCanceledOnTouchOutside(false);

            tv_resend_otp.setOnClickListener(v -> {
                if (NetworkUtils.getConnectivityStatus(context) != 0) {
                    try {
                        alertDialog.dismiss();
                        sendCredentials(mobileNo_st, fk_memId, from);
                    } catch (Error | Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
                }
            });

            buttonCncel.setOnClickListener(v -> alertDialog.dismiss());

            buttonConfirm.setOnClickListener(view -> {
                confirmOtpAPI(mobileNo_st, fk_memId, from);
            });

            //Displaying the alert dialog
            alertDialog.show();
        } catch (Error | Exception e) {
            e.printStackTrace();
        }

    }

    private void confirmOtpAPI(String mobileNo_st, String fk_memId, String from) {
        try {
            showLoading();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("fk_memId", Integer.parseInt(fk_memId));
            jsonObject.addProperty("androidId", PreferencesManager.getInstance(context).getANDROIDID());
            jsonObject.addProperty("ProcId", PROC_TWO);
            jsonObject.addProperty("otpno", editTextConfirmOtp.getText().toString().trim());
            jsonObject.addProperty("data", pref.getString("regId", ""));
            LoggerUtil.logItem(jsonObject);
            Call<JsonObject> responseOtpReq = apiServicesLogin.getRequestotp(bodyParam(jsonObject), PreferencesManager.getInstance(context).getANDROIDID());
            responseOtpReq.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                    hideLoading();
                    LoggerUtil.logItem(response.body());
                    LoggerUtil.logItem(response.code());
                    ResponseRequestOTP responseRequestOTP;
                    try {
                        if (response.isSuccessful()) {
                            String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent);
                            LoggerUtil.logItem(paramResponse);
                            Gson gson = new GsonBuilder().create();
                            responseRequestOTP = gson.fromJson(paramResponse, ResponseRequestOTP.class);
                            if (response.body() != null && responseRequestOTP.getResponse().equalsIgnoreCase("Success")) {
                                alertDialog.dismiss();
//                                showMessage(responseRequestOTP.getMessage());
                                openResetPasswordDialog(mobileNo_st);
                            } else {
                                showMessage(responseRequestOTP.getMessage());
                            }
                        } else {
                            showMessage("Something went wrong.");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                    hideLoading();
                }
            });
        } catch (Error | Exception e) {
            e.printStackTrace();
        }
    }

    private void openResetPasswordDialog(String mobileNo_st) {
        try {
            hideKeyboard();
            resetPasswordDialog = new Dialog(context);
            resetPasswordDialog.setCanceledOnTouchOutside(true);
            resetPasswordDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            resetPasswordDialog.setContentView(R.layout.reset_pass_dialog);
            int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.80);
            int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.80);
            resetPasswordDialog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
            resetPasswordDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

            Button button_submit = resetPasswordDialog.findViewById(R.id.button_submit);
            EditText ed_Password = resetPasswordDialog.findViewById(R.id.ed_Password);
            EditText ed_Confirm_Password = resetPasswordDialog.findViewById(R.id.ed_Confirm_Password);

            TextView confirm_newpass_tv = resetPasswordDialog.findViewById(R.id.confirm_newpass_tv);
            TextView newpass_tv = resetPasswordDialog.findViewById(R.id.newpass_tv);
            newpass_tv.setText("New Transaction Password");
            confirm_newpass_tv.setText("Confirm Transaction Password");

            button_submit.setOnClickListener(v -> resetPasswordDialog.dismiss());
            button_submit.setOnClickListener(view -> {
                hideKeyboard();
                if (ed_Password.getText().toString().trim().length() != 0 && ed_Password.getText().toString().trim().length() >= 6) {
                    if (ed_Password.getText().toString().trim().equals(ed_Confirm_Password.getText().toString().trim())) {
                        getChangedPassword(mobileNo_st, ed_Confirm_Password.getText().toString().trim());
                        resetPasswordDialog.dismiss();
                    } else {
                        showMessage("New Password & Confirmed Password not matched.");
                    }
                } else {
                    showMessage("Please input password greater then 6 characters.");
                }
            });

            resetPasswordDialog.show();

        } catch (Exception e) {
            hideLoading();
        }

    }

    private void showError(String error_st, EditText editText) {
        Dialog error_dialog = new Dialog(context);
        error_dialog.setCanceledOnTouchOutside(false);
        error_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        error_dialog.setContentView(R.layout.error_dialoge);
        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
        int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.90);
        error_dialog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        error_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView error_text = error_dialog.findViewById(R.id.error_text);
        Button ok_btn = error_dialog.findViewById(R.id.ok_btn);
        error_text.setText(error_st);
        error_dialog.show();
        ok_btn.setOnClickListener(view -> {
            error_dialog.dismiss();
            requestFocus(editText);
        });
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            context.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}
