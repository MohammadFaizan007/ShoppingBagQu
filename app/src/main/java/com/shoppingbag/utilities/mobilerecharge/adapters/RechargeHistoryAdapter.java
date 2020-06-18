package com.shoppingbag.utilities.mobilerecharge.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.shoppingbag.model.mobile_recharge.responsemodel.RechargesItem;
import com.shoppingbag.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RechargeHistoryAdapter extends RecyclerView.Adapter<RechargeHistoryAdapter.HistoryViewHolder> {

    private Context context;
    private List<RechargesItem> list;

    private HistoryViewHolder mExpandedHolder;
    private int mExpandedPosition = -1;


    public RechargeHistoryAdapter(Context context, List<RechargesItem> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.history_adapter, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final HistoryViewHolder holder, int position) {
        holder.tvReferenceNo.setText(list.get(position).getReferenceNumber());
        holder.tvMobileNumber.setText(list.get(position).getMobileNumber());
        holder.tvDateAndTime.setText(list.get(position).getRechargeDateTime());
        holder.tvOperator.setText(list.get(position).getOperatorDescription());
        holder.tvTransactionID.setText(list.get(position).getOperatorTransactionId());
        holder.tvAmount.setText(String.format("₨ %s", list.get(position).getAmount()));
        holder.tvReStatus.setText(list.get(position).getRechargeStatus());


        final boolean isExpanded = position == mExpandedPosition;
        holder.detailLay.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        holder.arrowDetails.setRotation(0);
        if (isExpanded) {
            holder.arrowDetails.animate().setDuration(500).rotationBy(180).start();
        }

        holder.topLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mExpandedHolder != null) {
                    mExpandedHolder.detailLay.setVisibility(View.GONE);
                    notifyItemChanged(mExpandedPosition);
                }
                mExpandedPosition = isExpanded ? -1 : holder.getAdapterPosition();
                mExpandedHolder = isExpanded ? null : holder;
                notifyItemChanged(holder.getAdapterPosition());
            }
        });

        holder.arrowDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mExpandedHolder != null) {
                    mExpandedHolder.detailLay.setVisibility(View.GONE);
                    notifyItemChanged(mExpandedPosition);
                }
                mExpandedPosition = isExpanded ? -1 : holder.getAdapterPosition();
                mExpandedHolder = isExpanded ? null : holder;
                notifyItemChanged(holder.getAdapterPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tvMobileNumber)
        TextView tvMobileNumber;
        @BindView(R.id.tvDateAndTime)
        TextView tvDateAndTime;
        @BindView(R.id.tvOperator)
        TextView tvOperator;

        @BindView(R.id.tvAmount)
        TextView tvAmount;

        @BindView(R.id.tvReferenceNo)
        TextView tvReferenceNo;
        @BindView(R.id.tvTransactionID)
        TextView tvTransactionID;

        @BindView(R.id.arrowDetails)
        ImageView arrowDetails;

        @BindView(R.id.tvReStatus)
        TextView tvReStatus;

        @BindView(R.id.detailLay)
        ConstraintLayout detailLay;

        @BindView(R.id.topLay)
        ConstraintLayout topLay;

        HistoryViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
//            txtViewLedger.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
