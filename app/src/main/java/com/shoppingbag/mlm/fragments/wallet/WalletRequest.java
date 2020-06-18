package com.shoppingbag.mlm.fragments.wallet;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shoppingbag.app.PreferencesManager;
import com.shoppingbag.constants.BaseFragment;
import com.shoppingbag.constants.Cons;
import com.shoppingbag.model.response.wallet.ResponseWalletRequest;
import com.shoppingbag.utils.LoggerUtil;
import com.shoppingbag.utils.NetworkUtils;
import com.shoppingbag.R;
import com.shoppingbag.common_activities.LoginActivity;
import com.shoppingbag.mlm.adapter.WalletRequestAdapter;
import com.shoppingbag.wallet.AddMoney;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WalletRequest extends BaseFragment {

    @BindView(R.id.btn_newRequest)
    Button btnNewRequest;

    @BindView(R.id.etSearch)
    EditText etSearch;
    @BindView(R.id.walletRequestRecycler)
    RecyclerView walletRequestRecycler;
    Unbinder unbinder;
    WalletRequestAdapter adapter;
    @BindView(R.id.txtNoRequest)
    TextView txtNoRequest;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.wallet_request, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreatedStuff(View view, @Nullable Bundle savedInstanceState) {
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        walletRequestRecycler.setLayoutManager(manager);

        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            getWalletRequest();
        } else {
            showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
        }
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (adapter != null)
                    adapter.getFilter().filter(s);
            }
        });
    }


    private void getWalletRequest() {
        try {
            JsonObject param = new JsonObject();
            param.addProperty("Fk_MemId", Cons.decryptMsg(PreferencesManager.getInstance(context).getUSERID(), cross_intent));
            showLoading();
            LoggerUtil.logItem(param);
            Call<JsonObject> directCall = apiServices.getEWalletRequest(bodyParam(param), PreferencesManager.getInstance(context).getANDROIDID());

            directCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                    hideLoading();
                    LoggerUtil.logItem(response.body());
                    try {
                        if (response.isSuccessful()) {
                            String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent);
                            ResponseWalletRequest convertedObject = new Gson().fromJson(paramResponse, ResponseWalletRequest.class);
                            LoggerUtil.logItem(convertedObject);


                            if (convertedObject.getResponse().equalsIgnoreCase("Success")) {
                                adapter = new WalletRequestAdapter(context, convertedObject.getEWalletRequestlist());
                                walletRequestRecycler.setAdapter(adapter);
                                walletRequestRecycler.setVisibility(View.VISIBLE);
                                txtNoRequest.setVisibility(View.GONE);
                            } else {
                                walletRequestRecycler.setVisibility(View.GONE);
                                txtNoRequest.setVisibility(View.VISIBLE);
                            }
                        } else {
                            clearPrefrenceforTokenExpiry();
                            getAndroidId();
                            goToActivityWithFinish(LoginActivity.class, null);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        txtNoRequest.setVisibility(View.VISIBLE);
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

    @OnClick(R.id.btn_newRequest)
    public void onViewClicked() {
        goToActivityWithFinish(AddMoney.class, null);
    }

}
