package com.chenqihong.genjiwangcrawer;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class Crawer {
    private static OkHttpClient mOkHttpClient;
    public interface OnCrawerReadyListener{
        void onReady(String raw);
    }
    public static void syncGet(String url, final OnCrawerReadyListener listener) {
        mOkHttpClient = new OkHttpClient();
        Request.Builder requestBuilder = new Request.Builder().url(url);
        requestBuilder.method("GET", null);
        final Request request = requestBuilder.build();
        Call call = mOkHttpClient.newCall(request);
        try {
            listener.onReady(call.execute().body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("error", "error");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                listener.onReady(response.body().string());
            }
        });*/
    }

    public static String syncGet(String url) {
        mOkHttpClient = new OkHttpClient();
        Request.Builder requestBuilder = new Request.Builder().url(url);
        requestBuilder.method("GET", null);
        final Request request = requestBuilder.build();
        Call call = mOkHttpClient.newCall(request);
        try {
            return call.execute().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
        /*call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("error", "error");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                listener.onReady(response.body().string());
            }
        });*/
    }
}
