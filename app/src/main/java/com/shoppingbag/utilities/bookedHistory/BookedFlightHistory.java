package com.shoppingbag.utilities.bookedHistory;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shoppingbag.app.PreferencesManager;
import com.shoppingbag.constants.BaseActivity;
import com.shoppingbag.constants.Cons;
import com.shoppingbag.model.bookedHistory.responseModel.GetBookingHistorydetailItem;
import com.shoppingbag.model.bookedHistory.responseModel.ResponseFlightBooking;
import com.shoppingbag.utils.LoggerUtil;
import com.shoppingbag.utils.Utils;
import com.shoppingbag.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;

import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookedFlightHistory extends BaseActivity {

    String DATE_TAG;
    @BindView(R.id.bookigHistryRV)
    RecyclerView bookingList;
    @BindView(R.id.tvFromDate)
    TextView tvFromDate;
    @BindView(R.id.tvToDate)
    TextView tvToDate;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    LinearLayoutManager layoutManager;
    @BindView(R.id.txt_Norecfound)
    TextView txtNorecfound;
    private long departInMilli = 0;
    List<GetBookingHistorydetailItem> list = new ArrayList<>();
    String param1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booked_history);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        bookingList.setLayoutManager(layoutManager);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
            }
        });
        departInMilli = Calendar.getInstance().getTimeInMillis();

        tvToDate.setText(Utils.getTodayDate("dd-MM-yyyy"));
        tvFromDate.setText(Utils.getTodayDate("dd-MM-yyyy"));
        getHistory();
    }

    @OnClick({R.id.tvFromDate, R.id.tvToDate, R.id.getStatement})
    void MultipleClicks(View view) {
        switch (view.getId()) {
            case R.id.tvFromDate:
                DATE_TAG = "FROM";
                datePicker(tvFromDate, true);
                break;
            case R.id.tvToDate:
                DATE_TAG = "TO";
                datePicker(tvToDate, false);
                break;
            case R.id.getStatement:
                getHistory();
                break;
        }
    }

    private void datePicker(final TextView et, final boolean from) {
        Calendar cal = Calendar.getInstance();
        int mYear, mMonth, mDay;
        if (!from) {
            cal.setTimeInMillis(departInMilli);
        }
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                et.setText(Utils.changeDateFormatTravelNew(dayOfMonth, monthOfYear, year));
                if (from) {
                    Calendar departcal = Calendar.getInstance();
                    departcal.set(Calendar.MONTH, monthOfYear);
                    departcal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    departcal.set(Calendar.YEAR, year);
                    departInMilli = departcal.getTimeInMillis();
                }
            }
        }, mYear, mMonth, mDay);

        if (DATE_TAG.equalsIgnoreCase("FROM")) {
            datePickerDialog.getDatePicker().setMaxDate(cal.getTimeInMillis());
        } else if (DATE_TAG.equalsIgnoreCase("TO")) {
            datePickerDialog.getDatePicker().setMinDate(departInMilli);
        }

        datePickerDialog.show();
    }

    private void getHistory() {
        try {
            showLoading();
            JsonObject param = new JsonObject();
            param.addProperty("FK_memId", Cons.decryptMsg(PreferencesManager.getInstance(context).getUSERID(), cross_intent));
            param.addProperty("FromDate", tvFromDate.getText().toString());
            param.addProperty("ToDate", tvToDate.getText().toString());
            LoggerUtil.logItem(param);

            Call<JsonObject> busCall = apiServicesTravel.getFlightBookingHistory(bodyParam(param));
            busCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                    hideLoading();
                    LoggerUtil.logItem(response.body());
                    LoggerUtil.logItem(response.code());
                    /*LoggerUtil.logItem(response.body());
                    Log.e(">>>", response.toString());
                   *//* try {
                        param1 = Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent);
                    } catch (GeneralSecurityException e) {
                        e.printStackTrace();
                    }
                    Gson gson = new GsonBuilder().create();*//*
                    ResponseFlightBooking responseFlightBooking = null;
                    try {
                        responseFlightBooking = new Gson().fromJson(Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent), ResponseFlightBooking.class);
                    } catch (GeneralSecurityException e) {
                        e.printStackTrace();
                    }
                    // ResponseFlightBooking responseFlightBooking = gson.fromJson(param1, ResponseFlightBooking.class);
                    if (responseFlightBooking.getResponse().equalsIgnoreCase("Success")) {
                        FlightBookedHistoryAdapter adapter = new FlightBookedHistoryAdapter(context, responseFlightBooking.getGetBookingHistorydetail());
                        bookingList.setAdapter(adapter);
                    } else {
                        showMessage(responseFlightBooking.getResponse());
                        onBackPressed();
                    }*/
                    try {
                        if (response.isSuccessful()) {
                            param1 = Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent);
                            Gson gson = new GsonBuilder().create();
                            LoggerUtil.logItem(param1);
                            ResponseFlightBooking responseFlightBooking = gson.fromJson(param1, ResponseFlightBooking.class);
                            if (responseFlightBooking.getResponse().equalsIgnoreCase("Success") && responseFlightBooking.getGetBookingHistorydetail().size() > 0) {
                                bookingList.setVisibility(View.VISIBLE);
                                txtNorecfound.setVisibility(View.GONE);
                                list = new ArrayList<>();
                                Log.e("ggg", "gggg");


                                list = responseFlightBooking.getGetBookingHistorydetail();
                                Log.e("ggg", "gggg" + responseFlightBooking.getGetBookingHistorydetail().get(0).getBoookingDate());
                                Log.e("ggg", "gggg" + responseFlightBooking.getGetBookingHistorydetail().get(0).getAirlineName());

                                FlightBookedHistoryAdapter adapter = new FlightBookedHistoryAdapter(context, list);
                                bookingList.setAdapter(adapter);
                            } else {
                                txtNorecfound.setVisibility(View.VISIBLE);
                                bookingList.setVisibility(View.GONE);
                            }
                        } else {
                            txtNorecfound.setVisibility(View.VISIBLE);
                            bookingList.setVisibility(View.GONE);
                            showMessage("Something went wrong");
                        }
                    } catch (GeneralSecurityException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<JsonObject> call, @NotNull Throwable t) {

                }


            });

        } catch (Exception e) {
            e.printStackTrace();
            hideLoading();
        }
    }


}
