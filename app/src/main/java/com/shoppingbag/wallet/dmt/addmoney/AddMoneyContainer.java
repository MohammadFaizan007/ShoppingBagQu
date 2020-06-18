package com.shoppingbag.wallet.dmt.addmoney;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerTabStrip;
import androidx.viewpager.widget.ViewPager;


import com.shoppingbag.constants.BaseActivity;
import com.shoppingbag.constants.BaseFragment;
import com.shoppingbag.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.shoppingbag.app.AppConfig.PAYLOAD_BUNDLE;

public class AddMoneyContainer extends BaseActivity {

    public Fragment currentFragment;
    @BindView(R.id.side_menu)
    ImageView sideMenu;
    @BindView(R.id.title)
    public TextView title;
    public String amount = "0";
    private Bundle bundle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_add_money);
        ButterKnife.bind(this);
        bundle = getIntent().getBundleExtra(PAYLOAD_BUNDLE);
        amount = bundle.getString("amount", "0");
        sideMenu.setOnClickListener(v -> onBackPressed());
        title.setText("Beneficiary List");
        replaceFragmentWithHome(new BeneficiaryList(), "Beneficiary List");
    }


    public void replaceFragmentWithHome(Fragment setFragment, String title) {
        currentFragment = setFragment;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame, setFragment, title);
        transaction.commit();
    }

    public void replaceFragmentAddBack(Fragment setFragment, String title) {
        currentFragment = setFragment;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction().addToBackStack(title);
        transaction.replace(R.id.frame, setFragment, title);
        transaction.commit();
    }

}
