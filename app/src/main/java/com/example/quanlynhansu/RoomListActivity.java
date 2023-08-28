package com.example.quanlynhansu;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.quanlynhansu.Adapter.RoomAdapter;
import com.example.quanlynhansu.object.Room;
import com.example.quanlynhansu.sqlitehelper.RoomHelper;

import java.util.ArrayList;

public class RoomListActivity extends AppCompatActivity {

    ListView lvRoomList;
    TextView tvTotalRoom;
    Button btnSetting;
    ArrayList<Room> arrRoom;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_list);

        lvRoomList = (ListView) findViewById(R.id.lvRoomList);
        tvTotalRoom = (TextView) findViewById(R.id.tvTotalRoom);
        btnSetting = (Button) findViewById(R.id.btnSetting);

        RoomHelper roomHelper = new RoomHelper(RoomListActivity.this);
        //data khởi tạo ban đầu
        arrRoom = (ArrayList<Room>) roomHelper.getAllRooms();

        RoomAdapter adapter = new RoomAdapter(
                RoomListActivity.this,
                R.layout.room_view,
                arrRoom
        );
        lvRoomList.setAdapter(adapter);
        //tinh tong so phong ban
        tvTotalRoom.setText(String.valueOf(arrRoom.size()));
        //chuyen sang chinh sua phong ban
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RoomListActivity.this, RoomActivity.class);
                startActivity(intent);
            }
        });

        //xem chi tiet tung nhan vien trong moi phong ban
        lvRoomList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(RoomListActivity.this,RoomDetailsActivity.class);
                intent.putExtra("idRoom", adapter.getItem(position).getRoomID());
                startActivity(intent);
            }
        });

    }
}