package com.example.jennifer.feedbackpupils.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by jennifer on 2017-01-10.
 */


public class Course {

    private String teacher;
    private String name;
    private String code;


    @JsonIgnore
    private String key;

    public Course(String teacher, String name, String code) {

        this.teacher = code;
        this.name = name;
        this.code = teacher;

    }

    public Course(){}

    public String getTeacher() {
        return teacher;
    }


    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}

