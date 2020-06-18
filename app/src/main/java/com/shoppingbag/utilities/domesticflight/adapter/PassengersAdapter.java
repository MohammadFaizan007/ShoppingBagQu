package com.shoppingbag.utilities.domesticflight.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shoppingbag.model.domesticflight.responsemodel.PassengerDetailsItem;
import com.shoppingbag.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PassengersAdapter extends RecyclerView.Adapter<PassengersAdapter.PassengerHolder> {

    private Context context;
    private List<PassengerDetailsItem> passengerDetail;

    public PassengersAdapter(Context context, List<PassengerDetailsItem> passengerDetail) {
        this.context = context;
        this.passengerDetail = passengerDetail;
    }

    @NonNull
    @Override
    public PassengerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.passenger_adapter, parent, false);
        return new PassengerHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull PassengerHolder holder, int position) {
        holder.passengerName.setText(passengerDetail.get(position).getFirstName() + " " + passengerDetail.get(position).getLastName());
        holder.passengerType.setText("( " + passengerDetail.get(position).getPassengerType() + ")");
        holder.ticketNumber.setText("Ticket No : " + passengerDetail.get(position).getTicketNumber());

    }

    @Override
    public int getItemCount() {
        return passengerDetail.size();
    }

    public class PassengerHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.passengerName)
        TextView passengerName;

        @BindView(R.id.ticketNumber)
        TextView ticketNumber;

        @BindView(R.id.passengerType)
        TextView passengerType;


        public PassengerHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
