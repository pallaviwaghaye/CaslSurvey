package com.casl.caslsurvey;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.casl.Utility.MappingData;
import com.casl.Utility.SharedPreferenceManager;
import com.casl.dbrestful.RestClient;
import com.casl.model.Answer;
import com.casl.model.AnswerDao;
import com.casl.model.Contact;
import com.casl.model.ContactUpload;
import com.casl.model.DaoSession;
import com.casl.model.FormDao;
import com.casl.model.InstrumentInstance;
import com.casl.model.InstrumentInstanceDao;
import com.casl.model.Interview;
import com.casl.model.InterviewInstance;
import com.casl.model.InterviewInstanceDao;
import com.casl.model.InterviewStatus;
import com.casl.model.Participant;
import com.casl.model.ParticipantDao;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.greenrobot.greendao.query.QueryBuilder;
import org.json.JSONObject;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

public class SummaryActivity extends MainActivity {
    private ListView activeInterviewListView;
    private TwoLineArrayAdapter<InterviewInstance> adapter;
    //private SQLiteSurveyHelper db;
    private AnswerDao answerDao;
    private InterviewInstanceDao interviewInstanceDao;
    private ParticipantDao participantDao;
    private InstrumentInstanceDao instrumentInstanceDao;
    String staffid;
    // Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaoSession daoSession = ((App) getApplication()).getDaoSession();
        answerDao = daoSession.getAnswerDao();
        interviewInstanceDao = daoSession.getInterviewInstanceDao();
        participantDao = daoSession.getParticipantDao();
        instrumentInstanceDao = daoSession.getInstrumentInstanceDao();
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        ConstraintLayout cl = (ConstraintLayout) findViewById(R.id.contraint_content);
        View childLayout = inflater.inflate(R.layout.activity_summary, null);
        //zoomView.addView(childLayout);
        cl.addView(childLayout);
        //db = SQLiteSurveyHelper.getInstance(this);

        SharedPreferenceManager.setApplicationContext(SummaryActivity.this);
        //contact = SharedPreferenceManager.getContactObject();

        //Log.e("contactinfo", String.valueOf(contact));

        activeInterviewListView = (ListView) findViewById(R.id.summary_list_view);
        adapter = new TwoLineArrayAdapter<InterviewInstance>(this, R.layout.interview_summary, new ArrayList<InterviewInstance>()) {
            @Override
            public float getPercentage(InterviewInstance interviewInstance) {
                return interviewInstance.getPercentage();
            }

            @Override
            public String lineOneText(InterviewInstance interviewInstance) {
                int unUpload = 0;
                if (interviewInstance.getPt() != null) {
                    List<InstrumentInstance> instrumentInstanceList = instrumentInstanceDao.queryBuilder().where(
                            InstrumentInstanceDao.Properties.Uploaded.eq(false),
                            InstrumentInstanceDao.Properties.ParticipantId.eq(interviewInstance.getPt().getId()),
                            InstrumentInstanceDao.Properties.InterviewId.eq(interviewInstance.getInterviewId()),
                            InstrumentInstanceDao.Properties.Finished.eq(1)).list();
                    unUpload = instrumentInstanceList.size();
                }
                String uploadStr = "";
                if (unUpload > 0) {
                    uploadStr = " <font color='red'>(" + unUpload + " " + getResources().getString(R.string.uploadpending) + ")</font>";
                } //Scale not uploaded
                else {
                    uploadStr = " <font color='green'>(" + getResources().getString(R.string.uploaded) + ")</font>";

                } //Synced
                return interviewInstance.getName() + uploadStr;
            }

            @Override
            public String lineTwoText(InterviewInstance interviewInstance) {
                if (interviewInstance.getPt() != null) {
                    return interviewInstance.getPt().toString();
                }
                return "";
            }

            @Override
            public String lineThreeText(InterviewInstance interviewInstance) {
                if (interviewInstance.getInterviewStatus() == InterviewStatus.PAUSED) {
                    return " <font color='blue'>暂停</font>"; //time out / Suspend/ pause
                } else if (interviewInstance.getInterviewStatus() == InterviewStatus.UPLOADED) {
                    return " <font color='green'>已全部上传</font>"; //Uploaded all
                }
                return interviewInstance.getStatus();
            }
        };
        activeInterviewListView.setAdapter(adapter);
        activeInterviewListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final InterviewInstance interviewInstance = (InterviewInstance) parent.getAdapter().getItem(position);
                //final DonutProgress donutProgress = (DonutProgress) view.findViewById(R.id.donut_progress);

