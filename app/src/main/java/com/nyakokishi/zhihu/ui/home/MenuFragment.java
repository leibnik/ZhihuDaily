package com.nyakokishi.zhihu.ui.home;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.loopj.android.http.TextHttpResponseHandler;
import com.nyakokishi.data.data.Themes;
import com.nyakokishi.data.data.User;
import com.nyakokishi.zhihu.ui.MainActivity;
import com.nyakokishi.zhihu.ui.theme.StoriesFragment;
import com.victor.loading.rotate.RotateLoading;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import cn.bmob.v3.BmobUser;

import com.nyakokishi.zhihu.manager.LoginManager;
import com.nyakokishi.zhihu.manager.UpdateInfoManager;
import com.nyakokishi.zhihu.task.TaskBlurBackground;
import com.nyakokishi.zhihu.ui.login.LoginActivity;
import com.nyakokishi.zhihu.R;
import com.nyakokishi.zhihu.ui.ZhihuApplication;
import com.nyakokishi.zhihu.base.BaseFragment;
import com.nyakokishi.zhihu.constant.Constant;
import com.nyakokishi.zhihu.ui.profile.ProfileActivity;
import com.nyakokishi.zhihu.util.HttpUtil;
import com.nyakokishi.zhihu.util.PreferenceUtil;
import com.nyakokishi.zhihu.widget.DividerItemDecoration;

/**
 * Created by nyakokishi on 2016/3/22.
 */
public class MenuFragment extends BaseFragment {

    public static final String TAG = "MenuFragment";

    @Bind(R.id.user_iv)
    ImageView userIv;
    @Bind(R.id.user_tv)
    TextView userTv;
    @Bind(R.id.themes_lv)
    RecyclerView themesLv;
    @Bind(R.id.rotateloading)
    RotateLoading rotateLoading;

    private HomeMenuAdapter adapter;
    private int currentThemeId = 999;
    private String background;

    @Override
    public void initVariables() {
        super.initVariables();
        isColorTheme = PreferenceUtil.getPrefBoolean(mActivity, "isColorTheme", true);
        background = PreferenceUtil.getPrefString(mActivity, "background", "");
        ZhihuApplication.user = BmobUser.getCurrentUser(mActivity.getApplicationContext(), User.class);
    }

    @Override
    public int setLayout() {
        return R.layout.fragment_menu;
    }

    @Override
    public void initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        userIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ZhihuApplication.user != null) {
                    gotoProfile();
                } else {
                    gotoLogin();
                }
            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        themesLv.setLayoutManager(layoutManager);
        themesLv.setItemAnimator(new DefaultItemAnimator());
        themesLv.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));

        initTheme();
        initCallback();
    }

    private void initCallback() {
        LoginManager.addOnLoginListener(new LoginManager.OnLoginListener() {
            @Override
            public void login() {
                if (ZhihuApplication.user != null) {
                    Glide.with(mActivity.getApplicationContext())
                            .load(ZhihuApplication.user.getAvatar()).error(R.drawable.profile).into(userIv);
                    userTv.setText(ZhihuApplication.user.getUsername());
                }
            }

            @Override
            public void logout() {
                userTv.setText("点击头像登陆");
                userIv.setImageResource(R.drawable.profile);
            }
        });
        UpdateInfoManager.addOnUpdateInfoListener(new UpdateInfoManager.OnUpdateInfoListener() {
            @Override
            public void updateName() {
                if (ZhihuApplication.user != null) {
                    userTv.setText(ZhihuApplication.user.getUsername());
                }
            }

            @Override
            public void updateAvatar() {
                if (ZhihuApplication.user != null) {
                    Glide.with(mActivity.getApplicationContext())
                            .load(ZhihuApplication.user.getAvatar()).error(R.drawable.profile).into(userIv);
                }
            }
        });
    }

    private void initTheme() {
        if (TextUtils.isEmpty(background)) {
            updateTheme(isColorTheme, null);
        } else {
            new TaskBlurBackground(mActivity.getApplicationContext(),background) {
                @Override
                protected void onPostExecute(Bitmap bitmap) {
                    super.onPostExecute(bitmap);
                    BitmapDrawable background = new BitmapDrawable(getResources(), bitmap);
                    ((LinearLayout) themesLv.getParent().getParent())
                            .setBackground(background);
                }
            }.execute();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void gotoLogin() {
        Intent intent = new Intent(mActivity, LoginActivity.class);
        startActivity(intent);
        mActivity.overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
        ((MainActivity) mActivity).closeDrawer();
    }

    private void gotoProfile() {
        Intent intent = new Intent(mActivity, ProfileActivity.class);
        startActivity(intent);
        mActivity.overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
        ((MainActivity) mActivity).closeDrawer();
    }

    @Override
    public void loadData() {
        if (HttpUtil.isNetworkAvailable(mActivity.getApplicationContext())) {


            HttpUtil.get(Constant.THEMES,
                    new TextHttpResponseHandler() {
                        @Override
                        public void onStart() {
                            rotateLoading.start();
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            rotateLoading.stop();
                        }

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, String responseString) {
                            rotateLoading.stop();
                            handleResponse(responseString);
                            mDBManger.saveThemes(responseString);
                        }
                    }
            );
        } else {
            handleResponse(mDBManger.getThemes());
        }
    }

    private void handleResponse(String response) {
        if(response == null){
            return;
        }
        List<Themes> data = parseJson(response);
        Themes home = new Themes();
        home.setName("首页");
        home.setId(999);
        data.add(0, home);
        adapter = new HomeMenuAdapter(mActivity, data, currentThemeId);
        themesLv.setAdapter(adapter);
        adapter.setOnItemClickListener(new HomeMenuAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Object data) {
                BaseFragment fragment;
                Themes themes = (Themes) data;
                int id = themes.getId();
                if (currentThemeId == id) {
                    ((MainActivity) mActivity).closeDrawer();
                    return;
                } else {
                    currentThemeId = id;
                    adapter.setCurrentThemeId(id);
                }
                Bundle bundle = new Bundle();
                bundle.putInt("id", id);
                bundle.putBoolean("isColorTheme", isColorTheme);
                if (id == 999) {
                    fragment = new com.nyakokishi.zhihu.ui.daily.StoriesFragment();
                } else {
                    fragment = new StoriesFragment();
                }
                fragment.setArguments(bundle);
                getFragmentManager().beginTransaction().setCustomAnimations(
                        R.anim.slide_in_from_right, R.anim.slide_out_to_left
                ).replace(R.id.content, fragment).commit();
                ((MainActivity) mActivity).closeDrawer();
                ((MainActivity) mActivity).setCurrentFragment(fragment);
                ((MainActivity) mActivity).setRefreshEnable(true);
            }
        });
    }

    private List<Themes> parseJson(String response) {
        Log.e(TAG, response);
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("others");
            List<Themes> data = JSON.parseArray(jsonArray.toString(), Themes.class);
            return data;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateTheme(boolean isColorTheme, BitmapDrawable background) {
        super.updateTheme(isColorTheme, background);
        if (isColorTheme) {
            ((LinearLayout) themesLv.getParent().getParent()).setBackgroundColor(mActivity.getResources()
                    .getColor(R.color.blue));
        } else {
            if (background == null) {
                ((LinearLayout) themesLv.getParent().getParent()).setBackground(mActivity.getResources()
                        .getDrawable(R.drawable.menu_background));
            } else {
                ((LinearLayout) themesLv.getParent().getParent()).setBackground(background);
            }

        }
    }
}
