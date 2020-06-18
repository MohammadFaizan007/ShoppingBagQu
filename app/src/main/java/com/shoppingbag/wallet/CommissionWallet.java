package com.shoppingbag.wallet;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.shoppingbag.BuildConfig;
import com.shoppingbag.R;
import com.shoppingbag.app.PreferencesManager;
import com.shoppingbag.common_activities.KycCommisionActivity;
import com.shoppingbag.constants.BaseActivity;
import com.shoppingbag.constants.Cons;
import com.shoppingbag.model.response.wallet.ResponseDreamyWalletLedger;
import com.shoppingbag.model.wallet.ResponseGetKYC;
import com.shoppingbag.model.wallet.ResponseWalletBalance;
import com.shoppingbag.utils.LoggerUtil;
import com.shoppingbag.utils.NetworkUtils;
import com.shoppingbag.utils.Utils;
import com.shoppingbag.wallet.adapter.WalletLedgerAdapter;
import com.shoppingbag.wallet.dmt.DmtActivity;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommissionWallet extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.textViewNote)
    TextView textViewNote;
    @BindView(R.id.textViewCompKYC)
    TextView textViewCompKYC;
    @BindView(R.id.tv_walletBalance)
    TextView tvWalletBalance;
    @BindView(R.id.recycler_wallet)
    RecyclerView recyclerWallet;
    @BindView(R.id.btn_filter)
    Button btnFilter;
    @BindView(R.id.btn_uploadkyc)
    Button btn_uploadkyc;
    @BindView(R.id.txt_norecordfound)
    TextView txtNorecordfound;

    private int pageIndex = 1;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.commission_wallet_layout);
        ButterKnife.bind(this);
        btnFilter.setVisibility(View.GONE);
        title.setText("Commission Wallet");

        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(RecyclerView.VERTICAL);
        recyclerWallet.setLayoutManager(manager);
        textViewNote.setVisibility(View.GONE);


    }


    private void getWalletBalance() {
        try {
            String url = BuildConfig.BASE_URL_MLM + "GetWallet?MemberId=" + PreferencesManager.getInstance(context).getUSERID();
            Call<JsonObject> call = apiServices.getBankName(url, PreferencesManager.getInstance(context).getANDROIDID());
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                    try {
                        LoggerUtil.logItem(response.body());
                        LoggerUtil.logItem(response.code());
                        if (response.isSuccessful()) {
                            ResponseWalletBalance responseWalletBalance = new Gson().fromJson(Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent), ResponseWalletBalance.class);
                            if (response.body() != null && responseWalletBalance.getResponse().equalsIgnoreCase("Success")) {
                                tvWalletBalance.setText(String.format("₹ %s", responseWalletBalance.getResult().getCommisionWalletAmount()));
                            } else {
                                tvWalletBalance.setText("₹ 0");
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<JsonObject> call, @NotNull Throwable t) {

                }
            });

        } catch (Error | Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            getKYCStatus();
            getWalletBalance();
            getWalletLedger("", "");
        } else {
            showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
        }
    }

    private void getKYCStatus() {
        try {
            String url = BuildConfig.BASE_URL_MLM + "GetKYCStatus?MemberId=" + PreferencesManager.getInstance(context).getUSERID();
            Call<JsonObject> call = apiServices.getKYCStatus(url, PreferencesManager.getInstance(context).getANDROIDID());
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                    try {
                        Log.d(">>>", "onResponse: >>>"+response.body());
                        LoggerUtil.logItem(response.body());
                        LoggerUtil.logItem(response.code());
                        if (response.isSuccessful()) {
                            ResponseGetKYC responseWalletBalance = new Gson().fromJson(Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent), ResponseGetKYC.class);
                            if (response.body() != null && responseWalletBalance.getResponse().equalsIgnoreCase("Success")) {
                                if(responseWalletBalance.getStatus()==null ||responseWalletBalance.getStatus().equalsIgnoreCase("Pending")){
                                    btn_uploadkyc.setVisibility(View.VISIBLE);
                                    btn_uploadkyc.setText("Upload KYC");
                                    textViewCompKYC.setVisibility(View.VISIBLE);
                                }else if(responseWalletBalance.getStatus().equalsIgnoreCase("Applied")){
                                    btn_uploadkyc.setVisibility(View.VISIBLE);
                                    btn_uploadkyc.setText("KYC Approval Pending");
                                    btn_uploadkyc.setClickable(false);
                                    textViewCompKYC.setVisibility(View.VISIBLE);
//                                    btnFilter.setVisibility(View.VISIBLE);
                                }else if(responseWalletBalance.getStatus().equalsIgnoreCase("Approved")){
                                    btn_uploadkyc.setVisibility(View.GONE);
                                    if (PreferencesManager.getInstance(context).getStatus().equalsIgnoreCase("Active")) {
                                        btnFilter.setVisibility(View.VISIBLE);
                                        textViewCompKYC.setVisibility(View.GONE);
                                    } else {
//                                        btnFilter.setVisibility(View.VISIBLE);

                                        textViewCompKYC.setVisibility(View.VISIBLE);
                                        textViewCompKYC.setText("Please Complete Your shopping of Rs. 500 to activate your account.");
                                        btnFilter.setVisibility(View.GONE);
                                        btn_uploadkyc.setVisibility(View.GONE);
//                                        btn_uploadkyc.setText("InActive");
//                                        btn_uploadkyc.setClickable(false);
                                    }

                                }else {
                                    btn_uploadkyc.setVisibility(View.VISIBLE);
                                    btn_uploadkyc.setText("Upload KYC");
                                    textViewCompKYC.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<JsonObject> call, @NotNull Throwable t) {

                }
            });

        } catch (Error | Exception e) {
            e.printStackTrace();
        }
    }

    private void getWalletLedger(String fromDate, String toDate) {

        try {
            showLoading();
            JsonObject param = new JsonObject();
            param.addProperty("memberId", Cons.decryptMsg(PreferencesManager.getInstance(context).getUSERID(), cross_intent));
            param.addProperty("fromDate", fromDate);
            param.addProperty("toDate", toDate);
            param.addProperty("page", pageIndex);
            param.addProperty("size", 20);
            param.addProperty("walletType", "Com");
            LoggerUtil.logItem(param.toString());

            Call<JsonObject> call = apiServices.getWalletLedger(bodyParam(param), PreferencesManager.getInstance(context).getANDROIDID());
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                    try {
                        hideLoading();
                        LoggerUtil.logItem(response.body());
                        LoggerUtil.logItem(call.request().url().toString());
                        LoggerUtil.logItem("<><><><><><>" + Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent));

                        ResponseDreamyWalletLedger dreamyWalletLedger = new Gson().fromJson(Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent), ResponseDreamyWalletLedger.class);
                        if (dreamyWalletLedger.getResponse().equalsIgnoreCase("Success")) {

                            recyclerWallet.setVisibility(View.VISIBLE);
                            txtNorecordfound.setVisibility(View.GONE);
                            if (dreamyWalletLedger.getResult().size() > 0) {
                                WalletLedgerAdapter ledgerAdapter = new WalletLedgerAdapter(context, dreamyWalletLedger.getResult());
                                recyclerWallet.setAdapter(ledgerAdapter);
                            } else {
                                txtNorecordfound.setVisibility(View.VISIBLE);
                                recyclerWallet.setVisibility(View.GONE);
                            }
                        } else {
                            txtNorecordfound.setVisibility(View.VISIBLE);
                            recyclerWallet.setVisibility(View.GONE);

                            showMessage(Utils.getDefaultValue(dreamyWalletLedger.getMessage(), "No Data"));
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<JsonObject> call, @NotNull Throwable t) {

                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    @OnClick({R.id.side_menu, R.id.btn_filter, R.id.btn_uploadkyc})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.side_menu:
                onBackPressed();
                break;
            case R.id.btn_filter:
                if (NetworkUtils.getConnectivityStatus(context) != 0) {
                    goToActivity(CommissionWallet.this, DmtActivity.class, null);
                } else {
                    showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
                }
                break;
                case R.id.btn_uploadkyc:
                if (NetworkUtils.getConnectivityStatus(context) != 0) {
                    goToActivity(CommissionWallet.this, KycCommisionActivity.class, null);
                } else {
                    showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
                }
                break;

        }
    }

}

