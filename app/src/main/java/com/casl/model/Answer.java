package com.casl.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;

import java.util.Date;

/**
 * Created by Staff73 on 6/13/2017.
 */
@Entity(indexes = {@Index(value = "participantId, interviewId, questionId", unique = true)})
public class Answer {
    @Id(autoincrement = true)
    private Long id;
    @Expose
    private String answer = "";
    @Expose
    private String participantId;
    @Expose
    private int interviewId;
    @Expose
    private int instrumentId;
    @Expose
    private long questionId;
    @Expose
    private String comment = "";
    @Expose
    private String tranUser = "";
    @Expose
    @SerializedName("autotime")
    private Date createdtime;

    public Answer() {
    }



    @Generated(hash = 1521323665)
    public Answer(Long id, String answer, String participantId, int interviewId,
            int instrumentId, long questionId, String comment, String tranUser,
            Date createdtime) {
        this.id = id;
        this.answer = answer;
        this.participantId = participantId;
        this.interviewId = interviewId;
        this.instrumentId = instrumentId;
        this.questionId = questionId;
        this.comment = comment;
        this.tranUser = tranUser;
        this.createdtime = createdtime;
    }
    
    

    public String getParticipantId() {
        return this.participantId;
    }

    public void setParticipantId(String participantId) {
        this.participantId = participantId;
    }

    public int getInterviewId() {
        return this.interviewId;
    }

    public void setInterviewId(int interviewId) {
        this.interviewId = interviewId;
    }

    public int getInstrumentId() {
        return this.instrumentId;
    }

    public void setInstrumentId(int instrumentId) {
        this.instrumentId = instrumentId;
    }

    public long getQuestionId() {
        return this.questionId;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }

    public String getAnswer() {
        return this.answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


    public Long getId() {
        return this.id;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedtime() {
        return this.createdtime;
    }

    public void setCreatedtime(Date createdtime) {
        this.createdtime = createdtime;
    }


    public String getTranUser() {
        return this.tranUser;
    }


    public void setTranUser(String tranUser) {
        this.tranUser = tranUser;
    }
}
