package cn.xiaomeng.datepicker;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import cn.carbswang.android.numberpickerview.library.NumberPickerView;

/**
 * 日期选择器
 * Created by cj on 2017/1/19.
 */

public class DatePickerDialog extends Dialog {


    private String[] years = new String[191];
    private String[] months = new String[12];
    private String[] days = new String[31];
    private String[] hours = new String[24];
    private String[] minutes = new String[60];

    private List<String> list;

    // 添加大小月月份并将其转换为list,方便之后的判断
    private String[] months_big = {"1", "3", "5", "7", "8", "10", "12"};
    private String[] months_little = {"4", "6", "9", "11"};

    private final List<String> list_big = Arrays.asList(months_big);
    private final List<String> list_little = Arrays.asList(months_little);

    private Calendar calendar = Calendar.getInstance();
    private int year = calendar.get(Calendar.YEAR);

    private int currentYear = calendar.get(Calendar.YEAR);
    private int currentMonth = calendar.get(Calendar.MONTH) + 1;
    private int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
    private int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
    private int currentMinute = calendar.get(Calendar.MINUTE);


    private OnSureOnClickListener onSureOnClickListener;
    private OnCancelOnClickListener onCancelOnClickListener;
    private LinearLayout llShowTime;
    private TextView tvYear;
    private TextView tvMonth;
    private TextView tvDay;
    private TextView tvHour;
    private TextView tvMinute;
    private NumberPickerView yearPicker;
    private NumberPickerView monthPicker;
    private NumberPickerView dayPicker;
    private NumberPickerView hourPicker;
    private TextView tvTimeSeparator;
    private NumberPickerView minutePicker;
    private Button cancel;
    private Button sure;

