package com.android.wheelviewcalendar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.android.wheelviewcalendar.wheelpicker.LoopView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private LoopView pickerYear, pickerMonth, pickerDay;


    private int yearPos, monthPos, dayPos, minYear, maxYear;
    private ArrayList yearList = new ArrayList(), monthList = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialized();
    }

    private void initialized() {
        pickerYear = findViewById(R.id.picker_year);
        pickerMonth = findViewById(R.id.picker_month);
        pickerDay = findViewById(R.id.picker_day);

        setData();
        setListener();
    }

    private void setData() {
        minYear = Calendar.getInstance().get(Calendar.YEAR) - 100;
        maxYear = Calendar.getInstance().get(Calendar.YEAR) + 10;

        Calendar today = Calendar.getInstance();
        yearPos = today.get(Calendar.YEAR) - minYear;
        monthPos = today.get(Calendar.MONTH);
        dayPos = today.get(Calendar.DAY_OF_MONTH) - 1;

        pickerYear.setNotLoop();
        pickerMonth.setNotLoop();
        pickerDay.setNotLoop();
    }

    private void setListener() {
        pickerYear.setListener(item -> {
            yearPos = item;
            initDayPickerView();
        });

        pickerMonth.setListener(item -> {
            monthPos = item;
            initDayPickerView();
        });
        pickerDay.setListener(item -> dayPos = item);

        initPickerViews(); //
        initDayPickerView();
        pickerDay.setInitPosition(dayPos);
    }

    private void initDayPickerView() {
        int dayMaxInMonth;
        Calendar calendar = Calendar.getInstance();
        ArrayList dayList = new ArrayList<String>();

        calendar.set(Calendar.MONTH, monthPos);
        dayMaxInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        for (int i = 0; i < dayMaxInMonth; i++) {
            dayList.add(format2LenStr(i + 1));
        }
        pickerDay.setArrayList(dayList);
    }

    private void initPickerViews() {

        int yearCount = maxYear - minYear;

        for (int i = 0; i < yearCount; i++) {
            yearList.add(format2LenStr(minYear + i));
        }

        monthList.addAll(Arrays.asList(getResources().getStringArray(R.array.monthsArray)));

        pickerYear.setArrayList(yearList);
        pickerYear.setInitPosition(yearPos);
        pickerMonth.setArrayList(monthList);
        pickerMonth.setInitPosition(monthPos);
    }

    public static String format2LenStr(int num) {
        return (num < 10) ? "0" + num : String.valueOf(num);
    }
}