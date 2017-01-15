package com.nyakokishi.zhihu.ui.theme;

import com.nyakokishi.data.ZhihuModel;
import com.nyakokishi.data.data.Theme;
import com.nyakokishi.zhihu.base.BasePresenter;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by nyakokishi on 2017/1/15.
 */

public class StoriesPresenter extends BasePresenter<Contract.View> implements Contract.Presenter {

    private Contract.View view ;

    public StoriesPresenter(Contract.View view) {
        super(view);
        this.view = view;
    }

    @Override
    public void getThemeStories(int themeId) {
        ZhihuModel.getSubjectById(themeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Theme>() {
                    @Override
                    public void call(Theme theme) {
                        view.onFillTheme(theme);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
    }
}
