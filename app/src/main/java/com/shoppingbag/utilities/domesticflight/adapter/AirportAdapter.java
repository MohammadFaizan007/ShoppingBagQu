package com.shoppingbag.utilities.domesticflight.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shoppingbag.R;
import com.shoppingbag.utilities.domesticflight.responsemodel.AirlineCodeItem;
import com.shoppingbag.utilities.fragment.AirlinesClick;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AirportAdapter extends RecyclerView.Adapter<AirportAdapter.SingleItemRowHolder> implements AirlinesClick {

    private Context mContext;
    private ArrayList<AirlineCodeItem> airportLists;

    protected AirportAdapter(Context context, ArrayList<AirlineCodeItem> airportLists) {
        this.mContext = context;
        this.airportLists = airportLists;
    }

    public void changeList(ArrayList<AirlineCodeItem> airportLists) {
        this.airportLists = airportLists;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SingleItemRowHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.airport_item, null);
        return new SingleItemRowHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull SingleItemRowHolder holder, int i) {

        holder.airportCode.setText(airportLists.get(i).getCode());
        holder.airportCity.setText(String.format("(%s)", airportLists.get(i).getCity()));
        holder.airportStateCountry.setText(String.format("%s,%s", airportLists.get(i).getName(), airportLists.get(i).getCountry()));
    }

    @Override
    public int getItemCount() {
        return airportLists.size();
    }


    @Override
    public void clickAirlines(AirlineCodeItem item) {

    }

    class SingleItemRowHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.airportCode)
        TextView airportCode;
        @BindView(R.id.airport_state_country)
        TextView airportStateCountry;
        @BindView(R.id.airportCity)
        TextView airportCity;

        SingleItemRowHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickAirlines(airportLists.get(getAdapterPosition()));
        }
    }

}
