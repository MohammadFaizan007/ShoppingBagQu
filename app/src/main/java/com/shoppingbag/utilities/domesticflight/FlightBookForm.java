package com.shoppingbag.utilities.domesticflight;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.shoppingbag.BuildConfig;
import com.shoppingbag.R;
import com.shoppingbag.app.PreferencesManager;
import com.shoppingbag.common_activities.FullScreenLogin;
import com.shoppingbag.common_activities.MainContainer;
import com.shoppingbag.constants.BaseActivity;
import com.shoppingbag.constants.Cons;
import com.shoppingbag.model.domesticflight.requestmodel.AvailabilityInput;
import com.shoppingbag.model.domesticflight.requestmodel.BookingSegmentsItem;
import com.shoppingbag.model.domesticflight.requestmodel.CustomerDetails;
import com.shoppingbag.model.domesticflight.requestmodel.PassengerDetailsItem;
import com.shoppingbag.model.domesticflight.responsemodel.OngoingFlightsItem;
import com.shoppingbag.model.domesticflight.responsemodel.ResponseGetBookingDetails;
import com.shoppingbag.model.domesticflight.responsemodel.ReturnFlightsItem;
import com.shoppingbag.model.wallet.ResponseWalletBalance;
import com.shoppingbag.one_india_shopping.model.payment_method.ResponsePayMethod;
import com.shoppingbag.retrofit.Dialog_dismiss;
import com.shoppingbag.utils.LoggerUtil;
import com.shoppingbag.utils.Utils;

import org.jetbrains.annotations.NotNull;

