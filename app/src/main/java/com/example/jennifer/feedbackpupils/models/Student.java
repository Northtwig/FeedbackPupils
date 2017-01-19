package com.example.jennifer.feedbackpupils.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Map;

/**
 * Model class for students
 * Created by matej on 2017-01-18.
 */

public class Student implements Parcelable {

    private String username;
    private Map<String, Boolean> subscribedto;

    public Student() {

    }

    public Student(String username, Map<String, Boolean> subscribedto) {
        this.username = username;
        this.subscribedto = subscribedto;
    }


    protected Student(Parcel in) {
        username = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Map<String, Boolean> getSubscribedto() {
        return subscribedto;
    }

    public void setSubscribedto(Map<String, Boolean> subscribedto) {
        this.subscribedto = subscribedto;
    }


}

