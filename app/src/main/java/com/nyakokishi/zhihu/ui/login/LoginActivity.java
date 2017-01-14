package com.nyakokishi.zhihu.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.Bind;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;

import com.nyakokishi.data.data.User;
import com.nyakokishi.zhihu.R;
import com.nyakokishi.zhihu.ui.ZhihuApplication;
import com.nyakokishi.zhihu.base.BaseActivity;
import com.nyakokishi.zhihu.manager.LoginManager;
import com.nyakokishi.zhihu.ui.profile.ProfileActivity;
import com.nyakokishi.zhihu.util.MD5Util;
import com.nyakokishi.zhihu.util.PreferenceUtil;

/**
 * Created by nyakokishi on 2016/3/23.
 */
public class LoginActivity extends BaseActivity {

    @Bind(R.id.username_et)
    EditText usernameEt;
    @Bind(R.id.password_et)
    EditText passwordEt;
    @Bind(R.id.login_btn)
    Button loginBtn;
    @Bind(R.id.register_link)
    TextView registerLink;

    private String rawPassword;
    private String username;

    @Override
    protected void initVariables() {
        super.initVariables();
        rawPassword = PreferenceUtil.getPrefString(getApplicationContext(), "password", "");
        username = PreferenceUtil.getPrefString(getApplicationContext(), "username", "");
    }

    @Override
    protected int setLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        usernameEt.setText(username);
        passwordEt.setText(rawPassword);
        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivityForResult(intent, 999);
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = usernameEt.getText().toString();
                rawPassword = passwordEt.getText().toString();
                if (TextUtils.isEmpty(username)) {
                    Snackbar.make((View) loginBtn.getParent().getParent(), "用户名不能为空", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(rawPassword)) {
                    Snackbar.make((View) loginBtn.getParent().getParent(), "密码不能为空", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                Snackbar.make((View) loginBtn.getParent().getParent(), "登陆中...", Snackbar.LENGTH_INDEFINITE).show();
                String password = MD5Util.getMd5Value(MD5Util.getMd5Value(rawPassword));
                BmobUser.loginByAccount(getApplicationContext(), username
                        , password, new LogInListener<User>() {
                    @Override
                    public void done(User user, BmobException e) {
                        if (user != null) {
                            Snackbar.make((View) loginBtn.getParent().getParent(), "登陆成功", Snackbar.LENGTH_SHORT).show();
                            Log.i("smile", "用户登陆成功");
                            ZhihuApplication.user = BmobUser.getCurrentUser(getApplicationContext(), User.class);
                            Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
                            LoginManager.login();
                            PreferenceUtil.setPrefString(getApplicationContext(), "username", username);
                            PreferenceUtil.setPrefString(getApplicationContext(), "password", rawPassword);
                            finish();
                        } else {
                            Snackbar.make((View) loginBtn.getParent().getParent(), e.toString(), Snackbar.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 999) {
                finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
    }
}
