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

import com.shoppingbag.app.PreferencesManager;
import com.shoppingbag.constants.BaseActivity;
import com.shoppingbag.constants.Cons;
import com.shoppingbag.model.response.ResponsePaymentStatus;
import com.shoppingbag.utils.LoggerUtil;
import com.shoppingbag.utils.NetworkUtils;
import com.shoppingbag.utils.Utils;
import com.shoppingbag.R;
import com.shoppingbag.common_activities.LoginActivity;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.shoppingbag.app.AppConfig.PAYLOAD_BUNDLE;

public class TransactionStatusPaypal extends BaseActivity {

    String invoiceNo, from;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transaction_status);
        ButterKnife.bind(this);

        title.setText("Transaction Status");
        sideMenu.setOnClickListener(v -> onBackPressed());

        Bundle bundle = getIntent().getBundleExtra(PAYLOAD_BUNDLE);
        if (bundle != null) {
            invoiceNo = bundle.getString("invoice_no");
            from = bundle.getString("from");
        }

        imgStatus.setVisibility(View.VISIBLE);
        txtNarration.setText("Transaction Pending");
        transAmount.setText(String.format("₹%s", ""));
        transID.setText("NA");

        imgPayment.setImageResource(R.drawable.paypal);

        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            animate();
            getStatus();
        } else {
            showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
        }

        done.setOnClickListener(v -> onBackPressed());

        imgCopy.setOnClickListener(v -> {
            ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("TransId", transID.getText().toString());
            clipboard.setPrimaryClip(clip);
            showMessage("Copied");
        });

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
        super.onBackPressed();
    }

    private void getStatus() {
        try {
            showLoading();
            JsonObject param = new JsonObject();
            param.addProperty("InvoiceNo", invoiceNo);

            LoggerUtil.logItem(param);
            Call<JsonObject> statusCall = apiServices.getPaymentStatus(bodyParam(param), PreferencesManager.getInstance(context).getANDROIDID());
            statusCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                    hideLoading();
                    stopAnimate();
                    LoggerUtil.logItem(response.toString());
                    try {
                        if (response.isSuccessful()) {
                            ResponsePaymentStatus response_new = new Gson().fromJson(Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent), ResponsePaymentStatus.class);
                            if (response_new.getResponse().equalsIgnoreCase("Success")) {
                                transAmount.setText(String.format("₹%s", String.valueOf(response_new.getListPaymentTransactionDetails().get(0).getTotal())));
                                if (response_new.getListPaymentTransactionDetails().get(0).getPaymentStatus().equalsIgnoreCase("Success")) {
                                    upiPaymentStatus(TRANSACTION.TRANSACTION_SUCCESS, response_new.getListPaymentTransactionDetails().get(0).getTransactionNo());

                                } else if (response_new.getListPaymentTransactionDetails().get(0).getPaymentStatus().equalsIgnoreCase("Pending")) {
                                    upiPaymentStatus(TRANSACTION.TRANSACTION_PENDING, response_new.getListPaymentTransactionDetails().get(0).getTransactionNo());
                                } else if (response_new.getListPaymentTransactionDetails().get(0).getPaymentStatus().equalsIgnoreCase("Failed")) {
                                    upiPaymentStatus(TRANSACTION.TRANSACTION_FAILED, response_new.getListPaymentTransactionDetails().get(0).getTransactionNo());
                                }
                                transID.setText(response_new.getListPaymentTransactionDetails().get(0).getTransactionNo());
                            } else {
                                showAlert("Error! Something went wrong, Don't worry your money is safe.", R.color.red, R.drawable.error);
                                upiPaymentStatus(TRANSACTION.TRANSACTION_FAILED, response_new.getListPaymentTransactionDetails().get(0).getTransactionNo());
                            }
                        } else {
                            clearPrefrenceforTokenExpiry();
                            getAndroidId();
                            goToActivityWithFinish(context, LoginActivity.class, null);
                        }
                    } catch (Error | Exception e) {
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

    private void upiPaymentStatus(TRANSACTION status, String transId) {

        switch (status) {
            case TRANSACTION_SUCCESS:
                bgView.setBackgroundResource(R.drawable.trans_success);
                imgStatus.setVisibility(View.VISIBLE);
                imgStatus.setImageResource(R.drawable.trans_done);

                transID.setText(transId);
                txtNarration.setText("Transaction Successful");

                txtNarration.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
                transAmount.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
                stopAnimate();
                imageTimer.setVisibility(View.INVISIBLE);
                break;
            case TRANSACTION_PENDING:
                bgView.setBackgroundResource(R.drawable.trans_pending);
                imgStatus.setVisibility(View.VISIBLE);
                imgStatus.setImageResource(R.drawable.warning);
                imgStatus.setColorFilter(ContextCompat.getColor(context, R.color.orange), android.graphics.PorterDuff.Mode.MULTIPLY);

                transID.setText(transId);
                txtNarration.setText("Transaction Pending");
                txtNarration.setTextColor(ContextCompat.getColor(context, R.color.orange));
                transAmount.setTextColor(ContextCompat.getColor(context, R.color.orange));
                stopAnimate();
                imageTimer.setVisibility(View.INVISIBLE);
                break;
            case TRANSACTION_FAILED:
                bgView.setBackgroundResource(R.drawable.trans_failed);
                imgStatus.setVisibility(View.VISIBLE);
                imgStatus.setImageResource(R.drawable.rchg_failed);
                imgStatus.setColorFilter(ContextCompat.getColor(context, R.color.red), android.graphics.PorterDuff.Mode.MULTIPLY);

                transID.setText(transId);
                txtNarration.setText("Transaction Failed");
                txtNarration.setTextColor(ContextCompat.getColor(context, R.color.red));
                transAmount.setTextColor(ContextCompat.getColor(context, R.color.red));
                stopAnimate();
                imageTimer.setVisibility(View.INVISIBLE);
                break;
            case ERROR:
                bgView.setBackgroundResource(R.drawable.trans_failed);
                imgStatus.setVisibility(View.VISIBLE);
                imgStatus.setImageResource(R.drawable.rchg_failed);
                imgStatus.setColorFilter(ContextCompat.getColor(context, R.color.red), android.graphics.PorterDuff.Mode.MULTIPLY);
                transID.setText(transId);
                txtNarration.setText("Transaction Failed");
                txtNarration.setTextColor(ContextCompat.getColor(context, R.color.red));
                transAmount.setTextColor(ContextCompat.getColor(context, R.color.red));
                stopAnimate();
                imageTimer.setVisibility(View.INVISIBLE);
                break;
        }
    }

}
