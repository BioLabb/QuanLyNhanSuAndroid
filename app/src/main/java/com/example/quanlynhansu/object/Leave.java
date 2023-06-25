package com.example.quanlynhansu.object;
public class Leave {
    private int leaveId;
    private String startDate;
    private String endDate;
    private String reason;
    private String status;
    private int accountId;

    public Leave(int leaveId, String startDate, String endDate, String reason, String status, int accountId) {
        this.leaveId = leaveId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reason = reason;
        this.status = status;
        this.accountId = accountId;
    }
    public Leave( String startDate, String endDate, String reason, String status, int accountId) {

        this.startDate = startDate;
        this.endDate = endDate;
        this.reason = reason;
        this.status = status;
        this.accountId = accountId;
    }
    public int getLeaveId() {
        return leaveId;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getReason() {
        return reason;
    }

    public String getStatus() {
        return status;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setLeaveId(int leaveId) {
        this.leaveId = leaveId;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }
}
