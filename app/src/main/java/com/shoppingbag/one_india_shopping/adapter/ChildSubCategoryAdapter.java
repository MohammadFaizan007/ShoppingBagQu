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
import com.shoppingbag.one_india_shopping.model.category_list_response.ChildrenDataItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChildSubCategoryAdapter  extends RecyclerView.Adapter<ChildSubCategoryAdapter.MyViewHolder> {
    Context context;
    private MvpView mvpView;
    List<ChildrenDataItem> mainCategoryLists;


    public ChildSubCategoryAdapter(Context context, List<ChildrenDataItem> mainCategoryLists, MvpView mvpView) {
        this.context = context;
        this.mvpView = mvpView;
        this.mainCategoryLists = mainCategoryLists;
    }

    @NonNull
    @Override
    public ChildSubCategoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ind_sub_cat_child_row, parent, false);
        return new ChildSubCategoryAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChildSubCategoryAdapter.MyViewHolder holder, int position) {
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
            mvpView.getClickChildPosition(String.valueOf(mainCategoryLists.get(getAdapterPosition()).getId()), mainCategoryLists.get(getAdapterPosition()).getName(),null);
        }
    }
}


