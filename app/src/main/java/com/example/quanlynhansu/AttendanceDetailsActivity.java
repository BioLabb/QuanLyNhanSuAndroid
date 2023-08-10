package com.example.quanlynhansu;

import static com.example.quanlynhansu.R.id.lvAttendanceDetails;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.example.quanlynhansu.Adapter.AttendanceDetailsAdapter;
import com.example.quanlynhansu.Adapter.LeaveAdapter;
import com.example.quanlynhansu.object.Attendance;
import com.example.quanlynhansu.object.Leave;
import com.example.quanlynhansu.sqlitehelper.AttendanceHelper;
import com.example.quanlynhansu.sqlitehelper.LeaveHelper;

import java.util.ArrayList;

public class AttendanceDetailsActivity extends AppCompatActivity {

    ArrayList<Attendance> arrAttendanceDetails;
    ListView lvAttendanceDetails;
    TextView tvTotalAttendanceDetais;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_details);

        Intent intent = getIntent();
        int idUserAttendance = intent.getIntExtra("idUserAttendance",0);

        lvAttendanceDetails = (ListView) findViewById(R.id.lvAttendanceDetails);
        tvTotalAttendanceDetais = (TextView) findViewById(R.id.tvTotalAttendanceDetail);
        AttendanceHelper attendanceHelper = new AttendanceHelper(AttendanceDetailsActivity.this);
        arrAttendanceDetails = (ArrayList<Attendance>) attendanceHelper.getAllAttendanceByIdUser(idUserAttendance);

        AttendanceDetailsAdapter adapter = new AttendanceDetailsAdapter(
                AttendanceDetailsActivity.this,
                R.layout.activity_attendance_details,
                arrAttendanceDetails
        );

        lvAttendanceDetails.setAdapter(adapter);
        //tinh tong cham cong cua tung nguoi
        int sum = attendanceHelper.totalAllAttendance(idUserAttendance);
        tvTotalAttendanceDetais.setText(String.valueOf(sum));
    }
}