package com.shoppingbag.common_activities;


import android.app.Application;

import com.shoppingbag.R;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class AppControllerApp extends Application {

    public static final String TAG = AppControllerApp.class
            .getSimpleName();
    private static AppControllerApp mInstance;

    public static synchronized AppControllerApp getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .disableCustomViewInflation()
                .build());
    }


}