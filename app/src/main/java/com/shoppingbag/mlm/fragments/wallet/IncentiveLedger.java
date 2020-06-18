package com.shoppingbag.mlm.fragments.wallet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shoppingbag.R;
import com.shoppingbag.common_activities.LoginActivity;
import com.shoppingbag.mlm.adapter.IncentiveLedgerAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import com.shoppingbag.app.PreferencesManager;
import com.shoppingbag.constants.BaseFragment;
import com.shoppingbag.constants.Cons;
import com.shoppingbag.model.response.wallet.ResponseIncentiveLedger;
import com.shoppingbag.utils.LoggerUtil;
import com.shoppingbag.utils.NetworkUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IncentiveLedger extends BaseFragment {
    @BindView(R.id.available_balance)
    TextView availableBalance;
    @BindView(R.id.avail_lo)
    LinearLayout availLo;
    @BindView(R.id.wallet_ledger_recycler)
    RecyclerView walletLedgerRecycler;
    @BindView(R.id.noRecFound)
    RelativeLayout noRecFound;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.wallet_ledger, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreatedStuff(View view, @Nullable Bundle savedInstanceState) {
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        walletLedgerRecycler.setLayoutManager(manager);

        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            getWalletLedger();
        } else {
            showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
        }
    }


    private void getWalletLedger() {
        try {
            JsonObject param = new JsonObject();
            param.addProperty("Fk_MemId", Cons.decryptMsg(PreferencesManager.getInstance(context).getUSERID(), cross_intent));
            showLoading();
            LoggerUtil.logItem(param);
            Call<JsonObject> directCall = apiServices.getIncentiveLedger(bodyParam(param), PreferencesManager.getInstance(context).getANDROIDID());
            directCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                    hideLoading();
                    try {
                        if (response.isSuccessful()) {
                            String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent);
                            ResponseIncentiveLedger convertedObject = new Gson().fromJson(paramResponse, ResponseIncentiveLedger.class);
                            LoggerUtil.logItem(convertedObject);

                            LoggerUtil.logItem(response.body());
                            if (convertedObject.getResponse().equalsIgnoreCase("Success")) {
                                IncentiveLedgerAdapter adapter = new IncentiveLedgerAdapter(context, convertedObject.getPayoutRequestlist());
                                walletLedgerRecycler.setAdapter(adapter);
                                walletLedgerRecycler.setVisibility(View.VISIBLE);
                                noRecFound.setVisibility(View.GONE);
                            } else {
                                walletLedgerRecycler.setVisibility(View.GONE);
                                noRecFound.setVisibility(View.VISIBLE);
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
