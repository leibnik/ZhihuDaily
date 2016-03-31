# ZhihuDaily
###特性
* 该项目集成了Bmob SDK用于登陆，注册及收藏功能。
* 默认主题为蓝白色，可选择相册图片来自定义主题，采用类似CenterCrop方式设置背景。
* 首页背景，侧滑页背景及个人主页的AppBarLayout背景使用毛玻璃效果。
* 使用RecyclerView，全面替代ListView，无论是添加点击事件，滚动事件，header，footer

###效果图
![](http://ww4.sinaimg.cn/mw690/b5405c76gw1f2gd9u7wv5j21kw0rndqi.jpg)
![](http://ww3.sinaimg.cn/mw690/b5405c76gw1f2gdaaw0sqj21kw0rkqgv.jpg)
![](http://ww1.sinaimg.cn/mw690/b5405c76gw1f2gdaquw36j21kw0rt4bq.jpg)

###依赖库
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
    compile 'com.android.support:cardview-v7:23.1.1'
    compile 'com.android.support:recyclerview-v7:23.1.1'
    compile 'me.grantland:autofittextview:0.2.+'
    compile 'net.opacapp:multiline-collapsingtoolbar:1.0.0'
    compile 'com.victor:lib:1.0.4'
    compile files('libs/fastjson-1.2.8.jar')
    //bmob-sdk ：Bmob的android sdk包
    compile 'cn.bmob.android:bmob-sdk:3.4.5'
    compile 'com.squareup.okhttp:okhttp:2.4.0'
    compile 'com.squareup.okio:okio:1.4.0'
}
```
