package com.shoppingbag.utilities.bus;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.shoppingbag.constants.BaseActivity;
import com.shoppingbag.model.response.bus_response.responsepojo.BoardingPointsItem;
import com.shoppingbag.model.response.bus_response.responsepojo.DroppingPointsItem;
import com.shoppingbag.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.shoppingbag.app.AppConfig.PAYLOAD_BUNDLE;


public class BusBoardingPoint extends BaseActivity {
    public TabLayout tabLayout;
    public List<BoardingPointsItem> boardingPointsItems;
    public List<DroppingPointsItem> droppingPointsItems;
    public String originName = "", destinationName = "", arrivalTimeStr = "", departTimeStr = "";
    public Bundle param;
    public String boardingId = "", droppingId = "";
    @BindView(R.id.side_menu)
    ImageView sideMenu;
    @BindView(R.id.title)
    TextView title;

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bus_boarding_dropping_point);
        ButterKnife.bind(this);
        viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        TabLayout.Tab tab = tabLayout.getTabAt(0);
        tab.select();

        param = getIntent().getBundleExtra(PAYLOAD_BUNDLE);
        if (param != null) {
            boardingPointsItems = (List<BoardingPointsItem>) param.getSerializable("BoardingList");
            droppingPointsItems = (List<DroppingPointsItem>) param.getSerializable("DroppingList");
            originName = param.getString("originName");
            destinationName = param.getString("destinationName");
            arrivalTimeStr = param.getString("arrivalTimeStr");
            departTimeStr = param.getString("departTimeStr");
        }
        title.setText(String.format("%s - %s", originName, destinationName));

    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new BoardingPoint(), "BOARDING");
        adapter.addFragment(new DroppingPoint(), "DROPPING");
        viewPager.setAdapter(adapter);
    }

    public void pressBack(View v) {
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    @OnClick(R.id.side_menu)
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.side_menu:
                finish();
                overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right);
                break;
        }
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}