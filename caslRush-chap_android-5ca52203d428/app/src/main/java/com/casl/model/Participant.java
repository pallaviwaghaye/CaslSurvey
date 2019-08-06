package com.casl.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.gson.annotations.Expose;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.ToOne;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Transient;

/**
 * Created by kang on 7/14/2017.
 */
@Entity
public class Participant implements Parcelable {
    @Id
    @Expose
    private String id;
    @Expose
    private String cellPhone;
    @Expose
    private String workPhone;
    private Date dob;
    private String firstName;
    private String lastName;
    private String chineseName;
    private long homeid;
    private boolean isMissingSSN;
    @Expose
    private String langSpeakPre;
    @Expose
    private String langReadPre;
    @Expose
    private String langWritePre;
    @Expose
    private String langSpeakCan;
    @Expose
    private String langReadCan;
    @Expose
    private String langWriteCan;
    @ToOne(joinProperty = "homeid")
    @Expose
    private Home home;
    @Expose
    private String email;
    @Expose
    private String bornCountry;
    @Expose
    private String gender;
    private String recruitment;
    private String group;
    @Expose
    private String comment;

    

    @Generated(hash = 1200154759)
    public Participant() {
    }



    @Override
    public String toString() {
        return "("+getId()+") "+getName();
    }

    public String getName(){
        return this.firstName + " " +this.lastName;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public Date getDob() {
        return this.dob;
    }

    public String getDobStr(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd");
        if (this.dob!=null) {
            String dobStr = simpleDateFormat.format(this.dob);
            return dobStr;
        }
        return null;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public long getHomeid() {
        return this.homeid;
    }

    public void setHomeid(long homeid) {
        this.homeid = homeid;
    }

    public Home getMemHome(){
        return this.home;
    }






    public boolean getIsMissingSSN() {
        return this.isMissingSSN;
    }

    public void setIsMissingSSN(boolean isMissingSSN) {
        this.isMissingSSN = isMissingSSN;
    }

    public String getLangSpeakPre() {
        return this.langSpeakPre;
    }

    public void setLangSpeakPre(String langSpeakPre) {
        this.langSpeakPre = langSpeakPre;
    }

    public String getLangReadPre() {
        return this.langReadPre;
    }

    public void setLangReadPre(String langReadPre) {
        this.langReadPre = langReadPre;
    }

    public String getLangWritePre() {
        return this.langWritePre;
    }

    public void setLangWritePre(String langWritePre) {
        this.langWritePre = langWritePre;
    }

    public String getLangSpeakCan() {
        return this.langSpeakCan;
    }

    public void setLangSpeakCan(String langSpeakCan) {
        this.langSpeakCan = langSpeakCan;
    }

    public String getLangReadCan() {
        return this.langReadCan;
    }

    public void setLangReadCan(String langReadCan) {
        this.langReadCan = langReadCan;
    }

    public String getLangWriteCan() {
        return this.langWriteCan;
    }

    public void setLangWriteCan(String langWriteCan) {
        this.langWriteCan = langWriteCan;
    }


    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBornCountry() {
        return this.bornCountry;
    }

    public void setBornCountry(String bornCountry) {
        this.bornCountry = bornCountry;
    }

    public String getRecruitment() {
        return this.recruitment;
    }

    public void setRecruitment(String recruitment) {
        this.recruitment = recruitment;
    }

    public String getGroup() {
        return this.group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


    public String getCellPhone() {
        return this.cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    /** To-one relationship, resolved on first access. */
    @Keep
    public Home getHome() {
        long __key = this.homeid;
        if (home__resolvedKey == null || !home__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                return this.home;
                //throw new DaoException("Entity is detached from DAO context");
            }
            HomeDao targetDao = daoSession.getHomeDao();
            Home homeNew = targetDao.load(__key);
            synchronized (this) {
                home = homeNew;
                home__resolvedKey = __key;
            }
        }
        return home;
    }

    public String getChineseName() {
        return this.chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.cellPhone);
        dest.writeLong(this.dob != null ? this.dob.getTime() : -1);
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
        dest.writeString(this.chineseName);
        dest.writeLong(this.homeid);
        dest.writeByte(this.isMissingSSN ? (byte) 1 : (byte) 0);
        dest.writeString(this.langSpeakPre);
        dest.writeString(this.langReadPre);
        dest.writeString(this.langWritePre);
        dest.writeString(this.langSpeakCan);
        dest.writeString(this.langReadCan);
        dest.writeString(this.langWriteCan);
        dest.writeParcelable(this.home, flags);
        dest.writeString(this.email);
        dest.writeString(this.bornCountry);
        dest.writeString(this.recruitment);
        dest.writeString(this.group);
        dest.writeString(this.comment);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 699991406)
    public void setHome(@NotNull Home home) {
        if (home == null) {
            throw new DaoException("To-one property 'homeid' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.home = home;
            homeid = home.getId();
            home__resolvedKey = homeid;
        }
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    protected Participant(Parcel in) {
        this.id = in.readString();
        this.cellPhone = in.readString();
        long tmpDob = in.readLong();
        this.dob = tmpDob == -1 ? null : new Date(tmpDob);
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.chineseName = in.readString();
        this.homeid = in.readLong();
        this.isMissingSSN = in.readByte() != 0;
        this.langSpeakPre = in.readString();
        this.langReadPre = in.readString();
        this.langWritePre = in.readString();
        this.langSpeakCan = in.readString();
        this.langReadCan = in.readString();
        this.langWriteCan = in.readString();
        this.home = in.readParcelable(Home.class.getClassLoader());
        this.email = in.readString();
        this.bornCountry = in.readString();
        this.recruitment = in.readString();
        this.group = in.readString();
        this.comment = in.readString();
    }



    @Generated(hash = 1816679806)
    public Participant(String id, String cellPhone, String workPhone, Date dob, String firstName, String lastName,
            String chineseName, long homeid, boolean isMissingSSN, String langSpeakPre, String langReadPre,
            String langWritePre, String langSpeakCan, String langReadCan, String langWriteCan, String email,
            String bornCountry, String gender, String recruitment, String group, String comment) {
        this.id = id;
        this.cellPhone = cellPhone;
        this.workPhone = workPhone;
        this.dob = dob;
        this.firstName = firstName;
        this.lastName = lastName;
        this.chineseName = chineseName;
        this.homeid = homeid;
        this.isMissingSSN = isMissingSSN;
        this.langSpeakPre = langSpeakPre;
        this.langReadPre = langReadPre;
        this.langWritePre = langWritePre;
        this.langSpeakCan = langSpeakCan;
        this.langReadCan = langReadCan;
        this.langWriteCan = langWriteCan;
        this.email = email;
        this.bornCountry = bornCountry;
        this.gender = gender;
        this.recruitment = recruitment;
        this.group = group;
        this.comment = comment;
    }

    public static final Creator<Participant> CREATOR = new Creator<Participant>() {
        @Override
        public Participant createFromParcel(Parcel source) {
            return new Participant(source);
        }

        @Override
        public Participant[] newArray(int size) {
            return new Participant[size];
        }
    };
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1537769566)
    private transient ParticipantDao myDao;
    @Generated(hash = 1062067546)
    private transient Long home__resolvedKey;

    public boolean readLanguage(int lang){
        if (langReadCan==null){return false;}
        List<String> langReadCanList = new ArrayList<String>(Arrays.asList(langReadCan.split(",")));
        return langReadCanList.contains(getLangLabel(lang));
    }
    public boolean speakLanguage(int lang){
        if (langSpeakCan==null){
            return false;
        }
        List<String> langSpeakCanList = new ArrayList<String>(Arrays.asList(langSpeakCan.split(",")));
        return langSpeakCanList.contains(getLangLabel(lang));
    }
    public boolean writeLanguage(int lang){
        if (langWriteCan==null){return false;}
        List<String> langWriteCanList = new ArrayList<String>(Arrays.asList(langWriteCan.split(",")));
        Log.d("language",langWriteCan);
        boolean res = langWriteCanList.contains(getLangLabel(lang));
        return res;
    }
    private String getLangLabel(int i){
        switch (i){
            case 0:
                return "en";
            case 1:
                return "pt";
            case 2:
                return "gd";
            case 3:
                return "cn";
            case 4:
                return "ts";
            default:
                return "tw";

        }
    }



    public String getGender() {
        return this.gender;
    }



    public void setGender(String gender) {
        this.gender = gender;
    }



    public String getWorkPhone() {
        return this.workPhone;
    }



    public void setWorkPhone(String workPhone) {
        this.workPhone = workPhone;
    }



    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1996592993)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getParticipantDao() : null;
    }

}
