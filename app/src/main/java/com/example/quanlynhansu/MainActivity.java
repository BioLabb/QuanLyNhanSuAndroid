package com.example.quanlynhansu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.quanlynhansu.object.Account;
import com.example.quanlynhansu.object.Room;
import com.example.quanlynhansu.sqlitehelper.AccountHelper;
import com.example.quanlynhansu.sqlitehelper.RoomHelper;
import com.example.quanlynhansu.sqlitehelper.SQLiteHelper;
import com.example.quanlynhansu.store.AccountStore;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edtUser, edtPass;
    Button btnSign;

    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // init View
        edtUser = (EditText) findViewById(R.id.inputTextUser);
        edtPass= (EditText) findViewById(R.id.inputTextPass);
        btnSign = (Button) findViewById(R.id.btnSign);

        //init
        context = MainActivity.this;
        SQLiteHelper sqLiteHelper = new SQLiteHelper(context);



        //set Onclick
        btnSign.setOnClickListener(this);
    }

    // bắt sự kiên onclick các Button
    @Override
    public void onClick(View v) {
        if(v.getId() == btnSign.getId()) {
            if(isUser()){
                Intent intent = new Intent(this, UserActivity.class);
                startActivity(intent);
            }else {
                Toast.makeText(this,"incorrect user or password",Toast.LENGTH_SHORT).show();
            }
        }
    }

   // if valid user
   //  setError is null and return true
   // contrary
   //  setError and return false
   private Boolean validUser(){
        String user = edtUser.getText().toString().trim();
        if(user.length() > 0){
            edtUser.setError(null);
            return true;
        }else{
            edtUser.setError("Require");
            return false;
        }
   }

    // if valid pass
    //  setError is null and return true
    // contrary
    //  setError and return false
   private Boolean validPass(){
        String pass = edtPass.getText().toString().trim();
        if(pass.length() >0){
            edtPass.setError(null);
            return true;
        }else {
            edtPass.setError("requite");
            return false;
        }
   }


   // kiểm tra validData trước khi login
   private Boolean confirmInput(){
        if(!validUser() || !validPass()){
            return false;
        }else
            return true;
   }

   // kiểm tra user có trong data hay không
    // nếu có cho đăng nhập và lưu thông tin account vào static
    private Boolean isUser(){
        if(confirmInput()){
            AccountHelper accountHelper = new AccountHelper(context);

            String userName = edtUser.getText().toString().trim();
            Account account = accountHelper.getAccount(userName);


            if(null != account){
                AccountStore.setUser(account);
                return true;
            }
            return false;
        }
        return false;
    }

}
