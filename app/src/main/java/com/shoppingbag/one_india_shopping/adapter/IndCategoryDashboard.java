package com.shoppingbag.one_india_shopping.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.shoppingbag.retrofit.MvpView;
import com.shoppingbag.R;
import com.shoppingbag.one_india_shopping.model.dashboard.CategoryListItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IndCategoryDashboard extends RecyclerView.Adapter<IndCategoryDashboard.MyViewHolder> {

    private Activity context;
    private MvpView mvpView;
    private List<CategoryListItem> listItems;

    public IndCategoryDashboard(Activity context, List<CategoryListItem> listItems, MvpView mvpView) {
        this.context = context;
        this.listItems = listItems;
        this.mvpView = mvpView;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ind_rv_cateogorydashboard, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (listItems.get(position).isStatus()) {
            holder.txtName.setText(listItems.get(position).getName());
        }


    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_name)
        TextView txtName;
        @BindView(R.id.cons)
        ConstraintLayout cons;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.cons)
        public void onViewClicked() {
//            Intent intent = new Intent(context, ShopIndProductList.class);
//
//            Bundle bundle = new Bundle();
//            bundle.putString("id", String.valueOf(listItems.get(getAdapterPosition()).getId()));
//            intent.putExtras(bundle);
//            context.startActivity(intent);
            mvpView.getMyClickPosition(String .valueOf(listItems.get(getAdapterPosition()).getId()),listItems.get(getAdapterPosition()).getName());
        }
    }
}
