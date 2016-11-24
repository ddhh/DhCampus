package com.dh.dhnews.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dh.dhnews.R;
import com.dh.dhnews.activity.Main;
import com.dh.dhnews.bean.Course;
import com.dh.dhnews.bean.TermCourse;
import com.dh.dhnews.utils.HttpUtil;
import com.dh.dhnews.utils.JsoupUtil;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by 端辉 on 2016/3/16.
 */
public class GradeNumberFragment extends Fragment {

    private TextView tv_gradeNumber;
    private ProgressBar mProgressBar;

    private Main main;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        main = (Main) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gradenumber_layout, container, false);
        initView(view);
        return view;
    }

    private void initView(View v) {
        tv_gradeNumber = (TextView) v.findViewById(R.id.tv_gradenumber);
        mProgressBar = (ProgressBar) v.findViewById(R.id.gradenumber_progress);
        new CountGradeNumberTask().execute();
    }

    public String count(List<TermCourse> ltc) {
        String result = "";
        int count = 0;
        float grade_credit = 0.0f;
        float totalCredit = 0.0f;
        for (TermCourse tc : ltc) {
            for (Course c : tc.getCourseList()) {
                grade_credit += (Float.parseFloat(c.getCredit()) * c.getGradeNum());
                totalCredit += Float.parseFloat(c.getCredit());
                Log.d("GradeNumberFragment", "grade_credit:" + grade_credit);
                Log.d("GradeNumberFragment", "totalCredit:" + totalCredit);
                count++;
            }
        }
        try {
            result = (grade_credit/totalCredit)+"";
            Log.d("GradeNumberFragment", result);
        }catch (Exception e){
            return "0";
        }
        Log.d("GradeNumberFragment", "count:" + count);
        DecimalFormat df = new DecimalFormat("#.00");
        result = df.format(Double.parseDouble(result));
        return result;
    }

    class CountGradeNumberTask extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
            tv_gradeNumber.setText("绩点计算中......");
        }

        @Override
        protected String doInBackground(Void... params) {
            //计算绩点并返回
            if(main.allList.size()>0){
                return count(main.allList);
            }
            main.allList = JsoupUtil.getScore(HttpUtil.getScoreContent());
            return count(main.allList);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mProgressBar.setVisibility(View.GONE);
            tv_gradeNumber.setText("绩点为：" + s);
        }
    }


}
