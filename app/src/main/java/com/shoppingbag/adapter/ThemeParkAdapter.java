package com.shoppingbag.adapter;

import android.app.Activity;
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
import com.shoppingbag.model.themeParkResponse.theme_park_maincategories.AddinfoItem;
import com.shoppingbag.retrofit.MvpView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.bumptech.glide.load.engine.DiskCacheStrategy.AUTOMATIC;

public class ThemeParkAdapter extends RecyclerView.Adapter<ThemeParkAdapter.ViewHolder> {

    private String[] images = {"waterpark.png", "friendly.png", "sight.png", "walking.png", "history.png", "nature.png", "daytrip.png", "cruize.png", "nationalpark.png",
            "safaris.png", "nationalpark.png", "daytrip.png", "theme.png"};
    private Context mContext;
    private List<AddinfoItem> list;
    private MvpView mvpView;


    public ThemeParkAdapter(Activity context, List<AddinfoItem> addinfo, MvpView themePark) {
        mContext = context;
        this.list = addinfo;
        mvpView = themePark;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.theme_park_categories_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int listPosition) {
        holder.category_name.setText(list.get(listPosition).getName());
        if (listPosition < images.length) {
            Glide.with(mContext).load(BuildConfig.BASE_URL_ICONS + images[listPosition])
                    .apply(new RequestOptions().diskCacheStrategy(AUTOMATIC).placeholder(R.drawable.image_not_available)
                            .error(R.drawable.image_not_available))
                    .into(holder.themeImg);
        } else
            holder.themeImg.setImageResource(R.drawable.image_not_available);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.category_name)
        TextView category_name;
        @BindView(R.id.theme_img)
        ImageView themeImg;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(v ->
                    mvpView.getGiftCardCategoryId(list.get(getAdapterPosition()).getCategoryId(), list.get(getAdapterPosition()).getName()));
        }
    }
}