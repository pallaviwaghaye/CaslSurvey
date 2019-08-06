package com.casl.model;


import org.greenrobot.greendao.annotation.Entity;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;

/**
 * Created by kang on 7/13/2017.
 */
@Entity(indexes = {@Index(value = "participantId, instrumentId, interviewId", unique = true)})
public class InstrumentInstance{
    @Id(autoincrement = true)
    Long id;
    String testStaffId;
    int instrumentId;
    int interviewId;
    String participantId;
    boolean uploaded;
    int finished;
    Date modtime;
    Date createTime;
    public InstrumentInstance() {
    }
    public InstrumentInstance(String testStaffId, int instrumentId, int interviewId,
                              String participantId, int finished) {
        this.testStaffId = testStaffId;
        this.instrumentId = instrumentId;
        this.interviewId = interviewId;
        this.participantId = participantId;
        this.finished = finished;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Generated(hash = 1453017260)
    public InstrumentInstance(Long id, String testStaffId, int instrumentId, int interviewId,
            String participantId, boolean uploaded, int finished, Date modtime,
            Date createTime) {
        this.id = id;
        this.testStaffId = testStaffId;
        this.instrumentId = instrumentId;
        this.interviewId = interviewId;
        this.participantId = participantId;
        this.uploaded = uploaded;
        this.finished = finished;
        this.modtime = modtime;
        this.createTime = createTime;
    }
    public String getTestStaffId() {
        return testStaffId;
    }

    public void setTestStaffId(String testStaffId) {
        this.testStaffId = testStaffId;
    }

    public int getInstrumentId() {
        return instrumentId;
    }

    public void setInstrumentId(int instrumentId) {
        this.instrumentId = instrumentId;
    }

    public String getParticipantId() {
        return participantId;
    }

    public void setParticipantId(String participantId) {
        this.participantId = participantId;
    }

    public int getInterviewId() {
        return interviewId;
    }

    public void setInterviewId(int interviewId) {
        this.interviewId = interviewId;
    }

    public int isFinished() {
        return finished;
    }

    public Date getModtime() {
        return modtime;
    }

    public void setModtime(Date modtime) {
        this.modtime = modtime;
    }

    public void setFinished(int finished) {
        this.finished = finished;
    }
    public int getFinished() {
        return this.finished;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public boolean isUploaded() {
        return uploaded;
    }

    public void setUploaded(boolean uploaded) {
        this.uploaded = uploaded;
    }
    public boolean getUploaded() {
        return this.uploaded;
    }
}
