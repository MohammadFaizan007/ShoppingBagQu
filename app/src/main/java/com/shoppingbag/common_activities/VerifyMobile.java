package com.shoppingbag.common_activities;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.shoppingbag.constants.BaseActivity;
import com.shoppingbag.constants.Cons;
import com.shoppingbag.utils.LoggerUtil;
import com.shoppingbag.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyMobile extends BaseActivity {

    @BindView(R.id.edt_mobilenumber)
    TextInputEditText edtMobilenumber;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verify_mobile);
        ButterKnife.bind(this);
    }

    private boolean validate() {
        if (edtMobilenumber.getText().toString().isEmpty()) {
            edtMobilenumber.setError("Please Enter Your Phone Number");
            edtMobilenumber.requestFocus();
            return false;
        } else if (edtMobilenumber.getText().toString().length() < 10) {
            edtMobilenumber.setError("Please Enter Your Valid Phone Number");
            edtMobilenumber.requestFocus();
            return false;
        }
        return true;
    }

    @OnClick({R.id.txt_terms, R.id.txt_privacy, R.id.btn_continue, R.id.txt_loginhere})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txt_terms:
                break;
            case R.id.txt_privacy:
                break;
            case R.id.btn_continue:
                if (validate()) {
                    getVerify();
                }
                break;
            case R.id.txt_loginhere:
                goToActivityWithFinish(LoginActivity.class, null);
                break;
        }
    }

    public void getVerify() {
        showLoading();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("RefMobile", edtMobilenumber.getText().toString());
        LoggerUtil.logItem(jsonObject);
        Call<JsonObject> call = apiServicesLogin.getCustomerReferalmobile(bodyParam(jsonObject));
        LoggerUtil.logItem(call.request().url().toString());
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                hideLoading();
                LoggerUtil.logItem(response.body());
                if (response.isSuccessful()) {
                    try {
                        String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent);
                        Gson gson = new GsonBuilder().create();
                        LoggerUtil.logItem(paramResponse);
                        JsonObject otpResponseModel = gson.fromJson(paramResponse, JsonObject.class);
//                        {\"name\":\"Admin \",\"response\":\"success\"}"
                        if (response.body() != null && otpResponseModel.get("response").getAsString().equalsIgnoreCase("Success")) {
                            Bundle bundle = new Bundle();
                            bundle.putString("mobile", edtMobilenumber.getText().toString().trim());
                            bundle.putString("username", otpResponseModel.get("name").getAsString());
                            goToActivityWithFinish(LoginActivity.class, bundle);
                        } else {
                            Bundle bundle = new Bundle();
                            bundle.putString("from", "login");
                            bundle.putString("mobile", edtMobilenumber.getText().toString().trim());
                            goToActivityWithFinish(RegistrationActivity.class, bundle);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    showMessage("Something went wrong.");
                }
            }

            @Override
            public void onFailure(@NotNull Call<JsonObject> call, @NotNull Throwable t) {
                hideLoading();
            }
        });
    }
}
