package com.dh.dhnews.utils;

import android.util.Log;

import com.dh.dhnews.bean.Course;
import com.dh.dhnews.bean.News;
import com.dh.dhnews.bean.NewsTitle;
import com.dh.dhnews.bean.TermCourse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 端辉 on 2016/3/12.
 */
public class JsoupUtil {

    //学校网站地址
    public static final String SCHOOL_NET_ADDRESS = "http://www.ncst.edu.cn";

    //通知公告的地址ID
    public static final String NOTICE_ID = "1374811060149";
    //通知公告的列表地址
    public static final String NOTICE_INDEX = SCHOOL_NET_ADDRESS + "/col/" + NOTICE_ID + "/index.html";
    //通知公告分页的URL
    public static final String NOTICE_PAGE = SCHOOL_NET_ADDRESS + "/column.jsp?id=" + NOTICE_ID + "&current=";

    //学术交流的地址ID
    public static final String ACADEMIC_ID = "1374811047971";
    //学术交流的列表地址
    public static final String ACADEMIC_INDEX = SCHOOL_NET_ADDRESS + "/col/" + ACADEMIC_ID + "/index.html";
    //学术交流的分页URL
    public static final String ACADEMIC_PAGE = SCHOOL_NET_ADDRESS + "/column.jsp?id=" + ACADEMIC_ID + "&current=";


    public static final String[] NOTICE_URL = {NOTICE_INDEX, NOTICE_PAGE};
    public static final String[] ACADEMIC_URL = {ACADEMIC_INDEX, ACADEMIC_PAGE};


    public static final String[][] SCHOOL_URLS = {
            NOTICE_URL, ACADEMIC_URL
    };

    public static final String[] SCHOOL_TITLES = new String[]{
            "通知公告", "学术交流"
    };


    //新闻网站首页的地址
    public static final String NEWS_NET_ADDRESS = "http://newstest.heuu.edu.cn";

    //学校要闻的ID
    public static final String SCHOOL_NEWS_ID = "1393558054724";
    //部门资讯
    public static final String DEPARTMENT_NEWS_ID = "1393558093461";
    //基层
    public static final String BASIC_LEVEL_NEWS_ID = "1393558104092";
    //媒体
    public static final String MEDIA_ATTENTION_NEWS_ID = "1393558118050";
    //校园
    public static final String CAMPUS_NEWS_ID = "1393558142537";
    //校园明星
    public static final String CAMPUS_PEOPLE_NEWS_ID = "1393558153696";
    //校友
    public static final String ALUMNI_DYNAMICS_NEWS_ID = "1393558161834";
    //领导讲话
    public static final String LEADERSHIP_SPEECH_NEWS_ID = "1393558069961";

    public static final String LEARNING_NEWS_ID = "1393558080147";

    public static final String VIDEO_NEWS_ID = "1393558127719";

    public static final String ELECTRONIC_NEWSPAPER_ID = "1393558173719";

    //学校要闻列表地址
    public static final String SCHOOL_NEWS_INDEX = NEWS_NET_ADDRESS + "/col/" + SCHOOL_NEWS_ID + "/index.html";

    public static final String DEPARTMENT_NEWS_INDEX = NEWS_NET_ADDRESS + "/col/" + DEPARTMENT_NEWS_ID + "/index.html";

    public static final String BASIC_LEVEL_NEWS_INDEX = NEWS_NET_ADDRESS + "/col/" + BASIC_LEVEL_NEWS_ID + "/index.html";

    public static final String MEDIA_ATTENTION_NEWS_INDEX = NEWS_NET_ADDRESS + "/col/" + MEDIA_ATTENTION_NEWS_ID + "/index.html";

    public static final String CAMPUS_NEWS_INDEX = NEWS_NET_ADDRESS + "/col/" + CAMPUS_NEWS_ID + "/index.html";

    public static final String CAMPUS_PEOPLE_NEWS_INDEX = NEWS_NET_ADDRESS + "/col/" + CAMPUS_PEOPLE_NEWS_ID + "/index.html";

    public static final String ALUMNI_DYNAMICS_NEWS_INDEX = NEWS_NET_ADDRESS + "/col/" + ALUMNI_DYNAMICS_NEWS_ID + "/index.html";

    public static final String LEADERSHIP_SPEECH_NEWS_INDEX = NEWS_NET_ADDRESS + "/col/" + LEADERSHIP_SPEECH_NEWS_ID + "/index.html";

