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
import java.util.Locale;

public class AttendanceAdapter extends ArrayAdapter<Attendance> {
    private List<Attendance> attendanceList;
    private ArrayList<Attendance> arrayList;

    public AttendanceAdapter(@NonNull Context context, int attendance_view, List<Attendance> attendanceList) {
        super(context, 0, attendanceList);
        this.attendanceList = attendanceList;
        this.arrayList = new ArrayList<Attendance>();
        this.arrayList.addAll(attendanceList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.attendance_view, parent, false);
        }

        Attendance attendance = attendanceList.get(position);
        TextView tvNameUser = convertView.findViewById(R.id.tvNameUser);
        TextView tvTotalDate = convertView.findViewById(R.id.tvTotalDate);
        TextView tvIdUserAtten = convertView.findViewById(R.id.tvIdUserAtten);

        AttendanceHelper attendanceHelper = new AttendanceHelper(this.getContext());
        AccountHelper accountHelper = new AccountHelper(this.getContext());

        tvNameUser.setText(String.valueOf(accountHelper.getAccount(attendance.getAccountId()).getFullName()));
        tvIdUserAtten.setText(String.valueOf(attendance.getAccountId()));
        tvTotalDate.setText(String.valueOf(attendanceHelper.totalAllAttendance(attendance.getAccountId())));

        return convertView;
    }

    //filter theo id user chuc nang tim kiem
    public void filter(String charText){
        AccountHelper accountHelper = new AccountHelper(getContext());
        charText = charText.toLowerCase(Locale.getDefault());
        attendanceList.clear();
        if (charText.length() == 0){
            attendanceList.addAll(arrayList);
        }else {
            for (Attendance attendance : arrayList){
                if (String.valueOf(accountHelper.getAccount(attendance.getAccountId()).getFullName())
                        .toLowerCase(Locale.getDefault())
                        .contains(charText)){
                    attendanceList.add(attendance);
                }
            }
        }
        notifyDataSetChanged();
    }
}
