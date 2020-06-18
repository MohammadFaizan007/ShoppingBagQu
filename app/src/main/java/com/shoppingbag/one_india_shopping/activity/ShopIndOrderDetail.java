package com.shoppingbag.one_india_shopping.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.shoppingbag.BuildConfig;
import com.shoppingbag.R;
import com.shoppingbag.app.AppConfig;
import com.shoppingbag.app.PreferencesManager;
import com.shoppingbag.constants.BaseActivity;
import com.shoppingbag.constants.Cons;
import com.shoppingbag.one_india_shopping.adapter.OrderDetailsAdapter;
import com.shoppingbag.one_india_shopping.model.cancelOrder.ResponseCancelOrder;
import com.shoppingbag.one_india_shopping.model.myorderdetails.ResponseMyOrderDetails;
import com.shoppingbag.one_india_shopping.model.tracking_login.TrackingModel;
import com.shoppingbag.utils.LoggerUtil;
import com.shoppingbag.utils.NetworkUtils;
import com.shoppingbag.utils.Utils;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShopIndOrderDetail extends BaseActivity {
    @BindView(R.id.side_menu)
    ImageView sideMenu;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.img_cart)
    ImageView imgCart;
    @BindView(R.id.txt_count)
    TextView txtCount;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_address1)
    TextView tvAddress1;
    @BindView(R.id.tv_address2)
    TextView tvAddress2;
    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.tv_state_pinCode)
    TextView tvStatePinCode;
    @BindView(R.id.tv_phoneNo)
    TextView tvPhoneNo;
    @BindView(R.id.shipping_detailCardView)
    CardView shippingDetailCardView;
    @BindView(R.id.card)
    CardView cardtext;
    @BindView(R.id.text_order_status)
    TextView text_order_status;
    @BindView(R.id.text_note)
    TextView text_note;
    @BindView(R.id.tv_price_detail)
    TextView tvPriceDetail;
    @BindView(R.id.tv_discountedPrice)
    TextView tvDiscountedPrice;
    @BindView(R.id.tv_total_price)
    TextView tvTotalPrice;
    @BindView(R.id.price_detail_cardview)
    CardView priceDetailCardview;
    @BindView(R.id.txt_email)
    TextView txtEmail;
    @BindView(R.id.textView62)
    TextView textView62;
    @BindView(R.id.textView24)
    TextView textView24;
    @BindView(R.id.view12)
    View view12;
    @BindView(R.id.textView54)
    TextView textView54;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.textView57)
    TextView textView57;
    @BindView(R.id.view13)
    View view13;
    @BindView(R.id.textView58)
    TextView textView58;
    @BindView(R.id.textView64)
    TextView textView64;
    @BindView(R.id.btn_cancelorder)
    Button btnCancelorder;
    @BindView(R.id.scroll)
    ScrollView scroll;
    private Bundle bundle;
    @BindView(R.id.rv_productitems)
    RecyclerView rvProductitems;
    LinearLayoutManager layoutManager;
    OrderDetailsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ind_order_detail);
        ButterKnife.bind(this);
        title.setText("My Order Details");
        bundle = getIntent().getBundleExtra(AppConfig.PAYLOAD_BUNDLE);
        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            getOrderDetails(bundle.getString("order_id"));
        } else {
            showMessage(context.getString(R.string.alert_internet));
        }
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        rvProductitems.setLayoutManager(layoutManager);

