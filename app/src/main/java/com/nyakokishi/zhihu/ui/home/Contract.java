package com.nyakokishi.zhihu.ui.home;

import com.nyakokishi.data.data.Theme;
import com.nyakokishi.data.data.Themes;
import com.nyakokishi.zhihu.base.BaseView;


/**
 * Created by nyakokishi on 2017/1/15.
 */
public interface Contract {
    interface View extends BaseView{
        void fillThemes(Themes themes);
    }
    interface Presenter{
        void getThemes();
    }
}
