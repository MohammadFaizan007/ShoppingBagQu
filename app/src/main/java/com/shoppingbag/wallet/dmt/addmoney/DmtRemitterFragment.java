package com.shoppingbag.wallet.dmt.addmoney;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.shoppingbag.BuildConfig;
import com.shoppingbag.R;
import com.shoppingbag.app.PreferencesManager;
import com.shoppingbag.common_activities.MaintenancePage;
import com.shoppingbag.constants.BaseFragment;
import com.shoppingbag.constants.Cons;
import com.shoppingbag.model.response.wallet.ResponseFundLog;
import com.shoppingbag.model.wallet.ResponseWalletBalance;
import com.shoppingbag.utils.LoggerUtil;
import com.shoppingbag.utils.NetworkUtils;
import com.shoppingbag.utils.Utils;
import com.shoppingbag.wallet.DreamyWallet;
import com.shoppingbag.wallet.adapter.FundLogAdapter;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.shoppingbag.app.AppConfig.PAYLOAD_TYPE_FIVE_DMT_CUSTOMER_DETAILS;
import static com.shoppingbag.app.AppConfig.PAYLOAD_TYPE_TWENTY_FOUR_DMT_BENEFICIARY_UPDATE;
import static com.shoppingbag.app.AppConfig.PAYLOAD_TYPE_ZERO_DMT_CUSTOMER_REGISTRATION;

public class DmtRemitterFragment extends BaseFragment {

    @BindView(R.id.dmt_progress)
    ProgressBar dmtProgress;
    Unbinder unbinder;
    @BindView(R.id.txtCommisionWallet)
    TextView txtCommisionWallet;
    @BindView(R.id.rbDreamy)
    RadioButton rbDreamy;
    @BindView(R.id.rbBank)
    RadioButton rbBank;
    @BindView(R.id.radioWallet)
    RadioGroup radioWallet;
    @BindView(R.id.etAmount)
    TextInputEditText etAmount;
    @BindView(R.id.input_amount)
    TextInputLayout inputAmount;
    @BindView(R.id.etOtp)
    TextInputEditText etOtp;
    @BindView(R.id.txtInputOtpLay)
    TextInputLayout txtInputOtpLay;
    @BindView(R.id.btnOtp)
    Button btnOtp;
    @BindView(R.id.btn_proceed)
    Button btnProceed;
    @BindView(R.id.rv_transaction)
    RecyclerView rvTransaction;

