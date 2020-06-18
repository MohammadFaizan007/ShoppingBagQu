package com.shoppingbag.utilities.domesticflight;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shoppingbag.constants.BaseActivity;
import com.shoppingbag.utils.Utils;
import com.shoppingbag.R;
import com.shoppingbag.utilities.domesticflight.adapter.AirportAdapter;
import com.shoppingbag.utilities.domesticflight.responsemodel.AirlineCodeItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AirportList extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.airport_recycler_list)
    RecyclerView airportRecyclerList;

    ArrayList<AirlineCodeItem> airportLists = new ArrayList<>();
    ArrayList<AirlineCodeItem> filterairportLists = new ArrayList<>();
    AirportAdapter airportAdapter;

    String CODE = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.airport_list);
        ButterKnife.bind(this);

        if (getIntent().getStringExtra("CODE") != null) {
            CODE = getIntent().getStringExtra("CODE");
        }

        Log.e("CODE", "= " + CODE);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_clear_white);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(RESULT_CANCELED, intent);
                finish();
            }
        });


        try {
            loadJSONFromAsset();

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

    }

    public void loadJSONFromAsset() throws IOException, JSONException {
        String json;
//        InputStream is = getAssets().open("airlinecode.json");
        InputStream is = getAssets().open("india_airlinecode.json");
        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();
        json = new String(buffer, StandardCharsets.UTF_8);

        JSONObject obj = new JSONObject(json);
        JSONArray airline_code_array = obj.getJSONArray("airline_code");


        for (int i = 0; i < airline_code_array.length(); i++) {
            JSONObject airport_obj = airline_code_array.getJSONObject(i);

            AirlineCodeItem item = new AirlineCodeItem();
            item.setCode(airport_obj.getString("code"));
            item.setName(airport_obj.getString("name"));
            item.setCity(airport_obj.getString("city"));
            item.setState(airport_obj.getString("state"));
            item.setCountry(airport_obj.getString("country"));
//            item.setCountry(airport_obj.getString("country"));

            if (!CODE.equalsIgnoreCase(airport_obj.getString("code")))
                airportLists.add(item);

        }

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        airportRecyclerList.setLayoutManager(manager);

        airportAdapter = new AirportAdapter(this, airportLists) {
            @Override
            public void clickAirlines(AirlineCodeItem item) {
                super.clickAirlines(item);
                Utils.hideSoftKeyboard(AirportList.this);
                setResult(item);
            }
        };
        airportRecyclerList.setAdapter(airportAdapter);

    }


    private void setResult(AirlineCodeItem item) {
        Intent intent = new Intent();
        intent.putExtra("CODE", item.getCode());
        intent.putExtra("CITY", item.getCity());
        setResult(RESULT_OK, intent);
        finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        MenuItem myActionMenuItem = menu.findItem(R.id.search);

        final SearchView searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setQueryHint("Search Airport...");
//        myActionMenuItem.expandActionView();
        searchView.setIconifiedByDefault(false);
        searchView.requestFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterairportLists = new ArrayList<>();
                if (!TextUtils.isEmpty(newText)) {
                    Log.e("QUEARY", "= " + searchView.getQuery().toString());
                    for (int i = 0; i < airportLists.size(); i++) {
                        if (airportLists.get(i).getCity().toLowerCase().contains(searchView.getQuery().toString().toLowerCase())
                                || airportLists.get(i).getCode().toLowerCase().contains(searchView.getQuery().toString().toLowerCase())) {
                            filterairportLists.add(airportLists.get(i));
                        }
                    }
                    if (filterairportLists.size() > 0) {
                        airportAdapter.changeList(filterairportLists);
                    } else {
                        airportAdapter.changeList(airportLists);

                    }
                } else if (airportLists.size() > 0) {
                    airportAdapter.changeList(airportLists);
                }
                return true;
            }
        });


        return true;
    }


}
