package com.attendance.satvik.digtalattendance;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Student_ProfileActivity extends AppCompatActivity {
    public SharedPreferences mSharedPreferences;
    public static final String PREFERENCE= "preference";
    public static final String PREF_NAME = "name";
   public static final String PREF_TYPE_STUDENT = "STUDENT";
   SharedPreferences.Editor editor;

    ConnectionClass connectionClass;
String userid;
   TextView sid,sname,srol,sclas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student__profile);

        mSharedPreferences= getApplication().getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);

        userid=mSharedPreferences.getString("eduserid","");

        connectionClass = new ConnectionClass();
        sid= findViewById(R.id.prinStud_id);
        sname= findViewById(R.id.prinStud_name);
        srol= findViewById(R.id.prinRollno);
        sclas= findViewById(R.id.prinStud_Class);


        DoSetStudId DoSet =new DoSetStudId();
        DoSet.execute();
    }


    // set the Student details in textview from database
    @SuppressLint("StaticFieldLeak")
    public class DoSetStudId extends AsyncTask<String,String,String>
    {
        String z = "";
        Boolean isSuccess = false;
     String StudID = userid;

        ProgressDialog progressDialog;

//        @Override
//        protected void onPreExecute() {
//
//            progressDialog = ProgressDialog.show(Student_ProfileActivity.this,"","Please Wait...",true);
//
//        }

        @Override
        protected void onPostExecute(String r) {
//            Toast.makeText(Add_StudentActivity.this,r,Toast.LENGTH_SHORT).show();
//            if(z.equals("Uid Matched Successfully!")) {
//                Toast.makeText(Student_ProfileActivity.this, "Uid Matched Successfully!",Toast.LENGTH_SHORT).show();
//
//            }
        }

        // Data set in editText from database by matching Teacher UID.
        @Override
        protected String doInBackground(String... params) {
            if (StudID.equals("")  )
//                || password.trim().equals("")
                z = "Please enter Student UID";

            else
            {

                try {
                    Connection con = connectionClass.CONN();
                    if (con == null) {
                        z = "Error in connection with SQL server";
                    } else {
// Sql Query for select data from database and show into the defined labels.
                        String query = "select * from student where Stud_uid='" + StudID +"'";
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(query);

                        if(rs.next())
                        {
                            sid.setText(rs.getString("Stud_uid"));
                            sname.setText(rs.getString("Stud_name"));
                            srol.setText(rs.getString("Roll_no"));
                            sclas.setText(rs.getString("Class"));

                            z = "Uid Matched Successfully!";
                            isSuccess=true;

                        }
                        else
                        {
                            z = "Invalid Student UID";
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



