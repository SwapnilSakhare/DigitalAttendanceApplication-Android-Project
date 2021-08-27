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

// header files are imported for database connectivity
import java.sql.Connection;
import java.sql.Statement;

public class Add_TeacherActivity extends AppCompatActivity {
        EditText TeacherId,TeacherName,Password;
        Spinner Subject;
        Button addTeacher;
    ConnectionClass connectionClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__teacher);

        connectionClass = new ConnectionClass();
        TeacherId= findViewById(R.id.editTeacherid);
        TeacherName= findViewById(R.id.editteachername);
        Password= findViewById(R.id.setTeacherpass);
        Subject= findViewById(R.id.selectsub);
        addTeacher=findViewById(R.id.teacheradd);

        addTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DoAddTeacher doAddTeacher = new DoAddTeacher();
                doAddTeacher.execute("");

            }
        });

    }

    @SuppressLint("StaticFieldLeak")
    public class DoAddTeacher extends AsyncTask<String,String,String>
    {
        String z = "";
        Boolean isSuccess = false;


        String TeachID = TeacherId.getText().toString();
        String TeachName = TeacherName.getText().toString();
        String selectSub = Subject.getSelectedItem().toString();
        String pass = Password.getText().toString();




        @Override
        protected void onPostExecute(String r) {
            Toast.makeText(Add_TeacherActivity.this,r,Toast.LENGTH_SHORT).show();
            if(z.equals("Data Field successfully.")) {
                Toast.makeText(Add_TeacherActivity.this, "Data Inserted Successfully.",Toast.LENGTH_SHORT).show();

            }
        }

        @Override
        protected String doInBackground(String... params) {
            if(TeachID.trim().equals("")|| TeachName.trim().equals("")|| selectSub.trim().equals("")|| pass.trim().equals(""))
                z = "Please enter TeacherID,TeacherName,Subject and Password";
            else
            {
                try {
                    Connection con = connectionClass.CONN();
                    if (con == null) {
                        z = "Error in connection with SQL server";
                    } else {
                        String query = "insert into teacher values('"+TeachID+"','"+TeachName+"','"+selectSub+"','"+pass+"')";
                        Statement stmt = con.createStatement();
                        stmt.executeUpdate(query);
                        z = "Data Field successfully.";
                        isSuccess=true;

                        TeacherId.setText("");
                        TeacherName.setText("");
                        Password.setText("");

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
