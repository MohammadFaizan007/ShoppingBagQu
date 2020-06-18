package com.shoppingbag.common_activities;

import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.shoppingbag.R;
import com.shoppingbag.app.AppConfig;
import com.shoppingbag.app.PreferencesManager;
import com.shoppingbag.constants.BaseActivity;
import com.shoppingbag.constants.Cons;
import com.shoppingbag.model.response.ResponseLogin;
import com.shoppingbag.utils.LoggerUtil;
import com.shoppingbag.utils.NetworkUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends BaseActivity {

    @BindView(R.id.progressBar2)
    ProgressBar progressBar;
    private AppUpdateManager mAppUpdateManager;
    private static int RC_APP_UPDATE = 10;
    InstallStateUpdatedListener installStateUpdatedListener = new
            InstallStateUpdatedListener() {
                @Override
                public void onStateUpdate(InstallState state) {
                    if (state.installStatus() == InstallStatus.DOWNLOADED) {
                        popupSnackBarForCompleteUpdate();
                    } else if (state.installStatus() == InstallStatus.INSTALLED) {
                        if (mAppUpdateManager != null) {
                            mAppUpdateManager.unregisterListener(installStateUpdatedListener);
                        }

                    } else {
                        Log.i("MainContainer", "InstallStateUpdatedListener: state: " + state.installStatus());
                    }
                }
            };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        PreferencesManager.getInstance(context).settracking_bool(false);

        String temp_lid = PreferencesManager.getInstance(context).getSavedLOGINID();
        String temp_pwd = PreferencesManager.getInstance(context).getSavedPASSWORD();
        boolean firstVisit = PreferencesManager.getInstance(context).getIsFirstTimeLaunch();
        PreferencesManager.getInstance(context).clear();
        PreferencesManager.getInstance(context).setSavedLOGINID(temp_lid);
        PreferencesManager.getInstance(context).setSavedPASSWORD(temp_pwd);
        PreferencesManager.getInstance(context).setIsFirstTimeLaunch(firstVisit);
        LoggerUtil.logItem(PreferencesManager.getInstance(context).getIsFirstTimeLaunch());

        getAndroidId();
        checkUpdate();

        int SPLASH_TIME_OUT = 2000;
        new Handler().postDelayed(() -> {
            try {
                if (Cons.decryptMsg(PreferencesManager.getInstance(context).getSavedLOGINID(), cross_intent).length() != 0) {
                    if (NetworkUtils.getConnectivityStatus(context) != 0) {
                        getLogin();
                    } else {
                        showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
                    }
                } else {
                    LoggerUtil.logItem(PreferencesManager.getInstance(context).getIsFirstTimeLaunch());
                    if (!PreferencesManager.getInstance(context).getIsFirstTimeLaunch()) {
                        goToActivityWithFinish(SplashActivity.this, VerifyMobile.class, null);
                    } else {
                        goToActivityWithFinish(SplashActivity.this, SliderActivity.class, null);
//                        goToActivityWithFinish(SplashActivity.this, VerifyMobile.class, null);
                    }
                }
            } catch (Exception e) {
                goToActivityWithFinish(SplashActivity.this, VerifyMobile.class, null);
                e.printStackTrace();
            }
        }, SPLASH_TIME_OUT);
    }
    private void checkUpdate() {
        mAppUpdateManager = AppUpdateManagerFactory.create(this);
        mAppUpdateManager.registerListener(installStateUpdatedListener);
        mAppUpdateManager.getAppUpdateInfo().addOnSuccessListener(appUpdateInfo -> {

            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {

                try {
                    mAppUpdateManager.startUpdateFlowForResult(
                            appUpdateInfo, AppUpdateType.IMMEDIATE, SplashActivity.this, RC_APP_UPDATE);
                } catch (IntentSender.SendIntentException e) {
                    e.printStackTrace();
                }
                Log.e("MainContainer", "Update avalaible");

            } else if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                popupSnackBarForCompleteUpdate();
            } else {
                Log.e("MainContainer", "checkForAppUpdateAvailability: something else");
            }
        });
    }

    private void popupSnackBarForCompleteUpdate() {
        Snackbar snackbar =
                Snackbar.make(
                        findViewById(R.id.frame),
                        "New app is ready!",
                        Snackbar.LENGTH_INDEFINITE);

        snackbar.setAction("Install", view -> {
            if (mAppUpdateManager != null) {
                mAppUpdateManager.completeUpdate();
            }
        });


        snackbar.setActionTextColor(getResources().getColor(R.color.colorPrimary));
        snackbar.show();
    }
    private void getLogin() {
        try {
            progressBar.setVisibility(View.VISIBLE);
            JsonObject param = new JsonObject();
            param.addProperty("LoginID", Cons.decryptMsg(PreferencesManager.getInstance(context).getSavedLOGINID(), cross_intent));
            param.addProperty("Password", Cons.decryptMsg(PreferencesManager.getInstance(context).getSavedPASSWORD(), cross_intent));
            param.addProperty("DeviceId", pref.getString("regId", ""));
            param.addProperty("AndroidId", AppConfig.ANDROIDID);
            param.addProperty("DeviceType", "A");
            param.addProperty("Update", "Yes");
            LoggerUtil.logItem(param);
            Call<JsonObject> loginCall = apiServicesLogin.getLogin(bodyParam(param), PreferencesManager.getInstance(context).getANDROIDID());
            loginCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                    hideLoading();
                    LoggerUtil.logItem(response.body());
                    LoggerUtil.logItem(response.code());
                    ResponseLogin responseLogin;
                    try {
                        if (progressBar != null)
                            progressBar.setVisibility(View.GONE);

                        if (response.isSuccessful()) {
                            String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent);
                            LoggerUtil.logItem(paramResponse);
                            Gson gson = new GsonBuilder().create();
                            responseLogin = gson.fromJson(paramResponse, ResponseLogin.class);
                            if (response.body() != null && responseLogin.getResponse().equalsIgnoreCase("Success")) {

                                if (responseLogin.getResult().getMessage().equalsIgnoreCase("Device Id not Match")) {
//                                    showErrorNew("You are already active in some other mobile," + " Kindly logout from that device.");
                                    allowLogin(responseLogin);
                                } else if (responseLogin.getResult().getMessage().equalsIgnoreCase("Device Id Match")) {
                                    allowLogin(responseLogin);
                                }

                            } else {
                                if (response.body() == null) {
                                    goToActivityWithFinish(context, MaintenancePage.class, null);
                                } else if (responseLogin.getMessage().contains("website is under maintenance")) {
                                    goToActivityWithFinish(context, MaintenancePage.class, null);
                                } else if (responseLogin.getMessage().contains("stop")) {
                                    Bundle b = new Bundle();
                                    b.putString("msg", responseLogin.getMessage());
                                    goToActivityWithFinish(context, MaintenancePage.class, b);
                                } else {
                                    clearPrefrenceforTokenExpiry();
                                    getAndroidId();
                                    goToActivityWithFinish(context, VerifyMobile.class, null);
                                }
                            }
                        } else {
                            clearPrefrenceforTokenExpiry();
                            getAndroidId();
                            goToActivityWithFinish(context, VerifyMobile.class, null);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                    hideLoading();
                }
            });
        } catch (Error | Exception e) {
            e.printStackTrace();
            goToActivityWithFinish(SplashActivity.this, MainContainer.class, null);
        }
    }

    private void allowLogin(ResponseLogin response) {
        if (response.getResponse().equalsIgnoreCase("Success")) {
            try {
                AppConfig.authToken = response.getResult().getToken();
                PreferencesManager.getInstance(context).setUSERID(Cons.encryptMsg(String.valueOf(response.getResult().getMemberId()), cross_intent));
                PreferencesManager.getInstance(context).setLoginID(Cons.encryptMsg(response.getResult().getLoginId(), cross_intent));
                PreferencesManager.getInstance(context).setMOBILE(Cons.encryptMsg(response.getResult().getMobile(), cross_intent));
                PreferencesManager.getInstance(context).setNAME(Cons.encryptMsg(response.getResult().getName(), cross_intent));
                PreferencesManager.getInstance(context).setLNAME(Cons.encryptMsg(response.getResult().getLastName(), cross_intent));
                PreferencesManager.getInstance(context).setEMAIL(Cons.encryptMsg(response.getResult().getEmail(), cross_intent));
                PreferencesManager.getInstance(context).setPINCODE(Cons.encryptMsg(response.getResult().getPincode(), cross_intent));
                PreferencesManager.getInstance(context).setState(Cons.encryptMsg(response.getResult().getState(), cross_intent));
                PreferencesManager.getInstance(context).setCity(Cons.encryptMsg(response.getResult().getCity(), cross_intent));
                PreferencesManager.getInstance(context).setLastLogin(Cons.encryptMsg(response.getResult().getLastLogin(), cross_intent));
                PreferencesManager.getInstance(context).setInviteCode(response.getResult().getInviteCode());
                PreferencesManager.getInstance(context).setPROFILEPIC(response.getResult().getProfilePic());
                PreferencesManager.getInstance(context).setStatus(response.getResult().getStatus());
                PreferencesManager.getInstance(context).setBankAccount(response.getResult().getBankaccount());
                PreferencesManager.getInstance(context).setBankIfsc(response.getResult().getBenIFSC());
                PreferencesManager.getInstance(context).setBankName(response.getResult().getBankname());
                goToActivityWithFinish(SplashActivity.this, MainContainer.class, null);
            } catch (Error | Exception e) {
                e.printStackTrace();
            }
        } else {
            clearPrefrenceforTokenExpiry();
            Intent intent1 = new Intent(context, VerifyMobile.class);
            intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent1);
            finish();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        mAppUpdateManager
                .getAppUpdateInfo()
                .addOnSuccessListener(
                        appUpdateInfo -> {

                            if (appUpdateInfo.updateAvailability()
                                    == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                                // If an in-app update is already running, resume the update.
                                try {
                                    mAppUpdateManager.startUpdateFlowForResult(
                                            appUpdateInfo,
                                            AppUpdateType.IMMEDIATE,
                                            this,
                                            RC_APP_UPDATE);
                                } catch (IntentSender.SendIntentException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
    }

    private void getToken() {
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(SplashActivity.this, instanceIdResult -> {
            String newToken = instanceIdResult.getToken();
            ////Log.e("newToken", newToken);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("regId", newToken);
            editor.apply();
        });
    }

}
