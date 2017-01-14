package com.nyakokishi.data;

import com.nyakokishi.data.data.StoryDetail;
import com.nyakokishi.data.data.Theme;
import com.nyakokishi.data.data.Themes;
import com.nyakokishi.data.data.Daily;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by nyakokishi on 2017/1/13.
 */
public interface ZhihuApi {

    @GET("themes")
    Observable<Themes> getSubjects();

    @GET("news/latest")
    Observable<Daily> getTodayStroies();

    @GET("news/before/{date}")
    Observable<Daily> getOldStroies(@Path("date") String date);

    @GET("theme/{id}")
    Observable<Theme> getSubjectById(@Path("id") String id);

    @GET("news/{id}")
    Observable<StoryDetail> getStroyDetail(@Path("id") String id);
}
