package com.example.attendanceapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendanceapp.Models.AttendanceModel;
import com.example.attendanceapp.R;

import java.util.List;

public class AttendanceOverviewAdapter extends RecyclerView.Adapter<AttendanceOverviewAdapter.ListOfEmployeesViewHolder> {
    List<AttendanceModel> listOfAttendance;

    public AttendanceOverviewAdapter(List<AttendanceModel> listOfAttendance) {
        this.listOfAttendance = listOfAttendance;
    }

    @NonNull
    @Override
    public ListOfEmployeesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.attendance_item, parent, false);
        return new ListOfEmployeesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListOfEmployeesViewHolder holder, int position) {
        holder.date.setText("Date: " + listOfAttendance.get(position).getDate());
        holder.inTime.setText("In time: " + listOfAttendance.get(position).getInTime());
        holder.outTime.setText("Out time: " + listOfAttendance.get(position).getOutTime());
    }


    @Override
    public int getItemCount() {
        return listOfAttendance.size();
    }

    public static class ListOfEmployeesViewHolder extends RecyclerView.ViewHolder {
        TextView date, inTime, outTime;

        public ListOfEmployeesViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
            inTime = itemView.findViewById(R.id.inTime);
            outTime = itemView.findViewById(R.id.outTime);
        }
    }
}
