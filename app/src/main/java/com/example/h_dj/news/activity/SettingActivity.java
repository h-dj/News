package com.example.h_dj.news.activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.h_dj.news.R;
import com.example.h_dj.news.base.BaseActivity;
import com.example.h_dj.news.utils.DataCleanManager;
import com.example.h_dj.news.utils.LogUtil;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;


/**
 * Created by H_DJ on 2017/6/27.
 */
public class SettingActivity extends BaseActivity {

    @BindView(R.id.individual_setting)
    TextView mIndividualSetting;
    @BindView(R.id.clear_cache)
    TextView mClearCache;
    @BindView(R.id.logout)
    TextView mLogout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }
    @Override
    protected void init() {
        super.init();
        initToolbar(mToolbar, "设置");
        initCacheSize();
        boolean isLogin = mApp.checkLogin();
        if (isLogin) {
            mLogout.setVisibility(View.VISIBLE);
        } else {
            mLogout.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
    private void initCacheSize() {
        try {
            String size = DataCleanManager.getInstance(this).getTotalCacheSize();
            mClearCache.setText(String.format("清理缓存: %s", size));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.individual_setting, R.id.clear_cache, R.id.logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.individual_setting:
                break;
            case R.id.clear_cache:
                cacheData();
                break;
            case R.id.logout:
                logout();
                break;
        }
    }
    private void logout() {
        new AlertDialog.Builder(this)
                .setTitle("退出登录！")
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        BmobUser.logOut();
                        mLogout.setVisibility(View.GONE);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setCancelable(false)
                .create()
                .show();
    }
    /**
     * 清楚缓存
     */
    private void cacheData() {
        new AlertDialog.Builder(this)
                .setTitle("是否清理全部缓存！")
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DataCleanManager manager = DataCleanManager.getInstance(getApplicationContext());
                        try {
                            manager.cleanApplicationData();
                            LogUtil.e("缓存：" + manager.getTotalCacheSize());
                            Toast.makeText(SettingActivity.this, "清除成功！", Toast.LENGTH_SHORT).show();
                            mClearCache.setText(String.format("清理缓存: %s", "0KB"));
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(SettingActivity.this, "清除失败！", Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setCancelable(false)
                .create()
                .show();
    }


}
