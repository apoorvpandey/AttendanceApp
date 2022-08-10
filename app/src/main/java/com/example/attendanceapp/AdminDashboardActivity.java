package com.example.attendanceapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AdminDashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
        Button addEmployee = findViewById(R.id.addEmployee);
        Button viewEmployees = findViewById(R.id.viewEmployees);
        addEmployee.setOnClickListener(view -> startActivity(new Intent(AdminDashboardActivity.this, AddEmployeeActivity.class)));
        viewEmployees.setOnClickListener(view -> startActivity(new Intent(AdminDashboardActivity.this, ViewEmployeesActivity.class)));
    }
}