package com.dh.dhnews.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dh.dhnews.R;
import com.dh.dhnews.utils.JsoupUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 端辉 on 2016/3/15.
 */
public class NewsFragment extends Fragment{


    private TabLayout mNewsTab;
    private ViewPager mNewsPager;

    private String[] mNewsTitles = {
            "学校要闻", "领导讲话", "学习交流", "部门快讯",
            "基层动态", "媒体关注", "视频新闻", "菁菁校园",
            "校园人物", "校友动态", "电子校报"
    };
    private String[][] mNewsUrls = JsoupUtil.NEWS_URLS;
    private List<Fragment> fragmentList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frgment_news_layout,container,false);
        initView(view);
        return view;
    }


    private void initView(View v){
        mNewsTab = (TabLayout) v.findViewById(R.id.news_tab_layout);
        mNewsTab.removeAllTabs();
        fragmentList = new ArrayList<>();
        for(int i=0;i<mNewsTitles.length;i++){
            mNewsTab.addTab(mNewsTab.newTab().setText(mNewsTitles[i]));
            Bundle bundle = new Bundle();
            bundle.putStringArray(MessageListFragment.URL_TO_BUNDLE,mNewsUrls[i]);
            MessageListFragment f = new MessageListFragment();
            f.setArguments(bundle);
            fragmentList.add(f);
        }
        mNewsPager = (ViewPager) v.findViewById(R.id.news_viewpager);
        mNewsPager.setOffscreenPageLimit(fragmentList.size());
        mNewsPager.setAdapter(new FragmentPagerAdapter(getActivity().getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mNewsTitles[position];
            }
        });
        mNewsTab.setupWithViewPager(mNewsPager);

    }

}
