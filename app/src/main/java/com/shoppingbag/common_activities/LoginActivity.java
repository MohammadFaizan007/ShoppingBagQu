package com.shoppingbag.common_activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.shoppingbag.R;
import com.shoppingbag.app.AppConfig;
import com.shoppingbag.app.PreferencesManager;
import com.shoppingbag.constants.BaseActivity;
import com.shoppingbag.constants.Cons;
import com.shoppingbag.model.response.ResponseForgotPass;
import com.shoppingbag.model.response.ResponseLogin;
import com.shoppingbag.model.response.otp_req.ResponseRequestOTP;
import com.shoppingbag.utils.LoggerUtil;
import com.shoppingbag.utils.NetworkUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.shoppingbag.app.AppConfig.PROC_ONE;
import static com.shoppingbag.app.AppConfig.PROC_TWO;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.et_reg_mob_no)
    TextInputEditText etRegMobNo;
    @BindView(R.id.et_password)
    TextInputEditText etPassword;
    @BindView(R.id.input_layout_password)
    TextInputLayout inputLayoutPassword;
    @BindView(R.id.login_btn)
    Button loginBtn;
    @BindView(R.id.forgot_password)
    TextView forgotPassword;
    @BindView(R.id.register)
    TextView register;
    @BindView(R.id.textView3)
    TextView textView3;
    @BindView(R.id.txt_terms)
    TextView txtTerms;
    @BindView(R.id.txt_privacy)
    TextView txtPrivacy;
    @BindView(R.id.txt_uname)
    TextView txtUname;
    private Dialog resetPasswordDialog;
    private String mobileNo_st = "";
    private String password_st = "";
    private EditText editTextConfirmOtp;
    private int DELAY_TIME = 2000;
    private AlertDialog alertDialog;

    private void getToken() {
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(LoginActivity.this, instanceIdResult -> {
            String newToken = instanceIdResult.getToken();
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("regId", newToken);
            editor.apply();
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login_new);
            ButterKnife.bind(this);
            getToken();

//            String account = getColoredSpanned(getResources().getString(R.string.new_to_kkm), "#000000");
            String signup = getColoredSpanned("<b>" + getResources().getString(R.string.register_now) + "</b>", "#0281d5");
            register.setText(Html.fromHtml(signup));

            Bundle bundle = getIntent().getBundleExtra(AppConfig.PAYLOAD_BUNDLE);
            if (bundle != null) {
                etRegMobNo.setText(bundle.getString("mobile"));
                txtUname.setText("Welcome ! " + bundle.getString("username"));
                txtUname.setVisibility(View.VISIBLE);
                textView3.setVisibility(View.VISIBLE);
            } else {
                txtUname.setText("Welcome ! User");
                txtUname.setVisibility(View.VISIBLE);
                textView3.setVisibility(View.VISIBLE);
            }


        } catch (Error | Exception e) {
            e.printStackTrace();
        }
    }

    private String getColoredSpanned(String text, String color) {
        return "<font color=" + color + ">" + text + "</font>";
    }

    @OnClick({R.id.login_btn, R.id.forgot_password, R.id.register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_btn:
                if (Validation()) {
                    if (NetworkUtils.getConnectivityStatus(context) != 0) {
                        getLoginByEncryption();
                    } else {
                        showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
                    }
                }
                break;
            case R.id.forgot_password:
                openForgotPasswordDialog();
                break;
            case R.id.register:
//                Bundle bundle = new Bundle();
//                bundle.putString("from", "login");
//                bundle.putString("mobile", "");
//                goToActivityWithFinish(LoginActivity.this, RegistrationActivity.class, bundle);
                break;
        }
    }

    private boolean Validation() {
        try {
            mobileNo_st = etRegMobNo.getText().toString().trim();
            password_st = etPassword.getText().toString().trim();

            if (mobileNo_st.length() != 10) {
                mobileNo_st = "";
                showError(getResources().getString(R.string.reg_mob_no_err), etRegMobNo);
                return false;
            }
            if (password_st.length() == 0) {
                password_st = "";
                showError(getResources().getString(R.string.enter_pswd_err), etPassword);
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    private void showError(String error_st, EditText editText) {
        Dialog error_dialog = new Dialog(this);
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
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
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
                {
                    if (mobileNo_st.length() != 10) {
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
            jsonObject.addProperty("Fk_memId", Integer.parseInt(fk_memId));
            jsonObject.addProperty("AndroidId", PreferencesManager.getInstance(context).getANDROIDID());
            jsonObject.addProperty("ProcId", PROC_ONE);
            jsonObject.addProperty("Purpose", "login");
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

    private void allowLogin(ResponseLogin response) {
        try {
            LoggerUtil.logItem("Allow Login");
            AppConfig.authToken = response.getResult().getToken();
            LoggerUtil.logItem(AppConfig.authToken);
            PreferencesManager.getInstance(context).setUSERID(Cons.encryptMsg(String.valueOf(response.getResult().getMemberId()), cross_intent));
            PreferencesManager.getInstance(context).setLoginID(Cons.encryptMsg(response.getResult().getLoginId(), cross_intent));
            PreferencesManager.getInstance(context).setMOBILE(Cons.encryptMsg(response.getResult().getMobile(), cross_intent));
            PreferencesManager.getInstance(context).setNAME(Cons.encryptMsg(response.getResult().getName(), cross_intent));
            PreferencesManager.getInstance(context).setLNAME(Cons.encryptMsg(response.getResult().getLastName(), cross_intent));
            PreferencesManager.getInstance(context).setEMAIL(Cons.encryptMsg(response.getResult().getEmail(), cross_intent));
            PreferencesManager.getInstance(context).setPINCODE(Cons.encryptMsg(response.getResult().getPincode(), cross_intent));
            PreferencesManager.getInstance(context).setState(Cons.encryptMsg(response.getResult().getState(), cross_intent));
            PreferencesManager.getInstance(context).setCity(Cons.encryptMsg(response.getResult().getCity(), cross_intent));
            PreferencesManager.getInstance(context).setLastLogin(Cons.encryptMsg(response.getResult().getLastLogin(), cross_intent));
            PreferencesManager.getInstance(context).setInviteCode(response.getResult().getInviteCode());
            PreferencesManager.getInstance(context).setPROFILEPIC(response.getResult().getProfilePic());
            Log.e("============", "=====================================" + response.getResult().getProfilePic());

            PreferencesManager.getInstance(context).setStatus(response.getResult().getStatus());
            PreferencesManager.getInstance(context).setBankAccount(response.getResult().getBankaccount());
            PreferencesManager.getInstance(context).setBankIfsc(response.getResult().getBenIFSC());
            PreferencesManager.getInstance(context).setBankName(response.getResult().getBankname());
            PreferencesManager.getInstance(context).setSavedLOGINID(Cons.encryptMsg(mobileNo_st, cross_intent));
            PreferencesManager.getInstance(context).setSavedPASSWORD(Cons.encryptMsg(password_st, cross_intent));

            if (PreferencesManager.getInstance(context).getPermission()) {
                goToActivity(LoginActivity.this, MainContainer.class, null);
            } else {
                goToActivity(LoginActivity.this, Permission.class, null);
            }
            finishAffinity();
        } catch (Error | Exception e) {
            e.printStackTrace();
        }

    }

    private void getLoginByEncryption() {
        try {
            showLoading();
            JsonObject param = new JsonObject();
            param.addProperty("LoginID", mobileNo_st);
            param.addProperty("Password", password_st);
            param.addProperty("DeviceId", pref.getString("regId", ""));
            param.addProperty("AndroidId", AppConfig.ANDROIDID);
            param.addProperty("DeviceType", "A");
            param.addProperty("Update", "Yes");

            LoggerUtil.logItem(param);
            Call<JsonObject> loginCall = apiServicesLogin.getLogin(bodyParam(param), PreferencesManager.getInstance(context).getANDROIDID());
            loginCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                    hideLoading();
                    LoggerUtil.logItem(response.body());
                    LoggerUtil.logItem(response.code());
                    LoggerUtil.logItem(call.request().url().toString());
                    ResponseLogin responseLogin;
                    try {
                        if (response.isSuccessful()) {
                            String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent);
                            LoggerUtil.logItem(paramResponse);
                            Gson gson = new GsonBuilder().create();
                            responseLogin = gson.fromJson(paramResponse, ResponseLogin.class);
                            if (response.body() != null && responseLogin.getResponse().equalsIgnoreCase("Success")) {
                                if (responseLogin.getResult().getMessage().equalsIgnoreCase("Device Id not Match")) {
                                    allowLogin(responseLogin);
                                } else if (responseLogin.getResult().getMessage().equalsIgnoreCase("Device Id Match")) {
                                    allowLogin(responseLogin);
                                }
                            } else if (response.body() == null) {
                                goToActivityWithFinish(context, MaintenancePage.class, null);
                            } else if (responseLogin.getMessage().contains("website is under maintenance")) {
                                goToActivityWithFinish(context, MaintenancePage.class, null);
                            } else if (responseLogin.getMessage().contains("stop")) {
                                Bundle b = new Bundle();
                                b.putString("msg", responseLogin.getMessage());
                                goToActivityWithFinish(context, MaintenancePage.class, b);
                            } else if (responseLogin.getMessage().contains("not Valid")) {
                                showError("Mobile Number/Email or password is not valid.", etPassword);
                            } else {
                                showMessage(responseLogin.getMessage());
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
                    LoggerUtil.logItem(t.getMessage());
                    LoggerUtil.logItem(call.request().url().toString());
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
            jsonObject.addProperty("Fk_memId", Integer.parseInt(fk_memId));
            jsonObject.addProperty("AndroidId", PreferencesManager.getInstance(context).getANDROIDID());
            jsonObject.addProperty("ProcId", PROC_TWO);
            jsonObject.addProperty("Otpno", editTextConfirmOtp.getText().toString().trim());
            jsonObject.addProperty("Data", pref.getString("regId", ""));
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
                                if (from.equalsIgnoreCase("login")) {
                                    if (NetworkUtils.getConnectivityStatus(context) != 0) {
                                        getLoginByEncryption();
                                    } else {
                                        showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
                                    }
                                } else
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

    private void getChangedPassword(String mobileNo_st, String newpass) {
        try {
            JsonObject param = new JsonObject();
            param.addProperty("Mobile", mobileNo_st);
            param.addProperty("OldPassword", "");
            param.addProperty("NewPassword", newpass);
            param.addProperty("Formname", "Forgot Password");
            param.addProperty("UpdatedBy", "");
            param.addProperty("Fk_MemId", "");

            LoggerUtil.logItem(param);
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

                            ////Log.e("Resp", convertedObject.toString());

                            if (convertedObject.get("response").getAsString().equalsIgnoreCase("Success")) {
                                showMessage("Updated Successfully, Kindly, login to continue.");

//                                int WAIT_TIME = 2000;
//                                new Handler().postDelayed(() -> {
                                clearPrefrenceforTokenExpiry();

//                                    Intent intent1 = new Intent(context, LoginActivity.class);
//                                    intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                    intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                    intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                    context.startActivity(intent1);
//                                    context.finish();
//                                }, WAIT_TIME);


                            } else {
                                showMessage(convertedObject.get("response").getAsString());
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

}
