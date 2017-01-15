package com.nyakokishi.data;

import com.nyakokishi.data.data.Theme;
import com.nyakokishi.data.data.StoryDetail;
import com.nyakokishi.data.data.ThemeStory;
import com.nyakokishi.data.data.Daily;
import com.nyakokishi.data.data.Themes;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by nyakokishi on 2017/1/13.
 */
public interface ZhihuApi {

    @GET("themes")
    Observable<Themes> getThemes();

    @GET("news/latest")
    Observable<Daily> getLatestDaily();

    @GET("news/before/{date}")
    Observable<Daily> getBeforeDaily(@Path("date") String date);

    @GET("theme/{id}")
    Observable<ThemeStory> getThemeById(@Path("id") int id);

    @GET("news/{id}")
    Observable<StoryDetail> getStoryDetailById(@Path("id") int id);
}
