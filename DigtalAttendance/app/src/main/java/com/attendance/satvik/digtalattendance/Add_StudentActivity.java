package com.attendance.satvik.digtalattendance;


import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.Statement;


public class Add_StudentActivity extends AppCompatActivity {
    EditText S_name,S_id,S_rol,S_pass;
    Spinner S_class;
    Button addStudent;
    ConnectionClass connectionClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__student);

        connectionClass = new ConnectionClass();
        S_id =  findViewById(R.id.editid);
        S_name = findViewById(R.id.editstudname);
        S_rol =  findViewById(R.id.editstudrol);
        S_class =  findViewById(R.id.editclass);
        S_pass =  findViewById(R.id.editpass);

        addStudent=findViewById(R.id.Add_S);

        addStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DoAddStudent doAddStudent = new DoAddStudent();
                doAddStudent.execute("");

            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    public class DoAddStudent extends AsyncTask<String,String,String>
    {
        String z = "";
        Boolean isSuccess = false;


        String StudID = S_id.getText().toString();
        String StudName = S_name.getText().toString();
        String StudRoll = S_rol.getText().toString();
        String StudClass = S_class.getSelectedItem().toString();
        String pass = S_pass.getText().toString();



        @Override
        protected void onPostExecute(String r) {
//            Toast.makeText(Add_StudentActivity.this,r,Toast.LENGTH_SHORT).show();
            if(z.equals("Data Field successfully.")) {
                Toast.makeText(Add_StudentActivity.this, "Data Inserted Successfully.",Toast.LENGTH_SHORT).show();

            }
        }

        @Override
        protected String doInBackground(String... params) {
            if(StudID.trim().equals("")|| StudName.trim().equals("")|| StudRoll.trim().equals("")|| StudClass.trim().equals("")|| pass.trim().equals(""))
                z = "Please enter StudentID, Student Name, Roll No, Class and Password";
            else
            {
                try {
                    Connection con = connectionClass.CONN();
                    if (con == null) {
                        z = "Error in connection with SQL server";
                    } else {
                        String query = "insert into student values('"+StudID+"','"+StudName+"','"+StudRoll+"','"+StudClass+"','"+pass+"')";
                        Statement stmt = con.createStatement();
                        stmt.executeUpdate(query);
                        z = "Data Field successfully.";
                        isSuccess=true;

                        S_id.setText("");
                        S_name.setText("");
                        S_rol.setText("");
                        S_pass.setText("");

//                        if(rs.next())
//                        {
//
//                            z = "Login successfull";
//                            isSuccess=true;
//                        }
//                        else
//                        {
//                            z = "Invalid Credentials";
//                            isSuccess = false;
//                        }

                    }
                }
                catch (Exception ex)
                {
                    isSuccess = false;
                    z = "Exceptions";
                }
            }
            return z;
        }
    }


}
