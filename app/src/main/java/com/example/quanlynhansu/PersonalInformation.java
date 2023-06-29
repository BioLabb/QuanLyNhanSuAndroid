package com.example.quanlynhansu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.quanlynhansu.object.Account;
import com.example.quanlynhansu.sqlitehelper.AccountHelper;

public class PersonalInformation extends AppCompatActivity {
    AccountHelper accountHelper;
    Button btnEditInformation;
    EditText editLoginName;
    EditText editFullName ;
    EditText editEmail;
    EditText editNumber ;
    EditText editAddress ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_information);

        int idUser = 1;
        accountHelper = new AccountHelper(PersonalInformation.this);
        btnEditInformation = findViewById(R.id.btnEditInformation);
        editLoginName = findViewById(R.id.editLoginName);
        editFullName = findViewById(R.id.editFullName);
        editEmail = findViewById(R.id.editEmail);
        editNumber = findViewById(R.id.editNumber);
        editAddress = findViewById(R.id.editAddress);

        setLayoutUser(idUser);

        btnEditInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(btnEditInformation.getText() == "Cập nhập"){
                    editFullName.setEnabled(false);
                    editEmail.setEnabled(false);
                    editNumber.setEnabled(false);
                    editAddress.setEnabled(false);
                    updateAccout(idUser);
                    btnEditInformation.setText("Sửa thông tin");

                }
                else
                {
                    editFullName.setEnabled(true);
                    editEmail.setEnabled(true);
                    editNumber.setEnabled(true);
                    editAddress.setEnabled(true);
                    btnEditInformation.setText("Cập nhập");
                }
            }
        });


    }
    public void setLayoutUser(int idUser){
        Account account = accountHelper.getAccount(idUser);


        editLoginName.setText(account.getUserName());
        editFullName.setText(account.getFullName());
        editEmail.setText(account.getEmail());
        editNumber.setText(account.getPhone());
        editAddress.setText(account.getAddress());

    }
    public void updateAccout(int idUser){
        Account account = accountHelper.getAccount(idUser);

        account.setFullName(editFullName.getText().toString());
        account.setEmail(editEmail.getText().toString());
        account.setPhone(editNumber.getText().toString());
        account.setAddress(editAddress.getText().toString());
        accountHelper.updateAccount(account);

    }


}