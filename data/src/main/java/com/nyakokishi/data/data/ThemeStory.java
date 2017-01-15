package com.nyakokishi.data.data;

import java.util.List;

/**
 * Created by nyakokishi on 2016/3/24.
 */
public class ThemeStory {
    /*{
    "stories": [
        {
            "images": [
                "http://pic3.zhimg.com/30d7cc448a836acd245d06547de551a2_t.jpg"
            ],
            "type": 1,
            "id": 7086655,
            "title": "摩根·施奈德林专访：「我看过坎通纳的视频！我超喜欢哒！」"
        },
        {
            "type": 2,
            "id": 7082061,
            "title": "ESPN：NBA 今夏自由市场十大新援"
        }
    ],
    "description": "关注体育，不吵架。",
    "background": "http://pic1.zhimg.com/6bbd96bfcbe6f407227f9db36cbbaac0.jpg",
    "color": 16046124,
    "name": "体育日报",
    "image": "http://pic2.zhimg.com/2f7d4a0ab3b03b5d6f514197903bab62.jpg",
    "editors": [
        {
            "url": "http://www.zhihu.com/people/martias",
            "bio": "米兰球迷，已克死克罗地亚，智利，法国。",
            "id": 62,
            "avatar": "http://pic3.zhimg.com/591ff8b9a_m.jpg",
            "name": "martias"
        }
    ],
    "image_source": ""
}*/
    private List<DailyStory> stories;
    private String description;
    private String background;
    private String image;
    private String name;

    public List<DailyStory> getStories() {
        return stories;
    }

    public void setStories(List<DailyStory> stories) {
        this.stories = stories;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
