package com.shoppingbag.shopping.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.shoppingbag.BuildConfig;
import com.shoppingbag.R;
import com.shoppingbag.app.PreferencesManager;
import com.shoppingbag.billpayment.activity.ElectricityBillPayment;
import com.shoppingbag.billpayment.activity.OtherBillPayment;
import com.shoppingbag.common_activities.FullScreenLogin;
import com.shoppingbag.common_activities.LoginActivity;
import com.shoppingbag.common_activities.MainContainer;
import com.shoppingbag.common_activities.NormalWebViewActivity;
import com.shoppingbag.constants.BaseFragment;
import com.shoppingbag.constants.Cons;
import com.shoppingbag.gift_card.GiftCardCategories;
import com.shoppingbag.model.response.shopping.ResponseShoppingItemList;
import com.shoppingbag.model.wallet.ResponseWalletBalance;
import com.shoppingbag.one_india_shopping.activity.ShoppingIndiaActivity;
import com.shoppingbag.retrofit.MvpView;
import com.shoppingbag.shopping.adapter.DashboardTravelAdapter;
import com.shoppingbag.shopping.adapter.DashboardUtilitiesAdapter;
import com.shoppingbag.themepark.ThemePark;
import com.shoppingbag.utilities.activities.BusActivity;
import com.shoppingbag.utilities.activities.DTH_Recharge;
import com.shoppingbag.utilities.activities.FlightActivity;
import com.shoppingbag.utilities.activities.MobileRechargeActivity;
import com.shoppingbag.utils.LoggerUtil;
import com.shoppingbag.utils.NetworkUtils;
import com.shoppingbag.wallet.CashBackWallet;
import com.shoppingbag.wallet.CommissionWallet;
import com.shoppingbag.wallet.DreamyWallet;

import org.jetbrains.annotations.NotNull;

