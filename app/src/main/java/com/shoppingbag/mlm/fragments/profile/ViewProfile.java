package com.shoppingbag.mlm.fragments.profile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.shoppingbag.BuildConfig;
import com.shoppingbag.R;
import com.shoppingbag.app.AppConfig;
import com.shoppingbag.app.PreferencesManager;
import com.shoppingbag.common_activities.LoginActivity;
import com.shoppingbag.common_activities.MainContainer;
import com.shoppingbag.constants.BaseFragment;
import com.shoppingbag.constants.Cons;
import com.shoppingbag.constants.UrlConstants;
import com.shoppingbag.model.response.profile.ResponseViewProfile;
import com.shoppingbag.model.response.profile.Result;
import com.shoppingbag.utils.LoggerUtil;
import com.shoppingbag.utils.NetworkUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ViewProfile extends BaseFragment {

    @BindView(R.id.personal_info_lo)
    ConstraintLayout personalInfoLo;
    @BindView(R.id.personal_info_img)
    ImageView personalInfoImg;
    @BindView(R.id.bank_details_lo)
    ConstraintLayout bankDetailsLo;
    @BindView(R.id.bank_detail_img)
    ImageView bankDetailImg;

    @BindView(R.id.user_image)
    ImageView userImage;
    @BindView(R.id.additional_detail)
    TextView additionalDetail;
    @BindView(R.id.first_name)
    TextView FirstName;
    @BindView(R.id.last_name)
    TextView LastName;
    @BindView(R.id.dob)
    TextView Dob;
    @BindView(R.id.father_name)
    TextView FatherName;

    @BindView(R.id.gender)
    TextView gender;
    @BindView(R.id.married_atatus)
    TextView marriedatatus;
    @BindView(R.id.acco_number)
    TextView AccoNumber;
    @BindView(R.id.bank_name)
    TextView BankName;
    @BindView(R.id.ifsc_code)
    TextView IfscCode;
    @BindView(R.id.branch_name)
    TextView BranchName;

    @BindView(R.id.edit_profile_btn)
    Button edit_profile_btn;
    Unbinder unbinder;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_profile, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreatedStuff(View view, @Nullable Bundle savedInstanceState) {
        hideKeyboard();
        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            getUserProfile();
        } else {
            createInfoDialog(context, "Alert", getString(R.string.alert_internet));
        }
    }

    private void getUserProfile() {
        try {
            showLoading();
            String url = BuildConfig.BASE_URL_MLM + "ViewProfile?memberId=" + PreferencesManager.getInstance(context).getUSERID();
            LoggerUtil.log(url);
            Call<JsonObject> call = apiServices.getProfile(url, AppConfig.ANDROIDID);
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                    hideLoading();
                    LoggerUtil.logItem(response.body());
                    LoggerUtil.logItem(response.code());
                    LoggerUtil.logItem(call.request().url());
                    try {
                        if (response.isSuccessful()) {
                            String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent);
                            ResponseViewProfile convertedObject = new Gson().fromJson(paramResponse, ResponseViewProfile.class);
                            LoggerUtil.logItem(convertedObject);
                            if (response.body() != null && convertedObject.getResponse().equalsIgnoreCase("Success")) {
                                UrlConstants.profile = convertedObject;
                                setUserProfile();
                            } else {
                                edit_profile_btn.setVisibility(View.GONE);
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

    private void setUserProfile() {
        try {
            Result profile = UrlConstants.profile.getResult();
            PreferencesManager.getInstance(context).setNAME(Cons.encryptMsg(profile.getFirstName(), cross_intent));
            PreferencesManager.getInstance(context).setLNAME(Cons.encryptMsg(profile.getLastName(), cross_intent));
            PreferencesManager.getInstance(context).setPINCODE(Cons.encryptMsg(profile.getPinCode(), cross_intent));
            PreferencesManager.getInstance(context).setMOBILE(Cons.encryptMsg(profile.getMobile(), cross_intent));
            PreferencesManager.getInstance(context).setEMAIL(Cons.encryptMsg(profile.getEmail(), cross_intent));
            PreferencesManager.getInstance(context).setDOB(Cons.encryptMsg(profile.getDob(), cross_intent));
            PreferencesManager.getInstance(context).setState(Cons.encryptMsg(profile.getState(), cross_intent));
            PreferencesManager.getInstance(context).setCity(Cons.encryptMsg(profile.getCity(), cross_intent));
            PreferencesManager.getInstance(context).setAddress(Cons.encryptMsg(profile.getAddress1(), cross_intent));
            Log.e("============", "=====================================" + BuildConfig.BASE_URL_FORIMAGE + UrlConstants.profile.getResult().getProfilepic());

            Glide.with(context).load(UrlConstants.profile.getResult().getProfilepic().replace("~", BuildConfig.BASE_URL_FORIMAGE)).
                    apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).circleCrop().skipMemoryCache(true)
                            .placeholder(R.mipmap.ic_launcher_foreground)
                            .error(R.mipmap.ic_launcher_foreground))
                    .into(userImage);

            PreferencesManager.getInstance(context).setPROFILEPIC(UrlConstants.profile.getResult().getProfilepic());

            FirstName.setText(profile.getFirstName());
            LastName.setText(profile.getLastName());
            Dob.setText(profile.getDob());
            gender.setText(profile.getGender());
            marriedatatus.setText(profile.getMarritalStatus());
            FatherName.setText(profile.getEmail());
            AccoNumber.setText(profile.getMemberAccNo());
            BankName.setText(profile.getMemberBankName());
            IfscCode.setText(profile.getIfscCode());
            BranchName.setText(profile.getMemberBranch());

            edit_profile_btn.setVisibility(View.VISIBLE);

        } catch (Exception | Error e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.user_image, R.id.personal_info_img, R.id.bank_detail_img, R.id.edit_profile_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.user_image:
                break;
            case R.id.personal_info_img:
                if (personalInfoLo.getVisibility() == View.VISIBLE) {
                    personalInfoLo.setVisibility(View.GONE);
                    personalInfoImg.setBackgroundResource(R.drawable.ic_expand);
                } else {
                    personalInfoLo.setVisibility(View.VISIBLE);
                    personalInfoImg.setBackgroundResource(R.drawable.ic_collapse);
                }
                break;
            case R.id.bank_detail_img:
                if (bankDetailsLo.getVisibility() == View.VISIBLE) {
                    bankDetailsLo.setVisibility(View.GONE);
                    bankDetailImg.setBackgroundResource(R.drawable.ic_expand);
                } else {
                    bankDetailsLo.setVisibility(View.VISIBLE);
                    bankDetailImg.setBackgroundResource(R.drawable.ic_collapse);
                }
                break;

            case R.id.edit_profile_btn:
                ((MainContainer) context).ReplaceFragment(new EditProfileMlm(), "Edit Profile");
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
