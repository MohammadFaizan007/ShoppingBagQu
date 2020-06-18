package com.shoppingbag.utilities.mobilerecharge.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.shoppingbag.model.mobile_recharge.responsemodel.OperatorListsItem;
import com.shoppingbag.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OperatorsAdapter extends RecyclerView.Adapter<OperatorsAdapter.OperatorHolder> {

    private Context context;
    private List<OperatorListsItem> list;

    public OperatorsAdapter(Context context, List<OperatorListsItem> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public OperatorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.operators_adapter, parent, false);
        return new OperatorHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OperatorHolder holder, final int position) {
        holder.operatorName.setText(list.get(position).getOperatorDescritpion());
        holder.operator_adapter_code.setText(list.get(position).getRechargeType());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class OperatorHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.operator_adapter_name)
        AppCompatTextView operatorName;

        @BindView(R.id.operatorRoot)
        ConstraintLayout operatorRoot;

        @BindView(R.id.operator_adapter_code)
        AppCompatTextView operator_adapter_code;

        OperatorHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
