package com.attendance.satvik.digtalattendance;

import android.widget.Switch;

class ListModel {
    private String StudentRNum;
    private String StudentName;
//    private Switch btnswitch;

    public ListModel(String studentRNum, String studentName) {
        this.StudentRNum = studentRNum;
        this.StudentName = studentName;
//        this.btnswitch = btnswitch;
    }

    public String getStudentRNum() { return StudentRNum;
    }

    public String getStudentName() { return StudentName;
    }

}
