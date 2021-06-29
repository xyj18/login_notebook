package com.example.mynotepad;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        loadData();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        et = findViewById(R.id.editText);
    }

    /**
     * 加载读取到的数据到EditText中
     */
    private void loadData() {
        String data = readData();
        if (!TextUtils.isEmpty(data)) {
            et.setText(data);
            et.setSelection(data.length());
        }
    }

    /**
     * 读取myNotePad.txt文件中的数据
     *
     * @return 读取到的内容
     */
    private String readData() {
        FileInputStream fis;
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        try {
            fis = openFileInput("myNotePad.txt");
            br = new BufferedReader(new InputStreamReader(fis));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    /**
     * 保存数据到文件中，这里默认的文件名为myNotePad.txt
     *
     * @param data 需要保存的数据
     */
    public void saveData(String data) {
        FileOutputStream fos;
        BufferedWriter bw = null;
        try {
            fos = openFileOutput("myNotePad.txt", MODE_PRIVATE);
            bw = new BufferedWriter(new OutputStreamWriter(fos));
            bw.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != bw) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 退出前，保存已输入的内容
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        String data = et.getText().toString();
        if (!TextUtils.isEmpty(data))
            saveData(data);
    }
}
