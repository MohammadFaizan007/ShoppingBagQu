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
import com.shoppingbag.utilities.mobilerecharge.adapters.AccountStatAdapter;
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
import com.shoppingbag.model.mobile_recharge.requestmodel.AccountStatementInput;
import com.shoppingbag.model.mobile_recharge.requestmodel.RequestGetAccountStatement;
import com.shoppingbag.model.mobile_recharge.responsemodel.ResponseGetAccountStatement;
import com.shoppingbag.model.mobile_recharge.responsemodel.TransactionsItem;
import com.shoppingbag.utils.LoggerUtil;
import com.shoppingbag.utils.RecyclerTouchListener;
import com.shoppingbag.utils.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountStatement extends BaseActivity {

    private static String TAG = "ACCOUNT_STATEMENT";
    @BindView(R.id.tvFromDate)
    TextView tvFromDate;
    @BindView(R.id.tvToDate)
    TextView tvToDate;
    @BindView(R.id.accStatRecyclerView)
    RecyclerView accStatRecyclerView;
    @BindView(R.id.tvAgentID)
    TextView tvAgentID;
    @BindView(R.id.tvAgentAmount)
    TextView tvAgentAmount;
    @BindView(R.id.tvAgentName)
    TextView tvAgentName;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    AccountStatAdapter adapter;
    String DATE_TAG;
    private Gson gson = new GsonBuilder().create();
    private long departInMilli = 0;
    private List<TransactionsItem> list = new ArrayList<>();

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

    @SuppressLint({"SetTextI18n", "SimpleDateFormat"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_statement);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_clear_white);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        departInMilli = Calendar.getInstance().getTimeInMillis();

        tvToDate.setText(Utils.getTodayDate());
        tvFromDate.setText(Utils.getTodayDate());
        getAccountStatementHere();

        //TODO its sample.
//        try {
//            String json = loadJSONFromAsset();
//            JSONObject obj = new JSONObject(json);
//
//            ResponseGetAccountStatement resp = gson.fromJson(String.valueOf(obj), ResponseGetAccountStatement.class);
//            list = resp.getAccountStatementOutput().getTransactions();
//            tvAgentID.setText(": " + resp.getAccountStatementOutput().getTravelAgentID());
//            tvAgentName.setText(": " + resp.getAccountStatementOutput().getTravelAgentName());
//            tvAgentName.setEllipsize(TextUtils.TruncateAt.MARQUEE);
//            tvAgentID.setEllipsize(TextUtils.TruncateAt.MARQUEE);
//            tvAgentAmount.setText(": \u20B9 " + resp.getAccountStatementOutput().getTotalRemainingAmount() + " /-");
//            adapter = new AccountStatAdapter(context, list);
//            accStatRecyclerView.setLayoutManager(new LinearLayoutManager(context));
//            accStatRecyclerView.setAdapter(adapter);
//            accStatRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(context,
//                    accStatRecyclerView,
//                    new RecyclerTouchListener.ClickListener() {
//                        @Override
//                        public void onClick(View view, int position) {
//                            Toast.makeText(context, " " + list.get(position).getProductDescription(), Toast.LENGTH_SHORT).show();
//                        }
//
//                        @Override
//                        public void onLongClick(View view, int position) {
//
//                        }
//                    }));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        //TODO  sample ENDS.....
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
                getAccountStatementHere();
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
                et.setText(Utils.changeDateFormatTravel(dayOfMonth, monthOfYear, year,"MM/dd/yyyy"));
                if (from) {
                    Calendar departcal = Calendar.getInstance();
                    departcal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    departcal.set(Calendar.MONTH, monthOfYear);
                    departcal.set(Calendar.YEAR, year);
                    departInMilli = departcal.getTimeInMillis();
                }
            }
        }, mYear, mMonth, mDay);

//        datePickerDialog.getDatePicker().setMinDate(cal.getTimeInMillis());

        if (DATE_TAG.equalsIgnoreCase("FROM")) {
            datePickerDialog.getDatePicker().setMaxDate(cal.getTimeInMillis());
        } else if (DATE_TAG.equalsIgnoreCase("TO")) {
            datePickerDialog.getDatePicker().setMinDate(departInMilli);
//            datePickerDialog.getDatePicker().setMaxDate(cal.getTimeInMillis());
        }

        datePickerDialog.show();
    }

    private void getAccountStatementHere() {
        try {
            showLoading();
            RequestGetAccountStatement accStat = new RequestGetAccountStatement();
//        Authentication authentication = new Authentication();
//        authentication.setLoginId(Utils.USERNAME);
//        authentication.setPassword(Utils.PASSWORD);
//        accStat.setAuthentication(authentication);

            AccountStatementInput input = new AccountStatementInput();
//        input.setFromDate(strFromDate);
//        input.setToDate(strToDate);
            input.setFromDate(tvFromDate.getText().toString());
            input.setToDate(tvToDate.getText().toString());
            accStat.setAccountStatementInput(input);

            LoggerUtil.logItem(accStat);

            Call<ResponseGetAccountStatement> busCall = apiServicesTravel.getAccountStatement(accStat);
            busCall.enqueue(new Callback<ResponseGetAccountStatement>() {
                @Override
                public void onResponse(@NotNull Call<ResponseGetAccountStatement> call, @NotNull Response<ResponseGetAccountStatement> response) {
                    hideLoading();
                    LoggerUtil.logItem(response.body());
                    if (response != null && response.body().getResponseStatus() == 1) {
                        if (response.body().getResponseStatus() == 1) {
                            list.clear();
                            tvAgentID.setText(response.body().getAccountStatementOutput().getTravelAgentID());
                            tvAgentName.setText(response.body().getAccountStatementOutput().getTravelAgentName());
                            tvAgentAmount.setText(String.format("%s", response.body().getAccountStatementOutput().getTotalRemainingAmount()));
                            if (response.body().getAccountStatementOutput().getTransactions() != null) {
                                list.clear();
                                list = response.body().getAccountStatementOutput().getTransactions();
                                adapter = new AccountStatAdapter(context, list);
                                accStatRecyclerView.setLayoutManager(new LinearLayoutManager(context));
                                accStatRecyclerView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                                accStatRecyclerView.addOnItemTouchListener(
                                        new RecyclerTouchListener(context,
                                                accStatRecyclerView,
                                                new RecyclerTouchListener.ClickListener() {
                                                    @Override
                                                    public void onClick(View view, int position) {
                                                        Toast.makeText(context, " " + list.get(position).getProductDescription(), Toast.LENGTH_SHORT).show();
                                                    }

                                                    @Override
                                                    public void onLongClick(View view, int position) {

                                                    }
                                                }));
                            } else {
                                list.clear();
                                adapter.notifyDataSetChanged();
                                Log.e(TAG, "list is null....");
                            }
                        } else {
                            showMessage("Something went wrong");
                        }
                    }

                }

                @Override
                public void onFailure(@NotNull Call<ResponseGetAccountStatement> call, @NotNull Throwable t) {
                    hideLoading();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            hideLoading();
        }
    }

}
