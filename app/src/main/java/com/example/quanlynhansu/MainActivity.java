package com.example.quanlynhansu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edtUser, edtPass;
    Button btnSign;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // init
        edtUser = (EditText) findViewById(R.id.inputTextEmail);
        edtPass= (EditText) findViewById(R.id.inputTextPass);
        btnSign = (Button) findViewById(R.id.btnSign);

        //set Onclick
        btnSign.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == btnSign.getId()) {
            // nếu user và pass không rỗng thì cho đăng nhập
            // ngược lại thông báo lỗi
            if (isStringEmpty(edtUser.getText().toString())) {
                edtUser.setError("Vui lòng nhập user hoặc email");
            }if (isStringEmpty(edtPass.getText().toString())) {
                edtPass.setError("Vui lòng nhập mật khẩu");
                return;
            } else {
                Intent intent = new Intent(this, UserActivity.class);
                startActivity(intent);
            }
        }
    }

    // trả về true nếu chuỗi trong
    private Boolean isStringEmpty(String s){
        return s.length() == 0 ? true:false;
    }

    // trả về true nếu user và pass đúng
    private Boolean isUser(){
        return true;
    }

}