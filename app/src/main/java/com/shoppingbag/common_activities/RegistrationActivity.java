package com.shoppingbag.common_activities;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.shoppingbag.BuildConfig;
import com.shoppingbag.R;
import com.shoppingbag.app.AppConfig;
import com.shoppingbag.app.PreferencesManager;
import com.shoppingbag.constants.BaseActivity;
import com.shoppingbag.constants.Cons;
import com.shoppingbag.model.response.ResponseLogin;
import com.shoppingbag.model.response.ResponseReferalName;
import com.shoppingbag.model.response.ResponseRegistration;
import com.shoppingbag.model.response.otp_req.ResponseRequestOTP;
import com.shoppingbag.model.response.pincode.ResponsePincode;
import com.shoppingbag.model.response.referal.ResponseReferalCode;
import com.shoppingbag.utils.LoggerUtil;
import com.shoppingbag.utils.NetworkUtils;
import com.shoppingbag.utils.Utils;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.shoppingbag.app.AppConfig.PAYLOAD_BUNDLE;
import static com.shoppingbag.app.AppConfig.PROC_FOUR;
import static com.shoppingbag.app.AppConfig.PROC_THREE;
import static com.shoppingbag.app.AppConfig.PROC_TWO;

public class RegistrationActivity extends BaseActivity {

    @BindView(R.id.side_menu)
    ImageView sideMenu;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.et_invite_code)
    TextInputEditText et_invite_code;
    @BindView(R.id.et_mob_no)
    TextInputEditText etMobNo;
    @BindView(R.id.input_layout_mob_no)
    TextInputLayout inputLayoutMobNo;
    @BindView(R.id.et_password)
    TextInputEditText etPassword;
    @BindView(R.id.input_layout_password)
    TextInputLayout inputLayoutPassword;
    @BindView(R.id.et_email)
    TextInputEditText etEmail;
    @BindView(R.id.input_layout_email)
    TextInputLayout inputLayoutEmail;
    @BindView(R.id.signup_btn)
    Button signupBtn;
    @BindView(R.id.sign_up)
    TextView signUp;
    @BindView(R.id.login_now)
    TextView loginNow;
    @BindView(R.id.et_first_name)
    TextInputEditText etName;
    @BindView(R.id.input_name_lo)
    TextInputLayout inputNameLo;
    @BindView(R.id.tv_ref_name)
    TextView tv_ref_name;
    @BindView(R.id.input_pin_lo)
    TextInputLayout inputPinLo;
    @BindView(R.id.et_pincode)
    TextInputEditText etPincode;
    @BindView(R.id.et_lastname)
    TextInputEditText etLastname;
    @BindView(R.id.et_state)
    TextInputEditText etState;
    @BindView(R.id.input_state_lo)
    TextInputLayout inputStateLo;
    @BindView(R.id.et_city)
    TextInputEditText etCity;
    @BindView(R.id.input_city_lo)
    TextInputLayout inputCityLo;
    @BindView(R.id.pincodeProgress)
    ProgressBar pincodeProgress;
    @BindView(R.id.chkbx_terms)
    CheckBox chkbx_terms;
    boolean already_email = false, already_mobile = false;
    @BindView(R.id.textViewNumber)
    TextView textViewNumber;
    @BindView(R.id.edtone)
    EditText edtone;
    @BindView(R.id.edtwo)
    EditText edtwo;
    @BindView(R.id.edthree)
    EditText edthree;
    @BindView(R.id.edtfour)
    EditText edtfour;
    @BindView(R.id.edtfive)
    EditText edtfive;
    @BindView(R.id.edtSix)
    EditText edtSix;
    @BindView(R.id.tv_resend_otp)
    TextView tvResendOtp;
    @BindView(R.id.txt_time)
    TextView txtTime;
    private String etName_st = "", referral_mob_no_st = "", mobileno_st = "", pswd_st = "", email_id_st = "";
    private String from;
