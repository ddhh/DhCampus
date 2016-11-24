package com.dh.dhnews.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.dh.dhnews.R;

/**
 * Created by 端辉 on 2016/3/16.
 */
public class FunctionFragment extends Fragment implements View.OnClickListener {

    private LinearLayout tab_info,tab_schedule,tab_scroe;

    private Fragment infoFragment,scheduleFragment,scroeFragment;

    private Fragment currentFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_function_layout,container,false);
        initView(view);
        return view;
    }

    private void initView(View v){
        tab_info = (LinearLayout) v.findViewById(R.id.tab_one_info);
        tab_schedule = (LinearLayout) v.findViewById(R.id.tab_schedule);
        tab_scroe = (LinearLayout) v.findViewById(R.id.tab_scroe);

        tab_info.setOnClickListener(this);
        tab_schedule.setOnClickListener(this);
        tab_scroe.setOnClickListener(this);

        infoFragment = new OneInfoFragment();
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.content_layout,infoFragment).commit();
        currentFragment = infoFragment;
        tab_info.setBackgroundResource(R.drawable.bottom_bar_item_pressed_bg);
    }

    @Override
    public void onClick(View v) {
        resetBg();
        switch (v.getId()){
            case R.id.tab_one_info:
                tab_info.setBackgroundResource(R.drawable.bottom_bar_item_pressed_bg);
                switchFragment(infoFragment);
                break;
            case R.id.tab_schedule:
                if(scheduleFragment==null){
                    scheduleFragment = new ScheduleFragment();
                }
                tab_schedule.setBackgroundResource(R.drawable.bottom_bar_item_pressed_bg);
                switchFragment(scheduleFragment);
                break;
            case R.id.tab_scroe:
                if(scroeFragment==null){
                    scroeFragment = new ScoreFragment();
                }
                tab_scroe.setBackgroundResource(R.drawable.bottom_bar_item_pressed_bg);
                switchFragment(scroeFragment);
                break;
        }
    }


    private void resetBg(){
        tab_info.setBackgroundResource(R.drawable.bottom_bar_item_bg);
        tab_schedule.setBackgroundResource(R.drawable.bottom_bar_item_bg);
        tab_scroe.setBackgroundResource(R.drawable.bottom_bar_item_bg);
    }


    private void switchFragment(Fragment to) {
        if (currentFragment != to) {
            FragmentTransaction transaction = getActivity().getSupportFragmentManager()
                    .beginTransaction();
            if (!to.isAdded()) { // 先判断是否被add过
                transaction.hide(currentFragment).add(R.id.content_layout, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                transaction.hide(currentFragment).show(to).commit(); // 隐藏当前的fragment，显示下一个
            }
            currentFragment = to;
        }
    }
}
