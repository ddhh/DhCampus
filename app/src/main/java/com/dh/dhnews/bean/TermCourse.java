package com.dh.dhnews.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 端辉 on 2016/3/15.
 */
public class TermCourse {

    private String term;
    private List<Course> courseList = new ArrayList<>();

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public List<Course> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<Course> courseList) {
        this.courseList = courseList;
    }

    @Override
    public String toString() {
        return "TermCourse{" +
                "term='" + term + '\'' +
                ", courseList=" + courseList +
                '}';
    }
}
