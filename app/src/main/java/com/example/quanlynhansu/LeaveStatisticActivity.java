package com.example.quanlynhansu;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.quanlynhansu.Adapter.LeaveStatisticAdapter;
import com.example.quanlynhansu.object.Account;
import com.example.quanlynhansu.object.Leave;
import com.example.quanlynhansu.sqlitehelper.AccountHelper;
import com.example.quanlynhansu.sqlitehelper.LeaveHelper;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.ListIterator;

public class LeaveStatisticActivity extends AppCompatActivity {

    ArrayList<Leave> arrLeaveStatistic;
    ArrayList<Account> arrAccount;
    ListView lvStatisticLeave;
    Button btnLeaveStatus;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_statistic);

        btnLeaveStatus = (Button) findViewById(R.id.btnLeaveStatus);
        lvStatisticLeave = (ListView) findViewById(R.id.lvStatisticLeave);
        LeaveHelper leaveHelper = new LeaveHelper(LeaveStatisticActivity.this);
        arrLeaveStatistic = (ArrayList<Leave>) leaveHelper.getAllLeaves();
        arrLeaveStatistic.clear();

        AccountHelper accountHelper = new AccountHelper(LeaveStatisticActivity.this);
        arrAccount = (ArrayList<Account>) accountHelper.getAllAccounts();

        //duyet qua danh sach acccount de lay ra id user so sanh
        for (int i = 0 ; i < 5 || i < arrAccount.size() ; i++){
            int j = i + 1;
            //arrAttendance.add(attendanceHelper.getAttendanceByIdUser(arrAccount.get(i).getAccountID()));
            if(leaveHelper.getLeaveByIdUser(j) != null) {
                arrLeaveStatistic.add(leaveHelper.getLeaveByIdUser(j));
            }
        }

        LeaveStatisticAdapter adapter = new LeaveStatisticAdapter(
                LeaveStatisticActivity.this,
                R.layout.activity_leave_statistic,
                arrLeaveStatistic
        );

        lvStatisticLeave.setAdapter(adapter);

        //xoa ngay nghi
        lvStatisticLeave.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                new AlertDialog.Builder(LeaveStatisticActivity.this)
                        .setTitle("Bạn muốn xóa " + arrLeaveStatistic.get(position))
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //lay id room tai vi tri dang click
                                int posIdAttendance = arrLeaveStatistic.get(position).getLeaveId();
                                //xoa room trong database
                                //arrAttendance.remove(posIdAttendance);
                                leaveHelper.deleteLeave(posIdAttendance);
                                //lang nghe su thay doi cua data
                                adapter.notifyDataSetChanged();

                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create().show();
                return false;
            }
        });

        lvStatisticLeave.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(LeaveStatisticActivity.this,LeaveDetailsActivity.class);
                intent.putExtra("idUser", adapter.getItem(position).getAccountId());
                startActivity(intent);
            }
        });

        btnLeaveStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LeaveStatisticActivity.this,LeaveStatusOffActivity.class);
                intent.putExtra("status","chua duyet");
                startActivity(intent);
            }
        });
    }
}