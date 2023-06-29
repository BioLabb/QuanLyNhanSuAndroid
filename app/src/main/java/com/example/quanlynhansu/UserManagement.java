package com.example.quanlynhansu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.quanlynhansu.Adapter.AccountAdapter;
import com.example.quanlynhansu.object.Account;
import com.example.quanlynhansu.sqlitehelper.AccountHelper;

import java.util.List;

public class UserManagement extends AppCompatActivity {
    ListView listUser ;
    private AccountAdapter adapter;
    AccountHelper accountHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_management);
        accountHelper = new AccountHelper(UserManagement.this);
        listUser = findViewById(R.id.listUser);
       List<Account> accountList = accountHelper.getAllAccountsChien();
        //khoi tao vaf gan adapter
        adapter = new AccountAdapter(this,accountList );
        listUser.setAdapter(adapter);
    }
}