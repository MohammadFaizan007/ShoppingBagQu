package com.shoppingbag.utilities.bus;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shoppingbag.app.PreferencesManager;
import com.shoppingbag.constants.BaseActivity;
import com.shoppingbag.constants.Cons;
import com.shoppingbag.model.response.bus_response.bookingHistory.ResponseBusBookings;
import com.shoppingbag.utils.LoggerUtil;
import com.shoppingbag.utils.NetworkUtils;
import com.shoppingbag.utils.Utils;
import com.shoppingbag.R;
import com.shoppingbag.utilities.bus.adapter.BusBookingAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BusBookings extends BaseActivity {

    @BindView(R.id.rv_bus_bookings)
    RecyclerView rvBusBookings;
    @BindView(R.id.side_menu)
    ImageView sideMenu;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tvFromDate)
    TextView tvFromDate;
    @BindView(R.id.tvReStatus)
    TextView tvReStatus;
    @BindView(R.id.tvToDate)
    TextView tvToDate;
    @BindView(R.id.getStatement)
    ImageView getStatement;
    @BindView(R.id.txt_Norecfound)
    TextView txtNorecfound;
    @BindView(R.id.txt)
    TextView txt;
    @BindView(R.id.tvtravelledpop)
    TextView tvtravelledpop;
    @BindView(R.id.cardTrave)
    ConstraintLayout cardTrave;
    private String DATE_TAG;
    private long departInMilli = 0;
    private PopupMenu popupMenu;
    private String recordBy = "T";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bus_bookings);
        ButterKnife.bind(this);

        rvBusBookings.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        title.setText("Bus History");
        departInMilli = Calendar.getInstance().getTimeInMillis();

        tvToDate.setText(Utils.getTodayDate());
        tvFromDate.setText(Utils.getTodayDate());
        popupMenu = new PopupMenu(context, tvtravelledpop);
        popupMenu.getMenuInflater().inflate(R.menu.travellpop, popupMenu.getMenu());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (NetworkUtils.getConnectivityStatus(context) != 0)
            getBusBookings();
        else
            showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
    }

    private void getBusBookings() {
        try {
            showLoading();
            JsonObject jsonObject = new JsonObject();
            JsonObject jsonObjectBook = new JsonObject();
            jsonObjectBook.addProperty("FromDate", tvFromDate.getText().toString().trim());
            jsonObjectBook.addProperty("ToDate", tvToDate.getText().toString().trim());
            jsonObjectBook.addProperty("RecordsBy", recordBy);
            jsonObjectBook.addProperty("FK_memId", Cons.decryptMsg(PreferencesManager.getInstance(context).getUSERID(), cross_intent));
            jsonObject.add("BookedHistoryInput", jsonObjectBook);
            LoggerUtil.logItem(jsonObject);

            Call<JsonObject> call = apiServicesTravel.getBusBookings(bodyParam(jsonObject));
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                    try {
                        hideLoading();
                        LoggerUtil.logItem(response.code());
                        LoggerUtil.logItem(response.body());
                        if (response.isSuccessful()) {
                            String param = Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent);
                            LoggerUtil.logItem(param);
                            Gson gson = new GsonBuilder().create();
                            ResponseBusBookings postPaidresponse = gson.fromJson(param, ResponseBusBookings.class);
                            if (postPaidresponse.getResponseStatus().equalsIgnoreCase("1") && postPaidresponse.getBookedHistoryOutput().getBookedTickets().size() > 0) {
                                rvBusBookings.setVisibility(View.VISIBLE);
                                txtNorecfound.setVisibility(View.GONE);
                                BusBookingAdapter adapter = new BusBookingAdapter(context, postPaidresponse.getBookedHistoryOutput().getBookedTickets(), null);
                                rvBusBookings.setAdapter(adapter);
                            } else {
                                txtNorecfound.setVisibility(View.VISIBLE);
                                rvBusBookings.setVisibility(View.GONE);
                            }
                        } else {
                            txtNorecfound.setVisibility(View.VISIBLE);
                            rvBusBookings.setVisibility(View.GONE);
                            showMessage("something went wrong");
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
        }
    }

    public void pressBack(View v) {
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    @OnClick(R.id.side_menu)
    public void onViewClicked() {
        hideKeyboard();
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);

    }

    @OnClick({R.id.tvtravelledpop, R.id.tvFromDate, R.id.tvToDate, R.id.getStatement})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvtravelledpop:
                popupMenu.setOnMenuItemClickListener(item -> {
                    tvtravelledpop.setText(item.getTitle());
                    if (item.getTitle().equals("Travelled")) {
                        recordBy = "T";
                    } else {
                        recordBy = "B";
                    }
                    return true;
                });
                popupMenu.show();
                break;
            case R.id.tvFromDate:
                DATE_TAG = "FROM";
                datePicker(tvFromDate, true);
                break;
            case R.id.tvToDate:
                DATE_TAG = "TO";
                datePicker(tvToDate, false);
                break;
            case R.id.getStatement:
                getBusBookings();
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

        DatePickerDialog datePickerDialog = new DatePickerDialog(context, (view, year, monthOfYear, dayOfMonth) -> {
            et.setText(Utils.changeDateFormatTravelbyMe(dayOfMonth, monthOfYear, year));
            if (from) {
                Calendar departcal = Calendar.getInstance();
                departcal.set(Calendar.MONTH, monthOfYear);
                departcal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                departcal.set(Calendar.YEAR, year);
                departInMilli = departcal.getTimeInMillis();
            }
        }, mYear, mMonth, mDay);

        if (DATE_TAG.equalsIgnoreCase("FROM")) {
            datePickerDialog.getDatePicker().setMaxDate(cal.getTimeInMillis());
        } else if (DATE_TAG.equalsIgnoreCase("TO")) {
            datePickerDialog.getDatePicker().setMinDate(departInMilli);
        }

        datePickerDialog.show();
    }


}
