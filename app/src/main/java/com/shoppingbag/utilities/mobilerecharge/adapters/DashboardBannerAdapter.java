package com.shoppingbag.utilities.mobilerecharge.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shoppingbag.utils.BannerLayout;
import com.shoppingbag.R;

import java.util.List;

public class DashboardBannerAdapter extends RecyclerView.Adapter<DashboardBannerAdapter.MzViewHolder> {

    private Context context;
    private List<Integer> urlList;
    private BannerLayout.OnBannerItemClickListener onBannerItemClickListener;

    public DashboardBannerAdapter(Context context, List<Integer> urlList) {
        this.context = context;
        this.urlList = urlList;
    }

    public void setOnBannerItemClickListener(BannerLayout.OnBannerItemClickListener onBannerItemClickListener) {
        this.onBannerItemClickListener = onBannerItemClickListener;
    }

    @Override
    public DashboardBannerAdapter.MzViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MzViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.dashboard_banner_item_image, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DashboardBannerAdapter.MzViewHolder holder, final int position) {
        if (urlList == null || urlList.isEmpty())
            return;
        final int P = position % urlList.size();
        Integer url = urlList.get(P);
        ImageView img = holder.imageView;
        img.setImageResource(url);
        img.setOnClickListener(v -> {
            if (onBannerItemClickListener != null) {
                onBannerItemClickListener.onItemClick(P);
            }

        });
    }

    @Override
    public int getItemCount() {
        if (urlList != null) {
            return urlList.size();
        }
        return 0;
    }


    class MzViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        MzViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
        }
    }

}