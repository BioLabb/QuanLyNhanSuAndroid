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
import com.example.quanlynhansu.Adapter.LeaveStatisticAdapter;
import com.example.quanlynhansu.object.Account;
import com.example.quanlynhansu.object.Leave;
import com.example.quanlynhansu.sqlitehelper.AccountHelper;
import com.example.quanlynhansu.sqlitehelper.LeaveHelper;

import java.util.ArrayList;

public class LeaveDetailsActivity extends AppCompatActivity {

    ArrayList<Leave> arrLeaveDetails;
    ListView lvLeaveDetails;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_details);
        Intent intent = getIntent();
        int idUser = intent.getIntExtra("idUser",0);

        lvLeaveDetails = (ListView) findViewById(R.id.lvLeaveDetails);
        LeaveHelper leaveHelper = new LeaveHelper(LeaveDetailsActivity.this);
        arrLeaveDetails = (ArrayList<Leave>) leaveHelper.getAllLeaveByIdUser(idUser);

        LeaveAdapter adapter = new LeaveAdapter(
                LeaveDetailsActivity.this,
                R.layout.activity_leave,
                arrLeaveDetails
        );

        lvLeaveDetails.setAdapter(adapter);

        //duyet nghi phep bang cach nhan giu
        lvLeaveDetails.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if(!arrLeaveDetails.get(position).getStatus().equals("da duyet")){
                    new AlertDialog.Builder(LeaveDetailsActivity.this)
                            .setTitle("Xét nghỉ phép ! user : " + arrLeaveDetails.get(position).getAccountId())
                            .setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //set lai trang thai user
                                    arrLeaveDetails.get(position).setStatus("da duyet");
                                    //cap nhat lai phia database
                                    leaveHelper.updateLeave(arrLeaveDetails.get(position));
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