package com.shoppingbag.utilities.bus.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.shoppingbag.model.response.bus_response.responsepojo.DroppingPointsItem;
import com.shoppingbag.retrofit.MvpView;
import com.shoppingbag.R;
import com.shoppingbag.utilities.bus.BusBoardingPoint;

import java.util.List;

public class DroppingingPointAdapter extends RecyclerView.Adapter<DroppingingPointAdapter.ViewHolder> {

    private List<DroppingPointsItem> droppingPointsItems;
    private Context mContext;
    private MvpView mvp;

    public DroppingingPointAdapter(Context context, List<DroppingPointsItem> droppingPointsItems, MvpView mvp) {
        mContext = context;
        this.droppingPointsItems = droppingPointsItems;
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
        if (droppingPointsItems != null) {
            holder.place.setText(droppingPointsItems.get(listPosition).getPlace());
            holder.time.setText(droppingPointsItems.get(listPosition).getTime());
            holder.address.setText(droppingPointsItems.get(listPosition).getAddress());
        } else {
            holder.place.setText(((BusBoardingPoint) mContext).destinationName);
            holder.time.setText(((BusBoardingPoint) mContext).departTimeStr);
        }
    }

    @Override
    public int getItemCount() {
        int size;
        if (droppingPointsItems != null) {
            size = droppingPointsItems.size();
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

            cv_point.setOnClickListener(v ->
            {
                if (droppingPointsItems != null) {
                    mvp.getClickPosition(getAdapterPosition(), droppingPointsItems.get(getAdapterPosition()).getDroppingId());
                } else {
                    mvp.getClickPosition(getAdapterPosition(), "");
                }

            });
        }
    }

}