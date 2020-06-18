package com.shoppingbag.wallet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shoppingbag.model.response.wallet.FundTransferLogItem;
import com.shoppingbag.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FundLogAdapter extends RecyclerView.Adapter<FundLogAdapter.ViewHolder> {

    private Context mContext;
    private List<FundTransferLogItem> list;
    String type;

    public FundLogAdapter(Context context, List<FundTransferLogItem> list, String type) {
        mContext = context;
        this.list = list;
        this.type = type;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.commission_fund_log, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int listPosition) {

        holder.txtAmount.setText(String.format("- ₹ %s", list.get(listPosition).getTransAmount()));
//        holder.txtName.setText(list.get(listPosition).getBeneficiaryName());
        holder.txtTransNo.setText(String.format("Transaction No. : %s", list.get(listPosition).getTransNo()));
//        holder.txtTransStatus.setText(list.get(listPosition).getTransStatus());

        holder.txtDate.setText(list.get(listPosition).getTransdate());

        if (type.equalsIgnoreCase("CTTB")) {
            holder.imgType.setImageResource(R.drawable.bank_icon);
            holder.txtRemark.setText(String.format("%s", list.get(listPosition).getRemarks()));
        } else {
            holder.imgType.setImageResource(R.drawable.dreamy_icon);
            if (list.get(listPosition).getRemarks().equalsIgnoreCase("")) {
                holder.txtRemark.setText(String.format("%s", list.get(listPosition).getBeneficiaryName()));
            } else {
                holder.txtRemark.setText(String.format("%s", list.get(listPosition).getRemarks()));
            }
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_type)
        ImageView imgType;
        @BindView(R.id.txtRemark)
        TextView txtRemark;
        @BindView(R.id.txtAmount)
        TextView txtAmount;
        @BindView(R.id.txtDate)
        TextView txtDate;
        @BindView(R.id.txtTransNo)
        TextView txtTransNo;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }
    }


}
