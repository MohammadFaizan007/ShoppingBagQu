package com.shoppingbag.utilities.bus;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shoppingbag.constants.BaseActivity;
import com.shoppingbag.constants.Cons;
import com.shoppingbag.model.response.bus_response.ResponseGetSearchBus;
import com.shoppingbag.model.response.bus_response.requestpojo.PassengerListItem;
import com.shoppingbag.model.response.bus_response.responsepojo.ResponseBusLayout;
import com.shoppingbag.model.response.bus_response.responsepojo.SeatsItem;
import com.shoppingbag.utils.LoggerUtil;
import com.shoppingbag.R;
import com.shoppingbag.utilities.bus.adapter.BusSeatAdapter;
import com.shoppingbag.utilities.domesticflight.seatAdapter.OnSeatSelected;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.shoppingbag.app.AppConfig.PAYLOAD_BUNDLE;

public class BusSeatMap extends BaseActivity implements OnSeatSelected {

    @BindView(R.id.seatList)
    RecyclerView seatList;

    @BindView(R.id.txtOrigin)
    TextView txtOrigin;
    @BindView(R.id.txtDestination)
    TextView txtDestination;
    @BindView(R.id.btnNext)
    Button btnNext;
    @BindView(R.id.bottomLay)
    ConstraintLayout bottomLay;
    @BindView(R.id.selectionseatList)
    RecyclerView selectionSeatList;
    @BindView(R.id.side_menu)
    ImageView sideMenu;
    @BindView(R.id.title)
    TextView title;


    BottomSheetBehavior sheetBehavior;
    @BindView(R.id.lowerLayout)
    ConstraintLayout lowerLayout;
    @BindView(R.id.upperSeatList)
    RecyclerView upperSeatList;
    @BindView(R.id.upperLayout)
    ConstraintLayout upperLayout;
    @BindView(R.id.txtLower)
    TextView txtLower;
    @BindView(R.id.txtUpper)
    TextView txtUpper;


