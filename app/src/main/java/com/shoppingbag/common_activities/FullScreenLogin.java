package com.shoppingbag.common_activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.shoppingbag.BuildConfig;
import com.shoppingbag.R;
import com.shoppingbag.app.AppConfig;
import com.shoppingbag.app.PreferencesManager;
import com.shoppingbag.constants.Cons;
import com.shoppingbag.model.response.ResponseForgotPass;
import com.shoppingbag.model.response.ResponseLogin;
import com.shoppingbag.model.response.otp_req.ResponseRequestOTP;
import com.shoppingbag.retrofit.ApiServices;
import com.shoppingbag.retrofit.Dialog_dismiss;
import com.shoppingbag.retrofit.ServiceGenerator;
import com.shoppingbag.utils.DialogUtil;
import com.shoppingbag.utils.LoggerUtil;
import com.shoppingbag.utils.NetworkUtils;
import com.shoppingbag.utils.Utils;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.shoppingbag.app.AppConfig.PAYLOAD_BUNDLE;
import static com.shoppingbag.app.AppConfig.PROC_ONE;
import static com.shoppingbag.app.AppConfig.PROC_TWO;

public class FullScreenLogin extends DialogFragment {

    public static String TAG = "FullScreenLoginDialog";
    public Dialog_dismiss dialog_dismiss;
    public SecretKey cross_intent;
    @BindView(R.id.et_reg_mob_no)
    TextInputEditText etRegMobNo;
    @BindView(R.id.et_password)
    TextInputEditText etPassword;
    @BindView(R.id.register)
    TextView register;
    Unbinder unbinder;
    @BindView(R.id.input_layout_password)
    TextInputLayout inputLayoutPassword;
    private Dialog resetPasswordDialog;
    private SharedPreferences pref;
    private ApiServices apiServicesLogin;
    private String mobileNo_st = "", password_st = "";
    private ProgressDialog mProgressDialog;
    private EditText editTextConfirmOtp;
    private int DELAY_TIME = 2000;
    private AlertDialog alertDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle);
            cross_intent = new SecretKeySpec(BuildConfig.CASHBAG_COMPARED.getBytes(), "AES");
            apiServicesLogin = ServiceGenerator.createService(ApiServices.class, BuildConfig.LOGIN_URL);
            pref = getActivity().getSharedPreferences(AppConfig.SHARED_PREF, 0);
        } catch (Error | Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the EditNameDialogListener so we can send events to the host
            dialog_dismiss = ((Dialog_dismiss) getActivity());
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString() + " must implement DialogDismiss");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            LoggerUtil.logItem("Dialog");
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_login, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        cardView.setCardBackgroundColor(Color.WHITE);

        String account = getColoredSpanned(getResources().getString(R.string.new_to_kkm), "#000000");
        String signup = getColoredSpanned("<b>" + getResources().getString(R.string.register_now) + "</b>", "#0281d5");
        register.setText(Html.fromHtml(account + " " + signup));
    }

    public void showAlert(String msg, int color, int icon) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    private String getColoredSpanned(String text, String color) {
        return "<font color=" + color + ">" + text + "</font>";
    }

    public void goToActivityWithFinish(Activity activity, Class<?> classActivity, Bundle bundle) {
        Intent intent = new Intent(getActivity(), classActivity);
        if (bundle != null) intent.putExtra(PAYLOAD_BUNDLE, bundle);
        Utils.hideSoftKeyboard(activity);
        activity.startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.login_btn, R.id.forgot_password, R.id.register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_btn:
                if (Validation()) {
                    if (NetworkUtils.getConnectivityStatus(getActivity()) != 0) {
                        getLogin();
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
//                bundle.putString("from", "fullscreen");
//                bundle.putString("mobile", "");
//                goToActivityWithFinish(getActivity(), RegistrationActivity.class, bundle);
                dismiss();
                break;

//            case R.id.tv_terms:
//                Bundle bundleN = new Bundle();
//                bundleN.putString("from", "Main");
//                bundleN.putString("link", BuildConfig.TERM_CONDITION);
//                goToActivity(getActivity(), WebViewActivity.class, bundleN);
//                break;
        }
    }

    public void goToActivity(Activity activity, Class<?> classActivity, Bundle bundle) {
        Utils.hideSoftKeyboard(activity);
        Intent intent = new Intent(activity, classActivity);
        if (bundle != null) intent.putExtra(PAYLOAD_BUNDLE, bundle);
        activity.startActivity(intent);
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
        Dialog error_dialog = new Dialog(getActivity());
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
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    public void showLoading() {
        mProgressDialog = DialogUtil.showLoadingDialog(getActivity(), "Base Activity");
    }

    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    private JsonObject bodyParam(JsonObject param) {
        JsonObject body = new JsonObject();
        try {
            body.addProperty("body", Cons.encryptMsg(param.toString(), cross_intent));
            LoggerUtil.logItem(body);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return body;
    }

    private void getLogin() {
        showLoading();
        JsonObject param = new JsonObject();
        param.addProperty("LoginID", mobileNo_st);
        param.addProperty("Password", password_st);
        param.addProperty("DeviceId", pref.getString("regId", ""));
        param.addProperty("AndroidId", AppConfig.ANDROIDID);
        param.addProperty("DeviceType", "A");
        param.addProperty("Update", "Yes");

        LoggerUtil.logItem(param);
        Call<JsonObject> loginCall = apiServicesLogin.getLogin(bodyParam(param), PreferencesManager.getInstance(getActivity()).getANDROIDID());
        loginCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                hideLoading();
                LoggerUtil.logItem(response.body());
                LoggerUtil.logItem(response.code());
                try {
                    if (response.isSuccessful()) {
                        ResponseLogin responseLogin;
                        String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent);
                        LoggerUtil.logItem(paramResponse);
                        Gson gson = new GsonBuilder().create();
                        responseLogin = gson.fromJson(paramResponse, ResponseLogin.class);
                        if (response.body() != null && responseLogin.getResponse().equalsIgnoreCase("Success")) {
                            if (responseLogin.getResult().getMessage().equalsIgnoreCase("Device Id not Match")) {
//                                new Handler().postDelayed(() -> confirmOtp(mobileNo_st, String.valueOf(responseLogin.getResult().getMemberId()), "login"), DELAY_TIME);
                                allowLogin(responseLogin);
                            } else if (responseLogin.getResult().getMessage().equalsIgnoreCase("Device Id Match")) {
                                allowLogin(responseLogin);
                            }
                        } else {
                            if (response.body() == null) {
                                goToActivityWithFinish(getActivity(), MaintenancePage.class, null);
                            } else if (responseLogin.getMessage().contains("website is under maintenance")) {
                                goToActivityWithFinish(getActivity(), MaintenancePage.class, null);
                            } else if (responseLogin.getMessage().contains("stop")) {
                                Bundle b = new Bundle();
                                b.putString("msg", responseLogin.getMessage());
                                goToActivityWithFinish(getActivity(), MaintenancePage.class, b);
                            } else if (responseLogin.getMessage().contains("not Valid")) {
                                showError("Mobile Number/Email or password is not valid.", etPassword);
                            }
                        }
                    } else {
                        dialog_dismiss.onDismiss();
                        dismiss();
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
    }

    private void openForgotPasswordDialog() {
        //Creating a LayoutInflater object for the dialog box
        LayoutInflater li = LayoutInflater.from(getActivity());
        //Creating a view to get the dialog box
        View confirmDialog = li.inflate(R.layout.forgot_pass_dialog, null);

        //Initizliaing confirm button fo dialog box and edittext of dialog box
        AppCompatButton button_getpassword = confirmDialog.findViewById(R.id.button_getpassword);
        EditText mobile_number = confirmDialog.findViewById(R.id.mobile_number);
        EditText user_id = confirmDialog.findViewById(R.id.user_id);

        //Creating an alertdialog builder
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

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
                } else*/
                {
                    if (mobileNo_st.length() == 0 || mobileNo_st.length() != 10) {
                        mobileNo_st = "";
                        showError(getResources().getString(R.string.valid_mob_no_err), mobile_number);
                    } else {
                        if (NetworkUtils.getConnectivityStatus(getActivity()) != 0) {
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
        showLoading();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("Mobile", mobileNo_st);
        LoggerUtil.logItem(jsonObject);
        Call<JsonObject> responseForgotPassCall = apiServicesLogin.getForgotPassword(bodyParam(jsonObject), PreferencesManager.getInstance(getActivity()).getANDROIDID());
        responseForgotPassCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                hideLoading();
                LoggerUtil.logItem(response.body());
                LoggerUtil.logItem(response.code());
                try {
                    if (response.isSuccessful()) {
                        ResponseForgotPass responseForgot_pass;
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
                        dialog_dismiss.onDismiss();
                        dismiss();
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
    }

    private void sendCredentials(String mobileNo_st, String fk_memId, String from) {
        showLoading();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("Fk_memId", Integer.parseInt(fk_memId));
        jsonObject.addProperty("AndroidId", PreferencesManager.getInstance(getActivity()).getANDROIDID());
        jsonObject.addProperty("ProcId", PROC_ONE);
        jsonObject.addProperty("Purpose", "login");
        LoggerUtil.logItem(jsonObject);

        Call<JsonObject> responseOtpReq = apiServicesLogin.getRequestotp(bodyParam(jsonObject), PreferencesManager.getInstance(getActivity()).getANDROIDID());
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
                            new Handler().postDelayed(() -> confirmOtp(mobileNo_st, fk_memId, from), DELAY_TIME);
                        } else {
                            showAlert(responseRequestOTP.getMessage(), R.color.red, R.drawable.error);
                        }
                    } else {
                        dialog_dismiss.onDismiss();
                        dismiss();
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
    }

    private void confirmOtp(String mobileNo_st, String fk_memId, String from) {
        try {
            //Creating a LayoutInflater object for the dialog box
            LayoutInflater li = LayoutInflater.from(getActivity());
            //Creating a view to get the dialog box
            View confirmDialog = li.inflate(R.layout.otp_lay, null);

            //Initizliaing confirm button fo dialog box and edittext of dialog box
            AppCompatButton buttonConfirm = confirmDialog.findViewById(R.id.buttonConfirm);
            AppCompatButton buttonCncel = confirmDialog.findViewById(R.id.buttonCncel);
            TextView tv_resend_otp = confirmDialog.findViewById(R.id.tv_resend_otp);
            editTextConfirmOtp = confirmDialog.findViewById(R.id.editTextOtp);


            //Creating an alertdialog builder
            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

            //Adding our dialog box to the view of alert dialog
            alert.setView(confirmDialog);

            //Creating an alert dialog
            alertDialog = alert.create();
            alertDialog.setCancelable(false);
            alertDialog.setCanceledOnTouchOutside(false);

            tv_resend_otp.setOnClickListener(v -> {
                if (NetworkUtils.getConnectivityStatus(getActivity()) != 0) {
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
        showLoading();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("Fk_memId", Integer.parseInt(fk_memId));
        jsonObject.addProperty("AndroidId", PreferencesManager.getInstance(getActivity()).getANDROIDID());
        jsonObject.addProperty("ProcId", PROC_TWO);
        jsonObject.addProperty("Otpno", editTextConfirmOtp.getText().toString().trim());
        jsonObject.addProperty("Data", pref.getString("regId", ""));
        LoggerUtil.logItem(jsonObject);
        Call<JsonObject> responseOtpReq = apiServicesLogin.getRequestotp(bodyParam(jsonObject), PreferencesManager.getInstance(getActivity()).getANDROIDID());
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
//                            showAlert(responseRequestOTP.getMessage(), R.color.green, R.drawable.alerter_ic_notifications);
                            if (from.equalsIgnoreCase("login")) {
                                if (NetworkUtils.getConnectivityStatus(getActivity()) != 0) {
                                    getLogin();
                                } else {
                                    showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
                                }
                            } else
                                openResetPasswordDialog(mobileNo_st);
                        } else {
                            showAlert(responseRequestOTP.getMessage(), R.color.red, R.drawable.error);
                        }
                    } else {
                        dialog_dismiss.onDismiss();
                        dismiss();
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
    }

    private void openResetPasswordDialog(String mobileNo_st) {
        try {
            resetPasswordDialog = new Dialog(getActivity());
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
                if (ed_Password.getText().toString().trim().length() != 0 && ed_Password.getText().toString().trim().length() >= 6) {
                    if (ed_Password.getText().toString().trim().equals(ed_Confirm_Password.getText().toString().trim())) {
                        getChangedPassword(mobileNo_st, ed_Confirm_Password.getText().toString().trim());
                        resetPasswordDialog.dismiss();
                    } else {
                        Toast.makeText(getActivity(), "New Password & Confirmed Password not matched.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Please input password greater then 6 characters.", Toast.LENGTH_SHORT).show();
                }
            });
            resetPasswordDialog.show();

        } catch (Exception e) {
            hideLoading();
        }

    }

    private void getChangedPassword(String mobileNo_st, String newpass) {
        JsonObject param = new JsonObject();
        param.addProperty("Mobile", mobileNo_st);
        param.addProperty("OldPassword", "");
        param.addProperty("NewPassword", newpass);
        param.addProperty("Formname", "Forgot Password");
        param.addProperty("UpdatedBy", "");
        LoggerUtil.logItem(param);
        showLoading();
        Call<JsonObject> call = apiServicesLogin.ChangePassword(bodyParam(param), PreferencesManager.getInstance(getActivity()).getANDROIDID());
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
                            Toast.makeText(getActivity(), "Updated Successfully, Kindly, login to continue.", Toast.LENGTH_SHORT).show();

                            int WAIT_TIME = 2000;
                            new Handler().postDelayed(() -> {
                                PreferencesManager.getInstance(getActivity()).clear();

                                Intent intent1 = new Intent(getActivity(), LoginActivity.class);
                                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                getActivity().startActivity(intent1);
                                getActivity().finish();
                            }, WAIT_TIME);
                        } else {
                            Toast.makeText(getActivity(), convertedObject.get("response").getAsString(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        dialog_dismiss.onDismiss();
                        dismiss();
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
    }

    private void allowLogin(ResponseLogin response) {
        hideLoading();
        LoggerUtil.logItem(response);
        if (response != null && response.getResponse().equalsIgnoreCase("Success")) {
            try {
                AppConfig.authToken = response.getResult().getToken();
                PreferencesManager.getInstance(getActivity()).setUSERID(Cons.encryptMsg(String.valueOf(response.getResult().getMemberId()), cross_intent));
                PreferencesManager.getInstance(getActivity()).setLoginID(Cons.encryptMsg(response.getResult().getLoginId(), cross_intent));
                PreferencesManager.getInstance(getActivity()).setMOBILE(Cons.encryptMsg(response.getResult().getMobile(), cross_intent));
                PreferencesManager.getInstance(getActivity()).setNAME(Cons.encryptMsg(response.getResult().getName(), cross_intent));
            PreferencesManager.getInstance(getActivity()).setLNAME(Cons.encryptMsg(response.getResult().getLastName(), cross_intent));
//            PreferencesManager.getInstance(getActivity()).setTransactionPass(Cons.encryptMsg(response.getResult().getEwalletPassword(), cross_intent));
                PreferencesManager.getInstance(getActivity()).setPINCODE(Cons.encryptMsg(response.getResult().getPincode(), cross_intent));

                PreferencesManager.getInstance(getActivity()).setState(Cons.encryptMsg(response.getResult().getState(), cross_intent));
                PreferencesManager.getInstance(getActivity()).setCity(Cons.encryptMsg(response.getResult().getCity(), cross_intent));
//            PreferencesManager.getInstance(getActivity()).setAddress(Cons.encryptMsg(response.getResult().getAddress(), cross_intent));

//            PreferencesManager.getInstance(getActivity()).setEMAIL(Cons.encryptMsg(response.getResult().getEmailID(), cross_intent));
                PreferencesManager.getInstance(getActivity()).setLastLogin(Cons.encryptMsg(response.getResult().getLastLogin(), cross_intent));
                PreferencesManager.getInstance(getActivity()).setPROFILEPIC(response.getResult().getProfilePic());
//            PreferencesManager.getInstance(getActivity()).setDOB(Cons.encryptMsg(response.getResult().getDob(), cross_intent));
                PreferencesManager.getInstance(getActivity()).setSavedLOGINID(Cons.encryptMsg(mobileNo_st, cross_intent));
                PreferencesManager.getInstance(getActivity()).setStatus(response.getResult().getStatus());
                PreferencesManager.getInstance(getActivity()).setSavedPASSWORD(Cons.encryptMsg(password_st, cross_intent));


            } catch (Exception | Error E) {
                E.printStackTrace();
            }
            dialog_dismiss.onDismiss();
            dismiss();
        } else {
            showAlert(response.getMessage(), R.color.red, R.drawable.error);
        }
    }
}
