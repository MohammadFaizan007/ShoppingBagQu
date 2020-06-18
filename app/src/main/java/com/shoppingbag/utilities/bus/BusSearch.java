package com.shoppingbag.utilities.bus;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shoppingbag.constants.BaseActivity;
import com.shoppingbag.constants.Cons;
import com.shoppingbag.model.response.bus_response.GetDestinationResponse;
import com.shoppingbag.model.response.bus_response.ResponseGetSearchBus;
import com.shoppingbag.utils.LoggerUtil;
import com.shoppingbag.utils.NetworkUtils;
import com.shoppingbag.R;
import com.shoppingbag.utilities.bus.adapter.BusListAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;

import java.security.GeneralSecurityException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.shoppingbag.app.AppConfig.PAYLOAD_BUNDLE;

public class BusSearch extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.busList)
    RecyclerView busList;

    Bundle param;
    String originId, destinationId, travelDate, originName, destinationName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bus_search_list);
        ButterKnife.bind(this);

        param = getIntent().getBundleExtra(PAYLOAD_BUNDLE);
        originId = param.getString("originId");
        destinationId = param.getString("destinationId");
        travelDate = param.getString("travelDate");
        originName = param.getString("originName");
        destinationName = param.getString("destinationName");

        title.setText(String.format("%s - %s", originName, destinationName));

        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        busList.setLayoutManager(manager);

        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            getBuses();
        } else {
            createInfoDialog(context, "Alert", getString(R.string.alert_internet));
        }

    }

    private void getBuses() {
        try {
            showLoading();
            JsonObject param = new JsonObject();
            param.addProperty("OriginId", originId);
            param.addProperty("DestinationId", destinationId);
            param.addProperty("TravelDate", travelDate);

            LoggerUtil.logItem(param);

            Call<JsonObject> busCall = apiServicesTravel.getBusSearch(bodyParam(param));
            busCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                    try {
                        hideLoading();
                        LoggerUtil.logItem(response.body());
                        LoggerUtil.logItem(call.request().url());
                        Log.e(">>>", response.toString());
                        LoggerUtil.logItem(response.body());
                        String param_ = Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent);
                        Gson gson = new GsonBuilder().create();
                        ResponseGetSearchBus postPaidresponse = gson.fromJson(param_, ResponseGetSearchBus.class);
                        if (postPaidresponse.getResponseStatus().equalsIgnoreCase("1")) {

                            Bundle param = new Bundle();
                            param.putString("originName", originName);
                            param.putString("destinationName", destinationName);
                            param.putString("TravelDate", travelDate);
                            param.putString("UserTrackId", postPaidresponse.getUserTrackId());

                            BusListAdapter busListAdapter = new BusListAdapter(context, postPaidresponse.getSearchOutput().getAvailableBuses(), param);
                            busList.setAdapter(busListAdapter);

                        } else {
                            showMessage(postPaidresponse.getFailureRemarks());
                            onBackPressed();
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

    @OnClick(R.id.side_menu)
    public void onViewClicked() {
        onBackPressed();


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);

    }
}
