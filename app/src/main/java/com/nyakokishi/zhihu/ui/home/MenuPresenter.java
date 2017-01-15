package com.nyakokishi.zhihu.ui.home;

import com.nyakokishi.data.ZhihuModel;
import com.nyakokishi.data.data.Theme;
import com.nyakokishi.data.data.Themes;
import com.nyakokishi.zhihu.base.BasePresenter;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by nyakokishi on 2017/1/15.
 */

public class MenuPresenter extends BasePresenter<Contract.View> implements Contract.Presenter{

    private Contract.View view;

    public MenuPresenter(Contract.View view) {
        super(view);
        this.view = view;
    }

    @Override
    public void getThemes() {
        ZhihuModel.getThemes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Themes>() {
                    @Override
                    public void call(Themes theme) {
                        view.fillThemes(theme);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
    }
}
