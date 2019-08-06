package com.casl.dbrestful;

import android.util.Log;
import android.widget.ArrayAdapter;

import com.casl.Utility.JsonHelper;
import com.casl.model.Instrument;
import com.casl.model.Pair;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import cz.msebera.android.httpclient.Header;

/**
 * Created by Staff73 on 6/8/2017.
 */
/*
public class RestClientService {
    public static <T> void restClientAnswerSync(String url, RequestParams req) {
        RestClient.post(url,req,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.d("syncReturn","success");
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("inv",errorResponse.toString());
            }
        });
    }
}*/
