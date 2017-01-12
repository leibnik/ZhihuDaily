package com.nyakokishi.zhihu.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

/**
 * Created by Droidroid on 2016/3/22.
 */
public class HttpUtil {
    private static AsyncHttpClient httpClient = new AsyncHttpClient();

    public static void get(String url, AsyncHttpResponseHandler responseHandler) {
        httpClient.get(url, responseHandler);
    }

    public static void get(String url, TextHttpResponseHandler responseHandler) {
        httpClient.get(url, responseHandler);
    }

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        httpClient.get(url, params, responseHandler);
    }

    public static void get(String url, RequestParams params, TextHttpResponseHandler responseHandler) {
        httpClient.get(url, params, responseHandler);
    }

    public static void post(String url, AsyncHttpResponseHandler responseHandler) {
        httpClient.post(url, responseHandler);
    }

    public static void post(String url, TextHttpResponseHandler responseHandler) {
        httpClient.post(url, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        httpClient.post(url, params, responseHandler);
    }

    public static void post(String url, RequestParams params, TextHttpResponseHandler responseHandler) {
        httpClient.post(url, params, responseHandler);
    }

    /**
     * 检测当的网络（WLAN、3G/2G）状态
     *
     * @param context Context
     * @return true 表示网络可用
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                // 当前网络是连接的
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    // 当前所连接的网络可用
                    return true;
                }
            }
        }
        return false;
    }
}
