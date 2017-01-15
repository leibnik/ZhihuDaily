package com.nyakokishi.zhihu.ui.daily.detail;

import com.nyakokishi.data.data.StoryDetail;
import com.nyakokishi.zhihu.base.BaseView;

/**
 * Created by nyakokishi on 2017/1/15.
 */

public interface Contract {
    interface View extends BaseView{
        void fillStoryDetail(StoryDetail detail);
    }
    interface Presenter{
        void getStoryDetailById(int id);
    }
}
