package com.dh.dhnews.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dh.dhnews.R;
import com.dh.dhnews.activity.Main;
import com.dh.dhnews.bean.LoginUser;
import com.dh.dhnews.utils.HttpUtil;

/**
 * Created by 端辉 on 2016/3/16.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {

    private TextView error_text;

    private TextInputLayout mIdInput;
    private TextInputLayout mPwdInput;
    private TextInputLayout mIdentifyInput;

    private ImageView mIdentifyImage;
    private Button mBtnSubmit;
    private Button getmBtnReset;

    private ProgressBar mLoginProgressBar;

    private Main main;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        main = (Main)context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login_layout, container, false);
        initView(view);
        return view;
    }

    private void initView(View v) {

        error_text = (TextView) v.findViewById(R.id.error_text);

        mIdInput = (TextInputLayout) v.findViewById(R.id.input_id);
        mIdInput.getEditText().addTextChangedListener(new LoginTextWatcher(mIdInput));

        mPwdInput = (TextInputLayout) v.findViewById(R.id.input_password);
        mPwdInput.getEditText().addTextChangedListener(new LoginTextWatcher(mPwdInput));

        mIdentifyInput = (TextInputLayout) v.findViewById(R.id.input_identify);
        mIdentifyInput.getEditText().addTextChangedListener(new LoginTextWatcher(mIdentifyInput));

        mIdentifyImage = (ImageView) v.findViewById(R.id.identify_image);
        mIdentifyImage.setOnClickListener(this);
        mBtnSubmit = (Button) v.findViewById(R.id.btn_login_sign_in);
        mBtnSubmit.setOnClickListener(this);
        getmBtnReset = (Button) v.findViewById(R.id.btn_login_reset);
        getmBtnReset.setOnClickListener(this);

        mLoginProgressBar = (ProgressBar) v.findViewById(R.id.login_progress);

        new GetIdentifyImageTask().execute();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login_sign_in:
                loginSubmit();
                break;
            case R.id.btn_login_reset:
                inputReset();
                break;
            case R.id.identify_image:
                new GetIdentifyImageTask().execute();
                break;
        }
    }

    class LoginTextWatcher implements TextWatcher {

        TextInputLayout input;

        public LoginTextWatcher(TextInputLayout input) {
            this.input = input;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!TextUtils.isEmpty(s)) {
                input.setError(null);
                if(input.getChildCount()==2){
                    input.getChildAt(1).setVisibility(View.GONE);
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    private void loginSubmit() {

        String id = mIdInput.getEditText().getText().toString();
        String pwd = mPwdInput.getEditText().getText().toString();
        String identify = mIdentifyInput.getEditText().getText().toString();

        if (TextUtils.isEmpty(id)) {
            mIdInput.setError("学号不能为空");
            mIdInput.getChildAt(1).setVisibility(View.VISIBLE);
            return;
        }
        if (TextUtils.isEmpty(pwd)) {
            mPwdInput.setError("密码不能为空");
            mPwdInput.getChildAt(1).setVisibility(View.VISIBLE);
            return;
        }
        if (TextUtils.isEmpty(identify)) {
            mIdentifyInput.setError("验证码不能为空");
            mIdentifyInput.getChildAt(1).setVisibility(View.VISIBLE);
            return;
        }
        LoginUser user = new LoginUser(id,pwd,identify);
        Log.d("LoginFragment", user.toString());
        new LoginTask().execute(user);
    }

    private void inputReset() {
        mIdInput.getEditText().setText("");
        mPwdInput.getEditText().setText("");
        mIdentifyInput.getEditText().setText("");
    }

    class GetIdentifyImageTask extends AsyncTask<Void, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(Void... params) {
            return HttpUtil.getIdentifyCodeImage();
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            mIdentifyImage.setImageBitmap(bitmap);
        }
    }

    class LoginTask extends AsyncTask<LoginUser,Void,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoginProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(LoginUser... params) {
            return HttpUtil.login(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s.equals(HttpUtil.LOGIN_SUCCESS)){
                main.isLogined = true;
                Toast.makeText(getActivity(),"登录成功",Toast.LENGTH_SHORT).show();
                //跳转到功能界面
                main.getSupportFragmentManager()
                        .beginTransaction()
                        .hide(LoginFragment.this)
                        .add(R.id.main_fragment,
                               main.currentFragment = main.functionFragment = new FunctionFragment())
                        .commit();
            }else if(s.equals(HttpUtil.CODE_ERROR)){
                Toast.makeText(getActivity(),"验证码错误！",Toast.LENGTH_SHORT).show();
                error_text.setVisibility(View.VISIBLE);
                error_text.setText("登录失败，请重试！");
            }else {
                Toast.makeText(getActivity(),"登录失败",Toast.LENGTH_SHORT).show();
                error_text.setVisibility(View.VISIBLE);
                error_text.setText("登录失败，请重试！");
            }
            mLoginProgressBar.setVisibility(View.GONE);
        }
    }

}
