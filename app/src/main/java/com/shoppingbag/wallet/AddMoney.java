package com.shoppingbag.wallet;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;
import com.shoppingbag.BuildConfig;
import com.shoppingbag.R;
import com.shoppingbag.app.PreferencesManager;
import com.shoppingbag.constants.BaseActivity;
import com.shoppingbag.constants.Cons;
import com.shoppingbag.model.wallet.ResponseWalletBalance;
import com.shoppingbag.utils.LoggerUtil;
import com.shoppingbag.utils.NetworkUtils;
import com.shoppingbag.utils.Utils;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.shoppingbag.app.AppConfig.PAYLOAD_BUNDLE;


public class AddMoney extends BaseActivity implements PaymentResultWithDataListener {

    Bundle param;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.wallet_amount)
    TextView walletAmount;
    @BindView(R.id.et_amount)
    TextInputEditText etAmount;
    @BindView(R.id.input_layout_amount)
    TextInputLayout inputLayoutAmount;

    @BindView(R.id.radioGroup_payment)
    RadioGroup radioGroupPayment;
    @BindView(R.id.radio_online)
    RadioButton radio_online;
    @BindView(R.id.radio_offline)
    RadioButton radio_offline;
    private String amount_st = "0", from = "wallet";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_add_balance_new);
        ButterKnife.bind(this);
        param = getIntent().getBundleExtra(PAYLOAD_BUNDLE);
        title.setText("Request Wallet");

        try {
            if (NetworkUtils.getConnectivityStatus(context) != 0) {
                getWalletBalance();
            } else {
                showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void disableEditText(EditText edt) {
        edt.setFocusable(false);
        edt.setFocusableInTouchMode(false);
        edt.setClickable(false);
        edt.setCursorVisible(false);
        Utils.hideSoftKeyboard(context);
        edt.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
    }

    private void enableEditText(EditText edt) {
        edt.setFocusable(true);
        edt.setFocusableInTouchMode(true);
        edt.setClickable(true);
        edt.setCursorVisible(true);
        edt.setSelection(edt.getText().toString().length());
        requestFocus(edt);
        edt.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
    }

    @OnClick({R.id.side_menu, R.id.addmoney_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.side_menu:
                Utils.hideSoftKeyboard(context);
                finish();
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                break;
            case R.id.addmoney_btn:
                if (Validation()) {
                    Utils.hideSoftKeyboard(context);
                    int btnId = radioGroupPayment.getCheckedRadioButtonId();
                    switch (btnId) {
                        case R.id.radio_offline:
                            Bundle bundle = new Bundle();
                            bundle.putString("amount", etAmount.getText().toString());
                            goToActivityWithFinish(context, OfflineBagRequest.class, bundle);
                            break;
                        case R.id.radio_online:
//                            paypalGateWay(etAmount.getText().toString());
                            startPayment(etAmount.getText().toString());
                            break;
                    }
                }
                break;

        }
    }


//    public void paypalGateWay(String paymentGateway) {
//        try {
//            LoggerUtil.logItem(paymentGateway);
//            String url = PAYPAL_PAYMENT_URL + "WALLET&currency=INR&price="
//                    + paymentGateway + "&quantity=1&sku=sku&subtotal=" + paymentGateway +
//                    "&fk_memid=" + Cons.decryptMsg(PreferencesManager.getInstance(context).getUSERID(), cross_intent) +
//                    "&transtype=Wallet&fk_orderid=" + "";
//
//
//            Bundle bundle = new Bundle();
//            bundle.putString("link", url);
//            bundle.putString("from", from);
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

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST_CODE_PAYPAL) {
//            if (resultCode == Activity.RESULT_OK) {
//                try {
//                    String invoiceNo = data.getStringExtra("invoice_no");
//
//                    getWalletBalance();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            } else if (resultCode == Activity.RESULT_CANCELED) {
//                Log.i("paymentExample", "The user canceled.");
//                showError("Payment Cancelled", null);
//            }
//
//        }
//    }

    private boolean Validation() {
        try {
            amount_st = etAmount.getText().toString().trim();
            if (amount_st.equalsIgnoreCase("")) {
                showError("Please enter amount", etAmount);
                return false;
            } else if (Integer.parseInt(amount_st) < 500) {
                showError("Minimum transaction amount is 500", etAmount);
                return false;
            } else if (radioGroupPayment.getCheckedRadioButtonId() == -1) {
                showMessage("Please select any payment method to place order.");
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
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
            if (editText != null)
                requestFocus(editText);
        });
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    private void getWalletBalance() {
        try {
            String url = BuildConfig.BASE_URL_MLM + "GetWallet?MemberId=" + PreferencesManager.getInstance(context).getUSERID();
            Call<JsonObject> call = apiServices.getBankName(url, PreferencesManager.getInstance(context).getANDROIDID());
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                    try {
                        LoggerUtil.logItem(response.body());
                        LoggerUtil.logItem(response.code());
                        if (response.isSuccessful()) {
                            ResponseWalletBalance responseWalletBalance = new Gson().fromJson(Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent), ResponseWalletBalance.class);
                            if (response.body() != null && responseWalletBalance.getResponse().equalsIgnoreCase("Success")) {
                                walletAmount.setText(String.format("₹ %s", responseWalletBalance.getResult().getDreamyWalletAmount()));
                            } else {
                                walletAmount.setText("₹ 0");
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

        } catch (Error | Exception e) {
            e.printStackTrace();
        }
    }

    public void startPayment(String amount) {

        final Activity activity = this;

        final Checkout co = new Checkout();

        try {
            JSONObject options = new JSONObject();
            options.put("name", "Wallet");
            options.put("description", "Charges");
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("currency", "INR");
            /*
             * Amount is always passed in currency subunits
             * Eg: "500" = INR 5.00
             */
            options.put("amount", String.valueOf(Integer.parseInt(amount) * 100));

            try {
                JSONObject preFill = new JSONObject();
                preFill.put("email", Cons.decryptMsg(PreferencesManager.getInstance(context).getEMAIL().trim(), cross_intent));
                preFill.put("contact", Cons.decryptMsg(PreferencesManager.getInstance(context).getMOBILE().trim(), cross_intent));

                options.put("prefill", preFill);

            } catch (Exception e) {
                e.printStackTrace();
            }

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
//            String RazorPaymentID = razorpayPaymentID;
//            paytoWallet(Cons.decryptMsg(PreferencesManager.getInstance(context).getUSERID(), cross_intent), etAmount.getText().toString(), RazorPaymentID);
//        } catch (Exception e) {
//            Log.e("TAG", "Exception in onPaymentSuccess", e);
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
//            hitFailedTransaction(Cons.decryptMsg(PreferencesManager.getInstance(context).getUSERID(), cross_intent), etAmount.getText().toString());
//        } catch (Exception e) {
//            Log.e("TAG", "Exception in onPaymentError", e);
//        }
//    }

    private void paytoWallet(String id, String amount, String invoice_no) {
        try {
            showLoading();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("Fk_MemId", id);
            jsonObject.addProperty("Amount", amount);
            jsonObject.addProperty("InvoiceNo", invoice_no);

            Log.e("REQ", "=" + jsonObject.toString());

            Call<JsonObject> call = apiServices.getPayToWallet(bodyParam(jsonObject), PreferencesManager.getInstance(context).getANDROIDID());
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                    try {
                        hideLoading();
                        LoggerUtil.logItem(response.body());
                        JsonObject responseWalletBalance = new Gson().fromJson(Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent), JsonObject.class);
                        Log.e("RES", "= " + responseWalletBalance);

                        getWalletBalance();
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

    private void hitFailedTransaction(String id, String amount, String paymentId) {
        try {
            showLoading();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("Fk_MemId", id);
            jsonObject.addProperty("Amount", amount);
            jsonObject.addProperty("InvoiceNo", paymentId);
            jsonObject.addProperty("Type", "wallet");

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

                        getWalletBalance();
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
            String RazorPaymentID = paymentData.getPaymentId();
            paytoWallet(Cons.decryptMsg(PreferencesManager.getInstance(context).getUSERID(), cross_intent), etAmount.getText().toString(), RazorPaymentID);
        } catch (Exception e) {
            Log.e("TAG", "Exception in onPaymentSuccess", e);
        }
    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {
        try {

            Log.e("TAG", "Exception in onPaymentError= "+ paymentData.getPaymentId()+" = "+i+" = "+s+" = "+paymentData.getOrderId()+" = "+
                    paymentData.getData().toString());

            Toast.makeText(this, "Payment failed: " + i, Toast.LENGTH_SHORT).show();
            hitFailedTransaction(Cons.decryptMsg(PreferencesManager.getInstance(context).getUSERID(), cross_intent), etAmount.getText().toString(), paymentData.getPaymentId());
        } catch (Exception e) {
            Log.e("TAG", "Exception in onPaymentError", e);
        }
    }
}

