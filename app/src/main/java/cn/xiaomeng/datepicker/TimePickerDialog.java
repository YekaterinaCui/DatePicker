package cn.xiaomeng.datepicker;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.carbswang.android.numberpickerview.library.NumberPickerView;
import cn.xiaomeng.datepicker.tools.DateUtil;


/**
 * 类名：TimePickerDialog
 * 编辑时间：2018/2/6
 * 编辑人：崔婧
 * 简介：时间选择器
 */
public class TimePickerDialog extends Dialog {

    TimePickerDialog(Context context) {
        super(context);
    }

    public static class Builder {

        private String[] years = new String[191];
        private String[] months = new String[12];
        private String[] days = new String[31];
        private String[] hours = new String[24];
        private String[] minutes = new String[60];
        private String[] seconds = new String[60];

        // 添加大小月月份并将其转换为list,方便之后的判断
        private String[] bigMonthArray = {"1", "3", "5", "7", "8", "10", "12"};
        private String[] littleMonthArray = {"4", "6", "9", "11"};

        private final List<String> bigMonthList = Arrays.asList(bigMonthArray);
        private final List<String> littleMonthList = Arrays.asList(littleMonthArray);

        private Calendar calendar = Calendar.getInstance();
        private String currentTime = DateUtil.format(calendar.getTime(), "yyyy-MM-dd HH:mm:ss");
        private int currentYear = calendar.get(Calendar.YEAR);
        private int currentMonth = calendar.get(Calendar.MONTH) + 1;
        private int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        private int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        private int currentMinute = calendar.get(Calendar.MINUTE);
        private int currentSecond = calendar.get(Calendar.SECOND);

        private boolean hideYear;
        private boolean hideMonth;
        private boolean hideDay;
        private boolean hideHour;
        private boolean hideMinute;
        private boolean hideSecond;

        private String time = currentTime;
        private int year = currentYear;
        private int month = currentMonth;
        private int day = currentDay;
        private int hour = currentHour;
        private int minute = currentMinute;
        private int second = currentSecond;

        private String leftBtnText = "取消";
        private String rightBtnText = "确定";
        private int leftBtnTextColor = R.color.color_gray_a5a6a5;
        private int rightBtnTextColor = R.color.color_gray_a5a6a5;

        private OnLeftBtnOnClickListener onLeftBtnOnClickListener;
        private OnRightBtnOnClickListener onRightBtnOnClickListener;

        public Builder setHideYear(boolean hideYear) {
            this.hideYear = hideYear;
            return this;
        }

        public Builder setHideMonth(boolean hideMonth) {
            this.hideMonth = hideMonth;
            return this;
        }

        public Builder setHideDay(boolean hideDay) {
            this.hideDay = hideDay;
            return this;
        }

        public Builder setHideHour(boolean hideHour) {
            this.hideHour = hideHour;
            return this;
        }

        public Builder setHideMinute(boolean hideMinute) {
            this.hideMinute = hideMinute;
            return this;
        }

        public Builder setHideSecond(boolean hideSecond) {
            this.hideSecond = hideSecond;
            return this;
        }

        public Builder setTime(String time) {
            this.time = time;
            Calendar timeCalender = Calendar.getInstance();
            timeCalender.setTime(DateUtil.getDate(time));
            this.year = timeCalender.get(Calendar.YEAR);
            this.month = timeCalender.get(Calendar.MONTH) + 1;
            this.day = timeCalender.get(Calendar.DAY_OF_MONTH);
            this.hour = timeCalender.get(Calendar.HOUR_OF_DAY);
            this.minute = timeCalender.get(Calendar.MINUTE);
            this.second = timeCalender.get(Calendar.SECOND);
            return this;
        }

        public Builder setLeftBtnText(String leftBtnText) {
            this.leftBtnText = leftBtnText;
            return this;
        }

        public Builder setRightBtnText(String rightBtnText) {
            this.rightBtnText = rightBtnText;
            return this;
        }

        public Builder setLeftBtnTextColor(int leftBtnTextColor) {
            this.leftBtnTextColor = leftBtnTextColor;
            return this;
        }

