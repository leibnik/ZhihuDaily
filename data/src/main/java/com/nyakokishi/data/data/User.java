package com.nyakokishi.data.data;

import cn.bmob.v3.BmobUser;

/**
 * Created by nyakokishi on 2016/3/22.
 */
public class User extends BmobUser{
    private String avatar;



    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
