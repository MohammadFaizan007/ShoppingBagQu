package com.shoppingbag.common_activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shoppingbag.constants.BaseActivity;
import com.shoppingbag.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends BaseActivity {

    @BindView(R.id.search)
    SearchView search;
    @BindView(R.id.rv_search_items)
    RecyclerView rvSearchItems;
    Bundle param = new Bundle();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);
        ButterKnife.bind(this);

        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(RecyclerView.VERTICAL);
        rvSearchItems.setLayoutManager(manager);

//        if (NetworkUtils.getConnectivityStatus(context) != 0) searchList("");
//        else
//            showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);

        search.setOnClickListener(v -> search.onActionViewExpanded());

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
//                searchList(s);
                return false;
            }
        });
    }

//    public void searchList(String key) {
//        try {
//            JsonObject param = new JsonObject();
//            param.addProperty("CreatedBy", Cons.decryptMsg(PreferencesManager.getInstance(context).getUSERID(), cross_intent));
//            param.addProperty("SearchString", key);
//
//            Call<JsonObject> call = apiServicesIIShoppingV2.getSearchList(bodyParam(param), PreferencesManager.getInstance(context).getANDROIDID());
//            call.enqueue(new Callback<JsonObject>() {
//                @Override
//                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
//                    hideLoading();
//                    LoggerUtil.logItem(response.body());
//                    LoggerUtil.logItem(response.code());
//                    ResponseSearchItems responseSearchItems;
//                    try {
//                        if (response.isSuccessful()) {
//                            String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent);
//                            LoggerUtil.logItem(paramResponse);
////                        Log.e("============ ", paramResponse);
//                            Gson gson = new GsonBuilder().create();
//                            responseSearchItems = gson.fromJson(paramResponse, ResponseSearchItems.class);
////                        Log.e("============ ", String.valueOf(responseSearchItems));
//
//                            if (response.body() != null && responseSearchItems.getResponse().equalsIgnoreCase("Success")) {
//                                SearchAdapter adapter = new SearchAdapter(context, responseSearchItems, SearchActivity.this);
//                                rvSearchItems.setAdapter(adapter);
//                            }
//                        } else {
//                            clearPrefrenceforTokenExpiry();
//                            getAndroidId();
//                            goToActivityWithFinish(LoginActivity.class, null);
//                        }
//                    } catch (Exception | Error e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                @Override
//                public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
//                    hideLoading();
//                }
//            });
//        } catch (Error | Exception e) {
//            e.printStackTrace();
//        }
//    }


    @Override
    public void openSearchCategory(String searchItemId, String searchName) {
        super.openSearchCategory(searchItemId, searchName);
    }

}
