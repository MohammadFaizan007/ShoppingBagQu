package com.shoppingbag.constants;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonObject;
import com.shoppingbag.BuildConfig;
import com.shoppingbag.R;
import com.shoppingbag.app.AppConfig;
import com.shoppingbag.app.PreferencesManager;
import com.shoppingbag.retrofit.ApiServices;
import com.shoppingbag.retrofit.MvpView;
import com.shoppingbag.retrofit.ServiceGenerator;
import com.shoppingbag.utils.DialogUtil;
import com.shoppingbag.utils.LoggerUtil;
import com.shoppingbag.utils.NetworkConnectionChecker;
import com.shoppingbag.utils.Utils;
import com.tapadoo.alerter.Alerter;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.shoppingbag.app.AppConfig.PAYLOAD_BUNDLE;


public abstract class BaseActivity extends AppCompatActivity implements NetworkConnectionChecker.OnConnectivityChangedListener, View.OnClickListener, MvpView {
    private static final String TAG = "BaseActivity";
    public ApiServices apiServices, apiServicesLogin, apiServicesTravel,apiServicesCyper, apiServicesOneStoreIndia, apiServiceOneInd,apiLogin,api_service_tracking;
    public ServiceGenerator serviceGenerator;
    public Activity context;
    public boolean isConnected;
    public SecretKey cross_intent, easy_pay_oneStoreIndia;
    private ProgressDialog mProgressDialog;
    public SharedPreferences pref;
    public double cashBackWalletPercentage = 0.10;
    public String companyId = "10003";

    public static void finishActivity(Activity activity) {
        Utils.hideSoftKeyboard(activity);
        activity.finish();
    }

