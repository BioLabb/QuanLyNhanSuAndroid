package com.example.quanlynhansu;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.quanlynhansu.Adapter.LeaveAdapter;
import com.example.quanlynhansu.object.Leave;
import com.example.quanlynhansu.sqlitehelper.LeaveHelper;

import java.util.ArrayList;
import java.util.List;

public class LeaveStatusOffActivity extends AppCompatActivity {
    ListView lvLeaveStatusOff;

    List<Leave> arrLeaveStatusOff;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_status_off);

        Intent intent = getIntent();
        String status = intent.getStringExtra("status");

        lvLeaveStatusOff = (ListView) findViewById(R.id.lvLeaveStatusOff);
        LeaveHelper leaveHelper = new LeaveHelper(LeaveStatusOffActivity.this);
        arrLeaveStatusOff = (ArrayList<Leave>) leaveHelper.getAllLeaveByStatus(status);

        LeaveAdapter adapter = new LeaveAdapter(
                LeaveStatusOffActivity.this,
                R.layout.activity_leave,
                arrLeaveStatusOff
        );

        lvLeaveStatusOff.setAdapter(adapter);

        //duyet nghi phep bang cach nhan giu
        lvLeaveStatusOff.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if(!arrLeaveStatusOff.get(position).getStatus().equals("da duyet")){
                    new AlertDialog.Builder(LeaveStatusOffActivity.this)
                            .setTitle("Xét nghỉ phép ! user : " + arrLeaveStatusOff.get(position).getAccountId())
                            .setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //set lai trang thai user
                                    arrLeaveStatusOff.get(position).setStatus("da duyet");
                                    //cap nhat lai phia database
                                    leaveHelper.updateLeave(arrLeaveStatusOff.get(position));
                                    //lang nghe su thay doi cua data
                                    adapter.notifyDataSetChanged();

                                }
                            }).setNegativeButton("Từ chối", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).create().show();
                }
                return false;
            }
        });
    }
}