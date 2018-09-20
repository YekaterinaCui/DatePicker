package cn.xiaomeng.datepicker;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import cn.xiaomeng.datepicker.tools.DateUtil;
import cn.xiaomeng.datepicker.wedige.NumberPickerView;


/**
 * 类名：TimePickerDialog
 * 编辑时间：2018/4/8
 * 编辑人：崔婧
 * 简介：时间选择器
 */
public class TimePickerDialog extends Dialog {

    TimePickerDialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    public static class Builder {

        private String[] years = new String[191];
        private String[] months = new String[12];
        private String[] days = new String[31];
        private String[] hours = new String[24];
        private String[] minutes = new String[60];
        private String[] seconds = new String[60];

        private List<String> list;

        // 添加大小月月份并将其转换为list,方便之后的判断
        private String[] bigMonthArray = {"1", "3", "5", "7", "8", "10", "12"};
        private String[] littleMonthArray = {"4", "6", "9", "11"};

        private final List<String> bigMonthList = Arrays.asList(bigMonthArray);
        private final List<String> littleMonthList = Arrays.asList(littleMonthArray);

        private boolean hideYear;
        private boolean hideMonth;
        private boolean hideDay;
        private boolean hideHour;
        private boolean hideMinute;
        private boolean hideSecond;

        private int year;
        private int month;
        private int day;
        private int hour;
        private int minute;
        private int second;

        private String leftBtnText = "清除";
        private String rightBtnText = "确定";
        private int leftBtnTextColor = R.color.color_gray_a5a6a5;
        private int rightBtnTextColor = R.color.color_gray_a5a6a5;

        private OnLeftBtnOnClickListener onLeftBtnOnClickListener;
        private OnRightBtnOnClickListener onRightBtnOnClickListener;

        public Builder hideYear(boolean hideYear) {
            this.hideYear = hideYear;
            return this;
        }

        public Builder hideMonth(boolean hideMonth) {
            this.hideMonth = hideMonth;
            return this;
        }

        public Builder hideDay(boolean hideDay) {
            this.hideDay = hideDay;
            return this;
        }

        public Builder hideHour(boolean hideHour) {
            this.hideHour = hideHour;
            return this;
        }

        public Builder hideMinute(boolean hideMinute) {
            this.hideMinute = hideMinute;
            return this;
        }

        public Builder hideSecond(boolean hideSecond) {
            this.hideSecond = hideSecond;
            return this;
        }

        public Builder setTime(String time) {
            if (DateUtil.getDate(time) == null) {
                return this;
            } else {
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

        //=============================================
        //  方法：setView
        //  时间：2018/4/4 下午7:01
        //  简介：设置时间选择的view
        //=============================================
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

            if (TextUtils.isEmpty(leftBtnText)) {
                btnLeft.setVisibility(View.GONE);
                btnRight.setBackground(context.getResources().getDrawable(R.drawable.sel_dialog_btn));
            } else {
                btnLeft.setText(leftBtnText);
                btnLeft.setTextColor(context.getResources().getColor(leftBtnTextColor));
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
            }

            if (TextUtils.isEmpty(rightBtnText)) {
                btnRight.setVisibility(View.GONE);
                btnLeft.setBackground(context.getResources().getDrawable(R.drawable.sel_dialog_btn));
            } else {
                btnRight.setText(rightBtnText);
                btnRight.setTextColor(context.getResources().getColor(rightBtnTextColor));
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
        }

        //=============================================
        //  方法：setTime
        //  时间：2018/4/4 下午6:43
        //  简介：设置年/月/日
        //=============================================
        private void setTime(final NumberPickerView pvYear, final NumberPickerView pvMonth,
                             final NumberPickerView pvDay, final NumberPickerView pvHour,
                             final NumberPickerView pvMinute, final NumberPickerView pvSecond,
                             Integer year, Integer month, final Integer day,
                             Integer hour, Integer minute, Integer second) {

            //=============================================
            //  简介：年
            //=============================================
            list = new ArrayList<>();
            int START_YEAR = 1970;
            int END_YEAR = 2200;
            for (int i = START_YEAR; i <= END_YEAR; i++) {
                list.add(String.valueOf(i));
            }
            years = list.toArray(years);
            pvYear.setDisplayedValues(years);
            pvYear.setMinValue(START_YEAR);
            pvYear.setMaxValue(END_YEAR);
            pvYear.setValue(year);
            pvYear.setOnValueChangedListener(new NumberPickerView.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPickerView wheel, int oldCheckedValue, int newCheckedYear) {
                    Log.d("TimePicker", String.format("年----------------------%s", newCheckedYear));

                    int yearNum = newCheckedYear;
                    list = new ArrayList<>();
                    for (int i = 1; i <= 31; i++) {
                        list.add(String.valueOf(i));
                    }
                    days = list.toArray(days);


                    // 判断大小月及是否闰年,用来确定"日"的数据
                    int pvMonthValue = pvMonth.getValue();
                    if (bigMonthList.contains(String.valueOf(pvMonthValue))) {
                        pvDay.setMaxValue(31);
                    } else if (littleMonthList.contains(String.valueOf(pvMonthValue))) {
                        pvDay.setMaxValue(30);
                    } else {
                        // 闰年
                        if ((yearNum % 4 == 0 && yearNum % 100 != 0) || yearNum % 400 == 0) {
                            pvDay.setMaxValue(29);
                        } else {
                            pvDay.setMaxValue(28);
                        }
                    }
                    if (day >= pvDay.getMaxValue()) {
                        pvDay.setValue(pvDay.getMaxValue());
                    } else {
                        pvDay.setValue(day);
                    }
                }
            });

            //=============================================
            //  简介：月
            //=============================================
            list = new ArrayList<>();
            for (int i = 1; i <= 12; i++) {
                list.add(String.valueOf(i));
            }
            months = list.toArray(months);

            pvMonth.setDisplayedValues(months);
            pvMonth.setMinValue(1);
            pvMonth.setMaxValue(12);
            pvMonth.setValue(month);
            pvMonth.setOnValueChangedListener(new NumberPickerView.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPickerView wheel, int oldCheckedMonth, int newCheckedMonth) {
                    Log.d("TimePicker", String.format("月----------------------%s", newCheckedMonth));
                    int monthNum = newCheckedMonth;
                    list = new ArrayList<>();
                    for (int i = 1; i <= 31; i++) {
                        list.add(String.valueOf(i));
                    }
                    days = list.toArray(days);
                    pvDay.setDisplayedValues(days);
                    pvDay.setMinValue(1);
                    // 判断大小月及是否闰年,用来确定"日"的数据
                    if (bigMonthList.contains(String.valueOf(monthNum))) {
                        pvDay.setMaxValue(31);
                    } else if (littleMonthList.contains(String.valueOf(monthNum))) {
                        pvDay.setMaxValue(30);
                    } else {
                        int pvYearValue = pvYear.getValue();
                        if ((pvYearValue % 4 == 0 && pvYearValue % 100 != 0) || pvYearValue % 400 == 0) {
                            pvDay.setMaxValue(29);
                        } else {
                            pvDay.setMaxValue(28);
                        }
                    }
                    if (day >= pvDay.getMaxValue()) {
                        pvDay.setValue(pvDay.getMaxValue());
                    } else {
                        pvDay.setValue(day);
                    }
                }
            });

            //=============================================
            //  简介：日
            //=============================================
            list = new ArrayList<>();
            for (int i = 1; i <= 31; i++) {
                list.add(String.valueOf(i));
            }
            days = list.toArray(days);
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
                }
            });

            //=============================================
            //  简介：时
            //=============================================
            list = new ArrayList<>();
            for (int i = 0; i <= 23; i++) {
                if (i < 10) {
                    list.add(String.format("0%s", i));
                } else {
                    list.add(String.valueOf(i));
                }
            }
            hours = list.toArray(hours);
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

            //=============================================
            //  简介：分
            //=============================================
            list = new ArrayList<>();
            for (int i = 0; i <= 59; i++) {
                if (i < 10) {
                    list.add(String.format("0%s", i));
                } else {
                    list.add(String.valueOf(i));
                }
            }
            minutes = list.toArray(minutes);
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

            //=============================================
            //  简介：秒
            //=============================================
            list = new ArrayList<>();
            for (int i = 0; i <= 59; i++) {
                if (i < 10) {
                    list.add(String.format("0%s", i));
                } else {
                    list.add(String.valueOf(i));
                }
            }
            seconds = list.toArray(seconds);
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
