package com.shoppingbag.shopping.activities;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.shoppingbag.constants.BaseActivity;
import com.shoppingbag.model.response.bill.ResponseCollectUpi;
import com.shoppingbag.utils.LoggerUtil;
import com.shoppingbag.utils.NetworkUtils;
import com.shoppingbag.utils.Utils;
import com.shoppingbag.R;
import com.google.gson.JsonObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

import static com.shoppingbag.app.AppConfig.PAYLOAD_BUNDLE;

public class TransactionStatus extends BaseActivity {

    @BindView(R.id.imageView11)
    ImageView imageView11;
    @BindView(R.id.imageTimer)
    ImageView imageTimer;
    @BindView(R.id.transAmount)
    TextView transAmount;
    @BindView(R.id.imgStatus)
    ImageView imgStatus;
    @BindView(R.id.txtNarration)
    TextView txtNarration;
    @BindView(R.id.txtDateTime)
    TextView txtDateTime;
    @BindView(R.id.imgPayment)
    ImageView imgPayment;
    @BindView(R.id.transID)
    TextView transID;
    @BindView(R.id.imgCopy)
    ImageView imgCopy;
    @BindView(R.id.done)
    Button done;
    @BindView(R.id.txtWalletBalance)
    TextView txtWalletBalance;
    @BindView(R.id.side_menu)
    ImageView sideMenu;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.bg_view)
    ConstraintLayout bgView;
    Call<JsonObject> createOrderCall;
    private String total_amount;
    private String upi;
    private boolean backBool = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transaction_status);
        ButterKnife.bind(this);
        title.setText("Transaction Status");
        sideMenu.setOnClickListener(v -> onBackPressed());
        sideMenu.setVisibility(View.GONE);
        done.setVisibility(View.GONE);


        Bundle bundle = getIntent().getBundleExtra(PAYLOAD_BUNDLE);
        if (bundle != null) {
            total_amount = Utils.format.format(Double.parseDouble(bundle.getString("amount")));
            upi = bundle.getString("upi");
            String from = bundle.getString("from");
            LoggerUtil.logItem(total_amount);
        }

        imgStatus.setVisibility(View.VISIBLE);
        txtNarration.setText("Transaction Pending");
        transAmount.setText(String.format("₹%s", String.valueOf(total_amount)));
        transID.setText("NA");

        showMessage("Do not press back button while payment is in process.");

        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            backBool = false;
            animate();
//            makeUpiPayment();
        } else {
            showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
        }

        txtDateTime.setText(Utils.getTodayDate("dd/MM/yyyy - HH:mm"));
    }

    private void animate() {
        Drawable drawable = imageTimer.getDrawable();
        if (drawable instanceof Animatable) {
            ((Animatable) drawable).start();
        }
    }

    private void stopAnimate() {
        Drawable drawable = imageTimer.getDrawable();
        if (drawable instanceof Animatable) {
            if (((Animatable) drawable).isRunning()) {
                ((Animatable) drawable).stop();
            }
        }
    }

    @Override
    protected void onDestroy() {
        if (createOrderCall != null) {
            createOrderCall.cancel();
        }
        super.onDestroy();

    }

