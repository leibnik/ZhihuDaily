package com.nyakokishi.data;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by nyakokishi on 2017/1/13.
 */
public class HttpService {

    private Retrofit builder;
    public static final String HOST = "http://news-at.zhihu.com/api/4/";

    public ZhihuApi api;

    private HttpService() {
        builder = new Retrofit.Builder()
                .baseUrl(HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        api = builder.create(ZhihuApi.class);
    }

    private static class SingletonHolder {
        private final static HttpService INSTANCE = new HttpService();
    }

    public static HttpService getInstance() {
        return SingletonHolder.INSTANCE;
    }


}
