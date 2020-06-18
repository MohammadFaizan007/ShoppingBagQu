package com.shoppingbag.gift_card;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shoppingbag.app.CheckErrorCode;
import com.shoppingbag.app.PreferencesManager;
import com.shoppingbag.constants.BaseActivity;
import com.shoppingbag.constants.Cons;
import com.shoppingbag.model.giftCardResponse.ResponseGiftsCards;
import com.shoppingbag.utils.LoggerUtil;
import com.shoppingbag.utils.NetworkUtils;
import com.shoppingbag.utils.Utils;
import com.shoppingbag.R;
import com.shoppingbag.adapter.CouponsListAdapter;
import com.shoppingbag.common_activities.LoginActivity;
import com.shoppingbag.common_activities.MaintenancePage;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.shoppingbag.app.AppConfig.PAYLOAD_BUNDLE;


public class GiftCoupons extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.rv_gift_categories)
    RecyclerView rvGiftCategories;

    Bundle param;

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

        if (NetworkUtils.getConnectivityStatus(context) != 0)
            getThemeParkPackeges();
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

    private void getThemeParkPackeges() {
        showLoading();
        try {

            JsonObject object = new JsonObject();
            object.addProperty("Amount", "10.00");
            object.addProperty("Amount_All", "10.00");
            object.addProperty("Category_id", param.getString("giftId"));
            object.addProperty("Type", "2");

            LoggerUtil.logItem(object);

            Call<JsonObject> call = apiServicesCyper.getGiftCoupons(bodyParam(object), PreferencesManager.getInstance(context).getANDROIDID());
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                    hideLoading();
                    LoggerUtil.logItem(response.body());
                    try {
                        if (response.isSuccessful()) {
                            String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent);
                            JsonArray convertedObject = new Gson().fromJson(paramResponse, JsonArray.class);
                            LoggerUtil.logItem(convertedObject);
                            if (response.body() != null && convertedObject.size() > 0) {
                                JsonObject object = convertedObject.get(0).getAsJsonObject();
                                if (object.get("ERROR").getAsString().equalsIgnoreCase("0") &&
                                        object.get("RESULT").getAsString().equalsIgnoreCase("0")) {
                                    JSONArray addinfo = null;
                                    List<ResponseGiftsCards> giftsCards = new ArrayList<>();

                                    addinfo = new JSONArray((Utils.replaceBackSlash(object.get("ADDINFO").getAsString())));
                                    for (int i = 0; i < addinfo.length(); i++) {
                                        ResponseGiftsCards cards = new ResponseGiftsCards();
                                        JSONObject object1 = addinfo.getJSONObject(i);
                                        cards.setName(object1.getString("name"));
                                        cards.setId(object1.getString("id"));
                                        cards.setCustomDenominations(object1.getString("custom_denominations"));
                                        cards.setMaxCustomPrice(object1.getString("max_custom_price"));
                                        cards.setMinCustomPrice(object1.getString("min_custom_price"));
                                        cards.setPriceType(object1.getString("price_type"));
                                        cards.setImages(object1.getJSONArray("images").getJSONObject(0).getString("image"));
                                        giftsCards.add(cards);
                                    }
                                    CouponsListAdapter adapter = new CouponsListAdapter(context, giftsCards, GiftCoupons.this);
                                    rvGiftCategories.setAdapter(adapter);

                                } else {
                                    CheckErrorCode checkErrorCode = new CheckErrorCode();
                                    checkErrorCode.isValidTransaction(context, object.get("ERROR").getAsString());
                                }
                            } else {
                                showMessage("Something went wrong. \nPlease try after some time.");
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
                        showMessage("Something went wrong. \nPlease try after some time.");
                    }

                }

                @Override
                public void onFailure(@NotNull Call<JsonObject> call, @NotNull Throwable t) {
                    hideLoading();
                    showMessage("Something went wrong. \nPlease try after some time.");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            hideLoading();
        }
    }


    @Override
    public void getGiftCardCategoryId(String id, String name) {
        param = new Bundle();
        param.putString("productId", id);
        param.putString("productName", name);
        LoggerUtil.logItem(id + name);
        goToActivity(context, CreateCouponsCard.class, param);
    }
}