import java.security.GeneralSecurityException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FlightBookForm extends BaseActivity implements Dialog_dismiss {

    private final int TRANSACTION_STAT_CODE = 300;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_pay_amount)
    TextView tv_pay_amount;
    @BindView(R.id.btn_make_payment)
    Button btn_make_payment;
    @BindView(R.id.lv_dynamic_names)
    LinearLayout lv_dynamic_names;
    @BindView(R.id.rg_title)
    RadioGroup rg_title;
    @BindView(R.id.name_et)
    TextInputEditText name_et;
    @BindView(R.id.mobile_edt_no)
    TextInputEditText mobile_edt_no;
    @BindView(R.id.email_et)
    TextInputEditText email_et;
    @BindView(R.id.address_et)
    TextInputEditText address_et;
    @BindView(R.id.pin_et)
    TextInputEditText pin_et;
    @BindView(R.id.city_et)
    TextInputEditText city_et;
    float totalFareOngoing = 0;
    float totalFareReturn = 0;
    private AvailabilityInput availabilityInput;
    private String trackId = "";
    private OngoingFlightsItem ongoingFlightsItem;
    private ReturnFlightsItem returnFlightsItem;
    private int adultCount, childCount, infantCount;
    private Dialog addTravellerDialog;
    private int mYear, mMonth, mDay;
    private Calendar cal = Calendar.getInstance();
    private List<PassengerDetailsItem> passengerDetailsOngoingList = new ArrayList<>();
    private List<PassengerDetailsItem> passengerDetailsReturnList = new ArrayList<>();
    private String selectedTitle = "";
    private CustomerDetails customerDetails = new CustomerDetails();
    private String age = "0";
    View.OnClickListener textClickListener = v -> addTravellerDialog((String) v.getTag(R.string.tag), v.getId());

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flight_book_form);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (getIntent() != null) {
            getSupportActionBar().setTitle("Traveller Details");
            availabilityInput = (AvailabilityInput) getIntent().getSerializableExtra("Input");
            adultCount = availabilityInput.getAdultCount();
            childCount = availabilityInput.getChildCount();
            infantCount = availabilityInput.getInfantCount();
            ongoingFlightsItem = (OngoingFlightsItem) getIntent().getSerializableExtra("ongoing");
            if (getIntent().getSerializableExtra("return") != null) {
                returnFlightsItem = (ReturnFlightsItem) getIntent().getSerializableExtra("return");
            }
            totalFareOngoing = getIntent().getFloatExtra("ongoingFare", 0);
            totalFareReturn = getIntent().getFloatExtra("returnFare", 0);

            Log.e("Amount", "ONGOING" + totalFareOngoing + " RETURN= " + totalFareReturn);

            tv_pay_amount.setText(String.format(Locale.getDefault(), "₹%.2f", totalFareReturn + totalFareOngoing));

            createTravellers(adultCount, childCount, infantCount);
            trackId = getIntent().getStringExtra("TrackId");
        }

        rg_title.setOnCheckedChangeListener((radioGroup, i) -> {
            RadioButton radioButton = radioGroup.findViewById(i);
            selectedTitle = String.valueOf(radioButton.getText());
        });

        btn_make_payment.setOnClickListener(view -> {
            if (PreferencesManager.getInstance(context).getUSERID().equalsIgnoreCase("")) {
                FullScreenLogin dialog = new FullScreenLogin();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                dialog.show(ft, FullScreenLogin.TAG);
            } else {
                validateLogin();
            }
        });
    }

    private void getFlightBooK(Double dreamyWallet) {
        showLoading();


        try {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("UserTrackId", trackId);
            jsonObject.addProperty("CompanyId", companyId);
            jsonObject.addProperty("TransType", "Flight");
            jsonObject.addProperty("Origin", getIntent().getStringExtra("Origin"));
            jsonObject.addProperty("Destination", getIntent().getStringExtra("Destination"));
            jsonObject.addProperty("DepartingDate", getIntent().getStringExtra("TravelDate"));
            jsonObject.addProperty("ReturningDate", getIntent().getStringExtra("ReturningDate"));

            jsonObject.addProperty("FK_MemID", Cons.decryptMsg(PreferencesManager.getInstance(context).getUSERID(), cross_intent));

            jsonObject.addProperty("BookingId", System.currentTimeMillis());

            JsonObject bookInput = new JsonObject();
            bookInput.addProperty("AdultCount", adultCount);
            bookInput.addProperty("BookingType", availabilityInput.getBookingType());
            bookInput.addProperty("ChildCount", childCount);
            bookInput.addProperty("InfantCount", infantCount);
            bookInput.addProperty("NotifyByMail", 0);
            bookInput.addProperty("NotifyBySMS", 0);
            bookInput.addProperty("SpecialRemarks", "");
            bookInput.addProperty("TotalAmount", totalFareOngoing + totalFareReturn);
            Log.e("AmountBOOK", "ONGOING" + totalFareOngoing + " RETURN= " + totalFareReturn);


            JsonObject custDetails = new JsonObject();
            custDetails.addProperty("Title", selectedTitle);
            custDetails.addProperty("Name", name_et.getText().toString().trim());
            custDetails.addProperty("Address", address_et.getText().toString().trim());
            custDetails.addProperty("ContactNumber", mobile_edt_no.getText().toString().trim());
            custDetails.addProperty("City", city_et.getText().toString().trim());
            custDetails.addProperty("CountryId", "99");
            custDetails.addProperty("PinCode", pin_et.getText().toString().trim());
            custDetails.addProperty("EmailId", email_et.getText().toString().trim());
            bookInput.add("CustomerDetails", custDetails);


            JsonArray flightBookDetails = new JsonArray();

            JsonObject flightbook = new JsonObject();
            flightbook.addProperty("AirlineCode", ongoingFlightsItem.getAvailSegments().get(0).getAirlineCode());
            flightbook.addProperty("TourCode", "");

            JsonArray passengerDetails = new JsonArray();

            JsonObject payDetails = new JsonObject();
            payDetails.addProperty("Amount", totalFareOngoing + totalFareReturn);
            payDetails.addProperty("CurrencyCode", "INR");


            for (int i = 0; i < adultCount; i++) {

                JsonObject passenger = new JsonObject();
                passenger.addProperty("Age", passengerDetailsOngoingList.get(i).getAge());
                passenger.addProperty("DateofBirth", passengerDetailsOngoingList.get(i).getDateofBirth());
                passenger.addProperty("FirstName", passengerDetailsOngoingList.get(i).getFirstName());
                passenger.addProperty("Gender", passengerDetailsOngoingList.get(i).getGender());
                passenger.addProperty("IdentityProofId", passengerDetailsOngoingList.get(i).getIdentityProofId());
                passenger.addProperty("IdentityProofNumber", passengerDetailsOngoingList.get(i).getIdentityProofNumber());
                passenger.addProperty("LCCBaggageRequest", passengerDetailsOngoingList.get(i).getLCCBaggageRequest());
                passenger.addProperty("LCCMealsRequest", passengerDetailsOngoingList.get(i).getLCCMealsRequest());
                passenger.addProperty("LastName", passengerDetailsOngoingList.get(i).getLastName());
                passenger.addProperty("OtherSSRRequest", passengerDetailsOngoingList.get(i).getOtherSSRRequest());
                passenger.addProperty("PassengerType", "ADULT");
                passenger.addProperty("SeatRequest", passengerDetailsOngoingList.get(i).getSeatRequest());
                passenger.addProperty("Title", passengerDetailsOngoingList.get(i).getTitle());

                JsonArray bookSegments = new JsonArray();

                for (int j = 0; j < ongoingFlightsItem.getAvailSegments().size(); j++) {
                    JsonObject bookSegmentObject = new JsonObject();
                    bookSegmentObject.addProperty("ClassCode", ongoingFlightsItem.getAvailSegments().get(j).getAvailPaxFareDetails().get(0).getClassCode());
                    bookSegmentObject.addProperty("FlightId", ongoingFlightsItem.getAvailSegments().get(j).getFlightId());
                    bookSegmentObject.addProperty("FrequentFlyerId", "");
                    bookSegmentObject.addProperty("FrequentFlyerNumber", "");
                    bookSegmentObject.addProperty("MealCode", "");
                    bookSegmentObject.addProperty("SeatPreferId", "");
                    bookSegmentObject.addProperty("SpecialServiceCode", "");
                    bookSegmentObject.addProperty("SupplierId", "0");
                    bookSegments.add(bookSegmentObject);
                }
                passenger.add("BookingSegments", bookSegments);
                passengerDetails.add(passenger);


                if (returnFlightsItem != null) {

                    JsonObject payDetails2 = new JsonObject();
                    payDetails2.addProperty("Amount", totalFareReturn);
                    payDetails2.addProperty("CurrencyCode", "INR");

                    JsonObject passenger1 = new JsonObject();
                    passenger1.addProperty("Age", passengerDetailsReturnList.get(i).getAge());
                    passenger1.addProperty("DateofBirth", passengerDetailsReturnList.get(i).getDateofBirth());
                    passenger1.addProperty("FirstName", passengerDetailsReturnList.get(i).getFirstName());
                    passenger1.addProperty("Gender", passengerDetailsReturnList.get(i).getGender());
                    passenger1.addProperty("IdentityProofId", passengerDetailsReturnList.get(i).getIdentityProofId());
                    passenger1.addProperty("IdentityProofNumber", passengerDetailsReturnList.get(i).getIdentityProofNumber());
                    passenger1.addProperty("LCCBaggageRequest", passengerDetailsReturnList.get(i).getLCCBaggageRequest());
                    passenger1.addProperty("LCCMealsRequest", passengerDetailsReturnList.get(i).getLCCMealsRequest());
                    passenger1.addProperty("LastName", passengerDetailsReturnList.get(i).getLastName());
                    passenger1.addProperty("OtherSSRRequest", passengerDetailsReturnList.get(i).getOtherSSRRequest());
                    passenger1.addProperty("PassengerType", "ADULT");
                    passenger1.addProperty("SeatRequest", passengerDetailsReturnList.get(i).getSeatRequest());
                    passenger1.addProperty("Title", passengerDetailsReturnList.get(i).getTitle());

                    JsonArray bookSegments1 = new JsonArray();

                    for (int j = 0; j < returnFlightsItem.getAvailSegments().size(); j++) {


                        JsonObject bookSegmentObject = new JsonObject();
                        bookSegmentObject.addProperty("ClassCode", returnFlightsItem.getAvailSegments().get(j).getAvailPaxFareDetails().get(0).getClassCode());
                        bookSegmentObject.addProperty("FlightId", returnFlightsItem.getAvailSegments().get(j).getFlightId());
                        bookSegmentObject.addProperty("FrequentFlyerId", "");
                        bookSegmentObject.addProperty("FrequentFlyerNumber", "");
                        bookSegmentObject.addProperty("MealCode", "");
                        bookSegmentObject.addProperty("SeatPreferId", "");
                        bookSegmentObject.addProperty("SpecialServiceCode", "");
                        bookSegmentObject.addProperty("SupplierId", "0");
                        bookSegments1.add(bookSegmentObject);
                    }
                    passenger1.add("BookingSegments", bookSegments1);

                    passengerDetails.add(passenger1);


                }

            }

            for (int i = adultCount; i < adultCount + childCount; i++) {

                JsonObject passenger = new JsonObject();
                passenger.addProperty("Age", passengerDetailsOngoingList.get(i).getAge());
                passenger.addProperty("DateofBirth", passengerDetailsOngoingList.get(i).getDateofBirth());
                passenger.addProperty("FirstName", passengerDetailsOngoingList.get(i).getFirstName());
                passenger.addProperty("Gender", passengerDetailsOngoingList.get(i).getGender());
                passenger.addProperty("IdentityProofId", passengerDetailsOngoingList.get(i).getIdentityProofId());
                passenger.addProperty("IdentityProofNumber", passengerDetailsOngoingList.get(i).getIdentityProofNumber());
                passenger.addProperty("LCCBaggageRequest", passengerDetailsOngoingList.get(i).getLCCBaggageRequest());
                passenger.addProperty("LCCMealsRequest", passengerDetailsOngoingList.get(i).getLCCMealsRequest());
                passenger.addProperty("LastName", passengerDetailsOngoingList.get(i).getLastName());
                passenger.addProperty("OtherSSRRequest", passengerDetailsOngoingList.get(i).getOtherSSRRequest());
                passenger.addProperty("PassengerType", "CHILD");
                passenger.addProperty("SeatRequest", passengerDetailsOngoingList.get(i).getSeatRequest());
                passenger.addProperty("Title", passengerDetailsOngoingList.get(i).getTitle());


                JsonArray bookSegments = new JsonArray();

                for (int j = 0; j < ongoingFlightsItem.getAvailSegments().size(); j++) {


                    JsonObject bookSegmentObject = new JsonObject();
                    bookSegmentObject.addProperty("ClassCode", ongoingFlightsItem.getAvailSegments().get(j).getAvailPaxFareDetails().get(0).getClassCode());
                    bookSegmentObject.addProperty("FlightId", ongoingFlightsItem.getAvailSegments().get(j).getFlightId());
                    bookSegmentObject.addProperty("FrequentFlyerId", "");
                    bookSegmentObject.addProperty("FrequentFlyerNumber", "");
                    bookSegmentObject.addProperty("MealCode", "");
                    bookSegmentObject.addProperty("SeatPreferId", "");
                    bookSegmentObject.addProperty("SpecialServiceCode", "");
                    bookSegmentObject.addProperty("SupplierId", "0");
                    bookSegments.add(bookSegmentObject);
                }
                passenger.add("BookingSegments", bookSegments);

                passengerDetails.add(passenger);

                if (returnFlightsItem != null) {

                    JsonObject passenger1 = new JsonObject();
                    passenger1.addProperty("Age", passengerDetailsReturnList.get(i).getAge());
                    passenger1.addProperty("DateofBirth", passengerDetailsReturnList.get(i).getDateofBirth());
                    passenger1.addProperty("FirstName", passengerDetailsReturnList.get(i).getFirstName());
                    passenger1.addProperty("Gender", passengerDetailsReturnList.get(i).getGender());
                    passenger1.addProperty("IdentityProofId", passengerDetailsReturnList.get(i).getIdentityProofId());
                    passenger1.addProperty("IdentityProofNumber", passengerDetailsReturnList.get(i).getIdentityProofNumber());
                    passenger1.addProperty("LCCBaggageRequest", passengerDetailsReturnList.get(i).getLCCBaggageRequest());
                    passenger1.addProperty("LCCMealsRequest", passengerDetailsReturnList.get(i).getLCCMealsRequest());
                    passenger1.addProperty("LastName", passengerDetailsReturnList.get(i).getLastName());
                    passenger1.addProperty("OtherSSRRequest", passengerDetailsReturnList.get(i).getOtherSSRRequest());
                    passenger1.addProperty("PassengerType", "CHILD");
                    passenger1.addProperty("SeatRequest", passengerDetailsReturnList.get(i).getSeatRequest());
                    passenger1.addProperty("Title", passengerDetailsReturnList.get(i).getTitle());

                    JsonArray bookSegments1 = new JsonArray();

                    for (int j = 0; j < returnFlightsItem.getAvailSegments().size(); j++) {


                        JsonObject bookSegmentObject = new JsonObject();
                        bookSegmentObject.addProperty("ClassCode", returnFlightsItem.getAvailSegments().get(j).getAvailPaxFareDetails().get(0).getClassCode());
                        bookSegmentObject.addProperty("FlightId", returnFlightsItem.getAvailSegments().get(j).getFlightId());
                        bookSegmentObject.addProperty("FrequentFlyerId", "");
                        bookSegmentObject.addProperty("FrequentFlyerNumber", "");
                        bookSegmentObject.addProperty("MealCode", "");
                        bookSegmentObject.addProperty("SeatPreferId", "");
                        bookSegmentObject.addProperty("SpecialServiceCode", "");
                        bookSegmentObject.addProperty("SupplierId", "0");
                        bookSegments1.add(bookSegmentObject);
                    }
                    passenger1.add("BookingSegments", bookSegments1);

                    passengerDetails.add(passenger1);

                }
            }

            for (int i = adultCount + childCount; i < adultCount + childCount + infantCount; i++) {
                JsonObject passenger = new JsonObject();
                passenger.addProperty("Age", passengerDetailsOngoingList.get(i).getAge());
                passenger.addProperty("DateofBirth", passengerDetailsOngoingList.get(i).getDateofBirth());
                passenger.addProperty("FirstName", passengerDetailsOngoingList.get(i).getFirstName());
                passenger.addProperty("Gender", passengerDetailsOngoingList.get(i).getGender());
                passenger.addProperty("IdentityProofId", passengerDetailsOngoingList.get(i).getIdentityProofId());
                passenger.addProperty("IdentityProofNumber", passengerDetailsOngoingList.get(i).getIdentityProofNumber());
                passenger.addProperty("LCCBaggageRequest", passengerDetailsOngoingList.get(i).getLCCBaggageRequest());
                passenger.addProperty("LCCMealsRequest", passengerDetailsOngoingList.get(i).getLCCMealsRequest());
                passenger.addProperty("LastName", passengerDetailsOngoingList.get(i).getLastName());
                passenger.addProperty("OtherSSRRequest", passengerDetailsOngoingList.get(i).getOtherSSRRequest());
                passenger.addProperty("PassengerType", "INFANT");
                passenger.addProperty("SeatRequest", passengerDetailsOngoingList.get(i).getSeatRequest());
                passenger.addProperty("Title", passengerDetailsOngoingList.get(i).getTitle());


                JsonArray bookSegments = new JsonArray();

                for (int j = 0; j < ongoingFlightsItem.getAvailSegments().size(); j++) {


                    JsonObject bookSegmentObject = new JsonObject();
                    bookSegmentObject.addProperty("ClassCode", ongoingFlightsItem.getAvailSegments().get(j).getAvailPaxFareDetails().get(0).getClassCode());
                    bookSegmentObject.addProperty("FlightId", ongoingFlightsItem.getAvailSegments().get(j).getFlightId());
                    bookSegmentObject.addProperty("FrequentFlyerId", "");
                    bookSegmentObject.addProperty("FrequentFlyerNumber", "");
                    bookSegmentObject.addProperty("MealCode", "");
                    bookSegmentObject.addProperty("SeatPreferId", "");
                    bookSegmentObject.addProperty("SpecialServiceCode", "");
                    bookSegmentObject.addProperty("SupplierId", "0");
                    bookSegments.add(bookSegmentObject);
                }
                passenger.add("BookingSegments", bookSegments);

                passengerDetails.add(passenger);

                if (returnFlightsItem != null) {

                    JsonObject passenger1 = new JsonObject();
                    passenger1.addProperty("Age", passengerDetailsReturnList.get(i).getAge());
                    passenger1.addProperty("DateofBirth", passengerDetailsReturnList.get(i).getDateofBirth());
                    passenger1.addProperty("FirstName", passengerDetailsReturnList.get(i).getFirstName());
                    passenger1.addProperty("Gender", passengerDetailsReturnList.get(i).getGender());
                    passenger1.addProperty("IdentityProofId", passengerDetailsReturnList.get(i).getIdentityProofId());
                    passenger1.addProperty("IdentityProofNumber", passengerDetailsReturnList.get(i).getIdentityProofNumber());
                    passenger1.addProperty("LCCBaggageRequest", passengerDetailsReturnList.get(i).getLCCBaggageRequest());
                    passenger1.addProperty("LCCMealsRequest", passengerDetailsReturnList.get(i).getLCCMealsRequest());
                    passenger1.addProperty("LastName", passengerDetailsReturnList.get(i).getLastName());
                    passenger1.addProperty("OtherSSRRequest", passengerDetailsReturnList.get(i).getOtherSSRRequest());
                    passenger1.addProperty("PassengerType", "INFANT");
                    passenger1.addProperty("SeatRequest", passengerDetailsReturnList.get(i).getSeatRequest());
                    passenger1.addProperty("Title", passengerDetailsReturnList.get(i).getTitle());

                    JsonArray bookSegments1 = new JsonArray();

                    for (int j = 0; j < returnFlightsItem.getAvailSegments().size(); j++) {


                        JsonObject bookSegmentObject = new JsonObject();
                        bookSegmentObject.addProperty("ClassCode", returnFlightsItem.getAvailSegments().get(j).getAvailPaxFareDetails().get(0).getClassCode());
                        bookSegmentObject.addProperty("FlightId", returnFlightsItem.getAvailSegments().get(j).getFlightId());
                        bookSegmentObject.addProperty("FrequentFlyerId", "");
                        bookSegmentObject.addProperty("FrequentFlyerNumber", "");
                        bookSegmentObject.addProperty("MealCode", "");
                        bookSegmentObject.addProperty("SeatPreferId", "");
                        bookSegmentObject.addProperty("SpecialServiceCode", "");
                        bookSegmentObject.addProperty("SupplierId", "0");
                        bookSegments1.add(bookSegmentObject);
                    }
                    passenger1.add("BookingSegments", bookSegments1);

                    passengerDetails.add(passenger1);


                }

            }

            flightbook.add("PassengerDetails", passengerDetails);
            flightbook.add("PaymentDetails", payDetails);
            flightBookDetails.add(flightbook);
            bookInput.add("FlightBookingDetails", flightBookDetails);

            jsonObject.add("BookInput", bookInput);

            LoggerUtil.logItem(jsonObject);

            Call<JsonObject> statusCall = apiServicesTravel.getBookTicketAPI(bodyParam(jsonObject));
            statusCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                    try {
                        hideLoading();
                        LoggerUtil.logItem(response.body());
                        Log.e(">>response", response.toString());

                        LoggerUtil.logItem(call.request().url());
                        String param_ = Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent);
                        Gson gson = new GsonBuilder().create();
                        JsonObject flightBook = gson.fromJson(param_, JsonObject.class);
                        if (flightBook.get("ResponseStatus").getAsInt() != 0) {

//                            Intent in = new Intent(FlightBookForm.this, FlightBookingDetail.class);
//                            in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                            in.putExtra("response",  flightBook.toString());
//                            in.putExtra("input", availabilityInput);
//                            startActivity(in);

                            hitDebitWallet(dreamyWallet, flightBook.toString());
                        } else {
                            flightBook.addProperty("Fk_MemId", PreferencesManager.getInstance(context).getUSERID());
                            finishAffinity();
                            Intent in = new Intent(FlightBookForm.this, MainContainer.class);
                            startActivity(in);
                            showMessage(response.body().get("FailureRemarks").getAsString());
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        showMessage("Something went wrong.");
                    }
                }

                @Override
                public void onFailure(@NotNull Call<JsonObject> call, @NotNull Throwable t) {
                    hideLoading();
                }
            });
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }

    }


    public void createTravellers(int adultCount, int childCount, int infantCount) {
        for (int i = 0; i < adultCount; i++) {
            // create a new textview

            final TextView rowTextView = new TextView(this);
            // set some properties of rowTextView or something
            PassengerDetailsItem passengerDetailsItem = new PassengerDetailsItem();
            passengerDetailsItem.setPassengerType("ADULT");
            passengerDetailsItem.setIdentityProofId("");
            passengerDetailsItem.setIdentityProofNumber("");
            passengerDetailsItem.setLCCBaggageRequest("");
            passengerDetailsItem.setLCCMealsRequest("");
            passengerDetailsItem.setOtherSSRRequest("");
            passengerDetailsItem.setSeatRequest("");

            ArrayList<BookingSegmentsItem> bookingSegmentsItems = new ArrayList<>();

            for (int j = 0; j < ongoingFlightsItem.getAvailSegments().size(); j++) {

                BookingSegmentsItem segmentsItem = new BookingSegmentsItem();
                segmentsItem.setFlightId(ongoingFlightsItem.getAvailSegments().get(j).getFlightId());
                segmentsItem.setClassCode(ongoingFlightsItem.getAvailSegments().get(j).getAvailPaxFareDetails().get(0).getClassCode());
                segmentsItem.setFrequentFlyerId("");
                segmentsItem.setFrequentFlyerNumber("");
                segmentsItem.setMealCode("");
                segmentsItem.setSeatPreferId("");
                segmentsItem.setSpecialServiceCode("");
                segmentsItem.setSupplierId("0");

                bookingSegmentsItems.add(segmentsItem);
            }
            passengerDetailsItem.setBookingSegments(bookingSegmentsItems);
            passengerDetailsOngoingList.add(i, passengerDetailsItem);

            if (returnFlightsItem != null) {

                PassengerDetailsItem passengerDetailsItem1 = new PassengerDetailsItem();
                passengerDetailsItem1.setPassengerType("ADULT");
                passengerDetailsItem1.setIdentityProofId("");
                passengerDetailsItem1.setIdentityProofNumber("");
                passengerDetailsItem1.setLCCBaggageRequest("");
                passengerDetailsItem1.setLCCMealsRequest("");
                passengerDetailsItem1.setOtherSSRRequest("");
                passengerDetailsItem1.setSeatRequest("");

                ArrayList<BookingSegmentsItem> bookingSegmentsItems1 = new ArrayList<>();

                for (int j = 0; j < returnFlightsItem.getAvailSegments().size(); j++) {

                    BookingSegmentsItem segmentsItem = new BookingSegmentsItem();
                    segmentsItem.setFlightId(returnFlightsItem.getAvailSegments().get(j).getFlightId());
                    segmentsItem.setClassCode(returnFlightsItem.getAvailSegments().get(j).getAvailPaxFareDetails().get(0).getClassCode());
                    segmentsItem.setFrequentFlyerId("");
                    segmentsItem.setFrequentFlyerNumber("");
                    segmentsItem.setMealCode("");
                    segmentsItem.setSeatPreferId("");
                    segmentsItem.setSpecialServiceCode("");
                    segmentsItem.setSupplierId("0");

                    bookingSegmentsItems1.add(segmentsItem);
                }
                passengerDetailsItem1.setBookingSegments(bookingSegmentsItems1);


                passengerDetailsReturnList.add(i, passengerDetailsItem1);
            }

            if (passengerDetailsOngoingList.get(i).getFirstName() == null)
                rowTextView.setText(R.string.adult);
            else
                rowTextView.setText(String.format("%s %s", passengerDetailsOngoingList.get(i).getFirstName(), passengerDetailsOngoingList.get(i).getLastName()));

            rowTextView.setPadding(10, 5, 10, 20);
            rowTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.adult, 0, R.drawable.rt_arrow, 0);
            rowTextView.setCompoundDrawablePadding(20);
            rowTextView.setId(i);
            rowTextView.setTag(R.string.tag, "A");
            rowTextView.setClickable(true);
            rowTextView.setOnClickListener(textClickListener);
            // add the textview to the linearlayout
            lv_dynamic_names.addView(rowTextView);
        }

        for (int i = adultCount; i < adultCount + childCount; i++) {
            // create a new textview
            final TextView rowTextView = new TextView(this);
            // set some properties of rowTextView or something
            PassengerDetailsItem passengerDetailsItem = new PassengerDetailsItem();
            passengerDetailsItem.setPassengerType("CHILD");
            passengerDetailsItem.setIdentityProofId("");
            passengerDetailsItem.setIdentityProofNumber("");
            passengerDetailsItem.setLCCBaggageRequest("");
            passengerDetailsItem.setLCCMealsRequest("");
            passengerDetailsItem.setOtherSSRRequest("");
            passengerDetailsItem.setSeatRequest("");


            ArrayList<BookingSegmentsItem> bookingSegmentsItems = new ArrayList<>();

            for (int j = 0; j < ongoingFlightsItem.getAvailSegments().size(); j++) {

                BookingSegmentsItem segmentsItem = new BookingSegmentsItem();
                segmentsItem.setFlightId(ongoingFlightsItem.getAvailSegments().get(j).getFlightId());
                segmentsItem.setClassCode(ongoingFlightsItem.getAvailSegments().get(j).getAvailPaxFareDetails().get(0).getClassCode());
                segmentsItem.setFrequentFlyerId("");
                segmentsItem.setFrequentFlyerNumber("");
                segmentsItem.setMealCode("");
                segmentsItem.setSeatPreferId("");
                segmentsItem.setSpecialServiceCode("");
                segmentsItem.setSupplierId("0");

                bookingSegmentsItems.add(segmentsItem);
            }
            passengerDetailsItem.setBookingSegments(bookingSegmentsItems);


            passengerDetailsOngoingList.add(i, passengerDetailsItem);

            if (returnFlightsItem != null) {

                PassengerDetailsItem passengerDetailsItem1 = new PassengerDetailsItem();
                passengerDetailsItem1.setPassengerType("CHILD");
                passengerDetailsItem1.setIdentityProofId("");
                passengerDetailsItem1.setIdentityProofNumber("");
                passengerDetailsItem1.setLCCBaggageRequest("");
                passengerDetailsItem1.setLCCMealsRequest("");
                passengerDetailsItem1.setOtherSSRRequest("");
                passengerDetailsItem1.setSeatRequest("");

                ArrayList<BookingSegmentsItem> bookingSegmentsItems1 = new ArrayList<>();

                for (int j = 0; j < returnFlightsItem.getAvailSegments().size(); j++) {

                    BookingSegmentsItem segmentsItem = new BookingSegmentsItem();
                    segmentsItem.setFlightId(returnFlightsItem.getAvailSegments().get(j).getFlightId());
                    segmentsItem.setClassCode(returnFlightsItem.getAvailSegments().get(j).getAvailPaxFareDetails().get(0).getClassCode());
                    segmentsItem.setFrequentFlyerId("");
                    segmentsItem.setFrequentFlyerNumber("");
                    segmentsItem.setMealCode("");
                    segmentsItem.setSeatPreferId("");
                    segmentsItem.setSpecialServiceCode("");
                    segmentsItem.setSupplierId("0");

                    bookingSegmentsItems1.add(segmentsItem);
                }
                passengerDetailsItem1.setBookingSegments(bookingSegmentsItems1);


                passengerDetailsReturnList.add(i, passengerDetailsItem1);
            }

            if (passengerDetailsOngoingList.get(i).getFirstName() == null)
                rowTextView.setText(R.string.child);
            else
                rowTextView.setText(String.format("%s %s", passengerDetailsOngoingList.get(i).getFirstName(), passengerDetailsOngoingList.get(i).getLastName()));
            rowTextView.setPadding(10, 5, 10, 20);
            rowTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.child, 0, R.drawable.rt_arrow, 0);
            rowTextView.setCompoundDrawablePadding(20);
            rowTextView.setId(i);
            rowTextView.setTag(R.string.tag, "C");
            rowTextView.setClickable(true);
            rowTextView.setOnClickListener(textClickListener);
            // add the textview to the linearlayout
            lv_dynamic_names.addView(rowTextView);
        }

        for (int i = adultCount + childCount; i < adultCount + childCount + infantCount; i++) {
            // create a new textview
            final TextView rowTextView = new TextView(this);
            // set some properties of rowTextView or something
            PassengerDetailsItem passengerDetailsItem = new PassengerDetailsItem();
            passengerDetailsItem.setPassengerType("INFANT");
            passengerDetailsItem.setIdentityProofId("");
            passengerDetailsItem.setIdentityProofNumber("");
            passengerDetailsItem.setLCCBaggageRequest("");
            passengerDetailsItem.setLCCMealsRequest("");
            passengerDetailsItem.setOtherSSRRequest("");
            passengerDetailsItem.setSeatRequest("");


            ArrayList<BookingSegmentsItem> bookingSegmentsItems = new ArrayList<>();

            for (int j = 0; j < ongoingFlightsItem.getAvailSegments().size(); j++) {

                BookingSegmentsItem segmentsItem = new BookingSegmentsItem();
                segmentsItem.setFlightId(ongoingFlightsItem.getAvailSegments().get(j).getFlightId());
                segmentsItem.setClassCode(ongoingFlightsItem.getAvailSegments().get(j).getAvailPaxFareDetails().get(0).getClassCode());
                segmentsItem.setFrequentFlyerId("");
                segmentsItem.setFrequentFlyerNumber("");
                segmentsItem.setMealCode("");
                segmentsItem.setSeatPreferId("");
                segmentsItem.setSpecialServiceCode("");
                segmentsItem.setSupplierId("0");

                bookingSegmentsItems.add(segmentsItem);
            }
            passengerDetailsItem.setBookingSegments(bookingSegmentsItems);


            passengerDetailsOngoingList.add(i, passengerDetailsItem);
            if (returnFlightsItem != null) {

                PassengerDetailsItem passengerDetailsItem1 = new PassengerDetailsItem();
                passengerDetailsItem1.setPassengerType("INFANT");
                passengerDetailsItem1.setIdentityProofId("");
                passengerDetailsItem1.setIdentityProofNumber("");
                passengerDetailsItem1.setLCCBaggageRequest("");
                passengerDetailsItem1.setLCCMealsRequest("");
                passengerDetailsItem1.setOtherSSRRequest("");
                passengerDetailsItem1.setSeatRequest("");

                ArrayList<BookingSegmentsItem> bookingSegmentsItems1 = new ArrayList<>();

                for (int j = 0; j < returnFlightsItem.getAvailSegments().size(); j++) {

                    BookingSegmentsItem segmentsItem = new BookingSegmentsItem();
                    segmentsItem.setFlightId(returnFlightsItem.getAvailSegments().get(j).getFlightId());
                    segmentsItem.setClassCode(returnFlightsItem.getAvailSegments().get(j).getAvailPaxFareDetails().get(0).getClassCode());
                    segmentsItem.setFrequentFlyerId("");
                    segmentsItem.setFrequentFlyerNumber("");
                    segmentsItem.setMealCode("");
                    segmentsItem.setSeatPreferId("");
                    segmentsItem.setSpecialServiceCode("");
                    segmentsItem.setSupplierId("0");

                    bookingSegmentsItems1.add(segmentsItem);
                }
                passengerDetailsItem1.setBookingSegments(bookingSegmentsItems1);


                passengerDetailsReturnList.add(i, passengerDetailsItem1);
            }

            if (passengerDetailsOngoingList.get(i).getFirstName() == null)
                rowTextView.setText(R.string.infant);
            else
                rowTextView.setText(String.format("%s %s", passengerDetailsOngoingList.get(i).getFirstName(), passengerDetailsOngoingList.get(i).getLastName()));
            rowTextView.setPadding(10, 5, 10, 20);
            rowTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.infant, 0, R.drawable.rt_arrow, 0);
            rowTextView.setCompoundDrawablePadding(20);
            rowTextView.setId(i);
            rowTextView.setTag(R.string.tag, "I");
            rowTextView.setClickable(true);
            rowTextView.setOnClickListener(textClickListener);
            // add the textview to the linearlayout
            lv_dynamic_names.addView(rowTextView);
        }
    }

    private void addTravellerDialog(final String tag, final int position) {
        addTravellerDialog = new Dialog(FlightBookForm.this, R.style.DialogTheme);
        addTravellerDialog.setCanceledOnTouchOutside(true);
        addTravellerDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        addTravellerDialog.setContentView(R.layout.dialog_add_passenger);
        addTravellerDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);

        final String[] selectedTitle = {""};
        final String[] selectedGender = {""};
        RadioGroup rg_title = addTravellerDialog.findViewById(R.id.rg_title);
        final TextInputEditText first_name_et = addTravellerDialog.findViewById(R.id.first_name_et);
        final TextInputEditText last_name_et = addTravellerDialog.findViewById(R.id.last_name_et);
        final TextInputEditText dob_et = addTravellerDialog.findViewById(R.id.dob_et);
        final TextInputLayout input_dob = addTravellerDialog.findViewById(R.id.input_dob);
        dob_et.setKeyListener(null);
        ImageButton btn_add = addTravellerDialog.findViewById(R.id.btn_add);

        //final int position = Integer.parseInt(String.valueOf(pos));
        final String type = String.valueOf(tag);

        if (type.equalsIgnoreCase("A") || type.equalsIgnoreCase("C")) {
            dob_et.setVisibility(View.GONE);
        } else {
            input_dob.setVisibility(View.VISIBLE);
            dob_et.setVisibility(View.VISIBLE);
        }

        if (passengerDetailsOngoingList.get(position).getFirstName() != null) {
            first_name_et.setText(passengerDetailsOngoingList.get(position).getFirstName());
            last_name_et.setText(passengerDetailsOngoingList.get(position).getLastName());
            dob_et.setText(passengerDetailsOngoingList.get(position).getDateofBirth());
            age = String.valueOf(passengerDetailsOngoingList.get(position).getAge());

            switch (passengerDetailsOngoingList.get(position).getTitle()) {
                case "Mr.":
                    rg_title.check(R.id.rb_mr);
                    selectedTitle[0] = "Mr.";
                    break;
                case "Mrs.":
                    rg_title.check(R.id.rb_mrs);
                    selectedTitle[0] = "Mrs.";
                    break;
                default:
                    rg_title.check(R.id.rb_miss);
                    selectedTitle[0] = "Miss.";
                    break;
            }
        }

        dob_et.setOnClickListener(view -> {
            mYear = cal.get(Calendar.YEAR);
            mMonth = cal.get(Calendar.MONTH);
            mDay = cal.get(Calendar.DAY_OF_MONTH);
            datePicker(dob_et, type);
        });

        rg_title.setOnCheckedChangeListener((radioGroup, i) -> {
            RadioButton radioButton = radioGroup.findViewById(i);
            selectedTitle[0] = String.valueOf(radioButton.getText());
            if (selectedTitle[0].equalsIgnoreCase("Mr."))
                selectedGender[0] = "M";
            else
                selectedGender[0] = "F";
        });

        btn_add.setOnClickListener(view -> {
            if (selectedTitle[0].equals(""))
                showMessage("Please select title");
            else if (first_name_et.getText().toString().trim().length() < 3)
                first_name_et.setError("Please enter first name");
            else if (last_name_et.getText().toString().trim().length() < 3)
                last_name_et.setError("Please enter last name");
            else if (input_dob.isShown() && dob_et.getText().toString().trim().length() < 3)
                dob_et.setError("Please enter dob");
            else {
                PassengerDetailsItem detailsItem = passengerDetailsOngoingList.get(position);
                detailsItem.setFirstName(first_name_et.getText().toString().trim());
                detailsItem.setLastName(last_name_et.getText().toString().trim());
                detailsItem.setTitle(selectedTitle[0]);
                detailsItem.setGender(selectedGender[0]);
                detailsItem.setDateofBirth(dob_et.getText().toString().trim());
                detailsItem.setAge(Integer.parseInt(age));

                if (returnFlightsItem != null) {
                    PassengerDetailsItem detailsItem1 = passengerDetailsReturnList.get(position);
                    detailsItem1.setFirstName(first_name_et.getText().toString().trim());
                    detailsItem1.setLastName(last_name_et.getText().toString().trim());
                    detailsItem1.setTitle(selectedTitle[0]);
                    detailsItem1.setGender(selectedGender[0]);
                    detailsItem1.setDateofBirth(dob_et.getText().toString().trim());
                    detailsItem1.setAge(Integer.parseInt(age));
                }
                UpdateTravellers(adultCount, childCount, infantCount);
                addTravellerDialog.dismiss();
            }
        });

        addTravellerDialog.setCancelable(true);
        addTravellerDialog.show();
    }

    private void datePicker(final TextInputEditText et, final String type) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(FlightBookForm.this,
                (view, year, monthOfYear, dayOfMonth) -> {
                    switch (type) {
                        case "C":
                            if (Integer.parseInt(getAge(year, monthOfYear + 1, dayOfMonth)) > 12 ||
                                    Integer.parseInt(getAge(year, monthOfYear + 1, dayOfMonth)) < 2) {
                                showMessage("Passenger's age could not be greater than 12 and less than 2");
                            } else {
                                age = getAge(year, monthOfYear + 1, dayOfMonth);
                                et.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year);
                            }
                            break;
                        case "I":
                            if (Integer.parseInt(getAge(year, monthOfYear + 1, dayOfMonth)) > 2)
                                showMessage("Passenger's age could not be greater than 2");
                            else {
                                age = getAge(year, monthOfYear + 1, dayOfMonth);
                                et.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year);
                            }
                            break;
                        case "A":
                            if (Integer.parseInt(getAge(year, monthOfYear + 1, dayOfMonth)) < 12)
                                showMessage("Passenger's age should be greater than 12");
                            else {
                                age = getAge(year, monthOfYear + 1, dayOfMonth);
                                et.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year);
                            }
                            break;
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMaxDate(cal.getTimeInMillis());
        datePickerDialog.show();
    }

    public void UpdateTravellers(int adultCount, int childCount, int infantCount) {
        lv_dynamic_names.removeAllViews();
        for (int i = 0; i < adultCount; i++) {
            // create a new textview
            final TextView rowTextView = new TextView(this);
            // set some properties of rowTextView or something
            if (passengerDetailsOngoingList.get(i).getFirstName() == null)
                rowTextView.setText(R.string.adult);
            else
                rowTextView.setText(String.format("%s %s", passengerDetailsOngoingList.get(i).getFirstName(), passengerDetailsOngoingList.get(i).getLastName()));

            rowTextView.setPadding(10, 5, 10, 20);
            rowTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.adult, 0, R.drawable.rt_arrow, 0);
            rowTextView.setCompoundDrawablePadding(20);
            rowTextView.setId(i);
            rowTextView.setTag(R.string.tag, "A");
            rowTextView.setClickable(true);
            rowTextView.setOnClickListener(textClickListener);
            // add the textview to the linearlayout
            lv_dynamic_names.addView(rowTextView);
        }

        for (int i = adultCount; i < adultCount + childCount; i++) {
            // create a new textview
            final TextView rowTextView = new TextView(this);
            // set some properties of rowTextView or something
            if (passengerDetailsOngoingList.get(i).getFirstName() == null)
                rowTextView.setText(R.string.child);
            else
                rowTextView.setText(String.format("%s %s", passengerDetailsOngoingList.get(i).getFirstName(), passengerDetailsOngoingList.get(i).getLastName()));
            rowTextView.setPadding(10, 5, 10, 20);
            rowTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.child, 0, R.drawable.rt_arrow, 0);
            rowTextView.setCompoundDrawablePadding(20);
            rowTextView.setId(i);
            rowTextView.setTag(R.string.tag, "C");
            rowTextView.setClickable(true);
            rowTextView.setOnClickListener(textClickListener);
            // add the textview to the linearlayout
            lv_dynamic_names.addView(rowTextView);
        }

        for (int i = adultCount + childCount; i < adultCount + childCount + infantCount; i++) {
            // create a new textview
            final TextView rowTextView = new TextView(this);
            // set some properties of rowTextView or something
            if (passengerDetailsOngoingList.get(i).getFirstName() == null)
                rowTextView.setText(R.string.infant);
            else
                rowTextView.setText(String.format("%s %s", passengerDetailsOngoingList.get(i).getFirstName(), passengerDetailsOngoingList.get(i).getLastName()));
            rowTextView.setPadding(10, 5, 10, 20);
            rowTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.infant, 0, R.drawable.rt_arrow, 0);
            rowTextView.setCompoundDrawablePadding(20);
            rowTextView.setId(i);
            rowTextView.setTag(R.string.tag, "I");
            rowTextView.setClickable(true);
            rowTextView.setOnClickListener(textClickListener);
            // add the textview to the linearlayout
            lv_dynamic_names.addView(rowTextView);
        }
    }

    private String getAge(int year, int month, int day) {
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();
        dob.set(year, month, day);
        int ageInt = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
        return Integer.toString(ageInt);
    }

    @Override
    public void onDismiss() {
        validateLogin();
    }

    void validateLogin() {
        if (selectedTitle.equals(""))
            showMessage("Please select title");
        else if (name_et.getText().toString().trim().length() < 3)
            name_et.setError("Please enter name");
        else if (mobile_edt_no.getText().toString().trim().length() < 10)
            mobile_edt_no.setError("Please enter valid mobile number");
        else if (email_et.getText().toString().trim().length() == 0)
            email_et.setError("Please enter email id");
        else if (!Utils.isEmailAddress(email_et.getText().toString().trim()))
            email_et.setError("Please enter valid email id");
        else if (address_et.getText().toString().trim().length() < 1)
            address_et.setError("Please enter address");
        else if (pin_et.getText().toString().trim().length() < 6)
            pin_et.setError("Please enter PIN");
        else if (city_et.getText().toString().trim().length() < 3)
            city_et.setError("Please enter city");
        else if (passengerDetailsOngoingList.get(0).getFirstName() == null) {
            showMessage("Please fill traveller details.");
        } else {
            customerDetails.setAddress(address_et.getText().toString().trim());
            customerDetails.setCity(city_et.getText().toString().trim());
            customerDetails.setContactNumber(mobile_edt_no.getText().toString().trim());
            customerDetails.setEmailId(email_et.getText().toString().trim());
            customerDetails.setName(name_et.getText().toString().trim());
            customerDetails.setTitle(selectedTitle);
            customerDetails.setPinCode(pin_et.getText().toString().trim());
            customerDetails.setCountryId("99");

            getWalletBalance(String.valueOf(totalFareOngoing + totalFareReturn));

        }
    }

    private Double DreamywalletBalance = 0.0;
    private static DecimalFormat format = new DecimalFormat("0.00");


    private void getWalletBalance(String totalAmount) {
        showLoading();
        String url = BuildConfig.BASE_URL_MLM + "GetWallet?MemberId=" + PreferencesManager.getInstance(context).getUSERID();
        Call<JsonObject> call = apiServices.getBankName(url, PreferencesManager.getInstance(context).getANDROIDID());
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                try {
                    hideLoading();
                    LoggerUtil.logItem(response.body());
                    LoggerUtil.logItem(response.code());
                    if (response.isSuccessful()) {
                        ResponseWalletBalance responseWalletBalance = new Gson().fromJson(Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent), ResponseWalletBalance.class);
                        if (response.body() != null && responseWalletBalance.getResponse().equalsIgnoreCase("Success")) {
                            DreamywalletBalance = responseWalletBalance.getResult().getDreamyWalletAmount();
                            if (!TextUtils.isEmpty(totalAmount)) {
                                double amount = Double.parseDouble(totalAmount);
                                if (DreamywalletBalance >= amount) {
                                    getFlightBooK(amount);
                                } else {
                                    showMessage("Please Add Amount in Dreamy Wallet");
                                }
                            }

                        }
                    }
                } catch (Exception e) {
                    hideLoading();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NotNull Call<JsonObject> call, @NotNull Throwable t) {
                hideLoading();
            }
        });
    }


    private void hitDebitWallet(Double dreamyWallet, String responseStr) {
        try {
            ResponseGetBookingDetails dtls;
            Gson gson = new GsonBuilder().create();
            dtls = gson.fromJson(String.valueOf(responseStr), ResponseGetBookingDetails.class);
            showLoading();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("type", "Flight");
            jsonObject.addProperty("transactiondate", Utils.getTodayDate());
            jsonObject.addProperty("transactionId", dtls.getBookOutput().getFlightTicketDetails().get(0).getTransactionId());
            jsonObject.addProperty("mainamount", String.valueOf(format.format(dreamyWallet)));
            jsonObject.addProperty("walletamount", "0");
            jsonObject.addProperty("paymentgatewayamount", "0");
            jsonObject.addProperty("memberId", Cons.decryptMsg(PreferencesManager.getInstance(context).getUSERID(), cross_intent));

            LoggerUtil.logItem(jsonObject);
            Call<JsonObject> jsonObjectCall = apiServices.executeDebitWallet(bodyParam(jsonObject), PreferencesManager.getInstance(context).getANDROIDID());
            jsonObjectCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                    hideLoading();
                    LoggerUtil.logItem(response.body());
                    LoggerUtil.logItem(response.code());
                    try {
                        if (response.isSuccessful()) {
                            String param = Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent);
                            Gson gson = new GsonBuilder().create();
                            ResponsePayMethod addAdress = gson.fromJson(param, ResponsePayMethod.class);
                            if (addAdress.getResponse().equalsIgnoreCase("Success")) {
                                Intent in = new Intent(FlightBookForm.this, FlightBookingDetail.class);
                                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                in.putExtra("response", responseStr);
                                in.putExtra("input", availabilityInput);
                                startActivity(in);
                            }
                        } else {
                            showMessage("something went wrong");
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<JsonObject> call, @NotNull Throwable t) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
