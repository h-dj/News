package com.example.h_dj.news.activity;

import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.h_dj.news.Message.MyMessageEvent;
import com.example.h_dj.news.R;
import com.example.h_dj.news.bean.User;
import com.example.h_dj.news.utils.LogUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;

/**
 * Created by H_DJ on 2017/5/26.
 */
public class LoginActivity extends BaseActivity {

    private static final int EDITE_USER = 1;
    private static final int EDITE_PWD = 2;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.user_name)
    EditText mUserName;
    @BindView(R.id.user_clear)
    ImageView mUserClear;
    @BindView(R.id.user_password)
    EditText mUserPassword;
    @BindView(R.id.forgetPwd)
    TextView mForgetPwd;
    @BindView(R.id.pwd_clear)
    ImageView mPwdClear;
    @BindView(R.id.login)
    Button mLogin;
    @BindView(R.id.register)
    TextView mRegister;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }


    @Override
    protected void init() {
        super.init();
        initToolbar(mToolbar,getString(R.string.login_title));
        initEditText();
    }

    /**
     * 设置EditText文本监听
     */
    private void initEditText() {
        mUserName.addTextChangedListener(new MyTextWatcher(EDITE_USER));
        mUserPassword.addTextChangedListener(new MyTextWatcher(EDITE_PWD));
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @OnClick({R.id.user_clear, R.id.forgetPwd, R.id.pwd_clear, R.id.login, R.id.register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.user_clear:
                mUserName.setText("");
                break;
            case R.id.forgetPwd:
                break;
            case R.id.pwd_clear:
                mUserPassword.setText("");
                break;
            case R.id.login:
                login();
                break;
            case R.id.register:
                goTo(RegisterActivity.class);
                break;
        }
    }

    /**
     * 登陆
     */
    private void login() {
        String username = mUserName.getText().toString().trim();
        String password = mUserPassword.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(LoginActivity.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(LoginActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        showProgressDialog("登陆", "登陆中...");
        BmobUser.loginByAccount(username, password, new LogInListener<User>() {

            @Override
            public void done(User user, BmobException e) {
                if (user != null) {
                    Log.i("smile", "用户登陆成功");
                    finish();
                    EventBus.getDefault().post(new MyMessageEvent<>(user, MyMessageEvent.MSG_FROM_LOGIN));
                } else {
                    LogUtil.e("登陆失败：" + e.getLocalizedMessage());
                }
                hiddenProgressDialog();
            }
        });
    }


    /**
     * 监听EditText文本变化
     */
    private class MyTextWatcher implements TextWatcher {
        private int type;

        public MyTextWatcher(int type) {
            this.type = type;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            LogUtil.e("e:" + s);
            if (!TextUtils.isEmpty(s)) {
                if (type == EDITE_USER) {
                    mUserClear.setVisibility(View.VISIBLE);
                } else {
                    mPwdClear.setVisibility(View.VISIBLE);
                }
            } else {
                if (type == EDITE_USER) {
                    mUserClear.setVisibility(View.GONE);
                } else {
                    mPwdClear.setVisibility(View.GONE);
                }
            }
        }
    }
}
