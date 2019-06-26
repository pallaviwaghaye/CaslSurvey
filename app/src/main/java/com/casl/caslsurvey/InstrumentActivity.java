package com.casl.caslsurvey;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.TextView;

import com.casl.Utility.JsonHelper;
import com.casl.dbrestful.RestClient;
import com.casl.model.Answer;
import com.casl.model.AnswerDao;
import com.casl.model.Choice;
import com.casl.model.ChoiceDao;
import com.casl.model.DaoSession;
import com.casl.model.Form;
import com.casl.model.FormDao;
import com.casl.model.HomeDao;
import com.casl.model.Instrument;
import com.casl.model.InstrumentDao;
import com.casl.model.InstrumentInstance;
import com.casl.model.InstrumentInstanceDao;
import com.casl.model.InterviewInstance;
import com.casl.model.InterviewInstanceDao;
import com.casl.model.InterviewStatus;
import com.casl.model.Participant;
import com.casl.model.ParticipantDao;
import com.casl.model.Question;
import com.casl.model.QuestionDao;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.greenrobot.greendao.query.QueryBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.Header;

import static com.casl.Utility.JsonHelper.getInstrumentArray;
//import static com.casl.dbrestful.RestClientService.restClientCall;

public class InstrumentActivity extends MainActivity {
    //constanct field
    public static final String INS_ID = "ins_id";
    public static final String INS_LIST = "ins_list";
    private static final String PER = "";
    private static final String Interview = "interview";
    private static String serialNum = null;
    //UI components
    private ListView instrumentListView;
    private ArrayAdapter<Instrument> adapter;
    //private ProgressDialog progressDialog;
    //Activity data
    private int interviewId;
    private String istr;
    private Participant pt;
    private boolean enableDone =false;
    private float percentage;
    //SQlite database
    //private ParticipantDao participantDao;
    //private HomeDao homeDao;
    private InterviewInstanceDao interviewInstanceDao;

