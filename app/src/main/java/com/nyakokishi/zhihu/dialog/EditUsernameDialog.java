package com.nyakokishi.zhihu.dialog;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.Bind;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.UpdateListener;

import com.nyakokishi.data.data.User;
import com.nyakokishi.zhihu.R;
import com.nyakokishi.zhihu.ui.ZhihuApplication;
import com.nyakokishi.zhihu.base.BaseActivity;

/**
 * Created by nyakokishi on 2016/3/30.
 */
public class EditUsernameDialog extends BaseActivity{
    @Bind(R.id.username_et)
    EditText usernameEt;
    @Bind(R.id.save_btn)
    Button saveBtn;

    @Override
    protected int setLayout() {
        return R.layout.view_dialog_edit_username;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String newUsername = usernameEt.getText().toString();
                if (TextUtils.isEmpty(newUsername)){
                    Snackbar.make((View) saveBtn.getParent(),"用户名不能为空",Snackbar.LENGTH_SHORT).show();
                    return;
                }
                final User user = BmobUser.getCurrentUser(getApplicationContext(),User.class);
                user.setUsername(newUsername);
                user.update(getApplicationContext(), user.getObjectId(), new UpdateListener() {
                    @Override
                    public void onSuccess() {
                        ZhihuApplication.user.setUsername(newUsername);
                        setResult(RESULT_OK);
                        finish();
                    }

                    @Override
                    public void onFailure(int i, String s) {

                    }
                });
            }
        });
    }
}
