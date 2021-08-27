package com.attendance.satvik.digtalattendance;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class Attend_list extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attend_list);

        Switch SButton=(Switch)findViewById(R.id.stud_attend_switch);

      /*  SButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean on) {
                if (on){
                   Toast.makeText(Attend_list.this, "Present", Toast.LENGTH_SHORT).show();

                }else {
                  Toast.makeText(Attend_list.this, "Absent", Toast.LENGTH_SHORT).show();
                }
            }
        });*/
    }
//
//    public class AttendStatus extends AsyncTask<String,String,String>{
//
//        String z="";
//        boolean isSuccess;
//
//        @Override
//        protected String doInBackground(String... strings) {
//
//            try{
//                Connection con=connectionClass.CONN();
//                if(con==null)
//                {
//                    z="Error in Connection";
//                }
//                else
//                {
//                    String query = "select * from student ";
//                    Statement stmt = con.createStatement();
//                    ResultSet rs = stmt.executeQuery(query);
//
//                    if(rs != null){
//
//                        while(rs.next()){
//
//                            try {
//
//                            }
//                            catch (Exception e){
//                                e.printStackTrace();
//                            }
//                        }
//
//                        z="Found";
//                        isSuccess = true;
//                    }
//                    else{
//                        z = "No data Found";
//                        isSuccess = false;
//                    }
//                }
//            }
//            catch (Exception e){
//
//                e.printStackTrace();
//                Writer writer = new StringWriter();
//                e.printStackTrace(new PrintWriter(writer));
//                z = writer.toString();
//                isSuccess = false;
//            }
//            return null;
//        }
//    }

}