    public JsonObject bodyParam(JsonObject param) {
        JsonObject body = new JsonObject();
        try {
            body.addProperty("body", Cons.encryptMsg(param.toString(), cross_intent));
            LoggerUtil.logItem(body);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return body;
    }


    public JsonObject bodyParamOneStoreIndia(JsonObject param) {
        JsonObject body = new JsonObject();
        try {
            body.addProperty("body", Cons.encryptMsg(param.toString(), easy_pay_oneStoreIndia));
            LoggerUtil.logItem(body);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return body;
    }


    public void getAndroidId() {
        try {
            if (PreferencesManager.getInstance(context).getANDROIDID().equalsIgnoreCase("")) {
                AppConfig.ANDROIDID = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
                PreferencesManager.getInstance(context).setANDROIDID(AppConfig.ANDROIDID);
                LoggerUtil.logItem(AppConfig.ANDROIDID);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearPrefrenceforTokenExpiry() {
        boolean firstVisit = PreferencesManager.getInstance(context).getIsFirstTimeLaunch();
        PreferencesManager.getInstance(context).clear();
        PreferencesManager.getInstance(context).setIsFirstTimeLaunch(firstVisit);
        LoggerUtil.logItem(PreferencesManager.getInstance(context).getIsFirstTimeLaunch());
    }

    protected static final int PERMISSION_CONTACTS_REQUEST_CODE = 13;

    public void hasContactPermission() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED)

            requestContactPermission();
    }

    public void requestContactPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(context, Manifest.permission.CALL_PHONE)
                || ActivityCompat.shouldShowRequestPermissionRationale(context, Manifest.permission.READ_CONTACTS)) {
            Utils.createSimpleDialog1(context, getString(R.string.alert_text), getString(R.string.permission_camera_rationale11), getString(R.string.reqst_permission), new Utils.Method() {
                @Override
                public void execute() {
                    ActivityCompat.requestPermissions(context, new String[]{
                                    Manifest.permission.CALL_PHONE,
                                    Manifest.permission.READ_CONTACTS},
                            PERMISSION_CONTACTS_REQUEST_CODE);
                }
            });

        } else {
            ActivityCompat.requestPermissions(context, new String[]{
                            Manifest.permission.CALL_PHONE,
                            Manifest.permission.READ_CONTACTS},
                    PERMISSION_CONTACTS_REQUEST_CODE);
        }
    }

    @Override
    public void getClickChildPosition(String name, String tag, Bundle bundle) {

    }

    @Override
    public void getMyClickPosition(String name, String tag) {

    }

    Dialog flightDialog;

    public void showFlightLoading() {
        flightDialog = DialogUtil.showFlightDialog(this);
    }

    public void hideFlightLoading() {
        if (flightDialog != null && flightDialog.isShowing()) {
            flightDialog.dismiss();
        }
    }

    public void goToActivitySingle(Activity activity, Class<?> classActivity, Bundle bundle) {
        Intent intent = new Intent(activity, classActivity);
        if (bundle != null) intent.putExtra(PAYLOAD_BUNDLE, bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = this;
        try {
            pref = getApplicationContext().getSharedPreferences(AppConfig.SHARED_PREF, 0);
//            context.getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
            cross_intent = new SecretKeySpec(BuildConfig.CASHBAG_COMPARED.getBytes(), "AES");
            easy_pay_oneStoreIndia = new SecretKeySpec(BuildConfig.OneStore_India_Key.getBytes(), "AES");
            getAndroidId();
            serviceGenerator = ServiceGenerator.getInstance();
            apiServices = ServiceGenerator.createServiceWithToken(ApiServices.class);
            apiServicesTravel = ServiceGenerator.createService(ApiServices.class, BuildConfig.BASE_URL_TRAVEL);
            apiServicesCyper = ServiceGenerator.createService(ApiServices.class, BuildConfig.BASE_URL_CyberPlate);
            apiServicesLogin = ServiceGenerator.createService(ApiServices.class, BuildConfig.LOGIN_URL);
            apiServicesOneStoreIndia = ServiceGenerator.createService(ApiServices.class, BuildConfig.BASE_URL_OneStoreIndia);
            apiServiceOneInd = ServiceGenerator.createService(ApiServices.class, BuildConfig.BASE_URL_OneInd);
            apiLogin = ServiceGenerator.createService(ApiServices.class, BuildConfig.BASE_URL_LOGIN);
            api_service_tracking = ServiceGenerator.createServiceWithTokenTracking(ApiServices.class,PreferencesManager.getInstance(context).getTrackingToken());
            new DialogUtil(BaseActivity.this);
        } catch (Error | Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void openSearchCategory(String searchItemId, String searchName) {

    }

    public void goToActivityWithFinish(Class<?> classActivity, Bundle bundle) {
        Intent intent = new Intent(context, classActivity);
        if (bundle != null) intent.putExtra(PAYLOAD_BUNDLE, bundle);
        Utils.hideSoftKeyboard(context);
        context.startActivity(intent);
        context.finish();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public void showSnackBar(String message) {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT);
        View sbView = snackbar.getView();
        TextView textView = sbView.findViewById(R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(this, R.color.white));
        snackbar.show();
    }

    public void showToastS(String text) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout, findViewById(R.id.toast_layout_root));
        TextView text1 = layout.findViewById(R.id.text);
        text1.setText(text);
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 170);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void showLoading() {
        mProgressDialog = DialogUtil.showLoadingDialog(context, "Base Activity");
    }

    @Override
    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void openActivityOnTokenExpire() {

    }

    @Override
    public void onError(int resId) {

    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMessage(int resId) {

    }

    @Override
    public boolean isNetworkConnected() {
        return isConnected;
    }

    public void showAlert(String msg, int color, int icon) {
        Alerter.create(context).setText(msg).setTextAppearance(R.style.alertTextColor).setBackgroundColorRes(color).setIcon(icon).show();
    }

    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    @Override
    public void getClickPosition(int position, String tag) {

    }

    @Override
    public void connectivityChanged(boolean availableNow) {
        isConnected = availableNow;
    }

    public void createInfoDialog(Context context, String title, String msg) {
        androidx.appcompat.app.AlertDialog.Builder builder1 = new androidx.appcompat.app.AlertDialog.Builder(context);
        builder1.setTitle(title);
        builder1.setMessage(msg);
        builder1.setCancelable(true);

        builder1.setNegativeButton("OK", (dialog, id) -> {
            dialog.cancel();
            finish();
        });

        androidx.appcompat.app.AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public void hideSoftKeyboard(Activity activity) {
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View v = activity.getCurrentFocus();
        if (v != null) {
            IBinder binder = activity.getCurrentFocus().getWindowToken();
            if (binder != null) {
                if (inputMethodManager != null) {
                    inputMethodManager.hideSoftInputFromWindow(binder, 0);
                }
            }
        }
    }

    public void goToActivity(Activity activity, Class<?> classActivity, Bundle bundle) {
        Utils.hideSoftKeyboard(activity);
        Intent intent = new Intent(activity, classActivity);
        if (bundle != null) intent.putExtra(PAYLOAD_BUNDLE, bundle);
        activity.startActivity(intent);
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    public void goToActivityWithFinish(Activity activity, Class<?> classActivity, Bundle bundle) {
        Intent intent = new Intent(context, classActivity);
        if (bundle != null) intent.putExtra(PAYLOAD_BUNDLE, bundle);
        Utils.hideSoftKeyboard(activity);
        activity.startActivity(intent);
        activity.finish();
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    @Override
    public void getClickPositionDirectMember(int position, String tag, String memberId) {

    }

    @Override
    public void getProviderHint(String providerName, String hint) {

    }

    @Override
    public void checkAvailability(String id, String date, String name, String amount) {

    }

    @Override
    public void getGiftCardCategoryId(String id, String name) {

    }
}

