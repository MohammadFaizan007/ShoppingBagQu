package com.shoppingbag.utilities.activities;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.shoppingbag.BuildConfig;
import com.shoppingbag.R;
import com.shoppingbag.adapter.RechargeHistoryAdapter;
import com.shoppingbag.app.AppConfig;
import com.shoppingbag.app.CheckErrorCode;
import com.shoppingbag.app.PreferencesManager;
import com.shoppingbag.common_activities.LoginActivity;
import com.shoppingbag.common_activities.MaintenancePage;
import com.shoppingbag.constants.BaseActivity;
import com.shoppingbag.constants.Cons;
import com.shoppingbag.model.jioPrepaid.ResponseJioPrepaidRecharge;
import com.shoppingbag.model.mobile_recharge.responsemodel.RechargesItem;
import com.shoppingbag.model.request.utility.response.ResponseBalanceAmount;
import com.shoppingbag.model.utility.ResponsePostpaidRecharge;
import com.shoppingbag.model.utility.ResponsePrepaidRecharge;
import com.shoppingbag.model.utility.ResponseRecentRecharges;
import com.shoppingbag.utilities.BrowsePlanJio;
import com.shoppingbag.utils.LoggerUtil;
import com.shoppingbag.utils.RecyclerTouchListener;
import com.shoppingbag.utils.Utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MobileRechargeActivity extends BaseActivity {
    private static String TAG = "MobileRecharge";
    private final int TRANSACTION_STAT_CODE = 300;
    private final int OPERATOR_STAT_CODE = 100;
    private final int CONTACT_STAT_CODE = 101;
    public String jio_plan_id = "";
    @BindView(R.id.side_menu)
    ImageView sideMenu;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.textViewNote)
    TextView textViewNote;
    @BindView(R.id.choosePrepaid)
    TextView choosePrepaid;
    @BindView(R.id.choosePostpaid)
    TextView choosePostpaid;
    @BindView(R.id.CV_chooseOp)
    LinearLayout CVChooseOp;
    @BindView(R.id.imageView3)
    ImageView imageView3;
    @BindView(R.id.img_contact)
    ImageView imgContact;
    @BindView(R.id.mobile_edt_no)
    TextInputEditText etMobileNumber;
    @BindView(R.id.mobile_input_mobileno)
    TextInputLayout mobileInputMobileno;
    @BindView(R.id.card_mobile)
    CardView cardMobile;
    @BindView(R.id.imageView5)
    ImageView imageView5;
    @BindView(R.id.mobile_edt_operator)
    TextInputEditText etMobileCurrentOperator;
    @BindView(R.id.mobile_input_operator)
    TextInputLayout mobileInputOperator;
    @BindView(R.id.currentOperator)
    ConstraintLayout currentOperator;
    @BindView(R.id.card_currentOperator)
    CardView cardCurrentOperator;
    @BindView(R.id.imageView6)
    ImageView imageView6;
    @BindView(R.id.mobile_edt_amount)
    public TextInputEditText etRechargeAmount;
    @BindView(R.id.mobile_input_amount)
    TextInputLayout mobileInputAmount;
    @BindView(R.id.card_totalamount)
    CardView cardTotalamount;
    @BindView(R.id.proceedToPay)
    Button proceedToPay;
    Gson gson = new GsonBuilder().create();
    @BindView(R.id.rv_rchistory)
    RecyclerView rvRchistory;
    @BindView(R.id.txt_norecordfound)
    TextView txtNorecordfound;
    @BindView(R.id.browse_plans_tv)
    TextView browsePlansTv;
    private String operatorCode;
    private String BASEURL, operatorType;
    private RechargeHistoryAdapter adapter;
    private List<RechargesItem> list = new ArrayList<>();
    private boolean prepaid = true;
    private String tempToken;
    Bundle sendParam = new Bundle();
    private boolean once = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mymobilkerecharge);
        ButterKnife.bind(this);
        title.setText("Mobile Recharge");

        Utils.hideSoftKeyboard(this);

        textViewNote.setText("10% of the Total Amount is Deducted from Cashback Wallet");
        choosePrepaid.setBackgroundResource(R.drawable.rect_login_bg);
        choosePrepaid.setTextColor(ContextCompat.getColor(context, R.color.white));
        choosePostpaid.setTextColor(ContextCompat.getColor(context, R.color.text_color));
        choosePostpaid.setBackgroundResource(0);
        BASEURL = BuildConfig.BASE_URL_TRAVEL;
        operatorType = Utils.ITS_PREPAID;
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        rvRchistory.setLayoutManager(layoutManager);

        getRecentRechargeList("PrepaidMobile", "");

    }


    @OnClick({R.id.browse_plans_tv, R.id.side_menu, R.id.choosePrepaid, R.id.choosePostpaid, R.id.img_contact, R.id.proceedToPay, R.id.mobile_edt_operator, R.id.card_currentOperator, R.id.mobile_input_operator})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.side_menu:
                hideKeyboard();
                finish();
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                break;
            case R.id.choosePrepaid:
                if (!prepaid) {
                    choosePrepaid.setBackgroundResource(R.drawable.rect_login_bg);
                    choosePrepaid.setTextColor(ContextCompat.getColor(context, R.color.white));
                    choosePostpaid.setTextColor(ContextCompat.getColor(context, R.color.text_color));
                    choosePostpaid.setBackgroundResource(0);
                    BASEURL = BuildConfig.BASE_URL_TRAVEL;
                    operatorType = Utils.ITS_PREPAID;
                    etMobileCurrentOperator.setText(getString(R.string.chooseOperatorCode));
                    prepaid = true;
                    getRecentRechargeList("PrepaidMobile", "");
                }
                break;
            case R.id.choosePostpaid:
                if (prepaid) {
                    choosePostpaid.setBackgroundResource(R.drawable.rect_login_bg);
                    choosePrepaid.setBackgroundResource(0);
                    choosePostpaid.setTextColor(ContextCompat.getColor(context, R.color.white));
                    choosePrepaid.setTextColor(ContextCompat.getColor(context, R.color.text_color));
                    BASEURL = BuildConfig.BASE_URL_TRAVEL;
                    operatorType = Utils.ITS_POSTPAID;
                    etMobileCurrentOperator.setText(getString(R.string.chooseOperatorCode));
                    prepaid = false;
                    getRecentRechargeList("BBPS", "Postpaid");
                }
                break;
            case R.id.img_contact:
                hasContactPermission();
                Intent contactPickerIntent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(contactPickerIntent, CONTACT_STAT_CODE);
                break;
            case R.id.proceedToPay:
                if (validation()) {
                    getWalletBalanceToken();
                }
                break;
            case R.id.mobile_input_operator:
            case R.id.card_currentOperator:
                break;

            case R.id.mobile_edt_operator:
                PopupMenu popup_operator = new PopupMenu(this, view);
                if (prepaid) {
                    popup_operator.getMenuInflater().inflate(R.menu.menu_provider_prepaid, popup_operator.getMenu());
                } else {
                    popup_operator.getMenuInflater().inflate(R.menu.menu_provider_postpaid, popup_operator.getMenu());
                }
                popup_operator.setOnMenuItemClickListener(item -> {
                    etMobileCurrentOperator.setText(item.getTitle());
                    if (item.getTitle().equals("Jio")) {
                        browsePlansTv.setVisibility(View.VISIBLE);
                    } else {
                        browsePlansTv.setVisibility(View.GONE);
                    }
                    return true;
                });
                popup_operator.show();
                break;
            case R.id.browse_plans_tv:
                if (!etMobileNumber.getText().toString().replace("+91 ", "").equalsIgnoreCase("")) {
                    BrowsePlanJio dialog = new BrowsePlanJio();
                    Bundle b = new Bundle();
                    b.putString("MobNo", etMobileNumber.getText().toString().replace("+91 ", ""));
                    dialog.setArguments(b);
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    dialog.show(ft, BrowsePlanJio.TAG);
                } else {
                    showError(getResources().getString(R.string.valid_mob_no_err), etMobileNumber);
                }
                break;
        }
    }


    private void showError(String error_st, EditText editText) {
        Dialog error_dialog = new Dialog(this);
        error_dialog.setCanceledOnTouchOutside(false);
        error_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        error_dialog.setContentView(R.layout.error_dialoge);
        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
        int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.90);
        error_dialog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        error_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView error_text = error_dialog.findViewById(R.id.error_text);
        Button ok_btn = error_dialog.findViewById(R.id.ok_btn);
        error_text.setText(error_st);
        error_dialog.show();
        ok_btn.setOnClickListener(view -> {
            error_dialog.dismiss();
            requestFocus(editText);
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case RESULT_OK: // REQUEST CONFIRM....................................
                switch (requestCode) {
                    case CONTACT_STAT_CODE:
                        Uri contactData = data.getData();
                        assert contactData != null;
                        Cursor cur = Objects.requireNonNull(context).getContentResolver().query(contactData, null, null, null, null);
                        assert cur != null;
                        if (cur.getCount() > 0) {// thats mean some resutl has been found
                            if (cur.moveToNext()) {
                                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                                Log.d("Names", name);
                                if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                                    Cursor phones = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id, null, null);
                                    while (phones.moveToNext()) {
                                        String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                        String onlyNumber = phoneNumber.replaceAll("[^0-9]", "");
                                        Log.d("Number", phoneNumber);
                                        etMobileNumber.setText(getReplacePhoneNumber(onlyNumber));
                                    }
                                    phones.close();
                                }
                            }
                        }
                        cur.close();
                        break;
                    case OPERATOR_STAT_CODE:
                        if (data != null) {
                            Log.e("OPERATOR=======", "= CODE" + data.getStringExtra("CODE"));
                            Log.e("OPERATOR=======", "= OP_NAME " + data.getStringExtra("OP_NAME"));
                            operatorCode = data.getStringExtra("CODE");
                            String operatorName = data.getStringExtra("OP_NAME");
                            etMobileCurrentOperator.setText(operatorName);
                        }
                        break;
                }
        }
    }


    public boolean validation() {
        String strMobile = etMobileNumber.getText().toString().trim();
        String strCurrentOperator = etMobileCurrentOperator.getText().toString().trim();
        String strTotalAmount = etRechargeAmount.getText().toString().trim();
        if (strMobile.length() == 0) {
            etMobileNumber.setError(getString(R.string.acc_type));
            requestFocus(etMobileNumber);
            return false;
        } else if (strMobile.length() != 10) {
            etMobileNumber.setError(getString(R.string.hintChkMobile));
            requestFocus(etMobileNumber);
            return false;
        }

        if (strCurrentOperator.length() == 0) {
            etMobileCurrentOperator.setError(getString(R.string.hintChkOperator));
            requestFocus(etMobileCurrentOperator);
            return false;
        } else if (strCurrentOperator.equalsIgnoreCase(getString(R.string.chooseOperatorCode))) {
            etMobileCurrentOperator.setError(getString(R.string.hintChkOperator));
            requestFocus(etMobileCurrentOperator);
            return false;
        }

        if (strTotalAmount.length() == 0) {
            etRechargeAmount.setError(getString(R.string.hintChkRechrgAmt));
            requestFocus(etRechargeAmount);
            return false;
        }
        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            context.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }


    private String getReplacePhoneNumber(String number) {
        String newNumber = "";
        if (number.length() > 10) {
            if (number.startsWith("+91")) {
                newNumber = number.replaceFirst("\\+91", "");
                Log.e("-------->+91", newNumber);
            } else if (number.startsWith("91")) {
                newNumber = number.replaceFirst("91", "");
                Log.e("=======> 91", newNumber);
            } else if (number.startsWith("0")) {
                newNumber = number.replaceFirst("0", "");
                Log.e(">>>>>>>>>>0", newNumber);
            }
            return newNumber;
        }
        return number;
    }

    private static DecimalFormat format = new DecimalFormat("0.00");

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    @Override
    public void getClickChildPosition(String name, String tag, Bundle bundle) {
        super.getClickChildPosition(name, tag, bundle);
        etMobileNumber.setText(bundle.getString("mobile"));
        etMobileCurrentOperator.setText(bundle.getString("operator"));
        etRechargeAmount.setText(bundle.getString("amount"));

    }

    //TODO MY CHANGES IN MOBILE RECHARGES

    private void getRecentRechargeList(String action_type, String type) {
        try {
            if (!PreferencesManager.getInstance(context).getUSERID().equalsIgnoreCase("")) {
                JsonObject mainjson = new JsonObject();
                mainjson.addProperty("Action", action_type);
                mainjson.addProperty("FK_MemID", Cons.decryptMsg(PreferencesManager.getInstance(context).getUSERID(), cross_intent));
                mainjson.addProperty("Type", type);
                LoggerUtil.logItem(mainjson);

                Call<JsonObject> walletBalanceCall = apiServicesCyper.getRecentRecharges(bodyParam(mainjson), PreferencesManager.getInstance(context).getANDROIDID());
                walletBalanceCall.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                        hideLoading();
                        LoggerUtil.logItem(response.body());
                        try {
                            if (response.isSuccessful()) {
                                String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent);
                                ResponseRecentRecharges convertedObject = new Gson().fromJson(paramResponse, ResponseRecentRecharges.class);
                                LoggerUtil.logItem(convertedObject);


                                if (response.body() != null && convertedObject.getResponse().equalsIgnoreCase("Success")) {
                                    LinearLayoutManager manager = new LinearLayoutManager(context);
                                    manager.setOrientation(RecyclerView.VERTICAL);
                                    rvRchistory.setVisibility(View.VISIBLE);
                                    rvRchistory.setLayoutManager(manager);
                                    adapter = new RechargeHistoryAdapter(context, convertedObject.getRecentActivity(), MobileRechargeActivity.this);
                                    rvRchistory.setLayoutManager(new LinearLayoutManager(context));
                                    rvRchistory.setAdapter(adapter);
                                    rvRchistory.addOnItemTouchListener(new RecyclerTouchListener(context,
                                            rvRchistory,
                                            new RecyclerTouchListener.ClickListener() {
                                                @Override
                                                public void onClick(View view, int position) {

                                                }

                                                @Override
                                                public void onLongClick(View view, int position) {

                                                }
                                            }));
                                } else {
//                            showAlert("No records found.", R.color.red, R.drawable.alerter_ic_notifications);
                                    rvRchistory.setVisibility(View.GONE);
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
            } else {
                hideLoading();
            }
        } catch (Exception e) {
            e.printStackTrace();
            hideLoading();
        }

    }

    private void getWalletBalanceToken() {
        try {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("MemberId", Cons.decryptMsg(PreferencesManager.getInstance(context).getUSERID(), cross_intent));
            LoggerUtil.logItem(jsonObject);


            Call<JsonObject> walletBalanceCall = apiServicesCyper.getbalanceAmount(bodyParam(jsonObject), PreferencesManager.getInstance(context).getANDROIDID());
            walletBalanceCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                    hideLoading();
                    LoggerUtil.logItem(call.request().url());
                    LoggerUtil.logItem(response.body());
                    try {
                        if (response.isSuccessful()) {
                            String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent);
                            LoggerUtil.logItem(paramResponse);
                            ResponseBalanceAmount convertedObject = new Gson().fromJson(paramResponse, ResponseBalanceAmount.class);
                            if (convertedObject.getStatus().equalsIgnoreCase("Success")) {
                                hideKeyboard();
                                if (convertedObject.getBalanceAmount() >= Float.parseFloat(etRechargeAmount.getText().toString().trim())) {
                                    tempToken = convertedObject.getValidateToken();
                                    if (prepaid) {
                                        if (etMobileCurrentOperator.getText().toString().trim().equalsIgnoreCase("Jio")) {
                                            if (!jio_plan_id.equalsIgnoreCase("")) {
                                                getJioPrepaidRecharge();
                                            } else {
                                                showAlert("Please select plan from Browse Plan.", R.color.red, R.drawable.error);
                                            }
                                        } else {
                                            getPrepaidRecharge();
                                        }
                                    } else {
                                        getPostpaidRecharge();
                                    }
                                } else {
                                    showMessage("Insufficient bag balance,You have insufficient balance in your bag, add money before making transactions.");
                                }
                            } else {
                                tempToken = "";
                                showMessage("Something went wrong");
                            }
//                        else {
//                            tv_wallet_amount.setText(String.format("%s 0", getResources().getString(R.string.wallet_amt)));
//                        }

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


    private void getJioPrepaidRecharge() {
        try {
            showLoading();
            JsonObject mainjson = new JsonObject();
            mainjson.addProperty("NUMBER", etMobileNumber.getText().toString().trim());
            mainjson.addProperty("ValidateToken", tempToken);
            mainjson.addProperty("PlanOffer", jio_plan_id);
            mainjson.addProperty("AMOUNT", format.format(Double.parseDouble(etRechargeAmount.getText().toString().trim())));
            mainjson.addProperty("Provider", "Jio");
            mainjson.addProperty("Type", "Prepaid");
            mainjson.addProperty("FK_MemID", Cons.decryptMsg(PreferencesManager.getInstance(context).getUSERID(), cross_intent));
            LoggerUtil.logItem(mainjson);

            Call<JsonObject> prepaidCall = apiServicesCyper.getJioPrepaidRecharge(bodyParam(mainjson), PreferencesManager.getInstance(context).getANDROIDID());
            prepaidCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                    hideLoading();
                    LoggerUtil.logItem(response.body());
                    try {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                once = true;

                                String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent);
                                List<ResponseJioPrepaidRecharge> list = Utils.getList(paramResponse, ResponseJioPrepaidRecharge.class);
                                LoggerUtil.logItem(list);
                                if (list.get(0).getError().equalsIgnoreCase("0") && (list.get(0).getResult().equalsIgnoreCase("0"))) {
                                    showMessage("Recharge Successfully");
                                } else {
                                    CheckErrorCode code = new CheckErrorCode();
                                    code.isValidTransaction(context, list.get(0).getError());
                                    showMessage("Recharge Failed");
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
                        showMessage("Something went wrong. \nPlease try after some time.");
                    }
                }

                @Override
                public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void getPrepaidRecharge() {
        try {
            showLoading();
            JsonObject mainjson = new JsonObject();
            mainjson.addProperty("NUMBER", etMobileNumber.getText().toString().trim());
            mainjson.addProperty("ValidateToken", tempToken);
            mainjson.addProperty("ACCOUNT", AppConfig.PAYLOAD_ACCOUNT_RECHARGE_TWO);
            mainjson.addProperty("AMOUNT", format.format(Double.parseDouble(etRechargeAmount.getText().toString().trim())));
            mainjson.addProperty("Provider", etMobileCurrentOperator.getText().toString().trim());
            mainjson.addProperty("FK_MemID", Cons.decryptMsg(PreferencesManager.getInstance(context).getUSERID(), cross_intent));
            LoggerUtil.logItem(mainjson);

            Call<JsonObject> prepaidCall = apiServicesCyper.getPrepaidRecharge(bodyParam(mainjson), PreferencesManager.getInstance(context).getANDROIDID());
            prepaidCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                    hideLoading();
                    LoggerUtil.logItem(call.request().url());

                    LoggerUtil.logItem(response.body());
                    try {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                once = true;
                                String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent);
                                LoggerUtil.logItem(paramResponse);
                                List<ResponsePrepaidRecharge> list = Utils.getList(paramResponse, ResponsePrepaidRecharge.class);
                                LoggerUtil.logItem(list);
                                if (list.get(0).getError().equalsIgnoreCase("0") && (list.get(0).getResult().equalsIgnoreCase("0"))) {
//                                    sendParam.putString("TRAK_ID", list.get(0).getTransid());
//                                    sendParam.putString("TRANS_STATUS", "Recharged Successfully");
//                                    sendParam.putString("OPERATOR__TYPE", operatorType);
//                                    sendParam.putString("MOBNO", etMobileNumber.getText().toString().trim());
//                                    sendParam.putString("AMT", format.format(Double.parseDouble(etRechargeAmount.getText().toString().trim())));
//                                    sendParam.putString("Date", list.get(0).getDate());
//                                    goToActivity(context, RechargeStatus.class, sendParam);
                                    showMessage("Recharged Successfully");
                                } else {
//                                    CheckErrorCode code = new CheckErrorCode();
//                                    code.isValidTransaction(context, list.get(0).getError());
//                                    sendParam.putString("TRAK_ID", list.get(0).getTransid());
//                                    sendParam.putString("TRANS_STATUS", "Failed");
//                                    sendParam.putString("OPERATOR__TYPE", operatorType);
//                                    sendParam.putString("MOBNO", etMobileNumber.getText().toString().trim());
//                                    sendParam.putString("AMT", format.format(Double.parseDouble(etRechargeAmount.getText().toString().trim())));
//                                    sendParam.putString("Date", list.get(0).getDate());
//                                    goToActivity(context, RechargeStatus.class, sendParam);
                                    showMessage("Recharged Failed");
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
                        showMessage("Something went wrong. \nPlease try after some time.");
                    }
                }

                @Override
                public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getPostpaidRecharge() {
        try {
            showLoading();
            JsonObject mainjson = new JsonObject();
            mainjson.addProperty("NUMBER", etMobileNumber.getText().toString().trim());
            mainjson.addProperty("ValidateToken", tempToken);
            mainjson.addProperty("AMOUNT_ALL", format.format(Double.parseDouble(etRechargeAmount.getText().toString().trim())));
            mainjson.addProperty("AMOUNT", format.format(Double.parseDouble(etRechargeAmount.getText().toString().trim())));
            mainjson.addProperty("Provider", etMobileCurrentOperator.getText().toString().trim());
            mainjson.addProperty("Type", "Postpaid");
            mainjson.addProperty("FK_MemID", Cons.decryptMsg(PreferencesManager.getInstance(context).getUSERID(), cross_intent));
            LoggerUtil.logItem(mainjson);

            Call<JsonObject> postpaidCall = apiServicesCyper.getPostpaidRecharge(bodyParam(mainjson), PreferencesManager.getInstance(context).getANDROIDID());

            postpaidCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                    hideLoading();
                    LoggerUtil.logItem(call.request().url());

                    LoggerUtil.logItem(response.body());
                    try {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                once = true;
                                String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent);
                                LoggerUtil.logItem(paramResponse);

                                List<ResponsePostpaidRecharge> list = Utils.getList(paramResponse, ResponsePostpaidRecharge.class);

                                if (list.get(0).getError().equalsIgnoreCase("0") && (list.get(0).getResult().equalsIgnoreCase("0"))) {
          /*                          sendParam.putString("TRAK_ID", list.get(0).getTransid());
                                    sendParam.putString("TRANS_STATUS", "Recharged Successfully");
                                    sendParam.putString("OPERATOR__TYPE", operator_st);
                                    sendParam.putString("MOBNO", mobileno_st);
                                    sendParam.putString("AMT", format.format(Double.parseDouble(amount_st)));
                                    sendParam.putString("Date", list.get(0).getDate());
                                    goToActivity(context, RechargeStatus.class, sendParam);*/
                                    showMessage("Recharged Successfully");
                                } else {
                                /*          CheckErrorCode code = new CheckErrorCode();
                                    code.isValidTransaction(context, list.get(0).getError());
                              sendParam.putString("TRAK_ID", list.get(0).getTransid());
                                    sendParam.putString("TRANS_STATUS", "Failed");
                                    sendParam.putString("OPERATOR__TYPE", operator_st);
                                    sendParam.putString("MOBNO", mobileno_st);
                                    sendParam.putString("AMT", format.format(Double.parseDouble(amount_st)));
                                    sendParam.putString("Date", list.get(0).getDate());
                                    goToActivity(context, RechargeStatus.class, sendParam);*/
                                    showMessage("Recharged Failed");
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
                        showMessage("Something went wrong. \nPlease try after some time.");
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
    protected void onResume() {
        super.onResume();
        getRecentRechargeList("PrepaidMobile", "");
    }
}
