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

public class Update_TeacherActivity extends AppCompatActivity {
    ConnectionClass connectionClass;
    EditText searchid,teachName,teachSub;
    Button Searchbtn,Updatebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update__teacher);

        connectionClass = new ConnectionClass();
        searchid = findViewById(R.id.SearchId);
        teachName = findViewById(R.id.editTeachname);
        teachSub = findViewById(R.id.selectsub);
        Searchbtn = findViewById(R.id.seachbtn);
        Searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DoSearchTeach Dosearch =new DoSearchTeach();
                Dosearch.execute();
            }
        });

        Updatebtn = findViewById(R.id.updateTeach);
        Updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DoUpdateTeach Doupdate =new DoUpdateTeach();
                Doupdate.execute();
            }
        });
    }


    @SuppressLint("StaticFieldLeak")
    public class DoSearchTeach extends AsyncTask<String,String,String>
    {
        String z = "";
        Boolean isSuccess = false;


        String TeachID = searchid.getText().toString();

        @Override
        protected void onPostExecute(String r) {
//            Toast.makeText(Add_StudentActivity.this,r,Toast.LENGTH_SHORT).show();
            if(z.equals("Uid Matched Successfully!")) {
                Toast.makeText(Update_TeacherActivity.this, "Uid Matched Successfully!",Toast.LENGTH_SHORT).show();

            }
        }


// Data set in editText from database by matching Teacher UID.
        @Override
        protected String doInBackground(String... params) {
            if (TeachID.trim().equals("")  )
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
                                String query1 = "select Teach_name,Subject from teacher where Teach_uid='" + TeachID +"'";
                                Statement stmt1 = con.createStatement();
                                ResultSet rs1 = stmt1.executeQuery(query1);

                                if(rs1.next())
                                {
                                    teachName.setText(rs1.getString("Teach_name"));
                                    teachSub.setText(rs1.getString("Subject"));
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


//    THis Class for update the teacher details from editText widget to database.

    @SuppressLint("StaticFieldLeak")
    public class DoUpdateTeach extends AsyncTask<String,String,String>
    {
        String z = "";
        Boolean isSuccess = false;


        String TeachID = searchid.getText().toString();
        String TeacherName = teachName.getText().toString();
        String TeachSubject = teachSub.getText().toString();

        @Override
        protected void onPostExecute(String r) {
//            Toast.makeText(Add_StudentActivity.this,r,Toast.LENGTH_SHORT).show();
            if(z.equals("Details Modified Successfully!")) {
                Toast.makeText(Update_TeacherActivity.this, "Details Modified Successfully!",Toast.LENGTH_SHORT).show();

            }
        }

        @Override
        protected String doInBackground(String... params) {
            if (TeachID.trim().equals("") || TeacherName.trim().equals("")|| TeachSubject.trim().equals("") )
//
                z = "Please enter Teacher UID";

            else
            {

                try {
                    Connection con = connectionClass.CONN();
                    if (con == null) {
                        z = "Error in connection with SQL server";
                    } else {
//
                        String query1 = "update teacher set Teach_name='"+TeacherName+"', Subject='"+TeachSubject+"' where Teach_uid='"+TeachID+"'";
                        Statement stmt1 = con.createStatement();
                        stmt1.executeUpdate(query1);

                        z = "Details Modified Successfully!";
                        isSuccess=true;

                        searchid.setText("");
                        teachName.setText("");
                        teachSub.setText("");
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
