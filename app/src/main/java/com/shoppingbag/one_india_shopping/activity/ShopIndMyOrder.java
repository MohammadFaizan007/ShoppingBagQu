package com.shoppingbag.one_india_shopping.activity;

import android.os.Bundle;
import android.view.View;
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
import com.shoppingbag.one_india_shopping.adapter.IndMyOrdersAdapter;
import com.shoppingbag.one_india_shopping.model.myorder.DataItem;
import com.shoppingbag.one_india_shopping.model.myorder.ResponseMyOrder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShopIndMyOrder extends BaseActivity {
    LinearLayoutManager layoutManager;
    @BindView(R.id.side_menu)
    ImageView sideMenu;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.img_cart)
    ImageView imgCart;
    @BindView(R.id.txt_count)
    TextView txtCount;
    @BindView(R.id.rv_productitems)
    RecyclerView rvProductitems;
    @BindView(R.id.txt_norecordfound)
    TextView txtNorecordfound;
    private IndMyOrdersAdapter adapter;
    List<DataItem> dataItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ind_myorders);
        ButterKnife.bind(this);
        title.setText("My Orders");

        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        rvProductitems.setLayoutManager(layoutManager);


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            getMyOrders();
        } else {
            showMessage(context.getString(R.string.alert_internet));
        }
    }

    private void getMyOrders() {
        showLoading();
        try {

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("user_id", Cons.decryptMsg(PreferencesManager.getInstance(context).getUSERID(),cross_intent));
            jsonObject.addProperty("email",Cons.decryptMsg(PreferencesManager.getInstance(context).getEMAIL(),cross_intent));
//            jsonObject.addProperty("user_id", "1234545667");
//            jsonObject.addProperty("email", "devraj@quaeretech.com");
            LoggerUtil.logItem(jsonObject);
            Call<JsonObject> jsonObjectCall = apiServicesOneStoreIndia.getMyOrders(bodyParamOneStoreIndia(jsonObject));
            jsonObjectCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    hideLoading();
                    LoggerUtil.logItem(response.body());
                    LoggerUtil.logItem(response.code());
                    try {
                        if (response.isSuccessful()) {
                            String param = Cons.decryptMsg(response.body().get("body").getAsString(), easy_pay_oneStoreIndia);
                            Gson gson = new GsonBuilder().create();
                            LoggerUtil.logItem(param);
                            ResponseMyOrder responseMyOrder = gson.fromJson(param, ResponseMyOrder.class);
                            if (responseMyOrder.getMessage().equalsIgnoreCase("success")) {
                                txtNorecordfound.setVisibility(View.GONE);
                                rvProductitems.setVisibility(View.VISIBLE);
                                dataItems = new ArrayList<>();
                                dataItems = responseMyOrder.getData();
                                adapter = new IndMyOrdersAdapter(context, dataItems, ShopIndMyOrder.this);
                                rvProductitems.setAdapter(adapter);
                            } else {
                                rvProductitems.setVisibility(View.GONE);
                                txtNorecordfound.setVisibility(View.VISIBLE);

                            }

                        } else {
                            showMessage("Something went wrong");
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void getMyClickPosition(String name, String tag) {
        super.getMyClickPosition(name, tag);
        Bundle bundle = new Bundle();
        bundle.putString("order_id", name);
        goToActivity(ShopIndMyOrder.this, ShopIndOrderDetail.class, bundle);
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
}



