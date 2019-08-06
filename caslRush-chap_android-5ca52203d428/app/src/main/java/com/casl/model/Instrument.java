package com.casl.model;

import com.zqc.opencc.android.lib.ChineseConverter;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

/**
 * Created by Staff73 on 6/29/2017.
 */
@Entity
public class Instrument extends  TranslateBase{
    @Id
    private long id;
    private String name;
    private String chName;
    @Property(nameInDb = "content")
    private String enContent;
    private String chContent;

    @Generated(hash = 1355234291)
    public Instrument() {
    }



    @Generated(hash = 1649554028)
    public Instrument(long id, String name, String chName, String enContent,
            String chContent) {
        this.id = id;
        this.name = name;
        this.chName = chName;
        this.enContent = enContent;
        this.chContent = chContent;
    }

    

    @Override
    protected String getCh() {
        return chName;
    }

    @Override
    protected String getEn() {
        return name;
    }


    protected String getChContent() {
        return chContent;
    }

    protected String getEnContent() {
        return enContent;
    }

    public String getContent(){
        if (getlocale().equals("tr")){
            if (getCh()==null&&getCh().length()==0){
                return getEnContent();
            }
            return getChContent();
        }
        else if (getlocale().equals("zh")){
            if (getCh()==null&&getCh().length()==0){
                return getEnContent();
            }
            return ChineseConverter.convert(getChContent(),currentConversionType,context);
        }
        else{
            return getEnContent();
        }
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
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

    public void setEnContent(String enContent) {
        this.enContent = enContent;
    }

    public void setChContent(String chContent) {
        this.chContent = chContent;
    }
}
