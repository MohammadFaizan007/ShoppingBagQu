package com.shoppingbag.mlm.fragments;

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

import com.shoppingbag.app.PreferencesManager;
import com.shoppingbag.constants.BaseFragment;
import com.shoppingbag.constants.Cons;
import com.shoppingbag.model.response.ResponseTicket;
import com.shoppingbag.model.response.SupportTicketsItem;
import com.shoppingbag.retrofit.MvpView;
import com.shoppingbag.utils.LoggerUtil;
import com.shoppingbag.utils.NetworkUtils;
import com.shoppingbag.R;
import com.shoppingbag.common_activities.LoginActivity;
import com.shoppingbag.mlm.adapter.SupportTicketsAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TicketListMLM extends BaseFragment implements MvpView {
    public LinearLayoutManager layoutManager;
    Unbinder unbinder;
    @BindView(R.id.universal_recycler)
    RecyclerView universal_recycler;

    @BindView(R.id.txtNoData)
    TextView txtNoData;
    private List<SupportTicketsItem> items;
    private SupportTicketsAdapter supportTicketsAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ticket_history, container, false);
        unbinder = ButterKnife.bind(this, view);
        items = new ArrayList<>();
        layoutManager = new LinearLayoutManager(getActivity());
        return view;
    }

    @Override
    public void onViewCreatedStuff(View view, @Nullable Bundle savedInstanceState) {
        universal_recycler.setLayoutManager(new LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL, false));
        universal_recycler.setItemAnimator(new DefaultItemAnimator());

        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            getTicketList();
        } else {
            txtNoData.setVisibility(View.VISIBLE);
            txtNoData.setText("No internet connection.");
            createInfoDialog(context, "Alert", getString(R.string.alert_internet));
        }
    }


    public void getTicketList() {
        try {
            JsonObject param = new JsonObject();
            param.addProperty("Fk_MemId", Cons.decryptMsg(PreferencesManager.getInstance(context).getUSERID(), cross_intent));

            showLoading();
            Call<JsonObject> call = apiServices.getTicketCall(bodyParam(param), PreferencesManager.getInstance(context).getANDROIDID());
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                    hideLoading();
                    try {
                        if (response.isSuccessful()) {
                            String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent);
                            ResponseTicket convertedObject = new Gson().fromJson(paramResponse, ResponseTicket.class);
                            LoggerUtil.logItem(convertedObject);

                            if (response.isSuccessful()) {
                                LoggerUtil.logItem(response.body());
                                if (response.body() != null && convertedObject.getResponse().equalsIgnoreCase("Success")) {
                                    items.addAll(convertedObject.getSupportTickets());
                                    supportTicketsAdapter = new SupportTicketsAdapter(context, items);
                                    universal_recycler.setAdapter(supportTicketsAdapter);
                                    txtNoData.setVisibility(View.GONE);
                                } else {
                                    txtNoData.setVisibility(View.VISIBLE);
                                    txtNoData.setText("No data found !");
                                }
                            } else {
                                clearPrefrenceforTokenExpiry();
                                getAndroidId();
                                goToActivityWithFinish(LoginActivity.class, null);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}

