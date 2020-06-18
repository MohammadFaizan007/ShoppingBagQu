package com.shoppingbag.utilities.dthrecharge.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.shoppingbag.R;
import com.shoppingbag.utilities.mobilerecharge.responsemodel.OperatorListsItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DthOperatorAdapter extends RecyclerView.Adapter<DthOperatorAdapter.DthHolder> {

    private Context context;
    private List<OperatorListsItem> list;

    public DthOperatorAdapter(Context context, List<OperatorListsItem> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public DthHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.operators_adapter, parent, false);
        return new DthHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DthHolder holder, final int position) {
        holder.operatorName.setText(list.get(position).getOperatorDescritpion());
        holder.operator_adapter_code.setText(list.get(position).getRechargeType());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class DthHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.operator_adapter_name)
        AppCompatTextView operatorName;

        @BindView(R.id.operatorRoot)
        ConstraintLayout operatorRoot;

        @BindView(R.id.operator_adapter_code)
        AppCompatTextView operator_adapter_code;

        public DthHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
