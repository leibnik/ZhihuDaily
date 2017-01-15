package com.nyakokishi.data.data;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by nyakokishi on 2016/3/22.
 */
public class DailyStory extends BmobObject{
    /*"stories": [
        {
            "images": [
                "http://pic2.zhimg.com/312c4c0fdf80b81ab54a322cdb3beff9.jpg"
            ],
            "type": 0,
            "id": 8041772,
            "ga_prefix": "032322",
            "title": "深夜惊奇 · 糖丸救命"
        }
    ]*/
    private String title;
    private List<String> images;
    private int type;
    private int id;
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (this == o) return true;
        if (o instanceof DailyStory){
            DailyStory dailyStory = (DailyStory)o;
            if (dailyStory.id == this.id) return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
