package com.shoppingbag.utilities.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.shoppingbag.constants.BaseActivity;
import com.shoppingbag.constants.Cons;
import com.shoppingbag.model.mobile_recharge.responsemodel.ResponseBillPaymentDone;
import com.shoppingbag.model.response.bus_response.DestinationCitiesItem;
import com.shoppingbag.model.response.bus_response.GetDestinationResponse;
import com.shoppingbag.model.response.bus_response.GetOriginResponse;
import com.shoppingbag.model.response.bus_response.OriginCitiesItem;
import com.shoppingbag.utils.LoggerUtil;
import com.shoppingbag.utils.NetworkUtils;
import com.shoppingbag.utils.Utils;
import com.shoppingbag.R;
import com.shoppingbag.utilities.bus.BusBookings;
import com.shoppingbag.utilities.bus.BusSearch;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;

import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BusActivity extends BaseActivity {

    @BindView(R.id.side_menu)
    ImageView sideMenu;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.et_from)
    TextInputEditText etFrom;
    @BindView(R.id.btn_exchange)
    ImageButton btnExchange;
    @BindView(R.id.et_to)
    TextInputEditText etTo;
    @BindView(R.id.et_journy_date)
    TextInputEditText etJournyDate;
    @BindView(R.id.search_bus_btn)
    Button searchBusBtn;
    @BindView(R.id.btn_show_bookings)
    Button btnShowBookings;

    private long departInMilli = 0;
    private ArrayList<OriginCitiesItem> originList;
    private ArrayList<DestinationCitiesItem> destinationList;
    private String origin_id = "", destination_id = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bus_new);
        ButterKnife.bind(this);
        title.setText("Bus Booking");

        Calendar cal = Calendar.getInstance();
        int mYear, mMonth, mDay;

        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);

        etJournyDate.setText(Utils.changeDateFormatTravel(mDay, mMonth, mYear, "MM/dd/yyyy"));

        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            getOriginList();
        } else {
            createInfoDialog(context, "Alert", getString(R.string.alert_internet));
        }
    }

    @OnClick({R.id.side_menu, R.id.et_journy_date, R.id.btn_exchange, R.id.et_from, R.id.et_to, R.id.search_bus_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.side_menu:
                hideKeyboard();
                finish();
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                break;
            case R.id.et_journy_date:
                datePicker(etJournyDate);
                break;

            case R.id.btn_exchange:
                String fromCity, toCity, fromCityId, toCityId;
                fromCity = etFrom.getText().toString();
                toCity = etTo.getText().toString();
                etFrom.setText(toCity);
                etTo.setText(fromCity);

                fromCityId = origin_id;
                toCityId = destination_id;

                origin_id = toCityId;
                destination_id = fromCityId;
                break;

            case R.id.et_from:
                if (originList.size() != 0) {
                    new SimpleSearchDialogCompat(context, "From",
                            "Search...", null, originList,
                            (SearchResultListener<OriginCitiesItem>) (dialog, item, position) -> {
                                etFrom.setText(item.getOriginName());
                                origin_id = item.getOriginId();
                                dialog.dismiss();
                                hideKeyboard();
                                if (NetworkUtils.getConnectivityStatus(context) != 0) {
                                    getDestinationList();
                                } else {
                                    createInfoDialog(context, "Alert", getString(R.string.alert_internet));
                                }
                            }).show();
                } else {
                    showToastS("No origin Found.\nPlease try again.");
                }
                break;

            case R.id.et_to:
                if (!origin_id.equalsIgnoreCase("")) {
                    new SimpleSearchDialogCompat(BusActivity.this, "To",
                            "Search...", null, destinationList,
                            (SearchResultListener<DestinationCitiesItem>) (dialog, item, position) -> {
                                etTo.setText(item.getDestinationName());
                                destination_id = item.getDestinationId();
                                dialog.dismiss();
                                hideKeyboard();
                            }).show();
                } else {
                    showToastS("Please select From or Origin First");
                }
                break;
            case R.id.search_bus_btn:
                if (NetworkUtils.getConnectivityStatus(context) != 0) {
                    if (validation()) {
                        Bundle bundle = new Bundle();
                        bundle.putString("originId", origin_id);
                        bundle.putString("originName", etFrom.getText().toString());
                        bundle.putString("destinationId", destination_id);
                        bundle.putString("destinationName", etTo.getText().toString());
                        bundle.putString("travelDate", etJournyDate.getText().toString());
                        goToActivity(context, BusSearch.class, bundle);
                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                    }
                } else {
                    createInfoDialog(context, "Alert", getString(R.string.alert_internet));
                }

                break;
        }

    }

    private boolean validation() {
        if (TextUtils.isEmpty(origin_id)) {
            showMessage("Select origin");
            return false;
        } else if (TextUtils.isEmpty(destination_id)) {
            showMessage("Select destination");
            return false;
        } else if (TextUtils.isEmpty(etJournyDate.getText().toString())) {
            showMessage("Select travel date");
            return false;
        }

        return true;
    }

    private void getOriginList() {
        try {
            showLoading();

            Call<JsonObject> incomeCall = apiServicesTravel.getBusOrigin();
            incomeCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                    try {
                        hideLoading();
                        LoggerUtil.logItem(call.request().url());
                        LoggerUtil.logItem(response.body());
                        String param = Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent);
                        Gson gson = new GsonBuilder().create();
                        GetOriginResponse postPaidresponse = gson.fromJson(param, GetOriginResponse.class);
                        if (postPaidresponse.getResponseStatus() != null && postPaidresponse.getResponseStatus().equalsIgnoreCase("1")) {
                            originList = new ArrayList<>(postPaidresponse.getOriginOutput().getOriginCities());
                        } else {
                            originList = new ArrayList<>();
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
            Log.e("Origin", e.toString());
        }
    }

    private void getDestinationList() {
        try {
            showLoading();
            JsonObject param = new JsonObject();
            param.addProperty("OriginId", origin_id);
            Call<JsonObject> incomeCall = apiServicesTravel.getBusDestination(bodyParam(param));
            LoggerUtil.logItem(param);
            incomeCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                    try {
                        hideLoading();
                        LoggerUtil.logItem(call.request().url());
                        Log.e(">>>", response.toString());
                        LoggerUtil.logItem(response.body());
                        String param = Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent);
                        Gson gson = new GsonBuilder().create();
                        GetDestinationResponse postPaidresponse = gson.fromJson(param, GetDestinationResponse.class);
                        if (response.body() != null && postPaidresponse.getResponseStatus() != null && postPaidresponse.getResponseStatus().equalsIgnoreCase("1")) {
                            destinationList = new ArrayList<>(postPaidresponse.getDestinationOutput().getDestinationCities());
                        } else {
                            showToastS(postPaidresponse.getFailureResponse().toString());
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
            Log.e("Destination", e.toString());
        }
    }

    private void datePicker(final TextView et) {
        Calendar cal = Calendar.getInstance();
        int mYear, mMonth, mDay;

        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(context, (view, year, monthOfYear, dayOfMonth) -> {
            et.setText(Utils.changeDateFormatTravel(dayOfMonth, monthOfYear, year, "MM/dd/yyyy"));

            Calendar departcal = Calendar.getInstance();
            departcal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            departcal.set(Calendar.MONTH, monthOfYear);
            departcal.set(Calendar.YEAR, year);
            departInMilli = departcal.getTimeInMillis();

        }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMinDate(cal.getTimeInMillis());
        datePickerDialog.show();
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    @OnClick(R.id.btn_show_bookings)
    public void onViewClicked() {
        goToActivity(context, BusBookings.class, null);
    }

}
