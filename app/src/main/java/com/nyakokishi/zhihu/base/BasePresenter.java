package com.nyakokishi.zhihu.base;

import android.content.Context;

import com.nyakokishi.zhihu.ui.daily.Contract;

/**
 * Created by nyakokishi on 2017/1/14.
 */

public class BasePresenter<T extends BaseView> {
    protected T view;
    public BasePresenter(T view){

    }
}
