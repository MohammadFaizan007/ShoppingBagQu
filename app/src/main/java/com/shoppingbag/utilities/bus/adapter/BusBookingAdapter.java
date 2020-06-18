package com.shoppingbag.utilities.bus.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shoppingbag.model.response.bus_response.bookingHistory.BookedTicketsItem;
import com.shoppingbag.utils.Utils;
import com.shoppingbag.R;
import com.shoppingbag.utilities.bus.BusBookingDetail;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.shoppingbag.app.AppConfig.PAYLOAD_BUNDLE;

public class BusBookingAdapter extends RecyclerView.Adapter<BusBookingAdapter.ViewHolder> {

    private List<BookedTicketsItem> busesItems;
    private Context mContext;
    private Bundle mParam;

    public BusBookingAdapter(Context context, List<BookedTicketsItem> busesItems, Bundle param) {
        mContext = context;
        this.busesItems = busesItems;
        this.mParam = param;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_bus_bookings, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int listPosition) {

        holder.tvTravelDate.setText(Utils.getDateOnly(busesItems.get(listPosition).getTravelDate()));
        holder.tvTravelMonth.setText(Utils.getMonthYear(busesItems.get(listPosition).getTravelDate()));
        holder.tvFromTo.setText(String.format("%s - %s", busesItems.get(listPosition).getOrigin(), busesItems.get(listPosition).getDestination()));
        holder.tvTraveller.setText(busesItems.get(listPosition).getTransportName());
        String amoount = busesItems.get(listPosition).getAmount();
        amoount = amoount.replace(" (WC:0.00)", "");
        holder.tvAmount.setText(String.format("₹%s", amoount));
        holder.tvStatus.setText(busesItems.get(listPosition).getTicketStatus());

        if (busesItems.get(listPosition).getTicketStatus().contains("CANCEL"))
            holder.tvStatus.setTextColor(mContext.getResources().getColor(R.color.red));
        else
            holder.tvStatus.setTextColor(mContext.getResources().getColor(R.color.dark_green));


    }

    @Override
    public int getItemCount() {
        return busesItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_travel_date)
        TextView tvTravelDate;
        @BindView(R.id.tv_travel_month)
        TextView tvTravelMonth;
        @BindView(R.id.tv_from_to)
        TextView tvFromTo;
        @BindView(R.id.tv_traveller)
        TextView tvTraveller;
        @BindView(R.id.tv_amount)
        TextView tvAmount;
        @BindView(R.id.tv_status)
        TextView tvStatus;


        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(v -> {

                Bundle param = new Bundle();
                param.putString("TransactionId", busesItems.get(getAdapterPosition()).getTransactionId());
                param.putString("TransportId", busesItems.get(getAdapterPosition()).getTransportId());
                param.putString("TicketStatus", busesItems.get(getAdapterPosition()).getTicketStatus());
                Intent intent = new Intent(mContext, BusBookingDetail.class);
                intent.putExtra(PAYLOAD_BUNDLE, param);
                mContext.startActivity(intent);
                ((Activity) mContext).overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            });

        }
    }

}