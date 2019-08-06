package com.casl.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.zqc.opencc.android.lib.ChineseConverter;
import com.zqc.opencc.android.lib.ConversionType;

/**
 * Created by kang on 7/12/2017.
 */

public abstract class TranslateBase {
    protected ConversionType currentConversionType = ConversionType.T2S;
    public static SharedPreferences sp;
    public static Context context;
    protected abstract String getCh();
    protected abstract String getEn();
    public String toString(){
        if (getlocale().equals("tr")){
            if (getCh()==null||getCh().length()==0){
                return getEn();
            }
            return getCh();
        }
        else if (getlocale().equals("zh")){
            if (getCh()==null||getCh().length()==0){
                return getEn();
            }
            return ChineseConverter.convert(getCh(),currentConversionType,context);
        }
        else{
            return getEn();
        }
    }

    public static String getlocale(){
        return sp.getString("Language","en");
    }
}
