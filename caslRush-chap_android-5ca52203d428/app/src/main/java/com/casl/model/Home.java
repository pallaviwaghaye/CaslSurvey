package com.casl.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by kang on 7/26/2017.
 */
@Entity
public class Home implements Parcelable {
    @Id
    @Expose
    private long id;
    @Expose
    private String streetNumber;
    @Expose
    private String streetDirection;
    @Expose
    private String streetName;
    @Expose
    private String streetType;
    @Expose
    private String apt;
    @Expose
    private String city;
    @Expose
    private String state;
    @Expose
    private String zip;
    @Expose
    private String homePhone;


    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return this.zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getStreetNumber() {
        return this.streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getStreetDirection() {
        return this.streetDirection;
    }

    public void setStreetDirection(String streetDirection) {
        this.streetDirection = streetDirection;
    }

    public String getStreetName() {
        return this.streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getStreetType() {
        return this.streetType;
    }

    public void setStreetType(String streetType) {
        this.streetType = streetType;
    }

    public String getApt() {
        return this.apt;
    }

    public void setApt(String apt) {
        this.apt = apt;
    }

    @Generated(hash = 838501358)
    public Home(long id, String streetNumber, String streetDirection, String streetName,
            String streetType, String apt, String city, String state, String zip,
            String homePhone) {
        this.id = id;
        this.streetNumber = streetNumber;
        this.streetDirection = streetDirection;
        this.streetName = streetName;
        this.streetType = streetType;
        this.apt = apt;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.homePhone = homePhone;
    }

    @Generated(hash = 858147737)
    public Home() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.streetNumber);
        dest.writeString(this.streetDirection);
        dest.writeString(this.streetName);
        dest.writeString(this.streetType);
        dest.writeString(this.apt);
        dest.writeString(this.city);
        dest.writeString(this.state);
        dest.writeString(this.zip);
        dest.writeString(this.homePhone);
    }

    protected Home(Parcel in) {
        this.id = in.readLong();
        this.streetNumber = in.readString();
        this.streetDirection = in.readString();
        this.streetName = in.readString();
        this.streetType = in.readString();
        this.apt = in.readString();
        this.city = in.readString();
        this.state = in.readString();
        this.zip = in.readString();
        this.homePhone = in.readString();
    }

    public static final Creator<Home> CREATOR = new Creator<Home>() {
        @Override
        public Home createFromParcel(Parcel source) {
            return new Home(source);
        }

        @Override
        public Home[] newArray(int size) {
            return new Home[size];
        }
    };

    public String getAddress(){
        return denull(getStreetNumber())+" "+denull(getStreetDirection())+" "+denull(getStreetName())
                +" "+denull(getStreetType())+" "+denull(getApt());
    }

    private static String denull(String str) {
        return str == null ? "" : str;
    }
}
