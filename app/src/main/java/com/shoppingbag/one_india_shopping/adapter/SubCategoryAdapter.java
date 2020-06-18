package com.shoppingbag.one_india_shopping.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shoppingbag.retrofit.MvpView;
import com.shoppingbag.R;
import com.shoppingbag.one_india_shopping.model.category_list_response.ChildrenDataItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SubCategoryAdapter  extends RecyclerView.Adapter<SubCategoryAdapter.MyViewHolder> {
    Context context;
    private MvpView mvpView;
    List<ChildrenDataItem> mainCategoryLists;


    public SubCategoryAdapter(Context context, List<ChildrenDataItem> mainCategoryLists, MvpView mvpView) {
        this.context = context;
        this.mvpView = mvpView;
        this.mainCategoryLists = mainCategoryLists;
    }

    @NonNull
    @Override
    public SubCategoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ind_sub_categroy_row, parent, false);
        return new SubCategoryAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubCategoryAdapter.MyViewHolder holder, int position) {
        holder.textViewCategory.setText(mainCategoryLists.get(position).getName());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        holder.sub_cat_child_recycler.setLayoutManager(linearLayoutManager);
        ChildSubCategoryAdapter dataAdapter = new ChildSubCategoryAdapter(context, mainCategoryLists.get(position).getChildrenData(), mvpView);
        holder. sub_cat_child_recycler.setAdapter(dataAdapter);
        holder.sub_cat_child_recycler.setHasFixedSize(true);
    }

    @Override
    public int getItemCount() {
        return mainCategoryLists.size();
    }




    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textViewCategory)
        TextView textViewCategory;
        @BindView(R.id.sub_cat_child_recycler)
        RecyclerView sub_cat_child_recycler;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
        @OnClick(R.id.textViewCategory)
        public void onViewClicked() {
            mvpView.getClickPosition(mainCategoryLists.get(getAdapterPosition()).getId(), mainCategoryLists.get(getAdapterPosition()).getName());
        }
    }
}


