package com.example.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import cn.xiaomeng.datepicker.TimePickerDialog;

public class MainActivity extends AppCompatActivity {

    private Button btnTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        btnTime = (Button) findViewById(R.id.btn_time);
        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog.Builder builder = new TimePickerDialog.Builder();
                builder.setTime("2018-03-14 18:08:56")
                        .setLeftBtnText(null)
                        .setRightBtnText("确定")
                        .setRightBtnTextColor(R.color.color_green_3bb4bc)
                        .setOnRightBtnOnClickListener(new TimePickerDialog.OnRightBtnOnClickListener() {
                            @Override
                            public void onRightBtnClick(TimePickerDialog timePickerDialog, String time) {
                                timePickerDialog.dismiss();
                                Toast.makeText(MainActivity.this, time, Toast.LENGTH_SHORT).show();
                                btnTime.setText(time);
                            }
                        })
                        .create(MainActivity.this).show();
            }
        });
    }
}
