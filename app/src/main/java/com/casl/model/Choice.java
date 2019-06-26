package com.casl.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;

/**
 * Created by Staff73 on 6/12/2017.
 */
@Entity(indexes = {@Index(value = "content, questionId", unique = true)})
public class Choice extends TranslateBase{
    @Id(autoincrement = true)
    private Long id;
    private String codeId = null;
    private String content;
    private int triggersub;
    private long questionId;
    private String chContent;

    public Choice() {
    }


    @Generated(hash = 1255205606)
    public Choice(Long id, String codeId, String content, int triggersub,
            long questionId, String chContent) {
        this.id = id;
        this.codeId = codeId;
        this.content = content;
        this.triggersub = triggersub;
        this.questionId = questionId;
        this.chContent = chContent;
    }


    @Override
    protected String getCh() {
        return this.chContent;
    }

    @Override
    protected String getEn() {
        return content;
    }

    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getCodeId() {
        return this.codeId;
    }
    public void setCodeId(String codeId) {
        this.codeId = codeId;
    }
    public String getContent() {
        return this.content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public int getTriggersub() {
        return this.triggersub;
    }
    public void setTriggersub(int triggersub) {
        this.triggersub = triggersub;
    }
    public long getQuestionId() {
        return this.questionId;
    }
    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }
    public String getChContent() {
        return this.chContent;
    }
    public void setChContent(String chContent) {
        this.chContent = chContent;
    }

}
