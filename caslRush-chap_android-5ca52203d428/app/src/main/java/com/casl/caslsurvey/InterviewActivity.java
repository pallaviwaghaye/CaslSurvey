package com.casl.caslsurvey;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.casl.Utility.JsonHelper;
import com.casl.dbrestful.RestClient;
import com.casl.model.Answer;
import com.casl.model.AnswerDao;
import com.casl.model.DaoSession;
import com.casl.model.HomeDao;
import com.casl.model.Interview;
import com.casl.model.Participant;
import com.casl.model.ParticipantDao;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class InterviewActivity extends MainActivity {
    public static final String PID = "pid";
    public static final String IID = "iid";
    public static final String ISTR = "id";
    public static final String PSTR = "pstr";
    private Spinner interviewsDropdown;
    private ListView participantListView;
    private ArrayAdapter<Participant> pAdapter;
    private ArrayAdapter<Interview> iAdapter;
    private String interview = "";
    private int interviewId;
    private ParticipantDao participantDao;
    private HomeDao homeDao;
    private AnswerDao answerDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaoSession daoSession = ((App)getApplication()).getDaoSession();
        participantDao = daoSession.getParticipantDao();
        homeDao = daoSession.getHomeDao();
        answerDao = daoSession.getAnswerDao();
        LayoutInflater inflater = (LayoutInflater)this.getSystemService(LAYOUT_INFLATER_SERVICE);
        ConstraintLayout cl = (ConstraintLayout)findViewById(R.id.contraint_content);
        View childLayout = inflater.inflate(R.layout.activity_interview,null);
        cl.addView(childLayout);
        participantListView = (ListView) findViewById(R.id.my_list_view);
        pAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, new ArrayList<Participant>());
        participantListView.setAdapter(pAdapter);
        participantListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), InstrumentActivity.class);
                Participant pt = (Participant) parent.getAdapter().getItem(position);
                participantDao.insertOrReplace(pt);
                homeDao.insertOrReplace(pt.getMemHome());
                final RequestParams req3  = new RequestParams();
                req3.put("iid",interviewId);
                req3.put("pid",pt.getId());
                RestClient.get("GetAnswer",req3,new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        List<Answer> aList = JsonHelper.getAnswerArray(response);
                        Log.d("answerList",aList.size()+"");
                        if (aList!=null && aList.size()>0){
                            InsertAnswersTask adder = new InsertAnswersTask(aList);
                            adder.execute();
                            /*if(aList.get(0).getAnswer().equalsIgnoreCase("88") || aList.get(0).getAnswer().equalsIgnoreCase("99"))
                            {
                                Toast.makeText(InterviewActivity.this, "Dk", Toast.LENGTH_SHORT).show();
                            }*/
                        }
                    }
                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject object) {
                        super.onFailure(statusCode, headers, throwable, object);
                        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),throwable.getMessage(),Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);
                        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),throwable.getMessage(),Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),throwable.getMessage(),Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                    }
                });
                intent.putExtra(PID,pt);
                intent.putExtra(ISTR,interview);
                intent.putExtra(IID,interviewId);
                startActivity(intent);
                finish();
            }
        });
        addInterviews();
        setTitle("Select Interview and Participant");
    }

    public void addInterviews(){
        interviewsDropdown = (Spinner) findViewById(R.id.interview);
        iAdapter = new ArrayAdapter<>(getBaseContext(),android.R.layout.simple_spinner_item,new ArrayList<Interview>());
        iAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        iAdapter.add(new Interview(-1,"Select Interview"));
        iAdapter.add(new Interview(10001,"PINE 1.5"));
        iAdapter.add(new Interview(10002,"PINE 5.5"));
        iAdapter.add(new Interview(10003,"PINE Adult Children Study 4.4"));
        iAdapter.add(new Interview(10004,"PINE Adult Children Study 1.4"));
        iAdapter.add(new Interview(10005,"PINE 2.5"));
        iAdapter.add(new Interview(10006,"PINE 3.5"));
        iAdapter.add(new Interview(10007,"PINE 4.5"));
        iAdapter.add(new Interview(10008,"PINE Adult Children Study 2.4"));
        iAdapter.add(new Interview(10009,"PINE Adult Children Study 3.4"));
        iAdapter.add(new Interview(946,"The Self-neglect Survey-Senior "));
        iAdapter.add(new Interview(947,"The Self-neglect Survey-Family Member"));
        iAdapter.add(new Interview(9999,"TEST 1.1"));
        iAdapter.add(new Interview(10012,"TEST 2.2 "));
       // iAdapter.add(new Interview(948,"Nanjing Pine 1.1"));
        //iAdapter.add(new Interview(9999,"Test 1.1"));
        //restClientCall("GetActiveInterview",null,iAdapter,new Boolean(false));

        RestClient.get("GetActiveInterview",null,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                List<Interview> interviews = JsonHelper.getInterviewArray(response);
                /*iAdapter.addAll(interviews);
                if(interviews != null)
                {
                    //iAdapter.remove(interviews.remove(9999));
                    iAdapter.remove(interviews.get(69));
                    iAdapter.remove(interviews.get(70));
                    iAdapter.remove(interviews.get(71));
                    iAdapter.remove(interviews.get(72));
                    iAdapter.remove(interviews.get(73));
                    iAdapter.remove(interviews.get(74));
                    iAdapter.remove(interviews.get(75));
                    iAdapter.remove(interviews.get(76));
                    iAdapter.remove(interviews.get(77));
                    iAdapter.remove(interviews.get(78));
                    iAdapter.remove(interviews.get(79));

                }*/

                iAdapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                //Log.d("inv",errorResponse.toString());
            }
        });

        interviewsDropdown.setAdapter(iAdapter);

        interviewsDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position>0){
                    interview=((Interview)parent.getAdapter().getItem(position)).getName();
                    interviewId = ((Interview)parent.getAdapter().getItem(position)).getId();
                    getParticipant(interviewId);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void getParticipant(long interviewId){
        pAdapter.clear();
        RequestParams req = new RequestParams();
        req.put("staffId","\""+staffId+"\"");
        req.put("iid",interviewId);
        RestClient.get("GetAssignedParticipant",req,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                try {
                    List<Participant> participants = JsonHelper.getPtArray(response);
                    pAdapter.addAll(participants);
                    pAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private class InsertAnswersTask extends AsyncTask{
        List<Answer> qList;

        public InsertAnswersTask(List<Answer> qList) {
            this.qList = qList;
        }

        @Override
        protected Object doInBackground(Object[] params) {
            //db.addQuestion(qList);
            answerDao.insertOrReplaceInTx(qList);
            return qList;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            //adapter.notifyDataSetChanged();
        }
    }

}
