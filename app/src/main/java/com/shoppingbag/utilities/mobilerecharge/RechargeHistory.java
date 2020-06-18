package com.shoppingbag.utilities.mobilerecharge;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shoppingbag.R;
import com.shoppingbag.utilities.mobilerecharge.adapters.RechargeHistoryAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.shoppingbag.constants.BaseActivity;
import com.shoppingbag.model.mobile_recharge.requestmodel.RechargeHistoryInput;
import com.shoppingbag.model.mobile_recharge.requestmodel.RequestGetRechargeHistory;
import com.shoppingbag.model.mobile_recharge.responsemodel.RechargesItem;
import com.shoppingbag.model.mobile_recharge.responsemodel.ResponseGetRechargeHistory;
import com.shoppingbag.utils.LoggerUtil;
import com.shoppingbag.utils.RecyclerTouchListener;
import com.shoppingbag.utils.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RechargeHistory extends BaseActivity {
    private static String TAG = "Recharge_History";
    @BindView(R.id.tvFromDate)
    TextView tvFromDate;
    @BindView(R.id.tvToDate)
    TextView tvToDate;
    @BindView(R.id.rechargehistoryRecyclerView)
    RecyclerView rechargehistoryRecyclerView;
    Gson gson = new GsonBuilder().create();
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    String DATE_TAG;
    //    private String strToDate;
//    Date newDate;
//    private String strFromDate;
    private long departInMilli = 0;
    private List<RechargesItem> list = new ArrayList<>();

    public static boolean isValidFormat(String format, String value) {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
            date = sdf.parse(value);
            if (!value.equals(sdf.format(date))) {
                date = null;
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return date != null;
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recharge_history);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
            }
        });
//
////        try {
////            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm/dd/yyyy");
////            strToDate = Utils.getTodayDate();
////            newDate = new Date(simpleDateFormat.parse(strToDate).getTime() - 345600000L); // 4 * 24 * 60 * 60 * 1000
//////            strFromDate = simpleDateFormat.format(newDate);
////            strFromDate = Utils.getTodayDate();
////            Log.e(TAG, "String TO DATE" + strToDate);
////            Log.e(TAG, "String FROM DATE" + strFromDate);
////        } catch (ParseException e) {
////            e.printStackTrace();
////        }
//
////        if (isValidFormat("mm/dd/yyyy", strToDate)
////                && isValidFormat("mm/dd/yyyy", strFromDate)) {
        tvToDate.setText(Utils.getTodayDate());
        tvFromDate.setText(Utils.getTodayDate());
//        getRechargehistoryHere();
////        } else {
////            Toast.makeText(context, "date is not correct", Toast.LENGTH_SHORT).show();
////        }
//
//
//        //TODO its sample.
//        try {
//            String json = loadJSONFromAsset();
//            JSONObject obj = new JSONObject(json);
//            Log.e("JSON OBJECTR", obj.toString());
//
//            ResponseGetRechargeHistory resp = gson.fromJson(String.valueOf(obj), ResponseGetRechargeHistory.class);
//            list = resp.getRecharges();
//            RechargeHistoryAdapter adapter = new RechargeHistoryAdapter(RechargeHistory.this, list);
//            rechargehistoryRecyclerView.setLayoutManager(new LinearLayoutManager(context));
//            rechargehistoryRecyclerView.setAdapter(adapter);
//            rechargehistoryRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(context,
//                    rechargehistoryRecyclerView,
//                    new RecyclerTouchListener.ClickListener() {
//                        @Override
//                        public void onClick(View view, int position) {
//                            Toast.makeText(RechargeHistory.this, " " + list.get(position).getMobileNumber(), Toast.LENGTH_SHORT).show();
//                        }
//
//                        @Override
//                        public void onLongClick(View view, int position) {
//
//                        }
//                    }));
//
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        //TODO  sample ENDS.
    }

    @OnClick({R.id.tvFromDate, R.id.tvToDate, R.id.getHistory})
    public void getMultipleClicks(View view) {
        switch (view.getId()) {
            case R.id.tvFromDate:
                DATE_TAG = "FROM";
                datePicker(tvFromDate, true);
                break;
            case R.id.tvToDate:
                DATE_TAG = "TO";
                datePicker(tvToDate, false);
                break;
            case R.id.getHistory:
//                getRechargehistoryHere();
                break;
        }
    }

 /*   private void getRechargehistoryHere()
    {
        try {
            showLoading();
            RequestGetRechargeHistory req = new RequestGetRechargeHistory();
//        Authentication authentication = new Authentication();
//        authentication.setLoginId(Utils.USERNAME);
//        authentication.setPassword(Utils.PASSWORD);
//        req.setAuthentication(authentication);
            RechargeHistoryInput input = new RechargeHistoryInput();
            input.setFromDate(tvFromDate.getText().toString());
            input.setToDate(tvToDate.getText().toString());
            input.setMobileNumber("");
            req.setRechargeHistoryInput(input);

            LoggerUtil.logItem(req);

            Call<ResponseGetRechargeHistory> busCall = apiServicesTravel.getRechargeHistory(req);
            busCall.enqueue(new Callback<ResponseGetRechargeHistory>() {
                @Override
                public void onResponse(@NotNull Call<ResponseGetRechargeHistory> call, @NotNull Response<ResponseGetRechargeHistory> response) {
                    hideLoading();
                    LoggerUtil.logItem(response.body());
                    if (response.body().getResponseStatus() == 1) {
                        list = response.body().getRecharges();
                        if (list != null) {
                            RechargeHistoryAdapter adapter = new RechargeHistoryAdapter(RechargeHistory.this, list);
                            rechargehistoryRecyclerView.setLayoutManager(new LinearLayoutManager(context));
                            rechargehistoryRecyclerView.setAdapter(adapter);
                            rechargehistoryRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(context,
                                    rechargehistoryRecyclerView,
                                    new RecyclerTouchListener.ClickListener() {
                                        @Override
                                        public void onClick(View view, int position) {

                                        }

                                        @Override
                                        public void onLongClick(View view, int position) {

                                        }
                                    }));
                        } else {
                            Log.e(TAG, "recharge history list is empty.");
                        }
                    } else {
                        Toast.makeText(RechargeHistory.this, "No Record Found", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(@NotNull Call<ResponseGetRechargeHistory> call, @NotNull Throwable t) {
                    hideLoading();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            hideLoading();
        }
    }
*/

    private void datePicker(final TextView et, final boolean depart) {
        Calendar cal = Calendar.getInstance();
        int mYear, mMonth, mDay;
        if (!depart) {
            cal.setTimeInMillis(departInMilli);
        }
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear, int dayOfMonth) {

                et.setText(Utils.changeDateFormatTravel(dayOfMonth, monthOfYear, year,"MM/dd/yyyy"));

                if (depart) {
                    Calendar departcal = Calendar.getInstance();
                    departcal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    departcal.set(Calendar.MONTH, monthOfYear);
                    departcal.set(Calendar.YEAR, year);
                    departInMilli = departcal.getTimeInMillis();
                }

            }
        }, mYear, mMonth, mDay);
        if (DATE_TAG.equalsIgnoreCase("FROM")) {
            datePickerDialog.getDatePicker().setMaxDate(cal.getTimeInMillis());
        } else if (DATE_TAG.equalsIgnoreCase("TO")) {
            datePickerDialog.getDatePicker().setMinDate(cal.getTimeInMillis());
            datePickerDialog.getDatePicker().setMaxDate(cal.getTimeInMillis());
        }
        datePickerDialog.show();
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

}
