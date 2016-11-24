package com.dh.dhnews.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dh.dhnews.R;
import com.dh.dhnews.bean.NewsTitle;
import com.dh.dhnews.bean.SchoolNewsBean;

import java.util.List;

/**
 * Created by 端辉 on 2016/3/12.
 */
public class SchoolNewsAdapter extends RecyclerView.Adapter {

    public static int TYPE_ITEM = 0;
    public static int TYPE_FOOTER = 1;

    private onItemClickListener listener;
    private List<NewsTitle> list;

    public SchoolNewsAdapter(List<NewsTitle> list) {
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.school_news_item_layout, parent, false);
            return new MyViewHolder(view);
        } else if (viewType == TYPE_FOOTER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.footerview, parent, false);
            return new FooterHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position + 1 == getItemCount()) return;
        MyViewHolder mHolder = (MyViewHolder) holder;
        NewsTitle snb = list.get(position);
        mHolder.tv_date.setText(snb.getDate());
        mHolder.tv_title.setText(snb.getTitle());
        mHolder.postion = position;
        mHolder.url = snb.getUrl();
    }

    @Override
    public int getItemCount() {
        return list.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_title;
        TextView tv_date;
        int postion;
        String url;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_school_news_list_title);
            tv_date = (TextView) itemView.findViewById(R.id.tv_school_news_list_date);
            itemView.setClickable(true);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(list.get(postion));
                }
            });
        }
    }

    class FooterHolder extends RecyclerView.ViewHolder {

        ProgressBar pb_loading;
        TextView tv_loading;

        public FooterHolder(View itemView) {
            super(itemView);
            pb_loading = (ProgressBar) itemView.findViewById(R.id.pb_loading);
            tv_loading = (TextView) itemView.findViewById(R.id.tv_loading);
        }
    }

    public interface onItemClickListener {
        public void onItemClick(NewsTitle newsTitle);
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }
}
