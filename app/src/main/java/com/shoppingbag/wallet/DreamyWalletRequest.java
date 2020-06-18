package com.shoppingbag.wallet;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shoppingbag.app.PreferencesManager;
import com.shoppingbag.constants.BaseActivity;
import com.shoppingbag.constants.Cons;
import com.shoppingbag.utils.HidingScrollListener;
import com.shoppingbag.utils.LoggerUtil;
import com.shoppingbag.utils.NetworkUtils;
import com.shoppingbag.R;
import com.shoppingbag.wallet.adapter.DreamyWalletRequestAdapter;
import com.shoppingbag.wallet.model.ResultItem;
import com.shoppingbag.wallet.model.WalletRequestModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.savvi.rangedatepicker.CalendarPickerView;

import org.jetbrains.annotations.NotNull;

import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DreamyWalletRequest extends BaseActivity {
    @BindView(R.id.side_menu)
    ImageView sideMenu;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.bell_icon)
    ImageView bellIcon;
    @BindView(R.id.request_wallet_RecyclerView)
    RecyclerView requestWalletRecyclerView;
    @BindView(R.id.txtDateFrom)
    TextView txtDateFrom;
    @BindView(R.id.txtDateTo)
    TextView txtDateTo;
    @BindView(R.id.img_open_filters)
    TextView imgOpenFilters;
    @BindView(R.id.txt_norecordfound)
    TextView txtNoRecordFound;
    private int pageNo = 1;
    private int pageSize = 20;
    List<ResultItem> list;
    private boolean once = true;
    LinearLayoutManager layoutManager;
    DreamyWalletRequestAdapter dreamyWalletRequestAdapter;

    private static String changeDateFormat(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
        LoggerUtil.logItem(date);
        Date myDate = null;
        try {
            myDate = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat timeFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        return timeFormat.format(myDate);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dreamy_wallet_request);
        ButterKnife.bind(this);

        title.setText("Dreamy Wallet Request");
        getWalletRequest("", "");
        txtDateFrom.setOnClickListener(v -> getFromDate());
        txtDateTo.setOnClickListener(v -> getToDate());
        imgOpenFilters.setOnClickListener(v -> getWalletRequest(txtDateFrom.getText().toString().trim(), txtDateTo.getText().toString().trim()));
    }

    private void updateScroll() {
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        requestWalletRecyclerView.setLayoutManager(layoutManager);
        requestWalletRecyclerView.setHasFixedSize(true);
        requestWalletRecyclerView.addOnScrollListener(new HidingScrollListener(layoutManager) {
            @Override
            public void onHide() {
            }

            @Override
            public void onLoadMore(int i) {
                if (NetworkUtils.getConnectivityStatus(context) != 0) {
                    if (list.size() == pageNo * pageSize && once) {
                        once = false;
                        pageNo = i;
                        Log.e("Reach", "= " + pageNo);
                        getWalletRequest(txtDateFrom.getText().toString().trim(), txtDateTo.getText().toString().trim());
                    }
                }
            }

            @Override
            public void onShow() {
            }
        });
    }

    public void getFromDate() {
        Calendar calendar = Calendar.getInstance();
        int mMonth = calendar.get(Calendar.MONTH);
        int yYear = calendar.get(Calendar.YEAR);
        int dDay = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> txtDateFrom.setText(new StringBuilder().append(dayOfMonth).append("/").append(month + 1).append("/").append(year).toString()), yYear, mMonth, dDay);
        datePickerDialog.show();
    }

    public void getToDate() {
        Calendar calendar = Calendar.getInstance();
        int mMonth = calendar.get(Calendar.MONTH);
        int yYear = calendar.get(Calendar.YEAR);
        int dDay = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> txtDateTo.setText(String.format("%s/%s/%s", dayOfMonth, month + 1, year)), yYear, mMonth, dDay);
        datePickerDialog.show();
        // datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
    }

    private void getWalletRequest(String from_date, String to_date) {
        showLoading();
        JsonObject jsonObject = new JsonObject();
        try {
            jsonObject.addProperty("memberId", Cons.decryptMsg(PreferencesManager.getInstance(context).getUSERID(), cross_intent));
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        jsonObject.addProperty("fromDate", from_date);
        jsonObject.addProperty("toDate", to_date);
        jsonObject.addProperty("page", pageNo);
        jsonObject.addProperty("size", pageSize);
        Call<JsonObject> call = apiServices.getWalletRequestLedger(bodyParam(jsonObject), PreferencesManager.getInstance(context).getANDROIDID());
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                hideLoading();
                LoggerUtil.logItem(response.body());
                LoggerUtil.logItem(response.code());
                try {
                    if (response.isSuccessful()) {
                        String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent);
                        LoggerUtil.logItem(paramResponse);
                        Gson gson = new GsonBuilder().create();
                        WalletRequestModel walletRequestModel = gson.fromJson(paramResponse, WalletRequestModel.class);
                        if (response.body() != null && walletRequestModel.getResponse().equalsIgnoreCase("Success")) {
                            if (pageNo == 1) {
                                requestWalletRecyclerView.setVisibility(View.VISIBLE);
                                txtNoRecordFound.setVisibility(View.GONE);
                                updateScroll();
                                list = walletRequestModel.getResult();
                                dreamyWalletRequestAdapter = new DreamyWalletRequestAdapter(DreamyWalletRequest.this, list);
                                requestWalletRecyclerView.setAdapter(dreamyWalletRequestAdapter);
                            }
                        } else {
                            txtNoRecordFound.setVisibility(View.VISIBLE);
                            requestWalletRecyclerView.setVisibility(View.GONE);
                        }
                    } else {
                        showMessage("Something went wrong");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NotNull Call<JsonObject> call, @NotNull Throwable t) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    private void datePicker() {
        try {
            LayoutInflater li = LayoutInflater.from(context);
            View dateDialog = li.inflate(R.layout.dialog_calender, null);
            CalendarPickerView calendar_view = dateDialog.findViewById(R.id.calendar_view);
            final Calendar maxDate = Calendar.getInstance();
            final Calendar minDate = Calendar.getInstance();
            minDate.add(Calendar.YEAR, 0);
            minDate.add(Calendar.MONTH, -6);

            LoggerUtil.logItem(minDate.getTime());
            LoggerUtil.logItem(maxDate.getTime());

            calendar_view.init(minDate.getTime(), maxDate.getTime(), new SimpleDateFormat("MMMM, yyyy", Locale.ENGLISH)) //
                    .inMode(CalendarPickerView.SelectionMode.RANGE);

            boolean bool = calendar_view.scrollToDate(maxDate.getTime());
            LoggerUtil.logItem(bool);
            Button button_confirm = dateDialog.findViewById(R.id.button_confirm);
            AlertDialog.Builder alert = new AlertDialog.Builder(context);
            alert.setView(dateDialog);
            final AlertDialog alertDialog = alert.create();
            alertDialog.setCancelable(false);
            alertDialog.setCanceledOnTouchOutside(true);
            alertDialog.show();
            button_confirm.setOnClickListener(view -> {
                if (calendar_view.getSelectedDates().size() != 0) {
                    txtDateFrom.setText(changeDateFormat(calendar_view.getSelectedDates().get(0).toString()));
                    txtDateTo.setText(changeDateFormat(calendar_view.getSelectedDates().get(calendar_view.getSelectedDates().size() - 1).toString()));
                    alertDialog.dismiss();
                    getWalletRequest(txtDateFrom.getText().toString().trim(), txtDateTo.getText().toString().trim());
                } else {
                    alertDialog.dismiss();
                    txtDateFrom.setText("");
                    txtDateTo.setText("");
                    getWalletRequest("", "");
                }
            });

        } catch (Error | Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.side_menu)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txtDateTo:
                hideKeyboard();
                datePicker();
                break;
            case R.id.side_menu:
                finish();
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                break;
        }
    }

}
