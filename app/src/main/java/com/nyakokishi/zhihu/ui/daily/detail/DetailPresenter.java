package com.nyakokishi.zhihu.ui.daily.detail;

import com.nyakokishi.data.ZhihuModel;
import com.nyakokishi.data.data.StoryDetail;
import com.nyakokishi.zhihu.base.BasePresenter;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by nyakokishi on 2017/1/15.
 */

public class DetailPresenter extends BasePresenter<Contract.View> implements Contract.Presenter{

    private Contract.View view;

    public DetailPresenter(Contract.View view) {
        super(view);
        this.view = view;
    }

    @Override
    public void getStoryDetailById(int id) {
        ZhihuModel.getStoryDetailById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<StoryDetail>() {
                    @Override
                    public void call(StoryDetail storyDetail) {
                        view.fillStoryDetail(storyDetail);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
    }
}
