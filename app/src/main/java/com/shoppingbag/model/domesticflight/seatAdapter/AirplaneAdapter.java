package com.shoppingbag.model.domesticflight.seatAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

import com.shoppingbag.R;

public class AirplaneAdapter extends SelectableAdapter<RecyclerView.ViewHolder> {
    int mSelection;
    private OnSeatSelected mOnSeatSelected;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<AbstractItem> mItems;

    public AirplaneAdapter(Context context, List<AbstractItem> items, int selection) {
        mOnSeatSelected = (OnSeatSelected) context;
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mItems = items;
        mSelection = selection;
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
            View itemView = mLayoutInflater.inflate(R.layout.list_item_seat, parent, false);
            return new BlockedViewHolder(itemView);
        } else if (viewType == AbstractItem.TYPE_BOOKED) {
            View itemView = mLayoutInflater.inflate(R.layout.list_item_seat, parent, false);
            return new BookViewHolder(itemView);
        } else if (viewType == AbstractItem.TYPE_AVAILABLE) {
            View itemView = mLayoutInflater.inflate(R.layout.list_item_seat, parent, false);
            return new AvaliableViewHolder(itemView);
        } else {
            View itemView = new View(mContext);
            return new EmptyViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, final int position) {
        int type = mItems.get(position).getType();

        if (type == AbstractItem.TYPE_BLOCK) {
            BlockedViewHolder holder = (BlockedViewHolder) viewHolder;
            holder.imgSeat.setImageResource(R.drawable.seat_normal_booked);

        } else if (type == AbstractItem.TYPE_BOOKED) {
//            final CenterItem item = (CenterItem) mItems.get(position);
            BookViewHolder holder = (BookViewHolder) viewHolder;
            holder.imgSeat.setImageResource(R.drawable.seat_normal_booked);

        } else if (type == AbstractItem.TYPE_AVAILABLE) {

//            final EdgeItem item = (EdgeItem) mItems.get(position);
            final AvaliableViewHolder holder = (AvaliableViewHolder) viewHolder;
            holder.imgSeat.setImageResource(isSelected(position) ? R.drawable.seat_normal_selected : R.drawable.seat_normal);
        }
    }

    private class AvaliableViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imgSeat;

        AvaliableViewHolder(View itemView) {
            super(itemView);
            imgSeat = itemView.findViewById(R.id.img_seat);
            imgSeat.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (getSelectedItemCount() < mSelection) {
                toggleSelection(getAdapterPosition());
                mOnSeatSelected.onSeatSelected(getSelectedItemCount(), getAdapterPosition());
            } else if (isSelected(getAdapterPosition())) {
                toggleSelection(getAdapterPosition());
                mOnSeatSelected.onSeatSelected(getSelectedItemCount(), getAdapterPosition());
            } else {
                Toast.makeText(mContext, "Not select more than " + mSelection + " people.", Toast.LENGTH_SHORT).show();
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
