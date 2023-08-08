package com.example.quanlynhansu;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlynhansu.Adapter.AccountAdapter;
import com.example.quanlynhansu.Adapter.AttendanceAdapter;
import com.example.quanlynhansu.object.Account;
import com.example.quanlynhansu.object.Attendance;
import com.example.quanlynhansu.sqlitehelper.AccountHelper;
import com.example.quanlynhansu.sqlitehelper.AttendanceHelper;

import java.util.ArrayList;
import java.util.List;

public class AttendanceActivity extends AppCompatActivity {
    ArrayList<Attendance> arrAttendance;
    List<Account> arrAccount;
    ListView lvAttendance;
    TextView tvTotalAttendance;
    SearchView searchView;
    Attendance attendance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        lvAttendance = (ListView) findViewById(R.id.lvAttendace);
        tvTotalAttendance = (TextView) findViewById(R.id.tvTotalAttendance);
        searchView = (SearchView) findViewById(R.id.svSearchNameUser);

        AttendanceHelper attendanceHelper = new AttendanceHelper(AttendanceActivity.this);
        arrAttendance = (ArrayList<Attendance>) attendanceHelper.getAllAttendances();
        arrAttendance.clear();//tra ve giao dien mang rong

        AccountHelper accountHelper = new AccountHelper(AttendanceActivity.this);
        arrAccount =  accountHelper.getAllAccounts();

        //test data
//        accountHelper.insertAccount(new Account("huynh7","12345","vanchien","email11","01424234","adress",1));
//        accountHelper.insertAccount(new Account("huynh5","12345","vanchien","email11","01424234","adress",1));
//        accountHelper.insertAccount(new Account("huynh6","12345","vanchien","email11","01424234","adress",1));

        for (int i = 0 ; i < arrAccount.size() ; i++){
            attendance = attendanceHelper.getAttendanceByIdUser(arrAccount.get(i).getAccountID());
            //duyet xem co tim thay user trong mang cham cong
            if(attendance != null)
                arrAttendance.add(attendance);
            else {
                attendance = new Attendance(null, arrAccount.get(i).getAccountID());
                arrAttendance.add(attendance);
            }
        }

        AttendanceAdapter adapter = new AttendanceAdapter(
                AttendanceActivity.this,
                R.layout.attendance_view,
                arrAttendance
        );
        lvAttendance.setAdapter(adapter);

        lvAttendance.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(AttendanceActivity.this,AttendanceDetailsActivity.class);
                intent.putExtra("idUserAttendance", adapter.getItem(position).getAccountId());
                startActivity(intent);
            }
        });

        //data hien co
//        attendanceHelper.insertAttendance(new Attendance("1/1/2024",4));
//        attendanceHelper.insertAttendance(new Attendance("1/1/2024",5));
//        attendanceHelper.insertAttendance(new Attendance("1/1/2024",6));
//        attendanceHelper.insertAttendance(new Attendance("2/1/2024",7));
//        attendanceHelper.insertAttendance(new Attendance("2/1/2024",8));
//        attendanceHelper.insertAttendance(new Attendance("3/1/2024",9));
//        attendanceHelper.insertAttendance(new Attendance("4/1/2024",4));
//        attendanceHelper.insertAttendance(new Attendance("4/1/2024",5));
//        attendanceHelper.insertAttendance(new Attendance("4/1/2024",6));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
               if(TextUtils.isEmpty(newText)){
                   adapter.filter("");
                   lvAttendance.clearTextFilter();
               }else {
                   adapter.filter(newText);
               }
                return true;
            }
        });

        //function xoa item attendance
        lvAttendance.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                new AlertDialog.Builder(AttendanceActivity.this)
                        .setTitle("Bạn muốn xóa " + arrAttendance.get(position))
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //lay id room tai vi tri dang click
                                int posIdAttendance = arrAttendance.get(position).getAttendanceId();
                                //xoa room trong database
                                //arrAttendance.remove(posIdAttendance);
                                attendanceHelper.removeAttendance(posIdAttendance);
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

        int tmp = 0;
        for (int i = 0; i < arrAttendance.size(); i++){
            tmp += attendanceHelper.totalAllAttendance(arrAttendance.get(i).getAccountId());
        }
        tvTotalAttendance.setText(String.valueOf(tmp));
    }


}