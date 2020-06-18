package com.shoppingbag.utilities.bookedHistory;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shoppingbag.constants.BaseActivity;
import com.shoppingbag.model.bookedHistory.requestModel.PartialCancelPassengerDetailsItem;
import com.shoppingbag.model.bookedHistory.requestModel.PartialCancelTicketDetailsItem;
import com.shoppingbag.model.bookedHistory.requestModel.PartialCancellationInput;
import com.shoppingbag.model.bookedHistory.requestModel.RequestPartialCancel;
import com.shoppingbag.model.bookedHistory.responseModel.CancelPassengerDetailsItem;
import com.shoppingbag.model.bookedHistory.responseModel.CancelTicketDetailsItem;
import com.shoppingbag.model.bookedHistory.responseModel.ResponsePartialCancel;
import com.shoppingbag.utils.LoggerUtil;
import com.shoppingbag.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PartialCancelTicket extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.flightList)
    RecyclerView flightList;
    @BindView(R.id.btn_cancel)
    Button btnCancel;

    private ResponsePartialCancel partialCancel;
    private ArrayList<PartialCancelPassengerDetailsItem> passengerDetailsItem = new ArrayList<>();
    private Gson gson = new GsonBuilder().create();
    private List<CancelPassengerDetailsItem> passengerItem = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.partial_cancel_ticket);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setTitle("Passenger Detail");
        this.setTitle("Passenger Detail");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (getIntent() != null) {

            String response = getIntent().getStringExtra("response");
            partialCancel = gson.fromJson(response, ResponsePartialCancel.class);

            flightList.setLayoutManager(new LinearLayoutManager(this));
            passengerItem = partialCancel.getCancellationOutput()
                    .getCancellationDetails().getPartialCancelPNRDetails().getCancelPassengerDetails();
            PassesngerDetailAdapter detailAdapter = new PassesngerDetailAdapter(this, passengerItem);
            flightList.setAdapter(detailAdapter);
        }

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getList();
            }
        });

    }

    private void getList() {

        passengerDetailsItem = new ArrayList<>();

        for (int j = 0; j < passengerItem.size(); j++) {
            PartialCancelPassengerDetailsItem detailsItem = new PartialCancelPassengerDetailsItem();
            ArrayList<PartialCancelTicketDetailsItem> itemArrayList = new ArrayList<>();

            for (int i = 0; i < passengerItem.get(j).getCancelTicketDetails().size(); i++) {
                CancelTicketDetailsItem cancelTicketDetailsItem = passengerItem.get(j).getCancelTicketDetails().get(i);
                if (cancelTicketDetailsItem.isItemClick()) {
                    PartialCancelTicketDetailsItem item = new PartialCancelTicketDetailsItem();
                    item.setDestination(cancelTicketDetailsItem.getDestination());
                    item.setFlightNumber(cancelTicketDetailsItem.getFlightNumber());
                    item.setOrigin(cancelTicketDetailsItem.getOrigin());
                    item.setSegmentId(cancelTicketDetailsItem.getSegmentId());
                    item.setTicketNumber(cancelTicketDetailsItem.getTicketNumber());
                    itemArrayList.add(item);
                }

            }
            if (itemArrayList.size() > 0) {
                detailsItem.setPaxNumber(passengerItem.get(j).getPaxNumber());
                detailsItem.setPartialCancelTicketDetails(itemArrayList);
                passengerDetailsItem.add(detailsItem);
            }
        }

        if (passengerDetailsItem.size() > 0) {
            getCancelTicket();
        } else {
            showMessage("Please Select Flight for cancel Ticket.");
        }


    }

    private void getCancelTicket() {
        try {
            showLoading();
            RequestPartialCancel requestPartialCancel = new RequestPartialCancel();


//            Authentication authentication = new Authentication();
//            authentication.setLoginId(Utils.USERNAME);
//            authentication.setPassword(Utils.PASSWORD);
//            requestPartialCancel.setAuthentication(authentication);
            PartialCancellationInput cancellationInput = new PartialCancellationInput();
            cancellationInput.setAirlinePNR(partialCancel.getCancellationOutput().getCancellationDetails().getPartialCancelPNRDetails()
                    .getAilinePNR());
            cancellationInput.setCRSPNR(partialCancel.getCancellationOutput().getCancellationDetails().getPartialCancelPNRDetails()
                    .getCRSPNR());
            cancellationInput.setHermesPNR(partialCancel.getCancellationOutput().getCancellationDetails().getPartialCancelPNRDetails()
                    .getHermesPNR());
            cancellationInput.setPartialCancelPassengerDetails(passengerDetailsItem);
            requestPartialCancel.setPartialCancellationInput(cancellationInput);

            String json = gson.toJson(requestPartialCancel);
            JSONObject param = new JSONObject(json);

            LoggerUtil.logItem(param);

            Call<JsonObject> busCall = apiServicesTravel.getPartialCancellation(requestPartialCancel);
            busCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                    hideLoading();
                    LoggerUtil.logItem(response.body());
                    if (response.body().get("ResponseStatus").getAsInt() == 1) {
                        showMessage(response.body().get("PartialCancellationOutput").getAsJsonObject()
                                .get("PartialCancellationDetails").getAsJsonObject()
                                .get("PartialCancellationRemarks").getAsString());

                        finish();

//                            saveCancelticket(response.getJSONObject("PartialCancellationOutput")
//                                    .getJSONObject("PartialCancellationDetails")
//                                    .getString("PartialCancellationRemarks"));
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