    public static final String LEARNING_NEWS_INDEX = NEWS_NET_ADDRESS + "/col/" + LEARNING_NEWS_ID + "/index.html";

    public static final String VIDEO_NEWS_INDEX = NEWS_NET_ADDRESS + "/col/" + VIDEO_NEWS_ID + "/index.html";

    public static final String ELECTRONIC_NEWSPAPER_INDEX = NEWS_NET_ADDRESS + "/col/" + ELECTRONIC_NEWSPAPER_ID + "/index.html";


    //学校要闻翻页时显示第URL,current=后面的是第几页
    public static final String SCHOOL_NEWS_PAGES = NEWS_NET_ADDRESS + "/column.jsp?id=" + SCHOOL_NEWS_ID + "&current=";

    public static final String DEPARTMENT_NEWS_PAGES = NEWS_NET_ADDRESS + "/column.jsp?id=" + DEPARTMENT_NEWS_ID + "&current=";

    public static final String BASIC_LEVEL_NEWS_PAGES = NEWS_NET_ADDRESS + "/column.jsp?id=" + BASIC_LEVEL_NEWS_ID + "&current=";

    public static final String MEDIA_ATTENTION_NEWS_PAGES = NEWS_NET_ADDRESS + "/column.jsp?id=" + MEDIA_ATTENTION_NEWS_ID + "&current=";

    public static final String CAMPUS_NEWS_PAGES = NEWS_NET_ADDRESS + "/column.jsp?id=" + CAMPUS_NEWS_ID + "&current=";

    public static final String CAMPUS_PEOPLE_NEWS_PAGES = NEWS_NET_ADDRESS + "/column.jsp?id=" + CAMPUS_PEOPLE_NEWS_ID + "&current=";

    public static final String ALUMNI_DYNAMICS_NEWS_PAGES = NEWS_NET_ADDRESS + "/column.jsp?id=" + ALUMNI_DYNAMICS_NEWS_ID + "&current=";

    public static final String LEADERSHIP_SPEECH_NEWS_PAGES = NEWS_NET_ADDRESS + "/column.jsp?id=" + LEADERSHIP_SPEECH_NEWS_ID + "&current=";

    public static final String LEARNING_NEWS_PAGES = NEWS_NET_ADDRESS + "/column.jsp?id=" + LEARNING_NEWS_ID + "&current=";

    public static final String VIDEO_NEWS_PAGES = NEWS_NET_ADDRESS + "/column.jsp?id=" + VIDEO_NEWS_ID + "&current=";

    public static final String ELECTRONIC_NEWSPAPER_PAGES = NEWS_NET_ADDRESS + "/column.jsp?id=" + ELECTRONIC_NEWSPAPER_ID + "&current=";


    public static final String[] SCHOOL_NEWS_URL = {SCHOOL_NEWS_INDEX, SCHOOL_NEWS_PAGES};
    public static final String[] DEPARTMENT_NEWS_URL = {DEPARTMENT_NEWS_INDEX, DEPARTMENT_NEWS_PAGES};
    public static final String[] BASIC_LEVEL_NEWS_URL = {BASIC_LEVEL_NEWS_INDEX, BASIC_LEVEL_NEWS_PAGES};
    public static final String[] MEDIA_ATTENTION_NEWS_URL = {MEDIA_ATTENTION_NEWS_INDEX, MEDIA_ATTENTION_NEWS_PAGES};
    public static final String[] CAMPUS_NEWS_URL = {CAMPUS_NEWS_INDEX, CAMPUS_NEWS_PAGES};
    public static final String[] CAMPUS_PEOPLE_NEWS_URL = {CAMPUS_PEOPLE_NEWS_INDEX, CAMPUS_PEOPLE_NEWS_PAGES};
    public static final String[] ALUMNI_DYNAMICS_NEWS_URL = {ALUMNI_DYNAMICS_NEWS_INDEX, ALUMNI_DYNAMICS_NEWS_PAGES};
    public static final String[] LEADERSHIP_SPEECH_NEWS_URL = {LEADERSHIP_SPEECH_NEWS_INDEX, LEADERSHIP_SPEECH_NEWS_PAGES};
    public static final String[] LEARNING_NEWS_URL = {LEARNING_NEWS_INDEX, LEARNING_NEWS_PAGES};
    public static final String[] VIDEO_NEWS_URL = {VIDEO_NEWS_INDEX, VIDEO_NEWS_PAGES};
    public static final String[] ELECTRONIC_NEWSPAPER_URL = {ELECTRONIC_NEWSPAPER_INDEX, ELECTRONIC_NEWSPAPER_PAGES};


