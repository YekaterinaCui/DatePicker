package com.example.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import cn.xiaomeng.datepicker.TimePickerDialog;

public class MainActivity extends AppCompatActivity {

    private TextView tvTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        tvTime = findViewById(R.id.tv_time);
        tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog.Builder builder = new TimePickerDialog.Builder();
                builder.setTime("2018-03-14 18:08:56")
                        .setLeftBtnText("清除")
                        .setRightBtnText("确定")
                        .setOnRightBtnOnClickListener(new TimePickerDialog.OnRightBtnOnClickListener() {
                            @Override
                            public void onRightBtnClick(TimePickerDialog timePickerDialog, String time) {
                                timePickerDialog.dismiss();
                                Toast.makeText(MainActivity.this, time, Toast.LENGTH_SHORT).show();
                                tvTime.setText(time);
                            }
                        })
                        .create(MainActivity.this)
                        .show();
            }
        });
    }
}
