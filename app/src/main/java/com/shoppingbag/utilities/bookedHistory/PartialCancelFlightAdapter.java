package com.shoppingbag.utilities.bookedHistory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shoppingbag.model.bookedHistory.responseModel.CancelTicketDetailsItem;
import com.shoppingbag.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PartialCancelFlightAdapter extends RecyclerView.Adapter<PartialCancelFlightAdapter.ViewHolder> {

    private Context context;
    private List<CancelTicketDetailsItem> list;

    PartialCancelFlightAdapter(Context context, List<CancelTicketDetailsItem> list) {
        this.context = context;
        this.list = list;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.partial_cancel_flight_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.txtTicketNo.setText(list.get(position).getTicketNumber());
        holder.txtFlightCode.setText(String.format("(%s)", list.get(position).getFlightNumber()));
        holder.txtOrigin.setText(list.get(position).getOrigin());
        holder.txtDestination.setText(list.get(position).getDestination());
        holder.txtGrossAmount.setText(String.valueOf(list.get(position).getGrossAmount()));
        holder.txtTicketStatus.setText(list.get(position).getTicketStatus());

        if (list.get(position).isItemClick()) {
            holder.imgCheck.setImageResource(android.R.drawable.checkbox_on_background);
        } else {
            holder.imgCheck.setImageResource(android.R.drawable.checkbox_off_background);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.txtTicketNo)
        TextView txtTicketNo;
        @BindView(R.id.txtFlightCode)
        TextView txtFlightCode;
        @BindView(R.id.txtOrigin)
        TextView txtOrigin;
        @BindView(R.id.txtDestination)
        TextView txtDestination;
        @BindView(R.id.txtGrossAmount)
        TextView txtGrossAmount;
        @BindView(R.id.imgCheck)
        ImageView imgCheck;
        @BindView(R.id.txtTicketStatus)
        TextView txtTicketStatus;


        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            imgCheck.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.imgCheck:
                    if (list.get(getAdapterPosition()).isItemClick()) {
                        list.get(getAdapterPosition()).setItemClick(false);
                    } else {
                        list.get(getAdapterPosition()).setItemClick(true);
                    }
                    notifyItemChanged(getAdapterPosition());
                    break;
            }
        }
    }

}
