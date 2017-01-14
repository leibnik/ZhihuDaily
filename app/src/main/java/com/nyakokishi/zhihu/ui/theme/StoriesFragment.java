package com.nyakokishi.zhihu.ui.theme;

import android.content.Intent;
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
import com.nyakokishi.data.data.Theme;
import com.nyakokishi.zhihu.ui.theme.detail.StoryDetailActivity;
import com.victor.loading.rotate.RotateLoading;

import org.apache.http.Header;

import butterknife.Bind;
import com.nyakokishi.zhihu.R;
import com.nyakokishi.zhihu.base.BaseFragment;
import com.nyakokishi.zhihu.constant.Constant;
import com.nyakokishi.zhihu.ui.MainActivity;
import com.nyakokishi.zhihu.util.HttpUtil;

/**
 * Created by Droidroid on 2016/3/23.
 */
public class StoriesFragment extends BaseFragment {

    @Bind(R.id.news_lv)
    RecyclerView mRecyclerView;
    @Bind(R.id.rotateloading)
    RotateLoading mRotateLoading;

    public static final String TAG = "StoriesFragment";
    private int id;
    private StoriesAdapter mAdapter;

    @Override
    public void initVariables() {
        super.initVariables();
        id = getArguments().getInt("id", 0);
        isColorTheme = getArguments().getBoolean("isColorTheme", false);
    }

    @Override
    public int setLayout() {
        return R.layout.fragment_theme_stories;
    }

    @Override
    public void initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRotateLoading.setLoadingColor(getResources().getColor(isColorTheme ? R.color.blue
                : android.R.color.white));
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                Log.e(TAG, dx + "/" + dy);
                ((MainActivity) mActivity).setRefreshEnable(linearLayoutManager
                        .findFirstCompletelyVisibleItemPosition() == 0);
            }
        });
        ((MainActivity) mActivity).setRefreshEnable(true);
    }

    @Override
    public void loadData() {
        super.loadData();
        if (HttpUtil.isNetworkAvailable(mActivity.getApplicationContext())) {
            HttpUtil.get(Constant.THEMENEWS + id, new TextHttpResponseHandler() {
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
                    parseResult(responseString);
                    mDBManger.saveThemeSummary(responseString, id);
                }
            });
        } else {
            parseResult(mDBManger.getThemeSummary(id));
        }
    }

    private void parseResult(String responseString) {
        if (TextUtils.isEmpty(responseString)) {
            mRecyclerView.setAdapter(new StoriesAdapter(mActivity, new Theme()));
            ((MainActivity) mActivity).showSnackBar("网络无连接");
            return;
        }
        Theme newsTheme = JSON.parseObject(responseString, Theme.class);
        mAdapter = new StoriesAdapter(mActivity, newsTheme);
        mAdapter.setOnItemClickListener(new StoriesAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View v, Story data) {
                int[] location = new int[2];
                v.getLocationOnScreen(location);
                location[0] += v.getWidth() / 2;
                Intent intent = new Intent(mActivity, StoryDetailActivity.class);
                intent.putExtra("summary", data);
                intent.putExtra("location", location);
                startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }


}
