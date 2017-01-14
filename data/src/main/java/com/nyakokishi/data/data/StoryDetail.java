package com.nyakokishi.data.data;

/**
 * Created by nyakokishi on 2016/3/22.
 */
public class StoryDetail {
    /*{
    "image_source": "Nanimo / CC BY-SA",
    "title": "深夜惊奇 · 糖丸救命",
    "image": "http://pic3.zhimg.com/ef735eb05b6c99634ac6861989867ae6.jpg",
    "share_url": "http://daily.zhihu.com/story/8041772",
    "js": [ ],
    "ga_prefix": "032322",
    "section": {
        "thumbnail": "http://pic2.zhimg.com/312c4c0fdf80b81ab54a322cdb3beff9.jpg",
        "id": 1,
        "name": "深夜惊奇"
    },
    "type": 0,
    "id": 8041772,
    "css": [
        "http://news-at.zhihu.com/css/news_qa.auto.css?v=77778"
    ]
    ,
    "body": "html文本"
}*/
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

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

    private String body;
    private String title;
    private String image;
    private int type;
    private int id;

}
