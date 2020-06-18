package com.shoppingbag.one_india_shopping.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shoppingbag.app.PreferencesManager;
import com.shoppingbag.constants.BaseFragment;
import com.shoppingbag.constants.Cons;
import com.shoppingbag.utils.LoggerUtil;
import com.shoppingbag.utils.NetworkUtils;
import com.shoppingbag.R;
import com.shoppingbag.one_india_shopping.activity.ShopIndProductList;
import com.shoppingbag.one_india_shopping.adapter.BannerEventsAdapter;
import com.shoppingbag.one_india_shopping.adapter.IndCategoryDashboard;
import com.shoppingbag.one_india_shopping.adapter.IndDealsOneAdapter;
import com.shoppingbag.one_india_shopping.adapter.IndDealsTwoAdapter;
import com.shoppingbag.one_india_shopping.model.dashboard.BannerItem;
import com.shoppingbag.one_india_shopping.model.dashboard.BestDealItem;
import com.shoppingbag.one_india_shopping.model.dashboard.BestSellerItemItem;
import com.shoppingbag.one_india_shopping.model.dashboard.CategoryListItem;
import com.shoppingbag.one_india_shopping.model.dashboard.ResponseDashboard;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShopIndDashboard extends BaseFragment {

    @BindView(R.id.rv_category)
    RecyclerView rvCategory;
    @BindView(R.id.txt_deals)
    TextView txtDeals;
    @BindView(R.id.txtlow_to_high)
    TextView txtlow_to_high;
    @BindView(R.id.rv_dealsone)
    RecyclerView rvDealsone;
    @BindView(R.id.txt_dealsone)
    TextView txtDealsone;
    @BindView(R.id.rv_dealstwo)
    RecyclerView rvDealstwo;
    @BindView(R.id.rvimages)
    RecyclerView rvimages;
    @BindView(R.id.radio_low)
    RadioButton radio_low;
    @BindView(R.id.radio_high)
    RadioButton radio_high;
    @BindView(R.id.imageViewSort)
    ImageView imageViewSort;
    private List<CategoryListItem> listItems = new ArrayList<>();
    private List<BestDealItem> bestDealItems = new ArrayList<>();
    private List<BestSellerItemItem> bestSellerItemItems = new ArrayList<>();
    private GridLayoutManager gridLayoutManager1, gridLayoutManager2;
    private LinearLayoutManager layoutManager;
    private IndDealsOneAdapter dealsOneAdapter;
    private IndDealsTwoAdapter indDealsTwoAdapter;
    private IndCategoryDashboard categoryDashboardadapter;
    private Unbinder unbinder;
    private BannerEventsAdapter eventsAdapter;
    private LinearLayoutManager eventlayoutmanager;
    private List<BannerItem> bannerItems;
    BottomSheetBehavior sheetBehavior;
    @BindView(R.id.bottomLay)
    ConstraintLayout bottomLay;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_shop_ind_dashboard, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;

    }

    @Override
    public void onViewCreatedStuff(View view, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        rvCategory.setLayoutManager(layoutManager);

        gridLayoutManager1 = new GridLayoutManager(context, 2);
        gridLayoutManager1.setOrientation(RecyclerView.VERTICAL);
        rvDealsone.setLayoutManager(gridLayoutManager1);

        gridLayoutManager2 = new GridLayoutManager(context, 2);
        gridLayoutManager2.setOrientation(RecyclerView.VERTICAL);
        rvDealstwo.setLayoutManager(gridLayoutManager2);

        eventlayoutmanager = new LinearLayoutManager(context);
        eventlayoutmanager.setOrientation(RecyclerView.VERTICAL);
        rvimages.setLayoutManager(eventlayoutmanager);


        if (NetworkUtils.getConnectivityStatus(context) != 0) {

            getDashBoard();
        } else {
            showMessage(context.getString(R.string.alert_internet));
        }
        sheetBehavior = BottomSheetBehavior.from(bottomLay);

        sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        //sheetBehavior.setHideable(false);
    }


    @Override
    public void getMyClickPosition(String name, String tag) {
        super.getMyClickPosition(name, tag);
        Bundle bundle = new Bundle();
        bundle.putString("name", tag);
        bundle.putString("id", name);
        goToActivity(ShopIndProductList.class, bundle);

    }


    private void getDashBoard() {
        showLoading();
        try {

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("user_id", Cons.decryptMsg(PreferencesManager.getInstance(context).getUSERID(), cross_intent));
            LoggerUtil.logItem(jsonObject);
            Log.e("=======", "<<<<<<<<<<=>>>>>>>>");

            Call<JsonObject> jsonObjectCall = apiServicesonestoreIndia.getDashboard(bodyParamoneStoreIndia(jsonObject));
            jsonObjectCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    hideLoading();
                    LoggerUtil.logItem(response.body());
                    LoggerUtil.logItem(response.code());
                    LoggerUtil.logItem(call.request().url());
                    Log.e("=======", ">>>>>>>>>>>>>>>>>");
                    try {
                        if (response.isSuccessful()) {
                            Log.e("=======", "<<<<<<<<<<<<<");

                            String para = Cons.decryptMsg(response.body().get("body").getAsString(), easy_pay_onestoreindia);
                            Gson gson = new GsonBuilder().create();
                            LoggerUtil.logItem(para);
                            ResponseDashboard dashboard = gson.fromJson(para, ResponseDashboard.class);

//                            showPopUp();
                            if (dashboard.getCat() != null && dashboard.getCat().getCategoryList().size() > 0) {
                                listItems = new ArrayList<>();
                                for (int i = 0; i < dashboard.getCat().getCategoryList().size(); i++) {
                                    if (dashboard.getCat().getCategoryList().get(i).isStatus()) {
                                        listItems.add(dashboard.getCat().getCategoryList().get(i));
                                    }
                                }
                                categoryDashboardadapter = new IndCategoryDashboard(context, listItems, ShopIndDashboard.this);
                                rvCategory.setAdapter(categoryDashboardadapter);
                            }

                            if (dashboard.getCategory() != null && dashboard.getCategory().getBestDeal().size() > 0) {
                                txtDeals.setText("Best Deals");
                                bestDealItems = dashboard.getCategory().getBestDeal();


                                // Collections.sort(bestDealItems, (lhs, rhs) -> Double.compare(lhs.getPrice(), rhs.getPrice()));
                                dealsOneAdapter = new IndDealsOneAdapter(context, bestDealItems, ShopIndDashboard.this);
                                rvDealsone.setAdapter(dealsOneAdapter);

                            }
                            if (dashboard.getDayDeal() != null && dashboard.getDayDeal().getBestSellerItem().size() > 0) {
                                txtDealsone.setText("Best Sellers");
                                bestSellerItemItems = dashboard.getDayDeal().getBestSellerItem();
                                indDealsTwoAdapter = new IndDealsTwoAdapter(context, bestSellerItemItems, ShopIndDashboard.this);
                                rvDealstwo.setAdapter(indDealsTwoAdapter);
                            }

//                            if (dashboard.getBanner().getBanner() != null && dashboard.getBanner().getBanner().size() > 0) {
//                                rvimages.setVisibility(View.VISIBLE);
//                                bannerItems = dashboard.getBanner().getBanner();
//                                eventsAdapter = new BannerEventsAdapter(context, bannerItems, ShopIndDashboard.this);
//                                rvimages.setAdapter(eventsAdapter);
//
//
//                            } else {
                                rvimages.setVisibility(View.GONE);
//                            }


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


//    private void showPopUp() {
//        Dialog settingsDialog = new Dialog(context);
//        settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//        settingsDialog.getWindow().setBackgroundDrawable(
//                new ColorDrawable(Color.TRANSPARENT));
//        settingsDialog.setContentView(getLayoutInflater().inflate(R.layout.popupdreamymall
//                , null));
//
//        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//        lp.copyFrom(settingsDialog.getWindow().getAttributes());
//        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        lp.gravity = Gravity.CENTER;
//
//        settingsDialog.getWindow().setAttributes(lp);
//
//        settingsDialog.show();
//        settingsDialog.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                settingsDialog.dismiss();
//            }
//        });
//
//    }

    @OnClick({R.id.imageViewSort, R.id.txtlow_to_high, R.id.txthigh_to_low, R.id.radio_high, R.id.radio_low})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageViewSort:
                if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                }
                break;
            case R.id.txtlow_to_high:
            case R.id.radio_low:
                sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                radio_low.setChecked(true);
                radio_high.setChecked(false);
                Collections.sort(bestDealItems, (lhs, rhs) -> Double.compare(lhs.getPrice(), rhs.getPrice()));
                dealsOneAdapter = new IndDealsOneAdapter(context, bestDealItems, ShopIndDashboard.this);
                rvDealsone.setAdapter(dealsOneAdapter);
               // dealsOneAdapter.notifyDataSetChanged();
                break;
            case R.id.txthigh_to_low:
            case R.id.radio_high:
                sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                radio_low.setChecked(false);
                radio_high.setChecked(true);
                Collections.sort(bestDealItems, (lhs, rhs) -> Double.compare(rhs.getPrice(), lhs.getPrice()));
                dealsOneAdapter = new IndDealsOneAdapter(context, bestDealItems, ShopIndDashboard.this);
                rvDealsone.setAdapter(dealsOneAdapter);
               // dealsOneAdapter.notifyDataSetChanged();
                break;
        }

    }
}
