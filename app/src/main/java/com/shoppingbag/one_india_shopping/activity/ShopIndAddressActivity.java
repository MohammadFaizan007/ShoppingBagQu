package com.shoppingbag.one_india_shopping.activity;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.shoppingbag.BuildConfig;
import com.shoppingbag.R;
import com.shoppingbag.app.AppConfig;
import com.shoppingbag.app.PreferencesManager;
import com.shoppingbag.constants.BaseActivity;
import com.shoppingbag.constants.Cons;
import com.shoppingbag.model.response.pincode.ResponsePincode;
import com.shoppingbag.one_india_shopping.model.addaddress.ResponseAddAddress;
import com.shoppingbag.one_india_shopping.model.addaddress.ResponseUpdateAddress;
import com.shoppingbag.utils.LoggerUtil;
import com.shoppingbag.utils.NetworkUtils;

import org.jetbrains.annotations.NotNull;

import java.security.GeneralSecurityException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShopIndAddressActivity extends BaseActivity {
    @BindView(R.id.side_menu)
    ImageView sideMenu;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.fullname_et)
    EditText fullname_et;
    @BindView(R.id.input_fullnmae)
    TextInputLayout input_fullnmae;
    @BindView(R.id.address_et)
    EditText address_et;
    @BindView(R.id.input_address)
    TextInputLayout input_address;
    @BindView(R.id.mobile_et)
    EditText mobile_et;
    @BindView(R.id.input_mobile)
    TextInputLayout input_mobile;
    @BindView(R.id.landmark_et)
    EditText landmark_et;
    @BindView(R.id.input_landmark)
    TextInputLayout input_landmark;
    @BindView(R.id.is_permanent_check)
    CheckBox is_permanent_check;
    @BindView(R.id.state_et)
    AutoCompleteTextView state_et;
    @BindView(R.id.input_state)
    TextInputLayout input_state;
    @BindView(R.id.city_et)
    AutoCompleteTextView city_et;
    @BindView(R.id.input_city)
    TextInputLayout input_city;
    @BindView(R.id.pincode_et)
    TextInputEditText pincode_et;
    @BindView(R.id.input_pincode)
    TextInputLayout input_pincode;
    @BindView(R.id.et_addressType)
    EditText et_addressType;
    @BindView(R.id.input_addresstype)
    TextInputLayout input_addresstype;
    @BindView(R.id.btn_add_address)
    TextView btn_add_address;
    @BindView(R.id.cv_add_address)
    CardView cv_add_address;
    private String addresstype_st;

    private String addressId = "";
    private Bundle bundle;
    private String tagtype = "";
    private PopupMenu addresstypepopup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ind_add_address);
        ButterKnife.bind(this);
        getPaymentMethod();

        try {
            mobile_et.setText(Cons.decryptMsg(PreferencesManager.getInstance(context).getMOBILE(), cross_intent));

            addresstypepopup = new PopupMenu(this, et_addressType);
            addresstypepopup.getMenuInflater().inflate(R.menu.addresstype, addresstypepopup.getMenu());


            title.setText("Address");

            bundle = getIntent().getBundleExtra(AppConfig.PAYLOAD_BUNDLE);

            Log.e("bundleString", String.valueOf(bundle.getString("name")));
            tagtype = bundle.getString("tag");
            if (tagtype.equalsIgnoreCase("Edit")) {
                title.setText("Update Address");

                getViewAddressinFeilds();
            } else if (tagtype.equalsIgnoreCase("newAddress")) {
                title.setText("Add Your New Address");
            }

        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }

        pincode_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() != 0 && s.length() == 6) {
                    hideKeyboard();
                    getStateCityName(pincode_et.getText().toString().trim());
                } else {
                    input_pincode.setHelperText("");
                    input_pincode.setHelperTextEnabled(false);
                    input_pincode.setHelperTextColor(ColorStateList.valueOf(Color.RED));
                    city_et.setText("");
                    state_et.setText("");
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
            }
        });
    }

    private void getPaymentMethod() {

    }


    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private boolean Validation() {
        try {
            String fullnme_st = fullname_et.getText().toString().trim();
            String mobile_st = mobile_et.getText().toString().trim();
            String pincode_st = pincode_et.getText().toString().trim();
            addresstype_st = et_addressType.getText().toString().trim();
            String address_st = address_et.getText().toString().trim();
            String state_st = state_et.getText().toString().trim();
            String city_st = city_et.getText().toString().trim();

            /*fullname*/
            if (fullnme_st.length() == 0) {
                input_fullnmae.setError(getString(R.string.hint_fullname));
                requestFocus(fullname_et);
                return false;
            } else {
                input_fullnmae.setErrorEnabled(false);
            }
            /*Address*/
            if (address_st.length() == 0) {
                input_address.setError(getString(R.string.hint_address));
                requestFocus(address_et);
                return false;
            } else {
                input_address.setErrorEnabled(false);
            }
            /*mobile number*/
            if (mobile_st.length() == 0) {
                input_mobile.setError(getString(R.string.valid_mob_no_err));
                requestFocus(mobile_et);
                return false;
            } else {
                input_mobile.setErrorEnabled(false);
            }
            /*state*/
            if (state_st.length() == 0) {
                input_state.setError(getString(R.string.hint_state));
                requestFocus(state_et);
                return false;
            } else {
                input_state.setErrorEnabled(false);
            }
            /*City*/
            if (city_st.length() == 0) {
                input_city.setError(getString(R.string.hint_city));
                requestFocus(city_et);
                return false;
            } else {
                input_city.setErrorEnabled(false);
            }
            /*pincode*/
            if (pincode_st.length() == 0) {
                input_pincode.setError(getString(R.string.hint_pincode));
                requestFocus(pincode_et);
                return false;
            } else {
                input_pincode.setErrorEnabled(false);
            }
            /*address Type*/
            if (addresstype_st.length() == 0) {
                input_addresstype.setError("Please select Address type");
                requestFocus(et_addressType);
                return false;
            } else {
                input_addresstype.setErrorEnabled(false);
            }
        } catch (Error | Exception e) {
            hideLoading();
            e.printStackTrace();
        }
        return true;
    }

    //Todo add address method
    public void getAddAddress() {
        showLoading();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("token", "");
        jsonObject.addProperty("firstname", fullname_et.getText().toString().trim());
        try {
            jsonObject.addProperty("customer_id", Cons.decryptMsg(PreferencesManager.getInstance(context).getUSERID(), cross_intent));
            jsonObject.addProperty("email", Cons.decryptMsg(PreferencesManager.getInstance(context).getEMAIL(), cross_intent));
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        jsonObject.addProperty("add", address_et.getText().toString().trim());
        jsonObject.addProperty("city", city_et.getText().toString().trim());
        jsonObject.addProperty("state", state_et.getText().toString().trim());
        jsonObject.addProperty("pincode", pincode_et.getText().toString().trim());
        jsonObject.addProperty("mobile", mobile_et.getText().toString().trim());
        jsonObject.addProperty("add_id", "");
        jsonObject.addProperty("landmark", landmark_et.getText().toString());
        jsonObject.addProperty("address_type", et_addressType.getText().toString());
        LoggerUtil.logItem(jsonObject);
        Call<JsonObject> jsonObjectCall = apiServicesOneStoreIndia.getAddAddress(bodyParamOneStoreIndia(jsonObject));
        jsonObjectCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                hideLoading();
                LoggerUtil.logItem(response.body());
                LoggerUtil.logItem(response.code());
                try {
                    if (response.isSuccessful()) {
                        String param = Cons.decryptMsg(response.body().get("body").getAsString(), easy_pay_oneStoreIndia);
                        Gson gson = new GsonBuilder().create();
                        LoggerUtil.logItem(param);
                        ResponseAddAddress addAddress = gson.fromJson(param, ResponseAddAddress.class);
                        if (addAddress.getResponse().equalsIgnoreCase("success")) {

                            showMessage(addAddress.getData());
                            finish();
                            overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                        } else {
                            showMessage(addAddress.getData());

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

    //Todo update address method
    public void getUpdateAddress() {
        try {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("token", "");
            jsonObject.addProperty("firstname", fullname_et.getText().toString().trim());
            jsonObject.addProperty("lastname", "");


            jsonObject.addProperty("customer_id", Cons.decryptMsg(PreferencesManager.getInstance(context).getUSERID(), cross_intent));
            jsonObject.addProperty("email", Cons.decryptMsg(PreferencesManager.getInstance(context).getMOBILE(), cross_intent));

            jsonObject.addProperty("add", address_et.getText().toString().trim());
            jsonObject.addProperty("city", city_et.getText().toString().trim());
            jsonObject.addProperty("pincode", pincode_et.getText().toString().trim());
            jsonObject.addProperty("mobile", mobile_et.getText().toString().trim());
            jsonObject.addProperty("state", state_et.getText().toString().trim());
            jsonObject.addProperty("add_id", addressId);
            jsonObject.addProperty("landmark", landmark_et.getText().toString());
            jsonObject.addProperty("address_type", et_addressType.getText().toString());
            LoggerUtil.logItem(jsonObject);
            Call<JsonObject> jsonObjectCall = apiServicesOneStoreIndia.getUpdateAddress(bodyParamOneStoreIndia(jsonObject));
            jsonObjectCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                    hideLoading();
                    LoggerUtil.logItem(response.body());
                    LoggerUtil.logItem(response.code());
                    try {
                        if (response.isSuccessful()) {
                            String param = Cons.decryptMsg(response.body().get("body").getAsString(), easy_pay_oneStoreIndia);
                            Gson gson = new GsonBuilder().create();
                            LoggerUtil.logItem(param);

                            ResponseUpdateAddress updateAddress = gson.fromJson(param, ResponseUpdateAddress.class);
                            if (updateAddress.getResponse().equalsIgnoreCase("success")) {
                                finish();
                                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                                showMessage(updateAddress.getMessage());
                            } else {
                                showMessage(updateAddress.getMessage());
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

        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
    }


    private void getViewAddressinFeilds() {
        fullname_et.setText(String.format("%s %s", bundle.getString("firstname"), bundle.getString("lastname")));
        address_et.setText(bundle.getString("street"));
        mobile_et.setText(bundle.getString("mobile"));
        landmark_et.setText(bundle.getString("landmark"));
        state_et.setText(bundle.getString("state"));
        city_et.setText(bundle.getString("city"));
        pincode_et.setText(bundle.getString("pincode"));
        et_addressType.setText(bundle.getString("addresstype"));
        addressId = bundle.getString("addressId");

        String isPermanent = bundle.getString("check");
        if (isPermanent.equalsIgnoreCase("1")) {
            is_permanent_check.setChecked(true);
        } else {
            is_permanent_check.setChecked(false);
        }


    }

    @OnClick({R.id.side_menu, R.id.et_addressType, R.id.btn_add_address})
    public void onViewClicked(View view) {
        try {
            switch (view.getId()) {
                case R.id.side_menu:
                    finish();
                    overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                    break;
                case R.id.et_addressType:
                    addresstypepopup.setOnMenuItemClickListener(item -> {
                        String title = (String) item.getTitle();
                        addresstype_st = String.valueOf(item.getItemId());
                        et_addressType.setText(title);
                        return true;
                    });
                    addresstypepopup.show();
                    break;
                case R.id.btn_add_address:
                    if (Validation()) {
                        if (NetworkUtils.getConnectivityStatus(context) != 0) {
                            if (tagtype.equalsIgnoreCase("Edit")) {
                                getUpdateAddress();

                               // getViewAddressinFeilds();
                            } else if (tagtype.equalsIgnoreCase("newAddress")) {
                                getAddAddress();
                            }
                        } else {
                            showAlert(context.getResources().getString(R.string.alert_internet), R.color.colorPrimary, R.drawable.alerter_alert_button_background);
                        }
                    }
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                            city_et.setText(responseRequestOTP.getResult().get(0).getDistrictName());
                            state_et.setText(responseRequestOTP.getResult().get(0).getStateName());
                        } else {
                            input_pincode.setHelperText("Invalid PinCode");
                            input_pincode.setHelperTextEnabled(true);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        city_et.setText("");
                        state_et.setText("");
                        input_pincode.setHelperText("Invalid PinCode");
                        input_pincode.setHelperTextEnabled(true);
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
