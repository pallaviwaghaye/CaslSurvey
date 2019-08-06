package com.casl.Utility;

import com.casl.model.Answer;
import com.casl.model.Choice;
import com.casl.model.Home;
import com.casl.model.Instrument;
import com.casl.model.Interview;
import com.casl.model.Participant;
import com.casl.model.Question;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Staff73 on 6/29/2017.
 */

public class JsonHelper {

    public static List<Instrument> getInstrumentArray(JSONArray jsonArray) throws JSONException {
        List<Instrument> instruments = new ArrayList<>();
        if (jsonArray != null) {
            int length = jsonArray.length();
            int order = 0;
            for (int i = 0; i < length; i++) {
                JSONObject record = jsonArray.getJSONObject(i);
                instruments.add(new Instrument(record.getLong("id"), record.getString("name"),record.getString("chineseName"),record.getString("content"),record.getString("chineseContent")));
                order++;
            }
        }
        return instruments;
    }

    public static List<Question> getQuestionArray(JSONArray jsonArray) throws JSONException {
        List<Question> qArray = new ArrayList<>();
        if (jsonArray != null) {
            int length = jsonArray.length();
            for (int i = 0; i < length; i++) {
                JSONObject question = jsonArray.getJSONObject(i);
                JSONArray choices = question.getJSONArray("newChoices");
                //JSONArray subs = record.getJSONArray("subs");
                final Question q = new Question(question.getInt("id"),question.getInt("instrumentid"),question.getInt("parentId"), question.getString("content"),question.getString("chContent"),question.getInt("type"),question.getString("help"),
                        question.getString("chHelp"),question.getInt("hasComment"));
                if (choices!=null && choices.length()>0){
                    for(int j=0 ; j<choices.length();j++){
                        JSONObject choice = choices.getJSONObject(j);
                        q.getMemChoiceList().add(new Choice(choice.getLong("id"),choice.getString("code"),choice.getString("content"),choice.getInt("triggersub"),q.getId(),choice.getString("chContent")));
                    }
                }
                if (q.getType()==0){
                    Choice dk = new Choice();
                    dk.setCodeId("88");
                    dk.setTriggersub(0);
                    dk.setQuestionId(q.getId());
                    dk.setChContent("不知道");
                    dk.setContent("DK");
                    Choice rf = new Choice();
                    rf.setCodeId("99");
                    rf.setTriggersub(0);
                    rf.setQuestionId(q.getId());
                    rf.setContent("RF");
                    rf.setChContent("拒絕");
                    q.getMemChoiceList().add(dk);
                    q.getMemChoiceList().add(rf);
                }
                qArray.add(q);
            }
        }
        return qArray;
    }

    public static List<Participant> getPtArray(JSONArray jsonArray) throws JSONException {
        List<Participant> participants = new ArrayList<>();
        if (jsonArray != null) {
            int length = jsonArray.length();
            DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
            for (int i = 0; i < length; i++) {
                JSONObject record = jsonArray.getJSONObject(i);
                JSONObject homeStr = record.getJSONObject("home");
                Participant pt = new Participant();
                //String street = homeStr.getString("streetNumber")+" "+homeStr.getString("streetDirection")+" "+homeStr.getString("streetName")+" "+homeStr.getString("streetType")+" "+homeStr.getString("apt");
                Home home = new Home();
                home.setId(homeStr.getLong("id"));
                home.setHomePhone(homeStr.getString("homePhone"));
                home.setApt(homeStr.getString("apt"));
                home.setCity(homeStr.getString("city"));
                home.setState(homeStr.getString("state"));
                home.setStreetDirection(homeStr.getString("streetDirection"));
                home.setStreetNumber(homeStr.getString("streetNumber"));
                home.setStreetName(homeStr.getString("streetName"));
                home.setStreetType(homeStr.getString("streetType"));
                home.setZip(homeStr.getString("zip"));
                try {
                    pt.setDob(df.parse(record.getString("dobYear")+"-"+record.getString("dobMonth")+"-"+record.getString("dobDay")));
                    pt.setId(record.getString("id"));
                    pt.setFirstName(record.getString("firstName"));
                    pt.setLangReadCan(record.getString("langReadCan"));
                    pt.setGender(record.getString("gender"));
                    pt.setLangReadPre(record.getString("langReadPre"));
                    pt.setLangWriteCan(record.getString("langWriteCan"));
                    pt.setLangWritePre(record.getString("langWritePre"));
                    pt.setLangSpeakCan(record.getString("langSpeakCan"));
                    pt.setLangSpeakPre(record.getString("langSpeakPre"));
                    pt.setBornCountry(record.getString("bornCountry"));
                    pt.setCellPhone(record.getString("cellPhone"));
                    pt.setWorkPhone(record.getString("workPhone"));
                    pt.setLastName(record.getString("lastName"));
                    pt.setCellPhone(record.getString("cellPhone"));
                    pt.setEmail(record.getString("email"));
                    pt.setRecruitment(record.getString("recruitment"));
                    pt.setGroup(record.getString("socialGroup"));
                    pt.setChineseName(record.getString("chineseName"));
                    pt.setHome(home);
                    int ssnMissing = record.getInt("missingSSNBaseline");
                    if (ssnMissing==0) {
                        pt.setIsMissingSSN(false);
                    }else{
                        pt.setIsMissingSSN(true);
                    }
                    pt.setHomeid(home.getId());
                    participants.add(pt);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        return participants;
    }

    public static List<Interview> getInterviewArray(JSONArray jsonArray) {
        List<Interview> interviewArray = new ArrayList<>();
        if (jsonArray != null) {
            try {
                int length = jsonArray.length();
                for (int i = 0; i < length; i++) {
                    JSONObject record = jsonArray.getJSONObject(i);
                    interviewArray.add(new Interview(record.getInt("id"), record.getString("name")));
                    }
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return interviewArray;
    }

    public static List<Answer> getAnswerArray(JSONArray jsonArray) {
        List<Answer> answerArray = new ArrayList<>();
        if (jsonArray != null) {
            try {
                int length = jsonArray.length();
                for (int i = 0; i < length; i++) {
                    //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
                    JSONObject record = jsonArray.getJSONObject(i);
                    Date parsedDate = new Date(record.getLong("autotime"));
                    answerArray.add(new Answer(null,record.getString("answer"), record.getString("participantId"), record.getInt("interviewId"),
                            record.getInt("instrumentId"), record.getLong("questionId"), record.getString("comment"), record.getString("tranUser"),
                            parsedDate));
                }
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return answerArray;
    }
}
