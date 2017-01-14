package com.nyakokishi.zhihu.ui.theme.detail;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;

import butterknife.Bind;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

import com.nyakokishi.data.data.Story;
import com.nyakokishi.data.data.StoryDetail;
import com.nyakokishi.data.data.User;
import com.nyakokishi.zhihu.R;
import com.nyakokishi.zhihu.base.BaseActivity;
import com.nyakokishi.zhihu.constant.Constant;
import com.nyakokishi.zhihu.util.HttpUtil;
import com.nyakokishi.zhihu.widget.RevealBackgroundView;

/**
 * Created by Droidroid on 2016/3/25.
 */
public class StoryDetailActivity extends BaseActivity implements RevealBackgroundView.OnStateChangeListener {

    @Bind(R.id.coordinatorlayout)
    CoordinatorLayout mCoordinatorLayout;
    @Bind(R.id.revealview)
    RevealBackgroundView mRevealBackgroundView;
    @Bind(R.id.appbar)
    AppBarLayout mAppBarLayout;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.webview)
    WebView mWebView;

    private int[] location;
    public static final String TAG = "StoryDetailActivity";
    private Story mStory;

    @Override
    protected void initVariables() {
        super.initVariables();
        mStory = (Story) getIntent().getSerializableExtra("summary");
        location = getIntent().getIntArrayExtra("location");
    }

    @Override
    protected int setLayout() {
        return R.layout.activity_theme_story_detail;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mAppBarLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(" ");

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        // 开启DOM storage API 功能
        mWebView.getSettings().setDomStorageEnabled(true);
        // 开启database storage API功能
        mWebView.getSettings().setDatabaseEnabled(true);
        // 开启Application Cache功能
        mWebView.getSettings().setAppCacheEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mToolbar.setVisibility(View.VISIBLE);
                        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                onBackPressed();
                            }
                        });
                        mToolbar.setTitle("享受阅读的乐趣");
                        mToolbar.setBackgroundColor(getResources().getColor(R.color.blue));
                        getMenuInflater().inflate(R.menu.menu_detail, mToolbar.getMenu());
                        mToolbar.getMenu().getItem(0).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                Log.e(TAG, "收藏");
                                mStory.setUser(BmobUser.getCurrentUser(getApplicationContext(), User.class));
                                mStory.setType(Constant.TYPE_THEME_DETAIL);
                                mStory.save(getApplicationContext(), new SaveListener() {
                                    @Override
                                    public void onSuccess() {
                                        Snackbar.make(mCoordinatorLayout, "收藏成功", Snackbar.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onFailure(int i, String s) {

                                        Snackbar.make(mCoordinatorLayout, s, Snackbar.LENGTH_SHORT).show();
                                    }
                                });
                                return true;
                            }
                        });
                    }
                }, 500);
            }
        });

        showRevealBackground();
    }

    @Override
    protected void loadData() {
        if (HttpUtil.isNetworkAvailable(getApplicationContext())) {

            HttpUtil.get(Constant.CONTENT + mStory.getId(), new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    handleResponse(responseString);
                    mDBManager.saveDetail(responseString, mStory.getId());
                }
            });
        } else {
            handleResponse(mDBManager.getDetail(mStory.getId()));
        }
    }

    private void handleResponse(String responseString) {
        if (TextUtils.isEmpty(responseString)) {
            Snackbar.make(mCoordinatorLayout, "网络无连接", Snackbar.LENGTH_SHORT).show();
            return;
        }
        StoryDetail data = JSON.parseObject(responseString, StoryDetail.class);
        String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/css/news.css\" type=\"text/css\">";
        String html = "<html><head>" + css + "</head><body>" + data.getBody() + "</body></html>";
        html = html.replace("<div class=\"img-place-holder\">", "");
        mWebView.loadDataWithBaseURL("x-data://base", html, "text/html", "UTF-8", null);
    }

    private void showRevealBackground() {
        mRevealBackgroundView.setOnStateChangeListener(this);
        mRevealBackgroundView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                mRevealBackgroundView.startFromLocation(location);
                mRevealBackgroundView.getViewTreeObserver().removeOnPreDrawListener(this);
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(0, R.anim.slide_out_to_right);
    }

    @Override
    public void onStateChange(int state) {
        if (state == RevealBackgroundView.STATE_FINISHED) {
        }
    }
}
