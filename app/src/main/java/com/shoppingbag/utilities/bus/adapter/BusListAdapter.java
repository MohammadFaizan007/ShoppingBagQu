package com.shoppingbag.utilities.bus.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shoppingbag.model.response.bus_response.AvailableBusesItem;
import com.shoppingbag.R;
import com.shoppingbag.utilities.bus.BusSeatMap;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.shoppingbag.app.AppConfig.PAYLOAD_BUNDLE;

public class BusListAdapter extends RecyclerView.Adapter<BusListAdapter.ViewHolder> {

    private List<AvailableBusesItem> busesItems;
    private Context mContext;
    private Bundle mParam;

    public BusListAdapter(Context context, List<AvailableBusesItem> busesItems, Bundle param) {
        mContext = context;
        this.busesItems = busesItems;
        this.mParam = param;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bus_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int listPosition) {

        holder.txtTransportName.setText(busesItems.get(listPosition).getTransportName());
        holder.txtBusType.setText(String.format("(%s)", busesItems.get(listPosition).getBusType()));
        holder.txtDeptTime.setText(busesItems.get(listPosition).getDepartureTime());
        holder.txtSeatType.setText(busesItems.get(listPosition).getFares().get(0).getSeatType());
        holder.txtArrivalTime.setText(busesItems.get(listPosition).getArrivalTime());
        holder.txtAvlSeat.setText(String.format("%s seats", busesItems.get(listPosition).getAvailableSeatCount()));
        holder.txtTotalHour.setText("NA");
        holder.txtFare.setText(String.format("Fare: ₹%s", busesItems.get(listPosition).getFares().get(0).getFare()));

    }

    @Override
    public int getItemCount() {
        return busesItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txtTransportName)
        TextView txtTransportName;
        @BindView(R.id.txtBusType)
        TextView txtBusType;
        @BindView(R.id.txtDeptTime)
        TextView txtDeptTime;
        @BindView(R.id.txtSeatType)
        TextView txtSeatType;
        @BindView(R.id.txtArrivalTime)
        TextView txtArrivalTime;
        @BindView(R.id.txtAvlSeat)
        TextView txtAvlSeat;
        @BindView(R.id.txtTotalHour)
        TextView txtTotalHour;
        @BindView(R.id.txtFare)
        TextView txtFare;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(v -> {

                Bundle param = new Bundle();
                param.putString("originName", mParam.getString("originName"));
                param.putString("destinationName", mParam.getString("destinationName"));
                param.putString("travelDate", mParam.getString("TravelDate"));
                param.putString("userTrackId", mParam.getString("UserTrackId"));
                param.putString("scheduleId", busesItems.get(getAdapterPosition()).getScheduleId());
                param.putString("stationId", busesItems.get(getAdapterPosition()).getStationId());
                param.putString("transportId", busesItems.get(getAdapterPosition()).getTransportId());
                param.putString("ArrivalTime", busesItems.get(getAdapterPosition()).getArrivalTime());
                param.putString("DepartureTime", busesItems.get(getAdapterPosition()).getDepartureTime());

                Intent intent = new Intent(mContext, BusSeatMap.class);
                intent.putExtra(PAYLOAD_BUNDLE, param);
                mContext.startActivity(intent);
            });

        }
    }

}