//    private EditText editTextConfirmOtp;
//    private int DELAY_TIME = 2000;
//    private AlertDialog alertDialog;
    private Bundle bundleN;
    String mobile = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);
        title.setText(getResources().getString(R.string.kkm_registration));
        bundleN = new Bundle();
        clearPrefrenceforTokenExpiry();

        if (getIntent().getBundleExtra(PAYLOAD_BUNDLE) != null) {
            from = getIntent().getBundleExtra(PAYLOAD_BUNDLE).getString("from");
            mobile = getIntent().getBundleExtra(PAYLOAD_BUNDLE).getString("mobile");
            etMobNo.setText(mobile);
            textViewNumber.setText(String.format("OTP sent to ********%s", mobile.substring(8)));
            sendCredentials(mobile);
            LoggerUtil.logItem(from);
        }
        tvResendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendCredentials(mobile);
            }
        });


        et_invite_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() >= 9) {
                    if (NetworkUtils.getConnectivityStatus(context) != 0)
                        getReferralNameCode();
                    else
                        showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
                } else {
                    tv_ref_name.setText("");
                }
            }
        });


//        etMobNo.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if (s.length() != 0 && s.length() == 10) {
//                    hideKeyboard();
//                    getMobileValid();
//                }
//            }
//        });
//
//        etEmail.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if (s.length() != 0) {
//                    if (Utils.isEmailAddress(etEmail.getText().toString())) {
//                        getEmailValid();
//                    }
//                }
//            }
//        });

        etPincode.addTextChangedListener(new TextWatcher() {
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

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
            }
        });

        FirebaseDynamicLinks.getInstance()
                .getDynamicLink(getIntent())
                .addOnSuccessListener(this, pendingDynamicLinkData -> {
                    // Get deep link from result (may be null if no link is found)
                    Uri deepLink = null;
                    if (pendingDynamicLinkData != null) {
                        deepLink = pendingDynamicLinkData.getLink();
                    }
                    //
                    // If the user isn't signed in and the pending Dynamic Link is
                    // an invitation, sign in the user anonymously, and record the
                    // referrer's UID.
                    //
//                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (deepLink != null
                            && deepLink.getBooleanQueryParameter("invitedby", false)) {
                        String referrerUid = deepLink.getQueryParameter("invitedby");
                        et_invite_code.setText(referrerUid);
                        et_invite_code.setClickable(false);
                        et_invite_code.setFocusable(false);
                        et_invite_code.setFocusableInTouchMode(false);
                        et_invite_code.setCursorVisible(false);

                    } else {
                        et_invite_code.setText("SBK000123");
                        et_invite_code.setSelection(et_invite_code.getText().length());
                    }
                });

        chkbx_terms.setOnClickListener(v -> {
            //is chkIos checked?
            if (((CheckBox) v).isChecked()) {
                signupBtn.setVisibility(View.VISIBLE);
            } else
                signupBtn.setVisibility(View.GONE);

        });
        getChanged();
    }

    private CountDownTimer countDownTimer;

    private void startTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        tvResendOtp.setVisibility(View.GONE);
        countDownTimer = new CountDownTimer(60000, 1000) { // adjust the milli seconds here
            public void onTick(long millisUntilFinished) {

                long data = (TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));
                txtTime.setText("00:" + data);
            }

            public void onFinish() {
                txtTime.setText("00:00");
                tvResendOtp.setVisibility(View.VISIBLE);
                txtTime.setVisibility(View.GONE);
            }
        }.start();
    }

    private void getChanged() {
        edtone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (edtone.getText().toString().isEmpty()) {


                } else {
                    edtwo.requestFocus();

                }

            }
        });

        edtwo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (edtwo.getText().toString().isEmpty()) {
                    edtone.requestFocus();

                } else {
                    edthree.requestFocus();

                }

            }
        });

        edthree.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (edthree.getText().toString().isEmpty()) {
                    edtwo.requestFocus();

                } else {
                    edtfour.requestFocus();

                }

            }
        });

        edtfour.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (edtfour.getText().toString().isEmpty()) {
                    edthree.requestFocus();

                } else {
                    edtfive.requestFocus();
                }

            }
        });

        edtfive.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (edtfive.getText().toString().isEmpty()) {
                    edtfour.requestFocus();

                } else {
                    edtSix.requestFocus();


                }

            }
        });

        edtSix.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (edtSix.getText().toString().isEmpty()) {
                    edtfive.requestFocus();

                } else {
                    hideSoftKeyboard(context);

                }

            }
        });

    }


    @OnClick({R.id.side_menu, R.id.signup_btn, R.id.login_now, R.id.tv_terms, R.id.tv_privacy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.side_menu:
                if (from.equalsIgnoreCase("mobile")) {
                    goToActivityWithFinish(context, Permission.class, null);
                } else {
                    Intent intent3 = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent3);
                    overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                    finish();
                }
                break;
            case R.id.signup_btn:
                if (Validation()) {
                    try {
                        if (NetworkUtils.getConnectivityStatus(context) != 0) {
                            confirmOtpAPI(edtone.getText().toString().trim() + edtwo.getText().toString().trim() + edthree.getText().toString().trim() + edtfour.getText().toString().trim()
                                    + edtfive.getText().toString().trim() + edtSix.getText().toString().trim());
                        } else {
                            showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
                        }
                    } catch (Error | Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.login_now:
                Intent intent2 = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent2);
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                finish();
                break;

            case R.id.tv_terms:
                bundleN.putString("link", BuildConfig.TERM_CONDITION);
                goToActivity(context, NormalWebViewActivity.class, bundleN);
//                showMessage("Under Development");
                break;
            case R.id.tv_privacy:
                bundleN.putString("link", BuildConfig.PRIVACY_POLICY);
                goToActivity(context, NormalWebViewActivity.class, bundleN);
                break;
        }
    }

    private boolean Validation() {
        try {
            referral_mob_no_st = et_invite_code.getText().toString().trim();
            mobileno_st = etMobNo.getText().toString().trim();
            pswd_st = etPassword.getText().toString().trim();
            email_id_st = etEmail.getText().toString().trim();
            etName_st = etName.getText().toString().trim();

            if (edtone.getText().toString().isEmpty()) {
                edtone.setError("Please Enter OTP");
                edtone.requestFocus();
                return false;
            } else if (edtwo.getText().toString().isEmpty()) {
                edtwo.setError("Please Enter OTP");
                edtwo.requestFocus();
                return false;
            } else if (edthree.getText().toString().isEmpty()) {
                edthree.setError("Please Enter OTP");
                edthree.requestFocus();
                return false;
            } else if (edtfour.getText().toString().isEmpty()) {
                edtfour.setError("Please Enter OTP");
                edtfour.requestFocus();
                return false;
            } else if (edtfive.getText().toString().isEmpty()) {
                edtfive.setError("Please Enter OTP");
                edtfive.requestFocus();
                return false;
            } else if (edtSix.getText().toString().isEmpty()) {
                edtSix.setError("Please Enter OTP");
                edtSix.requestFocus();
                return false;
            } else if (etName_st.length() == 0) {
                showError("Enter First Name", etName);
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

    private void getRegistration() {
        try {
            showLoading();
            JsonObject param = new JsonObject();
            param.addProperty("MobileNo", mobileno_st);

            if (tv_ref_name.getText().toString().trim().equalsIgnoreCase("")) {
                param.addProperty("InvitedBy", "DREAMYDROSHKY");
            } else {
                param.addProperty("InvitedBy", et_invite_code.getText().toString().trim());
            }

            param.addProperty("Password", pswd_st);
            param.addProperty("Email", email_id_st);
            param.addProperty("CreatedBy", "100");
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
                                String msg = "Dear " + etName_st +
                                        " you are registered with Shopping Bag, we will be sending you your login credentials on your registered email id.";
                                sendCredentialsDetails(mobileno_st, msg);
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
//                hideLoading();
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
            getLogin(etMobNo.getText().toString().trim(), etPassword.getText().toString().trim());
        });
    }

    private void getLogin(String id, String pswd) {
        try {
            showLoading();
            JsonObject param = new JsonObject();
            param.addProperty("LoginID", id);
            param.addProperty("Password", pswd);
            param.addProperty("DeviceId", pref.getString("regId", ""));
            param.addProperty("AndroidId", AppConfig.ANDROIDID);
            param.addProperty("DeviceType", "A");
            param.addProperty("Update", "Yes");

            Call<JsonObject> loginCall = apiServicesLogin.getLogin(bodyParam(param), PreferencesManager.getInstance(context).getANDROIDID());
            LoggerUtil.logItem(param);
            loginCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                    hideLoading();
                    LoggerUtil.logItem(response.body());
                    LoggerUtil.logItem(response.code());
                    ResponseLogin responseLogin;
                    try {
                        if (response.isSuccessful()) {
                            String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent);
                            LoggerUtil.logItem(paramResponse);
                            ////Log.e("============ ", paramResponse);
                            Gson gson = new GsonBuilder().create();
                            responseLogin = gson.fromJson(paramResponse, ResponseLogin.class);

                            ////Log.e("============ ", String.valueOf(responseLogin));
                            if (response.body() != null && responseLogin.getResponse().equalsIgnoreCase("Success")) {
                                allowLogin(responseLogin, id, pswd);
                            } else {
                                clearPrefrenceforTokenExpiry();
                                Intent intent1 = new Intent(context, LoginActivity.class);
                                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent1);
                                finish();
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

    private void allowLogin(ResponseLogin response, String mobileNo_st, String password_st) {
        try {
            AppConfig.authToken = response.getResult().getToken();
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
            PreferencesManager.getInstance(context).setStatus(response.getResult().getStatus());
            PreferencesManager.getInstance(context).setSavedLOGINID(Cons.encryptMsg(mobileNo_st, cross_intent));
            PreferencesManager.getInstance(context).setSavedPASSWORD(Cons.encryptMsg(password_st, cross_intent));
            PreferencesManager.getInstance(context).setBankAccount(response.getResult().getBankaccount());
            PreferencesManager.getInstance(context).setBankIfsc(response.getResult().getBenIFSC());
            PreferencesManager.getInstance(context).setBankName(response.getResult().getBankname());
            goToActivityWithFinish(MainContainer.class, null);

        } catch (Error | Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        if (from.equalsIgnoreCase("mobile")) {
            goToActivityWithFinish(context, Permission.class, null);
        } else if (from.equalsIgnoreCase("login")) {
            Intent intent3 = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent3);
            overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
            finish();
        } else {
            super.onBackPressed();
        }
    }

//    private void confirmOtp(String mobileNo_st, String fk_memId) {
//        try {
//            //Creating a LayoutInflater object for the dialog box
//            LayoutInflater li = LayoutInflater.from(context);
//            //Creating a view to get the dialog box
//            View confirmDialog = li.inflate(R.layout.otp_lay, null);
//
//            //Initizliaing confirm button fo dialog box and edittext of dialog box
//            AppCompatButton buttonConfirm = confirmDialog.findViewById(R.id.buttonConfirm);
//            AppCompatButton buttonCncel = confirmDialog.findViewById(R.id.buttonCncel);
//            tv_resend_otp = confirmDialog.findViewById(R.id.tv_resend_otp);
//            editTextConfirmOtp = confirmDialog.findViewById(R.id.editTextOtp);
//
//
//            //Creating an alertdialog builder
//            AlertDialog.Builder alert = new AlertDialog.Builder(context);
//
//            //Adding our dialog box to the view of alert dialog
//            alert.setView(confirmDialog);
//
//            //Creating an alert dialog
//            alertDialog = alert.create();
//            alertDialog.setCancelable(false);
//            alertDialog.setCanceledOnTouchOutside(false);
//
//            tv_resend_otp.setOnClickListener(v -> {
//                if (NetworkUtils.getConnectivityStatus(context) != 0) {
//                    try {
//                        alertDialog.dismiss();
//                        sendCredentials(mobileNo_st, fk_memId);
//                    } catch (Error | Exception e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
//                }
//            });
//
//            buttonCncel.setOnClickListener(v -> alertDialog.dismiss());
//
//            buttonConfirm.setOnClickListener(view -> {
//                confirmOtpAPI(fk_memId);
//            });
//
//            //Displaying the alert dialog
//            alertDialog.show();
//        } catch (Error | Exception e) {
//            e.printStackTrace();
//        }
//    }

    private void confirmOtpAPI(String otp) {
        try {
            showLoading();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("Fk_memId", 0);
            jsonObject.addProperty("AndroidId", AppConfig.ANDROIDID);
            jsonObject.addProperty("ProcId", PROC_TWO);
            jsonObject.addProperty("Otpno", otp);
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
//                                alertDialog.dismiss();
                                showAlert("Please wait....", R.color.green, R.drawable.alerter_ic_notifications);
                                getRegistration();
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

    private void sendCredentials(String mobileNo_st) {
        try {
            tvResendOtp.setVisibility(View.VISIBLE);
            edtone.setText("");
            edtwo.setText("");
            edthree.setText("");
            edtfour.setText("");
            edtfive.setText("");
            edtSix.setText("");

            showLoading();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("Fk_memId", 0);
            jsonObject.addProperty("AndroidId", AppConfig.ANDROIDID);
            jsonObject.addProperty("DeviceId", "");
            jsonObject.addProperty("ProcId", PROC_FOUR);
            jsonObject.addProperty("Purpose", "mobile verification");
            jsonObject.addProperty("Data", "User");
            jsonObject.addProperty("Mobile", mobileNo_st);
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
//                                new Handler().postDelayed(() -> confirmOtp(mobileNo_st), DELAY_TIME);
//                                startTimer();

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

    void getReferralNameCode() {
        try {
            JsonObject object = new JsonObject();
            object.addProperty("InviteCode", et_invite_code.getText().toString().trim());

            Call<JsonObject> call = apiServicesLogin.getReferalNameFronCode(bodyParam(object), PreferencesManager.getInstance(context).getANDROIDID());
            LoggerUtil.logItem(object);
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                    {
                        hideLoading();
                        LoggerUtil.logItem(response.body());
                        LoggerUtil.logItem(response.code());
                        ResponseReferalCode responseReferalCode;
                        try {
                            if (response.isSuccessful()) {
                                String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent);
                                LoggerUtil.logItem(paramResponse);
                                ////Log.e("============ ", paramResponse);
                                Gson gson = new GsonBuilder().create();
                                responseReferalCode = gson.fromJson(paramResponse, ResponseReferalCode.class);

                                ////Log.e("============ ", String.valueOf(responseReferalCode));
                                if (response.body() != null && responseReferalCode.getResponse().equalsIgnoreCase("Success")) {
                                    tv_ref_name.setText(responseReferalCode.getReferalName());
                                } else {
                                    et_invite_code.setError("Invalid invite code.");
                                    tv_ref_name.setText("");
                                }
                            } else {
                                et_invite_code.setError("Invalid invite code.");
                                tv_ref_name.setText("");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
//                hideLoading();
                }
            });
        } catch (Error | Exception e) {
            e.printStackTrace();
        }

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

    void getMobileValid() {
        try {
            JsonObject object = new JsonObject();
            object.addProperty("RefMobile", etMobNo.getText().toString().trim());
            object.addProperty("email", "");

            Call<JsonObject> call = apiServicesLogin.getReferalName(bodyParam(object), PreferencesManager.getInstance(context).getANDROIDID());
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                    hideLoading();
                    LoggerUtil.logItem(response.body());
                    LoggerUtil.logItem(response.code());
                    ResponseReferalName responseReferalName;
                    try {
                        if (response.isSuccessful()) {
                            String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent);
                            LoggerUtil.logItem(paramResponse);
                            Gson gson = new GsonBuilder().create();
                            responseReferalName = gson.fromJson(paramResponse, ResponseReferalName.class);
                            if (response.body() != null && responseReferalName.getResponse().equalsIgnoreCase("Success")) {
                                etMobNo.setError("Mobile no already exist");
                                already_mobile = true;
                            } else {
                                already_mobile = false;
                            }
                        } else {
                            clearPrefrenceforTokenExpiry();
                            getAndroidId();
                            goToActivityWithFinish(LoginActivity.class, null);
                        }
                    } catch (Exception | Error e) {
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

    void getEmailValid() {
        try {
            JsonObject object = new JsonObject();
            object.addProperty("RefMobile", "");
            object.addProperty("Email", etEmail.getText().toString().trim());

            Call<JsonObject> call = apiServicesLogin.getReferalName(bodyParam(object), PreferencesManager.getInstance(context).getANDROIDID());
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                    hideLoading();
                    LoggerUtil.logItem(response.body());
                    LoggerUtil.logItem(response.message());
                    LoggerUtil.logItem(response.code());
                    LoggerUtil.logItem(call.request().url());
                    ResponseReferalName responseReferalName;
                    try {
                        String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent);
                        LoggerUtil.logItem(paramResponse);
                        ////Log.e("============ ", paramResponse);
                        Gson gson = new GsonBuilder().create();
                        responseReferalName = gson.fromJson(paramResponse, ResponseReferalName.class);
                        ////Log.e("============ ", String.valueOf(responseReferalName));
                        if (response.body() != null && responseReferalName.getResponse().equalsIgnoreCase("Success")) {
                            etEmail.setError("Email already exist");
                            already_email = true;
                        } else {
                            already_email = false;
                        }
                    } catch (Exception | Error e) {
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
