package com.dh.dhnews.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.http.AndroidHttpClient;
import android.util.Log;

import com.dh.dhnews.bean.LoginUser;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 端辉 on 2016/3/14.
 */
public class HttpUtil {

    //请求的IP
    public static final String IP_URL = "http://210.31.198.167/";
    //请求用的cookie
//    public static final String COOKIE = "JSESSIONID=abcUkHuhJfvYX4ZEEc0nv";
    //登录的Url
    public static final String LOGIN_URL = "http://210.31.198.167/loginAction.do";
    //获取验证码的Url
    public static final String IDENTIFY_CODE_URL = "http://210.31.198.167/validateCodeAction.do";
    //获取成绩的Url
    public static final String SCORE_URL = "http://210.31.198.167/gradeLnAllAction.do?type=ln&oper=qbinfo";
    //获取课表deUrl
    public static final String SCHEDULE_URL = "http://210.31.198.167/lnkbcxAction.do";
    //学籍信息的Url
    public static final String INFO_URL = "http://210.31.198.167/xjInfoAction.do?oper=xjxx";
    //学籍信息头像图片的Url
    public static final String HEADER_IMG_URL = "http://210.31.198.167/xjInfoAction.do?oper=img";


    public static String Cookie = "";

    public static Bitmap getIdentifyCodeImage(){
        Bitmap result = null;
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet request = new HttpGet(IDENTIFY_CODE_URL);
        try {
            HttpResponse response = httpClient.execute(request);
            if(response.getStatusLine().getStatusCode()== HttpStatus.SC_OK){
                InputStream is = response.getEntity().getContent();
                result = BitmapFactory.decodeStream(is);
                Header header = response.getHeaders("Set-Cookie")[0];
                Cookie = header.getValue();
                Cookie = Cookie.substring(0,Cookie.length()-8);
            }
        } catch (IOException e) {

            e.printStackTrace();
        }finally {
            httpClient.getConnectionManager().shutdown();
        }
        return  result;
    }

    public static final String LOGIN_SUCCESS = "LOGIN_SUCCESS";
    public static final String LOGIN_ERROR = "LOGIN_ERROR";
    public static final String CODE_ERROR = "CODE_ERROR";
    public static final String ID_ERROR = "ID_ERROR";

    public static String login(LoginUser user){
        String result = LOGIN_ERROR;
        HttpClient httpClient = new DefaultHttpClient();
        try{
            HttpPost request = new HttpPost(LOGIN_URL);
            request.addHeader("Cookie",Cookie);
            List<NameValuePair> nvps = new ArrayList<>();
            nvps.add(new BasicNameValuePair("zjh",user.getStudentId()));
            nvps.add(new BasicNameValuePair("mm",user.getPassword()));
            nvps.add(new BasicNameValuePair("v_yzm",user.getCode()));
            request.setEntity(new UrlEncodedFormEntity(nvps,"GBK"));
            HttpResponse response = httpClient.execute(request);
            if(response.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
                String bodyContent = EntityUtils.toString(response.getEntity());
                if(bodyContent.contains("学分制综合教务")){
                    result = LOGIN_SUCCESS;
                }else if(bodyContent.contains("你输入的验证码错误")){
                    result = CODE_ERROR;
                }else if(bodyContent.contains("你输入的证件号不存在")){
                    result = ID_ERROR;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        httpClient.getConnectionManager().shutdown();
        return result;
    }

    public static Bitmap getHeaderImage(){
        Bitmap result = null;
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet request = new HttpGet(HEADER_IMG_URL);
        request.addHeader("Cookie",Cookie);
        try {
            HttpResponse response = httpClient.execute(request);
            if(response.getStatusLine().getStatusCode()== HttpStatus.SC_OK){
                InputStream is = response.getEntity().getContent();
                result = BitmapFactory.decodeStream(is);
            }
        } catch (IOException e) {

            e.printStackTrace();
        }finally {
            httpClient.getConnectionManager().shutdown();
        }

        return  result;
    }

    public static String getInfoContent(){
        String result = "";
        HttpClient httpClient = new DefaultHttpClient();
        try {
            HttpGet request = new HttpGet(INFO_URL);
            request.addHeader("Cookie",Cookie);
            HttpResponse response = httpClient.execute(request);
            if(response.getStatusLine().getStatusCode()== HttpStatus.SC_OK){
                result = EntityUtils.toString(response.getEntity());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            httpClient.getConnectionManager().shutdown();
        }
        return result;
    }

    public static String getScoreContent(){
        String result = "";
        HttpClient httpClient = new DefaultHttpClient();
        try {
            HttpGet request = new HttpGet(SCORE_URL);
            request.addHeader("Cookie",Cookie);
            HttpResponse response = httpClient.execute(request);
            if(response.getStatusLine().getStatusCode()== HttpStatus.SC_OK){
                result = EntityUtils.toString(response.getEntity());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            httpClient.getConnectionManager().shutdown();
        }
        return result;
    }

    public static String getScheduleContent(String value){
        String result = "";
        HttpClient httpClient = new DefaultHttpClient();
        try {
            HttpGet request = new HttpGet(SCHEDULE_URL+"?zxjxjhh="+value);
            request.addHeader("Cookie",Cookie);
            HttpResponse response = httpClient.execute(request);
            if(response.getStatusLine().getStatusCode()== HttpStatus.SC_OK){
                result = EntityUtils.toString(response.getEntity());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            httpClient.getConnectionManager().shutdown();
        }
        Log.d("HttpUtil", result);
        return result;
    }

//    public static Bitmap getIdentifyCodeImage() {
//        Bitmap result = null;
//        try {
//
//            double random = Math.random();
//            URL url = new URL(IDENTIFY_CODE_URL + "?random=" + random);
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setRequestMethod("GET");
//            conn.setReadTimeout(5000);
//            conn.setConnectTimeout(5000);
//
//            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
//                InputStream is = conn.getInputStream();
//                result = BitmapFactory.decodeStream(is);
//                Log.d("HttpUtil", "OK");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return result;
//    }
//
//    public static boolean login(LoginUser user) {
//        boolean result = false;
//        try {
//            //登录的URL
//            URL url = new URL(LOGIN_URL);
//            //获取连接
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            //post模式
//            conn.setRequestMethod("POST");
//            //设置超时
//            conn.setReadTimeout(5000);
//            conn.setConnectTimeout(5000);
//            conn.setRequestProperty("Cookie", COOKIE);
//
//            //传递的参数字符串
//            String param = "zjh=" + user.getStudentId() + "&mm=" + user.getPassword() + "&v_yzm=" + user.getCode();
//
//            conn.setDoOutput(true); // 发送POST请求必须设置允许输出
//            conn.setDoInput(true); // 发送POST请求必须设置允许输入
//            //setDoInput的默认值就是true
//
//            //获取输出流
//            OutputStream os = conn.getOutputStream();
//            os.write(param.getBytes());
//            os.flush();
//            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
//                InputStream is = conn.getInputStream();
//                ByteArrayOutputStream bos = new ByteArrayOutputStream();
//                int len = 0;
//                byte[] buffer = new byte[1024];
//                while ((len = is.read(buffer)) != -1) {
//                    bos.write(buffer, 0, len);
//                }
//                is.close();
//                bos.close();
//                String bodyContent = new String(bos.toByteArray());
//                Log.d("HttpUtil", bodyContent);
//                if (bodyContent.contains("学分制综合教务")) {
//                    result = true;
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return result;
//    }


}
