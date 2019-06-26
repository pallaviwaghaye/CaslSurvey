package com.casl.dbrestful;

import android.content.Context;
import com.loopj.android.http.*;
import cz.msebera.android.httpclient.entity.StringEntity;

public class RestClient{
    //10.0.2.2:8080
    private static String baseURL = "http://chinesehealthyaging.org/chapRest/";
    private static AsyncHttpClient client = new AsyncHttpClient();
    private static SyncHttpClient syncClient = new SyncHttpClient();
    private static String username="staff73";
    private static String password="41roufo";
    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        if (url.equals("GetAllQuestion")){
            client.setTimeout(1000*1000);
        }
        else{
            client.setTimeout(10*1000);
        }
        client.setBasicAuth(username,password);
        client.get(baseURL + url, params, responseHandler);
    }
    public static void post(Context context, String url, StringEntity entity, String contentType, AsyncHttpResponseHandler responseHandler) {
        client.setTimeout(20*1000);
        client.setBasicAuth(username,password);
        client.post(context, baseURL + url,  entity, contentType, responseHandler);
    }
    public static void syncGet(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        syncClient.setBasicAuth(username,password);
        syncClient.get(baseURL + url, params, responseHandler);
    }

    public void setBasicAuth(String username, String password){
        this.username = username;
        this.password = password;
    }
}

