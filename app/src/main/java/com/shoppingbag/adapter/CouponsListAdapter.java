package com.shoppingbag.adapter;

import android.content.Context;
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
import com.shoppingbag.model.giftCardResponse.ResponseGiftsCards;
import com.shoppingbag.retrofit.MvpView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.bumptech.glide.load.engine.DiskCacheStrategy.AUTOMATIC;

public class CouponsListAdapter extends RecyclerView.Adapter<CouponsListAdapter.ViewHolder> {

    private Context mContext;
    private List<ResponseGiftsCards> list;
    private MvpView mvpView;

    public CouponsListAdapter(Context context, List<ResponseGiftsCards> list, MvpView mvp) {
        mContext = context;
        this.list = list;
        mvpView = mvp;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_giftcards, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.tvCardName.setText(list.get(position).getName());
        holder.tvAmount.setText(String.format("₹ %s to ₹ %s", list.get(position).getMinCustomPrice(), list.get(position).getMaxCustomPrice()));

        if (list.get(position).getPriceType().equalsIgnoreCase("Slab")) {
            holder.tvSlabs.setVisibility(View.VISIBLE);
            holder.tvSlabs.setText(String.format("Denominations(₹) : %s", list.get(position).getCustomDenominations()));
        } else holder.tvSlabs.setVisibility(View.GONE);

        Glide.with(mContext).load(list.get(position).getImages().replace("~", BuildConfig.BASE_URL_FORIMAGE))
                .apply(new RequestOptions().diskCacheStrategy(AUTOMATIC).placeholder(R.drawable.pending).error(R.drawable.pending))
                .into(holder.imgGift);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_gift)
        ImageView imgGift;
        @BindView(R.id.tv_card_name)
        TextView tvCardName;
        @BindView(R.id.tv_amount)
        TextView tvAmount;
        @BindView(R.id.tv_slabs)
        TextView tvSlabs;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(v -> mvpView.getGiftCardCategoryId(list.get(getAdapterPosition()).getId(), list.get(getAdapterPosition()).getName()));
        }
    }


}
