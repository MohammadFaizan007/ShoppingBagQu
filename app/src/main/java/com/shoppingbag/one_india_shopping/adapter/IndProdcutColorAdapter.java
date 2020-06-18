package com.shoppingbag.one_india_shopping.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.shoppingbag.retrofit.MvpView;
import com.shoppingbag.R;
import com.shoppingbag.one_india_shopping.model.myproductdescription.ColorItem;
import com.shoppingbag.one_india_shopping.model.product_description_new.ValuesItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IndProdcutColorAdapter extends RecyclerView.Adapter<IndProdcutColorAdapter.MyViewHolder> {
    private Activity context;
    private List<ValuesItem> list;
    private MvpView mvpView;
    private int rowIndex = 0;
    private String optionColorId = "";


    public IndProdcutColorAdapter(Activity context, List<ValuesItem> list, String optionColorId, MvpView mvpView) {
        this.context = context;
        this.list = list;
        this.mvpView = mvpView;
        this.optionColorId = optionColorId;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ind_rv_productcolor, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Log.d("TAG", "onBindViewHolder: " + list.get(position).getTitle().toLowerCase());
        try {
            holder.txtColor.setBackgroundColor(Color.parseColor(list.get(position).getTitle().toLowerCase()));
        } catch (Exception e) {
            holder.txtColor.setBackgroundColor(Color.parseColor("black"));
            e.printStackTrace();
        }


        if (rowIndex == position) {
            holder.color_lin_lay.setBackgroundResource(R.drawable.size_border);
        } else {
            holder.color_lin_lay.setBackgroundColor(context.getResources().getColor(R.color.white));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtColor)
        View txtColor;
        @BindView(R.id.color_lin_lay)
        ConstraintLayout color_lin_lay;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            txtColor.setOnClickListener(v -> {
                rowIndex = getAdapterPosition();
                mvpView.getMyClickPosition(String.valueOf(list.get(rowIndex).getOptionTypeId()), optionColorId);

                notifyDataSetChanged();
            });

        }
    }
}
