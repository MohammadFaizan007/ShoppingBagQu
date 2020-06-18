package com.shoppingbag.shopping.fragment.myorders_all;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.shoppingbag.BuildConfig;
import com.shoppingbag.R;
import com.shoppingbag.app.PreferencesManager;
import com.shoppingbag.common_activities.LoginActivity;
import com.shoppingbag.constants.BaseFragment;
import com.shoppingbag.constants.Cons;
import com.shoppingbag.model.response.shopping.LstINRItem;
import com.shoppingbag.model.response.shopping.ResponseINRDeailsOrders;
import com.shoppingbag.shopping.adapter.ShoppingOtherOrderAdapter;
import com.shoppingbag.utils.HidingScrollListener;
import com.shoppingbag.utils.LoggerUtil;
import com.shoppingbag.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RechargeBillOrders extends BaseFragment {

    public LinearLayoutManager layoutManager;
    Unbinder unbinder;
    @BindView(R.id.rv_orderlist)
    RecyclerView rvorderlist;
    @BindView(R.id.noRecord)
    TextView noRecord;
    private int pageNo = 1;
    private List<LstINRItem> items;
    private ShoppingOtherOrderAdapter shoppingOtherOrderAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.orderlist, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onViewCreatedStuff(View view, @Nullable Bundle savedInstanceState) {
        items = new ArrayList<>();
        layoutManager = new LinearLayoutManager(getActivity());
        rvorderlist.setLayoutManager(layoutManager);
        rvorderlist.setItemAnimator(new DefaultItemAnimator());


        rvorderlist.addOnScrollListener(new HidingScrollListener(layoutManager) {
            @Override
            public void onHide() {
            }

            @Override
            public void onLoadMore(int i) {
                //Log.e(">>>> ", "you have reached to the bottom = " + pageNo + "" + i);
                if (NetworkUtils.getConnectivityStatus(getActivity()) != 0) {
                    pageNo = i;
                    //Log.e(">>>> ", "you have reached to the bottom = " + pageNo);
                    getOtherOrders();
                }
            }

            @Override
            public void onShow() {
            }
        });

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isResumed()) {
            if (NetworkUtils.getConnectivityStatus(context) != 0) {
                getOtherOrders();
            } else {
                showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
            }

        }
    }


    private void getOtherOrders() {
        try {
            showLoading();
            String url = BuildConfig.BASE_URL_INR + "GetINRDealsTransaction?LoginId=" + PreferencesManager.getInstance(context).getUSERID()
                    + "&Page=" + Cons.encryptMsg(pageNo + "", cross_intent) + "&StoreName=" + Cons.encryptMsg("Recharge", cross_intent);

            Call<JsonObject> call = apiServices.getOtherOrders(url, PreferencesManager.getInstance(context).getANDROIDID());
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                    hideLoading();
                    LoggerUtil.logItem("RECHARGE- " + response.body().toString());
                    try {
                        if (response.isSuccessful()) {
                            ResponseINRDeailsOrders response_new = new Gson().fromJson(Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent), ResponseINRDeailsOrders.class);
                            if (response_new.getResponse().equalsIgnoreCase("Success")) {
                                if (pageNo == 1) {
                                    items.addAll(response_new.getLstINR());
                                    shoppingOtherOrderAdapter = new ShoppingOtherOrderAdapter(context, items);
                                    rvorderlist.setAdapter(shoppingOtherOrderAdapter);
                                    //Log.e("SIZEEEE AA- ", "" + items.size());
                                } else {
                                    if (response_new.getResponse().equalsIgnoreCase("Success")) {
                                        items.addAll(response_new.getLstINR());
                                        shoppingOtherOrderAdapter.notifyItemRangeChanged(0, shoppingOtherOrderAdapter.getItemCount());
                                        //Log.e("SIZEEEE BB- ", "" + items.size());
                                    }
                                }
                            } else {
                                if (pageNo == 1) {
                                    noRecord.setVisibility(View.VISIBLE);
                                    rvorderlist.setVisibility(View.GONE);
                                }
                            }
                        } else {
                            clearPrefrenceforTokenExpiry();
                            getAndroidId();
                            goToActivityWithFinish(LoginActivity.class, null);
                        }
                    } catch (Error | Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                    noRecord.setVisibility(View.VISIBLE);
                    hideLoading();
                    LoggerUtil.logItem(t.getMessage());
                }
            });
        } catch (Error | Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        pageNo = 1;
        unbinder.unbind();
    }

}