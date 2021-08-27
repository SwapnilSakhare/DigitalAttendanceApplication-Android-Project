package com.attendance.satvik.digtalattendance;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class remove_teacher extends AppCompatActivity {
    ConnectionClass connectionClass;
    EditText searchid;
    TextView teachName,teachSub,teachID;
    Button Searchbtn,Deletebtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_teacher);

        connectionClass = new ConnectionClass();
        searchid = findViewById(R.id.rmTeachId);

        teachID = findViewById(R.id.printTeach_id);
        teachName = findViewById(R.id.printTeach_name);
        teachSub = findViewById(R.id.printTeach_Subject);

        Searchbtn = findViewById(R.id.searchStudbtn);
        Searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DoSearchTeach Dosearch =new DoSearchTeach();
                Dosearch.execute();
            }
        });
        Deletebtn = findViewById(R.id.RemoveTeachbtn);
        Deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DoDelete Dodelete =new DoDelete();
                Dodelete.execute();
            }
        });



    }

// set the teacher details in textview from database
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
                Toast.makeText(remove_teacher.this, "Uid Matched Successfully!",Toast.LENGTH_SHORT).show();

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
                        String query = "select * from teacher where Teach_uid='" + TeachID +"'";
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(query);

                        if(rs.next())
                        {
                            teachID.setText(rs.getString("Teach_uid"));
                            teachName.setText(rs.getString("Teach_name"));
                            teachSub.setText(rs.getString("Subject"));

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



    @SuppressLint("StaticFieldLeak")
    public class DoDelete extends AsyncTask<String,String,String>
    {
        String z = "";
        Boolean isSuccess = false;


        String TeachID = searchid.getText().toString();


        @Override
        protected void onPostExecute(String r) {
//            Toast.makeText(Add_StudentActivity.this,r,Toast.LENGTH_SHORT).show();
            if(z.equals("Data Deleted Successfully!")) {
                Toast.makeText(remove_teacher.this, "Data Deleted Successfully!",Toast.LENGTH_SHORT).show();

            }
        }

        @Override
        protected String doInBackground(String... params) {
            if (TeachID.trim().equals("")  )
//|| TeacherName.trim().equals("")|| TeachSubject.trim().equals("")
                z = "Please enter Teacher UID";

            else
            {

                try {
                    Connection con = connectionClass.CONN();
                    if (con == null) {
                        z = "Error in connection with SQL server";
                    } else {

                        String query1 = "delete from teacher where Teach_uid='"+TeachID+"'";
                        Statement stmt1 = con.createStatement();
                        stmt1.executeUpdate(query1);

                        z = "Data Deleted Successfully!";
                        isSuccess=true;

                        searchid.setText("");

                        teachID.setText("");
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
