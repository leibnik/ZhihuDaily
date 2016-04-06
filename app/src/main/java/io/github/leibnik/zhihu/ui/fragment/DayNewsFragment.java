package io.github.leibnik.zhihu.ui.fragment;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.TextHttpResponseHandler;
import com.victor.loading.rotate.RotateLoading;

import org.apache.http.Header;

import java.util.List;

import butterknife.Bind;
import io.github.leibnik.zhihu.R;
import io.github.leibnik.zhihu.ui.activity.DayNewsDetailActivity;
import io.github.leibnik.zhihu.ui.activity.MainActivity;
import io.github.leibnik.zhihu.adapter.DaySummaryAdapter;
import io.github.leibnik.zhihu.base.BaseFragment;
import io.github.leibnik.zhihu.constant.Constant;
import io.github.leibnik.zhihu.entity.NewsADay;
import io.github.leibnik.zhihu.entity.Summary;
import io.github.leibnik.zhihu.util.DateUtil;
import io.github.leibnik.zhihu.util.HttpUtil;

/**
 * Created by Droidroid on 2016/3/22.
 */
public class DayNewsFragment extends BaseFragment {

    @Bind(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @Bind(R.id.rotateloading)
    RotateLoading mRotateLoading;

    public static final String TAG = "DayNewsFragment";
    private DaySummaryAdapter mAdapter;
    private String date;
    private boolean isLoading = false;
    private boolean isColorTheme;

    @Override
    public void initVariables() {
        super.initVariables();
        isColorTheme = getArguments().getBoolean("isColorTheme", false);
    }

    @Override
    public int setLayout() {
        return R.layout.fragment_day_news;
    }


    @Override
    public void initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRotateLoading.setLoadingColor(getResources().getColor(isColorTheme ? R.color.blue
                : android.R.color.white));
        final LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                Log.e(TAG, dx + "/" + dy);
                ((MainActivity) mActivity).setRefreshEnable(layoutManager
                        .findFirstCompletelyVisibleItemPosition() == 0);
                if (!isLoading && mAdapter != null && layoutManager.findLastVisibleItemPosition() == mAdapter.getItemCount() - 1) {
                    isLoading = true;
                    loadMoreData();
                }
            }
        });
        ((MainActivity) mActivity).setRefreshEnable(true);
    }

    private void loadMoreData() {

        if (HttpUtil.isNetworkAvailable(mActivity.getApplicationContext())) {

            HttpUtil.get(Constant.BEFORE + date, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    handleBeforeNews(responseString);
                }
            });
        } else {
            ((MainActivity) mActivity).showSnackBar("网络无连接");
        }

    }

    private void handleBeforeNews(String responseString) {
        NewsADay newsADay = JSON.parseObject(responseString, NewsADay.class);
        List<Summary> result = newsADay.getStories();
        Summary summary = new Summary();
        summary.setTitle(DateUtil.parse(date));
        summary.setType(Constant.DAILY_DATE);
        result.add(0, summary);
        newsADay.setStories(result);
        mAdapter.loadMore(newsADay);
        date = newsADay.getDate();
        isLoading = false;
    }

    @Override
    public void loadData() {
        if (HttpUtil.isNetworkAvailable(mActivity.getApplicationContext())) {
            HttpUtil.get(Constant.LATESTNEWS, new TextHttpResponseHandler() {

                @Override
                public void onStart() {
                    if (isFirstLoad) {
                        mRotateLoading.start();
                        isFirstLoad = false;
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    if (mRotateLoading.isStart()) {
                        mRotateLoading.stop();
                    }
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    if (mRotateLoading.isStart()) {
                        mRotateLoading.stop();
                    }
                    handleResponse(responseString);
                    mDBManger.saveDaySummary(responseString);
                }
            });
        } else {
            handleResponse(mDBManger.getDaySummary());
        }
    }

    private void handleResponse(String responseString) {
        if (TextUtils.isEmpty(responseString)) {
            mRecyclerView.setAdapter(new DaySummaryAdapter(mActivity, new NewsADay(), isColorTheme));

            ((MainActivity) mActivity).showSnackBar("网络无连接");
            return;
        }
        NewsADay newsADay = JSON.parseObject(responseString, NewsADay.class);
        date = newsADay.getDate();
        List<Summary> result = newsADay.getStories();
        Summary summary = new Summary();
        summary.setTitle("今日最新");
        summary.setType(Constant.DAILY_DATE);
        result.add(0, summary);
        newsADay.setStories(result);
        mAdapter = new DaySummaryAdapter(getActivity(), newsADay, isColorTheme);
        mAdapter.setOnItemClickListener(new DaySummaryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Summary data) {
                int[] location = new int[2];
                view.getLocationOnScreen(location);
                location[0] += view.getWidth() / 2;
                Intent intent = new Intent(mActivity, DayNewsDetailActivity.class);
                intent.putExtra("summary", data);
                intent.putExtra("location", location);
                startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void updateTheme(boolean isColorTheme, BitmapDrawable background) {
        super.isColorTheme = isColorTheme;
        if (mAdapter != null) {
            mAdapter.updateTheme(isColorTheme);
        }
    }
}
