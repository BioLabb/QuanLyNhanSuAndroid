package com.example.quanlynhansu;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.quanlynhansu.Adapter.GetSalaryAdapter;
import com.example.quanlynhansu.object.Account;
import com.example.quanlynhansu.object.GetSalary;
import com.example.quanlynhansu.sqlitehelper.AccountHelper;
import com.example.quanlynhansu.sqlitehelper.GetSalaryHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class GetSalaryActivity extends AppCompatActivity {

    ListView lvGetSalary;
    ArrayList<GetSalary> arrGetSalary;
    ArrayList<Account> arrAccount;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_salary);

        lvGetSalary = (ListView) findViewById(R.id.lvGetSalary);
        GetSalaryHelper getSalaryHelper = new GetSalaryHelper(GetSalaryActivity.this);
        arrGetSalary = (ArrayList<GetSalary>) getSalaryHelper.getSalaryList();

//        //test data
//        getSalaryHelper.insert(new GetSalary("1/1/1970", 5000, 100,0,1 ));
//        getSalaryHelper.insert(new GetSalary("1/1/1970", 1000, 700,0,2 ));
//        getSalaryHelper.insert(new GetSalary("1/1/1970", 4000, 200,0,3 ));
//        getSalaryHelper.insert(new GetSalary("1/2/1970", 2000, 300,0,1 ));
//        getSalaryHelper.insert(new GetSalary("1/2/1970", 7000, 500,0,2 ));
//        getSalaryHelper.insert(new GetSalary("1/2/1970", 500, 900,0,3));
//        getSalaryHelper.insert(new GetSalary("1/3/1970", 4000, 600,0,1 ));
//        getSalaryHelper.insert(new GetSalary("1/3/1970", 3000, 400,0,2 ));
//        getSalaryHelper.insert(new GetSalary("1/3/1970", 2500, 1000,0,3 ));

        AccountHelper accountHelper = new AccountHelper(this);
        arrAccount = (ArrayList<Account>) accountHelper.getAllAccounts();

        //duyet qua danh sach acccount de lay ra id user so sanh
//        for (int i = 0 ; i < 5 || i < arrAccount.size() ; i++){
//            int j = i + 1;
//            try {
//                if(getSalaryHelper.getGetSalaryByIdUser(j) != null) {
//                    arrGetSalary.add(getSalaryHelper.getGetSalaryByIdUser(j));
//                }
//            } catch (ParseException e) {
//                throw new RuntimeException(e);
//            }
//        }

        GetSalaryAdapter adapter = new GetSalaryAdapter(
                GetSalaryActivity.this,
                R.layout.activity_get_salary,
                arrGetSalary
        );
        lvGetSalary.setAdapter(adapter);

        lvGetSalary.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                new AlertDialog.Builder(GetSalaryActivity.this)
                        .setTitle("Bạn muốn xóa " + arrGetSalary.get(position))
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //lay id room tai vi tri dang click
                                int posIdAttendance = arrGetSalary.get(position).getId();
                                //xoa room trong database
                                //arrAttendance.remove(posIdAttendance);
                                getSalaryHelper.remove(posIdAttendance);
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
    }
}