    private String remitterId = "";
    private boolean varified = false;
    private boolean Otpvarified = false;
    private double walletBalance = 0;
    private String type = "CTTW";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dmt_layout_new, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreatedStuff(View view, @Nullable Bundle savedInstanceState) {

        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(RecyclerView.VERTICAL);
        rvTransaction.setLayoutManager(manager);
        if (!PreferencesManager.getInstance(context).getREMITTER_ID().equalsIgnoreCase("")) {
            varified = true;
            Otpvarified = true;
        } else {
            dmtProgress.setVisibility(View.GONE);
        }


        if (varified) {
            txtInputOtpLay.setVisibility(View.GONE);
            btnOtp.setVisibility(View.GONE);
        } else {
            getCustomerVerification();
        }

        radioWallet.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (radioGroup.getCheckedRadioButtonId() == R.id.rbBank) {
                    txtInputOtpLay.setVisibility(View.GONE);
                    btnOtp.setVisibility(View.GONE);
                    type="CTTB";
                    showTransaction();
                } else {
                    type="CTTW";
                    showTransaction();
                    if (varified) {
                        txtInputOtpLay.setVisibility(View.GONE);
                        btnOtp.setVisibility(View.GONE);
                    } else {
                        txtInputOtpLay.setVisibility(View.VISIBLE);
                        btnOtp.setVisibility(View.GONE);
                    }
                }

            }
        });


        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (radioWallet.getCheckedRadioButtonId() == R.id.rbDreamy) {
                    if (!TextUtils.isEmpty(etAmount.getText().toString()) && Float.parseFloat(etAmount.getText().toString().trim()) >= 50.00) {
                        if (walletBalance >= Float.parseFloat(etAmount.getText().toString())) {
                            // DO PAYMENT
                            transferToDreamyWallet();
                        } else {
                            Toast.makeText(context, "Invalid Amount", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, "Minimum amount should be 50.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (varified) {
                        if (Otpvarified) {
                            if (!TextUtils.isEmpty(etAmount.getText().toString()) && Float.parseFloat(etAmount.getText().toString().trim()) >= 50.00) {
                                if (walletBalance >= Float.parseFloat(etAmount.getText().toString())) {
                                    // DO PAYMENT
                                    Bundle bundle = new Bundle();
                                    bundle.putString("amount", etAmount.getText().toString().trim());
                                    goToActivity(AddMoneyContainer.class, bundle);
                                } else {
                                    Toast.makeText(context, "Invalid Amount", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(context, "Minimum amount should be 50.", Toast.LENGTH_SHORT).show();
                            }

                        } else {

                            if (!TextUtils.isEmpty(etAmount.getText().toString()) && Float.parseFloat(etAmount.getText().toString().trim()) >= 50.00) {
                                if (walletBalance >= Float.parseFloat(etAmount.getText().toString())) {
                                    // DO PAYMENT
                                    if (TextUtils.isEmpty(etOtp.getText().toString().trim())) {
                                        etOtp.setText("");
                                        etOtp.setError("Invalid OTP");
                                    } else if (etOtp.getText().toString().trim().length() < 4) {
                                        etOtp.setError("Invalid OTP");
                                    } else {
                                        getRemitterRegistrationValidationOTP(remitterId, etOtp.getText().toString().trim());
                                    }

                                } else {
                                    Toast.makeText(context, "Invalid Amount", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(context, "Minimum amount should be 50.", Toast.LENGTH_SHORT).show();
                            }

                        }
                    } else {
                        Toast.makeText(context, "Please Try Again Later", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        getWalletBalance();
        if (!PreferencesManager.getInstance(context).getREMITTER_ID().equalsIgnoreCase("")) {
            showTransaction();
        } else {
            dmtProgress.setVisibility(View.GONE);
        }
    }

    private void showTransaction() {
        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            rvTransaction.setVisibility(View.GONE);
            getTransactionList();
        } else {
            showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
        }
    }

    private void getTransactionList() {
        try {
            dmtProgress.setVisibility(View.VISIBLE);
            JsonObject param = new JsonObject();
            param.addProperty("FK_MemID", Cons.decryptMsg(PreferencesManager.getInstance(context).getUSERID(), cross_intent));
            param.addProperty("Type", type);
            LoggerUtil.logItem(param);
            Call<JsonObject> listCall = apiServicesCyper.getFundTransferLog(bodyParam(param), PreferencesManager.getInstance(context).getANDROIDID());
            listCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                    try {
                        if (dmtProgress != null)
                            dmtProgress.setVisibility(View.GONE);
                        LoggerUtil.logItem("TRASACTION");
                        if (response.isSuccessful()) {
                            ResponseFundLog response_new = new Gson().fromJson(Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent), ResponseFundLog.class);
                            LoggerUtil.logItem(response.body());
                            rvTransaction.setVisibility(View.VISIBLE);
                            if (response_new.getResponse().equalsIgnoreCase("Success")) {
                                FundLogAdapter logAdapter = new FundLogAdapter(context, response_new.getFundTransferLog(),type);
                                rvTransaction.setAdapter(logAdapter);
                            }
                        } else if (response.code() == 403) {
                        } else {
                            goToActivityWithFinish(MaintenancePage.class, null);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                    try {
                        if (dmtProgress != null) {
                            dmtProgress.setVisibility(View.GONE);
                            rvTransaction.setVisibility(View.VISIBLE);
                        }
                    } catch (Error | Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Error | Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void getCustomerVerification() {
        try {
            showLoading();
            JsonObject object = new JsonObject();
            object.addProperty("Type", PAYLOAD_TYPE_FIVE_DMT_CUSTOMER_DETAILS);
            object.addProperty("NUMBER", Cons.decryptMsg(PreferencesManager.getInstance(context).getMOBILE(), cross_intent));
            object.addProperty("AMOUNT_ALL", "1.00");
            object.addProperty("Amount", "1.00");

            LoggerUtil.logItem(object);

            Call<JsonObject> objectCall = apiServicesCyper.getCustomerVerification(bodyParam(object), PreferencesManager.getInstance(context).getANDROIDID());
            objectCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                    try {
                        hideLoading();
                        LoggerUtil.logItem("getCustomerVerification");
                        LoggerUtil.logItem(response.body());
                        if (response.isSuccessful()) {
                            JsonArray response_new = new Gson().fromJson(Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent), JsonArray.class);
                            JsonObject jobj = response_new.get(0).getAsJsonObject();
                            LoggerUtil.logItem(response_new.toString());
                            if ((jobj.get("ERROR").getAsString()).equalsIgnoreCase("0") &&
                                    (jobj.get("RESULT").getAsString()).equalsIgnoreCase("0")) {
                                JSONObject addinfo = new JSONObject((Utils.replaceBackSlash(jobj.get("ADDINFO").getAsString())));
                                JSONObject data = addinfo.getJSONObject("data").getJSONObject("remitter");
                                LoggerUtil.logItem(data);
                                if (data.getString("is_verified").equalsIgnoreCase("1")) {
                                    PreferencesManager.getInstance(context).setREMITTER_ID(Cons.encryptMsg(data.getString("id"), cross_intent));
                                    remitterId = data.getString("id");
                                    varified = true;
                                    Otpvarified = true;
                                    txtInputOtpLay.setVisibility(View.GONE);
                                    btnOtp.setVisibility(View.GONE);
                                } else {
                                    remitterId = data.getString("id");
                                    varified = true;
                                    Otpvarified = false;
                                    txtInputOtpLay.setVisibility(View.VISIBLE);
                                    btnOtp.setVisibility(View.GONE);
                                }
                            } else {
                                varified = false;
                                txtInputOtpLay.setVisibility(View.VISIBLE);
                                btnOtp.setVisibility(View.GONE);
                                getRemitterRegister();
                            }
                        } else if (response.code() == 403) {
                            LoggerUtil.logItem("403");
//
                        } else {
                            goToActivityWithFinish(MaintenancePage.class, null);
                        }
                    } catch (Error | Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                    hideLoading();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            hideLoading();
        }
    }

    private void getRemitterRegister() {
        try {
            showLoading();
            JsonObject object = new JsonObject();
            object.addProperty("Type", PAYLOAD_TYPE_ZERO_DMT_CUSTOMER_REGISTRATION);
            object.addProperty("NUMBER", Cons.decryptMsg(PreferencesManager.getInstance(context).getMOBILE(), cross_intent));
            object.addProperty("fName", Cons.decryptMsg(PreferencesManager.getInstance(context).getNAME(), cross_intent));
            object.addProperty("lName", Cons.decryptMsg(PreferencesManager.getInstance(context).getLNAME(), cross_intent));
            object.addProperty("Pin", Cons.decryptMsg(PreferencesManager.getInstance(context).getPINCODE(), cross_intent));
            object.addProperty("AMOUNT", "1.00");
            object.addProperty("AMOUNT_ALL", "1.00");
            LoggerUtil.logItem(object);

            Call<JsonObject> objectCall = apiServicesCyper.getRemitterRegistration(bodyParam(object), PreferencesManager.getInstance(context).getANDROIDID());
            objectCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                    try {
                        hideLoading();
                        LoggerUtil.logItem("getRemitterRegister");
                        LoggerUtil.logItem(response.body());
                        if (response.isSuccessful()) {
                            JsonArray response_new = new Gson().fromJson(Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent), JsonArray.class);
                            LoggerUtil.logItem(response_new.toString());
                            JsonObject jobj = response_new.get(0).getAsJsonObject();
                            if ((jobj.get("ERROR").getAsString()).equalsIgnoreCase("0") && (jobj.get("RESULT").getAsString()).equalsIgnoreCase("0")) {
                                JSONObject addinfo = new JSONObject((Utils.replaceBackSlash(jobj.get("ADDINFO").getAsString())));
                                JSONObject data = addinfo.getJSONObject("data").getJSONObject("remitter");
                                remitterId = data.getString("id");
                                varified = true;
                                Otpvarified = false;

                            }
                        } else {
                            goToActivityWithFinish(MaintenancePage.class, null);
                        }
                    } catch (Error | Exception e) {
                        hideLoading();
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                    hideLoading();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            hideLoading();
        }
    }

    private void getRemitterRegistrationValidationOTP(String remId, String otp) {
        try {
            showLoading();
            JsonObject object = new JsonObject();
            object.addProperty("Type", PAYLOAD_TYPE_TWENTY_FOUR_DMT_BENEFICIARY_UPDATE);
            object.addProperty("NUMBER", Cons.decryptMsg(PreferencesManager.getInstance(context).getMOBILE(), cross_intent));
            object.addProperty("remId", remId);
            object.addProperty("otc", otp);
            object.addProperty("AMOUNT", "1.00");
            object.addProperty("AMOUNT_ALL", "1.00");

            LoggerUtil.logItem(object);

            Call<JsonObject> objectCall = apiServicesCyper.getRemitterRegistrationValidationOTP(bodyParam(object), PreferencesManager.getInstance(context).getANDROIDID());
            objectCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                    try {
                        hideLoading();
                        LoggerUtil.logItem(response.body());
                        if (response.isSuccessful()) {
                            JsonArray response_new = new Gson().fromJson(Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent), JsonArray.class);
                            JsonObject jobj = response_new.get(0).getAsJsonObject();
                            if ((jobj.get("ERROR").getAsString()).equalsIgnoreCase("0") && (jobj.get("RESULT").getAsString()).equalsIgnoreCase("0")) {
                                PreferencesManager.getInstance(context).setREMITTER_ID(Cons.encryptMsg(remId, cross_intent));
                                Otpvarified = true;

                            } else {
                                Otpvarified = false;
                                etOtp.setText("");
                                btnOtp.setVisibility(View.VISIBLE);
                                etOtp.setError("Invalid OTP");
                            }
                        } else if (response.code() == 403) {

                        } else {
                            goToActivityWithFinish(MaintenancePage.class, null);
                        }
                    } catch (Error | Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                    hideLoading();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            hideLoading();
        }
    }

    private void getWalletBalance() {
        try {
            String url = BuildConfig.BASE_URL_MLM + "GetWallet?MemberId=" + PreferencesManager.getInstance(context).getUSERID();
            LoggerUtil.logItem("Wallet- " + url);
            Call<JsonObject> call = apiServices.getBankName(url, PreferencesManager.getInstance(context).getANDROIDID());
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                    try {
                        LoggerUtil.logItem("WALLET BALANCE");
                        LoggerUtil.logItem(response.body());
                        LoggerUtil.logItem(response.code());
                        if (response.isSuccessful()) {
                            ResponseWalletBalance responseWalletBalance = new Gson().fromJson(Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent), ResponseWalletBalance.class);
                            if (response.body() != null && responseWalletBalance.getResponse().equalsIgnoreCase("Success")) {

                                txtCommisionWallet.setText("Rs. " + responseWalletBalance.getResult().getCommisionWalletAmount());
                                walletBalance = responseWalletBalance.getResult().getCommisionWalletAmount();


                            } else {
                                walletBalance = 0;
                                txtCommisionWallet.setText("Rs. " + 0);

                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<JsonObject> call, @NotNull Throwable t) {
                    LoggerUtil.logItem("Wallet - " + t.getLocalizedMessage());
                }
            });

        } catch (Error | Exception e) {
            e.printStackTrace();
        }
    }

    private void transferToDreamyWallet() {
        try {
            showLoading();
            JsonObject object = new JsonObject();
            object.addProperty("fk_MemId", Cons.decryptMsg(PreferencesManager.getInstance(context).getUSERID(), cross_intent));
            object.addProperty("Amount", etAmount.getText().toString().trim());
            object.addProperty("TransactionId", "" + System.currentTimeMillis());

            LoggerUtil.logItem("Transfer Dreamy wallet");
            LoggerUtil.logItem(object);
            Call<JsonObject> objectCall = apiServices.getCommissionTransferToWallet(bodyParam(object), PreferencesManager.getInstance(context).getANDROIDID());

            objectCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                    hideLoading();
                    try {
                        LoggerUtil.logItem(response.body());
                        LoggerUtil.logItem(response.code());
                        LoggerUtil.logItem(call.request().url());
//                            'response':'',
//                            'message':''
                        if (response.isSuccessful()) {
                            JsonObject responseWalletBalance = new Gson().fromJson(Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent), JsonObject.class);
                            if (response.body() != null && responseWalletBalance.get("response").getAsString().equalsIgnoreCase("Success")) {
                                showMessage("Transfer Successfully");
                                goToActivityWithFinish(DreamyWallet.class, null);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(@NotNull Call<JsonObject> call, @NotNull Throwable t) {
                    hideLoading();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
