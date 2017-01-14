package com.nyakokishi.zhihu.constant;

/**
 * Created by nyakokishi on 2016/3/22.
 */
public interface Constant {
    String BASEURL = "http://news-at.zhihu.com/api/4/";
    String START = BASEURL + "start-image/1080*1776";
    String THEMES = BASEURL + "themes";
    String LATESTNEWS = BASEURL + "news/latest";
    String BEFORE = BASEURL + "news/before/";
    String THEMENEWS = BASEURL + "theme/";
    String CONTENT = BASEURL + "news/";
    int DAILY_DATE = 233;
    int DAY_SUMMARY_COLUMN = Integer.MAX_VALUE;
    int THEME_SUMMARY_COLUMN = 100000000;
    int MENU_COLUMN = 666;
    String APPLICATION_ID = "0dd7abc33c4d53c35ca5145f376aa6e8";
    int TYPE_DAY_DETAIL = 0;
    int TYPE_THEME_DETAIL = 1;
    int ALBUTM = 888;
    int EDIT_NAME = 222;
    int RESET_PASSWORD = 333;
}
