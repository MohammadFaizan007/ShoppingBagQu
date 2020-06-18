package com.shoppingbag.wallet;

import android.os.Bundle;
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
import com.shoppingbag.constants.BaseActivity;
import com.shoppingbag.constants.Cons;
import com.shoppingbag.model.response.wallet.ResponseDreamyWalletLedger;
import com.shoppingbag.model.wallet.ResponseWalletBalance;
import com.shoppingbag.utils.LoggerUtil;
import com.shoppingbag.utils.NetworkUtils;
import com.shoppingbag.utils.Utils;
import com.shoppingbag.wallet.adapter.WalletLedgerAdapter;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CashBackWallet extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.textViewNote)
    TextView textViewNote;
    @BindView(R.id.tv_walletBalance)
    TextView tvWalletBalance;
    //    @BindView(R.id.et_fromDate)
//    TextView etFromDate;
//    @BindView(R.id.et_toDate)
//    TextView etToDate;
    @BindView(R.id.recycler_wallet)
    RecyclerView recyclerWallet;
    @BindView(R.id.btn_filter)
    Button btnFilter;
    @BindView(R.id.txt_norecordfound)
    TextView txtNorecordfound;

    private int pageIndex = 1;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cash_back_wallet);
        ButterKnife.bind(this);

        title.setText("CashBack Wallet");
        textViewNote.setVisibility(View.GONE);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(RecyclerView.VERTICAL);
        recyclerWallet.setLayoutManager(manager);

        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            getWalletBalance();
            getWalletLedger("", "");
        } else {
            showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
        }

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
                                tvWalletBalance.setText(String.format("₹ %s", responseWalletBalance.getResult().getCashbackWalletAmount()));
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

    private void getWalletLedger(String fromDate, String toDate) {

        try {
            showLoading();
            JsonObject param = new JsonObject();
            param.addProperty("memberId", Cons.decryptMsg(PreferencesManager.getInstance(context).getUSERID(), cross_intent));
            param.addProperty("fromDate", fromDate);
            param.addProperty("toDate", toDate);
            param.addProperty("page", pageIndex);
            param.addProperty("size", 20);
            param.addProperty("walletType", "C");
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

    @OnClick({R.id.side_menu/*, R.id.btn_filter, R.id.et_fromDate, R.id.et_toDate*/})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.side_menu:
                onBackPressed();
                break;
//            case R.id.btn_filter:
//                if (validation()) {
//                    if (NetworkUtils.getConnectivityStatus(context) != 0) {
//                        getWalletLedger(etFromDate.getText().toString(), etToDate.getText().toString());
//                    } else {
//                        showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
//                    }
//                }
//                break;
//            case R.id.et_fromDate:
//                getFromDate(false);
//                break;
//            case R.id.et_toDate:
//                if (etFromDate.getText().toString().trim().equalsIgnoreCase("")) {
//                    showMessage("Select From date.");
//                } else {
//                    getFromDate(true);
//                }
//
//                break;

        }
    }

//    private boolean validation() {
//
//        if (TextUtils.isEmpty(etFromDate.getText().toString().trim())) {
//            showMessage("Select From Date.");
//            return false;
//        } else if (TextUtils.isEmpty(etToDate.getText().toString().trim())) {
//            showMessage("Select To Date.");
//            return false;
//        }
//
//        return true;
//    }


//    public void getFromDate(boolean todate) {
//        Calendar calendar = Calendar.getInstance();
//        int mMonth = calendar.get(Calendar.MONTH);
//        int yYear = calendar.get(Calendar.YEAR);
//        int dDay = calendar.get(Calendar.DAY_OF_MONTH);
//
//        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                if (todate) {
//                    etToDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
//                } else {
//                    etFromDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
//                }
//            }
//        }, yYear, mMonth, dDay);
//        datePickerDialog.show();
//        if (todate) {
//            datePickerDialog.getDatePicker().setMinDate(Utils.getTimestamp(etFromDate.getText().toString(), "dd/MM/yyyy"));
//        }
//
//        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
//    }
}
