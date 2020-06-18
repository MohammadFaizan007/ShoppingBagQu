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

public class OnGoingFlightAdapter extends RecyclerView.Adapter<OnGoingFlightAdapter.SingleItemRowHolder> implements FlightClick {
    int TYPE = 0;
    private Context mContext;
    private List<FlightItem> flightsItems;


    public OnGoingFlightAdapter(Context context, List<FlightItem> ongoingFlightsItems, int type) {

        this.mContext = context;
        this.flightsItems = ongoingFlightsItems;
        TYPE = type;

    }

    public void updateOnGoingList(List<FlightItem> ongoingFlightsItems) {
        this.flightsItems = ongoingFlightsItems;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SingleItemRowHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(i, null, false);
        return new SingleItemRowHolder(v);
    }

    @Override
    public int getItemViewType(int position) {
        switch (TYPE) {
            case 0:
                return R.layout.flight_item_lay;
            default:
                return R.layout.flight_item_lay;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull SingleItemRowHolder holder, int i) {
        holder.txtTime.setText(String.format("%s - %s", flightsItems.get(i).getDepartureDateTime(), flightsItems.get(i).getArrivalDateTime()));
        if (flightsItems.get(i).isMultiple()) {
            holder.txtFlightCode.setText(String.format("Via- %s", flightsItems.get(i).getVia()));
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

        if (FlightSearchActivity.FIRST_POS == i) {
            holder.cardLay.setBackgroundResource(R.drawable.line_border);
        } else {
            holder.cardLay.setBackgroundResource(0);
        }


        Glide.with(mContext).load("https://images.kiwi.com/airlines/64/" + flightsItems.get(i).getAirlineCode() + ".png").
                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        .placeholder(R.drawable.flight)
                        .error(R.drawable.flight))
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
        @BindView(R.id.cardLay)
        ConstraintLayout cardLay;

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
