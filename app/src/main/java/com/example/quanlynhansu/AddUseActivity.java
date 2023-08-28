package com.example.quanlynhansu;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.quanlynhansu.object.Account;
import com.example.quanlynhansu.sqlitehelper.AccountHelper;

public class AddUseActivity extends Activity {

    EditText edtName, edtUser,edtPass,edtMail,edtPhone,edtAddress;
    Button btnAdd;
    LinearLayout onBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_use);


        this.init();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AccountHelper accountHelper = new AccountHelper(getApplicationContext());
                if(isEmpty(edtUser.getText().toString()) || isEmpty(edtPass.getText().toString() )||
                    isEmpty(edtName.getText().toString())||
                    isEmpty(edtMail.getText().toString())||
                    isEmpty(edtPhone.getText().toString())||
                    isEmpty(edtAddress.getText().toString())){
                    Toast.makeText(getApplicationContext(),"Không được để trống",Toast.LENGTH_SHORT).show();
                }
                else if(accountHelper.getAccount(edtUser.getText().toString().trim()) != null){
                    Toast.makeText(getApplicationContext(),"Use da duoc dang ky",Toast.LENGTH_SHORT).show();
                }else{
                    Account account = new Account(edtUser.getText().toString(),edtPass.getText().toString(),
                            edtName.getText().toString(),edtMail.getText().toString(),edtPhone.getText().toString(),
                            edtAddress.getText().toString(), 1);
                    accountHelper.insertAccount(account);
                    Toast.makeText(getApplicationContext(),"thêm thành công",Toast.LENGTH_SHORT).show();
                }
            }
        });
        onBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void init(){
        edtUser = (EditText) findViewById(R.id.editLoginName);
        edtPass = (EditText) findViewById(R.id.edit_pass);
        edtName = (EditText) findViewById(R.id.editFullName);
        edtMail = (EditText) findViewById(R.id.editEmail);
        edtPhone= (EditText) findViewById(R.id.editNumber);
        edtAddress = (EditText) findViewById(R.id.editAddress);
        onBack = findViewById(R.id.onBack);
        btnAdd = (Button) findViewById(R.id.btnAdd);

    }

    private Boolean isEmpty(String string){
        return  string.trim().length() == 0;
    }

}