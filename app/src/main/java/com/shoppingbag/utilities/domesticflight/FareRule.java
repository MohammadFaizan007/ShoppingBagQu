package com.shoppingbag.utilities.domesticflight;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.TabHost;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.shoppingbag.constants.BaseActivity;
import com.shoppingbag.constants.Cons;
import com.shoppingbag.model.domesticflight.requestmodel.FareRuleInput;
import com.shoppingbag.model.domesticflight.requestmodel.RequestgetFareRule;
import com.shoppingbag.model.domesticflight.responsemodel.AvailSegmentsItem;
import com.shoppingbag.model.domesticflight.responsemodel.ResponseFlightAvalibility;
import com.shoppingbag.model.domesticflight.responsemodel.ResponseGetFareRule;
import com.shoppingbag.utils.LoggerUtil;
import com.shoppingbag.utils.NetworkUtils;
import com.shoppingbag.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;

import java.security.GeneralSecurityException;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FareRule extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tvBaggagePolicy)
    TextView tvBaggagePolicy;
    @BindView(R.id.tabHost)
    TabHost tabHost;
    @BindView(R.id.tvFareRule)
    TextView tvFareRule;
    AvailSegmentsItem availSegmentsItem;
    String trackId;
    private Gson gson = new GsonBuilder().create();
    private String strRules;

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fare_rule);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (getIntent() != null) {
            availSegmentsItem = (AvailSegmentsItem) getIntent().getSerializableExtra("Flight");
            trackId = getIntent().getStringExtra("TrackId");
            tvBaggagePolicy.setText(getIntent().getStringExtra("Baggage"));

        }


        tabHost.setup();


        //Tab 1
//        TabHost.TabSpec spec = tabHost.newTabSpec("Baggage Policy");
        TabHost.TabSpec spec = tabHost.newTabSpec("Fare Rule");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Fare Rule");
        tabHost.addTab(spec);

        //Tab 2
        spec = tabHost.newTabSpec("Baggage Policy");
//        spec = tabHost.newTabSpec("Fare Rule");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Baggage Policy");
        tabHost.addTab(spec);

        for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
            TextView tv = tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            tv.setTextColor(Color.parseColor("#ffffff"));
        }

        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            getFareRuleHere();
        }
    }
    /*{
        "UserTrackId": "RMWVO97098859975957980959850704866642185",
            "FareRuleInput": {
        "AirlineCode": "SG",
                "FlightId": "11",
                "ClassCode": "UUSAVERSS",
                "SupplierId": "0"
    }
    }*/

    private void getFareRuleHere() {
        try {
          //  RequestgetFareRule rule = new RequestgetFareRule();
          //  rule.setUserTrackId(trackId);
//        Authentication authentication = new Authentication();
//        authentication.setLoginId(Utils.USERNAME);
//        authentication.setPassword(Utils.PASSWORD);
//        rule.setAuthentication(authentication);
           /* FareRuleInput input = new FareRuleInput();
            input.setAirlineCode(availSegmentsItem.getAirlineCode());
            input.setClassCode(availSegmentsItem.getAvailPaxFareDetails().get(0).getClassCode());
            input.setFlightId(availSegmentsItem.getFlightId());
            input.setSupplierId(availSegmentsItem.getSupplierId());
            rule.setFareRuleInput(input);

            showLoading();

            LoggerUtil.logItem(rule);*/


            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("UserTrackId",trackId);
            JsonObject gstdatails = new JsonObject();
            gstdatails.addProperty("AirlineCode",availSegmentsItem.getAirlineCode());
            gstdatails.addProperty("FlightId",availSegmentsItem.getFlightId());
            gstdatails.addProperty("ClassCode",availSegmentsItem.getAvailPaxFareDetails().get(0).getClassCode());
            gstdatails.addProperty("SupplierId",availSegmentsItem.getSupplierId());
            jsonObject.add("FareRuleInput",gstdatails);

            Call<JsonObject> busCall = apiServicesTravel.getFareRulesAPI(bodyParam(jsonObject));
            busCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                    try {
                        hideLoading();
                        LoggerUtil.logItem(response.body());
                        Log.e(">>response", response.toString());
                        LoggerUtil.logItem(call.request().url());
                        String param_ = Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent);
                        Gson gson = new GsonBuilder().create();
                        ResponseGetFareRule postPaidresponse = gson.fromJson(param_, ResponseGetFareRule.class);

                        if (postPaidresponse.getResponseStatus() == 1) {
                            strRules =postPaidresponse.getFareRuleOutput().getFareRules();

                            String strRulesSet = strRules.replace("?", " ");

                            tvFareRule.setText(strRulesSet);
    //                        setHtmlTextInTextView(tvFareRule, strRulesSet);
                        } else {
                            showMessage("Something went wrong");
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
            hideLoading();
        }
    }


    private void setHtmlTextInTextView(TextView textView, String message) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            textView.setText(Html.fromHtml(message, Html.FROM_HTML_MODE_COMPACT));
        } else {
            textView.setText(Html.fromHtml(message));
        }
    }
}
