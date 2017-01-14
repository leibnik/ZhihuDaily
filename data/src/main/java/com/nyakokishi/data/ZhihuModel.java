package com.nyakokishi.data;

import com.nyakokishi.data.data.StoryDetail;
import com.nyakokishi.data.data.Theme;
import com.nyakokishi.data.data.Themes;
import com.nyakokishi.data.data.Daily;

import rx.Observable;

/**
 * Created by nyakokishi on 2017/1/13.
 */

public class ZhihuModel {

    /**
     * 获取所有主题
     *
     * @return
     */
    public static Observable<Themes> getSubjects() {
        return HttpService.getInstance().api.getSubjects();
    }

    /**
     * 获取指定主题
     *
     * @param id
     * @return
     */
    public static Observable<Theme> getSubjectById(String id) {
        return HttpService.getInstance().api.getSubjectById(id);
    }

    /**
     * 获取今日最新日报
     *
     * @return
     */
    public static Observable<Daily> getTodayStories() {
        return HttpService.getInstance().api.getTodayStroies();
    }


    /**
     * 获取指定日期的日报
     * @param date
     * @return
     */
    public static Observable<Daily> getOlderStories(String date) {
        return HttpService.getInstance().api.getOldStroies(date);
    }

    /**
     * 获取新闻详情
     *
     * @param id
     * @return
     */
    public static Observable<StoryDetail> getStoryDetail(String id) {
        return HttpService.getInstance().api.getStroyDetail(id);
    }


}
