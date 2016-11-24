package com.dh.dhnews.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.dh.dhnews.R;
import com.dh.dhnews.utils.JsoupUtil;

import java.util.logging.Logger;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.utils.Log;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;


/**
 * Created by 端辉 on 2016/3/13.
 */
public class VideoTestActivity extends AppCompatActivity {


    public static final String INTENT_VIDEO_URL = "INTENT_VIDEO_URL";

    private VideoView mVideoView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Vitamio.isInitialized(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.acticity_video);
        intView();
    }

    private static boolean isWifiConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if(wifiNetworkInfo.isConnected())
        {
            return true ;
        }
        return false ;
    }
    private void intView() {
        mVideoView = (VideoView) findViewById(R.id.videoview);
        //设置媒体控制器
        mVideoView.setMediaController(new MediaController(this));
        //设置缩放
        mVideoView.setVideoLayout(VideoView.VIDEO_LAYOUT_STRETCH,0);
        mVideoView.setBufferSize(512 * 1024);

        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setPlaybackSpeed(1.0f);
            }
        });
        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                finish();
            }
        });
        final String video_url = getIntent().getStringExtra(INTENT_VIDEO_URL);
        if(isWifiConnected(this)) {
            new GetVideoUrlTask().execute(video_url);
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("提示")
                    .setMessage("确定在流量模式下观看该视频吗？")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            new GetVideoUrlTask().execute(video_url);
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    })
                    .create()
                    .show();
        }
    }

    class GetVideoUrlTask extends AsyncTask<String,Void,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            findViewById(R.id.video_progressBar).setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            return JsoupUtil.getVideoContent(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(!TextUtils.isEmpty(s)) {
                //设置网络播放地址
                mVideoView.setVideoPath(s);
                mVideoView.requestFocus();
                findViewById(R.id.video_progressBar).setVisibility(View.GONE);
            }else{
                finish();
            }
        }
    }

}
