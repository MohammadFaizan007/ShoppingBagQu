package com.shoppingbag.utilities.domesticflight;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.shoppingbag.app.PreferencesManager;
import com.shoppingbag.constants.BaseActivity;
import com.shoppingbag.constants.Cons;
import com.shoppingbag.model.domesticflight.responsemodel.BookedSegmentsItem;
import com.shoppingbag.model.domesticflight.responsemodel.PassengerDetailsItem;
import com.shoppingbag.model.domesticflight.responsemodel.ResponseGetBookingDetails;
import com.shoppingbag.utils.LoggerUtil;
import com.shoppingbag.utils.Utils;
import com.shoppingbag.R;
import com.shoppingbag.common_activities.MainContainer;
import com.shoppingbag.utilities.domesticflight.adapter.BookedJourneyAdapter;
import com.shoppingbag.utilities.domesticflight.adapter.PassengersAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FlightBookingDetail extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.travellingBookingStatus)
    TextView travellingBookingStatus;
    @BindView(R.id.bookingemailMob)
    TextView bookingemailMob;
    @BindView(R.id.pnrNumber)
    TextView pnrNumber;
    @BindView(R.id.flightNameAndNumb)
    TextView flightNameAndNumb;
    @BindView(R.id.passengersRecyclerview)
    RecyclerView passengersRecyclerview;
    @BindView(R.id.totalpaidAmount)
    TextView totalpaidAmount;
    @BindView(R.id.journeyRecyclerview)
    RecyclerView journeyRecyclerview;
    @BindView(R.id.flightbookinglo)
    NestedScrollView flightbookinglo;
    @BindView(R.id.flightLogo)
    ImageView flightLogo;
    @BindView(R.id.txtIssueDate)
    TextView txtIssueDate;
    @BindView(R.id.transId)
    TextView transId;
    private String trackId = "";
    @BindView(R.id.footer)
    ConstraintLayout footer;
    @BindView(R.id.txtPrintTicket)
    TextView txtPrintTicket;


    private ResponseGetBookingDetails dtls;
    private BookedJourneyAdapter adapter;
    private List<BookedSegmentsItem> bookedSegments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flight_booking_detail);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        if (getIntent() != null) {
            getSupportActionBar().setTitle("Booking Detail");
            String response = getIntent().getStringExtra("response");
            try {


//                String param_ = Cons.decryptMsg(response.get("body").getAsString(), cross_intent);
                Gson gson = new GsonBuilder().create();
                JsonObject flightBook = gson.fromJson(response, JsonObject.class);


                flightBook.addProperty("Fk_MemId", Cons.decryptMsg(PreferencesManager.getInstance(context).getUSERID(), cross_intent));
                trackId = flightBook.get("UserTrackId").toString();
                dtls = gson.fromJson(String.valueOf(response), ResponseGetBookingDetails.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        toolbar.setNavigationOnClickListener(v -> {
            finishAffinity();
            Intent in = new Intent(FlightBookingDetail.this, MainContainer.class);
            startActivity(in);
        });


        getTransaction();

        bookingemailMob.setText(String.format("We have send the booking details to :\n %s And %s", dtls.getBookOutput().getFlightTicketDetails().get(0).getCustomerDetails().getEmailId(), dtls.getBookOutput().getFlightTicketDetails().get(0).getCustomerDetails().getContactNumber()));

        transId.setText(String.format("Transaction Id: %s", dtls.getBookOutput().getFlightTicketDetails().get(0).getTransactionId()));

        flightNameAndNumb.setText(String.format("%s-%s", dtls.getBookOutput().getFlightTicketDetails().get(0).getAirlineDetails().get(0).getAirlineName(), dtls.getBookOutput().getFlightTicketDetails().get(0).getAirlineDetails().get(0).getAirlineCode()));

        bookedSegments = dtls.getBookOutput().getFlightTicketDetails().get(0).getPassengerDetails().get(0).getBookedSegments();
        List<PassengerDetailsItem> passengerDetails = dtls.getBookOutput().getFlightTicketDetails().get(0).getPassengerDetails();

        pnrNumber.setText(String.format("PNR : %s", dtls.getBookOutput().getFlightTicketDetails().get(0).getAirlineDetails().get(0).getAirlinePNR()));

        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        journeyRecyclerview.setLayoutManager(manager);
        journeyRecyclerview.setHasFixedSize(true);

        journeyRecyclerview.setItemAnimator(new DefaultItemAnimator());

        adapter = new BookedJourneyAdapter(context, bookedSegments, dtls.getBookOutput().getFlightTicketDetails().get(0).getHermesPNR(),
                dtls.getBookOutput().getFlightTicketDetails().get(0).getAirlineDetails().get(0).getAirlinePNR());
        journeyRecyclerview.setAdapter(adapter);
        journeyRecyclerview.setNestedScrollingEnabled(false);

        LinearLayoutManager manager1 = new LinearLayoutManager(context);
        manager1.setOrientation(LinearLayoutManager.VERTICAL);
        passengersRecyclerview.setLayoutManager(manager1);
        passengersRecyclerview.setHasFixedSize(true);

        passengersRecyclerview.setAdapter(new PassengersAdapter(context, passengerDetails));

        passengersRecyclerview.setNestedScrollingEnabled(false);

        totalpaidAmount.setText(String.format(Locale.getDefault(), "₹ %s", dtls.getBookOutput().getFlightTicketDetails().get(0).getTotalAmount()));
        flightbookinglo.fullScroll(ScrollView.FOCUS_UP);
        flightbookinglo.scrollTo(0, flightbookinglo.getTop());

        txtIssueDate.setText(String.format("%s %s", Utils.getMonthDate(dtls.getBookOutput()
                .getFlightTicketDetails().get(0).getIssueDateTime()), Utils.getTime(dtls.getBookOutput()
                .getFlightTicketDetails().get(0).getIssueDateTime())));

        Glide.with(this).load("https://images.kiwi.com/airlines/64/" + dtls.getBookOutput().getFlightTicketDetails()
                .get(0).getAirlineDetails().get(0).getAirlineCode() + ".png").
                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        .placeholder(R.drawable.flight)
                        .error(R.drawable.flight))
                .into(flightLogo);


        footer.setOnClickListener(v -> {
            finishAffinity();
            Intent in = new Intent(FlightBookingDetail.this, MainContainer.class);
            startActivity(in);
        });

        txtPrintTicket.setOnClickListener(v -> showMessage("Under Development."));

    }

    @Override
    public void onBackPressed() {
        finishAffinity();
        Intent in = new Intent(FlightBookingDetail.this, MainContainer.class);
        startActivity(in);
    }


    private void getTransaction() {
        try {
            showLoading();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("UserTrackId", trackId);
            Call<JsonObject> busCall = apiServicesTravel.getTransactionStatusFlightBookingDetail(bodyParam(jsonObject));
            busCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                    try {
                        hideLoading();
                        LoggerUtil.logItem(response.body());
                        Log.e(">>response", response.toString());
                        LoggerUtil.logItem(call.request().url());
                        String param_ = Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent);
                        Gson gson = new GsonBuilder().create();
                        JsonObject postresponse = gson.fromJson(param_, JsonObject.class);
                        if (postresponse.get("ResponseStatus").getAsInt() == 1) {

                            JsonObject TransactionStatusOutput = postresponse.get("TransactionStatusOutput").getAsJsonObject();
                            JsonObject TransactionStatus = TransactionStatusOutput.get("TransactionStatus").getAsJsonObject();

                            if (TransactionStatus.get("Status").getAsInt() == 1) {
                                travellingBookingStatus.setText("Transaction Successful!");
                                //                                updateBookingdetail();
                            }

                        } else {
                            travellingBookingStatus.setText("Transaction Failed!");
                            travellingBookingStatus.setTextColor(ContextCompat.getColor(FlightBookingDetail.this,
                                    R.color.color_red));

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
            hideLoading();
        }
    }

}
