package com.nyakokishi.zhihu.base;

/**
 * Created by nyakokishi on 2017/1/14.
 */

public class BasePresenter<T extends BaseView> {
    protected T view;
    public BasePresenter(T view){

    }
}
