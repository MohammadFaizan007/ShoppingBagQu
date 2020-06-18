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
import com.shoppingbag.one_india_shopping.model.ProductListItem;
import com.shoppingbag.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IndProductListAdapter extends RecyclerView.Adapter<IndProductListAdapter.MyViewHolder> {
    private Activity context;
    private List<ProductListItem> listItems;

    public IndProductListAdapter(Activity context, List<ProductListItem> listItems) {
        this.context = context;
        this.listItems = listItems;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.productlist, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String check = String.valueOf(listItems.get(position).getStatus());
        if (check.equalsIgnoreCase("1")) {
           /* if (listItems.get(position).getOfferprice() != null&&!listItems.get(position).getOfferprice().equalsIgnoreCase("")) {
                holder.txtFixedprice.setPaintFlags(holder.txtFixedprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                holder.txtFixedprice.setText(String.format("₹ %s", listItems.get(position).getPrice()));
                holder.txtFinalprice.setVisibility(View.VISIBLE);
                holder.txtFinalprice.setText(String.format("₹ %s", Utils.getValue(Double.parseDouble(listItems.get(position).getOfferprice()))));
            } else {
                holder.txtFixedprice.setText(String.format("₹ %s", listItems.get(position).getPrice()));
                holder.txtFinalprice.setVisibility(View.GONE);

            }*/

            if (listItems.get(position).getOfferprice() == null) {
                holder.txtFixedprice.setVisibility(View.GONE);
                holder.txtFinalprice.setText(String.format("₹ %s", Utils.getValue(listItems.get(position).getPrice())));
            } else {
                holder.txtFixedprice.setText(String.format("₹ %s", Utils.getValue(listItems.get(position).getPrice())));
                holder.txtFixedprice.setPaintFlags(holder.txtFixedprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                holder.txtFinalprice.setText(String.format("₹ %s", Utils.getValue(Double.parseDouble(listItems.get(position).getOfferprice()))));
            }
            holder.txtProductname.setSelected(true);
            holder.txtProductname.setText(listItems.get(position).getName());
            Glide.with(context).load(BuildConfig.BASE_Image_URL_OnestoreIndia + listItems.get(position).getGallery())
                    .apply(new RequestOptions().placeholder(R.drawable.image_not_available)).into(holder.imgProduct);

        }


    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public void updatelist(List<ProductListItem> listItems) {
        this.listItems = listItems;
        notifyDataSetChanged();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_product)
        ImageView imgProduct;
        @BindView(R.id.txt_productname)
        TextView txtProductname;
        @BindView(R.id.txt_offerPrice)
        TextView txtFinalprice;
        @BindView(R.id.txt_fixedprice)
        TextView txtFixedprice;
        @BindView(R.id.cardview)
        CardView cardview;
        @BindView(R.id.btnAddtocart)
        Button btnAddtocart;
        @BindView(R.id.btnBuynow)
        Button btnBuynow;
       /* @BindView(R.id.img_like)
        LikeButton imgLike;*/

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            btnAddtocart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ShopIndMyProductDescription.class);

                    Bundle bundle = new Bundle();
                    bundle.putString("sku", listItems.get(getAdapterPosition()).getSku());
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
            btnBuynow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ShopIndMyProductDescription.class);

                    Bundle bundle = new Bundle();
                    bundle.putString("sku", listItems.get(getAdapterPosition()).getSku());
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
           
            cardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent in = new Intent(context, ShopIndProductDescription.class);
//                    bundle.putString("sku",listItems.get(getAdapterPosition()).getSku());
//                    context.startActivity(in);
                    Intent intent = new Intent(context, ShopIndMyProductDescription.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("sku", listItems.get(getAdapterPosition()).getSku());
                    intent.putExtras(bundle);
                    context.startActivity(intent);

                }
            });
        }
    }
}
