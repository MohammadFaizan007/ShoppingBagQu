package com.shoppingbag.one_india_shopping.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shoppingbag.app.PreferencesManager;
import com.shoppingbag.constants.BaseActivity;
import com.shoppingbag.constants.Cons;
import com.shoppingbag.utils.LoggerUtil;
import com.shoppingbag.utils.NetworkUtils;
import com.shoppingbag.utils.Utils;
import com.shoppingbag.R;
import com.shoppingbag.common_activities.MainContainer;
import com.shoppingbag.one_india_shopping.adapter.MainCategoryAdapter;
import com.shoppingbag.one_india_shopping.fragment.ShopIndDashboard;
import com.shoppingbag.one_india_shopping.model.cartitem.ResponseCartItem;
import com.shoppingbag.one_india_shopping.model.category_list_response.ChildrenDataItem;
import com.shoppingbag.one_india_shopping.model.category_list_response.ResponseMainCategoryList;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShoppingIndiaActivity extends BaseActivity {
    @BindView(R.id.notification_bell)
    ImageView notificationBell;
    @BindView(R.id.title)
    TextView title_tv;
    @BindView(R.id.tv_noti_count)
    TextView tv_noti_count;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.img_cart)
    ImageView imgCart;
    @BindView(R.id.img_search_dreamy)
    ImageView img_search_dreamy;
    @BindView(R.id.txt_count)
    TextView txtCount;

    RecyclerView categoryRecycler;
    TextView nav_dashboard, nav_profile, nav_wallet, nav_orders;

    public int cartCounter = 0;

    private Fragment currentFragment;
    List<ResponseMainCategoryList> mainCategoryLists = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ind_activity_shopping);
        ButterKnife.bind(this);
        title_tv.setText("Shopping Mall");
        img_search_dreamy.setVisibility(View.VISIBLE);
        imgCart.setVisibility(View.VISIBLE);
        txtCount.setVisibility(View.VISIBLE);
        initView();
    }

    private void initView() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout.setScrimColor(getResources().getColor(android.R.color.transparent));
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        View hView = navView.getHeaderView(0);
        findIDs(hView);
        ReplaceFragment(new ShopIndDashboard(), "Dashboard");

        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            hitCategoryList();
            if (pref.getString("token", "").equalsIgnoreCase("")) {
                getCreateToken();
            } else {
                getCartItems();
            }
        } else {
            showMessage(context.getString(R.string.alert_internet));
        }
        imgCart.setOnClickListener(v -> goToActivity(ShoppingIndiaActivity.this, ShopIndCartItems.class, null));
        img_search_dreamy.setOnClickListener(v -> goToActivity(ShoppingIndiaActivity.this, ShopIndSearchActivity.class, null));
    }

    public void findIDs(View view) {
        categoryRecycler = view.findViewById(R.id.category_recycler);
        nav_dashboard = view.findViewById(R.id.nav_dashboard);
        nav_orders = view.findViewById(R.id.nav_orders);
        nav_profile = view.findViewById(R.id.nav_profile);
        nav_wallet = view.findViewById(R.id.nav_wallet);
        nav_wallet.setOnClickListener(this);
        nav_dashboard.setOnClickListener(this);
        nav_orders.setOnClickListener(this);
        nav_profile.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.nav_orders:
                drawerLayout.closeDrawers();
                goToActivity(ShoppingIndiaActivity.this, ShopIndMyOrder.class, null);
                break;
            case R.id.nav_dashboard:
            case R.id.nav_profile:
                drawerLayout.closeDrawers();
                break;
            case R.id.nav_wallet:
                drawerLayout.closeDrawers();
                goToActivity(ShoppingIndiaActivity.this, ShopIndCartItems.class, null);
                break;

        }

    }

    public void ReplaceFragment(Fragment setFragment, String titleStr) {
        currentFragment = setFragment;
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame, setFragment, titleStr);
        getSupportActionBar().setTitle(Html.fromHtml("<small><b>" + titleStr.toUpperCase() + "</b></small>"));
        transaction.commit();
    }


    private void hitCategoryList() {
        showLoading();
        Call<JsonObject> call = apiServicesOneStoreIndia.getCategoryList();
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                hideLoading();
                LoggerUtil.logItem(response.body());
                if (response.isSuccessful()) {
                    try {
                        String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), easy_pay_oneStoreIndia);
                        ArrayList<ResponseMainCategoryList> mainCategoryList = new ArrayList<>();
                        mainCategoryList.addAll(Utils.getList(paramResponse, ResponseMainCategoryList.class));
                        if (response.body() != null && mainCategoryList.size() > 0) {
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ShoppingIndiaActivity.this);
                            linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                            categoryRecycler.setLayoutManager(linearLayoutManager);
                            for (int i = 0; i < mainCategoryList.size(); i++) {
                                if (mainCategoryList.get(i).isIsActive()) {
                                    mainCategoryLists.add(mainCategoryList.get(i));
                                }
                            }
                            MainCategoryAdapter dataAdapter = new MainCategoryAdapter(ShoppingIndiaActivity.this, mainCategoryLists, ShoppingIndiaActivity.this);
                            categoryRecycler.setAdapter(dataAdapter);
                            categoryRecycler.setHasFixedSize(true);
                        } else {
                            showMessage("No Record Found");
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    showMessage("Something went wrong.");
                }

            }

            @Override
            public void onFailure(@NotNull Call<JsonObject> call, @NotNull Throwable t) {
                hideLoading();
                showMessage(t.getMessage());

            }
        });
    }

    @Override
    public void getClickPositionDirectMember(int position, String tag, String memberId) {
        super.getClickPositionDirectMember(position, tag, memberId);
        drawerLayout.closeDrawers();
        List<ChildrenDataItem> list = new Gson().fromJson(memberId, new TypeToken<List<ChildrenDataItem>>() {
        }.getType());
        if (list.size() > 0) {
            Bundle bundle = new Bundle();
            bundle.putString("list", memberId);
            bundle.putString("name", tag);
            goToActivity(ShoppingIndiaActivity.this, ShopIndSubCategoryListActivity.class, bundle);
        } else {
            Bundle bundle = new Bundle();
            bundle.putString("id", String.valueOf(position));
            bundle.putString("name", tag);
            goToActivity(this, ShopIndProductList.class, bundle);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        goToActivitySingle(ShoppingIndiaActivity.this, MainContainer.class, null);

    }

    @Override
    protected void onResume() {
        super.onResume();
        cartCounter = PreferencesManager.getInstance(context).getCartCount();
        txtCount.setText(String.format("%s", cartCounter));
    }

    private void getCreateToken() {
        Call<String> jsonObjectCall = apiServiceOneInd.getCreateToken();
        jsonObjectCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NotNull Call<String> call, @NotNull Response<String> response) {
                LoggerUtil.logItem("Token ====>" + response.body());
                LoggerUtil.logItem("Token ====>" + response.code());
                try {
                    if (response.isSuccessful()) {

                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("token", response.body().replace("\"", ""));
                        editor.apply();
                        getCartItems();
                        // PreferencesManager.getInstance(context).setToken(responseToken.getToken());

                    } else {
                        showMessage("Something went wrong");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NotNull Call<String> call, @NotNull Throwable t) {

            }
        });
    }

    public void getCartItems() {
        try {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("token", pref.getString("token", ""));

            LoggerUtil.logItem(jsonObject);
            Call<JsonObject> jsonObjectCall = apiServicesOneStoreIndia.getItems(bodyParamOneStoreIndia(jsonObject));
            jsonObjectCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                    try {
                        LoggerUtil.logItem(response.code());
                        LoggerUtil.logItem(response.body());
                        if (response.isSuccessful()) {
                            String param = Cons.decryptMsg(response.body().get("body").getAsString(), easy_pay_oneStoreIndia);
                            Gson gson = new GsonBuilder().create();
                            LoggerUtil.logItem(param);
                            ResponseCartItem cartItem = gson.fromJson(param, ResponseCartItem.class);
                            cartCounter = cartItem.getCartitems().size();
                            PreferencesManager.getInstance(context).setCartCount(cartCounter);
                            txtCount.setText(String.format("%s", cartCounter));

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
}
