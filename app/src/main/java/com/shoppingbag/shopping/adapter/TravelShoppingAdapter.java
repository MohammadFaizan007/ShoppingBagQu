package com.shoppingbag.shopping.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.shoppingbag.BuildConfig;
import com.shoppingbag.R;
import com.shoppingbag.model.response.shopping.LstINRItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.bumptech.glide.load.engine.DiskCacheStrategy.AUTOMATIC;

public class TravelShoppingAdapter extends RecyclerView.Adapter<TravelShoppingAdapter.MyViewHolder> {
    private Context context;


    public TravelShoppingAdapter(Context mContext) {
        this.context = mContext;

    }

    @NonNull
    @Override
    public TravelShoppingAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_travel_row, parent, false);
        return new TravelShoppingAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TravelShoppingAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

    }



    @Override
    public int getItemCount() {
        return 8;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {


        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
