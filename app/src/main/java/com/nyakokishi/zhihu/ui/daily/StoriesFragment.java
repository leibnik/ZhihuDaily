package com.nyakokishi.zhihu.ui.daily;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nyakokishi.data.data.DailyStory;
import com.nyakokishi.data.data.Daily;
import com.nyakokishi.zhihu.ui.daily.detail.DetailActivity;
import com.nyakokishi.zhihu.util.DateUtil;
import com.victor.loading.rotate.RotateLoading;

import java.util.List;

import butterknife.Bind;

import com.nyakokishi.zhihu.R;
import com.nyakokishi.zhihu.ui.MainActivity;
import com.nyakokishi.zhihu.base.BaseFragment;
import com.nyakokishi.zhihu.constant.Constant;

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

    private StoriesPresenter presenter = new StoriesPresenter(this);

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
                ((MainActivity) mActivity).setRefreshEnable(layoutManager
                        .findFirstCompletelyVisibleItemPosition() == 0);
                if (!isLoading && mAdapter != null && layoutManager.findLastVisibleItemPosition() == 0) {
                    isRefreshing = true;
                    loadData();
                } else if (!isLoading && mAdapter != null && layoutManager.findLastVisibleItemPosition() == mAdapter.getItemCount() - 1) {
                    isRefreshing = false;
                    loadData();
                }
            }
        });
        mAdapter = new StoriesAdapter(getActivity(), isColorTheme);
        mAdapter.setOnItemClickListener(new StoriesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, DailyStory data) {
                int[] location = new int[2];
                view.getLocationOnScreen(location);
                location[0] += view.getWidth() / 2;
                Intent intent = new Intent(mActivity, DetailActivity.class);
                intent.putExtra("story", data);
                intent.putExtra("location", location);
                startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(mAdapter);

        ((MainActivity) mActivity).setRefreshEnable(true);

    }

    @Override
    public void loadData() {
        isLoading = true;
        mRotateLoading.start();
        if (isRefreshing)
            presenter.getLatestDaily();
        else
            presenter.getBeforeDaily(date);
    }

    @Override
    public void updateTheme(boolean isColorTheme, BitmapDrawable background) {
        super.isColorTheme = isColorTheme;
        if (mAdapter != null) {
            mAdapter.updateTheme(isColorTheme);
        }
    }

    @Override
    public void onFillDaily(Daily daily, boolean isLoadMore) {

        date = daily.getDate();

        List<DailyStory> result = daily.getStories();
        DailyStory dailyStory = new DailyStory();
        dailyStory.setType(Constant.DAILY_DATE);

        if (isLoadMore) {
            dailyStory.setTitle(DateUtil.parse(date));
            result.add(0, dailyStory);
            daily.setStories(result);
            mAdapter.loadMore(daily);
        } else {
            dailyStory.setTitle("今日最新");
            result.add(0, dailyStory);
            daily.setStories(result);
            mAdapter.refreshData(daily);
            isRefreshing = false;
        }
        mRotateLoading.stop();
        isLoading = false;
    }
}