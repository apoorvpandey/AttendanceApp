package com.example.attendanceapp.Models;

public class EmployeeModel {
    String name, address, mobileNumber, designation, currency, photoURL, faceData, id, password;
    double salaryPerMonth;

    public EmployeeModel() {
    }

    public EmployeeModel(String name, String address, String mobileNumber, String designation, String currency, String photoURL, String faceData, String id, String password, double salaryPerMonth) {
        this.name = name;
        this.address = address;
        this.mobileNumber = mobileNumber;
        this.designation = designation;
        this.currency = currency;
        this.photoURL = photoURL;
        this.faceData = faceData;
        this.id = id;
        this.password = password;
        this.salaryPerMonth = salaryPerMonth;
    }

    public String getFaceData() {
        return faceData;
    }

    public void setFaceData(String faceData) {
        this.faceData = faceData;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getSalaryPerMonth() {
        return salaryPerMonth;
    }

    public void setSalaryPerMonth(double salaryPerMonth) {
        this.salaryPerMonth = salaryPerMonth;
    }
}
