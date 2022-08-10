package com.example.attendanceapp;

import static com.example.attendanceapp.Common.Common.adminsNode;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.attendanceapp.Helpers.Helpers;
import com.example.attendanceapp.Models.AdminModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

public class LoginAsAdminActivity extends AppCompatActivity {
    EditText email, password;
    Button login;
    String TAG = "kkkkkkkkk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_as_admin);
        login = findViewById(R.id.login);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        login.setOnClickListener(view -> validate());
    }

    private void validate() {
        if (email.getText().toString().trim().isEmpty()) {
            email.setError("Please enter email");
            email.requestFocus();
        } else if (password.getText().toString().trim().isEmpty()) {
            password.setError("Please enter password");
            password.requestFocus();
        } else {
            adminLogin();
        }
    }

    private void adminLogin() {
        ProgressDialog dialog = Helpers.showProgressDialog(this, "Loading...", "Please wait");
        dialog.show();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(adminsNode);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot caseSnapshot : dataSnapshot.getChildren()) {
                    AdminModel admins = caseSnapshot.getValue(AdminModel.class);
                    assert admins != null;
                    if (email.getText().toString().trim().toLowerCase(Locale.ROOT).equals(admins.getEmail())) {
                        dialog.dismiss();
                        if (email.getText().toString().toLowerCase(Locale.ROOT).matches(admins.getEmail()) && password.getText().toString().trim().equals(admins.getPassword())) {
                            Toast.makeText(LoginAsAdminActivity.this, "Login success!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginAsAdminActivity.this, AdminDashboardActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                        } else {
                            dialog.dismiss();
                            Toast.makeText(LoginAsAdminActivity.this, "Invalid credentials!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        dialog.dismiss();
                        Toast.makeText(LoginAsAdminActivity.this, "Invalid credentials!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialog.dismiss();
                Toast.makeText(LoginAsAdminActivity.this, "Some error occurred!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}