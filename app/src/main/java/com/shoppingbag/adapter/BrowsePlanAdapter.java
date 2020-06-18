package com.shoppingbag.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shoppingbag.model.jioPrepaid.PlanofferingItem;
import com.shoppingbag.retrofit.MvpView;
import com.shoppingbag.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BrowsePlanAdapter extends RecyclerView.Adapter<BrowsePlanAdapter.ViewHolder> {

    private Context mContext;
    private List<PlanofferingItem> list;
    private MvpView mvpView;

    public BrowsePlanAdapter(Context context, List<PlanofferingItem> list, MvpView mvp) {
        mContext = context;
        this.list = list;
        mvpView = mvp;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.browse_plan_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int listPosition) {

        holder.recharge_description.setText(list.get(listPosition).getDescription());
        holder.amount.setText(String.format("₹ %s", list.get(listPosition).getPrice()));
//        holder.validity.setText(list.get(listPosition).getPrice());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.recharge_description)
        TextView recharge_description;
        @BindView(R.id.amount)
        TextView amount;
        @BindView(R.id.more_details)
        TextView more_details;
        @BindView(R.id.validity)
        TextView validity;
        @BindView(R.id.rc_plan_lo)
        LinearLayout rcPlanLo;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            rcPlanLo.setOnClickListener(v -> mvpView.getClickPosition(Integer.parseInt(list.get(getAdapterPosition()).getPrice()), list.get(getAdapterPosition()).getId()));

        }
    }


}