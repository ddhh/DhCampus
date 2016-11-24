package com.dh.dhnews.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dh.dhnews.activity.VideoTestActivity;
import com.dh.dhnews.activity.MessageInfoActivity;
import com.dh.dhnews.utils.JsoupUtil;
import com.dh.dhnews.R;
import com.dh.dhnews.adapter.SchoolNewsAdapter;
import com.dh.dhnews.bean.NewsTitle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 端辉 on 2016/3/12.
 */
public class MessageListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

//    private static int STATE_PULL = 0;
//    private static int STATE_LOAD = 1;

    private int page = 1;

    private View view;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private SchoolNewsAdapter mAdapter;
    private List<NewsTitle> mList = new ArrayList<>();

    private String indexUrl, pageUrl;

    public static final String URL_TO_BUNDLE = "URL_TO_BUNDLE";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_school_news, container, false);
        Bundle bundle = getArguments();
        indexUrl = bundle.getStringArray(URL_TO_BUNDLE)[0];
        pageUrl = bundle.getStringArray(URL_TO_BUNDLE)[1];
        initView(view);
        initData();

        return view;
    }

    int lastVisibleItem;

    private void initView(View v) {
        mRecyclerView = (RecyclerView) v.findViewById(R.id.recyclerview);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem + 1 == mAdapter.getItemCount()) {
                    new SecondAsyncTask().execute();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
            }
        });
        mSwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_refresh);
        mSwipeRefreshLayout.setColorSchemeColors(Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW);
        // 这句话是为了，第一次进入页面的时候显示加载进度条
        mSwipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        new MyAsyncTask().execute();
    }

    private void initData() {
        new MyAsyncTask().execute();
    }

    private void setAdapter() {
        mAdapter = new SchoolNewsAdapter(mList);
        mAdapter.setOnItemClickListener(new SchoolNewsAdapter.onItemClickListener() {
            @Override
            public void onItemClick(NewsTitle newsTitle) {
                if(newsTitle.getUrl().startsWith(JsoupUtil.VIDEO_BASEURL)||newsTitle.getUrl().startsWith(JsoupUtil.VIDEO_2_BASEURL)){
                    Intent intent = new Intent(getActivity(),VideoTestActivity.class);
                    intent.putExtra(VideoTestActivity.INTENT_VIDEO_URL,newsTitle.getUrl());
                    startActivity(intent);
                    return;
                }
                Intent intent = new Intent(getActivity(),MessageInfoActivity.class);
                intent.putExtra(MessageInfoActivity.INTENT_MESSAGE_URL , newsTitle);
                startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }

    class MyAsyncTask extends AsyncTask<Void, Void, List<NewsTitle>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mSwipeRefreshLayout.setRefreshing(true);
        }

        @Override
        protected List<NewsTitle> doInBackground(Void... params) {
            return JsoupUtil.getTitles(indexUrl);

        }

        @Override
        protected void onPostExecute(List<NewsTitle> newsTitles) {
            super.onPostExecute(newsTitles);
            mList = newsTitles;
            setAdapter();
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    class SecondAsyncTask extends AsyncTask<Void, Void, List<NewsTitle>> {

        @Override
        protected List<NewsTitle> doInBackground(Void... params) {
            return JsoupUtil.getTitles(pageUrl + (++page));

        }

        @Override
        protected void onPostExecute(List<NewsTitle> newsTitles) {
            super.onPostExecute(newsTitles);
            mList.addAll(newsTitles);
            mAdapter.notifyDataSetChanged();
        }
    }

}
