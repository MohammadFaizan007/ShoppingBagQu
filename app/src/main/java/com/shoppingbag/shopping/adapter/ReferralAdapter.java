package com.shoppingbag.shopping.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.shoppingbag.model.referral.DataItem;
import com.shoppingbag.model.response.notification.NotificationListItem;
import com.shoppingbag.R;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.bumptech.glide.load.engine.DiskCacheStrategy.AUTOMATIC;

public class ReferralAdapter extends RecyclerView.Adapter<ReferralAdapter.MyViewHolder> {
    List<DataItem> list;
    private Context context;

    public ReferralAdapter(Context context, List<DataItem> notificationList) {
        list = notificationList;
        this.context = context;
    }

    @NonNull
    @Override
    public ReferralAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.referral_row, parent, false);
        return new ReferralAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ReferralAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.txtSubject.setText("Name : "+list.get(position).getMemberName());
        holder.txtBody.setText("Total Referrals : "+String.valueOf(list.get(position).getTotalReferal()));
       // holder.txtDate.setText(String.format("%s %s", list.get(position).getNotificationDate(), list.get(position).getNotificationTime()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }




    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txtSubject)
        TextView txtSubject;
        @BindView(R.id.txtBody)
        TextView txtBody;
        @BindView(R.id.txtDate)
        TextView txtDate;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }
    }
}
