package com.shoppingbag.utilities.mobilerecharge;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.shoppingbag.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.shoppingbag.constants.BaseActivity;
import com.shoppingbag.model.mobile_recharge.requestmodel.RequestGetReprintMobile;
import com.shoppingbag.model.mobile_recharge.requestmodel.RequestGetTransactionStatus;
import com.shoppingbag.model.mobile_recharge.responsemodel.ResponseGetReprintMobile;
import com.shoppingbag.model.mobile_recharge.responsemodel.ResponseGetTransactionStatus;
import com.shoppingbag.utils.LoggerUtil;
import com.shoppingbag.utils.NetworkUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransactionStatus extends BaseActivity {

    private static final String TAG = "TransactionStatus";
    String userTrackID, operatorType, strMob, strOpDesc;
    int strAmt;
    //    @BindView(R.id.constOne)
//    ConstraintLayout constOne;
//    @BindView(R.id.constTwo)
//    ConstraintLayout constTwo;
//    @BindView(R.id.statusImage)
//    ImageView statusImage;
//    @BindView(R.id.tvstatTop)
//    TextView tvstatTop;
//    @BindView(R.id.statBox)
//    TextView statBox;
//    @BindView(R.id.statBottom)
//    TextView statBottom;
    //    @BindView(R.id.tvReferenceNumber)
//    TextView tvReferenceNumber;
//    @BindView(R.id.tvTransID)
//    TextView tvTransID;
//    @BindView(R.id.tvRechargeAmount)
//    TextView tvRechargeAmount;
    @BindView(R.id.tvMobileNumber)
    TextView tvMobileNumber;
    @BindView(R.id.tvDateAndTime)
    TextView tvDateAndTime;
    //    @BindView(R.id.tvOperatorName)
//    TextView tvOperatorName;
    private Gson gson = new GsonBuilder().create();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transaction_status);
        ButterKnife.bind(this);

//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_clear_white);

