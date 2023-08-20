package com.example.quanlynhansu;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlynhansu.Adapter.RoomAdapter;
import com.example.quanlynhansu.object.Room;
import com.example.quanlynhansu.sqlitehelper.RoomHelper;

import java.util.ArrayList;

public class RoomActivity extends AppCompatActivity {

    ListView lvRoom;
    ArrayList<Room> arrRoom;
    EditText edtRoom;
    TextView tvTotalRoom;
    Button btnAddRoom;
    Button btnUpdateRoom;
    int _posId;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        btnUpdateRoom = (Button) findViewById(R.id.btnUpdateRoom);
        btnAddRoom = (Button) findViewById(R.id.btnAddRoom);
        lvRoom = (ListView) findViewById(R.id.listViewRoom);
        edtRoom = (EditText) findViewById(R.id.edtInputRoom);
        tvTotalRoom = (TextView) findViewById(R.id.tvTotalRoom);

        RoomHelper roomHelper = new RoomHelper(RoomActivity.this);
        //data khởi tạo ban đầu
        arrRoom = (ArrayList<Room>) roomHelper.getAllRooms();

        RoomAdapter adapter = new RoomAdapter(
                RoomActivity.this,
                R.layout.room_view,
                arrRoom
        );
        lvRoom.setAdapter(adapter);

        //them room vao listview
        btnAddRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //lay data ma user nhap tu input
                String nameRoom = edtRoom.getText().toString();
                Toast.makeText(RoomActivity.this,"ban da them thanh cong " + nameRoom,Toast.LENGTH_SHORT).show();
                //them room vao database
                roomHelper.insertRoom(new Room(nameRoom,0));
                //reset lai giao dien listview
                arrRoom.clear();
                //update lai mang ra giao dien
                arrRoom.addAll(roomHelper.getAllRooms());
                //lang nghe su thay doi cua data
                adapter.notifyDataSetChanged();
                tvTotalRoom.setText(String.valueOf(arrRoom.size()));
                edtRoom.setText("");
            }
        });

        //sua ten room lisview
        lvRoom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                _posId = arrRoom.get(position).getRoomID();
                String nameRoom = arrRoom.get(position).getName();
                edtRoom.setText(nameRoom);
            }
        });
        btnUpdateRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //lay data tu input user nhap vao
                String nameRoom = edtRoom.getText().toString();
                Room roomItem = new Room(nameRoom,0);
                //chinh sua xong va gan lai vao database
                roomHelper.updateNameRoom(roomItem,_posId);
                //reset lai giao dien listview
                arrRoom.clear();
                //update lai mang ra giao dien
                arrRoom.addAll(roomHelper.getAllRooms());
                //lang nghe su thay doi cua data
                adapter.notifyDataSetChanged();
                edtRoom.setText("");
            }
        });

        //xoa room trong listview
        lvRoom.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                new AlertDialog.Builder(RoomActivity.this)
                        .setTitle("Bạn muốn xóa " + arrRoom.get(position))
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //lay id room tai vi tri dang click
                                int posIdRoom = arrRoom.get(position).getRoomID();
                                //xoa room trong database
                                roomHelper.removeRoom(posIdRoom);
                                //reset lai giao dien listview
                                arrRoom.clear();
                                //update lai mang ra giao dien
                                arrRoom.addAll(roomHelper.getAllRooms());
                                //lang nghe su thay doi cua data
                                adapter.notifyDataSetChanged();
                                tvTotalRoom.setText(String.valueOf(arrRoom.size()));
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
