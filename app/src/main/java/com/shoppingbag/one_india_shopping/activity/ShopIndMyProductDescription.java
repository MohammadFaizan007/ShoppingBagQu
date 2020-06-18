package com.shoppingbag.one_india_shopping.activity;

import android.graphics.Paint;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.shoppingbag.BuildConfig;
import com.shoppingbag.R;
import com.shoppingbag.app.AppConfig;
import com.shoppingbag.app.PreferencesManager;
import com.shoppingbag.constants.BaseActivity;
import com.shoppingbag.constants.Cons;
import com.shoppingbag.one_india_shopping.adapter.IndProdcutColorAdapter;
import com.shoppingbag.one_india_shopping.adapter.IndProductImagesAdapter;
import com.shoppingbag.one_india_shopping.adapter.IndProductSizeAdapter;
import com.shoppingbag.one_india_shopping.model.add_to_cart.AddToCartModel;
import com.shoppingbag.one_india_shopping.model.cartitem.ResponseCartItem;
import com.shoppingbag.one_india_shopping.model.product_description_new.MediaGalleryItem;
import com.shoppingbag.one_india_shopping.model.product_description_new.ResponseProductDescriptionNew;
import com.shoppingbag.one_india_shopping.model.product_description_new.ValuesItem;
import com.shoppingbag.utils.LoggerUtil;
import com.shoppingbag.utils.NetworkUtils;
import com.shoppingbag.utils.Utils;
import com.travijuu.numberpicker.library.NumberPicker;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShopIndMyProductDescription extends BaseActivity {

    @BindView(R.id.side_menu)
    ImageView sideMenu;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.img_cart)
    ImageView imgCart;
    @BindView(R.id.txt_count)
    TextView txtCount;

    @BindView(R.id.img_product)
    ImageView imgProduct;
    @BindView(R.id.txt_productname)
    TextView txtProductname;
    @BindView(R.id.txt_offerPrice)
    TextView txt_offerPrice;
    @BindView(R.id.txt_fixedprice)
    TextView txtFixedprice;
    @BindView(R.id.qtyBox)
    NumberPicker qtyBox;
    @BindView(R.id.rv_productimage)
    RecyclerView rvProductimage;
    @BindView(R.id.rv_size)
    RecyclerView rvSize;
    @BindView(R.id.rv_color)
    RecyclerView rvColor;
    @BindView(R.id.txt_productdetails)
    TextView txtProductdetails;
    @BindView(R.id.bnt_addtocart)
    Button bntAddtocart;
    @BindView(R.id.txtproductsku)
    TextView txtproductsku;
    @BindView(R.id.txt_productNote)
    TextView txt_productNote;
    @BindView(R.id.txtstock)
    TextView txtstock;
    @BindView(R.id.cardcolor)
    CardView cardcolor;
    @BindView(R.id.cardsize)
    CardView cardsize;
    @BindView(R.id.btnbuynow)
    Button btnbuynow;
    @BindView(R.id.consmain)
    ConstraintLayout consmain;
    private Bundle bundle;
    private String sku;
    private List<MediaGalleryItem> mediaGalleryItems = new ArrayList<>();
    private List<ValuesItem> sizeItemList = new ArrayList<>();
    private List<ValuesItem> colorItems = new ArrayList<>();
    private IndProductImagesAdapter imagesAdapter;
    private IndProductSizeAdapter sizeAdapter;
    private IndProdcutColorAdapter colorAdapter;
    private String optionColorId = "", optionSizeId = "";
    private String optionColorValue = "", optionSizeValue = "";
    private String type;
    public int cartCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newproductdescription);
        ButterKnife.bind(this);
        imgCart.setVisibility(View.VISIBLE);
        txtCount.setVisibility(View.VISIBLE);

        cartCounter = PreferencesManager.getInstance(context).getCartCount();
        txtCount.setText(String.format("%s", cartCounter));
        bundle = getIntent().getBundleExtra(AppConfig.PAYLOAD_BUNDLE);
        bundle = getIntent().getExtras();
        Log.e("check", "check" + bundle.getString("sku"));
        if (bundle != null) {
            sku = bundle.getString("sku");
        }


        // Colors Adapter
        LinearLayoutManager layoutManagerColor = new LinearLayoutManager(this);
        layoutManagerColor.setOrientation(RecyclerView.HORIZONTAL);
        rvColor.setLayoutManager(layoutManagerColor);


        // Product Image Adapter
        LinearLayoutManager layoutManagerImage = new LinearLayoutManager(this);
        layoutManagerImage.setOrientation(RecyclerView.HORIZONTAL);
        rvProductimage.setLayoutManager(layoutManagerImage);


        // Product Size Adapter
        LinearLayoutManager layoutManagerSize = new LinearLayoutManager(this);
        layoutManagerSize.setOrientation(RecyclerView.HORIZONTAL);
        rvSize.setLayoutManager(layoutManagerSize);


    }


    @Override
    protected void onResume() {
        super.onResume();
        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            getProductDescription();
        }
    }

    private void getProductDescription() {
        try {
            showLoading();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("sku", sku);
            LoggerUtil.logItem(jsonObject);
            Call<JsonObject> jsonObjectCall = apiServicesOneStoreIndia.getProductDescription(bodyParamOneStoreIndia(jsonObject));
            jsonObjectCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                    hideLoading();
                    LoggerUtil.logItem(response.body());
                    LoggerUtil.logItem(response.code());
                    try {
                        if (response.isSuccessful()) {     
                            String param = Cons.decryptMsg(response.body().get("body").getAsString(), easy_pay_oneStoreIndia);
                            Gson gson = new GsonBuilder().create();
                            LoggerUtil.logItem(param);
                            ResponseProductDescriptionNew productDescription = gson.fromJson(param, ResponseProductDescriptionNew.class);


                            title.setText(productDescription.getName().trim());
                            consmain.setVisibility(View.VISIBLE);
                            if (productDescription.getMediaGallery() != null) {
                                mediaGalleryItems = productDescription.getMediaGallery();
                                imagesAdapter = new IndProductImagesAdapter(context, mediaGalleryItems, ShopIndMyProductDescription.this);
                                rvProductimage.setAdapter(imagesAdapter);

                                Glide.with(context).load(BuildConfig.BASE_Image_URL_OnestoreIndia + mediaGalleryItems.get(0).getFile())
                                        .apply(new RequestOptions().placeholder(R.drawable.image_not_available)).into(imgProduct);

                            }
                            if (productDescription.getStockItem().isIsInStock()) {

                                txtstock.setText("In Stock");
                            } else {
                                txtstock.setText("Out of Stock");
                            }
                            if(productDescription.getCashback()>0){
                                txt_productNote.setText("Note:- "+productDescription.getCashback()+" % Wallet Cashback on this product");
                            }
                            if (productDescription.getOfferprice() != null && !productDescription.getOfferprice().equalsIgnoreCase("")) {
                                txt_offerPrice.setText(String.format("₹ %s", Utils.getValue(Double.parseDouble(productDescription.getOfferprice()))));
                                txtFixedprice.setVisibility(View.VISIBLE);
                                txtFixedprice.setText(String.format("₹ %s", Utils.getValue(productDescription.getPrice())));
                                txtFixedprice.setPaintFlags(txtFixedprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                                txt_offerPrice.setGravity(Gravity.END);

                            } else {
                                txt_offerPrice.setText(String.format("₹ %s", Utils.getValue(productDescription.getPrice())));
                                txtFixedprice.setVisibility(View.GONE);

                            }

                            if (productDescription.getDescription() != null) {
                                txtProductdetails.setText(Html.fromHtml(productDescription.getDescription() + " \n" + productDescription.getShortDescription()));

                            }

                            txtproductsku.setText(productDescription.getSku());


                            type = productDescription.getTypeId();

                            txtProductname.setText(productDescription.getName());

                            if (productDescription.getOption() != null && productDescription.getOption().size() > 0) {
                                if (productDescription.getOption().get(0).getValues() != null) {
//                                    txtSize.setVisibility(View.VISIBLE);
//                                    rvSize.setVisibility(View.VISIBLE);
                                    cardsize.setVisibility(View.VISIBLE);
                                    if (productDescription.getOption().get(0).getTitle().equalsIgnoreCase("Size")) {
                                        if (productDescription.getOption().get(0).getValues().size() > 0) {
                                            optionSizeId = String.valueOf(productDescription.getOption().get(0).getOptionId());
                                            optionSizeValue = String.valueOf(productDescription.getOption().get(0).getValues().get(0).getOptionTypeId());
                                            sizeItemList = productDescription.getOption().get(0).getValues();
                                            sizeAdapter = new IndProductSizeAdapter(context, sizeItemList, optionSizeId, ShopIndMyProductDescription.this);
                                            rvSize.setAdapter(sizeAdapter);
                                        }

                                    } else {
                                        if (productDescription.getOption().get(1).getValues().size() > 1) {
                                            optionSizeId = String.valueOf(productDescription.getOption().get(1).getOptionId());
                                            optionSizeValue = String.valueOf(productDescription.getOption().get(1).getValues().get(0).getOptionTypeId());
                                            sizeItemList = productDescription.getOption().get(1).getValues();
                                            sizeAdapter = new IndProductSizeAdapter(context, sizeItemList, optionSizeId, ShopIndMyProductDescription.this);
                                            rvSize.setAdapter(sizeAdapter);
                                        }
                                    }


                                } else {
//                                    txtSize.setVisibility(View.GONE);
//                                    rvSize.setVisibility(View.GONE);
                                    cardsize.setVisibility(View.GONE);
                                }


                                if (productDescription.getOption().get(1).getValues() != null) {
//                                    txtColor.setVisibility(View.VISIBLE);
//                                    rvColor.setVisibility(View.VISIBLE);
                                    cardcolor.setVisibility(View.VISIBLE);
                                    if (productDescription.getOption().get(1).getTitle().equalsIgnoreCase("Color")) {
                                        if (productDescription.getOption().get(1).getValues().size() > 1) {
                                            optionColorId = String.valueOf(productDescription.getOption().get(1).getOptionId());
                                            optionColorValue = String.valueOf(productDescription.getOption().get(1).getValues().get(0).getOptionTypeId());
                                            colorItems = productDescription.getOption().get(1).getValues();
                                            colorAdapter = new IndProdcutColorAdapter(context, colorItems, optionColorId, ShopIndMyProductDescription.this);
                                            rvColor.setAdapter(colorAdapter);
                                        }
                                    } else {
                                        if (productDescription.getOption().get(0).getValues().size() > 0) {
                                            optionColorId = String.valueOf(productDescription.getOption().get(0).getOptionId());
                                            optionColorValue = String.valueOf(productDescription.getOption().get(0).getValues().get(0).getOptionTypeId());
                                            colorItems = productDescription.getOption().get(0).getValues();
                                            colorAdapter = new IndProdcutColorAdapter(context, colorItems, optionColorId, ShopIndMyProductDescription.this);
                                            rvColor.setAdapter(colorAdapter);
                                        }
                                    }


                                } else {
//                                    txtColor.setVisibility(View.GONE);
//                                    rvColor.setVisibility(View.GONE);
                                    cardcolor.setVisibility(View.GONE);
                                }
                            } else {
//                                txtColor.setVisibility(View.GONE);
//                                rvColor.setVisibility(View.GONE);
//                                txtSize.setVisibility(View.GONE);
//                                rvSize.setVisibility(View.GONE);
                                cardcolor.setVisibility(View.GONE);
                                cardsize.setVisibility(View.GONE);
                            }

                        } else {
                            showMessage("something went wrong");

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

    @Override
    public void getClickPosition(int position, String tag) {
        super.getClickPosition(position, tag);

        Glide.with(context).load(BuildConfig.BASE_Image_URL_OnestoreIndia + mediaGalleryItems.get(position).getFile())
                .apply(new RequestOptions().placeholder(R.drawable.image_not_available)).into(imgProduct);

        //  imgProduct.setBackgroundResource(R.drawable.size_border);

    }

    @Override
    public void getMyClickPosition(String name, String tag) {
        super.getMyClickPosition(name, tag);
        Log.e("<><>", "<><><>//" + name);
        Log.e("<><>", "//<><><>" + tag);
        optionColorId = tag;
        optionColorValue = name;

        //  optionId = tag;
        //optionValue = name;

    }

    @Override
    public void getGiftCardCategoryId(String id, String name) {
        super.getGiftCardCategoryId(id, name);
        optionSizeId = name;
        optionSizeValue = id;
    }

    public void addToCart(int qty, boolean buynow) {
        showLoading();
        JsonObject cart = new JsonObject();
        cart.addProperty("sku", sku);
        cart.addProperty("qty", qty);
        cart.addProperty("token", pref.getString("token", ""));
        JsonArray options = new JsonArray();
        JsonObject optionColor = new JsonObject();
        optionColor.addProperty("option_id", optionColorId);
        optionColor.addProperty("option_value", optionColorValue);
        JsonObject optionSize = new JsonObject();
        optionSize.addProperty("option_id", optionSizeId);
        optionSize.addProperty("option_value", optionSizeValue);
        options.add(optionColor);
        options.add(optionSize);
        cart.add("custom_options", options);
        //  cart.addProperty("option_id", optionId);
        //  cart.addProperty("option_value", optionValue);
        cart.addProperty("type", type);
        LoggerUtil.logItem(cart);
        Call<JsonObject> jsonObjectCall = apiServicesOneStoreIndia.getGuestAddTo(bodyParamOneStoreIndia(cart));
        jsonObjectCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                hideLoading();
                LoggerUtil.logItem(response.code());
                LoggerUtil.logItem(response.body());
                try {
                    if (response.isSuccessful()) {
                        try {
                            String param = Cons.decryptMsg(response.body().get("body").getAsString(), easy_pay_oneStoreIndia);
                            Gson gson = new GsonBuilder().create();
                            LoggerUtil.logItem(param);
                            AddToCartModel addToCartModel = gson.fromJson(param, AddToCartModel.class);
                            if (addToCartModel.getResponse().equalsIgnoreCase("success")) {
                                showMessage(addToCartModel.getMessage());
                                getCartItems();
                                if (buynow) {
                                    goToActivity(ShopIndMyProductDescription.this, ShopIndCartItems.class, null);
                                }
                            } else {
                                txtCount.setText("0");
                                showMessage(addToCartModel.getMessage());
                            }
                            LoggerUtil.logItem(param);


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NotNull Call<JsonObject> call, @NotNull Throwable t) {

            }
        });
    }


    public void getCartItems() {
        try {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("token", pref.getString("token", ""));

            LoggerUtil.logItem(jsonObject);
            Call<JsonObject> jsonObjectCall = apiServicesOneStoreIndia.getItems(bodyParamOneStoreIndia(jsonObject));
            jsonObjectCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                    try {
                        LoggerUtil.logItem(response.code());
                        LoggerUtil.logItem(response.body());
                        if (response.isSuccessful()) {
                            String param = Cons.decryptMsg(response.body().get("body").getAsString(), easy_pay_oneStoreIndia);
                            Gson gson = new GsonBuilder().create();
                            LoggerUtil.logItem(param);
                            ResponseCartItem cartItem = gson.fromJson(param, ResponseCartItem.class);
                            cartCounter = cartItem.getCartitems().size();
                            PreferencesManager.getInstance(context).setCartCount(cartCounter);
                            txtCount.setText(String.format("%s", cartCounter));
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


    @OnClick({R.id.side_menu, R.id.img_cart, R.id.bnt_addtocart, R.id.btnbuynow})
    public void onViewClicked(View view) {
        try {

            switch (view.getId()) {
                case R.id.side_menu:
                    finish();
                    overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                    break;
                case R.id.img_cart:
                    goToActivityWithFinish(ShopIndMyProductDescription.this, ShopIndCartItems.class, null);
                    break;
                case R.id.bnt_addtocart:
                    if (NetworkUtils.getConnectivityStatus(context) != 0) {
                        addToCart(qtyBox.getValue(), false);
                    }
                    break;
                case R.id.btnbuynow:
                    if (NetworkUtils.getConnectivityStatus(context) != 0) {
                        addToCart(qtyBox.getValue(), true);
                    }
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
