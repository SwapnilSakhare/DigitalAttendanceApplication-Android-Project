package com.attendance.satvik.digtalattendance;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
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



public class MainActivity extends AppCompatActivity {
    EditText edtuserid,edtpass;
    Button btnlogin;
    ConnectionClass connectionClass;

    public SharedPreferences mSharedPreferences;
    public static final String PREFERENCE= "preference";
    public static final String PREF_NAME = "name";
    public static final String PREF_PASSWD = "passwd";
    public static final String PREF_TYPE_ADMIN = "ADMIN";
    public static final String PREF_TYPE_STUDENT = "STUDENT";
    public static final String PREF_TYPE_TEACHER = "TEACHER";
    public static final String PREF_SKIP_LOGIN = "skip_login";
    SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSharedPreferences = getApplicationContext().getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        mEditor=mSharedPreferences.edit();

        connectionClass = new ConnectionClass();
        edtuserid =  findViewById(R.id.uid);
         edtpass =  findViewById(R.id.pass);
        btnlogin =  findViewById(R.id.login_btn);

        if(mSharedPreferences.contains(PREF_NAME) && mSharedPreferences.contains(PREF_TYPE_ADMIN)){
            mEditor = mSharedPreferences.edit();
            mEditor.putString(PREF_SKIP_LOGIN,"skip");
            mEditor.apply();
            Intent i = new Intent(MainActivity.this, Admin_HomeActivity.class);
            startActivity(i);
            finish();
        }else {
            if(mSharedPreferences.contains(PREF_NAME) && mSharedPreferences.contains(PREF_TYPE_TEACHER)){
                 mEditor = mSharedPreferences.edit();
                mEditor.putString(PREF_SKIP_LOGIN,"skip");
                mEditor.apply();
                Intent i = new Intent(MainActivity.this, Teacher_HomeActivity.class);
                startActivity(i);
                finish();
            }else {
                if(mSharedPreferences.contains(PREF_NAME) && mSharedPreferences.contains(PREF_TYPE_STUDENT)){
                    mEditor = mSharedPreferences.edit();
                    mEditor.putString(PREF_SKIP_LOGIN,"skip");
                    mEditor.apply();
                    Intent i = new Intent(MainActivity.this, Student_HomeActivity.class);
                    startActivity(i);
                    finish();
                }
            }

        }

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DoLogin doLogin = new DoLogin();
                doLogin.execute("");

                final String pass = edtpass.getText().toString();
                if (!isValidPassword(pass)) {
                    edtpass.setError("Invalid Password");
                }
            }
        });

    }
    // validating password with retype password
    private boolean isValidPassword(String pass) {
        if (pass != null && pass.length() <= 10 ) {
            return true;
        }
        return false;
    }

    @SuppressLint("StaticFieldLeak")
    public class DoLogin extends AsyncTask<String,String,String>
    {
        String z = "";
        Boolean isSuccess = false;

        String userid = edtuserid.getText().toString();
        String password = edtpass.getText().toString();




        @Override
        protected void onPostExecute(String z) {

            Toast.makeText(MainActivity.this,z,Toast.LENGTH_SHORT).show();

            if(z.equals("Teacher Login successfully")) {
                mEditor.putString("eduserid",userid);
                mEditor.putString("edtpass",password);
                mEditor.commit();
                Intent i = new Intent(MainActivity.this, Teacher_HomeActivity.class);
                startActivity(i);

//                finish();

            }
            if(z.equals("Student Login successfully")){
                mEditor.putString("eduserid",userid);
                mEditor.putString("edtpass",password);
                mEditor.commit();
                    Intent i = new Intent(MainActivity.this, Student_HomeActivity.class);
                    startActivity(i);


            }


        }

        @Override
        protected String doInBackground(String... params) {
            if (userid.trim().equals("") || password.trim().equals(""))
                z = "Please enter User Id and Password";

            else
            {
                if (userid.equals("admin") && password.equals("admin123")) {
//                   Toast.makeText(MainActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                    SharedPreferences mSharedPreference = getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
                  mEditor = mSharedPreference.edit();
                    mEditor.putString(PREF_NAME,"admin");
                    mEditor.putString(PREF_PASSWD,"admin123");
                    mEditor.putString(PREF_TYPE_ADMIN,"ADMIN");
                    mEditor.apply();

                    Intent i = new Intent(MainActivity.this, Admin_HomeActivity.class);
                    startActivity(i);
                    z="Admin Login Successful!";
                    finish();
                }else {

                    try {
                        Connection con = connectionClass.CONN();
                        if (con == null) {
                            z = "Error in connection with SQL server";
                        } else {
                            String query = "select * from teacher where Teach_uid='" + userid + "' and Password='" + password + "'";
                            Statement stmt = con.createStatement();
                            ResultSet rs = stmt.executeQuery(query);

                            if (rs.next()) {

                                z = "Teacher Login successfully";
                                isSuccess = true;
                                SharedPreferences mSharedPreference = getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
                                mEditor = mSharedPreference.edit();
                                mEditor.putString(PREF_NAME,userid);
                                mEditor.putString(PREF_PASSWD,password);
                                mEditor.putString(PREF_TYPE_TEACHER,"TEACHER");
                                mEditor.apply();



                            } else {
//                                z = "Invalid Credentials";
//                                isSuccess = false;
                                String query1 = "select * from student where Stud_uid='" + userid + "' and Password='" + password + "'";
                                Statement stmt1 = con.createStatement();
                                ResultSet rs1 = stmt1.executeQuery(query1);

                                if(rs1.next())
                                {

                                    z = "Student Login successfully";
                                    isSuccess=true;
                                    SharedPreferences mSharedPreference = getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
                                    mEditor = mSharedPreference.edit();
                                    mEditor.putString(PREF_NAME,userid);
                                    mEditor.putString(PREF_PASSWD,password);
                                    mEditor.putString(PREF_TYPE_STUDENT,"STUDENT");

                                    mEditor.apply();
                                }
                                else
                                {
                                    z = "Invalid Credentials";
                                    isSuccess = false;
                                }

                            }


                        }
                    } catch (Exception ex) {
                        isSuccess = false;
                        z = "Exceptions";
                    }
                }
            }
            return z;
        }
    }


}
