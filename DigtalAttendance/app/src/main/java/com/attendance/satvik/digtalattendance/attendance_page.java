package com.attendance.satvik.digtalattendance;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import android.widget.Spinner;

public class attendance_page extends AppCompatActivity {

    //Connection Class-
    ConnectionClass connectionClass;

    //ArrayList Of Student
    public ArrayList<ListModel> itemArrayStudList;

    //Adapter
    private MyAppAdapter myAppAdapter;

    //List View
    ListView listView;

    boolean status[]={true,false};

    //Button
    Button Submit;

    //String value of Attendance State
    String attenState;

    TextView date;
    TextView StudentRNum,StudentName;
    String recordclass;
    Spinner sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_page);

        connectionClass = new ConnectionClass();
        listView=findViewById(R.id.attend_list_view);
        itemArrayStudList= new ArrayList<ListModel>();
        FillList fill=new FillList();
        fill.execute("");

        StudentRNum = findViewById(R.id.stud_roll);
        StudentName = findViewById(R.id.stud_names);

        Submit = findViewById(R.id.Attend_Submit);

      /*  listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(attendance_page.this,"c :",Toast.LENGTH_LONG).show();

            }
        });*/
//        Submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DoAttendSubmit doAttendSubmit = new DoAttendSubmit();
//                doAttendSubmit.execute("");
//            }
//        });


//        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                switch (i){
//
//                    case 0:
//                        recordclass = "FYIT";
//                        break;
//
//                    case 1:
//                        recordclass = "SYIT";
//                        break;
//
//                    case 2:
//                        recordclass = "TYIT";
//                        break;
//
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//                recordclass="TYIT";
//
//            }
//        });

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());

//Set Current Date
        date = findViewById(R.id.tvSelectedDate);
        date.setText(currentDate);

    }

    public class FillList extends AsyncTask<String,String,String>{

        String z = "";
        Boolean isSuccess = false;

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {

            progressDialog = ProgressDialog.show(attendance_page.this,"Synchronising","Loading Student List! Please Wait...",true);

        }



        @Override
        protected String doInBackground(String... strings) {
            try{
                Connection con=connectionClass.CONN();
                if(con==null)
                {
                    z="Error in Connection";
                }
                else
                {
                    String query = "select * from student";
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);

                    if(rs != null){

                        while(rs.next()){

                            try {
                                itemArrayStudList.add(new ListModel(rs.getString("Roll_no"),rs.getString("Stud_name")));

                            }
                            catch (Exception e){
                                e.printStackTrace();
                            }
                        }

                        z="Found";
                        isSuccess = true;
                    }
                    else{
                        z = "No data Found";
                        isSuccess = false;
                    }
                }
            }
            catch (Exception e){

                e.printStackTrace();
                Writer writer = new StringWriter();
                e.printStackTrace(new PrintWriter(writer));
                z = writer.toString();
                isSuccess = false;
            }
            return z;
        }

        @Override
        protected void onPostExecute(String s) {
//            Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();

            progressDialog.dismiss();
            Toast.makeText(attendance_page.this,z+"",Toast.LENGTH_LONG).show();
            if(isSuccess==false){
                Toast.makeText(attendance_page.this,"Something went wrong",Toast.LENGTH_LONG).show();

            }else {
                try{
                    myAppAdapter = new MyAppAdapter(itemArrayStudList, attendance_page.this);
                    //Choise mode Multiple
                   listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                    listView.setAdapter(myAppAdapter);
                }catch (Exception e){
                    e.printStackTrace();

                }

            }


        }

    }
    public class MyAppAdapter extends BaseAdapter
    {

        @Override
        public int getCount() {
            return studlist.size();
        }

        @Override
        public Object getItem(int i) {

            return i;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View convertView, ViewGroup parent) {
            View rowView = convertView;
            ViewHolder viewHolder = null;

            if(rowView==null){
                LayoutInflater inflater = getLayoutInflater();
                rowView = inflater.inflate(R.layout.attend_list,parent,false);
                viewHolder = new ViewHolder();
                viewHolder.tvStudentroll = (TextView) rowView.findViewById(R.id.stud_roll);
                viewHolder.tvStudentname = (TextView) rowView.findViewById(R.id.stud_names);
                viewHolder.btnSwitch = (Switch) rowView.findViewById(R.id.stud_attend_switch);



                viewHolder.btnSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                        if (isChecked){
                            Toast.makeText(attendance_page.this,"Present :"+i,Toast.LENGTH_SHORT).show();
                            attenState = "Present";
                        }
                        else{
                            Toast.makeText(attendance_page.this,"Absent :"+i,Toast.LENGTH_SHORT).show();
                            attenState = "Absent";
                        }
                    }
                });

                rowView.setTag(viewHolder);
            }
            else{
                viewHolder= (ViewHolder) convertView.getTag();
            }

          //  if(viewHolder.btnSwitch.isChecked()){             viewHolder.btnSwitch.setChecked(true);}else{viewHolder.btnSwitch.setChecked(false);}

            //Here setting up Names and Roll Numbers

            viewHolder.tvStudentroll.setText(studlist.get(i).getStudentRNum()+"");
            viewHolder.tvStudentname.setText(studlist.get(i).getStudentName()+"");
          // viewHolder.btnSwitch.isSelected();

            return rowView;
        }

        public class ViewHolder{
            TextView tvStudentroll,tvStudentname;
            Switch btnSwitch;
        }

        public List<ListModel> studlist;

        public Context context;

        ArrayList<ListModel> studarraylist;

        private MyAppAdapter(List<ListModel> apps,Context context)
        {
            this.studlist = apps;
            this.context = context;
            studarraylist = new ArrayList<ListModel>();
            studarraylist.addAll(studlist);

        }


    }
//
//    private class DoAttendSubmit extends AsyncTask<String,String,String> {
//        String z = "";
//        Boolean isSuccess = false;
//        int n = 0;
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
//                    while(n<itemArrayStudList.size()){
//
//                        String query = "update attendance set attendance_status='"+attenState+"',Roll_No='"+(n+1)+"',Date='"+date+"'";
//                        Statement stmt = con.createStatement();
//                        stmt.executeQuery(query);
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
//
//            return null;
//        }
//    }
}
