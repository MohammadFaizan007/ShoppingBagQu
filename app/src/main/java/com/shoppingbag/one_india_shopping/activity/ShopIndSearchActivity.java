package com.shoppingbag.one_india_shopping.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shoppingbag.constants.BaseActivity;
import com.shoppingbag.constants.Cons;
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

public class ShopIndSearchActivity extends BaseActivity {
    @BindView(R.id.side_menu)
    ImageView sideMenu;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.img_cart)
    ImageView imgCart;
    @BindView(R.id.txt_count)
    TextView txtCount;
    @BindView(R.id.btn_filter)
    Button btnFilter;
    @BindView(R.id.search_edit)
    EditText search_edit;
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
        setContentView(R.layout.ind_search_layout);
        ButterKnife.bind(this);
        title.setText("Search Products");
        layoutManager = new GridLayoutManager(context, 2);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        rvProductitems.setLayoutManager(layoutManager);
        rvProductitems.setHasFixedSize(true);

        search_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()==0){
                    adapter = new IndProductListAdapter(context, listItems);
                    rvProductitems.setAdapter(adapter);
                }
                if (s.length() > 3) {
                    if (NetworkUtils.getConnectivityStatus(context) != 0) {
                        hitSearchProduct(s.toString());
                        hideKeyboard();
                    } else {
                        showMessage(context.getString(R.string.alert_internet));
                    }

                }
            }
        });
        rvProductitems.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard();
                return false;
            }
        });
    }

    public void hitSearchProduct(String name) {
        try {
            showLoading();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("name", name);
            LoggerUtil.logItem(jsonObject);
            Call<JsonObject> jsonObjectCall = apiServicesOneStoreIndia.executesearchProducts(bodyParamOneStoreIndia(jsonObject));
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
                              //  if (pageNo == 1) {
                                    txtNorecordfound.setVisibility(View.GONE);
                                    rvProductitems.setVisibility(View.VISIBLE);

                                    listItems = productList.getProductList();
                                    adapter = new IndProductListAdapter(context, listItems);
                                    rvProductitems.setAdapter(adapter);
                                /*} else {
                                    listItems.addAll(productList.getProductList());
                                    adapter.updatelist(listItems);
                                }*/
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
               // goToActivity(ShopIndSearchActivity.this, ShopIndCartItems.class, null);
                break;
        }
    }
}

