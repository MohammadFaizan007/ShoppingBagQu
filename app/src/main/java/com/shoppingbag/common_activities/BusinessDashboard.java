package com.shoppingbag.common_activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.shoppingbag.BuildConfig;
import com.shoppingbag.R;
import com.shoppingbag.adapter.RecentOrdersAdapter;
import com.shoppingbag.app.PreferencesManager;
import com.shoppingbag.constants.BaseFragment;
import com.shoppingbag.constants.Cons;
import com.shoppingbag.model.business_dash_response.OrderItem;
import com.shoppingbag.model.business_dash_response.ResponseBusinessDashboard;
import com.shoppingbag.utils.LoggerUtil;
import com.shoppingbag.utils.NetworkUtils;
import com.shoppingbag.wallet.CashBackWallet;
import com.shoppingbag.wallet.CommissionWallet;
import com.shoppingbag.wallet.DreamyWallet;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BusinessDashboard extends BaseFragment {

    @BindView(R.id.all_busi_txt)
    TextView all_busi_txt;
    @BindView(R.id.today_business_txt)
    TextView today_business_txt;
    @BindView(R.id.team_business_txt)
    TextView team_business_txt;
    @BindView(R.id.teamsize_txt)
    TextView teamsize_txt;
    @BindView(R.id.all_txt)
    TextView all_txt;
    @BindView(R.id.today_txt)
    TextView today_txt;
    @BindView(R.id.magic_txt)
    TextView magic_txt;
    @BindView(R.id.lifetime_txt)
    TextView lifetime_txt;
    @BindView(R.id.ref_income_txt)
    TextView ref_income_txt;
    @BindView(R.id.self_txt)
    TextView self_txt;
    @BindView(R.id.team_txt)
    TextView team_txt;
    @BindView(R.id.text_dreamy_amt)
    TextView text_dreamy_amt;
    @BindView(R.id.text_cashback_amt)
    TextView text_cashback_amt;
    @BindView(R.id.text_comm_amt)
    TextView text_comm_amt;
    @BindView(R.id.textViewNetIncome)
    TextView textViewNetIncome;
    @BindView(R.id.service_chg_txt)
    TextView service_chg_txt;
    @BindView(R.id.gross_txt)
    TextView gross_txt;
    @BindView(R.id.textView25)
    TextView viewCashback;
    @BindView(R.id.textView23)
    TextView viewDreamy;
    @BindView(R.id.textView27)
    TextView viewCommission;
    @BindView(R.id.tds_txt)
    TextView tds_txt;
    @BindView(R.id.rep_income_txt)
    TextView rep_income_txt;
    @BindView(R.id.txt_norecordfound)
    TextView txt_norecordfound;
    @BindView(R.id.order_recycler)
    RecyclerView order_recycler;
    List<OrderItem> orderItems = new ArrayList<>();
    Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.business_dashboard, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreatedStuff(View view, @Nullable Bundle savedInstanceState) {
       // ((MainContainer) context).title_tv.setText("Business Dashboard");
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        order_recycler.setLayoutManager(manager);
        viewDreamy.setOnClickListener(view1 -> goToActivity(DreamyWallet.class, null));
        viewCashback.setOnClickListener(view1 -> goToActivity( CashBackWallet.class, null));
        viewCommission.setOnClickListener(view1 -> goToActivity( CommissionWallet.class, null));

        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            getDashboard();
        } else {
            showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
        }}

    //    GetNotificationsList
    //http://webapi.dreamydroshky.in/api/Web/Dashboard?Id=2pXDeTUrR1oBkLxlu6oSow==
    private void getDashboard() {
        try {
            showLoading();
            String url = BuildConfig.BASE_URL_MLM + "DashboardV2?Id=" + PreferencesManager.getInstance(context).getUSERID();
            LoggerUtil.logItem(url);

            Call<JsonObject> notificationCall = apiServices.getBusinessDashboard(url, PreferencesManager.getInstance(context).getANDROIDID());
            notificationCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                    hideLoading();
                    LoggerUtil.logItem("<><"+response.body());
                    ResponseBusinessDashboard response_new;
                    try {
                        if (response.isSuccessful()) {
                            String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent);
                            LoggerUtil.logItem(paramResponse);
                            response_new = new Gson().fromJson(Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent), ResponseBusinessDashboard.class);
                            LoggerUtil.logItem("<><"+response_new.toString());

                            if (response.body() != null && response_new.getResponse().equalsIgnoreCase("Success")) {
                                orderItems.addAll(response_new.getOrder());
                                all_busi_txt.setText(String.valueOf(response_new.getDashboardDetails().getAllBusiness()));
                                today_business_txt.setText(String.valueOf(response_new.getDashboardDetails().getTodayBusiness()));
                                team_business_txt.setText(String.valueOf(response_new.getDashboardDetails().getTeamsize()));
                                teamsize_txt.setText(String.valueOf(response_new.getDashboardDetails().getDirectCount()));
                                all_txt.setText(String.valueOf(response_new.getDashboardDetails().getAllOrders()));


                                service_chg_txt.setText(String.format("₹ %s", String.valueOf(response_new.getDashboardDetails().getServiceCharges())));
                                gross_txt.setText(String.format("₹ %s", String.valueOf(response_new.getDashboardDetails().getGross())));
                                tds_txt.setText(String.format("₹ %s", String.valueOf(response_new.getDashboardDetails().getTDS())));
                                textViewNetIncome.setText(String.format("₹ %s", String.valueOf(response_new.getDashboardDetails().getNetIncome())));


                                today_txt.setText(String.valueOf(response_new.getDashboardDetails().getTodayBusiness()));
                                magic_txt.setText(String.format("₹ %s", String.valueOf(response_new.getDashboardDetails().getMagixMoves())));
                                lifetime_txt.setText(String.format("₹ %s", String.valueOf(response_new.getDashboardDetails().getLifeTimeIncome())));
                                ref_income_txt.setText(String.format("₹ %s", String.valueOf(response_new.getDashboardDetails().getReferralIncome())));
                                self_txt.setText(String.format("₹ %s", String.valueOf(response_new.getDashboardDetails().getRepurchaseIncomeSelf())));
                                team_txt.setText(String.format("₹ %s", String.valueOf(response_new.getDashboardDetails().getRepurchaseIncomeTeam())));
                                float repIncome = response_new.getDashboardDetails().getRepurchaseIncomeTeam()+
                                        response_new.getDashboardDetails().getRepurchaseIncomeSelf();
                                rep_income_txt.setText(String.format("₹ %s", String.valueOf(repIncome)));
                                text_dreamy_amt.setText(String.valueOf(response_new.getDashboardDetails().getDreamyWallet()));
                                text_cashback_amt.setText(String.valueOf(response_new.getDashboardDetails().getCashbackWallet()));
                                text_comm_amt.setText(String.valueOf(response_new.getDashboardDetails().getCommissionWallet()));
                                if(orderItems.size()>0){
                                    RecentOrdersAdapter notificationAdapter = new RecentOrdersAdapter(context, orderItems);
                                    order_recycler.setAdapter(notificationAdapter);
                                    order_recycler.setVisibility(View.VISIBLE);
                                    txt_norecordfound.setVisibility(View.GONE);
                                }else {
                                    txt_norecordfound.setVisibility(View.VISIBLE);
                                    order_recycler.setVisibility(View.GONE);
                                }

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
        }

    }

}
