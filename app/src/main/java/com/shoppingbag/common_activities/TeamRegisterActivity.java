package com.shoppingbag.common_activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.shoppingbag.BuildConfig;
import com.shoppingbag.R;
import com.shoppingbag.app.AppConfig;
import com.shoppingbag.app.PreferencesManager;
import com.shoppingbag.constants.BaseActivity;
import com.shoppingbag.constants.Cons;
import com.shoppingbag.model.response.ResponseRegistration;
import com.shoppingbag.model.response.otp_req.ResponseRequestOTP;
import com.shoppingbag.model.response.pincode.ResponsePincode;
import com.shoppingbag.utils.LoggerUtil;
import com.shoppingbag.utils.NetworkUtils;
import com.shoppingbag.utils.Utils;

import java.security.GeneralSecurityException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.shoppingbag.app.AppConfig.PROC_FOUR;
import static com.shoppingbag.app.AppConfig.PROC_THREE;
import static com.shoppingbag.app.AppConfig.PROC_TWO;

public class TeamRegisterActivity extends BaseActivity {
    @BindView(R.id.side_menu)
    ImageView sideMenu;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.bell_icon)
    ImageView bellIcon;
    @BindView(R.id.et_invite_code)
    TextInputEditText etInviteCode;
    @BindView(R.id.input_invite_code)
    TextInputLayout inputInviteCode;
    @BindView(R.id.tv_ref_name)
    TextView tvRefName;
    @BindView(R.id.et_first_name)
    TextInputEditText etFirstName;
    @BindView(R.id.input_name_lo)
    TextInputLayout inputNameLo;
    @BindView(R.id.et_lastname)
    TextInputEditText etLastname;
    @BindView(R.id.input_nameL_lo)
    TextInputLayout inputNameLLo;
    @BindView(R.id.img_contact1)
    TextView imgContact1;
    @BindView(R.id.et_mob_no)
    TextInputEditText etMobNo;
    @BindView(R.id.input_layout_mob_no)
    TextInputLayout inputLayoutMobNo;
    @BindView(R.id.et_email)
    TextInputEditText etEmail;
    @BindView(R.id.input_layout_email)
    TextInputLayout inputLayoutEmail;
    @BindView(R.id.et_password)
    TextInputEditText etPassword;
    @BindView(R.id.input_layout_password)
    TextInputLayout inputLayoutPassword;
    @BindView(R.id.et_pincode)
    TextInputEditText etPincode;
    @BindView(R.id.input_pin_lo)
    TextInputLayout inputPinLo;
    @BindView(R.id.pincodeProgress)
    ProgressBar pincodeProgress;
    @BindView(R.id.et_state)
    TextInputEditText etState;
    @BindView(R.id.input_state_lo)
    TextInputLayout inputStateLo;
    @BindView(R.id.et_city)
    TextInputEditText etCity;
    @BindView(R.id.input_city_lo)
    TextInputLayout inputCityLo;
    @BindView(R.id.signup_btn)
    Button signupBtn;
    private String etName_st = "", referral_mob_no_st = "", mobileno_st = "", pswd_st = "", email_id_st = "";

    private EditText editTextConfirmOtp;
    private int DELAY_TIME = 2000;
    private AlertDialog alertDialog;
    private TextView tv_resend_otp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_register);
        ButterKnife.bind(this);
        title.setText("Registration");
        etInviteCode.setText(PreferencesManager.getInstance(context).getInviteCode());
        try {
            tvRefName.setText(Cons.decryptMsg(PreferencesManager.getInstance(context).getNAME(), cross_intent));
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }

        etPincode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() != 0 && s.length() == 6) {
                    hideKeyboard();
                    getStateCityName(etPincode.getText().toString().trim());
                } else {
                    etCity.setText("");
                    etState.setText("");
                }
            }
        });
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Validation()) {
                    if (NetworkUtils.getConnectivityStatus(context) != 0) {
                        sendCredentials(mobileno_st);
                    } else {
                        showMessage(getResources().getString(R.string.alert_internet));
                    }
                }
            }
        });
    }

    private void getStateCityName(String pincode) {
        try {
            pincodeProgress.setVisibility(View.VISIBLE);
            String url = BuildConfig.PINCODEURL + Cons.encryptMsg(pincode, cross_intent);
            LoggerUtil.logItem(url);
            Call<JsonObject> getCity = apiServicesLogin.getStateCity(url, PreferencesManager.getInstance(context).getANDROIDID());
            getCity.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                    LoggerUtil.logItem(response.body());
                    pincodeProgress.setVisibility(View.GONE);
                    try {
                        String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent);
                        LoggerUtil.logItem(paramResponse);
                        Gson gson = new GsonBuilder().create();
                        ResponsePincode responseRequestOTP = gson.fromJson(paramResponse, ResponsePincode.class);
                        if (response.body() != null && responseRequestOTP.getResponse().equalsIgnoreCase("Success")) {
                            etCity.setText(responseRequestOTP.getResult().get(0).getDistrictName());
                            etState.setText(responseRequestOTP.getResult().get(0).getStateName());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        etCity.setText("");
                        etState.setText("");
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

    private void getRegistered() {
        try {
//            showLoading();
            JsonObject param = new JsonObject();
            param.addProperty("MobileNo", mobileno_st);

            if (tvRefName.getText().toString().trim().equalsIgnoreCase("")) {
                param.addProperty("InvitedBy", "DREAMYDROSHKY");
            } else {
                param.addProperty("InvitedBy", etInviteCode.getText().toString().trim());
            }
            param.addProperty("Password", pswd_st);
            param.addProperty("Email", email_id_st);
            param.addProperty("CreatedBy", Cons.decryptMsg(PreferencesManager.getInstance(context).getUSERID(), cross_intent));
            param.addProperty("FirstName", etName_st);
            param.addProperty("LastName", etLastname.getText().toString().trim());
            param.addProperty("Pincode", etPincode.getText().toString());
            param.addProperty("State", etState.getText().toString());
            param.addProperty("City", etCity.getText().toString());
            LoggerUtil.logItem(param);
            Call<JsonObject> registrationCall = apiServicesLogin.getRegistration(bodyParam(param), PreferencesManager.getInstance(context).getANDROIDID());
            registrationCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                    hideLoading();
                    Log.e("REG", "as");
                    LoggerUtil.logItem(response.body());
                    LoggerUtil.logItem(response.code());
                    ResponseRegistration responseRegistration;
                    try {
                        if (response.isSuccessful()) {
                            String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent);
                            LoggerUtil.logItem(paramResponse);
                            Gson gson = new GsonBuilder().create();
                            responseRegistration = gson.fromJson(paramResponse, ResponseRegistration.class);
                            if (response.body() != null && responseRegistration.getResponse().equalsIgnoreCase("Success")) {
                                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                                showSuccess();

                            } else {
                                showAlert(responseRegistration.getMessage(), R.color.red, R.drawable.error);
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
                    LoggerUtil.logItem(t.getMessage());
                }
            });
        } catch (Error | Exception e) {
            e.printStackTrace();
        }
    }

    private void showSuccess() {
        Dialog error_dialog = new Dialog(this);
        error_dialog.setCanceledOnTouchOutside(false);
        error_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        error_dialog.setContentView(R.layout.error_dialoge);
        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
        error_dialog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        error_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView error_text = error_dialog.findViewById(R.id.error_text);
        Button ok_btn = error_dialog.findViewById(R.id.ok_btn);
        error_text.setText(String.format("Account created successfully.\nPlease Login using these credentials :\nID : %s\nPassword : %s", etMobNo.getText().toString().trim(), etPassword.getText().toString().trim()));

        error_dialog.show();
        ok_btn.setOnClickListener(view -> {
            error_dialog.dismiss();
            finish();
        });
    }

    @OnClick(R.id.side_menu)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.side_menu:
                finish();
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                break;
        }
    }

    private boolean Validation() {
        try {
            referral_mob_no_st = etInviteCode.getText().toString().trim();
            mobileno_st = etMobNo.getText().toString().trim();
            pswd_st = etPassword.getText().toString().trim();
            email_id_st = etEmail.getText().toString().trim();
            etName_st = etFirstName.getText().toString().trim();

            if (etName_st.length() == 0) {
                showError("Enter First Name", etFirstName);
                return false;
            } else if (etLastname.getText().toString().trim().length() == 0) {
                showError("Enter last name", etLastname);
                return false;
            } else if (mobileno_st.length() != 10) {
                showError(getResources().getString(R.string.valid_mob_no_err), etMobNo);
                return false;
            } else if (pswd_st.length() < 6) {
                showError(getResources().getString(R.string.enter_pswd), etPassword);
                return false;
            } else if (email_id_st.equalsIgnoreCase("")) {
                showError(getResources().getString(R.string.email_id_err), etEmail);
                return false;
            } else if (!Utils.isEmailAddress(email_id_st)) {
                showError(getResources().getString(R.string.email_id_err), etEmail);
                return false;
            } else if (etPincode.getText().toString().length() < 6) {
                showError("Enter valid pin code.", etMobNo);
                return false;
            } else if (etState.getText().toString().trim().length() == 0) {
                etState.setError("Please enter a valid state");
                etState.requestFocus();
                return false;
            } else if (etCity.getText().toString().trim().length() == 0) {
                etCity.setError("Please enter a valid city");
                etCity.requestFocus();
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


    private void sendCredentials(String mobileNo_st) {
        try {
            showLoading();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("Fk_memId", 0);
            jsonObject.addProperty("AndroidId", AppConfig.ANDROIDID);
            jsonObject.addProperty("DeviceId", "");
            jsonObject.addProperty("ProcId", PROC_FOUR);
            jsonObject.addProperty("Purpose", "mobile verification");
            jsonObject.addProperty("Data", etName_st.trim() + " " + etLastname.getText().toString().trim());
            jsonObject.addProperty("Mobile", mobileno_st);
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
                            ////Log.e("============ ", paramResponse);
                            Gson gson = new GsonBuilder().create();
                            responseRequestOTP = gson.fromJson(paramResponse, ResponseRequestOTP.class);
                            ////Log.e("============ ", String.valueOf(responseRequestOTP));
                            if (response.body() != null && responseRequestOTP.getResponse().equalsIgnoreCase("Success")) {
                                showAlert(responseRequestOTP.getMessage(), R.color.green, R.drawable.alerter_ic_notifications);
                                new Handler().postDelayed(() -> confirmOtp(mobileNo_st), DELAY_TIME);
                            } else {
                                showAlert(responseRequestOTP.getMessage(), R.color.red, R.drawable.error);
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

    private void sendCredentialsDetails(String mobileNo_st, String msg) {
        try {
//            showLoading();
//        {"fk_memId":24932, "androidId":"ecf0a2075cebaaeb", "ProcId":3,"msg":"some message", "mobile":"9026648094"}
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("Fk_memId", 0);
            jsonObject.addProperty("AndroidId", PreferencesManager.getInstance(context).getANDROIDID());
            jsonObject.addProperty("ProcId", PROC_THREE);
            jsonObject.addProperty("Msg", msg);
            jsonObject.addProperty("Mobile", mobileNo_st);
            LoggerUtil.logItem(jsonObject);

            Call<JsonObject> responseOtpReq = apiServicesLogin.getRequestotp(bodyParam(jsonObject), PreferencesManager.getInstance(context).getANDROIDID());

            responseOtpReq.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                    hideLoading();
                    LoggerUtil.logItem(response.body());
                    LoggerUtil.logItem(response.code());
                    ////Log.e("INSIDE==", "=====R======" + "sendCredentials");
                    ResponseRequestOTP responseRequestOTP;
                    try {
                        if (response.isSuccessful()) {
                            String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent);
                            LoggerUtil.logItem(paramResponse);
                            ////Log.e("============ ", paramResponse);
                            Gson gson = new GsonBuilder().create();
                            responseRequestOTP = gson.fromJson(paramResponse, ResponseRequestOTP.class);
                            ////Log.e("============ ", String.valueOf(responseRequestOTP));
                            if (response.body() != null && responseRequestOTP.getResponse().equalsIgnoreCase("Success")) {
                            } else {
                                showAlert(responseRequestOTP.getMessage(), R.color.red, R.drawable.error);
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


    private void confirmOtp(String mobileNo_st) {
        try {
            //Creating a LayoutInflater object for the dialog box
            LayoutInflater li = LayoutInflater.from(context);
            //Creating a view to get the dialog box
            View confirmDialog = li.inflate(R.layout.otp_lay, null);

            //Initizliaing confirm button fo dialog box and edittext of dialog box
            AppCompatButton buttonConfirm = confirmDialog.findViewById(R.id.buttonConfirm);
            AppCompatButton buttonCncel = confirmDialog.findViewById(R.id.buttonCncel);
            tv_resend_otp = confirmDialog.findViewById(R.id.tv_resend_otp);
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
                        sendCredentials(mobileNo_st);
                    } catch (Error | Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
                }
            });

            buttonCncel.setOnClickListener(v -> alertDialog.dismiss());

            buttonConfirm.setOnClickListener(view -> {
                confirmOtpAPI();
            });

            //Displaying the alert dialog
            alertDialog.show();
        } catch (Error | Exception e) {
            e.printStackTrace();
        }
    }

    private void confirmOtpAPI() {
        try {
            showLoading();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("Fk_memId", 0);
            jsonObject.addProperty("AndroidId", AppConfig.ANDROIDID);
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
                                showAlert("Please wait....", R.color.green, R.drawable.alerter_ic_notifications);
                                getRegistered();
                            } else {
                                showAlert(responseRequestOTP.getMessage(), R.color.red, R.drawable.error);
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
}
