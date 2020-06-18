package com.shoppingbag.one_india_shopping.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;
import com.shoppingbag.BuildConfig;
import com.shoppingbag.R;
import com.shoppingbag.app.PreferencesManager;
import com.shoppingbag.constants.BaseActivity;
import com.shoppingbag.constants.Cons;
import com.shoppingbag.model.wallet.ResponseWalletBalance;
import com.shoppingbag.one_india_shopping.model.address.DataItem;
import com.shoppingbag.one_india_shopping.model.address.ResponseGetAddress;
import com.shoppingbag.one_india_shopping.model.cartitem.CartitemsItem;
import com.shoppingbag.one_india_shopping.model.payment_method.ResponsePayMethod;
import com.shoppingbag.one_india_shopping.model.registrationModel.RegistrationModel;
import com.shoppingbag.utils.LoggerUtil;
import com.shoppingbag.utils.NetworkUtils;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.security.GeneralSecurityException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.shoppingbag.app.AppConfig.PAYLOAD_BUNDLE;

public class ShopIndMakePayment extends BaseActivity implements PaymentResultWithDataListener {

    @BindView(R.id.side_menu)
    ImageView sideMenu;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.img_cart)
    ImageView imgCart;
    @BindView(R.id.txt_count)
    TextView txtCount;
    @BindView(R.id.add_address_txt)
    TextView add_address_txt;
    @BindView(R.id.change_billing)
    TextView changeBilling;
    @BindView(R.id.fullname_bill)
    TextView fullnameBill;
    @BindView(R.id.textViewCash)
    TextView textViewCash;
    @BindView(R.id.textViewDreamy)
    TextView textViewDreamy;
    @BindView(R.id.address_bill)
    TextView addressBill;
    @BindView(R.id.landmark_bill)
    TextView landmarkBill;
    @BindView(R.id.pinCode_bill)
    TextView pinCodeBill;
    @BindView(R.id.addressType_bill)
    TextView addressTypeBill;
    @BindView(R.id.mobileno)
    TextView mobileno;
    @BindView(R.id.cv_address_default_bill)
    CardView cvAddressDefaultBill;
    @BindView(R.id.tv_payment)
    TextView tvPayment;
    @BindView(R.id.textView115)
    TextView textView115;
    @BindView(R.id.textViewNote)
    TextView textViewNote;
    @BindView(R.id.tv_total_amount)
    TextView tvTotalAmount;
    @BindView(R.id.checkbox_dreamy)
    CheckBox checkbox_dreamy;
    DataItem dataItem = new DataItem();
    @BindView(R.id.rgPayment)
    RadioGroup rgPayment;
    private String payMethod = "cashondelivery";
    private String TotalAmount = "";
    private int REQUEST_CODE = 900;
    private int REQUEST_CODE_PAYPAL = 111;
    private double CashwalletBalance = 0.0;
    private double DreamywalletBalance = 0.0;
    private double RazorCashwalletAmt = 0.0;
    private double RazorDreamywalletAmt = 0.0;
    private double RazorPaymentAmt = 0.0;
    private String RazorPaymentID = "";
    private String paymentMethod = "";
    private static DecimalFormat format = new DecimalFormat("0.00");
    private Bundle param;
    private static final String TAG = ShopIndMakePayment.class.getSimpleName();
    private boolean isDreamy = true;
    private List<CartitemsItem> list = new ArrayList();
    double finalAmt = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_ind_make_payment);
        ButterKnife.bind(this);
        title.setText("Checkout");
