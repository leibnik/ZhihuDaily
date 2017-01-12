package com.nyakokishi.data;

import com.nyakokishi.data.data.StoryDetail;
import com.nyakokishi.data.data.Subject;
import com.nyakokishi.data.data.Subjects;
import com.nyakokishi.data.data.Today;

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
    public static Observable<Subjects> getSubjects() {
        return HttpService.getInstance().api.getSubjects();
    }

    /**
     * 获取指定主题
     *
     * @param id
     * @return
     */
    public static Observable<Subject> getSubjectById(String id) {
        return HttpService.getInstance().api.getSubjectById(id);
    }

    /**
     * 获取今日最新日报
     *
     * @return
     */
    public static Observable<Today> getTodayStories() {
        return HttpService.getInstance().api.getTodayStroies();
    }


    /**
     * 获取指定日期的日报
     * @param date
     * @return
     */
    public static Observable<Today> getOlderStories(String date) {
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
