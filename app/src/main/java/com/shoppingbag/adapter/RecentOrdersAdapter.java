package com.shoppingbag.adapter;

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
import com.shoppingbag.model.business_dash_response.OrderItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.bumptech.glide.load.engine.DiskCacheStrategy.AUTOMATIC;

public class RecentOrdersAdapter extends RecyclerView.Adapter<RecentOrdersAdapter.MyViewHolder> {
    private Activity context;
    private List<OrderItem> list;

    public RecentOrdersAdapter(Activity context, List<OrderItem> list) {
        this.context = context;
        this.list = list;

    }


    @NonNull
    @Override
    public RecentOrdersAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recent_order_items, parent, false);
        return new RecentOrdersAdapter.MyViewHolder(view);
    }


    private void setProviderImage(String path, ImageView imageView) {
        Glide.with(context).load(path.replace("~", BuildConfig.BASE_URL_FORIMAGE))
                .apply(new RequestOptions().diskCacheStrategy(AUTOMATIC).placeholder(R.drawable.image_not_available)
                        .error(R.drawable.cross))
                .into(imageView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentOrdersAdapter.MyViewHolder holder, int listPosition) {


        holder.orderNo.setText(String.format("Order No %s", list.get(listPosition).getOrderId()));
        holder.amount.setText(String.format("₹ %s", list.get(listPosition).getAmount()));
        holder.date.setText(list.get(listPosition).getOrderDate());
        holder.type.setText("Type : "+list.get(listPosition).getType());
        holder.action_type.setText(list.get(listPosition).getActionType());
       // holder.rechargeNumber.setText(String.format("Recharge of %s Mobile %s", list.get(listPosition).getOperator(), list.get(listPosition).getMobileNo()));

       /* if (list.get(listPosition).getError().equalsIgnoreCase("0") && list.get(listPosition).getResult().equalsIgnoreCase("0")) {
            holder.status.setText("Your recharge is successful");
            holder.status.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        } else {
            holder.status.setText("Your recharge is failed");
            holder.status.setTextColor(context.getResources().getColor(R.color.red));
        }

        if (list.get(listPosition).getOperator().equalsIgnoreCase("Airtel") ||
                list.get(listPosition).getOperator().equalsIgnoreCase("AirtelLandlineRetails")) {
            setProviderImage(BuildConfig.BASE_URL_ICONS + "providers/airtel.png", holder.operatorName);
        } else if (list.get(listPosition).getOperator().equalsIgnoreCase("Idea")) {
            setProviderImage(BuildConfig.BASE_URL_ICONS + "providers/idea.png", holder.operatorName);
        } else if (list.get(listPosition).getOperator().equalsIgnoreCase("BSNLTopUp") ||
                list.get(listPosition).getOperator().equalsIgnoreCase("BSNLRetails") ||
                list.get(listPosition).getOperator().equalsIgnoreCase("Cellone")) {
            setProviderImage(BuildConfig.BASE_URL_ICONS + "providers/bsnl.png", holder.operatorName);
        } else if (list.get(listPosition).getOperator().equalsIgnoreCase("TATADocomoFlexi")) {
            setProviderImage(BuildConfig.BASE_URL_ICONS + "providers/tatadocomo.png", holder.operatorName);
        } else if (list.get(listPosition).getOperator().equalsIgnoreCase("Vodafone")) {
            setProviderImage(BuildConfig.BASE_URL_ICONS + "providers/vodafone.png", holder.operatorName);
        } else if (list.get(listPosition).getOperator().equalsIgnoreCase("TATADocomoSpecial")) {
            setProviderImage(BuildConfig.BASE_URL_ICONS + "providers/tatadocomo.png", holder.operatorName);
        } else if (list.get(listPosition).getOperator().equalsIgnoreCase("BSNLValiditySpecial")) {
            setProviderImage(BuildConfig.BASE_URL_ICONS + "providers/bsnl.png", holder.operatorName);
        } else if (list.get(listPosition).getOperator().equalsIgnoreCase("MTNLTopUP")) {
            setProviderImage(BuildConfig.BASE_URL_ICONS + "providers/mts.png", holder.operatorName);
        } else if (list.get(listPosition).getOperator().equalsIgnoreCase("MTNLValiditySpecial") ||
                list.get(listPosition).getOperator().equalsIgnoreCase("MTNLDelhiRetails") ||
                list.get(listPosition).getOperator().equalsIgnoreCase("MTNLMumbaiRetails")) {
            setProviderImage(BuildConfig.BASE_URL_ICONS + "providers/mts.png", holder.operatorName);
        } else if (list.get(listPosition).getOperator().equalsIgnoreCase("RelianceDigitalTV")) {
            setProviderImage(BuildConfig.BASE_URL_ICONS + "providers/reliancedigitaltv.png", holder.operatorName);
        } else if (list.get(listPosition).getOperator().equalsIgnoreCase("SUNTV")) {
            setProviderImage(BuildConfig.BASE_URL_ICONS + "providers/suntv.png", holder.operatorName);
        } else if (list.get(listPosition).getOperator().equalsIgnoreCase("VideoconD2H")) {
            setProviderImage(BuildConfig.BASE_URL_ICONS + "providers/videocondh.png", holder.operatorName);
        } else if (list.get(listPosition).getOperator().equalsIgnoreCase("DISHTV")) {
            setProviderImage(BuildConfig.BASE_URL_ICONS + "providers/dishtv.png", holder.operatorName);
        } else if (list.get(listPosition).getOperator().equalsIgnoreCase("Jio")) {
            setProviderImage(BuildConfig.BASE_URL_ICONS + "providers/jio.png", holder.operatorName);
        }*/


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.order_no)
        TextView orderNo;
        @BindView(R.id.amount)
        TextView amount;
        @BindView(R.id.date)
        TextView date;
        @BindView(R.id.type)
        TextView type;
        @BindView(R.id.action_type)
        TextView action_type;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
