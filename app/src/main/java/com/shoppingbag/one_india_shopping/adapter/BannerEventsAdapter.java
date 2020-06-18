package com.shoppingbag.one_india_shopping.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import com.shoppingbag.model.response.mlmDashboardNew.BannersItem;
import com.shoppingbag.retrofit.MvpView;
import com.shoppingbag.R;
import com.shoppingbag.one_india_shopping.activity.ShopIndProductList;
import com.shoppingbag.one_india_shopping.activity.ShoppingIndiaActivity;
import com.shoppingbag.one_india_shopping.fragment.ShopIndDashboard;
import com.shoppingbag.one_india_shopping.model.dashboard.BannerItem;
import com.shoppingbag.one_india_shopping.model.dashboard.BestSellerItemItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BannerEventsAdapter extends RecyclerView.Adapter<BannerEventsAdapter.MyVIewHolder> {
    private Activity context;
    private List<BannerItem> list;
    private MvpView mvpView;

    public BannerEventsAdapter(Activity context, List<BannerItem> list, MvpView mvpView) {
        this.context = context;
        this.list = list;

    }

    @NonNull
    @Override
    public MyVIewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_bannerevents, parent, false);
        return new MyVIewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyVIewHolder holder, int position) {
        Glide.with(context).load(list.get(position).getUrl()).skipMemoryCache(true)
                .apply(new RequestOptions().placeholder(R.drawable.image_not_available)).fitCenter().into(holder.imageViewBanner);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class MyVIewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageViewBanner)
        ImageView imageViewBanner;

        public MyVIewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.imageViewBanner)
        public void onViewClicked() {
            if(getAdapterPosition() == 0){
                Bundle bundle = new Bundle();
                bundle.putString("name", "Up to 50% Off");
                bundle.putString("id", "151");
                ((ShoppingIndiaActivity)context).goToActivity(context,ShopIndProductList.class, bundle);
            }else if(getAdapterPosition() == 1){
                Bundle bundle = new Bundle();
                bundle.putString("name", "50% CASHBACK");
                bundle.putString("id", "152");
                ((ShoppingIndiaActivity)context).goToActivity(context,ShopIndProductList.class, bundle);
            }

        }
    }
}
