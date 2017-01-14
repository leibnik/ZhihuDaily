package com.nyakokishi.zhihu.util;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by nyakokishi on 2016/3/31.
 */
public class DisplayUtil {
    public static int px2dip(Context context, float pxValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue/scale + 0.5);
    }
    public static int dip2px(Context context,float dipValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5);
    }
    public static int getScreenWidth(Context context){
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return metrics.widthPixels;
    }
    public static int getScreenHeight(Context context){
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return metrics.heightPixels;
    }
    public static float getScreenScale(Context context){
        return (float)getScreenWidth(context)/getScreenHeight(context);
    }
}
