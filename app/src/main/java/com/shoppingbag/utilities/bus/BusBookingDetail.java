package com.shoppingbag.utilities.bus;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.shoppingbag.BuildConfig;
import com.shoppingbag.R;
import com.shoppingbag.app.PreferencesManager;
import com.shoppingbag.constants.BaseActivity;
import com.shoppingbag.constants.Cons;
import com.shoppingbag.model.response.bus_response.requestpojo.CancellationInput;
import com.shoppingbag.model.response.bus_response.requestpojo.CancellationPenaltyInput;
import com.shoppingbag.model.response.bus_response.requestpojo.RequestGetCancellation;
import com.shoppingbag.model.response.bus_response.requestpojo.RequestGetCancellationPenalty;
import com.shoppingbag.model.response.bus_response.responsepojo.ReprintOutput;
import com.shoppingbag.model.response.bus_response.responsepojo.ResponseGetCancellationPenalty;
import com.shoppingbag.model.response.bus_response.responsepojo.ResponseGetReprint;
import com.shoppingbag.utils.LoggerUtil;
import com.shoppingbag.utils.NetworkUtils;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.shoppingbag.app.AppConfig.PAYLOAD_BUNDLE;

public class BusBookingDetail extends BaseActivity {
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
    @BindView(R.id.tv_cancellation_policy)
    TextView tvCancellationPolicy;
    @BindView(R.id.btn_cancel_ticket)
    Button btnCancelTicket;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.side_menu)
    ImageView sideMenu;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_cancelTxt)
    TextView tvCancelTxt;
    private Dialog CancellationPolicyDialog;
    private String cancellationPolicyTxt_st, transactionId, transportId, totalTicket, ticketNo, ticketStatus;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bus_booking_detail);
        ButterKnife.bind(this);
        try {
            title.setText("Your Ticket Detail");
            Bundle param = getIntent().getBundleExtra(PAYLOAD_BUNDLE);
            transactionId = param.getString("TransactionId");
            Log.e("TransId", param.getString("TransactionId"));
            transportId = param.getString("TransportId");
            ticketStatus = param.getString("TicketStatus");

            if (NetworkUtils.getConnectivityStatus(context) != 0)
                getBusBookingDetail(param.getString("TransactionId"));
            else
                showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getBusBookingDetail(String tranId) {
        showLoading();
        try {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("TransactionId", tranId);
            JsonObject mainobject = new JsonObject();
            mainobject.add("ReprintInput", jsonObject);
            LoggerUtil.logItem(mainobject);
            Call<JsonObject> jsonObjectCall = apiServicesTravel.getBusReprint(bodyParam(mainobject));
            jsonObjectCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    hideLoading();
                    LoggerUtil.logItem(response.body());
                    LoggerUtil.logItem(response.code());

                    try {
                        if (response.isSuccessful()) {
                            String param = Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent);
                            Gson gson = new GsonBuilder().create();
                            LoggerUtil.logItem(param);
                            ResponseGetReprint getReprint = gson.fromJson(param, ResponseGetReprint.class);
                            if (getReprint.getResponseStatus().equalsIgnoreCase("1")) {
                                getCancelParameter(getReprint.getUserTrackId());
                                setData(getReprint.getReprintOutput());
                            } else {
                                showMessage("No Record Found");
                            }
                        } else {
                            showMessage("something went wrong");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    showMessage(t.getMessage());

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @OnClick({R.id.tv_cancellation_policy, R.id.btn_cancel_ticket})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cancellation_policy:
                CancellationPolicyDialog();
                break;
            case R.id.btn_cancel_ticket:
                if (NetworkUtils.getConnectivityStatus(context) != 0)
                    getCancellationPenalty();
                else
                    showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
                break;
        }
    }

    private void setData(ReprintOutput data) {
        try {
            tvPnr.setText(data.getReprintTicketDetails().getReprintPNRDetails().getTransportPNR());
            tvBusType.setText(data.getReprintTicketDetails().getReprintPNRDetails().getBusName());
            tvBoard.setText(data.getReprintTicketDetails().getReprintPNRDetails().getOrigin());
            tvDest.setText(data.getReprintTicketDetails().getReprintPNRDetails().getDestination());
            tvDepartTime.setText(data.getReprintTicketDetails().getReprintPNRDetails().getReprintPaxList().get(0).getBoardingPoints().getTime());
            tvDestTime.setText(data.getReprintTicketDetails().getReprintPNRDetails().getReprintPaxList().get(0).getDroppingPoints().getTime());
            tvTravelDate.setText(data.getReprintTicketDetails().getReprintPNRDetails().getTravelDate());
            tvSeats.setText(data.getReprintTicketDetails().getReprintPNRDetails().getReprintPaxList().get(0).getSeatType());
            tvSeatNumbers.setText(data.getReprintTicketDetails().getReprintPNRDetails().getReprintPaxList().get(0).getSeatNo());
            tvName.setText(data.getReprintTicketDetails().getReprintPNRDetails().getReprintPaxList().get(0).getPassengerName());
            tvTicketNumber.setText(data.getReprintTicketDetails().getReprintPNRDetails().getReprintPaxList().get(0).getTicketNumber());
            tvFare.setText(data.getReprintTicketDetails().getReprintPNRDetails().getReprintPaxList().get(0).getFare());
            tvBoardAddress.setText(data.getReprintTicketDetails().getReprintPNRDetails().getReprintPaxList().get(0).getBoardingPoints().getPlace());
            tvArrivalAddress.setText(data.getReprintTicketDetails().getReprintPNRDetails().getReprintPaxList().get(0).getDroppingPoints().getPlace());

            cancellationPolicyTxt_st = data.getReprintTicketDetails().getCancellationPolicy().replace("UPDATED CANCELLATION POLICY*", "");
            cancellationPolicyTxt_st = cancellationPolicyTxt_st.replace(" %%  deduction %%", "% deduction");
            cancellationPolicyTxt_st = cancellationPolicyTxt_st.replace("*", "\n\n");
            cancellationPolicyTxt_st = cancellationPolicyTxt_st.replace(".", ". ");

            totalTicket = data.getReprintTicketDetails().getReprintPNRDetails().getTotalTickets();
            ticketNo = data.getReprintTicketDetails().getReprintPNRDetails().getReprintPaxList().get(0).getTicketNumber() + ",";


            String currentDate_st = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());

            String travelDate_st = data.getReprintTicketDetails().getReprintPNRDetails().getTravelDate();
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date travelDate = formatter.parse(travelDate_st);
            Date currentDate = formatter.parse(currentDate_st);

            if (travelDate.before(currentDate)) {
                if (ticketStatus.contains("CANCEL")) {
                    tvCancelTxt.setVisibility(View.VISIBLE);
                    btnCancelTicket.setVisibility(View.GONE);
                } else {
                    tvCancelTxt.setVisibility(View.GONE);
                    btnCancelTicket.setVisibility(View.GONE);
                }
            } else {
                //  myDate must be today or later
                if (ticketStatus.contains("CANCEL")) {
                    tvCancelTxt.setVisibility(View.VISIBLE);
                    btnCancelTicket.setVisibility(View.GONE);
                } else {
                    tvCancelTxt.setVisibility(View.GONE);
                    btnCancelTicket.setVisibility(View.VISIBLE);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void CancellationPolicyDialog() {
        try {
            CancellationPolicyDialog = new Dialog(context);
            CancellationPolicyDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            CancellationPolicyDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        CancellationPolicyDialog.getWindow().getAttributes().windowAnimations = R.style.dialog_animation;
            CancellationPolicyDialog.setContentView(R.layout.dialog_cancellation_policy);

            Button btn_ok = CancellationPolicyDialog.findViewById(R.id.btn_ok);
            btn_ok.setOnClickListener(v -> CancellationPolicyDialog.dismiss());

            TextView tv_policy = CancellationPolicyDialog.findViewById(R.id.tv_policy);
            tv_policy.setText(cancellationPolicyTxt_st);

            CancellationPolicyDialog.setCanceledOnTouchOutside(true);
            CancellationPolicyDialog.setCancelable(true);
            CancellationPolicyDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getCancellationPenalty() {
        try {
            CancellationPenaltyInput input = new CancellationPenaltyInput();
            input.setTransactionId(transactionId);

            RequestGetCancellationPenalty requestGetCancellationPenalty = new RequestGetCancellationPenalty();
            requestGetCancellationPenalty.setCancellationPenaltyInput(input);
            LoggerUtil.logItem(requestGetCancellationPenalty);
            showLoading();

            Call<ResponseGetCancellationPenalty> call = apiServicesTravel.getCancellationPenalty(requestGetCancellationPenalty);
            call.enqueue(new Callback<ResponseGetCancellationPenalty>() {
                @Override
                public void onResponse(Call<ResponseGetCancellationPenalty> call, Response<ResponseGetCancellationPenalty> response) {
                    LoggerUtil.logItem(response.body());
                    if (response.body().getResponseStatus().equalsIgnoreCase("1")) {
                        getTicketCancel(response.body().getCancellationPenaltyOutput().getToCancelPNRDetails().getToCancelPaxList().get(0).getPenatlyAmount());
                    } else {
                        hideLoading();
                        showToastS(response.body().getFailureRemarks().toString());
                    }
                }

                @Override
                public void onFailure(Call<ResponseGetCancellationPenalty> call, Throwable t) {
                    hideLoading();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    private void getCancelParameter(String rootId) {
        try {
            String url = BuildConfig.BASE_URL_TRAVEL + "GetCancellationPolicyInput?UserTrackId=" + Cons.encryptMsg(rootId, cross_intent);
            LoggerUtil.logItem("Cancel" + url);
            Call<JsonObject> call = apiServices.getCancelParameter(url);
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                    LoggerUtil.logItem(response.body());
                    LoggerUtil.logItem(response.code());
                    LoggerUtil.logItem(call.request().url());
                    try {
                        if (response.isSuccessful()) {
                            String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent);
                            LoggerUtil.logItem("cancelparameter" + paramResponse);

                        } else {
                            showMessage("Something went wrog");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                    hideLoading();
                    LoggerUtil.logItem(t.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getTicketCancel(String penaltyAmt) {
        try {
            CancellationInput input = new CancellationInput();
            input.setTransactionId(transactionId);
            input.setTotalTicketsToCancel(totalTicket);
            input.setPenaltyAmount(penaltyAmt);
            input.setTicketNumbers(ticketNo);
            input.setTransportId(transportId);

            RequestGetCancellation requestGetCancellation = new RequestGetCancellation();
            requestGetCancellation.setFKMemID(PreferencesManager.getInstance(context).getUSERID());
            requestGetCancellation.setCancellationInput(input);
            LoggerUtil.logItem(requestGetCancellation);
            Call<JsonObject> call = apiServicesTravel.getCancellation(requestGetCancellation);
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                    hideLoading();
                    Log.e(">>response", response.toString());
                    if (response.body() != null && response.body().get("ResponseStatus").getAsString().equalsIgnoreCase("1")) {
                        showMessage("Ticket Cancel Successfully..");
                        tvCancelTxt.setVisibility(View.VISIBLE);
                        btnCancelTicket.setVisibility(View.GONE);

                    } else {
                        showMessage(response.body().get("FailureRemarks").toString() + "\nCancellation Failed..");
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

    public void pressBack(View v) {
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }
}
