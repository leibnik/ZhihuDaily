package com.nyakokishi.zhihu.ui.daily;

import com.nyakokishi.data.data.Daily;
import com.nyakokishi.zhihu.base.BaseView;

/**
 * Created by nyakokishi on 2017/1/14.
 */

public interface Contract {
    interface View extends BaseView{
        void onFillDaily(Daily daily, boolean isLoadMore);
    }
    interface Presenter{
        void getLatestDaily();
        void getBeforeDaily(String date);
    }
}
