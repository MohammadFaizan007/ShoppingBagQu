package com.shoppingbag.one_india_shopping.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.shoppingbag.BuildConfig;
import com.shoppingbag.R;
import com.shoppingbag.one_india_shopping.model.product_description_new.MediaGalleryItem;
import com.shoppingbag.retrofit.MvpView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IndProductImagesAdapter extends RecyclerView.Adapter<IndProductImagesAdapter.MyViewHolder> {
    private Activity context;
    private List<MediaGalleryItem> list;
    private MvpView mvpView;
    int rowIndex = 0;

    public IndProductImagesAdapter(Activity context, List<MediaGalleryItem> mediaGalleryItems, MvpView mvpView) {
        this.context = context;
        this.list = mediaGalleryItems;
        this.mvpView = mvpView;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ind_rv_product_image, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context).load(BuildConfig.BASE_Image_URL_OnestoreIndia + list.get(position)
                .getFile()).apply(new RequestOptions().placeholder(R.drawable.image_not_available)).into(holder.itemthumbnail);


        if (rowIndex == position) {
            holder.itemthumbnail.setBackgroundResource(R.drawable.size_border);
        } else {
            holder.itemthumbnail.setBackgroundColor(context.getResources().getColor(R.color.white));
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.itemthumbnail)
        ImageView itemthumbnail;
        @BindView(R.id.constraintLayout111)
        ConstraintLayout constraintLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemthumbnail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rowIndex = getAdapterPosition();
                    mvpView.getClickPosition(rowIndex, "image");
                    notifyDataSetChanged();
                }
            });

        }
    }
}