                staffid= interviewInstance.getStaffId();

                AlertDialog.Builder adb = new AlertDialog.Builder(SummaryActivity.this);
                adb.setTitle(getResources().getString(R.string.EditOrUploadInterview));
                adb.setMessage(interviewInstance.getName() + ", " + interviewInstance.getPt().getName());
                if (interviewInstance.getInterviewStatus() == InterviewStatus.PAUSED) {
                    adb.setPositiveButton(getResources().getString(R.string.edit), new AlertDialog.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getApplicationContext(), InstrumentActivity.class);
                            interviewInstance.getPt().getHome();
                            Participant pt = participantDao.load(interviewInstance.getParticipantId());
                            pt.getHome();
                            intent.putExtra(InterviewActivity.PID, pt);
                            intent.putExtra(InterviewActivity.ISTR, interviewInstance.getName());
                            intent.putExtra(InterviewActivity.IID, interviewInstance.getInterviewId());
                            startActivity(intent);
                        }
                    });
                } else if (interviewInstance.getInterviewStatus() == InterviewStatus.UPLOADED) {
                    adb.setTitle(getResources().getString(R.string.UploadOrDeleteInterview)); //This interview has been uploaded successfully, Please select delete or upload again
                    adb.setMessage(interviewInstance.getName() + ", " + interviewInstance.getPt().getName() + "\n" + "\n" + getResources().getString(R.string.note));
                    adb.setPositiveButton(getResources().getString(R.string.delete), new AlertDialog.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            delete(interviewInstance);
                        }
                    });
                }
                adb.setNegativeButton(getResources().getString(R.string.upload), new AlertDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        uploadAnswer(interviewInstance, answerDao, getApplicationContext(), interviewInstanceDao, instrumentInstanceDao);
                        Participant pt = participantDao.load(interviewInstance.getParticipantId());
                        if (pt != null) {
                            pt.getHome();
                            uploadParticipant(pt);
                            uploadInterviewInstance(interviewInstance);

                            Contact contactSharedPRef = SharedPreferenceManager.getContactByPIdAndIId(interviewInstance.getInterviewId(), interviewInstance.getParticipantId());
                            if (contactSharedPRef != null) {
                                if (!contactSharedPRef.isUploaded()) {
                                    uploadContact(contactSharedPRef);
                                } else {
                                   // Toast.makeText(SummaryActivity.this, "No need to upload - contact", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                });
                adb.show();
            }
        });

        activeInterviewListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder adb = new AlertDialog.Builder(SummaryActivity.this);
                final InterviewInstance ii = adapter.getItem(position);
                adb.setTitle("Delete?");
                adb.setMessage("Are you sure you want to delete " + ii.getName() + ", " + ii.getPt().getName());
                final int positionToRemove = position;
                adb.setNegativeButton("Cancel", null);
                adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        delete(ii);
                    }
                });
                adb.show();
                return true;
            }
        });
        try {
            getActiveInterview();
        } catch (Exception e) {
            e.printStackTrace();
        }
        setTitle("Interviews");
    }

    private void delete(InterviewInstance ii) {
        adapter.clear();
        //participantDao.delete(ii.getPt());
        List<InterviewInstance> interviewInstances = interviewInstanceDao.queryBuilder().where(
                InterviewInstanceDao.Properties.InterviewId.eq(ii.getInterviewId()),
                InterviewInstanceDao.Properties.ParticipantId.eq(ii.getParticipantId())).list();
        interviewInstanceDao.deleteInTx(interviewInstances);
        //db.removeInterviewInstance(ii.getInterviewId(),ii.getPt().getId());
        List<Answer> answerList = answerDao.queryBuilder().where(
                AnswerDao.Properties.InterviewId.eq(ii.getInterviewId()),
                AnswerDao.Properties.ParticipantId.eq(ii.getParticipantId())).list();
        answerDao.deleteInTx(answerList);
        ; //db.removeAnswer(ii.interviewId,ii.pt.getId());
        //instrumentInstance delete
        List<InstrumentInstance> instrumentInstanceList = instrumentInstanceDao.queryBuilder().where(
                InstrumentInstanceDao.Properties.ParticipantId.eq(ii.getParticipantId()),
                InstrumentInstanceDao.Properties.InterviewId.eq(ii.getInterviewId())
        ).list();
        instrumentInstanceDao.deleteInTx(instrumentInstanceList);
        //participantDao.delete(ii.getPt());
        List<Participant> ptList = participantDao.queryBuilder().where(
                ParticipantDao.Properties.Id.eq(ii.getParticipantId())
        ).list();
        participantDao.deleteInTx(ptList);
        try {
            getActiveInterview();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //SparseBooleanArray selectedItem = activeInterviewListView.getCheckedItemPositions();
        int id = item.getItemId();
        if (id == R.id.add) {
            Intent intent = new Intent(getApplicationContext(), InterviewActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.refresh) {
            //Toast.makeText(SummaryActivity.this,"Update the interview data when connected to the internet",Toast.LENGTH_LONG).show();
            adapter.clear();
            try {
                getActiveInterview();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public void uploadAnswer(final InterviewInstance ii, AnswerDao answerDao, final Context context, final InterviewInstanceDao interviewInstanceDao, final InstrumentInstanceDao instrumentInstanceDao) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();
        List<Answer> answersInterview = null;
        if (ii != null && ii.getInterviewId() != -1) {
            QueryBuilder qb = answerDao.queryBuilder().where(
                    AnswerDao.Properties.ParticipantId.eq(ii.getPt().getId()),
                    AnswerDao.Properties.InterviewId.eq(ii.getInterviewId()));
            answersInterview = qb.list();
        } else {
            answersInterview = answerDao.loadAll();
        }
        if (answersInterview != null) {
            String answerListJson = gson.toJson(answersInterview);
            Log.d("answer", answerListJson);
            try {
                StringEntity answerEntity = new StringEntity(answerListJson, "UTF-8");
                answerEntity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                RestClient.post(context, "SaveAnswer", answerEntity, "application/json", new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        List<InstrumentInstance> instrumentInstanceList = instrumentInstanceDao.queryBuilder().where(
                                InstrumentInstanceDao.Properties.ParticipantId.eq(ii.getParticipantId()),
                                InstrumentInstanceDao.Properties.InterviewId.eq(ii.getInterviewId())).list();
                        for (InstrumentInstance instrumentInstance : instrumentInstanceList) {
                            instrumentInstance.setUploaded(true);
                        }
                        interviewInstanceDao.insertOrReplace(ii);
                        instrumentInstanceDao.insertOrReplaceInTx(instrumentInstanceList);
                        if (ii.getPercentage() == 1) {
                            ii.setInterviewStatus(InterviewStatus.UPLOADED);
                            interviewInstanceDao.insertOrReplace(ii);
                        }
                        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Successfully Uploaded.", Snackbar.LENGTH_LONG);
                        //snackbar.show();
                        View snackbarView = snackbar.getView();
                        TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                        tv.setTextColor(getResources().getColor(R.color.dark_green));
                        tv.setTextSize(17);
                        //tv.setGravity(0);
                        snackbarView.setBackgroundColor(Color.parseColor("#FFB0D9B9"));
                        snackbar.show();

                        try {
                            //adapter.clear();
                            getActiveInterview();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        //donutProgress.setInnerBottomText("Failed");
                        //Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),error.getMessage(),Snackbar.LENGTH_LONG);
                        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Error occurred! Please log out and log in again to upload the interview." + "\n" + "Note: Do not log out if you want to resume the offline interview process.", Snackbar.LENGTH_LONG);
                        View snackbarView = snackbar.getView();
                        TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                        tv.setTextColor(getResources().getColor(R.color.dashboard_count_color));
                        tv.setMaxLines(5);
                        //tv.setTextSize(17);
                        //tv.setGravity(0);
                        //snackbarView.setBackgroundColor(getResources().getColor(R.color.newblack));
                        snackbar.show();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void uploadParticipant(Participant participant) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        if (participant != null) {
            String participantJson = gson.toJson(participant);
            Log.d("participant", participantJson + " ");
            try {
                StringEntity participantEntity = new StringEntity(participantJson, "UTF-8");
                participantEntity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                RestClient.post(this, "SaveParticipant", participantEntity, "application/json", new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        //Toast.makeText(this,.getName() + " "+ ii.getParticipantId()+" answers was automatically uploaded.", Toast.LENGTH_SHORT).show();
                        /*
                        try {
                            //adapter.clear();
                            getActiveInterview();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        */
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        //donutProgress.setInnerBottomText("Failed");
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void getActiveInterview() throws ExecutionException, InterruptedException {
        adapter.clear();
        List<InterviewInstance> instances = interviewInstanceDao.queryBuilder().where(InterviewInstanceDao.Properties.StaffId.eq(staffId)).orderAsc(InterviewInstanceDao.Properties.ParticipantId).list();//db.getInterviewInstance();
        if (instances != null) {
            adapter.addAll(instances);
            adapter.notifyDataSetChanged();
        }
    }

    private void uploadInterviewInstance(InterviewInstance instance) {
        if (checkActiveInternetConnection()) {
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
            List<InterviewInstance> instances = new ArrayList<>();
            instances.add(instance);
            String interviewListJson = gson.toJson(instances);
            try {
                StringEntity interviewEntity = new StringEntity(interviewListJson, "UTF-8");
                interviewEntity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                //Log.d("answer_json", answerListJson);
                //Log.d("interviewInstance",interviewListJson);
                RestClient.post(this, "SaveInterview", interviewEntity, "application/json", new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        Log.d("response", "success");
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        Log.d("response", "error");
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void uploadContact(final Contact contact) {
        Gson gson = new GsonBuilder().serializeNulls().create();
        if (contact != null) {

            ContactUpload contactUpload = getContactUpload(contact);
            String contactJson = gson.toJson(contactUpload);
            Log.d("Contact", contactJson);
            try {
                StringEntity contactEntity = new StringEntity(contactJson, "UTF-8");
                contactEntity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                RestClient.post(this, "SaveContact", contactEntity, "application/json", new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        //SharedPreferenceManager.clearPreferences();
                        contact.setUploaded(true);
                        SharedPreferenceManager.saveContactByPIdAndIId(contact);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        //donutProgress.setInnerBottomText("Failed");
                        Log.e("Upload Contact API", error.getLocalizedMessage());
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private ContactUpload getContactUpload(Contact contact) {
        ContactUpload contactUpload = new ContactUpload();
        //long time= System.currentTimeMillis();

        // 51 fields, 35 here and 16 null in model

        contactUpload.setParticipantId(contact.getParticipantId());
        contactUpload.setInterviewId(contact.getInterviewId());
        contactUpload.setReluctance(contact.getReluctance());
        contactUpload.setSchTime(contact.getSchTime());
        contactUpload.setSchDate(contact.getSchDate());
        contactUpload.setPartcont(contact.getPartcont());
        contactUpload.setOthrcontact(contact.getOthrcontact());
        contactUpload.setNopartcont(contact.getNopartcont());
        contactUpload.setDeath(contact.getDeath());
        contactUpload.setDieDate(contact.getDieDate());
        contactUpload.setDieCause(contact.getDieCause());
        contactUpload.setNursHome(contact.getNursHome());
        contactUpload.setDiePlace(contact.getDiePlace());
        contactUpload.setOtherDiePlace(contact.getOtherDiePlace());
        contactUpload.setDieState(contact.getDieState());
        contactUpload.setPartrel1(contact.getPartrel1());
        contactUpload.setPartrel2(contact.getPartrel2());
        contactUpload.setPartrel3(contact.getPartrel3());
        contactUpload.setFamrel1(contact.getFamrel1());
        contactUpload.setFamrel2(contact.getFamrel2());
        contactUpload.setFamrel3(contact.getFamrel3());
        contactUpload.setFamrel4(contact.getFamrel4());
        contactUpload.setDieWhom(contact.getDieWhom());
        contactUpload.setDieComm(contact.getDieComm());
        contactUpload.setFormComm(contact.getFormComm());
        contactUpload.setPartcontadd(contact.getPartcontadd());
        contactUpload.setIntMethod(contact.getIntMethod());
        //contactUpload.setTran_user(contact.getTran_user());
        //contactUpload.setTran_date(contact.getTran_date());
        contactUpload.setContactDate(contact.getContactDate());
        contactUpload.setContactTime(contact.getContactTime());
        contactUpload.setContactType(contact.getContactType());
        contactUpload.setOtherType(contact.getOtherType());
        contactUpload.setPurpose(contact.getPurpose());
        contactUpload.setOtherPurpose(contact.getOtherPurpose());
        contactUpload.setTran_date(contact.getTran_date());
        contactUpload.setTran_user(staffid);
        /*
         private String contactDate;
    private String contactTime;
    private String contactType;
    private String otherType;
    private String purpose;
    private String otherPurpose;
         */



        return contactUpload;
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.clear();
        try {
            getActiveInterview();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
