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
import com.shoppingbag.mlm.adapter.DirectMemberAdapter;
import com.shoppingbag.model.directmembermodel.ResponseDirectMemberDialog;
import com.shoppingbag.model.response.team.ResponseViewModel;
import com.shoppingbag.model.response.team.ResultItem;
import com.shoppingbag.retrofit.MvpView;
import com.shoppingbag.utils.HidingScrollListener;
import com.shoppingbag.utils.LoggerUtil;
import com.shoppingbag.utils.NetworkUtils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DirectMember extends BaseFragment implements MvpView {

    public static ArrayList<String> previousIds = new ArrayList<>();
    public static ArrayList<String> previousNames = new ArrayList<>();
    @BindView(R.id.txtName)
    TextView txtName;
    @BindView(R.id.btn_root)
    TextView btnRoot;
    @BindView(R.id.member_recycler)
    RecyclerView memberRecycler;
    Unbinder unbinder;
    @BindView(R.id.txtNoRecord)
    TextView txtNoRecord;
    @BindView(R.id.btn_up)
    TextView btn_up;
    @BindView(R.id.constraintLayoutSearch)
    ConstraintLayout constraintLayoutSearch;
    @BindView(R.id.tv_totalteam)
    TextView tv_totalteam;
    @BindView(R.id.tv_totalReferral)
    TextView tv_totalReferral;
    @BindView(R.id.tv_login_mob)
    TextView tv_login_mob;
    @BindView(R.id.et_mob_loginid)
    TextInputEditText etMobLoginid;
    private String rootId;
    private String memberId;
    private List<ResultItem> list;
    private int pageNo = 1;
    private int pageSize = 10;
    private boolean once = true;
    private LinearLayoutManager layoutManager;
    private DirectMemberAdapter adapter;
    private TextView name, totalteam, totalbusiness, referral;
    private AlertDialog dialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.direct_member, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreatedStuff(View view, @Nullable Bundle savedInstanceState) {
        try {
            ((MainContainer) context).title_tv.setText("Direct Member");
            rootId = Cons.decryptMsg(PreferencesManager.getInstance(context).getUSERID(), cross_intent);
            txtName.setText(Cons.decryptMsg(PreferencesManager.getInstance(context).getNAME(), cross_intent));

            pageNo = 1;
            if (NetworkUtils.getConnectivityStatus(context) != 0) {
                getDirect(rootId, etMobLoginid.getText().toString().trim().length() > 0 ? etMobLoginid.getText().toString().trim() : "All");
                btn_up.setVisibility(View.GONE);
            } else {
                showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
            }
        } catch (Error | Exception e) {
            e.printStackTrace();
        }
        ((MainContainer) Objects.requireNonNull(getActivity())).img_search_downline_direct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (constraintLayoutSearch.getVisibility() == View.VISIBLE) {
                    constraintLayoutSearch.setVisibility(View.GONE);
                    tv_login_mob.setText("All");
                    etMobLoginid.setText("");
                } else {
                    constraintLayoutSearch.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btn_root, R.id.tv_login_mob, R.id.btn_up, R.id.btn_Search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_root:
                if (NetworkUtils.getConnectivityStatus(context) != 0) {
                    try {
                        txtName.setText(Cons.decryptMsg(PreferencesManager.getInstance(context).getNAME(), cross_intent));
                        pageNo = 1;
                        getDirect(rootId, etMobLoginid.getText().toString().trim().length() > 0 ? etMobLoginid.getText().toString().trim() : "All");
                        btn_up.setVisibility(View.GONE);
                        previousIds.clear();
                        previousNames.clear();
                    } catch (Error | Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
                }
                break;
            case R.id.tv_login_mob:
                PopupMenu popLoginIdMobile = new PopupMenu(context, tv_login_mob);
                popLoginIdMobile.getMenuInflater().inflate(R.menu.menu_mob_loginid, popLoginIdMobile.getMenu());
                popLoginIdMobile.setOnMenuItemClickListener(item -> {
                    tv_login_mob.setText(item.getTitle());
                    etMobLoginid.setText("");
                    //int pos = item.getItemId();
                    return true;
                });
                popLoginIdMobile.show();
                break;
            case R.id.btn_up:
                try {
                    if (NetworkUtils.getConnectivityStatus(context) != 0) {
                        pageNo = 1;
                        if (previousIds.size() > 1) {
                            previousIds.remove(previousIds.size() - 1);
                            previousNames.remove(previousNames.size() - 1);
                            getDirect(previousIds.get(previousIds.size() - 1), etMobLoginid.getText().toString().trim().length() > 0 ? etMobLoginid.getText().toString().trim() : "All");
                            txtName.setText(previousNames.get(previousNames.size() - 1));
                        } else {
                            previousIds.clear();
                            previousNames.clear();
                            btn_up.setVisibility(View.GONE);
                            getDirect(rootId, etMobLoginid.getText().toString().trim().length() > 0 ? etMobLoginid.getText().toString().trim() : "All");
                            txtName.setText(Cons.decryptMsg(PreferencesManager.getInstance(context).getNAME(), cross_intent));
                        }
                        LoggerUtil.logItem(previousIds + " " + previousNames);
                    } else {
                        showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
                    }
                } catch (Error | Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_Search:

                if (!(tv_login_mob.getText().toString().equalsIgnoreCase("All")) && TextUtils.isEmpty(etMobLoginid.getText().toString().trim())) {
                    etMobLoginid.setError("Enter valid Name/Mobile Number");
                    etMobLoginid.requestFocus();
                } else if (TextUtils.isEmpty(etMobLoginid.getText().toString().trim())) {
                    etMobLoginid.setError(null);
                    pageNo = 1;
                    getDirect(rootId, etMobLoginid.getText().toString().trim().length() > 0 ? etMobLoginid.getText().toString().trim() : "All");
                } else {
                    etMobLoginid.setError(null);
                    pageNo = 1;
                    getDirect(rootId, etMobLoginid.getText().toString().trim().length() > 0 ? etMobLoginid.getText().toString().trim() : "All");
                }
                break;
        }
    }

    @Override
    public void getClickPositionDirectMember(int position, String tag, String memberId) {
        super.getClickPosition(position, tag);
        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            previousIds.add(memberId);
            previousNames.add(tag);
            txtName.setText(String.format("%s %s", list.get(position).getFirstName(), list.get(position).getLastName()));
            pageNo = 1;
            getDirect(memberId, etMobLoginid.getText().toString().trim().length() > 0 ? etMobLoginid.getText().toString().trim() : "All");
            btn_up.setVisibility(View.VISIBLE);
        } else {
            showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
        }
    }

    private void updateScroll(String id) {
        layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        memberRecycler.setLayoutManager(layoutManager);
        memberRecycler.setHasFixedSize(true);
        memberId = id;
        memberRecycler.addOnScrollListener(new HidingScrollListener(layoutManager) {
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
                        getDirect(memberId, etMobLoginid.getText().toString().trim().length() > 0 ? etMobLoginid.getText().toString().trim() : "All");
                    }
                }
            }

            @Override
            public void onShow() {

            }
        });

    }

    private void getDirect(String rootId, String searchText) {
        try {
            showLoading();
            String url = BuildConfig.BASE_URL_MLM + "GetTeamStatus?memberId=" + Cons.encryptMsg(rootId, cross_intent) + "&page=" + (Cons.encryptMsg(String.valueOf(pageNo), cross_intent)) + "&pagesize=" + (Cons.encryptMsg(String.valueOf(pageSize), cross_intent)) +
                    "&searchtype=" + (Cons.encryptMsg(tv_login_mob.getText().toString(), cross_intent))
                    + "&searchvalue=" + (Cons.encryptMsg(searchText, cross_intent));

            Log.e("URL ", url);
            Call<JsonObject> call = apiServices.getApi(url, AppConfig.ANDROIDID);
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

                                if (pageNo == 1) {
                                    updateScroll(rootId);
                                    list = convertedObject.getResult();
                                    tv_totalteam.setText(String.format("%s", convertedObject.gettotalCount()));

                                    if (list.size() > 0) {
                                        tv_totalReferral.setText(String.format("%s", list.get(0).getTotalrefferal()));
                                    } else {
                                        tv_totalReferral.setText(String.format("%s", "0"));
                                    }

                                    adapter = new DirectMemberAdapter(context, list, DirectMember.this, rootId);
                                    memberRecycler.setAdapter(adapter);
                                    memberRecycler.setVisibility(View.VISIBLE);
                                    txtNoRecord.setVisibility(View.GONE);
                                } else {
                                    list.addAll(convertedObject.getResult());
                                    adapter.updateList(list);
                                }
                            } else {
                                memberRecycler.setVisibility(View.GONE);
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
        getTeamandBusiness(name, tag);

    }


    private void dialogpop(String pname, String team, String business, String referra) {


        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);

        // ...Irrelevant code for customizing the buttons and title

        LayoutInflater inflater = this.getLayoutInflater();

        View dialoglog = inflater.inflate(R.layout.teamandbusines, null);
        dialogBuilder.setView(dialoglog);


        name = dialoglog.findViewById(R.id.txtName);
        totalteam = dialoglog.findViewById(R.id.txteam);
        totalbusiness = dialoglog.findViewById(R.id.txt_totalbusiness);
        referral = dialoglog.findViewById(R.id.txt_refrral);
        name.setText(pname);
        totalteam.setText(team);
        totalbusiness.setText(business);
        referral.setText(referra);


        dialog = dialogBuilder.create();
        dialog.show();


    }

    private void getTeamandBusiness(String name, String memberId) {
        try {
            showLoading();
            Log.e("==>", "memIIID" + memberId);

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
                            /*        totalteam.setText(memberDialog.getResult().getTotalTeam());
                                    totalbusiness.setText(memberDialog.getResult().getBusiness());
                                    referral.setText(memberDialog.getResult().getTotalRefferal());*/
                                    dialogpop(name, memberDialog.getResult().getTotalTeam(), memberDialog.getResult().getBusiness(), memberDialog.getResult().getTotalRefferal());

                                }


                            } else {
                                showMessage(memberDialog.getMessage());
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

}
