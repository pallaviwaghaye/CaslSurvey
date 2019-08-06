package com.casl.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Index;

/**
 * Created by kang on 7/27/2017.
 */
@Entity(indexes = {@Index(value = "interviewId, instrumentId", unique = true)})
public class Form {
    private int instrumentorder;
    private int interviewId;
    private long instrumentId;
    @Generated(hash = 691766543)
    public Form(int instrumentorder, int interviewId, long instrumentId) {
        this.instrumentorder = instrumentorder;
        this.interviewId = interviewId;
        this.instrumentId = instrumentId;
    }
    @Generated(hash = 535210737)
    public Form() {
    }
    public int getInstrumentorder() {
        return this.instrumentorder;
    }
    public void setInstrumentorder(int instrumentorder) {
        this.instrumentorder = instrumentorder;
    }
    public int getInterviewId() {
        return this.interviewId;
    }
    public void setInterviewId(int interviewId) {
        this.interviewId = interviewId;
    }
    public long getInstrumentId() {
        return this.instrumentId;
    }
    public void setInstrumentId(long instrumentId) {
        this.instrumentId = instrumentId;
    }
}
