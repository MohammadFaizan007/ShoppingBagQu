package com.shoppingbag.utilities.domesticflight.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.shoppingbag.model.domesticflight.responsemodel.FlightItem;
import com.shoppingbag.R;
import com.shoppingbag.utilities.domesticflight.FlightSearchActivity;
import com.shoppingbag.utilities.fragment.FlightClick;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReturnFlightAdapter extends RecyclerView.Adapter<ReturnFlightAdapter.SingleItemRowHolder> implements FlightClick {

    private Context mContext;
    private List<FlightItem> flightsItems;


    public ReturnFlightAdapter(Context context, List<FlightItem> ongoingFlightsItems) {
        this.mContext = context;
        this.flightsItems = ongoingFlightsItems;
    }

    public void updateReturnList(List<FlightItem> ongoingFlightsItems) {
        this.flightsItems = ongoingFlightsItems;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ReturnFlightAdapter.SingleItemRowHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.flight_item_return_lay, null, false);
        return new ReturnFlightAdapter.SingleItemRowHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull ReturnFlightAdapter.SingleItemRowHolder holder, int i) {
        holder.txtTime.setText(String.format("%s - %s", flightsItems.get(i).getDepartureDateTime(), flightsItems.get(i).getArrivalDateTime()));

        if (flightsItems.get(i).isMultiple()) {
            holder.txtFlightCode.setText("Multiple");
        } else {
            holder.txtFlightCode.setText(String.format("%s - %s", flightsItems.get(i).getAirlineCode(), flightsItems.get(i).getFlightNumber()));
        }

        holder.txtDuration.setText(flightsItems.get(i).getDuration());

        if (flightsItems.get(i).getNumberofStops().equalsIgnoreCase("0")) {
            holder.txtStops.setText("Non-Stop");
        } else {
            holder.txtStops.setText(String.format("%s Stops", flightsItems.get(i).getNumberofStops()));
        }

        holder.txtPrice.setText(String.format("₹%s", flightsItems.get(i).getGrossAmount()));
        holder.txtRefundable.setText(flightsItems.get(i).getFareType());

        if (FlightSearchActivity.SECOND_POS == i) {
            holder.cardLay2.setBackgroundResource(R.drawable.line_border);
        } else {
            holder.cardLay2.setBackgroundResource(0);
        }

        Glide.with(mContext).load("https://images.kiwi.com/airlines/64/" + flightsItems.get(i).getAirlineCode() + ".png").
                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        .placeholder(R.drawable.flight2)
                        .error(R.drawable.flight2))
                .into(holder.imgFlight);

    }

    @Override
    public int getItemCount() {
        return flightsItems.size();
    }

    @Override
    public void click(int pos) {

    }

    class SingleItemRowHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.img_flight)
        ImageView imgFlight;
        @BindView(R.id.txtTime)
        TextView txtTime;
        @BindView(R.id.txtFlightCode)
        TextView txtFlightCode;
        @BindView(R.id.txtDuration)
        TextView txtDuration;
        @BindView(R.id.txtStops)
        TextView txtStops;
        @BindView(R.id.txtPrice)
        TextView txtPrice;
        @BindView(R.id.txtRefundable)
        TextView txtRefundable;
        @BindView(R.id.cardLay2)
        ConstraintLayout cardLay2;


        SingleItemRowHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            click(getAdapterPosition());
            notifyDataSetChanged();
        }
    }
}
