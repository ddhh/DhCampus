package com.dh.dhnews.fragment;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dh.dhnews.R;
import com.dh.dhnews.utils.HttpUtil;
import com.dh.dhnews.utils.JsoupUtil;

/**
 * Created by 端辉 on 2016/3/18.
 */
public class OneInfoFragment extends Fragment {

    private TextView tv_text;
    private ImageView iv_header;
    private ProgressBar progressBar;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_info_layout,container,false);
        initView(view);
        return view;
    }

    private void initView(View v) {
        progressBar = (ProgressBar) v.findViewById(R.id.info_progressBar);
        tv_text = (TextView) v.findViewById(R.id.info_text);
        iv_header = (ImageView) v.findViewById(R.id.info_header_image);
        new GetInfoHeaderImageTask().execute();
        new GetInfoTask().execute();
    }

    class GetInfoTask extends AsyncTask<Void,Void,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Void... params) {
            return JsoupUtil.getInfo(HttpUtil.getInfoContent());
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            tv_text.setText(s);
            progressBar.setVisibility(View.GONE);
        }
    }

    class GetInfoHeaderImageTask extends AsyncTask<Void,Void,Bitmap>{

        @Override
        protected Bitmap doInBackground(Void... params) {
            return HttpUtil.getHeaderImage();
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            iv_header.setImageBitmap(bitmap);
        }
    }

}
