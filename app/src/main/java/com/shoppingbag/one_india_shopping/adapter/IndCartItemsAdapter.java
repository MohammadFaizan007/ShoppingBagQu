package com.shoppingbag.one_india_shopping.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shoppingbag.R;
import com.travijuu.numberpicker.library.NumberPicker;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IndCartItemsAdapter extends RecyclerView.Adapter<IndCartItemsAdapter.MyViewHolder> {
    Context context;
    public IndCartItemsAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ind_rv_cartlist, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_product)
        ImageView imgProduct;
        @BindView(R.id.qtyBox)
        NumberPicker qtyBox;
        @BindView(R.id.tv_product_name)
        TextView tvProductName;
        @BindView(R.id.tv_product_status)
        TextView tvProductStatus;
        @BindView(R.id.tv_product_current_price)
        TextView tvProductCurrentPrice;
        @BindView(R.id.tv_product_original_price)
        TextView tvProductOriginalPrice;
        @BindView(R.id.tv_remove)
        TextView tvRemove;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
