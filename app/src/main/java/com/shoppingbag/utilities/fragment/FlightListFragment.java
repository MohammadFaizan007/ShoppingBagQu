package com.shoppingbag.utilities.fragment;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shoppingbag.constants.BaseFragment;
import com.shoppingbag.model.domesticflight.responsemodel.FlightItem;
import com.shoppingbag.R;
import com.shoppingbag.utilities.domesticflight.FlightSearchActivity;
import com.shoppingbag.utilities.domesticflight.FlightsItineraryActivity;
import com.shoppingbag.utilities.domesticflight.adapter.OnGoingFlightAdapter;
import com.shoppingbag.utilities.domesticflight.adapter.ReturnFlightAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class FlightListFragment extends BaseFragment {

    @BindView(R.id.flightList)
    RecyclerView flightList;
    Unbinder unbinder;
    ArrayList<FlightItem> originalList = new ArrayList<>();
    ReturnFlightAdapter adapter_return;
    OnGoingFlightAdapter adapter;

    Intent in;
    @BindView(R.id.departure)
    TextView departure;
    @BindView(R.id.fast)
    TextView fast;
    @BindView(R.id.price)
    TextView price;
    boolean departureFilt = true, fastFilt = true, priceFilt = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.flight_list_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreatedStuff(View view, @Nullable Bundle savedInstanceState) {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(RecyclerView.VERTICAL);
        flightList.setLayoutManager(manager);

        in = new Intent(getActivity(), FlightsItineraryActivity.class);

        if (getArguments() != null) {

            if (getArguments().getBoolean("ongoing")) {
                originalList = (ArrayList<FlightItem>) getArguments().getSerializable("List");
                adapter = new OnGoingFlightAdapter(getActivity(), originalList, 0) {
                    @Override
                    public void click(int pos) {
                        super.click(pos);
                        if (FlightSearchActivity.NUM_ITEMS == 1) {

                            in.putExtra("Input", ((FlightSearchActivity) Objects.requireNonNull(getActivity())).input);
                            in.putExtra("TrackId", ((FlightSearchActivity) getActivity()).responseFlightAvalibility.getUserTrackId());
                            in.putExtra("Flight1Details", ((FlightSearchActivity) getActivity()).responseFlightAvalibility.getAvailabilityOutput().getAvailableFlights().
                                    getOngoingFlights().get(pos));
                            Log.e("track_id>>>>", ((FlightSearchActivity) getActivity()).responseFlightAvalibility.getUserTrackId());
                            getActivity().startActivity(in);
                        } else {

                            FlightSearchActivity.FIRST_POS = pos;
                            FlightSearchActivity.chooseFirstFlight = true;

                            ((FlightSearchActivity) Objects.requireNonNull(getActivity())).vpPager.setCurrentItem(1, true);
                        }
                    }
                };
                flightList.setAdapter(adapter);

            } else {
                originalList = (ArrayList<FlightItem>) getArguments().getSerializable("List");
                adapter_return = new ReturnFlightAdapter(getActivity(), originalList) {
                    @Override
                    public void click(int pos) {
                        super.click(pos);
                        if (FlightSearchActivity.chooseFirstFlight) {
                            FlightSearchActivity.SECOND_POS = pos;

                            in.putExtra("Input", ((FlightSearchActivity) getActivity()).input);
                            in.putExtra("TrackId", ((FlightSearchActivity) getActivity()).responseFlightAvalibility.getUserTrackId());
                            in.putExtra("Flight1Details", ((FlightSearchActivity) getActivity()).responseFlightAvalibility.getAvailabilityOutput().getAvailableFlights().
                                    getOngoingFlights().get(FlightSearchActivity.FIRST_POS));
                            in.putExtra("Flight2Details", ((FlightSearchActivity) getActivity()).responseFlightAvalibility.getAvailabilityOutput().getAvailableFlights().
                                    getReturnFlights().get(FlightSearchActivity.SECOND_POS));
                            getActivity().startActivity(in);
                        } else {
                            showMessage("First select depart flight");
                            ((FlightSearchActivity) getActivity()).vpPager.setCurrentItem(0, true);
                        }

                    }
                };

                flightList.setAdapter(adapter_return);
            }
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.departure, R.id.fast, R.id.price})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.departure:
                try {
                    departure.setTextColor(getResources().getColor(R.color.text_color));
                    fast.setTextColor(getResources().getColor(R.color.text_color));
                    price.setTextColor(getResources().getColor(R.color.text_color));
                    Collections.sort(originalList, new MyFlightDeparture());
                    if (getArguments().getBoolean("ongoing")) {
                        adapter.updateOnGoingList(originalList);
                    } else {
                        adapter_return.updateReturnList(originalList);
                    }
                    if (departureFilt) {
                        departure.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.down_arr_black, 0);
                        fast.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.exchange_arr_black, 0);
                        price.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.exchange_arr_black, 0);
                        departureFilt = false;
                    } else {
                        departure.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.up_arr_black, 0);
                        fast.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.exchange_arr_black, 0);
                        price.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.exchange_arr_black, 0);
                        departureFilt = true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.fast:
                try {
                    departure.setTextColor(getResources().getColor(R.color.text_color));
                    fast.setTextColor(getResources().getColor(R.color.text_color));
                    price.setTextColor(getResources().getColor(R.color.text_color));
                    Collections.sort(originalList, new MyFlightFast());
                    if (getArguments().getBoolean("ongoing")) {
                        adapter.updateOnGoingList(originalList);
                    } else {
                        adapter_return.updateReturnList(originalList);
                    }
                    if (fastFilt) {
                        fast.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.down_arr_black, 0);
                        departure.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.exchange_arr_black, 0);
                        price.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.exchange_arr_black, 0);
                        fastFilt = false;
                    } else {
                        fast.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.up_arr_black, 0);
                        departure.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.exchange_arr_black, 0);
                        price.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.exchange_arr_black, 0);
                        fastFilt = true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            case R.id.price:
                try {
                    departure.setTextColor(getResources().getColor(R.color.text_color));
                    fast.setTextColor(getResources().getColor(R.color.text_color));
                    price.setTextColor(getResources().getColor(R.color.text_color));
                    Collections.sort(originalList, new PriceComp());
                    if (getArguments().getBoolean("ongoing")) {
                        adapter.updateOnGoingList(originalList);
                    } else {
                        adapter_return.updateReturnList(originalList);
                    }
                    if (priceFilt) {
                        price.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.down_arr_black, 0);
                        departure.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.exchange_arr_black, 0);
                        fast.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.exchange_arr_black, 0);
                        priceFilt = false;
                    } else {
                        price.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.up_arr_black, 0);
                        departure.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.exchange_arr_black, 0);
                        fast.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.exchange_arr_black, 0);
                        priceFilt = true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    class PriceComp implements Comparator<FlightItem> {
        @Override
        public int compare(FlightItem e1, FlightItem e2) {
            if (priceFilt) {
                if (Double.parseDouble(e1.getGrossAmount()) > Double.parseDouble(e2.getGrossAmount())) {
                    return 1;
                } else {
                    return -1;
                }
            } else {
                if (Double.parseDouble(e1.getGrossAmount()) < Double.parseDouble(e2.getGrossAmount())) {
                    return 1;
                } else {
                    return -1;
                }
            }
        }
    }

    class MyFlightFast implements Comparator<FlightItem> {
        @Override
        public int compare(FlightItem f1, FlightItem f2) {
            if (fastFilt) {
                return f1.getNumberofStops().compareTo(f2.getNumberofStops());
            } else {
                return f2.getNumberofStops().compareTo(f1.getNumberofStops());
            }
        }
    }


    class MyFlightDeparture implements Comparator<FlightItem> {
        @Override
        public int compare(FlightItem d1, FlightItem d2) {
            if (departureFilt) {
                return d1.getDepartureDateTime().compareTo(d2.getDepartureDateTime());
            } else {
                return d2.getDepartureDateTime().compareTo(d1.getDepartureDateTime());
            }
        }
    }

}
