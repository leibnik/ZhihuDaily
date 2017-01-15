package com.nyakokishi.data;

import com.nyakokishi.data.data.StoryDetail;
import com.nyakokishi.data.data.Theme;
import com.nyakokishi.data.data.ThemeStory;
import com.nyakokishi.data.data.Daily;
import com.nyakokishi.data.data.Themes;

import rx.Observable;

/**
 * Created by nyakokishi on 2017/1/13.
 */

public class ZhihuModel {

    /**
     * 获取所有主题
     *
     * @returnss
     */
    public static Observable<Themes> getThemes() {
        return HttpService.getInstance().api.getThemes();
    }

    /**
     * 获取指定主题
     *
     * @param id
     * @return
     */
    public static Observable<ThemeStory> getThemeById(int id) {
        return HttpService.getInstance().api.getThemeById(id);
    }

    /**
     * 获取今日最新日报
     *
     * @return
     */
    public static Observable<Daily> getLatestDaily() {
        return HttpService.getInstance().api.getLatestDaily();
    }


    /**
     * 获取指定日期的日报
     * @param date
     * @return
     */
    public static Observable<Daily> getBeforeDaily(String date) {
        return HttpService.getInstance().api.getBeforeDaily(date);
    }

    /**
     * 获取新闻详情
     *
     * @param id
     * @return
     */
    public static Observable<StoryDetail> getStoryDetailById(int id) {
        return HttpService.getInstance().api.getStoryDetailById(id);
    }


}
