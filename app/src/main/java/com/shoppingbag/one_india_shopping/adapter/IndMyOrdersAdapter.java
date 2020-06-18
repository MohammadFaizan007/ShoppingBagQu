package com.shoppingbag.one_india_shopping.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import com.shoppingbag.retrofit.MvpView;
import com.shoppingbag.utils.Utils;
import com.shoppingbag.R;
import com.shoppingbag.one_india_shopping.model.myorder.DataItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IndMyOrdersAdapter extends RecyclerView.Adapter<IndMyOrdersAdapter.MyViewHolder> {

    private Activity context;
    private List<DataItem> list;
    private MvpView mvpView;

    public IndMyOrdersAdapter(Activity context, List<DataItem> dataItems, MvpView mvpView) {
        this.context = context;
        this.list = dataItems;
        this.mvpView = mvpView;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ind_myorders_items, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.txtProductname.setText(list.get(position).getName());
        holder.txtOrderamount.setText(String.format("₹ %s", Utils.getValue(Double.parseDouble(list.get(position).getSubtotal()))));
        holder.txtOrderdate.setText(list.get(position).getCreatedAt());
        holder.txtOrderid.setText(String.format("Order Id: %s", list.get(position).getEntityId()));
        if (list.get(position).getStatus().equalsIgnoreCase("pending")) {
            holder.txtOrderstatus.setText("Success");
        } else {
            holder.txtOrderstatus.setText(list.get(position).getStatus());
        }
        /*try {
            if (list.get(position).getValue() != null) {
                Glide.with(context).load(BuildConfig.BASE_Image_URL_OnestoreIndia + list.get(position)
                        .getValue()).apply(new RequestOptions().placeholder(R.drawable.image_not_available)).into(holder.imgProductimage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_productimage)
        ImageView imgProductimage;
        @BindView(R.id.txt_productname)
        TextView txtProductname;
        @BindView(R.id.txt_orderid)
        TextView txtOrderid;
        @BindView(R.id.txt_orderdate)
        TextView txtOrderdate;
        @BindView(R.id.txt_orderamount)
        TextView txtOrderamount;
        @BindView(R.id.txt_orderstatus)
        TextView txtOrderstatus;
        @BindView(R.id.card)
        CardView card;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.card)
        public void onViewClicked() {
            mvpView.getMyClickPosition(list.get(getAdapterPosition()).getEntityId(), "");
        }
    }
}
