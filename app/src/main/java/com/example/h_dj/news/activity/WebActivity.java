package com.example.h_dj.news.activity;


import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.SupportMenuInflater;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.h_dj.news.R;
import com.example.h_dj.news.bean.CollectBean;
import com.example.h_dj.news.bean.CommentBean;
import com.example.h_dj.news.utils.LogUtil;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.lang.reflect.Method;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CountListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by H_DJ on 2017/5/17.
 */

public class WebActivity extends BaseActivity {

    @BindView(R.id.web)
    WebView mWeb;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.progress)
    ProgressBar mProgress;
    @BindView(R.id.comment)
    EditText mComment;
    @BindView(R.id.send)
    Button mSend;


    private String url;
    private Integer commentCount = 0;//跟帖数
    private Menu mMenu;
    /**
     * 当前网页被收藏的id
     */
    private String currentCollectId = null;
    private boolean isCollection;
    /**
     * 字体
     */
    private CharSequence[] mFontSizesTag = new CharSequence[]{
            "特大号字",
            "大号字",
            "正常",
            "小号字",
            "特小号字"
    };
    /**
     * 字体大小
     */
    private WebSettings.TextSize[] mFontSizesValue = new WebSettings.TextSize[]{
            WebSettings.TextSize.LARGEST,
            WebSettings.TextSize.LARGER,
            WebSettings.TextSize.NORMAL,
            WebSettings.TextSize.SMALLER,
            WebSettings.TextSize.SMALLEST
    };
    private int currentFontSize = 2;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_web;
    }

    @Override
    protected void init() {
        super.init();
        Intent intent = getIntent();
        if (intent != null) {
            Bundle data = intent.getBundleExtra("data");
            if (data != null && !data.isEmpty()) {
                url = data.getString("url");
                mWeb.loadUrl(url);
            }
        }
        initProgress();
        initToolbar(mToolbar, "");
        initWeb();

    }

    /**
     * 初始化进度
     */
    private void initProgress() {
        mProgress.setMax(100);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mMenu = menu;
        setIconsVisible(menu, true);
        MenuInflater inflater = new SupportMenuInflater(this);
        inflater.inflate(R.menu.web_menu, menu);
        getCommentCount();
        isCollection();
        return true;
    }

    /**
     * 设置评论数
     */
    public void setCommentCount(Integer commentCount) {
        mMenu.findItem(R.id.comment).setTitle(String.format("%s 跟帖", commentCount));
    }

    /**
     * 获取评论数
     *
     * @return
     */
    private void getCommentCount() {
        BmobQuery<CommentBean> query = new BmobQuery<CommentBean>();
        query.addQueryKeys("commentUrl");
        query.addWhereEqualTo("commentUrl", url);
        query.count(CommentBean.class, new CountListener() {
            @Override
            public void done(Integer count, BmobException e) {
                if (e == null) {
                    commentCount = count;
                    setCommentCount(commentCount);
                    LogUtil.e("CommentCount:" + count + ":url" + url + ":" + mWeb.getOriginalUrl());
                } else {
                    Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

    /**
     * 解决menu不显示图标问题
     *
     * @param menu
     * @param flag
     */
    private void setIconsVisible(Menu menu, boolean flag) {
        //判断menu是否为空
        if (menu != null) {
            try {
                //如果不为空,就反射拿到menu的setOptionalIconsVisible方法
                Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                //暴力访问该方法
                method.setAccessible(true);
                //调用该方法显示icon
                method.invoke(menu, flag);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
            case R.id.comment:
                Bundle bundle = new Bundle();
                bundle.putString("url", mWeb.getOriginalUrl());
                goTo(CommentListActivity.class, bundle);
                break;
            case R.id.fontSize:
                showSelectFontSizeDialog();
                break;
            case R.id.nightMode:

                break;
            case R.id.collection:
                if (isCollection) {
                    cancleCollection();
                    isCollection = false;
                } else {
                    addCollection();
                    isCollection = true;
                }
                setCollectionStyle(isCollection);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }


    /**
     * 添加收藏
     *
     * @return
     */
    public void addCollection() {
        if (mApp.checkLogin()) {
            CollectBean collectBean = new CollectBean();
            //注意：不能调用gameScore.setObjectId("")方法
            collectBean.setUserId(mApp.mUser.getObjectId());
            collectBean.setCollectTitle(mWeb.getTitle());
            collectBean.setCollectUrl(mWeb.getUrl());
            collectBean.save(new SaveListener<String>() {
                @Override
                public void done(String objectId, BmobException e) {
                    if (e == null) {
                        setCollectionStyle(true);
                        Toast.makeText(WebActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                        Toast.makeText(WebActivity.this, "收藏失败", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            goTo(LoginActivity.class);
        }
    }

    /**
     * 设置收藏菜单的样式
     *
     * @param isAdd
     */
    public void setCollectionStyle(boolean isAdd) {
        LogUtil.e("设置收藏：" + isAdd);
        MenuItem item = mMenu.findItem(R.id.collection);
        if (isAdd) {
            item.setIcon(R.drawable.collection_select);
            item.setTitle("取消收藏");
        } else {
            item.setIcon(R.drawable.collection);
            item.setTitle("收藏");
        }
    }

    /**
     * 取消收藏
     */
    public void cancleCollection() {
        CollectBean gameScore = new CollectBean();
        gameScore.setObjectId(currentCollectId);
        gameScore.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Log.i("bmob", "成功");
                    setCollectionStyle(false);
                } else {
                    Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

    /**
     * 查询是否收藏
     *
     * @return
     */
    public void isCollection() {
        //只返回Person表的objectId这列的值
        BmobQuery<CollectBean> bmobQuery = new BmobQuery<CollectBean>();
        bmobQuery.addQueryKeys("objectId");
        bmobQuery.addWhereEqualTo("collectUrl", mWeb.getUrl());
        bmobQuery.findObjects(new FindListener<CollectBean>() {
            @Override
            public void done(List<CollectBean> object, BmobException e) {
                if (e == null) {
                    currentCollectId = object.get(0).getObjectId();
                    isCollection = true;
                    LogUtil.e("bmob成功：" + currentCollectId + "," + isCollection);
                } else {
                    currentCollectId = null;
                    isCollection = false;
                    LogUtil.e("bmob失败：" + e.getMessage() + "," + e.getErrorCode());
                }
                setCollectionStyle(isCollection);
            }
        });
    }

    /**
     * 显示选择字体的对话框
     */
    private void showSelectFontSizeDialog() {
        new AlertDialog.Builder(this)
                .setTitle("正文字号")
                .setSingleChoiceItems(mFontSizesTag, currentFontSize, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        currentFontSize = which;
                    }
                })
                .setCancelable(false)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setFontSize(mFontSizesValue[currentFontSize]);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create()
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mWeb.getSettings().setJavaScriptEnabled(true);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mWeb.getSettings().setJavaScriptEnabled(false);
    }

    /**
     * 初始化webView
     */
    private void initWeb() {
        mWeb.getSettings().setUseWideViewPort(true); //将图片调整到适合webview的大小
        mWeb.getSettings().setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        mWeb.getSettings().setLoadsImagesAutomatically(true); //支持自动加载图片
        mWeb.getSettings().setDefaultTextEncodingName("utf-8");//设置编码格式

        mWeb.setWebViewClient(new WebViewClient() {
            //防止查看网页时，调用系统浏览器或qit
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (Uri.parse(url).getHost().equals(url)) {
                    // This is my web site, so do not override; let my WebView load the page
                    return false;
                }
                // Otherwise, the link is not for a page on my site, so launch another Activity that handles URLs
                Bundle bundle = new Bundle();
                bundle.putString("url", url);
                goTo(WebActivity.class, bundle);
                return true;
            }

        });

        mWeb.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView webView, int i) {
                if (mProgress == null) {
                    return;
                }
                if (i <= 100) {
                    LogUtil.e("加载进度：" + i);
                    mProgress.setVisibility(View.VISIBLE);
                    mProgress.setProgress(i);
                } else {
                    mProgress.setVisibility(View.GONE);
                }

            }
        });

    }

    /**
     * 设置字体大小
     *
     * @param fontSize
     */
    public void setFontSize(WebSettings.TextSize fontSize) {
        if (fontSize == WebSettings.TextSize.LARGEST) {
            mWeb.getSettings().setTextSize(WebSettings.TextSize.LARGEST);
        } else if (fontSize == WebSettings.TextSize.LARGER) {
            mWeb.getSettings().setTextSize(WebSettings.TextSize.LARGER);
        } else if (fontSize == WebSettings.TextSize.SMALLER) {
            mWeb.getSettings().setTextSize(WebSettings.TextSize.SMALLER);
        } else if (fontSize == WebSettings.TextSize.SMALLEST) {
            mWeb.getSettings().setTextSize(WebSettings.TextSize.SMALLEST);
        } else {
            mWeb.getSettings().setTextSize(WebSettings.TextSize.NORMAL);
        }
    }

    @Override
    protected void onDestroy() {
        if (mWeb != null) {
            //清除网页访问留下的缓存
            //由于内核缓存是全局的因此这个方法不仅仅针对webview而是针对整个应用程序.
            mWeb.clearCache(true);

            //清除当前webview访问的历史记录
            //只会webview访问历史记录里的所有记录除了当前访问记录
            mWeb.clearHistory();
        }

        super.onDestroy();
    }

    @OnClick(R.id.send)
    public void onViewClicked() {
        if (mApp.checkLogin()) {
            String comment = mComment.getText().toString().trim();
            if (TextUtils.isEmpty(comment)) {
                Toast.makeText(WebActivity.this, "还没填写评论", Toast.LENGTH_SHORT).show();
                return;
            }
            sendComment(comment);
        } else {
            goTo(LoginActivity.class);
        }
    }

    /**
     * 发送评论
     *
     * @param comment 评论内容
     */
    private void sendComment(String comment) {
        CommentBean comments = new CommentBean();
        //注意：不能调用gameScore.setObjectId("")方法
        comments.setUserId(mApp.mUser.getObjectId());
        comments.setCommentContent(comment);
        comments.setCommentUrl(mWeb.getOriginalUrl());
        comments.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if (e == null) {
                    Toast.makeText(WebActivity.this, "评论成功", Toast.LENGTH_SHORT).show();
                    commentCount++;
                    setCommentCount(commentCount);
                    mComment.setText("");
                } else {
                    Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                    Toast.makeText(WebActivity.this, "评论失败", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
