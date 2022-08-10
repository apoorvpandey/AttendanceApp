package com.example.attendanceapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.attendanceapp.Common.Common;
import com.example.attendanceapp.Models.EmployeeModel;
import com.example.attendanceapp.R;
import com.example.attendanceapp.ViewEmployeeDetails;

import java.util.List;

public class ListOfEmployeesAdapter extends RecyclerView.Adapter<ListOfEmployeesAdapter.ListOfEmployeesViewHolder> {
    List<EmployeeModel> listOfEmployeeModels;
    Context context;

    public ListOfEmployeesAdapter(List<EmployeeModel> listOfEmployeeModels, Context context) {
        this.listOfEmployeeModels = listOfEmployeeModels;
        this.context = context;
    }

    @NonNull
    @Override
    public ListOfEmployeesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.employee_item, parent, false);
        return new ListOfEmployeesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListOfEmployeesViewHolder holder, int position) {
        holder.employeeName.setText(listOfEmployeeModels.get(position).getName());
        holder.employeeContact.setText(listOfEmployeeModels.get(position).getMobileNumber());
        Glide.with(context).load(listOfEmployeeModels.get(position).getPhotoURL()).into(holder.photo);
        holder.itemView.setOnClickListener(view -> {
            Common.currentEmployee = listOfEmployeeModels.get(position);
            context.startActivity(new Intent(context, ViewEmployeeDetails.class));
        });
    }


    @Override
    public int getItemCount() {
        return listOfEmployeeModels.size();
    }

    public static class ListOfEmployeesViewHolder extends RecyclerView.ViewHolder {
        TextView employeeName, employeeContact;
        ImageView photo;

        public ListOfEmployeesViewHolder(@NonNull View itemView) {
            super(itemView);
            employeeName = itemView.findViewById(R.id.employeeName);
            employeeContact = itemView.findViewById(R.id.employeeContact);
            photo = itemView.findViewById(R.id.photo);
        }
    }
}
