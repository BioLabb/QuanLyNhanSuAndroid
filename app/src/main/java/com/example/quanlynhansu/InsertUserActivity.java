package com.example.quanlynhansu;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.quanlynhansu.object.Account;
import com.example.quanlynhansu.sqlitehelper.AccountHelper;
import com.example.quanlynhansu.sqlitehelper.RoleAccountHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class InsertUserActivity extends AppCompatActivity {

    private static final String FILE_NAME = "data.dat";
    Button btnInsertUser, btnListInsertUser;
    EditText editUserName, editPassword, editFullName, editAddress, editPhone, editEmail, editRoomId;
    AccountHelper accountHelper;
    RoleAccountHelper roleAccountHelper;
    LinearLayout onBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_user);

        btnInsertUser = findViewById(R.id.btnInsertUser);
        btnListInsertUser = findViewById(R.id.btnListInsertUser);
        editUserName = findViewById(R.id.editUserName);
        editPassword = findViewById(R.id.editPassword);
        editFullName = findViewById(R.id.editFullName);
        editEmail = findViewById(R.id.editEmail);
        editAddress = findViewById(R.id.editAddress);
        editPhone = findViewById(R.id.editNumber);
        editRoomId = findViewById(R.id.editRoomId);
        onBack = findViewById(R.id.onBack);

        accountHelper = new AccountHelper(InsertUserActivity.this);
        roleAccountHelper = new RoleAccountHelper(InsertUserActivity.this);
        JSONArray  myList = new JSONArray();

        JSONObject accountJson1 = createAccountJson("userNameDS1", "12345", "Hoang Nam", "nam@example.com", "123456789", "New York", 1);
        JSONObject accountJson2 = createAccountJson("userNameDS2", "12345", "Sinh Tien", "tien@example.com", "987654321", "Los Angeles", 2);
        JSONObject accountJson3 = createAccountJson("userNameDS3", "12345", "Hoang Huynh", "huynh@example.com", "456789123", "Ho Chi Minh City", 3);

        myList.put(accountJson1);
        myList.put(accountJson2);
        myList.put(accountJson3);

        // Thêm các phần tử khác vào mảng myList

        saveArrayToFile(myList);



        List<JSONObject> loadedList = loadArrayFromFile(); // Đọc JSONArray từ tệp

        for (JSONObject jsonObject : loadedList) {
            System.out.println(3);

            // Làm gì đó với mỗi đối tượng JSON trong danh sách đã đọc
        }
        onBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        btnInsertUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkErrorValue())
                {
                    int error = accountHelper.insertAccount(new Account(editUserName.getText().toString(), editPassword.getText().toString()
                            , editFullName.getText().toString(), editEmail.getText().toString()
                            , editPhone.getText().toString(), editAddress.getText().toString(),  Integer.parseInt(editRoomId.getText().toString())));
                    if(error == 1) {
                        Account account = accountHelper.getAccount(editUserName.getText().toString());
                        //Them vai tro, mac dinh user (2 )
                        roleAccountHelper.insertRoleAccount(2, account.getAccountID());
                        Toast.makeText(InsertUserActivity.this, "Đã thêm thành công", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }
                }


            }
        });
        btnListInsertUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<JSONObject> loadedList = loadArrayFromFile(); // Đọc JSONArray từ tệp

                // Làm gì đó với mỗi đối tượng JSON trong danh sách đã đọc
                AlertDialog.Builder builder = new AlertDialog.Builder(InsertUserActivity.this);
                builder.setTitle("Xác nhận thêm danh sách");
                builder.setMessage("Bạn có chắc chắn muốn thêm danh sách thành viên từ file " + FILE_NAME);

                // Xử lý khi người dùng nhấn nút Xác nhận (Có)
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Thực hiện lệnh
                        for (JSONObject jsonObject : loadedList) {
                            try {
                                String username = jsonObject.getString("username");
                                String password = jsonObject.getString("password");
                                String fullName = jsonObject.getString("full_name");
                                String email = jsonObject.getString("email");
                                String phone = jsonObject.getString("phone");
                                String address = jsonObject.getString("address");
                                int roomID = jsonObject.getInt("room_id");

                                accountHelper.insertAccount(new Account(username, password, fullName, email, phone, address, roomID));
                                Account account = accountHelper.getAccount(username);
                                //Them vai tro, mac dinh user (2)
                                roleAccountHelper.insertRoleAccount(2,account.getAccountID());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        Toast.makeText(InsertUserActivity.this, "Đã thêm thành công",Toast.LENGTH_SHORT).show();
                        onBackPressed();
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

    private void saveArrayToFile(JSONArray jsonArray) {
        try {
            FileOutputStream fos = openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            BufferedWriter bw = new BufferedWriter(osw);

            bw.write(jsonArray.toString());

            bw.close();
            osw.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<JSONObject> loadArrayFromFile() {
        List<JSONObject> jsonObjectList = new ArrayList<>();

        try {
            FileInputStream fis = openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();

            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            Log.d("File Data", sb.toString());
            br.close();
            isr.close();
            fis.close();

            JSONArray jsonArray = new JSONArray(sb.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                jsonObjectList.add(jsonObject);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObjectList;
    }

    public static JSONObject createAccountJson(String userName, String password, String fullName, String email, String phone, String address, int roomID) {
        JSONObject accountJson = new JSONObject();
        try {
            accountJson.put("username", userName);
            accountJson.put("password", password);
            accountJson.put("full_name", fullName);
            accountJson.put("email", email);
            accountJson.put("phone", phone);
            accountJson.put("address", address);
            accountJson.put("room_id", roomID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return accountJson;
    }

    public boolean checkErrorValue(){

       int count = 0;
        EditText[] editTexts = new EditText[] {
                editUserName, editPassword, editFullName, editEmail, editPhone, editAddress, editRoomId
        };

        for (EditText editText : editTexts) {
            if (editText.isEnabled()) {
                String hint = editText.getHint().toString();

                String errorMessage = hint + " không bỏ trống";

                String fieldValue = editText.getText().toString().trim();
//                nếu trống thì báo lỗi
                if (fieldValue.isEmpty()) {
                    editText.setError(errorMessage);
                    count++;
                }
            }
        }
        if (count == 0)
            return true;
        else
            return false;

    }


}