//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                setResult(RESULT_CANCELED, intent);
//                finish();
//            }
//        });

        Intent intent = getIntent();
        if (intent != null) {
            userTrackID = intent.getStringExtra("TRAK_ID");
//            BASE__URL = intent.getStringExtra("BASE__URL");
            operatorType = intent.getStringExtra("OPERATOR__TYPE");
            Log.e("================", "" + operatorType);
            strAmt = intent.getIntExtra("AMT", 0);
            strMob = intent.getStringExtra("MOBNO");
            strOpDesc = intent.getStringExtra("OPDESC");
        }

        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            getTransactionStatusHere();
        } else {
            showMessage(getString(R.string.alert_internet));
        }

    }

    @SuppressLint("SetTextI18n")
    private void getDataFromModelReceivedByIntent(String strStatTop, String strStatBottom, String strStatBox) {
        try {

            tvMobileNumber.setText("Mobile Number : " + strMob);
//            tvRechargeAmount.setText("Amount :  \u20B9 " + strAmt);
//            statBox.setText(strStatBox);
//            tvstatTop.setText(strStatTop);
//            statBox.setBackgroundColor(Color.parseColor("#10AF53"));
//            statBottom.setText(strStatBottom);
//            tvOperatorName.setText(strOpDesc);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void getTransactionStatusHere() {
        try {
            showLoading();
            RequestGetTransactionStatus status = new RequestGetTransactionStatus();
//        Authentication authentication = new Authentication();
//        authentication.setLoginId(Utils.USERNAME);
//        authentication.setPassword(Utils.PASSWORD);
//        status.setAuthentication(authentication);
            status.setUserTrackId(userTrackID);

            LoggerUtil.logItem(status);

            Call<ResponseGetTransactionStatus> busCall = apiServicesTravel.getTransactionStatus(status);
            busCall.enqueue(new Callback<ResponseGetTransactionStatus>() {
                @Override
                public void onResponse(@NotNull Call<ResponseGetTransactionStatus> call, @NotNull Response<ResponseGetTransactionStatus> response) {
                    hideLoading();
                    LoggerUtil.logItem(response.body());
                    if (response.body().getResponseStatus() == 1) {
                        if (response.body().getTransactionStatusOutput().getTransactionStatus() == 0) {
                            //TODO
//                                statBox.setBackgroundColor(ContextCompat.getColor(context, R.color.color_red));
//                                Glide.with(context).load(R.drawable.pending).into(statusImage);
                            getDataFromModelReceivedByIntent(
                                    "Recharge Pending", "PENDING WITH HOST; PLEASE VERIFY LATER", "PENDING");
                        } else if (response.body().getTransactionStatusOutput().getTransactionStatus() == 1) {
                            //TODO getReprint here.
                            getReprintHere(response.body().getTransactionStatusOutput().getTransactionDetails().getReferenceNumber(),
                                    response.body().getTransactionStatusOutput().getTransactionDetails().getOperatorTransactionId(), response.body().getUserTrackId());
                        } else if (response.body().getTransactionStatusOutput().getTransactionStatus() == 2) {
                            //TODO
//                                statBox.setBackgroundColor(ContextCompat.getColor(context, R.color.color_red));
//                                Glide.with(context).load(R.drawable.failed).into(statusImage);
                            getDataFromModelReceivedByIntent(
                                    "Recharged Failed.", "Something Wrong", "FAILED");
                        } else if (response.body().getTransactionStatusOutput().getTransactionStatus() == 3) {
                            //TODO
//                                statBox.setBackgroundColor(ContextCompat.getColor(context, R.color.color_red));
//                                Glide.with(context).load(R.drawable.failed).into(statusImage);
                            getDataFromModelReceivedByIntent(
                                    "Recharged Failed.", "TRANSACTION NOT AVAILABLE ON OUR HOST", "FAILED");
                        } else if (response.body().getTransactionStatusOutput().getTransactionStatus() == 4) {
                            //TODO
//                                statBox.setBackgroundColor(ContextCompat.getColor(context, R.color.color_red));
//                                Glide.with(context).load(R.drawable.failed).into(statusImage);
                            getDataFromModelReceivedByIntent(
                                    "Recharged Failed.", "PURGED / INVALID USERTRACKID", "FAILED");
                        }
                    } else {
                        showMessage("Something went wrong");
                    }

                }

                @Override
                public void onFailure(@NotNull Call<ResponseGetTransactionStatus> call, @NotNull Throwable t) {
                    hideLoading();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            hideLoading();
        }
    }

    private void getReprintHere(String struserTrackID, final String reference, final String transId) {
        try {
            showLoading();
            RequestGetReprintMobile rePrint = new RequestGetReprintMobile();
//        Authentication authentication = new Authentication();
//        authentication.setLoginId(Utils.USERNAME);
//        authentication.setPassword(Utils.PASSWORD);
//        rePrint.setAuthentication(authentication);
            rePrint.setUserTrackId(struserTrackID);

            LoggerUtil.logItem(rePrint);

            Call<ResponseGetReprintMobile> busCall = apiServicesTravel.getReprintMobile(rePrint);
            busCall.enqueue(new Callback<ResponseGetReprintMobile>() {
                @Override
                public void onResponse(@NotNull Call<ResponseGetReprintMobile> call, @NotNull Response<ResponseGetReprintMobile> response) {
                    hideLoading();
                    LoggerUtil.logItem(response.body());
                    if (response.body().getResponseStatus() == 1) {

//                            tvReferenceNumber.setText(String.format("Reference Number : %s", resp.getReprintOutput().getReferenceNumber()));
                        tvMobileNumber.setText(String.format("Mobile Number : %s", response.body().getReprintOutput().getMobileNumber()));
                        tvDateAndTime.setText(String.format("Date & Time : %s", response.body().getReprintOutput().getRechargeDateTime()));
//                            tvRechargeAmount.setText(String.format("Amount :  ₹ %d", resp.getReprintOutput().getAmount()));
//                            statBox.setText("COMPLETED");
//                            tvOperatorName.setText(String.format("Operator : %s", strOpDesc));
//                            tvstatTop.setText("Recharged Successfully.");
//                            statBox.setBackgroundColor(Color.parseColor("#10AF53"));
//                            statBottom.setText(String.format("Payment of %d recharge Successfully.", resp.getReprintOutput().getAmount()));
//                            Glide.with(context).load(R.drawable.successful).into(statusImage);

                    } else {
                        showMessage("Something went wrong");
                    }

                }

                @Override
                public void onFailure(@NotNull Call<ResponseGetReprintMobile> call, @NotNull Throwable t) {
                    hideLoading();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            hideLoading();
        }
    }
}
