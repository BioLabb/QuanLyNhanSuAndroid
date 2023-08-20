package com.example.quanlynhansu;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

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
    List<Account> arrAccount;
    ArrayList<GetSalary> arrGetSalary;
    TextView tvTotalGetSalary;
    double sumSalary;
    int idAccount;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_salary);

        lvGetSalary = (ListView) findViewById(R.id.lvGetSalary);
        tvTotalGetSalary = (TextView) findViewById(R.id.tvTotalGetSalary);
        GetSalaryHelper getSalaryHelper = new GetSalaryHelper(GetSalaryActivity.this);
        arrGetSalary = (ArrayList<GetSalary>) getSalaryHelper.getSalaryList();
        //set luong cho tung nhan vien
        AccountHelper accountHelper = new AccountHelper(this);
        arrAccount =  accountHelper.getAllAccounts();

        GetSalaryAdapter adapter = new GetSalaryAdapter(
                GetSalaryActivity.this,
                R.layout.activity_get_salary,
                arrGetSalary
        );
        lvGetSalary.setAdapter(adapter);

        GetSalary getSalary;
        for (int i = 0; i < arrAccount.size() ; i++){
            idAccount = arrAccount.get(i).getAccountID();
            getSalary = getSalaryHelper.getSalaryByIdUser(idAccount);

            //cap nhat vao database
            sumSalary = getSalaryHelper.sumGetSalaryById(idAccount,this);
            getSalaryHelper.updateSum(getSalary,sumSalary);
        }
        String sumGetSalary = String.valueOf(getSalaryHelper.totalGetSalary(this));
        tvTotalGetSalary.setText(sumGetSalary);

        //ham xoa bang luong
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