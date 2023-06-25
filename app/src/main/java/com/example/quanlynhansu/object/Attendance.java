package com.example.quanlynhansu.object;
public class Attendance {
    private int attendanceId;
    private String date;
    private int accountId;

    public Attendance(int attendanceId, String date, int accountId) {
        this.attendanceId = attendanceId;
        this.date = date;
        this.accountId = accountId;
    }
    public Attendance( String date, int accountId) {

        this.date = date;
        this.accountId = accountId;
    }
    public int getAttendanceId() {
        return attendanceId;
    }

    public String getDate() {
        return date;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAttendanceId(int attendanceId) {
        this.attendanceId = attendanceId;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }
}
