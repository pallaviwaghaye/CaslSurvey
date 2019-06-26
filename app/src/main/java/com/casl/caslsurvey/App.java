package com.casl.caslsurvey;

/**
 * Created by kang on 7/27/2017.
 */

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;
import android.support.multidex.MultiDex;

import com.casl.model.DaoMaster;
import com.casl.model.DaoSession;

import org.greenrobot.greendao.database.Database;

//import org.greenrobot.greendao.example.DaoMaster.DevOpenHelper;

public class App extends Application {
    /** A flag to show how easily you can switch from standard SQLite to the encrypted SQLCipher. */
    public static final boolean ENCRYPTED = true;
    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "surveyGreen");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);

    }

}
