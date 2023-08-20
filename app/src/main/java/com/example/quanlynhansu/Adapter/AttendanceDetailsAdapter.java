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
import com.example.quanlynhansu.object.Attendance;
import com.example.quanlynhansu.sqlitehelper.AccountHelper;
import com.example.quanlynhansu.sqlitehelper.AttendanceHelper;

import java.util.ArrayList;
import java.util.List;

public class AttendanceDetailsAdapter extends ArrayAdapter<Attendance> {
    private List<Attendance> attendanceList;
    public AttendanceDetailsAdapter(@NonNull Context context, int attendance_view, List<Attendance> attendanceList) {
        super(context, 0, attendanceList);
        this.attendanceList = attendanceList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.attendance_details_view, parent, false);
        }

        Attendance attendance = attendanceList.get(position);
        TextView tvIdUserAttendance = convertView.findViewById(R.id.tvIdUserAttendance);
        TextView tvIdAttendance = convertView.findViewById(R.id.tvIdAttendance);
        TextView tvDateAttendance = convertView.findViewById(R.id.tvDateAttendance);

        AccountHelper accountHelper = new AccountHelper(this.getContext());

        tvIdUserAttendance.setText(String.valueOf(accountHelper.getAccount(attendance.getAccountId()).getFullName()));
        tvIdAttendance.setText(String.valueOf(attendance.getAttendanceId()));
        tvDateAttendance.setText(String.valueOf(attendance.getDate()));

        return convertView;
    }
}
