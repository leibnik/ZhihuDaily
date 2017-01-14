package com.nyakokishi.zhihu.ui.daily;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
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
import com.nyakokishi.data.data.Story;
import com.nyakokishi.data.data.Daily;
import com.nyakokishi.zhihu.ui.daily.detail.StoryDetailActivity;
import com.victor.loading.rotate.RotateLoading;

import org.apache.http.Header;

import java.util.List;

import butterknife.Bind;
import com.nyakokishi.zhihu.R;
import com.nyakokishi.zhihu.ui.MainActivity;
import com.nyakokishi.zhihu.base.BaseFragment;
import com.nyakokishi.zhihu.constant.Constant;
import com.nyakokishi.zhihu.util.DateUtil;
import com.nyakokishi.zhihu.util.HttpUtil;

/**
 * Created by nyakokishi on 2016/3/22.
 */
public class StoriesFragment extends BaseFragment implements Contract.View {

    @Bind(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @Bind(R.id.rotateloading)
    RotateLoading mRotateLoading;

    public static final String TAG = "StoriesFragment";
    private StoriesAdapter mAdapter;
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
        return R.layout.fragment_daily_stories;
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
        Daily newsADay = JSON.parseObject(responseString, Daily.class);
        List<Story> result = newsADay.getStories();
        Story story = new Story();
        story.setTitle(DateUtil.parse(date));
        story.setType(Constant.DAILY_DATE);
        result.add(0, story);
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
            mRecyclerView.setAdapter(new StoriesAdapter(mActivity, new Daily(), isColorTheme));

            ((MainActivity) mActivity).showSnackBar("网络无连接");
            return;
        }
        Daily newsADay = JSON.parseObject(responseString, Daily.class);
        date = newsADay.getDate();
        List<Story> result = newsADay.getStories();
        Story story = new Story();
        story.setTitle("今日最新");
        story.setType(Constant.DAILY_DATE);
        result.add(0, story);
        newsADay.setStories(result);
        mAdapter = new StoriesAdapter(getActivity(), newsADay, isColorTheme);
        mAdapter.setOnItemClickListener(new StoriesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Story data) {
                int[] location = new int[2];
                view.getLocationOnScreen(location);
                location[0] += view.getWidth() / 2;
                Intent intent = new Intent(mActivity, StoryDetailActivity.class);
                intent.putExtra("story", data);
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
