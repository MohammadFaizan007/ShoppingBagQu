package com.shoppingbag.retrofit;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.shoppingbag.BuildConfig;
import com.shoppingbag.app.AppConfig;
import com.shoppingbag.utils.LoggerUtil;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static com.shoppingbag.app.AppConfig.authToken;

/**
 * Created by Vivek on 1/8/18.
 */

public class ServiceGenerator {

    private static ServiceGenerator serviceGenerator;

    public static ServiceGenerator getInstance() {
        if (serviceGenerator == null) serviceGenerator = new ServiceGenerator();
        return serviceGenerator;
    }

    private static Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    // TODO BUILDER FILE FOR FILE UPLOAD
    private static Retrofit.Builder builderFile = new Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL_FILE)
            .addConverterFactory(GsonConverterFactory.create(gson));

    private static Retrofit.Builder buildermlm = new Retrofit.Builder().baseUrl(BuildConfig.BASE_URL_MLM)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create());

    private static Retrofit.Builder builderTracking = new Retrofit.Builder().baseUrl(BuildConfig.BASE_URL_LOGIN)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create());


    private static Retrofit retrofit;


    // TODO SERVICE WITH TOKEN
    public static <S> S createServiceWithToken(Class<S> serviceClass) {
        if (!TextUtils.isEmpty(authToken)) {
            String authToken = AppConfig.authToken;
            LoggerUtil.logItem("Token-- " + authToken);
            return createServiceWithToken(serviceClass, authToken);
        }
        return createServiceWithToken(serviceClass, null);
    }

    public static <S> S createServiceWithToken(Class<S> serviceClass, final String authToken) {
        if (!TextUtils.isEmpty(authToken)) {
            AuthenticationInterceptor interceptor = new AuthenticationInterceptor(authToken);
            if (!httpClient.interceptors().contains(interceptor)) {
                httpClient.addInterceptor(interceptor);
                httpClient.readTimeout(60, TimeUnit.SECONDS);
                httpClient.connectTimeout(120, TimeUnit.SECONDS);
                buildermlm.client(httpClient.build());
                retrofit = buildermlm.build();
            }
            return retrofit.create(serviceClass);
        } else {
            buildermlm.client(httpClient.build());
            retrofit = buildermlm.build();
            return retrofit.create(serviceClass);
        }
    }
  public static <S> S createServiceWithTokenTracking(Class<S> serviceClass, final String authToken) {
        if (!TextUtils.isEmpty(authToken)) {
            AuthenticationInterceptor interceptor = new AuthenticationInterceptor(authToken);
            if (!httpClient.interceptors().contains(interceptor)) {
                httpClient.addInterceptor(interceptor);
                httpClient.readTimeout(60, TimeUnit.SECONDS);
                httpClient.connectTimeout(120, TimeUnit.SECONDS);
                builderTracking.client(httpClient.build());
                retrofit = builderTracking.build();
            }
            return retrofit.create(serviceClass);
        } else {
            builderTracking.client(httpClient.build());
            retrofit = builderTracking.build();
            return retrofit.create(serviceClass);
        }
    }

    // TODO SERVICE WITH TOKEN




    // TODO SERVICE FOR FILE UPLOAD THROUGH MULTIPART
    public static <S> S createServiceFile(Class<S> serviceClass) {
        if (!TextUtils.isEmpty(authToken)) {
            AuthenticationInterceptor interceptor = new AuthenticationInterceptor(authToken);
            if (!httpClient.interceptors().contains(interceptor)) {
                httpClient.addInterceptor(interceptor);
            }
        }
        httpClient.readTimeout(60, TimeUnit.SECONDS);
        httpClient.connectTimeout(120, TimeUnit.SECONDS);
        httpClient.addInterceptor(chain -> {
            Request original = chain.request();
            Request.Builder requestBuilder;
            requestBuilder = original.newBuilder()
                    .header("Content-Type", "multipart/form-data")
                    .method(original.method(), original.body());
            Request request = requestBuilder.build();
            return chain.proceed(request);
        });
        OkHttpClient client = httpClient.build();
        Retrofit retrofit = builderFile.client(client).build();
        return retrofit.create(serviceClass);
    }

    // TODO SERVICE WITHOUT TOKEN
    public static <S> S createService(Class<S> serviceClass, String url) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(provideOkHttpClient())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(serviceClass);
    }

    private static OkHttpClient provideOkHttpClient() {
        return new OkHttpClient.Builder().readTimeout(1, TimeUnit.MINUTES).connectTimeout(2, TimeUnit.MINUTES)
                .build();
    }

    static class AuthenticationInterceptor implements Interceptor {
        private String authToken;

        AuthenticationInterceptor(String token) {
            this.authToken = token;
        }

        @NotNull
        @Override
        public Response intercept(@NotNull Interceptor.Chain chain) throws IOException {
            Request original = chain.request();
            Request.Builder builder = original.newBuilder()
                    .header("Authorization", "Bearer " + authToken);

            Request request = builder.build();
            return chain.proceed(request);
        }
    }


    //OneStoreIndia By Meeeeee


}