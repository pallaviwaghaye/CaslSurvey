package com.casl.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Staff73 on 6/8/2017.
 */

public class Pair{
    public int ID;
    public String name;
    public Pair(){

    }
    public Pair(int ID, String name) {
        this.ID = ID;
        this.name = name;
    }

    public Pair(int ID, String name, int order, int parentId) {
        this.ID = ID;
        this.name = name;
    }

    @Override
    public String toString(){
        return this.name;
    }

}
