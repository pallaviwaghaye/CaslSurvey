package com.casl.caslsurvey;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.casl.Utility.JsonHelper;
import com.casl.dbrestful.RestClient;
import com.casl.model.Answer;
import com.casl.model.AnswerDao;
import com.casl.model.Choice;
import com.casl.model.ChoiceDao;
import com.casl.model.DaoSession;
import com.casl.model.Form;
import com.casl.model.FormDao;
import com.casl.model.Instrument;
import com.casl.model.InstrumentDao;
import com.casl.model.InstrumentInstance;
import com.casl.model.InstrumentInstanceDao;
import com.casl.model.InterviewInstance;
import com.casl.model.InterviewInstanceDao;
import com.casl.model.Participant;
import com.casl.model.Question;
import com.casl.model.QuestionDao;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.greenrobot.greendao.query.QueryBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

import static com.casl.Utility.JsonHelper.getInstrumentArray;
import static java.lang.Integer.parseInt;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link QuestionListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link QuestionListFragment#create} factory method to
 * create an instance of this fragment.
 */
public class QuestionListFragment extends Fragment{
    private static final String ARG_ISTR = "instrumentName";
    private static final String ARG_DESC = "description";
    private static final String ARG_UID = "staffId";
    private List<Question> qList;
    private static final String ARG_INST = "instrumentId";
    private static final String ARG_PID = "pId";
    private static final String ARG_IID = "iId";
    private int instrumentId = -1;
    private int iid=-1;
    private String title="";
    private Participant pt;
    private String staffId="";
    //question views
    private LinearLayout sv;
    private QuestionRecyAdapter questionAdapter;
    private OnFragmentInteractionListener mListener;
    private String description;
    private ViewGroup rootView;
    private RecyclerView qListView;
    private AnswerDao answerDao;
    private QuestionDao questionDao;
    private InstrumentInstanceDao instrumentInstanceDao;

    private InstrumentDao instrumentDao;
    private FormDao formDao;
    private InterviewInstanceDao interviewInstanceDao;
    private ChoiceDao choiceDao;

    private int instrumentAnswered = 1;
    public QuestionListFragment() {
        // Required empty public constructor
    }


