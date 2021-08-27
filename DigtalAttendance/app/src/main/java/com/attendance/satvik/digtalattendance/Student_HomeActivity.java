package com.attendance.satvik.digtalattendance;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class Student_HomeActivity extends AppCompatActivity {
    ImageView SLogout;
    public SharedPreferences mSharedPreferences;
    public static final String PREFERENCE= "preference";
    public static final String PREF_NAME = "name";
    public static final String PREF_PASSWD = "passwd";
    public static final String PREF_TYPE_STUDENT = "STUDENT";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student__home);


        SLogout = findViewById(R.id.Studlogout);
        SLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
    }

    private void logout(){
        mSharedPreferences= getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE );
        SharedPreferences.Editor editor= mSharedPreferences.edit();
        editor.remove("name");
        editor.remove("STUDENT");
        editor.clear();
        editor.commit();
        startActivity(new Intent(Student_HomeActivity.this,MainActivity.class));
        Toast.makeText(this, "Logout Successful!", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void ViewReportClick(View view) {
        Intent i = new Intent(this, ReportActivity.class);
        startActivity(i);
    }

    public void Change_PasswordClick(View view) {
        Intent i = new Intent(this, Change_PasswordActivity.class);
        startActivity(i);
    }

    public void Student_AboutusClick(View view) {
        Intent i = new Intent(this, About_Us.class);
        startActivity(i);
    }


    public void ProfileClick(View view) {
        Intent i = new Intent(this, Student_ProfileActivity.class);
        startActivity(i);
    }
}