//        Checkout.preload(getApplicationContext());
        registerUser();
        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            getAddressList("");
        } else {
            showMessage(context.getString(R.string.alert_internet));
        }
        getWalletBalance("0", true);
        textViewNote.setText("10% of the Total Amount is Deducted from Cashback Wallet");
        param = getIntent().getBundleExtra(PAYLOAD_BUNDLE);
        tvTotalAmount.setText(String.format("₹%s", param.getDouble("amount")));
        list = new Gson().fromJson(param.getString("list"), new TypeToken<List<CartitemsItem>>() {
        }.getType());


        checkbox_dreamy.setOnCheckedChangeListener((buttonView, isChecked) -> {
            isDreamy = isChecked;
        });

        rgPayment.setOnCheckedChangeListener((group, checkedId) -> {

            switch (checkedId) {
                case R.id.rbOnline:
                    paymentMethod = "ONLINE";
                    checkbox_dreamy.setEnabled(true);
                    break;
                case R.id.rbWallet:
                    paymentMethod = "WALLET";
                    checkbox_dreamy.setEnabled(true);
                    break;
                case R.id.rbCod:
                    paymentMethod = "COD";
                    checkbox_dreamy.setEnabled(false);
                    break;
            }

        });


    }

    private void getTotalCashback() {
        finalAmt = 0.0;
        // double amount = Double.parseDouble(totalAmount) * cashBackWalletPercentage;
        for (int i = 0; i < list.size(); i++) {
            double amount;
            if (list.get(i).getCashback() > 0) {
                amount = (list.get(i).getPrice() * list.get(i).getCashback()) / 100;
            } else {
                amount = list.get(i).getPrice() * cashBackWalletPercentage;
            }
            finalAmt = finalAmt + amount;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void getAddressList(String addressId) {
        showLoading();
        try {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("customer_id", Cons.decryptMsg(PreferencesManager.getInstance(context).getUSERID(), cross_intent));
            LoggerUtil.logItem(jsonObject);

            Call<JsonObject> jsonObjectCall = apiServicesOneStoreIndia.getAddressList(bodyParamOneStoreIndia(jsonObject));
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
                            ResponseGetAddress getAddress = gson.fromJson(param, ResponseGetAddress.class);
                            if (getAddress.getResponse().equalsIgnoreCase("success") && getAddress.getData().size() > 0) {
                                add_address_txt.setVisibility(View.GONE);
                                cvAddressDefaultBill.setVisibility(View.VISIBLE);

                                if (addressId.equalsIgnoreCase("")) {
                                    for (int i = 0; i < getAddress.getData().size(); i++) {
                                        dataItem = getAddress.getData().get(i);
                                        if (getAddress.getData().get(i).getIsActive().equalsIgnoreCase("1")) {
                                            fullnameBill.setText(String.format("%s %s", getAddress.getData().get(i).getFirstname(), getAddress.getData().get(i).getLastname()));
                                            addressBill.setText(getAddress.getData().get(i).getStreet());
                                            landmarkBill.setText(String.format("%s,%s%s", getAddress.getData().get(i).getLandmark(), getAddress.getData().get(i).getCity(), getAddress.getData().get(i).getRegion()
                                            ));
                                            pinCodeBill.setText(String.format("Pincode : %s", getAddress.getData().get(i).getPostcode()));
                                            addressTypeBill.setText(getAddress.getData().get(i).getAddressType());
                                            mobileno.setText(getAddress.getData().get(i).getTelephone());
                                            break;
                                        }
                                    }
                                } else {
                                    for (int i = 0; i < getAddress.getData().size(); i++) {
                                        dataItem = getAddress.getData().get(i);
                                        if (getAddress.getData().get(i).getAddressId().equalsIgnoreCase(addressId)) {
                                            fullnameBill.setText(String.format("%s %s", getAddress.getData().get(i).getFirstname(), getAddress.getData().get(i).getLastname()));
                                            addressBill.setText(getAddress.getData().get(i).getStreet());
                                            landmarkBill.setText(String.format("%s,%s%s", getAddress.getData().get(i).getLandmark(), getAddress.getData().get(i).getCity(), getAddress.getData().get(i).getRegion()
                                            ));
                                            pinCodeBill.setText(String.format("Pincode : %s", getAddress.getData().get(i).getPostcode()));
                                            addressTypeBill.setText(getAddress.getData().get(i).getAddressType());
                                            mobileno.setText(getAddress.getData().get(i).getTelephone());
                                            break;
                                        }
                                    }
                                }
                                getPaymentMethod();
                            } else {
                                add_address_txt.setVisibility(View.VISIBLE);
                                cvAddressDefaultBill.setVisibility(View.INVISIBLE);
                            }
                        } else {
                            showMessage("something went wrong");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<JsonObject> call, @NotNull Throwable t) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getPaymentMethod() {
        showLoading();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("token", pref.getString("token", ""));
        jsonObject.addProperty("firstname", dataItem.getFirstname().trim());
        jsonObject.addProperty("lastname", dataItem.getFirstname().trim());
        jsonObject.addProperty("shipping", "flatrate");
        try {
            jsonObject.addProperty("customer_id", Cons.decryptMsg(PreferencesManager.getInstance(context).getUSERID(), cross_intent));
            jsonObject.addProperty("email", Cons.decryptMsg(PreferencesManager.getInstance(context).getEMAIL(), cross_intent));
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        jsonObject.addProperty("add", dataItem.getStreet() + " " + dataItem.getCity());
        jsonObject.addProperty("city", dataItem.getCity().trim());
        jsonObject.addProperty("state", dataItem.getRegion().trim());
        jsonObject.addProperty("pincode", dataItem.getPostcode().trim());
        jsonObject.addProperty("mobile", dataItem.getTelephone().trim());
        jsonObject.addProperty("add_id", dataItem.getAddressId().trim());
        jsonObject.addProperty("landmark", dataItem.getLandmark());
        jsonObject.addProperty("address_type", dataItem.getAddressType());
        LoggerUtil.logItem(jsonObject);
        Call<JsonObject> jsonObjectCall = apiServicesOneStoreIndia.executeCheckoutData(bodyParamOneStoreIndia(jsonObject));
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
                        ResponsePayMethod responsePayMethod = gson.fromJson(param, ResponsePayMethod.class);
                        if (responsePayMethod.getResponse().equalsIgnoreCase("Success")) {

                            payMethod = responsePayMethod.getData().getPaymentMethod().get(0).getCode();

                            Log.d("payMethod", "onResponse: " + payMethod);
                            TotalAmount = String.valueOf(responsePayMethod.getData().getGrandTotal());
                            if (responsePayMethod.getData().getShippingAmount() != 0) {
                                tvTotalAmount.setText(String.format("₹%s", responsePayMethod.getData().getGrandTotal()));
                            } else {
                                tvTotalAmount.setText(String.format("₹%s", responsePayMethod.getData().getGrandTotal()));
                            }
                        }
                    } else {
                        showMessage("something went wrong");
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

    private void hitCreateOrderV2(double cashWallet, double dreamyWallet, double paymentGateway, String transId, String paymentMethod) {
        try {
            showLoading();
            String address = dataItem.getStreet() + "," + dataItem.getLandmark() + "," + dataItem.getCity() + "," + dataItem.getRegion() + "," + dataItem.getPostcode();
            JsonObject jsonObject = new JsonObject();
            JsonArray cartitems = new JsonArray();
            for (int i = 0; i < list.size(); i++) {
                JsonObject taxReqObject = new JsonObject();
                Log.e("Name", "= " + list.get(i).getName());
                taxReqObject.addProperty("productname", list.get(i).getName().replaceAll("&", "and"));
                taxReqObject.addProperty("price", list.get(i).getPrice());
                taxReqObject.addProperty("producttype", "simple");
                taxReqObject.addProperty("quatity", list.get(i).getQty());
                taxReqObject.addProperty("image", list.get(i).getImage());
                taxReqObject.addProperty("cashbackper", list.get(i).getCashback());
                cartitems.add(taxReqObject);
            }
            jsonObject.addProperty("token", pref.getString("token", ""));
            jsonObject.addProperty("TotalAmount", TotalAmount);
            jsonObject.addProperty("dreamyAmount", (format.format(dreamyWallet)));
            jsonObject.addProperty("gatewayAmount", (format.format(paymentGateway)));
            jsonObject.addProperty("cashbackAmount", (format.format(cashWallet)));
            jsonObject.addProperty("shippingAddress", address);
            jsonObject.addProperty("billingAddress", address);

            jsonObject.addProperty("Fk_MemID", Cons.decryptMsg(PreferencesManager.getInstance(context).getUSERID(), cross_intent));
            jsonObject.addProperty("method", paymentMethod);

            if (paymentGateway != 0.0) {
                jsonObject.addProperty("transactionNo", transId);
            } else {
                jsonObject.addProperty("transactionNo", System.currentTimeMillis());
            }
            jsonObject.add("cartitems", cartitems);


            LoggerUtil.logItem(jsonObject);
            Call<JsonObject> jsonObjectCall = apiServices.executeCreateOrderV2(bodyParam(jsonObject), PreferencesManager.getInstance(context).getANDROIDID());
            jsonObjectCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                    hideLoading();
                    LoggerUtil.logItem(response.body());
                    LoggerUtil.logItem(response.code());
                    LoggerUtil.logItem(call.request().url());
                    try {
                        if (response.isSuccessful()) {
                            String param = Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent);
                            Gson gson = new GsonBuilder().create();
                            ResponsePayMethod addAdress = gson.fromJson(param, ResponsePayMethod.class);
                            if (addAdress.getResponse().equalsIgnoreCase("Success")) {
                                successDialog("");
                            }
                        } else {
                            showMessage("something went wrong");
                        }

                    } catch (Exception e) {
                        hideLoading();
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<JsonObject> call, @NotNull Throwable t) {
                    hideLoading();
                }
            });
        } catch (Exception e) {
            hideLoading();
            e.printStackTrace();
        }
    }

    private void successDialog(String orderID) {
        Dialog dialoglog = new Dialog(context);
        dialoglog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialoglog.setContentView(R.layout.ind_confirmation_dialog);
        Window window = dialoglog.getWindow();
        window.setLayout(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);
        dialoglog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        AppCompatButton btn_go = dialoglog.findViewById(R.id.btn_go);
        TextView msg = dialoglog.findViewById(R.id.msg);

        // msg.setText(String.format("Order ID : %s", orderID));
        dialoglog.setCancelable(false);
        btn_go.setOnClickListener(view -> {
            dialoglog.dismiss();
            if (!pref.getString("token", "").equals("")) {
                getCreateToken();
            }

        });

        //Displaying the alert dialog
        dialoglog.show();
    }

    private void getWalletBalance(String totalAmount, boolean from) {
        if (!from)
            showLoading();
        String url = BuildConfig.BASE_URL_MLM + "GetWallet?MemberId=" + PreferencesManager.getInstance(context).getUSERID();
        Call<JsonObject> call = apiServices.getBankName(url, PreferencesManager.getInstance(context).getANDROIDID());
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                try {
                    if (!from)
                        hideLoading();
                    LoggerUtil.logItem(response.body());
                    LoggerUtil.logItem(response.code());
                    if (response.isSuccessful()) {
                        ResponseWalletBalance responseWalletBalance = new Gson().fromJson(Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent), ResponseWalletBalance.class);
                        if (response.body() != null && responseWalletBalance.getResponse().equalsIgnoreCase("Success")) {
                            CashwalletBalance = responseWalletBalance.getResult().getCashbackWalletAmount();
                            DreamywalletBalance = responseWalletBalance.getResult().getDreamyWalletAmount();
                            if (from) {
                                textViewDreamy.setText(String.format("₹ %s", DreamywalletBalance));
                                textViewCash.setText(String.format("₹ %s", CashwalletBalance));
                            } else {
                                if (!TextUtils.isEmpty(totalAmount)) {
                                    getTotalCashback();
                                    Log.e("Final Cashback Amount", "= " + finalAmt);
                                    if (CashwalletBalance >= finalAmt) {
                                        double discAmt = Double.parseDouble(totalAmount) - finalAmt;
                                        if (isDreamy) {
                                            if (DreamywalletBalance >= discAmt) {
                                                //hitOrderPlace(amount, discAmt, 0.0, "", "cashondelivery");
                                                hitCreateOrderV2(finalAmt, discAmt, 0.0, "", "cashondelivery");
                                            } else if (rgPayment.getCheckedRadioButtonId() == R.id.rbOnline) {
                                                double payAmt = discAmt - DreamywalletBalance;
                                                startPayment(finalAmt, DreamywalletBalance, payAmt);
                                            } else {
                                                showMessage("No Sufficient balance");
                                            }
                                        } else if (rgPayment.getCheckedRadioButtonId() == R.id.rbOnline) {
                                            startPayment(finalAmt, 0.0, discAmt);
                                        } else {
                                            showMessage("No Sufficient balance");
                                        }

                                    } else {
                                        double discAmt = Double.parseDouble(totalAmount) - CashwalletBalance;
                                        if (isDreamy) {
                                            if (DreamywalletBalance >= discAmt) {
                                                hitCreateOrderV2(CashwalletBalance, discAmt, 0.0, "", "cashondelivery");
//                                                 hitOrderPlace(CashwalletBalance, discAmt, 0.0, "", "cashondelivery");
                                            } else if (rgPayment.getCheckedRadioButtonId() == R.id.rbOnline) {
                                                double payAmt = discAmt - DreamywalletBalance;
                                                startPayment(CashwalletBalance, DreamywalletBalance, payAmt);
                                            } else {
                                                showMessage("No Sufficient balance");
                                            }
                                        } else if (rgPayment.getCheckedRadioButtonId() == R.id.rbOnline) {
                                            startPayment(CashwalletBalance, 0.0, discAmt);
                                        } else {
                                            showMessage("No Sufficient balance");
                                        }

                                    }
                                }
                            }
                        }

                    }

                } catch (Exception e) {
                    if (!from)
                        hideLoading();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NotNull Call<JsonObject> call, @NotNull Throwable t) {
                if (!from)
                    hideLoading();
            }
        });
    }

    private void getCreateToken() {
        showLoading();
        Call<String> jsonObjectCall = apiServiceOneInd.getCreateToken();
        jsonObjectCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NotNull Call<String> call, @NotNull Response<String> response) {
                hideLoading();
                LoggerUtil.logItem("Token ====>" + response.body());
                LoggerUtil.logItem("Token ====>" + response.code());
                try {
                    if (response.isSuccessful()) {
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("token", response.body().replace("\"", ""));
                        editor.apply();
                        PreferencesManager.getInstance(context).setCartCount(0);
                        goToActivity(ShopIndMakePayment.this, ShoppingIndiaActivity.class, null);
                        finishAffinity();

                    } else {
                        showMessage("Something went wrong");
                    }

                } catch (Exception e) {
                    hideLoading();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NotNull Call<String> call, @NotNull Throwable t) {
                hideLoading();
            }
        });
    }

    // Register User to 1India Store
    private void registerUser() {
        try {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("email", Cons.decryptMsg(PreferencesManager.getInstance(context).getEMAIL(), cross_intent));
            jsonObject.addProperty("fistname", Cons.decryptMsg(PreferencesManager.getInstance(context).getNAME(), cross_intent));
            jsonObject.addProperty("lastname", Cons.decryptMsg(PreferencesManager.getInstance(context).getNAME(), cross_intent));
            jsonObject.addProperty("mobile", Cons.decryptMsg(PreferencesManager.getInstance(context).getMOBILE(), cross_intent));
            jsonObject.addProperty("password", Cons.decryptMsg(PreferencesManager.getInstance(context).getSavedPASSWORD(), cross_intent));
            LoggerUtil.logItem(jsonObject);
            Call<JsonObject> jsonObjectCall = apiServicesOneStoreIndia.userRegistration(bodyParamOneStoreIndia(jsonObject));
            jsonObjectCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                    hideLoading();
                    LoggerUtil.logItem(response.body());
                    LoggerUtil.logItem(response.code());
                    try {
                        if (response.isSuccessful()) {
                            String param = Cons.decryptMsg(response.body().get("body").getAsString(), easy_pay_oneStoreIndia);
                            LoggerUtil.logItem(param);
                            Gson gson = new GsonBuilder().create();
                            RegistrationModel registrationModel = gson.fromJson(param, RegistrationModel.class);
                            if (registrationModel.getResponse().equalsIgnoreCase("Success")) {
                                //Toast.makeText(context, "" + registrationModel.getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                //showMessage("Something went wrong");
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(@NotNull Call<JsonObject> call, @NotNull Throwable t) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            String addressid = "";
            if (data != null) {
                addressid = data.getStringExtra("addressid");
                getAddressList(addressid);
            }

        }
//        else if (requestCode == REQUEST_CODE_PAYPAL) {
//            if (resultCode == Activity.RESULT_OK) {
//                String invoiceNo = data.getStringExtra("invoice_no");
//                hitOrderPlace(RazorCashwalletAmt, RazorDreamywalletAmt, RazorPaymentAmt, paymentMethod, invoiceNo);
//            } else if (resultCode == Activity.RESULT_CANCELED) {
//                Log.i("paymentExample", "The user canceled.");
//            }
//
//        }
    }


    @OnClick({R.id.change_billing, R.id.add_address_txt, R.id.side_menu, R.id.tv_payment})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.change_billing:
            case R.id.add_address_txt: {
                Intent i = new Intent(context, ShopIndAllAdress.class);
                i.putExtra("from", "checkout");
                startActivityForResult(i, REQUEST_CODE);
            }
            break;
            case R.id.side_menu:
                finish();
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                break;
            case R.id.tv_payment: {
                if (add_address_txt.getVisibility() == View.GONE) {
                    if (rgPayment.getCheckedRadioButtonId() != -1) {
                        getWalletBalance(TotalAmount, false);
//                        double finalAmt = 0.0;
//
//                        // double amount = Double.parseDouble(totalAmount) * cashBackWalletPercentage;
//                        for (int i = 0; i < list.size(); i++) {
//                            double amount;
//                            if (list.get(i).getCashback() > 0) {
//                                amount = (list.get(i).getPrice() * list.get(i).getCashback()) / 100;
//                            } else {
//                                amount = list.get(i).getPrice() * cashBackWalletPercentage;
//                            }
//                            Log.e(" Amount", "= " + amount);
//                            Log.e(" Price", "= " + list.get(i).getPrice());
//                            Log.e(" cashbag", "= " + list.get(i).getCashback());
//                            finalAmt = finalAmt + amount;
//                            Log.e(" FINAL Amount", "= " + finalAmt);
//                        }
//                        Log.e("Final Cashback Amount", "= " + finalAmt);
                    } else {
                        showMessage("Choose Payment Method");
                    }
                } else {
                    showMessage("Please Add Address");
                }
            }
            break;
        }
    }

//    public void paypalGateWay(double paymentGateway) {
//        try {
//            LoggerUtil.logItem(paymentGateway);
//            String url = PAYPAL_PAYMENT_URL + "SHOPPING&currency=INR&price="
//                    + paymentGateway + "&quantity=1&sku=sku&subtotal=" + paymentGateway +
//                    "&fk_memid=" + Cons.decryptMsg(PreferencesManager.getInstance(context).getUSERID(), cross_intent) +
//                    "&transtype=Shopping&fk_orderid=" + "";
//
//            Bundle bundle = new Bundle();
//            bundle.putString("link", url);
//            bundle.putString("from", "shopping");
//            goToActivity(context, PaypalWebView.class, bundle);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    public void goToActivity(Activity activity, Class<?> classActivity, Bundle bundle) {
//        Utils.hideSoftKeyboard(activity);
//        Intent intent = new Intent(activity, classActivity);
//        if (bundle != null) intent.putExtra(PAYLOAD_BUNDLE, bundle);
//        activity.startActivityForResult(intent, REQUEST_CODE_PAYPAL);
//        overridePendingTransition(com.shoppingbag.R.anim.slide_from_right, com.shoppingbag.R.anim.slide_to_left);
//    }

//    public void startPayment(double cashWalletBalance, double discAmt, double paymentGateway) {
//        RazorCashwalletAmt = cashWalletBalance;
//        RazorDreamywalletAmt = discAmt;
//        RazorPaymentAmt = paymentGateway;
//        paypalGateWay(paymentGateway);
//    }


    public void startPayment(double cashwalletBalance, double discAmt, double paymentGateway) {
        /*
          You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        RazorCashwalletAmt = cashwalletBalance;
        RazorDreamywalletAmt = discAmt;
        RazorPaymentAmt = paymentGateway;
        final Activity activity = this;

        final Checkout co = new Checkout();

        try {
            JSONObject options = new JSONObject();
            options.put("name", "Dreamy Mall");
            options.put("description", "Charges");
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("currency", "INR");
            /*
             * Amount is always passed in currency subunits
             * Eg: "500" = INR 5.00
             */
            options.put("amount", String.valueOf(Math.round(paymentGateway) * 100));

            JSONObject preFill = new JSONObject();
            preFill.put("email", dataItem.getEmail().trim());
            preFill.put("contact", dataItem.getTelephone().trim());

            options.put("prefill", preFill);

            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }
    }

