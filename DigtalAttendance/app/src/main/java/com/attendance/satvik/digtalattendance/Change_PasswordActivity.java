package com.attendance.satvik.digtalattendance;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.Statement;

public class Change_PasswordActivity extends AppCompatActivity {
    EditText Oldpass,Newpass,Confirm;
    Button submit;
    String userid,Cpass;
    ConnectionClass connectionClass;
    public SharedPreferences mSharedPreferences;
    public static final String PREFERENCE= "preference";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change__password);

        mSharedPreferences= getApplication().getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);

        userid=mSharedPreferences.getString("eduserid","");
       Cpass=mSharedPreferences.getString("edtpass","");

        connectionClass = new ConnectionClass();
        Oldpass= findViewById(R.id.oldpass);
        Newpass= findViewById(R.id.newpass);
        Confirm= findViewById(R.id.confpass);

        submit= findViewById(R.id.submit_btn);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DoUpdateStudPass Doupdate =new DoUpdateStudPass();
                Doupdate.execute();


                final String check = Confirm.getText().toString();
                if (!isValidCPassword(check)) {
                    Confirm.setError("Password no Matched");
                }

            }
        });

    }

    // validating password with retype password
    private boolean isValidCPassword(String check) {
        if (!(check == null) && check.length() == 10 && !check.equals(Newpass)) {
            return true;
        }
        return false;
    }

    //    THis Class for update the Student details from editText widget to database.

    @SuppressLint("StaticFieldLeak")
    public class DoUpdateStudPass extends AsyncTask<String,String,String>
    {
        String z = "";
        Boolean isSuccess = false;

        String UserID = userid;
        String UPass = Oldpass.getText().toString();
        String NPass = Newpass.getText().toString();
        String Confirmpass = Confirm.getText().toString();

        @Override
        protected void onPostExecute(String r) {
//            Toast.makeText(Add_StudentActivity.this,r,Toast.LENGTH_SHORT).show();
            if(z.equals("Student Modified Successfully!")) {

                Toast.makeText(Change_PasswordActivity.this, "Reset Successfully!",Toast.LENGTH_SHORT).show();

            }
            if(z.equals("Teacher Modified Successfully!")) {
                Toast.makeText(Change_PasswordActivity.this, "Reset Successfully!",Toast.LENGTH_SHORT).show();

            }
            if(z.equals("Password is incorrect.")) {
                Toast.makeText(Change_PasswordActivity.this, "Old Password is incorrect.",Toast.LENGTH_SHORT).show();

            }
            if(z.equals("New Password not matched.")) {
                Toast.makeText(Change_PasswordActivity.this, "New Password not matched.",Toast.LENGTH_SHORT).show();

            }
        }

        @Override
        protected String doInBackground(String... params) {
            if (UserID.trim().equals("") || UPass.trim().equals("")|| NPass.trim().equals("") || Confirmpass.trim().equals("") )
//
                z = "Please enter Student Password";

            else
            {

                try {
                    Connection con = connectionClass.CONN();
                    if (con == null) {
                        z = "Error in connection with SQL server";
                    } else {
                        if(UPass.equals(Cpass)) {
                            if (Confirmpass.equals(NPass)) {
                                String query1 = "update student set Password='" + Confirmpass + "' where Stud_uid='" + UserID + "' AND Password='" + UPass + "'";
                                Statement stmt1 = con.createStatement();
                                stmt1.executeUpdate(query1);

                                z = "Student Modified Successfully!";
                                isSuccess = true;
                                Oldpass.setText("");
                                Newpass.setText("");
                                Confirm.setText("");
                            }
                            if (Confirmpass.equals(NPass)) {
                                String query = "update teacher set Password='" + Confirmpass + "' where Teach_uid='" + UserID + "' AND Password='" + UPass + "'";
                                Statement stmt = con.createStatement();
                                stmt.executeUpdate(query);

                                z = "Teacher Modified Successfully!";
                                isSuccess = true;
                                Oldpass.setText("");
                                Newpass.setText("");
                                Confirm.setText("");
                            } else {
                                z = "New Password not matched.";
                                isSuccess = false;
                            }
                        }
                        else{
                            z="Password is incorrect.";

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
