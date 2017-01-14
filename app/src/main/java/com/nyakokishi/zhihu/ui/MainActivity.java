package com.nyakokishi.zhihu.ui;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import butterknife.Bind;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;

import com.nyakokishi.data.data.User;
import com.nyakokishi.zhihu.R;
import com.nyakokishi.zhihu.base.BaseActivity;
import com.nyakokishi.zhihu.base.BaseFragment;
import com.nyakokishi.zhihu.constant.Constant;
import com.nyakokishi.zhihu.manager.LoginManager;
import com.nyakokishi.zhihu.ui.daily.StoriesFragment;
import com.nyakokishi.zhihu.ui.home.MenuFragment;
import com.nyakokishi.zhihu.util.DisplayUtil;
import com.nyakokishi.zhihu.util.MD5Util;
import com.nyakokishi.zhihu.util.PreferenceUtil;
import com.nyakokishi.zhihu.task.TaskBlurBackground;

public class MainActivity extends BaseActivity {

    @Bind(R.id.main_layout)
    LinearLayout mLinearLayout;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.drawerlayout)
    DrawerLayout mDrawerLayout;
    @Bind(R.id.content)
    FrameLayout mFrameLayout;
    @Bind(R.id.refreshlayout)
    SwipeRefreshLayout mRefreshLayout;

    private BaseFragment currentFragment;
    private long currentTime;


    private boolean isColorTheme = true;
    private String background;

    private boolean isAutoLogin;
    private String rawPassword;
    private String username;

    @Override
    protected void initVariables() {
        super.initVariables();
        isColorTheme = PreferenceUtil.getPrefBoolean(getApplicationContext(), "isColorTheme", true);
        background = PreferenceUtil.getPrefString(getApplicationContext(), "background", "");

        isAutoLogin = PreferenceUtil.getPrefBoolean(getApplicationContext(), "isAutoLogin", false);
        rawPassword = PreferenceUtil.getPrefString(getApplicationContext(), "password", "");
        username = PreferenceUtil.getPrefString(getApplicationContext(), "username", "");
    }

    @Override
    protected int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setSupportActionBar(mToolbar);

        if (isColorTheme) {
            mToolbar.setBackgroundColor(getResources().getColor(R.color.blue));
            mRefreshLayout.setBackgroundColor(getResources().getColor(R.color.home_background));
        } else {
            if (TextUtils.isEmpty(background)) {
                mLinearLayout.setBackground(getResources().getDrawable(R.drawable.main_background));
            } else {
                updateBackground();
            }
        }

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar
                , R.string.app_name, R.string.app_name);
        mDrawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();

        initFragment();

        mRefreshLayout.setColorSchemeResources(android.R.color.holo_red_light
                , android.R.color.holo_orange_light, android.R.color.holo_green_light
                , android.R.color.holo_blue_bright);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currentFragment.loadData();
                mRefreshLayout.setRefreshing(false);
            }
        });

        if (isAutoLogin) {
            autoLogin();
        }
    }

    private void autoLogin() {
        BmobUser.loginByAccount(getApplicationContext(), username
                , MD5Util.getMd5Value(MD5Util.getMd5Value(rawPassword)), new LogInListener<User>() {
            @Override
            public void done(User o, BmobException e) {
                if (o != null) {
                    ZhihuApplication.user = BmobUser.getCurrentUser(getApplicationContext(), User.class);
                    LoginManager.login();
                }
            }
        });
    }

    private void initFragment() {
        StoriesFragment dayNewsFragment = new StoriesFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("isColorTheme", isColorTheme);
        dayNewsFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, dayNewsFragment)
                .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                .commit();
        currentFragment = dayNewsFragment;
    }

    public void setRefreshEnable(boolean isRefreshEnable) {
        mRefreshLayout.setEnabled(isRefreshEnable);
    }

    public void setCurrentFragment(BaseFragment currentFragment) {
        this.currentFragment = currentFragment;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_custom_mode) {
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, Constant.ALBUTM);
        }
        if (id == R.id.action_scenery_mode) {
            isColorTheme = false;
            mLinearLayout.setBackground(getResources().getDrawable(R.drawable.main_background));
            mToolbar.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            currentFragment.updateTheme(isColorTheme, null);
            ((MenuFragment) getSupportFragmentManager().findFragmentById(R.id.menu_fragment))
                    .updateTheme(isColorTheme, null);
            PreferenceUtil.setPrefBoolean(getApplicationContext(), "isColorTheme", isColorTheme);
            PreferenceUtil.setPrefString(getApplicationContext(), "background", "");
        }
        if (id == R.id.action_light_mode) {
            isColorTheme = true;
            mLinearLayout.setBackground(null);
            mToolbar.setBackgroundColor(getResources().getColor(R.color.blue));
            mRefreshLayout.setBackgroundColor(getResources().getColor(R.color.home_background));
            currentFragment.updateTheme(isColorTheme, null);
            ((MenuFragment) getSupportFragmentManager().findFragmentById(R.id.menu_fragment))
                    .updateTheme(isColorTheme, null);
            PreferenceUtil.setPrefBoolean(getApplicationContext(), "isColorTheme", isColorTheme);
            PreferenceUtil.setPrefString(getApplicationContext(), "background", "");
        }

        return super.onOptionsItemSelected(item);
    }

    public void closeDrawer() {
        mDrawerLayout.closeDrawers();
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
            mDrawerLayout.closeDrawers();
            return;
        }
        if (System.currentTimeMillis() - currentTime < 3 * 1000) {
            finish();
        } else {
            currentTime = System.currentTimeMillis();
            Snackbar.make(mFrameLayout, "再按一次退出", Snackbar.LENGTH_LONG).show();
        }
    }

    public void showSnackBar(String tips) {
        Snackbar.make(mFrameLayout, tips, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BmobUser.logOut(getApplicationContext());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == Constant.ALBUTM && data != null) {
                Uri selectedImage = data.getData();
                String[] filePathColumns = {MediaStore.Images.Media.DATA};
                Cursor c = this.getContentResolver().query(selectedImage, filePathColumns, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePathColumns[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                background = picturePath;
                // 更新背景
                updateBackground();
            }
        }
    }

    private void updateBackground() {
        new TaskBlurBackground(getApplicationContext(), background) {
            @Override
            protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);
                int screenWidth = DisplayUtil.getScreenWidth(getApplicationContext());
                int menuWidth = DisplayUtil.dip2px(getApplicationContext(), 280);
                float scale = (float) menuWidth / screenWidth;
                isColorTheme = false;
                mLinearLayout.setBackground(new BitmapDrawable(getResources(), bitmap));
                mToolbar.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                currentFragment.updateTheme(isColorTheme, null);
                ((MenuFragment) getSupportFragmentManager().findFragmentById(R.id.menu_fragment))
                        .updateTheme(isColorTheme, new BitmapDrawable(getResources()
                                , Bitmap.createBitmap(bitmap, 0, 0, (int) (bitmap.getWidth() * scale), bitmap.getHeight())));
                PreferenceUtil.setPrefBoolean(getApplicationContext(), "isColorTheme", isColorTheme);
                PreferenceUtil.setPrefString(getApplicationContext(), "background", background);
            }
        }.execute();
    }

}