package com.example.quanlynhansu;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.example.quanlynhansu.Adapter.AccountAdapter;
import com.example.quanlynhansu.Adapter.RoomAdapter;
import com.example.quanlynhansu.Adapter.RoomDetailsAdapter;
import com.example.quanlynhansu.object.Account;
import com.example.quanlynhansu.object.Room;
import com.example.quanlynhansu.sqlitehelper.AccountHelper;
import com.example.quanlynhansu.sqlitehelper.RoomHelper;

import java.util.ArrayList;

public class RoomDetailsActivity extends AppCompatActivity {

    TextView tvNameRoomDetails;
    ListView lvRoomDetails;
    ArrayList<Account> arrAccount;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_details);

        Intent intent = getIntent();
        int idRoom = intent.getIntExtra("idRoom",0);
        tvNameRoomDetails = (TextView) findViewById(R.id.tvNameRoomDetails);
        lvRoomDetails = (ListView) findViewById(R.id.lvRoomDetails);

        AccountHelper accountHelper = new AccountHelper(this);
        //data khởi tạo ban đầu
        arrAccount = (ArrayList<Account>) accountHelper.getAllAccountByIdRoom(idRoom);

        RoomDetailsAdapter adapter = new RoomDetailsAdapter(
                RoomDetailsActivity.this,
                R.layout.account_room_view,
                arrAccount
        );
        lvRoomDetails.setAdapter(adapter);
        RoomHelper roomHelper = new RoomHelper(this);
        tvNameRoomDetails.setText(String.valueOf(roomHelper.getRoom(idRoom).getName()));
    }
}