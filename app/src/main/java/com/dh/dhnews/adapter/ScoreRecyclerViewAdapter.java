package com.dh.dhnews.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dh.dhnews.R;
import com.dh.dhnews.bean.Course;

import java.util.List;

/**
 * Created by 端辉 on 2016/3/15.
 */
public class ScoreRecyclerViewAdapter extends RecyclerView.Adapter {


    public List<Course> list ;

    public ScoreRecyclerViewAdapter(List<Course> list){
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.score_item_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Course course = list.get(position);
        MyViewHolder mHolder = (MyViewHolder)holder;
        mHolder.tv_name.setText(course.getName());
        mHolder.tv_credit.setText(course.getCredit());
        mHolder.tv_property.setText(course.getProperty());
        mHolder.tv_score.setText(course.getScore());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_name;
        private TextView tv_credit;
        private TextView tv_property;
        private TextView tv_score;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_score_name);
            tv_credit = (TextView) itemView.findViewById(R.id.tv_score_credit);
            tv_property = (TextView) itemView.findViewById(R.id.tv_score_property);
            tv_score = (TextView) itemView.findViewById(R.id.tv_score_score);
        }
    }
}
