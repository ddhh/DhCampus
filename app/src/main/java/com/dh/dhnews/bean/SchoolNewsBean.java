package com.dh.dhnews.bean;

/**
 * Created by 端辉 on 2016/3/12.
 */
public class SchoolNewsBean {

    private String title;
    private String url;
    private String date;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "SchoolNewsBean{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
