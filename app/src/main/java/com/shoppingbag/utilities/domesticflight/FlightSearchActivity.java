package com.shoppingbag.utilities.domesticflight;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerTabStrip;
import androidx.viewpager.widget.ViewPager;

import com.shoppingbag.R;
import com.shoppingbag.utilities.fragment.FlightListFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.shoppingbag.constants.BaseActivity;
import com.shoppingbag.model.domesticflight.requestmodel.AvailabilityInput;
import com.shoppingbag.model.domesticflight.responsemodel.AvailSegmentsItem;
import com.shoppingbag.model.domesticflight.responsemodel.FlightItem;
import com.shoppingbag.model.domesticflight.responsemodel.OngoingFlightsItem;
import com.shoppingbag.model.domesticflight.responsemodel.ResponseFlightAvalibility;
import com.shoppingbag.model.domesticflight.responsemodel.ReturnFlightsItem;
import com.shoppingbag.utils.Utils;

public class FlightSearchActivity extends BaseActivity {

    public static int NUM_ITEMS;
    public static boolean chooseFirstFlight = false;
    public static int FIRST_POS = -1;
    public static int SECOND_POS = -1;
    @BindView(R.id.vpPager)
    public ViewPager vpPager;
    public ResponseFlightAvalibility responseFlightAvalibility;
    public AvailabilityInput input;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pager_header)
    PagerTabStrip pagerHeader;
    ArrayList<FlightItem> onGoingFlightItems = new ArrayList<>();
    ArrayList<FlightItem> returnFlightsItems = new ArrayList<>();
    MyPagerAdapter adapterViewPager;
    Fragment currentFragment;

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flight_search);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        chooseFirstFlight = false;
        FIRST_POS = -1;
        SECOND_POS = -1;

        showFlightLoading();

        if (getIntent() != null) {
            getSupportActionBar().setTitle(getIntent().getStringExtra("Title"));
            Log.e("TITLE", toolbar.getTitle().toString());
            toolbar.setTitleMarginBottom(0);
            toolbar.setSubtitle(getIntent().getStringExtra("SubTitle"));
            responseFlightAvalibility = (ResponseFlightAvalibility) getIntent().getSerializableExtra("Response");
            input = (AvailabilityInput) getIntent().getSerializableExtra("Input");
        }

        pagerHeader.setDrawFullUnderline(false);
        pagerHeader.setTabIndicatorColor(getResources().getColor(R.color.colorPrimary));
        pagerHeader.setBackgroundColor(ContextCompat.getColor(FlightSearchActivity.this, R.color.white));

        for (int i = 0; i < pagerHeader.getChildCount(); ++i) {
            View nextChild = pagerHeader.getChildAt(i);
            if (nextChild instanceof TextView) {
                TextView textViewToConvert = (TextView) nextChild;
                textViewToConvert.setTextColor(getResources().getColor(R.color.colorPrimary));
                textViewToConvert.setTextSize(18);
            }
        }
//        ----------------------------- Ongoing Flight List -----------------------------------------

        onGoingFlightItems = new ArrayList<>();

        String arrival;
        String depart = "";

        for (int i = 0; i < responseFlightAvalibility.getAvailabilityOutput().getAvailableFlights().getOngoingFlights().size(); i++) {
            OngoingFlightsItem items = responseFlightAvalibility.getAvailabilityOutput().getAvailableFlights().getOngoingFlights().get(i);
            FlightItem flightItem = new FlightItem();

            int grossAmount = 0;
            for (int j = 0; j < items.getAvailSegments().size(); j++) {
                AvailSegmentsItem segmentsItem = items.getAvailSegments().get(j);

                grossAmount = input.getAdultCount() * (grossAmount + segmentsItem.getAvailPaxFareDetails().get(0).getAdult().getGrossAmount());
                if (segmentsItem.getAvailPaxFareDetails().get(0).getChild() != null) {
                    grossAmount = input.getChildCount() * (grossAmount + segmentsItem.getAvailPaxFareDetails().get(0).getChild().getGrossAmount());
                }
                if (segmentsItem.getAvailPaxFareDetails().get(0).getInfant() != null) {
                    grossAmount = input.getInfantCount() * (grossAmount + segmentsItem.getAvailPaxFareDetails().get(0).getInfant().getGrossAmount());
                }

                if (j == 0) {
                    depart = segmentsItem.getDepartureDateTime();
                    arrival = segmentsItem.getArrivalDateTime();
                    flightItem.setDepartureDateTime(Utils.getTime(segmentsItem.getDepartureDateTime()));
                    flightItem.setArrivalDateTime(Utils.getTime(segmentsItem.getArrivalDateTime()));
                    flightItem.setFlightId(segmentsItem.getFlightId());
                    flightItem.setFlightNumber(segmentsItem.getFlightNumber());
                    flightItem.setAirlineCode(segmentsItem.getAirlineCode());
                    flightItem.setOrigin(segmentsItem.getOrigin());
                    flightItem.setDestination(segmentsItem.getDestination());
                    flightItem.setMultiple(false);
                    flightItem.setVia(segmentsItem.getDestination());
                    flightItem.setFareType(segmentsItem.getAvailPaxFareDetails().get(0).getAdult().getFareType().replace("\n", ""));
                    flightItem.setNumberofStops("0");
                    flightItem.setDuration(Utils.getTimeDifference(depart, arrival));
                    flightItem.setGrossAmount(String.valueOf(grossAmount));
                }

                if (j > 0 && j == items.getAvailSegments().size() - 1) {
                    arrival = segmentsItem.getArrivalDateTime();
                    flightItem.setMultiple(true);
                    flightItem.setNumberofStops(String.valueOf(j));
                    flightItem.setDestination(segmentsItem.getDestination());
                    flightItem.setArrivalDateTime(Utils.getTime(segmentsItem.getArrivalDateTime()));
                    flightItem.setDuration(Utils.getTimeDifference(depart, arrival));
                    flightItem.setGrossAmount(String.valueOf(grossAmount));

                }


            }
            onGoingFlightItems.add(flightItem);
        }

