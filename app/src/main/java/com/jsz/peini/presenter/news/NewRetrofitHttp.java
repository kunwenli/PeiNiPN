package com.jsz.peini.presenter.news;

import com.jsz.peini.presenter.IpConfig;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by th on 2017/1/2.
 */

public class NewRetrofitHttp {
    private static NewService SERVICE;

    public static NewService getNetWork() {
        if (SERVICE == null) {
            OkHttpClient client = new OkHttpClient.Builder().build();
            SERVICE = new Retrofit.Builder()
                    .baseUrl(IpConfig.HttpPeiniIp)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build().create(NewService.class);
        }
        return SERVICE;
    }
}
