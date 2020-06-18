package com.shoppingbag.utilities.domesticflight.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shoppingbag.model.domesticflight.responsemodel.AirlineCodeItem;
import com.shoppingbag.model.domesticflight.responsemodel.AllAirLinesCodes;
import com.shoppingbag.model.domesticflight.responsemodel.BookedSegmentsItem;
import com.shoppingbag.utils.Utils;
import com.shoppingbag.R;
import com.shoppingbag.utilities.domesticflight.FlightSeatMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BookedJourneyAdapter extends RecyclerView.Adapter<BookedJourneyAdapter.BookingHolder> {

    private Context context;
    private List<BookedSegmentsItem> bookedSegments;
    private Gson gson = new GsonBuilder().create();
    private String json;
    private List<AirlineCodeItem> codeItems = new ArrayList<>();
    private String PNR, AIRLINEPNR;

    public BookedJourneyAdapter(Context context, List<BookedSegmentsItem> bookedSegments, String pnr, String airlinepnr) {
        this.context = context;
        this.bookedSegments = bookedSegments;
        json = loadJSONFromAsset();
        AllAirLinesCodes dtls = gson.fromJson(json, AllAirLinesCodes.class);
        codeItems = dtls.getAirlineCode();
        this.PNR = pnr;
        this.AIRLINEPNR = airlinepnr;
    }


    @NonNull
    @Override
    public BookingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ticket_journey_adapter, parent, false);
        return new BookingHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingHolder holder, int position) {

        //TODO use the below json to find the city codes
        holder.cityCodeFrom.setText(bookedSegments.get(position).getOrigin());
        holder.departTerminal.setText(getCityName(bookedSegments.get(position).getOrigin().trim()));
        holder.cityCodeTwo.setText(bookedSegments.get(position).getDestination());
        holder.ariveTerminal.setText(getCityName(bookedSegments.get(position).getDestination().trim()));

        holder.departTime.setText(Utils.getTime(bookedSegments.get(position).getDepartureDateTime()));
        holder.ariveTime.setText(Utils.getTime(bookedSegments.get(position).getArrivaldatetime()));
        holder.txtTotalTime.setText(Utils.getTimeDifference(bookedSegments.get(position)
                .getDepartureDateTime(), bookedSegments.get(position).getArrivaldatetime()));
        holder.txtDate.setText(Utils.getMonthDate(bookedSegments.get(position).getDepartureDateTime()));
        holder.txtDay.setText(Utils.getDay(bookedSegments.get(position).getDepartureDateTime()));

        if (bookedSegments.get(position).isSeatComnirm()) {
            holder.txtSeatMap.setVisibility(View.GONE);
        } else {
            holder.txtSeatMap.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return bookedSegments.size();
    }

    private String loadJSONFromAsset() {
        String json;
        try {
            InputStream is = context.getAssets().open("india_airlinecode.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private String getCityName(String cityCode) {
        String cityName = "";

        try {
            for (int i = 0; i < codeItems.size(); i++) {
                if ((codeItems.get(i).getCode()).equalsIgnoreCase(cityCode)) {
                    cityName = codeItems.get(i).getCity();
                    Log.e("CITY", cityCode + " = " + cityName);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cityName;
    }

    public class BookingHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cityCodeFrom)
        TextView cityCodeFrom;
        @BindView(R.id.cityCodeTwo)
        TextView cityCodeTwo;
        @BindView(R.id.txtDay)
        TextView txtDay;
        @BindView(R.id.txtDate)
        TextView txtDate;
        @BindView(R.id.departTime)
        TextView departTime;
        @BindView(R.id.departTerminal)
        TextView departTerminal;
        @BindView(R.id.ariveTime)
        TextView ariveTime;
        @BindView(R.id.ariveTerminal)
        TextView ariveTerminal;
        @BindView(R.id.txtTotalTime)
        TextView txtTotalTime;
        @BindView(R.id.txtSeatMap)
        TextView txtSeatMap;

        BookingHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            txtSeatMap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, FlightSeatMap.class);
                    intent.putExtra("code", bookedSegments.get(getAdapterPosition()).getAirlineCode());
                    intent.putExtra("pnr", PNR);
                    intent.putExtra("airlinepnr", AIRLINEPNR);
                    context.startActivity(intent);

                }
            });
        }
    }


}
