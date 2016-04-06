# ZhihuDaily

下载apk体验：[CSDN下载](http://download.csdn.net/detail/leibnik/9482723) | [百度云盘下载](http://pan.baidu.com/s/1hrT9UNq)

###欢迎
* star一下表示支持
* fork一下可以参与进来完善这个项目
* issue尽管提

###Tips
* 该项目集成了Bmob SDK用于登陆，注册，收藏，修改密码，修改用户名，上传头像，项目已含有Application Id无需自行申请。
* 默认主题为蓝白色，可选择相册图片来自定义主题，通过对Bitmap进行裁剪使能以CenterCrop方式设置背景
* 对首页背景图片，侧滑页背景图片及个人主页的AppBarLayout背景图片进行模糊处理，采用了流传较广的模糊算法FastBlur
* 使用RecyclerView，全面替代ListView，无论是添加点击事件，滚动监听事件，header，footer
* SwipeRefreshLayout配合RecyclerView.OnScrollListener实现下拉刷新，footer视图配合RecyclerView.OnScrollListener实现上拉加载
* 使用multiline-collapsingtoolbar代替了原生的collapsingtoolbar，使得Expanded状态的title能多行显示
* 对图片进行模糊处理耗时在几十到几百毫秒不等，因此开启异步任务进行模糊处理，如个人主页的AppBarLayout需要根据头像生成模糊的背景图片
* 上传图片成功并且Glide完成新头像的加载，需要更换AppBarLayout背景图片，可在使用Glide加载新头像时添加RequestListener，取得Drawble后开启异步任务进行模糊处理

###效果图
![](http://ww3.sinaimg.cn/mw690/b5405c76gw1f2gxioolg8j21bu0v2al1.jpg)
![](http://ww1.sinaimg.cn/mw690/b5405c76gw1f2gxiazwj9j21bq0v2dwo.jpg)
![](http://ww1.sinaimg.cn/mw690/b5405c76gw1f2gxi50polj21bu0v2n8o.jpg)
![](http://ww3.sinaimg.cn/mw690/b5405c76gw1f2gxikcd56j21bq0v27jk.jpg)
![](http://ww2.sinaimg.cn/mw690/b5405c76gw1f2gxig3efij21bk0v2aks.jpg)


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
