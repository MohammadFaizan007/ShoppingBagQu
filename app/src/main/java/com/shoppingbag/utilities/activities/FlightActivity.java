package com.shoppingbag.utilities.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shoppingbag.constants.BaseActivity;
import com.shoppingbag.constants.Cons;
import com.shoppingbag.model.domesticflight.requestmodel.AvailabilityInput;
import com.shoppingbag.model.domesticflight.requestmodel.JourneyDetailsItem;
import com.shoppingbag.model.domesticflight.requestmodel.RequestFlightAvalibility;
import com.shoppingbag.model.domesticflight.responsemodel.ResponseFlightAvalibility;
import com.shoppingbag.model.seat_book_response.ResponseSeatBooked;
import com.shoppingbag.utils.DialogUtil;
import com.shoppingbag.utils.LoggerUtil;
import com.shoppingbag.utils.Utils;
import com.shoppingbag.R;
import com.shoppingbag.utilities.domesticflight.AirportList;
import com.shoppingbag.utilities.domesticflight.FlightSearchActivity;
import com.shoppingbag.utilities.domesticflight.adapter.ChildTravellerAdapter;
import com.shoppingbag.utilities.domesticflight.adapter.TravellerAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FlightActivity extends BaseActivity {
    @BindView(R.id.side_menu)
    ImageView sideMenu;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.txtOneWay)
    TextView txtOneWay;
    @BindView(R.id.txtroundTrip)
    TextView txtroundTrip;
    @BindView(R.id.relativeLayout)
    LinearLayout relativeLayout;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.view2)
    View view2;
    @BindView(R.id.txtFlightClass)
    TextView txtFlightClass;
    @BindView(R.id.imageView4)
    ImageView imageView4;
    @BindView(R.id.constraintLayout)
    CardView constraintLayout;
    @BindView(R.id.txtFrom)
    TextView txtFrom;
    @BindView(R.id.txtFromCity)
    TextView txtFromCity;
    @BindView(R.id.cardFrom)
    CardView cardFrom;
    @BindView(R.id.txtTo)
    TextView txtTo;
    @BindView(R.id.txToCity)
    TextView txToCity;
    @BindView(R.id.cardTo)
    CardView cardTo;
    @BindView(R.id.txtDepart)
    TextView txtDepart;
    @BindView(R.id.cardViewDepart)
    CardView cardViewDepart;
    @BindView(R.id.txtReturn)
    TextView txtReturn;
    @BindView(R.id.cardViewReturn)
    CardView cardViewReturn;
    @BindView(R.id.imageView2)
    ImageView imageView2;
    @BindView(R.id.view3)
    View view3;
    @BindView(R.id.textView11)
    TextView textView11;
    @BindView(R.id.txtTravellerDes)
    TextView txtTravellerDes;
    @BindView(R.id.txtInfant)
    TextView txtInfant;
    @BindView(R.id.txtChild)
    TextView txtChild;
    @BindView(R.id.txtAdult)
    TextView txtAdult;
    @BindView(R.id.travellerList)
    RecyclerView travellerList;
    @BindView(R.id.button_done)
    ImageButton buttonDone;
    @BindView(R.id.btn_switcher)
    ImageButton btnSwitcher;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    long departInMilli = 0;
    private int ADULT = 1;
    private int CHILD = 0;
    private int INFANT = 0;
    private int CLASS_POS = 0;
    private String CLASS = "ECONOMY";
    private char BOOKING_TYPE = 'R';
    private CharSequence[] items = {"ECONOMY", "BUSINESS", "FIRST CLASS", "PREMIUM ECONOMY"};

    private TravellerAdapter adultAdapter;
    private ChildTravellerAdapter childAdapter;
    private ChildTravellerAdapter infantsAdapter;
    private Gson gson = new GsonBuilder().create();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_lay);
        ButterKnife.bind(this);
        title.setText("Flight Booking");

        travellerList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));

        resetColor();
        txtAdult.setBackgroundResource(R.drawable.rect_login_bg);
        txtAdult.setTextColor(ContextCompat.getColor(context, R.color.white));

        txtDepart.setText(Utils.getTodayDate("MM/dd/yyyy"));
        txtReturn.setText(Utils.getTodayDate("MM/dd/yyyy"));

        departInMilli = Calendar.getInstance().getTimeInMillis();

        adultAdapter = new TravellerAdapter(context, 9) {
            @Override
            public void click() {
                super.click();
                ADULT = adultAdapter.getNoOfTraveller();
                Log.e("ADULT", "=" + ADULT);
            }
        };
        travellerList.setAdapter(adultAdapter);
        adultAdapter.setNoOfTraveller(ADULT);
    }

    @OnClick({R.id.side_menu, R.id.txtOneWay, R.id.txtroundTrip, R.id.txtFlightClass, R.id.cardFrom, R.id.cardTo, R.id.cardViewDepart, R.id.cardViewReturn,
            R.id.txtInfant, R.id.txtChild, R.id.txtAdult, R.id.button_done,
            R.id.btn_switcher})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.side_menu:
                hideKeyboard();
                finish();
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                break;
            case R.id.btn_switcher:
                String code, city;
                code = txtFrom.getText().toString();
                city = txtFromCity.getText().toString();
                txtFrom.setText(txtTo.getText().toString());
                txtFromCity.setText(txToCity.getText().toString());
                txtTo.setText(code);
                txToCity.setText(city);
                break;
            case R.id.txtOneWay:
                BOOKING_TYPE = 'O';
                txtOneWay.setBackgroundResource(R.drawable.rect_login_bg);
                txtroundTrip.setBackgroundResource(0);
                txtroundTrip.setTextColor(ContextCompat.getColor(context, R.color.text_color));
                txtOneWay.setTextColor(ContextCompat.getColor(context, R.color.white));
                cardViewReturn.setVisibility(View.GONE);
                break;
            case R.id.txtroundTrip:
                BOOKING_TYPE = 'R';
                txtOneWay.setBackgroundResource(0);
                txtroundTrip.setBackgroundResource(R.drawable.rect_login_bg);
                txtroundTrip.setTextColor(ContextCompat.getColor(context, R.color.white));
                txtOneWay.setTextColor(ContextCompat.getColor(context, R.color.text_color));
                cardViewReturn.setVisibility(View.VISIBLE);
                break;
            case R.id.txtFlightClass:
                showClass(CLASS_POS);
                break;
            case R.id.cardFrom:
                if (!TextUtils.isEmpty(txtTo.getText().toString())) {
                    Intent in = new Intent(context, AirportList.class);
                    in.putExtra("CODE", txtTo.getText().toString());
                    context.startActivityForResult(in, 100);
                } else {
                    context.startActivityForResult(new Intent(context, AirportList.class), 100);
                }
                break;
            case R.id.cardTo:
                if (!TextUtils.isEmpty(txtFrom.getText().toString())) {
                    Intent in = new Intent(context, AirportList.class);
                    in.putExtra("CODE", txtFrom.getText().toString());
                    context.startActivityForResult(in, 101);
                } else {
                    context.startActivityForResult(new Intent(context, AirportList.class), 101);
                }
                break;
            case R.id.cardViewDepart:
                datePicker(txtDepart, true);
                break;
            case R.id.cardViewReturn:
                if (TextUtils.isEmpty(txtDepart.getText().toString())) {
                    showSnackBar("First select depart date!");
                } else {
                    datePicker(txtReturn, false);
                }
                break;
            case R.id.txtInfant:
                txtTravellerDes.setText(R.string.infant_details);
                resetColor();
                txtInfant.setBackgroundResource(R.drawable.rect_login_bg);
                txtInfant.setTextColor(ContextCompat.getColor(context, R.color.white));

                infantsAdapter = new ChildTravellerAdapter(context, 5, ADULT) {
                    @Override
                    public void click() {
                        super.click();

                        INFANT = infantsAdapter.getNoOfTraveller();
                    }
                };
                travellerList.setAdapter(infantsAdapter);
                infantsAdapter.setNoOfTraveller(INFANT);
                break;
            case R.id.txtChild:
                txtTravellerDes.setText(R.string.child_details);
                resetColor();
                txtChild.setBackgroundResource(R.drawable.rect_login_bg);
                txtChild.setTextColor(ContextCompat.getColor(context, R.color.white));

                childAdapter = new ChildTravellerAdapter(context, 9, ADULT) {
                    @Override
                    public void click() {
                        super.click();

                        CHILD = childAdapter.getNoOfTraveller();
                    }
                };
                travellerList.setAdapter(childAdapter);
                childAdapter.setNoOfTraveller(CHILD);
                break;
            case R.id.txtAdult:
                txtTravellerDes.setText(R.string.adult_details);
                resetColor();
                txtAdult.setBackgroundResource(R.drawable.rect_login_bg);
                txtAdult.setTextColor(ContextCompat.getColor(context, R.color.white));

                adultAdapter = new TravellerAdapter(context, 9) {
                    @Override
                    public void click() {
                        super.click();
                        ADULT = adultAdapter.getNoOfTraveller();
                        Log.e("ADULT", "=" + ADULT);
                    }
                };
                travellerList.setAdapter(adultAdapter);
                adultAdapter.setNoOfTraveller(ADULT);
                break;
            case R.id.button_done:
                if (validation()) {
                    getFlightAvaliabilty();
                }
                break;
        }
    }

    private void showClass(int pos) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Make your selection");
        builder.setSingleChoiceItems(items, pos, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                // Do something with the selection
                CLASS_POS = item;
                CLASS = items[item].toString();
                txtFlightClass.setText(items[item]);
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private boolean validation() {
        if (TextUtils.isEmpty(txtFrom.getText().toString())) {
            showMessage("Please Select a source airport.");
            return false;
        } else if (TextUtils.isEmpty(txtTo.getText().toString())) {
            showMessage("Please Select a destination airport.");
            return false;
        } else if ((ADULT + CHILD + INFANT) > 9) {
            DialogUtil.showInfoDialog(context, "Traveller Count Error", "Maximum of 9 travellers are allowed per booking.");
            return false;
        }

        return true;
    }

    private void resetColor() {
        txtInfant.setBackgroundResource(R.drawable.round_white_bg);
        txtInfant.setTextColor(ContextCompat.getColor(context, R.color.text_color));

        txtChild.setBackgroundResource(R.drawable.round_white_bg);
        txtChild.setTextColor(ContextCompat.getColor(context, R.color.text_color));

        txtAdult.setBackgroundResource(R.drawable.round_white_bg);
        txtAdult.setTextColor(ContextCompat.getColor(context, R.color.text_color));
    }

    private void datePicker(final TextView et, final boolean depart) {
        Calendar cal = Calendar.getInstance();
        int mYear, mMonth, mDay;

        if (!depart) {
            cal.setTimeInMillis(departInMilli);
        }

        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear, int dayOfMonth) {


                et.setText(Utils.changeDateFormatTravel(dayOfMonth, monthOfYear, year,"MM/dd/yyyy"));

                if (depart) {
                    Calendar departcal = Calendar.getInstance();
                    departcal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    departcal.set(Calendar.MONTH, monthOfYear);
                    departcal.set(Calendar.YEAR, year);
                    departInMilli = departcal.getTimeInMillis();
                }

            }
        }, mYear, mMonth, mDay);

        datePickerDialog.getDatePicker().setMinDate(cal.getTimeInMillis());

        datePickerDialog.show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {

                case 100:
                    txtFrom.setText(data.getStringExtra("CODE"));
                    txtFromCity.setText(data.getStringExtra("CITY"));
                    break;
                case 101:
                    txtTo.setText(data.getStringExtra("CODE"));
                    txToCity.setText(data.getStringExtra("CITY"));
                    break;
            }
        }
    }
    /*{
        "AvailabilityInput": {
        "AdultCount": 1,
                "AirlineCode": "",
                "BookingType": "O",
                "ChildCount": 0,
                "ClassType": "ECONOMY",
                "InfantCount": 0,
                "JourneyDetails": [
        {
            "Destination": "BOM",
                "Origin": "DEL",
                "TravelDate": "09/16/2019"
        }
    ],
        "Optional1": "0",
                "Optional2": "0",
                "Optional3": "0",
                "ResidentofIndia": 1
    }
    }*/
    private void getFlightAvaliabilty() {
        try {
            showFlightLoading();

//        ApiServices apiServices = ServiceGenerator.createServiceFlight(ApiServices.class);
            RequestFlightAvalibility flightAvalibility = new RequestFlightAvalibility();
//        Authentication authentication = new Authentication();
//        authentication.setLoginId(Utils.USERNAME);
//        authentication.setPassword(Utils.PASSWORD);

            final AvailabilityInput availabilityInput = new AvailabilityInput();

            availabilityInput.setBookingType(String.valueOf(BOOKING_TYPE));
            availabilityInput.setClassType(CLASS);
            availabilityInput.setAirlineCode("");
            availabilityInput.setAdultCount(ADULT);
            availabilityInput.setChildCount(CHILD);
            availabilityInput.setInfantCount(INFANT);
            availabilityInput.setResidentofIndia(1); //  1-Citizen of India 0-Not a citizen of India

            availabilityInput.setOptional1("0");
            availabilityInput.setOptional2("0");
            availabilityInput.setOptional3("0");

            List<JourneyDetailsItem> journeyDetailsItems = new ArrayList<>();

            if (BOOKING_TYPE == 'O') {
                JourneyDetailsItem journeyDetailsItem = new JourneyDetailsItem();
                journeyDetailsItem.setDestination(txtTo.getText().toString());
                journeyDetailsItem.setOrigin(txtFrom.getText().toString());
                journeyDetailsItem.setTravelDate(txtDepart.getText().toString());
                journeyDetailsItems.add(journeyDetailsItem);

            } else {
                JourneyDetailsItem journeyDetailsItem = new JourneyDetailsItem();
                journeyDetailsItem.setDestination(txtTo.getText().toString());
                journeyDetailsItem.setOrigin(txtFrom.getText().toString());
                journeyDetailsItem.setTravelDate(txtDepart.getText().toString());

                JourneyDetailsItem journeyDetailsItemR = new JourneyDetailsItem();
                journeyDetailsItemR.setDestination(txtFrom.getText().toString());
                journeyDetailsItemR.setOrigin(txtTo.getText().toString());
                journeyDetailsItemR.setTravelDate(txtReturn.getText().toString());
                journeyDetailsItems.add(journeyDetailsItem);
                journeyDetailsItems.add(journeyDetailsItemR);
            }

            availabilityInput.setJourneyDetails(journeyDetailsItems);
//        flightAvalibility.setAuthentication(authentication);
            flightAvalibility.setAvailabilityInput(availabilityInput);

            LoggerUtil.logItem(flightAvalibility);

            JsonObject jsonObject = new JsonObject();
            JsonObject availabilty = new JsonObject();
            availabilty.addProperty("AdultCount",ADULT);
            availabilty.addProperty("AirlineCode","");
            availabilty.addProperty("BookingType",String.valueOf(BOOKING_TYPE));
            availabilty.addProperty("ChildCount",CHILD);
            availabilty.addProperty("ClassType",CLASS);
            availabilty.addProperty("InfantCount",INFANT);
            availabilty.addProperty("Optional1","0");
            availabilty.addProperty("Optional2","0");
            availabilty.addProperty("Optional3","0");
            availabilty.addProperty("ResidentofIndia",1);

            JsonArray journeyDetails = new JsonArray();
            if (BOOKING_TYPE == 'O') {
                JsonObject journeyObject = new JsonObject();
                journeyObject.addProperty("Destination",txtTo.getText().toString());
                journeyObject.addProperty("Origin",txtFrom.getText().toString());
                journeyObject.addProperty("TravelDate",txtDepart.getText().toString());
                journeyDetails.add(journeyObject);
            }else {
                JsonObject journeyObject = new JsonObject();
                journeyObject.addProperty("Destination",txtTo.getText().toString());
                journeyObject.addProperty("Origin",txtFrom.getText().toString());
                journeyObject.addProperty("TravelDate",txtDepart.getText().toString());
                journeyDetails.add(journeyObject);

                JsonObject journeyObjectR = new JsonObject();
                journeyObjectR.addProperty("Destination",txtFrom.getText().toString());
                journeyObjectR.addProperty("Origin",txtTo.getText().toString());
                journeyObjectR.addProperty("TravelDate",txtReturn.getText().toString());
                journeyDetails.add(journeyObjectR);
            }

            availabilty.add("JourneyDetails",journeyDetails);
            jsonObject.add("AvailabilityInput",availabilty);

            Call<JsonObject> busCall = apiServicesTravel.getFlightDetailsAPI(bodyParam(jsonObject));
            busCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                    hideLoading();
                    LoggerUtil.logItem(response.body());
                    try {
                        hideFlightLoading();
                        Log.e(">>response", response.toString());
                        LoggerUtil.logItem(call.request().url());
                        String param_ = Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent);
                        Gson gson = new GsonBuilder().create();
                        ResponseFlightAvalibility postPaidresponse = gson.fromJson(param_, ResponseFlightAvalibility.class);
                        if (postPaidresponse.getResponseStatus() == 1) {
                            Intent in = new Intent(context, FlightSearchActivity.class);
                            in.putExtra("Title", txtFrom.getText().toString() + "-" + txtTo.getText().toString());
                            in.putExtra("SubTitle", Utils.changeDateFormat(txtDepart.getText().toString()) + ", " + (ADULT + CHILD + INFANT) + " Travellers");
                            in.putExtra("Response", postPaidresponse);
                            in.putExtra("Source", txtFrom.getText().toString());
                            in.putExtra("Destination", txtTo.getText().toString());
                            in.putExtra("Input", availabilityInput);
                            startActivity(in);
                            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                        } else {
                            showMessage("No Flight Available");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        hideFlightLoading();

                        showMessage("Something went wrong.");
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


    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

}
