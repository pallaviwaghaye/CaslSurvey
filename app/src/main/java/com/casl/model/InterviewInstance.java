package com.casl.model;
import com.google.gson.annotations.Expose;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Transient;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

/**
 * Created by Staff73 on 6/26/2017.
 */
@Entity(indexes = {@Index(value = "participantId, interviewId, staffId", unique = true)})
public class InterviewInstance {
    @Id(autoincrement = true)
    private Long id;
    @Expose
    private int interviewId;
    @Expose
    private String tranDate;
    @Expose
    private String staffId;
    @Transient
    private InterviewStatus interviewStatus;
    @Expose
    private String status;
    @Expose
    private float percentage;
    private String name;
    @Expose
    private String participantId;
    @ToOne(joinProperty = "participantId")
    private Participant pt;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1344817153)
    private transient InterviewInstanceDao myDao;
    @Generated(hash = 847644031)
    private transient String pt__resolvedKey;


    public InterviewInstance(Long id, int interviewId, String date, InterviewStatus interviewStatus, String istr, Participant pt, String staffId, float percentage) {
        this.interviewId = interviewId;
        this.tranDate = date;
        this.staffId = staffId;
        this.interviewStatus = interviewStatus;
        this.status = interviewStatus.name();
        this.percentage = percentage;
        this.name = istr;
        this.pt = pt;
        this.participantId = pt.getId();
    }



    @Generated(hash = 1252673440)
    public InterviewInstance() {
    }



    @Generated(hash = 1861408117)
    public InterviewInstance(Long id, int interviewId, String tranDate, String staffId, String status, float percentage, String name, String participantId) {
        this.id = id;
        this.interviewId = interviewId;
        this.tranDate = tranDate;
        this.staffId = staffId;
        this.status = status;
        this.percentage = percentage;
        this.name = name;
        this.participantId = participantId;
    }



    @Override
    public String toString() {
        return name+"\nParticipant ID:"+pt.getId()+"\n"+interviewStatus+"\n"+ tranDate;
    }

    public InterviewStatus getInterviewStatus() {
        return InterviewStatus.valueOf(status);
    }

    public void setInterviewStatus(InterviewStatus interviewStatus) {
        this.interviewStatus = interviewStatus;
        this.status = interviewStatus.name();
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public float getPercentage() {
        return percentage;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }

    public int getInterviewId() {
        return this.interviewId;
    }

    public void setInterviewId(int interviewId) {
        this.interviewId = interviewId;
    }

    public String getTranDate() {
        return this.tranDate;
    }

    public void setTranDate(String tranDate) {
        this.tranDate = tranDate;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
        this.interviewStatus = InterviewStatus.valueOf(status);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParticipantId() {
        return this.participantId;
    }

    public void setParticipantId(String participantId) {
        this.participantId = participantId;
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 740368275)
    public Participant getPt() {
        String __key = this.participantId;
        if (pt__resolvedKey == null || pt__resolvedKey != __key) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ParticipantDao targetDao = daoSession.getParticipantDao();
            Participant ptNew = targetDao.load(__key);
            synchronized (this) {
                pt = ptNew;
                pt__resolvedKey = __key;
            }
        }
        return pt;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 227767548)
    public void setPt(Participant pt) {
        synchronized (this) {
            this.pt = pt;
            participantId = pt == null ? null : pt.getId();
            pt__resolvedKey = participantId;
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

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 554499892)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getInterviewInstanceDao() : null;
    }
}