        public Builder setRightBtnTextColor(int rightBtnTextColor) {
            this.rightBtnTextColor = rightBtnTextColor;
            return this;
        }

        public Builder setOnLeftBtnOnClickListener(OnLeftBtnOnClickListener onLeftBtnOnClickListener) {
            this.onLeftBtnOnClickListener = onLeftBtnOnClickListener;
            return this;
        }

        public Builder setOnRightBtnOnClickListener(OnRightBtnOnClickListener onRightBtnOnClickListener) {
            this.onRightBtnOnClickListener = onRightBtnOnClickListener;
            return this;
        }

        public TimePickerDialog create(Context context) {
            TimePickerDialog timePickerDialog = new TimePickerDialog(context);
            View contentView = LayoutInflater.from(context).inflate(R.layout.dialog_time_picker, null);

            NumberPickerView pvYear = contentView.findViewById(R.id.pv_year);
            NumberPickerView pvMonth = contentView.findViewById(R.id.pv_month);
            NumberPickerView pvDay = contentView.findViewById(R.id.pv_day);
            NumberPickerView pvHour = contentView.findViewById(R.id.pv_hour);
            TextView tvSeparatorOfMinute = contentView.findViewById(R.id.tv_separator_of_minute);
            NumberPickerView pvMinute = contentView.findViewById(R.id.pv_minute);
            TextView tvSeparatorOfSecond = contentView.findViewById(R.id.tv_separator_of_second);
            NumberPickerView pvSecond = contentView.findViewById(R.id.pv_second);
            Button btnLeft = contentView.findViewById(R.id.btn_left);
            Button btnRight = contentView.findViewById(R.id.btn_right);
            setView(context, timePickerDialog, pvYear, pvMonth, pvDay, pvHour, pvMinute, pvSecond,
                    tvSeparatorOfMinute, tvSeparatorOfSecond, btnLeft, btnRight
            );
            setTime(pvYear, pvMonth, pvDay, pvHour, pvMinute, pvSecond, year, month, day, hour,
                    minute, second
            );
            timePickerDialog.addContentView(
                    contentView,
                    new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    )
            );
            return timePickerDialog;
        }

