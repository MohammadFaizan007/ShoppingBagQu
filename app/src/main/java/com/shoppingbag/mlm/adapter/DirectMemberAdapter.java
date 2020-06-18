package com.shoppingbag.mlm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.shoppingbag.R;
import com.shoppingbag.model.response.team.ResultItem;
import com.shoppingbag.retrofit.MvpView;
import com.shoppingbag.utils.LoggerUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DirectMemberAdapter extends RecyclerView.Adapter<DirectMemberAdapter.ViewHolder> {



    private Context mContext;
    private List<ResultItem> list;
    private MvpView mvpView;
    private String id;

    public DirectMemberAdapter(Context context, List<ResultItem> list, MvpView mvp, String rootId) {
        mContext = context;
        this.list = list;
        mvpView = mvp;
        id = rootId;
    }

    public void updateList(List<ResultItem> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.direct_member_item_lay, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int listPosition) {

        if (listPosition % 2 == 0) {
            holder.cons.setBackgroundColor(mContext.getResources().getColor(R.color.lightwiht));
        } else {
            holder.cons.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        }
        holder.txtMemberId.setText(list.get(listPosition).getLoginId());
        holder.txtMemberName.setText(String.format("%s %s", list.get(listPosition).getFirstName(), list.get(listPosition).getLastName()));
        holder.txtJoiningDate.setText(list.get(listPosition).getjoiningDate());
        LoggerUtil.logItem(listPosition);
//        if (DirectMember.previousIds.size() == 0) {
//            holder.tv_mob_no.setText(list.get(listPosition).getMobile());
//            holder.tv_mob_no.setVisibility(View.VISIBLE);
//            holder.textView11.setVisibility(View.VISIBLE);
//        } else {
        holder.tv_mob_no.setText("");
        holder.tv_mob_no.setVisibility(View.GONE);
        holder.textView11.setVisibility(View.GONE);
//        }

        holder.btnView.setVisibility(id.equalsIgnoreCase(String.valueOf(list.get(listPosition).getMemberId())) ? View.GONE : View.VISIBLE);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @OnClick(R.id.btn_teanandbusiness)
    public void onViewClicked() {
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textView11)
        TextView textView11;
        @BindView(R.id.tv_mob_no)
        TextView tv_mob_no;
        @BindView(R.id.txtMemberId)
        TextView txtMemberId;
        @BindView(R.id.txtMemberName)
        TextView txtMemberName;
        @BindView(R.id.txtJoiningDate)
        TextView txtJoiningDate;
        @BindView(R.id.btn_view)
        Button btnView;
        @BindView(R.id.btn_teanandbusiness)
        TextView btnTeanandbusiness;
        @BindView(R.id.cons)
        ConstraintLayout cons;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            btnView.setOnClickListener(v -> mvpView.getClickPositionDirectMember(getAdapterPosition(),
                    list.get(getAdapterPosition()).getFirstName(), String.valueOf(list.get(getAdapterPosition()).getMemberId())));
            btnTeanandbusiness.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mvpView.getMyClickPosition(list.get(getAdapterPosition()).getFirstName() + " " + list.get(getAdapterPosition()).getLastName(), String.valueOf(list.get(getAdapterPosition()).getMemberId()));

                }
            });


        }
    }
}

