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
import com.example.quanlynhansu.sqlitehelper.LeaveHelper;


import java.util.List;

public class LeaveStatisticAdapter extends ArrayAdapter<Leave> {

    private List<Leave> leaveList;

    public LeaveStatisticAdapter(@NonNull Context context, int activity_leave_statistic, List<Leave> leaveList) {
        super(context, 0, leaveList);
        this.leaveList = leaveList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.leave_statistic_view, parent, false);
        }

        Leave leave = leaveList.get(position);
        LeaveHelper leaveHelper = new LeaveHelper(this.getContext());

        TextView tvIdUser = convertView.findViewById(R.id.tvIdUser);
        TextView tvTotalLeave = convertView.findViewById(R.id.tvTotalLeave);
        TextView tvStatus = convertView.findViewById(R.id.tvStatus);

        tvIdUser.setText(String.valueOf(leave.getAccountId()));
        tvTotalLeave.setText(String.valueOf((int) leaveHelper.totalAllLeaveDate(leave.getAccountId())));
        tvStatus.setText(String.valueOf((int) leaveHelper.totalUnapprovedDay(leave.getAccountId())));

        return convertView;
    }
}

