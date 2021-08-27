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

public class remove_student extends AppCompatActivity {

    ConnectionClass connectionClass;
    EditText StudsId;
    TextView ShowStudId, ShowStudName, ShowRoll, ShowClass;
    Button ShowStudBtn,DeleteStudBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_student);

        connectionClass = new ConnectionClass();

// EditText
        StudsId= findViewById(R.id.SearchSId);

// TextView
        ShowStudId= findViewById(R.id.printStud_id);
        ShowStudName= findViewById(R.id.printStud_name);
        ShowRoll= findViewById(R.id.printStud_rollno);
        ShowClass= findViewById(R.id.printStud_class);

// Show Button and method
        ShowStudBtn= findViewById(R.id.searchStudbtn);
        ShowStudBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DoSearchStudId Dosearch =new DoSearchStudId();
                Dosearch.execute();
            }
        });
// Delete Button and method
        DeleteStudBtn= findViewById(R.id.RemoveStudbtn);
        DeleteStudBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DoDeleteStud Dodelete =new DoDeleteStud();
                Dodelete.execute();
            }
        });
    }


    // set the Student details in textview from database
    @SuppressLint("StaticFieldLeak")
    public class DoSearchStudId extends AsyncTask<String,String,String>
    {
        String z = "";
        Boolean isSuccess = false;


        String StudID = StudsId.getText().toString();

        @Override
        protected void onPostExecute(String r) {
//            Toast.makeText(Add_StudentActivity.this,r,Toast.LENGTH_SHORT).show();
            if(z.equals("Uid Matched Successfully!")) {
                Toast.makeText(remove_student.this, "Uid Matched Successfully!",Toast.LENGTH_SHORT).show();

            }
        }

        // Data set in editText from database by matching Teacher UID.
        @Override
        protected String doInBackground(String... params) {
            if (StudID.trim().equals("")  )
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
                            ShowStudId.setText(rs.getString("Stud_uid"));
                            ShowStudName.setText(rs.getString("Stud_name"));
                            ShowRoll.setText(rs.getString("Roll_no"));
                            ShowClass.setText(rs.getString("Class"));

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


//    Delete the Student data by using Student UID.
    @SuppressLint("StaticFieldLeak")
    public class DoDeleteStud extends AsyncTask<String,String,String>
    {
        String z = "";
        Boolean isSuccess = false;


        String StudID = StudsId.getText().toString();


        @Override
        protected void onPostExecute(String r) {
//            Toast.makeText(Add_StudentActivity.this,r,Toast.LENGTH_SHORT).show();
            if(z.equals("Data Deleted Successfully!")) {
                Toast.makeText(remove_student.this, "Data Deleted Successfully!",Toast.LENGTH_SHORT).show();

            }
        }

        @Override
        protected String doInBackground(String... params) {
            if (StudID.trim().equals("")  )
//|| TeacherName.trim().equals("")|| TeachSubject.trim().equals("")
                z = "Please enter Student UID";

            else
            {

                try {
                    Connection con = connectionClass.CONN();
                    if (con == null) {
                        z = "Error in connection with SQL server";
                    } else {

                        String query1 = "delete from student where Stud_uid='"+StudID+"'";
                        Statement stmt1 = con.createStatement();
                        stmt1.executeUpdate(query1);

                        z = "Data Deleted Successfully!";
                        isSuccess=true;

                        StudsId.setText("");

// TextView
                        ShowStudId.setText("");
                        ShowStudName.setText("");
                        ShowRoll.setText("");
                        ShowClass.setText("");
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
