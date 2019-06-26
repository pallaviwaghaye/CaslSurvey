package com.casl.caslsurvey;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.LocaleList;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.casl.Utility.JsonHelper;
import com.casl.dbrestful.RestClient;
import com.casl.model.Choice;
import com.casl.model.ChoiceDao;
import com.casl.model.DaoSession;
import com.casl.model.Form;
import com.casl.model.FormDao;
import com.casl.model.Instrument;
import com.casl.model.InstrumentDao;
import com.casl.model.Question;
import com.casl.model.QuestionDao;
import com.casl.model.TranslateBase;
import com.casl.sqlite.SqliteSurveyHelperGreen;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.greenrobot.greendao.query.QueryBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

import static com.casl.Utility.JsonHelper.getInstrumentArray;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    protected String staffId;
    private SharedPreferences sp;
    protected ZoomView zoomView;
    private TextView userId;
    private ProgressDialog progressDialog;
    private QuestionDao questionDao;
    private ChoiceDao choiceDao;
    private FormDao formDao;
    private InstrumentDao instrumentDao;

    private static Locale myLocale;
    private NavigationView navigationView;

    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sp = getSharedPreferences("global", Context.MODE_PRIVATE);
        TranslateBase.sp = sp;
        TranslateBase.context = getApplicationContext();
        redirectToLogin();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ConstraintLayout cl = (ConstraintLayout)findViewById(R.id.contraint_content);
        DaoSession daoSession = ((App)getApplication()).getDaoSession();

        //participantDao = daoSession.getParticipantDao();
        //homeDao = daoSession.getHomeDao();
        formDao = daoSession.getFormDao();
        instrumentDao = daoSession.getInstrumentDao();
        questionDao = daoSession.getQuestionDao();
        choiceDao = ((App)getApplication()).getDaoSession().getChoiceDao();
        progressDialog = new ProgressDialog(this);
        zoomView = new ZoomView(this);
        cl.addView(zoomView);
        userId = (TextView) navigationView.getHeaderView(0).findViewById(R.id.nav_text2);

       // Menu menu = nav_view.getMenu();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

        /*if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }else {
            //super.onBackPressed();
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce=false;
                }
            }, 2000);
        }*/
    }



    @Override
    protected void onResume() {
        super.onResume();
        if (userId!=null&&staffId!=null){
            userId.setText(staffId);
        }
        //redirectToLogin();
       // navigationView.getMenu().getItem(4).setActionView(R.layout.menu_dot);
        /*TextView view = (TextView) navigationView.getMenu().findItem(R.id.item3).getActionView().findViewById(R.id.textUsername);
        view.setText(staffId);*/
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id){
            /*case R.id.nav_clearall:
                //this.deleteDatabase(SqliteSurveyHelperGreen.DATABASE_NAME);
                break;
            case R.id.nav_interviews:
                Intent dbmanager = new Intent(this,AndroidDatabaseManager.class);
                startActivity(dbmanager);
                return true;*/
            case R.id.nav_refresh:

                AlertDialog.Builder alertDialog1 = new AlertDialog.Builder(MainActivity.this);
                alertDialog1.setTitle("Reload");
                alertDialog1.setMessage("Update the interview data when connected to the internet");
                alertDialog1.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        //downloadAllData(948,"Nanjing Pine 1.1",null);
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
                });
                alertDialog1.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alertDialog1.show();
                // break;


                return true;
            case R.id.nav_ch:
                saveLocale("zh");
                setLocale("zh");
                break;
            case R.id.nav_en:
                saveLocale("en");
                setLocale("en");
                break;
            case R.id.nav_tr:
                saveLocale("tr");
                setLocale("zh-rTW");
                break;
            case R.id.nav_hi :
                saveLocale("hi");
                setLocale("hi");
                break;
            case R.id.nav_add_participant:
                Intent intent = new Intent(getApplicationContext(), AddParticipantActivity.class);
                startActivity(intent);
                return true;
                 //break;
            case R.id.nav_signout:
                sp.edit().clear().commit();
                redirectToLogin();
                return true;
           /* case R.id.nav_thanks:
                Intent intent1 = new Intent(getApplicationContext(), ThankYouActivity.class);
                startActivity(intent1);
                return true;*/
        }
        finish();
        startActivity(getIntent());
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void redirectToLogin(){
        staffId = sp.getString("UserId",null);
        if (staffId==null){
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
    public void saveLocale(String lang) {

        String langPref = "Language";
        SharedPreferences prefs = getSharedPreferences("global",
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(langPref, lang);
        editor.commit();


    }
    public void setLocale(String lang) {
        myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            conf.setLocale(myLocale);
            getApplicationContext().createConfigurationContext(conf);

        } else {
            conf.locale = myLocale;
            res.updateConfiguration(conf, dm);
        }*/

        Intent refresh = new Intent(this, MainActivity.class);
        startActivity(refresh);
    }

    /*@Override
    protected void attachBaseContext(Context newBase) {
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            super.attachBaseContext(wrap(newBase, "hi"));
        }
        else {
            super.attachBaseContext(newBase);
        }
    }

    public static ContextWrapper wrap(Context context, String language) {
        Resources res = context.getResources();
        Configuration configuration = res.getConfiguration();
        Locale newLocale = new Locale(language);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            configuration.setLocale(newLocale);
            LocaleList localeList = new LocaleList(newLocale);
            LocaleList.setDefault(localeList);
            configuration.setLocales(localeList);
            context = context.createConfigurationContext(configuration);

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            configuration.setLocale(newLocale);
            context = context.createConfigurationContext(configuration);

        } else {
            configuration.locale = newLocale;
            res.updateConfiguration(configuration, res.getDisplayMetrics());
        }

        return new ContextWrapper(context);
    }*/

    /*@SuppressWarnings("deprecation")
    private void setLocale(String lang){
        myLocale = new Locale(lang);
        //SharedPrefUtils.saveLocale(locale); // optional - Helper method to save the selected language to SharedPreferences in case you might need to attach to activity context (you will need to code this)
        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        configuration.locale = myLocale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
            getApplicationContext().createConfigurationContext(configuration);
        } else{
            resources.updateConfiguration(configuration,displayMetrics);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            getApplicationContext().createConfigurationContext(configuration);
        } else{
            resources.updateConfiguration(configuration,displayMetrics);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1){
            getApplicationContext().createConfigurationContext(configuration);
        } else{
            resources.updateConfiguration(configuration,displayMetrics);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            getApplicationContext().createConfigurationContext(configuration);
        } else{
            resources.updateConfiguration(configuration,displayMetrics);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            getApplicationContext().createConfigurationContext(configuration);
        } else {
            resources.updateConfiguration(configuration,displayMetrics);
        }
        Intent refresh = new Intent(this, MainActivity.class);
        startActivity(refresh);
    }*/

        protected boolean checkActiveInternetConnection() {
        ConnectivityManager manager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            // Network is present and connected
            isAvailable = true;
        }
        return isAvailable;
    }
    protected void downloadAllData(final int interviewId, String istr, final ArrayAdapter adapter) {
        final RequestParams req  = new RequestParams();
        progressDialog.show();
        progressDialog.setMessage(getString(R.string.progressSaving));
        progressDialog.setCanceledOnTouchOutside(false);
        req.put("id",interviewId);
        //rest call instrument
        RestClient.get("GetInstrument",req,new JsonHttpResponseHandler(){
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject object) {
                super.onFailure(statusCode, headers, throwable, object);
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),throwable.getMessage(),Snackbar.LENGTH_LONG);
                snackbar.show();
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),throwable.getMessage(),Snackbar.LENGTH_LONG);
                snackbar.show();
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),throwable.getMessage(),Snackbar.LENGTH_LONG);
                snackbar.show();
                progressDialog.dismiss();
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
                                    InsertQuestionsTask adder = new InsertQuestionsTask(qList,progressDialog);
                                    //async task inserting questions
                                    adder.execute();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                progressDialog.dismiss();
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            super.onFailure(statusCode, headers, responseString, throwable);
                            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),throwable.getMessage(),Snackbar.LENGTH_LONG);
                            snackbar.show();
                            progressDialog.dismiss();
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            super.onFailure(statusCode, headers, throwable, errorResponse);
                            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),throwable.getMessage(),Snackbar.LENGTH_LONG);
                            snackbar.show();
                            progressDialog.dismiss();
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                            super.onFailure(statusCode, headers, throwable, errorResponse);
                            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),throwable.getMessage(),Snackbar.LENGTH_LONG);
                            snackbar.show();
                            progressDialog.dismiss();
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
                            progressDialog.dismiss();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            }
        });
    }

    private class InsertQuestionsTask extends AsyncTask {
        List<Question> qList;
        ProgressDialog progressDialog;

        public InsertQuestionsTask(List<Question> qList, ProgressDialog progressDialog) {
            this.qList = qList;
            this.progressDialog = progressDialog;
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
}
