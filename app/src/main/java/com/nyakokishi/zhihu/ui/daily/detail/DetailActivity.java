package com.nyakokishi.zhihu.ui.daily.detail;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import com.bumptech.glide.Glide;

import butterknife.Bind;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

import com.nyakokishi.data.data.DailyStory;
import com.nyakokishi.data.data.StoryDetail;
import com.nyakokishi.data.data.User;
import com.nyakokishi.zhihu.R;
import com.nyakokishi.zhihu.base.BaseActivity;
import com.nyakokishi.zhihu.constant.Constant;
import com.nyakokishi.zhihu.widget.RevealBackgroundView;

/**
 * Created by nyakokishi on 2016/3/25.
 */
public class DetailActivity extends BaseActivity implements Contract.View, RevealBackgroundView.OnStateChangeListener {

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
    private DailyStory mDailyStory;
    public static final String TAG = "DetailActivity";
    private DetailPresenter presenter = new DetailPresenter(this);

    @Override
    protected void initVariables() {
        super.initVariables();
        mDailyStory = (DailyStory) getIntent().getSerializableExtra("story");
        location = getIntent().getIntArrayExtra("location");
    }

    @Override
    protected int setLayout() {
        return R.layout.activity_daily_story_detail;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

        mAppbarLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getMenuInflater().inflate(R.menu.menu_detail, mToolbar.getMenu());

        mCollapsingToolbarLayout.setTitle(" ");
        mCollapsingToolbarLayout.setContentScrimColor(getResources().getColor(R.color.blue));
        mCollapsingToolbarLayout.setTitle(mDailyStory.getTitle());
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mToolbar.getMenu().getItem(0).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Log.e(TAG, "收藏");
                mDailyStory.setType(Constant.TYPE_DAY_DETAIL);
                mDailyStory.setUser(BmobUser.getCurrentUser(getApplicationContext(),User.class));
                mDailyStory.save(getApplicationContext(), new SaveListener() {
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
                Glide.with(getApplicationContext()).load(imgUrl).into(mImageView);
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
        presenter.getStoryDetailById(mDailyStory.getId());
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

    @Override
    public void fillStoryDetail(StoryDetail detail) {
        imgUrl = detail.getImage();
        String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/css/news.css\" type=\"text/css\">";
        String html = "<html><head>" + css + "</head><body>" + detail.getBody() + "</body></html>";
        html = html.replace("<div class=\"img-place-holder\">", "");
        mWebView.loadDataWithBaseURL("x-data://base", html, "text/html", "UTF-8", null);
    }
}
