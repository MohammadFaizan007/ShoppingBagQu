package com.shoppingbag.one_india_shopping.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shoppingbag.constants.BaseActivity;
import com.shoppingbag.constants.Cons;
import com.shoppingbag.utils.HidingScrollListener;
import com.shoppingbag.utils.LoggerUtil;
import com.shoppingbag.utils.NetworkUtils;
import com.shoppingbag.R;
import com.shoppingbag.one_india_shopping.adapter.IndProductListAdapter;
import com.shoppingbag.one_india_shopping.model.ProductListItem;
import com.shoppingbag.one_india_shopping.model.ResponseProductList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.shoppingbag.app.AppConfig.PAYLOAD_BUNDLE;

public class ShopIndProductList extends BaseActivity {
    @BindView(R.id.side_menu)
    ImageView sideMenu;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.img_cart)
    ImageView imgCart;
    @BindView(R.id.txt_count)
    TextView txtCount;
    @BindView(R.id.txt_note)
    TextView txt_note;
    @BindView(R.id.btn_filter)
    Button btnFilter;
    @BindView(R.id.btn_sort)
    Button btnSort;
    @BindView(R.id.rv_productitems)
    RecyclerView rvProductitems;
    @BindView(R.id.txt_norecordfound)
    TextView txtNorecordfound;
    private IndProductListAdapter adapter;
    private List<ProductListItem> listItems = new ArrayList<>();
    private int pageNo = 1;
    private int pageSize = 20;
    private boolean once = true;
    private GridLayoutManager layoutManager;
    private Bundle bundle;
    private String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ind_productlist);
        ButterKnife.bind(this);

        bundle = getIntent().getBundleExtra(PAYLOAD_BUNDLE);
        Log.e("<><><><>", "=======>" + bundle.getString("id"));
        id = bundle.getString("id");
        title.setText(bundle.getString("name"));
        if (id.equalsIgnoreCase("151") || id.equalsIgnoreCase("152")) {
            txt_note.setVisibility(View.VISIBLE);
        } else {
            txt_note.setVisibility(View.GONE);
        }

        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            getProductList(id);
        } else {
            showMessage(context.getString(R.string.alert_internet));
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    private void updateScroll() {
        layoutManager = new GridLayoutManager(context, 2);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        rvProductitems.setLayoutManager(layoutManager);
        rvProductitems.setHasFixedSize(true);
        rvProductitems.addOnScrollListener(new HidingScrollListener(layoutManager) {
            @Override
            public void onHide() {
            }

            @Override
            public void onLoadMore(int i) {
                if (NetworkUtils.getConnectivityStatus(context) != 0) {
                    if (listItems.size() == pageNo * pageSize && once) {
                        once = false;
                        pageNo = i;
                        Log.e("Reach", "= " + pageNo);
                        getProductList(id);
                    }
                }
            }

            @Override
            public void onShow() {

            }
        });

    }

    public void getProductList(String id) {
        try {
            showLoading();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("id", id);
            jsonObject.addProperty("page_size", pageSize);
            jsonObject.addProperty("page_no", pageNo);
            LoggerUtil.logItem(jsonObject);
            Call<JsonObject> jsonObjectCall = apiServicesOneStoreIndia.getProductDetails(bodyParamOneStoreIndia(jsonObject));
            jsonObjectCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                    hideLoading();
                    LoggerUtil.logItem(response.body());
                    LoggerUtil.logItem(response.code());
                    try {
                        once = true;
                        if (response.isSuccessful()) {
                            String param = Cons.decryptMsg(response.body().get("body").getAsString(), easy_pay_oneStoreIndia);
                            LoggerUtil.logItem(param);
                            Gson gson = new GsonBuilder().create();
                            ResponseProductList productList = gson.fromJson(param, ResponseProductList.class);
                            if (productList.getProductList() != null && productList.getProductList().size() > 0) {
                                if (pageNo == 1) {
                                    txtNorecordfound.setVisibility(View.GONE);
                                    rvProductitems.setVisibility(View.VISIBLE);
                                    updateScroll();
                                    listItems = productList.getProductList();
                                    adapter = new IndProductListAdapter(context, listItems);
                                    rvProductitems.setAdapter(adapter);
                                } else {
                                    listItems.addAll(productList.getProductList());
                                    adapter.updatelist(listItems);
                                }
                            } else {
                                rvProductitems.setVisibility(View.GONE);
                                txtNorecordfound.setVisibility(View.VISIBLE);
                            }
                        } else {
                            showMessage("something went wrong");
                            rvProductitems.setVisibility(View.GONE);
                            txtNorecordfound.setVisibility(View.VISIBLE);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<JsonObject> call, @NotNull Throwable t) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @OnClick({R.id.side_menu, R.id.img_cart})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.side_menu:
                finish();
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                break;
            case R.id.img_cart:
                goToActivity(ShopIndProductList.this, ShopIndCartItems.class, null);
                break;
        }
    }
}
