package com.shoppingbag.mlm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.shoppingbag.BuildConfig;
import com.shoppingbag.R;
import com.shoppingbag.model.response.mlmDashboardNew.BannersItem;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DashboardMLMBannerAdapter extends RecyclerView.Adapter<DashboardMLMBannerAdapter.MzViewHolder> {

    private Context context;
    private List<BannersItem> shoppingoffersitem;

    public DashboardMLMBannerAdapter(Context context, List<BannersItem> shoppingoffersitem) {
        this.context = context;
        this.shoppingoffersitem = shoppingoffersitem;
    }


    @NotNull
    @Override
    public DashboardMLMBannerAdapter.MzViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MzViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.dashboard_banner_item_image,
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DashboardMLMBannerAdapter.MzViewHolder holder, final int position) {
        if (shoppingoffersitem == null || shoppingoffersitem.size() <= 0)
            return;

        Glide.with(context).load(shoppingoffersitem.get(position).getBannerImgUrl().replace("~", BuildConfig.BASE_URL_FORIMAGE))
                .apply(new RequestOptions().placeholder(R.drawable.image_not_available)
                        .error(R.drawable.image_not_available))
                .into(holder.imageView);

//        holder.imageView.setOnClickListener(v -> {
//            Bundle bundle = new Bundle();
//            bundle.putString("from", "Main");
//            bundle.putString("link", shoppingoffersitem.get(position).getBannerLink());
//            ((MainContainerMLM) context).goToActivity((Activity) context, WebViewActivity.class, bundle);
//        });

    }

    @Override
    public int getItemCount() {
        if (shoppingoffersitem != null) {
            return shoppingoffersitem.size();
        }
        return 0;
    }


    class MzViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        MzViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageTimer);
        }
    }

}