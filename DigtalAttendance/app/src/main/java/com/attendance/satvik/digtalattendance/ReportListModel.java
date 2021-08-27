package com.attendance.satvik.digtalattendance;

public class ReportListModel {
    private String StudRNumRepo;
    private String StudNameRepo;
    private String StudBlacklist;

    public ReportListModel(String studRNumRepo, String studNameRepo, String studBlacklist) {
        StudRNumRepo = studRNumRepo;
        StudNameRepo = studNameRepo;
        StudBlacklist = studBlacklist;
    }

    public String getStudRNumRepo() {
        return StudRNumRepo;
    }

    public String getStudNameRepo() {
        return StudNameRepo;
    }

    public String getStudBlacklist() {
        return StudBlacklist;
    }
}
