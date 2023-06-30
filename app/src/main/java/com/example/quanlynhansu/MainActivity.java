package com.example.quanlynhansu;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlynhansu.Adapter.RoomAdapter;
import com.example.quanlynhansu.object.Account;
import com.example.quanlynhansu.object.Role;
import com.example.quanlynhansu.object.RoleAccount;
import com.example.quanlynhansu.object.Room;
import com.example.quanlynhansu.sqlitehelper.AccountHelper;
import com.example.quanlynhansu.sqlitehelper.RoleAccountHelper;
import com.example.quanlynhansu.sqlitehelper.RoleHelper;
import com.example.quanlynhansu.sqlitehelper.RoomHelper;
import com.example.quanlynhansu.sqlitehelper.SQLiteHelper;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button Them, Xem, ChuyenPage;
    ListView Lview;
    RoleAccountHelper roleAccountHelper;
    RoleHelper roleHelper;
    final int accountID = 2;
    final String roleDelete = "User";
    // nếu là người dùng thì chuyển thành User
    private RoomAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        SQLiteHelper sqLiteHelper = new SQLiteHelper(MainActivity.this);
        RoomHelper roomHelper = new RoomHelper(MainActivity.this);
        AccountHelper accountHelper = new AccountHelper(MainActivity.this);
        roleAccountHelper = new RoleAccountHelper(MainActivity.this);
        roleHelper = new RoleHelper(MainActivity.this);
        Them = findViewById(R.id.Them);
        Xem = findViewById(R.id.Xem);
        ChuyenPage = findViewById(R.id.btnPageChange);
        Lview = findViewById(R.id.Lview);
        // Lấy dữ liệu từ Room
        List<Room> roomList = roomHelper.getAllRooms();

        // Tạo adapter và gắn dữ liệu vào ListView
        adapter = new RoomAdapter(this, roomList);
        Lview.setAdapter(adapter);
        //account test
//        Account account = new Account(2,"useName","12345","sinhTien","email12","01424234","adress",1);
//        accountHelper.insertAccount(account);

        ChuyenPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                goToSecondActivity(view,LeaveActivity.class);
                goToSecondActivity(view,UserManagement.class);
//----
                // Tạo Intent để chuyển đến Activity mới và truyền thông tin KHi đăng nhập xong chạp hàm này
//                Intent intent = new Intent(MainActivity.this, PersonalInformation.class);
//                intent.putExtra("accountID", accountID);
//                intent.putExtra("roleDelete",roleDelete);
//                startActivity(intent);



            }
        });
        Them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                roomHelper.insertRoom("chien",10);
                roomHelper.insertRoom("chien",10);
                roomHelper.removeRoom(3);
            }
        });
        Xem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                adapter = new RoomAdapter(MainActivity.this, roomHelper.getAllRooms());
                Lview.setAdapter(adapter);
            }
        });


    }
    // Phương thức xử lý sự kiện khi nhấn vào nút chuyển trang
    public void goToSecondActivity(View view, Class classname) {
        Intent intent = new Intent(this, classname);
        startActivity(intent);
    }



}