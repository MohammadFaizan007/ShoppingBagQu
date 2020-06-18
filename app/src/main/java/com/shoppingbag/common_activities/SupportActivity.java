package com.shoppingbag.common_activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.shoppingbag.BuildConfig;
import com.shoppingbag.R;
import com.shoppingbag.adapter.SupportAdapter;
import com.shoppingbag.app.PreferencesManager;
import com.shoppingbag.constants.BaseActivity;
import com.shoppingbag.constants.Cons;
import com.shoppingbag.model.support.ResponseGetAllSupport;
import com.shoppingbag.model.support.ResultItem;
import com.shoppingbag.utils.LoggerUtil;
import com.shoppingbag.utils.NetworkUtils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SupportActivity extends BaseActivity {
    @BindView(R.id.side_menu)
    ImageView sideMenu;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.bell_icon)
    ImageView bellIcon;
    @BindView(R.id.support_recycler)
    RecyclerView supportRecycler;
    @BindView(R.id.txtNoSupport)
    TextView txtNoSupport;
    @BindView(R.id.add_request_btn)
    Button addRequestBtn;
    List<ResultItem> resultItems = new ArrayList<>();
    LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.support_layout_activity);
        ButterKnife.bind(this);
        title.setText("Support");
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        supportRecycler.setLayoutManager(layoutManager);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            getAllSupport();
        } else {
            showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
        }

    }

    @OnClick({R.id.side_menu, R.id.add_request_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.side_menu:
              onBackPressed();
                break;
            case R.id.add_request_btn:
                goToActivity(SupportActivity.this,AddRequestSupport.class,null);
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                break;
        }
    }
    private void getAllSupport() {
        try {
            showLoading();
            String url = BuildConfig.BASE_URL_MLM + "GetAllSupport?MemberId=" + PreferencesManager.getInstance(context).getUSERID();
            LoggerUtil.logItem(url);

            Call<JsonObject> notificationCall = apiServices.getBusinessDashboard(url, PreferencesManager.getInstance(context).getANDROIDID());
            notificationCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                    hideLoading();
                    LoggerUtil.logItem("<><"+response.body());
                    try {
                        if (response.isSuccessful()) {
                            String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent);
                            LoggerUtil.logItem(paramResponse);
                            resultItems.clear();
                            ResponseGetAllSupport response_new = new Gson().fromJson(Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent), ResponseGetAllSupport.class);
                            if (response.body() != null && response_new.getResponse().equalsIgnoreCase("Success")) {
                                resultItems.addAll(response_new.getResult());
                                if(resultItems.size()>0){
                                    SupportAdapter notificationAdapter = new SupportAdapter(context, resultItems);
                                    supportRecycler.setAdapter(notificationAdapter);
                                    supportRecycler.setVisibility(View.VISIBLE);
                                    txtNoSupport.setVisibility(View.GONE);
                                }else {
                                    txtNoSupport.setVisibility(View.VISIBLE);
                                    supportRecycler.setVisibility(View.GONE);
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
