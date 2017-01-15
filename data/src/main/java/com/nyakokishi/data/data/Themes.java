package com.nyakokishi.data.data;

import java.util.List;

/**
 * Created by nyakokishi on 2017/1/15.
 */
public class Themes {
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
    private long limit;
    private List<Theme> others;

    public long getLimit() {
        return limit;
    }

    public void setLimit(long limit) {
        this.limit = limit;
    }

    public List<Theme> getOthers() {
        return others;
    }

    public void setOthers(List<Theme> others) {
        this.others = others;
    }
}
