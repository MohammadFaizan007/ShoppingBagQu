package com.shoppingbag.themepark;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.PopupMenu;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.shoppingbag.BuildConfig;
import com.shoppingbag.R;
import com.shoppingbag.app.PreferencesManager;
import com.shoppingbag.common_activities.LoginActivity;
import com.shoppingbag.common_activities.MaintenancePage;
import com.shoppingbag.constants.BaseActivity;
import com.shoppingbag.constants.Cons;
import com.shoppingbag.model.request.utility.response.ResponseBalanceAmount;
import com.shoppingbag.model.response.pincode.ResponsePincode;
import com.shoppingbag.model.themeParkResponse.ResponseBookTemePark;
import com.shoppingbag.model.themeParkResponse.themeParkAvailabilityArray.CategoriesItem;
import com.shoppingbag.model.themeParkResponse.themeParkAvailabilityArray.TourOptionsItem;
import com.shoppingbag.utils.LoggerUtil;
import com.shoppingbag.utils.NetworkUtils;
import com.shoppingbag.utils.Utils;
import com.shoppingbag.wallet.AddMoney;
import com.travijuu.numberpicker.library.NumberPicker;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookPark extends BaseActivity {

    @BindView(R.id.first_name_et)
    TextInputEditText firstNameEt;
    @BindView(R.id.last_name_et)
    TextInputEditText lastNameEt;
    @BindView(R.id.mobile_edt_no)
    TextInputEditText mobileEdtNo;
    @BindView(R.id.email_et)
    TextInputEditText emailEt;
    @BindView(R.id.address_et1)
    TextInputEditText addressEt1;
    @BindView(R.id.address_et2)
    TextInputEditText addressEt2;
    @BindView(R.id.pin_et)
    TextInputEditText pinEt;
    @BindView(R.id.city_et)
    TextInputEditText cityEt;
    @BindView(R.id.state_et)
    TextInputEditText stateEt;
    @BindView(R.id.tv_tour_package)
    TextView tv_tour_package;
    @BindView(R.id.side_menu)
    ImageView sideMenu;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_category)
    TextView tvCategory;
    @BindView(R.id.qtyBox)
    NumberPicker qtyBox;
    EditText editTextTransPwd;
    String tempToken;
    private PopupMenu tour_menu, tour_category;
    private String tourItemId = "";
    private List<TourOptionsItem> tourItem = new ArrayList<>();
    private List<CategoriesItem> cateItem = new ArrayList<>();
    private String productId = "";
    private String pricingId = "";
    private String pricingName = "";
    private int catePosition;
    private float totalAmount;
    private android.app.AlertDialog alertDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.theme_park_book_form);
        ButterKnife.bind(this);

        title.setText("Theme Park Booking");

        productId = getIntent().getStringExtra("productId");
        //Log.e("Product", "= " + pricingId);

        tour_menu = new PopupMenu(context, tv_tour_package);
        tour_category = new PopupMenu(context, tvCategory);

        tourItem = Cons.responseAvailabilityArrays.get(0).getData().getTourOptions();
        LoggerUtil.logItem(tourItem);

        for (int i = 0; i < tourItem.size(); i++) {
            tour_menu.getMenu().add(0, Integer.parseInt(tourItem.get(i).getId()), i, tourItem.get(i).getTitle());
        }

        pinEt.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() != 0 && s.length() == 6) {
                    hideKeyboard();
                    getStateCityName(pinEt.getText().toString().trim());
                } else {
                    stateEt.setText("");
                    cityEt.setText("");
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        qtyBox.setValueChangedListener((value, action) -> LoggerUtil.logItem(value));
    }

    @OnClick({R.id.tv_tour_package, R.id.btn_make_payment, R.id.side_menu, R.id.tv_category})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_tour_package:
                tour_menu.setOnMenuItemClickListener(menuItem -> {
                    tourItemId = String.valueOf(menuItem.getItemId());
                    tv_tour_package.setText(menuItem.getTitle());
                    cateItem = tourItem.get(menuItem.getOrder()).getPricing().getCategories();
                    tvCategory.setText("");
                    pricingName = "";
                    pricingId = "";
                    tour_category.getMenu().clear();
                    for (int i = 0; i < cateItem.size(); i++) {
                        tour_category.getMenu().add(0, Integer.valueOf(cateItem.get(i).getId()), i, cateItem.get(i).getName());
                    }
                    return true;
                });
                tour_menu.show();
                break;
            case R.id.btn_make_payment:
                if (validation()) {
                    if (NetworkUtils.getConnectivityStatus(context) != 0) {
                        confirmTransPwd();
                    }
                }
                break;
            case R.id.side_menu:
                onBackPressed();
                break;
            case R.id.tv_category:
                tour_category.setOnMenuItemClickListener(menuItem -> {

                    pricingId = String.valueOf(menuItem.getItemId());
                    pricingName = String.valueOf(menuItem.getTitle());
                    tvCategory.setText(pricingName);
                    catePosition = menuItem.getOrder();
                    qtyBox.setVisibility(View.VISIBLE);
                    qtyBox.setValue(0);
                    qtyBox.setMax(Integer.parseInt(cateItem.get(menuItem.getOrder()).getScale().get(0).getScaleMaxParticipant()));
                    qtyBox.setMin(0);

                    return true;
                });
                tour_category.show();
                break;
        }
    }

    private void confirmTransPwd() {
        try {
            LayoutInflater li = LayoutInflater.from(context);
            View confirmDialog = li.inflate(R.layout.confirm_trans_otp_dialog, null);
            AppCompatButton buttonConfirm = confirmDialog.findViewById(R.id.buttonConfirm);
            AppCompatButton buttonCancel = confirmDialog.findViewById(R.id.buttonCancel);
            editTextTransPwd = confirmDialog.findViewById(R.id.editTextTransPwd);


            //Creating an alertdialog builder
            android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(context);

            //Adding our dialog box to the view of alert dialog
            alert.setView(confirmDialog);

            //Creating an alert dialog
            alertDialog = alert.create();
            alertDialog.setCancelable(false);
            alertDialog.setCanceledOnTouchOutside(false);

            buttonCancel.setOnClickListener(v -> alertDialog.dismiss());

            buttonConfirm.setOnClickListener(view -> {
//                Cons.decryptMsg(PreferencesManager.getInstance(context).getMOBILE(), cross_intent)
                verifyTransactionPwdAPI(editTextTransPwd.getText().toString().trim());
                alertDialog.dismiss();
            });

            //Displaying the alert dialog
            alertDialog.show();
        } catch (Error | Exception e) {
            e.printStackTrace();
        }

    }

    private void verifyTransactionPwdAPI(String pass) {
        try {
            JsonObject mainjson = new JsonObject();
            mainjson.addProperty("Mobile", Cons.decryptMsg(PreferencesManager.getInstance(context).getMOBILE(), cross_intent));
            mainjson.addProperty("OldPassword", pass);

            Call<JsonObject> verifyTransaction = apiServicesLogin.verifyTransaction(bodyParam(mainjson), PreferencesManager.getInstance(context).getANDROIDID());

            verifyTransaction.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                    hideLoading();
                    LoggerUtil.logItem(response.body());

                    try {
                        if (response.isSuccessful()) {
                            String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent);
                            JsonObject convertedObject = new Gson().fromJson(paramResponse, JsonObject.class);
                            LoggerUtil.logItem(convertedObject);
                            if (response.body() != null && convertedObject.get("response").getAsString().equalsIgnoreCase("Success")) {
                                getWalletBalance();
                            } else {
                                showMessage("Invalid transaction password.");
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
        } catch (Error | Exception e) {
            e.printStackTrace();
        }
    }

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
                            ResponseBalanceAmount convertedObject = new Gson().fromJson(paramResponse, ResponseBalanceAmount.class);
                            if (convertedObject.getStatus().equalsIgnoreCase("Success")) {
                                tempToken = convertedObject.getValidateToken();
                                if (convertedObject.getBalanceAmount() >= totalAmount) {
                                    getParkBooked(productId, totalAmount, Cons.responseAvailabilityArrays.get(0).getData().getAvailabilities().getVisitDate());
                                } else
                                    createAddBalanceDialog(context, "Insufficient bag balance", "You have insufficient balance in your bag, add money before making transactions.");
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
            b.putString("total", String.valueOf(totalAmount));
            b.putString("from", "bookpark");

            goToActivity(BookPark.this, AddMoney.class, b);
        });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    private void getParkBooked(String productId, float amount, String date) {
        showLoading();

        try {
            JsonObject param = new JsonObject();
            param.addProperty("ValidateToken", tempToken);
            param.addProperty("FK_MemID", Cons.decryptMsg(PreferencesManager.getInstance(context).getUSERID(), cross_intent));
            param.addProperty("ProductId", productId);
            param.addProperty("AMOUNT", String.valueOf(amount));
            param.addProperty("AMOUNT_ALL", String.valueOf(amount));
            param.addProperty("benAddressline1", addressEt1.getText().toString().trim());
            param.addProperty("benAddressline2", addressEt2.getText().toString().trim());
            param.addProperty("benCity", cityEt.getText().toString());
            param.addProperty("benCountryid", "IND");
            param.addProperty("benfName", firstNameEt.getText().toString().trim());
            param.addProperty("benlName", lastNameEt.getText().toString().trim());
            param.addProperty("benMobile", mobileEdtNo.getText().toString().trim());
            param.addProperty("benPostcode", pinEt.getText().toString().trim());
            param.addProperty("DateOfTour", date);
            param.addProperty("Email", emailEt.getText().toString());
            param.addProperty("NUMBER", Cons.decryptMsg(PreferencesManager.getInstance(context).getMOBILE(), cross_intent));
            param.addProperty("pricing_id", pricingId);
            param.addProperty("pricing_name", pricingName);
            param.addProperty("pricing_qty", String.valueOf(qtyBox.getValue()));
            param.addProperty("State", stateEt.getText().toString());
            param.addProperty("time", "10:00 AM");
            param.addProperty("tour_options_Id", tourItemId);
            param.addProperty("tour_options_title", tv_tour_package.getText().toString());
            param.addProperty("Type", "5");
            LoggerUtil.logItem(param);


            Call<JsonObject> call = apiServicesCyper.getThemeParkBooking(bodyParam(param), PreferencesManager.getInstance(context).getANDROIDID());
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                    hideLoading();
                    LoggerUtil.logItem(response.body());
                    try {
                        if (response.isSuccessful()) {
                            String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent);
                            LoggerUtil.logItem(paramResponse);
                            List<ResponseBookTemePark> list = Utils.getList(paramResponse, ResponseBookTemePark.class);

                            if (response.body() != null && list.get(0).getResult().equalsIgnoreCase("0") &&
                                    list.get(0).getError().equalsIgnoreCase("0") &&
                                    list.get(0).getTrnxstatus().equalsIgnoreCase("7")) {
                                showMessage("Booking Confirm");
                                onBackPressed();
                            } else if (response.body() != null && list.get(0).getResult().equalsIgnoreCase("0") &&
                                    list.get(0).getError().equalsIgnoreCase("0") &&
                                    list.get(0).getTrnxstatus().equalsIgnoreCase("3")) {
                                showMessage("Booking Pending");
                                onBackPressed();
                            } else {
                                showMessage("Booking Failed");
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
        }
    }


    private boolean validation() {
        if (Objects.requireNonNull(firstNameEt.getText()).toString().trim().length() == 0) {
            firstNameEt.setError("Enter beneficiary first name");
            return false;
        } else if (Objects.requireNonNull(lastNameEt.getText()).toString().trim().length() == 0) {
            lastNameEt.setError("Enter beneficiary last name");
            return false;
        } else if (Objects.requireNonNull(mobileEdtNo.getText()).toString().trim().length() != 10) {
            mobileEdtNo.setError("Enter beneficiary mobile number");
            return false;
        } else if (!Utils.isEmailAddress(emailEt.getText().toString().trim())) {
            emailEt.setError("Enter beneficiary email id");
            return false;
        } else if (Objects.requireNonNull(addressEt1.getText()).toString().trim().length() < 5) {
            addressEt1.setError("Enter beneficiary mobile number");
            return false;
        } else if (Objects.requireNonNull(cityEt.getText()).toString().trim().length() == 6) {
            pinEt.setError("Enter valid pin code");
            return false;
        } else if (tourItemId.equalsIgnoreCase("")) {
            tv_tour_package.setError("Choose tour");
            return false;
        } else if (pricingId.equalsIgnoreCase("")) {
            tvCategory.setError("Select Category");
            return false;
        } else if (qtyBox.getValue() < Integer.valueOf(cateItem.get(catePosition).getScale().get(0).getScaleMinParticipant())) {
            showMessage("Please select minimum number of category");
            return false;
        }

        totalAmount = qtyBox.getValue() * Float.parseFloat(cateItem.get(catePosition).getScale().get(0).getPrice());
        LoggerUtil.logItem("Total");
        LoggerUtil.logItem(totalAmount);
        LoggerUtil.logItem(qtyBox.getValue());
        return true;
    }

    private void getStateCityName(String pincode) {
        try {
            showLoading();
            String url = BuildConfig.PINCODEURL + Cons.encryptMsg(pincode, cross_intent);
            LoggerUtil.logItem(url);
            Call<JsonObject> getCity = apiServicesLogin.getStateCity(url, PreferencesManager.getInstance(context).getANDROIDID());
            getCity.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                    LoggerUtil.logItem(response.body());
                    hideLoading();
                    try {
                        String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent);
                        LoggerUtil.logItem(paramResponse);
                        Gson gson = new GsonBuilder().create();
                        ResponsePincode responseRequestOTP = gson.fromJson(paramResponse, ResponsePincode.class);
                        if (response.body() != null && responseRequestOTP.getResponse().equalsIgnoreCase("Success")) {
                            cityEt.setText(responseRequestOTP.getResult().get(0).getDistrictName());
                            stateEt.setText(responseRequestOTP.getResult().get(0).getStateName());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        cityEt.setText("");
                        stateEt.setText("");
                    }
                }

                @Override
                public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                    hideLoading();
                }
            });
        } catch (Error | Exception e) {
            e.printStackTrace();
        }
    }

}

