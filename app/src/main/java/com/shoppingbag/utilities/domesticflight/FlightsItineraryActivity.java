package com.shoppingbag.utilities.domesticflight;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.shoppingbag.constants.BaseActivity;
import com.shoppingbag.constants.Cons;
import com.shoppingbag.model.domesticflight.requestmodel.AvailabilityInput;
import com.shoppingbag.model.domesticflight.responsemodel.AvailSegmentsItem;
import com.shoppingbag.model.domesticflight.responsemodel.OngoingFlightsItem;
import com.shoppingbag.model.domesticflight.responsemodel.ResponseGetTax;
import com.shoppingbag.model.domesticflight.responsemodel.ReturnFlightsItem;
import com.shoppingbag.utils.LoggerUtil;
import com.shoppingbag.utils.Utils;
import com.shoppingbag.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FlightsItineraryActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    // Ongoing Layout ------------------------
    @BindView(R.id.txt_Source_dest)
    TextView txtSourceDest;
    @BindView(R.id.txtRefund)
    TextView txtRefund;
    @BindView(R.id.txt_flight_date)
    TextView txtFlightDate;
    @BindView(R.id.topLay)
    ConstraintLayout topLay;
    @BindView(R.id.txtFirst_flight_id)
    TextView txtFirstFlightId;
    @BindView(R.id.txt_depart_date)
    TextView txtDepartDate;
    @BindView(R.id.txt_duration)
    TextView txtDuration;
    @BindView(R.id.txtArrival_date)
    TextView txtArrivalDate;
    @BindView(R.id.txtSource)
    TextView txtSource;
    @BindView(R.id.txtDest)
    TextView txtDest;
    @BindView(R.id.txtDepart_time)
    TextView txtDepartTime;
    @BindView(R.id.txtArrival_time)
    TextView txtArrivalTime;
    @BindView(R.id.txtSource_airport)
    TextView txtSourceAirport;
    @BindView(R.id.txtDest_airport)
    TextView txtDestAirport;
    @BindView(R.id.txtDelay_time)
    TextView txtDelayTime;
    @BindView(R.id.txtSecond_flight_id)
    TextView txtSecondFlightId;
    @BindView(R.id.txt_depart_date2)
    TextView txtDepartDate2;
    @BindView(R.id.txt_duration2)
    TextView txtDuration2;
    @BindView(R.id.txtArrival_date2)
    TextView txtArrivalDate2;
    @BindView(R.id.txtSource2)
    TextView txtSource2;
    @BindView(R.id.txtDepart_time2)
    TextView txtDepartTime2;
    @BindView(R.id.txtSource_airport2)
    TextView txtSourceAirport2;
    @BindView(R.id.txtDest2)
    TextView txtDest2;
    @BindView(R.id.txtArrival_time2)
    TextView txtArrivalTime2;
    @BindView(R.id.txtDest_airport2)
    TextView txtDestAirport2;
    @BindView(R.id.ongoing_details)
    FrameLayout ongoingDetails;
    // Return layout ----------------------
    @BindView(R.id.txt_Source_dest2)
    TextView txtSourceDest2;
    @BindView(R.id.txtRefund2)
    TextView txtRefund2;
    @BindView(R.id.txt_flight_date2)
    TextView txtFlightDate2;
    @BindView(R.id.txtFirst_flight_id2)
    TextView txtFirstFlightId2;
    @BindView(R.id.txt_depart_date12)
    TextView txtDepartDate12;
    @BindView(R.id.txt_duration12)
    TextView txtDuration12;
    @BindView(R.id.txtArrival_date12)
    TextView txtArrivalDate12;
    @BindView(R.id.txtSource12)
    TextView txtSource12;
    @BindView(R.id.txtDest12)
    TextView txtDest12;
    @BindView(R.id.txtDepart_time12)
    TextView txtDepartTime12;
    @BindView(R.id.txtArrival_time12)
    TextView txtArrivalTime12;
    @BindView(R.id.txtSource_airport12)
    TextView txtSourceAirport12;
    @BindView(R.id.txtDest_airport12)
    TextView txtDestAirport12;
    @BindView(R.id.txtDelay_time2)
    TextView txtDelayTime2;
    @BindView(R.id.txtSecond_flight_id2)
    TextView txtSecondFlightId2;
    @BindView(R.id.txt_depart_date22)
    TextView txtDepartDate22;
    @BindView(R.id.txt_duration22)
    TextView txtDuration22;
    @BindView(R.id.txtArrival_date22)
    TextView txtArrivalDate22;
    @BindView(R.id.txtSource22)
    TextView txtSource22;
    @BindView(R.id.txtDepart_time22)
    TextView txtDepartTime22;
    @BindView(R.id.txtSource_airport22)
    TextView txtSourceAirport22;
    @BindView(R.id.txtDest22)
    TextView txtDest22;
    @BindView(R.id.txtArrival_time22)
    TextView txtArrivalTime22;
    @BindView(R.id.txtBook)
    TextView txtBook;
    @BindView(R.id.txtDest_airport22)
    TextView txtDestAirport22;
    @BindView(R.id.txtFareRule)
    TextView txtFareRule;
    @BindView(R.id.return_card)
    CardView returnCard;
    @BindView(R.id.returning_details)
    FrameLayout returningDetails;
    @BindView(R.id.ongoing_card)
    CardView ongoingCard;
    @BindView(R.id.txtFare)
    TextView txtFare;
    @BindView(R.id.txtTraveller)
    TextView txtTraveller;
    private OngoingFlightsItem ongoingFlightsItem;
    private ReturnFlightsItem returnFlightsItem;
    private AvailabilityInput availabilityInput;
    private String trackId = "";
    private float totalFareOngoing = 0;
    private float totalFareReturn = 0;
    private int totalTraveller = 0;

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flight_itinerary_lay);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (getIntent() != null) {
            getSupportActionBar().setTitle("Review Itineary");
            ongoingFlightsItem = (OngoingFlightsItem) getIntent().getSerializableExtra("Flight1Details");
            if (getIntent().getSerializableExtra("Flight2Details") != null) {
                returnFlightsItem = (ReturnFlightsItem) getIntent().getSerializableExtra("Flight2Details");
            }
            availabilityInput = (AvailabilityInput) getIntent().getSerializableExtra("Input");
            trackId = getIntent().getStringExtra("TrackId");

        }

        getTax(true);
        OngoingDetails(null);

        if (availabilityInput.getJourneyDetails().size() > 1) {
            ReturningDetails(null);
            getTax(false);
        } else {
            returningDetails.setVisibility(View.GONE);
        }


        txtBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(FlightsItineraryActivity.this, FlightBookForm.class);
                in.putExtra("Input", availabilityInput);
                in.putExtra("TrackId", trackId);
                in.putExtra("Origin", availabilityInput.getJourneyDetails().get(0).getOrigin());
                in.putExtra("Destination", availabilityInput.getJourneyDetails().get(0).getDestination());
                in.putExtra("TravelDate", availabilityInput.getJourneyDetails().get(0).getTravelDate());
                if (availabilityInput.getJourneyDetails().size() > 1) {
                    in.putExtra("ReturningDate", availabilityInput.getJourneyDetails().get(1).getTravelDate());
                } else {
                    in.putExtra("ReturningDate", "");
                }
                in.putExtra("ClassType", availabilityInput.getClassType());
                in.putExtra("ongoing", ongoingFlightsItem);
                in.putExtra("return", returnFlightsItem);
                in.putExtra("ongoingFare", totalFareOngoing);
                in.putExtra("returnFare", totalFareReturn);
                startActivity(in);

