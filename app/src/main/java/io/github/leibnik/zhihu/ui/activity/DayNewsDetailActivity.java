package io.github.leibnik.zhihu.ui.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.loopj.android.http.TextHttpResponseHandler;


import net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout;

import org.apache.http.Header;

import butterknife.Bind;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;
import io.github.leibnik.zhihu.R;
import io.github.leibnik.zhihu.base.BaseActivity;
import io.github.leibnik.zhihu.constant.Constant;
import io.github.leibnik.zhihu.entity.Detail;
import io.github.leibnik.zhihu.entity.Summary;
import io.github.leibnik.zhihu.entity.User;
import io.github.leibnik.zhihu.util.HttpUtil;
import io.github.leibnik.zhihu.view.RevealBackgroundView;

/**
 * Created by Droidroid on 2016/3/25.
 */
public class DayNewsDetailActivity extends BaseActivity implements RevealBackgroundView.OnStateChangeListener {

    @Bind(R.id.coordinatorlayout)
    CoordinatorLayout mCoordinatorLayout;
    @Bind(R.id.revealview)
    RevealBackgroundView mRevealBackgroundView;
    @Bind(R.id.appbar)
    AppBarLayout mAppbarLayout;
    @Bind(R.id.collapsinglayout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @Bind(R.id.imageview)
    ImageView mImageView;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.webview)
    WebView mWebView;

    private int[] location;
    private String imgUrl;
    private Summary mSummary;
    public static final String TAG = "DayNewsDetailActivity";

    @Override
    protected void initVariables() {
        super.initVariables();
        mSummary = (Summary) getIntent().getSerializableExtra("summary");
        location = getIntent().getIntArrayExtra("location");
    }

    @Override
    protected int setLayout() {
        return R.layout.activity_day_news;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

        mAppbarLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        setSupportActionBar(mToolbar);


        mCollapsingToolbarLayout.setTitle(" ");
        mCollapsingToolbarLayout.setContentScrimColor(getResources().getColor(R.color.blue));

        mWebView.setVisibility(View.INVISIBLE);
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
                mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                Glide.with(getApplicationContext()).load(imgUrl).listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }


                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        mCollapsingToolbarLayout.setTitle(mSummary.getTitle());
                        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                onBackPressed();
                            }
                        });
                        getMenuInflater().inflate(R.menu.menu_detail, mToolbar.getMenu());
                        mToolbar.getMenu().getItem(0).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                Log.e(TAG, "收藏");
                                mSummary.setType(Constant.TYPE_DAY_DETAIL);
                                mSummary.setUser(BmobUser.getCurrentUser(getApplicationContext(),User.class));
                                mSummary.save(getApplicationContext(), new SaveListener() {
                                    @Override
                                    public void onSuccess() {
                                        Snackbar.make(mCoordinatorLayout,"收藏成功",Snackbar.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onFailure(int i, String s) {
                                        Snackbar.make(mCoordinatorLayout,"收藏成功",Snackbar.LENGTH_SHORT).show();

                                    }
                                });
                                return true;
                            }
                        });
                        return false;
                    }
                }).into(mImageView);
                mWebView.setVisibility(View.VISIBLE);
            }
        });
        showRevealBackground();
    }

    private void showRevealBackground() {
        mRevealBackgroundView.setOnStateChangeListener(this);
        mRevealBackgroundView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                mRevealBackgroundView.getViewTreeObserver().removeOnPreDrawListener(this);
                mRevealBackgroundView.startFromLocation(location);
                return true;
            }
        });
    }

    @Override
    protected void loadData() {
        if (HttpUtil.isNetworkAvailable(getApplicationContext())) {


            HttpUtil.get(Constant.CONTENT + mSummary.getId(), new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    handleResponse(responseString);
                    mDBManager.saveDetail(responseString, mSummary.getId());
                }
            });
        } else {
            handleResponse(mDBManager.getDetail(mSummary.getId()));
        }
    }

    private void handleResponse(String responseString) {
        if (TextUtils.isEmpty(responseString)){
            Snackbar.make(mCoordinatorLayout, "网络无连接", Snackbar.LENGTH_SHORT).show();
            return;
        }
        Detail data = JSON.parseObject(responseString, Detail.class);
        imgUrl = data.getImage();
        String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/css/news.css\" type=\"text/css\">";
        String html = "<html><head>" + css + "</head><body>" + data.getBody() + "</body></html>";
        html = html.replace("<div class=\"img-place-holder\">", "");
        mWebView.loadDataWithBaseURL("x-data://base", html, "text/html", "UTF-8", null);
    }

    @Override
    public void onStateChange(int state) {
        if (state == RevealBackgroundView.STATE_FINISHED) {
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(0, R.anim.slide_out_to_right);
    }
}
