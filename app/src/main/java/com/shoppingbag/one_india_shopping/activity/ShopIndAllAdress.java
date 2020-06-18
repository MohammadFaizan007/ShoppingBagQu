package com.shoppingbag.one_india_shopping.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shoppingbag.app.PreferencesManager;
import com.shoppingbag.constants.BaseActivity;
import com.shoppingbag.constants.Cons;
import com.shoppingbag.utils.LoggerUtil;
import com.shoppingbag.utils.NetworkUtils;
import com.shoppingbag.R;
import com.shoppingbag.one_india_shopping.adapter.IndAddressAdapter;
import com.shoppingbag.one_india_shopping.model.addaddress.ResponseRemoveAddress;
import com.shoppingbag.one_india_shopping.model.address.DataItem;
import com.shoppingbag.one_india_shopping.model.address.ResponseGetAddress;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;

import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShopIndAllAdress extends BaseActivity {

    @BindView(R.id.side_menu)
    ImageView sideMenu;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.img_cart)
    ImageView imgCart;
    @BindView(R.id.txt_count)
    TextView txtCount;
    @BindView(R.id.rv_addressList)
    RecyclerView rvAddressitems;
    @BindView(R.id.txt_norecordfound)
    TextView txtNorecordfound;
    LinearLayoutManager layoutManager;
    @BindView(R.id.btn_addnewAdress)
    Button btnAddnewAdress;
    private IndAddressAdapter indAddressAdapter;
    private List<DataItem> list = new ArrayList<>();
    int REQUEST_CODE = 900;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ind_addresslist);
        ButterKnife.bind(this);
        title.setText("Choose Address");
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        rvAddressitems.setLayoutManager(layoutManager);


    }


    @Override
    protected void onResume() {
        super.onResume();
        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            getAddressList();
        } else {
            showMessage(context.getString(R.string.alert_internet));
        }
    }

    public void getAddressList() {
        showLoading();
        try {

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("customer_id", Cons.decryptMsg(PreferencesManager.getInstance(context).getUSERID(), cross_intent));
            LoggerUtil.logItem(jsonObject);

            Call<JsonObject> jsonObjectCall = apiServicesOneStoreIndia.getAddressList(bodyParamOneStoreIndia(jsonObject));
            jsonObjectCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                    hideLoading();
                    LoggerUtil.logItem(response.body());
                    LoggerUtil.logItem(response.code());
                    try {
                        if (response.isSuccessful()) {
                            String param = Cons.decryptMsg(response.body().get("body").getAsString(), easy_pay_oneStoreIndia);
                            Gson gson = new GsonBuilder().create();
                            LoggerUtil.logItem(param);
                            ResponseGetAddress getAddress = gson.fromJson(param, ResponseGetAddress.class);
                            if (getAddress.getResponse().equalsIgnoreCase("success") && getAddress.getData().size() > 0) {

                                rvAddressitems.setVisibility(View.VISIBLE);
                                txtNorecordfound.setVisibility(View.GONE);
                                list = getAddress.getData();

                                indAddressAdapter = new IndAddressAdapter(context, list, ShopIndAllAdress.this);
                                rvAddressitems.setAdapter(indAddressAdapter);

                            } else {
                                txtNorecordfound.setVisibility(View.VISIBLE);
                                rvAddressitems.setVisibility(View.GONE);
                            }


                        } else {
                            txtNorecordfound.setVisibility(View.VISIBLE);
                            rvAddressitems.setVisibility(View.GONE);
                            showMessage("something went wrong");
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(@NotNull Call<JsonObject> call, @NotNull Throwable t) {

                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //Todo remove address method
    public void getRemoveAddress(String addressId) {
        showLoading();
        JsonObject jsonObject = new JsonObject();
        try {
            jsonObject.addProperty("customer_id", Cons.decryptMsg(PreferencesManager.getInstance(context).getUSERID(), cross_intent));
            jsonObject.addProperty("address_id", addressId);
            LoggerUtil.logItem(jsonObject);
            Call<JsonObject> jsonObjectCall = apiServicesOneStoreIndia.getRemoveAddress(bodyParamOneStoreIndia(jsonObject));
            jsonObjectCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                    hideLoading();
                    LoggerUtil.logItem(response.body());
                    LoggerUtil.logItem(response.code());
                    try {
                        if (response.isSuccessful()) {
                            String param = Cons.decryptMsg(response.body().get("body").getAsString(), easy_pay_oneStoreIndia);
                            Gson gson = new GsonBuilder().create();
                            LoggerUtil.logItem(param);
                            ResponseRemoveAddress removeAddress = gson.fromJson(param, ResponseRemoveAddress.class);
                            if (removeAddress.getResponse().equalsIgnoreCase("success")) {
                                showMessage(removeAddress.getMessage());
                                getAddressList();
                            } else {
                                showMessage(removeAddress.getMessage());
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

                }
            });
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void getClickChildPosition(String name, String tag, Bundle bundle) {
        super.getClickChildPosition(name, tag, bundle);
        if (tag.equalsIgnoreCase("Edit")) {
            bundle.putString("name", name);
            bundle.putString("tag", tag);
            goToActivity(ShopIndAllAdress.this, ShopIndAddressActivity.class, bundle);

        } else if (tag.equalsIgnoreCase("Remove")) {
            getRemoveAddress(name);
        } else if (tag.equalsIgnoreCase("Selected")) {
            Intent intent = new Intent();
            intent.putExtra("addressid", name);
            setResult(REQUEST_CODE, intent);
            finish();
        }

    }

    @OnClick(R.id.side_menu)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.side_menu:
                finish();
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                break;
        }
    }

    @OnClick(R.id.btn_addnewAdress)
    public void onViewClicked() {
        Bundle bundle = new Bundle();
        bundle.putString("tag", "newAddress");
        goToActivity(ShopIndAllAdress.this, ShopIndAddressActivity.class, bundle);

    }
}
