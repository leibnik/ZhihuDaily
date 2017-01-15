package com.nyakokishi.data.data;

/**
 * Created by nyakokishi on 2016/3/22.
 */
public class Theme {
/*{
    "limit": 1000,
    "subscribed": [ ],
    "others": [
        // Themes单元
        {
            "color": 15007,
            "thumbnail": "http://pic3.zhimg.com/0e71e90fd6be47630399d63c58beebfc.jpg",
            "description": "了解自己和别人，了解彼此的欲望和局限。",
            "id": 13,
            "name": "日常心理学"
        }
    ]
}*/
    private String thumbnail;
    private String name;
    private String description;
    private int id;
    private int color;

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
