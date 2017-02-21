package com.jsz.peini.utils;

import com.jsz.peini.presenter.IpConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtil {

    public static Retrofit retrofit;
    private static OkHttpClient okHttpClient = new OkHttpClient();

    static {
        okHttpClient.newBuilder().connectTimeout(3000, TimeUnit.MILLISECONDS);
    }

    public static <T> T createService(Class<T> clazz) {
        if (retrofit == null) {
            synchronized (RetrofitUtil.class) {
                Retrofit.Builder builder = new Retrofit.Builder();
                retrofit = builder
                        .baseUrl(IpConfig.HttpPeiniIp)
                        .client(okHttpClient)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
        }
        return retrofit.create(clazz);
    }
}