//        ------------------------------- Returning Flight List ---------------------------------------

        if (responseFlightAvalibility.getAvailabilityOutput().getAvailableFlights().getReturnFlights() != null) {
            returnFlightsItems = new ArrayList<>();

            depart = "";

            for (int i = 0; i < responseFlightAvalibility.getAvailabilityOutput().getAvailableFlights().getReturnFlights().size(); i++) {
                ReturnFlightsItem items = responseFlightAvalibility.getAvailabilityOutput().getAvailableFlights().getReturnFlights().get(i);
                FlightItem flightItem = new FlightItem();

                int grossAmount = 0;
                for (int j = 0; j < items.getAvailSegments().size(); j++) {
                    AvailSegmentsItem segmentsItem = items.getAvailSegments().get(j);

                    grossAmount = input.getAdultCount() * (grossAmount + segmentsItem.getAvailPaxFareDetails().get(0).getAdult().getGrossAmount());
                    if (segmentsItem.getAvailPaxFareDetails().get(0).getChild() != null) {
                        grossAmount = input.getChildCount() * (grossAmount + segmentsItem.getAvailPaxFareDetails().get(0).getChild().getGrossAmount());
                    }
                    if (segmentsItem.getAvailPaxFareDetails().get(0).getInfant() != null) {
                        grossAmount = input.getInfantCount() * (grossAmount + segmentsItem.getAvailPaxFareDetails().get(0).getInfant().getGrossAmount());
                    }

                    if (j == 0) {

                        depart = segmentsItem.getDepartureDateTime();
                        arrival = segmentsItem.getArrivalDateTime();
                        flightItem.setDepartureDateTime(Utils.getTime(segmentsItem.getDepartureDateTime()));
                        flightItem.setArrivalDateTime(Utils.getTime(segmentsItem.getArrivalDateTime()));
                        flightItem.setFlightId(segmentsItem.getFlightId());
                        flightItem.setFlightNumber(segmentsItem.getFlightNumber());
                        flightItem.setAirlineCode(segmentsItem.getAirlineCode());
                        flightItem.setOrigin(segmentsItem.getOrigin());
                        flightItem.setDestination(segmentsItem.getDestination());
                        flightItem.setMultiple(false);
                        flightItem.setVia(segmentsItem.getDestination());
                        flightItem.setFareType(segmentsItem.getAvailPaxFareDetails().get(0).getAdult().getFareType().replace("\n", ""));
                        flightItem.setNumberofStops("0");
                        flightItem.setDuration(Utils.getTimeDifference(depart, arrival));
                        flightItem.setGrossAmount(String.valueOf(grossAmount));

                    }

                    if (j > 0 && j == items.getAvailSegments().size() - 1) {

                        arrival = segmentsItem.getArrivalDateTime();
                        flightItem.setMultiple(true);
                        flightItem.setNumberofStops(String.valueOf(j));
                        flightItem.setDestination(segmentsItem.getDestination());
                        flightItem.setArrivalDateTime(Utils.getTime(segmentsItem.getArrivalDateTime()));
                        flightItem.setDuration(Utils.getTimeDifference(depart, arrival));
                        flightItem.setGrossAmount(String.valueOf(grossAmount));

                    }
                }
                returnFlightsItems.add(flightItem);
            }
        }

//        ---------------------------------------------------------------------------------------------

        if (responseFlightAvalibility.getAvailabilityOutput().getAvailableFlights().getReturnFlights() != null) {
            adapterViewPager = new MyPagerAdapter(getSupportFragmentManager(), 2, getIntent().getStringExtra("Source"),
                    getIntent().getStringExtra("Destination"));
        } else {
            adapterViewPager = new MyPagerAdapter(getSupportFragmentManager(), 1, getIntent().getStringExtra("Source"),
                    getIntent().getStringExtra("Destination"));
        }

        vpPager.setAdapter(adapterViewPager);
        currentFragment = adapterViewPager.getItem(0);


        vpPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override

            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                currentFragment = adapterViewPager.getItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        hideFlightLoading();
    }

    @Override
    public void onBackPressed() {
        if (vpPager.getCurrentItem() > 0) {
            vpPager.setCurrentItem(0, true);
        } else {
            super.onBackPressed();
        }
    }

    public class MyPagerAdapter extends FragmentPagerAdapter {

        String s, d;
        FlightListFragment ongoingFrag, returningFrag;

        MyPagerAdapter(FragmentManager fragmentManager, int length, String source, String destination) {
            super(fragmentManager);
            NUM_ITEMS = length;
            s = source;
            d = destination;
            ongoingFrag = new FlightListFragment();
            Bundle bundle = new Bundle();
            bundle.putBoolean("ongoing", true);
            bundle.putSerializable("List", onGoingFlightItems);
            ongoingFrag.setArguments(bundle);

            if (length > 1) {
                returningFrag = new FlightListFragment();
                bundle = new Bundle();
                bundle.putBoolean("ongoing", false);
                bundle.putSerializable("List", returnFlightsItems);
                returningFrag.setArguments(bundle);
            }
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: // Fragment # 0 - Ongoing Flight List.
                    return ongoingFrag;
                case 1: // Fragment # 1 - Return Flight List.
                    return returningFrag;
                default:
                    return null;
            }
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return s + " - " + d;
                case 1:
                    return d + " - " + s;

            }
            return "";
        }
    }


}
