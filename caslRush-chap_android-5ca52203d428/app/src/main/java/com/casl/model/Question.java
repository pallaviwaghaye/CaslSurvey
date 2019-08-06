package com.casl.model;

import android.text.Editable;
import android.text.TextWatcher;
import android.transition.Visibility;
import android.util.Log;
import android.view.View;

import com.zqc.opencc.android.lib.ChineseConverter;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.ToMany;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

/**
 * Created by Staff73 on 6/12/2017.
 */
@Entity
public class Question extends TranslateBase{
    @Id
    private long id;
    private int istrId;
    private int pid;
    private String name;
    private String chName;
    private int type;
    @ToMany(referencedJoinProperty = "questionId")
    private List<Choice> choiceList;

    private transient String comment="";
    private transient String answer;
    private transient Set<String> checkedMultiple;
    private transient HashMap<Long, Choice> choices = new LinkedHashMap<>();
    private transient int visible = View.GONE;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 891254763)
    private transient QuestionDao myDao;
    private String help;
    private String chHelp;
    private int hasComment;

    @Generated(hash = 1868476517)
    public Question() {
    }





    @Keep
    public Question(long id, int istrId, int pid, String name, String chName, int type, String help, String chHelp, int hasComment) {
        this.id = id;
        this.istrId = istrId;
        this.pid = pid;
        this.name = name;
        this.chName = chName;
        this.type = type;
        this.help = help;
        this.chHelp = chHelp;
        this.hasComment = hasComment;
        if (pid==0){
            setVisible(View.VISIBLE);
        }
    }





    @Override
    protected String getCh() {
        return chName;
    }
    @Override
    protected String getEn() {
        return name;
    }

    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public int getIstrId() {
        return this.istrId;
    }
    public void setIstrId(int istrId) {
        this.istrId = istrId;
    }
    public int getPid() {
        return this.pid;
    }
    public void setPid(int pid) {
        this.pid = pid;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getChName() {
        return this.chName;
    }
    public void setChName(String chName) {
        this.chName = chName;
    }
    public int getType() {
        return this.type;
    }
    public void setType(int type) {
        this.type = type;
    }

    public Set<String> getCheckedMultiple() {
        if (checkedMultiple==null){
            this.checkedMultiple = new HashSet<>();
        }
        return checkedMultiple;
    }

    public void setCheckedMultiple(HashSet<String> checkedMultiple) {
        this.checkedMultiple = checkedMultiple;
    }

    public HashMap<Long, Choice> getChoices() {
        if (choiceList==null || choices.size()==choiceList.size()) {
            return choices;
        }
        choices.clear();
        for (Choice choice : choiceList){
            if (choice.getCodeId().equals(88)||choice.getCodeId().equals(99)){
                choices.put(Long.parseLong(choice.getCodeId()),choice);
            }
            else {
                choices.put(choice.getId(), choice);
            }
        }
        return choices;
    }

    public void setChoices(HashMap<Long, Choice> choices) {
        this.choices = choices;
    }

    public List<Choice> getMemChoiceList() {
        if (choiceList==null){
            this.choiceList = new ArrayList<>();
        }
        return choiceList;
    }

    public String getAnswer() {
        if (type==0) {
            Choice choice = this.getCheckedChoice();
            if (choice != null && choice.getCodeId() != null && (choice.getCodeId().equals("88") || choice.getCodeId().equals("99"))) {
                return choice.getCodeId();
            }
        }
        return this.answer;
    }



    public void setAnswer(String answer) {
        this.answer = answer;
    }



    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 190687298)
    public synchronized void resetChoiceList() {
        choiceList = null;
    }
    



    public Choice getCheckedChoice() {
        if (answer!=null && answer.length()>0){
            try {
                Long choiceId = Long.parseLong(answer);
                return getChoices().get(choiceId);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }

    public boolean isAnswered() {
        return (answer!=null&&answer.length()>0)||(getCheckedMultiple().size()>0);
    }


    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 816462840)
    public List<Choice> getChoiceList() {
        if (choiceList == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ChoiceDao targetDao = daoSession.getChoiceDao();
            List<Choice> choiceListNew = targetDao._queryQuestion_ChoiceList(id);
            synchronized (this) {
                if (choiceList == null) {
                    choiceList = choiceListNew;
                }
            }
        }
        return choiceList;
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


    public String getHelpTrans(){
        if (getlocale().equals("tr")){
            if (getCh()==null&&getCh().length()==0){
                return getHelp();
            }
            return getChHelp();
        }
        else if (getlocale().equals("zh")){
            if (getCh()==null&&getCh().length()==0){
                return getHelp();
            }
            return ChineseConverter.convert(getChHelp(),currentConversionType,context);
        }
        else{
            return getHelp();
        }
    }


    public void setHelp(String help) {
        this.help = help;
    }


    public String getChHelp() {
        return this.chHelp;
    }


    public void setChHelp(String chHelp) {
        this.chHelp = chHelp;
    }


    public String getHelp() {
        return this.help;
    }

    


    public int getHasComment() {
        return this.hasComment;
    }





    public void setHasComment(int hasComment) {
        this.hasComment = hasComment;
    }





    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }





    public int getVisible() {
        return visible;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }





    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 754833738)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getQuestionDao() : null;
    }
}
