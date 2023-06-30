package com.example.quanlynhansu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.example.quanlynhansu.Adapter.AccountAdapter;
import com.example.quanlynhansu.object.Account;
import com.example.quanlynhansu.object.Role;
import com.example.quanlynhansu.object.RoleAccount;
import com.example.quanlynhansu.object.Room;
import com.example.quanlynhansu.sqlitehelper.AccountHelper;
import com.example.quanlynhansu.sqlitehelper.RoleAccountHelper;
import com.example.quanlynhansu.sqlitehelper.RoleHelper;
import com.example.quanlynhansu.sqlitehelper.RoomHelper;

import java.util.ArrayList;
import java.util.List;

public class UserManagement extends AppCompatActivity {
    ListView listUser ;
    private AccountAdapter adapter;
    AccountHelper accountHelper;
    RoomHelper roomHelper;
    RoleHelper roleHelper;
    RoleAccountHelper roleAccountHelper;
    LinearLayout onBack;
    Spinner spinnerRoom,spinnerRole;
    SearchView searchAccount;
    Button searchCondition;
    ImageView imgSetting;
    TextView txtLableSetting;
    List<Account> accountList;
    String searchValue = "fullName";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_management);

        onBack = findViewById(R.id.onBack);
        accountHelper = new AccountHelper(UserManagement.this);
        roleHelper = new RoleHelper(UserManagement.this);
        roomHelper = new RoomHelper(UserManagement.this);
        roleAccountHelper = new RoleAccountHelper(UserManagement.this);
        listUser = findViewById(R.id.listUser);
        searchAccount = findViewById(R.id.searchAccount);
        spinnerRole = findViewById(R.id.spinnerRole);
        spinnerRoom = findViewById(R.id.spinnerRoom);
        searchCondition = findViewById(R.id.searchCondition);
        imgSetting = findViewById(R.id.imgSetting);
        txtLableSetting = findViewById(R.id.txtLableSetting);

         accountList = accountHelper.getAllAccountsChien();
        //khoi tao vaf gan adapter
        adapter = new AccountAdapter(this,accountList );
        listUser.setAdapter(adapter);
        initSpenner();
        onBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        listUser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Lấy đối tượng Account tại vị trí được chọn
                Account selectedAccount = adapter.getItem(position);

                // Lấy thông tin từ đối tượng Account

                int accountID = selectedAccount.getAccountID();


                // Tạo Intent để chuyển đến Activity mới và truyền thông tin
                Intent intent = new Intent(UserManagement.this, PersonalInformation.class);
                intent.putExtra("accountID", accountID);
                startActivity(intent);
            }
        });
        searchAccount.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Xử lý tìm kiếm khi người dùng nhấn nút tìm kiếm trên bàn phím
                search(query,searchValue);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Xử lý tìm kiếm theo từ khoá newText khi người dùng nhập liệu vào SearchView
                search(newText,searchValue);
                return false;
            }
        });
        searchCondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lấy đối tượng đã chọn khi người dùng chọn một lựa chọn trong Spinner
                Role selectedRole = (Role) spinnerRole.getSelectedItem();
                Room selectedRoom = (Room) spinnerRoom.getSelectedItem();
                searchCondition(selectedRole,selectedRoom);
            }
        });

        String[] items = {"Lựa chọn phưng thức tìm kiếm", "Tìm Theo Tên", "Tìm Theo Email", "Tìm Theo Số DT","Tìm Theo Địa Chỉ","Tìm Theo Phòng Ban"};

        imgSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(UserManagement.this, imgSetting);
                for (int i = 0; i < items.length; i++) {
                    popupMenu.getMenu().add(Menu.NONE, i, i, items[i]);
                }
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        // Xử lý khi người dùng chọn giá trị từ danh sách
                        String selectedOption = items[item.getItemId()];
                        txtLableSetting.setText(selectedOption);
                        System.out.println(item.getItemId());
                        switch(item.getItemId()){
                            case 1:
                                searchValue = "fullName";
                                break;
                            case 2:
                                searchValue = "email";
                                break;
                            case 3:
                                searchValue = "phone";
                                break;
                            case 4:
                                searchValue = "address";
                                break;
                            case 5:
                                searchValue = "room";
                                break;
                            default:
                                break;
                        }
                        // Thực hiện hành động với giá trị đã chọn
                        return true;
                    }
                });
                popupMenu.show();
            }
        });


    }
    private void search(String query, String field) {
        List<Account> accountListNew = new ArrayList<>();
        for (Account item : accountList) {
            String fieldValue;
            switch (field) {
                case "fullName":
                    fieldValue = item.getFullName().toLowerCase();
                    break;
                case "email":
                    fieldValue = item.getEmail().toLowerCase();
                    break;
                case "phone":
                    fieldValue = item.getPhone().toLowerCase();
                    break;
                case "address":
                    fieldValue = item.getAddress().toLowerCase();
                    break;
                case "room":
                   Room room = roomHelper.getRoom(item.getRoomID());
                    fieldValue = room.getName().toLowerCase();
                    break;
                default:
                    fieldValue = ""; // Trường hợp không khớp, giá trị rỗng
                    break;
            }

            if (fieldValue.contains(query.toLowerCase())) {
                accountListNew.add(item);
            }
        }

        // Hiển thị danh sách filteredItems lên giao diện người dùng
        // Ví dụ: cập nhật Adapter của ListView hoặc RecyclerView
        AccountAdapter adapterNew = new AccountAdapter(this, accountListNew);
        listUser.setAdapter(adapterNew);
    }

    private void searchCondition(Role role, Room room) {
        List<Account> accountListNew = new ArrayList<>();
        List<Account> accountListFinal = new ArrayList<>();

        List<RoleAccount> roleAccountList = roleAccountHelper.getAllRoleAccounts();
        AccountAdapter adapterNew;
        if(room.getName().equals("Tất cả"))
        {
            accountListNew = accountList;
        }
        else
        {
            for (Account item : accountList) {
                if (item.getRoomID() == room.getRoomID()) {
                    accountListNew.add(item);
                }
            }
        }

        if(role.getRoleName().equals("Tất cả")){
            adapterNew = new AccountAdapter(this,accountListNew );

        }else {
            for (RoleAccount item : roleAccountList) {
                if (item.getRoleId() == role.getRoleId()) {
                    for (Account account : accountListNew) {
                        if (account.getAccountID() == item.getAccountId()) {
                            accountListFinal.add(account);
                        }
                    }
                }
            }
            adapterNew = new AccountAdapter(this,accountListFinal );

        }

        // Hiển thị danh sách filteredItems lên giao diện người dùng
        // Ví dụ: cập nhật Adapter của ListView hoặc RecyclerView
        listUser.setAdapter(adapterNew);
    }
    private void initSpenner(){
        // Tạo Spinner

        List<Role> roleList = roleHelper.getAllRoles();
        Role roleTam = new Role("Tất cả");
        roleList.add(0,roleTam);


        // Định nghĩa ArrayAdapter để hiển thị tên của các đối tượng trong Spinner
        ArrayAdapter<Role> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, roleList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Gán adapter cho Spinner
        spinnerRole.setAdapter(adapter);
// Lấy đối tượng đã chọn khi người dùng chọn một lựa chọn trong Spinner
//        YourObject selectedObject = (YourObject) spinner.getSelectedItem();
// Đặt vị trí tìm được làm giá trị mặc định của Spinner

            spinnerRole.setSelection(0); // Đặt vị trí đầu tiên làm giá trị mặc định

//---
        // Tạo Spinner

        List<Room> roomList = roomHelper.getAllRooms();
        Room room = new Room("Tất cả",0);
        roomList.add(0,room);

        // Định nghĩa ArrayAdapter để hiển thị tên của các đối tượng trong Spinner
        ArrayAdapter<Room> adapterRoom = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, roomList);
        adapterRoom.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Gán adapter cho Spinner
        spinnerRoom.setAdapter(adapterRoom);
// Lấy đối tượng đã chọn khi người dùng chọn một lựa chọn trong Spinner
//        YourObject selectedObject = (YourObject) spinner.getSelectedItem();
// Đặt vị trí tìm được làm giá trị mặc định của Spinner

            spinnerRoom.setSelection(0); // Đặt vị trí đầu tiên làm giá trị mặc định


    }
    //Cập nhập lại listview khi onback
    @Override
    protected void onResume() {
        super.onResume();
        // Gọi lại hàm lấy dữ liệu từ CSDL (hoặc từ nguồn dữ liệu khác)
        accountList = accountHelper.getAllAccountsChien();
        //khoi tao vaf gan adapter
        adapter = new AccountAdapter(this,accountList );
        listUser.setAdapter(adapter);
    }


}