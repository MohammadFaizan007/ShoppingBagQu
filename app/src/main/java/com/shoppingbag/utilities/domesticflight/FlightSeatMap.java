package com.shoppingbag.utilities.domesticflight;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shoppingbag.R;
import com.shoppingbag.utilities.domesticflight.seatAdapter.AbstractItem;
import com.shoppingbag.utilities.domesticflight.seatAdapter.AirplaneAdapter;
import com.shoppingbag.utilities.domesticflight.seatAdapter.AvaliableItem;
import com.shoppingbag.utilities.domesticflight.seatAdapter.BlockedItem;
import com.shoppingbag.utilities.domesticflight.seatAdapter.Bookedtem;
import com.shoppingbag.utilities.domesticflight.seatAdapter.EmptyItem;
import com.shoppingbag.utilities.domesticflight.seatAdapter.OnSeatSelected;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.shoppingbag.constants.BaseActivity;
import com.shoppingbag.model.domesticflight.seatAdapter.FlightSegmentsItem;
import com.shoppingbag.model.domesticflight.seatAdapter.RequestSeatMap;
import com.shoppingbag.model.domesticflight.seatAdapter.ResponseSeatMap;
import com.shoppingbag.model.domesticflight.seatAdapter.SeatMapAfterBookingInput;
import com.shoppingbag.model.domesticflight.seatAdapter.SeatRowsItem;
import com.shoppingbag.model.domesticflight.seatAdapter.SeatsItem;
import com.shoppingbag.model.domesticflight.seatAdapter.seatConfirm.PassengerDetailsItem;
import com.shoppingbag.model.domesticflight.seatAdapter.seatConfirm.RequestSeatConfirm;
import com.shoppingbag.model.domesticflight.seatAdapter.seatConfirm.SeatConfirmAfterBookingInput;
import com.shoppingbag.utils.LoggerUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FlightSeatMap extends BaseActivity implements OnSeatSelected {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.seatList)
    RecyclerView seatList;
    int selection;
    @BindView(R.id.txtOrigin)
    TextView txtOrigin;
    @BindView(R.id.txtDestination)
    TextView txtDestination;
    @BindView(R.id.btnNext)
    Button btnNext;
    @BindView(R.id.bottomLay)
    ConstraintLayout bottomLay;
    BottomSheetBehavior sheetBehavior;
    @BindView(R.id.selectionseatList)
    RecyclerView selectionSeatList;
    List<com.shoppingbag.model.domesticflight.seatAdapter.seatConfirm.FlightSegmentsItem> flightSegments = new ArrayList<>();
    private ResponseSeatMap resp;
    private String airLineCode = "";
    private String pnr = "", airpnr = "";
    private int indexCounter;
    private AirplaneAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flight_seat_map_lay);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(v -> finish());

        sheetBehavior = BottomSheetBehavior.from(bottomLay);

        sheetBehavior.setState(BottomSheetBehavior.PEEK_HEIGHT_AUTO);
        sheetBehavior.setHideable(false);

        /**
         * bottom sheet state change listener
         * we are changing button text when sheet changed state   358978062591172   80
         * */

        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        break;
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });


        try {

            if (getIntent() != null) {

//                NLUW9O NLUW9Q NLUW9R NLUW9S

                airLineCode = getIntent().getStringExtra("code");
                pnr = getIntent().getStringExtra("pnr");
                airpnr = getIntent().getStringExtra("airlinepnr");
                getseatMap(airLineCode, pnr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (indexCounter < resp.getSeatMapAfterBookingOutput().getFlightSegments().size()) {
                    updatePassengerSeat();
                    updateAdapter(indexCounter);
                    indexCounter++;
                } else {
                    updatePassengerSeat();
                    updateSeatMap();
                }
            }
        });
    }

    private void getseatMap(String airlineCode, String PNR) {
        try {
            showLoading();
            RequestSeatMap requestSeatMap = new RequestSeatMap();

//        Authentication authentication = new Authentication();
//        authentication.setLoginId(Utils.USERNAME);
//        authentication.setPassword(Utils.PASSWORD);
//        requestSeatMap.setAuthentication(authentication);

            SeatMapAfterBookingInput bookingInput = new SeatMapAfterBookingInput();

            bookingInput.setAirlineCode(airlineCode);
            bookingInput.setHermesPNR(PNR);
            requestSeatMap.setSeatMapAfterBookingInput(bookingInput);

            LoggerUtil.logItem(requestSeatMap);

            Call<ResponseSeatMap> busCall = apiServicesTravel.getSeatMapAfterBooking(requestSeatMap);
            busCall.enqueue(new Callback<ResponseSeatMap>() {
                @Override
                public void onResponse(@NotNull Call<ResponseSeatMap> call, @NotNull Response<ResponseSeatMap> response) {
                    hideLoading();
                    LoggerUtil.logItem(response.body());
                    resp = response.body();
                    if (response.body().getResponseStatus() == 1) {
                        indexCounter = 0;
                        updateAdapter(indexCounter);
                        indexCounter++;
                    } else {
                        showMessage("Something Went Wrong");
                        finish();
                    }

                }

                @Override
                public void onFailure(@NotNull Call<ResponseSeatMap> call, @NotNull Throwable t) {
                    hideLoading();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            hideLoading();
        }
    }

    private void updateSeatMap() {
        try {
            showLoading();
            RequestSeatConfirm seatConfirm = new RequestSeatConfirm();

//            Authentication authentication = new Authentication();
//            authentication.setLoginId(Utils.USERNAME);
//            authentication.setPassword(Utils.PASSWORD);
//            seatConfirm.setAuthentication(authentication);

            SeatConfirmAfterBookingInput bookingInput = new SeatConfirmAfterBookingInput();
            bookingInput.setAirlineCode(airLineCode);
            bookingInput.setAirlinePnr(airpnr);
            bookingInput.setHermesPNR(pnr);
            bookingInput.setFlightSegments(flightSegments);

            seatConfirm.setSeatConfirmAfterBookingInput(bookingInput);

            LoggerUtil.logItem(seatConfirm);

            Call<JsonObject> busCall = apiServicesTravel.getSeatBook(seatConfirm);
            busCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                    hideLoading();
                    LoggerUtil.logItem(response.body());
                    if (response.body().get("ResponseStatus").getAsInt() == 1) {
                        showMessage(response.body().get("SeatConfirmAfterBookingOutput").getAsJsonObject().get("Remarks").getAsString());
//                            updateBookingSeatdetail(response.getJSONObject("SeatConfirmAfterBookingOutput").getString("Remarks"));
                        finish();
                    } else {
                        showMessage(response.body().get("FailureRemarks").getAsString());
                        hideLoading();
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

    private void updatePassengerSeat() {
        com.shoppingbag.model.domesticflight.seatAdapter.seatConfirm.FlightSegmentsItem bookFlighteSegment;
        bookFlighteSegment = flightSegments.get(indexCounter - 1);
        List<Integer> seatList = adapter.getSelectedItems();

        boolean isFound;
        for (int i = 0; i < bookFlighteSegment.getPassengerDetails().size(); i++) {
            if (i == indexCounter - 1) {
                PassengerDetailsItem detailsItem = bookFlighteSegment.getPassengerDetails().get(i);

                List<SeatRowsItem> rowsItem = resp.getSeatMapAfterBookingOutput().getFlightSegments().get(indexCounter - 1)
                        .getSeatLayoutDetails().getSeatLayout().getSeatRows();
                int counter = 0;
                isFound = false;
                //--------------------------------------------------------------
                for (int j = 0; j < rowsItem.size(); j++) {
                    for (int k = 0; k < rowsItem.get(j).getSeats().size(); k++) {

                        for (int l = 0; l < seatList.size(); l++) {
                            if (seatList.get(l) == counter) {
                                isFound = true;
                                SeatsItem item = rowsItem.get(j).getSeats().get(k);
                                detailsItem.setSeatAmount(item.getSeatAmount());
                                detailsItem.setSeatCode(item.getSeatCode());
                                detailsItem.setSeatNumber(item.getSeatNo());
                                break;
                            }
                        }
                        if (isFound)
                            break;
                        counter++;
                    }
                    if (isFound)
                        break;
                }
                //--------------------------------------------------------------
            }

        }


    }

    private void updateAdapter(int pos) {

        com.shoppingbag.model.domesticflight.seatAdapter.seatConfirm.FlightSegmentsItem bookFlighteSegment = new com.shoppingbag.model.domesticflight.seatAdapter.seatConfirm.FlightSegmentsItem();

        FlightSegmentsItem segmentsItem = resp.getSeatMapAfterBookingOutput().getFlightSegments().get(pos);

        bookFlighteSegment.setAirlineCode(segmentsItem.getAirlineCode());
        bookFlighteSegment.setClassCode(segmentsItem.getClassCode());
        bookFlighteSegment.setOrigin(segmentsItem.getOrigin());
        bookFlighteSegment.setDestination(segmentsItem.getDestination());
        bookFlighteSegment.setFlightNumber(segmentsItem.getFlightNumber());
        bookFlighteSegment.setDepartureDate(segmentsItem.getDepartureDate());

        List<PassengerDetailsItem> bookPassengerItem = new ArrayList<>();
        List<com.shoppingbag.model.domesticflight.seatAdapter.PassengerDetailsItem> passengerDetailsItem = resp.getSeatMapAfterBookingOutput().getPassengerDetails();

        for (int i = 0; i < passengerDetailsItem.size(); i++) {
            PassengerDetailsItem item = new PassengerDetailsItem();
            item.setFirstName(passengerDetailsItem.get(i).getFirstName());
            item.setLastName(passengerDetailsItem.get(i).getLastName());
            item.setTitle(passengerDetailsItem.get(i).getTitle());
            item.setPaxNumber(passengerDetailsItem.get(i).getPaxNumber());
            item.setPaxType(passengerDetailsItem.get(i).getPaxType());
            bookPassengerItem.add(item);
        }

        bookFlighteSegment.setPassengerDetails(bookPassengerItem);

        flightSegments.add(bookFlighteSegment);


//        toolbar.setTitle("Seat Map " + segmentsItem.getOrigin() + " - " + segmentsItem.getDestination());

        txtOrigin.setText(segmentsItem.getOrigin());
        txtDestination.setText(segmentsItem.getDestination());

        int COLUMNS = segmentsItem.getSeatLayoutDetails()
                .getSeatLayout().getSeatRows().get(0).getSeats().size();

//        String bookedSeat = segmentsItem
//                .getSeatLayoutDetails().getBookedSeats();
//        String blockedSeat = segmentsItem
//                .getSeatLayoutDetails().getBlockedSeats();
//

        List<String> bookedSeat = new ArrayList<>(Arrays.asList(segmentsItem.getSeatLayoutDetails().getBookedSeats().split(",")));

        List<String> blockedSeat = new ArrayList<>(Arrays.asList(segmentsItem.getSeatLayoutDetails().getBlockedSeats().split(",")));


        selection = resp.getSeatMapAfterBookingOutput().getPassengerDetails().size();

        toolbar.setSubtitle(selection + " Travellers");

        List<AbstractItem> items = new ArrayList<>();
        for (int i = 0; i < segmentsItem.getSeatLayoutDetails()
                .getSeatLayout().getSeatRows().size(); i++) {
            SeatRowsItem rowsItem = segmentsItem.getSeatLayoutDetails()
                    .getSeatLayout().getSeatRows().get(i);
            for (int j = 0; j < rowsItem.getSeats().size(); j++) {
                SeatsItem seatsItem = rowsItem.getSeats().get(j);

                if (seatsItem != null) {
                    if (bookedSeat != null && bookedSeat.contains(seatsItem.getSeatNo())) {
                        items.add(new Bookedtem(String.valueOf(i)));
                    } else if (blockedSeat != null && blockedSeat.contains(seatsItem.getSeatNo())) {
                        items.add(new BlockedItem(String.valueOf(i)));
                    } else {
                        items.add(new AvaliableItem(String.valueOf(i)));
                    }
                } else {
                    items.add(new EmptyItem(String.valueOf(i)));
                }

            }
        }

        GridLayoutManager manager = new GridLayoutManager(this, COLUMNS);
        seatList.setLayoutManager(manager);

        adapter = new AirplaneAdapter(this, items, selection);
        seatList.setAdapter(adapter);

    }

    @Override
    public void onSeatSelected(int count, int itemPos) {

    }
}
