package com.example.quanlynhansu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.quanlynhansu.fragment.HomeFragment;
import com.example.quanlynhansu.object.Account;
import com.example.quanlynhansu.sqlitehelper.AccountHelper;
import com.example.quanlynhansu.sqlitehelper.RoleAccountHelper;
import com.example.quanlynhansu.sqlitehelper.SQLiteHelper;
import com.example.quanlynhansu.store.AccountStore;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edtUser, edtPass;
    Button btnLogin;
    Button btnSign;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init View
        edtUser = (EditText) findViewById(R.id.inputTextUser);
        edtPass = (EditText) findViewById(R.id.inputTextPass);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnSign = (Button) findViewById(R.id.btn_sign);
        //init
        context = MainActivity.this;
        SQLiteHelper sqLiteHelper = new SQLiteHelper(context);

        //set Onclick
        btnLogin.setOnClickListener(this);
        btnSign.setOnClickListener(this);
    }

    // bắt sự kiên onclick các Button
    @Override
    public void onClick(View v) {
        // khi button sign được click
        if(v.getId() == btnLogin.getId()) {
            AccountHelper accountHelper = new AccountHelper(this);
            // kiểm tra user có tồn tại hay ko
            // nếu phải chuyển sang activity khác
            if(isUser()){
                System.out.println("isAdmin"+ isAdmin());
                if(isAdmin()){
                    changeActivity(context, AdminActivity.class);
                }else{
                    changeActivity(context, UserActivity.class);
                }
            }else {
                Toast.makeText(this,"incorrect user or password",Toast.LENGTH_SHORT).show();
            }
        }else if( v.getId() == btnSign.getId()){
            changeActivity(context,InsertUserActivity.class);
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
            Account account = accountHelper.getAccount(userName,edtPass.getText().toString().trim());


            if(null != account){
                AccountStore.setUser(account);
                return true;
            }
            return false;
        }
        return false;
    }

    private Boolean isAdmin(){
       RoleAccountHelper roleAccountHelper = new RoleAccountHelper(context);
       if ( roleAccountHelper.searchRoleUser(AccountStore.getUser().getAccountID()).equals("Admin") )
       {
           return true;
       }
       return false;
    }

    private void changeActivity(Context context, Class<?> activity){
        Intent intent = new Intent(context,activity);
        startActivity(intent);
    }
}
