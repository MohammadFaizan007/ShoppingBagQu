package com.shoppingbag.mlm.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shoppingbag.R;
import com.shoppingbag.model.response.team.ResultItem;
import com.shoppingbag.retrofit.MvpView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DownlineMemberAdapter extends RecyclerView.Adapter<DownlineMemberAdapter.ViewHolder> {


    private Activity mContext;
    private List<ResultItem> list;
    private MvpView mvpView;

    public DownlineMemberAdapter(Activity context, List<ResultItem> list, MvpView mvp) {
        mContext = context;
        this.list = list;
        mvpView = mvp;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.downline_item_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int listPosition) {
        if (listPosition % 2 == 0) {
            holder.lin.setBackgroundColor(mContext.getResources().getColor(R.color.lightwiht));
        } else {
            holder.lin.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        }

        holder.tvLoginId.setText(list.get(listPosition).getLoginId());
        holder.tvMemberName.setText(String.format("%s %s", list.get(listPosition).getFirstName(), list.get(listPosition).getLastName()));
        holder.tvJoingDate.setText(list.get(listPosition).getjoiningDate());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void updatelist(List<ResultItem> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_login_id)
        TextView tvLoginId;
        @BindView(R.id.tv_member_name)
        TextView tvMemberName;
        @BindView(R.id.tv_joing_date)
        TextView tvJoingDate;
        @BindView(R.id.btn_teanandbusiness)
        TextView btnTeanandbusiness;
        @BindView(R.id.lin)
        LinearLayout lin;


        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            btnTeanandbusiness.setOnClickListener(v -> {
                Log.e("MEMBER ID", "= " + list.get(getAdapterPosition()).getMemberId());
                mvpView.getMyClickPosition(list.get(getAdapterPosition()).getFirstName() + " " + list.get(getAdapterPosition()).getLastName(), String.valueOf(list.get(getAdapterPosition()).getMemberId()));

            });

        }
    }
}
