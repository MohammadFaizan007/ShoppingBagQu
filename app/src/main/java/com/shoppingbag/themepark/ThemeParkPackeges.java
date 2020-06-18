package com.shoppingbag.themepark;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shoppingbag.app.CheckErrorCode;
import com.shoppingbag.app.PreferencesManager;
import com.shoppingbag.constants.BaseActivity;
import com.shoppingbag.constants.Cons;
import com.shoppingbag.model.request.utility.response.ResponseBalanceAmount;
import com.shoppingbag.model.themeParkResponse.themeParkAvailabilityArray.ResponseAvailabilityArray;
import com.shoppingbag.model.themeParkResponse.themeParkAvailabilityObject.ResponseAvailabilityObject;
import com.shoppingbag.model.themeParkResponse.themeParkDetails.ResponseThemeParkDetails;
import com.shoppingbag.utils.LoggerUtil;
import com.shoppingbag.utils.NetworkUtils;
import com.shoppingbag.utils.Utils;
import com.shoppingbag.R;
import com.shoppingbag.adapter.ThemePackagesAdapter;
import com.shoppingbag.common_activities.LoginActivity;
import com.shoppingbag.common_activities.MaintenancePage;
import com.shoppingbag.wallet.AddMoney;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.shoppingbag.app.AppConfig.PAYLOAD_BUNDLE;


public class ThemeParkPackeges extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.rv_gift_categories)
    RecyclerView rvGiftCategories;
    @BindView(R.id.txtNoData)
    TextView txtNoData;
    //    List<ResponseAvailabilityArray> responseAvailabilityArray;
    private Bundle param;
    private String amountPackage = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.giftcard_categories);
        ButterKnife.bind(this);

        param = getIntent().getBundleExtra(PAYLOAD_BUNDLE);
        title.setText(param.getString("giftName"));

        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(RecyclerView.VERTICAL);
        rvGiftCategories.setLayoutManager(manager);
