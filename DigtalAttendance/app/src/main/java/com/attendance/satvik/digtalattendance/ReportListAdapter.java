package com.attendance.satvik.digtalattendance;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.app.Activity;
import android.widget.TextView;

public class ReportListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] Repo_Rollnum;
    private final String[] Repo_Sname;
    private final String[] Repo_blacklist;

    public ReportListAdapter(Activity context, String[] Repo_Rollnum, String[] Repo_Sname, String[] Repo_blacklist) {
        super(context,R.layout.student_displaylayout,Repo_Rollnum);
        this.context = context;
        this.Repo_Rollnum = Repo_Rollnum;
        this.Repo_Sname = Repo_Sname;
        this.Repo_blacklist = Repo_blacklist;
    }

    public View getView(int position, View view, ViewGroup viewGroup){

        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.student_displaylayout,null,true);

        TextView SRrollnum = (TextView)rowView.findViewById((R.id.Repo_Rnum));
        TextView SRsname = (TextView)rowView.findViewById((R.id.Repo_studname));
        TextView SRblacklist = (TextView)rowView.findViewById((R.id.Repo_blacklist));

        SRrollnum.setText(Repo_Rollnum[position]);
        SRsname.setText(Repo_Sname[position]);
        SRblacklist.setText(Repo_blacklist[position]);

        return rowView;
    };
}
