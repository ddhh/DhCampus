package com.dh.dhnews.bean;

/**
 * Created by 端辉 on 2016/3/14.
 */
public class LoginUser {

    private String studentId;
    private String password;

    private String code;


    public LoginUser(String studentId, String password, String code) {
        this.studentId = studentId;
        this.password = password;
        this.code = code;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "LoginUser{" +
                "studentId='" + studentId + '\'' +
                ", password='" + password + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
