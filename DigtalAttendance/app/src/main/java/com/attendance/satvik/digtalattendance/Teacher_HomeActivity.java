package com.attendance.satvik.digtalattendance;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class Teacher_HomeActivity extends AppCompatActivity {
     ImageView img,img2,img3,Tlogout;
    public SharedPreferences mSharedPreferences;
    public static final String PREFERENCE= "preference";
    public static final String PREF_NAME = "name";
    public static final String PREF_PASSWD = "passwd";
    public static final String PREF_TYPE_TEACHER= "TEACHER";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher__home);



        Tlogout = findViewById(R.id.signout);
        Tlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        img=findViewById(R.id.update_student);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(Teacher_HomeActivity.this,Update_StudentActivity.class);
                startActivity(intent);

            }
        });

        img2=findViewById(R.id.attedance_take);

        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(Teacher_HomeActivity.this,attendance_page.class);
                startActivity(intent);

            }
        });

        img3=findViewById(R.id.view_report);

        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(Teacher_HomeActivity.this,ReportActivity.class);
                startActivity(intent);

            }
        });

    }
    private void logout(){
        mSharedPreferences= getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE );
        SharedPreferences.Editor editor= mSharedPreferences.edit();
        editor.remove("name");
        editor.remove("TEACHER");
        editor.clear();
        editor.commit();
        startActivity(new Intent(Teacher_HomeActivity.this,MainActivity.class));
        Toast.makeText(this, "Logout Successful!", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void Add_StudentClick(View view) {

        Intent i = new Intent(this, Add_StudentActivity.class);
        startActivity(i);
    }



    public void RemoveStudentClick(View view) {

        Intent i = new Intent(this, remove_student.class);
        startActivity(i);
    }

    public void Student_Change_PasswordClick(View view) {

        Intent i = new Intent(this, Change_PasswordActivity.class);
        startActivity(i);
    }


    public void AboutusClick(View view) {
        Intent i = new Intent(this, About_Us.class);
        startActivity(i);
    }



    public void Teacher_ProfileClick(View view) {
        Intent i = new Intent(this, Teacher_ProfileActivity.class);
        startActivity(i);
    }


}
