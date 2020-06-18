package com.shoppingbag.one_india_shopping.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shoppingbag.app.PreferencesManager;
import com.shoppingbag.constants.BaseActivity;
import com.shoppingbag.constants.Cons;
import com.shoppingbag.utils.LoggerUtil;
import com.shoppingbag.utils.NetworkUtils;
import com.shoppingbag.utils.Utils;
import com.shoppingbag.R;
import com.shoppingbag.one_india_shopping.adapter.IndCartAdapter;
import com.shoppingbag.one_india_shopping.model.add_to_cart.AddToCartModel;
import com.shoppingbag.one_india_shopping.model.cartitem.CartitemsItem;
import com.shoppingbag.one_india_shopping.model.cartitem.ResponseCartItem;
import com.shoppingbag.one_india_shopping.model.removecart.ResponseRemovecart;
import com.shoppingbag.one_india_shopping.model.updatecart.ResponseUpdateCart;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShopIndCartItems extends BaseActivity {

    @BindView(R.id.side_menu)
    ImageView sideMenu;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.img_cart)
    ImageView imgCart;
    @BindView(R.id.txt_count)
    TextView txtCount;
    @BindView(R.id.tvemty)
    TextView tvemty;
    @BindView(R.id.gotoDashboard)
    TextView gotoDashboard;
    @BindView(R.id.view_cart_list)
    RecyclerView viewCartList;
    @BindView(R.id.tv_product_price_item_count)
    TextView tvProductPriceItemCount;
    @BindView(R.id.tv_product_total_price)
    TextView tvProductTotalPrice;
    @BindView(R.id.tv_total_amount)
    TextView tvTotalAmount;
    @BindView(R.id.cv_price_det)
    CardView cvPriceDet;
    @BindView(R.id.removeall)
    Button removeall;
    @BindView(R.id.tv_continue_to_checkout)
    Button tvContinueToCheckout;
    @BindView(R.id.cv_amount)
    CardView cvAmount;
    @BindView(R.id.view3)
    View view3;
    @BindView(R.id.textView113)
    TextView textView113;
    @BindView(R.id.view17)
    View view17;
    @BindView(R.id.view18)
    View view18;
    @BindView(R.id.textView116)
    TextView textView116;
    @BindView(R.id.textView117)
    TextView textView117;
    @BindView(R.id.view19)
    View view19;
    @BindView(R.id.textView118)
    TextView textView118;

    private LinearLayoutManager layoutManager;
    private IndCartAdapter adapter;

    private List<CartitemsItem> list = new ArrayList();
    private boolean checkpage = true;
    double totalPrice = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_ind_cart_items);
        ButterKnife.bind(this);

        title.setText("Cart Item");

        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        viewCartList.setLayoutManager(layoutManager);


    }


    @Override
    protected void onResume() {
        super.onResume();
        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            getCartItems();

        } else {
            showMessage(context.getString(R.string.alert_internet));
        }
    }

    public void getCartItems() {
        showLoading();
        try {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("token", pref.getString("token", ""));
            LoggerUtil.logItem(jsonObject);
            Call<JsonObject> jsonObjectCall = apiServicesOneStoreIndia.getItems(bodyParamOneStoreIndia(jsonObject));
            jsonObjectCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                    hideLoading();
                    try {
                        LoggerUtil.logItem(response.code());
                        LoggerUtil.logItem(response.body());
                        if (response.isSuccessful()) {
                            tvemty.setVisibility(View.GONE);
                            viewCartList.setVisibility(View.VISIBLE);
                            gotoDashboard.setVisibility(View.GONE);
                            String param = Cons.decryptMsg(response.body().get("body").getAsString(), easy_pay_oneStoreIndia);
                            Gson gson = new GsonBuilder().create();
                            LoggerUtil.logItem(param);
                            ResponseCartItem cartItem = gson.fromJson(param, ResponseCartItem.class);
                            if (cartItem.getCartitems().size() > 0) {

                                list = cartItem.getCartitems();
                                PreferencesManager.getInstance(context).setCartCount(list.size());
                                adapter = new IndCartAdapter(context, list, ShopIndCartItems.this);
                                viewCartList.setAdapter(adapter);

                                totalPrice = 0;
                                for (int i = 0; i < list.size(); i++) {
                                    totalPrice += list.get(i).getQty() * list.get(i).getPrice();
                                }

                                Log.e("==============", "------------" + totalPrice);

                                tvProductPriceItemCount.setText(String.format("Subtotal : %s", list.size()));
                                tvProductTotalPrice.setText(String.format("₹ %s", Utils.getValue(totalPrice)));
                                tvTotalAmount.setText(String.format("₹ %s", Utils.getValue(totalPrice)));
                            } else {
                                viewCartList.setVisibility(View.GONE);
                                tvemty.setVisibility(View.VISIBLE);
                                gotoDashboard.setVisibility(View.VISIBLE);
                                PreferencesManager.getInstance(context).setCartCount(0);
                                txtCount.setText("0");
                                textView113.setVisibility(View.GONE);
                                tvProductPriceItemCount.setVisibility(View.GONE);
                                tvProductTotalPrice.setVisibility(View.GONE);
                                textView116.setVisibility(View.GONE);
                                textView117.setVisibility(View.GONE);
                                textView118.setVisibility(View.GONE);
                                tvTotalAmount.setVisibility(View.GONE);
                                tvContinueToCheckout.setVisibility(View.GONE);

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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void getClickChildPosition(String name, String tag, Bundle bundle) {
        super.getClickChildPosition(name, tag, bundle);
        if (tag.equalsIgnoreCase("increment")) {

            addToCart(String.valueOf(bundle.getString("qtybox")), String.valueOf(bundle.getString("sku")), String.valueOf(bundle.getString("optionColorId")), String.valueOf(bundle.getString("optionColorValue"))
                    , String.valueOf(bundle.getString("optionSizeId")) , String.valueOf(bundle.getString("optionSizeValue")), String.valueOf(bundle.getString("type")));

        } else {

            getUpdateCartItem(bundle.getString("itemid"), bundle.getString("price"), String.valueOf(bundle.getString("qtybox")));
        }

    }


    public void getUpdateCartItem(String itemid, String price, String qty) {
        showLoading();
        try {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("item_id", itemid);
            jsonObject.addProperty("price", price);
            jsonObject.addProperty("token", pref.getString("token", ""));
            jsonObject.addProperty("qty", qty);
            LoggerUtil.logItem(jsonObject);
            Call<JsonObject> jsonObjectCall = apiServicesOneStoreIndia.getCartUpdate(bodyParamOneStoreIndia(jsonObject));
            jsonObjectCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                    hideLoading();
                    try {
                        LoggerUtil.logItem(response.code());
                        LoggerUtil.logItem(response.body());
                        if (response.isSuccessful()) {
                            String param = Cons.decryptMsg(response.body().get("body").getAsString(), easy_pay_oneStoreIndia);
                            Gson gson = new GsonBuilder().create();
                            LoggerUtil.logItem(param);
                            ResponseUpdateCart updateCart = gson.fromJson(param, ResponseUpdateCart.class);
                            if (updateCart.getResponse().equalsIgnoreCase("success")) {
                                showMessage(updateCart.getMessage());
                                getCartItems();
                            } else {
                                showMessage(updateCart.getMessage());
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void addToCart(String qty, String sku, String optionColorId, String optionColorValue, String optionSizeId, String optionSizeValue, String type) {
        showLoading();
        JsonObject cart = new JsonObject();
        cart.addProperty("sku", sku);
        cart.addProperty("qty", "1");
        cart.addProperty("token", pref.getString("token", ""));
        JsonArray options = new JsonArray();
        JsonObject optionColor = new JsonObject();
        optionColor.addProperty("option_id", optionColorId);
        optionColor.addProperty("option_value", optionColorValue);
        JsonObject optionSize = new JsonObject();
        optionSize.addProperty("option_id", optionSizeId);
        optionSize.addProperty("option_value", optionSizeValue);
        options.add(optionColor);
        options.add(optionSize);
        cart.add("custom_options", options);
        //  cart.addProperty("option_id", optionId);
        //  cart.addProperty("option_value", optionValue);
        cart.addProperty("type", type);



        LoggerUtil.logItem(cart);
        Call<JsonObject> jsonObjectCall = apiServicesOneStoreIndia.getGuestAddTo(bodyParamOneStoreIndia(cart));
        jsonObjectCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                hideLoading();
                LoggerUtil.logItem(response.code());
                LoggerUtil.logItem(response.body());
                try {
                    if (response.isSuccessful()) {
                        try {
                            String param = Cons.decryptMsg(response.body().get("body").getAsString(), easy_pay_oneStoreIndia);
                            Gson gson = new GsonBuilder().create();
                            LoggerUtil.logItem(param);
                            AddToCartModel addToCartModel = gson.fromJson(param, AddToCartModel.class);
                            if (addToCartModel.getResponse().equalsIgnoreCase("success")) {
                                showMessage(addToCartModel.getMessage());
                                getCartItems();
                            } else {
                                showMessage(addToCartModel.getMessage());
                            }
                            LoggerUtil.logItem(param);


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

    public void getRemovecartItems(int itemid) {
        showLoading();
        try {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("item_id", itemid);
            jsonObject.addProperty("token", pref.getString("token", ""));
            LoggerUtil.logItem(jsonObject);
            Call<JsonObject> jsonObjectCall = apiServicesOneStoreIndia.getremovecartitems(bodyParamOneStoreIndia(jsonObject));
            jsonObjectCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    hideLoading();
                    try {
                        LoggerUtil.logItem(response.code());
                        LoggerUtil.logItem(response.body());
                        if (response.isSuccessful()) {
                            String param = Cons.decryptMsg(response.body().get("body").getAsString(), easy_pay_oneStoreIndia);
                            Gson gson = new GsonBuilder().create();
                            LoggerUtil.logItem(param);
                            ResponseRemovecart removecart = gson.fromJson(param, ResponseRemovecart.class);
                            if (removecart.getResponse().equalsIgnoreCase("success")) {
                                checkpage = false;
                                if (checkpage == false) {
                                    getCartItems();
                                }
                                showMessage(removecart.getMessage());
                            } else {
                                showMessage(removecart.getMessage());
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
    public void getClickPosition(int position, String tag) {
        super.getClickPosition(position, tag);
        getRemovecartItems(position);
    }


    @OnClick({R.id.side_menu, R.id.gotoDashboard, R.id.removeall, R.id.tv_continue_to_checkout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.side_menu:
                finish();
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                break;
            case R.id.gotoDashboard:
                goToActivityWithFinish(ShoppingIndiaActivity.class, null);

                break;
            case R.id.removeall:
                break;
            case R.id.tv_continue_to_checkout:
                String list_ = new Gson().toJson(list);
                Bundle bundle = new Bundle();
                bundle.putString("list", list_);
                bundle.putDouble("amount", totalPrice);
                goToActivity(ShopIndCartItems.this, ShopIndMakePayment.class, bundle);
                break;
        }
    }
}
