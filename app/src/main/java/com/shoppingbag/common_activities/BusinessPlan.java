package com.shoppingbag.common_activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.shoppingbag.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BusinessPlan extends AppCompatActivity {
//    @BindView(R.id.webView)
//    WebView webView;
//    @BindView(R.id.shopping_progress)
//    ProgressBar shoppingProgress;
    String url;
    @BindView(R.id.side_menu)
    ImageView sideMenu;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.bell_icon)
    ImageView bellIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_business_plan);
        setContentView(R.layout.newbusinessplan);
        ButterKnife.bind(this);
        title.setText("Business Plan");
//        url = "http://webapi.dreamydroshky.in/AndroidIcon/index.html";
//        url = "http://dreamydroshky.in/Home-BusinessPlan";

//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
//        webView.getSettings().setDomStorageEnabled(true);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
//        }
//        webView.getSettings().setAllowFileAccessFromFileURLs(true);
//        webView.getSettings().setAllowUniversalAccessFromFileURLs(true);
//        webView.getSettings().setAllowContentAccess(true);
//        webView.getSettings().setAllowFileAccess(true);
//        webView.getSettings().setDatabaseEnabled(true);
//        webView.getSettings().setLoadWithOverviewMode(true);
//        webView.getSettings().setAppCacheEnabled(true);
//        webView.getSettings().setSupportZoom(true);
//        webView.getSettings().setBuiltInZoomControls(true);
//        webView.setWebViewClient(new MyWebViewClient());
//        webView.loadUrl(url);
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

//    private class MyWebViewClient extends WebViewClient {
//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            view.loadUrl(url);
//            return true;
//        }
//
//        @Override
//        public void onPageStarted(WebView view, String url, Bitmap favicon) {
//            super.onPageStarted(view, url, favicon);
//            if (shoppingProgress != null) {
//                shoppingProgress.setVisibility(View.VISIBLE);
//            }
//        }
//
//        @Override
//        public void onPageFinished(WebView view, String url) {
//            super.onPageFinished(view, url);
//            if (shoppingProgress != null) {
//                shoppingProgress.setVisibility(View.GONE);
//            }
//        }
//    }
}