    private InstrumentDao instrumentDao;
    private AnswerDao answerDao;
    private InstrumentInstanceDao instrumentInstanceDao;
    private InterviewStatus interviewStatus = InterviewStatus.PAUSED;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class, String.class );
            serialNum = (String)(   get.invoke(c, "ril.serialnumber", "unknown" )  );
            //Log.d("sn",serialNum);
        }
        catch (Exception ignored)
        {
        }

        //Initialize database
        //initialize data
        DaoSession daoSession = ((App)getApplication()).getDaoSession();
        //participantDao = daoSession.getParticipantDao();
        //homeDao = daoSession.getHomeDao();
        interviewInstanceDao = daoSession.getInterviewInstanceDao();
        //formDao = daoSession.getFormDao();
        instrumentDao = daoSession.getInstrumentDao();
        //questionDao = daoSession.getQuestionDao();
        //choiceDao = ((App)getApplication()).getDaoSession().getChoiceDao();
        answerDao = daoSession.getAnswerDao();
        instrumentInstanceDao = ((App)getApplication()).getDaoSession().getInstrumentInstanceDao();
        Intent intent = getIntent();
        interviewId = intent.getIntExtra(InterviewActivity.IID,0);
        pt = intent.getParcelableExtra(InterviewActivity.PID);
        istr = intent.getStringExtra(InterviewActivity.ISTR);
        //Initialize UI
        LayoutInflater inflater = (LayoutInflater)this.getSystemService(LAYOUT_INFLATER_SERVICE);
        //ConstraintLayout cl = (ConstraintLayout)findViewById(R.id.contraint_content);
        View childLayout = inflater.inflate(R.layout.activity_instrument,null);
        //cl.addView(childLayout);
        zoomView.addView(childLayout);
        instrumentListView = (ListView)findViewById(R.id.my_list_view2);
        TextView tv = (TextView)findViewById(R.id.participant);
        //progressDialog = new ProgressDialog(this);
        //listview adapter
        adapter = new ArrayAdapter<Instrument>(this, R.layout.instrument_item, new ArrayList<Instrument>()){
            @Override
            public void notifyDataSetChanged() {
                super.notifyDataSetChanged();
                int total = adapter.getCount();
                int done=0;
                //instrumentListView.setItemChecked(0, true);
                //Log.d("ptInst",pt.getName()+pt.getId());
                for(int i=0;i<total;i++){
                    Instrument instrument = adapter.getItem(i);
                    List<InstrumentInstance> instrumentInstances = instrumentInstanceDao.queryBuilder().where(
                            InstrumentInstanceDao.Properties.ParticipantId.eq(pt.getId()),
                            InstrumentInstanceDao.Properties.InterviewId.eq(interviewId),
                            InstrumentInstanceDao.Properties.InstrumentId.eq(instrument.getId())).list();
                    if(instrumentInstances!=null&&instrumentInstances.size()>0) {
                        if (instrumentInstances.get(0).isFinished()==1) {
                            instrumentListView.setItemChecked(i, true);
                            done++;
                        }
                        if (instrumentInstances.get(0).isFinished()==0) {
                            instrumentListView.setItemChecked(i, false);
                        }
                    }
                }
                percentage = 0f;
                if (total!=0) {
                    percentage = (float) done / total;
                }
            }
            /*
            @Override
            public boolean isEnabled(int position) {
                View child = instrumentListView.getChildAt(position);
                if (!instrumentListView.isItemChecked(0)&&position!=0) {
                    if (child!=null){
                        child.setAlpha(0.5f);
                    }
                    return false;
                }
                if (child!=null){
                    child.setAlpha(1f);
                }
                return super.isEnabled(position);
            }*/
        };
        instrumentListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        instrumentListView.setAdapter(adapter);
        //retrieve instruments
        getInstrumentFromLocal();
        //getInstrumentFromServer();
        tv.setText(Html.fromHtml("Click Here To Verify <u>"+ pt.toString()+"</u>"));
        //set onclick to open instrument
        instrumentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ScreenSlideActivity.class);
                Instrument instrument = (Instrument)parent.getAdapter().getItem(position);
                long ins_id = instrument.getId();
                String ins_name = instrument.toString();
                instrumentListView.setItemChecked(position, false);
                //Log.d("ins_id",ins_id+"");
                Bundle b = new Bundle();
                intent.putExtra(InterviewActivity.PID,pt);
                intent.putExtra(INS_ID,ins_id);
                intent.putExtra(InterviewActivity.IID,interviewId);
                intent.putExtra(INS_LIST,listToIntArray(adapter));
                intent.putExtra(InterviewActivity.ISTR,ins_name);
                intent.putExtra("Interview",istr);
                intent.putExtra("percentage",percentage);
                intent.putExtra("position",position);
                startActivity(intent);
            }
        });
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ScreenSlideActivity.class);
                intent.putExtra(InterviewActivity.PID,pt);
                intent.putExtra(InterviewActivity.IID,interviewId);
                intent.putExtra("Interview",istr);
                intent.putExtra("position",-1);
                startActivity(intent);
            }
        });
        setTitle(istr);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_pause){
            finish();
            quitInterview(interviewStatus);
            Intent intent = new Intent(getApplicationContext(), SummaryActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void quitInterview(InterviewStatus status) {
        InterviewInstance instance = new InterviewInstance(null,interviewId,serialNum,status,istr,pt,staffId,percentage);
        interviewInstanceDao.insertOrReplace(instance);
        //participantDao.insertOrReplace(pt);
        //homeDao.insertOrReplace(pt.getMemHome());
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        quitInterview(interviewStatus);
        super.onDestroy();
    }

    //For viewpager retriving instrument by id
    private static long[] listToIntArray(ArrayAdapter<Instrument> adapter){
        long[] array = new long[adapter.getCount()];
        for (int i=0;i<adapter.getCount();i++){
            array[i] = adapter.getItem(i).getId();
        }
        return array;
    }

    public void getInstrumentFromServer(){
        RequestParams req = new RequestParams();
        req.put("id",new Integer(interviewId));
        //restClientCall("GetInstrument",req,adapter,interviewId);
    }

    public void getInstrumentFromLocal(){
        List<Instrument> instruments = getInstruments(this.interviewId);
        //db.getAllInstruments(this.interviewId);
        //Download if instruments is null
        if (instruments==null || instruments.size()==0) {
            adapter.clear();
            downloadAllData(this.interviewId,istr,adapter);
        }
        else{
            //instruments.add(0,new Instrument(-1L,"Verfiy Participant Info","請確認參與者信息",null,null));
            adapter.addAll(instruments);
            adapter.notifyDataSetChanged();
        }
    }


}
