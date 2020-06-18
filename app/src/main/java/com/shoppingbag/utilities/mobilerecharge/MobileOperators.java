package com.shoppingbag.utilities.mobilerecharge;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.shoppingbag.BuildConfig;
import com.shoppingbag.R;
import com.shoppingbag.app.PreferencesManager;
import com.shoppingbag.constants.BaseActivity;
import com.shoppingbag.constants.Cons;
import com.shoppingbag.model.mobile_recharge.responsemodel.OperatorListsItem;
import com.shoppingbag.model.mobile_recharge.responsemodel.ResponseBillPaymentOperators;
import com.shoppingbag.model.mobile_recharge.responsemodel.ResponseGetRechargeOperators;
import com.shoppingbag.utilities.mobilerecharge.adapters.BillingOperatorsAdapter;
import com.shoppingbag.utilities.mobilerecharge.adapters.OperatorsAdapter;
import com.shoppingbag.utils.LoggerUtil;
import com.shoppingbag.utils.NetworkUtils;
import com.shoppingbag.utils.RecyclerTouchListener;
import com.shoppingbag.utils.Utils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MobileOperators extends BaseActivity {

    private static String TAG = "MobileOperator";

    @BindView(R.id.operatorRecyclerView)
    RecyclerView operatorRecyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private String operatorType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.moble_operators);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_clear_white);

        if (getIntent() != null) {
            operatorType = getIntent().getStringExtra("OPERATOR__TYPE");
            Log.e("---------------", Objects.requireNonNull(operatorType));

        } else {
            Intent intent = new Intent();
            setResult(RESULT_CANCELED, intent);
            finish();
        }
        toolbar.setNavigationOnClickListener(v -> {
            Intent intent = new Intent();
            setResult(RESULT_CANCELED, intent);
            finish();
        });

        if (NetworkUtils.getConnectivityStatus(this) != 0) {
            if (operatorType.equalsIgnoreCase(Utils.ITS_PREPAID)) {
                getPrepaidRecharge();
            } else if (operatorType.equalsIgnoreCase(Utils.ITS_POSTPAID)) {
                getPostpaidRecharge();
            }
        } else {
            showSnackBar(getString(R.string.alert_internet));
        }

    }

    private void getPrepaidRecharge() {
        try {
            showLoading();

            String url = BuildConfig.BASE_URL_TRAVEL + "GetOperatorsMobile";

            Call<JsonObject> busCall = apiServicesTravel.getApi(url, PreferencesManager.getInstance(context).getANDROIDID());
            busCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                    try {
                        hideLoading();
                        LoggerUtil.logItem(call.request().url());
                        LoggerUtil.logItem(response.body());

                        String param = Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent);
                        Gson gson = new GsonBuilder().create();
                        LoggerUtil.logItem(param);
                        ResponseGetRechargeOperators rechargeOperators = gson.fromJson(param, ResponseGetRechargeOperators.class);

                        if (rechargeOperators.getResponseStatus() == 1) {
                            final ArrayList<OperatorListsItem> operatorList = new ArrayList<>();
                            operatorRecyclerView.setLayoutManager(new LinearLayoutManager(context));

                            for (int i = 0; i < rechargeOperators.getRechargeOperatorsOutput().getOperatorLists().size(); i++) {
                                if (!rechargeOperators.getRechargeOperatorsOutput().getOperatorLists().get(i).getRechargeType().equalsIgnoreCase("DTH") &&
                                        !rechargeOperators.getRechargeOperatorsOutput().getOperatorLists().get(i).getOperatorDescritpion().contains("MTNL") &&
                                        !rechargeOperators.getRechargeOperatorsOutput().getOperatorLists().get(i).getOperatorDescritpion().equalsIgnoreCase("TATA INDICOM") &&
                                        !rechargeOperators.getRechargeOperatorsOutput().getOperatorLists().get(i).getOperatorDescritpion().equalsIgnoreCase("TATA PHOTON PLUS")) {

                                    OperatorListsItem item = new OperatorListsItem();
                                    item.setOperatorCode(rechargeOperators.getRechargeOperatorsOutput().getOperatorLists().get(i).getOperatorCode());
                                    item.setCommission(rechargeOperators.getRechargeOperatorsOutput().getOperatorLists().get(i).getCommission());
                                    item.setRechargeType(rechargeOperators.getRechargeOperatorsOutput().getOperatorLists().get(i).getRechargeType());
                                    item.setOperatorDescritpion(rechargeOperators.getRechargeOperatorsOutput().getOperatorLists().get(i).getOperatorDescritpion());
                                    operatorList.add(item);
                                }
                            }

                            OperatorsAdapter adapter = new OperatorsAdapter(MobileOperators.this, operatorList);
                            operatorRecyclerView.setAdapter(adapter);
                            operatorRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(MobileOperators.this, operatorRecyclerView, new RecyclerTouchListener.ClickListener() {
                                @Override
                                public void onClick(View view, int position) {
                                    Intent intent = new Intent();
                                    intent.putExtra("CODE", operatorList.get(position).getOperatorCode());
                                    intent.putExtra("OP_NAME", operatorList.get(position).getOperatorDescritpion());
                                    setResult(RESULT_OK, intent);
                                    finish();
                                }

                                @Override
                                public void onLongClick(View view, int position) {

                                }
                            }));
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

    private void getPostpaidRecharge() {
        try {
            showLoading();

            String url = BuildConfig.BASE_URL_TRAVEL + "GetBillPaymentOperatorsRequest";

            Call<JsonObject> busCall = apiServicesTravel.getApi(url, PreferencesManager.getInstance(context).getANDROIDID());
            busCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                    try {
                        hideLoading();
                        LoggerUtil.logItem(response.body());

                        String param = Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent);
                        Gson gson = new GsonBuilder().create();
                        LoggerUtil.logItem(param);
                        ResponseBillPaymentOperators rechargeOperators = gson.fromJson(param, ResponseBillPaymentOperators.class);


                        if (rechargeOperators.getResponseStatus() == 1) {
                            Log.e(TAG, "response two " + response.body().toString());
                            final List<OperatorListsItem> operatorList = new ArrayList<>();
                            for (int i = 0; i < rechargeOperators.getBillPaymentOperatorsOutput().getOperatorLists().size(); i++) {
                                if (!rechargeOperators.getBillPaymentOperatorsOutput().getOperatorLists().get(i).getOperatorDescritpion().equalsIgnoreCase("TATA INDICOM LANDLINE")) {
                                    OperatorListsItem item = new OperatorListsItem();
                                    item.setOperatorCode(rechargeOperators.getBillPaymentOperatorsOutput().getOperatorLists().get(i).getOperatorCode());
                                    item.setCommission(rechargeOperators.getBillPaymentOperatorsOutput().getOperatorLists().get(i).getCommission());
                                    item.setRechargeType(rechargeOperators.getBillPaymentOperatorsOutput().getOperatorLists().get(i).getRechargeType());
                                    item.setOperatorDescritpion(rechargeOperators.getBillPaymentOperatorsOutput().getOperatorLists().get(i).getOperatorDescritpion());
                                    operatorList.add(item);
                                }
                            }

                            BillingOperatorsAdapter adapter = new BillingOperatorsAdapter(MobileOperators.this, operatorList);
                            operatorRecyclerView.setLayoutManager(new LinearLayoutManager(context));
                            operatorRecyclerView.setAdapter(adapter);
                            operatorRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(MobileOperators.this, operatorRecyclerView, new RecyclerTouchListener.ClickListener() {
                                @Override
                                public void onClick(View view, int position) {
                                    Intent intent = new Intent();
                                    intent.putExtra("CODE", operatorList.get(position).getOperatorCode());
                                    intent.putExtra("OP_NAME", operatorList.get(position).getOperatorDescritpion());
                                    setResult(RESULT_OK, intent);
                                    finish();
                                }

                                @Override
                                public void onLongClick(View view, int position) {

                                }
                            }));
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

}
