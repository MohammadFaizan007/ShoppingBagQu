package com.shoppingbag.utilities.bookedHistory;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;

import com.shoppingbag.app.PreferencesManager;
import com.shoppingbag.constants.BaseActivity;
import com.shoppingbag.utils.LoggerUtil;
import com.shoppingbag.R;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

public class PrintTicketLay extends BaseActivity {

    @BindView(R.id.webview)
    WebView webview;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;

    public static void writeBytesToFile(File theFile, byte[] bytes) throws IOException {
        BufferedOutputStream bos = null;

        try {
            FileOutputStream fos = new FileOutputStream(theFile);
            bos = new BufferedOutputStream(fos);
            bos.write(bytes);
        } finally {
            if (bos != null) {
                try {
                    //flush and close the BufferedOutputStream
                    bos.flush();
                    bos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.print_lay);
        ButterKnife.bind(this);

        webview.getSettings().setJavaScriptEnabled(true);

        if (getIntent() != null) {
            final String pnr = getIntent().getStringExtra("pnr");
//            String url = "http://travelportal.udlifecare.com/BookingHistory/DownloadTicket/?PNR=" + pnr;
            getTicket(pnr);
//            PRDownloader.download(url, Environment.getExternalStorageDirectory() + "/UDLife", pnr)
//
//                    .build()
//                    .setOnStartOrResumeListener(new OnStartOrResumeListener() {
//                        @Override
//                        public void onStartOrResume() {
//                            progressbar.setVisibility(View.VISIBLE);
//                            Log.e("DOWNLOAD", "= " + "START");
//                        }
//                    })
//                    .setOnPauseListener(new OnPauseListener() {
//                        @Override
//                        public void onPause() {
//                            Log.e("DOWNLOAD", "= " + "PAUSE");
//                        }
//                    })
//                    .setOnCancelListener(new OnCancelListener() {
//                        @Override
//                        public void onCancel() {
//                            progressbar.setVisibility(View.GONE);
//                            Log.e("DOWNLOAD", "= " + "CANCEL");
//                        }
//                    })
//                    .setOnProgressListener(new OnProgressListener() {
//                        @Override
//                        public void onProgress(Progress progress) {
//
//                        }
//                    })
//                    .start(new OnDownloadListener() {
//                        @Override
//                        public void onDownloadComplete() {
//                            progressbar.setVisibility(View.GONE);
//                            String path = Utils.getPath(Environment.getExternalStorageDirectory() + "/UDLife", pnr + ".pdf");
//                            Log.e("PATH", "= " + path);
//                            showPdf(path);
//                        }
//
//                        @Override
//                        public void onError(Error error) {
//                            progressbar.setVisibility(View.GONE);
//                            Log.e("DOWNLOAD", "= " + "ERROR = " + error.isConnectionError());
//                        }
//                    });
        }
    }

    public void showPdf(String path) {
        webview.loadUrl("http://" + path);
        webview.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
                progressbar.setVisibility(View.GONE);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                progressbar.setVisibility(View.GONE);
            }
        });

    }

    private void getTicket(final String pnr) {
        try {
            showLoading();
            JsonObject param = new JsonObject();
            param.addProperty("HermesPNR", pnr);
            param.addProperty("Fk_MemId", PreferencesManager.getInstance(context).getUSERID());
            LoggerUtil.logItem(param);

            Call<JsonObject> busCall = apiServicesTravel.getDownloadTicket(param);
            busCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NotNull Call<JsonObject> call, @NotNull retrofit2.Response<JsonObject> response) {
                    hideLoading();
                    LoggerUtil.logItem(response.body());
                    if (response.body().get("ResponseStatus").getAsString().equalsIgnoreCase("Success")) {
                        showPdf(response.body().get("URL").getAsString());
//                        ConvertWebPageToPDF("http://" + response.body().get("URL").getAsString(), pnr);
                    }

                }

                @Override
                public void onFailure(@NotNull Call<JsonObject> call, @NotNull Throwable t) {
                    hideLoading();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            hideLoading();
        }
    }


//    public void ConvertWebPageToPDF(String value, final String fileName) {
//        // note: be sure to copy the helper function ConvertHTMLStringToPDF() from this webpage
//
//        // a url starting with http or an HTML string
//        String apiKey = "3b4833f5-b689-486e-ade5-2a0962220959";
//        String apiURL = "http://api.html2pdfrocket.com/pdf";
//        HashMap<String, String> params = new HashMap<>();
//        params.put("apiKey", apiKey);
//        params.put("value", value.replace("\\", "/"));
//
//        Log.e("VALUE", "= " + value.replace("\\", "/"));
//
//
//        // Call the API convert to a PDF
//        InputStreamReader request = new InputStreamReader(Request.Method.POST, apiURL, new Response.Listener<byte[]>() {
//            @Override
//            public void onResponse(byte[] response) {
//                try {
//                    if (response != null) {
//
//                        Log.e("RESPONSE", "= " + response.length);
//
//                        File localFolder = new File(Environment.getExternalStorageDirectory(), "UDLIFE");
//                        if (!localFolder.exists()) {
//                            localFolder.mkdirs();
//                        }
//
//                        // Write stream output to local file
//                        File pdfFile = new File(localFolder, "UD_" + fileName + ".pdf");
//                        writeBytesToFile(pdfFile, response);
//
//                        Log.e("FILE", "SAVE INTO SDCARD");
//                    }
//                } catch (Exception ex) {
//
//                    Log.e("ERROR", "= " + ex.getMessage());
//                    Toast.makeText(getBaseContext(), "Error while generating PDF file!!", Toast.LENGTH_SHORT).show();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        }, params);
//
//        RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext(), new HurlStack());
//        mRequestQueue.add(request);
//    }

}
