package com.dh.dhnews.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.dh.dhnews.R;
import com.dh.dhnews.activity.Main;
import com.dh.dhnews.adapter.ScoreRecyclerViewAdapter;
import com.dh.dhnews.bean.Course;
import com.dh.dhnews.bean.TermCourse;
import com.dh.dhnews.utils.HttpUtil;
import com.dh.dhnews.utils.JsoupUtil;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 端辉 on 2016/3/15.
 */
public class ScoreFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private ScoreRecyclerViewAdapter mAdapter;
    private List<Course> courseList = new ArrayList<>();
    private Spinner spinner;
    private LinearLayout ll;
    private ProgressBar progressBar;

    private Button btn_gradeNumSelect;

    private Main main;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        main = (Main) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_score_layout, container, false);
        initView(view);
        return view;
    }

    private void initView(View v) {
        ll = (LinearLayout) v.findViewById(R.id.score_title_layout);
        progressBar = (ProgressBar) v.findViewById(R.id.scroe_progress);

        spinner = (Spinner) v.findViewById(R.id.term_spinner);

        btn_gradeNumSelect = (Button) v.findViewById(R.id.btn_gradenumber_select);
        btn_gradeNumSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("绩点查询")
                        .setMessage("你的绩点是："+count(main.allList))
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create()
                        .show();
            }
        });

        mRecyclerView = (RecyclerView) v.findViewById(R.id.score_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(
                new HorizontalDividerItemDecoration
                        .Builder(getActivity())
                        .marginResId(R.dimen.activity_horizontal_margin, R.dimen.activity_horizontal_margin)
                        .build());
        new GetScoreAsyncTask().execute();
    }

    private void setAdapter(final List<TermCourse> allList) {
        List<String> th = new ArrayList<>();
        for (TermCourse tc : allList) {
            th.add(tc.getTerm());
        }
        ArrayAdapter spinnerAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item, th);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mAdapter.list = allList.get(position).getCourseList();
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        courseList = allList.get(0).getCourseList();
        mAdapter = new ScoreRecyclerViewAdapter(courseList);
        mRecyclerView.setAdapter(mAdapter);
        ll.setVisibility(View.VISIBLE);
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

    class GetScoreAsyncTask extends AsyncTask<Void, Void, List<TermCourse>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ll.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<TermCourse> doInBackground(Void... params) {
            if(main.allList.size()>0){
                return main.allList;
            }
            return main.allList = JsoupUtil.getScore(HttpUtil.getScoreContent());
        }

        @Override
        protected void onPostExecute(List<TermCourse> termCourses) {
            super.onPostExecute(termCourses);
            if(termCourses.size()>0){
                setAdapter(termCourses);
                ll.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                btn_gradeNumSelect.setVisibility(View.VISIBLE);
            }else{

            }
        }
    }

}
