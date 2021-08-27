package com.attendance.satvik.digtalattendance;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class Admin_HomeActivity extends AppCompatActivity {

    ImageView Alogout;
    public SharedPreferences mSharedPreferences;
    public static final String PREFERENCE= "preference";
    public static final String PREF_NAME = "name";
    public static final String PREF_PASSWD = "passwd";
    public static final String PREF_TYPE_ADMIN = "ADMIN";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__home);


        Alogout=findViewById(R.id.logout);
        Alogout.setOnClickListener(new View.OnClickListener() {
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
        editor.remove("ADMIN");
        editor.clear();
        editor.commit();

        startActivity(new Intent(Admin_HomeActivity.this,MainActivity.class));
        Toast.makeText(this, "Logout Successful!", Toast.LENGTH_SHORT).show();
    finish();

    }

    public void ViewReportClick(View view) {
        Intent i = new Intent(this, ReportActivity.class);
        startActivity(i);

    }

    public void Add_TeacherClick(View view) {
        Intent i = new Intent(this, Add_TeacherActivity.class);
        startActivity(i);
    }

    public void Update_TeacherClick(View view) {
        Intent i = new Intent(this, Update_TeacherActivity.class);
        startActivity(i);
    }

    public void Remove_TeacherClick(View view) {
        Intent i = new Intent(this, remove_teacher.class);
        startActivity(i);
    }

    public void AboutusClick(View view) {
        Intent i = new Intent(this, About_Us.class);
        startActivity(i);
    }


}
