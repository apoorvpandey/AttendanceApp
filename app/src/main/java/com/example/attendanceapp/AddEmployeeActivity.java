package com.example.attendanceapp;

import static com.example.attendanceapp.Common.Common.employeesNode;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.attendanceapp.Common.Common;
import com.example.attendanceapp.Helpers.Helpers;
import com.example.attendanceapp.Models.EmployeeModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;
import java.util.UUID;

public class AddEmployeeActivity extends AppCompatActivity {
    EditText name, address, mobile, designation, salaryPerMonth,
            currency, password;
    Button addEmployee;

    ImageView face;

    String candidateBase64Photo = "", candidateFaceArray = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);
        name = findViewById(R.id.name);
        address = findViewById(R.id.address);
        mobile = findViewById(R.id.mobile);
        designation = findViewById(R.id.designation);
        salaryPerMonth = findViewById(R.id.salaryPerMonth);
        currency = findViewById(R.id.currency);
        password = findViewById(R.id.password);
        addEmployee = findViewById(R.id.addEmployee);
        face = findViewById(R.id.face);

        addEmployee.setOnClickListener(view -> validate());


        face.setOnClickListener(view -> startActivityForResult(new Intent(AddEmployeeActivity.this, CaptureFace.class).putExtra("captureFace", true), 201));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 201 && resultCode == RESULT_OK && data != null) {
            candidateFaceArray = data.getStringExtra("candidateFaceArray");
            candidateBase64Photo = "data:image/jpeg;base64," + Common.imageBase64;

            byte[] decodedString = Base64.decode(Common.imageBase64, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            face.setImageBitmap(decodedByte);

        }
    }

    private void validate() {
        if (name.getText().toString().trim().isEmpty()) {
            name.setError("Required!");
            name.requestFocus();
        } else if (address.getText().toString().trim().isEmpty()) {
            address.setError("Required!");
            address.requestFocus();
        } else if (mobile.getText().toString().trim().isEmpty()) {
            mobile.setError("Required!");
            mobile.requestFocus();
        } else if (address.getText().toString().trim().isEmpty()) {
            address.setError("Required!");
            address.requestFocus();
        } else if (designation.getText().toString().trim().isEmpty()) {
            designation.setError("Required!");
            designation.requestFocus();
        } else if (salaryPerMonth.getText().toString().trim().isEmpty()) {
            salaryPerMonth.setError("Required!");
            salaryPerMonth.requestFocus();
        } else if (currency.getText().toString().trim().isEmpty()) {
            currency.setError("Required!");
            currency.requestFocus();
        } else if (password.getText().toString().trim().isEmpty()) {
            password.setError("Required");
            password.requestFocus();
        } else if (Objects.equals(candidateFaceArray, "")) {
            Toast.makeText(this, "Please select your photo", Toast.LENGTH_SHORT).show();

        } else {
            addEmployee();
        }
    }

    private void addEmployee() {
        ProgressDialog dialog = Helpers.showProgressDialog(this, "Loading...", "Please wait");
        dialog.show();
        String id = UUID.randomUUID().toString();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(employeesNode);


        StorageReference storageRef = FirebaseStorage.getInstance().getReference();


        storageRef.child("image/").child(id).putBytes(Base64.decode(Common.imageBase64, Base64.DEFAULT)).addOnSuccessListener(taskSnapshot -> taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(uri -> {

            EmployeeModel employeeModel = new EmployeeModel(
                    name.getText().toString().trim(), address.getText().toString().trim(), mobile.getText().toString().trim(),
                    designation.getText().toString().trim(), currency.getText().toString().trim(), uri.toString(), candidateFaceArray,
                    id, password.getText().toString().trim(), Double.parseDouble(salaryPerMonth.getText().toString().trim())
            );

            myRef.child(id).
                    setValue(employeeModel).addOnSuccessListener(unused -> {
                        dialog.dismiss();
                        Toast.makeText(AddEmployeeActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                        finish();
                    }).addOnFailureListener(e -> {
                        dialog.dismiss();
                        Toast.makeText(AddEmployeeActivity.this, "Some error occurred: " + e, Toast.LENGTH_SHORT).show();
                    });


        }).addOnFailureListener(e -> {
            dialog.dismiss();
            Toast.makeText(AddEmployeeActivity.this, "Some error occurred: " + e, Toast.LENGTH_SHORT).show();
        }));


    }
}