package com.shoppingbag.utilities.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.shoppingbag.app.AppConfig;
import com.shoppingbag.app.CheckErrorCode;
import com.shoppingbag.app.PreferencesManager;
import com.shoppingbag.constants.BaseActivity;
import com.shoppingbag.constants.Cons;
import com.shoppingbag.model.request.utility.response.ResponseBalanceAmount;
import com.shoppingbag.model.utility.RecentActivityItem;
import com.shoppingbag.model.utility.ResponseDthRecharge;
import com.shoppingbag.model.utility.ResponseRecentRecharges;
import com.shoppingbag.model.wallet.ResponseWalletBalance;
import com.shoppingbag.utils.LoggerUtil;
import com.shoppingbag.utils.RecyclerTouchListener;
import com.shoppingbag.utils.Utils;
import com.shoppingbag.R;
import com.shoppingbag.adapter.RechargeHistoryAdapter;
import com.shoppingbag.common_activities.LoginActivity;
import com.shoppingbag.common_activities.MaintenancePage;
import com.shoppingbag.one_india_shopping.model.payment_method.ResponsePayMethod;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DTH_Recharge extends BaseActivity {
    private static String TAG = "DTH_recharge";
    private final int OPERATOR_STAT_CODE = 100;
    @BindView(R.id.side_menu)
    ImageView sideMenu;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.textViewNote)
    TextView textViewNote;
    @BindView(R.id.dth_edt_operator)
    TextInputEditText dthEdtOperator;
    @BindView(R.id.dth_input_operator)
    TextInputLayout dthInputOperator;
    @BindView(R.id.dth_edt_id)
    TextInputEditText dthEdtId;
    @BindView(R.id.dth_input_customerid)
    TextInputLayout dthInputCustomerid;
    @BindView(R.id.dth_edt_amount)
    TextInputEditText dthEdtAmount;
    @BindView(R.id.dth_total_amount)
    TextInputLayout dthTotalAmount;
    @BindView(R.id.dth_proceedToPay)
    Button dthProceedToPay;
    @BindView(R.id.currentOperator)
    ConstraintLayout currentOperator;
    @BindView(R.id.rv_rchistory)
    RecyclerView rvRchistory;
    @BindView(R.id.txt_norecordfound)
    TextView txtNorecordfound;
    private String operatorCode, operatorName;

    private Double CashwalletBalance = 0.0;
    private Double DreamywalletBalance = 0.0;
    private static DecimalFormat format = new DecimalFormat("0.00");
    String tempToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dth_recharge_new);
        ButterKnife.bind(this);
        title.setText("DTH Recharge");
        textViewNote.setText("10% of the Total Amount is Deducted from Cashback Wallet");
        Utils.hideSoftKeyboard(this);
        if (operatorCode != null) {
            dthEdtOperator.setText(operatorName);
        } else {
            dthEdtOperator.setText(getString(R.string.chooseOperatorCode));
        }

        getRechargeHistoryHere();
    }

    @OnClick({R.id.side_menu, R.id.dth_proceedToPay, R.id.dth_edt_operator})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.side_menu:
                hideKeyboard();
                finish();
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                break;
            case R.id.dth_edt_operator:
                hideKeyboard();
                PopupMenu popup_operator = new PopupMenu(context, view);
                popup_operator.getMenuInflater().inflate(R.menu.menu_provider_dthrecharge, popup_operator.getMenu());
                popup_operator.setOnMenuItemClickListener(item -> {
                    dthEdtOperator.setText(item.getTitle());
                    return true;
                });
                popup_operator.show();
                //  startActivityForResult(new Intent(getApplicationContext(), DthOperators.class), OPERATOR_STAT_CODE);
                break;
            case R.id.dth_proceedToPay:
                if (validation()) {
                    getWalletBalanceCheckRunTime(dthEdtAmount.getText().toString().trim());
                }
                break;
        }
    }

    public boolean validation() {
        String strOperator, strCustomerID, strTotalAmount;
        strOperator = dthEdtOperator.getText().toString().trim();
        strCustomerID = dthEdtId.getText().toString().trim();
        strTotalAmount = dthEdtAmount.getText().toString().trim();

        if (strOperator.length() == 0) {
            dthEdtOperator.setError("Please Check Your DTH Operator.");
            requestFocus(dthEdtOperator);
            return false;
        }
        if (strOperator.equalsIgnoreCase(getString(R.string.chooseOperatorCode))) {
            dthEdtOperator.setError("Please Check Your DTH Operator.");
            requestFocus(dthEdtOperator);
            return false;
        } else if (strCustomerID.length() == 0) {
            dthEdtId.setError("Please Check Your Customer Id");
            requestFocus(dthEdtId);
            return false;
        }
        if (strTotalAmount.length() == 0) {
            dthEdtAmount.setError("Please Check Your Recharge Amount.");
            requestFocus(dthEdtAmount);
            return false;
        }
        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case OPERATOR_STAT_CODE:
                    if (data != null) {
                        Log.e("OPERATOR", "= " + data.getStringExtra("DTH_OPERATOR"));
                        Log.e("OPERATOR", "= " + data.getStringExtra("DTH_CODE"));
                        operatorName = data.getStringExtra("DTH_OPERATOR");
                        operatorCode = data.getStringExtra("DTH_CODE");
                        if (operatorCode != null) {
                            dthEdtOperator.setText(operatorName);
                        } else {
                            dthEdtOperator.setText(R.string.choose_operator);
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }

//    private void getWalletBalance(String totalAmount) {
//        showLoading();
//        String url = BuildConfig.BASE_URL_MLM + "GetWallet?MemberId=" + PreferencesManager.getInstance(context).getUSERID();
//        Call<JsonObject> call = apiServices.getBankName(url, PreferencesManager.getInstance(context).getANDROIDID());
//        call.enqueue(new Callback<JsonObject>() {
//            @Override
//            public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
//                try {
//                    hideLoading();
//                    LoggerUtil.logItem(response.body());
//                    LoggerUtil.logItem(response.code());
//                    LoggerUtil.logItem(call.request().url());
//                    if (response.isSuccessful()) {
//                        ResponseWalletBalance responseWalletBalance = new Gson().fromJson(Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent), ResponseWalletBalance.class);
//                        if (response.body() != null && responseWalletBalance.getResponse().equalsIgnoreCase("Success")) {
//                            CashwalletBalance = responseWalletBalance.getResult().getCashbackWalletAmount();
//                            DreamywalletBalance = responseWalletBalance.getResult().getDreamyWalletAmount();
//                            if (!TextUtils.isEmpty(totalAmount)) {
//                                double amount = Double.parseDouble(totalAmount);
//                                if (DreamywalletBalance >= amount) {
//                                    getWalletBalanceCheckRunTime(dthEdtAmount.getText().toString(), CashwalletBalance, amount);
//                                } else {
//                                    showMessage("Please Add Amount in Dreamy Wallet");
//                                }
//                            }
//                        }
//                    }
//                } catch (Exception e) {
//                    hideLoading();
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(@NotNull Call<JsonObject> call, @NotNull Throwable t) {
//                hideLoading();
//            }
//        });
//    }

    private void getWalletBalanceCheckRunTime(String productAmount) {
        try {
            showLoading();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("MemberId", Cons.decryptMsg(PreferencesManager.getInstance(context).getUSERID(), cross_intent));
            LoggerUtil.logItem(jsonObject);

            Call<JsonObject> walletBalanceCall = apiServicesCyper.getbalanceAmount(bodyParam(jsonObject), PreferencesManager.getInstance(context).getANDROIDID());
            walletBalanceCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                    hideLoading();
                    LoggerUtil.logItem(response.body());
                    LoggerUtil.logItem(call.request().url());
                    try {
                        if (response.isSuccessful()) {
                            String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent);
                            ResponseBalanceAmount convertedObject = new Gson().fromJson(paramResponse, ResponseBalanceAmount.class);
                            if (convertedObject.getStatus().equalsIgnoreCase("Success")) {
                                tempToken = convertedObject.getValidateToken();
                                Log.d(TAG, "onResponse: " + convertedObject.getBalanceAmount());
                                if (convertedObject.getBalanceAmount() >= Float.parseFloat(productAmount)) {
                                    getRechargeDoneHere(productAmount);
                                } else {
                                    showMessage("Insufficient wallet balance");
                                    // createAddBalanceDialog(context, "Insufficient bag balance", "You have insufficient balance in your bag, add money before making transactions.");
                                }
                            } else {
                                tempToken = "";
                            }
                        } else if (response.code() == 403) {
                            clearPrefrenceforTokenExpiry();
                            getAndroidId();
                            goToActivityWithFinish(LoginActivity.class, null);
                        } else {
                            goToActivityWithFinish(MaintenancePage.class, null);
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void hitDebitWallet(Double cashWallet, Double dreamyWallet, String orderId) {
        try {

            showLoading();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("type", "DTH");
            jsonObject.addProperty("transactiondate", Utils.getTodayDate());
            jsonObject.addProperty("transactionId", orderId);
            jsonObject.addProperty("mainamount", String.valueOf(format.format(dreamyWallet)));
            jsonObject.addProperty("walletamount", String.valueOf(format.format(cashWallet)));
            jsonObject.addProperty("paymentgatewayamount", "0");

            jsonObject.addProperty("memberId", Cons.decryptMsg(PreferencesManager.getInstance(context).getUSERID(), cross_intent));


            LoggerUtil.logItem(jsonObject);
            Call<JsonObject> jsonObjectCall = apiServices.executeDebitWallet(bodyParam(jsonObject), PreferencesManager.getInstance(context).getANDROIDID());
            jsonObjectCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                    hideLoading();
                    LoggerUtil.logItem(response.body());
                    LoggerUtil.logItem(response.code());
                    LoggerUtil.logItem(call.request().url());
                    try {
                        if (response.isSuccessful()) {
                            String param = Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent);
                            Gson gson = new GsonBuilder().create();
                            ResponsePayMethod addAdress = gson.fromJson(param, ResponsePayMethod.class);
                            if (addAdress.getResponse().equalsIgnoreCase("Success")) {

                                dthEdtId.setText("");
                                dthEdtAmount.setText("");
                                dthEdtOperator.setText(getString(R.string.chooseOperatorCode));
                                showMessage("DTH Recharge Successfully");
                            }
                        } else {
                            showMessage("something went wrong");
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<JsonObject> call, @NotNull Throwable t) {
                    LoggerUtil.logItem(t.getMessage());
                    hideLoading();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getRechargeDoneHere(String amount) {
        try {
            showLoading();
            JsonObject mainjson = new JsonObject();
            mainjson.addProperty("NUMBER", dthEdtId.getText().toString());
            mainjson.addProperty("ValidateToken", tempToken);
            mainjson.addProperty("ACCOUNT", AppConfig.PAYLOAD_ACCOUNT_RECHARGE_TWO);
            mainjson.addProperty("AMOUNT", dthEdtAmount.getText().toString().trim());
            mainjson.addProperty("Provider", dthEdtOperator.getText().toString());
            mainjson.addProperty("FK_MemID", Cons.decryptMsg(PreferencesManager.getInstance(context).getUSERID(), cross_intent));
            LoggerUtil.logItem(mainjson);

            Call<JsonObject> busCall = apiServicesCyper.getDthRecharge(bodyParam(mainjson), PreferencesManager.getInstance(context).getANDROIDID());
            busCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                    try {
                        hideLoading();
                        LoggerUtil.logItem(response.body());
                        LoggerUtil.logItem(call.request().url());
                        String param = Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent);
                        Gson gson = new GsonBuilder().create();
                        LoggerUtil.logItem("<><<" + param);

                        if (response.body() != null) {
                            String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent);
                            List<ResponseDthRecharge> list = Utils.getList(paramResponse, ResponseDthRecharge.class);
                            LoggerUtil.logItem(list);
                            if (list.get(0).getError().equalsIgnoreCase("0")
                                    && (list.get(0).getResult().equalsIgnoreCase("0"))) {
                                showMessage("DTH Recharge Successfully");
                                getRechargeHistoryHere();
                            } else {
                                CheckErrorCode code = new CheckErrorCode();
                                code.isValidTransaction(context, list.get(0).getError());
                            }
                        } else {
                            showMessage("Something went wrong. \nPlease try after some time.");
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
            hideLoading();
        }
    }


    private void getRechargeHistoryHere() {
        try {

            JsonObject mainjson = new JsonObject();
            mainjson.addProperty("Action", "DTH");
            mainjson.addProperty("FK_MemID", Cons.decryptMsg(PreferencesManager.getInstance(context).getUSERID(), cross_intent));
            mainjson.addProperty("Type", "");
            LoggerUtil.logItem(mainjson);

            Call<JsonObject> busCall = apiServicesCyper.getRecentRecharges(bodyParam(mainjson), PreferencesManager.getInstance(context).getANDROIDID());
            busCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                    LoggerUtil.logItem(response.body());
                    LoggerUtil.logItem(response.code());
                    LoggerUtil.logItem(call.request().url());
                    try {
                        if (response.isSuccessful()) {

                            String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent);
                            ResponseRecentRecharges convertedObject = new Gson().fromJson(paramResponse, ResponseRecentRecharges.class);
                            LoggerUtil.logItem(convertedObject);

                            if (convertedObject.getResponse().equalsIgnoreCase("Success")) {
                                List<RecentActivityItem> list = convertedObject.getRecentActivity();
                                if (list != null) {
                                    rvRchistory.setVisibility(View.VISIBLE);
                                    txtNorecordfound.setVisibility(View.GONE);
                                    RechargeHistoryAdapter adapter = new RechargeHistoryAdapter(context, list, DTH_Recharge.this);
                                    rvRchistory.setLayoutManager(new LinearLayoutManager(context));
                                    rvRchistory.setAdapter(adapter);
                                    rvRchistory.addOnItemTouchListener(new RecyclerTouchListener(context,
                                            rvRchistory,
                                            new RecyclerTouchListener.ClickListener() {
                                                @Override
                                                public void onClick(View view, int position) {

                                                }

                                                @Override
                                                public void onLongClick(View view, int position) {

                                                }
                                            }));
                                } else {
                                    txtNorecordfound.setVisibility(View.VISIBLE);
                                    rvRchistory.setVisibility(View.GONE);
                                }
                            } else {
                                txtNorecordfound.setVisibility(View.VISIBLE);
                                rvRchistory.setVisibility(View.GONE);

                            }

                        } else {
                            showMessage("Something went wrong");
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
            hideLoading();
        }
    }


    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }
}
