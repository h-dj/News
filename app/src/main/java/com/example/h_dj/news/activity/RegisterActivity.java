package com.example.h_dj.news.activity;

import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.h_dj.news.R;
import com.example.h_dj.news.bean.User;
import com.example.h_dj.news.utils.LogUtil;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by H_DJ on 2017/5/27.
 */
public class RegisterActivity extends BaseActivity {
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
    @BindView(R.id.pwd_clear)
    ImageView mPwdClear;
    @BindView(R.id.register)
    Button mRegister;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void init() {
        super.init();
        initToolbar();
        initEditText();

    }

    /**
     * 初始化toolbar
     */
    private void initToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(getString(R.string.register_title));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
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


    /**
     * 设置EditText文本监听
     */
    private void initEditText() {
        mUserName.addTextChangedListener(new MyTextWatcher(EDITE_USER));
        mUserPassword.addTextChangedListener(new MyTextWatcher(EDITE_PWD));
    }


    @OnClick({R.id.user_clear, R.id.pwd_clear, R.id.register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.user_clear:
                mUserName.setText("");
                break;
            case R.id.pwd_clear:
                mUserPassword.setText("");
                break;
            case R.id.register:
                register();
                break;
        }
    }

    /**
     * 注册
     */
    private void register() {
        String username = mUserName.getText().toString().trim();
        String password = mUserPassword.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(RegisterActivity.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(RegisterActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        User bu = new User();
        bu.setUsername(username);
        bu.setPassword(password);
        bu.setUser_from("广东广州");
        //注意：不能用save方法进行注册
        showProgressDialog("注册", "注册中...");
        bu.signUp(new SaveListener<User>() {
            @Override
            public void done(User s, BmobException e) {
                if (e == null) {
                    LogUtil.e("注册成功");
                    finish();
                } else {
                    LogUtil.e("注册失败");
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
