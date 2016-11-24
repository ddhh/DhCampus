package com.dh.dhnews.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.Toast;

import com.dh.dhnews.R;
import com.dh.dhnews.bean.TermCourse;
import com.dh.dhnews.fragment.LoginFragment;
import com.dh.dhnews.fragment.NewsFragment;
import com.dh.dhnews.fragment.MessageListFragment;
import com.dh.dhnews.utils.JsoupUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 端辉 on 2016/3/15.
 */
public class Main extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    public NavigationView mNavigationView;
    private Toolbar mToolbar;

    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

    public Fragment academicFragment,newsFragment, loginFragment, functionFragment, scoreFragment, gradeNumFragment,noticeFragment;

    public Fragment currentFragment;

    public boolean isLogined = false;

    public List<TermCourse> allList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        initView();
        initDrawer();

        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        currentFragment = newsFragment = new NewsFragment();
        loginFragment = new LoginFragment();
        transaction.add(R.id.main_fragment,loginFragment).hide(loginFragment);
        transaction.add(R.id.main_fragment, newsFragment);
        transaction.commit();
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void initDrawer() {

        mDrawerLayout = (DrawerLayout) findViewById(R.id.main_drawerlayout);
        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close);
        toggle.syncState();
        mDrawerLayout.setDrawerListener(toggle);
        mNavigationView = (NavigationView) findViewById(R.id.main_navigationview);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_notice:
                        if(noticeFragment==null){
                            noticeFragment = new MessageListFragment();
                            Bundle bundle = new Bundle();
                            bundle.putStringArray(MessageListFragment.URL_TO_BUNDLE, JsoupUtil.SCHOOL_URLS[0]);
                            noticeFragment.setArguments(bundle);
                        }
                        switchFragment(noticeFragment);
                        break;
                    case R.id.nav_academic:
                        if(academicFragment==null){
                            academicFragment = new MessageListFragment();
                            Bundle bundle = new Bundle();
                            bundle.putStringArray(MessageListFragment.URL_TO_BUNDLE, JsoupUtil.SCHOOL_URLS[1]);
                            academicFragment.setArguments(bundle);
                        }
                        switchFragment(academicFragment);
                        break;
                    case R.id.nav_news:
                        switchFragment(newsFragment);
                        break;
//                    case R.id.nav_schedule:
//                        if (!isLogined) {
//                            Toast.makeText(Main.this, "请先登录后查看", Toast.LENGTH_LONG).show();
//                            if (loginFragment == null) {
//                                loginFragment = new LoginFragment();
//                            }
//                            switchFragment(loginFragment);
//                            item.setCheckable(false);
//                            mNavigationView.setCheckedItem(R.id.nav_login);
//                        } else {
//
//                        }
//                        break;
//                    case R.id.nav_score:
//                        if (!isLogined) {
//                            Toast.makeText(Main.this, "请先登录后查看", Toast.LENGTH_LONG).show();
//                            if (loginFragment == null) {
//                                loginFragment = new LoginFragment();
//                            }
//                            switchFragment(loginFragment);
//                            item.setCheckable(false);
//                            mNavigationView.setCheckedItem(R.id.nav_login);
//                        } else {
//                            if (scoreFragment == null) {
//                                scoreFragment = new ScoreFragment();
//                            }
//                            switchFragment(scoreFragment);
//                            item.setCheckable(true);
//                        }
//                        break;
//                    case R.id.nav_GPA:
//                        if (!isLogined) {
//                            Toast.makeText(Main.this, "请先登录后查看", Toast.LENGTH_LONG).show();
//                            if (loginFragment == null) {
//                                loginFragment = new LoginFragment();
//                            }
//                            switchFragment(loginFragment);
//                            item.setCheckable(false);
//                            mNavigationView.setCheckedItem(R.id.nav_login);
//                        } else {
//                            if (gradeNumFragment == null) {
//                                gradeNumFragment = new GradeNumberFragment();
//                            }
//                            switchFragment(gradeNumFragment);
//                            item.setCheckable(true);
//                        }
//                        break;
                    case R.id.nav_login:
                        if (!isLogined) {
                            if (loginFragment == null) {
                                loginFragment = new LoginFragment();
                            }
                            switchFragment(loginFragment);
                        } else {
                            switchFragment(functionFragment);
                        }
                        break;
                    case R.id.nav_about:
                        item.setCheckable(false);
                        if (!isLogined) {
                            Toast.makeText(Main.this, "还未登录", Toast.LENGTH_LONG).show();
                            if (loginFragment == null) {
                                loginFragment = new LoginFragment();
                            }
                            switchFragment(loginFragment);
                            mNavigationView.setCheckedItem(R.id.nav_login);
                        } else {
                            isLogined = false;
                            removeFragment(scoreFragment);
                            removeFragment(gradeNumFragment);
                            removeFragment(functionFragment);
                            removeFragment(loginFragment);
                            switchFragment(loginFragment=new LoginFragment());
                            mNavigationView.setCheckedItem(R.id.nav_login);
                            Toast.makeText(Main.this, "注销成功", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
        mNavigationView.setCheckedItem(R.id.nav_news);
    }

    private void switchFragment(Fragment to) {
        if (currentFragment != to) {
            FragmentTransaction transaction = getSupportFragmentManager()
                    .beginTransaction();
            if (!to.isAdded()) { // 先判断是否被add过
                transaction.hide(currentFragment).add(R.id.main_fragment, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                transaction.hide(currentFragment).show(to).commit(); // 隐藏当前的fragment，显示下一个
            }
            currentFragment = to;
        }
    }


    private void removeFragment(Fragment f){
       if(f!=null&&f.isAdded()){
           getSupportFragmentManager().beginTransaction().remove(f).commit();
       }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("提示")
                    .setMessage("确定要退出吗？")
                    .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setNegativeButton("退出", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .create()
                    .show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
