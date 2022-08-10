package com.example.attendanceapp;

import static com.example.attendanceapp.Common.Common.attendanceNode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.attendanceapp.Common.Common;
import com.example.attendanceapp.Models.AttendanceModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class EmployeeDashboardActivity extends AppCompatActivity {

    Button checkIn, checkOut;
    boolean isIn = false;
    AttendanceModel attendanceModel;
    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        if (attendanceModel == null) {
                            attendanceModel = new AttendanceModel(getCurrentDateStamp(), getCurrentTimeStamp(), "", Common.currentEmployee.getId());
                        }
                        markAttendance(isIn, attendanceModel);
                        Toast.makeText(EmployeeDashboardActivity.this, "Okay", Toast.LENGTH_SHORT).show();
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_dashboard);
        checkIn = findViewById(R.id.checkIn);
        checkOut = findViewById(R.id.checkOut);
        Button attendanceOverview = findViewById(R.id.attendanceOverview);
        TextView greeting = findViewById(R.id.greetingText), username = findViewById(R.id.username),
        currentDate = findViewById(R.id.currentDate);
        greeting.setText(greetingText());
        username.setText(Common.currentEmployee.getName());
        currentDate.setText(getCurrentDateStamp());

        attendanceOverview.setOnClickListener(view -> startActivity(new Intent(this, AttendanceOverviewActivity.class)));

        getCurrentEmployeeAttendance();
    }

    private void getCurrentEmployeeAttendance() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(attendanceNode + "/" + getCurrentDateStamp());
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("kkkkkk", "onDataChange: " + Common.currentEmployee.getId());
                if (dataSnapshot.child(Common.currentEmployee.getId()).exists()) {
                    attendanceModel = dataSnapshot.child(Common.currentEmployee.getId()).getValue(AttendanceModel.class);

                    if (attendanceModel.getInTime().isEmpty()) {
                        checkOut.setEnabled(false);
                    } else {
                        checkIn.setEnabled(false);
                        checkIn.setText("Last check in: " + attendanceModel.getInTime());
                    }


                    if (!attendanceModel.getOutTime().isEmpty()) {
                        checkOut.setEnabled(false);
                        checkOut.setText("Last check out: " + attendanceModel.getOutTime());
                    }

                    checkIn.setOnClickListener(view -> {
                        isIn = true;
                        matchFace();
                    });

                    checkOut.setOnClickListener(view -> {
                        isIn = false;
                        matchFace();
                    });

                } else {
                    AttendanceModel attendanceModel = new AttendanceModel(getCurrentDateStamp(), getCurrentTimeStamp(), "", Common.currentEmployee.getId());
                    //checkIn.setOnClickListener(view -> markAttendance(true, attendanceModel));
                    checkIn.setOnClickListener(view -> {
                        isIn = true;
                        matchFace();
                    });
                    checkOut.setEnabled(false);
                }


            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EmployeeDashboardActivity.this, "Some error occurred!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void matchFace() {

        Intent intent = new Intent(this, MatchFace.class);
        someActivityResultLauncher.launch(intent);

    }

    private void markAttendance(boolean isIn, AttendanceModel attendance) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(attendanceNode);

        AttendanceModel attendanceModel = new AttendanceModel(getCurrentDateStamp(), isIn ? getCurrentTimeStamp() : attendance.getInTime(), isIn ? attendance.getOutTime() : getCurrentTimeStamp(),
                Common.currentEmployee.getId()
        );

        myRef.child(Objects.requireNonNull(getCurrentDateStamp())).child(Common.currentEmployee.getId()).
                setValue(attendanceModel).addOnSuccessListener(unused -> {
                    Toast.makeText(EmployeeDashboardActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                }).addOnFailureListener(e -> Toast.makeText(EmployeeDashboardActivity.this, "Failure!", Toast.LENGTH_SHORT).show());
    }

    private String getCurrentTimeStamp() {
        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.UK);

            return dateFormat.format(new Date());
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    private String getCurrentDateStamp() {
        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.UK);

            return dateFormat.format(new Date());
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    private String greetingText() {
        Calendar calendar = Calendar.getInstance();
        int timeOfDay = calendar.get(Calendar.HOUR_OF_DAY);

        if (timeOfDay < 12) {
            return "Good Morning";
        } else if (timeOfDay < 16) {
            return "Good Afternoon";
        } else if (timeOfDay < 21) {
            return "Good Evening";
        } else {
            return "Good Night";
        }
    }
}
