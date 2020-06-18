package com.shoppingbag.utilities.bus.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.shoppingbag.model.response.bus_response.responsepojo.BoardingPointsItem;
import com.shoppingbag.retrofit.MvpView;
import com.shoppingbag.R;
import com.shoppingbag.utilities.bus.BusBoardingPoint;

import java.util.List;

public class BoardingPointAdapter extends RecyclerView.Adapter<BoardingPointAdapter.ViewHolder> {

    private List<BoardingPointsItem> boardingPointsItems;
    private Context mContext;
    private MvpView mvp;

    public BoardingPointAdapter(Context context, List<BoardingPointsItem> boardingPointsItems, MvpView mvp) {
        mContext = context;
        this.boardingPointsItems = boardingPointsItems;
        this.mvp = mvp;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.boarding_point_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int listPosition) {
        if (boardingPointsItems != null) {
            holder.place.setText(boardingPointsItems.get(listPosition).getPlace());
            holder.time.setText(boardingPointsItems.get(listPosition).getTime());
            holder.address.setText(boardingPointsItems.get(listPosition).getAddress());
        } else {
            holder.place.setText(((BusBoardingPoint) mContext).originName);
            holder.time.setText(((BusBoardingPoint) mContext).arrivalTimeStr);
        }
    }

    @Override
    public int getItemCount() {
        int size;
        if (boardingPointsItems != null) {
            size = boardingPointsItems.size();
        } else {
            size = 1;
        }
        return size;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView place, time, address;
        CardView cv_point;

        ViewHolder(View view) {
            super(view);
            place = view.findViewById(R.id.place);
            time = view.findViewById(R.id.time);
            address = view.findViewById(R.id.address);
            cv_point = view.findViewById(R.id.cv_point);

            cv_point.setOnClickListener(v -> mvp.getClickPosition(getAdapterPosition(), boardingPointsItems.get(getAdapterPosition()).getBoardingId()));
        }
    }

}