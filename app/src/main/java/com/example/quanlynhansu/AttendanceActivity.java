package com.example.quanlynhansu;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

public class AttendanceActivity extends AppCompatActivity {
    ArrayList<Attendance> arrAttendance;
    ArrayList<Account> arrAccount;
    ListView lvAttendance;
    TextView tvTotalAttendance;
    SearchView searchView;

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
        accountHelper.insertAccount(new Account("huynh1","12345","vanchien","email11","01424234","adress",1));
        accountHelper.insertAccount(new Account("huynh2","12345","vanchien","email11","01424234","adress",1));
        accountHelper.insertAccount(new Account("huynh3","12345","vanchien","email11","01424234","adress",1));
        arrAccount = (ArrayList<Account>) accountHelper.getAllAccounts();

        //duyet qua danh sach acccount de lay ra id user so sanh
        for (int i = 0 ; i < arrAccount.size() ; i++){
            int j = i + 1;
            //arrAttendance.add(attendanceHelper.getAttendanceByIdUser(arrAccount.get(i).getAccountID()));
            if(attendanceHelper.getAttendanceByIdUser(j) != null) {
                arrAttendance.add(attendanceHelper.getAttendanceByIdUser(j));
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
                Toast.makeText(AttendanceActivity.this, "your click - userId = "+ adapter.getItem(position).getAccountId(),Toast.LENGTH_SHORT).show();
            }
        });

        //data hien co
//        attendanceHelper.insertAttendance(new Attendance("1/1/2024",1));
//        attendanceHelper.insertAttendance(new Attendance("1/1/2024",2));
//        attendanceHelper.insertAttendance(new Attendance("1/1/2024",3));
//        attendanceHelper.insertAttendance(new Attendance("2/1/2024",2));
//        attendanceHelper.insertAttendance(new Attendance("2/1/2024",3));
//        attendanceHelper.insertAttendance(new Attendance("3/1/2024",3));
//        attendanceHelper.insertAttendance(new Attendance("4/1/2024",1));
//        attendanceHelper.insertAttendance(new Attendance("4/1/2024",2));
//        attendanceHelper.insertAttendance(new Attendance("4/1/2024",3));

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