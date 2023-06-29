package com.example.quanlynhansu.Adapter;

import static com.example.quanlynhansu.MainActivity.RoleUser;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.quanlynhansu.R;
import com.example.quanlynhansu.object.Account;


import java.util.List;

public class AccountAdapter extends ArrayAdapter<Account> {

    private List<Account> accountList;

    public AccountAdapter(@NonNull Context context, List<Account> accountList) {
        super(context, 0, accountList);
        this.accountList = accountList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.account_view, parent, false);
        }

        Account account = accountList.get(position);
        TextView accountViewFullName = convertView.findViewById(R.id.accountViewFullName);
        TextView accountViewEmail = convertView.findViewById(R.id.accountViewEmail);
        TextView accountViewRole = convertView.findViewById(R.id.accountViewRole);
        TextView accountViewPhone = convertView.findViewById(R.id.accountViewPhone);

        accountViewFullName.setText(account.getFullName());
        accountViewEmail.setText(account.getEmail());
        accountViewRole.setText(RoleUser);
        accountViewPhone.setText(account.getPhone());



        return convertView;
    }
}

