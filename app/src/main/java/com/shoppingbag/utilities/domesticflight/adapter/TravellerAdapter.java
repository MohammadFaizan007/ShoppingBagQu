package com.shoppingbag.utilities.domesticflight.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.shoppingbag.R;
import com.shoppingbag.utilities.fragment.RecyclerClick;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TravellerAdapter extends RecyclerView.Adapter<TravellerAdapter.SingleItemRowHolder> implements RecyclerClick {

    private Context mContext;
    private int selectPosition = 0;
    private int travellerNo;

    public TravellerAdapter(Context context, int noOfTraveller) {
        this.mContext = context;
        travellerNo = noOfTraveller;
    }

    public int getNoOfTraveller() {
        return selectPosition + 1;
    }

    public void setNoOfTraveller(int noOfTraveller) {
        selectPosition = noOfTraveller - 1;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SingleItemRowHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.traveller_item_lay, null);
        return new SingleItemRowHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull SingleItemRowHolder holder, int i) {

        if (selectPosition == i) {
            holder.txtNumber.setTextSize(22);
            holder.txtNumber.setTextColor(ContextCompat.getColor(mContext, R.color.text_color));
        } else {
            holder.txtNumber.setTextSize(14);
            holder.txtNumber.setTextColor(ContextCompat.getColor(mContext, R.color.text_color));
        }

        holder.txtNumber.setText(String.format(Locale.getDefault(), "%d", i + 1));

    }

    @Override
    public int getItemCount() {
        return travellerNo;
    }

    @Override
    public void click() {

    }

    class SingleItemRowHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.txtNumber)
        TextView txtNumber;

        SingleItemRowHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            selectPosition = getAdapterPosition();
            click();
            notifyDataSetChanged();
        }
    }

}
