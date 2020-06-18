package com.shoppingbag.utilities.bus;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.shoppingbag.constants.BaseActivity;
import com.shoppingbag.constants.Cons;
import com.shoppingbag.model.response.bus_response.bookingHistory.ResponseBusBookings;
import com.shoppingbag.model.response.bus_response.busTransactionStatus.ResponseBusBookingTransation;
import com.shoppingbag.model.response.bus_response.busTransactionStatus.TransactionStatusOutput;
import com.shoppingbag.utils.LoggerUtil;
import com.shoppingbag.R;
import com.shoppingbag.common_activities.MainContainer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;

import java.security.GeneralSecurityException;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.shoppingbag.app.AppConfig.PAYLOAD_BUNDLE;

public class BusBookingConfirmation extends BaseActivity {

    @BindView(R.id.img_trans_status)
    ImageView imgTransStatus;
    @BindView(R.id.tv_trans_status)
    TextView tvTransStatus;
    @BindView(R.id.tv_share_with)
    TextView tvShareWith;
    @BindView(R.id.tv_pnr)
    TextView tvPnr;
    @BindView(R.id.tv_bus_type)
    TextView tvBusType;
    @BindView(R.id.tv_board)
    TextView tvBoard;
    @BindView(R.id.tv_dest)
    TextView tvDest;
    @BindView(R.id.tv_depart_time)
    TextView tvDepartTime;
    @BindView(R.id.tv_dest_time)
    TextView tvDestTime;
    @BindView(R.id.tv_travel_date)
    TextView tvTravelDate;
    @BindView(R.id.tv_seats)
    TextView tvSeats;
    @BindView(R.id.tv_seat_numbers)
    TextView tvSeatNumbers;
    @BindView(R.id.tv_ticket_number)
    TextView tvTicketNumber;
    @BindView(R.id.tv_fare)
    TextView tvFare;
    @BindView(R.id.tv_board_address)
    TextView tvBoardAddress;
    @BindView(R.id.tv_arrival_address)
    TextView tvArrivalAddress;
    Bundle param;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bus_booking_confirmation);
        ButterKnife.bind(this);
        param = getIntent().getBundleExtra(PAYLOAD_BUNDLE);
        String userTrackId = param.getString("userTrackId");
        getTrasactionDetails(userTrackId);

    }

    private void getTrasactionDetails(String userTrackId) {
        try {
            JsonObject object = new JsonObject();
            object.addProperty("UserTrackId", userTrackId);
            LoggerUtil.logItem(object);
            Log.e(">>object", object.toString());
            showLoading();

            Call<JsonObject> call = apiServicesTravel.getbustransactionstatus(bodyParam(object));
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                    try {
                        hideLoading();
                        LoggerUtil.logItem(call.request().url());
                        Log.e(">>>", response.toString());
                        LoggerUtil.logItem(response.body());
                        String param = Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent);
                        Gson gson = new GsonBuilder().create();
                        ResponseBusBookingTransation postPaidresponse = gson.fromJson(param, ResponseBusBookingTransation.class);
                        if (postPaidresponse.getResponseStatus().equalsIgnoreCase("1")) {
                            setData(postPaidresponse.getTransactionStatusOutput());
                        } else {
                            showMessage("Something went wrong");
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

    private void setData(TransactionStatusOutput data) {

        String sta = data.getTransactionStatus();
        if (sta.equalsIgnoreCase("1")) {
            Glide.with(context).load(R.drawable.success).into(imgTransStatus);
            tvTransStatus.setText("Transaction Successful");
            tvTransStatus.setTextColor(ContextCompat.getColor(context, R.color.dark_green));
        } else {
            Glide.with(context).load(R.drawable.failed).into(imgTransStatus);
            tvTransStatus.setText("Transaction Failed");
            tvTransStatus.setTextColor(ContextCompat.getColor(context, R.color.color_red));
        }

//        tvTransStatus.setText(data.getTransactionStatus());
        tvPnr.setText(data.getTicketDetails().getPNRDetails().getTransportPNR());
        tvBusType.setText(data.getTicketDetails().getPNRDetails().getBusName());
        tvBoard.setText(data.getTicketDetails().getPNRDetails().getOrigin());
        tvDest.setText(data.getTicketDetails().getPNRDetails().getDestination());
        tvDepartTime.setText(data.getTicketDetails().getPNRDetails().getPaxList().get(0).getBoardingPoints().getTime());
        tvDestTime.setText(data.getTicketDetails().getPNRDetails().getPaxList().get(0).getDroppingPoints().getTime());
        tvTravelDate.setText(data.getTicketDetails().getPNRDetails().getTravelDate());
        tvSeats.setText(data.getTicketDetails().getPNRDetails().getPaxList().get(0).getSeatType());
        tvSeatNumbers.setText(data.getTicketDetails().getPNRDetails().getPaxList().get(0).getSeatNo());
        tvTicketNumber.setText(data.getTicketDetails().getPNRDetails().getPaxList().get(0).getTicketNumber());
        tvFare.setText(data.getTicketDetails().getPNRDetails().getPaxList().get(0).getFare());
        tvBoardAddress.setText(data.getTicketDetails().getPNRDetails().getPaxList().get(0).getBoardingPoints().getPlace());
        tvArrivalAddress.setText(data.getTicketDetails().getPNRDetails().getPaxList().get(0).getDroppingPoints().getPlace());


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), MainContainer.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
