package com.shoppingbag.utilities.bus;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.shoppingbag.BuildConfig;
import com.shoppingbag.R;
import com.shoppingbag.app.PreferencesManager;
import com.shoppingbag.constants.BaseActivity;
import com.shoppingbag.constants.Cons;
import com.shoppingbag.model.response.bus_response.requestpojo.BlockingInput;
import com.shoppingbag.model.response.bus_response.requestpojo.BookingCustomerDetails;
import com.shoppingbag.model.response.bus_response.requestpojo.BookingDetails;
import com.shoppingbag.model.response.bus_response.requestpojo.PassengerListItem;
import com.shoppingbag.model.response.bus_response.requestpojo.RequestGetSeatBlock;
import com.shoppingbag.model.seat_book_response.ResponseSeatBooked;
import com.shoppingbag.model.wallet.ResponseWalletBalance;
import com.shoppingbag.one_india_shopping.model.payment_method.ResponsePayMethod;
import com.shoppingbag.utils.LoggerUtil;
import com.shoppingbag.utils.Utils;

import org.jetbrains.annotations.NotNull;

import java.security.GeneralSecurityException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.shoppingbag.app.AppConfig.PAYLOAD_BUNDLE;

public class BusBookingForm extends BaseActivity {


    @BindView(R.id.side_menu)
    ImageView sideMenu;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.lv_dynamic_names)
    LinearLayout lvDynamicNames;
    @BindView(R.id.cv_passenger_information)
    CardView cvPassengerInformation;
    @BindView(R.id.rb_mr)
    RadioButton rbMr;
    @BindView(R.id.rb_mrs)
    RadioButton rbMrs;
    @BindView(R.id.rb_miss)
    RadioButton rbMiss;
    @BindView(R.id.rg_title)
    RadioGroup rgTitle;
    @BindView(R.id.name_et)
    TextInputEditText nameEt;
    @BindView(R.id.mobile_input_name)
    TextInputLayout mobileInputName;
    @BindView(R.id.mobile_edt_no)
    TextInputEditText mobileEdtNo;
    @BindView(R.id.mobile_input_mobileno)
    TextInputLayout mobileInputMobileno;
    @BindView(R.id.email_et)
    TextInputEditText emailEt;
    @BindView(R.id.mobile_input_email)
    TextInputLayout mobileInputEmail;
    @BindView(R.id.address_et)
    TextInputEditText addressEt;
    @BindView(R.id.mobile_input_address)
    TextInputLayout mobileInputAddress;
    @BindView(R.id.city_et)
    TextInputEditText cityEt;
    @BindView(R.id.input_city)
    TextInputLayout inputCity;
    @BindView(R.id.country_et)
    TextInputEditText countryEt;
    @BindView(R.id.input_country)
    TextInputLayout inputCountry;
    @BindView(R.id.idProof_et)
    TextInputEditText idProofEt;
    @BindView(R.id.input_idproof)
    TextInputLayout inputIdproof;
    @BindView(R.id.idProofNumber_et)
    TextInputEditText idProofNumberEt;
    @BindView(R.id.input_idProofNumber)
    TextInputLayout inputIdProofNumber;
    @BindView(R.id.cv_user_details)
    CardView cvUserDetails;
    @BindView(R.id.tv_pay_amount)
    TextView tvPayAmount;
    @BindView(R.id.btn_make_payment)
    Button btnMakePayment;
    Bundle param;
    String userTrackId, scheduleId, stationId, transportId, dropId, bordingId, travelDate, originName, destinationName;
    private String selectedTitle = "", idProofValue_st = "";
    private List<EditText> etNameList = new ArrayList<>();
    private List<EditText> etAgeList = new ArrayList<>();
    private List<EditText> etGenderList = new ArrayList<>();
    private ArrayList<PassengerListItem> passengerList = new ArrayList<>();
    private Double DreamywalletBalance = 0.0;
    private static DecimalFormat format = new DecimalFormat("0.00");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bus_booking_form);
        ButterKnife.bind(this);

        param = getIntent().getBundleExtra(PAYLOAD_BUNDLE);
        dropId = param.getString("dropId");
        bordingId = param.getString("bordingId");
        travelDate = param.getString("travelDate");

        userTrackId = param.getString("userTrackId");
        scheduleId = param.getString("scheduleId");
        stationId = param.getString("stationId");
        transportId = param.getString("transportId");

        originName = param.getString("originName");
        destinationName = param.getString("destinationName");
        passengerList = (ArrayList<PassengerListItem>) param.getSerializable("total_sheet");

        title.setText(String.format("%s - %s", originName, destinationName));

        rgTitle.setOnCheckedChangeListener((radioGroup, i) -> {
            RadioButton radioButton = radioGroup.findViewById(i);
            selectedTitle = String.valueOf(radioButton.getText());
        });

        if (passengerList != null && passengerList.size() > 0)
            createPassengers(passengerList.size());
        else
            showMessage("Something went wrong.");
    }

    public void createPassengers(int count) {
        double totalAmount = 0;
        for (int i = 0; i < count; i++) {

            totalAmount = totalAmount + passengerList.get(i).getFare();

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(16, 5, 16, 0);
            LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params2.setMargins(0, 25, 0, 0);
            LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params3.setMargins(16, 5, 16, 25);
            LinearLayout.LayoutParams params4 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params4.setMargins(16, 30, 16, 0);
            TextView rowTextView = new TextView(this);
            rowTextView.setTextColor(getResources().getColor(R.color.text_color));
            rowTextView.setTypeface(null, Typeface.BOLD);

            if (i == 0) {
                rowTextView.setLayoutParams(params);
                rowTextView.setText("Primary Passenger");
            } else {
                rowTextView.setLayoutParams(params4);
                rowTextView.setText("Co-Passenger " + i);
            }
            lvDynamicNames.addView(rowTextView);

            TextInputLayout textInputLayoutName = new TextInputLayout(this);
            textInputLayoutName.setLayoutParams(params);
            textInputLayoutName.setHint("Name");
            TextInputEditText editTextName = new TextInputEditText(textInputLayoutName.getContext());
            etNameList.add(editTextName);
            editTextName.setId(i);
            editTextName.setMaxLines(1);
            editTextName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f);
            textInputLayoutName.addView(editTextName);
            DrawableCompat.setTint(editTextName.getBackground(), ContextCompat.getColor(context, R.color.colorPrimary));

            TextInputLayout textInputLayoutGender = new TextInputLayout(this);
            textInputLayoutGender.setLayoutParams(params);
            textInputLayoutGender.setHint("Select Gender");
            TextInputEditText editTextGender = new TextInputEditText(textInputLayoutGender.getContext());
            etGenderList.add(editTextGender);
            editTextGender.setClickable(false);
            editTextGender.setCursorVisible(false);
            editTextGender.setFocusable(false);
            editTextGender.setFocusableInTouchMode(false);
            editTextGender.setId(i);
            editTextGender.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f);
            textInputLayoutGender.addView(editTextGender);
            DrawableCompat.setTint(editTextGender.getBackground(), ContextCompat.getColor(context, R.color.colorPrimary));


            TextInputLayout textInputLayoutAge = new TextInputLayout(this);
            textInputLayoutAge.setLayoutParams(params3);
            textInputLayoutAge.setHint("Age");
            TextInputEditText editTextAge = new TextInputEditText(textInputLayoutAge.getContext());
            etAgeList.add(editTextAge);
            editTextAge.setInputType(InputType.TYPE_CLASS_NUMBER);
            editTextAge.setId(i);
            editTextAge.setFilters(new InputFilter[]{new InputFilter.LengthFilter(2)});
            editTextAge.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f);
            textInputLayoutAge.addView(editTextAge);
            DrawableCompat.setTint(editTextAge.getBackground(), ContextCompat.getColor(context, R.color.colorPrimary));

            View view = new View(this);
            view.setBackgroundColor(getResources().getColor(R.color.text_color));
            view.setLayoutParams(params2);

            lvDynamicNames.addView(textInputLayoutName);
            lvDynamicNames.addView(textInputLayoutGender);
            lvDynamicNames.addView(textInputLayoutAge);
            if (i < count - 1) {
                lvDynamicNames.addView(view);
            }

            editTextGender.setOnClickListener(view1 -> {
                PopupMenu popupcountry = new PopupMenu(context, textInputLayoutGender);
                popupcountry.getMenuInflater().inflate(R.menu.menu_gender, popupcountry.getMenu());
                popupcountry.setOnMenuItemClickListener(item -> {
                    try {
                        textInputLayoutGender.getEditText().setText(item.getTitle());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return true;
                });
                popupcountry.show();
            });
        }
        tvPayAmount.setText(String.valueOf(totalAmount));

    }

    @OnClick({R.id.side_menu, R.id.country_et, R.id.idProof_et, R.id.btn_make_payment})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.side_menu:
                onBackPressed();
                break;
            case R.id.country_et:
                PopupMenu popupcountry = new PopupMenu(context, countryEt);
                popupcountry.getMenuInflater().inflate(R.menu.menu_country, popupcountry.getMenu());
                popupcountry.setOnMenuItemClickListener(item -> {
                    try {
                        countryEt.setText(item.getTitle());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return true;
                });
                popupcountry.show();
                break;
            case R.id.idProof_et:
                PopupMenu popupIdType = new PopupMenu(context, idProofEt);
                popupIdType.getMenuInflater().inflate(R.menu.menu_idproof_type, popupIdType.getMenu());
                popupIdType.setOnMenuItemClickListener(item -> {
                    try {
                        idProofEt.setText(item.getTitle());
                        if (item.getTitle().toString().equalsIgnoreCase("Passport")) {
                            idProofValue_st = "1";
                        } else if (item.getTitle().toString().equalsIgnoreCase("VoterId")) {
                            idProofValue_st = "3";
                        } else if (item.getTitle().toString().equalsIgnoreCase("Driving License")) {
                            idProofValue_st = "4";
                        } else if (item.getTitle().toString().equalsIgnoreCase("PAN Card")) {
                            idProofValue_st = "5";
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return true;
                });
                popupIdType.show();
                break;
            case R.id.btn_make_payment:
                if (Validation()) {
                    double totalAmount = 0;
                    for (int i = 0; i < etNameList.size(); i++) {
                        totalAmount = totalAmount + passengerList.get(i).getFare();
                    }
//                    getSeatBlock();
                    getWalletBalance(String.valueOf(totalAmount));
                }
                break;
        }
    }

    private void getSeatBlock() {

        double totalAmount = 0;
        for (int i = 0; i < etNameList.size(); i++) {
            LoggerUtil.logItem(etNameList.get(i).getText().toString());
            LoggerUtil.logItem(etAgeList.get(i).getText().toString());
            LoggerUtil.logItem(etGenderList.get(i).getText().toString());
            passengerList.get(i).setPassengerName(etNameList.get(i).getText().toString());
            passengerList.get(i).setAge(Integer.parseInt(etAgeList.get(i).getText().toString()));
            if (etGenderList.get(i).getText().toString().equalsIgnoreCase("Male")) {
                passengerList.get(i).setGender("M");
            } else {
                passengerList.get(i).setGender("F");
            }
            passengerList.get(i).setBoardingId(bordingId);
            passengerList.get(i).setDroppingId(dropId);
            totalAmount = totalAmount + passengerList.get(i).getFare();
        }
        BookingCustomerDetails bookingCustomerDetails = new BookingCustomerDetails();

        bookingCustomerDetails.setTitle(selectedTitle);
        bookingCustomerDetails.setName(nameEt.getText().toString().trim());
        bookingCustomerDetails.setAddress(addressEt.getText().toString().trim());
        bookingCustomerDetails.setContactNumber(mobileEdtNo.getText().toString().trim());
        bookingCustomerDetails.setCity(cityEt.getText().toString().trim());
        bookingCustomerDetails.setCountryId("91");
        bookingCustomerDetails.setEmailId(emailEt.getText().toString().trim());
        bookingCustomerDetails.setIdProofId(idProofValue_st);
        bookingCustomerDetails.setIdProofNumber(idProofNumberEt.getText().toString());

        BookingDetails bookingDetails = new BookingDetails();
        bookingDetails.setPassengerList(passengerList);
        bookingDetails.setScheduleId(scheduleId);
        bookingDetails.setStationId(stationId);
        bookingDetails.setTotalAmount(totalAmount);  // GET TOTAL AMOUNT.....
        bookingDetails.setTotalTickets(passengerList.size());
        bookingDetails.setTransportId(Integer.parseInt(transportId));
        bookingDetails.setTravelDate(travelDate);

        BlockingInput blockingInput = new BlockingInput();
        blockingInput.setBookingCustomerDetails(bookingCustomerDetails);
        blockingInput.setBookingDetails(bookingDetails);

        RequestGetSeatBlock seatBlock = new RequestGetSeatBlock();
        seatBlock.setFKMemID(PreferencesManager.getInstance(context).getUSERID());
        seatBlock.setUserTrackId(userTrackId);

        LoggerUtil.logItem(seatBlock);


        showLoading();

        Call<JsonObject> objectCall = apiServicesTravel.getSeatBlock(seatBlock);
        objectCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                hideLoading();
//                {
//                    "ResponseStatus": 1,
//                        "UserTrackId": "RMNZZ97799999989907984974966182",
//                        "BlockingOutput":
//                    {
//                        "BlockingStatus": 1,
//                            "Remarks": "SUCCESSFULLLY BLOCKED"
//                    }
//                }
                if (response.body() != null && response.body().get("ResponseStatus").getAsString().equalsIgnoreCase("1")) {
                    //getSeatBooked();
                } else {
                    showMessage("Seat Blocked Failed..");
                }
            }

            @Override
            public void onFailure(@NotNull Call<JsonObject> call, @NotNull Throwable t) {

            }
        });


    }

    private boolean Validation() {
        try {
//            if (passenger == null) {
//                showMessage("Please enter passenger name & age");
//                return false;
//            } else

            if (etNameList != null) {
                for (int i = 0; i < etNameList.size(); i++) {
                    if (etNameList.get(i).getText().toString().equalsIgnoreCase("")) {
                        if (i == 0) {
                            showMessage("Please enter Primary Passenger name");
                            return false;
                        } else {
                            showMessage("Please enter Co-Passenger " + i + " name");
                            return false;
                        }
                    } else if (etGenderList.get(i).getText().toString().equalsIgnoreCase("")) {
                        if (i == 0) {
                            showMessage("Please select Primary Passenger gender");
                            return false;
                        } else {
                            showMessage("Please select Co-Passenger " + i + " gender");
                            return false;
                        }
                    } else if (etAgeList.get(i).getText().toString().equalsIgnoreCase("")) {
                        if (i == 0) {
                            showMessage("Please enter Primary Passenger age");
                            return false;
                        } else {
                            showMessage("Please enter Co-Passenger " + i + " age");
                            return false;
                        }
                    }

                }
            }
            if (selectedTitle.equals("")) {
                showMessage("Please select title");
                return false;
            } else if (nameEt.getText().toString().trim().length() < 3) {
                nameEt.setError("Please enter name");
                requestFocus(nameEt);
                return false;
            } else if (mobileEdtNo.getText().toString().trim().length() < 10) {
                mobileEdtNo.setError("Please enter valid mobile number");
                requestFocus(mobileEdtNo);
                return false;
            } else if (emailEt.getText().toString().trim().length() == 0) {
                emailEt.setError("Please enter email id");
                requestFocus(emailEt);
                return false;
            } else if (!Utils.isEmailAddress(emailEt.getText().toString().trim())) {
                emailEt.setError("Please enter valid email id");
                requestFocus(emailEt);
                return false;
            } else if (addressEt.getText().toString().trim().length() < 1) {
                addressEt.setError("Please enter address");
                requestFocus(addressEt);
                return false;
            } else if (cityEt.getText().toString().trim().length() == 0) {
                cityEt.setError("Please enter city");
                requestFocus(cityEt);
                return false;
            } else if (countryEt.getText().toString().trim().length() == 0) {
                countryEt.setError("Please select country");
                requestFocus(countryEt);
                return false;
            } /*else if (idProofEt.getText().toString().trim().length() == 0) {
                showMessage("Please select ID Proof type");
                return false;
            } else if (idProofNumberEt.getText().toString().trim().length() == 0) {
                idProofNumberEt.setError("Please enter ID Proof number");
                requestFocus(idProofNumberEt);
                return false;
            }*/ /*else if (passenger == null) {
                showMessage("Please fill passenger details.");
                return false;
            }*/ else {

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);

    }


    /*{
        "UserTrackId": "RMWVO9706777999391798096988087082503317",
            "CompanyId": "10001",
            "FK_MemID": "100",
            "TransType": "Bus Booking",
            "BookingId": "190140",
            "BookingInput": {
        "BookingCustomerDetails": {
            "Title": "Mr.",
                    "Name": "Deepak",
                    "Address": "Supertech",
                    "ContactNumber": "9918703130",
                    "City": "Noida",
                    "CountryId": "91",
                    "EmailId": "deepakverma9918@gmail.com",
                    "IdProofId": "",
                    "IdProofNumber": ""
        },
        "BookingDetails": {
            "TotalTickets": 1,
                    "TotalAmount": 170,
                    "TransportId": 24,
                    "ScheduleId": "358326",
                    "StationId": "6521",
                    "TravelDate": "07/16/2019",
                    "PassengerList": [
            {
                "PassengerName": "Rajeev Singh",
                    "Gender": "M",
                    "Age": 35,
                    "SeatNo": "13",
                    "SeatTypeId": 64,
                    "Fare": 170,
                    "BoardingId": "14334",
                    "DroppingId": "5620"
            },
            {
                "PassengerName": "Rajeev Singh",
                    "Gender": "M",
                    "Age": 35,
                    "SeatNo": "13",
                    "SeatTypeId": 64,
                    "Fare": 170,
                    "BoardingId": "14334",
                    "DroppingId": "5620"
            }
      ]
        }
    }
    }*/
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
                                    getSeatBooked(amount);
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

    private void getSeatBooked(double amount) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("UserTrackId", userTrackId);
        jsonObject.addProperty("CompanyId", companyId);
        jsonObject.addProperty("TransType", "Bus Booking");

        try {
            jsonObject.addProperty("FK_MemID", Cons.decryptMsg(PreferencesManager.getInstance(context).getUSERID(), cross_intent));
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        jsonObject.addProperty("BookingId", System.currentTimeMillis());

        JsonObject bookInput = new JsonObject();

        JsonObject custDetails = new JsonObject();
        custDetails.addProperty("Title", selectedTitle);
        custDetails.addProperty("Name", nameEt.getText().toString().trim());
        custDetails.addProperty("Address", addressEt.getText().toString().trim());
        custDetails.addProperty("ContactNumber", mobileEdtNo.getText().toString().trim());
        custDetails.addProperty("City", cityEt.getText().toString().trim());
        custDetails.addProperty("CountryId", "91");
        custDetails.addProperty("EmailId", emailEt.getText().toString().trim());
        custDetails.addProperty("IdProofId", idProofValue_st);
        custDetails.addProperty("IdProofNumber", idProofNumberEt.getText().toString());
        bookInput.add("BookingCustomerDetails", custDetails);


        JsonArray jsonArray = new JsonArray();
        double totalAmount = 0;
        for (int i = 0; i < etNameList.size(); i++) {
            LoggerUtil.logItem(etNameList.get(i).getText().toString());
            LoggerUtil.logItem(etAgeList.get(i).getText().toString());
            LoggerUtil.logItem(etGenderList.get(i).getText().toString());
            JsonObject passenger = new JsonObject();
            passenger.addProperty("PassengerName", etNameList.get(i).getText().toString());
            if (etGenderList.get(i).getText().toString().equalsIgnoreCase("Male")) {
                passenger.addProperty("Gender", "M");
            } else {
                passenger.addProperty("Gender", "F");
            }

            passenger.addProperty("Age", Integer.parseInt(etAgeList.get(i).getText().toString()));
            passenger.addProperty("BoardingId", bordingId);
            passenger.addProperty("DroppingId", dropId);
            passenger.addProperty("Fare", passengerList.get(i).getFare());
            passenger.addProperty("SeatNo", passengerList.get(i).getSeatNo());
            passenger.addProperty("SeatTypeId", passengerList.get(i).getSeatTypeId());
            totalAmount = totalAmount + passengerList.get(i).getFare();
            jsonArray.add(passenger);

        }
        JsonObject bookDetails = new JsonObject();
        bookDetails.addProperty("TotalTickets", passengerList.size());
        bookDetails.addProperty("TotalAmount", totalAmount);
        bookDetails.addProperty("TransportId", Integer.parseInt(transportId));
        bookDetails.addProperty("ScheduleId", scheduleId);
        bookDetails.addProperty("StationId", stationId);
        bookDetails.addProperty("TravelDate", travelDate);
        bookDetails.add("PassengerList", jsonArray);
        bookInput.add("BookingDetails", bookDetails);
        jsonObject.add("BookingInput", bookInput);


        LoggerUtil.logItem(jsonObject);

        showLoading();

        Call<JsonObject> call = apiServicesTravel.getSeatBooked(bodyParam(jsonObject));
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                try {
                    hideLoading();
                    Log.e(">>response", response.toString());
                    LoggerUtil.logItem(response.body());
                    LoggerUtil.logItem(call.request().url());
                    String param_ = Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent);
                    Gson gson = new GsonBuilder().create();
                    ResponseSeatBooked postPaidresponse = gson.fromJson(param_, ResponseSeatBooked.class);
                    if (response.body() != null && postPaidresponse.getResponseStatus().equalsIgnoreCase("1")) {
                        hitDebitWallet(amount, postPaidresponse.getBookingOutput().getTicketDetails().getPNRDetails().getTransactionId());
                    } else {
                        showMessage("Seat Booking Failed..");
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

    private void hitDebitWallet(Double dreamyWallet, String orderId) {
        showLoading();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", "Bus");
        jsonObject.addProperty("transactiondate", Utils.getTodayDate());
        jsonObject.addProperty("transactionId", orderId);
        jsonObject.addProperty("mainamount", String.valueOf(format.format(dreamyWallet)));
        jsonObject.addProperty("walletamount", "0");
        jsonObject.addProperty("paymentgatewayamount", "0");
        try {
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
                                showMessage("Seat Booked Successfully..");
                                Bundle bundle = new Bundle();
                                bundle.putString("userTrackId", userTrackId);
                                goToActivityWithFinish(BusBookingForm.this, BusBookingConfirmation.class, bundle);
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
