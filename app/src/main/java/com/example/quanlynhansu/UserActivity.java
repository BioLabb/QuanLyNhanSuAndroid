package com.example.quanlynhansu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.quanlynhansu.object.Account;
import com.example.quanlynhansu.object.Room;
import com.example.quanlynhansu.sqlitehelper.RoomHelper;
import com.example.quanlynhansu.store.AccountStore;

public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        Account userName = AccountStore.getUser();
        Toast toast = Toast.makeText(this,"Chao"+ userName.getUserName(),Toast.LENGTH_SHORT);
        toast.show();


    }
}