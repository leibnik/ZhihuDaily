package com.nyakokishi.zhihu.ui.theme;

import com.nyakokishi.data.data.ThemeStory;
import com.nyakokishi.zhihu.base.BaseView;


/**
 * Created by nyakokishi on 2017/1/15.
 */
public interface Contract {
    interface View extends BaseView {
        void onFillTheme(ThemeStory themeStory);
    }

    interface Presenter {
        void getThemeStories(int themeId);
    }
}
