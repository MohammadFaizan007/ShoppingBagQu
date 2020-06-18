package com.shoppingbag.utilities.bus;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shoppingbag.constants.BaseFragment;
import com.shoppingbag.retrofit.MvpView;
import com.shoppingbag.R;
import com.shoppingbag.utilities.bus.adapter.BoardingPointAdapter;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class BoardingPoint extends BaseFragment implements MvpView {
    Unbinder unbinder;
    @BindView(R.id.dropping_list)
    RecyclerView droppingList;
    BoardingPointAdapter boardingPointAdapter;
    LinearLayoutManager linearLayoutManager;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bus_dropping_point, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreatedStuff(View view, @Nullable Bundle savedInstanceState) {
        droppingList.setVisibility(View.VISIBLE);
        linearLayoutManager = new LinearLayoutManager(context);
        droppingList.setLayoutManager(linearLayoutManager);
        droppingList.setItemAnimator(new DefaultItemAnimator());
        boardingPointAdapter = new BoardingPointAdapter(context, ((BusBoardingPoint) getActivity()).boardingPointsItems, BoardingPoint.this);
        droppingList.setAdapter(boardingPointAdapter);

    }

    @Override
    public void getClickPosition(int position, String tag) {
        ((BusBoardingPoint) getActivity()).boardingId = tag;
        Log.e("Boarding_id>>>", tag);
        ((BusBoardingPoint) getActivity()).param.putString("bordingId", ((BusBoardingPoint) getActivity()).boardingId);
        ((BusBoardingPoint) getActivity()).tabLayout.getTabAt(1).select();

    }

}