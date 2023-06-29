package com.example.quanlynhansu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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

public class MainActivity extends AppCompatActivity  {
    Button Them, Xem, ChuyenPage;
    ListView Lview;
    RoleAccountHelper roleAccountHelper;
    RoleHelper roleHelper;
    public static String RoleUser = "Chưa xét vai trò" ;
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
        //Khởi tạo tìm vai trò của user
        searchRoleUser(1);
        ChuyenPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                goToSecondActivity(view,LeaveActivity.class);
                goToSecondActivity(view,UserManagement.class);

//                    goToSecondActivity(view,PersonalInformation.class);


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
    public void searchRoleUser(int idUser){
       List<RoleAccount> roleAccountList = roleAccountHelper.getAllRoleAccounts();
       List<Role> roles =  roleHelper.getAllRoles();
       for(RoleAccount roleAccount: roleAccountList){
           if(roleAccount.getAccountId() == idUser)
               for(Role role: roles){
                   if(role.getRoleId() == roleAccount.getRoleId())
                   {
                       RoleUser = role.getRoleName();
                       break;
                   }
               }
       }

    }


}