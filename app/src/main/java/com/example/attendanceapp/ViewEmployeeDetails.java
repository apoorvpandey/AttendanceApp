package com.example.attendanceapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.attendanceapp.Common.Common;

public class ViewEmployeeDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_employee_details);

        TextView name, address, mobile, designation, salaryPerMonth, currency,
                password;
        ImageView photo;
        Button attendanceOverview;


        name = findViewById(R.id.name);
        address = findViewById(R.id.address);
        mobile = findViewById(R.id.mobile);
        designation = findViewById(R.id.designation);
        salaryPerMonth = findViewById(R.id.salaryPerMonth);
        currency = findViewById(R.id.currency);
        password = findViewById(R.id.password);
        photo = findViewById(R.id.photo);
        attendanceOverview = findViewById(R.id.attendanceOverview);

        attendanceOverview.setOnClickListener(view -> startActivity(new Intent(this, AttendanceOverviewActivity.class)));

        name.setText(Common.currentEmployee.getName());
        address.setText(Common.currentEmployee.getAddress());
        mobile.setText(Common.currentEmployee.getMobileNumber());
        designation.setText(Common.currentEmployee.getDesignation());
        salaryPerMonth.setText(String.valueOf(Common.currentEmployee.getSalaryPerMonth()));
        currency.setText(Common.currentEmployee.getCurrency());
        password.setText(Common.currentEmployee.getPassword());
        Glide.with(this).load(Common.currentEmployee.getPhotoURL()).into(photo);
    }
}