package com.trubuzz.trubuzz.utils;

import android.util.Log;

import com.trubuzz.trubuzz.constant.Conf;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static com.trubuzz.trubuzz.utils.DoIt.sleep;

/**
 * Created by king on 2017/6/21.
 */

public class DoHttp {
    private String TAG = "jcd_" + DoHttp.class.getSimpleName();

    public void doGet(final String url) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder builder = new Request.Builder().url(url);

        builder.method("GET", null);
        builder.header("Cookie", Conf.ad_log_cookie.getKey() + "=" + Conf.ad_log_cookie.getValue());
        Call call = okHttpClient.newCall(builder.build());
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()) {
                    ResponseBody body = response.body();
                    String s = body.string();
                    Log.i(TAG, "onResponse: body = " + s);
                }else{
                    Log.e(TAG, "onResponse: get " + url + " error!");
                }
            }
        });
    }

    public void doPost(final String url,  Kvp<String, String>... params) {

        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody.Builder formBodybBuilder = new FormBody.Builder();
        for (Kvp<String, String> kv : params) {
            formBodybBuilder.add(kv.getKey(), kv.getValue());
        }
        FormBody formBody = formBodybBuilder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()) {
                    Headers headers = response.headers();
                }else{
                    Log.e(TAG, "onResponse: post " + url + " error!");
                }
            }
        });
    }

    public void urlPost(){

    }
}
