package com.shoppingbag.utilities.dthrecharge;

import android.content.Intent;
import android.os.Bundle;
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
import com.shoppingbag.model.mobile_recharge.requestmodel.RequestGetRechargeOperators;
import com.shoppingbag.model.mobile_recharge.responsemodel.OperatorListsItem;
import com.shoppingbag.model.mobile_recharge.responsemodel.ResponseGetRechargeOperators;
import com.shoppingbag.utilities.mobilerecharge.adapters.OperatorsAdapter;
import com.shoppingbag.utils.LoggerUtil;
import com.shoppingbag.utils.NetworkUtils;
import com.shoppingbag.utils.RecyclerTouchListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DthOperators extends BaseActivity {

    private static String TAG = "DTH_OPERATOR";
    List<OperatorListsItem> operatorList = new ArrayList<>();
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.dthOperatorRecyclerView)
    RecyclerView dthOperatorRecyclerView;
    Gson gson = new GsonBuilder().create();
    List<OperatorListsItem> filteredList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dth_operators);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_clear_white);
        toolbar.setNavigationOnClickListener(v -> {
            Intent intent = new Intent();
            setResult(RESULT_CANCELED, intent);
            finish();
        });
        if (NetworkUtils.getConnectivityStatus(this) != 0) {
            getRechargeOperatorsHere();
        } else {
            showSnackBar(getString(R.string.alert_internet));
        }
        dthOperatorRecyclerView.setLayoutManager(new LinearLayoutManager(context));
    }

    private void getRechargeOperatorsHere() {
        try {
            showLoading();
            final RequestGetRechargeOperators request = new RequestGetRechargeOperators();

            LoggerUtil.logItem(request);
            String url = BuildConfig.BASE_URL_TRAVEL + "GetOperatorsMobile";

            Call<JsonObject> busCall = apiServicesTravel.getApi(url, PreferencesManager.getInstance(context).getANDROIDID());
            busCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                    try {
                    hideLoading();
                    LoggerUtil.logItem(call.request().url());
                    LoggerUtil.logItem(response.body());

                    String param = null;

                        param = Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent);

                    Gson gson = new GsonBuilder().create();
                    LoggerUtil.logItem(param);
                    ResponseGetRechargeOperators rechargeOperators = gson.fromJson(param, ResponseGetRechargeOperators.class);

                    if (rechargeOperators.getResponseStatus() == 1) {
                        operatorList = rechargeOperators.getRechargeOperatorsOutput().getOperatorLists();

                        for (int i = 0; i < operatorList.size(); i++) {
                            if (operatorList.get(i).getRechargeType().equalsIgnoreCase("DTH")) {
                                OperatorListsItem item = new OperatorListsItem();
                                item.setOperatorCode(operatorList.get(i).getOperatorCode());
                                item.setCommission(operatorList.get(i).getCommission());
                                item.setRechargeType(operatorList.get(i).getRechargeType());
                                item.setOperatorDescritpion(operatorList.get(i).getOperatorDescritpion());
                                filteredList.add(item);
                            }
                        }

                        OperatorsAdapter adapter = new OperatorsAdapter(DthOperators.this, filteredList);
                        dthOperatorRecyclerView.setAdapter(adapter);

                        dthOperatorRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(DthOperators.this, dthOperatorRecyclerView,
                                new RecyclerTouchListener.ClickListener() {
                                    @Override
                                    public void onClick(View view, int position) {
//                                    Log.e("CODE", "= " + operatorList.get(position).getOperatorCode());
                                        Intent intent = new Intent();
                                        intent.putExtra("DTH_CODE", filteredList.get(position).getOperatorCode());
                                        intent.putExtra("DTH_OPERATOR", filteredList.get(position).getOperatorDescritpion());
                                        setResult(RESULT_OK, intent);
                                        finish();
                                    }

                                    @Override
                                    public void onLongClick(View view, int position) {

                                    }
                                }));
                    } else if (rechargeOperators.getResponseStatus() == 0) {
                        showMessage(getString(R.string.some_error) + " Please Try Again !");
                        setResult(RESULT_CANCELED);
                        finish();
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
