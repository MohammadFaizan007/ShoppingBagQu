package com.shoppingbag.common_activities;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.shoppingbag.BuildConfig;
import com.shoppingbag.R;
import com.shoppingbag.app.PreferencesManager;
import com.shoppingbag.constants.BaseFragment;
import com.shoppingbag.constants.Cons;
import com.shoppingbag.model.referral.ResponseTodayReferral;
import com.shoppingbag.shopping.adapter.ReferralAdapter;
import com.shoppingbag.utils.LoggerUtil;
import com.shoppingbag.utils.NetworkUtils;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReferralActivity extends BaseFragment {
    @BindView(R.id.referralList)
    RecyclerView referralList;
    @BindView(R.id.txtNoReferral)
    TextView txtNoReferral;
    Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_referral, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreatedStuff(View view, @Nullable Bundle savedInstanceState) {

      //  ((MainContainer) context).title_tv.setText("Today's top 10 referrals");
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        referralList.setLayoutManager(manager);

        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            getBankDetails();
        } else {
            showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
        }
    }

    //    GetNotificationsList
    private void getNotification() {
        try {
            showLoading();
            String url = BuildConfig.BASE_URL_MLM + "TodayReferal";
            LoggerUtil.logItem(url);
            Call<JsonObject> notificationCall = apiServices.getTodayReferral(url, PreferencesManager.getInstance(context).getANDROIDID());
            notificationCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                    hideLoading();
                    LoggerUtil.logItem(response.body());
                    Log.e(">>response", response.toString());
                    LoggerUtil.logItem(call.request().url());
                    ResponseTodayReferral response_new;
                    try {
                        if (response.isSuccessful()) {
                            response_new = new Gson().fromJson(Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent), ResponseTodayReferral.class);
//
                            if (response.body() != null && response_new.getResponse().equalsIgnoreCase("Success")) {
                                ReferralAdapter notificationAdapter = new ReferralAdapter(context, response_new.getData());
                                referralList.setAdapter(notificationAdapter);
                                txtNoReferral.setVisibility(View.GONE);
                            } else {
                                txtNoReferral.setVisibility(View.VISIBLE);
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
                public void onFailure(@NotNull Call<JsonObject> call, @NotNull Throwable t) {
                    hideLoading();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void getBankDetails() {
        showLoading();
        String url = BuildConfig.BASE_URL_MLM + "TodayReferal";
        LoggerUtil.logItem(url);
        Call<JsonObject> jsonObjectCall = apiServices.getTodayReferral(url, PreferencesManager.getInstance(context).getANDROIDID());
        jsonObjectCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                hideLoading();
                LoggerUtil.logItem(response.body());
                LoggerUtil.logItem(response.code());
                try {

                    if (response.isSuccessful()) {
                        String param = Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent);
                        LoggerUtil.logItem(call.request().url());
                        LoggerUtil.logItem(param);
                        Gson gson = new GsonBuilder().create();
                        ResponseTodayReferral bankDetails = gson.fromJson(param, ResponseTodayReferral.class);
                        if (bankDetails.getResponse().equalsIgnoreCase("success")) {
                            ReferralAdapter notificationAdapter = new ReferralAdapter(context, bankDetails.getData());
                            referralList.setAdapter(notificationAdapter);
                            txtNoReferral.setVisibility(View.GONE);
                        } else {
                            txtNoReferral.setVisibility(View.VISIBLE);
                        }

                    } else {
                        hideLoading();
                        showMessage("Something went wrong");
                    }

                } catch (Exception e) {
                    hideLoading();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                hideLoading();
                Log.d("TAG", "onFailure: " + t.getMessage());
            }
        });
    }

}
