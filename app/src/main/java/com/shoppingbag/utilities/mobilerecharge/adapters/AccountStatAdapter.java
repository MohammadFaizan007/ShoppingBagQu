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

import com.shoppingbag.model.mobile_recharge.responsemodel.TransactionsItem;
import com.shoppingbag.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AccountStatAdapter extends RecyclerView.Adapter<AccountStatAdapter.AccStatHolder> {

    private Context context;
    private List<TransactionsItem> list;

    private AccStatHolder accStatHolder;
    private int mExpandedPosition = -1;

    public AccountStatAdapter(Context context, List<TransactionsItem> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public AccStatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.acc_stat_adapter, parent, false);
        return new AccStatHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final AccStatHolder holder, int position) {
//        holder.tvAccStat.setText("Date and Time : " + list.get(position).getDateTime() + "\n" +
//                "Description : " + list.get(position).getDescription() + "\n" +
//                " Ref. Number: " + list.get(position).getReferenceNumber() + "\n" +
//                " Credit Amt : " + list.get(position).getCreditAmount() + "\n" +
//                " Debit Amt : " + list.get(position).getDebitAmount() + "\n" +
//                " Remaining Amt : " + list.get(position).getRemainingAmount() + "\n" +
//                " Remarks : " + list.get(position).getRemarks() + "\n" +
//                " Terminal Name :  " + list.get(position).getTerminalName() + "\n" +
//                " Prod Desc :  " + list.get(position).getProductDescription() + "\n");

        holder.tvDateAndTime.setText(list.get(position).getDateTime());
        holder.tvCreditAmount.setText("" + list.get(position).getCreditAmount());
        holder.tvDebitAmount.setText("" + list.get(position).getDebitAmount());
        holder.tvDecription.setText(list.get(position).getDescription());
        holder.tvReference.setText(list.get(position).getReferenceNumber());
        holder.tvRemark.setText(list.get(position).getRemarks());

        holder.tvTerminalName.setText(list.get(position).getTerminalName());
        holder.tvTerminalDesc.setText(list.get(position).getProductDescription());
        holder.tvTerminalAmt.setText("" + list.get(position).getRemainingAmount());

        final boolean isExpanded = position == mExpandedPosition;
        holder.detailLay.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        holder.arrowDetails.setRotation(0);
        if (isExpanded) {
            holder.arrowDetails.animate().setDuration(500).rotationBy(180).start();
        }

        holder.topLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (accStatHolder != null) {
                    accStatHolder.detailLay.setVisibility(View.GONE);
                    notifyItemChanged(mExpandedPosition);
                }
                mExpandedPosition = isExpanded ? -1 : holder.getAdapterPosition();
                accStatHolder = isExpanded ? null : holder;
                notifyItemChanged(holder.getAdapterPosition());
            }
        });

        holder.arrowDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (accStatHolder != null) {
                    accStatHolder.detailLay.setVisibility(View.GONE);
                    notifyItemChanged(mExpandedPosition);
                }
                mExpandedPosition = isExpanded ? -1 : holder.getAdapterPosition();
                accStatHolder = isExpanded ? null : holder;
                notifyItemChanged(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class AccStatHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvDateAndTime)
        TextView tvDateAndTime;

        @BindView(R.id.tvCreditAmount)
        TextView tvCreditAmount;

        @BindView(R.id.tvDebitAmount)
        TextView tvDebitAmount;

        @BindView(R.id.tvDecription)
        TextView tvDecription;

        @BindView(R.id.tvReference)
        TextView tvReference;

        @BindView(R.id.tvRemark)
        TextView tvRemark;

        @BindView(R.id.tvTerminalName)
        TextView tvTerminalName;

        @BindView(R.id.tvTerminalDesc)
        TextView tvTerminalDesc;

        @BindView(R.id.tvTerminalAmt)
        TextView tvTerminalAmt;

        @BindView(R.id.arrowDetails)
        ImageView arrowDetails;

        @BindView(R.id.topLay)
        ConstraintLayout topLay;

        @BindView(R.id.detailLay)
        ConstraintLayout detailLay;


        public AccStatHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
