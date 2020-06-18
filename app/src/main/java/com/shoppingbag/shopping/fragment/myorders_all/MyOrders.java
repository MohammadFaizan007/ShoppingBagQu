package com.shoppingbag.shopping.fragment.myorders_all;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.shoppingbag.constants.BaseFragment;
import com.shoppingbag.R;
import com.google.android.material.tabs.TabLayout;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.listeners.IPickResult;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MyOrders extends BaseFragment implements IPickResult {

    Unbinder unbinder;
    @BindView(R.id.place_pager)
    ViewPager vpPager;

    @BindView(R.id.tablayout)
    TabLayout tablayout;

    MyPagerAdapter adapterViewPager;
    Fragment currentFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myorders_new, container, false);
        unbinder = ButterKnife.bind(this, view);
        currentFragment = new OtherOrders();
        return view;
    }

    @Override
    public void onViewCreatedStuff(View view, @Nullable Bundle savedInstanceState) {
        adapterViewPager = new MyPagerAdapter(getChildFragmentManager());
        vpPager.setAdapter(adapterViewPager);
        tablayout.setupWithViewPager(vpPager);
        currentFragment = adapterViewPager.getItem(0);

        vpPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                currentFragment = adapterViewPager.getItem(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        currentFragment.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onPickResult(PickResult pickResult) {
        //Log.e("RESULT _MY ORDER", " = REACH");
        ((AmazonOrders) (currentFragment)).onPickResult(pickResult);
    }

    public class MyPagerAdapter extends FragmentPagerAdapter {
        Fragment allOrders = new AllOrders();
        Fragment amazonOrders = new AmazonOrders();
        Fragment flipkartOrders = new FlipkartOrders();
        Fragment rechargebillOrders = new RechargeBillOrders();
        Fragment swiggyOrders = new SwiggyOrders();
        Fragment oyoOrders = new OyoOrders();
        Fragment otherOrders = new OtherOrders();
        private int NUM_ITEMS = 8;

        MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return allOrders;
                case 1:
                    return rechargebillOrders;
                case 2:
                    return amazonOrders;
                case 3:
                    return flipkartOrders;
                case 4:
                    return swiggyOrders;
                case 5:
                    return oyoOrders;
                case 6:
                    return otherOrders;
                default:
                    return null;
            }
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "All";
                case 1:
                    return "Recharge & Bills";
                case 2:
                    return "Amazon";
                case 3:
                    return "Flipkart";
                case 4:
                    return "Swiggy";
                case 5:
                    return "Oyo";
                case 6:
                    return getResources().getString(R.string.others);
            }
            return "";
        }
    }


}
