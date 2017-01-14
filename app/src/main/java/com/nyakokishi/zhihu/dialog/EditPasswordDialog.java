package com.nyakokishi.zhihu.dialog;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.Bind;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.UpdateListener;
import com.nyakokishi.zhihu.R;
import com.nyakokishi.zhihu.base.BaseActivity;
import com.nyakokishi.zhihu.util.MD5Util;
import com.nyakokishi.zhihu.util.PreferenceUtil;

/**
 * Created by nyakokishi on 2016/3/31.
 */
public class EditPasswordDialog extends BaseActivity {

    @Bind(R.id.password_et)
    EditText passwordEt;
    @Bind(R.id.save_btn)
    Button saveBtn;

    String oldPassword;

    @Override
    protected void initVariables() {
        super.initVariables();
        oldPassword = PreferenceUtil.getPrefString(getApplicationContext(), "password", "");
    }

    @Override
    protected int setLayout() {
        return R.layout.view_dialog_edit_password;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String newPassword = passwordEt.getText().toString();
                if (newPassword.length() < 6) {
                    Snackbar.make((View) saveBtn.getParent(), "密码不能少于6位", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                BmobUser.updateCurrentUserPassword(getApplicationContext()
                        , MD5Util.getMd5Value(MD5Util.getMd5Value(oldPassword))
                        , MD5Util.getMd5Value(MD5Util.getMd5Value(newPassword))
                        , new UpdateListener() {

                    @Override
                    public void onSuccess() {
                        PreferenceUtil.setPrefString(getApplicationContext(), "password", newPassword);
                        setResult(RESULT_OK);
                        finish();
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        Snackbar.make((View) saveBtn.getParent(), msg, Snackbar.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