//    /**
//     * The name of the function has to be
//     * onPaymentSuccess
//     * Wrap your code in try catch, as shown, to ensure that this method runs correctly
//     */
//    @SuppressWarnings("unused")
//    @Override
//    public void onPaymentSuccess(String razorpayPaymentID) {
//        try {
//            Toast.makeText(this, "Payment Successful", Toast.LENGTH_SHORT).show();
//            RazorPaymentID = razorpayPaymentID;
//            hitCreateOrderV2(RazorCashwalletAmt, RazorDreamywalletAmt, RazorPaymentAmt, RazorPaymentID, "checkmo");
//        } catch (Exception e) {
//            Log.e(TAG, "Exception in onPaymentSuccess", e);
//        }
//    }

//    /**
//     * The name of the function has to be
//     * onPaymentError
//     * Wrap your code in try catch, as shown, to ensure that this method runs correctly
//     */
//    @SuppressWarnings("unused")
//    @Override
//    public void onPaymentError(int code, String response) {
//        try {
//            Toast.makeText(this, "Payment failed: " + code + " " + response, Toast.LENGTH_SHORT).show();
//            hitFailedTransaction(Cons.decryptMsg(PreferencesManager.getInstance(context).getUSERID(), cross_intent), String.valueOf(RazorPaymentAmt));
//        } catch (Exception e) {
//            Log.e(TAG, "Exception in onPaymentError", e);
//        }
//    }

    private void hitFailedTransaction(String id, String amount, String paymentId) {
        try {
            showLoading();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("Fk_MemId", id);
            jsonObject.addProperty("Amount", amount);
            jsonObject.addProperty("InvoiceNo", paymentId);
            jsonObject.addProperty("Type", "shopping");

            Log.e("REQ", "=" + jsonObject.toString());

            Call<JsonObject> call = apiServices.getGatewayFailedTrans(bodyParam(jsonObject), PreferencesManager.getInstance(context).getANDROIDID());
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                    try {
                        hideLoading();
                        LoggerUtil.logItem(response.body());
                        JsonObject responseWalletBalance = new Gson().fromJson(Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent), JsonObject.class);
                        Log.e("RES", "= " + responseWalletBalance);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<JsonObject> call, @NotNull Throwable t) {
                    hideLoading();
                }
            });

        } catch (Error | Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onPaymentSuccess(String s, PaymentData paymentData) {
        try {
            Toast.makeText(this, "Payment Successful", Toast.LENGTH_SHORT).show();
            RazorPaymentID = paymentData.getPaymentId();
            hitCreateOrderV2(RazorCashwalletAmt, RazorDreamywalletAmt, RazorPaymentAmt, RazorPaymentID, "checkmo");
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentSuccess", e);
        }
    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {
        try {
            Toast.makeText(this, "Payment failed: " + i, Toast.LENGTH_SHORT).show();
            hitFailedTransaction(Cons.decryptMsg(PreferencesManager.getInstance(context).getUSERID(), cross_intent), String.valueOf(RazorPaymentAmt), paymentData.getPaymentId());
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentError", e);
        }
    }
}