    public static QuestionListFragment create(int instrumentId, Participant pt, int iid, String title, String description, String staffId) {
        QuestionListFragment fragment = new QuestionListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_INST, instrumentId);
        args.putParcelable(ARG_PID, pt);
        args.putInt(ARG_IID, iid);
        args.putString(ARG_ISTR, title);
        args.putString(ARG_DESC, description);
        args.putString(ARG_UID, staffId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaoSession daoSession = ((App)getActivity().getApplication()).getDaoSession();
        answerDao = daoSession.getAnswerDao();
        questionDao = daoSession.getQuestionDao();
        instrumentInstanceDao = daoSession.getInstrumentInstanceDao();

        instrumentDao = daoSession.getInstrumentDao();
        formDao = daoSession.getFormDao();
        interviewInstanceDao = daoSession.getInterviewInstanceDao();
        choiceDao = ((App)getActivity().getApplication()).getDaoSession().getChoiceDao();

        if (getArguments() != null) {
            instrumentId = getArguments().getInt(ARG_INST);
            pt = getArguments().getParcelable(ARG_PID);
            if (pt==null){
                pt = new Participant();
            }
            iid = getArguments().getInt(ARG_IID);
            title = getArguments().getString(ARG_ISTR);
            description = getArguments().getString(ARG_DESC);
            staffId = getArguments().getString(ARG_UID);
            //Log.d("titleDebug",title);
        }
        qList = questionDao.queryBuilder().where(QuestionDao.Properties.IstrId.eq(instrumentId)).list();
        for (Question question : qList){
            if (question.getType()!=1){
                List<Answer> answers = getAnswerByQuestion(question, pt, iid);
                String answerStr="";
                if (answers!=null && answers.size()>0) {
                    try {
                        answerStr = answers.get(0).getAnswer();//db.getAnswerId(pt.getId(),iid,q.ID);
                        question.setAnswer(answerStr);
                        if (question.getHasComment()==1){
                            question.setComment(answers.get(0).getComment());
                        }
                    }catch (NumberFormatException e){

                    }
                }
            }
            else if (question.getType()==1){
                List<Answer> answers = getAnswerByQuestion(question, pt, iid);
                String answerStr="";
                if (answers!=null && answers.size()>0) {
                    try {
                        answerStr = answers.get(0).getAnswer();//db.getAnswerId(pt.getId(),iid,q.ID);
                        question.setAnswer(answerStr);
                        List<String> answerList = new ArrayList<String>(Arrays.asList(answerStr.split(",")));
                        if (question.getHasComment()==1){
                            question.setComment(answers.get(0).getComment());
                        }
                        question.setCheckedMultiple(new HashSet(answerList));
                    }catch (NumberFormatException e){

                    }
                }
            }
            /*else if(question.getType()==0)
            {
                *//*downloadAllData(10001,"PINE 1.5",null);
                downloadAllData(10002,"PINE 5.5",null);
                downloadAllData(10003,"PINE Adult Children Study 4.4",null);
                downloadAllData(10004,"PINE Adult Children Study 1.4",null);
                downloadAllData(10005,"PINE 2.5",null);
                downloadAllData(10006,"PINE 3.5",null);
                downloadAllData(10007,"PINE 4.5",null);
                downloadAllData(10008,"PINE Adult Children Study 2.4",null);
                downloadAllData(10009,"PINE Adult Children Study 3.4",null);
                downloadAllData(946,"The Self-neglect Survey-Senior ",null);
                downloadAllData(947,"The Self-neglect Survey-Family Member",null);
                downloadAllData(9999,"TEST 1.1",null);*//*
                downloadAllData(10012,"TEST 2.2 ",null);
            }*/
        }



        questionAdapter = new QuestionRecyAdapter(qList,answerDao,pt,iid);
        //Log.d("answer", qList.get(0).getAnswer()==null?"null":qList.get(0).getAnswer());
        questionAdapter.setHasStableIds(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = (ViewGroup) inflater
                .inflate(R.layout.activity_question_list, container, false);
        TextView tv = (TextView) rootView.findViewById(R.id.inst_textView);
        TextView tv1 = (TextView) rootView.findViewById(R.id.instDesp_textView);
        tv.setText(title);
        tv1.setText(Html.fromHtml(description));
        //sv = (LinearLayout) rootView.findViewById(R.id.my_question_scroll);
        //addView(sv, inflater);
        qListView = (RecyclerView)rootView.findViewById(R.id.q_recycler_view);
        //qListView.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
        qListView.setAdapter(questionAdapter);
        qListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //Log.d("answer1", qList.get(0).getAnswer()==null?"null":qList.get(0).getAnswer());

        /*downloadAllData(10001,"PINE 1.5",null);
        downloadAllData(10002,"PINE 5.5",null);
        downloadAllData(10003,"PINE Adult Children Study 4.4",null);
        downloadAllData(10004,"PINE Adult Children Study 1.4",null);
        downloadAllData(10005,"PINE 2.5",null);
        downloadAllData(10006,"PINE 3.5",null);
        downloadAllData(10007,"PINE 4.5",null);
        downloadAllData(10008,"PINE Adult Children Study 2.4",null);
        downloadAllData(10009,"PINE Adult Children Study 3.4",null);
        downloadAllData(946,"The Self-neglect Survey-Senior ",null);
        downloadAllData(947,"The Self-neglect Survey-Family Member",null);
        downloadAllData(9999,"TEST 1.1",null);*/
        //downloadAllData(10012,"TEST 2.2 ",null);

        questionAdapter.notifyDataSetChanged();
        //((ScreenSlideActivity)getActivity()).getProgressDialog().dismiss();

       /* try {
            getActiveInterview();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        /*try {
            List<Question> qList = JsonHelper.getQuestionArray(response);*/

       /* } catch (JSONException e) {
            e.printStackTrace();
            //progressDialog.dismiss();
        }
*/
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        questionAdapter.notifyDataSetChanged();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (getUserVisibleHint()) {
            saveQuestion();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (title!=null && title.length()>0 &&!isVisibleToUser) {
            saveQuestion();
        }
    }

    private void saveQuestion() {
        if (qList!=null) {
            List<Answer> singleAnswer = new ArrayList<>();
            for (int i=0;i<qList.size();i++) {
                Question question = qList.get(i);
                if (question.isAnswered()) {
                    Date date = new Date();
                    List<Answer> answers = answerDao.queryBuilder().where(
                            AnswerDao.Properties.ParticipantId.eq(pt.getId()),
                            AnswerDao.Properties.InterviewId.eq(iid),
                            AnswerDao.Properties.InstrumentId.eq(instrumentId),
                            AnswerDao.Properties.QuestionId.eq(question.getId())).list();
                    Answer answer = null;
                    if (answers.size()>0){
                        answer = answers.get(0);
                    }
                    switch (question.getType()){
                        case 0:
                            if (answer==null) {
                                answer = new Answer(null, question.getAnswer(), pt.getId(), iid, instrumentId, question.getId(), question.getComment(), staffId, date);
                            }else{
                                answer.setAnswer(question.getAnswer());
                                answer.setComment(question.getComment());
                            }
                            answerDao.insertOrReplace(answer);
                            singleAnswer.add(answer);


                            if(question.getAnswer().equalsIgnoreCase("88") || question.getAnswer().equalsIgnoreCase("99"))
                            {
                                downloadAllData(10001,"PINE 1.5",null);
                                downloadAllData(10002,"PINE 5.5",null);
                                downloadAllData(10003,"PINE Adult Children Study 4.4",null);
                                downloadAllData(10004,"PINE Adult Children Study 1.4",null);
                                downloadAllData(10005,"PINE 2.5",null);
                                downloadAllData(10006,"PINE 3.5",null);
                                downloadAllData(10007,"PINE 4.5",null);
                                downloadAllData(10008,"PINE Adult Children Study 2.4",null);
                                downloadAllData(10009,"PINE Adult Children Study 3.4",null);
                                downloadAllData(946,"The Self-neglect Survey-Senior ",null);
                                downloadAllData(947,"The Self-neglect Survey-Family Member",null);
                                downloadAllData(9999,"TEST 1.1",null);
                                downloadAllData(10012,"TEST 2.2 ",null);

                            }


                            //Toast.makeText(getActivity(),question.getAnswer(),Toast.LENGTH_LONG).show();

                            break;
                        case 1:
                            Set<String> answerSet = question.getCheckedMultiple();
                            String answerStr = android.text.TextUtils.join(",",answerSet);
                            if (answer==null) {
                                answer = new Answer(null,answerStr,pt.getId(),iid,instrumentId,question.getId(), question.getComment(),staffId,date);
                            }else{
                                answer.setAnswer(answerStr);
                                answer.setComment(question.getComment());
                            }
                            answerDao.insertOrReplace(answer);
                            singleAnswer.add(answer);
                            break;
                        case 2:
                            if (answer==null){
                                answer = new Answer(null,question.getAnswer(),pt.getId(),iid,instrumentId,question.getId(), question.getComment(),staffId,date);
                            }else{
                                answer.setAnswer(question.getAnswer());
                                answer.setComment(question.getComment());
                            }
                            answerDao.insertOrReplace(answer);
                            singleAnswer.add(answer);
                            break;
                        default:
                            if (answer==null){
                                answer = new Answer(null,question.getAnswer(),pt.getId(),iid,instrumentId,question.getId(), question.getComment(),staffId,date);
                            }else{
                                answer.setAnswer(question.getAnswer());
                                answer.setComment(question.getComment());
                            }
                            answerDao.insertOrReplace(answer);
                            singleAnswer.add(answer);
                            break;
                    }
                    if (singleAnswer.size()>0) {
                        syncQuestion(singleAnswer);
                    }
                }
                else if (!question.isAnswered() && question.getVisible()==View.VISIBLE){
                    instrumentAnswered=0;
                }
            }
        }
        saveTracker(instrumentAnswered,false);
    }

    private <T> void syncQuestion(List<T> answerList){
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();
        String answerListJson = gson.toJson(answerList);
        StringEntity answerEntity = null;
        try {
            answerEntity = new StringEntity(answerListJson,"UTF-8");
            answerEntity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        RestClient.post(this.getActivity(),"SaveAnswer",answerEntity,"application/json",new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                super.onSuccess(statusCode, headers, responseString);
                saveTracker(instrumentAnswered,true);
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                if (statusCode==201){
                    saveTracker(instrumentAnswered,true);
                }
                else {
                    saveTracker(instrumentAnswered, false);
                }
            }
        });
    }


    private void saveTracker(int instrumentAnswered, boolean uploaded) {
        InstrumentInstance instrumentInstance = new InstrumentInstance();
        List<InstrumentInstance> iList = instrumentInstanceDao.queryBuilder().where(
                InstrumentInstanceDao.Properties.ParticipantId.eq(pt.getId()),
                InstrumentInstanceDao.Properties.InstrumentId.eq(instrumentId),
                InstrumentInstanceDao.Properties.InterviewId.eq(iid),
                InstrumentInstanceDao.Properties.TestStaffId.eq(staffId)).list();
        if (iList==null || iList.size()==0) {
            instrumentInstance.setFinished(instrumentAnswered);
            instrumentInstance.setInstrumentId(instrumentId);
            instrumentInstance.setInterviewId(iid);
            instrumentInstance.setTestStaffId(staffId);
            instrumentInstance.setParticipantId(pt.getId());
            instrumentInstance.setModtime(new Date());
            instrumentInstance.setCreateTime(new Date());
            instrumentInstance.setUploaded(uploaded);
            instrumentInstanceDao.insertOrReplace(instrumentInstance);
        }else {
            instrumentInstance = iList.get(0);
            instrumentInstance.setModtime(new Date());
            instrumentInstance.setFinished(instrumentAnswered);
            instrumentInstance.setUploaded(uploaded);
            instrumentInstanceDao.update(instrumentInstance);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private List<Answer> getAnswerByQuestion(Question q, Participant pt, int iid) {
        List<Answer> answers = answerDao.queryBuilder().where(
                AnswerDao.Properties.ParticipantId.eq(pt.getId()),
                AnswerDao.Properties.InterviewId.eq(iid),
                AnswerDao.Properties.QuestionId.eq(q.getId())).list();
        return answers;
    }

    protected void downloadAllData(final int interviewId, String istr, final ArrayAdapter adapter) {
        final RequestParams req  = new RequestParams();
        /*progressDialog.show();
        progressDialog.setMessage(getString(R.string.progressSaving));
        progressDialog.setCanceledOnTouchOutside(false);*/
        req.put("id",interviewId);
        //rest call instrument
        RestClient.get("GetInstrument",req,new JsonHttpResponseHandler(){
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject object) {
                super.onFailure(statusCode, headers, throwable, object);
                Log.e("Error1",throwable.getMessage());
                /*Snackbar snackbar = Snackbar.make(rootView.findViewById(android.R.id.content),throwable.getMessage(),Snackbar.LENGTH_LONG);
                snackbar.show();*/
               // progressDialog.dismiss();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e("Error2",throwable.getMessage());
                /*Snackbar snackbar = Snackbar.make(rootView.findViewById(android.R.id.content),throwable.getMessage(),Snackbar.LENGTH_LONG);
                snackbar.show();*/
                //progressDialog.dismiss();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.e("Error3",throwable.getMessage());
                /*Snackbar snackbar = Snackbar.make(rootView.findViewById(android.R.id.content),throwable.getMessage(),Snackbar.LENGTH_LONG);
                snackbar.show();*/
                //progressDialog.dismiss();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                try {
                    //Save instruments
                    final List<Instrument> instrumentList = getInstrumentArray(response);
                    instrumentDao.insertOrReplaceInTx(instrumentList);
                    int order = 0;
                    final RequestParams req2  = new RequestParams();
                    for (Instrument instrument : instrumentList) {
                        formDao.insertOrReplace(new Form(order,interviewId,instrument.getId()));
                        order++; //db.addInstrument(instrumentList, SQLiteSurveyHelper.TABLE_INSTRUMENT);
                        req2.add("list", instrument.getId()+"");
                    }
                    //rest call question
                    RestClient.get("GetAllQuestion",req2, new JsonHttpResponseHandler(){
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                            try {
                                List<Question> qList = JsonHelper.getQuestionArray(response);
                                if(qList!=null){
                                    InsertQuestionsTask adder = new InsertQuestionsTask(qList);
                                    //async task inserting questions
                                    adder.execute();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                //progressDialog.dismiss();
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            super.onFailure(statusCode, headers, responseString, throwable);
                            /*Snackbar snackbar = Snackbar.make(rootView.findViewById(android.R.id.content),throwable.getMessage(),Snackbar.LENGTH_LONG);
                            snackbar.show();*/
                            Log.e("Error4",throwable.getMessage());
                            //progressDialog.dismiss();
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            super.onFailure(statusCode, headers, throwable, errorResponse);
                            /*Snackbar snackbar = Snackbar.make(rootView.findViewById(android.R.id.content),throwable.getMessage(),Snackbar.LENGTH_LONG);
                            snackbar.show();*/
                            Log.e("Error5",throwable.getMessage());
                           // progressDialog.dismiss();
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                            super.onFailure(statusCode, headers, throwable, errorResponse);
                            /*Snackbar snackbar = Snackbar.make(rootView.findViewById(android.R.id.content),throwable.getMessage(),Snackbar.LENGTH_LONG);
                            snackbar.show();*/
                            Log.e("Error6",throwable.getMessage());
                           // progressDialog.dismiss();
                        }

                        @Override
                        public void onFinish() {
                            super.onFinish();

                            List<Instrument> instruments = getInstruments(interviewId); //db.getAllInstruments(interviewId);
                            //instruments.add(0,new Instrument(-1L,"Verifyy Participant Info","CHN title for verification",null,null));
                            if (instruments!=null && adapter!=null) {
                                adapter.clear();
                                adapter.addAll(instruments);
                                adapter.notifyDataSetChanged();
                            }
                           // progressDialog.dismiss();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                    //progressDialog.dismiss();
                }
            }
        });
    }

    private class InsertQuestionsTask extends AsyncTask {
        List<Question> qList;
        //ProgressDialog progressDialog;

        public InsertQuestionsTask(List<Question> qList) {
            this.qList = qList;
           // this.progressDialog = progressDialog;
        }

        @Override
        protected Object doInBackground(Object[] params) {
            //db.addQuestion(qList);
            questionDao.insertOrReplaceInTx(qList);
            List<Choice> choiceList = new ArrayList<>();
            for (Question question : qList){
                choiceList.addAll(question.getMemChoiceList());
            }
            if (choiceList!=null) {
                choiceDao.insertOrReplaceInTx(choiceList);
            }
            return qList;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            //progressDialog.dismiss();
        }
    }

    protected List<Instrument> getInstruments(int interviewId) {
        QueryBuilder<Instrument> queryBuilder = instrumentDao.queryBuilder();
        queryBuilder.join(Form.class, FormDao.Properties.InstrumentId)
                .where(FormDao.Properties.InterviewId.eq(interviewId));
        return queryBuilder.orderRaw("instrumentorder").list();
    }

    /*public void getActiveInterview() throws ExecutionException, InterruptedException {
        //questionAdapter.clear();
        List<InterviewInstance> instances = interviewInstanceDao.queryBuilder().where(InterviewInstanceDao.Properties.StaffId.eq(staffId)).orderAsc(InterviewInstanceDao.Properties.ParticipantId).list();//db.getInterviewInstance();
        if (instances != null) {
          //  questionAdapter.addAll(instances);
            questionAdapter.notifyDataSetChanged();
        }
    }*/


}
