package com.shoppingbag.shopping.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.shoppingbag.R;
import com.shoppingbag.model.response.shopping.TravelItem;
import com.shoppingbag.retrofit.MvpView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DashboardTravelAdapter extends RecyclerView.Adapter<DashboardTravelAdapter.ViewHolder> {

    private List<TravelItem> itemList;
    private Context mContext;
    private MvpView mvp;

    public DashboardTravelAdapter(Context context, List<TravelItem> list, MvpView mvp) {
        mContext = context;
        itemList = list;
        this.mvp = mvp;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.travel_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int listPosition) {
//        holder.androidGridviewText.setText(itemList.get(listPosition).getName());
        Glide.with(mContext).load(itemList.get(listPosition).getImageurl().replace("~", "http://webapi.shoppingbag.co.in")).
                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
                        .error(R.drawable.image_not_available))
                .into(holder.androidGridviewImage);

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.android_gridview_image)
        ImageView androidGridviewImage;
        //        @BindView(R.id.android_gridview_text)
//        TextView androidGridviewText;
        @BindView(R.id.android_custom_gridview_layout)
        LinearLayout androidCustomGridviewLayout;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(v -> mvp.getClickPositionDirectMember(getAdapterPosition(), itemList.get(getAdapterPosition()).getLink()
                    , itemList.get(getAdapterPosition()).getName()));
        }
    }

}