package com.shoppingbag.one_india_shopping.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.shoppingbag.BuildConfig;
import com.shoppingbag.R;
import com.shoppingbag.one_india_shopping.model.myorderdetails.DataItem;
import com.shoppingbag.retrofit.MvpView;
import com.shoppingbag.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderDetailsAdapter extends RecyclerView.Adapter<OrderDetailsAdapter.MyViewHolder> {

    private Activity context;
    private List<DataItem> list;
    private MvpView mvpView;

    public OrderDetailsAdapter(Activity context, List<DataItem> dataItems, MvpView mvpView) {
        this.context = context;
        this.list = dataItems;
        this.mvpView = mvpView;
    }

    @NonNull
    @Override
    public OrderDetailsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_details_items, parent, false);
        return new OrderDetailsAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailsAdapter.MyViewHolder holder, int position) {
        Glide.with(context).load(BuildConfig.BASE_Image_URL_OnestoreIndia +list.get(position).getImage())
                .apply(new RequestOptions().placeholder(R.drawable.image_not_available)).into(holder.imgProductimage);
       holder.txtProductname.setText(list.get(position).getName());
        holder.tv_qty.setText("Qty : "+Utils.getValue(Double.parseDouble(list.get(position).getQty())));
        holder.tv_price.setText(String.format("₹ %s", Utils.getValue(Double.parseDouble(list.get(position).getPrice()))));
        if(list.get(position).getStatus().equalsIgnoreCase("pending")){
            holder.txtOrderstatus.setText("Success");
        }else {
            holder.txtOrderstatus.setText(list.get(position).getStatus());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.product_img)
        ImageView imgProductimage;
        @BindView(R.id.tv_product_name)
        TextView txtProductname;
        @BindView(R.id.tv_qty)
        TextView tv_qty;
        @BindView(R.id.tv_price)
        TextView tv_price;
        @BindView(R.id.txtstatus)
        TextView txtOrderstatus;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
