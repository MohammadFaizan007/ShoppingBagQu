package com.shoppingbag.one_india_shopping.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shoppingbag.retrofit.MvpView;
import com.shoppingbag.R;
import com.shoppingbag.one_india_shopping.model.category_list_response.ResponseMainCategoryList;
import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainCategoryAdapter extends RecyclerView.Adapter<MainCategoryAdapter.MyViewHolder> {
    Context context;
    private MvpView mvpView;
    List<ResponseMainCategoryList> mainCategoryLists;


    public MainCategoryAdapter(Context context, List<ResponseMainCategoryList> mainCategoryLists, MvpView mvpView) {
        this.context = context;
        this.mvpView = mvpView;
        this.mainCategoryLists = mainCategoryLists;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ind_sub_categroy_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textViewCategory.setText(mainCategoryLists.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mainCategoryLists.size();
    }




    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textViewCategory)
        TextView textViewCategory;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
        @OnClick(R.id.textViewCategory)
        public void onViewClicked() {
            if(mainCategoryLists.get(getAdapterPosition()).getChildrenData().size()>0){
                String list = new Gson().toJson(mainCategoryLists.get(getAdapterPosition()).getChildrenData());
                mvpView.getClickPositionDirectMember(getAdapterPosition(),mainCategoryLists.get(getAdapterPosition()).getName(),list);
            }else {
                String list = new Gson().toJson(mainCategoryLists.get(getAdapterPosition()).getChildrenData());
                mvpView.getClickPositionDirectMember(mainCategoryLists.get(getAdapterPosition()).getId(),String.valueOf(mainCategoryLists.get(getAdapterPosition()).getName()),list);
            }

        }
    }
}

