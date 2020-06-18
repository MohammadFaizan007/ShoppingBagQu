package com.shoppingbag.one_india_shopping.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shoppingbag.retrofit.MvpView;
import com.shoppingbag.R;
import com.shoppingbag.one_india_shopping.model.myproductdescription.SizeItem;
import com.shoppingbag.one_india_shopping.model.product_description_new.ValuesItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IndProductSizeAdapter extends RecyclerView.Adapter<IndProductSizeAdapter.MyViewHolder> {
    private Activity context;
    private List<ValuesItem> list;
    private MvpView mvpView;
    int rowIndex = 0;
    String optionSizeId = "";

/*    public IndProductSizeAdapter(Context context, List<String> list_size) {
        this.context = context;
        this.list_size = list_size;
    }*/

    public IndProductSizeAdapter(Activity context, List<ValuesItem> sizeItemList, String optionSizeId, MvpView mvpView) {
        this.context = context;
        this.list = sizeItemList;
        this.mvpView = mvpView;
        this.optionSizeId = optionSizeId;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ind_rv_productsize, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.txtSize.setText(list.get(position).getTitle());

        if (rowIndex == position) {
            holder.txtSize.setBackgroundResource(R.drawable.size_border);
        } else {
            holder.txtSize.setBackgroundResource(R.drawable.sizebg);
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtSize)
        TextView txtSize;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            txtSize.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rowIndex = getAdapterPosition();
                    mvpView.getGiftCardCategoryId(String.valueOf(list.get(rowIndex).getOptionTypeId()), optionSizeId);
                    notifyDataSetChanged();
                }
            });
        }
    }
}
