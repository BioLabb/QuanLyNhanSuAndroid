package com.example.quanlynhansu.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.quanlynhansu.R;
import com.example.quanlynhansu.object.Leave;


import java.util.List;

public class LeaveAdapter extends ArrayAdapter<Leave> {

    private List<Leave> leaveList;

    public LeaveAdapter(@NonNull Context context, List<Leave> leaveList) {
        super(context, 0, leaveList);
        this.leaveList = leaveList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.leave_view, parent, false);
        }

        Leave leave = leaveList.get(position);
        TextView textViewStartDate = convertView.findViewById(R.id.editLStartDate);
        TextView textViewEndDate = convertView.findViewById(R.id.editLEndDate);
        TextView textViewReason = convertView.findViewById(R.id.editLReason);
        TextView textViewStatus = convertView.findViewById(R.id.editLStatus);

        textViewStartDate.setText(leave.getStartDate());
        textViewEndDate.setText(leave.getEndDate());
        textViewReason.setText(leave.getReason());
        textViewStatus.setText(leave.getStatus());


        return convertView;
    }
}