//                Toast.makeText(FlightsItineraryActivity.this, "Under development.", Toast.LENGTH_SHORT).show();
            }
        });

        txtFareRule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(FlightsItineraryActivity.this, FareRule.class);

                in.putExtra("Flight", ongoingFlightsItem.getAvailSegments().get(0));
                in.putExtra("TrackId", trackId);

                String Baggage = "CheckInBaggage: " + ongoingFlightsItem.getAvailSegments().get(ongoingFlightsItem.getAvailSegments().size() - 1).
                        getAvailPaxFareDetails().get(0).getBaggageAllowed().getCheckInBaggage();
                Baggage = Baggage + "\n\nHandBaggage: " + ongoingFlightsItem.getAvailSegments().get(ongoingFlightsItem.getAvailSegments().size() - 1).
                        getAvailPaxFareDetails().get(0).getBaggageAllowed().getHandBaggage();
                in.putExtra("Baggage", Baggage);

                startActivity(in);
            }
        });

        txtFare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fareDialog();
            }
        });

    }

    private void setVisibilityLay1(int visibility) {
        txtDelayTime.setVisibility(visibility);
        txtSecondFlightId.setVisibility(visibility);
        txtDepartDate2.setVisibility(visibility);
        txtDuration2.setVisibility(visibility);
        txtArrivalDate2.setVisibility(visibility);
        txtSource2.setVisibility(visibility);
        txtDepartTime2.setVisibility(visibility);
        txtDest2.setVisibility(visibility);
        txtArrivalTime2.setVisibility(visibility);
    }

    private void setVisibilityLay2(int visibility) {
        txtDelayTime2.setVisibility(visibility);
        txtSecondFlightId2.setVisibility(visibility);
        txtDepartDate22.setVisibility(visibility);
        txtDuration22.setVisibility(visibility);
        txtArrivalDate22.setVisibility(visibility);
        txtSource22.setVisibility(visibility);
        txtDepartTime22.setVisibility(visibility);
        txtDest22.setVisibility(visibility);
        txtArrivalTime22.setVisibility(visibility);
    }

    private void OngoingDetails(ResponseGetTax responseGetTaxOngoing) {

        // ONGOING FLIGHT DETAILS. -----------------------------------------------------------
        txtSourceDest.setText(String.format("%s - %s", availabilityInput.getJourneyDetails().get(0).getOrigin(),
                availabilityInput.getJourneyDetails().get(0).getDestination()));
        txtRefund.setText(ongoingFlightsItem.getAvailSegments().get(0).getAvailPaxFareDetails().get(0).getAdult().getFareType());

        // Get No of Stops.
        String stops = "Non-Stop";
        if (ongoingFlightsItem.getAvailSegments().size() > 1) {
            stops = ongoingFlightsItem.getAvailSegments().size() - 1 + " Stops";
        }
        // Get Total journey duration.
        String totalDuration = Utils.getTimeDifference(ongoingFlightsItem.getAvailSegments().get(0).getDepartureDateTime(),
                ongoingFlightsItem.getAvailSegments().get(ongoingFlightsItem.getAvailSegments().size() - 1).getArrivalDateTime());

        txtFlightDate.setText(String.format("%s | %s | %s", Utils.changeDateFormat(availabilityInput.getJourneyDetails().get(0).getTravelDate())
                , stops, totalDuration));


        txtFirstFlightId.setText(String.format("%s - %s (%s)", ongoingFlightsItem.getAvailSegments().get(0).getAirlineCode(), ongoingFlightsItem.getAvailSegments()
                .get(0).getFlightNumber(), availabilityInput.getClassType()));
        txtDepartDate.setText(Utils.getDate(ongoingFlightsItem.getAvailSegments().get(0).getDepartureDateTime()));

        txtDuration.setText(String.format("---- %s ----", ongoingFlightsItem.getAvailSegments().get(0).getDuration().replace("hrs", "h")
                .replace("mins", "m")));
        txtArrivalDate.setText(Utils.getDate(ongoingFlightsItem.getAvailSegments().get(0).getArrivalDateTime()));
        txtSource.setText(ongoingFlightsItem.getAvailSegments().get(0).getOrigin());
        txtDest.setText(ongoingFlightsItem.getAvailSegments().get(0).getDestination());
        txtDepartTime.setText(Utils.getTime(ongoingFlightsItem.getAvailSegments().get(0).getDepartureDateTime()));
        txtArrivalTime.setText(Utils.getTime(ongoingFlightsItem.getAvailSegments().get(0).getArrivalDateTime()));

        float totalFareAdult;
        float totalFareChild = 0;
        float totalFareInfants = 0;

        AvailSegmentsItem segmentsItem = ongoingFlightsItem.getAvailSegments().get(ongoingFlightsItem.getAvailSegments().size() - 1);

        if (responseGetTaxOngoing != null) {


            totalFareAdult = availabilityInput.getAdultCount() * responseGetTaxOngoing.getTaxOutput()
                    .getTaxResFlightSegments().get(0).getAdultTax()
                    .getFareBreakUpDetails().getGrossAmount();
        } else {
            totalFareAdult = availabilityInput.getAdultCount() * segmentsItem.getAvailPaxFareDetails().get(0)
                    .getAdult().getGrossAmount();
        }

        if (segmentsItem.getAvailPaxFareDetails().get(0).getChild() != null) {

            if (responseGetTaxOngoing != null) {

                totalFareChild = availabilityInput.getChildCount() * responseGetTaxOngoing.getTaxOutput()
                        .getTaxResFlightSegments().get(0).getChildTax()
                        .getFareBreakUpDetails().getGrossAmount();

            } else {
                totalFareChild = availabilityInput.getChildCount() * segmentsItem.getAvailPaxFareDetails()
                        .get(0).getChild().getGrossAmount();

            }
        }

        if (segmentsItem.getAvailPaxFareDetails().get(0).getInfant() != null) {
            totalFareInfants = availabilityInput.getInfantCount() * segmentsItem.getAvailPaxFareDetails()
                    .get(0).getInfant().getGrossAmount();
        }

        totalFareOngoing = totalFareAdult + totalFareChild + totalFareInfants;


        totalTraveller = availabilityInput.getAdultCount() + availabilityInput.getChildCount() + availabilityInput.getInfantCount();

        txtFare.setText(String.format(Locale.getDefault(), "₹ %.2f", totalFareOngoing));
        txtTraveller.setText(String.format(Locale.getDefault(), "%d Traveller", totalTraveller));

        if (ongoingFlightsItem.getAvailSegments().size() > 1) {

            txtDelayTime.setText(String.format("%s - %s Layover", ongoingFlightsItem.getAvailSegments().get(0).getDestination(), Utils.getTimeDifference(ongoingFlightsItem.getAvailSegments().get(0).getArrivalDateTime(),
                    ongoingFlightsItem.getAvailSegments().get(ongoingFlightsItem.getAvailSegments().size() - 1).getDepartureDateTime())));

            setVisibilityLay1(View.VISIBLE);
            txtSecondFlightId.setText(String.format("%s - %s (%s)", ongoingFlightsItem.getAvailSegments().get(1).getAirlineCode(),
                    ongoingFlightsItem.getAvailSegments().get(1).getFlightNumber(), availabilityInput.getClassType()));
            txtDepartDate2.setText(Utils.getDate(ongoingFlightsItem.getAvailSegments().get(1).getDepartureDateTime()));

            txtDuration2.setText(String.format("---- %s ----", ongoingFlightsItem.getAvailSegments().get(1).getDuration().replace("hrs", "h")
                    .replace("mins", "m")));
            txtArrivalDate2.setText(Utils.getDate(ongoingFlightsItem.getAvailSegments().get(1).getArrivalDateTime()));
            txtSource2.setText(ongoingFlightsItem.getAvailSegments().get(1).getOrigin());
            txtDest2.setText(ongoingFlightsItem.getAvailSegments().get(1).getDestination());
            txtDepartTime2.setText(Utils.getTime(ongoingFlightsItem.getAvailSegments().get(1).getDepartureDateTime()));
            txtArrivalTime2.setText(Utils.getTime(ongoingFlightsItem.getAvailSegments().get(1).getArrivalDateTime()));

        } else {
            setVisibilityLay1(View.GONE);
        }
    }

    private void ReturningDetails(ResponseGetTax responseGetTax) {

        float totalFareAdult;
        float totalFareChild = 0;
        float totalFareInfants = 0;

        returningDetails.setVisibility(View.VISIBLE);


        AvailSegmentsItem segmentsItem = returnFlightsItem.getAvailSegments().get(returnFlightsItem.getAvailSegments().size() - 1);

        if (responseGetTax != null) {

            totalFareAdult = availabilityInput.getAdultCount() * responseGetTax.getTaxOutput().getTaxResFlightSegments()
                    .get(0).getAdultTax()
                    .getFareBreakUpDetails().getGrossAmount();
        } else {
            totalFareAdult = availabilityInput.getAdultCount() * segmentsItem.getAvailPaxFareDetails().get(0)
                    .getAdult().getGrossAmount();
        }

        if (segmentsItem.getAvailPaxFareDetails().get(0).getChild() != null) {

            if (responseGetTax != null) {

                totalFareChild = availabilityInput.getChildCount() * responseGetTax.getTaxOutput()
                        .getTaxResFlightSegments().get(0).getChildTax().getFareBreakUpDetails().getGrossAmount();

            } else {
                totalFareChild = availabilityInput.getChildCount() * segmentsItem.getAvailPaxFareDetails()
                        .get(0).getChild().getGrossAmount();

            }
        }

        if (segmentsItem.getAvailPaxFareDetails().get(0).getInfant() != null) {
            totalFareInfants = availabilityInput.getInfantCount() * segmentsItem.getAvailPaxFareDetails()
                    .get(0).getInfant().getGrossAmount();
        }

        totalFareReturn = totalFareAdult + totalFareChild + totalFareInfants;


        txtSourceDest2.setText(String.format("%s - %s", availabilityInput.getJourneyDetails().get(1).getOrigin(),
                availabilityInput.getJourneyDetails().get(1).getDestination()));
        txtRefund2.setText(returnFlightsItem.getAvailSegments().get(0).getAvailPaxFareDetails().get(0).getAdult().getFareType());

        // Get No of Stops.
        String stops = "Non-Stop";
        if (returnFlightsItem.getAvailSegments().size() > 1) {
            stops = returnFlightsItem.getAvailSegments().size() - 1 + " Stops";
        }
        // Get Total journey duration.
        String totalDuration = Utils.getTimeDifference(returnFlightsItem.getAvailSegments().get(0).getDepartureDateTime(),
                returnFlightsItem.getAvailSegments().get(returnFlightsItem.getAvailSegments().size() - 1).getArrivalDateTime());

        txtFlightDate2.setText(String.format("%s | %s | %s", Utils.changeDateFormat(availabilityInput.getJourneyDetails().get(1).getTravelDate())
                , stops, totalDuration));


        txtFare.setText(String.format(Locale.getDefault(), "₹ %.2f", (totalFareOngoing + totalFareReturn)));
        txtTraveller.setText(String.format(Locale.getDefault(), "%d Traveller", totalTraveller));
        returningDetails.setVisibility(View.VISIBLE);

        // RETURN FLIGHT DETAILS. -----------------------------------------------------------

        txtFirstFlightId2.setText(String.format("%s - %s (%s)", returnFlightsItem.getAvailSegments().get(0).getAirlineCode(),
                returnFlightsItem.getAvailSegments().get(0).getFlightNumber(), availabilityInput.getClassType()));
        txtDepartDate12.setText(Utils.getDate(returnFlightsItem.getAvailSegments().get(0).getDepartureDateTime()));

        txtDuration12.setText(String.format("---- %s ----", returnFlightsItem.getAvailSegments().get(0).getDuration().replace("hrs", "h")
                .replace("mins", "m")));
        txtArrivalDate12.setText(Utils.getDate(returnFlightsItem.getAvailSegments().get(0).getArrivalDateTime()));
        txtSource12.setText(returnFlightsItem.getAvailSegments().get(0).getOrigin());
        txtDest12.setText(returnFlightsItem.getAvailSegments().get(0).getDestination());
        txtDepartTime12.setText(Utils.getTime(returnFlightsItem.getAvailSegments().get(0).getDepartureDateTime()));
        txtArrivalTime12.setText(Utils.getTime(returnFlightsItem.getAvailSegments().get(0).getArrivalDateTime()));

        txtSourceDest2.setText(String.format("%s - %s", availabilityInput.getJourneyDetails().get(1).getOrigin(),
                availabilityInput.getJourneyDetails().get(1).getDestination()));
        txtRefund2.setText(returnFlightsItem.getAvailSegments().get(0).getAvailPaxFareDetails().get(0).getAdult().getFareType());


        if (returnFlightsItem.getAvailSegments().size() > 1) {

            txtDelayTime2.setText(String.format("%s - %s Layover", returnFlightsItem.getAvailSegments().get(0).getDestination(), Utils.getTimeDifference(returnFlightsItem.getAvailSegments().get(0).getArrivalDateTime(),
                    returnFlightsItem.getAvailSegments().get(returnFlightsItem.getAvailSegments().size() - 1).getDepartureDateTime())));

            setVisibilityLay2(View.VISIBLE);
            txtSecondFlightId2.setText(String.format("%s - %s (%s)", returnFlightsItem.getAvailSegments().get(1).getAirlineCode(),
                    returnFlightsItem.getAvailSegments().get(1).getFlightNumber(), availabilityInput.getClassType()));
            txtDepartDate22.setText(Utils.getDate(returnFlightsItem.getAvailSegments().get(1).getDepartureDateTime()));


            txtDuration22.setText(String.format("---- %s ----", returnFlightsItem.getAvailSegments().get(1).getDuration().replace("hrs", "h")
                    .replace("mins", "m")));
            txtArrivalDate22.setText(Utils.getDate(returnFlightsItem.getAvailSegments().get(1).getArrivalDateTime()));
            txtSource22.setText(returnFlightsItem.getAvailSegments().get(1).getOrigin());
            txtDest22.setText(returnFlightsItem.getAvailSegments().get(1).getDestination());
            txtDepartTime22.setText(Utils.getTime(returnFlightsItem.getAvailSegments().get(1).getDepartureDateTime()));
            txtArrivalTime22.setText(Utils.getTime(returnFlightsItem.getAvailSegments().get(1).getArrivalDateTime()));


        } else {
//            setVisibilityLay2(0x00000008);
            setVisibilityLay2(View.GONE);
        }
    }

    private void fareDialog() {

        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.fare_description);

        TextView textViewSrc = dialog.findViewById(R.id.textViewSrc);
        TextView textViewDest = dialog.findViewById(R.id.textViewDest);
        TextView textViewAdult = dialog.findViewById(R.id.textViewAdult);
        TextView textViewChildLabel = dialog.findViewById(R.id.textViewChildLabel);
        TextView textViewChild = dialog.findViewById(R.id.textViewChild);
        TextView textViewInfantsLabel = dialog.findViewById(R.id.textViewInfantsLabel);
        TextView textViewInfants = dialog.findViewById(R.id.textViewInfants);
        TextView textViewTotalTax = dialog.findViewById(R.id.textViewTotalTax);
        TextView textViewServiceTax = dialog.findViewById(R.id.textViewServiceTax);
        TextView textViewYqTax = dialog.findViewById(R.id.textViewYqTax);
        TextView textViewOther = dialog.findViewById(R.id.textViewOther);
        TextView textViewGrandTotal = dialog.findViewById(R.id.textViewGrandTotal);

        textViewSrc.setText(availabilityInput.getJourneyDetails().get(0).getOrigin());
        textViewDest.setText(availabilityInput.getJourneyDetails().get(0).getDestination());

        AvailSegmentsItem segmentsItem = ongoingFlightsItem.getAvailSegments().get(ongoingFlightsItem.getAvailSegments().size() - 1);

        int totalFareChild = 0;
        int totalFareInfants = 0;
        float totalFareTotalTax = 0.0f;
        float totalFareServiceTax = 0.0f;
        float totalFareOtherTax = 0.0f;
        float totalFareYq = 0.0f;

        int totalFareAdult = segmentsItem.getAvailPaxFareDetails().get(0)
                .getAdult().getBasicAmount();

        for (int i = 0; i < segmentsItem.getAvailPaxFareDetails().get(0).getAdult().getTaxDetails().size(); i++) {
            switch (segmentsItem.getAvailPaxFareDetails().get(0).getAdult().getTaxDetails().get(i).getDescription()) {
                case "TotalTax":
                    totalFareTotalTax = segmentsItem.getAvailPaxFareDetails().get(0).getAdult().getTaxDetails().get(i).getAmount();
                    break;
                case "YQ":
                    totalFareYq = segmentsItem.getAvailPaxFareDetails().get(0).getAdult().getTaxDetails().get(i).getAmount();
                    break;
                case "ServiceTax":
                    totalFareServiceTax = segmentsItem.getAvailPaxFareDetails().get(0).getAdult().getTaxDetails().get(i).getAmount();
                    break;
                case "OtherCharges":
                    totalFareOtherTax = segmentsItem.getAvailPaxFareDetails().get(0).getAdult().getTaxDetails().get(i).getAmount();
                    break;
            }
        }


        if (segmentsItem.getAvailPaxFareDetails().get(0).getChild() != null) {
            totalFareChild = segmentsItem.getAvailPaxFareDetails().get(0).getChild().getBasicAmount();


            for (int i = 0; i < segmentsItem.getAvailPaxFareDetails().get(0).getChild().getTaxDetails().size(); i++) {
                switch (segmentsItem.getAvailPaxFareDetails().get(0).getChild().getTaxDetails().get(i).getDescription()) {
                    case "TotalTax":
                        totalFareTotalTax = totalFareTotalTax + segmentsItem.getAvailPaxFareDetails().get(0).getChild().getTaxDetails().get(i).getAmount();
                        break;
                    case "YQ":
                        totalFareYq = totalFareYq + segmentsItem.getAvailPaxFareDetails().get(0).getChild().getTaxDetails().get(i).getAmount();
                        break;
                    case "ServiceTax":
                        totalFareServiceTax = totalFareServiceTax + segmentsItem.getAvailPaxFareDetails().get(0).getChild().getTaxDetails().get(i).getAmount();
                        break;
                    case "OtherCharges":
                        totalFareOtherTax = totalFareOtherTax + segmentsItem.getAvailPaxFareDetails().get(0).getChild().getTaxDetails().get(i).getAmount();
                        break;
                }
            }

        } else {
            textViewChildLabel.setVisibility(View.GONE);
            textViewChild.setVisibility(View.GONE);
        }

        if (segmentsItem.getAvailPaxFareDetails().get(0).getInfant() != null) {
            totalFareInfants = segmentsItem.getAvailPaxFareDetails().get(0).getInfant().getBasicAmount();
        } else {
            textViewInfantsLabel.setVisibility(View.GONE);
            textViewInfants.setVisibility(View.GONE);
        }


        if (availabilityInput.getJourneyDetails().size() > 1) {

            segmentsItem = returnFlightsItem.getAvailSegments().get(returnFlightsItem.getAvailSegments().size() - 1);

            totalFareAdult = totalFareAdult + segmentsItem.getAvailPaxFareDetails().get(0)
                    .getAdult().getBasicAmount();

            for (int i = 0; i < segmentsItem.getAvailPaxFareDetails().get(0).getAdult().getTaxDetails().size(); i++) {
                switch (segmentsItem.getAvailPaxFareDetails().get(0).getAdult().getTaxDetails().get(i).getDescription()) {
                    case "TotalTax":
                        totalFareTotalTax = totalFareTotalTax + segmentsItem.getAvailPaxFareDetails().get(0).getAdult().getTaxDetails().get(i).getAmount();
                        break;
                    case "YQ":
                        totalFareYq = totalFareYq + segmentsItem.getAvailPaxFareDetails().get(0).getAdult().getTaxDetails().get(i).getAmount();
                        break;
                    case "ServiceTax":
                        totalFareServiceTax = totalFareServiceTax + segmentsItem.getAvailPaxFareDetails().get(0).getAdult().getTaxDetails().get(i).getAmount();
                        break;
                    case "OtherCharges":
                        totalFareOtherTax = totalFareOtherTax + segmentsItem.getAvailPaxFareDetails().get(0).getAdult().getTaxDetails().get(i).getAmount();
                        break;
                }
            }


            if (segmentsItem.getAvailPaxFareDetails().get(0).getChild() != null) {
                totalFareChild = totalFareChild + segmentsItem.getAvailPaxFareDetails().get(0).getChild().getBasicAmount();

                for (int i = 0; i < segmentsItem.getAvailPaxFareDetails().get(0).getChild().getTaxDetails().size(); i++) {
                    switch (segmentsItem.getAvailPaxFareDetails().get(0).getChild().getTaxDetails().get(i).getDescription()) {
                        case "TotalTax":
                            totalFareTotalTax = totalFareTotalTax + segmentsItem.getAvailPaxFareDetails().get(0).getChild().getTaxDetails().get(i).getAmount();
                            break;
                        case "YQ":
                            totalFareYq = totalFareYq + segmentsItem.getAvailPaxFareDetails().get(0).getChild().getTaxDetails().get(i).getAmount();
                            break;
                        case "ServiceTax":
                            totalFareServiceTax = totalFareServiceTax + segmentsItem.getAvailPaxFareDetails().get(0).getChild().getTaxDetails().get(i).getAmount();
                            break;
                        case "OtherCharges":
                            totalFareOtherTax = totalFareOtherTax + segmentsItem.getAvailPaxFareDetails().get(0).getChild().getTaxDetails().get(i).getAmount();
                            break;
                    }
                }

            } else {
                textViewChildLabel.setVisibility(View.GONE);
                textViewChild.setVisibility(View.GONE);
            }

            if (segmentsItem.getAvailPaxFareDetails().get(0).getInfant() != null) {

                totalFareInfants = totalFareInfants + segmentsItem.getAvailPaxFareDetails().get(0).getInfant().getBasicAmount();

            } else {
                textViewInfantsLabel.setVisibility(View.GONE);
                textViewInfants.setVisibility(View.GONE);
            }
        }

        textViewAdult.setText(String.format(Locale.getDefault(), "₹ %d", totalFareAdult));
        textViewChild.setText(String.format(Locale.getDefault(), "₹ %d", totalFareChild));
        textViewInfants.setText(String.format(Locale.getDefault(), "₹ %d", totalFareInfants));
        textViewTotalTax.setText(String.format(Locale.getDefault(), "₹ %.2f", totalFareTotalTax));
        textViewServiceTax.setText(String.format(Locale.getDefault(), "₹ %.2f", totalFareServiceTax));
        textViewYqTax.setText(String.format(Locale.getDefault(), "₹ %.2f", totalFareYq));
        textViewOther.setText(String.format(Locale.getDefault(), "₹ %.2f", totalFareOtherTax));
        textViewGrandTotal.setText(String.format(Locale.getDefault(), "₹ %.2f", totalFareOngoing + totalFareReturn));


        int width = Math.round(getResources().getDisplayMetrics().widthPixels * 0.85f);

        ViewGroup.LayoutParams params = dialog.getWindow().getAttributes();
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;

        dialog.getWindow().setLayout(width, params.height);

        dialog.show();

    }


    private void getTax(final boolean ongoing) {
        try {

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("UserTrackId", trackId);
            JsonObject taxInputObject = new JsonObject();

            JsonObject gstdatails = new JsonObject();
            gstdatails.addProperty("GSTNumber", "22AAAAA0000A1Z5");
            gstdatails.addProperty("EMailId", "test@test.com");
            gstdatails.addProperty("CompanyName", "Hermes");
            gstdatails.addProperty("ContactNumber", "1234567890");
            gstdatails.addProperty("Address", "Guindy");
            gstdatails.addProperty("FirstName", "Vel");
            gstdatails.addProperty("LastName", "Murugan");


            JsonArray taxReqFlight = new JsonArray();
            if (ongoing) {
                for (int i = 0; i < ongoingFlightsItem.getAvailSegments().size(); i++) {
                    AvailSegmentsItem segmentsItem = ongoingFlightsItem.getAvailSegments().get(i);
                    JsonObject taxReqObject = new JsonObject();
                    taxReqObject.addProperty("FlightId", segmentsItem.getFlightId());
                    taxReqObject.addProperty("ClassCode", segmentsItem.getAvailPaxFareDetails().get(0).getClassCode());
                    taxReqObject.addProperty("AirlineCode", segmentsItem.getAirlineCode());
                    taxReqObject.addProperty("ETicketFlag", 1);
                    taxReqObject.addProperty("BasicAmount", ongoingFlightsItem.getAvailSegments().get(ongoingFlightsItem.getAvailSegments().size() - 1)
                            .getAvailPaxFareDetails().get(0).getAdult().getBasicAmount());
                    taxReqObject.addProperty("SupplierId", "0");
                    taxReqFlight.add(taxReqObject);
                }
            } else {
                for (int i = 0; i < returnFlightsItem.getAvailSegments().size(); i++) {
                    AvailSegmentsItem segmentsItem = returnFlightsItem.getAvailSegments().get(i);
                    JsonObject taxReqObject = new JsonObject();
                    taxReqObject.addProperty("FlightId", segmentsItem.getFlightId());
                    taxReqObject.addProperty("ClassCode", segmentsItem.getAvailPaxFareDetails().get(0).getClassCode());
                    taxReqObject.addProperty("AirlineCode", segmentsItem.getAirlineCode());
                    taxReqObject.addProperty("ETicketFlag", 1);
                    taxReqObject.addProperty("BasicAmount", returnFlightsItem.getAvailSegments().get(returnFlightsItem.getAvailSegments().size() - 1)
                            .getAvailPaxFareDetails().get(0).getAdult().getBasicAmount());
                    taxReqObject.addProperty("SupplierId", "0");
                    taxReqFlight.add(taxReqObject);
                }
            }
            taxInputObject.add("TaxReqFlightSegments", taxReqFlight);
            taxInputObject.add("GSTDetails", gstdatails);

            jsonObject.add("TaxInput", taxInputObject);


            Call<JsonObject> busCall = apiServicesTravel.getTaxAPI(bodyParam(jsonObject));
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
                        ResponseGetTax postPaidresponse = gson.fromJson(param_, ResponseGetTax.class);
                        if (postPaidresponse.getResponseStatus() == 1) {

                            if (ongoing) {
                                OngoingDetails(postPaidresponse);
                            } else {
                                ReturningDetails(postPaidresponse);
                            }
                        } else {

                            if (ongoing) {
                                showMessage(postPaidresponse.getResponseStatus());
                                finish();
                            } else {
                                showMessage(postPaidresponse.getResponseStatus());
                                finish();
                            }

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
