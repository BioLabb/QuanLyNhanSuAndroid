package com.example.quanlynhansu;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.quanlynhansu.object.Account;
import com.example.quanlynhansu.object.Role;
import com.example.quanlynhansu.object.RoleAccount;
import com.example.quanlynhansu.object.Room;
import com.example.quanlynhansu.sqlitehelper.AccountHelper;
import com.example.quanlynhansu.sqlitehelper.RoleAccountHelper;
import com.example.quanlynhansu.sqlitehelper.RoleHelper;
import com.example.quanlynhansu.sqlitehelper.RoomHelper;
import com.example.quanlynhansu.store.AccountStore;

import java.util.List;

public class PersonalInformation extends AppCompatActivity {
    AccountHelper accountHelper;
    Button btnEditInformation;
    EditText editLoginName;
    EditText editFullName ;
    EditText editEmail;
    EditText editNumber ;
    EditText editAddress ;
    TextView txtLable;
    Spinner spinnerRole, spinnerRoom;
    RoleHelper roleHelper;
    RoleAccountHelper roleAccountHelper;
    RoomHelper roomHelper;

    LinearLayout onBack;
    int idUser;
    String roleDelete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_information);

//        int idUser = 1;
        Intent intent = getIntent();
        idUser = intent.getIntExtra("accountID", 1);
        roleDelete = intent.getStringExtra("roleDelete");
        System.out.println(idUser);
        System.out.println(roleDelete);



        // Sử dụng các giá trị nhận được ở đây theo nhu cầu của bạn
        // Ví dụ:
        Log.d("PersonalInformation", "accountID: " + idUser);
        Log.d("PersonalInformation", "store: " +  AccountStore.getUser().getAccountID());

        accountHelper = new AccountHelper(PersonalInformation.this);
        roleHelper = new RoleHelper(PersonalInformation.this);
        roomHelper = new RoomHelper(PersonalInformation.this);
        roleAccountHelper = new RoleAccountHelper(PersonalInformation.this);
        btnEditInformation = findViewById(R.id.btnEditInformation);
        editLoginName = findViewById(R.id.editLoginName);
        editFullName = findViewById(R.id.editFullName);
        editEmail = findViewById(R.id.editEmail);
        editNumber = findViewById(R.id.editNumber);
        editAddress = findViewById(R.id.editAddress);
        txtLable = findViewById(R.id.txtLable);
        spinnerRole = findViewById(R.id.spinnerRole);
        spinnerRoom = findViewById(R.id.spinnerRoom);
        onBack = findViewById(R.id.onBack);

        //Tắt đối với giao diện xem bình thường
        spinnerRole.setEnabled(false);
        spinnerRoom.setEnabled(false);


        setLayoutUser(idUser);

        //Tạo btnDelete
        if(!"User".equals(roleDelete)) {


            createBtnDelete();
        }

        onBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        btnEditInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(btnEditInformation.getText() == "Cập nhập"){
                    AlertDialog.Builder builder = new AlertDialog.Builder(PersonalInformation.this);
                    builder.setTitle("Xác nhận sửa thông tin");
                    builder.setMessage("Bạn có chắc chắn muốn sửa thông tin?");

                    // Xử lý khi người dùng nhấn nút Xác nhận (Có)
                    builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Thực hiện lệnh xóa ở đây
                            editFullName.setEnabled(false);
                            editEmail.setEnabled(false);
                            editNumber.setEnabled(false);
                            editAddress.setEnabled(false);
                            updateAccout(idUser);
                            btnEditInformation.setText("Sửa thông tin");
                            txtLable.setText("Thông tin cá nhân");
                        }
                    });

                    // Xử lý khi người dùng nhấn nút Hủy (Không)
                    builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Đóng hộp thoại
                            dialog.dismiss();
                        }
                    });

                    // Tạo và hiển thị AlertDialog
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();



                }
                else
                {
                    editFullName.setEnabled(true);
                    editEmail.setEnabled(true);
                    editNumber.setEnabled(true);
                    editAddress.setEnabled(true);
                    if(!roleDelete.equals("User")) {
                        spinnerRole.setEnabled(true);
                        spinnerRoom.setEnabled(true);
                    }
                    btnEditInformation.setText("Cập nhập");
                    txtLable.setText("Sửa thông tin cá nhân");

                }
            }
        });


    }
    public void setLayoutUser(int idUser){
        System.out.println("setlayout");
        Account account = accountHelper.getAccount(idUser);
        String role = roleAccountHelper.searchRoleUser(idUser);
        System.out.println(role);
        editLoginName.setText(account.getUserName());
        editFullName.setText(account.getFullName());
        editEmail.setText(account.getEmail());
        editNumber.setText(account.getPhone());
        editAddress.setText(account.getAddress());
        // Tạo Spinner

        List<Role> roleList = roleHelper.getAllRoles();
        // Tìm vị trí của đối tượng có ten role cần tìm trong danh sách
        int positionOfDefaultValue = -1; // Giá trị mặc định nếu không tìm thấy
        for (int i = 0; i < roleList.size(); i++) {
            Role obj = roleList.get(i);
            if (obj.getRoleName().equals(role)) {
                positionOfDefaultValue = i;
                break; // Tìm thấy đối tượng, thoát khỏi vòng lặp
            }
        }

        // Định nghĩa ArrayAdapter để hiển thị tên của các đối tượng trong Spinner
        ArrayAdapter<Role> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, roleList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Gán adapter cho Spinner
        spinnerRole.setAdapter(adapter);
// Lấy đối tượng đã chọn khi người dùng chọn một lựa chọn trong Spinner
//        YourObject selectedObject = (YourObject) spinner.getSelectedItem();
// Đặt vị trí tìm được làm giá trị mặc định của Spinner
        if (positionOfDefaultValue != -1) {
            spinnerRole.setSelection(positionOfDefaultValue);
        } else {
            // Nếu không tìm thấy đối tượng có tên "chien", bạn có thể xử lý hoặc đặt một giá trị mặc định khác ở đây
            // Ví dụ:
            spinnerRole.setSelection(0); // Đặt vị trí đầu tiên làm giá trị mặc định
            // Hoặc hiển thị một thông báo lỗi cho người dùng
        }

//---
        // Tạo Spinner

        List<Room> roomList = roomHelper.getAllRooms();
        // Tìm vị trí của đối tượng có ten role cần tìm trong danh sách
        int positionOfDefaultValueRoom = -1; // Giá trị mặc định nếu không tìm thấy
        for (int i = 0; i < roomList.size(); i++) {
            Room obj = roomList.get(i);
            if (obj.getRoomID() == account.getRoomID()) {
                System.out.println(obj.getRoomID());
                System.out.println(account.getRoomID());

                positionOfDefaultValueRoom = i;
                break; // Tìm thấy đối tượng, thoát khỏi vòng lặp
            }
        }

        // Định nghĩa ArrayAdapter để hiển thị tên của các đối tượng trong Spinner
        ArrayAdapter<Room> adapterRoom = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, roomList);
        adapterRoom.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Gán adapter cho Spinner
        spinnerRoom.setAdapter(adapterRoom);
