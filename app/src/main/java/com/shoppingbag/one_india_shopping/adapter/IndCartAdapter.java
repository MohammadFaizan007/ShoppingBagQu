package com.shoppingbag.one_india_shopping.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.shoppingbag.BuildConfig;
import com.shoppingbag.R;
import com.shoppingbag.one_india_shopping.model.cartitem.CartitemsItem;
import com.shoppingbag.retrofit.MvpView;
import com.shoppingbag.utils.Utils;
import com.travijuu.numberpicker.library.Enums.ActionEnum;
import com.travijuu.numberpicker.library.NumberPicker;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IndCartAdapter extends RecyclerView.Adapter<IndCartAdapter.MyViewHolder> {

    private Activity context;
    private List<CartitemsItem> list;
    private MvpView mvpView;


    public IndCartAdapter(Activity context, List<CartitemsItem> list, MvpView mvpView) {
        this.context = context;
        this.list = list;
        this.mvpView = mvpView;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ind_rv_cartlist, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvProductName.setText(list.get(position).getName());
        holder.qtyBox.setValue(list.get(position).getQty());
        Log.d("TAG", "onBindViewHolder: "+holder.qtyBox.getValue());
        //holder.tvProductStatus.setText(String.format("Stock : %s", list.get(position).getStockQuantity()));
          if(holder.qtyBox.getValue()<list.get(position).getStockQuantity()){
              holder.tvProductStatus.setText("In Stock");
              holder.tvProductStatus.setTextColor(context.getResources().getColor(R.color.green));
            }else{
              holder.tvProductStatus.setTextColor(context.getResources().getColor(R.color.red));
              holder.tvProductStatus.setText("Out of Stock");
            }
        try {
           /* if (list.get(position).getSpecialPrice()!=null&&!list.get(position).getSpecialPrice().equalsIgnoreCase("")){
                holder.tvProductOriginalPrice.setText(String.format("₹ %s", Utils.getValue(Double.parseDouble(list.get(position).getSpecialPrice()))));
                holder.tvProductOriginalPrice.setVisibility(View.VISIBLE);
                holder.tvProductCurrentPrice.setText(String.format("₹ %s", Utils.getValue(list.get(position).getPrice())));
                holder.tvProductOriginalPrice.setPaintFlags(holder.tvProductOriginalPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            }else {*/
                holder.tvProductCurrentPrice.setText(String.format("₹ %s", Utils.getValue(list.get(position).getPrice())));

           // }

            if (list.get(position).getImage() != null) {
                String[] img = list.get(position).getImage().split(",");
                Glide.with(context).load(BuildConfig.BASE_Image_URL_OnestoreIndia + img[0]).apply(new RequestOptions().placeholder(R.drawable.image_not_available)).into(holder.imgProduct);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
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
            qtyBox.setValueChangedListener((value, action) -> {
                if (action == ActionEnum.INCREMENT) {
                    if (list.get(getAdapterPosition()).getProductOption() != null) {
                        Bundle bundle = new Bundle();
                        bundle.putString("qtybox", String.valueOf(qtyBox.getValue()));
                        bundle.putString("price", String.valueOf(list.get(getAdapterPosition()).getPrice()));
                        bundle.putString("itemid", String.valueOf(list.get(getAdapterPosition()).getItemId()));
                        if(list.get(getAdapterPosition()).getProductOption().size()>0) {
                            bundle.putString("optionColorId", String.valueOf(list.get(getAdapterPosition()).getProductOption().get(0).getOptionId()));
                            bundle.putString("optionColorValue", String.valueOf(list.get(getAdapterPosition()).getProductOption().get(0).getOptionValue()));
                        }else {
                            bundle.putString("optionColorId", "");
                            bundle.putString("optionColorValue", "");
                        }
                        if(list.get(getAdapterPosition()).getProductOption().size()>1){
                            bundle.putString("optionSizeId", String.valueOf(list.get(getAdapterPosition()).getProductOption().get(1).getOptionId()));
                            bundle.putString("optionSizeValue", String.valueOf(list.get(getAdapterPosition()).getProductOption().get(1).getOptionValue()));
                        }else {
                            bundle.putString("optionSizeId", "");
                            bundle.putString("optionSizeValue", "");
                        }
                        bundle.putString("type", list.get(getAdapterPosition()).getProductType());
                        bundle.putString("sku", list.get(getAdapterPosition()).getSku());
                        mvpView.getClickChildPosition("", "increment", bundle);

                    }


//                    mvpView.getClickPositionDirectMember(qtyBox.getValue(), String.valueOf(list.get(getAdapterPosition()).getPrice()), String.valueOf(list.get(getAdapterPosition()).getItemId()));

                } else if (action == ActionEnum.DECREMENT) {
                    Log.d("TAG", "MyViewHolder: "+qtyBox.getValue());
//                    mvpView.getClickPositionDirectMember(qtyBox.getValue(), String.valueOf(list.get(getAdapterPosition()).getPrice()), String.valueOf(list.get(getAdapterPosition()).getItemId()));
                    Bundle bundle = new Bundle();
                    bundle.putString("qtybox", String.valueOf(qtyBox.getValue()+1));
                    bundle.putString("price", String.valueOf(list.get(getAdapterPosition()).getPrice()));
                    bundle.putString("itemid", String.valueOf(list.get(getAdapterPosition()).getItemId()));
                    if(list.get(getAdapterPosition()).getProductOption().size()>0) {
                        bundle.putString("optionColorId", String.valueOf(list.get(getAdapterPosition()).getProductOption().get(0).getOptionId()));
                        bundle.putString("optionColorValue", String.valueOf(list.get(getAdapterPosition()).getProductOption().get(0).getOptionValue()));
                    }else {
                        bundle.putString("optionColorId", "");
                        bundle.putString("optionColorValue", "");
                    }
                    if(list.get(getAdapterPosition()).getProductOption().size()>1){
                        bundle.putString("optionSizeId", String.valueOf(list.get(getAdapterPosition()).getProductOption().get(1).getOptionId()));
                        bundle.putString("optionSizeValue", String.valueOf(list.get(getAdapterPosition()).getProductOption().get(1).getOptionValue()));
                    }else {
                        bundle.putString("optionSizeId", "");
                        bundle.putString("optionSizeValue", "");
                    }
                    bundle.putString("type", list.get(getAdapterPosition()).getProductType());
                    bundle.putString("sku", list.get(getAdapterPosition()).getSku());
                    mvpView.getClickChildPosition("", "decrement", bundle);

                }


            });

        }

        @OnClick({R.id.qtyBox, R.id.tv_remove})
        public void onViewClicked(View view) {
            switch (view.getId()) {
                case R.id.qtyBox:
//                    mvpView.getClickPositionDirectMember(list.get(getAdapterPosition()).getQty(), String.valueOf(list.get(getAdapterPosition()).getPrice()), String.valueOf(list.get(getAdapterPosition()).getItemId()));
                    break;
                case R.id.tv_remove:
                    mvpView.getClickPosition(list.get(getAdapterPosition()).getItemId(), "");
                    break;
            }
        }

    }
}