//fail
//        checkAvailable("57208", "2019-03-12", "Vilaspur");
//success
//        checkAvailable("39690", "2019-03-12", "Vilaspur");

        if (NetworkUtils.getConnectivityStatus(context) != 0)
            getThemeParkPackages();
        else
            showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
    }

    @OnClick(R.id.side_menu)
    public void onViewClicked() {
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    private void getThemeParkPackages() {
        showLoading();
//        RequestThemeParkLists categories = new RequestThemeParkLists();
//        categories.setAMOUNT("1.00");
//        categories.setAMOUNTALL("1.00");
//        categories.setType("2");
//        categories.setCategoryId(param.getString("giftId"));
//        categories.setLimit("2");
//        categories.setOffset("50");
//        LoggerUtil.logItem(categories);
        try {

            JsonObject paramObj = new JsonObject();
            paramObj.addProperty("AMOUNT", "1.00");
            paramObj.addProperty("AMOUNT_ALL", "1.00");
            paramObj.addProperty("Type", "2");
            paramObj.addProperty("CategoryId", param.getString("giftId"));
            paramObj.addProperty("limit", "2");
            paramObj.addProperty("offset", "50");
            LoggerUtil.logItem(paramObj);

            Call<JsonObject> call = apiServicesCyper.getThemeParkLists(bodyParam(paramObj), PreferencesManager.getInstance(context).getANDROIDID());
            call.enqueue(new Callback<JsonObject>() {
                @Override

                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                    hideLoading();
                    LoggerUtil.logItem(response.body());
                    try {
                        if (response.isSuccessful()) {
                            String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent);
                            LoggerUtil.logItem(paramResponse);
                            List<ResponseThemeParkDetails> list = Utils.getList(paramResponse, ResponseThemeParkDetails.class);

                            if (list.get(0).getError().equalsIgnoreCase("0") &&
                                    list.get(0).getResult().equalsIgnoreCase("0")) {
                                if (list.get(0).getAddinfo().size() > 0) {
                                    ThemePackagesAdapter adapter = new ThemePackagesAdapter(context, list.get(0).getAddinfo(), ThemeParkPackeges.this);
                                    rvGiftCategories.setAdapter(adapter);
                                    txtNoData.setVisibility(View.GONE);
                                } else {
                                    txtNoData.setVisibility(View.VISIBLE);
                                }

                            } else {
                                txtNoData.setVisibility(View.VISIBLE);
                                CheckErrorCode checkErrorCode = new CheckErrorCode();
                                checkErrorCode.isValidTransaction(context, list.get(0).getError());
                            }
                        } else if (response.code() == 403) {
                            clearPrefrenceforTokenExpiry();
                            getAndroidId();
                            goToActivityWithFinish(LoginActivity.class, null);
                        } else {
                            goToActivityWithFinish(MaintenancePage.class, null);
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void checkAvailability(String id, String date, String name, String amount) {
        checkAvailable(id, date, name);
        amountPackage = amount;
    }

    void checkAvailable(String id, String date, String name) {
        showLoading();
        try {

            JsonObject paramObj = new JsonObject();
            paramObj.addProperty("AMOUNT", "1.00");
            paramObj.addProperty("AMOUNT_ALL", "1.00");
            paramObj.addProperty("Type", "4");
            paramObj.addProperty("ProductId", id);
            paramObj.addProperty("DateOfTour", date);
            LoggerUtil.logItem(paramObj);

            Call<JsonObject> call = apiServicesCyper.getThemeParkAvailability(bodyParam(paramObj), PreferencesManager.getInstance(context).getANDROIDID());
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                    hideLoading();
                    Cons.responseAvailabilityArrays = new ArrayList<>();
                    LoggerUtil.logItem(response.body());
                    try {
                        if (response.isSuccessful()) {
                            String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent);
                            JsonArray convertedObject = new Gson().fromJson(paramResponse, JsonArray.class);
                            LoggerUtil.logItem(convertedObject);

                            JsonObject object = convertedObject.get(0).getAsJsonObject();
                            if (object.get("ERROR").getAsString().equalsIgnoreCase("0") &&
                                    object.get("RESULT").getAsString().equalsIgnoreCase("0")) {
                                try {
                                    String addinfo = Utils.replaceBackSlash(object.get("ADDINFO").getAsString());
                                    if (!TextUtils.isEmpty(addinfo)) {
                                        String firstChar = String.valueOf(addinfo.charAt(0));
                                        if (firstChar.equalsIgnoreCase("[")) {
                                            Cons.responseAvailabilityArrays = Utils.getList(addinfo, ResponseAvailabilityArray.class);
                                            //Log.e("Array==", "Success");
                                            showInformation(name, "Tour available.", id);
                                        } else {
                                            Gson gson = new GsonBuilder().create();
                                            ResponseAvailabilityObject responseAvailabilityObject = gson.fromJson(addinfo, ResponseAvailabilityObject.class);
                                            //Log.e("Object==", "Fail");
                                            showInformation(name, responseAvailabilityObject.getData().getError() + ".\nHowever it's available on " +
                                                    responseAvailabilityObject.getData().getAvailabilities().getNextAvailabilities(), id);
                                        }
                                    }
                                } catch (Error | Exception e) {
                                    showInformation(name, "Tour not found.", id);
                                    e.printStackTrace();
                                }
                            } else {
                                CheckErrorCode checkErrorCode = new CheckErrorCode();
                                checkErrorCode.isValidTransaction(context, object.get("ERROR").getAsString());
                            }
                        } else if (response.code() == 403) {
                            clearPrefrenceforTokenExpiry();
                            getAndroidId();
                            goToActivityWithFinish(LoginActivity.class, null);
                        } else {
                            goToActivityWithFinish(MaintenancePage.class, null);
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showInformation(String tourName, String msg_st, String productId) {
        Dialog error_dialog = new Dialog(context);
        error_dialog.setCanceledOnTouchOutside(true);
        error_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        error_dialog.setContentView(R.layout.error_dialoge);
        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
        int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.90);
        error_dialog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        error_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView error_text = error_dialog.findViewById(R.id.error_text);
        Button ok_btn = error_dialog.findViewById(R.id.ok_btn);
        Button book_now_btn = error_dialog.findViewById(R.id.book_now_btn);
        error_text.setText(String.format("%s\n%s", tourName, msg_st));
        error_dialog.show();
        if (msg_st.equalsIgnoreCase("Tour available."))
            book_now_btn.setVisibility(View.VISIBLE);
        ok_btn.setOnClickListener(view -> {
            error_dialog.dismiss();
        });
        book_now_btn.setOnClickListener(v -> {
            error_dialog.dismiss();
            getWalletBalance(productId);

        });
    }

    private void getWalletBalance(String productId) {
        try {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("MemberId", Cons.decryptMsg(PreferencesManager.getInstance(context).getUSERID(), cross_intent));
            LoggerUtil.logItem(jsonObject);

            Call<JsonObject> walletBalanceCall = apiServicesCyper.getbalanceAmount(bodyParam(jsonObject), PreferencesManager.getInstance(context).getANDROIDID());
            walletBalanceCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                    hideLoading();
                    LoggerUtil.logItem(response.body());
                    try {
                        if (response.isSuccessful()) {
                            String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent);
                            ResponseBalanceAmount convertedObject = new Gson().fromJson(paramResponse, ResponseBalanceAmount.class);
                            if (convertedObject.getStatus().equalsIgnoreCase("Success")) {
                                if (convertedObject.getBalanceAmount() >= Float.parseFloat(amountPackage)) {

                                    Intent intent = new Intent(context, BookPark.class);
                                    intent.putExtra("productId", productId);
                                    context.startActivity(intent);
                                } else
                                    createAddBalanceDialog(context, "Insufficient bag balance", "You have insufficient balance in your bag, add money before making transactions.");
                            }
                        } else if (response.code() == 403) {
                            clearPrefrenceforTokenExpiry();
                            getAndroidId();
                            goToActivityWithFinish(LoginActivity.class, null);
                        } else {
                            goToActivityWithFinish(MaintenancePage.class, null);
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createAddBalanceDialog(Context context, String title, String msg) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setTitle(title);
        builder1.setMessage(msg);
        builder1.setCancelable(true);

        builder1.setNegativeButton("Add Balance", (dialog, id) -> {
            dialog.cancel();
            Bundle b = new Bundle();
            b.putString("total", amountPackage + "");
            b.putString("from", "theme");

            goToActivity(ThemeParkPackeges.this, AddMoney.class, b);
        });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

}
