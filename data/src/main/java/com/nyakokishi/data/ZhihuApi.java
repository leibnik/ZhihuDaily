package com.nyakokishi.data;

import com.nyakokishi.data.data.StoryDetail;
import com.nyakokishi.data.data.Subject;
import com.nyakokishi.data.data.Subjects;
import com.nyakokishi.data.data.Today;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by nyakokishi on 2017/1/13.
 */
public interface ZhihuApi {

    @GET("themes")
    Observable<Subjects> getSubjects();

    @GET("news/latest")
    Observable<Today> getTodayStroies();

    @GET("news/before/{date}")
    Observable<Today> getOldStroies(@Path("date") String date);

    @GET("theme/{id}")
    Observable<Subject> getSubjectById(@Path("id") String id);

    @GET("news/{id}")
    Observable<StoryDetail> getStroyDetail(@Path("id") String id);
}
