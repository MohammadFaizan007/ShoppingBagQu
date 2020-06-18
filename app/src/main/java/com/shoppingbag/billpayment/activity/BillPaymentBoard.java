package com.shoppingbag.billpayment.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shoppingbag.constants.BaseActivity;
import com.shoppingbag.constants.Cons;
import com.shoppingbag.retrofit.MvpView;
import com.shoppingbag.utils.LoggerUtil;
import com.shoppingbag.R;
import com.shoppingbag.billpayment.adapter.BoardAdapter;
import com.shoppingbag.billpayment.adapter.ElectricityBoardAdapter;
import com.shoppingbag.billpayment.adapter.GasWaterAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BillPaymentBoard extends BaseActivity implements MvpView {

    @BindView(R.id.side_menu)
    ImageView sideMenu;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.list_item)
    RecyclerView listItem;

    BoardAdapter boardAdapter;
    ElectricityBoardAdapter electricity_boardAdapter;
    GasWaterAdapter gaswater_boardAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bill_list_layout);
        ButterKnife.bind(this);
        LoggerUtil.logItem("title");
        LoggerUtil.logItem(getIntent().getStringExtra("bill"));
        title.setText(getIntent().getStringExtra("bill"));
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(RecyclerView.VERTICAL);
        listItem.setLayoutManager(manager);

        getBoard(title.getText().toString());
        sideMenu.setOnClickListener(v -> onBackPressed());

    }

    @Override
    public void onBackPressed() {
        Intent resultIntent = new Intent();
        // TODO Add extras or a data URI to this intent as appropriate.
        setResult(Activity.RESULT_CANCELED, resultIntent);
        finish();
    }


    @Override
    public void getProviderHint(String providerName, String hint) {
        super.getProviderHint(providerName, hint);

        Intent resultIntent = new Intent();
        // TODO Add extras or a data URI to this intent as appropriate.
        resultIntent.putExtra("data", providerName);
        resultIntent.putExtra("hint", hint);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();

    }

    private void getBoard(String providerName) {
        switch (providerName) {
            case Cons.ELECTRICITY_BILL_PAYMENT:
                electricity_boardAdapter = new ElectricityBoardAdapter(context, this, Cons.allElectricityProviderlist);
                listItem.setAdapter(electricity_boardAdapter);
                break;
            case Cons.GAS_BILL_PAYMENT:
                gaswater_boardAdapter = new GasWaterAdapter(context, this, Cons.allProviderlistItemList.get(0).getGasBillPaymentProviderlist());
                listItem.setAdapter(gaswater_boardAdapter);
                break;
            case Cons.WATER_BILL_PAYMENT:
                gaswater_boardAdapter = new GasWaterAdapter(context, this, Cons.allProviderlistItemList.get(0).getWaterbillpaymentProviderlist());
                listItem.setAdapter(gaswater_boardAdapter);
                break;
            case Cons.INSURANCE_BILL_PAYMENT:
                boardAdapter = new BoardAdapter(context, this, Cons.allProviderlistItemList.get(0).getInsuranceProviderlist());
                listItem.setAdapter(boardAdapter);
                break;
            case Cons.BROADBAND_BILL_PAYMENT:
                boardAdapter = new BoardAdapter(context, this, Cons.allProviderlistItemList.get(0).getBroadbandProviderProviderlist());
                listItem.setAdapter(boardAdapter);
                break;
        }


    }


}
