# ZhihuDaily

下载apk体验：[CSDN下载](http://download.csdn.net/detail/leibnik/9482723) | [百度云盘下载](http://pan.baidu.com/s/1hrT9UNq)
### 声明 
本项目所使用API均来自 [Izzy Leung](https://github.com/izzyleung)的[知乎日报API分析](https://github.com/izzyleung/ZhihuDailyPurify/wiki/知乎日报-API-分析) 。获取与共享之行为或有侵犯知乎权益的嫌疑。若被告知需停止共享与使用，本人会及时删除此页面与整个项目。 请您暸解相关情况，并遵守知乎协议。
### API
[知乎日报API](https://github.com/leibnik/ZhihuDaily/blob/master/ZhihuDaily-api.md)
###Tips
* 该项目集成了Bmob SDK用于登陆，注册，收藏，修改密码，修改用户名，上传头像，项目已含有Application Id无需自行申请。

###效果图
![](http://ww4.sinaimg.cn/bmiddle/b5405c76gw1f8xzrnsbjvj20xm1tz15k.jpg)
![](http://ww3.sinaimg.cn/bmiddle/b5405c76gw1f8xzrtc4jlj20xm1tz4fz.jpg)

![](http://ww1.sinaimg.cn/bmiddle/b5405c76gw1f8xzs1mxxjj20xm1tzqf2.jpg)
![](http://ww2.sinaimg.cn/bmiddle/b5405c76gw1f8xzsbxvfij20xm1tz4fz.jpg)


### 开源依赖库
* [ButterKnife](https://github.com/JakeWharton/butterknife)
* [Android-async-http](https://github.com/loopj/android-async-http)
* [Glide](https://github.com/bumptech/glide)
* [FastJson](https://github.com/alibaba/fastjson)
* [CircleImageView](https://github.com/hdodenhof/CircleImageView)
* [multiline-collapsingtoolbar](https://github.com/opacapp/multiline-collapsingtoolbar)
* [Loading](https://github.com/yankai-victor/Loading)

### 官方依赖库
* com.android.support:Recyclerview:23.1.1
* com.android.support:appcompat-v7:23.1.1
* com.android.support:design:23.1.1

###Gradle
```
dependencies {
    compile fileTree(dir: 'libs', include: ['*.jar'])
    testCompile 'junit:junit:4.12'
    compile 'com.android.support:appcompat-v7:23.1.1'
    compile 'com.android.support:design:23.1.1'
    compile 'de.hdodenhof:circleimageview:2.0.0'
    compile 'com.loopj.android:android-async-http:1.4.8'
    compile 'com.github.bumptech.glide:glide:3.7.0'
    compile 'com.jakewharton:butterknife:7.0.1'
    compile 'com.android.support:recyclerview-v7:23.1.1'
    compile 'net.opacapp:multiline-collapsingtoolbar:1.0.0'
    compile 'com.victor:lib:1.0.4'
    compile files('libs/fastjson-1.2.8.jar')
    //bmob-sdk ：Bmob的android sdk包
    compile 'cn.bmob.android:bmob-sdk:3.4.5'
    compile 'com.squareup.okhttp:okhttp:2.4.0'
    compile 'com.squareup.okio:okio:1.4.0'
}
```

