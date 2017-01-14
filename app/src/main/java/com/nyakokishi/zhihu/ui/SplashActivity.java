package com.nyakokishi.zhihu.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import butterknife.Bind;

import com.nyakokishi.zhihu.R;
import com.nyakokishi.zhihu.base.BaseActivity;
import com.nyakokishi.zhihu.ui.MainActivity;

/**
 * Created by nyakokishi on 2016/3/22.
 */
public class SplashActivity extends BaseActivity {

    @Bind(R.id.background)
    ImageView backgroundIv;
    @Bind(R.id.logo)
    ImageView logoIv;

    @Override
    protected void initVariables() {
    }

    @Override
    protected int setLayout() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        logoIv.startAnimation(createLogoAnim());
        backgroundIv.startAnimation(createBackgroundAnim());
    }

    private Animation createBackgroundAnim() {
        final ScaleAnimation ivScaleAnim = new ScaleAnimation(1.0f, 1.3f, 1.0f, 1.3f
                , Animation.RELATIVE_TO_SELF, 0.5F, Animation.RELATIVE_TO_SELF, 0.5f);
        ivScaleAnim.setFillAfter(true);
        ivScaleAnim.setDuration(3000);
        ivScaleAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        return ivScaleAnim;
    }

    private Animation createLogoAnim() {
        final ScaleAnimation tvScaleAnim = new ScaleAnimation(1.0f, 1.3f, 1.0f, 1.3f
                , Animation.RELATIVE_TO_SELF, 0.5F, Animation.RELATIVE_TO_SELF, 0.5f);
        final TranslateAnimation tvTranslateAnim = new TranslateAnimation(0, 0, 0, -40);
        final AnimationSet tvAnimationSet = new AnimationSet(true);
        tvAnimationSet.addAnimation(tvScaleAnim);
        tvAnimationSet.addAnimation(tvTranslateAnim);
        tvAnimationSet.setDuration(3000);
        tvAnimationSet.setFillAfter(true);
        return tvAnimationSet;
    }

    private void startActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }

}
