package com.dh.dhnews.bean;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 端辉 on 2016/3/12.
 */
public class News extends NewsTitle implements Serializable {

    private List<String> praContents = new ArrayList<>();
    private List<String> imgUrls = new ArrayList<>();

    public List<String> getPraContents() {
        return praContents;
    }

    public void setPraContents(List<String> praContents) {
        this.praContents = praContents;
    }

    public List<String> getImgUrls() {
        return imgUrls;
    }

    public void setImgUrls(List<String> imgUrls) {
        this.imgUrls = imgUrls;
    }

    public void addImgUrl(String imgUrl) {
        imgUrl =  "<img src=\""+imgUrl+"\"/>";
        this.getImgUrls().add(imgUrl);
    }

    public void addPraContent(String praCon) {
        praCon = "<p>"+praCon+"</p>";
        this.praContents.add(praCon);
    }
    private void setContents(){
        int position = -1;
        for (String imgUrl : this.imgUrls) {
            int temp = (int) (Math.random() * praContents.size()+1);
            while (position >= temp) {
                temp = (int) (Math.random() * praContents.size()+1);
            }
            position = temp;
            praContents.add(position, imgUrl);
        }
    }
    public String getHtml(){
        setContents();
        String html = "<html><body>";
        for(String p:this.praContents){
            html += p;
        }
        html +="</body><html>";
        return html;
    }
}
