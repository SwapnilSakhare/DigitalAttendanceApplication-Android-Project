package com.attendance.satvik.digtalattendance;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.Calendar;
import android.app.DatePickerDialog;

public class dateSelectReport extends AppCompatActivity {

    Button btnRsubmit;
    TextView tvReportDate;
    String repoDate;
    Calendar calendar;
    int repoYear;
    int repoMonth;
    int day;
    DatePickerDialog ReportDatePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_select_report);

        btnRsubmit = (Button) findViewById(R.id.btndatereportsubmit);
        tvReportDate = (TextView) findViewById(R.id.ReportDate);
        repoDate = tvReportDate.getText().toString();

        btnRsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(dateSelectReport.this,ReportActivity.class);
                startActivity(i);
            }
        });


        tvReportDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                repoYear = calendar.get(Calendar.YEAR);
                repoMonth = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                ReportDatePicker = new DatePickerDialog(dateSelectReport.this,new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(android.widget.DatePicker view, int repoYear, int repoMonth, int day) {
                        tvReportDate.setText(repoYear+"/"+(repoMonth+1)+"/"+day);
                    }
                },repoYear,repoMonth,day);

                ReportDatePicker.show();
            }
        });
    }
}
