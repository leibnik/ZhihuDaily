package com.nyakokishi.data.data;

import java.util.List;

/**
 * Created by nyakokishi on 2016/3/22.
 */
public class Daily {
    /*{
    "date": "20160323",
    "stories": [
        {
            "images": [
                "http://pic2.zhimg.com/312c4c0fdf80b81ab54a322cdb3beff9.jpg"
            ],
            "type": 0,
            "id": 8041772,
            "ga_prefix": "032322",
            "title": "深夜惊奇 · 糖丸救命"
        }
    ],
    "top_stories": [
        {
            "image": "http://pic3.zhimg.com/260b27fa7d404e19cd99620d67e631a2.jpg",
            "type": 0,
            "id": 8037997,
            "ga_prefix": "032321",
            "title": "当一个理性刻薄的君王有了个耽于玩乐的儿子"
        }
    ]
}*/
    private String date;
    private List<DailyStory> stories;
    private List<TopStory> top_stories;

    public List<TopStory> getTopStories() {
        return top_stories;
    }

    public void setTop_stories(List<TopStory> top_stories) {
        this.top_stories = top_stories;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<DailyStory> getStories() {
        return stories;
    }

    public void setStories(List<DailyStory> stories) {
        this.stories = stories;
    }


    public static class TopStory {
        private String title;
        private String image;
        private int type;
        private int id;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
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
    }
}
