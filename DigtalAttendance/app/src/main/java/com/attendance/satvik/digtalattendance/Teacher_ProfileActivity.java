package com.attendance.satvik.digtalattendance;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Teacher_ProfileActivity extends AppCompatActivity {

    public SharedPreferences mSharedPreferences;
    public static final String PREFERENCE= "preference";
    public static final String PREF_NAME = "name";
    public static final String PREF_TYPE_TEACHER = "TEACHER";
    SharedPreferences.Editor editor;

    ConnectionClass connectionClass;
    String userid;
    TextView Tid,Tname,Tsubject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher__profile);

        mSharedPreferences= getApplication().getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        userid=mSharedPreferences.getString("eduserid","");

        connectionClass = new ConnectionClass();
        Tid=findViewById(R.id.pTeach_id);
        Tname=findViewById(R.id.pTeach_name);
        Tsubject=findViewById(R.id.pTeach_Subject);

        DoSetTeacherId DoSet =new DoSetTeacherId();
        DoSet.execute();
    }

    // set the Student details in textview from database
    @SuppressLint("StaticFieldLeak")
    public class DoSetTeacherId extends AsyncTask<String,String,String>
    {
        String z = "";
        Boolean isSuccess = false;
        String TeachID = userid;

//        ProgressDialog progressDialog;
//        @Override
//        protected void onPreExecute() {
//
//            progressDialog = ProgressDialog.show(Teacher_ProfileActivity.this,"","Please Wait...",true);
//
//        }
        @Override
        protected void onPostExecute(String r) {
        //Toast.makeText(Teacher_ProfileActivity.this,r,Toast.LENGTH_SHORT).show();
//            if(z.equals("Uid Matched Successfully!")) {
//                Toast.makeText(Teacher_ProfileActivity.this, "Uid Matched Successfully!",Toast.LENGTH_SHORT).show();
//
//            }
        }

        // Data set in editText from database by matching Teacher UID.
        @Override
        protected String doInBackground(String... params) {
            if (TeachID.equals("")  )
//                || password.trim().equals("")
                z = "Please enter Teacher UID";

            else
            {

                try {
                    Connection con = connectionClass.CONN();
                    if (con == null) {
                        z = "Error in connection with SQL server";
                    } else {
// Sql Query for select data from database and show into the defined labels.
                        String query = "select * from teacher where Teach_uid='" + TeachID +"'";
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(query);

                        if(rs.next())
                        {
                            Tid.setText(rs.getString("Teach_uid"));
                            Tname.setText(rs.getString("Teach_name"));
                            Tsubject.setText(rs.getString("Subject"));

                            z = "Uid Matched Successfully!";
                            isSuccess=true;

                        }
                        else
                        {
                            z = "Invalid Teacher UID";
                            isSuccess = false;
                        }

                    }

                } catch (Exception ex) {
                    isSuccess = false;
                    z = "Exceptions";
                }
            }

            return z;
        }
    }

}
