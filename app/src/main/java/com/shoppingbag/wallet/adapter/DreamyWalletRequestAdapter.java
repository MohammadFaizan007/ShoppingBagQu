package com.shoppingbag.wallet.adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.shoppingbag.BuildConfig;
import com.shoppingbag.R;
import com.shoppingbag.wallet.model.ResultItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.bumptech.glide.load.engine.DiskCacheStrategy.AUTOMATIC;

public class DreamyWalletRequestAdapter extends RecyclerView.Adapter<DreamyWalletRequestAdapter.ViewHolder> {

    List<ResultItem> list;

    private Context mContext;


    public DreamyWalletRequestAdapter(Context context, List<ResultItem> list) {
        mContext = context;
        this.list = list;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wallet_request_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int listPosition) {
        holder.tvDate.setText(list.get(listPosition).getPaymentdate());
        holder.tv_paymentMode.setText(list.get(listPosition).getPaymentmode());
        holder.tvAmount.setText("₹"+list.get(listPosition).getAmount());
        holder.transacId.setText(""+list.get(listPosition).getTransactionId());
        holder.tvRemark.setText(list.get(listPosition).getRemark());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_date)
        TextView tvDate;
        @BindView(R.id.tv_amount)
        TextView tvAmount;
        @BindView(R.id.transac_id)
        TextView transacId;
        @BindView(R.id.tv_paymentMode)
        TextView tv_paymentMode;
        @BindView(R.id.tv_remark)
        TextView tvRemark;
        @BindView(R.id.imageAttach)
        ImageView imageAttach;
        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            imageAttach.setOnClickListener(v -> {
                showDialog(list.get(getAdapterPosition()).getFilepath());
            });

        }
    }

    private void showDialog(String url) {
        Dialog birthday_dialog = new Dialog(mContext, R.style.FullScreenDialog);
        birthday_dialog.setCanceledOnTouchOutside(true);
        birthday_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        birthday_dialog.setContentView(R.layout.birthday_lay);
        int width = (int) (mContext.getResources().getDisplayMetrics().widthPixels * 0.95);
        int height = (int) (mContext.getResources().getDisplayMetrics().heightPixels * 0.60);
        birthday_dialog.getWindow().setLayout(width, height);
        birthday_dialog.getWindow().setGravity(Gravity.CENTER);
        birthday_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        ImageView birthdayImage = birthday_dialog.findViewById(R.id.birthdayImage);
        ImageButton imgClose = birthday_dialog.findViewById(R.id.imgClose);

        Glide.with(mContext).load(url.replace("~", BuildConfig.BASE_URL_FORIMAGE))
                .apply(new RequestOptions().diskCacheStrategy(AUTOMATIC).placeholder(R.drawable.image_not_available).error(R.drawable.image_not_available))
                .into(birthdayImage);

        imgClose.setOnClickListener(v1 -> birthday_dialog.dismiss());

        birthday_dialog.show();
    }
}
