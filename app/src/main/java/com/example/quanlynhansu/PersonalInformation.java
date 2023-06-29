package com.example.quanlynhansu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.example.quanlynhansu.object.Account;
import com.example.quanlynhansu.sqlitehelper.AccountHelper;

public class PersonalInformation extends AppCompatActivity {
    AccountHelper accountHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_information);

        int idUser = 1;
        accountHelper = new AccountHelper(PersonalInformation.this);
        setLayoutUser(idUser);


    }
    public void setLayoutUser(int idUser){
        Account account = accountHelper.getAccount(1);
        EditText editLoginName = findViewById(R.id.editLoginName);
        EditText editFullName = findViewById(R.id.editFullName);
        EditText editEmail = findViewById(R.id.editEmail);
        EditText editNumber = findViewById(R.id.editNumber);
        EditText editAddress = findViewById(R.id.editAddress);

        editLoginName.setText(account.getUserName());
        editFullName.setText(account.getFullName());
        editEmail.setText(account.getEmail());
        editNumber.setText(account.getPhone());
        editAddress.setText(account.getAddress());

    }

}