package com.shoppingbag.mlm.fragments;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.shoppingbag.BuildConfig;
import com.shoppingbag.R;
import com.shoppingbag.app.PreferencesManager;
import com.shoppingbag.common_activities.LoginActivity;
import com.shoppingbag.constants.BaseFragment;
import com.shoppingbag.constants.Cons;
import com.shoppingbag.mlm.adapter.DashBoardNewsInfoAdapter;
import com.shoppingbag.mlm.adapter.DashboardMLMBannerAdapter;
import com.shoppingbag.model.response.mlmDashboardNew.ResponseNewMLMDashboard;
import com.shoppingbag.retrofit.MvpView;
import com.shoppingbag.utils.BannerLayout;
import com.shoppingbag.utils.LoggerUtil;
import com.shoppingbag.utils.NetworkUtils;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardMLM extends BaseFragment implements MvpView {
    Unbinder unbinder;
    @BindView(R.id.txtPan)
    TextView txtPan;
    @BindView(R.id.txtAddress)
    TextView txtAddress;
    @BindView(R.id.txtBank)
    TextView txtBank;
    @BindView(R.id.txtLink)
    TextView txtLink;
    @BindView(R.id.recycler)
    BannerLayout recyclerBanner;
    @BindView(R.id.img_user)
    ImageView imgUser;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.last_login_date)
    TextView lastLoginDate;
    @BindView(R.id.tv_userid)
    TextView tvUserid;
    @BindView(R.id.tv_doj)
    TextView tvDoj;
    @BindView(R.id.mySwitch)
    Switch mySwitch;
    @BindView(R.id.unclear_bal)
    TextView unclearBal;
    @BindView(R.id.self_income)
    TextView selfIncome;
    @BindView(R.id.mobile)
    TextView mobile;
    @BindView(R.id.tv_teamsize)
    TextView tvTeamsize;
    @BindView(R.id.ad_recycler)
    BannerLayout adRecycler;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dashboard_mlm, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            txtLink.setOnClickListener(v -> {
                try {
                    ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("Referral Code", Cons.decryptMsg(PreferencesManager.getInstance(context).getInviteCode(), cross_intent));
                    clipboard.setPrimaryClip(clip);
                    showMessage("Copied to clipboard");
                } catch (Error | Exception e) {
                    e.printStackTrace();
                }
            });

            tvName.setText(String.format("%s %s", Cons.decryptMsg(PreferencesManager.getInstance(context).getNAME(), cross_intent), Cons.decryptMsg(PreferencesManager.getInstance(context).getLNAME(), cross_intent)));
            tvUserid.setText(String.format(": %s", Cons.decryptMsg(PreferencesManager.getInstance(context).getLoginID(), cross_intent)));
            Glide.with(context).load(PreferencesManager.getInstance(context).getPROFILEPIC().replace("~", BuildConfig.BASE_URL_FORIMAGE)).
                    apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
                            .apply(RequestOptions.circleCropTransform())
                            .placeholder(R.drawable.logo_login)
                            .error(R.drawable.logo_login))
                    .into(imgUser);


            if (NetworkUtils.getConnectivityStatus(context) != 0) {
                getDashboardDataNew();
            } else {
                showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
            }
        } catch (Error | Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onViewCreatedStuff(View view, @Nullable Bundle savedInstanceState) {
    }


    private void getDashboardDataNew() {
        try {
            ////Log.e("AUth TOKEN==", AppConfig.authToken);
            String url = BuildConfig.BASE_URL_MLM + "DashboardDetail?FK_memId=" + PreferencesManager.getInstance(context).getUSERID();

            ////Log.e("URL========", url);
            showLoading();

            Call<JsonObject> call = apiServices.getDashboardDataNew(url, PreferencesManager.getInstance(context).getANDROIDID());
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                    hideLoading();
                    LoggerUtil.logItem(response.body());
                    LoggerUtil.logItem(response.code());
                    try {
                        if (response.isSuccessful()) {
                            String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent);
                            ResponseNewMLMDashboard convertedObject = new Gson().fromJson(paramResponse, ResponseNewMLMDashboard.class);
                            LoggerUtil.logItem(convertedObject);
                            if (convertedObject.getResponse().equalsIgnoreCase("Success")) {
                                setDashboardNewData(convertedObject);
                            } else {
                                showMessage("Something went wrong");
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

    private void setDashboardNewData(ResponseNewMLMDashboard data) {
        try {
            lastLoginDate.setText(String.format("Last login : %s", data.getDashboardMlm().getFirstLoginDate()));
            tvDoj.setText(String.format(": %s", data.getDashboardMlm().getDoj()));

            unclearBal.setText(data.getDashboardMlm().getUncleared());
            selfIncome.setText(data.getDashboardMlm().getSelfincome());
            tvTeamsize.setText(data.getDashboardMlm().getTeamsize());
            mobile.setText(Cons.decryptMsg(PreferencesManager.getInstance(context).getMOBILE(), cross_intent));


            if (data.getDashboardMlm().getAdharStatus().equalsIgnoreCase("0")) {
                txtAddress.setCompoundDrawablesWithIntrinsicBounds(R.drawable.cross, 0, 0, 0);
                txtAddress.setText("Address Not Verified");
                txtAddress.setTextColor(getResources().getColor(R.color.red));
            } else if (data.getDashboardMlm().getAdharStatus().equalsIgnoreCase("1")) {
                txtAddress.setCompoundDrawablesWithIntrinsicBounds(R.drawable.pending, 0, 0, 0);
                txtAddress.setText("Address Pending");
                txtAddress.setTextColor(getResources().getColor(R.color.orange));
            } else if (data.getDashboardMlm().getAdharStatus().equalsIgnoreCase("2")) {
                txtAddress.setCompoundDrawablesWithIntrinsicBounds(R.drawable.tick, 0, 0, 0);
                txtAddress.setText("Address Verified");
                txtAddress.setTextColor(getResources().getColor(R.color.green));
            }
            if (data.getDashboardMlm().getBankStatus().equalsIgnoreCase("0")) {
                txtBank.setCompoundDrawablesWithIntrinsicBounds(R.drawable.cross, 0, 0, 0);
                txtBank.setText("Bank Not Verified");
                txtBank.setTextColor(getResources().getColor(R.color.red));
            } else if (data.getDashboardMlm().getBankStatus().equalsIgnoreCase("1")) {
                txtBank.setCompoundDrawablesWithIntrinsicBounds(R.drawable.pending, 0, 0, 0);
                txtBank.setText("Bank Pending");
                txtBank.setTextColor(getResources().getColor(R.color.orange));
            } else if (data.getDashboardMlm().getBankStatus().equalsIgnoreCase("2")) {
                txtBank.setCompoundDrawablesWithIntrinsicBounds(R.drawable.tick, 0, 0, 0);
                txtBank.setText("Bank Verified");
                txtBank.setTextColor(getResources().getColor(R.color.green));
            }
            if (data.getDashboardMlm().getPanCardStatus().equalsIgnoreCase("0")) {
                txtPan.setText("Pan Not Verified");
                txtPan.setCompoundDrawablesWithIntrinsicBounds(R.drawable.cross, 0, 0, 0);
                txtPan.setTextColor(getResources().getColor(R.color.red));
            } else if (data.getDashboardMlm().getPanCardStatus().equalsIgnoreCase("1")) {
                txtPan.setCompoundDrawablesWithIntrinsicBounds(R.drawable.pending, 0, 0, 0);
                txtPan.setText("Pan Pending");
                txtPan.setTextColor(getResources().getColor(R.color.orange));
            } else if (data.getDashboardMlm().getPanCardStatus().equalsIgnoreCase("2")) {
                txtPan.setCompoundDrawablesWithIntrinsicBounds(R.drawable.tick, 0, 0, 0);
                txtPan.setText("Pan Verified");
                txtPan.setTextColor(getResources().getColor(R.color.green));
            }

            DashboardMLMBannerAdapter webBannerAdapter = new DashboardMLMBannerAdapter(context, data.getBanners());
            adRecycler.setAdapter(webBannerAdapter);

            DashBoardNewsInfoAdapter webnewsAdapter = new DashBoardNewsInfoAdapter(context, data.getNewsAndInfo());
            recyclerBanner.setAdapter(webnewsAdapter);
        } catch (Error | Exception e) {
            e.printStackTrace();
        }
    }

}

