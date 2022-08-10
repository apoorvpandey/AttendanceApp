package com.example.attendanceapp;

import static com.example.attendanceapp.Common.Common.employeesNode;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.attendanceapp.Common.Common;
import com.example.attendanceapp.Helpers.Helpers;
import com.example.attendanceapp.Models.EmployeeModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginAsEmployeeActivity extends AppCompatActivity {
    EditText mobile, password;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_as_employee);
        mobile = findViewById(R.id.mobile);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);

        login.setOnClickListener(view -> validate());
    }

    private void validate() {
        if (mobile.getText().toString().trim().isEmpty()) {
            mobile.setError("Please enter mobile");
            mobile.requestFocus();
        } else if (password.getText().toString().trim().isEmpty()) {
            password.setError("Please enter password");
            password.requestFocus();
        } else {
            employeeLogin();
        }
    }

    private void employeeLogin() {
        ProgressDialog dialog = Helpers.showProgressDialog(this, "Loading...", "Please wait");
        dialog.show();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(employeesNode);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot caseSnapshot : dataSnapshot.getChildren()) {
                    EmployeeModel employees = caseSnapshot.getValue(EmployeeModel.class);
                    assert employees != null;
                    if (mobile.getText().toString().trim().equals(employees.getMobileNumber())) {
                        dialog.dismiss();
                        if (mobile.getText().toString().trim().matches(employees.getMobileNumber()) && password.getText().toString().trim().equals(employees.getPassword())) {
                            Toast.makeText(LoginAsEmployeeActivity.this, "Login success!", Toast.LENGTH_SHORT).show();
                            Common.currentEmployee = employees;

                            SharedPreferences preferences = getSharedPreferences("SharedPreferencesName", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();

                            editor.putString("face", (employees.getFaceData().equals("null") ? "{}" : employees.getFaceData()));

                            editor.putBoolean("IsLogged", true);
                            editor.apply();

                            startActivity(new Intent(LoginAsEmployeeActivity.this, EmployeeDashboardActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                        } else {
                            dialog.dismiss();
                            Toast.makeText(LoginAsEmployeeActivity.this, "Invalid credentials!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.d("kkkkkkkkkkk", "onDataChange: else");
                        dialog.dismiss();
                        Toast.makeText(LoginAsEmployeeActivity.this, "Invalid credentials!", Toast.LENGTH_SHORT).show();
                    }
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialog.dismiss();
                Toast.makeText(LoginAsEmployeeActivity.this, "Some error occurred!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}