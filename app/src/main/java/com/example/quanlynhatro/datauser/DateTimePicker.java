package com.example.quanlynhatro;

import android.app.DatePickerDialog;
import android.content.Context;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class DateTimePicker {

    public static void showDatePicker(Context context, TextInputEditText editText) {
        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(
                context,
                (view, y, m, d) -> {
                    String date = d + "/" + (m + 1) + "/" + y;
                    editText.setText(date);
                },
                year, month, day
        );
        dialog.show();
    }
}
