package com.attendance.satvik.digtalattendance;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ReportActivity extends AppCompatActivity {
    //List View
    ListView repolist;

    //Connection Class-
    ConnectionClass connectionClass;

    //ArrayList Of Student
    public ArrayList<ReportListModel> itemArrayReportList;

    //Adapter
    private MyRepoAdapter myRepoAdapter;

    //TextView
    TextView StudRollRepo,StudNameRepo,StudBlacklistRepo;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        connectionClass = new ConnectionClass();
        repolist=findViewById(R.id.report_list_view);
        itemArrayReportList= new ArrayList<ReportListModel>();
        FillRepoList fillRepoList = new FillRepoList();
        fillRepoList.execute("");

        StudRollRepo = findViewById(R.id.Repo_Rnum);
        StudNameRepo = findViewById(R.id.Repo_studname);
        StudBlacklistRepo = findViewById(R.id.Repo_blacklist);
    }

    private class FillRepoList extends AsyncTask<String,String,String> {

        String z = "";
        Boolean isSuccess = false;
        ProgressDialog RepoProgressDialog;

        @Override
        protected void onPreExecute() {

            RepoProgressDialog = ProgressDialog.show(ReportActivity.this,"Synchronising","Loading BlckList! Please Wait...",true);
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
                    String query = "select student.Roll_no, student.Stud_name, Attendanc.Attendance_Status from student INNER JOIN Attendanc ON student.Roll_No=Attendanc.Roll_No";
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);

                    if(rs != null){

                        while(rs.next()){

                            try {
                                itemArrayReportList.add(new ReportListModel(rs.getString("Roll_no"),rs.getString("Stud_name"),rs.getString("Attendance_Status")));
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
            RepoProgressDialog.dismiss();
            Toast.makeText(ReportActivity.this,z+"",Toast.LENGTH_LONG).show();
            if(isSuccess==false){

            }else {
                try{
                    myRepoAdapter = new MyRepoAdapter(itemArrayReportList, ReportActivity.this);
                    repolist.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                    repolist.setAdapter(myRepoAdapter);
                }catch (Exception e){

                }

            }
        }
    }

    public class MyRepoAdapter extends BaseAdapter
    {

        @Override
        public int getCount() {
            return studarrayrepolist.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View rowView = convertView;
            ReportViewHolder reportViewHolder = null;
            if(rowView==null){
                LayoutInflater inflater = getLayoutInflater();
                rowView = inflater.inflate(R.layout.student_displaylayout,parent,false);
                reportViewHolder = new ReportViewHolder();
                reportViewHolder.tvStudentRepoRoll = (TextView) rowView.findViewById(R.id.Repo_Rnum);
                reportViewHolder.tvStudentRepoName = (TextView) rowView.findViewById(R.id.Repo_studname);
                reportViewHolder.tvStudentRepoBlacklist = (TextView) rowView.findViewById(R.id.Repo_blacklist);
                rowView.setTag(reportViewHolder);
            }
            else{
                reportViewHolder = (ReportViewHolder) convertView.getTag();
            }

            //Here setting up Names and Roll Numbers

            reportViewHolder.tvStudentRepoRoll.setText(studarrayrepolist.get(position).getStudRNumRepo()+"");
            reportViewHolder.tvStudentRepoName.setText(studarrayrepolist.get(position).getStudNameRepo()+"");
            reportViewHolder.tvStudentRepoBlacklist.setText(studarrayrepolist.get(position).getStudBlacklist()+"");

            return rowView;
        }

        public class ReportViewHolder{
            TextView tvStudentRepoRoll,tvStudentRepoName,tvStudentRepoBlacklist;
        }

        public List<ReportListModel> studarrayrepolist;

        public Context context;

        ArrayList<ReportListModel> studarrepolist;

        private MyRepoAdapter(List<ReportListModel> apps,Context context)
        {
            this.studarrayrepolist = apps;
            this.context = context;
            studarrepolist = new ArrayList<ReportListModel>();
            studarrepolist.addAll(studarrayrepolist);

        }
    }
}
