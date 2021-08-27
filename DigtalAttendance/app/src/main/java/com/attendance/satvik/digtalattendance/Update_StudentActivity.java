package com.attendance.satvik.digtalattendance;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Update_StudentActivity extends AppCompatActivity {
    ConnectionClass connectionClass;
    EditText searchStudid,studname,studroll,studclass;
    Button SearchSbtn,SUpdatebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update__student);

        connectionClass = new ConnectionClass();
        searchStudid=findViewById(R.id.SsearchId);
        studname=findViewById(R.id.editstudname2);
        studroll=findViewById(R.id.editstudrol3);
        studclass=findViewById(R.id.editclass4);

        SearchSbtn=findViewById(R.id.seachbtn);
        SearchSbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DoSearchStud Dosearchs =new DoSearchStud();
                Dosearchs.execute();
            }
        });

        SUpdatebtn=findViewById(R.id.StudUpdate);
        SUpdatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DoUpdateStud Doupdate =new DoUpdateStud();
                Doupdate.execute();
            }
        });

    }

    @SuppressLint("StaticFieldLeak")
    public class DoSearchStud extends AsyncTask<String,String,String>
    {
        String z = "";
        Boolean isSuccess = false;


        String StudID = searchStudid.getText().toString();

        @Override
        protected void onPostExecute(String r) {
//            Toast.makeText(Add_StudentActivity.this,r,Toast.LENGTH_SHORT).show();
            if(z.equals("Uid Matched Successfully!")) {
                Toast.makeText(Update_StudentActivity.this, "Uid Matched Successfully!",Toast.LENGTH_SHORT).show();

            }
        }

        // Data set in editText from database by matching Teacher UID.
        @Override
        protected String doInBackground(String... params) {
            if (StudID.trim().equals("")  )
//                || password.trim().equals("")
                z = "Please enter Teacher UID";

            else
            {

                try {
                    Connection con = connectionClass.CONN();
                    if (con == null) {
                        z = "Error in connection with SQL server";
                    } else {
//
                        String query1 = "select Stud_name,Roll_no,Class from student where Stud_uid='" + StudID +"'";
                        Statement stmt1 = con.createStatement();
                        ResultSet rs1 = stmt1.executeQuery(query1);

                        if(rs1.next())
                        {
                            studname.setText(rs1.getString("Stud_name"));
                            studroll.setText(rs1.getString("Roll_no"));
                            studclass.setText(rs1.getString("Class"));
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


    //    THis Class for update the Student details from editText widget to database.

    @SuppressLint("StaticFieldLeak")
    public class DoUpdateStud extends AsyncTask<String,String,String>
    {
        String z = "";
        Boolean isSuccess = false;


        String StudID = searchStudid.getText().toString();
        String StudName = studname.getText().toString();
        String StudRoll = studroll.getText().toString();
        String StudClass = studclass.getText().toString();

        @Override
        protected void onPostExecute(String r) {
//            Toast.makeText(Add_StudentActivity.this,r,Toast.LENGTH_SHORT).show();
            if(z.equals("Details Modified Successfully!")) {
                Toast.makeText(Update_StudentActivity.this, "Details Modified Successfully!",Toast.LENGTH_SHORT).show();

            }
        }

        @Override
        protected String doInBackground(String... params) {
            if (StudID.trim().equals("") || StudName.trim().equals("")|| StudRoll.trim().equals("") || StudClass.trim().equals("") )
//
                z = "Please enter Student UID";

            else
            {

                try {
                    Connection con = connectionClass.CONN();
                    if (con == null) {
                        z = "Error in connection with SQL server";
                    } else {
//
                        String query1 = "update student set Stud_name='"+StudName+"', Roll_no='"+StudRoll+"',Class='"+StudClass+"' where Stud_uid='"+StudID+"'";
                        Statement stmt1 = con.createStatement();
                        stmt1.executeUpdate(query1);

                        z = "Details Modified Successfully!";
                        isSuccess=true;

                        searchStudid.setText("");
                        studname.setText("");
                        studroll.setText("");
                        studclass.setText("");
//                        if(rs1.next())
//                        {
//
//                            z = "Details Modified Successfully!";
//                            isSuccess=true;
//
//                        }
//                        else
//                        {
//                            z = "Invalid Teacher UID";
//                            isSuccess = false;
//                        }

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
