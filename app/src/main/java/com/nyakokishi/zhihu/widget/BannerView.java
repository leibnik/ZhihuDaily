package com.nyakokishi.zhihu.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import com.nyakokishi.data.data.Daily;
import com.nyakokishi.zhihu.R;

public class BannerView extends FrameLayout implements OnClickListener {
    private List<Daily.TopStory> topStories;
    private List<View> views;
    private Context context;
    private ViewPager vp;
    private boolean isAutoPlay;
    private int currentItem;
    private int delayTime;
    private LinearLayout ll_dot;
    private List<ImageView> iv_dots;
    private int START_PLAY = 999;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == START_PLAY) {
                if (isAutoPlay) {
                    currentItem = currentItem % (topStories.size() + 1) + 1;
                    if (currentItem == 1) {
                        vp.setCurrentItem(currentItem, false);
                        handler.sendEmptyMessage(START_PLAY);
                    } else {
                        vp.setCurrentItem(currentItem);
                        handler.sendEmptyMessageDelayed(START_PLAY, 5000);
                    }
                } else {
                    handler.sendEmptyMessageDelayed(START_PLAY, 5000);
                }
            }
        }
    };
    private OnItemClickListener mItemClickListener;

    public BannerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        this.topStories = new ArrayList<>();
        initView();
    }

    private void initView() {
        views = new ArrayList<View>();
        iv_dots = new ArrayList<ImageView>();
        delayTime = 2000;
    }

    public BannerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerView(Context context) {
        this(context, null);
    }

    public void setData(List<Daily.TopStory> topStories) {
        this.topStories = topStories;
        reset();
    }

    private void reset() {
        views.clear();
        initUI();
    }

    private void initUI() {
        View view = LayoutInflater.from(context).inflate(
                R.layout.view_banner_layout, this, true);
        vp = (ViewPager) view.findViewById(R.id.vp);
        ll_dot = (LinearLayout) view.findViewById(R.id.ll_dot);
        ll_dot.removeAllViews();

        int len = topStories.size();
        for (int i = 0; i < len; i++) {
            ImageView iv_dot = new ImageView(context);
            if (i == 0) {
                iv_dot.setImageResource(R.drawable.dot_focus);
            } else {
                iv_dot.setImageResource(R.drawable.dot_blur);
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.leftMargin = 5;
            params.rightMargin = 5;
            ll_dot.addView(iv_dot, params);
            iv_dots.add(iv_dot);
        }

        for (int i = 0; i <= len + 1; i++) {
            View fm = LayoutInflater.from(context).inflate(
                    R.layout.view_banner_content_layout, null);
            ImageView iv = (ImageView) fm.findViewById(R.id.iv_title);
            TextView tv_title = (TextView) fm.findViewById(R.id.tv_title);
            iv.setScaleType(ScaleType.CENTER_CROP);
//            iv.setBackgroundResource(R.drawable.loading1);
            if (i == 0) {
                Glide.with(context).load(topStories.get(len - 1).getImage()).into(iv);
                tv_title.setText(topStories.get(len - 1).getTitle());
            } else if (i == len + 1) {
                Glide.with(context).load(topStories.get(0).getImage()).into(iv);
                tv_title.setText(topStories.get(0).getTitle());
            } else {
                Glide.with(context).load(topStories.get(i - 1).getImage()).into(iv);
                tv_title.setText(topStories.get(i - 1).getTitle());
            }
            fm.setOnClickListener(this);
            views.add(fm);
        }
        vp.setAdapter(new MyPagerAdapter());
        vp.setFocusable(true);
        vp.setCurrentItem(1);
        currentItem = 1;
        vp.addOnPageChangeListener(new MyOnPageChangeListener());
        startPlay();
    }

    public void startPlay() {
        if (isAutoPlay) {
            stopPlay();
        }
        isAutoPlay = true;
        handler.sendEmptyMessageDelayed(START_PLAY, 3000);
    }

    public void stopPlay() {
        handler.removeMessages(START_PLAY);
    }



    class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(views.get(position));
            return views.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

    }

    class MyOnPageChangeListener implements OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {
            switch (arg0) {
                case 1:
                    isAutoPlay = false;
                    break;
                case 2:
                    isAutoPlay = true;
                    break;
                case 0:
                    if (vp.getCurrentItem() == 0) {
                        vp.setCurrentItem(topStories.size(), false);
                    } else if (vp.getCurrentItem() == topStories.size() + 1) {
                        vp.setCurrentItem(1, false);
                    }
                    currentItem = vp.getCurrentItem();
                    isAutoPlay = true;
                    break;
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int arg0) {
            for (int i = 0; i < iv_dots.size(); i++) {
                if (i == arg0 - 1) {
                    iv_dots.get(i).setImageResource(R.drawable.dot_focus);
                } else {
                    iv_dots.get(i).setImageResource(R.drawable.dot_blur);
                }
            }

        }

    }


    public void setOnItemClickListener(OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public interface OnItemClickListener {
        public void click(View v, Daily.TopStory entity);
    }

    @Override
    public void onClick(View v) {
        if (mItemClickListener != null) {
            Daily.TopStory entity = topStories.get(vp.getCurrentItem() - 1);
            mItemClickListener.click(v, entity);
        }
    }
}
