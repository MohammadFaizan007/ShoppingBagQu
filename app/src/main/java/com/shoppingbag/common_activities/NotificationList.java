package com.shoppingbag.common_activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shoppingbag.app.PreferencesManager;
import com.shoppingbag.constants.BaseActivity;
import com.shoppingbag.constants.Cons;
import com.shoppingbag.model.response.notification.ResponseNotification;
import com.shoppingbag.utils.LoggerUtil;
import com.shoppingbag.utils.NetworkUtils;
import com.shoppingbag.R;
import com.shoppingbag.shopping.adapter.NotificationAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationList extends BaseActivity {

    @BindView(R.id.side_menu)
    ImageView sideMenu;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.notiList)
    RecyclerView notiList;
    @BindView(R.id.txtNoNotification)
    TextView txtNoNotification;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_lay);
        ButterKnife.bind(this);

        title.setText("Notification");
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        notiList.setLayoutManager(manager);

        sideMenu.setOnClickListener(v -> onBackPressed());

        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            getNotification();
        } else {
            showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
        }


    }

    //    GetNotificationsList
    private void getNotification() {
        try {
            showLoading();
            JsonObject param = new JsonObject();
            param.addProperty("Fk_MemId", Cons.decryptMsg(PreferencesManager.getInstance(context).getUSERID(), cross_intent));

            Call<JsonObject> notificationCall = apiServices.getGetNotificationsList(bodyParam(param), PreferencesManager.getInstance(context).getANDROIDID());
            notificationCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                    hideLoading();
                    LoggerUtil.logItem(response.body());
                    ResponseNotification response_new;
                    try {
                        if (response.isSuccessful()) {
                            response_new = new Gson().fromJson(Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent), ResponseNotification.class);
//
                            if (response.body() != null && response_new.getResponse().equalsIgnoreCase("Success")) {
                                NotificationAdapter notificationAdapter = new NotificationAdapter(context, response_new.getNotificationList());
                                notiList.setAdapter(notificationAdapter);
                                txtNoNotification.setVisibility(View.GONE);
                            } else {
                                txtNoNotification.setVisibility(View.VISIBLE);
                            }
                        } else {
                            clearPrefrenceforTokenExpiry();
                            getAndroidId();
                            goToActivityWithFinish(context, LoginActivity.class, null);
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
