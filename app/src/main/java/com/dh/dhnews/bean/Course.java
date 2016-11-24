package com.dh.dhnews.bean;

/**
 * Created by 端辉 on 2016/3/15.
 */
public class Course {

    private String id;
    private String name;
    private String credit;
    private String property;
    private String score;

    private String level;       //等级
    private float gradeNum;     //这门课获得的绩点

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
        setLevelAndGradeNum(score);
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public float getGradeNum() {
        return gradeNum;
    }

    public void setGradeNum(float gradeNum) {
        this.gradeNum = gradeNum;
    }


    @Override
    public String toString() {
        return "Course{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", credit='" + credit + '\'' +
                ", property='" + property + '\'' +
                ", score='" + score + '\'' +
                ", level='" + level + '\'' +
                ", gradeNum=" + gradeNum +
                '}';
    }

    private void setLevelAndGradeNum(String s){
        if(s.contains("不及格")){
            gradeNum = 0.0f;
        }else if(s.contains("及格")){
            gradeNum = 1.0f;
        }else if(s.contains("中")){
            gradeNum = 2.0f;
        }else if(s.contains("良")){
            gradeNum = 3.0f;
        }else if(s.contains("优")){
            gradeNum = 4.0f;
        }else {
            s = s.substring(0,s.length()-1);//去掉字符串多出来的一个字符
            float grade = Float.parseFloat(s.trim());
            if (grade >= 90) {
                level = "A";
                gradeNum = 4.0f;
            } else if (grade >= 85) {
                level = "A-";
                gradeNum = 3.7f;
            } else if (grade >= 82) {
                level = "B+";
                gradeNum = 3.3f;
            } else if (grade >= 78) {
                level = "B";
                gradeNum = 3.0f;
            } else if (grade >= 75) {
                level = "B-";
                gradeNum = 2.7f;
            } else if (grade >= 71) {
                level = "C+";
                gradeNum = 2.3f;
            } else if (grade >= 66) {
                level = "C";
                gradeNum = 2.0f;
            } else if (grade >= 62) {
                level = "C-";
                gradeNum = 1.7f;
            } else if (grade >= 60) {
                level = "D";
                gradeNum = 1.3f;
            } else {
                level = "F";
                gradeNum = 0.0f;
            }
        }
    }

}