        private void setView(Context context, final TimePickerDialog timePickerDialog,
                             final NumberPickerView pvYear, final NumberPickerView pvMonth,
                             final NumberPickerView pvDay, final NumberPickerView pvHour,
                             final NumberPickerView pvMinute, final NumberPickerView pvSecond,
                             TextView tvSeparatorOfMinute, TextView tvSeparatorOfSecond,
                             Button btnLeft, Button btnRight) {

            pvYear.setVisibility(hideYear ? View.GONE : View.VISIBLE);
            pvMonth.setVisibility(hideMonth ? View.GONE : View.VISIBLE);
            pvDay.setVisibility(hideDay ? View.GONE : View.VISIBLE);
            pvHour.setVisibility(hideHour ? View.GONE : View.VISIBLE);
            pvMinute.setVisibility(hideMinute ? View.GONE : View.VISIBLE);
            tvSeparatorOfMinute.setVisibility(hideMinute ? View.GONE : View.VISIBLE);
            pvSecond.setVisibility(hideSecond ? View.GONE : View.VISIBLE);
            tvSeparatorOfSecond.setVisibility(hideSecond ? View.GONE : View.VISIBLE);

            btnLeft.setText(leftBtnText);
            btnLeft.setTextColor(context.getResources().getColor(leftBtnTextColor));
            btnRight.setText(rightBtnText);
            btnRight.setTextColor(context.getResources().getColor(rightBtnTextColor));
            btnLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onLeftBtnOnClickListener != null) {
                        onLeftBtnOnClickListener.onLeftBtnClick(timePickerDialog);
                    } else {
                        timePickerDialog.dismiss();
                    }
                }
            });

            btnRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onRightBtnOnClickListener != null) {
                        String dateTime = String.format(
                                "%1$s-%2$s-%3$s %4$s:%5$s:%6$s",
                                pvYear.getValue(),
                                pvMonth.getValue(),
                                pvDay.getValue(),
                                pvHour.getValue(),
                                pvMinute.getValue(),
                                pvSecond.getValue()
                        );
                        String time;
                        if (hideHour && hideMinute && hideSecond) {
                            time = DateUtil.formatConvert(dateTime, "yyyy-MM-dd");
                        } else if (hideSecond) {
                            time = DateUtil.formatConvert(dateTime, "yyyy-MM-dd HH:mm");
                        } else {
                            time = DateUtil.formatConvert(dateTime);
                        }
                        onRightBtnOnClickListener.onRightBtnClick(timePickerDialog, time);
                    } else {
                        timePickerDialog.dismiss();
                    }
                }

            });
        }

        int checkedDay = day;

        //设置年/月/日
        private void setTime(final NumberPickerView pvYear, final NumberPickerView pvMonth,
                             final NumberPickerView pvDay, final NumberPickerView pvHour,
                             final NumberPickerView pvMinute, final NumberPickerView pvSecond,
                             Integer year, Integer month, final Integer day,
                             Integer hour, Integer minute, Integer second) {

        /*年*/
            List<String> yearList = new ArrayList<>();
            int START_YEAR = 1970;
            int END_YEAR = 2200;
            for (int i = START_YEAR; i <= END_YEAR; i++) {
                yearList.add(String.valueOf(i));
            }
            years = yearList.toArray(years);
            pvYear.setDisplayedValues(years);
            pvYear.setMinValue(START_YEAR);
            pvYear.setMaxValue(END_YEAR);
            pvYear.setValue(year);
            pvYear.setOnValueChangedListener(new NumberPickerView.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPickerView wheel, int oldCheckedValue, int newCheckedYear) {
                    Log.d("TimePicker", String.format("年----------------------%s", newCheckedYear));

                    // 判断大小月及是否闰年,用来确定"日"的数据
                    int pvMonthValue = pvMonth.getValue();
                    if (bigMonthList.contains(String.valueOf(pvMonthValue))) {
                        pvDay.setMaxValue(31);
                    } else if (littleMonthList.contains(String.valueOf(pvMonthValue))) {
                        pvDay.setMaxValue(30);
                    } else {
                        // 闰年
                        if ((newCheckedYear % 4 == 0 && newCheckedYear % 100 != 0) || newCheckedYear % 400 == 0) {
                            pvDay.setMaxValue(29);
                        } else {
                            pvDay.setMaxValue(28);
                        }
                    }
                    if (checkedDay >= pvDay.getMaxValue()) {
                        pvDay.setValue(pvDay.getMaxValue());
                    } else {
                        pvDay.setValue(checkedDay);
                    }
                }
            });

        /*月*/
            List<String> monthList = new ArrayList<>();
            for (int i = 1; i <= 12; i++) {
                monthList.add(String.valueOf(i));
            }
            months = monthList.toArray(months);

            pvMonth.setDisplayedValues(months);
            pvMonth.setMinValue(1);
            pvMonth.setMaxValue(12);
            pvMonth.setValue(month);
            pvMonth.setOnValueChangedListener(new NumberPickerView.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPickerView wheel, int oldCheckedMonth, int newCheckedMonth) {
                    Log.d("TimePicker", String.format("月----------------------%s", newCheckedMonth));

                    // 判断大小月及是否闰年,用来确定"日"的数据
                    if (bigMonthList.contains(String.valueOf(newCheckedMonth))) {
                        pvDay.setMaxValue(31);
                    } else if (littleMonthList.contains(String.valueOf(newCheckedMonth))) {
                        pvDay.setMaxValue(30);
                    } else {
                        int pvYearValue = pvYear.getValue();
                        if ((pvYearValue % 4 == 0 && pvYearValue % 100 != 0)
                                || pvYearValue % 400 == 0) {
                            pvDay.setMaxValue(29);
                        } else {
                            pvDay.setMaxValue(28);
                        }
                    }
                    if (checkedDay >= pvDay.getMaxValue()) {
                        pvDay.setValue(pvDay.getMaxValue());
                    } else {
                        pvDay.setValue(checkedDay);
                    }
                }
            });

        /*日*/
            List<String> dayList = new ArrayList<>();
            for (int i = 1; i <= 31; i++) {
                dayList.add(String.valueOf(i));
            }
            days = dayList.toArray(days);
            pvDay.setDisplayedValues(days);
            pvDay.setMinValue(1);
            // 判断大小月及是否闰年,用来确定"日"的数据
            if (bigMonthList.contains(String.valueOf(month))) {
                pvDay.setMaxValue(31);
            } else if (littleMonthList.contains(String.valueOf(month))) {
                pvDay.setMaxValue(30);
            } else {
                // 闰年
                if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
                    pvDay.setMaxValue(29);
                } else {
                    pvDay.setMaxValue(28);
                }
            }
            pvDay.setValue(day);
            pvDay.setOnValueChangedListener(new NumberPickerView.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPickerView picker, int oldCheckedDay, int newCheckedDay) {
                    Log.d("TimePicker", String.format("日----------------------%s", newCheckedDay));
                    checkedDay = newCheckedDay;
                }
            });

        /*时*/
            List<String> hourList = new ArrayList<>();
            for (int i = 0; i <= 23; i++) {
                if (i < 10) {
                    hourList.add(String.format("0%s", i));
                } else {
                    hourList.add(String.valueOf(i));
                }
            }
            hours = hourList.toArray(hours);
            pvHour.setDisplayedValues(hours);
            pvHour.setMinValue(0);
            pvHour.setMaxValue(23);
            pvHour.setValue(hour);
            pvHour.setOnValueChangedListener(new NumberPickerView.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPickerView picker, int oldCheckedHour, int newCheckedHour) {
                    Log.d("TimePicker", String.format("时----------------------%s", newCheckedHour));

                }
            });

        /*分*/
            List<String> minuteList = new ArrayList<>();
            for (int i = 0; i <= 59; i++) {
                if (i < 10) {
                    minuteList.add(String.format("0%s", i));
                } else {
                    minuteList.add(String.valueOf(i));
                }
            }
            minutes = minuteList.toArray(minutes);
            pvMinute.setDisplayedValues(minutes);
            pvMinute.setMinValue(0);
            pvMinute.setMaxValue(59);
            pvMinute.setValue(minute);
            pvMinute.setOnValueChangedListener(new NumberPickerView.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPickerView picker, int oldCheckedMinute, int newCheckedMinute) {
                    Log.d("TimePicker", String.format("分----------------------%s", newCheckedMinute));
                }
            });

        /*秒*/
            List<String> secondList = new ArrayList<>();
            for (int i = 0; i <= 59; i++) {
                if (i < 10) {
                    secondList.add(String.format("0%s", i));
                } else {
                    secondList.add(String.valueOf(i));
                }
            }
            seconds = secondList.toArray(seconds);
            pvSecond.setDisplayedValues(seconds);
            pvSecond.setMinValue(0);
            pvSecond.setMaxValue(59);
            pvSecond.setValue(second);
            pvSecond.setOnValueChangedListener(new NumberPickerView.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPickerView picker, int oldCheckedSecond, int newCheckedSecond) {
                    Log.d("TimePicker", String.format("秒----------------------%s", newCheckedSecond));
                }
            });
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        renderDialog();
    }


    private void renderDialog() {
        if (getWindow() != null) {
            getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getWindow().setGravity(Gravity.BOTTOM);
            getWindow().setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
            //设置 Dialog 的显示消失动画
            getWindow().setWindowAnimations(R.style.AnimBottom);
        }
        //设置 Dialog 的背景透明度 (范围从 0.0 ~ 1.0 ， 1.0 表示完全不透明)
        //getWindow().getAttributes().alpha = 0.5f;
        setCanceledOnTouchOutside(true);
    }

    public interface OnLeftBtnOnClickListener {
        void onLeftBtnClick(TimePickerDialog timePickerDialog);
    }

    public interface OnRightBtnOnClickListener {
        void onRightBtnClick(TimePickerDialog timePickerDialog, String time);
    }
}
