package com.shoppingbag.mlm.fragments.team;


import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.shoppingbag.BuildConfig;
import com.shoppingbag.R;
import com.shoppingbag.app.AppConfig;
import com.shoppingbag.app.PreferencesManager;
import com.shoppingbag.common_activities.LoginActivity;
import com.shoppingbag.common_activities.MainContainer;
import com.shoppingbag.constants.BaseFragment;
import com.shoppingbag.constants.Cons;
import com.shoppingbag.mlm.adapter.DownlineMemberAdapter;
import com.shoppingbag.model.directmembermodel.ResponseDirectMemberDialog;
import com.shoppingbag.model.response.team.ResponseViewModel;
import com.shoppingbag.model.response.team.ResultItem;
import com.shoppingbag.utils.HidingScrollListener;
import com.shoppingbag.utils.LoggerUtil;
import com.shoppingbag.utils.NetworkUtils;

import org.jetbrains.annotations.NotNull;

import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class Downline extends BaseFragment {
    Unbinder unbinder;
    @BindView(R.id.txtName)
    TextView txtName;
    @BindView(R.id.btn_root)
    TextView btnRoot;
    @BindView(R.id.txt_norecordfound)
    TextView txtNoRecord;
    @BindView(R.id.tv_total_count)
    TextView tv_total_count;
    @BindView(R.id.btn_up)
    TextView btn_up;
    @BindView(R.id.tv_login_mob)
    TextView tvLoginMob;
    @BindView(R.id.et_mob_loginid)
    TextInputEditText etMobLoginid;
    private List<ResultItem> list;
    private String rootid;
    private int pageNo = 1;
    private int pageSize = 10;
    private boolean once = true;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private DownlineMemberAdapter adapter;
    private TextView name, totalteam, totalbusiness, referral;
    private AlertDialog dialog;
    @BindView(R.id.constraintLayoutSearch)
    ConstraintLayout constraintLayoutSearch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_downline, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreatedStuff(View view, @Nullable Bundle savedInstanceState) {
        try {

           // ((MainContainer) context).title_tv.setText("Downline");

            txtName.setText(Cons.decryptMsg(PreferencesManager.getInstance(context).getNAME(), cross_intent));
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        recyclerView = view.findViewById(R.id.recycler);
        rootid = PreferencesManager.getInstance(context).getUSERID();
        ((MainContainer) Objects.requireNonNull(getActivity())).img_search_downline_direct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (constraintLayoutSearch.getVisibility() == View.VISIBLE) {
                    constraintLayoutSearch.setVisibility(View.GONE);
                    tvLoginMob.setText("All");
                    etMobLoginid.setText("");
                } else {
                    constraintLayoutSearch.setVisibility(View.VISIBLE);
                }

            }
        });
        getDownline(etMobLoginid.getText().toString().trim().length() > 0 ? etMobLoginid.getText().toString().trim() : "All");
    }

    private void updateScroll() {
        layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addOnScrollListener(new HidingScrollListener(layoutManager) {
            @Override
            public void onHide() {
            }

            @Override
            public void onLoadMore(int i) {
                if (NetworkUtils.getConnectivityStatus(context) != 0) {
                    if (list.size() == pageNo * pageSize && once) {
                        once = false;
                        pageNo = i;
                        Log.e("Reach", "= " + pageNo);
                        Log.e("PageSize", "====>" + pageSize);
                        getDownline(etMobLoginid.getText().toString().trim().length() > 0 ? etMobLoginid.getText().toString().trim() : "All");
                    }
                }
            }
            @Override
            public void onShow() {

            }
        });

    }

    private void getDownline(String searchText) {
        try {
            showLoading();
            String url = BuildConfig.BASE_URL_MLM + "GetDownline?memberId=" + PreferencesManager.getInstance(context).getUSERID() + "&page=" + (Cons.encryptMsg(String.valueOf(pageNo), cross_intent)) + "&pagesize=" + (Cons.encryptMsg(String.valueOf(pageSize), cross_intent))
                    + "&searchtype=" + (Cons.encryptMsg(tvLoginMob.getText().toString(), cross_intent))
                    + "&searchvalue=" + (Cons.encryptMsg(searchText, cross_intent));
            Log.e("Url", ">>>>>>>>>>>" + url);
            Call<JsonObject> call = apiServices.getDownline(url, AppConfig.ANDROIDID);
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                    hideLoading();
                    LoggerUtil.logItem(response.body());
                    LoggerUtil.logItem(response.code());
                    LoggerUtil.logItem(call.request().url());
                    try {
                        once = true;
                        if (response.isSuccessful()) {
                            String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent);
                            ResponseViewModel convertedObject = new Gson().fromJson(paramResponse, ResponseViewModel.class);
                            LoggerUtil.logItem(convertedObject);
                            if (response.body() != null && convertedObject.getResponse().equalsIgnoreCase("Success")) {
                                tv_total_count.setText("" + convertedObject.gettotalCount());
                                if (pageNo == 1) {
                                    updateScroll();
                                    list = convertedObject.getResult();
                                    adapter = new DownlineMemberAdapter(context, list, Downline.this);
                                    recyclerView.setAdapter(adapter);
                                } else {
                                    list.addAll(convertedObject.getResult());
                                    adapter.updatelist(list);
                                }
                            } else {
                                recyclerView.setVisibility(View.GONE);
                                txtNoRecord.setVisibility(View.VISIBLE);
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getMyClickPosition(String name, String tag) {
        super.getMyClickPosition(name, tag);
        getTeamBusiness(name, tag);

    }

    private void dialogPopup(String pname, String team, String business, String referral) {


        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);

        // ...Irrelevant code for customizing the buttons and title

        LayoutInflater inflater = this.getLayoutInflater();

        View dialoglog = inflater.inflate(R.layout.teamandbusines, null);
        dialogBuilder.setView(dialoglog);


        name = dialoglog.findViewById(R.id.txtName);
        totalteam = dialoglog.findViewById(R.id.txteam);
        totalbusiness = dialoglog.findViewById(R.id.txt_totalbusiness);
        this.referral = dialoglog.findViewById(R.id.txt_refrral);
        name.setText(pname);
        totalteam.setText(team);
        totalbusiness.setText(business);
        this.referral.setText(referral);


        dialog = dialogBuilder.create();
        dialog.show();


    }

    private void getTeamBusiness(String name, String memberId) {
        try {
            showLoading();
            String url = BuildConfig.BASE_URL_MLM + "GetBusinessDetails?memberId=" + Cons.encryptMsg(memberId, cross_intent);
            Log.e("==>", "========>" + url);
            Call<JsonObject> jsonObjectCall = apiServices.getTeamandBusiness(url, AppConfig.ANDROIDID);
            jsonObjectCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                    hideLoading();
                    try {
                        LoggerUtil.logItem(response.body());
                        LoggerUtil.logItem(response.code());
                        if (response.isSuccessful()) {
                            String param = Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent);
                            Gson gson = new GsonBuilder().create();
                            Log.e("======", "========>" + param);
                            ResponseDirectMemberDialog memberDialog = gson.fromJson(param, ResponseDirectMemberDialog.class);
                            if (memberDialog.getResponse().equalsIgnoreCase("success")) {
                                if (memberDialog.getResult() != null) {
                                    dialogPopup(name, memberDialog.getResult().getTotalTeam(), memberDialog.getResult().getBusiness(), memberDialog.getResult().getTotalRefferal());
                                }
                            } else {
                                showMessage(memberDialog.getMessage());
                            }
                        } else {

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

    @OnClick({R.id.tv_login_mob, R.id.btn_Search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_login_mob:
                PopupMenu popLoginIdMobile = new PopupMenu(context, tvLoginMob);
                popLoginIdMobile.getMenuInflater().inflate(R.menu.menu_mob_loginid, popLoginIdMobile.getMenu());
                popLoginIdMobile.setOnMenuItemClickListener(item -> {
                    tvLoginMob.setText(item.getTitle());
                    etMobLoginid.setText("");
                    //int pos = item.getItemId();
                    return true;
                });
                popLoginIdMobile.show();
                break;
            case R.id.btn_Search:
                if (!(tvLoginMob.getText().toString().equalsIgnoreCase("All")) && TextUtils.isEmpty(etMobLoginid.getText().toString().trim())) {
                    etMobLoginid.setError("Enter valid Name/Mobile Number");
                    etMobLoginid.requestFocus();
                } else if (TextUtils.isEmpty(etMobLoginid.getText().toString().trim())) {
                    etMobLoginid.setError(null);
                    pageNo = 1;
                    getDownline(etMobLoginid.getText().toString().trim().length() > 0 ? etMobLoginid.getText().toString().trim() : "All");
                } else {
                    etMobLoginid.setError(null);
                    pageNo = 1;
                    getDownline(etMobLoginid.getText().toString().trim().length() > 0 ? etMobLoginid.getText().toString().trim() : "All");
                }
                break;
        }
    }
}