    public static final String[][] NEWS_URLS = {
            SCHOOL_NEWS_URL, LEADERSHIP_SPEECH_NEWS_URL, LEARNING_NEWS_URL, DEPARTMENT_NEWS_URL, BASIC_LEVEL_NEWS_URL,
            MEDIA_ATTENTION_NEWS_URL, VIDEO_NEWS_URL, CAMPUS_NEWS_URL, CAMPUS_PEOPLE_NEWS_URL,
            ALUMNI_DYNAMICS_NEWS_URL, ELECTRONIC_NEWSPAPER_URL
    };

    public static final String[] TAB_TITLES = new String[]{
            "学校要闻", "领导讲话", "学习交流", "部门快讯",
            "基层动态", "媒体关注", "视频新闻", "菁菁校园",
            "校园人物", "校友动态", "电子校报"
    };


    public static final String VIDEO_BASEURL = "http://video2.heuu.edu.cn:8080/shipin/";
    public static final String VIDEO_2_BASEURL = "http://videonews2.heuu.edu.cn/shipin/";

    public static List<NewsTitle> getTitles(String pageUrl) {
        if (pageUrl.startsWith(SCHOOL_NET_ADDRESS)) {
            return getNoticeAndAcademicTitle(pageUrl);
        } else {
            return getNewsTitle(pageUrl);
        }
    }

