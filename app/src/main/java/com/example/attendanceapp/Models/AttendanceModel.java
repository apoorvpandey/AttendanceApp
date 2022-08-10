package com.example.attendanceapp.Models;

public class AttendanceModel {
    String date, inTime, outTime, employeeId;

    public AttendanceModel() {
    }

    public AttendanceModel(String date, String inTime, String outTime, String employeeId) {
        this.date = date;
        this.inTime = inTime;
        this.outTime = outTime;
        this.employeeId = employeeId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getInTime() {
        return inTime;
    }

    public void setInTime(String inTime) {
        this.inTime = inTime;
    }

    public String getOutTime() {
        return outTime;
    }

    public void setOutTime(String outTime) {
        this.outTime = outTime;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }
}