// Lấy đối tượng đã chọn khi người dùng chọn một lựa chọn trong Spinner
//        YourObject selectedObject = (YourObject) spinner.getSelectedItem();
// Đặt vị trí tìm được làm giá trị mặc định của Spinner
        if (positionOfDefaultValueRoom != -1) {
            spinnerRoom.setSelection(positionOfDefaultValueRoom);
        } else {
            // Nếu không tìm thấy đối tượng có tên "chien", bạn có thể xử lý hoặc đặt một giá trị mặc định khác ở đây
            // Ví dụ:
            spinnerRoom.setSelection(0); // Đặt vị trí đầu tiên làm giá trị mặc định
            // Hoặc hiển thị một thông báo lỗi cho người dùng
        }


    }
    public void updateAccout(int idUser){
        Account account = accountHelper.getAccount(idUser);

        account.setFullName(editFullName.getText().toString());
        account.setEmail(editEmail.getText().toString());
        account.setPhone(editNumber.getText().toString());
        account.setAddress(editAddress.getText().toString());
        // Lấy đối tượng đã chọn khi người dùng chọn một lựa chọn trong Spinner
        Role selectedRole = (Role) spinnerRole.getSelectedItem();
        Room selectedRoom = (Room) spinnerRoom.getSelectedItem();
        account.setRoomID(selectedRoom.getRoomID());
        RoleAccount roleAccount = searchRoleAccount(idUser);
        roleAccountHelper.deleteRoleAccount(roleAccount.getRoleId(),account.getAccountID());
        roleAccountHelper.insertRoleAccount(selectedRole.getRoleId(),account.getAccountID());
        accountHelper.updateAccount(account);

    }
    public void createBtnDelete(){

        LinearLayout layoutSubmit = findViewById(R.id.layoutSubmit);
        Button btnDelete = new Button(PersonalInformation.this);
        btnDelete.setText("Xóa");
        btnDelete.setBackground(new ColorDrawable(Color.RED));
        btnDelete.setHeight(48);
        layoutSubmit.addView(btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PersonalInformation.this);
                builder.setTitle("Xác nhận xóa");
                builder.setMessage("Bạn có chắc muốn xóa tài khoản này không?");

                // Xử lý khi người dùng nhấn nút Xác nhận (Có)
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Thực hiện lệnh xóa ở đây
                        System.out.println("sdf");
                        System.out.println(idUser);


                        accountHelper.removeAccountChien(idUser);
                    }
                });

                // Xử lý khi người dùng nhấn nút Hủy (Không)
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Đóng hộp thoại
                        dialog.dismiss();
                    }
                });

                // Tạo và hiển thị AlertDialog
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }
    public String searchRoleUser(int idUser){
        roleAccountHelper = new RoleAccountHelper(PersonalInformation.this);
        roleHelper = new RoleHelper(PersonalInformation.this);
        List<RoleAccount> roleAccountList = roleAccountHelper.getAllRoleAccounts();
        List<Role> roles =  roleHelper.getAllRoles();
        for(RoleAccount roleAccount: roleAccountList){
            if(roleAccount.getAccountId() == idUser)
                for(Role role: roles){
                    if(role.getRoleId() == roleAccount.getRoleId())
                    {

                        return role.getRoleName().toString();
                    }
                }
        }
        return "Chưa xét";

    }

    public RoleAccount searchRoleAccount(int idUser){
        roleAccountHelper = new RoleAccountHelper(PersonalInformation.this);
        roleHelper = new RoleHelper(PersonalInformation.this);
        List<RoleAccount> roleAccountList = roleAccountHelper.getAllRoleAccounts();

        for(RoleAccount roleAccount: roleAccountList){
            if(roleAccount.getAccountId() == idUser)
                return roleAccount;
        }
        return null;

    }
}