package com.shoppingbag.one_india_shopping.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.shoppingbag.BuildConfig;
import com.shoppingbag.R;
import com.shoppingbag.one_india_shopping.activity.ShopIndMyProductDescription;
import com.shoppingbag.one_india_shopping.model.dashboard.BestDealItem;
import com.shoppingbag.retrofit.MvpView;
import com.shoppingbag.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IndDealsOneAdapter extends RecyclerView.Adapter<IndDealsOneAdapter.MyViewHolder> {
    private Activity context;
    private List<BestDealItem> list;
    private MvpView mvpView;

    public IndDealsOneAdapter(Activity context, List<BestDealItem> bestDealItems, MvpView mvpView) {
        this.context = context;
        this.list = bestDealItems;
        this.mvpView = mvpView;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.productlist, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.txtProductname.setText(list.get(position).getName());
/*

        if (list.get(position).getOfferprice() != null && !list.get(position).getOfferprice().equalsIgnoreCase("")) {
            holder.txtFixedprice.setText(String.format("₹ %s", Utils.getValue(Double.parseDouble(list.get(position).getOfferprice()))));
            holder.txtFixedprice.setVisibility(View.VISIBLE);
            holder.txt_offerPrice.setText(String.format("₹ %s", Utils.getValue(list.get(position).getPrice())));
            holder.txtFixedprice.setPaintFlags(holder.txtFixedprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            holder.txt_offerPrice.setText(String.format("₹ %s", Utils.getValue(list.get(position).getPrice())));
            holder.txtFixedprice.setVisibility(View.GONE);
        }
*/


        if (list.get(position).getOfferprice() == null) {
            holder.txtFixedprice.setVisibility(View.GONE);
            holder.txt_offerPrice.setText(String.format("₹ %s", Utils.getValue(list.get(position).getPrice())));
        } else {
            holder.txtFixedprice.setText(String.format("₹ %s", Utils.getValue(list.get(position).getPrice())));
            holder.txtFixedprice.setPaintFlags(holder.txtFixedprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.txt_offerPrice.setText(String.format("₹ %s", Utils.getValue(Double.parseDouble(list.get(position).getOfferprice()))));
        }

        Glide.with(context).load(BuildConfig.BASE_Image_URL_OnestoreIndia + list.get(position).getGallery())
                .apply(new RequestOptions().placeholder(R.drawable.image_not_available)).into(holder.imgProduct);

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_product)
        ImageView imgProduct;
        @BindView(R.id.txt_productname)
        TextView txtProductname;
        @BindView(R.id.txt_offerPrice)
        TextView txt_offerPrice;
        @BindView(R.id.txt_fixedprice)
        TextView txtFixedprice;
        @BindView(R.id.cardview)
        CardView cardview;
        @BindView(R.id.btnAddtocart)
        Button btnAddtocart;
        @BindView(R.id.btnBuynow)
        Button btnBuynow;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            btnAddtocart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ShopIndMyProductDescription.class);

                    Bundle bundle = new Bundle();
                    bundle.putString("sku", list.get(getAdapterPosition()).getSku());
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
            btnBuynow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ShopIndMyProductDescription.class);

                    Bundle bundle = new Bundle();
                    bundle.putString("sku", list.get(getAdapterPosition()).getSku());
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
        }

        @OnClick(R.id.cardview)
        public void onViewClicked() {
            Intent intent = new Intent(context, ShopIndMyProductDescription.class);

            Bundle bundle = new Bundle();
            bundle.putString("sku", list.get(getAdapterPosition()).getSku());
            intent.putExtras(bundle);
            context.startActivity(intent);
        }
    }
}
