package com.example.mynotepad;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    Button btLogin;
    EditText etName, etPwd;
    CheckBox cbSavePwd;
    SharedPreferences sp;
    boolean isSavePwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        loadData();
    }


    /**
     * 加载数据，加载历史用户名和是否保存密码信息
     */
    private void loadData() {
        sp = getSharedPreferences("userInfo", MODE_PRIVATE);
        isSavePwd = sp.getBoolean("isSavePwd", false);
        cbSavePwd.setChecked(isSavePwd);
        String saveName = sp.getString("name", "");
        String savePwd = sp.getString("pwd", "");
        if (isSavePwd) {
            etName.setText(saveName);
            etPwd.setText(savePwd);
        } else
            etName.setText(saveName);

        cbSavePwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isSavePwd = b;
            }
        });
    }

    /**
     * 初始化控件
     */
    private void initView() {
        btLogin = findViewById(R.id.btn_login);
        etName = findViewById(R.id.et_name);
        etPwd = findViewById(R.id.et_pwd);
        cbSavePwd = findViewById(R.id.cb_save_pwd);
    }


    /**
     * 按钮点击事件
     * 如果用户信息正确，则跳转到下一个页面，并保存用户信息，结束当前页面
     *
     * @param view 点击的按钮
     */
    public void doClick(View view) {
        String name = etName.getText().toString();
        String pwd = etPwd.getText().toString();
        if (checkUserInfo(name, pwd)) {
            startActivity(new Intent(this, MainActivity.class));
            saveUserInfo(name, pwd);
            finish();
        } else
            Toast.makeText(this, "登录失败,请核对您的用户名和密码！", Toast.LENGTH_SHORT).show();
    }

    /**
     * 保存用户登录信息
     * 如果用户勾选保存密码，则保存用户名和密码信息
     * 否则只保存用户名
     *
     * @param name 用户名
     * @param pwd  密码
     */
    private void saveUserInfo(String name, String pwd) {
        if (isSavePwd)
            sp.edit().putBoolean("isSavePwd", true)
                    .putString("name", name)
                    .putString("pwd", pwd)
                    .apply();
        else
            sp.edit().putBoolean("isSavePwd", false)
                    .putString("name", name)
                    .remove("pwd")
                    .apply();
    }

    /**
     * 检查用户信息
     * 如果用户为输入信息则提示用户名或密码不能为空
     * 如果用户名和密码都匹配，则通过检测（这里默认用户名：admin，默认密码：admin）
     *
     * @param name 输入的用户名
     * @param pwd  输入的密码
     * @return 用户信息是否正确
     */
    private boolean checkUserInfo(String name, String pwd) {
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(pwd)) {
            Toast.makeText(this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        return "admin".equals(name) && "admin".equals(pwd);
    }
}
