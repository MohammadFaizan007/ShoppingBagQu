package com.shoppingbag.billpayment.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.shoppingbag.BuildConfig;
import com.shoppingbag.R;
import com.shoppingbag.model.bill.BillPaymentProviderListItem;
import com.shoppingbag.retrofit.MvpView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GasWaterAdapter extends RecyclerView.Adapter<GasWaterAdapter.ViewHolder> {


    private Context mContext;
    private MvpView mvpView;
    private List<BillPaymentProviderListItem> listItem;


    public GasWaterAdapter(Activity context, MvpView view, List<BillPaymentProviderListItem> allElectricityProviderlist) {

        mContext = context;
        mvpView = view;
        listItem = allElectricityProviderlist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.board_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int listPosition) {
        holder.label.setText(listItem.get(listPosition).getProviderName());
        Glide.with(mContext).load(listItem.get(listPosition).getImageURL().replace("~", BuildConfig.BASE_URL_FORIMAGE)).
                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
                        .placeholder(R.mipmap.ic_launcher_round)
                        .error(R.mipmap.ic_launcher_round))
                .into(holder.provider_image);
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.label)
        TextView label;
        @BindView(R.id.provider_image)
        ImageView provider_image;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mvpView.getProviderHint(listItem.get(getAdapterPosition()).getProviderName(), listItem.get(getAdapterPosition()).getNumber() + " " +
                            listItem.get(getAdapterPosition()).getFormatofNumber());
                }
            });
        }
    }
}
