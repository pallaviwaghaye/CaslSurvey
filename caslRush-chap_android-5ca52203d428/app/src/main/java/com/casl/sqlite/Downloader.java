package com.casl.sqlite;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;

import com.casl.Utility.JsonHelper;
import com.casl.caslsurvey.InterviewActivity;
import com.casl.caslsurvey.MainActivity;
import com.casl.dbrestful.RestClient;
import com.casl.model.Instrument;
import com.casl.model.Pair;
import com.casl.model.Question;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import cz.msebera.android.httpclient.Header;
import static com.casl.Utility.JsonHelper.getInstrumentArray;

/**
 * Created by Staff73 on 6/28/2017.
 */
/*
public class Downloader{
    Activity activity;
    ProgressDialog progressDialog;
    int interviewId;
    String interviewName;
    SQLiteSurveyHelper db;
    public Downloader(MainActivity activity, int interviewId, String interviewName){
        this.activity = activity;
        this.interviewId = interviewId;
        this.interviewName = interviewName;
        progressDialog = new ProgressDialog(activity);
        db = new SQLiteSurveyHelper(activity);
    }

    public void retrieve(){
        final RequestParams req  = new RequestParams();
        progressDialog.show();
        progressDialog.setMessage("Saving "+interviewName+" Data To Your Device");

        req.put("id",interviewId);
        RestClient.get("GetInstrument",req,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                try {
                    final List<Instrument> pairs = getInstrumentArray(response,interviewId);
                    db.addInstrument(pairs,SQLiteSurveyHelper.TABLE_INSTRUMENT);
                    final RequestParams req2  = new RequestParams();
                    for (Instrument instrument : pairs) {
                        req2.add("list", instrument.ID+"");
                    }
                    RestClient.get("GetAllQuestion",req2, new JsonHttpResponseHandler(){
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                            try {
                                List<Question> qList = JsonHelper.getQuestionArray(response);
                                Log.d("qList",qList.size()+"");
                                db.addQuestion(qList);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            super.onFailure(statusCode, headers, responseString, throwable);
                            Log.d("throwable",throwable.getCause().getMessage());
                        }

                        @Override
                        public void onFinish() {
                            super.onFinish();
                            progressDialog.dismiss();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
*/