package com.example.dairymanagement;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.DatePicker;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class datePicker {

    private int cDate,cMonth,cYear;

    public void dateonClick(TextInputEditText dateField, Context context){
        dateField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cal = Calendar.getInstance();
                cDate=cal.get(Calendar.DATE);
                cMonth=cal.get(Calendar.MONTH);
                cYear=cal.get(Calendar.YEAR);
//                MainActivity.this
                DatePickerDialog datePickerDialog=new DatePickerDialog(context, android.R.style.Theme_DeviceDefault_Dialog,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                                dateField.setText(y+"-"+m+"-"+d);
                            }
                        },cYear,cMonth,cDate );
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis()-1000);
                datePickerDialog.show();
            }
        });

    }

}
