package com.example.quanlynhansu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.quanlynhansu.object.Room;
import com.example.quanlynhansu.sqlitehelper.RoomHelper;

public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);


        Room room = new Room(7,"test",1234);
        RoomHelper roomHelper = new RoomHelper(this);

        roomHelper.insertRoom(room);

        Room r1 = roomHelper.getRoom(10);

        System.out.println("id: "+ r1.getRoomID() + " , name: " + r1.getName());

    }
}