import java.security.GeneralSecurityException;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Dashboard extends BaseFragment implements MvpView {

    @BindView(R.id.txtNameUser)
    TextView txtNameUser;
    @BindView(R.id.tv_UserStatus)
    TextView tvUserStatus;
    @BindView(R.id.text_cashback_amt)
    TextView textCashbackAmt;
    @BindView(R.id.text_dreamy_amt)
    TextView textDreamyAmt;
    @BindView(R.id.text_comm_amt)
    TextView textCommAmt;
    @BindView(R.id.constraintLayout3)
    ConstraintLayout constraintLayout3;
    @BindView(R.id.constraintLayout2)
    ConstraintLayout constraintLayout2;
    @BindView(R.id.constraintLayout4)
    ConstraintLayout constraintLayout4;
    @BindView(R.id.shoppinamalltxt)
    TextView shoppinamalltxt;
    @BindView(R.id.img_flight)
    ImageView imgFlight;
    @BindView(R.id.txt_activateId)
    TextView txtActivateId;
    private Unbinder unbinder;
    @BindView(R.id.utilities)
    TextView utilities;
    @BindView(R.id.mainScroll)
    NestedScrollView mainScroll;

    @BindView(R.id.grid_view_more_sec)
    RecyclerView gridViewMoreSec;
    @BindView(R.id.grid_view_travel)
    RecyclerView gridViewTravel;
    @BindView(R.id.grid_view_utility)
    RecyclerView gridViewUtility;
    @BindView(R.id.foodRecycler)
    RecyclerView foodRecycler;
    @BindView(R.id.txtUpdate)
    TextView txtUpdate;
    @BindView(R.id.more)
    TextView tv_shopping;
    private Bundle param = new Bundle();


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.homenew, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreatedStuff(View view, @Nullable Bundle savedInstanceState) {
        mainScroll.setSmoothScrollingEnabled(true);

        ((MainContainer) context).title_tv.setText("DASHBOARD");
        ((MainContainer) context).img_referal.setVisibility(View.VISIBLE);
        ((MainContainer) context).currentFragment = this;

        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            getCategoryList();

        } else {
            showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
        }

        try {
            txtNameUser.setText(Cons.decryptMsg(PreferencesManager.getInstance(context).getNAME(), cross_intent) + " " +
                    Cons.decryptMsg(PreferencesManager.getInstance(context).getLNAME(), cross_intent));
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        tvUserStatus.setText(PreferencesManager.getInstance(context).getStatus());
        if (PreferencesManager.getInstance(context).getStatus().equalsIgnoreCase("Active")) {
            txtActivateId.setVisibility(View.GONE);
            tvUserStatus.setBackground(ContextCompat.getDrawable(context, R.drawable.status_green_drawable));
        } else {
            txtActivateId.setVisibility(View.VISIBLE);
            tvUserStatus.setBackground(ContextCompat.getDrawable(context, R.drawable.status_red_drawable));
        }
        constraintLayout2.setOnClickListener(view1 -> goToActivity(DreamyWallet.class, null));
        constraintLayout3.setOnClickListener(view1 -> goToActivity(CashBackWallet.class, null));
        constraintLayout4.setOnClickListener(view1 -> goToActivity(CommissionWallet.class, null));
       /*tv_shopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToActivity(ShopIndProductList.class, null);
            }
        });*/

    }

    @Override
    public void getClickPositionDirectMember(int position, String link, String tag) {
        super.getClickPositionDirectMember(position, link, tag);
        checkLoginUtility(tag, link);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void checkLoginUtility(String bottom_title, String link) {

        ((MainContainer) Objects.requireNonNull(getActivity())).bottomTitle = "Utility";
        ((MainContainer) Objects.requireNonNull(getActivity())).bottomLink = link;

        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            if (PreferencesManager.getInstance(context).getUSERID().equalsIgnoreCase("")) {
                FullScreenLogin dialog = new FullScreenLogin();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                dialog.show(ft, FullScreenLogin.TAG);
            } else {
                try {
                    Bundle bundle = new Bundle();
                    bundle.putString("from", "Main");
                    bundle.putString("link", link + "&subid=DD" + Cons.decryptMsg(PreferencesManager.getInstance(context).getLoginID(), cross_intent));
                    ((MainContainer) context).goToActivity(context, NormalWebViewActivity.class, bundle);
                } catch (Error | Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
        }

    }

    private void getWalletBalance() {
        //http://dreamydroshky.in/webapi/api/Web/GetWallet?MemberId=+/rsYtyXxjMp5KsWywOssA==

        String url = BuildConfig.BASE_URL_MLM + "GetWallet?MemberId=" + PreferencesManager.getInstance(context).getUSERID();
        LoggerUtil.logItem("WALLET - " + url);
        Call<JsonObject> call = apiServices.getBankName(url, PreferencesManager.getInstance(context).getANDROIDID());
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                try {
                    LoggerUtil.logItem(response.body());
                    LoggerUtil.logItem(response.code());
                    if (response.isSuccessful()) {
                        ResponseWalletBalance responseWalletBalance = new Gson().fromJson(Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent), ResponseWalletBalance.class);
                        if (response.body() != null && responseWalletBalance.getResponse().equalsIgnoreCase("Success")) {
                            textDreamyAmt.setText(String.format("₹ %s", responseWalletBalance.getResult().getDreamyWalletAmount()));
                            textCashbackAmt.setText(String.format("₹ %s", responseWalletBalance.getResult().getCashbackWalletAmount()));
                            textCommAmt.setText(String.format("₹ %s", responseWalletBalance.getResult().getCommisionWalletAmount()));
                        } else {
                            textDreamyAmt.setText("₹ 0");
                            textCashbackAmt.setText("₹ 0");
                            textCommAmt.setText("₹ 0");
                        }
                    } else {
                        Log.e("WALLET", "ERROR");
                        textDreamyAmt.setText("₹ 0");
                        textCashbackAmt.setText("₹ 0");
                        textCommAmt.setText("₹ 0");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NotNull Call<JsonObject> call, @NotNull Throwable t) {
                Log.e("WALLET", "ERROR" + t.getMessage());
            }
        });
    }

    private void getCategoryList() {
        try {
            String url = BuildConfig.INR_URL + "GetDashboardDetail";
            LoggerUtil.log(url);

            Call<JsonObject> call = apiServicesInr.getShoppingItemList(url, PreferencesManager.getInstance(context).getANDROIDID());
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                    hideLoading();
                    LoggerUtil.logItem(response.body());
                    try {
                        if (response.isSuccessful()) {
                            ResponseShoppingItemList response_new = new Gson().fromJson(Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent), ResponseShoppingItemList.class);
                            LoggerUtil.logItem(response_new);
                            if (response.body() != null && response_new.getResponse().equalsIgnoreCase("Success")) {


                                if (gridViewUtility != null) {
                                    DashboardUtilitiesAdapter adapterViewAndroidMore = new DashboardUtilitiesAdapter(getActivity(), response_new.getShopping(), Dashboard.this);
                                    gridViewMoreSec.setLayoutManager(new GridLayoutManager(getActivity(), 4));
                                    gridViewMoreSec.setAdapter(adapterViewAndroidMore);
                                    gridViewMoreSec.setHasFixedSize(true);

                                    DashboardTravelAdapter adapterViewAndroidTravel = new DashboardTravelAdapter(getActivity(), response_new.getTravel(), Dashboard.this);
                                    gridViewTravel.setLayoutManager(new GridLayoutManager(getActivity(), 4));
                                    gridViewTravel.setAdapter(adapterViewAndroidTravel);
                                    gridViewTravel.setHasFixedSize(true);

                                    DashboardUtilitiesAdapter adapterViewAndroidUtility = new DashboardUtilitiesAdapter(getActivity(), response_new.getUtilities(), Dashboard.this);
                                    gridViewUtility.setLayoutManager(new GridLayoutManager(getActivity(), 4));
                                    gridViewUtility.setAdapter(adapterViewAndroidUtility);
                                    gridViewUtility.setHasFixedSize(true);

                                    DashboardUtilitiesAdapter adapterViewAndroidFood = new DashboardUtilitiesAdapter(getActivity(), response_new.getFoodList(), Dashboard.this);
                                    foodRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 4));
                                    foodRecycler.setAdapter(adapterViewAndroidFood);
                                    foodRecycler.setHasFixedSize(true);
                                }

                            } else {
                                showMessage("Something went wrong.");
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
                public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                    hideLoading();
                    LoggerUtil.logItem(t.getMessage());
                }
            });

        } catch (Error | Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.imgMobile, R.id.imgDth, R.id.img_electricity, R.id.img_broadband, R.id.img_water, R.id.img_bus, R.id.img_gas, R.id.img_insurance, R.id.img_giftvoucher, R.id.img_themepark
            , R.id.img_flight, R.id.img_shoppingmall, R.id.tv_UserStatus})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgMobile:
                checkLogin("Mobile Recharge");
                break;
            case R.id.imgDth:
                checkLogin("DTH");
                break;
            case R.id.img_electricity:

                checkLogin("Electricity");
                break;
            case R.id.img_broadband:
