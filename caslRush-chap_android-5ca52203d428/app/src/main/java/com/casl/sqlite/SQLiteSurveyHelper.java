package com.casl.sqlite;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.casl.model.Answer;
import com.casl.model.Choice;
import com.casl.model.InstrumentInstance;
import com.casl.model.InterviewInstance;
import com.casl.model.InterviewStatus;
import com.casl.model.TranslateBase;

/**
 * Created by Staff73 on 6/13/2017.
 */
/*
public class SQLiteSurveyHelper extends SQLiteOpenHelper {
    //singleton
    private static SQLiteSurveyHelper sInstance;
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "survey";
    public static String getDatabase() {
        return DATABASE_NAME;
    }
    // Table names
    public static final String TABLE_ANSWER = "answer";
    public static final String TABLE_MUL_ANSWER = "mulAnswer";
    public static final String TABLE_INSTRUMENT = "instrument";
    public static final String TABLE_INSTRUMENT_INSTANCE = "instrument_instance";
    public static final String TABLE_QUESTION = "question";
    public static final String TABLE_II = "instance";
    public static final String TABLE_CHOICE = "choice";
    public static final String TABLE_PARTICIPANT = "participant";
    public static final String TABLE_HOME = "home";


    //Common Column nammes
    private static final String ID = "id";
    private static final String NAME= "name";
    private static final String CH_NAME= "chineseName";
    private static final String UID = "userId";
    private static final String P_ID = "participantId";
    private static final String I_ID = "interviewId";
    private static final String INS_ID = "instrumentId";
    private static final String Q_ID = "questionId";
    private static final String ANS = "answer";
    private static final String TYPE = "type";
    private static final String DATE = "date";
    private static final String STATUS = "status";
    private static final String ORDER = "orderId";
    private static final String PARENT = "parentId";
    private static final String CODE_ID = "codeid";
    private static final String TRIGGER = "trigger";
    private static final String FINISHED = "finish";
    private static final String CONTENT = "content";
    private static final String CH_CONTENT = "chContent";
    private static final String CREATETIME = "createTime";
    private static final String MODTIME = "modTime";
    private static final String PERCENTAGE = "percentage";
    private static final String PHONE = "phone";
    private static final String DOB = "dob";
    private static final String FNAME = "fname";
    private static final String LNAME = "lname";
    private static final String LANG_PREF = "langPref";
    private static final String LANG_CAN = "langCan";
    private static final String HOME_ID = "homeId";
    private static final String STREET = "street";
    private static final String STATE = "state";
    private static final String CITY = "city";
    private static final String ZIP = "zip";


    public static synchronized SQLiteSurveyHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new SQLiteSurveyHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    public SQLiteSurveyHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ANSWER_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_ANSWER + "("
                + P_ID + " TEXT," + I_ID + " INTEGER,"
                + Q_ID + " INTEGER," + INS_ID + " INTEGER,"
                + ANS + " TEXT,"
                + "PRIMARY KEY "+ "("+ P_ID +", " + Q_ID +", " +I_ID+")"+")";

        String CREATE_MULTIPLE_ANSWER_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_MUL_ANSWER + "("
                + P_ID + " TEXT," + I_ID + " INTEGER,"
                + Q_ID + " INTEGER," + INS_ID + " INTEGER,"
                + ANS + " TEXT" +")";

        String CREATE_INSTRUMENT_TABLE = "CREATE TABLE " + TABLE_INSTRUMENT + "("
                + ID + " INTEGER PRIMARY KEY,"
                + ORDER + " INTEGER,"
                + PARENT + " INTEGER,"
                + NAME + " TEXT,"
                + CONTENT + " TEXT,"
                + CH_CONTENT + " TEXT,"
                + CH_NAME + " TEXT"+ ")";

        String CREATE_INSTRUMENT_INSTANCE_TABLE = "CREATE TABLE " + TABLE_INSTRUMENT_INSTANCE + "("
                + INS_ID + " INTEGER,"
                + I_ID + " INTEGER,"
                + P_ID + " TEXT,"
                + CREATETIME + " DATE DEFAULT (datetime('now','localtime')),"
                + MODTIME + " DATE DEFAULT (datetime('now','localtime')),"
                + UID + " INTEGER,"
                + FINISHED + " INTEGER,"
                + "PRIMARY KEY "+ "("+ I_ID +", "  + P_ID +", "  + INS_ID +")"+ ")";;
        String CREATE_QUESTION_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_QUESTION + "("
                + INS_ID + " INTEGER,"
                + TYPE + " INTEGER,"
                + PARENT + " INTEGER,"
                + ID + " INTEGER PRIMARY KEY,"
                + NAME + " TEXT,"
                + CH_NAME + " TEXT"+ ")";
        String CREATE_CHOICE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_CHOICE + "("
                + Q_ID + " INTEGER,"
                + CODE_ID + " INTEGER,"
                + TRIGGER + " INTEGER,"
                + ID + " INTEGER PRIMARY KEY,"
                + NAME + " TEXT,"
                + CH_NAME + " TEXT"+ ")";
        String CREATE_II_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_II + "("
                + ID + " INTEGER,"
                + UID + " TEXT,"
                + DATE + " DATETIME,"
                + STATUS + " TEXT,"
                + NAME + " TEXT,"
                + P_ID + " TEXT,"
                + PERCENTAGE + " TEXT,"
                + "PRIMARY KEY "+ "("+ ID +", "  + P_ID +")"+ ")";
        String CREATE_PARTICIPANT_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_PARTICIPANT + "("
                + ID + " TEXT PRIMARY KEY,"
                + PHONE + " TEXT,"
                + DOB + " DATETIME,"
                + FNAME + " TEXT,"
                + LNAME + " TEXT,"
                + LANG_CAN+ " TEXT,"
                + LANG_PREF + " TEXT,"
                + HOME_ID + " INTEGER" + ")";
        String CREATE_HOME_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_HOME + "("
                + ID + " INTEGER PRIMARY KEY,"
                + STREET + " TEXT,"
                + STATE + " TEXT,"
                + CITY + " TEXT,"
                + ZIP + " TEXT" + ")";

        db.execSQL(CREATE_ANSWER_TABLE);
        db.execSQL(CREATE_INSTRUMENT_TABLE);
        db.execSQL(CREATE_QUESTION_TABLE);
        db.execSQL(CREATE_II_TABLE);
        db.execSQL(CREATE_CHOICE_TABLE);
        db.execSQL(CREATE_MULTIPLE_ANSWER_TABLE);
        db.execSQL(CREATE_INSTRUMENT_INSTANCE_TABLE);
        db.execSQL(CREATE_PARTICIPANT_TABLE);
        db.execSQL(CREATE_HOME_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ANSWER);
        onCreate(db);
    }
    /*
    public void addAnswer(Answer answer) throws SQLiteConstraintException {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(P_ID, answer.participantId);
        values.put(I_ID, answer.interviewId);
        values.put(Q_ID, answer.questionId);
        values.put(INS_ID, answer.instrumentId);
        values.put(ANS,answer.answer);
        long i = db.insertWithOnConflict(TABLE_ANSWER, null, values,SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    public void addMulAnswer(List<Answer> answers) throws SQLiteConstraintException {
        SQLiteDatabase db = this.getWritableDatabase();
        Answer answerT = answers.get(0);
        db.delete(TABLE_MUL_ANSWER,P_ID + "=? and " + I_ID + "=? and "
                + Q_ID + "=?",new String[]{String.valueOf(answerT.participantId),String.valueOf(answerT.interviewId),String.valueOf(answerT.questionId)});
        for (Answer answer : answers) {
            ContentValues values = new ContentValues();
            values.put(P_ID, answer.participantId);
            values.put(I_ID, answer.interviewId);
            values.put(Q_ID, answer.questionId);
            values.put(INS_ID, answer.instrumentId);
            values.put(ANS, answer.answer);
            long i = db.insertWithOnConflict(TABLE_MUL_ANSWER, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        }
        db.close();
    }*/
    /*
    public void addInstrument(List<Instrument> instruments, String tableName){
        SQLiteDatabase db = this.getWritableDatabase();
        for (Instrument instrument : instruments) {
            ContentValues values = new ContentValues();
            values.put(ID, instrument.ID);
            values.put(NAME, instrument.name);
            values.put(ORDER, instrument.order);
            values.put(PARENT, instrument.parentId);
            values.put(CH_NAME, instrument.chName);
            values.put(CH_CONTENT, instrument.chContent);
            values.put(CONTENT, instrument.content);
            long i = db.insertWithOnConflict(tableName, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        }
        db.close();
    }

    public void addQuestion(List<Question> questions){
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        for (Question question : questions) {
            ContentValues values = new ContentValues();
            values.put(INS_ID, question.istrId);
            values.put(TYPE, question.type);
            values.put(ID, question.ID);
            values.put(NAME, question.name);
            values.put(PARENT, question.pid);
            values.put(CH_NAME, question.chName);
            long i = db.insertWithOnConflict(TABLE_QUESTION, null, values, SQLiteDatabase.CONFLICT_REPLACE);
            //Log.d("questionSQL",i+"");
            if (question.choices!=null) {
                for (Choice choice : question.choices.values()) {
                    //Log.d("Choices",choice.chContent);
                    ContentValues valuesChoice = new ContentValues();
                    valuesChoice.put(CODE_ID, choice.codeId);
                    valuesChoice.put(NAME, choice.content);
                    valuesChoice.put(ID, choice.id);
                    valuesChoice.put(TRIGGER, choice.triggersub);
                    valuesChoice.put(Q_ID, choice.questionId);
                    valuesChoice.put(CH_NAME, choice.chContent);
                    long j = db.insertWithOnConflict(TABLE_CHOICE, null, valuesChoice, SQLiteDatabase.CONFLICT_REPLACE);
                    //Log.d("answerSQL",j+"");
                }
            }
            //Log.d("answerSQL",i+"");
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }*/
    /*
    public void addInterviewInstance(InterviewInstance interviewInstance){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        //Log.d("percentageDB",interviewInstance.percentage);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID, interviewInstance.getInterviewId());
        values.put(DATE, dateFormat.format(interviewInstance.tranDate));
        values.put(STATUS, interviewInstance.status);
        values.put(NAME, interviewInstance.name);
        if (interviewInstance.getPt()!=null) {
            values.put(P_ID, interviewInstance.getPt().getId());
        }
        values.put(UID,interviewInstance.staffId);
        values.put(PERCENTAGE,interviewInstance.percentage);
        long i = db.insertWithOnConflict(TABLE_II, null, values,SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    public void addInstrumentInstance(InstrumentInstance instrumentInstance) throws SQLiteConstraintException {
        SQLiteDatabase db = this.getWritableDatabase();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ContentValues values = new ContentValues();
        values.put(P_ID, instrumentInstance.getParticipantId());
        values.put(I_ID, instrumentInstance.getInterviewId());
        values.put(UID, instrumentInstance.getTestStaffId());
        values.put(INS_ID, instrumentInstance.getInstrumentId());
        values.put(FINISHED,instrumentInstance.getFinished());
        long i = db.insertWithOnConflict(TABLE_INSTRUMENT_INSTANCE, null, values,SQLiteDatabase.CONFLICT_IGNORE);
        if (i==-1){
            ContentValues update = new ContentValues();
            update.put(FINISHED,instrumentInstance.getFinished());
            update.put(MODTIME, dateFormat.format(instrumentInstance.getModtime()));
            db.update(TABLE_INSTRUMENT_INSTANCE, update,
                    P_ID + " =? and " + I_ID + " =? and " + UID + " =? and "
                            + INS_ID + " =?",
                    new String[]{instrumentInstance.getParticipantId()+"",
                    String.valueOf(instrumentInstance.getInterviewId()),
                    instrumentInstance.getTestStaffId(),
                    String.valueOf(instrumentInstance.getInstrumentId())});
        }
        db.close();
    }

    public String getAnswerId(String pid, int iid, int qid) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_ANSWER, new String[] {ANS}, P_ID + "=? and " + I_ID + "=? and "
                + Q_ID + "=?",new String[]{String.valueOf(pid),String.valueOf(iid),String.valueOf(qid)},null,null,null);
        if (cursor != null)
            cursor.moveToFirst();
        if (cursor.getCount()<=0){
            return "";
        }
        String answer = cursor.getString(0);
        db.close();
        return answer;
    }*/
    /*
    public List<Answer> getAnswers(String pid, int iid, boolean isSingle) {
        String tableName = TABLE_ANSWER;
        if (isSingle==false){
            tableName = TABLE_MUL_ANSWER;
        }
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(tableName, new String[] {P_ID,I_ID,INS_ID,Q_ID,ANS}, P_ID + "=? and " + I_ID + "=?"
                ,new String[]{String.valueOf(pid),String.valueOf(iid)},null,null,null);
        if (cursor != null)
            cursor.moveToFirst();
        if (cursor.getCount()<=0){
            return null;
        }
        List<Answer> answers = new ArrayList<>();
        //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            Answer answer = null;
            answer = new Answer(cursor.getString(0),cursor.getInt(1),cursor.getInt(2),cursor.getInt(3),cursor.getString(4),null);
            answers.add(answer);
        }
        db.close();
        return answers;
    }

    public List<Answer> getMultipleAnswers(String pid, int iid, long qid) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_MUL_ANSWER, new String[] {P_ID,I_ID,INS_ID,Q_ID,ANS}, P_ID + "=? and " + I_ID + "=? and " + Q_ID + "=?"
                ,new String[]{String.valueOf(pid),String.valueOf(iid),String.valueOf(qid)},null,null,null);
        if (cursor != null)
            cursor.moveToFirst();
        if (cursor.getCount()<=0){
            return null;
        }
        List<Answer> answers = new ArrayList<>();
        //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            Answer answer = null;
            //answer = new Answer(cursor.getString(0),cursor.getInt(1),cursor.getInt(2),cursor.getInt(3),cursor.getString(4),null);
            answers.add(answer);
        }
        db.close();
        return answers;
    }

    public List<InstrumentInstance> getInstrumentInstance(String pid, long instrumentId, int iid) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_INSTRUMENT_INSTANCE, new String[] {INS_ID,I_ID,P_ID,UID,FINISHED}, P_ID + "=? and " + INS_ID + "=? and " + I_ID + "=?"
                ,new String[]{String.valueOf(pid),
                              String.valueOf(instrumentId),
                              String.valueOf(iid)},null,null,null);
        if (cursor != null)
            cursor.moveToFirst();
        if (cursor.getCount()<=0){
            return null;
        }
        List<InstrumentInstance> instrumentInstances = new ArrayList<>();
        //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            InstrumentInstance instrumentInstance = new InstrumentInstance(cursor.getString(3),cursor.getInt(0),cursor.getInt(1),cursor.getString(2),cursor.getInt(4));
            instrumentInstances.add(instrumentInstance);
        }
        db.close();
        return instrumentInstances;
    }

    public List<InterviewInstance> getInterviewInstance() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_II, new String[] {ID,DATE,STATUS,NAME,P_ID,UID,PERCENTAGE},null,null,null,null,P_ID);
        if (cursor != null)
            cursor.moveToFirst();
        if (cursor.getCount()<=0){
            return null;
        }
        List<InterviewInstance> instances = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            InterviewInstance interviewInstance = null;
            try {
                interviewInstance = new InterviewInstance(cursor.getInt(0),dateFormat.parse(cursor.getString(1)),
                        InterviewStatus.valueOf(cursor.getString(2)),cursor.getString(3),null,cursor.getString(5),cursor.getString(6));
                //interviewInstance.setPt(getParticipant(cursor.getString(4)));
                //Log.d("in",interviewInstance.getPt().getFirstName());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            instances.add(interviewInstance);
        }
        db.close();
        return instances;
    }


    public List<Instrument> getAllInstruments(int iid) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_INSTRUMENT, new String[] {ID,NAME,ORDER,PARENT,CH_NAME,CONTENT,CH_CONTENT},PARENT+ "=?"
                ,new String[]{String.valueOf(iid)},null,null,ORDER);
        if (cursor != null)
            cursor.moveToFirst();
        if (cursor.getCount()<=0){
            return null;
        }
        List<Instrument> instances = new ArrayList<>();
        Instrument verifyInstrument = new Instrument(-1,"Verify Participant Info", "Chinese Name for Verify", -1, -1, null, null);
        instances.add(0,verifyInstrument);
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            Instrument instance = new Instrument(cursor.getInt(0),cursor.getString(1),cursor.getString(4),cursor.getInt(2),cursor.getInt(3),cursor.getString(5),cursor.getString(6));
            instances.add(instance);
        }
        db.close();
        return instances;
    }

    public List<Question> getQuestion(int instrumentId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_QUESTION, new String[] {ID,NAME,TYPE,INS_ID,PARENT,CH_NAME},INS_ID + "=?"
                ,new String[]{String.valueOf(instrumentId)},null,null,ID);
        if (cursor != null)
            cursor.moveToFirst();
        if (cursor.getCount()<=0){
            return null;
        }
        List<Question> instances = new ArrayList<>();
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            Question instance = new Question(cursor.getInt(0),cursor.getString(1),cursor.getInt(2),cursor.getInt(3),cursor.getInt(4),cursor.getString(5));
            List<Choice> choices = getChoice(db,instance.ID);
            if (choices!=null) {
                for (Choice choice : choices) {
                    instance.choices.put(choice.getId(), choice);
                }
            }
            instances.add(instance);
        }
        db.close();
        return instances;
    }

    public List<Choice> getChoice(SQLiteDatabase db, int questionId) {
        Cursor cursor = db.query(TABLE_CHOICE, new String[] {ID,CODE_ID,NAME,TRIGGER,Q_ID,CH_NAME},Q_ID + "=?"
                ,new String[]{String.valueOf(questionId)},null,null,null);
        if (cursor != null)
            cursor.moveToFirst();
        if (cursor.getCount()<=0){
            return null;
        }
        List<Choice> instances = new ArrayList<>();
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            Choice instance = new Choice(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getInt(3),cursor.getInt(4),cursor.getString(5));
            instances.add(instance);
        }
        return instances;
    }
    public String[] getInstrumentMeta(final int id){
        final String[] res = new String[2];
        SQLiteDatabase db = this.getReadableDatabase();
        final Cursor cursor = db.query(TABLE_INSTRUMENT, new String[] {NAME, CH_NAME, CONTENT, CH_CONTENT},ID + "=?"
                ,new String[]{String.valueOf(id)},null,null,null);
        if (cursor != null)
            cursor.moveToFirst();
        if (cursor.getCount()<=0 && id!=-1){
            return res;
        }
        final TranslateBase translateBase = new TranslateBase() {
            @Override
            protected String getCh() {
                if (id==-1){
                    res[0] = "CHN Verify Participant Info";
                }
                else {
                    res[1] = cursor.getString(3);
                    res[0] = cursor.getString(1);
                }
                return res[0];
            }
            @Override
            protected String getEn() {
                if (id==-1){
                    res[0] = "Verify Participant Info";
                }else {
                    res[1] = cursor.getString(2);
                    res[0] = cursor.getString(0);
                }
                return res[0];
            }
            @Override
            protected String getTr() {
                return cursor.getString(1);
            }
            @Override
            public String toString() {
                return super.toString();
            }
        };
        translateBase.toString();
        db.close();
        return res;
    }

    public int removeInterviewInstance(int id, String pid){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_II, ID +"= ? and "+P_ID+"=?",new String[]{String.valueOf(id),String.valueOf(pid)});
        int i = db.delete(TABLE_INSTRUMENT_INSTANCE, I_ID +"= ? and "+P_ID+"=?",new String[]{String.valueOf(id),String.valueOf(pid)});
        db.close();
        return i;
    }

    public void removeAnswer(int iid, String pid){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MUL_ANSWER,P_ID + "=? and " + I_ID + "=?"
                ,new String[]{String.valueOf(pid),String.valueOf(iid)});
        db.delete(TABLE_ANSWER,P_ID + "=? and " + I_ID + "=?"
                ,new String[]{String.valueOf(pid),String.valueOf(iid)});
        db.close();
    }


    public ArrayList<Cursor> getData(String Query){
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String[] columns = new String[] { "message" };
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2= new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);

        try{
            String maxQuery = Query ;
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(maxQuery, null);

            //add value to cursor2
            Cursor2.addRow(new Object[] { "Success" });

            alc.set(1,Cursor2);
            if (null != c && c.getCount() > 0) {

                alc.set(0,c);
                c.moveToFirst();

                return alc ;
            }
            return alc;
        } catch(SQLException sqlEx){
            Log.d("printing exception", sqlEx.getMessage());
            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+sqlEx.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        } catch(Exception ex){
            Log.d("printing exception", ex.getMessage());

            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+ex.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        }
    }

    public void addParticipant(Participant pt) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        ContentValues values = new ContentValues();
        values.put(ID, pt.getId());
        values.put(PHONE, pt.getPhone());
        values.put(DOB, dateFormat.format(pt.getDob()));
        values.put(FNAME, pt.getFirstName());
        values.put(LNAME, pt.getLastName());
        values.put(LANG_CAN, pt.getLangCan());
        values.put(LANG_PREF, pt.getLangPref());
        values.put(HOME_ID, pt.getHome().getId());
        long i = db.insertWithOnConflict(TABLE_PARTICIPANT, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        //Log.d("questionSQL",i+"");
        Home home = pt.getHome();
        //Log.d("Choices",choice.chContent);
        ContentValues valuesChoice = new ContentValues();
        valuesChoice.put(ID, home.getId());
        valuesChoice.put(STREET, home.getStreet());
        valuesChoice.put(STATE, home.getState());
        valuesChoice.put(CITY, home.getCity());
        valuesChoice.put(ZIP, home.getZip());
        long j = db.insertWithOnConflict(TABLE_HOME, null, valuesChoice, SQLiteDatabase.CONFLICT_REPLACE);
        //Log.d("answerSQL",j+"");
        //Log.d("answerSQL",i+"");
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }*/
    /*
    public Participant getParticipant(String pid) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PARTICIPANT, new String[] {ID,PHONE,DOB,FNAME,LNAME,LANG_PREF,LANG_CAN,HOME_ID},ID + "=?"
                ,new String[]{pid},null,null,null);
        if (cursor != null)
            cursor.moveToFirst();
        if (cursor.getCount()<=0){
            return null;
        }
        List<Participant> instances = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            Participant participant = null;
            try {
                participant = new Participant (cursor.getString(0),cursor.getString(1),dateFormat.parse(cursor.getString(2)),cursor.getString(3),
                        cursor.getString(4),cursor.getString(5),cursor.getString(6),null);
                Cursor cursorH = db.query(TABLE_HOME, new String[] {ID,STREET,STATE,CITY,ZIP},ID + "=?"
                        ,new String[]{String.valueOf(cursor.getInt(7))},null,null,null);
                if (cursorH != null)
                    cursorH.moveToFirst();
                if (cursorH.getCount()>0){
                    Home home = new Home(cursorH.getInt(0),cursorH.getString(1),cursorH.getString(2),cursorH.getString(3),cursorH.getString(4));
                    participant.setHome(home);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            instances.add(participant);
        }
        db.close();
        return instances.get(0);

    }*/