    private ResponseBusLayout resp;
    private BusSeatAdapter lowerAdapter, upperAdapter;
    private String originNameStr = "", destinationNameStr = "", travelDate = "", userTrackId = "", scheduleId = "", stationId = "", transportId = "", arrivalTimeStr = "", departTimeStr = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bus_seat_map);
        ButterKnife.bind(this);

        sideMenu.setOnClickListener(v -> onBackPressed());

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

            Bundle param = getIntent().getBundleExtra(PAYLOAD_BUNDLE);
            if (getIntent() != null) {
                originNameStr = param.getString("originName");
                destinationNameStr = param.getString("destinationName");
                travelDate = param.getString("travelDate");
                userTrackId = param.getString("userTrackId");
                scheduleId = param.getString("scheduleId");
                stationId = param.getString("stationId");
                transportId = param.getString("transportId");
                arrivalTimeStr = param.getString("ArrivalTime");
                departTimeStr = param.getString("DepartureTime");

                title.setText(String.format("Seat Map %s - %s", originNameStr, destinationNameStr));
                txtOrigin.setText(originNameStr);
                txtDestination.setText(destinationNameStr);

                getSeatMap(scheduleId, stationId, transportId, travelDate, userTrackId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        btnNext.setOnClickListener(v -> {

            LoggerUtil.logItem(resp.getSeatMapOutput().getBoardingPoints());
            LoggerUtil.logItem("Boarding");


            List<Integer> lowerSelectSeatList = lowerAdapter.getSelectedItems();
            List<Integer> upperSelectSeatList = null;
            if (upperAdapter != null) {
                upperSelectSeatList = upperAdapter.getSelectedItems();
            }

            List<BusAbstractItem> lowerSeats = lowerAdapter.mItems;
            List<BusAbstractItem> upperSeats = null;
            if (upperAdapter != null) {
                upperSeats = upperAdapter.mItems;
            }

            ArrayList<PassengerListItem> passengerListItems = new ArrayList<>();

            // Get Lower Seat Details.
            for (int j = 0; j < lowerSeats.size(); j++) {

                for (int i = 0; i < lowerSelectSeatList.size(); i++) {
                    if (j == lowerSelectSeatList.get(i)) {
                        PassengerListItem listItem = new PassengerListItem();
                        listItem.setFare(Double.parseDouble(lowerSeats.get(j).getFare()));
                        listItem.setSeatNo(lowerSeats.get(j).getSeatNo());
                        listItem.setSeatTypeId(Integer.parseInt(lowerSeats.get(j).getSeatTypeId()));
                        listItem.setBoardingId("");
                        listItem.setDroppingId("");
                        passengerListItems.add(listItem);
                        LoggerUtil.logItem("Select " + lowerSeats.get(j).getSeatNo());
                        break;
                    }

                }
            }

            if (upperSeats != null) {
                // Get Upper Seat Details.
                for (int j = 0; j < upperSeats.size(); j++) {

                    for (int i = 0; i < upperSelectSeatList.size(); i++) {
                        if (j == upperSelectSeatList.get(i)) {
                            PassengerListItem listItem = new PassengerListItem();
                            listItem.setFare(Double.parseDouble(upperSeats.get(j).getFare()));
                            listItem.setSeatNo(upperSeats.get(j).getSeatNo());
                            listItem.setSeatTypeId(Integer.parseInt(upperSeats.get(j).getSeatTypeId()));
                            listItem.setBoardingId("");
                            listItem.setDroppingId("");
                            passengerListItems.add(listItem);
                            LoggerUtil.logItem("Select " + upperSeats.get(j).getSeatNo());
                            break;
                        }

                    }
                }
            }
            if (passengerListItems.size() > 0) {
                Bundle param = new Bundle();
                param.putString("originName", originNameStr);
                param.putString("destinationName", destinationNameStr);
                param.putString("arrivalTimeStr", arrivalTimeStr);
                param.putString("departTimeStr", departTimeStr);

                param.putString("userTrackId", userTrackId);
                param.putString("scheduleId", scheduleId);
                param.putString("stationId", stationId);
                param.putString("transportId", transportId);
                param.putString("travelDate", travelDate);

                param.putSerializable("total_sheet", passengerListItems);
                param.putSerializable("BoardingList", (Serializable) resp.getSeatMapOutput().getBoardingPoints());
                param.putSerializable("DroppingList", (Serializable) resp.getSeatMapOutput().getDroppingPoints());
                Intent intent = new Intent(context, BusBoardingPoint.class);
//                Intent intent = new Intent(context, BusBookingForm.class);
                intent.putExtra(PAYLOAD_BUNDLE, param);
                startActivity(intent);
            } else {
                showMessage("Please select at least one seat for booking.");
            }

        });
    }

    @Override
    public void onSeatSelected(int count, int itemPos) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    private void getSeatMap(String scheduleid, String stationid, String transportid, String traveldate, String usertrackid) {

        JsonObject mainObject = new JsonObject();

        showLoading();

        JsonObject object = new JsonObject();
        object.addProperty("scheduleId", scheduleid);
        object.addProperty("stationId", stationid);
        object.addProperty("transportId", transportid);
        object.addProperty("travelDate", traveldate);

        mainObject.addProperty("userTrackId", usertrackid);
        mainObject.add("SeatMapInput", object);

        LoggerUtil.logItem(mainObject);

        Call<JsonObject> call = apiServicesTravel.getBusSeatMap(bodyParam(mainObject));
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {

                try {
                    hideLoading();
                    LoggerUtil.logItem(response.body());
                    LoggerUtil.logItem(call.request().url());
                    Log.e(">>>", response.toString());
                    LoggerUtil.logItem(response.body());
                    String param_ = Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent);
                    Gson gson = new GsonBuilder().create();
                    ResponseBusLayout postPaidresponse = gson.fromJson(param_, ResponseBusLayout.class);

                    if (postPaidresponse.getResponseStatus().equalsIgnoreCase("1")) {
                        resp = postPaidresponse;
                        updateAdapter();
                    } else {
                        showMessage("Something Went Wrong");
                        finish();
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
    }

    private void updateAdapter() {

        int COLUMNS = resp.getSeatMapOutput().getSeatLayoutDetails().getSeatLayout().getSeatColumns().get(0).getSeats().size();

        ArrayList<String> bookedSeat = new ArrayList<>();
        if (resp.getSeatMapOutput().getSeatLayoutDetails().getBookedSeats() != null) {
            for (int i = 0; i < resp.getSeatMapOutput().getSeatLayoutDetails().getBookedSeats().size(); i++) {
                bookedSeat.add(resp.getSeatMapOutput().getSeatLayoutDetails().getBookedSeats().get(i).getSeatNo());
            }
        }

        List<BusAbstractItem> lower_seat_items = new ArrayList<>();
        List<BusAbstractItem> upper_seat_items = new ArrayList<>();

        for (int i = 0; i < resp.getSeatMapOutput().getSeatLayoutDetails()
                .getSeatLayout().getSeatColumns().size(); i++) {

            List<SeatsItem> rowsItem = resp.getSeatMapOutput().getSeatLayoutDetails()
                    .getSeatLayout().getSeatColumns().get(i).getSeats();

            LoggerUtil.logItem("------------------------ ");
            for (int j = 0; j < rowsItem.size(); j++) {
                SeatsItem seatsItem = rowsItem.get(j);

                if (seatsItem != null) {

                    if (seatsItem.getBerthType().equalsIgnoreCase("U")) {

                        if (bookedSeat.contains(seatsItem.getSeatNo())) {
                            upper_seat_items.add(new Bookedtem(String.valueOf(i), seatsItem.getBerthType(),
                                    seatsItem.getSeatNo(), seatsItem.getSeatTypeId(), seatsItem.getFare()));
                        }/* else if (blockedSeat.contains(seatsItem.getSeatNo())) {
                        upper_seat_items.add(new BlockedItem(String.valueOf(i)));
                    }*/ else {
                            upper_seat_items.add(new AvaliableItem(String.valueOf(i), seatsItem.getBerthType(),
                                    seatsItem.getSeatNo(), seatsItem.getSeatTypeId(), seatsItem.getFare()));
                        }

                    } else {
                        if (bookedSeat.contains(seatsItem.getSeatNo())) {
                            lower_seat_items.add(new Bookedtem(String.valueOf(i), seatsItem.getBerthType(),
                                    seatsItem.getSeatNo(), seatsItem.getSeatTypeId(), seatsItem.getFare()));
                        }/* else if (blockedSeat.contains(seatsItem.getSeatNo())) {
                        items.add(new BlockedItem(String.valueOf(i)));
                    }*/ else {
                            lower_seat_items.add(new AvaliableItem(String.valueOf(i), seatsItem.getBerthType(),
                                    seatsItem.getSeatNo(), seatsItem.getSeatTypeId(), seatsItem.getFare()));
                        }
                    }

                } else {
                    if (rowsItem.size() > (j + 1) && rowsItem.get(j + 1) != null && rowsItem.get(j + 1).getBerthType().equalsIgnoreCase("U")) {
                        upper_seat_items.add(new EmptyItem(String.valueOf(i)));
                    } else if (rowsItem.size() > (j + 1) && rowsItem.get(j + 1) != null && (rowsItem.get(j + 1).getBerthType().equalsIgnoreCase("L") ||
                            rowsItem.get(j + 1).getBerthType().equalsIgnoreCase(""))
                            &&
                            rowsItem.get(j - 1) != null && rowsItem.get(j - 1).getBerthType().equalsIgnoreCase("U")) {
                        LoggerUtil.logItem("EMPTY " + j);
                    } else if (rowsItem.size() > (j + 1) && rowsItem.get(j + 1) != null && (rowsItem.get(j + 1).getBerthType().equalsIgnoreCase("L") ||
                            rowsItem.get(j + 1).getBerthType().equalsIgnoreCase(""))
                            &&
                            rowsItem.get(j - 1) == null) {
                        LoggerUtil.logItem("EMPTY " + j);
                    } else if (rowsItem.size() > (j + 1) && rowsItem.get(j + 1) != null && (rowsItem.get(j + 1).getBerthType().equalsIgnoreCase("L")
                            || rowsItem.get(j + 1).getBerthType().equalsIgnoreCase(""))) {
                        lower_seat_items.add(new EmptyItem(String.valueOf(i)));
                    }
                }
            }
        }

        if (upper_seat_items.size() > 0) {
            COLUMNS = COLUMNS / 2;

            GridLayoutManager manager = new GridLayoutManager(this, COLUMNS);
            upperSeatList.setLayoutManager(manager);

            upperAdapter = new BusSeatAdapter(context, upper_seat_items);
            upperSeatList.setAdapter(upperAdapter);
            txtUpper.setVisibility(View.VISIBLE);
            txtLower.setVisibility(View.VISIBLE);

            txtUpper.setOnClickListener(v -> {

                upperLayout.setVisibility(View.VISIBLE);
                lowerLayout.setVisibility(View.GONE);

                txtLower.setBackgroundResource(R.drawable.border);
                txtLower.setTextColor(ContextCompat.getColor(context, R.color.text_color));

                txtUpper.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
                txtUpper.setTextColor(ContextCompat.getColor(context, R.color.white));


            });

            txtLower.setOnClickListener(v -> {

                upperLayout.setVisibility(View.GONE);
                lowerLayout.setVisibility(View.VISIBLE);

                txtUpper.setBackgroundResource(R.drawable.border);
                txtUpper.setTextColor(ContextCompat.getColor(context, R.color.text_color));

                txtLower.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
                txtLower.setTextColor(ContextCompat.getColor(context, R.color.white));
            });

        }

        if (lower_seat_items.size() > 0) {
            GridLayoutManager manager = new GridLayoutManager(this, COLUMNS);
            seatList.setLayoutManager(manager);
            lowerAdapter = new BusSeatAdapter(this, lower_seat_items);
            seatList.setAdapter(lowerAdapter);

            lowerLayout.setVisibility(View.VISIBLE);
        }

    }
}
