package com.shoppingbag.utilities.bus.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shoppingbag.R;
import com.shoppingbag.utilities.bus.BusAbstractItem;
import com.shoppingbag.utilities.domesticflight.seatAdapter.AbstractItem;
import com.shoppingbag.utilities.domesticflight.seatAdapter.OnSeatSelected;
import com.shoppingbag.utilities.domesticflight.seatAdapter.SelectableAdapter;

import java.util.List;

public class BusSeatAdapter extends SelectableAdapter<RecyclerView.ViewHolder> {

    public List<BusAbstractItem> mItems;
    private OnSeatSelected mOnSeatSelected;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public BusSeatAdapter(Context context, List<BusAbstractItem> items) {
        mOnSeatSelected = (OnSeatSelected) context;
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mItems = items;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mItems.get(position).getType();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == AbstractItem.TYPE_BLOCK) {
            View itemView = mLayoutInflater.inflate(R.layout.list_item_bus_seat, parent, false);
            return new BusSeatAdapter.BlockedViewHolder(itemView);
        } else if (viewType == AbstractItem.TYPE_BOOKED) {
            View itemView = mLayoutInflater.inflate(R.layout.list_item_bus_seat, parent, false);
            return new BusSeatAdapter.BookViewHolder(itemView);
        } else if (viewType == AbstractItem.TYPE_AVAILABLE) {
            View itemView = mLayoutInflater.inflate(R.layout.list_item_bus_seat, parent, false);
            return new AvailableViewHolder(itemView);
        } else {
            View itemView = new View(mContext);
            return new BusSeatAdapter.EmptyViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, final int position) {
        int type = mItems.get(position).getType();

        if (type == AbstractItem.TYPE_BLOCK) {
            BusSeatAdapter.BlockedViewHolder holder = (BusSeatAdapter.BlockedViewHolder) viewHolder;
            if (mItems.get(position).getBirthType() != null && mItems.get(position).getBirthType().equalsIgnoreCase("U")) {
                holder.imgSeat.setImageResource(R.drawable.ic_bus_sleeper_booked);
            } else {
                holder.imgSeat.setImageResource(R.drawable.ic_bus_seater_booked);
            }

        } else if (type == AbstractItem.TYPE_BOOKED) {
            BusSeatAdapter.BookViewHolder holder = (BusSeatAdapter.BookViewHolder) viewHolder;
            if (mItems.get(position).getBirthType() != null && mItems.get(position).getBirthType().equalsIgnoreCase("U")) {
                holder.imgSeat.setImageResource(R.drawable.ic_bus_sleeper_booked);
            } else {
                holder.imgSeat.setImageResource(R.drawable.ic_bus_seater_booked);
            }

        } else if (type == AbstractItem.TYPE_AVAILABLE) {
            final AvailableViewHolder holder = (AvailableViewHolder) viewHolder;
            if (mItems.get(position).getBirthType() != null && mItems.get(position).getBirthType().equalsIgnoreCase("U")) {
                holder.imgSeat.setImageResource(isSelected(position) ? R.drawable.ic_bus_sleeper_select : R.drawable.ic_bus_sleeper);
            } else {
                holder.imgSeat.setImageResource(isSelected(position) ? R.drawable.ic_bus_seater_select : R.drawable.ic_bus_seater);
            }
        }
    }

    private class AvailableViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imgSeat;

        AvailableViewHolder(View itemView) {
            super(itemView);
            imgSeat = itemView.findViewById(R.id.img_seat);
            imgSeat.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (isSelected(getAdapterPosition())) {
                toggleSelection(getAdapterPosition());
                mOnSeatSelected.onSeatSelected(getSelectedItemCount(), getAdapterPosition());
            } else {
                toggleSelection(getAdapterPosition());
                mOnSeatSelected.onSeatSelected(getSelectedItemCount(), getAdapterPosition());
            }
        }
    }

    private class BookViewHolder extends RecyclerView.ViewHolder {
        ImageView imgSeat;

        BookViewHolder(View itemView) {
            super(itemView);
            imgSeat = itemView.findViewById(R.id.img_seat);
        }
    }

    private class BlockedViewHolder extends RecyclerView.ViewHolder {
        ImageView imgSeat;

        BlockedViewHolder(View itemView) {
            super(itemView);
            imgSeat = itemView.findViewById(R.id.img_seat);
        }
    }

    private class EmptyViewHolder extends RecyclerView.ViewHolder {
        EmptyViewHolder(View itemView) {
            super(itemView);
        }
    }

}