//        if (!PreferencesManager.getInstance(context).gettracking_bool()) {
//            if (NetworkUtils.getConnectivityStatus(context) != 0) {
//                userTrackingLogin();
//            } else {
//                showMessage(context.getString(R.string.alert_internet));
//            }
//        } else {
//            trackOrderDetail();kjhtgfhjukitygre#wo*ip()i&*ur$#e@w ``````````````````````````````````````````
//        }
    }


    private void getOrderDetails(String id) {
        showLoading();
        try {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("order_id", id);
            LoggerUtil.logItem(jsonObject);
            Call<JsonObject> jsonObjectCall = apiServicesOneStoreIndia.getMyOrderbyDetails(bodyParamOneStoreIndia(jsonObject));
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
                            ResponseMyOrderDetails orderDetails = gson.fromJson(param, ResponseMyOrderDetails.class);
                            if (orderDetails.getResponse().equalsIgnoreCase("success")) {
                                adapter = new OrderDetailsAdapter(context, orderDetails.getData(), ShopIndOrderDetail.this);
                                rvProductitems.setAdapter(adapter);
                                int i = 0;
                                if (orderDetails.getData().size() > 0) {

                                    tvAddress1.setText(orderDetails.getData().get(i).getStreet());
                                    tvAddress2.setText(orderDetails.getData().get(i).getName());
                                    tvCity.setText(orderDetails.getData().get(i).getCity());
                                    tvStatePinCode.setText(String.format("%s, PinCode : %s", orderDetails.getData().get(i).getState(), orderDetails.getData().get(i).getPostcode()));
                                    tvPhoneNo.setText(String.format(": %s", orderDetails.getData().get(i).getMobile()));
//                                    tvTotalPrice.setText(String.format(": ₹ %s", String.valueOf(Double.parseDouble(orderDetails.getData().get(i).getPrice()))));
                                    double totl = Double.parseDouble(orderDetails.getData().get(i).getPrice()) + Double.parseDouble(orderDetails.getData().get(i).getDiscountAmount());
                                    tvTotalPrice.setText(String.format("₹ %s", Utils.getValue(totl)));
                                    tvPriceDetail.setText(String.format("₹ %s", String.valueOf(Double.parseDouble(orderDetails.getData().get(i).getPrice()))));
                                    tvDiscountedPrice.setText(String.format("₹ %s", Utils.getValue(Double.parseDouble(orderDetails.getData().get(i).getDiscountAmount()))));
                                    tvName.setText(orderDetails.getData().get(i).getFirstname());
                                    txtEmail.setText(String.format(": %s", orderDetails.getData().get(i).getEmail()));
                                    if (orderDetails.getData().get(i).getStatus().equalsIgnoreCase("Approved")) {
                                        btnCancelorder.setVisibility(View.GONE);
                                        // If approved than card visible
                                        cardtext.setVisibility(View.VISIBLE);
                                        text_note.setText("** Your product will be delivered with in 15 to 20 days. ");
                                    } else if (orderDetails.getData().get(i).getStatus().equalsIgnoreCase("Cancelled")) {
                                        btnCancelorder.setVisibility(View.GONE);
                                    } else {
                                        btnCancelorder.setVisibility(View.GONE);
                                        cardtext.setVisibility(View.VISIBLE);
                                        //text_order_status.setText("** Your order status is Pending");
                                        text_order_status.setText("** Your order status is Success");
                                    }
                                    if(orderDetails.getData().get(i).getStatus().equalsIgnoreCase("pending")){
                                        text_note.setText("** Your product will be delivered with in 15 to 20 days. ");
                                        cardtext.setVisibility(View.VISIBLE);
                                    }
//                                    tvProductName.setText(orderDetails.getData().get(i).getName());
//                                    break;
                                } else {
                                    showMessage("data not available");
                                    btnCancelorder.setVisibility(View.GONE);
                                    scroll.setVisibility(View.GONE);
                                }
                            } else {
                                showMessage("data not available");
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


    private void getCancelOrder(String id) {
        showLoading();
        try {

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("order_id", id);
            LoggerUtil.logItem(jsonObject);
            Call<JsonObject> jsonObjectCall = apiServicesOneStoreIndia.getCancelOrder(bodyParamOneStoreIndia(jsonObject));
            jsonObjectCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    hideLoading();
                    try {
                        LoggerUtil.logItem(response.body());
                        LoggerUtil.logItem(response.code());
                        if (response.isSuccessful()) {
                            String param = Cons.decryptMsg(response.body().get("body").getAsString(), easy_pay_oneStoreIndia);
                            Gson gson = new GsonBuilder().create();
                            LoggerUtil.logItem(param);
                            ResponseCancelOrder cancelOrder = gson.fromJson(param, ResponseCancelOrder.class);
                            if (cancelOrder.getResponse().equalsIgnoreCase("success")) {
                                showMessage(cancelOrder.getMessage());
                                finish();
                                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                            } else {
                                showMessage(cancelOrder.getMessage());
                            }
                        } else {
                            showMessage("something went wrong");
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

//    private void userTrackingLogin() {
//        try {
//            String url = BuildConfig.BASE_URL_LOGIN + "auth/login?email=" + BuildConfig.TRACKING_EMAIL_ID + "&password=" + BuildConfig.TRACKING_PASSWORD;
//            Call<JsonObject> jsonObjectCall = apiLogin.trakingLogin(url);
//            jsonObjectCall.enqueue(new Callback<JsonObject>() {
//                @Override
//                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//                    hideLoading();
//                    try {
//                        LoggerUtil.logItem(response.body());
//                        LoggerUtil.logItem(response.code());
//                        if (response.isSuccessful()) {
//                            PreferencesManager.getInstance(context).settracking_bool(true);
//                            // String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent);
//                            //  LoggerUtil.logItem(paramResponse);
//
//
//                            Gson gson = new GsonBuilder().create();
//                            TrackingModel trackingModel = gson.fromJson(response.body(), TrackingModel.class);
//                            PreferencesManager.getInstance(context).setTrackingToken(trackingModel.getToken());
//                            Log.d("tokennnnnnnnn", "onResponse: " + trackingModel.getToken());
//                            trackOrderDetail();
//                        }
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<JsonObject> call, Throwable t) {
//
//                }
//            });
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    private void trackOrderDetail() {
//        try {
//            String url = BuildConfig.BASE_URL_LOGIN + "courier/track?order_id=000000" + bundle.getString("order_id") + "&channel_id=" + BuildConfig.CHANNEL_ID;
//            Log.d(">>>>>>>>>>>>>>", "trackOrderDetail: " + url);
//            Call<JsonObject> jsonObjectCall = api_service_tracking.trackOrderDetail(url);
//            jsonObjectCall.enqueue(new Callback<JsonObject>() {
//                @Override
//                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//                    hideLoading();
//                    try {
//                        LoggerUtil.logItem("jkdfkljdfkljdfkljsdfskljdfklj" + response.body());
//                        LoggerUtil.logItem(response.code());
//                        if (response.isSuccessful()) {
//
//                        }
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<JsonObject> call, Throwable t) {
//
//                }
//            });
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


    @OnClick({R.id.side_menu, R.id.btn_cancelorder})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.side_menu:
                finish();
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                break;
            case R.id.btn_cancelorder:
                if (NetworkUtils.getConnectivityStatus(context) != 0) {
                    getCancelOrder(bundle.getString("order_id"));
                } else {
                    showMessage(context.getString(R.string.alert_internet));
                }
                break;
        }
    }
}