    public DatePickerDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_date_picker);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setGravity(Gravity.BOTTOM);
        getWindow().setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        //设置 Dialog 的显示消失动画
        getWindow().setWindowAnimations(R.style.AnimBottom);
        //设置 Dialog 的背景透明度 (范围从 0.0 ~ 1.0 ， 1.0 表示完全不透明)
        //getWindow().getAttributes().alpha = 0.5f;
        setCanceledOnTouchOutside(true);
        initBtnClickEvent();
        setYear(yearPicker, dayPicker, monthPicker);
        setMonth(monthPicker, dayPicker, yearPicker);
        setDay(dayPicker);
        setHour(hourPicker);
        setMinute(minutePicker);
    }

    private void initBtnClickEvent() {
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onSureOnClickListener != null) {
                    onSureOnClickListener.onSureClick();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onCancelOnClickListener != null) {
                    onCancelOnClickListener.onCancelClick();
                }
            }
        });
    }

    public void setCancelOnClickListener(String str, OnCancelOnClickListener onCancelOnClickListener) {
        String noStr;
        if (str == null) {
            noStr = "取消";
        } else {
            noStr = str;
        }
        this.onCancelOnClickListener = onCancelOnClickListener;
    }

    public void setSureOnClickListener(String str, OnSureOnClickListener onSureOnClickListener) {
        String yesStr;
        if (str == null) {
            yesStr = "取消";
        } else {
            yesStr = str;
        }
        this.onSureOnClickListener = onSureOnClickListener;
    }

    public int getYear() {
        return yearPicker.getValue();
    }

    public int getMonth() {
        return monthPicker.getValue();
    }

    public int getDay() {
        return dayPicker.getValue();
    }

    public int getMaxDay() {
        return dayPicker.getMaxValue();
    }

    public int getHour() {
        return hourPicker.getValue();
    }

    public int getMinute() {
        return minutePicker.getValue();
    }

    private void setMinute(NumberPickerView minutePicker) {
        list = new ArrayList<>();
        for (int i = 0; i <= 59; i++) {
            if (i < 10) {
                list.add("0" + String.valueOf(i));
            } else {
                list.add(String.valueOf(i));
            }
        }

        minutes = list.toArray(minutes);
        minutePicker.setDisplayedValues(minutes);
        minutePicker.setMinValue(0);
        minutePicker.setMaxValue(59);
        minutePicker.setValue(currentMinute);
        minutePicker.setOnValueChangedListener(new NumberPickerView.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPickerView picker, int oldVal, int newVal) {
                Log.d("minute", String.valueOf(newVal));
                tvMinute.setText(String.valueOf(newVal));
            }
        });
    }

    private void setHour(NumberPickerView hourPicker) {
        list = new ArrayList<>();
        for (int i = 0; i <= 23; i++) {
            if (i < 10) {
                list.add("0" + String.valueOf(i));
            } else {
                list.add(String.valueOf(i));
            }
        }
        hours = list.toArray(hours);
        hourPicker.setDisplayedValues(hours);
        hourPicker.setMinValue(0);
        hourPicker.setMaxValue(23);
        hourPicker.setValue(currentHour);
        hourPicker.setOnValueChangedListener(new NumberPickerView.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPickerView picker, int oldVal, int newVal) {
                Log.d("hour", String.valueOf(newVal));
                tvHour.setText(String.valueOf(newVal));
            }
        });
    }

    private void setDay(NumberPickerView dayPicker) {
        list = new ArrayList<>();
        for (int i = 1; i <= 31; i++) {
            list.add(String.valueOf(i));
        }
        days = list.toArray(days);
        dayPicker.setDisplayedValues(days);
        dayPicker.setMinValue(1);
        // 判断大小月及是否闰年,用来确定"日"的数据
        if (list_big.contains(String.valueOf(currentMonth))) {
            dayPicker.setMaxValue(31);
        } else if (list_little.contains(String.valueOf(currentMonth))) {
            dayPicker.setMaxValue(30);
        } else {
            // 闰年
            if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
                dayPicker.setMaxValue(29);
            } else {
                dayPicker.setMaxValue(28);
            }
        }
        dayPicker.setValue(currentDay);
        dayPicker.setOnValueChangedListener(new NumberPickerView.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPickerView picker, int oldVal, int newVal) {
                Log.d("day", String.valueOf(newVal));
                tvDay.setText(String.valueOf(newVal));
            }
        });
    }

    private void setMonth(NumberPickerView monthPicker, final NumberPickerView dayPicker, final NumberPickerView yearPicker) {
        list = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            list.add(String.valueOf(i));
        }
        months = list.toArray(months);
        monthPicker.setDisplayedValues(months);
        monthPicker.setMinValue(1);
        monthPicker.setMaxValue(12);
        monthPicker.setValue(currentMonth);
        monthPicker.setOnValueChangedListener(new NumberPickerView.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPickerView wheel, int oldValue, int newValue) {
                int monthNum = newValue;
                Log.d("month", String.valueOf(monthNum));
                tvMonth.setText(String.valueOf(newValue));
                list = new ArrayList<>();
                for (int i = 1; i <= 31; i++) {
                    list.add(String.valueOf(i));
                }
                days = list.toArray(days);
                dayPicker.setDisplayedValues(days);
                dayPicker.setMinValue(1);
                // 判断大小月及是否闰年,用来确定"日"的数据
                if (list_big.contains(String.valueOf(monthNum))) {
                    dayPicker.setMaxValue(31);
                } else if (list_little.contains(String.valueOf(monthNum))) {
                    dayPicker.setMaxValue(30);
                } else {
                    if ((yearPicker.getValue() % 4 == 0 && yearPicker.getValue() % 100 != 0)
                            || yearPicker.getValue() % 400 == 0) {
                        dayPicker.setMaxValue(29);
                    } else {
                        dayPicker.setMaxValue(28);
                    }
                }
                if (currentDay > dayPicker.getMaxValue()) {
                    dayPicker.setValue(dayPicker.getMaxValue());
                } else {
                    dayPicker.setValue(currentDay);
                }
            }
        });
    }

    private void setYear(NumberPickerView yearPicker, final NumberPickerView dayPicker, final NumberPickerView monthPicker) {
        list = new ArrayList<>();
        int START_YEAR = 1970;
        int END_YEAR = 2100;
        for (int i = START_YEAR; i <= END_YEAR; i++) {
            list.add(String.valueOf(i));
        }
        years = list.toArray(years);
        yearPicker.setDisplayedValues(years);
        yearPicker.setMinValue(START_YEAR);
        yearPicker.setMaxValue(END_YEAR);
        yearPicker.setValue(currentYear);
        yearPicker.setOnValueChangedListener(new NumberPickerView.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPickerView wheel, int oldValue, int newValue) {
                Log.d("year", String.valueOf(newValue));
                tvYear.setText(String.valueOf(newValue));
                int yearNum = newValue;
                list = new ArrayList<>();
                for (int i = 1; i <= 31; i++) {
                    list.add(String.valueOf(i));
                }
                days = list.toArray(days);
                // 判断大小月及是否闰年,用来确定"日"的数据
                int value = monthPicker.getValue();
                dayPicker.setDisplayedValues(days);
                dayPicker.setMinValue(1);
                if (list_big.contains(String.valueOf(value))) {
                    dayPicker.setMaxValue(31);
                } else if (list_little.contains(String.valueOf(value))) {
                    dayPicker.setMaxValue(30);
                } else {
                    // 闰年
                    if ((yearNum % 4 == 0 && yearNum % 100 != 0) || yearNum % 400 == 0) {
                        dayPicker.setMaxValue(29);
                    } else {
                        dayPicker.setMaxValue(28);
                    }
                }
                if (currentDay > dayPicker.getMaxValue()) {
                    dayPicker.setValue(dayPicker.getMaxValue());
                } else {
                    dayPicker.setValue(currentDay);
                }
            }
        });
    }

    private void initView() {
        llShowTime = (LinearLayout) findViewById(R.id.ll_show_time);
        tvYear = (TextView) findViewById(R.id.tv_year);
        tvMonth = (TextView) findViewById(R.id.tv_month);
        tvDay = (TextView) findViewById(R.id.tv_day);
        tvHour = (TextView) findViewById(R.id.tv_hour);
        tvMinute = (TextView) findViewById(R.id.tv_minute);
        yearPicker = (NumberPickerView) findViewById(R.id.year_picker);
        monthPicker = (NumberPickerView) findViewById(R.id.month_picker);
        dayPicker = (NumberPickerView) findViewById(R.id.day_picker);
        hourPicker = (NumberPickerView) findViewById(R.id.hour_picker);
        tvTimeSeparator = (TextView) findViewById(R.id.tv_time_separator);
        minutePicker = (NumberPickerView) findViewById(R.id.minute_picker);
        cancel = (Button) findViewById(R.id.cancel);
        sure = (Button) findViewById(R.id.sure);
    }

    public interface OnSureOnClickListener {
        void onSureClick();
    }

    public interface OnCancelOnClickListener {
        void onCancelClick();
    }
}
