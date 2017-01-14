package com.nyakokishi.zhihu.task;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.ByteArrayOutputStream;

import com.nyakokishi.zhihu.util.BitmapUtil;
import com.nyakokishi.zhihu.util.DisplayUtil;

/**
 * Created by nyakokishi on 2016/3/31.
 */
public class TaskBlurBackground extends AsyncTask<Void, Integer, Bitmap> {

    Bitmap mBitmap;

    public TaskBlurBackground(Context context, String filePath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 4;
        mBitmap = BitmapFactory.decodeFile(filePath, options);
        mBitmap = cropBitmap(context, mBitmap);
    }

    public TaskBlurBackground(Context context, int resourceId) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 4;
        mBitmap = BitmapFactory.decodeResource(context.getResources(), resourceId, options);
    }

    public TaskBlurBackground(Bitmap bitmap) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 4;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, baos);
        byte[] bytes = baos.toByteArray();
        mBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
    }

    @Override
    protected Bitmap doInBackground(Void... params) {
        mBitmap = BitmapUtil.fastBlur(mBitmap, 10, false);
        return mBitmap;
    }

    // centerCrop算法
    private Bitmap cropBitmap(Context context, Bitmap mBitmap) {
        float screenWidth = DisplayUtil.getScreenWidth(context);
        float screenHeight = DisplayUtil.getScreenHeight(context);
        Bitmap bitmap;
        int x = 0;
        int y = 0;
        if (mBitmap.getWidth() > screenWidth && mBitmap.getHeight() > screenHeight) {
            x = (int) ((mBitmap.getWidth() - screenWidth - 0.5) / 2.0);
            y = (int) ((mBitmap.getHeight() - screenHeight - 0.5) / 2.0);
            bitmap = Bitmap.createBitmap(mBitmap, x, y, (int) screenWidth
                    , (int) screenHeight);
            return bitmap;
        } else {
            if (mBitmap.getWidth() / (float) mBitmap.getHeight() < screenWidth / screenHeight) {
                x = 0;
                y = (int) ((mBitmap.getHeight() - mBitmap.getWidth() * screenHeight / screenWidth - 0.5) / 2.0);
                bitmap = Bitmap.createBitmap(mBitmap, x, y, mBitmap.getWidth()
                        , (int) (mBitmap.getWidth() * screenHeight / screenWidth - 0.5));
                return bitmap;
            } else {
                y = 0;
                x = (int) ((mBitmap.getWidth() - mBitmap.getHeight() * screenWidth / screenHeight - 0.5) / 2.0);
                bitmap = Bitmap.createBitmap(mBitmap, x, y, (int) (mBitmap.getHeight() * screenWidth / screenHeight - 0.5)
                        , mBitmap.getHeight());
                return bitmap;
            }
        }
    }

}
