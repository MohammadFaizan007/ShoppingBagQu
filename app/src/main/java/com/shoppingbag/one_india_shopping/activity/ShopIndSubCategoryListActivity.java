package com.shoppingbag.one_india_shopping.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shoppingbag.constants.BaseActivity;
import com.shoppingbag.R;
import com.shoppingbag.one_india_shopping.adapter.SubCategoryAdapter;
import com.shoppingbag.one_india_shopping.model.category_list_response.ChildrenDataItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.shoppingbag.app.AppConfig.PAYLOAD_BUNDLE;

public class ShopIndSubCategoryListActivity extends BaseActivity {
    @BindView(R.id.side_menu)
    ImageView sideMenu;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.img_cart)
    ImageView imgCart;
    @BindView(R.id.txt_count)
    TextView txtCount;
    @BindView(R.id.sub_cat_recycler)
    RecyclerView subCatRecycler;
    Bundle param;
    List<ChildrenDataItem> list = new ArrayList<>();
    SubCategoryAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ind_sub_category_activity);
        ButterKnife.bind(this);
        param = getIntent().getBundleExtra(PAYLOAD_BUNDLE);
        list = new Gson().fromJson(param.getString("list"), new TypeToken<List<ChildrenDataItem>>() {
        }.getType());
        title.setText(param.getString("name"));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        subCatRecycler.setLayoutManager(linearLayoutManager);
        adapter = new SubCategoryAdapter(this, list, this);
        subCatRecycler.setAdapter(adapter);

    }

    @OnClick(R.id.side_menu)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.side_menu:
                finish();
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                break;
        }
    }

    @Override
    public void getClickPosition(int position, String tag) {
        super.getClickPosition(position, tag);
        Bundle bundle = new Bundle();
        bundle.putString("id", String.valueOf(position));
        bundle.putString("name", tag);
        goToActivity(this, ShopIndProductList.class, bundle);
    }

    @Override
    public void getClickChildPosition(String name, String tag, Bundle bundle) {
        super.getClickChildPosition(name, tag, bundle);
        Bundle bundle1 = new Bundle();
        bundle1.putString("id", name);
        bundle1.putString("name", tag);
        goToActivity(this, ShopIndProductList.class, bundle1);
    }
}
