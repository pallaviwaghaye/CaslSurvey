package com.casl.caslsurvey;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;
import com.casl.model.AnswerDao;
import com.casl.model.DaoSession;
import com.casl.model.InstrumentInstanceDao;
import com.casl.model.InterviewInstance;
import com.casl.model.InterviewInstanceDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

public class NetworkChangeReceiver extends BroadcastReceiver {
    private AnswerDao answerDao;
    private InterviewInstanceDao interviewInstanceDao;
    private InstrumentInstanceDao instrumentInstanceDao;
    @Override
    public void onReceive(final Context context, final Intent intent) {
        Log.d("MY_DEBUG_TAG", "received");
        final ConnectivityManager connMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        final android.net.NetworkInfo wifi = connMgr
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        final android.net.NetworkInfo mobile = connMgr
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        /*
        if (wifi.isAvailable()) {
            DaoSession daoSession = ((App)context.getApplicationContext()).getDaoSession();
            answerDao = daoSession.getAnswerDao();
            interviewInstanceDao = daoSession.getInterviewInstanceDao();
            instrumentInstanceDao = daoSession.getInstrumentInstanceDao();
            //Toast.makeText(context,"You are online. Don't forget to upload your data from home page.", Toast.LENGTH_LONG).show();
            List<InterviewInstance> interviewInstanceList = interviewInstanceDao.loadAll();
            for (InterviewInstance ii : interviewInstanceList) {
                if (ii!=null && ii.getInterviewId()!=-1) {
                    SummaryActivity.uploadAnswer(ii, answerDao, context, interviewInstanceDao, instrumentInstanceDao);
                }
            }
        }*/
    }
}