//0085A4
                checkLogin("Broadband");
                break;

            case R.id.img_water:

                checkLogin("Water");
                break;
            case R.id.img_bus:

                checkLogin("Bus");
                break;
            case R.id.img_gas:

                checkLogin("Gas");
                break;
            case R.id.img_giftvoucher:
                checkLogin("Gift Card");
                break;
            case R.id.img_insurance:
                checkLogin("Insurance");
                break;
            case R.id.img_themepark:
                checkLogin("Theme Park");
                break;
            case R.id.img_flight:
                checkLogin("Flight");
                break;
            case R.id.tv_UserStatus:
                if (tvUserStatus.getText().toString().equalsIgnoreCase("Active")) {

                } else {
                    ((MainContainer) Objects.requireNonNull(getActivity())).startPayment();

                }
                break;
            case R.id.img_shoppingmall:
//                checkLogin("Shopping Mall");
                showMessage("Coming Soon");
                break;

        }
    }

    private void checkLogin(String bottom_title) {
        ((MainContainer) Objects.requireNonNull(getActivity())).bottomTitle = bottom_title;
        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            if (PreferencesManager.getInstance(context).getUSERID().equalsIgnoreCase("")) {
                FullScreenLogin dialog = new FullScreenLogin();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                dialog.show(ft, FullScreenLogin.TAG);
            } else {
                switch (bottom_title) {
                    case "Mobile Recharge":
                        goToActivity(MobileRechargeActivity.class, null);
                        break;
                    case "DTH":
                        goToActivity(DTH_Recharge.class, null);
                        break;
                    case "Flight":
                        goToActivity(FlightActivity.class, null);
                        break;
                    case "Bus":
                        goToActivity(BusActivity.class, null);
                        break;
                    case "Shopping":
                        goToActivity(ShoppingIndiaActivity.class, null);
                        break;
                    case "Broadband": {
                        param.putString("bill", Cons.BROADBAND_BILL_PAYMENT);
                        goToActivity(OtherBillPayment.class, param);
                        break;
                    }
                    case "Insurance": {
                        param.putString("bill", Cons.INSURANCE_BILL_PAYMENT);
                        goToActivity(OtherBillPayment.class, param);
                        break;
                    }
                    case "Electricity": {
                        param.putString("bill", Cons.ELECTRICITY_BILL_PAYMENT);
                        goToActivity(ElectricityBillPayment.class, param);
                        break;
                    }
                    case "Gas": {
                        param.putString("bill", Cons.GAS_BILL_PAYMENT);
                        goToActivity(OtherBillPayment.class, param);
                        break;
                    }
                    case "Water": {
                        param.putString("bill", Cons.WATER_BILL_PAYMENT);
                        goToActivity(OtherBillPayment.class, param);
                        break;

                    }

                    case "Theme Park":
                        goToActivity(ThemePark.class, null);
                        break;
                    case "Gift Card":
                        param.putString("from", "Gift Card");
                        goToActivity(GiftCardCategories.class, param);
                        break;
                    case "Shopping Mall":
                        param.putString("from", "Shopping Mall");
                        goToActivity(ShoppingIndiaActivity.class, param);

                        break;
                    default:
                        // For business and other click option
                        goToActivityWithFinish(LoginActivity.class, null);
                        break;
                }
            }
        } else {
            showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            getWalletBalance();
        } else {
            showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
        }
    }

    @OnClick(R.id.txt_activateId)
    public void onViewClicked() {
    }
}
