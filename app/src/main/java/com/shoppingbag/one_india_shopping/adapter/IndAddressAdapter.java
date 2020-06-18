package com.shoppingbag.one_india_shopping.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.shoppingbag.retrofit.MvpView;
import com.shoppingbag.R;
import com.shoppingbag.one_india_shopping.model.address.DataItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IndAddressAdapter extends RecyclerView.Adapter<IndAddressAdapter.MyViewHolder> {

    private Activity context;
    private List<DataItem> list;
    private MvpView mvpView;
    private Bundle bundle;

    public IndAddressAdapter(Activity context, List<DataItem> list, MvpView mvpView) {
        this.context = context;
        this.list = list;
        this.mvpView = mvpView;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ind_address, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.fullnameBill.setText(String.format("%s %s", list.get(position).getFirstname(), list.get(position).getLastname()));
        holder.addressBill.setText(list.get(position).getStreet());
        holder.landmarkBill.setText(String.format("%s,%s,%s", list.get(position).getLandmark(), list.get(position).getCity(), list.get(position).getRegion()));
        holder.pinCodeBill.setText(String.format("Pincode : %s", list.get(position).getPostcode()));
        holder.addressTypeBill.setText(list.get(position).getAddressType());
        holder.txtMobille.setText(list.get(position).getTelephone());


        if (list.get(position).getIsActive().equalsIgnoreCase("1")) {
            holder.btnSelected.setText("Selected");
            holder.btnSelected.setTextColor(ContextCompat.getColor(context, R.color.white));
            holder.btnSelected.setBackgroundResource(R.drawable.rect_btn_bg_darkgreen);
        } else {
            holder.btnSelected.setText("Select");
            holder.btnSelected.setTextColor(ContextCompat.getColor(context, R.color.text_color));
            holder.btnSelected.setBackgroundResource(R.drawable.size_border);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.fullname_bill)
        TextView fullnameBill;
        @BindView(R.id.addressType_bill)
        TextView addressTypeBill;
        @BindView(R.id.address_bill)
        TextView addressBill;
        @BindView(R.id.landmark_bill)
        TextView landmarkBill;
        @BindView(R.id.pinCode_bill)
        TextView pinCodeBill;
        @BindView(R.id.btn_edit_address)
        Button btnEditAddress;
        @BindView(R.id.btn_remove_address)
        Button btnRemoveAddress;
        @BindView(R.id.btn_selected)
        Button btnSelected;
        @BindView(R.id.txt_mobille)
        TextView txtMobille;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);


        }

        @OnClick({R.id.btn_edit_address, R.id.btn_remove_address, R.id.btn_selected})
        public void onViewClicked(View view) {
            switch (view.getId()) {
                case R.id.btn_edit_address:
                    bundle = new Bundle();
                    bundle.putString("firstname", list.get(getAdapterPosition()).getFirstname());
                    bundle.putString("lastname", list.get(getAdapterPosition()).getLastname());
                    bundle.putString("street", list.get(getAdapterPosition()).getStreet());
                    bundle.putString("mobile", list.get(getAdapterPosition()).getTelephone());
                    bundle.putString("landmark", list.get(getAdapterPosition()).getLandmark());
                    bundle.putString("check", list.get(getAdapterPosition()).getIsActive());
                    bundle.putString("state", list.get(getAdapterPosition()).getRegion());
                    bundle.putString("pincode", list.get(getAdapterPosition()).getPostcode());
                    bundle.putString("city", list.get(getAdapterPosition()).getCity());
                    bundle.putString("addresstype", list.get(getAdapterPosition()).getAddressType());
                    bundle.putString("addressId", list.get(getAdapterPosition()).getAddressId());
                    mvpView.getClickChildPosition(list.get(getAdapterPosition()).getAddressId(), "Edit", bundle);
                    break;
                case R.id.btn_remove_address:
                    mvpView.getClickChildPosition(list.get(getAdapterPosition()).getAddressId(), "Remove", null);
                    break;
                case R.id.btn_selected:
                    bundle = new Bundle();
                    mvpView.getClickChildPosition(list.get(getAdapterPosition()).getAddressId(), "Selected", null);
                    break;
            }
        }


    }
}
