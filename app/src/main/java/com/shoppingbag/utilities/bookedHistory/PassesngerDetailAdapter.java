package com.shoppingbag.utilities.bookedHistory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.shoppingbag.model.bookedHistory.responseModel.CancelPassengerDetailsItem;
import com.shoppingbag.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PassesngerDetailAdapter extends RecyclerView.Adapter<PassesngerDetailAdapter.ViewHolder> {

    private Context context;
    private List<CancelPassengerDetailsItem> list;


    PassesngerDetailAdapter(Context context, List<CancelPassengerDetailsItem> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.partial_cancel_item_lay, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.txtName.setText(String.format("%s %s", list.get(position).getFirstName(), list.get(position).getLastName()));
        holder.txtType.setText(String.format("(%s)", list.get(position).getPassengerType()));

        holder.flightlist.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        holder.flightlist.setAdapter(new PartialCancelFlightAdapter(context, list.get(position).getCancelTicketDetails()));
        holder.flightlist.setNestedScrollingEnabled(false);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtName)
        TextView txtName;
        @BindView(R.id.txtType)
        TextView txtType;
        @BindView(R.id.flightlist)
        RecyclerView flightlist;


        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);


        }

    }

}