package com.shoppingbag.billpayment.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shoppingbag.app.CheckErrorCode;
import com.shoppingbag.app.PreferencesManager;
import com.shoppingbag.constants.BaseActivity;
import com.shoppingbag.constants.Cons;
import com.shoppingbag.model.bill.GetAllProviderStateWiseListItem;
import com.shoppingbag.model.bill.ResponseBillPayment;
import com.shoppingbag.model.bill.ResponseElectricityState;
import com.shoppingbag.model.bill.ResponseElectricityVerification;
import com.shoppingbag.model.request.utility.response.ResponseBalanceAmount;
import com.shoppingbag.model.utility.ResponseRecentRecharges;
import com.shoppingbag.utils.LoggerUtil;
import com.shoppingbag.utils.NetworkUtils;
import com.shoppingbag.utils.Utils;
import com.shoppingbag.R;
import com.shoppingbag.billpayment.adapter.RecentBillPayment;
import com.shoppingbag.common_activities.LoginActivity;
import com.shoppingbag.common_activities.MaintenancePage;
import com.shoppingbag.wallet.AddMoney;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.shoppingbag.app.AppConfig.PAYLOAD_BUNDLE;


public class ElectricityBillPayment extends BaseActivity {

    public static List<GetAllProviderStateWiseListItem> allState;
    private static DecimalFormat formatWallet = new DecimalFormat("0.00");
    @BindView(R.id.side_menu)
    ImageView sideMenu;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.et_board)
    TextInputEditText etBoard;
    @BindView(R.id.input_layout_board)
    TextInputLayout inputLayoutBoard;
    @BindView(R.id.et_service_number)
    TextInputEditText etServiceNumber;
    @BindView(R.id.input_layout_service_num)
    TextInputLayout inputLayoutServiceNum;
    @BindView(R.id.service_number_lo)
    RelativeLayout serviceNumberLo;
    @BindView(R.id.et_account)
    TextInputEditText etAccount;
    @BindView(R.id.input_layout_account)
    TextInputLayout inputLayoutAccount;
    @BindView(R.id.et_authenticator)
    TextInputEditText etAuthenticator;
    @BindView(R.id.input_layout_authenticator)
    TextInputLayout inputLayoutAuthenticator;
    @BindView(R.id.et_amount)
    TextInputEditText etAmount;
    @BindView(R.id.input_layout_amount)
    TextInputLayout inputLayoutAmount;
    @BindView(R.id.make_payment_btn_ele_board)
    Button makePaymentBtnEleBoard;
    @BindView(R.id.rv_recent)
    RecyclerView rvRecent;
    @BindView(R.id.et_state)
    AutoCompleteTextView etState;
    InputFilter filter = (source, start, end, dest, dstart, dend) -> {
        for (int i = start; i < end; ++i) {
            if (!Pattern.compile("[ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890]*").matcher(String.valueOf(source.charAt(i))).matches()) {
                return "";
            }
        }

        return null;
    };
    String stateId = "";
    EditText editTextTransPwd;
    String tempToken;
    private Bundle param;
    private String totalAmount = "";
    private ArrayList<String> stateName;
    private android.app.AlertDialog alertDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.electricity_bill_payment);
        ButterKnife.bind(this);

        param = getIntent().getBundleExtra(PAYLOAD_BUNDLE);
        title.setText(param.getString("bill"));

        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            getState();
        } else {
            showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
        }

        etState.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                stateId = "";
                inputLayoutBoard.setVisibility(View.GONE);
                inputLayoutAccount.setVisibility(View.GONE);
                etBoard.setText("");
                etAccount.setText("");
                serviceNumberLo.setVisibility(View.GONE);
            }
        });

        etState.setOnItemClickListener((parent, view, position, id) -> {
            try {
                String state_Name = (String) parent.getItemAtPosition(position);
                LoggerUtil.logItem(state_Name);
                for (int i = 0; i < allState.size(); i++) {
                    if (allState.get(i).getStateName().equalsIgnoreCase(state_Name)) {
                        stateId = String.valueOf(allState.get(i).getPKStateId());
                        etBoard.setText("");
                        inputLayoutBoard.setVisibility(View.VISIBLE);
                        getElectricityProvider(stateId);
                        LoggerUtil.logItem(stateId);
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    private void getState() {
        try {
            showLoading();
            JsonObject param = new JsonObject();
            param.addProperty("StateId", "0");

            Call<JsonObject> providerListCall = apiServicesCyper.getElectricityProvider(bodyParam(param), PreferencesManager.getInstance(context).getANDROIDID());
            providerListCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                    LoggerUtil.logItem(call.request().url());
                    hideLoading();
                    try {
                        if (response.isSuccessful()) {
                            allState = new ArrayList<>();
                            getRecentList(title.getText().toString());
                            LoggerUtil.logItem(response.body());

                            String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent);
                            LoggerUtil.logItem(paramResponse);
                            ResponseElectricityState convertedObject = new Gson().fromJson(paramResponse, ResponseElectricityState.class);
                            LoggerUtil.logItem(convertedObject);

                            if (response.body() != null && convertedObject.getResponse().equalsIgnoreCase("Success")) {

                                allState = convertedObject.getGetAllProviderStateWiseList();
                                stateName = new ArrayList<>();
                                for (int i = 0; i < allState.size(); i++) {
                                    stateName.add(i, allState.get(i).getStateName());
                                }
                                etState.setThreshold(1);
                                etState.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, stateName));
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
                public void onFailure(@NotNull Call<JsonObject> call, @NotNull Throwable t) {
                    hideLoading();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.side_menu, R.id.et_board, R.id.verify, R.id.make_payment_btn_ele_board})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.side_menu:
                hideKeyboard();
                finish();
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                break;
            case R.id.et_board:
                if (Cons.allElectricityProviderlist.size() > 0) {
                    Intent intent = new Intent(context, BillPaymentBoard.class);
                    intent.putExtra("bill", title.getText().toString());
                    startActivityForResult(intent, 100);
                }
                break;
            case R.id.verify:
                if (etServiceNumber.getText().toString().length() > 0 && etBoard.getText().toString().length() > 0) {
                    if (NetworkUtils.getConnectivityStatus(context) != 0) {
                        verifyElectricityNumber();
                    }
                } else
                    Toast.makeText(this, "Enter valid Service Number", Toast.LENGTH_SHORT).show();
                break;
            case R.id.make_payment_btn_ele_board:
                if (ValidationEleBoard()) {
                    hideKeyboard();
                    getWalletBalance();
                }
                break;
        }
    }

    private void getElectricityProvider(String stateId) {
        try {
            showLoading();
            JsonObject param = new JsonObject();
            param.addProperty("StateId", stateId);

            Call<JsonObject> providerListCall = apiServicesCyper.getElectricityProvider(bodyParam(param), PreferencesManager.getInstance(context).getANDROIDID());
            providerListCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                    hideLoading();
                    Cons.allElectricityProviderlist = new ArrayList<>();
                    try {
                        if (response.isSuccessful()) {
                            LoggerUtil.logItem(response.body());

                            String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent);
                            LoggerUtil.logItem(call.request().url());
                            LoggerUtil.logItem(paramResponse);
                            ResponseElectricityState convertedObject = new Gson().fromJson(paramResponse, ResponseElectricityState.class);

                            if (response.body() != null && convertedObject.getResponse().equalsIgnoreCase("Success")) {
                                Cons.allElectricityProviderlist = convertedObject.getGetAllProviderStateWiseList();
                                LoggerUtil.logItem(Cons.allElectricityProviderlist);
                                inputLayoutBoard.setVisibility(View.VISIBLE);
                            } else {
                                inputLayoutBoard.setVisibility(View.GONE);
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
                public void onFailure(@NotNull Call<JsonObject> call, @NotNull Throwable t) {
                    hideLoading();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean ValidationEleBoard() {
        totalAmount = etAmount.getText().toString();
        if (stateId.trim().length() == 0) {
            etState.setError("Enter State name");
            return false;
        } else if (etBoard.getText().toString().trim().length() == 0) {
            etBoard.setError(getString(R.string.select_board_err));
            return false;
        } else if (etServiceNumber.getText().toString().trim().length() == 0) {
            etServiceNumber.setError(getString(R.string.service_number_err));
            return false;
        } else if (inputLayoutAccount.isShown() && etAccount.getText().toString().trim().length() == 0) {
            etAccount.setError(getString(R.string.service_number_err));
            return false;
        } else if (inputLayoutAuthenticator.isShown() && etAuthenticator.getText().toString().trim().length() == 0) {
            etAuthenticator.setError(getString(R.string.service_number_err));
            return false;
        } else if (etAmount.getText().toString().trim().length() == 0) {
            etAmount.setError(getString(R.string.service_number_err));
            return false;
        }
        return true;
    }

    private void verifyElectricityNumber() {
        try {
            showLoading();
            hideKeyboard();

            JsonObject param = new JsonObject();
            param.addProperty("Authenticator", etAuthenticator.getText().toString().trim());
            param.addProperty("AMOUNT", "10.00");
            param.addProperty("AMOUNT_ALL", "10.00");
            param.addProperty("ACCOUNT", etAccount.getText().toString().trim());
            param.addProperty("Type", Cons.ELECTRICITY_BILL_PAYMENT);
            param.addProperty("FK_MemID", Cons.decryptMsg(PreferencesManager.getInstance(context).getUSERID(), cross_intent));
            param.addProperty("NUMBER", etServiceNumber.getText().toString().trim());
            param.addProperty("Provider", etBoard.getText().toString().trim());

            LoggerUtil.logItem(param);

            Call<JsonObject> call = apiServicesCyper.getElectricityVerification(bodyParam(param), PreferencesManager.getInstance(context).getANDROIDID());
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {

                    hideLoading();
                    LoggerUtil.logItem(response.body());
                    try {
                        if (response.isSuccessful()) {
                            String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent);
                            LoggerUtil.logItem(call.request().url());
                            LoggerUtil.logItem(paramResponse);
                            List<ResponseElectricityVerification> list = Utils.getList(paramResponse, ResponseElectricityVerification.class);

                            if (response.body() != null && list.get(0).getPrice() != null && list.get(0).getPrice().length() != 0) {
                                etAmount.setText(formatWallet.format(Double.parseDouble(list.get(0).getPrice())));
                                inputLayoutAmount.setVisibility(View.VISIBLE);
                                makePaymentBtnEleBoard.setVisibility(View.VISIBLE);
                            } else if (list.get(0).getErrmsg().length() != 0) {
                                showAlert(list.get(0).getErrmsg(), R.color.red, R.drawable.error);
                            } else {
                                CheckErrorCode code = new CheckErrorCode();
                                code.isValidTransaction(context, list.get(0).getError());
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
                public void onFailure(@NotNull Call<JsonObject> call, @NotNull Throwable t) {
                    hideLoading();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
//
//    private void confirmTransPwd() {
//        try {
//            LayoutInflater li = LayoutInflater.from(context);
//            View confirmDialog = li.inflate(R.layout.confirm_trans_otp_dialog, null);
//            AppCompatButton buttonConfirm = confirmDialog.findViewById(R.id.buttonConfirm);
//            AppCompatButton buttonCancel = confirmDialog.findViewById(R.id.buttonCancel);
//            editTextTransPwd = confirmDialog.findViewById(R.id.editTextTransPwd);
//
//
//            //Creating an alertdialog builder
//            android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(context);
//
//            //Adding our dialog box to the view of alert dialog
//            alert.setView(confirmDialog);
//
//            //Creating an alert dialog
//            alertDialog = alert.create();
//            alertDialog.setCancelable(false);
//            alertDialog.setCanceledOnTouchOutside(false);
//
//            buttonCancel.setOnClickListener(v -> alertDialog.dismiss());
//
//            buttonConfirm.setOnClickListener(view -> {
////                Cons.decryptMsg(PreferencesManager.getInstance(context).getMOBILE(), cross_intent)
//                verifyTransactionPwdAPI(editTextTransPwd.getText().toString().trim());
//                alertDialog.dismiss();
//            });

//            //Displaying the alert dialog
//            alertDialog.show();
//        } catch (Error | Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    private void verifyTransactionPwdAPI(String pass) {
//        try {
//            JsonObject mainjson = new JsonObject();
//            mainjson.addProperty("Mobile", Cons.decryptMsg(PreferencesManager.getInstance(context).getMOBILE(), cross_intent));
//            mainjson.addProperty("OldPassword", pass);
//
//            Call<JsonObject> verifyTransaction = apiServicesCyper.getElectricityVerification(bodyParam(mainjson), PreferencesManager.getInstance(context).getANDROIDID());
//
//            verifyTransaction.enqueue(new Callback<JsonObject>() {
//                @Override
//                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
//                    hideLoading();
//
//                    LoggerUtil.logItem(response.body());
//
//                    try {
//                        if (response.isSuccessful()) {
//                            String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent);
//                            JsonObject convertedObject = new Gson().fromJson(paramResponse, JsonObject.class);
//                            LoggerUtil.logItem(convertedObject);
//                            if (response.body() != null && convertedObject.get("response").getAsString().equalsIgnoreCase("Success")) {
//                                getWalletBalance();
//                            } else {
//                                showMessage("Invalid transaction password.");
//                            }
//                        } else if (response.code() == 403) {
//                            clearPrefrenceforTokenExpiry();
//                            getAndroidId();
//                            goToActivityWithFinish(LoginActivity.class, null);
//                        } else {
//                            goToActivityWithFinish(MaintenancePage.class, null);
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                @Override
//                public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
//                    hideLoading();
//                }
//            });
//        } catch (Error | Exception e) {
//            e.printStackTrace();
//        }
//    }

    private void getWalletBalance() {
        try {

            showLoading();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("MemberId", Cons.decryptMsg(PreferencesManager.getInstance(context).getUSERID(), cross_intent));
            LoggerUtil.logItem(jsonObject);


            Call<JsonObject> walletBalanceCall = apiServicesCyper.getbalanceAmount(bodyParam(jsonObject), PreferencesManager.getInstance(context).getANDROIDID());
            walletBalanceCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                    hideLoading();
                    LoggerUtil.logItem(response.body());
                    try {
                        if (response.isSuccessful()) {
                            String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent);
                            LoggerUtil.logItem(call.request().url());
                            LoggerUtil.logItem(paramResponse);
                            ResponseBalanceAmount convertedObject = new Gson().fromJson(paramResponse, ResponseBalanceAmount.class);
                            if (convertedObject.getStatus().equalsIgnoreCase("Success")) {
                                tempToken = convertedObject.getValidateToken();
                                if (convertedObject.getBalanceAmount() >= Float.parseFloat(totalAmount)) {
                                    getBillPayment();
                                } else
                                    createAddBalanceDialog(context, "Insufficient bag balance", "You have insufficient balance in your bag, add money before making transactions.");
                            } else {
                                tempToken = "";
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
        } catch (Exception e) {
            e.printStackTrace();
            hideLoading();
        }
    }

    public void createAddBalanceDialog(Context context, String title, String msg) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setTitle(title);
        builder1.setMessage(msg);
        builder1.setCancelable(true);

        builder1.setNegativeButton("Add Balance", (dialog, id) -> {
            dialog.cancel();
            Bundle b = new Bundle();
            b.putString("total", totalAmount);
            b.putString("from", "billpayment");

            goToActivity(ElectricityBillPayment.this, AddMoney.class, b);
        });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    private void getBillPayment() {
        totalAmount = formatWallet.format(Double.parseDouble(totalAmount));
        showLoading();

        try {

            JsonObject param = new JsonObject();
            param.addProperty("ValidateToken", tempToken);
            param.addProperty("Authenticator", etAuthenticator.getText().toString().trim());
            param.addProperty("AMOUNT", totalAmount);
            param.addProperty("AMOUNT_ALL", totalAmount);
            param.addProperty("ACCOUNT", etAccount.getText().toString().trim());
            param.addProperty("Type", Cons.ELECTRICITY_BILL_PAYMENT);
            param.addProperty("FK_MemID", Cons.decryptMsg(PreferencesManager.getInstance(context).getUSERID(), cross_intent));
            param.addProperty("NUMBER", etServiceNumber.getText().toString().trim());
            param.addProperty("Provider", etBoard.getText().toString().trim());

            LoggerUtil.logItem(param);

            Call<JsonObject> arrayCall = apiServicesCyper.billPayment(bodyParam(param), PreferencesManager.getInstance(context).getANDROIDID());

            arrayCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                    hideLoading();
                    LoggerUtil.logItem(response.body());
                    try {
                        if (response.isSuccessful()) {
                            String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent);
                            LoggerUtil.logItem(call.request().url());
                            LoggerUtil.logItem(paramResponse);
                            List<ResponseBillPayment> list = Utils.getList(paramResponse, ResponseBillPayment.class);

                            if (response.body() != null && list.size() > 0) {
                                ResponseBillPayment billPayment1 = list.get(0);
                                if (billPayment1.getError().equalsIgnoreCase("0") &&
                                        billPayment1.getResult().equalsIgnoreCase("0")) {
                                    showMessage(getResources().getString(R.string.payment_done_to));
                                    getRecentList(title.getText().toString());
                                } else if (list.get(0).getErrmsg().length() != 0) {
                                    showAlert(list.get(0).getErrmsg(), R.color.red, R.drawable.error);
                                } else {
                                    CheckErrorCode code = new CheckErrorCode();
                                    code.isValidTransaction(context, list.get(0).getError());
                                }

                            } else {
                                showMessage("Something went wrong !");
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
                    onBackPressed();

                }

                @Override
                public void onFailure(@NotNull Call<JsonObject> call, @NotNull Throwable t) {
                    LoggerUtil.logItem(t.getMessage());
                    hideLoading();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getRecentList(String action_type) {
        try {
            if (!PreferencesManager.getInstance(context).getUSERID().equalsIgnoreCase("")) {
                JsonObject mainjson = new JsonObject();
                mainjson.addProperty("Action", "BBPS");
                mainjson.addProperty("FK_MemID", Cons.decryptMsg(PreferencesManager.getInstance(context).getUSERID(), cross_intent));
                mainjson.addProperty("Type", action_type);
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
                                LoggerUtil.logItem(call.request().url());
                                LoggerUtil.logItem(paramResponse);
                                ResponseRecentRecharges convertedObject = new Gson().fromJson(paramResponse, ResponseRecentRecharges.class);

                                if (convertedObject.getResponse().equalsIgnoreCase("Success")) {
                                    LinearLayoutManager manager = new LinearLayoutManager(context);
                                    manager.setOrientation(RecyclerView.VERTICAL);
                                    rvRecent.setLayoutManager(manager);
                                    rvRecent.setHasFixedSize(true);
                                    RecentBillPayment adapter = new RecentBillPayment(context, convertedObject.getRecentActivity(), ElectricityBillPayment.this);
                                    rvRecent.setAdapter(adapter);
                                } else {
//                            showAlert("No records found.", R.color.red, R.drawable.alerter_ic_notifications);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 100) {
            String board = data.getStringExtra("data");
            String hint = data.getStringExtra("hint");
            LoggerUtil.logItem(board);
            etBoard.setText(board);
            inputLayoutServiceNum.setHelperText(hint);
            inputLayoutAccount.setVisibility(View.GONE);
            inputLayoutAuthenticator.setVisibility(View.GONE);
            makePaymentBtnEleBoard.setVisibility(View.GONE);
            inputLayoutAccount.setHint("Account");
            etAccount.setText("");
            etAuthenticator.setText("");

            etAuthenticator.setFilters(new InputFilter[]{filter, new InputFilter.LengthFilter(10)});
            etAccount.setFilters(new InputFilter[]{filter, new InputFilter.LengthFilter(10)});
            etAmount.setText("");
            inputLayoutAmount.setVisibility(View.GONE);
            serviceNumberLo.setVisibility(View.VISIBLE);
            etServiceNumber.setText("");
            switch (board.trim()) {
                case "Dakshin Haryana Bijli Vitran Nigam":
                case "West Bengal State Electricity":
                    inputLayoutAccount.setVisibility(View.VISIBLE);
                    inputLayoutAccount.setHint("Mobile Number (10 digits)");
                    etAccount.setFilters(new InputFilter[]{filter, new InputFilter.LengthFilter(10)});
                    break;
                case "Jharkhand Bijli Vitran Nigam Limited":
                    inputLayoutAccount.setVisibility(View.VISIBLE);
                    inputLayoutAccount.setHint("Subdivision Code(1-3 digits)");
                    etAccount.setFilters(new InputFilter[]{filter, new InputFilter.LengthFilter(3)});
                    break;
                case "Torrent Power":
                    inputLayoutAccount.setVisibility(View.VISIBLE);
                    inputLayoutAccount.setHint("City Name (1-30 characters)");
                    etAccount.setFilters(new InputFilter[]{filter, new InputFilter.LengthFilter(30)});
                    break;
                case "Uttar Haryana Bijli Vitran Nigam":
                    inputLayoutAccount.setVisibility(View.VISIBLE);
                    inputLayoutAccount.setHint("Mobile Number(10 digits)");
                    etAccount.setFilters(new InputFilter[]{filter, new InputFilter.LengthFilter(10)});
                    break;
                case "Adani Electricity Mumbai Limited":// (Note- "Reliance Energy Limited" renamed to "Adani Electricity Mumbai Limited")
                    inputLayoutAccount.setVisibility(View.VISIBLE);
                    inputLayoutAccount.setHint("Cycle Number (2 digits)");
                    etAccount.setFilters(new InputFilter[]{filter, new InputFilter.LengthFilter(2)});
                    break;
                case "MSEDC Limited":
                    inputLayoutAccount.setVisibility(View.VISIBLE);
                    inputLayoutAccount.setHint("Billing Unit (4 digits)");
                    etAccount.setFilters(new InputFilter[]{filter, new InputFilter.LengthFilter(4)});
                    inputLayoutAuthenticator.setHint("Processing Cycle(PC) (2 digits)");
                    etAuthenticator.setFilters(new InputFilter[]{filter, new InputFilter.LengthFilter(2)});
                    inputLayoutAuthenticator.setVisibility(View.VISIBLE);
                    break;

            }
        }
    }
}