    public static List<NewsTitle> getNewsTitle(String pageUrl) {
        List<NewsTitle> list = new ArrayList<>();
        try {
            Document document = Jsoup.connect(pageUrl).timeout(10000).get();
            Element element = document.select("div.art").first();
            Elements titles = element.getElementsByTag("tr");
            for (Element e : titles) {
                NewsTitle news = new NewsTitle();
                for (int i = 0; i < TAB_TITLES.length; i++) {
                    String[] ss = NEWS_URLS[i];
                    if (pageUrl.equals(ss[0]) || pageUrl.startsWith(ss[1])) {
                        news.setNewsType(TAB_TITLES[i]);
                        break;
                    }
                }
                String title = e.select("td").select("a").text();
                String link = e.select("td").select("a").attr("href").trim();
                String date = e.select("td").get(1).text();
                String url = "";
                if (link.startsWith("http://")) {
                    url = link;
                } else {
                    url = NEWS_NET_ADDRESS + link;
                }
                news.setTitle(title);
                news.setDate(date);
                news.setBaseUrl(NEWS_NET_ADDRESS);
                news.setUrl(url);
                list.add(news);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static News getOneNews(String url) {
        News news = new News();
        Document document;
        try {
            document = Jsoup.connect(url).timeout(10000).get();
            Element element = document.select("div.art").first();
            Elements contentElements = element.select("p");
            Elements imageElement = element.select("img");
            for (Element e : imageElement) {
                String imageUrl = e.attr("src").trim();
                if (imageUrl.startsWith("http://")) {
                    news.addImgUrl(imageUrl);
                } else {
                    news.addImgUrl(NEWS_NET_ADDRESS + imageUrl);
                }
            }
            news.getImgUrls().remove(news.getImgUrls().size() - 1);
            for (Element e : contentElements) {
                news.addPraContent(e.text());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return news;
    }

    public static String getSchedule(String content) {
        String result = "";
        Document document = Jsoup.parse(content);
        Element e = document.getElementById("dayin");
        result = e.toString();
        return result;
    }

    public static String getInfo(String content) {
        String result = "";
        List<String> list = new ArrayList<>();
        Document document = Jsoup.parse(content);
        Element e = document.getElementById("tblView");
        Element con = Jsoup.parse(e.toString().replace("&nbsp;", ""));
        Elements trs = con.select("tr");
        for (int i = 0; i < trs.size() - 1; i++) {
            Elements tds = trs.get(i).select("td");
            if (tds.size() >= 4) {
                list.add(tds.get(0).text() + tds.get(1).text());
                list.add(tds.get(2).text() + tds.get(3).text());
            } else if (tds.size() < 3) {
                list.add(tds.get(0).text() + tds.get(1).text());
            }
        }
        for (String s : list) {
            result = result + s + "\n";
        }
        return result;
    }

    public static List<List<String>> getScheduleOptions(String content) {
        List<List<String>> lls = new ArrayList<>();
        List<String> keyList = new ArrayList<>();
        List<String> valueList = new ArrayList<>();
        Document document = Jsoup.parse(content);
        Elements options = document.select("option");
        for (Element op : options) {
            String key = op.attr("value").trim();
            String value = op.text().trim();
            Log.d("JsoupUtil", key + ":" + value);
            keyList.add(key);
            valueList.add(value);
        }
        lls.add(keyList);
        lls.add(valueList);
        return lls;
    }


    public static List<TermCourse> getScore(String content) {
        List<TermCourse> list = new ArrayList<>();
        Document document = Jsoup.parse(content);
        Elements titles = document.getElementsByClass("title");
        Elements tops = document.getElementsByClass("titleTop2");
        for (int i = 0; i < titles.size(); i++) {
            String term = titles.get(i).select("b").text();
            Log.d("JsoupUtil", term);
            TermCourse tc = new TermCourse();
            tc.setTerm(term);
            List<Course> lc = new ArrayList<>();
            Elements odds = tops.get(i).getElementsByClass("odd");
            for (Element odd : odds) {
                Elements tds = odd.select("td");
                Course c = new Course();
                for (int j = 0; j < tds.size(); j++) {
                    String s = tds.get(j).text().trim();
                    if (j == 0) {
                        c.setId(s);
                    } else if (j == 2) {
                        c.setName(s);
                    } else if (j == 4) {
                        c.setCredit(s);
                    } else if (j == 5) {
                        c.setProperty(s);
                    } else if (j == 6) {
                        c.setScore(s);
                    }
                }
                lc.add(c);
            }
            tc.setCourseList(lc);
            Log.d("JsoupUtil", tc.toString());
            list.add(tc);
        }

        return list;
    }

    public static List<NewsTitle> getNoticeAndAcademicTitle(String pageUrl) {
        List<NewsTitle> list = new ArrayList<>();
        try {
            Document document = Jsoup.connect(pageUrl).timeout(10000).get();
            Element conList = document.getElementsByClass("con-list").first();
            Elements cons = conList.select("div");
            for (int i = 1; i < cons.size() - 1; i++) {
                NewsTitle newsTitle = new NewsTitle();
                for (int j = 0; j < SCHOOL_TITLES.length; j++) {
                    String[] ss = SCHOOL_URLS[j];
                    if (pageUrl.equals(ss[0]) || pageUrl.startsWith(ss[1])) {
                        newsTitle.setNewsType(SCHOOL_TITLES[j]);
                        break;
                    }
                }
                String link = cons.get(i).select("a").attr("href").trim();
                String titleUrl = "";
                if (link.startsWith("http://")) {
                    titleUrl = link;
                } else {
                    titleUrl = SCHOOL_NET_ADDRESS + link;
                }
                newsTitle.setUrl(titleUrl);
                Element e = Jsoup.parse(cons.get(i).toString().trim().replace("&nbsp;", " "));
                String[] title = e.text().trim().split(" ", 2);
                for (int j = 0; j < title.length; j++) {
                    newsTitle.setTitle(title[0]);
                    newsTitle.setDate(title[1]);
                }
                newsTitle.setBaseUrl(SCHOOL_NET_ADDRESS);
                list.add(newsTitle);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static String getMassageInfo(String infoUrl) {
        String result = "";
        Document document = null;
        if (infoUrl.startsWith(SCHOOL_NET_ADDRESS)) {
            try {
                document = Jsoup.connect(infoUrl).timeout(10000).get();
                Element conList = document.getElementsByClass("con-list").first();
                result = conList.toString();
                result.replace("&nbsp;", " ");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (infoUrl.startsWith(NEWS_NET_ADDRESS)) {
            try {
                document = Jsoup.connect(infoUrl).timeout(10000).get();
                Element conList = document.getElementsByClass("art").first();
                Element lastImg = conList.select("img").last();
                result = conList.toString().replace(lastImg.toString(), "");
                result = result.replace("text-indent:28pt;", "");
                result = result.replace("img", "img style=\"width:100%;height:auto;\"");
                result.replace("&nbsp;", " ");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }


    public static String getVideoContent(String infoUrl) {
        String result = "";
        Document document = null;
        try {
            document = Jsoup.connect(infoUrl).timeout(10000).get();
            Element page = document.getElementById("page");
            Element a = page.select("a").first();
            result = a.attr("href").trim();
            Log.d("JsoupUtil", result);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
