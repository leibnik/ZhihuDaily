package com.nyakokishi.zhihu.ui.daily;

import com.nyakokishi.data.ZhihuModel;
import com.nyakokishi.data.data.Daily;
import com.nyakokishi.zhihu.base.BasePresenter;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by nyakokishi on 2017/1/14.
 */

public class StoriesPresenter extends BasePresenter<Contract.View> implements Contract.Presenter {

    public StoriesPresenter(Contract.View view) {
        super(view);
        this.view = view;
    }

    @Override
    public void getLatestDaily() {
        ZhihuModel.getLatestDaily()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Daily>() {
                    @Override
                    public void call(Daily daily) {
                        view.onFillDaily(daily, false);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
    }

    @Override
    public void getBeforeDaily(String date) {
        ZhihuModel.getBeforeDaily(date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Daily>() {
                    @Override
                    public void call(Daily daily) {
                        view.onFillDaily(daily, true);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
    }
}
