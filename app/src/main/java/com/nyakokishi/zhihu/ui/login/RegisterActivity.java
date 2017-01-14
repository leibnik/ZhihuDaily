package com.nyakokishi.zhihu.ui.login;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.Bind;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.RequestSMSCodeListener;
import cn.bmob.v3.listener.SaveListener;

import com.nyakokishi.data.data.User;
import com.nyakokishi.zhihu.R;
import com.nyakokishi.zhihu.ui.ZhihuApplication;
import com.nyakokishi.zhihu.base.BaseActivity;
import com.nyakokishi.zhihu.manager.LoginManager;
import com.nyakokishi.zhihu.ui.profile.ProfileActivity;
import com.nyakokishi.zhihu.util.CheckUtil;
import com.nyakokishi.zhihu.util.MD5Util;
import com.nyakokishi.zhihu.util.PreferenceUtil;

/**
 * Created by nyakokishi on 2016/3/29.
 */
public class RegisterActivity extends BaseActivity {

    @Bind(R.id.username_et)
    EditText usernameEt;
    @Bind(R.id.code_btn)
    Button codeBtn;
    @Bind(R.id.code_et)
    EditText codeEt;
    @Bind(R.id.password_et)
    EditText passwordEt;
    @Bind(R.id.register_btn)
    Button registerBtn;

    private String rawPassword;
    private String verifyCode;
    private String username;

    @Override
    protected int setLayout() {
        return R.layout.activity_register;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        codeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    username = usernameEt.getText().toString();
                if (CheckUtil.isMobile(username)) {
                    new TaskCodeButton().execute();
                    BmobSMS.requestSMSCode(getApplicationContext(), username, "hello", new RequestSMSCodeListener() {

                        @Override
                        public void done(Integer smsId, BmobException ex) {
                            // TODO Auto-generated method stub
                            if (ex == null) {//验证码发送成功
                                Log.i("smile", "短信id：" + smsId);//用于查询本次短信发送详情
                            }
                        }
                    });
                } else {
                    Snackbar.make((View) registerBtn.getParent(), "请输入正确的手机号码", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CheckUtil.isMobile(username)) {
                    Snackbar.make((View) registerBtn.getParent(), "请输入正确的手机号码", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if (codeEt.getText().length() < 6) {
                    Snackbar.make((View) registerBtn.getParent(), "验证码为六位数", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if (passwordEt.getText().length() < 6) {
                    Snackbar.make((View) registerBtn.getParent(), "密码必须大于六位", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                final User user = new User();
                rawPassword = passwordEt.getText().toString();
                verifyCode = codeEt.getText().toString();
                user.setMobilePhoneNumber(username);
                String password = MD5Util.getMd5Value(MD5Util.getMd5Value(rawPassword));
                user.setPassword(password);
                Snackbar.make((View) registerBtn.getParent(), "正在注册...", Snackbar.LENGTH_INDEFINITE).show();
                user.signOrLogin(getApplicationContext(), verifyCode, new SaveListener() {
                    @Override
                    public void onSuccess() {
                        ZhihuApplication.user = BmobUser.getCurrentUser(getApplicationContext(), User.class);
                        setResult(RESULT_OK);
                        PreferenceUtil.setPrefString(getApplicationContext(), "username", username);
                        PreferenceUtil.setPrefString(getApplicationContext(),"password",rawPassword);
                        Snackbar.make((View) registerBtn.getParent(), "注册成功", Snackbar.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),ProfileActivity.class);
                        startActivity(intent);
                        LoginManager.login();
                        finish();
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        Snackbar.make((View) registerBtn.getParent(), s, Snackbar.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }

    public class TaskCodeButton extends AsyncTask<Void, Integer, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            for (int i = 60; i > 0; i--) {
                publishProgress(i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            codeBtn.setText("验证码(" + values[0] + ")");
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            codeBtn.setClickable(true);
            codeBtn.setText("验证码");
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            codeBtn.setClickable(false);
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_in_from_left,R.anim.slide_out_to_right);
    }

}