//    private void makeUpiPayment() {
//
//
//        try {
//
//            JsonObject object = new JsonObject();
//            object.addProperty("Fk_MemId", Cons.decryptMsg(PreferencesManager.getInstance(context).getUSERID(), cross_intent));
//            object.addProperty("AMOUNT", String.valueOf(total_amount));
//            object.addProperty("AMOUNT_ALL", String.valueOf(total_amount));
//            object.addProperty("BillNumber", "WA" + System.currentTimeMillis());
//            object.addProperty("COMMENT", "Add wallet balance");
//            object.addProperty("NUMBER", upi);
//            object.addProperty("Type", "1");
//
//            LoggerUtil.logItem(object);
//
//
//            createOrderCall = apiServices_utility.getCollectPay(bodyParam(object), PreferencesManager.getInstance(context).getANDROIDID());
//            createOrderCall.enqueue(new Callback<JsonObject>() {
//                @Override
//                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
//                    LoggerUtil.logItem(response.body());
//                    LoggerUtil.logItem(response.code());
//                    try {
//                        if (response.isSuccessful()) {
//                            String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent);
//                            LoggerUtil.logItem(paramResponse);
//                            List<ResponseCollectUpi> list = Utils.getList(paramResponse, ResponseCollectUpi.class);
//
//                            if (response.body() != null && list.size() > 0 && list.get(0).getError().equalsIgnoreCase("0") &&
//                                    (list.get(0).getResult().equalsIgnoreCase("0"))
//                                    && (list.get(0).getTrnxstatus().equalsIgnoreCase("7"))) {
//                                upiPaymentStatus(TRANSACTION.TRANSACTION_SUCCESS, list.get(0));
//                            } else if (response.body() != null && list.size() > 0 && list.get(0).getError().equalsIgnoreCase("0") &&
//                                    (list.get(0).getResult().equalsIgnoreCase("0"))
//                                    && (list.get(0).getTrnxstatus().equalsIgnoreCase("3"))) {
//                                upiPaymentStatus(TRANSACTION.TRANSACTION_PENDING, list.get(0));
//                            } else if (list.size() > 0) {
//                                upiPaymentStatus(TRANSACTION.TRANSACTION_FAILED, list.get(0));
//                            } else {
//                                upiPaymentStatus(TRANSACTION.TRANSACTION_FAILED, null);
//                            }
//                        } else {
//                            clearPrefrenceforTokenExpiry();
//                            getAndroidId();
//                            goToActivityWithFinish(LoginActivity.class, null);
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        upiPaymentStatus(TRANSACTION.ERROR, null);
//                    }
//                }
//
//                @Override
//                public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
//                    LoggerUtil.logItem("Failure");
//                    LoggerUtil.logItem(t.getMessage());
//                    upiPaymentStatus(TRANSACTION.ERROR, null);
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


    private void upiPaymentStatus(TRANSACTION status, ResponseCollectUpi collectUpi) {
        switch (status) {
            case TRANSACTION_SUCCESS: {
                bgView.setBackgroundResource(R.drawable.trans_success);
                imgStatus.setVisibility(View.VISIBLE);
                imgStatus.setImageResource(R.drawable.trans_done);
                imgStatus.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.MULTIPLY);
                transID.setText(collectUpi.getTransid());
                txtNarration.setText("Transaction Successful");
                txtNarration.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
                transAmount.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
                stopAnimate();
                imageTimer.setVisibility(View.INVISIBLE);
                break;
            }
            case TRANSACTION_PENDING: {
                bgView.setBackgroundResource(R.drawable.trans_pending);
                imgStatus.setVisibility(View.VISIBLE);
                imgStatus.setImageResource(R.drawable.warning);
                imgStatus.setColorFilter(ContextCompat.getColor(context, R.color.orange), android.graphics.PorterDuff.Mode.MULTIPLY);
                transID.setText(collectUpi.getTransid());
                txtNarration.setText("Transaction Pending");
                txtNarration.setTextColor(ContextCompat.getColor(context, R.color.orange));
                transAmount.setTextColor(ContextCompat.getColor(context, R.color.orange));
                stopAnimate();
                imageTimer.setVisibility(View.INVISIBLE);
                break;
            }
            case TRANSACTION_FAILED: {
                bgView.setBackgroundResource(R.drawable.trans_failed);
                imgStatus.setVisibility(View.VISIBLE);
                imgStatus.setImageResource(R.drawable.rchg_failed);
                imgStatus.setColorFilter(ContextCompat.getColor(context, R.color.red), android.graphics.PorterDuff.Mode.MULTIPLY);
                transID.setText(collectUpi.getTransid());
                txtNarration.setText("Transaction Failed");
                txtNarration.setTextColor(ContextCompat.getColor(context, R.color.red));
                transAmount.setTextColor(ContextCompat.getColor(context, R.color.red));
                stopAnimate();
                imageTimer.setVisibility(View.INVISIBLE);

                break;
            }
            case ERROR: {
                bgView.setBackgroundResource(R.drawable.trans_failed);
                imgStatus.setVisibility(View.VISIBLE);
                imgStatus.setImageResource(R.drawable.rchg_failed);
                imgStatus.setColorFilter(ContextCompat.getColor(context, R.color.red), android.graphics.PorterDuff.Mode.MULTIPLY);
                if (collectUpi != null && collectUpi.getTransid() != null) {
                    transID.setText(collectUpi.getTransid());
                } else {
                    transID.setText("NA");
                }
                txtNarration.setText("Transaction Failed");
                txtNarration.setTextColor(ContextCompat.getColor(context, R.color.red));
                transAmount.setTextColor(ContextCompat.getColor(context, R.color.red));
                stopAnimate();
                imageTimer.setVisibility(View.INVISIBLE);

                break;
            }
        }
        sideMenu.setVisibility(View.VISIBLE);
        done.setVisibility(View.VISIBLE);
        backBool = true;
//        getBalanceAmount();
    }

    @OnClick({R.id.imgCopy, R.id.done})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgCopy:
                ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Referral Code", transID.getText().toString());
                clipboard.setPrimaryClip(clip);
                showMessage("Copied");
                break;
            case R.id.done:
                onBackPressed();
                break;
        }
    }


    @Override
    public void onBackPressed() {
        if (backBool) {
            super.onBackPressed();
        } else {
            showMessage("Do not press back button while payment is in process.");
        }

    }

//    private void getBalanceAmount() {
//        try {
//            showLoading();
//            JsonObject jsonObject = new JsonObject();
//            jsonObject.addProperty("MemberId", Cons.decryptMsg(PreferencesManager.getInstance(context).getUSERID(), cross_intent));
//            LoggerUtil.logItem(jsonObject);
//
//            Call<JsonObject> walletBalanceCall = apiServices_utility.getbalanceAmount(bodyParam(jsonObject), PreferencesManager.getInstance(context).getANDROIDID());
//            walletBalanceCall.enqueue(new Callback<JsonObject>() {
//                @Override
//                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
//                    hideLoading();
//                    LoggerUtil.logItem(response.body());
//                    try {
//                        if (response.isSuccessful()) {
//                            String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent);
//                            ResponseBalanceAmount convertedObject = new Gson().fromJson(paramResponse, ResponseBalanceAmount.class);
//                            if (response.body() != null && convertedObject.getStatus().equalsIgnoreCase("Success")) {
//                                txtWalletBalance.setText(String.format("₹%s", Float.parseFloat(String.valueOf(convertedObject.getBalanceAmount()))));
//                            }
//                        } else {
//                            clearPrefrenceforTokenExpiry();
//                            getAndroidId();
//                            goToActivityWithFinish(context, LoginActivity.class, null);
//                        }
//                    } catch (Error | Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                @Override
//                public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
//                    hideLoading();
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
