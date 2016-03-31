# ZhihuDaily

###说明
* 该项目集成了Bmob SDK用于登陆，注册及收藏功能。
* 默认主题为蓝白色，可选择相册图片来自定义主题，通过对Bitmap的计算处理使能以CenterCrop方式设置背景。
* 首页背景，侧滑页背景及个人主页的AppBarLayout背景使用毛玻璃效果。
* 使用RecyclerView，全面替代ListView，无论是添加点击事件，滚动事件，header，footer
* 使用multiline-collapsingtoolbar代替了原生的collapsingtoolbar，使得Expanded状态的title能多行显示

###效果图
![](http://ww4.sinaimg.cn/mw690/b5405c76gw1f2gd9u7wv5j21kw0rndqi.jpg)
![](http://ww3.sinaimg.cn/mw690/b5405c76gw1f2gdaaw0sqj21kw0rkqgv.jpg)
![](http://ww1.sinaimg.cn/mw690/b5405c76gw1f2gdaquw36j21kw0rt4bq.jpg)

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

### API
[知乎日报API](https://github.com/leibnik/ZhihuDaily/blob/master/ZhihuDaily-api.md)

# License

    The MIT License (MIT)

    Copyright (c) 2016 leibnik

    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE.
