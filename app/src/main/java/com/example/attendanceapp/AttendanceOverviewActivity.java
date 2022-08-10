package com.example.attendanceapp;

import static com.example.attendanceapp.Common.Common.attendanceNode;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendanceapp.Adapters.AttendanceOverviewAdapter;
import com.example.attendanceapp.Common.Common;
import com.example.attendanceapp.Models.AttendanceModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AttendanceOverviewActivity extends AppCompatActivity {
    RecyclerView attendanceOverviewRecyclerView;
    ProgressBar progressBar;
    List<AttendanceModel> listOfAttendance;
    AttendanceOverviewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_overview);
        attendanceOverviewRecyclerView = findViewById(R.id.attendanceOverviewRecyclerView);
        progressBar = findViewById(R.id.progressBar);
        listOfAttendance = new ArrayList<>();
        attendanceOverviewRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        getEmployeeAttendance();
    }

    private void getEmployeeAttendance() {
        progressBar.setVisibility(View.VISIBLE);
        attendanceOverviewRecyclerView.setVisibility(View.GONE);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(attendanceNode);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot caseSnapshot : dataSnapshot.getChildren()) {

                    for (DataSnapshot sp : caseSnapshot.getChildren()) {

                        AttendanceModel attendance = sp.getValue(AttendanceModel.class);
                        assert attendance != null;
                        if (sp.exists()) {
                            progressBar.setVisibility(View.GONE);
                            attendanceOverviewRecyclerView.setVisibility(View.VISIBLE);
                            if (Common.currentEmployee.getId().equals(attendance.getEmployeeId())) {
                                listOfAttendance.add(attendance);
                                adapter = new AttendanceOverviewAdapter(listOfAttendance);
                                attendanceOverviewRecyclerView.setAdapter(adapter);
                            }

                        } else {
                            Toast.makeText(AttendanceOverviewActivity.this, "Nothing to show", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.GONE);
                attendanceOverviewRecyclerView.setVisibility(View.VISIBLE);
                Toast.makeText(AttendanceOverviewActivity.this, "Some error occurred!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}