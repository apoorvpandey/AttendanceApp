package com.example.attendanceapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button loginAsEmployeeButton = findViewById(R.id.loginAsEmployeeButton);
        Button loginAsAdminButton = findViewById(R.id.loginAsAdminButton);

        loginAsAdminButton.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, LoginAsAdminActivity.class)));
        loginAsEmployeeButton.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, LoginAsEmployeeActivity.class)));
    }
}