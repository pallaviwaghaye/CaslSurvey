package com.casl.Utility;

import android.content.Context;
import android.content.SharedPreferences;

import com.casl.model.Contact;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


public class SharedPreferenceManager {
    private static Context applicationContext;
    private static SharedPreferences caslSurveyPreferences;

    public static void setApplicationContext(Context context) {
        applicationContext = context;
        setPreferences();
    }

    private static void setPreferences() {
        if (caslSurveyPreferences == null) {
            caslSurveyPreferences = applicationContext.getSharedPreferences("caslSurvey",
                    Context.MODE_PRIVATE);
        }
    }

    public static void clearPreferences() {
        caslSurveyPreferences.edit().clear().commit();
    }

    public static void storeContactObject(Contact contact) {
        SharedPreferences.Editor prefsEditor = caslSurveyPreferences.edit();
        //  prefsEditor.clear();
        Gson gson = new Gson();
        String json = gson.toJson(contact);
        prefsEditor.putString("ContactRespObj", json);
        prefsEditor.commit();
    }

    public static Contact getContactObject() {
        Gson gson1 = new Gson();
        String json1 = caslSurveyPreferences.getString("ContactRespObj", "");
        Contact obj = gson1.fromJson(json1, Contact.class);
//		Log.e("RetrivedName:", obj.getFirstName());
        return obj;
    }


    /*public static void saveMap(HashMap<String, Contact> map) {
        SharedPreferences.Editor prefsEditor = caslSurveyPreferences.edit();
        JSONObject jsonObject = new JSONObject(map);
        String jsonString = jsonObject.toString();
        prefsEditor.remove("My_map").commit();
        prefsEditor.putString("My_map", jsonString);
        prefsEditor.commit();
    }

    public static HashMap<String, Contact> getMap() {
        HashMap<String, Contact> outputMap = new HashMap<String, Contact>();
        try {
            String jsonString = caslSurveyPreferences.getString("My_map", (new JSONObject()).toString());
            JSONObject jsonObject = new JSONObject(jsonString);
            Iterator<String> keysItr = jsonObject.keys();
            while (keysItr.hasNext()) {
                String key = keysItr.next();
                Contact value = (Contact) jsonObject.get(key);
                outputMap.put(key, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outputMap;
    }*/

    public static void saveContactByPIdAndIId(Contact contact) {
        SharedPreferences.Editor prefsEditor = caslSurveyPreferences.edit();
        //  prefsEditor.clear();
        Gson gson = new Gson();
        String json = gson.toJson(contact);
        prefsEditor.putString(contact.getInterviewId() + contact.getParticipantId(), json);
        prefsEditor.commit();
    }

    public static Contact getContactByPIdAndIId(int interviewId, String pId) {
        Gson gson1 = new Gson();
        String json1 = caslSurveyPreferences.getString(interviewId + pId, "");
        Contact obj = gson1.fromJson(json1, Contact.class);
//		Log.e("RetrivedName:", obj.getFirstName());
        return obj;
    }


}
