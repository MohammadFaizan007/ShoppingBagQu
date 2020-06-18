package com.shoppingbag.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.shoppingbag.BuildConfig;
import com.shoppingbag.R;
import com.shoppingbag.model.support.ResultItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.bumptech.glide.load.engine.DiskCacheStrategy.AUTOMATIC;

public class SupportAdapter extends RecyclerView.Adapter<SupportAdapter.MyViewHolder> {
    private Activity context;
    private List<ResultItem> list;

    public SupportAdapter(Activity context, List<ResultItem> list) {
        this.context = context;
        this.list = list;

    }


    @NonNull
    @Override
    public SupportAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.support_items, parent, false);
        return new SupportAdapter.MyViewHolder(view);
    }


    private void setProviderImage(String path, ImageView imageView) {
        Glide.with(context).load(path.replace("~", BuildConfig.BASE_URL_FORIMAGE))
                .apply(new RequestOptions().diskCacheStrategy(AUTOMATIC).placeholder(R.drawable.image_not_available)
                        .error(R.drawable.cross))
                .into(imageView);
    }

    @Override
    public void onBindViewHolder(@NonNull SupportAdapter.MyViewHolder holder, int listPosition) {
        if(listPosition%2 ==0){
            holder.cons.setBackgroundColor(context.getResources().getColor(R.color.color_support_bg));
        }else {
            holder.cons.setBackgroundColor(context.getResources().getColor(R.color.white));
        }
        holder.txtid.setText(String.format("ID : %s", list.get(listPosition).getTicketNumber()));
        holder.txtshopname.setText(list.get(listPosition).getType());
        holder.txtstatus.setText(list.get(listPosition).getStatus());
        holder.txtcategory.setText(list.get(listPosition).getType());
        holder.txtmaritalstaus.setText(list.get(listPosition).getStatus());
        holder.txtmessage.setText(list.get(listPosition).getMessage());
        holder.txtdocumentimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopUp(list.get(listPosition).getPath());
            }
        });
        if(list.get(listPosition).getStatus().equalsIgnoreCase("pending")){
            holder.txtmaritalstaus.setBackground(context.getResources().getDrawable(R.drawable.txtpending));
        }else {
            holder.txtmaritalstaus.setBackground(context.getResources().getDrawable(R.drawable.txt_resolved));
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtshopname)
        TextView txtshopname;
        @BindView(R.id.txtid)
        TextView txtid;
        @BindView(R.id.txtstatus)
        TextView txtstatus;
        @BindView(R.id.txtcategory)
        TextView txtcategory;
        @BindView(R.id.txtmessage)
        TextView txtmessage;
        @BindView(R.id.txtmaritalstaus)
        TextView txtmaritalstaus;
        @BindView(R.id.txtdocumentimage)
        TextView txtdocumentimage;
        @BindView(R.id.cons)
        ConstraintLayout cons;
        @BindView(R.id.consvis)
        ConstraintLayout consvis;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            cons.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(consvis.getVisibility() == View.VISIBLE){
                        consvis.setVisibility(View.GONE);
                    }else {
                        consvis.setVisibility(View.VISIBLE);
                    }
                }
            });

        }
    }
    private void showPopUp(String path) {
        Dialog settingsDialog = new Dialog(context);
        ImageView imageView;
        settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        settingsDialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        settingsDialog.setContentView(context.getLayoutInflater().inflate(R.layout.pop_image
                , null));

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(settingsDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;

        settingsDialog.getWindow().setAttributes(lp);
        imageView = settingsDialog.findViewById(R.id.launch_img);
        Log.d("<><", "showPopUp: "+path.replace("~", BuildConfig.BASE_URL_FORIMAGE));
        Glide.with(context).load(path.replace("~", BuildConfig.BASE_URL_FORIMAGE))
                .apply(new RequestOptions().diskCacheStrategy(AUTOMATIC).placeholder(R.drawable.image_not_available)
                        .error(R.drawable.image_not_available))
                .into(imageView);
        settingsDialog.show();
        settingsDialog.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingsDialog.dismiss();
            }
        });

    }
}

