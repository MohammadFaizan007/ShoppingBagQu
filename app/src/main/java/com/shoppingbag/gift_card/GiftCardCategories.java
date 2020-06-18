package com.shoppingbag.gift_card;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shoppingbag.app.CheckErrorCode;
import com.shoppingbag.app.PreferencesManager;
import com.shoppingbag.constants.BaseActivity;
import com.shoppingbag.constants.Cons;
import com.shoppingbag.model.giftCardResponse.ResponseGiftCardcategory;
import com.shoppingbag.utils.LoggerUtil;
import com.shoppingbag.utils.NetworkUtils;
import com.shoppingbag.utils.Utils;
import com.shoppingbag.R;
import com.shoppingbag.adapter.GiftCardCategoriesAdapter;
import com.shoppingbag.common_activities.LoginActivity;
import com.shoppingbag.common_activities.MaintenancePage;
import com.google.gson.JsonObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.shoppingbag.app.AppConfig.PAYLOAD_BUNDLE;


public class GiftCardCategories extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.rv_gift_categories)
    RecyclerView rvGiftCategories;

    Bundle param;
    @BindView(R.id.txtNoData)
    TextView txtNoData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.giftcard_categories);
        ButterKnife.bind(this);

        param = getIntent().getBundleExtra(PAYLOAD_BUNDLE);
        title.setText(param.getString("from"));

        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(RecyclerView.VERTICAL);
        rvGiftCategories.setLayoutManager(manager);

        if (NetworkUtils.getConnectivityStatus(context) != 0)
            getGiftCardCategories();
        else
            showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
    }

    @OnClick(R.id.side_menu)
    public void onViewClicked() {
        onBackPressed();
    }


    private void getGiftCardCategories() {
        showLoading();
        try {

            JsonObject param = new JsonObject();
            param.addProperty("Amount", "10.00");
            param.addProperty("Amount_All", "10.00");
            param.addProperty("Type", "1");


            Call<JsonObject> call = apiServicesCyper.getGiftCardCategories(bodyParam(param), PreferencesManager.getInstance(context).getANDROIDID());
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                    hideLoading();
                    LoggerUtil.logItem(response.body());
                    try {
                        if (response.isSuccessful()) {
                            String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent);
                            LoggerUtil.logItem(paramResponse);
                            List<ResponseGiftCardcategory> list = Utils.getList(paramResponse, ResponseGiftCardcategory.class);

                            if (response.body() != null && list.size() > 0 && list.get(0).getError().equalsIgnoreCase("0") &&
                                    list.get(0).getResult().equalsIgnoreCase("0")) {
                                GiftCardCategoriesAdapter adapter = new GiftCardCategoriesAdapter(context, list.get(0).getAddinfo(), GiftCardCategories.this);
                                rvGiftCategories.setAdapter(adapter);
                                txtNoData.setVisibility(View.GONE);
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
                        txtNoData.setVisibility(View.GONE);
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
    public void getGiftCardCategoryId(String id, String name) {
        param = new Bundle();
        param.putString("giftId", id);
        param.putString("giftName", name);
        goToActivity(context, GiftCoupons.class, param);
    }
}
