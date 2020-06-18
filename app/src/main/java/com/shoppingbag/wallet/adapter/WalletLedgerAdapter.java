package com.shoppingbag.wallet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.shoppingbag.model.response.wallet.ResultItem;
import com.shoppingbag.utils.Utils;
import com.shoppingbag.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WalletLedgerAdapter extends RecyclerView.Adapter<WalletLedgerAdapter.ViewHolder> {

    List<ResultItem> list;
    private Context mContext;


    public WalletLedgerAdapter(Context context, List<ResultItem> list) {
        mContext = context;
        this.list = list;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dreamy_cash_wallet_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int listPosition) {
       /* if(listPosition%2==0){
            holder.cons_wallet.setBackgroundColor(R.color.text_color_gray);
        }else {
            holder.cons_wallet.setBackgroundColor(R.color.white);
        }*/

        holder.txtTransDate.setText(list.get(listPosition).getPaymentdate());
        //holder.txtBalance.setText(String.format("₹ %s", String.valueOf(list.get(listPosition).getRunningAmount())));
        holder.txtBalance.setText("Txn No : "+String.valueOf(list.get(listPosition).getTransactionId()));
        holder.txtRemark.setText(Utils.getDefaultValue(list.get(listPosition).getRemarks(), "NA"));

        if (list.get(listPosition).getDrAmount() > 0) {
            holder.txtDrCr.setText("DR");
            holder.txtDrCr.setBackgroundResource(R.drawable.circle_red_dr);
            holder.txtAmount.setText(String.format("₹%s", list.get(listPosition).getDrAmount()));
            holder.txtAmount.setTextColor(ContextCompat.getColor(mContext, R.color.red));
        } else {
            holder.txtDrCr.setText("CR");
            holder.txtDrCr.setBackgroundResource(R.drawable.circle_green_cr);
            holder.txtAmount.setText(String.format("₹%s", list.get(listPosition).getCrAmount()));
            holder.txtAmount.setTextColor(ContextCompat.getColor(mContext, R.color.success));
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtDrCr)
        TextView txtDrCr;
        @BindView(R.id.txtAmount)
        TextView txtAmount;
        @BindView(R.id.txtTransDate)
        TextView txtTransDate;
        @BindView(R.id.txtBalance)
        TextView txtBalance;
        @BindView(R.id.txtRemark)
        TextView txtRemark;
        @BindView(R.id.cons_wallet)
        ConstraintLayout cons_wallet;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }
    }


}