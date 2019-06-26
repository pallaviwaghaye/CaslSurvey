package com.casl.caslsurvey;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.casl.model.AnswerDao;
import com.casl.model.DaoSession;
import com.casl.model.Instrument;
import com.casl.model.InstrumentDao;
import com.casl.model.InstrumentInstance;
import com.casl.model.InstrumentInstanceDao;
import com.casl.model.Interview;
import com.casl.model.InterviewInstance;
import com.casl.model.InterviewInstanceDao;
import com.casl.model.InterviewStatus;
import com.casl.model.Participant;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ThankYouActivity extends MainActivity {

    //constanct field
    public static final String INS_ID = "ins_id";
    public static final String INS_LIST = "ins_list";
    private static String serialNum = null;
    //UI components
    private ListView instrumentListView;
    private ArrayAdapter<Instrument> adapter;
    //private ProgressDialog progressDialog;
    //Activity data
    private int interviewId;
    private String istr;
    //private String percentage;
    private Participant pt;
    private boolean enableDone =false;
    private Float percentage;
    //SQlite database
    //private ParticipantDao participantDao;
    //private HomeDao homeDao;
    private InterviewInstanceDao interviewInstanceDao;

    private InstrumentDao instrumentDao;
    private AnswerDao answerDao;
    private InstrumentInstanceDao instrumentInstanceDao;
    private InterviewStatus interviewStatus = InterviewStatus.PAUSED;

    private String interview = "";

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
        interviewId = intent.getIntExtra(ScreenSlideActivity.IID,0);
        //interview=((Interview)parent.getAdapter().getItem(position)).getName();
        pt = intent.getParcelableExtra(ScreenSlideActivity.PID);
        istr = intent.getStringExtra(ScreenSlideActivity.InterviewId);
        //percentage = intent.getFloatExtra(ScreenSlideActivity.PER, (float) 0.0);
        //percentage = intent.getStringExtra("PER");


        LayoutInflater inflater = (LayoutInflater)this.getSystemService(LAYOUT_INFLATER_SERVICE);
        //ConstraintLayout cl = (ConstraintLayout)findViewById(R.id.contraint_content);
        View childLayout = inflater.inflate(R.layout.activity_thank_you,null);
        //cl.addView(childLayout);
        zoomView.addView(childLayout);
        //setContentView(R.layout.activity_thank_you);

        adapter = new ArrayAdapter<Instrument>(this, R.layout.instrument_item, new ArrayList<Instrument>()){
            @Override
            public void notifyDataSetChanged() {
                super.notifyDataSetChanged();

                //percentage = getIntent().getFloatExtra(ScreenSlideActivity.PER, (float) 0.0);
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
                            //instrumentListView.setItemChecked(i, true);
                            done++;
                        }
                       /* if (instrumentInstances.get(0).isFinished()==0) {
                            instrumentListView.setItemChecked(i, false);
                        }*/
                    }
                }
                percentage = 0f;
                if (total!=0) {
                    percentage = (float) done / total;
                }
            }
        };

        getInstrumentFromLocal();

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
